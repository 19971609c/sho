package com.bw.sho.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bw.sho.R;
import com.bw.sho.bean.Addressinfo;

import java.util.List;

/**
 * @Auther: 不懂
 * @Date: 2019/3/17 14:43:13
 * @Description:
 */
public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<Addressinfo.ResultBean> list;

    public AddressAdapter(Context context, List<Addressinfo.ResultBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AddressHolder addressHolder = new AddressHolder(View.inflate(context, R.layout.item_address, null));
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
        int id = list.get(i).getId();
        Log.i("id",id+"");
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
        private final CheckBox check;
        private final Button delete;
        private final Button up;

        public AddressHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.add_name);
            phone = itemView.findViewById(R.id.add_phone);
            address = itemView.findViewById(R.id.add_adderss);
            check = itemView.findViewById(R.id.add_check);
            delete = itemView.findViewById(R.id.add_delete);
            up = itemView.findViewById(R.id.add_up);
        }
    }

    //接口回调
    public interface CallBackId {
        void getId(int id);
    }

    public CallBackId callBackId;

    public void getCallBackId(CallBackId callBackId) {
        this.callBackId = callBackId;
    }
}
