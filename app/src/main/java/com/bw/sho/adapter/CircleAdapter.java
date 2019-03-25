package com.bw.sho.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.sho.R;
import com.bw.sho.bean.Circleinfo;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * @Auther: 不懂
 * @Date: 2019/3/13 20:46:46
 * @Description:
 */
public class CircleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE0 = 1;

    private static final int TYPE1 = 2;

    private static final int TYPE2 = 3;

    private static final int TYPE3 = 4;

    private Context context;
    private List<Circleinfo.ResultBean> circleList;

    public CircleAdapter(Context context, List<Circleinfo.ResultBean> circleList) {
        this.context = context;
        this.circleList = circleList;
    }

    @Override
    public int getItemViewType(int position) {
        Circleinfo.ResultBean resultBean = circleList.get(position);
        String[] split = resultBean.getImage().split(",");
        Log.i("hhh", split.length + "");
        if (split.length == TYPE0) {
            return TYPE0;
        } else if (split.length == TYPE1) {
            return TYPE1;
        } else if (split.length == TYPE2) {
            return TYPE2;
        } else if (split.length == TYPE3) {
            return TYPE3;
        }
        return TYPE3;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == TYPE0) {
            OneHolder oneHolder = new OneHolder(View.inflate(context, R.layout.item_cirone, null));
            return oneHolder;
        }
        if (i == TYPE1) {
            TwoHolder twoHolder = new TwoHolder(View.inflate(context, R.layout.item_cirtwo, null));
            return twoHolder;
        }
        if (i == TYPE2) {
            ThreeHolder threeHolder = new ThreeHolder(View.inflate(context, R.layout.item_cirthree, null));
            return threeHolder;
        }
        FourHolder fourHolder = new FourHolder(View.inflate(context, R.layout.item_cirfour, null));
        return fourHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (viewHolder instanceof OneHolder) {
            OneHolder oneHolder = (OneHolder) viewHolder;
            oneHolder.onename.setText(circleList.get(i).getNickName());
            oneHolder.onetime.setText(circleList.get(i).getCreateTime() + "");
            oneHolder.onetitle.setText(circleList.get(i).getContent());
            oneHolder.onesd.setImageURI(circleList.get(i).getImage());
            Glide.with(context).load(circleList.get(i).getHeadPic()).into(oneHolder.oneimage);
        }

        if (viewHolder instanceof TwoHolder) {
            TwoHolder twoHolder = (TwoHolder) viewHolder;
            twoHolder.twoname.setText(circleList.get(i).getNickName());
            twoHolder.twotime.setText(circleList.get(i).getCreateTime() + "");
            twoHolder.twotitle.setText(circleList.get(i).getContent());
            twoHolder.twosd.setImageURI(circleList.get(i).getImage());
            String[] split = circleList.get(i).getHeadPic().split(",");
            Log.i("hhhh", split.length + ""+"s");
            Glide.with(context).load(split[0]).into(twoHolder.twoimage);
            /* Glide.with(context).load(split[1]).into(twoHolder.twoimagetwo);*/
        }

        if (viewHolder instanceof ThreeHolder) {
            ThreeHolder threeHolder = (ThreeHolder) viewHolder;
            threeHolder.threename.setText(circleList.get(i).getNickName());
            threeHolder.threetime.setText(circleList.get(i).getCreateTime() + "");
            threeHolder.threetitle.setText(circleList.get(i).getContent());
            threeHolder.threesd.setImageURI(circleList.get(i).getImage());
            String[] split = circleList.get(i).getHeadPic().split(",");
            Glide.with(context).load(split[0]).into(threeHolder.threeone);
           /* Glide.with(context).load(split[1]).into(threeHolder.threetwo);
            Glide.with(context).load(split[2]).into(threeHolder.threthree);*/
        }

        if (viewHolder instanceof FourHolder) {
            FourHolder fourHolder = (FourHolder) viewHolder;
            fourHolder.fourname.setText(circleList.get(i).getNickName());
            fourHolder.fourtime.setText(circleList.get(i).getCreateTime() + "");
            fourHolder.fourtitle.setText(circleList.get(i).getContent());
            fourHolder.foursd.setImageURI(circleList.get(i).getImage());
            String[] split = circleList.get(i).getHeadPic().split(",");
            Glide.with(context).load(split[0]).into(fourHolder.fourone);
            /*Glide.with(context).load(split[1]).into(fourHolder.fouretwo);
            Glide.with(context).load(split[2]).into(fourHolder.fourhree);
            Glide.with(context).load(split[3]).into(fourHolder.fourfour);*/
        }
    }

    @Override
    public int getItemCount() {
        return circleList.size();
    }


    private class OneHolder extends RecyclerView.ViewHolder {

        private final ImageView oneimage;
        private final TextView onename;
        private final SimpleDraweeView onesd;
        private final TextView onetime;
        private final TextView onetitle;

        public OneHolder(@NonNull View itemView) {
            super(itemView);
            oneimage = itemView.findViewById(R.id.one_image);
            onename = itemView.findViewById(R.id.one_name);
            onesd = itemView.findViewById(R.id.one_sd);
            onetime = itemView.findViewById(R.id.one_time);
            onetitle = itemView.findViewById(R.id.one_title);
        }
    }

    private class TwoHolder extends RecyclerView.ViewHolder {


        private final ImageView twoimage;
        private final ImageView twoimagetwo;
        private final SimpleDraweeView twosd;
        private final TextView twoname;
        private final TextView twotime;
        private final TextView twotitle;

        public TwoHolder(@NonNull View itemView) {
            super(itemView);
            twoimage = itemView.findViewById(R.id.two_image);
            twoimagetwo = itemView.findViewById(R.id.two_imagetwo);
            twosd = itemView.findViewById(R.id.two_sd);
            twoname = itemView.findViewById(R.id.two_name);
            twotime = itemView.findViewById(R.id.two_time);
            twotitle = itemView.findViewById(R.id.two_title);
        }
    }


    private class ThreeHolder extends RecyclerView.ViewHolder {

        private final ImageView threeone;
        private final ImageView threetwo;
        private final ImageView threthree;
        private final SimpleDraweeView threesd;
        private final TextView threename;
        private final TextView threetime;
        private final TextView threetitle;

        public ThreeHolder(@NonNull View itemView) {
            super(itemView);
            threeone = itemView.findViewById(R.id.three_image);
            threetwo = itemView.findViewById(R.id.three_imagetwo);
            threthree = itemView.findViewById(R.id.three_imagethree);
            threesd = itemView.findViewById(R.id.three_sd);
            threename = itemView.findViewById(R.id.three_name);
            threetime = itemView.findViewById(R.id.three_time);
            threetitle = itemView.findViewById(R.id.three_title);
        }
    }


    private class FourHolder extends RecyclerView.ViewHolder {

        private final ImageView fourone;
        private final ImageView fouretwo;
        private final ImageView fourhree;
        private final ImageView fourfour;
        private final SimpleDraweeView foursd;
        private final TextView fourname;
        private final TextView fourtime;
        private final TextView fourtitle;

        public FourHolder(@NonNull View itemView) {
            super(itemView);
            fourone = itemView.findViewById(R.id.four_image);
            fouretwo = itemView.findViewById(R.id.four_imagetwo);
            fourhree = itemView.findViewById(R.id.four_imagethree);
            fourfour = itemView.findViewById(R.id.four_imagefour);
            foursd = itemView.findViewById(R.id.four_sd);
            fourname = itemView.findViewById(R.id.four_name);
            fourtime = itemView.findViewById(R.id.four_time);
            fourtitle = itemView.findViewById(R.id.four_title);
        }
    }

}
