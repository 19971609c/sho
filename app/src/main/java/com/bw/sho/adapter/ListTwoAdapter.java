package com.bw.sho.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.sho.R;
import com.bw.sho.bean.TwoListinfo;

import java.util.List;

/**
 * @Auther: 不懂
 * @Date: 2019/3/1 14:06:13
 * @Description:
 */
public class ListTwoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<TwoListinfo.ResultBean> list;

    public ListTwoAdapter(Context context, List<TwoListinfo.ResultBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_two_list, null);
        ListTwoHolder listTwoHolder = new ListTwoHolder(view);
        return listTwoHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ListTwoHolder listTwoHolder = (ListTwoHolder) viewHolder;
        //二级列表
        listTwoHolder.text.setText(list.get(i).getName());
        listTwoHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = list.get(i).getId();
                if (onTwoClick != null) {
                    onTwoClick.setIdData(id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ListTwoHolder extends RecyclerView.ViewHolder {
        private final TextView text;

        public ListTwoHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.twoHolder_text);
        }
    }


    public interface OnTwoClick {
        void setIdData(String id);
    }

    public OnTwoClick onTwoClick;

    public void setOnTwoClick(OnTwoClick onTwoClick) {
        this.onTwoClick = onTwoClick;
    }

}
