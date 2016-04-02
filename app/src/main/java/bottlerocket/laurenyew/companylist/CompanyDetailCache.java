package bottlerocket.laurenyew.companylist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laurenyew on 4/1/16.
 *
 * Single instance cache
 *
 * TODO: Create eviction strategy
 */
public class CompanyDetailCache {

    private static CompanyDetailCache mInstance = null;
    private ArrayList<CompanyDetail> cache;


    private CompanyDetailCache()
    {
        cache = new ArrayList<CompanyDetail>();
    }

    public static CompanyDetailCache getInstance()
    {
        if(mInstance == null)
        {
            mInstance = new CompanyDetailCache();
        }

        return mInstance;
    }

    public void setCache(List<CompanyDetail> cache) {
        this.cache = (ArrayList<CompanyDetail>)cache;
    }

    public CompanyDetail getDetail(int index)
    {
        if(index < 0 || index >= cache.size())
        {
            return null;
        }
        else
        {
            return cache.get(index);
        }
    }

    public void addDetail(CompanyDetail detail)
    {
        cache.add(detail);
    }

    public void addAllDetails(List<CompanyDetail> details)
    {
        cache.addAll(details);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        for(CompanyDetail detail: cache)
        {
            builder.append(detail + "\n");
        }
        return builder.toString();
    }
}
