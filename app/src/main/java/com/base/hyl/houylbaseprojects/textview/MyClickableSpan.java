package com.base.hyl.houylbaseprojects.textview;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.base.hyl.houylbaseprojects.web.WebActivity;

/**
 * Created by yanggang12 on 2018/10/24.
 * 点击字段
 */

public class MyClickableSpan extends ClickableSpan {

    private String content;
    private Context context;

    public MyClickableSpan(String content , Context context) {
        this.content = content;
        this.context = context;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(true);
    }

    @Override
    public void onClick(View widget) {
        Intent intent = new Intent(context,WebActivity.class);
        intent.putExtra("url", content);
        context.startActivity(intent);
    }
}
