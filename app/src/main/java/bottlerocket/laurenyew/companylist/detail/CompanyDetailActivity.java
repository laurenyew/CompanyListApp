package bottlerocket.laurenyew.companylist.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import bottlerocket.laurenyew.companylist.R;
import bottlerocket.laurenyew.companylist.cache.CompanyDetailCache;
import bottlerocket.laurenyew.companylist.model.CompanyDetail;
import bottlerocket.laurenyew.companylist.util.LoadLogoBitmapImageUtil;

/**
 * Show the company detail
 */
public class CompanyDetailActivity extends AppCompatActivity {
    public static String COMPANY_DETAIL_CACHE_INDEX_KEY = "company_detail_cache_index_key";
    private int detailIndex = -1;

    private Button openGoogleMapsButton;
    private String googleMapsUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        setContentView(R.layout.activity_company_detail);

        if (savedInstanceState == null) {
            detailIndex = getIntent().getIntExtra(COMPANY_DETAIL_CACHE_INDEX_KEY, -1);
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

        if(detail != null)
        {
            initBasicInfoLogoCardView(detail.getName(), detail.getStoreLogoURL());
            initPhoneDetailCardView(detail.getPhone());
            initAddressDetailCardView(detail.getAddress(), detail.getCity(), detail.getState(), detail.getZipcode(), detail.getLatitude(), detail.getLongitude());
            initMoreInfoDetailCardView(detail.getStoreID());
        }
    }


    /**
     * Show the logo and the name card if available.
     * @param name
     * @param url
     */
    private void initBasicInfoLogoCardView(String name, String url) {

        if(url != null) {
            ImageView mLogo = (ImageView) findViewById(R.id.detail_store_logo);
            ProgressBar mLogoProgressBar = (ProgressBar) findViewById(R.id.detail_store_logo_loading_progress_bar);
            LoadLogoBitmapImageUtil.loadLogoBitmap(url, mLogo, mLogoProgressBar);
        }
        else
        {
            findViewById(R.id.detail_store_logo_container).setVisibility(View.GONE);
        }

        if(name != null)
        {
            ((TextView) findViewById(R.id.detail_store_name)).setText(name);
        }
    }

    /**
     * If phone is available, show a linked phone. Otherwise, hide this card.
     * @param phone
     */
    private void initPhoneDetailCardView(String phone) {
        if(phone != null)
        {
            ((TextView) findViewById(R.id.detail_store_phone)).setText(phone);
        }
        else
        {
            findViewById(R.id.detail_company_phone_card_view).setVisibility(View.GONE);
        }
    }

    /**
     * Show Address card with info if available. Also include a open in google maps butto if available
     * @param address
     * @param city
     * @param state
     * @param zipcode
     * @param latitude
     * @param longitude
     */
    private void initAddressDetailCardView(String address, String city, String state, String zipcode, String latitude, String longitude)
    {
        //Hide the card if none of this information is available
        if(address == null && city == null && state == null && zipcode == null && (latitude == null || longitude == null))
        {
            findViewById(R.id.detail_company_address_card_view).setVisibility(View.GONE);
        }
        else {
            if (address != null) {
                ((TextView) findViewById(R.id.detail_store_address)).setText(address);
            }

            if (city != null) {
                ((TextView) findViewById(R.id.detail_store_city)).setText(city);
            }

            if (state != null) {
                ((TextView) findViewById(R.id.detail_store_state)).setText(state);
            }

            if (zipcode != null) {
                ((TextView) findViewById(R.id.detail_store_zipcode)).setText(zipcode);
            }

            openGoogleMapsButton = (Button) findViewById(R.id.detail_store_open_google_maps);
            if (latitude == null || longitude == null) {
                openGoogleMapsButton.setVisibility(View.GONE);
                findViewById(R.id.detail_separator4).setVisibility(View.GONE);
            } else {
                googleMapsUrl = "http://maps.google.com/?q=" + latitude + "," + longitude;

                openGoogleMapsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(googleMapsUrl);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    /**
     * Show storeId in more info card if available. Otherwise, hide the card.
     * @param storeID
     */
    private void initMoreInfoDetailCardView(String storeID) {
        if(storeID == null)
        {
            findViewById(R.id.detail_company_more_info_card_view).setVisibility(View.GONE);
        }
        else
        {
            ((TextView) findViewById(R.id.detail_storeID)).setText(storeID);
        }
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(COMPANY_DETAIL_CACHE_INDEX_KEY, detailIndex);
        super.onSaveInstanceState(outState);
    }
}
