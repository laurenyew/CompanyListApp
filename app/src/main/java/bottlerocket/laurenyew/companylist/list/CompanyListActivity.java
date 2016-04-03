package bottlerocket.laurenyew.companylist.list;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.annotations.SerializedName;

import bottlerocket.laurenyew.companylist.R;
import bottlerocket.laurenyew.companylist.cache.CompanyDetailCache;
import bottlerocket.laurenyew.companylist.cache.LogoBitmapCache;

/**
 * TODO: Use BottleRocket's ground control
 */
public class CompanyListActivity extends AppCompatActivity{

    private enum SERVICE_TYPE {
        ASYNC_TASK, GROUND_CONTROL
    }
    private SERVICE_TYPE currentServiceType = null;
    private final SERVICE_TYPE defaultServiceType = SERVICE_TYPE.ASYNC_TASK;

    private Menu toolbarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);

        //If not rotated, then show the company list fragment
        if (savedInstanceState == null) {
            showCompanyListFragment(defaultServiceType);
            //Setup toolbar and floating action button
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        toolbarMenu = menu;
        updateToolbarMenuItems(currentServiceType);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        SERVICE_TYPE selectedServiceType = currentServiceType;
        SERVICE_TYPE oldServiceType = currentServiceType;
        switch(id)
        {
            case R.id.action_async_task: {
                System.out.println("Open Async Task View");
                selectedServiceType = SERVICE_TYPE.ASYNC_TASK;
                break;

            }
            case R.id.action_ground_control: {
                System.out.println("Open Ground Control View");
                selectedServiceType = SERVICE_TYPE.GROUND_CONTROL;
                break;
            }
        }

        if(selectedServiceType != oldServiceType) {
            showCompanyListFragment(selectedServiceType);
            updateToolbarMenuItems(selectedServiceType);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showCompanyListFragment(SERVICE_TYPE type)
    {
        if(type != null && type != currentServiceType) {

            //Clear the caches (so we can see the difference in the fragments when they reload.)
            CompanyDetailCache.getInstance().clear();
            LogoBitmapCache.getInstance().clear();

            //Update the fragments
            if (type == SERVICE_TYPE.ASYNC_TASK) {
                if (findViewById(R.id.company_list_fragment_container) != null) {
                    CompanyListFragment companyListFragment = new CompanyListFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.company_list_fragment_container, companyListFragment);
                    transaction.commit();

                    currentServiceType = SERVICE_TYPE.ASYNC_TASK;
                }
            } else if (type == SERVICE_TYPE.GROUND_CONTROL) {
                if (findViewById(R.id.company_list_fragment_container) != null) {
                    CompanyListFragmentWithGroundControl companyListFragment = new CompanyListFragmentWithGroundControl();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.company_list_fragment_container, companyListFragment);
                    transaction.commit();

                    currentServiceType = SERVICE_TYPE.GROUND_CONTROL;
                }
            }
        }
    }

    private void updateToolbarMenuItems(SERVICE_TYPE type)
    {
        if(type != null && toolbarMenu != null)
        {
            //Update the menu item
            MenuItem asyncMenuItem = toolbarMenu.findItem(R.id.action_async_task);
            if (asyncMenuItem != null) {
                asyncMenuItem.setEnabled(type != SERVICE_TYPE.ASYNC_TASK);
            }
            MenuItem groundControlMenuItem = toolbarMenu.findItem(R.id.action_ground_control);
            if (groundControlMenuItem != null) {
                groundControlMenuItem.setEnabled(type != SERVICE_TYPE.GROUND_CONTROL);
            }
        }
    }



}
