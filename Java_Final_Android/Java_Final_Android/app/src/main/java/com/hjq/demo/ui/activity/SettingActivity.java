package com.hjq.demo.ui.activity;

import android.content.SharedPreferences;
import android.view.View;

import com.hjq.demo.R;
import com.hjq.demo.common.MyActivity;
import com.hjq.demo.helper.ActivityStackManager;
import com.hjq.demo.helper.CacheDataManager;
import com.hjq.image.ImageLoader;
import com.hjq.widget.SettingBar;
import com.hjq.widget.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/03/01
 *    desc   : 设置界面
 */
public final class SettingActivity extends MyActivity
        implements SwitchButton.OnCheckedChangeListener {

    @BindView(R.id.sb_setting_cache)
    SettingBar mCleanCacheView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected int getTitleId() {
        return R.id.tb_setting_title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        // 获取应用缓存大小
        mCleanCacheView.setRightText(CacheDataManager.getTotalCacheSize(this));
    }

    @OnClick({R.id.sb_setting_agreement, R.id.sb_setting_about,
            R.id.sb_setting_cache,R.id.sb_setting_exit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sb_setting_agreement:
                startActivity(WebActivity.class);
                break;
            case R.id.sb_setting_about:
                startActivity(AboutActivity.class);
                break;
            case R.id.sb_setting_cache: // 清空缓存
                ImageLoader.clear(this);
                CacheDataManager.clearAllCache(this);
                // 重新获取应用缓存大小
                mCleanCacheView.setRightText(CacheDataManager.getTotalCacheSize(this));
                break;
            case R.id.sb_setting_exit: // 退出登录
                SharedPreferences settings1 = getSharedPreferences("UserInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = settings1.edit();
                editor1.putString("autoLogin","false");
                editor1.commit();
                startActivity(LoginActivity.class);
                // 进行内存优化，销毁掉所有的界面
                ActivityStackManager.getInstance().finishAllActivities(LoginActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * {@link SwitchButton.OnCheckedChangeListener}
     */

    @Override
    public void onCheckedChanged(SwitchButton button, boolean isChecked) {
        toast(isChecked);
    }
}