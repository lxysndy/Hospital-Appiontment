package com.example.hospital_appointy;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
public abstract class SingleDFragActivity extends AppCompatActivity{
    protected abstract Fragment createFragment();
    protected abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_container);
//        setContentView(getLayoutId());//实现双版面

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(getLayoutId());

        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction().
                    add(getLayoutId(),fragment)
                    .commit();
        }
    }
}
