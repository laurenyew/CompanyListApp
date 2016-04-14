package bottlerocket.laurenyew.companylist.list;

import android.content.DialogInterface;
import android.net.http.HttpResponseCache;
import android.nfc.Tag;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.io.File;

import bottlerocket.laurenyew.companylist.R;
import bottlerocket.laurenyew.companylist.cache.CompanyDetailCache;
import bottlerocket.laurenyew.companylist.cache.LogoBitmapCache;
import bottlerocket.laurenyew.companylist.services.Result;

/**
 * Main activity. Uses a fragment & RecyclerView to show the company list. Uses an async task to
 * fetch the company JSON. There is a toolbar available to switch between using Picasso or
 * a self-build Async task/LruCache for downloading the associated bitmap images.
 */
public class CompanyListActivity extends AppCompatActivity{

    private Menu toolbarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);

        //If not rotated, then show the company list fragment
        //and set up the HttpResponseCache
        if (savedInstanceState == null) {

            final long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
            //we don't care about security, so its ok to save in the external cache directory
            final File httpCacheDir = new File(getExternalCacheDir(), "http");
            try {
                //Installing the httpResponseCache
                Class.forName("android.net.http.HttpResponseCache")
                        .getMethod("install", File.class, long.class)
                        .invoke(null, httpCacheDir, httpCacheSize);
                System.out.println("HttpResponseCache set up.");
            }
            catch(Exception e){
                System.out.println("Could not set up HttpResponseCache");
                //Show error dialog when cache is not available.
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle(R.string.error_title);
                    builder.setMessage(R.string.cache_connection_error_message);
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

            showCompanyListFragment();
            //Setup toolbar and floating action button
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
            setSupportActionBar(toolbar);
        }
    }

    @Override
    protected void onStop() {
        HttpResponseCache cache = HttpResponseCache.getInstalled();
        if (cache != null) {
            cache.flush();
        }
        super.onStop();
    }

    private void showCompanyListFragment()
    {
            //Update the fragment
            if (findViewById(R.id.company_list_fragment_container) != null) {
                CompanyListFragment companyListFragment = new CompanyListFragment();
                Bundle args = new Bundle();
                companyListFragment.setArguments(args);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.company_list_fragment_container, companyListFragment);
                transaction.commit();
                }

    }





}
