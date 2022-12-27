package com.example.hospital_appointy;

import androidx.fragment.app.Fragment;

public class UserDoctorActivity extends SingalDoctorActivity{
    String DETAILEXTRA = "com.example.detailPersonal";
    userDoctorItem mUserDoctorItem = new userDoctorItem();
    @Override
    protected Fragment createFragment() {
        mUserDoctorItem = (userDoctorItem) getIntent().getSerializableExtra(DETAILEXTRA);
        return DoctorDetailFragment.newInstance2(mUserDoctorItem);
    }

    @Override
    protected int getLayoutId() {
        return R.id.fragment_container4;
    }
}
