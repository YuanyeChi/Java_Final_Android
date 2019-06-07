package com.hjq.demo.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ansen.http.net.HTTPCaller;
import com.ansen.http.net.NameValuePair;
import com.ansen.http.net.RequestDataCallback;
import com.chiyuanye.demo.entity.User;
import com.hjq.demo.R;
import com.hjq.demo.common.MyActivity;
import com.hjq.demo.helper.InputTextHelper;
import com.hjq.image.ImageLoader;
import com.hjq.umeng.Platform;
import com.hjq.umeng.UmengClient;
import com.hjq.umeng.UmengLogin;
import com.hjq.widget.SwitchButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 登录界面
 */
public final class LoginActivity extends MyActivity {

    @BindView(R.id.iv_login_logo)
    ImageView mLogoView;
    @BindView(R.id.et_login_phone)
    EditText mPhoneView;
    @BindView(R.id.et_login_password)
    EditText mPasswordView;

    @BindView(R.id.btn_login_commit)
    Button mCommitView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected int getTitleId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        new InputTextHelper.Builder(this)
                .setMain(mCommitView)
                .addView(mPhoneView)
                .addView(mPasswordView)
                .build();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRightClick(View v) {
        // 跳转到注册界面
        startActivity(RegisterActivity.class);
    }


    @Override
    public boolean isSupportSwipeBack() {
        // 不使用侧滑功能
        return false;
    }

    @OnClick({R.id.btn_login_commit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_commit:
                if (mPasswordView.getText().toString().trim().equals("") || mPhoneView.getText().toString().trim().equals("")) {
                    toast("请输入完整的及密码");
                } else {
                    List<NameValuePair> postParam = new ArrayList<>();
                    postParam.add(new NameValuePair("username",mPhoneView.getText().toString()));
                    postParam.add(new NameValuePair("password",mPasswordView.getText().toString()));
                    HTTPCaller.getInstance().post(User.class, "http://155.138.230.57:8080/Java_Final/login.do", null, postParam, requestDataCallback);
                }

                break;
            default:
                break;
        }
    }

    private RequestDataCallback requestDataCallback = new RequestDataCallback<User>() {
        @Override
        public void dataCallback(User user) {
            if (user.getExist().equals("true")) {
                Log.i("cyy", "登录");
                toast("登录成功");
                //存在SharedPeference里
                SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("username",user.getUsername());
                editor.putString("password",user.getPassword());
                editor.putString("autoLogin","true");
                editor.commit();

                startActivity(HomeActivity.class);
            } else {
                Log.i("cyy", "用户名重复，请重新注册");
                toast("登录失败");
            }
        }
    };
}