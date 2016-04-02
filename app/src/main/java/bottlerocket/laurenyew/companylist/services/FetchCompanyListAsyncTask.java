package bottlerocket.laurenyew.companylist.services;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import bottlerocket.laurenyew.companylist.cache.CompanyDetailCache;
import bottlerocket.laurenyew.companylist.Constants;
import bottlerocket.laurenyew.companylist.model.Stores;

/**
     * Created by laurenyew on 4/1/16.
     *
     * Fetch the JSON from the provided URL. Change into CompanyDetail model, and save to the CompanyDetailCache.
     * Params: url
     * Progress report: n/a
     * Result: Result enum
     */
    public class FetchCompanyListAsyncTask extends AsyncTask<String, Void, Result> {

    public interface FetchCompanyListUpdateListener {
        void onStartFetch();
        void onFetchComplete(Result result);
    }
    private List<FetchCompanyListUpdateListener> listeners = new ArrayList<FetchCompanyListUpdateListener>();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        notifyListenersOfStartFetch();
    }

    @Override
        protected Result doInBackground(String... params)
        {
            if(params != null && params.length == 1)
            {
                String url = params[0];

                try {
                    fetchCompanyList(url);
                }catch(IOException e)
                {
                    return Result.DATA_CONNECTION_NOT_AVAILABLE;
                }

                return Result.SUCCESS;
            }

            return Result.UNKNNOWN_ERROR;
        }

        private void fetchCompanyList(String urlPath) throws IOException {
            System.out.println("fetchCompanyList for url: " + urlPath);

            Stores result = null;

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            URL url = new URL(urlPath);

            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-length", "0");
                urlConnection.setUseCaches(false);
                urlConnection.setAllowUserInteraction(false);
                urlConnection.setConnectTimeout(Constants.DEFAULT_CONNECTION_TIMEOUT);
                urlConnection.setReadTimeout(Constants.DEFAULT_CONNECTION_TIMEOUT);
                urlConnection.connect();
                int status = urlConnection.getResponseCode();

                switch (status) {
                    case 200:
                    case 201:
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        Reader reader = new InputStreamReader(in);
                        TypeToken<Stores> type = new TypeToken<Stores>(){};
                        result = gson.fromJson(reader, type.getType());
                        System.out.println("result: " + result);
                }
            }
            finally{
                urlConnection.disconnect();
            }

            //Input result into cache
           CompanyDetailCache.getInstance().setStoreCache(result);
        }

        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);
            notifyListenersOfFetchComplete(result);
        }

        public void addListener(FetchCompanyListUpdateListener listener)
        {
            listeners.add(listener);
        }

        public void removeListener(FetchCompanyListUpdateListener listUpdateListener)
        {
            listeners.remove(listUpdateListener);
        }

        private void notifyListenersOfStartFetch()
        {
            for(FetchCompanyListUpdateListener listener: listeners)
            {
                listener.onStartFetch();
            }
        }

        private void notifyListenersOfFetchComplete(Result result)
        {
            for(FetchCompanyListUpdateListener listener: listeners)
            {
                listener.onFetchComplete(result);
            }
        }


    }