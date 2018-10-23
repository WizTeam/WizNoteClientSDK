package cn.wiz.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;

public class TestWebViewActivity extends AppCompatActivity {

    private WebView testWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web_view);
        testWebView = (WebView) findViewById(R.id.webview);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(testWebView != null) {
            try {
                if (testWebView.getParent() != null) {
                    ((ViewGroup) testWebView.getParent()).removeView(testWebView);
                }
                testWebView.destroy();
            } catch (Exception e) {
                //LogUtils.e(e);
            } finally {
                testWebView = null;
            }
        }

    }
}
