package bottlerocket.laurenyew.companylist.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import bottlerocket.laurenyew.companylist.cache.LogoBitmapCache;
import bottlerocket.laurenyew.companylist.list.DownloadedLogoDrawable;
import bottlerocket.laurenyew.companylist.services.LoadLogoBitmapAsyncTask;

/**
 * Created by laurenyew on 4/2/16.
 *
 * Util methods for loading logo bitmap image(s). Could have used a third party program like
 * Picasso or Android-Universal-Image-Loader
 * to do the image loading, but wanted to limit to self-generated code for this project.
 */
public class LoadLogoBitmapImageUtil {

    /**
     * Helper method to load logo bitmap. Uses cache if possible, otherwise, starts an async task to load the image.
     * @param url
     * @param imageView
     * @param progressBar
     */
    public static void loadLogoBitmap(String url, ImageView imageView, ProgressBar progressBar) {

        final Bitmap bitmap = LogoBitmapCache.getInstance().getBitmap(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else if (cancelPotentialDownload(url, imageView))
        {
            LoadLogoBitmapAsyncTask task = new LoadLogoBitmapAsyncTask(imageView, progressBar);
            DownloadedLogoDrawable drawable = new DownloadedLogoDrawable(task);
            imageView.setImageDrawable(drawable);
            imageView.setVisibility(View.GONE);
            task.execute(url);
        }
    }

    private static boolean cancelPotentialDownload(String url, ImageView imageView) {
        LoadLogoBitmapAsyncTask loadLogoBitmapAsyncTask = getLoadLogoBitmapAsyncTask(imageView);

        if (loadLogoBitmapAsyncTask != null) {
            String bitmapUrl = loadLogoBitmapAsyncTask.getUrlPath();

            //if bitmapUrl is null or does not equal the current url, then
            //the imageview's url task is either incomplete or for different url
            //and that task should be cancelled and be replaced by this one.
            if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
                loadLogoBitmapAsyncTask.cancel(true);
            } else {
                // The same URL is already being downloaded.
                return false;
            }
        }
        return true;
    }

    /**
     * Check the current imageview drawable's associated task
     * @param imageView
     * @return
     */
    public static LoadLogoBitmapAsyncTask getLoadLogoBitmapAsyncTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof DownloadedLogoDrawable) {
                DownloadedLogoDrawable downloadedDrawable = (DownloadedLogoDrawable)drawable;
                return downloadedDrawable.getLoadLogoBitmapAsyncTask();
            }
        }
        return null;
    }

    /**
     * Helper method.
     * Used by async task for loading bitmaps from url to try to sample loaded images down to a more managable size
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
