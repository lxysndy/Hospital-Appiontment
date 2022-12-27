package com.example.hospital_appointy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class SelectDepartActivity extends SingleFragmentActivity{
    String LOGIN_EXTRA = "com.example.hospital_appointy.Login";
    String DEPARTEXTRA = "com.example.hospital_appointy.Depart";
    String TAG = "SelectDepartActivity";
    @Override
    protected Fragment createFragment() {
        String username = getIntent().getStringExtra(LOGIN_EXTRA);
        return SelectDepartFragment.newInstance(username);
    }

    @Override
    protected int getLayoutId() {
        return R.id.fragment_container1;
    }

//    @Override
//    public void onDepartSelected(String department,String username) {
//        //进入选医生的界面
//        Log.i(TAG,"选择科室");
//        JSONObject ud = new JSONObject();
//        try {
//            ud.put("username",username);
//            ud.put("department",department);
//            String udStr = ud.toString();
//            Intent intent = new Intent(SelectDepartActivity.this,SelectDoctorActivity.class);
//            intent.putExtra(DEPARTEXTRA,udStr);
//            startActivity(intent);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}