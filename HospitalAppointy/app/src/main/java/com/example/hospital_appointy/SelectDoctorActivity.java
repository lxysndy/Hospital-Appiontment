package com.example.hospital_appointy;

import androidx.fragment.app.Fragment;

public class SelectDoctorActivity extends SingleDFragActivity{
    String DEPARTEXTRA = "com.example.hospital_appointy.Depart";
    @Override
    protected Fragment createFragment() {
        String udStr = getIntent().getStringExtra(DEPARTEXTRA);
        return SelectDoctorFragment.newInstance(udStr);
    }

    @Override
    protected int getLayoutId() {
        return R.id.fragment_container2;
    }

}
