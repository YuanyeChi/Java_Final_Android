package com.hjq.demo.ui.fragment;

import android.view.View;

import com.hjq.demo.Problems.CLozeSelectActivity;
import com.hjq.demo.Problems.IntelligentActivity;
import com.hjq.demo.Problems.MistakeBookActivity;
import com.hjq.demo.Problems.ReadingSelectActivity;
import com.hjq.demo.Problems.SentenceSelectActivity;
import com.hjq.demo.Problems.WordSelectActivity;
import com.hjq.demo.Problems.WritingSelectActivity;
import com.hjq.demo.R;
import com.hjq.demo.common.MyLazyFragment;
import com.hjq.demo.ui.activity.CopyActivity;

import butterknife.OnClick;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 可进行拷贝的副本
 */
public final class IntelligentFragment extends MyLazyFragment<CopyActivity> {

    public static IntelligentFragment newInstance() {
        return new IntelligentFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_intelligent;
    }

    @Override
    protected int getTitleId() {
        return R.id.tb_intelligent_problem_title;
    }

    @Override
    protected void initView() {

    }
    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    protected void initData() {

    }
    @OnClick({R.id.btn_intelligent_generate,R.id.btn_mistake_book})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_intelligent_generate:
                startActivity(IntelligentActivity.class);
                break;
            case R.id.btn_mistake_book:
                startActivity(MistakeBookActivity.class);
                break;
            default:
                break;
        }
    }
}