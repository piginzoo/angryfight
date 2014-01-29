package com.piginzoo;

import android.content.Context;

import android.util.Log;

import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import android.view.SurfaceHolder.Callback;

import android.view.SurfaceView;


/**
 * 游戏的主视图，继承于SurfaceView，实现了Callback接口，用于启动绘画线程。
 * 并且在onTouchEvent中，引入GestureDector用于处理手指的滚动触摸。
 */
public class MainView extends SurfaceView implements Callback {
    /** 绘画线程 */
    private DrawThread gameThread;

    /** 触摸屏控制 */
    private GestureDetector gestureDetector;

    public MainView(Context context) {
        super(context);
        this.init(context);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
        int height) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            return (true);
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            return (true);
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    public void init(Context context) {
        SurfaceHolder surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        

        //初始化触摸屏支持
        this.setLongClickable(true);
        this.gestureDetector = new GestureDetector(context, new MyGesture());
        this.gestureDetector.setIsLongpressEnabled(true);
        Log.d("piginzoo", "init finished");
    }

    /**
     * 让触摸屏的处理器，来接管onTouchEvent事件处理！
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Surface可以把它当作显存的一个映射,
     * 写入到Surface 的内容 可以被直接复制到显存从而显示出来，这使得显示速度会非常快,
     * 在Surface 被销毁之前必须结束。所以Callback 中的surfaceCreated 和surfaceDestroyed 就成了绘图处理代码的边界
     */
    public void surfaceCreated(SurfaceHolder holder) {
    	this.gameThread = new DrawThread(holder);
        this.gameThread.start();
        Log.d("piginzoo", "surfaceCreated");
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        this.gameThread.quit();
        Log.d("piginzoo", "surfaceDestroyed");
    }
}
