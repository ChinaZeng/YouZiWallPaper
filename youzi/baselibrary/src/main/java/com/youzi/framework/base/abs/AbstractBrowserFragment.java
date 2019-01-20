package com.youzi.framework.base.abs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youzi.framework.base.BaseFragment;
import com.youzi.framework.base.R;
import com.youzi.framework.common.Config;
import com.youzi.framework.common.ui.ILoadingHelper;
import com.youzi.framework.common.ui.dialog.i.IAlertDialog;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import okhttp3.HttpUrl;

/**
 * Created by LuoHaifeng on 2018/4/16 0016.
 * Email:496349136@qq.com
 */
public abstract class AbstractBrowserFragment extends BaseFragment {
    private WebView webView;

    @Override
    protected View provideLayoutView() {
        return View.inflate(getContext(), R.layout.base_fragment_browser, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = Objects.requireNonNull(getView()).findViewById(R.id.wv_browser);
        initWebView(webView);
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void initWebView(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.setDisplayZoomControls(false);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setSaveFormData(true);
        settings.setGeolocationEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);//是否使用缓存
        //初始化JavaScriptInterfaces
        Map<String, Object> interfaces = provideJavaScriptInterfaces();
        if (interfaces != null) {
            Set<String> keys = interfaces.keySet();
            for (String key : keys) {
                Object value = interfaces.get(key);
                webView.addJavascriptInterface(value, key);
            }
        }

        webView.setWebChromeClient(new WebChromeClient(this));
        webView.setWebViewClient(new WebViewClient(this));
        webView.setDownloadListener((url, userAgent, contentDisposition, mimeType, contentLength) -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                if (getContext() != null) {
                    PackageManager packageManager = getContext().getPackageManager();
                    List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
                    boolean isValid = !activities.isEmpty();
                    if (isValid) {
                        startActivity(intent);
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Toast.makeText(getContext(), "没有支持的下载管理器", Toast.LENGTH_SHORT).show();
        });
    }

    protected Map<String, Object> provideJavaScriptInterfaces() {
        return null;
    }

    protected void webViewLoadingProgressChanged(int newProgress) {

    }

    protected void webViewReceivedTitle(String title) {

    }

    protected void webViewPageStarted() {
        provideLoadingHelper().showLoading();
    }

    protected void webViewPageFinished() {
        provideLoadingHelper().restore();
    }

    protected boolean webViewOnReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        return false;
    }

    @Override
    public ILoadingHelper provideLoadingHelper() {
        return Config.getUiProvider().provideLoadingHelper(webView);
    }

    protected boolean webViewShouldOverrideUrlLoading(WebView webView, String url, boolean superResult) {
        try {
            HttpUrl httpUrl = HttpUrl.parse(url);
            if (httpUrl != null) {
                String scheme = httpUrl.scheme();
                if (scheme != null) {
                    if (scheme.equals("http") || scheme.equals("https")) {
                        return superResult;
                    }
                }
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            PackageManager packageManager = webView.getContext().getPackageManager();
            List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
            boolean isValid = !activities.isEmpty();
            if (isValid) {
                IAlertDialog alertDialog = mUiProvider.newAlertDialog(webView.getContext());
                alertDialog.setTitle("提示");
                alertDialog.setContent("网页请求打开外部应用，是否允许?");
                alertDialog.setNegativeButton("拒绝", v -> alertDialog.dismiss());
                alertDialog.setPositiveButton("允许", v -> {
                    alertDialog.dismiss();
                    startActivity(intent);
                });
                alertDialog.show();
            }
            return true;
        } catch (Exception ignored) {
        }

        return superResult;
    }

    public void load(String url) {
        webView.loadUrl(url);
    }

    public WebView getWebView() {
        return webView;
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        webView.destroy();
    }

    public static class WebChromeClient extends com.tencent.smtt.sdk.WebChromeClient {
        AbstractBrowserFragment fragment;

        public WebChromeClient(AbstractBrowserFragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            if (fragment != null) {
                fragment.webViewLoadingProgressChanged(i);
            }
        }

        @Override
        public void onReceivedTitle(WebView webView, String s) {
            super.onReceivedTitle(webView, s);
            if (fragment != null) {
                fragment.webViewReceivedTitle(s);
            }
        }
    }

    public static class WebViewClient extends com.tencent.smtt.sdk.WebViewClient {
        AbstractBrowserFragment fragment;

        public WebViewClient(AbstractBrowserFragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            if (fragment == null) {
                return super.shouldOverrideUrlLoading(webView, s);
            }
            return fragment.webViewShouldOverrideUrlLoading(webView, s, super.shouldOverrideUrlLoading(webView, s));
        }

        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
            if (fragment != null) {
                fragment.webViewPageStarted();
            }
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
            if (fragment != null) {
                fragment.webViewPageFinished();
            }
        }

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            if (fragment == null || !fragment.webViewOnReceivedSslError(webView, sslErrorHandler, sslError)) {
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
            }
        }
    }
}
