package com.bw.sho.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.sho.R;
import com.bw.sho.bean.OneListinfo;

import java.util.List;

/**
 * @Auther: 不懂
 * @Date: 2019/3/1 14:06:13
 * @Description:
 */
public class ListOneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<OneListinfo.ResultBean> oneList;

    public ListOneAdapter(Context context, List<OneListinfo.ResultBean> oneList) {
        this.context = context;
        this.oneList = oneList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_one_list, null);
        OneHolder oneHolder = new OneHolder(view);
        return oneHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final OneHolder oneHolder = (OneHolder) viewHolder;
        oneHolder.text.setText(oneList.get(i).getName());
        //默认回传第一个
        if (i == 0) {
            String id = oneList.get(0).getId();
            if (onOneClick != null) {
                onOneClick.setIdData(id);
            }

        }
        //点击条目回传
        oneHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = oneList.get(i).getId();
                if (onOneClick != null) {
                    onOneClick.setIdData(id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return oneList.size();
    }


    public class OneHolder extends RecyclerView.ViewHolder {

        private final TextView text;

        public OneHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.oneHolder_text);
        }
    }

    public interface OnOneClick {
        void setIdData(String id);
    }

    public OnOneClick onOneClick;

    public void setOnOneClick(OnOneClick onOneClick) {
        this.onOneClick = onOneClick;
    }


}
