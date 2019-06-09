package com.hjq.demo.ui.activity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;

import com.hjq.demo.R;
import com.hjq.demo.common.MyActivity;
import com.hjq.dialog.AddressDialog;
import com.hjq.dialog.InputDialog;
import com.hjq.widget.SettingBar;
import com.hjq.widget.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/04/20
 *    desc   : 个人资料
 */
public final class PersonalDataActivity extends MyActivity {

    @BindView(R.id.sb_person_data_id)
    SettingBar mIDView;
    @BindView(R.id.sb_person_data_name)
    SettingBar mNameView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_data;
    }

    @Override
    protected int getTitleId() {
        return R.id.tb_personal_data_title;
    }

    @Override
    protected void initView() {
        SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
        mIDView.setRightText(settings.getString("username",""));
        mNameView.setRightText(settings.getString("nickname",""));

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.sb_person_data_name})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sb_person_data_name:
                new InputDialog.Builder(this)
                        .setTitle(getResources().getString(R.string.personal_data_name_hint)) // 标题可以不用填写
                        .setContent(mNameView.getRightText())
                        //.setHint(getResources().getString(R.string.personal_data_name_hint))
                        //.setConfirm("确定")
                        //.setCancel("取消") // 设置 null 表示不显示取消按钮
                        //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                        .setListener(new InputDialog.OnListener() {

                            @Override
                            public void onConfirm(Dialog dialog, String content) {
                                if (!mNameView.getRightText().equals(content)) {
                                    mNameView.setRightText(content);
                                    SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString("nickname",content);
                                    editor.commit();
                                }
                            }

                            @Override
                            public void onCancel(Dialog dialog) {}
                        })
                        .show();
                break;
           default:
                break;
        }
    }
}