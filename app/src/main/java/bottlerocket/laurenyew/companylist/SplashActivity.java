package bottlerocket.laurenyew.companylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import bottlerocket.laurenyew.companylist.list.CompanyListActivity;

/**
 * Created by laurenyew on 4/3/16.
 *
 * Shows Bottle Rocket splash screen on launch
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, CompanyListActivity.class);
        startActivity(intent);
        finish();
    }
}