package com.bw.sho.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.sho.R;
import com.bw.sho.bean.Wallerinfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 不懂
 * @Date: 2019/3/17 14:43:13
 * @Description:
 */
public class WalletAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //正常内容
    private final static int TYPE0 = 0;
    //加载View
    private final static int TYPE1 = 1;


    private Context context;
    private List<Wallerinfo.ResultBean.DetailListBean> detailList;

    public WalletAdapter(Context context, List<Wallerinfo.ResultBean.DetailListBean> detailList) {
        this.context = context;
        this.detailList = detailList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == detailList.size()) {
            return TYPE1;
        }
        return TYPE0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == TYPE1) {
            FootHolder footHolder = new FootHolder(View.inflate(context, R.layout.foot, null));
            return footHolder;
        }
        WalletHolder walletHolder = new WalletHolder(View.inflate(context, R.layout.item_wallt, null));
        return walletHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof WalletHolder) {
            WalletHolder walletHolder = (WalletHolder) viewHolder;
            //赋值
            walletHolder.price.setText("$" + detailList.get(i).getAmount());
            long consumerTime = detailList.get(i).getConsumerTime();
            Date date = new Date(consumerTime);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String time = format.format(date);
            walletHolder.item.setText(time);
        }
        if (viewHolder instanceof FootHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return detailList.size() + 1;
    }

    private class WalletHolder extends RecyclerView.ViewHolder {

        private final TextView price;
        private final TextView item;

        public WalletHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.wallt_price);
            item = itemView.findViewById(R.id.wallt_time);
        }
    }

    private class FootHolder extends RecyclerView.ViewHolder {

        public FootHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

}
