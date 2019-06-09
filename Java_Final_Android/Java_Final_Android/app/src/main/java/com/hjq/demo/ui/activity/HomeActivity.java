package com.hjq.demo.ui.activity;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.chiyuanye.demo.utils.FileHelper;
import com.hjq.base.BaseFragmentAdapter;
import com.hjq.demo.R;
import com.hjq.demo.common.MyActivity;
import com.hjq.demo.common.MyLazyFragment;
import com.hjq.demo.helper.ActivityStackManager;
import com.hjq.demo.helper.DoubleClickHelper;
import com.hjq.demo.ui.fragment.IntelligentFragment;
import com.hjq.demo.ui.fragment.SelectProblemFragment;
import com.hjq.demo.ui.fragment.TestFragmentA;
import com.hjq.demo.ui.fragment.TestFragmentB;
import com.hjq.demo.ui.fragment.TestFragmentC;
import com.hjq.demo.ui.fragment.TestFragmentD;

import java.io.File;

import butterknife.BindView;


public final class HomeActivity extends MyActivity
        implements ViewPager.OnPageChangeListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.vp_home_pager)
    ViewPager mViewPager;
    @BindView(R.id.bv_home_navigation)
    BottomNavigationView mBottomNavigationView;

    private BaseFragmentAdapter<MyLazyFragment> mPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected int getTitleId() {
        return 0;
    }

    @Override
    protected void initView() {
        mViewPager.addOnPageChangeListener(this);

        // 不使用图标默认变色
        mBottomNavigationView.setItemIconTintList(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        String folder_main = "CYY";

        File f = new File(Environment.getExternalStorageDirectory(), folder_main);
        if (!f.exists()) {
            f.mkdirs();
        }

        //第一次开软件从内部存储移至外部存储
        SharedPreferences fileExistInfo = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = fileExistInfo.edit();
        if(!fileExistInfo.getString("problem","").equals("true")
                || !fileExistInfo.getString("record","").equals("true")){
            Log.i("CYY","文件被移至外部存储");
            new FileHelper().copyFile(this,"/CYY/","ProblemsTotal.db", false);
            new FileHelper().copyFile(this,"/CYY/","Records.db", false);
            new FileHelper().copyFile(this,"/CYY/","Problems.db", false);
            editor.putString("problem","true");
            editor.putString("record","true");
            editor.apply();
        }
    }

    @Override
    protected void initData() {
        mPagerAdapter = new BaseFragmentAdapter<>(this);
        mPagerAdapter.addFragment(SelectProblemFragment.newInstance());
        mPagerAdapter.addFragment(IntelligentFragment.newInstance());
        mPagerAdapter.addFragment(TestFragmentC.newInstance());
        mPagerAdapter.addFragment(TestFragmentD.newInstance());

        mViewPager.setAdapter(mPagerAdapter);

        // 限制页面数量
        mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount());
    }

    /**
     * {@link ViewPager.OnPageChangeListener}
     */

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mBottomNavigationView.setSelectedItemId(R.id.menu_home);
                break;
            case 1:
                mBottomNavigationView.setSelectedItemId(R.id.home_found);
                break;
            case 2:
                mBottomNavigationView.setSelectedItemId(R.id.home_message);
                break;
            case 3:
                mBottomNavigationView.setSelectedItemId(R.id.home_me);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    /**
     * {@link BottomNavigationView.OnNavigationItemSelectedListener}
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                //mViewPager.setCurrentItem(0);
                //mViewPager.setCurrentItem(0, false);
                // 如果切换的是相邻之间的 Item 就显示切换动画，如果不是则不要动画
                mViewPager.setCurrentItem(0, mViewPager.getCurrentItem() == 1);
                return true;
            case R.id.home_found:
                //mViewPager.setCurrentItem(1);
                //mViewPager.setCurrentItem(1, false);
                mViewPager.setCurrentItem(1, mViewPager.getCurrentItem() == 0 || mViewPager.getCurrentItem() == 2);
                return true;
            case R.id.home_message:
                //mViewPager.setCurrentItem(2);
                //mViewPager.setCurrentItem(2, false);
                mViewPager.setCurrentItem(2, mViewPager.getCurrentItem() == 1 || mViewPager.getCurrentItem() == 3);
                return true;
            case R.id.home_me:
                //mViewPager.setCurrentItem(3);
                //mViewPager.setCurrentItem(3, false);
                mViewPager.setCurrentItem(3, mViewPager.getCurrentItem() == 2);
                return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 回调当前 Fragment 的 onKeyDown 方法
        if (mPagerAdapter.getCurrentFragment().onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (DoubleClickHelper.isOnDoubleClick()) {
            //移动到上一个任务栈，避免侧滑引起的不良反应
            moveTaskToBack(false);
            postDelayed(new Runnable() {

                @Override
                public void run() {
                    // 进行内存优化，销毁掉所有的界面
                    ActivityStackManager.getInstance().finishAllActivities();
                    // 销毁进程
                    System.exit(0);
                }
            }, 300);
        } else {
            toast(getResources().getString(R.string.home_exit_hint));
        }
    }

    @Override
    protected void onDestroy() {
        mViewPager.removeOnPageChangeListener(this);
        mViewPager.setAdapter(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(null);
        super.onDestroy();
    }

    @Override
    public boolean isSupportSwipeBack() {
        // 不使用侧滑功能
        return false;
    }
}