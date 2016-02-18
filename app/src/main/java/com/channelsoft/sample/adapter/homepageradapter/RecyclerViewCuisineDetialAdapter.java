package com.channelsoft.sample.adapter.homepageradapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.channelsoft.sample.R;
import com.channelsoft.sample.activity.homepager.CaiCriticismActivity;
import com.channelsoft.sample.model.homepager.NaShouCaiInfo;
import com.channelsoft.sample.util.CommonUtils;

import java.util.List;

/**
 * Created by chenlijin on 2015/12/17.
 */
public class RecyclerViewCuisineDetialAdapter extends RecyclerView.Adapter<RecyclerViewCuisineDetialAdapter.ViewHolder> {
    private List<NaShouCaiInfo> mNaShouCaiInfoList;
    private Context mContext;
    public final String canGuanName = "canguanName";
    private Integer price = 0;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setmCuisineInfoList(List<NaShouCaiInfo> naShouCaiInfoList) {
        this.mNaShouCaiInfoList = naShouCaiInfoList;
    }

    public RecyclerViewCuisineDetialAdapter(List<NaShouCaiInfo> naShouCaiInfoList, Context mContext) {
        this.mNaShouCaiInfoList = naShouCaiInfoList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_nashoucai_info, null));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final NaShouCaiInfo naShouCaiInfo = mNaShouCaiInfoList.get(position);
        holder.textViewNaShouCai.setText(naShouCaiInfo.getCaiMing());
        CommonUtils.loadToImageView(naShouCaiInfo.getImageUrl(),holder.imageViewNaShouCai);
        holder.textViewPrice.setText(naShouCaiInfo.getPrice()+"");
        holder.textViewSum.setText(naShouCaiInfo.getSum()+"");
        holder.textViewLeftNum.setText(naShouCaiInfo.getLeftNum()+"");
        holder.textViewCurSelectNum.setText(naShouCaiInfo.getCurNum()+"");

        holder.imageViewNaShouCai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,CaiCriticismActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                mContext.startActivity(intent);
            }
        });

        if(naShouCaiInfo.getCurNum()==0){
            holder.imageViewSub.setVisibility(View.GONE);
            holder.textViewCurSelectNum.setVisibility(View.GONE);
        }
        //计算剩余份数
        final int[] leftNum = {Integer.parseInt(naShouCaiInfo.getLeftNum().toString())};
        holder.imageViewAdd.setOnClickListener(new View.OnClickListener() {
            int curNum = 0;
            @Override
            public void onClick(View v) {
                holder.imageViewSub.setVisibility(View.VISIBLE);
                holder.textViewCurSelectNum.setVisibility(View.VISIBLE);
                curNum = Integer.parseInt(holder.textViewCurSelectNum.getText().toString());
                if(leftNum[0] >0){
                    curNum++;
                    holder.textViewCurSelectNum.setText(curNum+"");
                    naShouCaiInfo.setCurNum(curNum);
                    price += naShouCaiInfo.getPrice();
                    whereChangPrice(price);
                    leftNum[0]--;
                }else{
                    CommonUtils.showToast("已经没有库存了");
                }
            }
        });

        holder.imageViewSub.setOnClickListener(new View.OnClickListener() {
            int curNum = 0;
            @Override
            public void onClick(View v) {
                curNum = Integer.parseInt(holder.textViewCurSelectNum.getText().toString());

                if(curNum>1){
                    curNum--;
                    naShouCaiInfo.setCurNum(curNum);
                    price -= naShouCaiInfo.getPrice();
                    whereChangPrice(price);
                    holder.textViewCurSelectNum.setText(curNum+"");
                    naShouCaiInfo.setCurNum(curNum);
                    leftNum[0]++;
                }else {
                    curNum--;
                    naShouCaiInfo.setCurNum(curNum);
                    leftNum[0]++;
                    price -= naShouCaiInfo.getPrice();
                    whereChangPrice(price);
                    holder.textViewCurSelectNum.setText(curNum+"");
                    holder.textViewCurSelectNum.setVisibility(View.GONE);
                    holder.imageViewSub.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNaShouCaiInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNaShouCai;
        ImageView imageViewNaShouCai;
        ImageView imageViewAdd;
        ImageView imageViewSub;
        TextView textViewPrice;
        TextView textViewLeftNum;
        TextView textViewSum;
        TextView textViewCurSelectNum;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNaShouCai = (TextView) itemView.findViewById(R.id.textview_nashoucai_name_detail);
            imageViewNaShouCai = (ImageView) itemView.findViewById(R.id.imageview_nashoucai_detail);
            imageViewAdd = (ImageView) itemView.findViewById(R.id.imageview_add_detail);
            imageViewSub = (ImageView) itemView.findViewById(R.id.imageview_sub_detail);
            textViewPrice = (TextView) itemView.findViewById(R.id.textview_price_detail);
            textViewLeftNum = (TextView) itemView.findViewById(R.id.textview_leftnum_detail);
            textViewSum = (TextView) itemView.findViewById(R.id.textview_sum_detail);
            textViewCurSelectNum = (TextView) itemView.findViewById(R.id.textview_cur_select_num_detail);
        }
    }

    private void whereChangPrice(int price){
        if(onPriceChangeListener!=null){
            onPriceChangeListener.onPriceChange(price);
        }
    }

    private  OnPriceChangeListener onPriceChangeListener;
    public interface OnPriceChangeListener{
        void onPriceChange(int price);
    }

    public void setOnPriceChangeListener(OnPriceChangeListener onPriceChangeListener){
        this.onPriceChangeListener = onPriceChangeListener;
    }
}
