package cn.wiz.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.jph.takephoto.uitl.TConstant;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import cn.wiz.note.sdk.WizNoteSDK;
import cn.wiz.sdk.util2.HttpURLConnectionUtil;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.launch_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSDK();
            }
        });
    }

    public void initSDK() {
        try {
            String apiServer = "http://sandbox.wiz.cn";
            String authCode = "ef65f67c1eae1e636a76c951b0f2d2a8wmvfpxdr8wc";
            String authType = "huawei";
            String authBody = "123";
            String enterpriseUserId = "anzhen-test2@wiz.cn";
            WizNoteSDK.InitListener listener = new WizNoteSDK.InitListener() {
                @Override
                public void onStart() {
                }

                @Override
                public void onSuccess() {
                    Button launchBtn = (Button) findViewById(R.id.sdk_status);
                    launchBtn.setEnabled(true);
                    launchBtn.setText("打开笔记");
                    launchBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WizNoteSDK.startNoteHomePage(MainActivity.this);
                        }
                    });
                }

                @Override
                public void onError(String s) {
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                }
            };
            WizNoteSDK.initNoteSDK(getApplication(), apiServer, authCode, authType, authBody, enterpriseUserId, listener);
            /**
             * 设置 takephoto fileprovider prefix
             * 暂时在外部调用,测试没有问题后再移动到 initNoteSDK 方法中
             */
            TConstant.initTConstant(getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
