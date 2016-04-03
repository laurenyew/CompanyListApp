package bottlerocket.laurenyew.companylist.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

import bottlerocket.laurenyew.companylist.R;
import bottlerocket.laurenyew.companylist.cache.CompanyDetailCache;
import bottlerocket.laurenyew.companylist.cache.LogoBitmapCache;
import bottlerocket.laurenyew.companylist.model.CompanyDetail;
import bottlerocket.laurenyew.companylist.util.LoadLogoBitmapImageUtil;

/**
 * Created by laurenyew on 4/2/16.
 *
 * RecyclerView.Adapter that uses AsyncTasks to load the images
 */
public class CompanyListAdapter extends RecyclerView.Adapter<CompanyPreviewViewHolder> {

    private CompanyDetailCache companyDetailCache;
    private LogoBitmapCache logoBitmapCache;

    public CompanyListAdapter()
    {
        companyDetailCache = CompanyDetailCache.getInstance();
        logoBitmapCache = LogoBitmapCache.getInstance();
    }

    @Override
    public CompanyPreviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_preview_card, parent, false);
        CompanyPreviewViewHolder vh = new CompanyPreviewViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CompanyPreviewViewHolder holder, int position) {

        CompanyDetail detail = companyDetailCache.getDetail(position);
        if(detail.getStoreLogoURL() != null) {

            /*
            //If we used Picasso, we could use these 3 lines:
                ImageView imageView = holder.mLogo;
                Context context = imageView.getContext();
                Picasso.with(context).load(detail.getStoreLogoURL()).into(imageView);
             */

            LoadLogoBitmapImageUtil.loadLogoBitmap(detail.getStoreLogoURL(), holder.mLogo, holder.mLogoProgressBar);
        }

        if(detail.getPhone()!= null) {
            holder.mPhone.setText(detail.getPhone());
        }
        else
        {
            holder.mPhone.setVisibility(View.GONE);
        }

        if(detail.getAddress() != null)
        {
            holder.mAddress.setText(detail.getAddress());
        }
        else
        {
            holder.mAddress.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return companyDetailCache.size();
    }


}

