package bottlerocket.laurenyew.companylist;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * TODO: Use BottleRocket's ground control
 */
public class CompanyListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);
        getSupportActionBar().setHomeButtonEnabled(false);

        //If not rotated, then show the company list fragment
        if (savedInstanceState == null) {
            if (findViewById(R.id.company_list_fragment_container) != null) {
                CompanyListFragment companyListFragment = new CompanyListFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.company_list_fragment_container, companyListFragment);
                transaction.commit();
            }
        }
    }
}
