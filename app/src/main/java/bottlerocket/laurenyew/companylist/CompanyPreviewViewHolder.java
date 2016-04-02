package bottlerocket.laurenyew.companylist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import bottlerocket.laurenyew.companylist.model.CompanyDetail;

/**
 * Created by laurenyew on 4/2/16.
 */
public class CompanyPreviewViewHolder extends RecyclerView.ViewHolder {

    public ImageView mLogo;
    public TextView mPhone;
    public TextView mAddress;
    public ProgressBar mLogoProgressBar;

    public CompanyPreviewViewHolder(View itemView) {
        super(itemView);
        mLogo = (ImageView) itemView.findViewById(R.id.store_logo);
        mPhone = (TextView) itemView.findViewById(R.id.store_phone);
        mAddress = (TextView) itemView.findViewById(R.id.store_address);
        mLogoProgressBar = (ProgressBar) itemView.findViewById(R.id.store_logo_loading_progress_bar);
    }


}
