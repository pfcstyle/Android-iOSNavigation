package com.example.developer.workcentertools;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import pfc.iosnavigation.MyNavigationActivity;
import pfc.iosnavigation.MyNavigationManager;

import static pfc.iosnavigation.MyNavigationManager.AnimationType.ANIMATION_TYPE_DOWN_TO_UP;
import static pfc.iosnavigation.MyNavigationManager.AnimationType.ANIMATION_TYPE_FADE_IN_OUT;
import static pfc.iosnavigation.MyNavigationManager.AnimationType.ANIMATION_TYPE_NONE;
import static pfc.iosnavigation.MyNavigationManager.AnimationType.ANIMATION_TYPE_RIGHT_TO_LEFT;
import static pfc.iosnavigation.MyNavigationManager.AnimationType.ANIMATION_TYPE_SCALE;

/**
 * Created by developer on 176.
 */

public class LocationActivity extends MyNavigationActivity {
    private Toolbar mToolbar;
    static MyNavigationManager.AnimationType animationType[] = {ANIMATION_TYPE_NONE,
            ANIMATION_TYPE_DOWN_TO_UP,
            ANIMATION_TYPE_RIGHT_TO_LEFT,
            ANIMATION_TYPE_FADE_IN_OUT,
            ANIMATION_TYPE_SCALE};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locaiton);
        mToolbar = (Toolbar)findViewById(R.id.location_toolbar);
        setSupportActionBar(mToolbar);
        this.getSupportActionBar().setTitle("location");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    boolean isStill = true;
    int animationIndex = 0;
    public void buttonClick(View view){
        isStill = !isStill;
        animationIndex++;
        System.out.println("buttonClick event");
        testView testView = new testView(this,R.layout.test_view);
        pushView(testView, animationType[animationIndex%5],isStill,null);
//        pushView(testView);
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }
}
