package com.hjq.demo.Problems;

import android.app.Dialog;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chiyuanye.demo.entity.ProblemsHolder;
import com.chiyuanye.demo.entity.Record;
import com.chiyuanye.demo.entity.TotalProblems;
import com.chiyuanye.demo.utils.ProblemHelper;
import com.hjq.bar.TitleBar;
import com.hjq.demo.R;
import com.hjq.demo.common.MyActivity;
import com.hjq.demo.ui.activity.HomeActivity;
import com.hjq.dialog.MessageDialog;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.ittiger.database.SQLiteDB;
import cn.ittiger.database.SQLiteDBConfig;
import cn.ittiger.database.SQLiteDBFactory;


public final class ProblemResultActivity extends MyActivity {
    @BindView(R.id.tv_result_show)
    TextView mResultShow;
    @BindView(R.id.tv_problem_review)
    TextView mReview;

    @BindView(R.id.btn_store)
    Button mStore;
    @BindView(R.id.btn_continue)
    Button mContinue;

    int correctNum = 0;
    int wrongNum = 0;
    boolean[] res;
    TotalProblems problems;
    SQLiteDBConfig recordConfig = new SQLiteDBConfig(this, "/CYY/", "Records.db");
    SQLiteDB recordDB = SQLiteDBFactory.createSQLiteDB(recordConfig);

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
        String[] myAnswer = ProblemsHolder.getInstance().getMyAnswer1();
        problems = ProblemsHolder.getInstance().getThisProblem();

        if (problems.getProblemType().equals("Writing")) {
            mReview.setText(problems.getContent() + "\n参考答案如下\n" + problems.getAnswer());
            mResultShow.setText("主观题请参照参考答案学习，等完全开发完成后可以实现上传给老师批改，敬请期待。");
        }else {
            //返回数组 对于填空题对应的是第一个，对于阅读和完型下标对应题号-1
            res = ProblemHelper.judgeSelectAnswer(myAnswer, problems);

            for (int i = 0; i < res.length; i++) {

                if (res[i]) correctNum++;
                else wrongNum++;
            }


            ;
            //<h2 id="结果报告"><a href="#结果报告" class="headerlink" title="结果报告"></a>结果报告</h2><p>本题<strong>正确率为100%</strong>,在<strong>词汇类型</strong>的题目中正确率高于其他<strong>95%</strong>的题，在所有客观题中正确率高于其他<strong>95%</strong>的题，由于你这次做的是<strong>模拟题</strong>,建议抽空刷刷高考真题。<br>本题<strong>正确率为100%</strong>,在<strong>词汇类型</strong>的题目中正确率高于其他<strong>95%</strong>的题，在所有客观题中正确率高于其他<strong>95%</strong>的题，由于你这次做的是<strong>高考题</strong>,建议抽空刷刷模拟题，多见见偏题怪题也有好处。</p>

            mReview.setText(problems.getContent() + "\n答案是" + problems.getAnswer());
            mResultShow.setText(Html.fromHtml(ProblemHelper.generateReport(problems, recordDB, res, myAnswer)));
        }
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_continue, R.id.btn_store})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_continue:
                if(ProblemsHolder.getInstance().getProblemType() != null && !ProblemsHolder.getInstance().getProblemType().equals("System")){
                    Intent intent;
                    if (problems.getProblemType().equals("Reading")) {
                        intent = new Intent(ProblemResultActivity.this, ReadingSelectActivity.class);
                    } else if (problems.getProblemType().equals("Cloze")) {
                        intent = new Intent(ProblemResultActivity.this, CLozeSelectActivity.class);
                    } else if (problems.getProblemType().equals("Sentence")) {
                        intent = new Intent(ProblemResultActivity.this, SentenceSelectActivity.class);
                    } else if (problems.getProblemType().equals("Word")) {
                        intent = new Intent(ProblemResultActivity.this, WordSelectActivity.class);
                    } else {
                        intent = new Intent(ProblemResultActivity.this, WritingSelectActivity.class);
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
                } else {
                    ProblemHelper.store(problems, recordDB, res);
                    startActivity(IntelligentActivity.class);
                    finish();
                }

            case R.id.btn_store:
                new MessageDialog.Builder(this)
                        .setMessage("保存此次结果?")
                        .setConfirm("保存")
                        .setCancel("没发挥出水平,算了") // 设置 null 表示不显示取消按钮
                        .setListener(new MessageDialog.OnListener() {
                            @Override
                            public void onConfirm(Dialog dialog) {
                                //保存结果并退出
                                ProblemHelper.store(problems, recordDB, res);

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
}