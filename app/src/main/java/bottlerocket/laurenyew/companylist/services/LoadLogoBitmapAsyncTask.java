package bottlerocket.laurenyew.companylist.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import bottlerocket.laurenyew.companylist.cache.LogoBitmapCache;
import bottlerocket.laurenyew.companylist.util.LoadLogoBitmapImageUtil;

/**
     * Load image bitmap from url in the background
     *
     * TODO: Slow performance when loading an image. Maybe do a pre-fetch? Or store in database...
     */
    public class LoadLogoBitmapAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private final WeakReference<ProgressBar> progressBarWeakReference;
        private int imageWidth = 1000;
        private int imageHeight = 1000;
        private String urlPath = null;

        public LoadLogoBitmapAsyncTask(ImageView imageView, ProgressBar progressBar) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
            progressBarWeakReference = new WeakReference<ProgressBar>(progressBar);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Show progress bar
            if(progressBarWeakReference != null)
            {
                ProgressBar progressBar = progressBarWeakReference.get();
                if(progressBar != null)
                {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }

            //Get how large the imageView is (so it can be sized)
            if(imageViewReference != null)
            {
                ImageView imageView = imageViewReference.get();
                if(imageView != null)
                {
                    imageWidth = imageView.getWidth();
                    imageHeight = imageView.getHeight();
                }
            }

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

                    //Try to use sample size option to limit the size of the image if necessary
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = LoadLogoBitmapImageUtil.calculateInSampleSize(options,imageWidth, imageHeight);
                    logoBitmap = BitmapFactory.decodeStream(input, null, options);


                    //add to our LogoBitmapCache for future reference
                    //Left in here so we can do a retry if we can't get the url the first time.
                    LogoBitmapCache.getInstance().addBitmap(urlPath, logoBitmap);

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