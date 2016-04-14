package bottlerocket.laurenyew.companylist.list;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;

import bottlerocket.laurenyew.companylist.R;
import bottlerocket.laurenyew.companylist.cache.CompanyDetailCache;
import bottlerocket.laurenyew.companylist.services.FetchCompanyListAsyncTask;
import bottlerocket.laurenyew.companylist.services.Result;
import bottlerocket.laurenyew.companylist.util.Constants;

/**
 * Created by laurenyew on 4/1/16.
 *
 * Company List Fragment that uses AsyncTasks to fetch the Json data
 */
public class CompanyListFragment extends android.support.v4.app.Fragment implements FetchCompanyListAsyncTask.FetchCompanyListUpdateListener{

    private WeakReference<FetchCompanyListAsyncTask> fetchCompanyListAsyncTaskRef = null;

    private RecyclerView mCompanyListRecyclerView = null;
    private ProgressBar mCompanyListProgressBar = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_company_list, container, false);

        mCompanyListProgressBar = (ProgressBar) view.findViewById(R.id.company_list_load_progress_bar);

        mCompanyListRecyclerView = (RecyclerView) view.findViewById(R.id.company_list_recycler_view);
        mCompanyListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCompanyListRecyclerView.setAdapter(new CompanyListAdapter());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(!isFetchListComplete()) {
            showProgressBar();
            initListWithAsyncTask();
        }
    }

    @Override
    public void onPause() {
        if(fetchCompanyListAsyncTaskRef != null && fetchCompanyListAsyncTaskRef.get() != null)
        {
            fetchCompanyListAsyncTaskRef.get().removeListener(this);
        }

        super.onPause();
    }

    /**
     * In this simple case, if the cache is filled, then the fetch call must have completed.
     * In a more complicated case, we may have to try a paging model, but it was not necessary
     * at this time.
     * @return
     */
    private boolean isFetchListComplete() {
        return !CompanyDetailCache.getInstance().isEmpty();
    }


    /***
     * Start the async task to load the list
     */
    private void initListWithAsyncTask()
    {
        FetchCompanyListAsyncTask asyncTask = new FetchCompanyListAsyncTask();
        fetchCompanyListAsyncTaskRef = new WeakReference<>(asyncTask);
        asyncTask.addListener(this);
        asyncTask.execute(Constants.FETCH_URL);
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
        if(result == Result.SUCCESS)
        {
            hideProgressBar();
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.error_title);

            if (result == Result.DATA_CONNECTION_NOT_AVAILABLE) {
                builder.setMessage(R.string.data_connection_error_message);
            } else if (result == Result.UNKNNOWN_ERROR) {
                builder.setMessage(R.string.unknown_error_message);
            }

            // Add Ok Button
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void showProgressBar()
    {
        //show progress bar, hide recyclerView
        if(mCompanyListProgressBar != null)
        {
            mCompanyListProgressBar.setVisibility(View.VISIBLE);
        }
        if(mCompanyListRecyclerView != null)
        {
            mCompanyListRecyclerView.setVisibility(View.GONE);
        }
    }

    private void hideProgressBar()
    {

        if(mCompanyListProgressBar != null)
        {
            mCompanyListProgressBar.setVisibility(View.GONE);
        }
        if(mCompanyListRecyclerView != null)
        {
            mCompanyListRecyclerView.setVisibility(View.VISIBLE);
        }
    }


}
