package bottlerocket.laurenyew.companylist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import bottlerocket.laurenyew.companylist.services.FetchCompanyListAsyncTask;
import bottlerocket.laurenyew.companylist.services.Result;

/**
 * Created by laurenyew on 4/1/16.
 *
 * TODO: Check observable pattern does not break on rotate/fragment life cycles.
 * TODO: Check for memory leaks...
 */
public class CompanyListFragment extends android.support.v4.app.Fragment implements FetchCompanyListAsyncTask.FetchCompanyListUpdateListener{

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

    @Override
    public void onPause() {
        if(fetchCompanyListAsyncTaskRef != null && fetchCompanyListAsyncTaskRef.get() != null)
        {
            fetchCompanyListAsyncTaskRef.get().removeListener(this);
        }

        super.onPause();
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
        fetchCompanyListAsyncTaskRef = new WeakReference<>(asyncTask);
        asyncTask.addListener(this);
        asyncTask.execute(Constants.FETCH_URL);
    }


    /**
     * implementing FetchCompanyListUpdateListener
     *
     * Show progress bar
     */
    @Override
    public void onStartFetch() {
        System.out.println("Start Fetch");
    }

    /**
     * implementing FetchCompanyListUpdateListener
     *
     * Update the list UI
     * @param result
     */
    @Override
    public void onFetchComplete(Result result) {
        System.out.println("Fetch complete. Result: " + result);
        TextView textView = (TextView) getView().findViewById(R.id.company_list_info);
        textView.setText("DONE");
    }
}
