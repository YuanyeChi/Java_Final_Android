package com.hjq.demo.Problems;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v4.widget.NestedScrollView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ansen.http.net.HTTPCaller;
import com.ansen.http.net.NameValuePair;
import com.ansen.http.net.RequestDataCallback;
import com.chiyuanye.demo.entity.ProblemsHolder;
import com.chiyuanye.demo.entity.Record;
import com.chiyuanye.demo.entity.User;
import com.chiyuanye.demo.entity.TotalProblems;
import com.chiyuanye.demo.utils.ProblemHelper;
import com.hjq.bar.TitleBar;
import com.hjq.demo.R;
import com.hjq.demo.common.MyActivity;
import com.hjq.demo.ui.activity.HomeActivity;
import com.hjq.dialog.MessageDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.ittiger.database.SQLiteDB;
import cn.ittiger.database.SQLiteDBConfig;
import cn.ittiger.database.SQLiteDBFactory;
import io.github.lizhangqu.coreprogress.ProgressUIListener;


public final class ProblemResultActivity extends MyActivity {
    @BindView(R.id.tv_result_show)
    TextView mResultShow;
    @BindView(R.id.tv_problem_review)
    TextView mReview;

    @BindView(R.id.btn_store)
    Button mStore;
    @BindView(R.id.btn_continue)
    Button mContinue;
    @BindView(R.id.btn_send_writing_answer)
    Button mUploadAnswer;

    @BindView(R.id.sl_result)
    NestedScrollView mSlResult;


    int correctNum = 0;
    int wrongNum = 0;
    boolean[] res;
    TotalProblems problems;
    SQLiteDBConfig recordConfig = new SQLiteDBConfig(this, "/CYY/", "Records.db");
    SQLiteDB recordDB = SQLiteDBFactory.createSQLiteDB(recordConfig);
    String[] myAnswer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_problem_result;
    }

    @Override
    protected int getTitleId() {
        return R.id.tb_result_title;
    }

    @Override
    protected void initView() {
        myAnswer = ProblemsHolder.getInstance().getMyAnswer1();
        problems = ProblemsHolder.getInstance().getThisProblem();

        if (problems.getProblemType().equals("Writing")) {
            mReview.setText(problems.getContent() + "\n参考答案如下\n" + problems.getAnswer());
            mResultShow.setVisibility(View.GONE);
            mSlResult.setVisibility(View.GONE);
            mUploadAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadAnswer(null);
                }
            });

        } else {
            mUploadAnswer.setVisibility(View.GONE);
            //返回数组 对于填空题对应的是第一个，对于阅读和完型下标对应题号-1
            res = ProblemHelper.judgeSelectAnswer(myAnswer, problems);

            for (int i = 0; i < res.length; i++) {

                if (res[i]) correctNum++;
                else wrongNum++;
            }
            mReview.setText(problems.getContent() + "\n答案是" + problems.getAnswer());
            mResultShow.setText(Html.fromHtml(ProblemHelper.generateReport(problems, recordDB, res, myAnswer)));
            mContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ProblemsHolder.getInstance().getProblemType() != null && !ProblemsHolder.getInstance().getProblemType().equals("System")) {
                        //writing不用保存
                        if (problems.getProblemType().equals("Writing")) {
                            Intent intent;
                            //通过intent传回select activity
                            intent = new Intent(ProblemResultActivity.this, WritingSelectActivity.class);
                            intent.putExtra("ShowType", "User");
                            intent.putExtra("ProblemType", problems.getProblemType());
                            intent.putExtra("SubProblemType", problems.getSubProblemType());
                            intent.putExtra("ExamType", problems.getExamType());
                            ProblemsHolder.getInstance().setProblemType("User");
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent;
                            if (problems.getProblemType().equals("Reading")) {
                                intent = new Intent(ProblemResultActivity.this, ReadingSelectActivity.class);
                            } else if (problems.getProblemType().equals("Cloze")) {
                                intent = new Intent(ProblemResultActivity.this, CLozeSelectActivity.class);
                            } else if (problems.getProblemType().equals("Sentence")) {
                                intent = new Intent(ProblemResultActivity.this, SentenceSelectActivity.class);
                            } else {
                                intent = new Intent(ProblemResultActivity.this, WordSelectActivity.class);
                            }

                            //通过intent传回select activity
                            intent.putExtra("ShowType", "User");
                            intent.putExtra("ProblemType", problems.getProblemType());
                            intent.putExtra("SubProblemType", problems.getSubProblemType());
                            intent.putExtra("ExamType", problems.getExamType());
                            ProblemsHolder.getInstance().setProblemType("User");
                            ProblemHelper.store(problems, recordDB, res);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        ProblemHelper.store(problems, recordDB, res);
                        startActivity(IntelligentActivity.class);
                        finish();
                    }

                }
            });

        }
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_store, R.id.btn_continue})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_store:
                new MessageDialog.Builder(this)
                        .setMessage("保存此次结果?")
                        .setConfirm("保存")
                        .setCancel("没发挥出水平,算了") // 设置 null 表示不显示取消按钮
                        .setListener(new MessageDialog.OnListener() {
                            @Override
                            public void onConfirm(Dialog dialog) {

                                //保存结果并退出
                                if (!problems.getProblemType().equals("Writing")) {
                                    ProblemHelper.store(problems, recordDB, res);
                                }
                                startActivity(HomeActivity.class);
                                toast("保存成功");
                                finish();
                            }

                            @Override
                            public void onCancel(Dialog dialog) {
                                toast("已放弃保存");
                                startActivity(HomeActivity.class);
                                finish();
                            }
                        }).show();
            default:
                break;
        }
    }

    private void uploadAnswer(ProgressUIListener progressUIListener) {

        SharedPreferences userInfo = getSharedPreferences("UserInfo", MODE_PRIVATE);
        List<NameValuePair> postParam = new ArrayList<>();
        postParam.add(new NameValuePair("username", userInfo.getString("username", "")));

        String s = "</p><h4>学生答案</h4><p>" + myAnswer[0] + "</p>";
        Log.i("CYY", s);
        try {
            s = new String(s.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        postParam.add(new NameValuePair("answer", s));

        postParam.add(new NameValuePair("fileName", problems.getSubProblemType() + "_" + problems.getSubID() + ".txt"));
        HTTPCaller.getInstance().post(User.class, "http://demo.boycyy.com/Java_Final/writeAnswer.do", null, postParam, new RequestDataCallback<User>() {
            @Override
            public void dataCallback(User user) {
                if (user == null) {
                    Log.i("CYY", "请求失败");
                    toast("上传失败");
                } else {
                    Log.i("CYY", "获取用户信息:" + user.toString());
                    toast("上传成功");
                    startActivity(HomeActivity.class);
                }
            }
        });

    }
}