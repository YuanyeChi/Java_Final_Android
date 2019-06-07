package com.hjq.demo.Problems;

import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;

import com.hjq.base.BaseDialog;
import com.hjq.demo.R;
import com.hjq.demo.common.MyActivity;
import com.hjq.dialog.MenuDialog;

import java.util.ArrayList;
import java.util.List;

import cn.ittiger.database.SQLiteDB;
import cn.ittiger.database.SQLiteDBConfig;
import cn.ittiger.database.SQLiteDBFactory;

public final class TotalSelectActivity extends MyActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_total;
    }

    @Override
    protected int getTitleId() {
        return R.id.tb_total_select__title;
    }

    @Override
    protected void initView() {
        List<String> problemType = new ArrayList<>();
        problemType.add("形容词");
        problemType.add("冠词");
        problemType.add("非谓语动词");
        problemType.add("名词");
        problemType.add("谓语动词");
        problemType.add("介词");
        problemType.add("代词");
        problemType.add("动词短语");
        problemType.add("副词");

        problemType.add("并列句");
        problemType.add("倒装句");
        problemType.add("定语从句");
        problemType.add("名词性从句");
        problemType.add("其他特殊句");
        problemType.add("强调句");
        problemType.add("状语从句");

        problemType.add("完形填空");

        problemType.add("推理判断");
        problemType.add("细节理解");
        problemType.add("主旨要义");
        problemType.add("词义推测");
        problemType.add("往年真题");

        problemType.add("应用文写作");
        problemType.add("概要写作");
        problemType.add("读后续写");


        new MenuDialog.Builder(this)
                .setCancel(null) // 设置 null 表示不显示取消按钮
                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                .setList(problemType)
                .setListener(new MenuDialog.OnListener() {
                    @Override
                    public void onSelected(Dialog dialog, int position, String text) {
                        if(position == 0) {
                            Intent intent = new Intent(TotalSelectActivity.this, FillingLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "Adjective");
                            showProblemNum0(intent, 30);
                        } else if(position == 1) {
                            Intent intent = new Intent(TotalSelectActivity.this, FillingLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "Article");
                            showProblemNum0(intent, 29);
                        }else if(position == 2) {
                            Intent intent = new Intent(TotalSelectActivity.this, FillingLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "NonPredicateVerb");
                            showProblemNum0(intent, 110);
                        }else if(position == 3) {
                            Intent intent = new Intent(TotalSelectActivity.this, FillingLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "Noun");
                            showProblemNum0(intent, 32);
                        }else if(position == 4) {
                            Intent intent = new Intent(TotalSelectActivity.this, FillingLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "PredicateVerb");
                            showProblemNum0(intent, 127);
                        }else if(position == 5) {
                            Intent intent = new Intent(TotalSelectActivity.this, FillingLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "Preposition");
                            showProblemNum0(intent, 43);
                        }else if(position == 6) {
                            Intent intent = new Intent(TotalSelectActivity.this, FillingLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "Pronoun");
                            showProblemNum0(intent, 33);
                        }else if(position == 7) {
                            Intent intent = new Intent(TotalSelectActivity.this, FillingLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "Verbphrase");
                            showProblemNum0(intent, 39);
                        }else if(position == 8) {
                            Intent intent = new Intent(TotalSelectActivity.this, FillingLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "Adverb");
                            showProblemNum0(intent, 26);
                        }else if(position == 9) {
                            Intent intent = new Intent(TotalSelectActivity.this, FillingLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "BingLie");
                            showProblemNum0(intent, 18);
                        }else if(position == 10) {
                            Intent intent = new Intent(TotalSelectActivity.this, FillingLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "DaoZhuang");
                            showProblemNum0(intent, 23);
                        }else if(position == 11) {
                            Intent intent = new Intent(TotalSelectActivity.this, FillingLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "DingYu");
                            showProblemNum0(intent, 64);
                        }else if(position == 12) {
                            Intent intent = new Intent(TotalSelectActivity.this, FillingLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "MingCiXing");
                            showProblemNum0(intent, 63);
                        }else if(position == 13) {
                            Intent intent = new Intent(TotalSelectActivity.this, FillingLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "QiTa");
                            showProblemNum0(intent, 12);
                        }else if(position == 14) {
                            Intent intent = new Intent(TotalSelectActivity.this, FillingLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "QiangDiao");
                            showProblemNum0(intent, 16);
                        }else if(position == 15) {
                            Intent intent = new Intent(TotalSelectActivity.this, FillingLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "ZhuangYu");
                            showProblemNum0(intent, 54);
                        }else if(position == 16) {
                            Intent intent = new Intent(TotalSelectActivity.this, SelectLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("ProblemType", "Cloze");
                            showProblemNum0(intent, 76);
                        }else if(position == 17) {
                            Intent intent = new Intent(TotalSelectActivity.this, SelectLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "Deduce");
                            showProblemNum0(intent, 52);
                        }else if(position == 18) {
                            Intent intent = new Intent(TotalSelectActivity.this, SelectLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "Detail");
                            showProblemNum0(intent, 44);
                        }else if(position == 19) {
                            Intent intent = new Intent(TotalSelectActivity.this, SelectLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "Subject");
                            showProblemNum0(intent, 39);
                        }else if(position == 20) {
                            Intent intent = new Intent(TotalSelectActivity.this, SelectLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "WordMeaning");
                            showProblemNum0(intent, 44);
                        }else if(position == 21) {
                            Intent intent = new Intent(TotalSelectActivity.this, SelectLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "PastYear");
                            showProblemNum0(intent, 110);
                        }else if(position == 22) {
                            Intent intent = new Intent(TotalSelectActivity.this, WritingLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "Application");
                            showProblemNum0(intent, 50);
                        }else if(position == 23) {
                            Intent intent = new Intent(TotalSelectActivity.this, WritingLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "SummaryWriting");
                            showProblemNum0(intent, 10);
                        }else if(position == 24) {
                            Intent intent = new Intent(TotalSelectActivity.this, WritingLoadActivity.class);
                            intent.putExtra("ShowType", "Search");
                            intent.putExtra("SubProblemType", "WriteAfterReading");
                            showProblemNum0(intent, 11);
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

    protected void showProblemNum0(final Intent intent, int subNum) {
        List<String> data1 = new ArrayList<>();
        for (int i = 1; i <= subNum; i++) {
            data1.add(String.valueOf(i));
        }
        new MenuDialog.Builder(this)
                .setCancel(null) // 设置 null 表示不显示取消按钮
                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                .setList(data1)
                .setListener(new MenuDialog.OnListener() {

                    @Override
                    public void onSelected(Dialog dialog, int position, String text) {
                            intent.putExtra("SubID", String.valueOf(position + 1));
                            startActivity(intent);
                            finish();
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