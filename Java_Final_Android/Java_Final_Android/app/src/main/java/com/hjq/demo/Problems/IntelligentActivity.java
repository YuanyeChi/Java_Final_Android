package com.hjq.demo.Problems;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;

import com.chiyuanye.demo.entity.ProblemsHolder;
import com.chiyuanye.demo.entity.Record;
import com.hjq.base.BaseDialog;
import com.hjq.demo.R;
import com.hjq.demo.common.MyActivity;
import com.hjq.dialog.MenuDialog;
import com.hjq.dialog.MessageDialog;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import cn.ittiger.database.SQLiteDB;
import cn.ittiger.database.SQLiteDBConfig;
import cn.ittiger.database.SQLiteDBFactory;

public final class IntelligentActivity extends MyActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_intelligent;
    }

    @Override
    protected int getTitleId() {
        return R.id.intelligent_activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initView() {
        SQLiteDBConfig recordConfig = new SQLiteDBConfig(this, "/CYY/", "Records.db");
        SQLiteDB recordDB = SQLiteDBFactory.createSQLiteDB(recordConfig);
        SQLiteDBConfig problemConfig = new SQLiteDBConfig(this, "/CYY/", "ProblemsTotal.db");
        SQLiteDB problemDB = SQLiteDBFactory.createSQLiteDB(problemConfig);

        List<Record> list = new ArrayList();
        list = recordDB.queryAll(Record.class);

//        List<String> data1 = new ArrayList<>();
//        data1.add("我想多刷刷新题");
//        data1.add("练练薄弱类型的题");
//        new MenuDialog.Builder(this)
//                .setCancel(null) // 设置 null 表示不显示取消按钮
//                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
//                .setList(data1)
//                .setListener(new MenuDialog.OnListener() {
//
//                    @Override
//                    public void onSelected(Dialog dialog, int position, String text) {
//                        if(position == 0){
//                            intent.putExtra("ExamType", "gk");
//                            startActivity(intent);
//                            finish();
//                        }else{
//                            intent.putExtra("ExamType", "mn");
//                            startActivity(intent);
//                            finish();
//                        }
//                    }
//                    @Override
//                    public void onCancel(Dialog dialog) {
//                        toast("取消了");
//                    }
//                })
//                .setGravity(Gravity.CENTER)
//                .setAnimStyle(BaseDialog.AnimStyle.SCALE)
//                .show();

        final List<Record> finalList = list;
        //考虑:1.平均正确率低的题型多生成(除去没做的) 2.是否做过 3. 相同类型下生成正确率低
        PriorityQueue<Record> q = new PriorityQueue<>(new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {

                Iterator iterator = finalList.iterator();
                float score1 = 0;
                float score2 = 0;
                int num1 = 0;
                int num2 = 0;
                //子类型相同
                while (iterator.hasNext()) {
                    Record temp = (Record) iterator.next();
                    if (temp.getSubProblemType().equals(o1.getSubProblemType())) {
                        score1 += temp.getScore();
                        num1++;
                    }
                    if (temp.getSubProblemType().equals(o2.getSubProblemType())) {
                        score2 += temp.getScore();
                        num2++;
                    }
                }

                if (o1.getFinishedTimes() == 0 && o2.getFinishedTimes() != 0) {
                    return -1;
                } else if (o1.getFinishedTimes() != 0 && o2.getFinishedTimes() == 0) {
                    return 1;
                } else {
                    if (o1.getSubProblemType().equals(o2.getSubProblemType())) {
                        return (int) (o1.getScore() - o2.getScore());//同一类
                    } else {
                        return (int) ((score1 / num1) - (score2 / num2));
                    }
                }
            }
        });
        q.addAll(list);

        Record record = q.poll();
        Log.i("CYY", String.valueOf(record.getID()));
        if (record.getProblemType().equals("Reading") || record.getProblemType().equals("Cloze")) {
            Intent intent = new Intent(IntelligentActivity.this, SelectLoadActivity.class);
            intent.putExtra("ShowType", "System");
            intent.putExtra("ProblemType", record.getProblemType());
            intent.putExtra("SubProblemType", record.getSubProblemType());
            intent.putExtra("ExamType", record.getExamType());
            ProblemsHolder.getInstance().setProblemType("System");
            startActivity(intent);
            finish();
        } else if (record.getProblemType().equals("Sentence") || record.getProblemType().equals("Word")) {
            Intent intent = new Intent(IntelligentActivity.this, FillingLoadActivity.class);
            intent.putExtra("ShowType", "System");
            intent.putExtra("ProblemType", record.getProblemType());
            intent.putExtra("SubProblemType", record.getSubProblemType());
            intent.putExtra("ExamType", record.getExamType());
            ProblemsHolder.getInstance().setProblemType("System");
            startActivity(intent);
            finish();
        } else { Intent intent = new Intent(IntelligentActivity.this, WritingLoadActivity.class);
            intent.putExtra("ShowType", "System");
            intent.putExtra("ProblemType", record.getProblemType());
            intent.putExtra("SubProblemType", record.getSubProblemType());
            intent.putExtra("ExamType", record.getExamType());
            ProblemsHolder.getInstance().setProblemType("System");
            startActivity(intent);
            finish();}

    }

    @Override
    protected void initData() {

    }
}