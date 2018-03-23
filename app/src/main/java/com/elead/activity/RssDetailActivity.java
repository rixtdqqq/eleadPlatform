package com.elead.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.elead.eplatform.R;

public class RssDetailActivity extends BaseActivity {

    WebView webview;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_detail_layout);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        String url = getIntent().getStringExtra("urlDetail");
        webview = (WebView) findViewById(R.id.webview);
        webview.setWebChromeClient(new WebChromeClient());
        //希望点击链接继续在当前browser中响应，必须覆盖 WebViewClient对象
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(url);

        webview.setWebChromeClient(new WebChromeClient() {//监听网页加载
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    progressBar.setVisibility(View.GONE);
                } else {
                    // 加载中
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });


    }

}
