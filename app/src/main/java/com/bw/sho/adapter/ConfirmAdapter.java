package com.bw.sho.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.sho.R;
import com.bw.sho.bean.OrderPagerinfo;
import com.bw.sho.view.NumberView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * @Auther: 不懂
 * @Date: 2019/3/21 11:54:52
 * @Description:
 */
public class ConfirmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<OrderPagerinfo> list;

    public ConfirmAdapter(Context context, List<OrderPagerinfo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_confirm, null, false);
        NumHolder numHolder = new NumHolder(view);
        return numHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        //赋值
        final NumHolder numHolder = (NumHolder) viewHolder;
        numHolder.text.setText(list.get(i).getCommodityName());
        numHolder.money.setText("$" + list.get(i).getPrice() + ".00");
        Glide.with(context).load(list.get(i).getImageurl()).into(numHolder.image);
        Button button = numHolder.numberView.findViewById(R.id.me_text);
        button.setText(list.get(i).getCount() + "");

        int moneys = 0;
        for (int j = 0; j < list.size(); j++) {
            moneys += list.get(j).getPrice() * list.get(j).getCount();
        }

        if (onMoneyClick != null) {
            onMoneyClick.getData(moneys);
        }

        numHolder.numberView.setOnSumItemClick(new NumberView.OnSumItemClick() {
            @Override
            public void sumItemClick(int sum) {

                Button button = numHolder.numberView.findViewById(R.id.me_text);
                String s = button.getText().toString();
                Integer integer = Integer.valueOf(s);
                list.get(i).setCount(integer);
                //价格
                int moneys = 0;
                for (int j = 0; j < list.size(); j++) {
                    moneys += list.get(j).getPrice() * list.get(j).getCount();
                }

                if (onMoneyClick != null) {
                    onMoneyClick.getData(moneys);
                }


                //数量
                if (onNumClick != null) {
                    onNumClick.getData(sum);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NumHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView image;
        private final TextView text;
        private final TextView money;
        private final NumberView numberView;

        public NumHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.conf_image);
            text = itemView.findViewById(R.id.conf_text);
            money = itemView.findViewById(R.id.conf_money);
            numberView = itemView.findViewById(R.id.conf_sum);
        }
    }

    //价格的接口回调
    public interface OnMoneyClick {
        void getData(Integer money);
    }

    public OnMoneyClick onMoneyClick;

    public void setOnMoneyClick(OnMoneyClick onMoneyClick) {
        this.onMoneyClick = onMoneyClick;
    }


    //数量
    public interface OnNumClick {
        void getData(int num);
    }

    public OnNumClick onNumClick;

    public void setOnNumClick(OnNumClick onNumClick) {
        this.onNumClick = onNumClick;
    }

}



