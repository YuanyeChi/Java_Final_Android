package com.hjq.demo.Problems;

import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;

import com.chiyuanye.demo.entity.ProblemsHolder;
import com.hjq.base.BaseDialog;
import com.hjq.demo.R;
import com.hjq.demo.common.MyActivity;
import com.hjq.dialog.MenuDialog;

import java.util.ArrayList;
import java.util.List;

public final class SentenceSelectActivity extends MyActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_copy;
    }

    @Override
    protected int getTitleId() {
        return R.id.tb_copy_title;
    }

    @Override
    protected void initView() {
        final Intent intent = new Intent(SentenceSelectActivity.this, FillingLoadActivity.class);
        intent.putExtra("ShowType", "User");
        intent.putExtra("ProblemType", "Sentence");
        List<String> data1 = new ArrayList<>();
            data1.add("并列句");
            data1.add("倒装句");
            data1.add("定语从句");
            data1.add("名词性从句");
            data1.add("其他特殊句");
            data1.add("强调句");
            data1.add("状语从句");
        new MenuDialog.Builder(this)
                .setCancel(null) // 设置 null 表示不显示取消按钮
                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                .setList(data1)
                .setListener(new MenuDialog.OnListener() {

                    @Override
                    public void onSelected(Dialog dialog, int position, String text) {
                        ProblemsHolder.getInstance().setProblemType("User");
                        if(position == 0) {
                            intent.putExtra("SubProblemType", "BingLie");
                            showProblemNum0(intent);
                        }else if (position == 1) {
                            intent.putExtra("SubProblemType", "DaoZhuang");
                            showProblemNum0(intent);
                        }else if (position == 2) {
                            intent.putExtra("SubProblemType", "DingYu");
                            showProblemNum0(intent);
                        }else if (position == 3) {
                            intent.putExtra("SubProblemType", "MingCiXing");
                            showProblemNum0(intent);
                        }else if (position == 4) {
                            intent.putExtra("SubProblemType", "QiTa");
                            showProblemNum1(intent);
                        }else if (position == 5) {
                            intent.putExtra("SubProblemType", "QiangDiao");
                            showProblemNum0(intent);
                        }else {
                            intent.putExtra("SubProblemType", "ZhuangYu");
                            showProblemNum0(intent);
                        };
                    }

                    @Override
                    public void onCancel(Dialog dialog) {
                        toast("取消了");
                    }
                })
                .setGravity(Gravity.CENTER)
                .setAnimStyle(BaseDialog.AnimStyle.SCALE)
                .show();
    }

    @Override
    protected void initData() {

    }

    protected void showProblemNum0(final Intent intent) {
        List<String> data1 = new ArrayList<>();
            data1.add("练习题");
            data1.add("高考题");
            data1.add("模拟题");
        new MenuDialog.Builder(this)
                .setCancel(null) // 设置 null 表示不显示取消按钮
                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                .setList(data1)
                .setListener(new MenuDialog.OnListener() {

                    @Override
                    public void onSelected(Dialog dialog, int position, String text) {
                        if(position == 0) {
                            intent.putExtra("ExamType", "lx");
                            startActivity(intent);
                            finish();
                        }else if(position == 1){
                            intent.putExtra("ExamType", "gk");
                            startActivity(intent);
                            finish();
                        }else{
                            intent.putExtra("ExamType", "mn");
                            startActivity(intent);
                            finish();
                        }
                    }
                    @Override
                    public void onCancel(Dialog dialog) {
                        toast("取消了");
                    }
                })
                .setGravity(Gravity.CENTER)
                .setAnimStyle(BaseDialog.AnimStyle.SCALE)
                .show();
    }

    protected void showProblemNum1(final Intent intent) {
        List<String> data1 = new ArrayList<>();
            data1.add("练习题");
            data1.add("高考题");
        new MenuDialog.Builder(this)
                .setCancel(null) // 设置 null 表示不显示取消按钮
                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                .setList(data1)
                .setListener(new MenuDialog.OnListener() {

                    @Override
                    public void onSelected(Dialog dialog, int position, String text) {
                        if(position == 0) {
                            intent.putExtra("ExamType", "lx");
                            startActivity(intent);
                        }else{
                            intent.putExtra("ExamType", "gk");
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancel(Dialog dialog) {
                        toast("取消了");
                    }
                })
                .setGravity(Gravity.CENTER)
                .setAnimStyle(BaseDialog.AnimStyle.SCALE)
                .show();
    }
}