//package com.example.danpingji.ui;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.danpingji.R;
//import com.example.danpingji.view.X5WebView;
//import com.tencent.smtt.sdk.WebSettings;
//import com.tencent.smtt.sdk.WebView;
//import com.tencent.smtt.sdk.WebViewClient;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//
//public class ChaXunActivity extends Activity {
//    private X5WebView webView;
//    private TextView title;
//    private ImageView famhui;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cha_xun);
//        webView= (X5WebView) findViewById(R.id.webwiew);
//
//        title= (TextView) findViewById(R.id.title);
//        title.setText("比对记录");
//        famhui= (ImageView) findViewById(R.id.leftim);
//        famhui.setVisibility(View.VISIBLE);
//        famhui.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        WebSettings webSetting = webView.getSettings();
//        webSetting.setAllowFileAccess(true);
//        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webSetting.setSupportZoom(true);
//        webSetting.setBuiltInZoomControls(true);
//        webSetting.setUseWideViewPort(true);
//        webSetting.setSupportMultipleWindows(false);
//        // webSetting.setLoadWithOverviewMode(true);
//        webSetting.setAppCacheEnabled(true);
//        // webSetting.setDatabaseEnabled(true);
//        webSetting.setDomStorageEnabled(true);
//        webSetting.setJavaScriptEnabled(true);
//        webSetting.setGeolocationEnabled(true);
//        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
//        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
//        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
//        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
//                .getPath());
//        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
//        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
//
//        webView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//                return false;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//
//            }
//        });
//        String str ="系统管理员"; //默认环境，已是UTF-8编码
//        try {
//            String strGBK = URLEncoder.encode(str,"UTF-8");
//            System.out.println(strGBK);
//        //    http://192.168.2.101:8081/Police/ipad.html?accountName=%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86%E5%91%98
////            String strUTF8 = URLDecoder.decode(str, "UTF-8");
////            System.out.println(strUTF8);
//            webView.loadUrl("http://192.168.2.101:8081/Police/ipad.html?accountName="+strGBK);
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//}
