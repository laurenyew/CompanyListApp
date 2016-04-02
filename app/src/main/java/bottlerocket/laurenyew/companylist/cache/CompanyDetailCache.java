package bottlerocket.laurenyew.companylist.cache;

import bottlerocket.laurenyew.companylist.model.CompanyDetail;
import bottlerocket.laurenyew.companylist.model.Stores;

/**
 * Created by laurenyew on 4/1/16.
 *
 * Single instance storeCache
 *
 * TODO: Create eviction strategy
 */
public class CompanyDetailCache {

    private static CompanyDetailCache mInstance = null;
    private Stores stores;

    private CompanyDetailCache()
    {
        stores = new Stores();
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
        this.stores = stores;
    }

    public CompanyDetail getDetail(int index)
    {
        return stores.get(index);
    }


    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        for(CompanyDetail detail: stores.getStores())
        {
            builder.append(detail + "\n");
        }
        return builder.toString();
    }
}
