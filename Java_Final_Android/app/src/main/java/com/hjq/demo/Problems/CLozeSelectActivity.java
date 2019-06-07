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

public final class CLozeSelectActivity extends MyActivity {

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
                            Intent intent = new Intent(CLozeSelectActivity.this, SelectLoadActivity.class);
                            ProblemsHolder.getInstance().setProblemType("User");
                            intent.putExtra("ShowType", "User");
                            intent.putExtra("ProblemType", "Cloze");
                            intent.putExtra("SubProblemType", "Cloze");
                            intent.putExtra("ExamType", "gk");
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

    @Override
    protected void initData() {

    }
}