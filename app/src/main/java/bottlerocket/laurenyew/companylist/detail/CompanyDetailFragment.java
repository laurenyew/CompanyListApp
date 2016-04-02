package bottlerocket.laurenyew.companylist.detail;

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

import java.lang.ref.WeakReference;

import bottlerocket.laurenyew.companylist.R;
import bottlerocket.laurenyew.companylist.list.CompanyListAdapter;
import bottlerocket.laurenyew.companylist.services.FetchCompanyListAsyncTask;
import bottlerocket.laurenyew.companylist.services.Result;
import bottlerocket.laurenyew.companylist.util.Constants;

/**
 * Created by laurenyew on 4/1/16.
 *
 * TODO: Check observable pattern does not break on rotate/fragment life cycles.
 * TODO: Check for memory leaks...
 */
public class CompanyDetailFragment extends android.support.v4.app.Fragment{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_detail, container, false);
        return view;
    }

}
