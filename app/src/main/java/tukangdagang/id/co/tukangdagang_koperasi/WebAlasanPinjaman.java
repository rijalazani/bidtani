package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import tukangdagang.id.co.tukangdagang_koperasi.app.WebInterface;

public class WebAlasanPinjaman extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout swipeRefreshLayout;
    private WebView view;
    private ProgressBar loading; //Menambahkan widget ProgressBar
    String Nalasan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_alasan_pinjaman);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Alasan");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Nalasan = intent.getExtras().getString("alasan");
        view = (WebView) this.findViewById(R.id.webview);
        loading = findViewById(R.id.progress);
        assert view != null;
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(WebAlasanPinjaman.this);
        settings();
        load_website();
    }
    private void settings(){
//        WebSettings web_settings = view.getSettings();
//        web_settings.setJavaScriptEnabled(true);
//        web_settings.setAllowContentAccess(true);
//        web_settings.setUseWideViewPort(true);
//        web_settings.setLoadsImagesAutomatically(true);
//        web_settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        web_settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        web_settings.setEnableSmoothTransition(true);
//        web_settings.setDomStorageEnabled(true);
        view.getSettings().setAppCachePath( getApplicationContext().getCacheDir().getAbsolutePath() );
        view.getSettings().setAllowFileAccess( true );
        view.getSettings().setAppCacheEnabled( true );
        view.getSettings().setJavaScriptEnabled( true );
        view.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT ); // load online by default
        view.addJavascriptInterface(new WebInterface(this), "Android");
    }

    private void load_website(){
        if(Build.VERSION.SDK_INT >= 19){
            view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }else{
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        //Tambahkan WebChromeClient
        view.setWebChromeClient(new WebChromeClient(){
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                view.loadUrl("file:///android_asset/lihat2.html");
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //ProgressBar akan Terlihat saat Halaman Dimuat
                loading.setVisibility(View.VISIBLE);
                loading.setProgress(newProgress);
                if(newProgress == 100){
                    //ProgressBar akan Menghilang setelah Valuenya mencapat 100%
                    loading.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        view.setWebViewClient(new WebViewClient(){
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                view.loadUrl("file:///android_asset/lihat2.html");
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.toString());
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //ProgressBar akan Menghilang setelah Halaman Selesai Dimuat
                super.onPageFinished(view, url);
                loading.setVisibility(View.GONE);
            }
        });

        view.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        view.loadUrl(Nalasan);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onRefresh() {

        settings();
        load_website();

    }
}
