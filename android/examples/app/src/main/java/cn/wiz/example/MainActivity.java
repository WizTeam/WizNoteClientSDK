package cn.wiz.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ListView;

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
////////////////更新
/**
 * 1. HWListViewHelper.getListView 接口，创建 ListView 实例用到的 Context 由 SDK 传入
 * 2. WizNoteSDK.InitListener.onSuccess 中启动笔记 WizNoteSDK.startNoteHomePage 传入 ApplicationContext，不要传入 Activity
 * 参数传递参考 loginEnterpriseStatic 或者 loginEnterprise
 */
////////////////更新 2018-12-29

/**
 *  1.初始化传入的参数都
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.launch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInEnterprise(new LogicCallback() {

                    @Override
                    public WizSDK.OuterStartType getStartType() {
                        return WizSDK.OuterStartType.List;
                    }

                    @Override
                    public String getOuterAppId() {
                        return null;
                    }

                    @Override
                    public String getOuterObjectId() {
                        return null;
                    }
                });
            }
        });
        findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInEnterprise(new LogicCallback() {

                    @Override
                    public WizSDK.OuterStartType getStartType() {
                        return WizSDK.OuterStartType.Edit;
                    }

                    @Override
                    public String getOuterAppId() {
                        return "outerAppId";
                    }

                    @Override
                    public String getOuterObjectId() {
                        return "outerObjectId";
                    }
                });
            }
        });
        findViewById(R.id.test_webview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TestWebViewActivity.class));
            }
        });
    }

    private static abstract class LogicCallback implements WizSDK.HWLogicCallback {
        @Override
        public String getAuthType() {
            String authType = "huawei";
            return authType;
        }

        @Override
        public String getAuthCode() {
            String authCode = "123";
            return authCode;
        }

        @Override
        public String getAuthBody() {
            String authBody = "w3Token=a8f145485b9c9bd230e0b4d21251d51d213afe8328f0527d0b72ee637ac972e1bfa84cc14d14932abf94591186361e7cd13a7ac0726a928cfd074c8d54cc4d1473395d151bc4d875bbc13c4302c30ed8a543c286cebcd2cc49d9fec2a1b8664b";
            return authBody;
        }

        @Override
        public String getEnterpriseUserId() {
            String enterpriseUserId = "aaaa";
            return enterpriseUserId;
        }

        @Override
        public String getApiServer() {
            String apiServer = "http://v3.wiz.cn";
            return apiServer;
        }

        @Override
        public void showShare(Context context, String shareUrl, String title, String description) {
            Log.e("wiz_hw", "share: url=" + shareUrl);
            Log.e("wiz_hw", "share: title=" + title);
            Log.e("wiz_hw", "share: description=" + description);
        }

        @Override
        public void reportLog(String tag, String msg) {
            Log.e(tag, msg);
        }
    }

    /**
     * 静态变量置空，每次使用前初始化
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    public void logInEnterprise(WizSDK.HWLogicCallback logicCallback) {
        WizNoteSDK.initNoteSDK(getApplication(), new WizNoteSDK.InitListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(String reason) {
                    }
                }, new WizNoteSDK.WeLinkEventCallback() {
                    @Override
                    public void onEvent(int eventNum, String eventData) {

                    }
                }, new WizNoteSDK.HuaweiUICallback() {
                    @Override
                    public void showWarning(Context ctx, CharSequence msg) {

                    }

                    @Override
                    public void showError(Context ctx, CharSequence msg) {

                    }

                    @Override
                    public void showLoading(Activity activity, CharSequence msg, @Nullable WizSDK.LoadingId id) {

                    }

                    @Override
                    public void dismissLoading(@Nullable WizSDK.LoadingId id) {

                    }

                    @Override
                    public WizSDK.HwListViewHelper getHwListView() {
                        return new WizSDK.HwListViewHelper() {
                            @Override
                            public ListView getListView(Activity activity) {
                                return new ListView(activity);
                            }

                            @Override
                            public void setPullRefreshEnable(boolean enable) {

                            }

                            @Override
                            public void setPullLoadEnable(boolean enable) {

                            }

                            @Override
                            public void autoRefresh() {

                            }

                            @Override
                            public void stopRefresh() {

                            }

                            @Override
                            public void stopLoadMore() {

                            }

                            @Override
                            public void setListViewListener(IHwListViewListener listener) {

                            }
                        };
                    }
                }, logicCallback);
    }

}
