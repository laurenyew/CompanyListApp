package bottlerocket.laurenyew.companylist.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.ProgressBar;

import bottlerocket.laurenyew.companylist.cache.LogoBitmapCache;
import bottlerocket.laurenyew.companylist.services.LoadLogoBitmapAsyncTask;

/**
 * Created by laurenyew on 4/2/16.
 *
 * Util methods for loading logo bitmap image(s)
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
        } else {
            LoadLogoBitmapAsyncTask task = new LoadLogoBitmapAsyncTask(imageView, progressBar);
            task.execute(url);
        }
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
