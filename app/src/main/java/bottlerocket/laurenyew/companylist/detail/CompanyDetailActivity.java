package bottlerocket.laurenyew.companylist.detail;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import bottlerocket.laurenyew.companylist.R;
import bottlerocket.laurenyew.companylist.list.CompanyListFragment;

/**
 * Show the company detail
 */
public class CompanyDetailActivity extends AppCompatActivity {
    public static String COMPANY_DETAIL_CACHE_INDEX_KEY = "company_detail_cache_index_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        System.out.println("OPENED COMPANY DETAIL");
        setContentView(R.layout.activity_company_detail);
        getSupportActionBar().setHomeButtonEnabled(false);

        //If not rotated, then show the company list fragment
        if (savedInstanceState == null) {
            if (findViewById(R.id.company_detail_fragment_container) != null) {
                CompanyDetailFragment companyDetailFragment = new CompanyDetailFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.company_detail_fragment_container, companyDetailFragment);
                transaction.commit();
            }
        }
    }
}
