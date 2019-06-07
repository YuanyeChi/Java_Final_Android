package com.hjq.demo.ui.activity;

import android.Manifest;
import android.content.SharedPreferences;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ansen.http.net.HTTPCaller;
import com.ansen.http.net.NameValuePair;
import com.ansen.http.net.RequestDataCallback;
import com.chiyuanye.demo.entity.User;
import com.gyf.barlibrary.ImmersionBar;
import com.hjq.demo.R;
import com.hjq.demo.common.MyActivity;
import com.hjq.demo.helper.InputTextHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 注册界面
 */
public final class RegisterActivity extends MyActivity {



    @BindView(R.id.et_register_code)
    EditText mCodeView;

    @BindView(R.id.et_register_password1)
    EditText mPasswordView1;
    @BindView(R.id.et_register_password2)
    EditText mPasswordView2;

    @BindView(R.id.btn_register_commit)
    Button mCommitView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected int getTitleId() {
        return R.id.tb_register_title;
    }

    @Override
    protected void initView() {
        new InputTextHelper.Builder(this)
                .setMain(mCommitView)
                .addView(mCodeView)
                .addView(mPasswordView1)
                .addView(mPasswordView2)
                .build();
    }

    @Override
    protected void initData() {
//        getHandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                finishResult(RESULT_OK);
//            }
//        }, 2000);
    }

    @Override
    public void onLeftClick(View v) {
        // 跳转到登录界面
        startActivity(LoginActivity.class);
    }

    @Override
    protected ImmersionBar statusBarConfig() {
        // 不要把整个布局顶上去
        return super.statusBarConfig().keyboardEnable(true);
    }

    @OnClick({R.id.btn_register_commit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register_commit: //提交注册
                if (mPasswordView1.getText().toString().trim().equals("") || mCodeView.getText().toString().trim().equals("")) {
                    toast("请输入完整的及密码");
                } else if (!mPasswordView1.getText().toString().equals(mPasswordView2.getText().toString())) {
                    toast(getString(R.string.register_password_input_error));
                } else {
                    List<NameValuePair> postParam = new ArrayList<>();
                    postParam.add(new NameValuePair("username",mCodeView.getText().toString()));
                    postParam.add(new NameValuePair("password",mPasswordView1.getText().toString()));
                    postParam.add(new NameValuePair("Exist","fuck"));
                    HTTPCaller.getInstance().post(User.class, "http://155.138.230.57:8080/Java_Final/register.do", null, postParam, requestDataCallback);
                }
                break;
        }
    }


    private RequestDataCallback requestDataCallback = new RequestDataCallback<User>() {
        @Override
        public void dataCallback(User user) {
            if (user.getExist().equals("false")) {
                Log.i("cyy", "注册成功");
                toast("注册成功");
                //存在SharedPeference里
                SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("username",user.getUsername());
                editor.putString("password",user.getPassword());
                editor.apply();

                startActivity(HomeActivity.class);
            } else {
                Log.i("cyy", "用户名重复，请重新注册");
                toast("用户名重复，请重新注册");
            }
        }
    };
}
