package top.fighter_lee.testapp.ui.fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.just.library.AgentWeb;
import com.just.library.AgentWebSettings;
import com.just.library.WebDefaultSettingsManager;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import top.fighter_lee.testapp.R;
import top.fighter_lee.testapp.base.WebviewFragment;
import top.fighter_lee.testapp.callback.ErrorCallback;
import top.fighter_lee.testapp.callback.LoadingCallback;
import top.fighter_lee.testapp.info.MSG;
import top.fighter_lee.testapp.inter.WebviewBackListener;
import top.fighter_lee.testapp.ui.activity.HomeActivity;
import top.fighter_lee.testapp.utils.Trace;


public class HomeFragment extends WebviewFragment {
    private static final String TAG = "HomeFragment";
    protected AgentWeb.PreAgentWeb mAgentWeb;
    @BindView(R.id.rl_webview)
    RelativeLayout rlWebview;
    private AgentWeb go;
    public static final String KEY_WEB_URL = "KEY_WEB_URL";
    public static final String KEY_PAGE_NUM = "key_page_num";


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void init(View view) {
        setWebView(view);
        request();
    }

    private void request() {

        BmobQuery<MSG> bmobQuery = new BmobQuery<MSG>();
        bmobQuery.getObject("s69JAAAG", new QueryListener<MSG>() {
            @Override
            public void done(MSG object, BmobException e) {
                if(e==null){
                    if (object.getIsshow() && !TextUtils.isEmpty(object.getWeb()) && !TextUtils.isEmpty(object.getPay())){
                        Trace.d(TAG, "done() "+HomeFragment.this.getArguments().getInt(KEY_PAGE_NUM));
                        switch (HomeFragment.this.getArguments().getInt(KEY_PAGE_NUM)){
                            case 1:
                                loadWebView(object.getWeb());
                                break;
                            case 2:
                                loadWebView(object.getPay());
                                break;
                        }
                    }else{
                        loadWebView(HomeFragment.this.getArguments().getString(KEY_WEB_URL));
                    }
                }else{
                    showErrorUI();
                }
            }
        });
//        Network.getNetApi()
//                .getCaiInfo()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<CaiInfo>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(CaiInfo caiInfo) {
//                        showSuccessUI();
//                        if ("1".equals(caiInfo.getData().getShow_url()) && !TextUtils.isEmpty(caiInfo.getData().getUrl())) {
//                            loadWebView(caiInfo.getData().getUrl());
////                            loadWebView("http://www.baidu.com/");
//                        } else {
//                            loadWebView("http://www.500.com/");
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Trace.d(TAG, "onError() ");
//                        showErrorUI();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                });

    }

    private void loadWebView(final String url) {
        //加载需要显示的网页
        //设置Web视图
        Trace.d(TAG, "loadWebView() url:"+url);
        go = mAgentWeb.go(url);
        go.getWebCreator().get().setOverScrollMode(WebView.OVER_SCROLL_NEVER);
    }

    private void setWebView(View view) {

        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(rlWebview, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//传入AgentWeb的父控件
                .setIndicatorColorWithHeight(-1, 2)//设置进度条颜色与高度-1为默认值，2单位为dp
                .setAgentWebWebSettings(getSettings())//设置 AgentWebSettings
                .setWebViewClient(mWebViewClient)//WebViewClient ， 与WebView 一样
                .setWebChromeClient(mWebChromeClient) //WebChromeClient
//                .setSecurityType(AgentWeb.SecurityType.strict) //严格模式
                .openParallelDownload()//打开并行下载 , 默认串行下载
                .setNotifyIcon(R.mipmap.download)
                .createAgentWeb()//创建AgentWeb
                .ready();//设置 WebSettings
        ((HomeActivity)getActivity()).setWebviewListener(new WebviewBackListener() {
            @Override
            public void pressRefresh() {
                onNetReload();
            }
        });
    }

    @Override
    protected void onNetReload() {
        Log.d(TAG, "onNetReload: ");
        showLoadingUI();
        request();
    }

    private void showLoadingUI() {
        Trace.d(TAG, "showLoadingUI() ");
        rlWebview.setVisibility(View.INVISIBLE);
        mBaseLoadService.showCallback(LoadingCallback.class);
    }

    private void showSuccessUI() {
        Trace.d(TAG, "showSuccessUI() ");
        rlWebview.setVisibility(View.VISIBLE);
        mBaseLoadService.showSuccess();
    }

    private void showErrorUI() {
        Trace.d(TAG, "showErrorUI() ");
        rlWebview.setVisibility(View.INVISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBaseLoadService.showCallback(ErrorCallback.class);
            }
        },500);

    }

    WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Trace.d(TAG, "onProgressChanged() "+newProgress);
            if (newProgress < 60) {
            } else if (newProgress == 100) {
                showSuccessUI();
            } else if (newProgress >= 60) {
                showSuccessUI();
            }
            super.onProgressChanged(view, newProgress);
        }
    };

    WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Trace.d(TAG, "onReceivedError() 1");
            showErrorUI();
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Trace.d(TAG, "onReceivedError() 2");
            showErrorUI();
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    };

    public AgentWebSettings getSettings() {
        return WebDefaultSettingsManager.getInstance();
    }

    @Override
    public boolean onFragmentKeyDown(int keyCode, KeyEvent event) {
        if (go != null) {
            return go.handleKeyEvent(keyCode, event);
        } else {
            return false;
        }
    }

    @Override
    public void onResume() {
        if (go != null) {
            go.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (go != null) {
            go.getWebLifeCycle().onPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        if (go != null) {
            go.getWebLifeCycle().onDestroy();
        }
        super.onDestroyView();
    }

}
