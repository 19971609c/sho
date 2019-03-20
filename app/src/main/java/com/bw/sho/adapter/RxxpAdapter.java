package com.bw.sho.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bw.sho.R;
import com.bw.sho.bean.HomeShow;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @Auther: 不懂
 * @Date: 2019/3/17 14:43:13
 * @Description:
 */
public class RxxpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private HomeShow.ResultBean.RxxpBean rxxp;

    public RxxpAdapter(Context context, HomeShow.ResultBean.RxxpBean rxxp) {
        this.context = context;
        this.rxxp = rxxp;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        RxxpHolder rxxpHolder = new RxxpHolder(View.inflate(context, R.layout.item_rxxp, null));
        return rxxpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        RxxpHolder rxxpHolder = (RxxpHolder) viewHolder;
        //赋值
        rxxpHolder.sd.setImageURI(rxxp.getCommodityList().get(i).getMasterPic());
        rxxpHolder.title.setText(rxxp.getCommodityList().get(i).getCommodityName());
        rxxpHolder.morney.setText("$:" + rxxp.getCommodityList().get(i).getPrice() + ".00");
        //点击事件
        rxxpHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //接口回调
                if (callBackId != null) {
                    int commodityId = rxxp.getCommodityList().get(i).getCommodityId();
                    callBackId.getId(commodityId);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rxxp.getCommodityList().size();
    }

    //热销商品
    private class RxxpHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView sd;
        private final TextView title;
        private final TextView morney;

        public RxxpHolder(@NonNull View itemView) {
            super(itemView);
            sd = itemView.findViewById(R.id.rxxp_sd);
            title = itemView.findViewById(R.id.rxxp_title);
            morney = itemView.findViewById(R.id.rxxp_moreny);
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
