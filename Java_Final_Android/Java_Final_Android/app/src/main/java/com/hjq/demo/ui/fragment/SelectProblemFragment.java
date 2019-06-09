package com.hjq.demo.ui.fragment;

import android.app.Dialog;
import android.view.View;

import com.hjq.demo.Problems.CLozeSelectActivity;
import com.hjq.demo.Problems.ReadingSelectActivity;
import com.hjq.demo.Problems.SentenceSelectActivity;
import com.hjq.demo.Problems.TotalSelectActivity;
import com.hjq.demo.Problems.WordSelectActivity;
import com.hjq.demo.Problems.WritingSelectActivity;
import com.hjq.demo.R;
import com.hjq.demo.common.MyLazyFragment;
import com.hjq.demo.ui.activity.HomeActivity;
import com.hjq.dialog.DateDialog;

import butterknife.OnClick;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目框架使用示例
 */
public final class SelectProblemFragment extends MyLazyFragment<HomeActivity> {

    public static SelectProblemFragment newInstance() {
        return new SelectProblemFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select_problem;
    }

    @Override
    protected int getTitleId() {
        return R.id.tb_select_problem_title;
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

    @OnClick({R.id.btn_cloze,R.id.btn_word,R.id.btn_sentence,R.id.btn_reading,R.id.btn_writing,R.id.btn_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cloze:
                startActivity(CLozeSelectActivity.class);
                break;
            case R.id.btn_word:
                startActivity(WordSelectActivity.class);
                break;
            case R.id.btn_sentence:
                startActivity(SentenceSelectActivity.class);
                break;
            case R.id.btn_reading:
                startActivity(ReadingSelectActivity.class);
                break;
            case R.id.btn_writing:
                startActivity(WritingSelectActivity.class);
                break;
            case R.id.btn_search:
                startActivity(TotalSelectActivity.class);
                break;
            default:
                break;
        }
    }
}