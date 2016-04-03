package bottlerocket.laurenyew.companylist.list;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import bottlerocket.laurenyew.companylist.R;
import bottlerocket.laurenyew.companylist.cache.CompanyDetailCache;
import bottlerocket.laurenyew.companylist.cache.LogoBitmapCache;
import bottlerocket.laurenyew.companylist.model.CompanyDetail;
import bottlerocket.laurenyew.companylist.util.LoadLogoBitmapImageUtil;

/**
 * Created by laurenyew on 4/2/16.
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

