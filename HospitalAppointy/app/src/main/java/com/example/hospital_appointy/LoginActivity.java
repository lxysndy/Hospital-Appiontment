package com.example.hospital_appointy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;


import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity{

    String LOGIN_EXTRA = "com.example.hospital_appointy.Login";
    String TAG = "LoginActivity";
    Button mLogin;
    Button mRegister;
    EditText mUsername;
    EditText mPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLogin = (Button) findViewById(R.id.login_button);
        mRegister = (Button) findViewById(R.id.register_button);
        mUsername = (EditText) findViewById(R.id.phone_text);
        mPassword = (EditText) findViewById(R.id.password_text);

        mLogin.setOnClickListener(view -> {
            new Thread(() -> {
                String loginAddress="http://192.168.31.157:1206/Server_Pro_war/LoginServlet";
                String loginAccount = mUsername.getText().toString();
                String loginPassword = mPassword.getText().toString();
                loginWithOkHttp(loginAddress,loginAccount,loginPassword);
            }).start();
        });
        mRegister.setOnClickListener(view -> {
            new Thread(() -> {
                String loginAddress="http://192.168.31.157:1206/Server_Pro_war/RegisterServlet";
                String loginAccount = mUsername.getText().toString();
                String loginPassword = mPassword.getText().toString();
                registerWithOkHttp(loginAddress,loginAccount,loginPassword);
            }).start();
        });
    }

    public void loginWithOkHttp(String address,String account,String password){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("username",account)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .method("POST",body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() != 200) {
                Looper.prepare();
                Log.i(TAG,"????????????"+response.code());
                Toast.makeText(LoginActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                Looper.loop();
            } else {
                Looper.prepare();
                Log.i(TAG,"???????????????");
                String ms = response.body().string();
                String msg = ms.replaceAll("\r|\n","");
//                System.out.println("msg"+msg);
//                System.out.println("cg");
//                System.out.println(msg.equals("cg"));
//                System.out.println("cg".equals(msg));

                if(msg.equals("cg")){
                    //TODO:????????????
                    Log.i(TAG,"????????????");
                    User.Username = account;
                    Intent intent = new Intent(LoginActivity.this, SelectDepartActivity.class);
                    intent.putExtra(LOGIN_EXTRA,account);
                    startActivity(intent);
                }else {
                    //??????
                    Toast.makeText(LoginActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
                }
                Looper.loop();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //????????????
    public void registerWithOkHttp(String address,String account,String password){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("username",account)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .method("POST",body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() != 200) {
                Looper.prepare();
                Log.i(TAG,"????????????"+response.code());
                Toast.makeText(LoginActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                Looper.loop();
            } else {
                Looper.prepare();
                Log.i(TAG,"???????????????");
                String ms = response.body().string();
                String msg = ms.replaceAll("\r|\n","");
                Log.i(TAG,"????????????????????????"+msg);
                if(msg.equals("cg")){
                    //????????????
                    Toast.makeText(LoginActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
                }else if(msg.equals("sb")){
                    //??????
                    Toast.makeText(LoginActivity.this, "???????????????????????????", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                }
                Looper.loop();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}