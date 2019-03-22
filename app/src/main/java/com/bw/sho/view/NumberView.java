package com.bw.sho.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.bw.sho.R;


/**
 * @Auther: 不懂
 * @Date: 2019/3/5 19:38:59
 * @Description:
 */
public class NumberView extends LinearLayout implements View.OnClickListener {
    int sum = 1;
    private Button text;

    public NumberView(Context context) {
        super(context);
    }

    public NumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.number, this);
        //找控件
        Button jia = findViewById(R.id.me_jia);
        text = findViewById(R.id.me_text);
        Button jian = findViewById(R.id.me_jian);
        //设置默认值1
        text.setText(sum + "");
        //点击事件
        jia.setOnClickListener(this);
        jian.setOnClickListener(this);
    }

    public NumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.me_jia:
                sum++;
                text.setText(sum + "");
                //适配器里做刷新
                if (onSumItemClick != null) {
                    onSumItemClick.sumItemClick(sum);
                }
                break;
            case R.id.me_jian:
                //判断不能小于一
                if (sum > 1) {
                    sum--;
                    text.setText(sum + "");
                    //适配器里做刷新
                    if (onSumItemClick != null) {
                        onSumItemClick.sumItemClick(sum);
                    }
                }
                break;
        }
    }

    //接口回调
    public interface OnSumItemClick {
        void sumItemClick(int sum);
    }

    public OnSumItemClick onSumItemClick;

    public void setOnSumItemClick(OnSumItemClick onSumItemClick) {
        this.onSumItemClick = onSumItemClick;
    }
}
