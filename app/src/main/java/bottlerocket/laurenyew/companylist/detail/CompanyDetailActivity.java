package bottlerocket.laurenyew.companylist.detail;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import bottlerocket.laurenyew.companylist.R;
import bottlerocket.laurenyew.companylist.cache.CompanyDetailCache;
import bottlerocket.laurenyew.companylist.list.CompanyListFragment;
import bottlerocket.laurenyew.companylist.model.CompanyDetail;
import bottlerocket.laurenyew.companylist.util.LoadLogoBitmapImageUtil;

/**
 * Show the company detail
 */
public class CompanyDetailActivity extends AppCompatActivity {
    public static String COMPANY_DETAIL_CACHE_INDEX_KEY = "company_detail_cache_index_key";
    private int detailIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_company_detail);
        getSupportActionBar().setHomeButtonEnabled(false);

        System.out.println("OnCreate: " + savedInstanceState);
        //Get intent
        if (savedInstanceState != null) {
            detailIndex = savedInstanceState.getInt(COMPANY_DETAIL_CACHE_INDEX_KEY, -1);
            if(detailIndex != -1)
            {
                initCompanyDetailView();
            }
        }
    }

    /**
     * Setup the company detail view with the appropriate info
     */
    private void initCompanyDetailView()
    {
        CompanyDetail detail = CompanyDetailCache.getInstance().getDetail(detailIndex);

        System.out.println("Init detail view with detail: " + detail);

        if(detail != null)
        {
            ImageView mLogo = (ImageView) findViewById(R.id.store_logo);
            ProgressBar mLogoProgressBar = (ProgressBar) findViewById(R.id.store_logo_loading_progress_bar);
            LoadLogoBitmapImageUtil.loadLogoBitmap(detail.getStoreLogoURL(), mLogo, mLogoProgressBar);
            ((TextView) findViewById(R.id.detail_store_name)).setText(detail.getName());
            ((TextView) findViewById(R.id.detail_store_city)).setText(detail.getCity());
            ((TextView) findViewById(R.id.detail_store_phone)).setText(detail.getPhone());
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(COMPANY_DETAIL_CACHE_INDEX_KEY, detailIndex);
        super.onSaveInstanceState(outState);
    }
}
