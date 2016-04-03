package bottlerocket.laurenyew.companylist.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by laurenyew on 4/2/16.
 *
 * Singleton cache. Used for Company Preview and Company Detail view
 *
 * Using Url as the key (similar stores may use same logo).
 *
 * TODO: Need to store images in database?
 */
public class LogoBitmapCache {
    private static LogoBitmapCache mInstance;
    //Key: URL, Value: Logo bitmap
    private LruCache<String, Bitmap> mCache;

    private LogoBitmapCache()
    {
        // Get max available VM memory
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mCache = new LruCache<String, Bitmap>(cacheSize){
            //Measure in size of cache (KB), not number of bitmaps
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public static LogoBitmapCache getInstance()
    {
        if(mInstance == null)
        {
            mInstance = new LogoBitmapCache();
        }
        return mInstance;
    }

    public void addBitmap(String url, Bitmap logoImage)
    {
        if(getBitmap(url) == null)
        {
            mCache.put(url, logoImage);
        }
    }

    public Bitmap getBitmap(String storeId)
    {
        return mCache.get(storeId);
    }

    public void clear()
    {
        mCache.evictAll();
    }
}
