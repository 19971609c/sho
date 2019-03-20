package com.bw.sho.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.bw.sho.R;

/**
 * @Auther: 不懂
 * @Date: 2019/3/14 11:33:55
 * @Description:
 */
public class SearchBoxView extends LinearLayout implements TextWatcher, View.OnClickListener {

    private Button search;
    private EditText text;
    private Button clear;

    public SearchBoxView(Context context) {
        super(context);
    }

    public SearchBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //加载布局文件
        LayoutInflater.from(context).inflate(R.layout.search_box_view, this, true);
        //找出控件
        search = findViewById(R.id.search_search);
        text = findViewById(R.id.search_text);
        clear = findViewById(R.id.search_clear);
        //默认隐藏清空按钮
        clear.setVisibility(GONE);
        //文本框改变事件
        text.addTextChangedListener(this);
        text.setOnClickListener(this);
        clear.setOnClickListener(this);
        //搜索按钮点击事件
        search.setOnClickListener(this);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //获取输入框的值
        String input = text.getText().toString();
        //判断是否为空
        if (TextUtils.isEmpty(input)) {
            clear.setVisibility(GONE);
            //显示光标
            text.setCursorVisible(false);
        } else {
            clear.setVisibility(VISIBLE);
            //隐藏光标
            text.setCursorVisible(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_search:
                //获取EditText值
                String texts = text.getText().toString().trim();
                //返回EditText值
                if (!TextUtils.isEmpty(texts)) {
                    if (onBackSearchText != null) {
                        onBackSearchText.getText(texts);
                    }
                }
                break;
            case R.id.search_text:
                if (onIntent != null) {
                    onIntent.onintent();
                }
                break;
            case R.id.search_clear:
                text.setText("");
                break;
        }
    }

    //接口回调回传值
    public interface OnBackSearchText {
        void getText(String text);
    }

    public OnBackSearchText onBackSearchText;

    public void setOnBackSearchText(OnBackSearchText onBackSearchText) {
        this.onBackSearchText = onBackSearchText;
    }

    //回调跳转
    public interface OnIntent {
        void onintent();
    }

    public OnIntent onIntent;

    public void setOnIntent(OnIntent onIntent) {
        this.onIntent = onIntent;
    }
}
