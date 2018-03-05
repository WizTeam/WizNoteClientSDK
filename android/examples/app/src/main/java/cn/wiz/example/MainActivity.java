package cn.wiz.example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.wiz.note.AccountHomeActivity;
import cn.wiz.note.sdk.WizNoteSDK;

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
            String authCode = "ef65f67c1eae1e636a76c951b0f2d2a88hnyrgsfntl";
            String authType = "huawei";
            String enterpriseUserId = "anzhen-test2@wiz.cn";
            WizNoteSDK.InitListener listener = new WizNoteSDK.InitListener() {
                @Override
                public void onSuccess() {
                    Button launchBtn = findViewById(R.id.sdk_status);
                    launchBtn.setEnabled(true);
                    launchBtn.setText("打开笔记");
                    launchBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, AccountHomeActivity.class);
                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void onError(String s) {
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                }
            };
            WizNoteSDK.initNoteSDK(getApplication(), apiServer, authCode, authType, enterpriseUserId, listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
