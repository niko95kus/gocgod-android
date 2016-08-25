package com.gocgod;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FaqActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("FAQ");
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        buildToolbar("FAQ");
        buildDrawer(savedInstanceState, toolbar);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);*/

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;

        WebView webView_faq = (WebView) findViewById(R.id.webview_faq);
        WebSettings webSettings = webView_faq.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        webView_faq.loadUrl(Global.IP + "gocgod/public/faq_data");

        // Stop local links and redirects from opening in browser instead of WebView
        webView_faq.setWebViewClient(new FaqWebClient());
    }
}
