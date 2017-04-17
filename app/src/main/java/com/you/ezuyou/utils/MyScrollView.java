package com.you.ezuyou.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/4/16.
 */

public class MyScrollView extends ScrollView {

    private ScrollView mAlternativeScrollView;

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setAlternativeScrollView(ScrollView alternativeScrollView) {
        mAlternativeScrollView = alternativeScrollView;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mAlternativeScrollView != null) {
            mAlternativeScrollView.scrollTo(l, t);
        }
    }
}
