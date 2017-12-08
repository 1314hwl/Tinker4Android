package com.crm.tinker4android;

import android.os.Handler;
import android.os.Message;

import com.tinkerpatch.sdk.TinkerPatch;

/**
 * Created by gavin
 * Created date 17/7/3
 * Created log
 */

public class FetchPatchHandler extends Handler {
    public static final long HOUR_INTERVAL = 60 * 1000;
    private long checkInterval;

    /**
     * 通过handler, 达到按照时间间隔轮训的效果
     *
     * @param hour
     */
    public void fetchPatchWithInterval(int hour) {
        //设置TinkerPatch的时间间隔，测试时把这里改成了0
        TinkerPatch.with().setFetchPatchIntervalByHours(0);
        checkInterval = hour * HOUR_INTERVAL;
        //立刻尝试去访问,检查是否有更新
        sendEmptyMessage(0);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        //这里使用false即可
        TinkerPatch.with().fetchPatchUpdate(false);
        //每隔一段时间都去访问后台, 增加10分钟的buffer时间，测试时改成了60秒
        sendEmptyMessageDelayed(0, 30000);
    }
}