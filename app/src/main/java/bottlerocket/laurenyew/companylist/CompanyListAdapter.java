package bottlerocket.laurenyew.companylist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import bottlerocket.laurenyew.companylist.cache.CompanyDetailCache;
import bottlerocket.laurenyew.companylist.cache.LogoBitmapCache;
import bottlerocket.laurenyew.companylist.model.CompanyDetail;

/**
 * Created by laurenyew on 4/2/16.
 */
public class CompanyListAdapter extends RecyclerView.Adapter<CompanyPreviewViewHolder> {

    private CompanyDetailCache companyDetailCache;
    private LogoBitmapCache logoBitmapCache;

    public CompanyListAdapter()
    {
        companyDetailCache = CompanyDetailCache.getInstance();
        logoBitmapCache = LogoBitmapCache.getInstance();
    }

    @Override
    public CompanyPreviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_preview_card, parent, false);
        CompanyPreviewViewHolder vh = new CompanyPreviewViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CompanyPreviewViewHolder holder, int position) {
        CompanyDetail detail = companyDetailCache.getDetail(position);
        loadLogoBitmap(detail.getStoreLogoURL(), holder.mLogo, holder.mLogoProgressBar);
        holder.mPhone.setText(detail.getPhone());
        holder.mAddress.setText(detail.getAddress());

    }

    @Override
    public int getItemCount() {
        return companyDetailCache.size();
    }

    public void loadLogoBitmap(String url, ImageView imageView, ProgressBar progressBar) {

        final Bitmap bitmap = logoBitmapCache.getBitmap(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            LoadLogoBitmapAsyncTask task = new LoadLogoBitmapAsyncTask(imageView, progressBar);
            task.execute(url);
        }
    }

    /**
     * Load image bitmap from url in the background
     */
    private class LoadLogoBitmapAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private final WeakReference<ProgressBar> progressBarWeakReference;
        private String urlPath = null;

        public LoadLogoBitmapAsyncTask(ImageView imageView, ProgressBar progressBar) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
            progressBarWeakReference = new WeakReference<ProgressBar>(progressBar);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap logoBitmap = null;
            if(params != null && params.length == 1) {
                try {
                    //Get the logo bitmap
                    urlPath = params[0];
                    URL url = new URL(urlPath);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    logoBitmap = BitmapFactory.decodeStream(input);


                    //add to our LogoBitmapCache for future reference
                    //Left in here so we can do a retry if we can't get the url the first time.
                    logoBitmapCache.addBitmap(urlPath, logoBitmap);

                }catch(Exception e)
                {
                    //If there is an error getting the image return null
                    System.err.println("Error loading logo image from url: " + urlPath);
                }
            }
            return logoBitmap;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //Hide the progress bar
            if(progressBarWeakReference != null)
            {
                final ProgressBar progressBar = progressBarWeakReference.get();
                if(progressBar != null)
                {
                    progressBar.setVisibility(View.GONE);
                }
            }

            //Show the image view
            if (imageViewReference != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    if(bitmap != null)
                    {
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageBitmap(bitmap);
                    }
                    //if we have a null image bitmap reference, hide the logo
                    else
                    {
                        imageView.setVisibility(View.GONE);
                    }
                }
            }
        }
    }


}

