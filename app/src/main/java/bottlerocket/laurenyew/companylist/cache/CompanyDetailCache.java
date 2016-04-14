package bottlerocket.laurenyew.companylist.cache;

import bottlerocket.laurenyew.companylist.model.CompanyDetail;
import bottlerocket.laurenyew.companylist.model.Stores;

/**
 * Created by laurenyew on 4/1/16.
 *
 * Singleton cache. Used for Company Preview and Company Detail view
 */
public class CompanyDetailCache {

    private static CompanyDetailCache mInstance = null;
    private Stores mCache;

    private CompanyDetailCache()
    {
        mCache = new Stores();
    }

    public static CompanyDetailCache getInstance()
    {
        if(mInstance == null)
        {
            mInstance = new CompanyDetailCache();
        }
        return mInstance;
    }

    public void setStoreCache(Stores stores)
    {
        mCache = stores;
    }

    public CompanyDetail getDetail(int index)
    {
        return mCache.get(index);
    }

    public int size()
    {
        int size = 0;

        if(mCache != null)
        {
            size = mCache.getStores().size();
        }
        return size;
    }

    public boolean isEmpty()
    {
        return size() <= 0;
    }


    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        for(CompanyDetail detail: mCache.getStores())
        {
            builder.append(detail + "\n");
        }
        return builder.toString();
    }
}
