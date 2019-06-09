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


public final class SelectLoadActivity extends MyActivity {

    @BindView(R.id.tv_problem_loading)
    TextView mProblemLoading;
    @BindView(R.id.tv_problem_number)
    TextView mProblemNumber;
    @BindView(R.id.tv_select)
    TextView mSelect;

    @BindView(R.id.btn_answer_a)
    Button mAnswerA;
    @BindView(R.id.btn_answer_b)
    Button mAnswerB;
    @BindView(R.id.btn_answer_c)
    Button mAnswerC;
    @BindView(R.id.btn_answer_d)
    Button mAnswerD;
    @BindView(R.id.btn_previous)
    Button previous;
    @BindView(R.id.btn_next)
    Button next;

    //存我的答案
    String[] myAnswer;
    //现在回答的题号
    int count = 0;

    TotalProblems problem;
    //现在这个问题有几个小题
    int problemNumber = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_load;
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
        SQLiteDBConfig recordConfig = new SQLiteDBConfig(this, "/CYY/", "Records.db");
        SQLiteDB recordDB = SQLiteDBFactory.createSQLiteDB(recordConfig);
        SQLiteDBConfig problemConfig = new SQLiteDBConfig(this, "/CYY/", "ProblemsTotal.db");
        SQLiteDB problemDB = SQLiteDBFactory.createSQLiteDB(problemConfig);

//        toast(intent.getStringExtra("ProblemType") + " "
//                +intent.getStringExtra("SubProblemType") + " " + intent.getStringExtra("ExamType")
//        + " " + intent.getStringExtra("ShowType"));

        //如果通过检索或者自己选择得到
        if(intent.getStringExtra("ShowType").equals("Search")) {
            List<Record> list = recordDB.queryAll(Record.class);
            int id = SQLiteHelper.InfoToID((ArrayList<Record>) list, intent.getStringExtra("SubProblemType"),
                    Integer.parseInt(intent.getStringExtra("SubID")));
            problem = problemDB.query(TotalProblems.class, String.valueOf(id));
        } else{
            Record r = ProblemHelper.fetchUserProblem(intent, recordDB);
            //通过唯一id找problem
            problem = problemDB.query(TotalProblems.class, String.valueOf(r.getID()));
        }

        //显示题目
        mProblemLoading.setText(problem.getSubID() +". " +problem.getContent());

        //得到题目道数，便于判断终点
        problemNumber = ProblemHelper.getAnswerNumber(problem);
        myAnswer = new String[problemNumber];

        //显示题号
        mProblemNumber.setText("Problem 1");

        //一开始没有上一题
        previous.setEnabled(false);

        Log.i("CYY", problem.getAnswer());
        //自适应标题
        if (intent.getStringExtra("ShowType").equals("User")) {
            setTitle("自主刷题");
        }
        if (intent.getStringExtra("ShowType").equals("Teacher")) {
            setTitle("作业");
        }
        if (intent.getStringExtra("ShowType").equals("System")) {
            setTitle("智能生成");
        }
        //if (intent.getStringExtra("ShowType").equals("User")){setTitle("自主刷题");}
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.btn_answer_a, R.id.btn_answer_b,
            R.id.btn_answer_c, R.id.btn_answer_d, R.id.btn_next, R.id.btn_previous})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_answer_a:
                myAnswer[count] = "A";
                mSelect.setText(Html.fromHtml("<b>You chose A</b>"));
                mAnswerA.setPressed(false);
                mAnswerB.setPressed(true);
                mAnswerC.setPressed(true);
                mAnswerD.setPressed(true);
                break;


            case R.id.btn_answer_b:
                myAnswer[count] = "B";
                mSelect.setText(Html.fromHtml("<b>You chose B</b>"));
                mAnswerB.setPressed(false);
                mAnswerA.setPressed(true);
                mAnswerC.setPressed(true);
                mAnswerD.setPressed(true);
                break;
            case R.id.btn_answer_c:
                myAnswer[count] = "C";
                mSelect.setText(Html.fromHtml("<b>You chose C</b>"));
                mAnswerC.setPressed(false);
                mAnswerB.setPressed(true);
                mAnswerA.setPressed(true);
                mAnswerD.setPressed(true);
                break;
            case R.id.btn_answer_d:
                myAnswer[count] = "D";
                mSelect.setText(Html.fromHtml("<b>You chose D</b>"));
                mAnswerD.setPressed(false);
                mAnswerB.setPressed(true);
                mAnswerC.setPressed(true);
                mAnswerA.setPressed(true);
                break;
            case R.id.btn_next:
                if (count == problemNumber - 1) {
                    new MessageDialog.Builder(this)
                            .setMessage("确定提交?")
                            .setConfirm("该交了")
                            .setCancel("容我三思") // 设置 null 表示不显示取消按钮
                            .setListener(new MessageDialog.OnListener() {

                                @Override
                                public void onConfirm(Dialog dialog) {
                                    //把答案和这次的problem对象向后传
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
                }
                if (count < problemNumber - 1) count++;
                if (count > 0) previous.setEnabled(true);
                Log.i("CYY", String.valueOf(count));
                //改变题号
                if (count < problemNumber) mProblemNumber.setText("Problem " + (count + 1));
                //显示已选
                if (myAnswer[count] != null) mSelect.setText(myAnswer[count]);
                else mSelect.setText("You have not selected");
                //差一
                if (count == problemNumber - 1) {
                    next.setText("结束回答");
                } else {
                    next.setText("下一题");
                }
                //恢复按钮状态
                mAnswerD.setPressed(true);
                mAnswerB.setPressed(true);
                mAnswerC.setPressed(true);
                mAnswerA.setPressed(true);
                break;
            case R.id.btn_previous:
                if (count > 0) count--;
                mProblemNumber.setText("Problem " + (count + 1));
                if (myAnswer[count] != null) mSelect.setText(myAnswer[count]);
                else mSelect.setText("You have not selected");
                if (count == 0) previous.setEnabled(false);
                break;
            default:
                break;
        }
    }
}