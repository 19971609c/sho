package com.bw.sho.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.sho.R;
import com.bw.sho.bean.Addressinfo;

import java.util.List;

/**
 * @Auther: 不懂
 * @Date: 2019/3/17 14:43:13
 * @Description:
 */
public class ConfirmAddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<Addressinfo.ResultBean> list;

    public ConfirmAddressAdapter(Context context, List<Addressinfo.ResultBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AddressHolder addressHolder = new AddressHolder(View.inflate(context, R.layout.item_confirmaddress, null));
        return addressHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        AddressHolder addressHolder = (AddressHolder) viewHolder;
        //赋值
        addressHolder.name.setText(list.get(i).getRealName());
        addressHolder.phone.setText(list.get(i).getPhone());
        addressHolder.address.setText(list.get(i).getAddress());
        //点击事件
        addressHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mid = list.get(i).getId();
                if (callBackConfirmId != null) {
                    callBackConfirmId.getId(mid + "");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //热销商品
    private class AddressHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView phone;
        private final TextView address;
        private final TextView check;

        public AddressHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.con_name);
            phone = itemView.findViewById(R.id.con_phone);
            address = itemView.findViewById(R.id.con_adderss);
            check = itemView.findViewById(R.id.con_check);
        }
    }

    //接口回调
    public interface CallBackConfirmId {
        void getId(String id);
    }

    public CallBackConfirmId callBackConfirmId;

    public void getCallBackConfirmId(CallBackConfirmId callBackConfirmId) {
        this.callBackConfirmId = callBackConfirmId;
    }
}
