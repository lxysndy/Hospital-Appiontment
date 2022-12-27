package com.example.hospital_appointy;


import static com.example.hospital_appointy.NotifyHelper.N_CALL;
import static com.example.hospital_appointy.NotifyHelper.N_MESSAGE;
import static com.example.hospital_appointy.NotifyHelper.N_QQ;
import static com.example.hospital_appointy.NotifyHelper.N_WX;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements NotifyListener {
    private static String PRICE = "doctor_price";
    int price=0;
    private static final int REQUEST_CODE = 9527;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        price = getIntent().getIntExtra(PRICE,0);
        textView = findViewById(R.id.textView);
        NotifyHelper.getInstance().setNotifyListener(this);
    }

    /**
     * 请求权限
     *
     * @param view
     */
    public void requestPermission(View view) {
        if (!isNLServiceEnabled()) {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            showMsg("通知服务已开启");
            toggleNotificationListenerService();
        }
    }

    /**
     * 是否启用通知监听服务
     *
     * @return
     */
    public boolean isNLServiceEnabled() {
        Set<String> packageNames = NotificationManagerCompat.getEnabledListenerPackages(this);
        if (packageNames.contains(getPackageName())) {
            return true;
        }
        return false;
    }

    /**
     * 切换通知监听器服务
     */
    public void toggleNotificationListenerService() {
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(getApplicationContext(), NotifyService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        pm.setComponentEnabledSetting(new ComponentName(getApplicationContext(), NotifyService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (isNLServiceEnabled()) {
                showMsg("通知服务已开启");
                toggleNotificationListenerService();
            } else {
                showMsg("通知服务未开启");
            }
        }
    }


    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 收到通知
     *
     * @param type 通知类型
     */
    @Override
    public void onReceiveMessage(int type) {
        switch (type) {
            case N_MESSAGE:
                textView.setText("收到短信消息");
                break;
            case N_CALL:
                textView.setText("收到来电消息");
                break;
            case N_WX:
                textView.setText("收到微信消息");
                break;
            case N_QQ:
                textView.setText("收到QQ消息");
                break;
            default:
                break;
        }
    }

    /**
     * 移除通知
     *
     * @param type 通知类型
     */
    @Override
    public void onRemovedMessage(int type) {
        switch (type) {
            case N_MESSAGE:
                textView.setText("移除短信消息");
                break;
            case N_CALL:
                textView.setText("移除来电消息");
                break;
            case N_WX:
                textView.setText("移除微信消息");
                break;
            case N_QQ:
                textView.setText("移除QQ消息");
                break;
            default:
                break;
        }
    }

    /**
     * 收到通知
     *
     * @param sbn 状态栏通知
     */
    @Override
    public void onReceiveMessage(StatusBarNotification sbn) {
        if (sbn.getNotification() == null) return;
        //消息内容
        String msgContent = "";
        if (sbn.getNotification().tickerText != null) {
            msgContent = sbn.getNotification().tickerText.toString();
        }

        //消息时间
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).format(new Date(sbn.getPostTime()));
        String finalMsgContent = msgContent;
        System.out.println(price);
        if(finalMsgContent.equals("微信支付: 微信支付收款"+price+".00元(朋友到店)")){
//            System.out.println("收到！！");
//            Toast.makeText(MainActivity.this,"支付成功，即将跳转",Toast.LENGTH_SHORT);
            System.out.println("收到！！");
            Intent intent = new Intent();
            intent.putExtra("return","cg");
            setResult(RESULT_OK,intent);
            finish();
        }
        if(finalMsgContent.equals("爸爸: 111")){
            System.out.println("收到！！");
            Intent intent = new Intent();
            intent.putExtra("return","cg");
            setResult(RESULT_OK,intent);
            finish();
        }
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                textView.setText(String.format(Locale.getDefault(),
                        "应用包名：%s\n消息内容：%s\n消息时间：%s\n",
                        sbn.getPackageName(), finalMsgContent, time));
                System.out.println("消息内容"+finalMsgContent);
            }
        });


    }

    /**
     * 移除通知
     *
     * @param sbn 状态栏通知
     */
    @Override
    public void onRemovedMessage(StatusBarNotification sbn) {
        textView.setText("通知移除");
    }
}