package com.gocgod;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

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

        WebView webView_faq = (WebView) findViewById(R.id.webview_faq);
        webView_faq.loadUrl(Global.IP + "gocgod/public/faq_data");
    }
}
