package com.shgbit.lawwisdom;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

/**
 * Created by xushun on  2019/6/26 10:22.
 * Email：shunplus@163.com
 * Des：
 */
public class MyApplacation extends Application {
    private static final String TAG = "MyApplacation";
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        XGPushConfig.enableDebug(getApplicationContext(), true);
        XGPushConfig.setHuaweiDebug(true);
        XGPushConfig.enableOtherPush(getApplicationContext(), true);
//        // 设置小米APPID和APPKEY
//        XGPushConfig.setMiPushAppId(getApplicationContext(), "xxxxxxxx");
//        XGPushConfig.setMiPushAppKey(getApplicationContext(), "xxxxxxxxxxxxxx");
        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                //token在设备卸载重装的时候有可能会变
                Log.d(TAG, "注册成功，设备token为：" + data);
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.d(TAG, "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });
    }
}
