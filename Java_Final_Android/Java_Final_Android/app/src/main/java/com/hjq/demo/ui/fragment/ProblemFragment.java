package com.hjq.demo.ui.fragment;

import com.hjq.demo.R;
import com.hjq.demo.common.MyLazyFragment;
import com.hjq.demo.ui.activity.CopyActivity;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 可进行拷贝的副本
 */
public final class ProblemFragment extends MyLazyFragment<CopyActivity> {

    public static ProblemFragment newInstance() {
        return new ProblemFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_copy;
    }

    @Override
    protected int getTitleId() {
        return R.id.tb_copy_title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}