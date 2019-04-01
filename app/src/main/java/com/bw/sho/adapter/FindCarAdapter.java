package com.bw.sho.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bw.sho.R;
import com.bw.sho.bean.FindCarResclt;
import com.bw.sho.bean.FindCarinfo;
import com.bw.sho.view.NumberView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * @Auther: 不懂
 * @Date: 2019/3/21 11:54:52
 * @Description:
 */
public class FindCarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<FindCarResclt> list;

    public FindCarAdapter(Context context, List<FindCarResclt> list) {
        this.context = context;
        this.list = list;
        isCheck(false);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_findcar, null, false);
        NumHolder numHolder = new NumHolder(view);
        return numHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        //赋值
        final NumHolder numHolder = (NumHolder) viewHolder;
        numHolder.text.setText(list.get(i).getCommodityName());
        numHolder.money.setText("$" + list.get(i).getPrice() + ".00");
        numHolder.image.setImageURI(list.get(i).getPic());
        numHolder.check.setChecked(list.get(i).getIsCheck());

        //点击事件
        numHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置反值
                boolean check = list.get(i).getIsCheck();
                list.get(i).setIsCheck(!check);
                //价格
                int moneys = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getIsCheck()) {
                        moneys += list.get(i).getPrice() * list.get(i).getCount();
                    }
                }
                if (onMoneyClick!=null){
                    onMoneyClick.getData(moneys);
                }
                //按钮
                boolean is = true;
                for (int j = 0; j < list.size(); j++) {
                    if (!list.get(j).getIsCheck()) {
                        is = false;
                        if (oncheckClick != null) {
                            oncheckClick.setBoolean(is);
                        }
                        return;
                    }
                }
                if (is) {
                    if (oncheckClick != null) {
                        oncheckClick.setBoolean(is);
                    }
                }
            }
        });

        //点击事件
        numHolder.numberView.setOnSumItemClick(new NumberView.OnSumItemClick() {
            @Override
            public void sumItemClick(int sum) {
                list.get(i).setCount(sum);
                //价格
                int moneys = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getIsCheck()) {
                        moneys += list.get(i).getPrice() * list.get(i).getCount();
                    }
                }
                if (onMoneyClick!=null){
                    onMoneyClick.getData(moneys);
                }
            }
        });

    }

    public void isCheck(boolean ischeck) {
        setCheck(ischeck);
    }

    private void setCheck(boolean check) {

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setIsCheck(check);
        }
        notifyDataSetChanged();

        //计算价格
        int moneys = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIsCheck()) {
                moneys += list.get(i).getPrice() * list.get(i).getCount();
            }
        }

        if (onMoneyClick!=null){
            onMoneyClick.getData(moneys);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NumHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView image;
        private final TextView text;
        private final TextView money;
        private final com.bw.sho.view.NumberView numberView;
        private final CheckBox check;

        public NumHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_image);
            text = itemView.findViewById(R.id.item_text);
            money = itemView.findViewById(R.id.item_money);
            numberView = itemView.findViewById(R.id.item_sum);
            check = itemView.findViewById(R.id.item_check);
        }
    }

    //checkBox接口回调
    public interface OncheckClick {
        void setBoolean(boolean check);
    }

    public OncheckClick oncheckClick;

    public void setOncheckClick(OncheckClick oncheckClick) {
        this.oncheckClick = oncheckClick;
    }

    //价格的接口回调

    public interface OnMoneyClick {
        void getData(Integer money);
    }

    public OnMoneyClick onMoneyClick;

    public void setOnMoneyClick(OnMoneyClick onMoneyClick) {
        this.onMoneyClick = onMoneyClick;
    }

}



