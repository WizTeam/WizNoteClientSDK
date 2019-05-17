package cn.wiz.example;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.wiz.note.sdk.WizNoteSDK;
import cn.wiz.sdk.api.WizSDK;
import cn.wiz.sdk.util2.ToastUtil;

/**
 * eventCallback uiCallback logicCallback 为公用回调
 * initCallback 不同方法需要提供不同实现
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {


    /**
     * 打开主页
     */
    public void startNoteHome(WizSDK.HWInitCallback initCallback) {
        WizNoteSDK.startNoteHome(getApplication(), initCallback, eventCallback, uiCallback, logicCallback);
    }

    /**
     * 查看笔记
     */
    public void startViewNote(WizSDK.HWInitCallback initCallback, String docGuid) {
        WizNoteSDK.startViewNote(getApplication(), initCallback, eventCallback, uiCallback, logicCallback, docGuid);
    }

    /**
     * 创建笔记
     */
    public void startCreateNote(WizSDK.HWInitCallback initCallback, String i18nNotebookName, String appId, String objectId, String title, String hwCategory) {
        WizNoteSDK.startCreateNote(getApplication(), initCallback, eventCallback, uiCallback, logicCallback, i18nNotebookName, appId, objectId, title, hwCategory);
    }

    /**
     * 根据 AppId 获取笔记列表
     */
    public void getNoteListByAppId(WizSDK.HWInitCallback initCallback, String appId, int start, int count) {
        WizNoteSDK.getNoteListByAppId(getApplication(), initCallback, eventCallback, uiCallback, logicCallback, appId, start, count);
    }

    public void getNoteListByCategory(WizSDK.HWInitCallback initCallback, String hwCategory) {
        WizNoteSDK.getNoteListByCategory(getApplication(), initCallback, eventCallback, uiCallback, logicCallback, hwCategory);
    }

    /**
     * 根据 AppId 和 ObjectId 获取笔记列表
     */
    public void getNoteListByObject(WizSDK.HWInitCallback initCallback, String appId, String objectId) {
        WizNoteSDK.getNoteListByObject(getApplication(), initCallback, eventCallback, uiCallback, logicCallback, appId, objectId);
    }

    /**
     * 根据 AppId 打开笔记本
     */
    public void startNoteListByAppId(WizSDK.HWInitCallback initCallback, String appId, String i18nNotebookName) {
        WizNoteSDK.startNoteListByAppId(getApplication(), initCallback, eventCallback, uiCallback, logicCallback, appId, i18nNotebookName);
    }

    /**
     * 在笔记本中创建一篇笔记，不和外部记录关联，笔记本传 null， 在默认笔记本中创建笔记
     */
    @Deprecated
    public void startCreateNote(WizSDK.HWInitCallback initCallback, String notebookName) {
        WizNoteSDK.startCreateNote(getApplication(), initCallback, eventCallback, uiCallback, logicCallback, notebookName);
    }


    private WizSDK.HWEventCallback eventCallback = new WizSDK.HWEventCallback() {
        @Override
        public void onEvent(int eventNum, String eventData) {
            Log.e("bug", "num: " + eventNum + "; data: " + eventData);
        }
    };
    private static WizSDK.HWUICallback uiCallback = new WizSDK.HWUICallback() {
        @Override
        public void showWarning(Context ctx, CharSequence msg) {
            ToastUtil.showShortToast(ctx, msg);
        }

        @Override
        public void showError(Context ctx, CharSequence msg) {
            ToastUtil.showShortToast(ctx, msg);
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
    };
    WizSDK.HWLogicCallback logicCallback = new WizSDK.HWLogicCallback() {
        @Override
        public String getAuthType() {
            return "huawei";
        }

        @Override
        public String getAuthCode() {
            return "123";
        }

        @Override
        public String getAuthBody() {
            return "w3Token=a8f145485b9c9bd230e0b4d21251d51d213afe8328f0527d0b72ee637ac972e1bfa84cc14d14932abf94591186361e7cd13a7ac0726a928cfd074c8d54cc4d1473395d151bc4d875bbc13c4302c30ed8a543c286cebcd2cc49d9fec2a1b8664b";
        }

        @Override
        public String getEnterpriseUserId() {
            return "aaaa";
        }

        @Override
        public String getApiServer() {
            return "http://v3.wiz.cn";
        }

        @Override
        public void showShare(Context context, String shareUrl, String title, String description) {
            Log.e("wiz_hw", "share: url=" + shareUrl);
            Log.e("wiz_hw", "share: title=" + title);
            Log.e("wiz_hw", "share: description=" + description);
            ToastUtil.showShortToast(context, description);
        }

        @Override
        public void reportLog(String tag, String msg) {
            Log.e(tag, msg);
        }

        @Override
        public String getLanguage() {
            return Language;
        }

        @Override
        public String getNoteTitleSize() {
            return "{\"titleFontSize\":16,\"subTitleFontSize\":14,\"auxiliaryArtFontSize\":12,\"bigMoreTitleFontSize\":20,\"contentFontSize\":16}";
        }
    };

    protected String Language = "CN";

    protected String getI18nNotebookName() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("CN", "我的会议");
            jsonObject.put("EN", "My Meetings");
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("error");
        }
    }

}
