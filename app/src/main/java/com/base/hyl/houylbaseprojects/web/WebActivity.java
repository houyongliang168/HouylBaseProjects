package com.base.hyl.houylbaseprojects.web;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.base.common.base.BaseActivity;
import com.base.hyl.houylbaseprojects.R;

import static com.base.hyl.houylbaseprojects.Constant.WEB_URL;

public class WebActivity extends BaseActivity {

    private WebView mWebView;
    private String url;//获取网络地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent = getIntent();
        url = intent.getStringExtra(WEB_URL);
        initView();
    }

    private void initView() {
        mWebView = generateFindViewById(R.id.webV);
        mWebView.setBackgroundColor(Color.parseColor("#ffffff"));
        // 开启浏览器的javascript脚本支持
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(url);
    }
}
