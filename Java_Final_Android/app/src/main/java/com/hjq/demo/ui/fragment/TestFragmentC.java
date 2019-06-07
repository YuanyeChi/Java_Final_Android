package com.hjq.demo.ui.fragment;

import android.view.View;
import android.widget.ImageView;

import com.hjq.demo.Problems.SelectLoadActivity;
import com.hjq.demo.R;
import com.hjq.demo.common.MyLazyFragment;
import com.hjq.demo.ui.activity.HomeActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目框架使用示例
 */
public final class TestFragmentC extends MyLazyFragment<HomeActivity> {

    @BindView(R.id.iv_test_image)
    ImageView mImageView;

    public static TestFragmentC newInstance() {
        return new TestFragmentC();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_c;
    }

    @Override
    protected int getTitleId() {
        return R.id.tb_test_c_title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @OnClick({R.id.btn_test_swipe_disable})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test_swipe_disable:
                startActivity(SelectLoadActivity.class);
                break;
            default:
                break;
        }
    }
}