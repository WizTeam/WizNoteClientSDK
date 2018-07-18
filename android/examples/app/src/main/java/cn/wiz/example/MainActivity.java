package cn.wiz.example;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.Toast;

import org.devio.takephoto.uitl.TConstant;
import org.json.JSONObject;

import cn.wiz.note.sdk.WizNoteSDK;
import cn.wiz.sdk.api.WizASXmlRpcServer;
import cn.wiz.sdk.api.WizApiUrl;
import cn.wiz.sdk.api.WizAsyncAction;
import cn.wiz.sdk.api.WizSDK;
import cn.wiz.sdk.settings.OEMPreferences;
import cn.wiz.sdk.settings.WizSystemSettings;
import cn.wiz.sdk.util.WizMisc;
import cn.wiz.sdk.util2.ActivityHelper;
import cn.wiz.sdk.util2.HttpURLConnectionUtil;

/**
 * 1.去掉第三方 aar,只需要引入 HWNOTE-(debug/release).aar,
 * 2.引入了so文件,build文件设置参考事例项目build.gradle 中 flavors
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.launch_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                initSDK();
                login("kjq_test@wiz.cn", "111111", null);
            }
        });
    }


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
    WizNoteSDK.WeLinkEventCallback eventCallback = new WizNoteSDK.WeLinkEventCallback() {
        @Override
        public void onEvent(int i, String s) {
            Log.e("welink_event", i + "");
        }
    };
    WizNoteSDK.AuthBodyCallback authBodyCallback = new WizNoteSDK.AuthBodyCallback() {
        @Override
        public String getValidAuthBody() {
            return "aa=hh;bb=xx";
        }
    };
    public void initSDK() {
        try {
            String apiServer = "http://sandbox.wiz.cn";
            String authCode = "ef65f67c1eae1e636a76c951b0f2d2a8s3q8qctr1gm";
            String authType = "huawei";
            String authBody = "123";
            String enterpriseUserId = "anzhen-test2@wiz.cn";
            TConstant.setHuaweiAppContext(getApplicationContext());
            WizNoteSDK.initNoteSDK(getApplication(), apiServer, authCode, authType, authBody,
                    enterpriseUserId, listener, eventCallback, new WizNoteSDK.AuthBodyCallback() {
                        @Override
                        public String getValidAuthBody() {
                            return "hehe";
                        }
                    }, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Activity mActivity = this;
    public void login(final String userId, String password, final String serverAddress) {
        //
        WizSDK.init(this, null, "body", eventCallback, authBodyCallback, null);
        boolean shouldEncryptPassword = OEMPreferences.isEncryptPassword();
        if (shouldEncryptPassword) {
            password = WizMisc.MD5Util.makeMD5Password(password);
        }
        final String finalPassword = password;
        WizAsyncAction.startAsyncAction(null, new WizAsyncAction.WizSimpleAction() {

            String remoteLicence = null;
            String oemInfo = null;
            String privateServerAddress = serverAddress;
            @Override
            public Object work(WizAsyncAction.WizAsyncActionThread thread, Object actionData)
                    throws Exception {
                if (!TextUtils.isEmpty(privateServerAddress) && !URLUtil.isNetworkUrl(privateServerAddress)) {
                    privateServerAddress = "http://" + privateServerAddress;
                }
                // update oem info
                if (!TextUtils.isEmpty(privateServerAddress)) {
                    oemInfo = HttpURLConnectionUtil.getResult(WizApiUrl.getOemURL(privateServerAddress));
                    JSONObject jsonObj = new JSONObject(oemInfo);
                    remoteLicence = jsonObj.getString("licence");
                    String localLicence = OEMPreferences.getLicenceByUserId(userId);
                    if (!TextUtils.isEmpty(localLicence) && !localLicence.equals(remoteLicence)) {
                        // 之前登录过并且是不同服务器
                    }
                }
                // local verify
                if (WizASXmlRpcServer.localClientLogin(mActivity, userId, finalPassword)) {
                    return null;
                }
                // server verify
                WizASXmlRpcServer.clientLogin(mActivity, userId, finalPassword, privateServerAddress);
                return null;
            }

            @Override
            public void onEnd(Object actionData, Object ret) {
                if (!TextUtils.isEmpty(privateServerAddress)) {
                    WizSystemSettings.setServerAddressWithCheck(mActivity, userId, privateServerAddress);
                    //相同服务器或者首次登录
                    OEMPreferences.saveOEMInfo(oemInfo, privateServerAddress);
                    OEMPreferences.saveLicenceWithUserId(remoteLicence, userId, privateServerAddress);
                }
                ActivityHelper.startAccountHomeActivity(mActivity);
            }

            @Override
            public void onException(Object actionData, Exception e) {
                e.printStackTrace();
            }
        });
    }
}
