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
public class PzshAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private HomeShow.ResultBean.PzshBean pzsh;

    public PzshAdapter(Context context, HomeShow.ResultBean.PzshBean pzsh) {
        this.context = context;
        this.pzsh = pzsh;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        PzshHolder pzshHolder = new PzshHolder(View.inflate(context, R.layout.item_pzsh, null));
        return pzshHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        PzshHolder pzshHolder = (PzshHolder) viewHolder;
        //赋值
        pzshHolder.sd.setImageURI(pzsh.getCommodityList().get(i).getMasterPic());
        pzshHolder.title.setText(pzsh.getCommodityList().get(i).getCommodityName());
        pzshHolder.morney.setText("$:" + pzsh.getCommodityList().get(i).getPrice() + ".00");
        //点击事件
        pzshHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //接口回调
                if (callBackId != null) {
                    int commodityId = pzsh.getCommodityList().get(i).getCommodityId();
                    callBackId.getId(commodityId);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return pzsh.getCommodityList().size();
    }

    //热销商品
    private class PzshHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView sd;
        private final TextView title;
        private final TextView morney;

        public PzshHolder(@NonNull View itemView) {
            super(itemView);
            sd = itemView.findViewById(R.id.pzsh_sd);
            title = itemView.findViewById(R.id.pzsh_title);
            morney = itemView.findViewById(R.id.pzsh_moreny);
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
