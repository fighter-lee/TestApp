package top.fighter_lee.testapp.ui.fragment;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import top.fighter_lee.testapp.R;
import top.fighter_lee.testapp.base.BaseFragment;
import top.fighter_lee.testapp.callback.ErrorCallback;
import top.fighter_lee.testapp.callback.LoadingCallback;

/**
 * @author fighter_lee
 * @date 2017/10/31
 */
public class PageFragment extends BaseFragment {
    private static final String TAG = "PageFragment";
    @BindView(R.id.wv_web)
    public WebView wvWeb;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    wvWeb.goBack();
                }
                break;
            }
        }
    };

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void init() {
        setWebView();
        loadWebView("http://m.kaka123.net/index.html");
    }

    private void loadWebView(final String url) {
        //加载需要显示的网页
        //设置Web视图
        Log.d(TAG, "loadWebView: "+url);
        wvWeb.loadUrl(url);
    }

    private void setWebView() {
        WebSettings settings = wvWeb.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        settings.setJavaScriptEnabled(true);
        //设置可以访问文件
        settings.setAllowFileAccess(true);
        //设置支持缩放
        settings.setBuiltInZoomControls(true);
        // 网页内容的宽度是否可大于mWebView控件的宽度
        settings.setLoadWithOverviewMode(false);
        // 保存表单数据
        settings.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        // 启动应用缓存
        settings.setAppCacheEnabled(true);
        // 设置缓存模式
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        settings.setUseWideViewPort(true);
        // 缩放比例 1
        wvWeb.setInitialScale(1);
        // 告诉mWebView启用JavaScript执行。默认的是false。
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        //  页面加载好以后，再放开图片
        settings.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        settings.setDomStorageEnabled(true);
        // 排版适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // mWebView是否支持多个窗口。
        settings.setSupportMultipleWindows(true);

        // mWebView从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        wvWeb.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.d(TAG, "onProgressChanged: " + newProgress);
                if (newProgress < 60) {
                    progressbar.setVisibility(View.VISIBLE);
                    progressbar.setAlpha(1f);
                } else if (newProgress == 100) {
                    mBaseLoadService.showSuccess();
                    progressbar.setVisibility(View.GONE);
                }
                else if (newProgress >= 60) {
                    progressbar.setAlpha((100 - newProgress) * 0.025f);
                    mBaseLoadService.showSuccess();
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        wvWeb.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && wvWeb.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    return true;
                }
                return false;
            }

        });

        wvWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d(TAG, "shouldOverrideUrlLoading: ");
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d(TAG, "onPageStarted: ");
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Log.d(TAG, "onReceivedError: ");
                mBaseLoadService.showCallback(ErrorCallback.class);
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.d(TAG, "onReceivedError: ");
                mBaseLoadService.showCallback(ErrorCallback.class);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                Log.d(TAG, "onReceivedHttpError: ");
                mBaseLoadService.showCallback(ErrorCallback.class);
                super.onReceivedHttpError(view, request, errorResponse);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG, "onPageFinished: ");
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    protected void onNetReload() {
        Log.d(TAG, "onNetReload: ");
        mBaseLoadService.showCallback(LoadingCallback.class);
        wvWeb.clearCache(true);
        wvWeb.reload();
    }

}
