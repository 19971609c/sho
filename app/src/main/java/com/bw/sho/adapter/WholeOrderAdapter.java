package com.bw.sho.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bw.sho.R;
import com.bw.sho.bean.WholeOrderinfo;

import java.util.List;

/**
 * @Auther: 不懂
 * @Date: 2019/3/17 14:43:13
 * @Description:
 */
public class WholeOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<WholeOrderinfo.OrderListBean> orderwhileList;

    public WholeOrderAdapter(Context context, List<WholeOrderinfo.OrderListBean> orderwhileList) {
        this.context = context;
        this.orderwhileList = orderwhileList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        WholeHolder wholeHolder = new WholeHolder(View.inflate(context, R.layout.item_wholeone, null));
        return wholeHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        WholeHolder wholeHolder = (WholeHolder) viewHolder;
        //赋值
        wholeHolder.id.setText(orderwhileList.get(i).getOrderId());
        wholeHolder.time.setText("2019-03-28");
        List<WholeOrderinfo.OrderListBean.DetailListBean> detailList = orderwhileList.get(i).getDetailList();
        wholeHolder.num.setText("共" + detailList.size() + "件商品");
        int money = 0;
        for (int j = 0; j < detailList.size(); j++) {
            money += detailList.get(j).getCommodityCount() * detailList.get(j).getCommodityPrice();
        }
        wholeHolder.price.setText("需支付" + money + ".00元");

        final int price = money;
        LinearLayoutManager manager = new LinearLayoutManager(context);
        wholeHolder.recyclerView.setLayoutManager(manager);
        //点击事件
        WholeChildAdapter wholeChildAdapter = new WholeChildAdapter(context, detailList);
        wholeHolder.recyclerView.setAdapter(wholeChildAdapter);

        wholeHolder.payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPayment != null) {
                    onPayment.getId(orderwhileList.get(i).getOrderId(),price);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderwhileList.size();
    }

    private class WholeHolder extends RecyclerView.ViewHolder {

        private final TextView id;
        private final TextView time;
        private final TextView price;
        private final TextView num;
        private final Button cancel;
        private final Button payment;
        private final RecyclerView recyclerView;

        public WholeHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.wh_id);
            time = itemView.findViewById(R.id.wh_time);
            num = itemView.findViewById(R.id.wh_num);
            price = itemView.findViewById(R.id.wh_price);
            cancel = itemView.findViewById(R.id.wh_cancel);
            payment = itemView.findViewById(R.id.wh_payment);
            recyclerView = itemView.findViewById(R.id.wh_recycle2);
        }
    }

    //支付
    public interface OnPayment {
        void getId(String id,int price);
    }

    public OnPayment onPayment;

    public void setOnPayment(OnPayment onPayment) {
        this.onPayment = onPayment;
    }
}
