package com.channelsoft.sample.adapter.discoveryadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.channelsoft.sample.R;
import com.channelsoft.sample.activity.discovery.Plan;
import com.channelsoft.sample.model.discovery.Welfare;

import java.util.List;

/**
 * Created by 王宗贤 on 2015/12/17.
 */
public class DisActivityAdapter extends RecyclerView.Adapter<DisActivityAdapter.ViewHolder> {
    private List<Welfare> mDate;
    private Context mContext;

    public DisActivityAdapter(List<Welfare> mDate, Context mContext) {
        this.mDate = mDate;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_discovery_activity,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.img.setImageResource(mDate.get(position).getImg());
        switch (position){
            case 0:
                holder.img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(mContext,Plan.class);
                        mContext.startActivity(intent);
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mDate.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            img= (ImageView) itemView.findViewById(R.id.img_discovery_activity_recycler_item);
        }
    }
}
