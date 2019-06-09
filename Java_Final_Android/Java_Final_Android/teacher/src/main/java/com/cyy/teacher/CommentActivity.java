package com.cyy.teacher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ansen.http.net.HTTPCaller;
import com.ansen.http.net.NameValuePair;
import com.ansen.http.net.RequestDataCallback;
import com.cyy.entity.User;
import com.cyy.entity.WholeAnswer;

import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.lizhangqu.coreprogress.ProgressUIListener;

public class CommentActivity extends AppCompatActivity {

    private Button mSend;

    private TextView mAnswer;

    private EditText mComment, mUser, mProblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        mSend = findViewById(R.id.btn_send);
        mAnswer = findViewById(R.id.tv_answer_loading);
        mComment = findViewById(R.id.et_send);
        mUser = findViewById(R.id.et_userName);
        mProblem = findViewById(R.id.et_userProblem);
        HTTPCaller.getInstance().get(WholeAnswer.class, "http://demo.boycyy.com/Java_Final/getAnswer.do", null, requestDataCallback);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadComment(null);
                Intent intent = new Intent(CommentActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void uploadComment(ProgressUIListener progressUIListener){
        List<NameValuePair> postParam = new ArrayList<>();
        postParam.add(new NameValuePair("username", mUser.getText().toString()));
        postParam.add(new NameValuePair("comment", mComment.getText().toString()));
        postParam.add(new NameValuePair("fileName", mProblem.getText().toString() + "_comment.txt"));
        HTTPCaller.getInstance().post(User.class, "http://demo.boycyy.com/Java_Final/writeComment.do", null, postParam, requestDataCallback1);

    }

    private RequestDataCallback requestDataCallback = new RequestDataCallback<WholeAnswer>() {
        @Override
        public void dataCallback(WholeAnswer wholeAnswer) {
            if(wholeAnswer==null){
                Log.i("CYY", "请求失败");
                Toast.makeText(CommentActivity.this, "获取失败", Toast.LENGTH_LONG).show();
            }else{
                Log.i("CYY",wholeAnswer.getUserNames());
                String answer = "<h2>学生作文</h2>";
                String[] users = wholeAnswer.getUserNames().split(";");
                //每个用户用|分开 注意正则特殊转义
                String[] eveyUserAnswer = wholeAnswer.getUserAnswer().split("\\|");
                for (int i = 0; i < users.length; i++ ) {
                    answer += "<h3>学生" + users[i] + ":</h3>";
                    Log.i("CYY",eveyUserAnswer[i]);
                    String[] everyProblem = eveyUserAnswer[i].split(";");
                    for(int j = 0; j < everyProblem.length; j++) {
                        answer += everyProblem[j];
                    }
                }
                mAnswer.setText(Html.fromHtml(answer));
            }
        }
    };
    //注意user
    private RequestDataCallback requestDataCallback1 = new RequestDataCallback<User>() {
        @Override
        public void dataCallback(User user) {
            if(user==null){
                Log.i("CYY", "请求失败");
                Toast.makeText(CommentActivity.this, "上传失败", Toast.LENGTH_LONG).show();
            }else{
                Log.i("CYY", "获取用户信息:" + user.toString());
            }
        }
    };
}
