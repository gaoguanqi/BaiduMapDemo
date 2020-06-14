package com.maple.baidu.demo.anim;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.maple.baidu.R;
import com.wang.avi.AVLoadingIndicatorView;

public class MyListeningView extends LinearLayout {
    public MyListeningView(Context context) {
        this(context,null,0);
    }

    public MyListeningView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyListeningView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs,defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_mylistening, this);
        AVLoadingIndicatorView av = v.findViewById(R.id.av);
        av.show();
    }
}
