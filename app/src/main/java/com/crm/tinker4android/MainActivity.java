package com.crm.tinker4android;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.tencent.tinker.lib.service.PatchResult;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.tinker.callback.ResultCallBack;

/**
 * Desc: Tinker 热修复
 * Author:HuWeiLiang
 * Date: 2017/12/7 10:36
 * Email:2072025612@qq.com
 **/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (BuildConfig.TINKER_ENABLE) {
            TinkerPatch.with().setPatchResultCallback(new ResultCallBack() {
                @Override
                public void onPatchResult(PatchResult patchResult) {
                    if (patchResult != null) {
                        if (patchResult.isSuccess) {
                            LogUtils.d("patchResult:" + patchResult);
                            h.sendEmptyMessage(0);
                            new Handler(getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setMessage("热修复成功,重启应用?").setCancelable(false).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent mStartActivity = new Intent(MainActivity.this, MainActivity.class);
                                            int mPendingIntentId = 123456;
                                            PendingIntent mPendingIntent = PendingIntent.getActivity(MainActivity.this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                                            AlarmManager mgr = (AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE);
                                            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, mPendingIntent);
                                            android.os.Process.killProcess(android.os.Process.myPid());
                                            System.exit(0);
                                        }
                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                                    AlertDialog alert = builder.create();
                                    alert.setCancelable(true);
                                    alert.show();
                                }
                            });

                        }
                    }
                }
            });
        }
    }

    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

}
