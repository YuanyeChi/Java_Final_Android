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

public final class ReadingSelectActivity extends MyActivity {

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
        final Intent intent = new Intent(ReadingSelectActivity.this, SelectLoadActivity.class);
        intent.putExtra("ShowType", "User");
        intent.putExtra("ProblemType", "Reading");
        List<String> data1 = new ArrayList<>();
            data1.add("推理判断");
            data1.add("细节理解");
            data1.add("主旨要义");
            data1.add("词义推测");
            data1.add("往年真题");
        new MenuDialog.Builder(this)
                .setCancel(null) // 设置 null 表示不显示取消按钮
                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                .setList(data1)
                .setListener(new MenuDialog.OnListener() {

                    @Override
                    public void onSelected(Dialog dialog, int position, String text) {
                        ProblemsHolder.getInstance().setProblemType("User");
                        if(position == 0) {
                            intent.putExtra("SubProblemType", "Deduce");
                            showProblemNum0(intent);
                        }else if (position == 1) {
                            intent.putExtra("SubProblemType", "Detail");
                            showProblemNum0(intent);
                        }else if (position == 2) {
                            intent.putExtra("SubProblemType", "Subject");
                            showProblemNum0(intent);
                        }else if (position == 3) {
                            intent.putExtra("SubProblemType", "WordMeaning");
                            showProblemNum0(intent);
                        }else{
                            intent.putExtra("SubProblemType", "PastYear");
                            showProblemNum1(intent);
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
                            intent.putExtra("ExamType", "gk");
                            startActivity(intent);
                            finish();
                        }else {
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
            data1.add("高考题");
        new MenuDialog.Builder(this)
                .setCancel(null) // 设置 null 表示不显示取消按钮
                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                .setList(data1)
                .setListener(new MenuDialog.OnListener() {

                    @Override
                    public void onSelected(Dialog dialog, int position, String text) {
                        if(position == 0) {
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