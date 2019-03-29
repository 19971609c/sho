package com.bw.sho.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.sho.R;
import com.bw.sho.base.BaseActivity;
import com.bw.sho.sql.SqlDao;
import com.bw.sho.view.SearchBoxView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private SqlDao sqlDao;
    private SearchBoxView searchBox;
    private TagFlowLayout flowLayout;
    private ImageView back;
    private TextView clear;
    private ImageView image;

    @Override
    protected void initData() {
        //回调的值
        searchBox.setOnBackSearchText(new SearchBoxView.OnBackSearchText() {
            @Override
            public void getText(String text) {
                //存数据库
                if (!text.equals("")) {
                    image.setVisibility(View.GONE);
                    flowLayout.setVisibility(View.VISIBLE);
                    sqlDao.add(text);
                    //流逝布局
                    onData();
                    //跳转到显示页面
                    Intent intent = new Intent(SearchActivity.this, DisplayActivity.class);
                    //传值
                    intent.putExtra("text", text);
                    startActivity(intent);
                    //
                    List<String> select = sqlDao.select();
                    finish();
                } else {
                    image.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.GONE);
                    flowLayout.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    protected void initView() {
        ImageView back = findViewById(R.id.s_back);
        clear = findViewById(R.id.s_clear);
        flowLayout = findViewById(R.id.s_flowlayout);
        searchBox = findViewById(R.id.s_search);
        image = findViewById(R.id.di_image);
        //关联数据库
        sqlDao = new SqlDao(this);
        onData();
        //点击事件
        clear.setOnClickListener(this);
        back.setOnClickListener(this);
        //onData调用布局方法
        onData();

        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                List<String> select = sqlDao.select();
                //跳转到显示页面
                Intent intent = new Intent(SearchActivity.this, DisplayActivity.class);
                //传值
                intent.putExtra("text", select.get(position));
                startActivity(intent);
                finish();
                return true;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    //
    private void onData() {
        List<String> select = sqlDao.select();
        //控件的显示与隐藏
        if (select.size() > 0) {
            //显示清空按键
            clear.setVisibility(View.VISIBLE);
        } else {
            //隐藏控件
            clear.setVisibility(View.GONE);
        }
        flowLayout.setAdapter(new TagAdapter<String>(select) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) View.inflate(SearchActivity.this, R.layout.search_button, null);
                tv.setText(s);
                return tv;
            }
        });
    }


    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.s_clear:
                //删除数据
                sqlDao.delete();
                //onData
                onData();
                break;
            case R.id.s_back:
                finish();
                break;
        }
    }
}
