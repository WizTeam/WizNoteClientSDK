package cn.wiz.example;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import cn.wiz.note.sdk.WizNoteSDK;
import cn.wiz.sdk.api.WizSDK;
import cn.wiz.sdk.util2.ToastUtil;

/**
 * eventCallback uiCallback logicCallback 为公用回调
 * initCallback 不同方法需要提供不同实现
 */
public class BaseActivity extends AppCompatActivity {


    public void startNoteHome(WizSDK.HWInitCallback initCallback) {
        WizNoteSDK.startNoteHome(getApplication(), initCallback, eventCallback, uiCallback, logicCallback);
    }

    public void startNotebook(WizSDK.HWInitCallback initCallback, String notebookName) {
        WizNoteSDK.startNoteList(getApplication(), initCallback, eventCallback, uiCallback, logicCallback, notebookName);
    }

    public void startViewNote(WizSDK.HWInitCallback initCallback, String docGuid) {
        WizNoteSDK.startViewNote(getApplication(), initCallback, eventCallback, uiCallback, logicCallback, docGuid);
    }

    public void startCreateNote(WizSDK.HWInitCallback initCallback, String notebookName, String appId, String objectId, String title) {
        WizNoteSDK.startCreateNote(getApplication(), initCallback, eventCallback, uiCallback, logicCallback, notebookName, appId, objectId, title);
    }

    @Deprecated
    public void startCreateNote(WizSDK.HWInitCallback initCallback, String notebookName, String appId, String objectId) {
        WizNoteSDK.startCreateNote(getApplication(), initCallback, eventCallback, uiCallback, logicCallback, notebookName, appId, objectId);
    }

    @Deprecated
    public void startCreateNote(WizSDK.HWInitCallback initCallback, String notebookName) {
        WizNoteSDK.startCreateNote(getApplication(), initCallback, eventCallback, uiCallback, logicCallback, notebookName);
    }


    public void getNotebookList(WizSDK.HWInitCallback initCallback, String notebookName, int start, int count) {
        WizNoteSDK.getNoteListByNotebook(getApplication(), initCallback, eventCallback, uiCallback, logicCallback, notebookName, start, count);
    }

    public void getObjectList(WizSDK.HWInitCallback initCallback, String appId, String objectId) {
        WizNoteSDK.getNoteListByObject(getApplication(), initCallback, eventCallback, uiCallback, logicCallback, appId, objectId);
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
    };

}
