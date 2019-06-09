package com.hjq.demo.Problems;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chiyuanye.demo.entity.ProblemsHolder;
import com.chiyuanye.demo.entity.Record;
import com.chiyuanye.demo.entity.TotalProblems;
import com.chiyuanye.demo.utils.ProblemHelper;
import com.chiyuanye.demo.utils.SQLiteHelper;
import com.hjq.demo.R;
import com.hjq.demo.common.MyActivity;
import com.hjq.dialog.MessageDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.ittiger.database.SQLiteDB;
import cn.ittiger.database.SQLiteDBConfig;
import cn.ittiger.database.SQLiteDBFactory;

public final class WritingLoadActivity extends MyActivity {


    @BindView(R.id.tv_problem_loading)
    TextView mProblemLoading;

    @BindView(R.id.btn_writing)
    Button mWriting;

    @BindView(R.id.et_fill)
    EditText mFill;


    //存我的答案
    String[] myAnswer = new String[1];

    TotalProblems problem;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_writing_load;
    }

    @Override
    protected int getTitleId() {
        return R.id.tb_copy_title;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initView() {

        Intent intent = getIntent();
        SQLiteDBConfig problemConfig = new SQLiteDBConfig(this, "/CYY/", "ProblemsTotal.db");
        SQLiteDB problemDB = SQLiteDBFactory.createSQLiteDB(problemConfig);
        SQLiteDBConfig recordConfig = new SQLiteDBConfig(this, "/CYY/", "Records.db");
        SQLiteDB recordDB = SQLiteDBFactory.createSQLiteDB(recordConfig);
//        toast(intent.getStringExtra("ProblemType") + " "
//                +intent.getStringExtra("SubProblemType") + " " + intent.getStringExtra("ExamType")
//        + " " + intent.getStringExtra("ShowType"));


        if (intent.getStringExtra("ShowType").equals("Search")) {
            List<Record> list = recordDB.queryAll(Record.class);
            int id = SQLiteHelper.InfoToID((ArrayList<Record>) list, intent.getStringExtra("SubProblemType"),
                    Integer.parseInt(intent.getStringExtra("SubID")));
            problem = problemDB.query(TotalProblems.class, String.valueOf(id));
        } else {

            Record r = ProblemHelper.fetchUserProblem(intent, recordDB);

            //通过唯一id找problem
            problem = problemDB.query(TotalProblems.class, String.valueOf(r.getID()));
        }
        //显示题目
        mProblemLoading.setText(problem.getSubID() + ". " + problem.getContent());

        Log.i("CYY", problem.getAnswer());
        //自适应标题
        if (intent.getStringExtra("ShowType").equals("User")) {
            setTitle("自主刷题");
        }
        if (intent.getStringExtra("ShowType").equals("Teacher")) {
            setTitle("作业");
        }
        //if (intent.getStringExtra("ShowType").equals("User")){setTitle("自主刷题");}


    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_writing})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_writing:
                if (mProblemLoading.getText().toString().length() < 50) {
                    new MessageDialog.Builder(this)
                            .setMessage("字数可能不太够?")
                            .setConfirm("没事")
                            .setCancel("再扯点") // 设置 null 表示不显示取消按钮
                            .setListener(new MessageDialog.OnListener() {

                                @Override
                                public void onConfirm(Dialog dialog) {
                                    //通过holder把答案和这次的problem对象向后传
                                    myAnswer[0] = mFill.getText().toString();
                                    //ProblemsHolder.getInstance().setProblemType("User");
                                    ProblemsHolder.getInstance().setThisProblem(problem);
                                    ProblemsHolder.getInstance().setMyAnswer1(myAnswer);
                                    startActivity(ProblemResultActivity.class);
                                    finish();
                                }

                                @Override
                                public void onCancel(Dialog dialog) {

                                }
                            })
                            .show();
                } else {
                    myAnswer[0] = mFill.getText().toString();
                    //ProblemsHolder.getInstance().setProblemType("User");
                    ProblemsHolder.getInstance().setThisProblem(problem);
                    ProblemsHolder.getInstance().setMyAnswer1(myAnswer);
                    startActivity(ProblemResultActivity.class);
                    finish();
                }
                break;
            default:
                break;
        }
    }
}