package cn.wiz.example;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.wiz.sdk.api.WizSDK;


/**
 * 2019-01-15
 *
 * 支持的操作 6 种操作：
 * 1. 启动笔记主界面 {@link cn.wiz.note.sdk.WizNoteSDK#startNoteHome(Application, WizSDK.HWInitCallback, WizSDK.HWEventCallback, WizSDK.HWUICallback, WizSDK.HWLogicCallback)}
 * 2. 根据 AppId 启动笔记本页面 {@link cn.wiz.note.sdk.WizNoteSDK#startNoteListByAppId(Application, WizSDK.HWInitCallback, WizSDK.HWEventCallback, WizSDK.HWUICallback, WizSDK.HWLogicCallback, String, String)}
 * 3. 查看笔记 {@link cn.wiz.note.sdk.WizNoteSDK#startViewNote(Application, WizSDK.HWInitCallback, WizSDK.HWEventCallback, WizSDK.HWUICallback, WizSDK.HWLogicCallback, String)}
 * 4. 创建笔记 {@link cn.wiz.note.sdk.WizNoteSDK#startCreateNote(Application, WizSDK.HWInitCallback, WizSDK.HWEventCallback, WizSDK.HWUICallback, WizSDK.HWLogicCallback, String, String, String, String)}
 * 5. 根据 AppId 获取笔记列表 {@link cn.wiz.note.sdk.WizNoteSDK#getNoteListByAppId(Application, WizSDK.HWInitCallback, WizSDK.HWEventCallback, WizSDK.HWUICallback, WizSDK.HWLogicCallback, String, int, int)}
 * {@link cn.wiz.sdk.api.WizSDK.HWInitCallback#onSuccess(String)} 回调返回结果
 * 6. 根据 AppId 和 ObjectId 获取笔记本列表 {@link cn.wiz.note.sdk.WizNoteSDK#getNoteListByObject(Application, WizSDK.HWInitCallback, WizSDK.HWEventCallback, WizSDK.HWUICallback, WizSDK.HWLogicCallback, String, String)}
 * {@link cn.wiz.sdk.api.WizSDK.HWInitCallback#onSuccess(String)} 回调返回结果
 *
 * 参数：
 * eventCallback uiCallback logicCallback 为公用参数。不同操作需要实现不同 initCallback 获取返回结果
 *
 * initCallback {@link cn.wiz.sdk.api.WizSDK.HWInitCallback} 调用不同方法传递不同 initCallback
 * eventCallback {@link cn.wiz.sdk.api.WizSDK.HWEventCallback} 埋点回调
 * uiCallback {@link cn.wiz.sdk.api.WizSDK.HWUICallback} 界面回调，显示 Toast Loading etc.
 * logicCallback {@link cn.wiz.sdk.api.WizSDK.HWLogicCallback} 逻辑回调，分享，上报 等。为了简化调用方法的参数个数。固定的必须传递的参数也通过此回到获取：
 * {@link WizSDK.HWLogicCallback#getAuthBody()}
 * {@link WizSDK.HWLogicCallback#getAuthCode()}
 * {@link WizSDK.HWLogicCallback#getAuthType()}
 * {@link WizSDK.HWLogicCallback#getEnterpriseUserId()}
 * {@link WizSDK.HWLogicCallback#getLanguage()}
 *
 * AppID 和 ObjectID 解释
 * AppID 为外部应用或者模块的ID，ObjectID 为外部应用或者模块内数据记录的ID
 * 同时传递 AppID 和 ObjectID 避免重复
 * 如 模块 A 为会议 AppID 为 meeting，模块 A 中有记录 会议1 会议2，对应 ObjectID 为 1， 2
 * 如 模块 B 为办公 AppID 为 office，模块 B 中有记录 办公1 办公2，对应 ObjectID 为 1， 2
 *
 * i18nNotebookName 为国际化的笔记本名称，方便之后添加其他语言，传递 json 字符串:
 * {"CN": "我的会议", "EN": "My Meetings"} 注意: Key "CN" "EN" 要与 {@link WizSDK.HWLogicCallback#getLanguage()} 对应
 *
 * 返回笔记列表为 JSONArray 字符串。
 * [
 *   {
 * 		"docGuid": "74d42e81-9f0b-4b37-ab58-c4252fbd0a80",
 * 	    "title": "嗯 新建",
 * 	    "category": "/My Meeting/",
 * 	    "abstractText": "白白净净哈哈哈 vv",
 * 	    "created": 1547539531000,
 * 	    "dataModified": 1547540247000,
 * 		"infoModified": 1547540249000,
 * 	    ....
 *   }
 *   ...
 * ]
 *
 * 可以参考此 Demo.基座实现几个公用参数。基座对外提供方法，其他模块调用者只需要实现 {@link cn.wiz.sdk.api.WizSDK.HWInitCallback} 即可
 */
