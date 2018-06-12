package com.ryzhang.mobileweb.app;


import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ryzhang.library.utils.dependent.view.annotation.ContentView;
import com.ryzhang.library.utils.dependent.view.annotation.ViewInject;
import com.ryzhang.mobileweb.R;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.wv_main)
    private WebView webView;

    @Override
    protected void loadData() {
//        setWebView();
    }

    @Override
    protected void initListener() {

    }

    @SuppressLint("AddJavascriptInterface")
    private void setWebView(String webUrl) {
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setDisplayZoomControls(false); //隐藏原生的缩放控件//其他细节操作
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSetting.setGeolocationDatabasePath(context.getDir("geolocation", 0).getPath());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("response", url);
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();  //接受所有证书
            }
        });
        //WebView 默认自动加载url TIELE
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
        webView.loadUrl(webUrl);
    }
}
