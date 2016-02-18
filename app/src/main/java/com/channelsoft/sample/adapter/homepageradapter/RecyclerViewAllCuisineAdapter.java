package com.channelsoft.sample.adapter.homepageradapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.channelsoft.sample.R;
import com.channelsoft.sample.activity.homepager.CuisineDetialActivity;
import com.channelsoft.sample.model.homepager.CuisineInfo;
import com.channelsoft.sample.util.CommonUtils;

import java.util.List;

/**
 * Created by chenlijin on 2015/12/17.
 */
public class RecyclerViewAllCuisineAdapter extends RecyclerView.Adapter<RecyclerViewAllCuisineAdapter.ViewHolder> {
    private List<CuisineInfo> mCuisineInfoList;
    private Context mContext;
    public static String CANGUAN_NAME="canguanName";

    public void setmCuisineInfoList(List<CuisineInfo> mCuisineInfoList) {
        this.mCuisineInfoList = mCuisineInfoList;
    }

    public RecyclerViewAllCuisineAdapter(List<CuisineInfo> cuisineInfoList, Context mContext) {
        this.mCuisineInfoList = cuisineInfoList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_cuisine_info,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //最后一项显示已加载到最后一项
        if(position<mCuisineInfoList.size()) {
            holder.bottomTextView.setVisibility(View.GONE);
            holder.itemLinearLayout.setVisibility(View.VISIBLE);
            final CuisineInfo cuisineInfo = mCuisineInfoList.get(position);
        //item点击进入菜单详情
            holder.itemLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CuisineDetialActivity.class);
                    intent.putExtra(RecyclerViewAllCuisineAdapter.CANGUAN_NAME,cuisineInfo.getCanguanName());
                    mContext.startActivity(intent);
                }
            });
            //餐馆名
            holder.textViewCanGuan.setText(cuisineInfo.getCanguanName());
            //
            holder.textViewDistance.setText(cuisineInfo.getDistance());
            //加载图片
            CommonUtils.loadToImageView(cuisineInfo.getImageUrl(),holder.imageViewMeiShi);
            CommonUtils.loadToImageView(cuisineInfo.getIconImageUrl(),holder.imageViewIcon);
        }else{
            holder.bottomTextView.setVisibility(View.VISIBLE);
            holder.itemLinearLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mCuisineInfoList.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemLinearLayout;
        TextView bottomTextView;
        ImageView imageViewIcon;
        ImageView imageViewMeiShi;
        TextView textViewCanGuan;
        TextView textViewDistance;
        public ViewHolder(View itemView) {
            super(itemView);
            bottomTextView = (TextView) itemView.findViewById(R.id.textview_bottom_item);
            itemLinearLayout = (LinearLayout) itemView.findViewById(R.id.linearlayout_item_cuisine_info);
            imageViewIcon = (ImageView) itemView.findViewById(R.id.imageview_icon_all_cuisine);
            imageViewMeiShi = (ImageView) itemView.findViewById(R.id.imageview_meishi_all_cuisine);
            textViewCanGuan = (TextView) itemView.findViewById(R.id.textview_canguan_name_all);
            textViewDistance = (TextView) itemView.findViewById(R.id.textview_distance_all);
        }
    }
}
