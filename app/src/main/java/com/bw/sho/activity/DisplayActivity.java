package com.bw.sho.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.sho.R;
import com.bw.sho.adapter.DisplayAdapter;
import com.bw.sho.api.Api;
import com.bw.sho.base.BaseActivity;
import com.bw.sho.base.OnLoadMoreListener;
import com.bw.sho.bean.Displayinfo;
import com.bw.sho.bean.HomeBanner;
import com.bw.sho.bean.HomeShow;
import com.bw.sho.content.Contach;
import com.bw.sho.presenter.ContachPresenter;
import com.bw.sho.sql.SqlDao;
import com.bw.sho.view.SearchBoxView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class DisplayActivity extends BaseActivity implements View.OnClickListener, Contach.ContachView {

    private RecyclerView recycle;
    private SearchBoxView searchBox;
    private ContachPresenter contachPresenter;
    private SqlDao sqlDao;
    private int page = 1;
    private SwipeRefreshLayout scroll;
    private DisplayAdapter displayAdapter;
    private Handler handler = new Handler();
    private String diplay;
    private List<Displayinfo.ResultBean> list = null;
    private ImageView image;
    //订阅者管理器
    CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_display;
    }

    @Override
    protected void initView() {
        TextView back = findViewById(R.id.di_back);
        recycle = findViewById(R.id.di_recycle);
        searchBox = findViewById(R.id.di_search);
        image = findViewById(R.id.dis_image);
        scroll = findViewById(R.id.di_scro);
        //设置下拉时圆圈的颜色（可以尤多种颜色拼成）
        scroll.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light);
        //设置下拉时圆圈的背景颜色（这里设置成白色）
        scroll.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //设置样式
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recycle.setLayoutManager(manager);
        back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        //接收数据
        Intent intent = getIntent();
        String texts = intent.getStringExtra("text");
        diplay = texts;
        //请求数据
        contachPresenter = new ContachPresenter();
        contachPresenter.attachView(this);
        contachPresenter.getDisplay(Api.DisplayUrl, diplay, page, 6, disposable);
        //数据库
        sqlDao = new SqlDao(this);
        searchBox.setOnBackSearchText(new SearchBoxView.OnBackSearchText() {
            @Override
            public void getText(String text) {
                sqlDao.add(text);
                if (!text.equals("")) {
                    image.setVisibility(View.GONE);
                    recycle.setVisibility(View.VISIBLE);
                    diplay = text;
                    contachPresenter.getDisplay(Api.DisplayUrl, diplay, page, 6, disposable);
                } else {
                    image.setVisibility(View.VISIBLE);
                    recycle.setVisibility(View.GONE);
                }
            }
        });
        //下拉刷新
        scroll.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                contachPresenter.getDisplay(Api.DisplayUrl, diplay, page, 6, disposable);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scroll.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        //上拉加载
        recycle.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            protected void onLoading(int countItem, int lastItem) {
                page++;
                contachPresenter.getDisplay(Api.DisplayUrl, diplay, page, 6, disposable);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scroll.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void getBannerData(HomeBanner body) {

    }

    @Override
    public void getHomeData(HomeShow body) {

    }

    //获得数据
    @Override
    public void getDisplay(Displayinfo displayinfo) {
        String status = displayinfo.getStatus();

        List<Displayinfo.ResultBean> result = displayinfo.getResult();
        //判断有没有更多
        if (result.size() == 0) {
            Toast.makeText(DisplayActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
        }
        Log.i("result", result.size() + "");
        if (page == 1) {
            list = new ArrayList<>();
        }
        list.addAll(result);
        displayAdapter = new DisplayAdapter(this, list);
        recycle.setAdapter(displayAdapter);
        recycle.scrollToPosition(list.size() - result.size() - 2);
        displayAdapter.getCallBackId(new DisplayAdapter.CallBackId() {
            @Override
            public void getId(int id) {
                Intent intent = new Intent(DisplayActivity.this, DetailsActivity.class);
                intent.putExtra("commodityId", id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contachPresenter.delachView(this);
        boolean disposed = disposable.isDisposed();
        if (!disposed) {
            //取消订阅
            disposable.clear();
            //解除订阅
            disposable.dispose();
        }
    }
}
