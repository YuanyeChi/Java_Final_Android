package com.cyy.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        mCheck = findViewById(R.id.btn_see_writing);
        setListeners();
    }

    private void setListeners(){
        OnClick onClick = new OnClick();
        mCheck.setOnClickListener(onClick);
    }

    private class  OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_see_writing:
                    intent = new Intent(MainActivity.this, CommentActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
