package com.bw.sho.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bw.sho.R;
import com.bw.sho.bean.WholeOrderinfo;
import com.bw.sho.view.NumberView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * @Auther: 不懂
 * @Date: 2019/3/17 14:43:13
 * @Description:
 */
public class WholeChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<WholeOrderinfo.OrderListBean.DetailListBean> detailList;

    public WholeChildAdapter(Context context, List<WholeOrderinfo.OrderListBean.DetailListBean> detailList) {
        this.context = context;
        this.detailList = detailList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        WholeChildHolder wholeChildHolder = new WholeChildHolder(View.inflate(context, R.layout.item_childwhole, null));
        return wholeChildHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        WholeChildHolder wholeChildHolder = (WholeChildHolder) viewHolder;
        //赋值
        String[] split = detailList.get(i).getCommodityPic().split(",");
        wholeChildHolder.image.setImageURI(split[0]);
        wholeChildHolder.title.setText(detailList.get(i).getCommodityName());
        wholeChildHolder.price.setText("$:" + detailList.get(i).getCommodityPrice() + ".00");
        Button num = wholeChildHolder.numberView.findViewById(R.id.me_text);
        num.setText(detailList.get(i).getCommodityCount()+"");
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }


    private class WholeChildHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView image;
        private final TextView price;
        private final NumberView numberView;
        private final TextView title;

        public WholeChildHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.who_image);
            price = itemView.findViewById(R.id.who_money);
            numberView = itemView.findViewById(R.id.who_sum);
            title = itemView.findViewById(R.id.who_text);
        }
    }

}
