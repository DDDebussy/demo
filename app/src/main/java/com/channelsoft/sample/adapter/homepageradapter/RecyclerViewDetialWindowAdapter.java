package com.channelsoft.sample.adapter.homepageradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.channelsoft.sample.R;
import com.channelsoft.sample.model.homepager.NaShouCaiInfo;
import com.channelsoft.sample.util.CommonUtils;

import java.util.List;

/**
 * Created by chenlijin on 2015/12/25.
 */
public class RecyclerViewDetialWindowAdapter extends RecyclerView.Adapter<RecyclerViewDetialWindowAdapter.ViewHolder> {
    private List<NaShouCaiInfo> mNaShouCaiInfoList;
    private Context mContext;
    private Integer price = 0;

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setNaShouCaiInfoList(List<NaShouCaiInfo> naShouCaiInfoList) {
        this.mNaShouCaiInfoList = naShouCaiInfoList;
    }

    public RecyclerViewDetialWindowAdapter(List<NaShouCaiInfo> naShouCaiInfoList, Context mContext) {
        this.mNaShouCaiInfoList = naShouCaiInfoList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_window_detail, null));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final NaShouCaiInfo naShouCaiInfo = mNaShouCaiInfoList.get(position);
        if(naShouCaiInfo.getCurNum()>0){
            holder.linearLayoutWindow.setVisibility(View.VISIBLE);
            holder.textViewCaiMing.setText(naShouCaiInfo.getCaiMing());
            holder.textViewPrice.setText(naShouCaiInfo.getPrice()+"");
            holder.textViewNum.setText(naShouCaiInfo.getCurNum()+"");
            //计算剩余份数
            final int[] leftNum = {naShouCaiInfo.getLeftNum()};
            holder.imageViewAdd.setOnClickListener(new View.OnClickListener() {
                int curNum = 0;
                @Override
                public void onClick(View v) {
                    holder.imageViewSub.setVisibility(View.VISIBLE);
                    holder.textViewNum.setVisibility(View.VISIBLE);
                    curNum = Integer.parseInt(holder.textViewNum.getText().toString());
                    if(leftNum[0] >0){
                        curNum++;
                        naShouCaiInfo.setCurNum(curNum);
                        holder.textViewNum.setText(curNum+"");
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
                    curNum = Integer.parseInt(holder.textViewNum.getText().toString());
                    if(curNum>1){
                        curNum--;
                        naShouCaiInfo.setCurNum(curNum);
                        price -= naShouCaiInfo.getPrice();
                        whereChangPrice(price);
                        holder.textViewNum.setText(curNum+"");
                        leftNum[0]++;
                    }else {
                        curNum--;
                        naShouCaiInfo.setCurNum(curNum);
                        leftNum[0]++;
                        price -= naShouCaiInfo.getPrice();
                        whereChangPrice(price);
                        holder.textViewNum.setText(curNum+"");
                        holder.textViewNum.setVisibility(View.INVISIBLE);
                        holder.imageViewSub.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }else {
            holder.linearLayoutWindow.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount(){
        return mNaShouCaiInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCaiMing;
        ImageView imageViewAdd;
        ImageView imageViewSub;
        TextView textViewPrice;
        TextView textViewNum;
        LinearLayout linearLayoutWindow;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewCaiMing = (TextView) itemView.findViewById(R.id.textview_cai_detail_window);
            textViewPrice = (TextView) itemView.findViewById(R.id.textview_caiqian_detail_window);
            textViewNum = (TextView) itemView.findViewById(R.id.textview_num_detail_window);
            imageViewSub = (ImageView) itemView.findViewById(R.id.imageview_sub_detail_window);
            imageViewAdd = (ImageView) itemView.findViewById(R.id.imageview_add_detail_window);
            linearLayoutWindow = (LinearLayout) itemView.findViewById(R.id.linearlayout_window);
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
