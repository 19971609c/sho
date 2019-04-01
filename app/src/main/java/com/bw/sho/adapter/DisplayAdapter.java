package com.bw.sho.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.sho.R;
import com.bw.sho.bean.Displayinfo;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * @Auther: 不懂
 * @Date: 2019/3/20 15:39:44
 * @Description:
 */
public class DisplayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //正常内容
    private final static int TYPE0 = 0;
    //加载View
    private final static int TYPE1 = 1;

    private Context context;
    private List<Displayinfo.ResultBean> result;

    public DisplayAdapter(Context context, List<Displayinfo.ResultBean> result) {
        this.context = context;
        this.result = result;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == result.size()) {
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
        DisHolder disHolder = new DisHolder(View.inflate(context, R.layout.item_grid, null));
        return disHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof DisHolder) {
            DisHolder disHolder = (DisHolder) viewHolder;
            //赋值
            disHolder.sd.setImageURI(result.get(i).getMasterPic());
            disHolder.title.setText(result.get(i).getCommodityName());
            disHolder.morney.setText("$:" + result.get(i).getPrice() + ".00");
            //点击事件
            disHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //接口回调
                    if (callBackId != null) {
                        int commodityId = result.get(i).getCommodityId();
                        callBackId.getId(commodityId);
                    }
                }
            });
        }
        if (viewHolder instanceof FootHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return result.size()+1;
    }

    //关键字
    private class DisHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView sd;
        private final TextView title;
        private final TextView morney;

        public DisHolder(@NonNull View itemView) {
            super(itemView);
            sd = itemView.findViewById(R.id.grid_sd);
            title = itemView.findViewById(R.id.grid_title);
            morney = itemView.findViewById(R.id.grid_moreny);
        }
    }

    //脚
    private class FootHolder extends RecyclerView.ViewHolder {

        public FootHolder(@NonNull View itemView) {
            super(itemView);

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
