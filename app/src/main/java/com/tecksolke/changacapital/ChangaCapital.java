package com.tecksolke.changacapital;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ChangaCapital extends AppCompatActivity {

    //String url = "https://changacapital.com/androidLogin";
    String url = "https://ecomatt.co/";
    WebView changacapitalview;
    private ProgressBar spinner;
    FloatingActionButton brefresh;

    //@SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changa_capital);

        //method to store cookie session and destroy
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().startSync();


        changacapitalview = findViewById(R.id.changawebview);
        spinner = findViewById(R.id.progressBar);
        brefresh = findViewById(R.id.refresh);

        //setting button visible to false
        brefresh.setVisibility(View.GONE);

        //progrees
        spinner.setVisibility(View.GONE);
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#303F9F"), PorterDuff.Mode.MULTIPLY);


        //webview
        WebSettings webSettings = changacapitalview.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
//        changacapitalview.webSettings.setBuiltInZoomControls(true);
//        changacapitalview.webSettings.setDisplayZoomControls(true);
//        changacapitalview.webSettings.setSupportZoom(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        changacapitalview.clearCache(true);
        changacapitalview.clearHistory();
        changacapitalview.requestFocus();
        changacapitalview.requestFocusFromTouch();
        changacapitalview.setWebViewClient(new WebViewClient());
        changacapitalview.setWebChromeClient(new WebChromeClient());

        //method for checking url state loading
        changacapitalview.setWebViewClient(new WebViewClient() {

            // This method will be triggered when the Page Started Loading

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                brefresh.setEnabled(false);
                spinner.setVisibility(View.VISIBLE);
               Toast toast =  Toast.makeText(ChangaCapital.this,
                        "Loading...", Toast.LENGTH_LONG);
               toast.setGravity(Gravity.CENTER,0,0);
               toast.show();
            }

            // This method will be triggered when the Page loading is completed

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                brefresh.setEnabled(true);
                brefresh.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.GONE);
            }

            // This method will be triggered when error page appear

            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // You can redirect to your own page instead getting the default
                // error page
                brefresh.setVisibility(View.GONE);
                Toast.makeText(ChangaCapital.this,
                        "Sorry A Network Error Occurred", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ChangaCapital.this,chama404.class));
               super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        changacapitalview.loadUrl(url);
        changacapitalview.loadUrl("javascript:fn()");

//        //activate downloading
//        changacapitalview.setDownloadListener(new DownloadListener() {
//            @Override
//            public void onDownloadStart(String url, String userAgent,
//                                        String contentDisposition, String mimeType,
//                                        long contentLength) {
//                DownloadManager.Request request = new DownloadManager.Request(
//                        Uri.parse(url));
//                request.setMimeType(mimeType);
//                String cookies = CookieManager.getInstance().getCookie(url);
//                request.addRequestHeader("cookie", cookies);
//                request.addRequestHeader("User-Agent", userAgent);
//                request.setDescription("Downloading file...");
//                request.setTitle(URLUtil.guessFileName(url, contentDisposition,
//                        mimeType));
//                request.allowScanningByMediaScanner();
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                request.setDestinationInExternalPublicDir(
//                        Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(
//                                url, contentDisposition, mimeType));
//                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//                dm.enqueue(request);
//                Toast.makeText(getApplicationContext(), "Downloading File",
//                        Toast.LENGTH_LONG).show();
//            }
//        });

        //refreshpage
        brefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changacapitalview.loadUrl(url);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (changacapitalview.canGoBack()) {
            changacapitalview.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
