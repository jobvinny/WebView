package com.tecksolke.changacapital;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainPage extends AppCompatActivity {
    WebView appWebView;
    private ProgressBar spinner;
    FloatingActionButton refreshButton;
    Url url = new Url();

    //@SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        //method to store cookie session and destroy
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().startSync();


        appWebView = findViewById(R.id.appWebView);
        spinner = findViewById(R.id.progressBar);
        refreshButton = findViewById(R.id.refresh);

        //setting button visible to false
        refreshButton.setVisibility(View.GONE);

        //progrees
        spinner.setVisibility(View.GONE);
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#303F9F"), PorterDuff.Mode.MULTIPLY);


        //webview
        WebSettings webSettings = appWebView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
//        appWebView.webSettings.setBuiltInZoomControls(true);
//        appWebView.webSettings.setDisplayZoomControls(true);
//        appWebView.webSettings.setSupportZoom(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        appWebView.clearCache(true);
        appWebView.clearHistory();
        appWebView.requestFocus();
        appWebView.requestFocusFromTouch();
        appWebView.setWebViewClient(new WebViewClient());
        appWebView.setWebChromeClient(new WebChromeClient());

        //method for checking url state loading
        appWebView.setWebViewClient(new WebViewClient() {

            // This method will be triggered when the Page Started Loading

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                refreshButton.setEnabled(false);
                spinner.setVisibility(View.VISIBLE);
                Toast toast = Toast.makeText(MainPage.this,
                        "Loading...", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }

            // This method will be triggered when the Page loading is completed

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                refreshButton.setEnabled(true);
                refreshButton.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.GONE);
            }

            // This method will be triggered when error page appear

            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // You can redirect to your own page instead getting the default
                // error page
                refreshButton.setVisibility(View.GONE);
                Toast.makeText(MainPage.this,
                        "Sorry A Network Error Occurred", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainPage.this, Error404.class));
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        appWebView.loadUrl(url.appUrl);
        appWebView.loadUrl("javascript:fn()");

//        //activate downloading
//        appWebView.setDownloadListener(new DownloadListener() {
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
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appWebView.loadUrl(url.appUrl);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (appWebView.canGoBack()) {
            appWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
