package bottlerocket.laurenyew.companylist;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by laurenyew on 4/1/16.
 */
public class CompanyListFragment extends android.support.v4.app.Fragment{

    private static final String FETCH_URL = "http://sandbox.bottlerocketapps.com/BR_Android_CodingExam_2015_Server/stores.json";

    private WeakReference<FetchCompanyListAsyncTask> fetchCompanyListAsyncTaskRef = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if(!isFetchListComplete()) {
            initList();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_company_list, container, false);
    }

    private boolean isFetchListComplete() {
        return this.fetchCompanyListAsyncTaskRef != null &&
                this.fetchCompanyListAsyncTaskRef.get() != null &&
                !this.fetchCompanyListAsyncTaskRef.get().getStatus().equals(AsyncTask.Status.FINISHED);
    }

    /***
     * Start the async task to load the list
     */
    private void initList()
    {
        FetchCompanyListAsyncTask asyncTask = new FetchCompanyListAsyncTask();
        fetchCompanyListAsyncTaskRef = new WeakReference<FetchCompanyListAsyncTask>(asyncTask);
        asyncTask.execute(FETCH_URL);
    }


    /**
     * Created by laurenyew on 4/1/16.
     *
     * Fetch the JSON from the provided URL. Change into CompanyDetail model, and save to the CompanyDetailCache.
     * Params: url
     * Progress report: n/a
     * Result: SUCCESS, FAILURE
     */
    public class FetchCompanyListAsyncTask extends AsyncTask<String, Void, String>{
        private static final String SUCCESS = "success";
        private static final String MALFORMED_URL_EXCEPTION = "malformed_url";
        private static final String IO_EXCEPTION = "io_error";
        private static final String UNKNOWN_EXCEPTION = "unknown_error";

        @Override
        protected String doInBackground(String... params)
        {
            if(params != null && params.length == 1)
            {
                String url = params[0];

                try {
                    fetchCompanyList(url);
                }catch(MalformedURLException e)
                {
                    return MALFORMED_URL_EXCEPTION;
                }catch(IOException e)
                {
                    return IO_EXCEPTION;
                }

                return SUCCESS;
            }

            return null;
        }

        private void fetchCompanyList(String urlPath) throws IOException {
            System.out.println("fetchCompanyList for url: " + urlPath);

            List<CompanyDetail> result = null;

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            URL url = new URL(urlPath);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Reader reader = new BufferedReader(new InputStreamReader(in));
                result = Arrays.asList(gson.fromJson(reader, CompanyDetail[].class));
            }catch (Exception e)
            {
                System.out.println("Exception: " + e);
            }
            finally{
                urlConnection.disconnect();
            }

            //Input result into cache
            CompanyDetailCache.getInstance().addAllDetails(result);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            System.out.println("CompanyDetailCache: \n" + CompanyDetailCache.getInstance());
        }
    }

}
