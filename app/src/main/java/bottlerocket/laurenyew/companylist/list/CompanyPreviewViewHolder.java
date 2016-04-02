package bottlerocket.laurenyew.companylist.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import bottlerocket.laurenyew.companylist.detail.CompanyDetailActivity;
import bottlerocket.laurenyew.companylist.R;

/**
 * Created by laurenyew on 4/2/16.
 */
public class CompanyPreviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public CardView mCard;
    public ImageView mLogo;
    public TextView mPhone;
    public TextView mAddress;
    public ProgressBar mLogoProgressBar;

    public CompanyPreviewViewHolder(View itemView) {
        super(itemView);
        mCard = (CardView) itemView.findViewById(R.id.company_preview_card_view);
        mLogo = (ImageView) itemView.findViewById(R.id.store_logo);
        mPhone = (TextView) itemView.findViewById(R.id.store_phone);
        mAddress = (TextView) itemView.findViewById(R.id.store_address);
        mLogoProgressBar = (ProgressBar) itemView.findViewById(R.id.store_logo_loading_progress_bar);

        mCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        System.out.println("onClick");
        Context context = v.getContext();
        int cacheIndex = getPosition();
        Intent openCompanyDetail = new Intent(context, CompanyDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(CompanyDetailActivity.COMPANY_DETAIL_CACHE_INDEX_KEY, cacheIndex);
        context.startActivity(openCompanyDetail, bundle);
    }
}
