package com.example.hospital_appointy;

import androidx.fragment.app.Fragment;

public class DoctorDetailActivity extends SingalDoctorActivity{
    String DETAILEXTRA = "com.example.detailDoctor";
    doctorItem mDoctorItem = new doctorItem();
    //userDoctorItem mUserDoctorItem = new userDoctorItem();
    @Override
    protected Fragment createFragment() {
        mDoctorItem = (doctorItem) getIntent().getSerializableExtra(DETAILEXTRA);
        //mUserDoctorItem = getIntent().getParcelableExtra(DETAILEXTRA2);
        return DoctorDetailFragment.newInstance1(mDoctorItem);
    }

    @Override
    protected int getLayoutId() {
        return R.id.fragment_container4;
    }
}
