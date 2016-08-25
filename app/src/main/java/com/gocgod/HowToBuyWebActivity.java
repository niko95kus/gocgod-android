package com.gocgod;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import carbon.widget.ProgressBar;

public class HowToBuyWebActivity extends AppCompatActivity {
    @BindView(R.id.progress)
    ProgressBar loadingWeb;
    @BindView(R.id.license_layout)
    WebView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_buy_web);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        String my_url = bundle.getString("my_url");
        String my_title = bundle.getString("my_title");
        loadingWeb.setVisibility(View.VISIBLE);

        //Build Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(my_title);

        view.loadUrl(my_url);
        view.setVisibility(View.VISIBLE);
        view.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                loadingWeb.setVisibility(View.GONE);
            }
        });
        WebSettings webSettings = view.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
