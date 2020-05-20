package site;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.muzhi.camerasdk.example.R;

import recyclerviewtest4.ImageAdapter1;

public class SiteActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.site_activity);
        WebView webView=(WebView)findViewById(R.id.web_site);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://212.64.48.72");

    }
}
