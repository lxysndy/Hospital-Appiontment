package com.example.hospital_appointy;

import androidx.fragment.app.Fragment;

public class PersonalActivity extends SinglePFragActivity{
    String NAME_EXTRA = "com.example.name";
    @Override
    protected Fragment createFragment() {
        String username = getIntent().getStringExtra(NAME_EXTRA);
        return PersonalFragment.newInstance(username);
    }

    @Override
    protected int getLayoutId() {
        return R.id.fragment_container3;
    }
}
