package com.hjq.demo.ui.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hjq.demo.R;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class SongActivity extends AppCompatActivity {

    private boolean isPlay = false;//播放状态 true:播放 false:未播放
    private boolean isPause = false;//暂停状态 true:暂停 false:未暂停

    private MediaPlayer mediaPlayer;
    private ImageView ivPlay;
    private SeekBar seekBar;

    private Handler handler = new Handler();

    private TextView tvStartTime, tvEndTime;//开始时间结束时间

    private final Runnable mTicker = new Runnable() {
        public void run() {
            long now = SystemClock.uptimeMillis();
            long next = now + (1000 - now % 1000);

            handler.postAtTime(mTicker, next);//延迟一秒再次执行runnable,就跟计时器一样效果

            if (mediaPlayer != null && isPlay && !isPause) {//播放中  并且未暂停
                seekBar.setProgress(mediaPlayer.getCurrentPosition());//更新播放进度
                tvStartTime.setText(getTimeStr(mediaPlayer.getCurrentPosition()));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        ivPlay = (ImageView) findViewById(R.id.iv_play);
        ivPlay.setOnClickListener(onClickListener);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);//seekbar改变监听

        tvStartTime = (TextView) findViewById(R.id.tv_start_time);
        tvEndTime = (TextView) findViewById(R.id.tv_end_time);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_play:
                    if (isPlay) {//播放中
                        if (isPause) {//暂停中
                            mediaPlayer.start();//开始播放
                            isPause = false;//
                            ivPlay.setImageResource(R.mipmap.icon_stop);
                            handler.post(mTicker);//更新进度
                        } else {//未暂停
                            mediaPlayer.pause();//暂停播放
                            isPause = true;
                            ivPlay.setImageResource(R.mipmap.icon_play);
                            handler.removeCallbacks(mTicker);//删除执行的Runnable 终止计时器
                        }
                    } else {//未播放过
                        playMusic();//播放音乐
                        ivPlay.setImageResource(R.mipmap.icon_stop);
                        isPlay = true;
                    }
                    break;
            }
        }
    };

    private void playMusic() {
        try {
            mediaPlayer = new MediaPlayer();
            Uri uri = Uri.parse("android.resource://com.hjq.demo/" + R.raw.roadtofreedom);
            mediaPlayer.setDataSource(SongActivity.this, uri);//设置播放资源(可以是应用的资源文件／url／sdcard路径)
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);//设置播放类型
            mediaPlayer.setOnCompletionListener(onCompletionListener);//播放完成监听
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {//预加载监听
                @Override
                public void onPrepared(MediaPlayer mp) {//预加载完成
                    seekBar.setMax(mediaPlayer.getDuration());//设置总进度
                    mediaPlayer.start();//开始播放
                    handler.post(mTicker);//更新进度
                    tvEndTime.setText(getTimeStr(mediaPlayer.getDuration()));
                }
            });
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {//进度改变
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {//开始拖动seekbar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {//停止拖动seekbar
            if (mediaPlayer != null && isPlay) {//播放中
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        }
    };

    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {//播放完成 进入暂停状态，恢复初始值
            isPause = true;
            ivPlay.setImageResource(R.mipmap.icon_play);
            mediaPlayer.seekTo(0);
            seekBar.setProgress(0);
            tvStartTime.setText("00:00:00");
            handler.removeCallbacks(mTicker);//删除执行的Runnable 终止计时器
        }
    };

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {//如果不为空 释放资源
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    /**
     * @param time 时间戳
     * @return 分秒字符串
     */
    private String getTimeStr(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        return simpleDateFormat.format(time);
    }
}
