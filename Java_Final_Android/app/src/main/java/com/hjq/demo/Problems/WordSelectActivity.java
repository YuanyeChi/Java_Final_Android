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

public final class WordSelectActivity extends MyActivity {

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
        final Intent intent = new Intent(WordSelectActivity.this, FillingLoadActivity.class);
        intent.putExtra("ShowType", "User");
        intent.putExtra("ProblemType", "Word");
        List<String> data1 = new ArrayList<>();
            data1.add("形容词");
            data1.add("冠词");
            data1.add("非谓语动词");
            data1.add("名词");
            data1.add("谓语动词");
            data1.add("介词");
            data1.add("代词");
            data1.add("动词短语");
            data1.add("副词");
        new MenuDialog.Builder(this)
                .setCancel(null) // 设置 null 表示不显示取消按钮
                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                .setList(data1)
                .setListener(new MenuDialog.OnListener() {

                    @Override
                    public void onSelected(Dialog dialog, int position, String text) {
                        ProblemsHolder.getInstance().setProblemType("User");
                        if(position == 0) {
                            intent.putExtra("SubProblemType", "Adjective");
                            showProblemNum0(intent);
                        }else if (position == 1) {
                            intent.putExtra("SubProblemType", "Article");
                            showProblemNum0(intent);
                        }else if (position == 2) {
                            intent.putExtra("SubProblemType", "NonPredicateVerb");
                            showProblemNum0(intent);
                        }else if (position == 3) {
                            intent.putExtra("SubProblemType", "Noun");
                            showProblemNum0(intent);
                        }else if (position == 4) {
                            intent.putExtra("SubProblemType", "PredicateVerb");
                            showProblemNum0(intent);
                        }else if (position == 5) {
                            intent.putExtra("SubProblemType", "Preposition");
                            showProblemNum0(intent);
                        }else if (position == 6) {
                            intent.putExtra("SubProblemType", "Pronoun");
                            showProblemNum0(intent);
                        }else if (position == 7) {
                            intent.putExtra("SubProblemType", "Verbphrase");
                            showProblemNum0(intent);
                        }else {
                            intent.putExtra("SubProblemType", "Adverb");
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
}