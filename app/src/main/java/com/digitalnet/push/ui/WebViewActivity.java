package com.digitalnet.push.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digitalnet.push.R;
import com.digitalnet.push.base.BaseActivity;
import com.digitalnet.push.base.BaseApplication;
import com.digitalnet.push.share.SharedPrefHelper;
import com.digitalnet.push.util.StatusBarUtil;

/***
 * create   wzx  2017/3/1
 * 与h5交互的webivew 协议页面
 *
 * 注意：url和title字段是必传的，
 * 如果不传title默认是现花钱
 * 如果不传irl默认是现花钱官网
 */
public class WebViewActivity extends  BaseActivity {
    WebView     mWebView;
    WebSettings mWebSettings;
    ProgressBar mProgressBar;
    String wyUrl ="http://123.126.109.195:8048/dme_mobile/Home/Main.aspx?loginname=" + SharedPrefHelper.getInstance().getLoginName();
    ;   //润农官网
    private boolean isOk = true;
    String url;
    private LinearLayout mNoData;
    private ImageView    mImgvNetError;
    private Bundle       mBundle;
    private String  title      = BaseApplication.mBaseApplication.getResources().getString(R.string.app_name);
    private Handler mHandler   =new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:

                    break;

            }
            return true;
        }
    });
    private boolean reload     =true;//第一次进入错误的话，再重载一次
    private boolean clearCache =false;//进如界面传的清除缓存标志
    private android.widget.RelativeLayout mLayoutLeft;
    private TextView mTvTitle;


    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initData() {
        StatusBarUtil.setColor(this,R.color.text_9 );
        if (getIntent().getExtras() != null) {
            mBundle = getIntent().getExtras();
            if (mBundle.getString("url") != null) {
                url = mBundle.getString("url",wyUrl);
            } else {
                url = wyUrl;
            }
            if (mBundle.getString("title")!=null) {
                title=mBundle.getString("title",title);
            }
            clearCache=mBundle.getBoolean("clearCache",false);
        } else {
            url = wyUrl;
        }
        initView();
        mTvTitle.setText(title);
                  mWebView = (WebView) findViewById(R.id.wb);
        mProgressBar = (ProgressBar) findViewById(R.id.pb);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view,url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
                    //图片自动缩放 打开
                    mWebView.getSettings().setLoadsImagesAutomatically(true);
                }
                mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                mHandler.sendEmptyMessageDelayed(1,200);

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String
                      failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                if (reload) {
                    reload=false;
                    mWebView.reload();
                }else {
                    mWebView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
                // super.onReceivedSslError(view, handler, error);
                // 接受所有网站的证书，忽略SSL错误，执行访问网页
                handler.proceed();

                if (reload) {
                    reload=false;
                    mWebView.reload();
                }else {
                    mWebView.setVisibility(View.GONE);
                }

            }
        });

        //可以控制进度，标题,图标
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress < 10) {
                    mProgressBar.setProgress(10);
                } else if (newProgress < 20) {
                    mProgressBar.setProgress(20);
                } else if (newProgress < 30) {
                    mProgressBar.setProgress(30);
                } else if (newProgress < 40) {
                    mProgressBar.setProgress(40);
                } else if (newProgress < 50) {
                    mProgressBar.setProgress(50);
                } else if (newProgress < 60) {
                    mProgressBar.setProgress(60);
                } else if (newProgress < 70) {
                    mProgressBar.setProgress(70);
                } else if (newProgress < 80) {
                    mProgressBar.setProgress(80);
                } else if (newProgress < 100) {
                    mProgressBar.setProgress(newProgress);
                }else {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });
        mWebView.addJavascriptInterface(new CallJS(this), "android");
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//软件解码
        }
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);//硬件解码
        //设置下载监听
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, String paramAnonymousString4, long paramAnonymousLong) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(paramAnonymousString1));
                startActivity(intent);
            }
        });

        mWebSettings = mWebView.getSettings();
        //webview在安卓5.0之前默认允许其加载混合网络协议内容
        // 在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //通过webview配置webview.getSettings().setTextZoom(100)就可以禁止缩放，按照百分百显示。
        mWebSettings.setTextZoom(100);

        //支持javascript脚本
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setSavePassword(true);//保存密码
        mWebSettings.setDomStorageEnabled(true);//是否开启本地DOM存储  鉴于它的安全特性（任何人都能读取到它，尽管有相应的限制，将敏感数据存储在这里依然不是明智之举），Android 默认是关闭该功能的。
        mWebSettings.setGeolocationEnabled(true);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //添加webview和js交互
        //不使用本地缓存
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //是否支持缩放
        mWebSettings.setSupportZoom(false);

        mWebSettings.setAppCacheEnabled(true);


        if (Build.VERSION.SDK_INT >= 19) {
            mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//加载缓存否则网络
        }

        if (Build.VERSION.SDK_INT >= 19) {
            mWebSettings.setLoadsImagesAutomatically(true);//图片自动缩放 打开
        } else {
            mWebSettings.setLoadsImagesAutomatically(false);//图片自动缩放 关闭
        }
        //当有表单需要处理时记得写上这2句
        mWebView.requestFocus();
        mWebView.requestFocusFromTouch();

        //让webview在自己本身呈现网页，而不是外部浏览器
    }


    @Override
    protected void onPause() {
        mWebView.onPause();
        super.onPause();
    }



    @Override
    protected void onResume() {
        mWebView.onResume();
        super.onResume();
        //设置webview加载url
        mWebView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (clearCache) {
            mWebView.clearCache(true);
            mWebView.clearHistory();
        }
        mWebView.destroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    private void initView() {
        mLayoutLeft = (RelativeLayout) findViewById(R.id.layout_left);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mLayoutLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    //    @Override
//    public void setOverScrollMode(int mode) {
//        super.setOverScrollMode(mode);
//        AutoSize.autoConvertDensityOfGlobal(Activity);
//    }

    class CallJS {
        private Activity mContent;

        public CallJS(Activity content) {
            mContent = content;
        }
        //API 17后，需要被js调用的方法加上这个注解，确保数据安全
        @JavascriptInterface
        public void finish(){
            //认证成功后把集合中当前类名所代表的str删除，详见HawkConstans.startActivity（）里key的值
//        HawkConstans.ActivityListRemove("5");
//        HawkConstans.startActivity(HawkConstans.getActivityList(),mContent);
            mContent.finish();
        }
        @JavascriptInterface
        public void finish(int num){
            mContent.finish();
        }
    }
}
