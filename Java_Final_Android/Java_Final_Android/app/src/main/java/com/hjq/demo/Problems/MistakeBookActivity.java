package com.hjq.demo.Problems;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.chiyuanye.demo.entity.Record;
import com.chiyuanye.demo.entity.TotalProblems;
import com.hjq.demo.R;
import com.hjq.demo.common.MyActivity;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import butterknife.BindView;
import cn.ittiger.database.SQLiteDB;
import cn.ittiger.database.SQLiteDBConfig;
import cn.ittiger.database.SQLiteDBFactory;

public final class MistakeBookActivity extends MyActivity {

    @BindView(R.id.tv_mistake_problem_loading)
    TextView mMistakeProblemLoading;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mistake_book;
    }

    @Override
    protected int getTitleId() {
        return R.id.mistake_title;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initView() {
        SQLiteDBConfig recordConfig = new SQLiteDBConfig(this, "/CYY/", "Records.db");
        SQLiteDB recordDB1 = SQLiteDBFactory.createSQLiteDB(recordConfig);
        SQLiteDBConfig problemConfig = new SQLiteDBConfig(this, "/CYY/", "ProblemsTotal.db");
        SQLiteDB problemDB1 = SQLiteDBFactory.createSQLiteDB(problemConfig);

        List<Record> list1 = recordDB1.queryAll(Record.class);
        Log.i("CYY", String.valueOf(list1.size()));
        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i).getScore() != 0) {
                Log.i("CYY", String.valueOf(list1.get(i).getID()));
                Log.i("CYY", String.valueOf(list1.get(i).getScore()));
                Log.i("CYY", String.valueOf(list1.get(i).getFinishedTimes()));
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<h2>错题汇集</h2>可以根据子题号和子题型在自主选题中选择多加练习");

        //List<Record> list1 = recordDB1.queryAll(Record.class);
        PriorityQueue<Record> recordQueue = new PriorityQueue<>(new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                if (o1.getScore() == 0 && o2.getScore() != 0)
                    return 1;
                else if  (o1.getScore() != 0 && o2.getScore() == 0)
                    return -1;
                else if (o1.getScore() != 0 && o2.getScore() != 0) {
                    if (o1.getScore() > o2.getScore())
                        return 1;
                    else if (o1.getScore() < o2.getScore())
                        return -1;
                    else
                        return 0;
                } else return 0;
            }
        });

        sb.append("<h3>阅读部分</h3>");
        for (int i = 0; i < list1.size(); i++) {
            if(list1.get(i).getProblemType().equals("Reading")) {
                recordQueue.add(list1.get(i));
            }
        }
        for (int i = 0; i < 8; i++) {
            Record temp = recordQueue.poll();
            TotalProblems problem = problemDB1.query(TotalProblems.class, String.valueOf(temp.getID()));
            sb.append("<p>" + (i + 1) + ". 子题型:" + temp.getSubProblemType() + ";</br>"
            + "子题号:" + temp.getSubID() + ";</br>"
            + "正确率：" + temp.getScore() + ";</br>"
            + "概览:" + problem.getContent().substring(0, 100) + "</p>");
        }

        recordQueue.clear();
        sb.append("<h3>词汇部分</h3>");
        for (int i = 0; i < list1.size(); i++) {
            if(list1.get(i).getProblemType().equals("Word")) {
                recordQueue.add(list1.get(i));
            }
        }
        for (int i = 0; i < 8; i++) {
            Record temp = recordQueue.poll();
            TotalProblems problem = problemDB1.query(TotalProblems.class, String.valueOf(temp.getID()));
            sb.append("<p>" + (i + 1) + ". 子题型:" + temp.getSubProblemType() + ";</br>"
                    + "子题号:" + temp.getSubID() + ";</br>"
                    + "正确率：" + temp.getScore() + ";</br>"
                    + "概览:" + problem.getContent() + "</p>");
        }

        recordQueue.clear();
        sb.append("<h3>完型部分</h3>");
        for (int i = 0; i < list1.size(); i++) {
            if(list1.get(i).getProblemType().equals("Cloze")) {
                recordQueue.add(list1.get(i));
            }
        }
        for (int i = 0; i < 8; i++) {
            Record temp = recordQueue.poll();
            TotalProblems problem = problemDB1.query(TotalProblems.class, String.valueOf(temp.getID()));
            sb.append("<p>" + (i + 1) + ". 子题型:" + temp.getSubProblemType() + ";</br>"
                    + "子题号:" + temp.getSubID() + ";</br>"
                    + "正确率：" + temp.getScore() + ";</br>"
                    + "概览:" + problem.getContent().substring(0, 100) + "</p>");
        }

//        recordQueue.clear();
//        sb.append("<h3>作文部分</h3>");
//        for (int i = 0; i < list1.size(); i++) {
//            if(list1.get(i).getProblemType().equals("Writing")) {
//                recordQueue.add(list1.get(i));
//            }
//        }
//        for (int i = 0; i < 8; i++) {
//            Record temp = recordQueue.poll();
//            TotalProblems problem = problemDB1.query(TotalProblems.class, String.valueOf(temp.getID()));
//            sb.append("<p>" + (i + 1) + ". 子题型:" + temp.getSubProblemType() + ";</br>"
//                    + "子题号:" + temp.getSubID() + ";</br>"
//                    + "正确率：" + temp.getScore() + ";</br>"
//                    + "概览:" + problem.getContent().substring(0, 80) + "</p>");
//        }

        recordQueue.clear();
        sb.append("<h3>句子部分</h3>");
        for (int i = 0; i < list1.size(); i++) {
            if(list1.get(i).getProblemType().equals("Sentence")) {
                recordQueue.add(list1.get(i));
            }
        }
        for (int i = 0; i < 8; i++) {
            Record temp = recordQueue.poll();
            TotalProblems problem = problemDB1.query(TotalProblems.class, String.valueOf(temp.getID()));
            sb.append("<p>" + (i + 1) + ". 子题型:" + temp.getSubProblemType() + ";</br>"
                    + "子题号:" + temp.getSubID() + ";</br>"
                    + "正确率：" + temp.getScore() + ";</br>"
                    + "概览:" + problem.getContent() + "</p>");
        }

        mMistakeProblemLoading.setText(Html.fromHtml(sb.toString()));



    }

    @Override
    protected void initData() {

    }
}