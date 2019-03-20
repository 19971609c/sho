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
public class MlssAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private HomeShow.ResultBean.MlssBean mlss;

    public MlssAdapter(Context context, HomeShow.ResultBean.MlssBean mlss) {
        this.context = context;
        this.mlss = mlss;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        MlssHolder mlssHolder = new MlssHolder(View.inflate(context, R.layout.item_mlss, null));
        return mlssHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MlssHolder mlssHolder = (MlssHolder) viewHolder;
        //赋值
        mlssHolder.sd.setImageURI(mlss.getCommodityList().get(i).getMasterPic());
        mlssHolder.title.setText(mlss.getCommodityList().get(i).getCommodityName());
        mlssHolder.morney.setText("$:" + mlss.getCommodityList().get(i).getPrice() + ".00");
        //点击事件
        mlssHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回调
                if (callBackId != null) {
                    int commodityId = mlss.getCommodityList().get(i).getCommodityId();
                    callBackId.getId(commodityId);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlss.getCommodityList().size();
    }

    //热销商品
    private class MlssHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView sd;
        private final TextView title;
        private final TextView morney;

        public MlssHolder(@NonNull View itemView) {
            super(itemView);
            sd = itemView.findViewById(R.id.mlss_sd);
            title = itemView.findViewById(R.id.mlss_title);
            morney = itemView.findViewById(R.id.mlss_moreny);
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
