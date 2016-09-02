package com.gocgod.ui.optional;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gocgod.ui.Global;

/**
 * Created by Aurel on 24/08/2016.
 */
public class FaqWebClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url){
        if(Uri.parse(url).getHost().endsWith(Global.IP + "gocgod/public/faq_data")){
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity(intent);
        return true;
    }
}
