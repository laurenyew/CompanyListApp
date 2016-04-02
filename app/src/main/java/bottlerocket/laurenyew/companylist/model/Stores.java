package bottlerocket.laurenyew.companylist.model;

import java.util.ArrayList;

/**
 * Created by laurenyew on 4/2/16.
 */
public class Stores {

    public ArrayList<CompanyDetail> stores = new ArrayList<>();

    public ArrayList<CompanyDetail> getStores() {
        return stores;
    }

    public void setStores(ArrayList<CompanyDetail> stores) {
        this.stores = stores;
    }

    public int size()
    {
        return stores.size();
    }

    public CompanyDetail get(int index)
    {
        if(index < 0 || index >= stores.size())
        {
            return null;
        }
        else
        {
            return stores.get(index);
        }
    }

}
