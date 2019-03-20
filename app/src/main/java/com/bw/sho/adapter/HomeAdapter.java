package com.bw.sho.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bw.sho.R;
import com.bw.sho.bean.HomeBanner;
import com.bw.sho.bean.HomeShow;
import com.stx.xhb.xbanner.XBanner;

import java.util.List;

/**
 * @Auther: 不懂
 * @Date: 2019/3/13 20:46:46
 * @Description:
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //轮播图
    private static final int TYPE0 = 0;
    //热销商品
    private static final int TYPE1 = 1;
    //魔力时尚
    private static final int TYPE2 = 2;
    //品质生活
    private static final int TYPE3 = 3;

    private Context context;
    private HomeShow homeShow;
    private HomeBanner body;
    private List<HomeBanner.ResultBean> result;

    public HomeAdapter(Context context, HomeShow homeShow, HomeBanner body) {
        this.context = context;
        this.homeShow = homeShow;
        this.body = body;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == TYPE0) {
            return TYPE0;
        } else if (position == TYPE1) {
            return TYPE1;
        } else if (position == TYPE2) {
            return TYPE2;
        } else {
            return TYPE3;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == TYPE0) {
            BannerHolder bannerHolder = new BannerHolder(View.inflate(context, R.layout.home_banner, null));
            return bannerHolder;
        }
        if (i == TYPE1) {
            RxxpHolder rxxpHolder = new RxxpHolder(View.inflate(context, R.layout.home_rxxp, null));
            return rxxpHolder;
        }
        if (i == TYPE2) {
            MlssHolder mlssHolder = new MlssHolder(View.inflate(context, R.layout.home_mlss, null));
            return mlssHolder;
        }
        PzshHolder pzshHolder = new PzshHolder(View.inflate(context, R.layout.home_pzsh, null));
        return pzshHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        //轮播图
        if (viewHolder instanceof BannerHolder) {
            BannerHolder bannerHolder = (BannerHolder) viewHolder;
            result = body.getResult();
            bannerHolder.xBanner.setData(result, null);
            bannerHolder.xBanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Glide.with(context).load(result.get(position).getImageUrl()).into((ImageView) view);
                }
            });
        }
        //热销
        if (viewHolder instanceof RxxpHolder) {
            RxxpHolder rxxpHolder = (RxxpHolder) viewHolder;
            //设置样式
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rxxpHolder.recycle.setLayoutManager(manager);
            //值
            HomeShow.ResultBean.RxxpBean rxxp = homeShow.getResult().getRxxp();
            RxxpAdapter rxxpAdapter = new RxxpAdapter(context, rxxp);
            rxxpHolder.recycle.setAdapter(rxxpAdapter);
            //回调
            rxxpAdapter.getCallBackId(new RxxpAdapter.CallBackId() {
                @Override
                public void getId(int id) {
                    //回调详情Id
                    if (callBackId != null) {
                        callBackId.getId(id);
                    }
                }
            });
        }
        //魔力
        if (viewHolder instanceof MlssHolder) {
            MlssHolder mlssHolder = (MlssHolder) viewHolder;
            //设置样式
            LinearLayoutManager manager = new LinearLayoutManager(context);
            mlssHolder.recycle.setLayoutManager(manager);
            //值
            HomeShow.ResultBean.MlssBean mlss = homeShow.getResult().getMlss();
            MlssAdapter mlssAdapter = new MlssAdapter(context, mlss);
            mlssHolder.recycle.setAdapter(mlssAdapter);
            //回调
            mlssAdapter.getCallBackId(new MlssAdapter.CallBackId() {
                @Override
                public void getId(int id) {
                    //回调详情Id
                    if (callBackId != null) {
                        callBackId.getId(id);
                    }
                }
            });
        }
        //品质
        if (viewHolder instanceof PzshHolder) {
            PzshHolder pzshHolder = (PzshHolder) viewHolder;
            //设置样式
            GridLayoutManager manager = new GridLayoutManager(context, 2);
            pzshHolder.recycle.setLayoutManager(manager);
            //值
            HomeShow.ResultBean.PzshBean pzsh = homeShow.getResult().getPzsh();
            PzshAdapter pzshAdapter = new PzshAdapter(context, pzsh);
            pzshHolder.recycle.setAdapter(pzshAdapter);
            //回调
            pzshAdapter.getCallBackId(new PzshAdapter.CallBackId() {
                @Override
                public void getId(int id) {
                    //回调详情Id
                    if (callBackId != null) {
                        callBackId.getId(id);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    //轮播图
    private class BannerHolder extends RecyclerView.ViewHolder {

        private final XBanner xBanner;

        public BannerHolder(@NonNull View itemView) {
            super(itemView);
            xBanner = itemView.findViewById(R.id.home_banner);
        }
    }

    //热销商品
    private class RxxpHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final RecyclerView recycle;

        public RxxpHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.rxxp_image);
            recycle = itemView.findViewById(R.id.rxxp_recycle);
        }
    }

    //魔力时尚
    private class MlssHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final RecyclerView recycle;

        public MlssHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mlss_image);
            recycle = itemView.findViewById(R.id.mlss_recycle);
        }
    }

    //品质生活
    private class PzshHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final RecyclerView recycle;

        public PzshHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pzsh_image);
            recycle = itemView.findViewById(R.id.pzsh_recycle);
        }
    }

    //商品详情接口
    public interface CallBackId {
        void getId(int id);
    }

    public CallBackId callBackId;

    public void getCallBackId(CallBackId callBackId) {
        this.callBackId = callBackId;
    }
}
