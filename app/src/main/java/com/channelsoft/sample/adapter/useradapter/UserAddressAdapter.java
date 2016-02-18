package com.channelsoft.sample.adapter.useradapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.channelsoft.sample.R;
import com.channelsoft.sample.activity.user.address.AddNewAddress;
import com.channelsoft.sample.model.user.Address;

import java.util.List;

/**
 * Created by 王宗贤 on 2015/12/28.
 */
public class UserAddressAdapter extends RecyclerView.Adapter<UserAddressAdapter.ViewHolder> {
    private List<Address> mDate;
    private Context mContext;


    public UserAddressAdapter(List<Address> mDate, Context mContext) {
        this.mDate = mDate;
        this.mContext = mContext;
    }

    @Override
    public UserAddressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_user_item_user_address,null));
    }

    @Override
    public void onBindViewHolder(UserAddressAdapter.ViewHolder holder, final int position) {
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddAddress = new Intent(mContext, AddNewAddress.class);
                intentAddAddress.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentAddAddress.putExtra("status", "edit");
                intentAddAddress.putExtra("position",position);
//                直接将送餐地址传递过去
                intentAddAddress.putExtra("name",mDate.get(position).getUserName());
                intentAddAddress.putExtra("tel",mDate.get(position).getUserTel());
                intentAddAddress.putExtra("address",mDate.get(position).getUserAddress());
                mContext.startActivity(intentAddAddress);
            }
        });
        holder.textName.setText(mDate.get(position).getUserName());
        holder.textTel.setText(mDate.get(position).getUserTel());
        holder.textAddress.setText(mDate.get(position).getUserAddress());
    }

    @Override
    public int getItemCount() {
        return mDate.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgEdit;
        TextView textName;
        TextView textTel;
        TextView textAddress;
        public ViewHolder(View itemView) {
            super(itemView);
            imgEdit= (ImageView) itemView.findViewById(R.id.img_address_item_edit);
            textName= (TextView) itemView.findViewById(R.id.text_address_item_name);
            textTel= (TextView) itemView.findViewById(R.id.text_address_item_tel);
            textAddress= (TextView) itemView.findViewById(R.id.text_address_item_address);
        }
    }
}
