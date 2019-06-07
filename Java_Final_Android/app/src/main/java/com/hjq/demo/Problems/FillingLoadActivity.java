package com.hjq.demo.Problems;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public final class FillingLoadActivity extends MyActivity{

    @BindView(R.id.tv_problem_loading)
    TextView mProblemLoading;

    @BindView(R.id.et_fill)
    TextView mFilling;

    @BindView(R.id.btn_next)
    Button next;

    //存我的答案
    String[] myAnswer = new String[1];

    TotalProblems problem;

    int wordNum = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_filling_load;
    }

    @Override
    protected int getTitleId() {
        return R.id.tb_copy_title;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initView() {

        Intent intent = getIntent();

        //两个数据库的参数配置
        SQLiteDBConfig recordConfig = new SQLiteDBConfig(this, "/CYY/" , "Records.db");
        SQLiteDB recordDB = SQLiteDBFactory.createSQLiteDB(recordConfig);
        SQLiteDBConfig problemConfig = new SQLiteDBConfig(this, "/CYY/" , "ProblemsTotal.db");
        SQLiteDB problemDB = SQLiteDBFactory.createSQLiteDB(problemConfig);
//
//        toast(intent.getStringExtra("ProblemType") + " "
//                +intent.getStringExtra("SubProblemType") + " " + intent.getStringExtra("ExamType")
//        + " " + intent.getStringExtra("ShowType"));

        if(intent.getStringExtra("ShowType").equals("Search")) {
            List<Record> list = recordDB.queryAll(Record.class);
            int id = SQLiteHelper.InfoToID((ArrayList<Record>) list, intent.getStringExtra("SubProblemType"),
                    Integer.parseInt(intent.getStringExtra("SubID")));
            problem = problemDB.query(TotalProblems.class, String.valueOf(id));
        }else {
            //如果是用户自己选择
            Record r = ProblemHelper.fetchUserProblem(intent, recordDB);

            //通过唯一id找problem
            problem = problemDB.query(TotalProblems.class, String.valueOf(r.getID()));
        }
            //显示题目
            mProblemLoading.setText(problem.getSubID() +". " + problem.getContent());
            //看看有几个空
            wordNum = ProblemHelper.getWordNumber(problem);

            Log.i("CYY", problem.getAnswer());
        //自适应标题
        if (intent.getStringExtra("ShowType").equals("User")){setTitle("自主刷题");}
        if (intent.getStringExtra("ShowType").equals("Teacher")){setTitle("作业");}
        //if (intent.getStringExtra("ShowType").equals("User")){setTitle("自主刷题");}
    }

    @Override
    protected void initData() {

    }




    @OnClick({R.id.et_fill,R.id.btn_next})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (mFilling.getText().toString().equals(null) || mFilling.getText().toString().equals("")) {
                    new MessageDialog.Builder(this)
                            .setMessage("确定提交?")
                            .setConfirm("该交了")
                            .setCancel("容我三思") // 设置 null 表示不显示取消按钮
                            .setListener(new MessageDialog.OnListener() {

                                @Override
                                public void onConfirm(Dialog dialog) {
                                    //通过holder把答案和这次的problem对象向后传
                                    myAnswer[0] = mFilling.getText().toString();
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
                } else if(ProblemHelper.getMyWordNumber(mFilling.getText().toString()) < wordNum) {
                    new MessageDialog.Builder(this)
                            .setMessage("词数与答案不匹配,确定提交?")
                            .setConfirm("该交了")
                            .setCancel("容我三思") // 设置 null 表示不显示取消按钮
                            .setListener(new MessageDialog.OnListener() {

                                @Override
                                public void onConfirm(Dialog dialog) {
                                    //把答案和这次的problem对象向后传
                                    myAnswer[0] = mFilling.getText().toString();
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
                }else {
                    myAnswer[0] = mFilling.getText().toString();
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