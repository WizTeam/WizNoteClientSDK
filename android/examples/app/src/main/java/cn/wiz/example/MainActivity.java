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
 * 2. 启动笔记本页面 {@link cn.wiz.note.sdk.WizNoteSDK#startNoteList(Application, WizSDK.HWInitCallback, WizSDK.HWEventCallback, WizSDK.HWUICallback, WizSDK.HWLogicCallback, String)}
 * 3. 查看笔记 {@link cn.wiz.note.sdk.WizNoteSDK#startViewNote(Application, WizSDK.HWInitCallback, WizSDK.HWEventCallback, WizSDK.HWUICallback, WizSDK.HWLogicCallback, String)}
 * 4. 创建笔记，notebookName 不为 null 时，在对应笔记本中创建笔记，否则在默认笔记本创建笔记，appId 和 objectId 不为 null 时，给创建的笔记添加参数，title 不为 null 时，标题为 title {@link cn.wiz.note.sdk.WizNoteSDK#startCreateNote(Application, WizSDK.HWInitCallback, WizSDK.HWEventCallback, WizSDK.HWUICallback, WizSDK.HWLogicCallback, String, String, String, String)}
 * 5. 获取笔记本中的笔记列表 {@link cn.wiz.note.sdk.WizNoteSDK#getNoteListByNotebook(Application, WizSDK.HWInitCallback, WizSDK.HWEventCallback, WizSDK.HWUICallback, WizSDK.HWLogicCallback, String, int, int)}
 * 6. 获取外部记录对应的笔记列表 {@link cn.wiz.note.sdk.WizNoteSDK#getNoteListByObject(Application, WizSDK.HWInitCallback, WizSDK.HWEventCallback, WizSDK.HWUICallback, WizSDK.HWLogicCallback, String, String)}
 *
 * 参数：
 * eventCallback uiCallback logicCallback 为公用参数。不同操作需要实现不同 initCallback 获取返回结果
 *
 * initCallback {@link cn.wiz.sdk.api.WizSDK.HWInitCallback} 调用不同方法传递不同 initCallback 获取返回结果
 * eventCallback {@link cn.wiz.sdk.api.WizSDK.HWEventCallback} 埋点回调
 * uiCallback {@link cn.wiz.sdk.api.WizSDK.HWUICallback} 界面回调，显示 Toast Loading etc.
 * logicCallback {@link cn.wiz.sdk.api.WizSDK.HWLogicCallback} 逻辑回调，分享，上报 等。为了简化调用方法的
 * 参数个数。固定的必须传递的参数也通过此回到获取：
 * {@link WizSDK.HWLogicCallback#getAuthBody()}
 * {@link WizSDK.HWLogicCallback#getAuthCode()}
 * {@link WizSDK.HWLogicCallback#getAuthType()}
 * {@link WizSDK.HWLogicCallback#getEnterpriseUserId()}
 *
 * AppID 和 ObjectID 解释
 * AppID 为外部应用或者模块的ID，ObjectID 为外部应用或者模块内数据记录的ID
 * 同时传递 AppID 和 ObjectID 避免重复
 * 如 模块 A 为会议 AppID 为 meeting，模块 A 中有记录 会议1 会议2，对应 ObjectID 为 1， 2
 * 如 模块 B 为办公 AppID 为 office，模块 B 中有记录 办公1 办公2，对应 ObjectID 为 1， 2
 *
 * notebookName 为笔记的文件夹名称
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
        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNoteHome(initCallbackWithoutResult);
            }
        });
        findViewById(R.id.create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCreateNote(initCallbackWithoutResult, mNotebookMeeting, null, null, null);
            }
        });
        findViewById(R.id.list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNotebook(initCallbackWithoutResult, mNotebookMeeting);
            }
        });
        findViewById(R.id.getNotebookList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNotebookList(new WizSDK.HWInitCallback() {
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
                }, mNotebookMeeting, 0, 10);
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
        getObjectList(new WizSDK.HWInitCallback() {
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
                            startCreateNote(initCallbackWithoutResult, mNotebookMeeting, appId, objectId, null);
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
    private String mNotebookMeeting = "My Meeting";

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
