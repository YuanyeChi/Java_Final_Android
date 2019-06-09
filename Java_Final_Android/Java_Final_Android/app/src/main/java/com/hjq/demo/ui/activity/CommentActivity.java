package com.hjq.demo.ui.activity;

import android.content.SharedPreferences;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ansen.http.net.HTTPCaller;
import com.ansen.http.net.RequestDataCallback;
import com.chiyuanye.demo.entity.User;
import com.hjq.demo.R;
import com.hjq.demo.common.MyActivity;

import butterknife.BindView;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 可进行拷贝的副本
 */
public final class CommentActivity extends MyActivity {
    @BindView(R.id.tv_comment_loading)
    TextView mComment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected int getTitleId() {
        return R.id.tb_comment_title;
    }

    @Override
    protected void initView() {
        SharedPreferences userInfo = getSharedPreferences("UserInfo", MODE_PRIVATE);

        HTTPCaller.getInstance().get(User.class, "http://demo.boycyy.com/Java_Final/getComment.do?user="
                + userInfo.getString("username", ""), null, requestDataCallback);
    }

    @Override
    protected void initData() {

    }

    private RequestDataCallback requestDataCallback = new RequestDataCallback<User>() {
        @Override
        public void dataCallback(User user) {
            if (user == null) {
                Log.i("CYY", "请求失败");
                Toast.makeText(CommentActivity.this, "请求失败", Toast.LENGTH_LONG).show();
            } else {
                //在注册的时候先放一个空的
                //没有新的
                if (!user.getComment().equals("")) {
                    SharedPreferences settings = getSharedPreferences("Writing", MODE_PRIVATE);
                    String total = settings.getString("Comment", "") + "<h2>老师评语</h2>" + user.getComment();
                    mComment.setText(Html.fromHtml(total));
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Comment", mComment.getText().toString());
                    editor.apply();
                } else {
                    SharedPreferences settings = getSharedPreferences("Writing", MODE_PRIVATE);
                    String total = settings.getString("Comment", "");
                    mComment.setText(Html.fromHtml(total));
                    toast("没有新消息");
                }


            }
        }
    };
}