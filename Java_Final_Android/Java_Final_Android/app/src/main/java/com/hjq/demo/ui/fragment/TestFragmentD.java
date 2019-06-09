package com.hjq.demo.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.hjq.demo.R;
import com.hjq.demo.common.MyLazyFragment;

import com.hjq.demo.ui.activity.HomeActivity;

import com.hjq.demo.ui.activity.PersonalDataActivity;

import com.hjq.demo.ui.activity.SettingActivity;
import com.hjq.demo.ui.activity.SongActivity;


import butterknife.OnClick;

public final class TestFragmentD extends MyLazyFragment<HomeActivity> {

    public static TestFragmentD newInstance() {
        return new TestFragmentD();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_d;
    }

    @Override
    protected int getTitleId() {
        return R.id.tb_test_d_title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_test_personal,R.id.btn_test_setting,R.id.btn_play_song})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test_personal:
                startActivity(PersonalDataActivity.class);
                break;
            case R.id.btn_test_setting:
                startActivity(SettingActivity.class);
                break;
            case R.id.btn_play_song:
                startActivity(SongActivity.class);
                break;
        }
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }
}