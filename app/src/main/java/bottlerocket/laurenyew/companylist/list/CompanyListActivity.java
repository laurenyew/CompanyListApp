package bottlerocket.laurenyew.companylist.list;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import bottlerocket.laurenyew.companylist.R;
import bottlerocket.laurenyew.companylist.cache.CompanyDetailCache;
import bottlerocket.laurenyew.companylist.cache.LogoBitmapCache;

/**
 * Main activity. Uses a fragment & RecyclerView to show the company list. Uses an async task to
 * fetch the company JSON. There is a toolbar available to switch between using Picasso or
 * a self-build Async task/LruCache for downloading the associated bitmap images.
 */
public class CompanyListActivity extends AppCompatActivity{

    private enum PHOTO_LOAD_BACKGROUND_SERVICE {
        ASYNC_TASK, PICASSO
    }
    private PHOTO_LOAD_BACKGROUND_SERVICE currentServiceType = null;
    private final PHOTO_LOAD_BACKGROUND_SERVICE defaultServiceType = PHOTO_LOAD_BACKGROUND_SERVICE.ASYNC_TASK;

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

        PHOTO_LOAD_BACKGROUND_SERVICE selectedServiceType = currentServiceType;
        PHOTO_LOAD_BACKGROUND_SERVICE oldServiceType = currentServiceType;
        switch(id)
        {
            case R.id.action_async_task: {
                System.out.println("Open Async Task View (self created)");
                selectedServiceType = PHOTO_LOAD_BACKGROUND_SERVICE.ASYNC_TASK;
                break;

            }
            case R.id.action_use_picasso: {
                System.out.println("Open Picasso View (third party)");
                selectedServiceType = PHOTO_LOAD_BACKGROUND_SERVICE.PICASSO;
                break;
            }
        }

        if(selectedServiceType != oldServiceType) {
            showCompanyListFragment(selectedServiceType);
            updateToolbarMenuItems(selectedServiceType);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showCompanyListFragment(PHOTO_LOAD_BACKGROUND_SERVICE type)
    {
        if(type != null && type != currentServiceType) {

            //NOTE: Did not clear the picasso cache,
            // so we can't really see the difference switching back and forth.

            //Clear the caches (so we can see the difference in the fragments when they reload.)
            CompanyDetailCache.getInstance().clear();
            LogoBitmapCache.getInstance().clear();

            //Update the fragment
            if (findViewById(R.id.company_list_fragment_container) != null) {
                CompanyListFragment companyListFragment = new CompanyListFragment();
                Bundle args = new Bundle();
                args.putBoolean(CompanyListFragment.USE_PICASSO_KEY, (type == PHOTO_LOAD_BACKGROUND_SERVICE.PICASSO));
                companyListFragment.setArguments(args);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.company_list_fragment_container, companyListFragment);
                transaction.commit();

                currentServiceType = type;
                }
        }
    }

    private void updateToolbarMenuItems(PHOTO_LOAD_BACKGROUND_SERVICE type)
    {
        if(type != null && toolbarMenu != null)
        {
            //Update the menu item
            MenuItem asyncMenuItem = toolbarMenu.findItem(R.id.action_async_task);
            if (asyncMenuItem != null) {
                asyncMenuItem.setEnabled(type != PHOTO_LOAD_BACKGROUND_SERVICE.ASYNC_TASK);
            }
            MenuItem groundControlMenuItem = toolbarMenu.findItem(R.id.action_use_picasso);
            if (groundControlMenuItem != null) {
                groundControlMenuItem.setEnabled(type != PHOTO_LOAD_BACKGROUND_SERVICE.PICASSO);
            }
        }
    }



}
