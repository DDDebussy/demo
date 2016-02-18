package com.channelsoft.sample.adapter.useradapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.channelsoft.sample.R;
import com.channelsoft.sample.activity.homepager.CuisineDetialActivity;
import com.channelsoft.sample.model.homepager.CuisineInfo;
import com.channelsoft.sample.util.CommonUtils;

import java.util.List;

/**
 * Created by 王宗贤 on 2015/12/29.
 */
public class UserFavourAdapter extends RecyclerView.Adapter<UserFavourAdapter.ViewHolder> {
    private List<CuisineInfo> mDate;
    private Context mContext;

    public UserFavourAdapter(List<CuisineInfo> mDate, Context mContext) {
        this.mDate = mDate;
        this.mContext = mContext;
    }

    @Override
    public UserFavourAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycyler_view_item_user_favour,null));
    }

    @Override
    public void onBindViewHolder(UserFavourAdapter.ViewHolder holder, final int position) {
//        将list中的数据设置在view上
        CommonUtils.loadToImageView(mDate.get(position).getImageUrl(),holder.img);
        holder.textName.setText(mDate.get(position).getCanguanName());
        holder.textDistance.setText(mDate.get(position).getDistance());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进入详情菜单
                Intent intent = new Intent(mContext, CuisineDetialActivity.class);
                intent.putExtra("canguanName", mDate.get(position).getCanguanName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDate.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView textName;
        TextView textDistance;
        RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            img= (ImageView) itemView.findViewById(R.id.img_user_favour_item);
            textDistance= (TextView) itemView.findViewById(R.id.text_user_favour_item_distance);
            textName= (TextView) itemView.findViewById(R.id.text_user_favour_item_name);
            relativeLayout= (RelativeLayout) itemView.findViewById(R.id.relative_user_favour_item);
        }
    }
}
