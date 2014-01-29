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
 * ��Ϸ������ͼ���̳���SurfaceView��ʵ����Callback�ӿڣ����������滭�̡߳�
 * ������onTouchEvent�У�����GestureDector���ڴ�����ָ�Ĺ���������
 */
public class MainView extends SurfaceView implements Callback {
    /** �滭�߳� */
    private DrawThread gameThread;

    /** ���������� */
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
        

        //��ʼ��������֧��
        this.setLongClickable(true);
        this.gestureDetector = new GestureDetector(context, new MyGesture());
        this.gestureDetector.setIsLongpressEnabled(true);
        Log.d("piginzoo", "init finished");
    }

    /**
     * �ô������Ĵ����������ӹ�onTouchEvent�¼�����
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
     * Surface���԰��������Դ��һ��ӳ��,
     * д�뵽Surface ������ ���Ա�ֱ�Ӹ��Ƶ��Դ�Ӷ���ʾ��������ʹ����ʾ�ٶȻ�ǳ���,
     * ��Surface ������֮ǰ�������������Callback �е�surfaceCreated ��surfaceDestroyed �ͳ��˻�ͼ�������ı߽�
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
