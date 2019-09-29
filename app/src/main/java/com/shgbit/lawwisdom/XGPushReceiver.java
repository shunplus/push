package com.shgbit.lawwisdom;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by xushun on  2019/6/1 14:26.
 * Email：shunplus@163.com
 * Des：测试 信鸽华为通道 IM测试
 *  在注意测试 华为厂商通道时  需要release打包
 */
public class XGPushReceiver extends XGPushBaseReceiver {
    private static final String TAG = "XGPushReceiver";
    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {

    }

    @Override
    public void onUnregisterResult(Context context, int i) {

    }

    @Override
    public void onSetTagResult(Context context, int i, String s) {

    }

    @Override
    public void onDeleteTagResult(Context context, int i, String s) {

    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {
        String title = xgPushTextMessage.getTitle();
        String custom = xgPushTextMessage.getCustomContent();
        String content=xgPushTextMessage.getContent();
        Log.i(TAG,xgPushTextMessage.toString());

//        hengfu(context);
        Toast.makeText(context, "title="+custom+"custom="+custom+",content="+content, Toast.LENGTH_LONG).show();
//        sendCustomNotification(context);

        context.startActivity(new Intent(context,MainActivity.class));
    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {

    }

    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {

    }


    private void hengfu(Context context){
        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder notifyBuilder =
                new NotificationCompat.Builder( context ).setContentTitle( "title" )
                        .setContentText( "ContentText" )
                        .setSmallIcon( R.drawable.ic_launcher_background )
                        // 点击消失
                        .setAutoCancel( true )
                        // 设置该通知优先级
                        .setPriority( Notification.PRIORITY_MAX )
                        .setLargeIcon( BitmapFactory.decodeResource( context.getResources(), R.drawable.ic_launcher_background ) )
//                        .setTicker( mTicker )
                        // 通知首次出现在通知栏，带上升动画效果的
                        .setWhen( System.currentTimeMillis() )
                        // 通知产生的时间，会在通知信息里显示
                        // 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                        .setDefaults( Notification.DEFAULT_VIBRATE | Notification.DEFAULT_ALL | Notification.DEFAULT_SOUND );
        Intent XuanIntent = new Intent();
        XuanIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        XuanIntent.setClass(context, MainActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity( context, 0, XuanIntent, PendingIntent.FLAG_UPDATE_CURRENT );
        notifyBuilder.setContentIntent( resultPendingIntent );
        mNotificationManager.notify( 0, notifyBuilder.build() );
    }

    //获取系统服务
    private NotificationManager mNotificationManager;
    private NotificationManager getNotificationManager(Context context) {
        if (mNotificationManager == null){
            mNotificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        }
        return mNotificationManager;
    }

    //兼容android8.0以及之前版本获取Notification.Builder方法
    private Notification.Builder getNotificationBuilder(Context context){
        Notification.Builder builder = new Notification.Builder(context)
                .setAutoCancel(true)//是否自动取消，设置为true，点击通知栏 ，移除通知
                .setContentTitle("通知栏消息标题")
                .setContentText("通知栏消息具体内容")
                .setSmallIcon(R.mipmap.ic_launcher)//通知栏消息小图标，不设置是不会显示通知的
                //ledARGB 表示灯光颜色、ledOnMs 亮持续时间、ledOffMs 暗的时间
                .setLights(Color.RED, 3000, 3000)
                //.setVibrate(new long[]{100,100,200})//震动的模式，第一次100ms，第二次100ms，第三次200ms
                //.setStyle(new Notification.BigTextStyle())
                ;
        //没加版本判断会报Call requires API level 26 (current min is 16):android.app.Notification.Builder#Builder）错误
        //builder.setChannelId("channel_id");
        //通过版本号判断兼容了低版本没有通知渠道方法的问题，只有当版本号大于26（Build.VERSION_CODES.O）时才使用渠道相关方法
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            //builder的channelId需和下面channel的保持一致；
            builder.setChannelId("channel_id");
            NotificationChannel channel = new
                    NotificationChannel("channel_id","channel_name",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setBypassDnd(true);//设置可以绕过请勿打扰模式
            channel.canBypassDnd();//可否绕过请勿打扰模式
            //锁屏显示通知
            channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            channel.shouldShowLights();//是否会闪光
            channel.enableLights(true);//闪光
            //指定闪光时的灯光颜色，为了兼容低版本在上面builder上通过setLights方法设置了
            //channel.setLightColor(Color.RED);
            channel.canShowBadge();//桌面launcher消息角标
            channel.enableVibration(true);//是否允许震动
            //震动模式，第一次100ms，第二次100ms，第三次200ms，为了兼容低版本在上面builder上设置了
            //channel.setVibrationPattern(new long[]{100,100,200});
            channel.getAudioAttributes();//获取系统通知响铃声音的配置
            channel.getGroup();//获取通知渠道组
            //绑定通知渠道
            getNotificationManager(context).createNotificationChannel(channel);
        }
        return builder;
    }


    //发一个自定义通知
    private void sendCustomNotification(Context context){
        //自定义通知也是在Android N之后才出现的，所以要加上版本号判断
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            Notification.Builder builder = getNotificationBuilder(context);
            //自定义通知栏视图初始化
            RemoteViews remoteViews =
                    new RemoteViews(context.getPackageName(),R.layout.activity_notification);
            remoteViews.setTextViewText(R.id.notification_title,"custom_title");
            remoteViews.setTextViewText(R.id.notification_content,"custom_content");
            //PendingIntent即将要发生的意图，可以被取消、更新
            Intent intent = new Intent(context,MainActivity.class);
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(context,-1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.turn_next,pendingIntent);
            //绑定自定义视图
            builder.setCustomContentView(remoteViews);
            getNotificationManager(context).notify(3,builder.build());
        }
    }

}