public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.language).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Language == "EN") {
                    Language = "CN";
                    ((Button) view).setText("EN");
                } else {
                    Language = "EN";
                    ((Button) view).setText("CN");
                }
            }
        });

        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNoteHome(initCallbackWithoutResult);
            }
        });
        findViewById(R.id.notebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNoteListByAppId(initCallbackWithoutResult, mAppId, getI18nNotebookName());
            }
        });
        findViewById(R.id.create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCreateNote(initCallbackWithoutResult, null);
            }
        });
        findViewById(R.id.getNotebookList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNoteListByAppId(new WizSDK.HWInitCallback() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(String result) {
                        try {
                            ((TextView) findViewById(R.id.select)).setText("笔记本笔记列表，点击查看:");
                            JSONArray documents = new JSONArray(result);
                            LinearLayout noteLayout = (LinearLayout) findViewById(R.id.meeting_notes);
                            noteLayout.removeAllViews();
                            for (int i=0; i<documents.length(); i++) {
                                final JSONObject document = documents.getJSONObject(i);
                                TextView textView = new TextView(MainActivity.this);
                                textView.setText(document.getString("title"));
                                noteLayout.addView(textView);
                                final String docGuid = document.getString("docGuid");
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startViewNote(initCallbackWithoutResult, docGuid);
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String reason) {

                    }
                }, mAppId, 0, 10);
            }
        });
        findViewById(R.id.meeting1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMeeting(((TextView) v).getText().toString(), mAppId, "meeting1");
            }
        });
        findViewById(R.id.meeting2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMeeting(((TextView) v).getText().toString(), mAppId, "meeting2");
            }
        });
        findViewById(R.id.meeting3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMeeting(((TextView) v).getText().toString(), mAppId, "meeting3");
            }
        });
    }

    private void setMeeting(final String name, final String appId, final String objectId) {
        ((TextView) findViewById(R.id.select)).setText(name + "笔记列表:");
        //
        getNoteListByObject(new WizSDK.HWInitCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONArray documents = new JSONArray(result);
                    LinearLayout noteLayout = (LinearLayout) findViewById(R.id.meeting_notes);
                    noteLayout.removeAllViews();
                    for (int i=0; i<documents.length(); i++) {
                        final JSONObject document = documents.getJSONObject(i);
                        TextView textView = new TextView(MainActivity.this);
                        textView.setText(document.getString("title"));
                        noteLayout.addView(textView);
                        final String docGuid = document.getString("docGuid");
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startViewNote(initCallbackWithoutResult, docGuid);
                            }
                        });
                    }
                    Button createButton = new Button(MainActivity.this);
                    createButton.setText("为" + name + "新建笔记");
                    createButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startCreateNote(initCallbackWithoutResult, getI18nNotebookName(), appId, objectId, name);
                        }
                    });
                    noteLayout.addView(createButton);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String reason) {

            }
        }, appId, objectId);
    }

    private String mAppId = "meeting";

    private WizSDK.HWInitCallback initCallbackWithoutResult = new WizSDK.HWInitCallback() {
        @Override
        public void onStart() {
            Log.e("bug", "onStart");
        }

        @Override
        public void onSuccess(String s) {
            Log.e("bug", "onSuccess without result data");
        }

        @Override
        public void onError(String s) {
            Log.e("bug", "onError: " + s);
        }
    };
}
