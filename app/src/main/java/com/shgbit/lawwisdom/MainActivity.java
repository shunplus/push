package com.shgbit.lawwisdom;


import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.shgbit.lawwisdom.net.RetrofitUitls;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import java.io.File;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.et_register)
    EditText etRegister;
    @BindView(R.id.btn_et_register)
    Button btnEtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        XGPushManager.unregisterPush(this);
//        XGPushManager.delAccount( this,  etRegister.getText().toString());
    }

    @OnClick(R.id.btn_send)
    public void onViewClicked() {
        String url = "api/test";
        WeakHashMap<String, Object> parm = new WeakHashMap<>();
        parm.put("id", "520");

        RetrofitUitls.getInstance().getApiService().post(url, parm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext  " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError  " + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.btn_et_register)
    public void btn_et_register() {

        XGPushManager.bindAccount(getApplicationContext(), etRegister.getText().toString(), new XGIOperateCallback() {
            @Override
            public void onSuccess(Object o, int i) {

                Toast.makeText(MainActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "bindAccount onSuccess  i" + i);
            }

            @Override
            public void onFail(Object o, int i, String s) {
                Toast.makeText(MainActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFail  s" + s);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initPermissons();
    }


    /**
     * 动态获取权限
     */
    private void initPermissons() {
        String[] permissions = new String[]{/*Manifest.permission.ACCESS_COARSE_LOCATION,*/
//                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE, /*Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_WIFI_STATE,*/
//                Manifest.permission.ACCESS_NETWORK_STATE
        };
//        PermissionsUtils.showSystemSetting = false;//是否支持显示系统设置权限设置窗口跳转
        //创建监听权限的接口对象
        PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
            @Override
            public void passPermissons() {

            }

            @Override
            public void forbitPermissons() {
//                Intent intent = new Intent(mContext, LoginActivity.class);
//                startActivity(intent);
//                finish();

            }
        };
        //这里的this不是上下文，是Activity对象！
        PermissionsUtils.getInstance().chekPermissions(this, permissions, permissionsResult);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);

    }
}
