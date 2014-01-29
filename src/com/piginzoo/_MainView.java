package com.piginzoo;

import android.content.Context;

import android.content.res.Resources;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import android.graphics.drawable.Drawable;

import android.media.MediaPlayer;

import android.os.Handler;
import android.os.Message;

import android.util.AttributeSet;

import android.view.KeyEvent;
import android.view.View;

import java.util.Random;

/**
 * 已经废弃，过去的简单实现而已
 * @deprecated 
 */
public class _MainView extends View {
    Bitmap hand = null;
    private boolean upKeyHoldInProcess = false;
    private int intervalHead = 100;
    private Paint paint = new Paint();
    private int HAND_TOP_INI = 320;
    private int handLeft = 0;
    private int handTop = HAND_TOP_INI;
    private int headLeft = 0;
    private Random random = new Random();
    private RefreshHandler mRedrawHandler = new RefreshHandler();
    private Bitmap currentHead = null;
    private Bitmap smileHead = null;
    private Bitmap cryHead = null;
    private MediaPlayer soundA;
    private MediaPlayer soundHit;
    private int SCREEN_HEIGHT;
    private int SCREEN_WIDTH;

    public _MainView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(context);
    }

    public _MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            handTop = 150;

            if (Math.abs((handLeft + 35) - (headLeft + 75)) < 50) { //如果击中
                this.update(handTop, cryHead); //更换脑袋图像，和手位置
                this.soundA.start();
            } else { //只移动手位置
                this.update(handTop);
            }

            upKeyHoldInProcess = true;
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            if ((handLeft + 10) > 250) {
                return true;
            }

            handLeft += 10;
            this.update(HAND_TOP_INI);

            return (true);
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            if ((handLeft - 10) < 0) {
                return true;
            }

            handLeft -= 10;
            this.update(HAND_TOP_INI);

            return (true);
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            upKeyHoldInProcess = false;
            update(HAND_TOP_INI, this.smileHead);
        }

        return super.onKeyUp(keyCode, event);
    }

    public void init(Context context) {
        this.setFocusable(true);
        mRedrawHandler.sleep(intervalHead);
        hand = loadHandBitmap();
        smileHead = loadHeadBitmap(R.drawable.avatar_jy1);
        cryHead = loadHeadBitmap(R.drawable.avatar_jy2);
        currentHead = smileHead;
        
        this.SCREEN_HEIGHT = this.getHeight();
        this.SCREEN_WIDTH = this.getWidth();
    }

    private Bitmap loadHeadBitmap(int i) {
        Resources r = this.getContext().getResources();
        Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable draw = r.getDrawable(i);
        draw.setBounds(0, 0, 150, 150);
        draw.draw(canvas);

        return bitmap;
    }

    private Bitmap loadHandBitmap() {
        Resources r = this.getContext().getResources();
        Drawable draw = r.getDrawable(R.drawable.hand);
        //把资源加载成一个Bitmap对象
        Bitmap bitmap = Bitmap.createBitmap(
        		100,//初始位图大小
        		100, 
        		Bitmap.Config.ARGB_8888);
        //然后把bitmap放到
        Canvas canvas = new Canvas(bitmap);
        draw.setBounds(0, 0, 75, 75);//画到什么位置
        draw.draw(canvas);

        return bitmap;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(this.currentHead, headLeft, 0, paint);
        canvas.drawBitmap(hand, handLeft, handTop, paint);

        Paint circlePaint = new Paint();
        circlePaint.setColor(0xffffff);
        canvas.drawCircle(100, 100, 10, circlePaint);
    }

    public void update(int handTop, Bitmap newHead) {
        if (this.upKeyHoldInProcess) {
            return;
        }

        this.currentHead = newHead;
        this.handTop = handTop;
        this.invalidate();

        mRedrawHandler.sleep(intervalHead);
    }

    public void update(int handTop) {
        this.headLeft = (int) (random.nextDouble() * SCREEN_WIDTH);
        this.update(handTop, this.currentHead);
    }

    public void setSound(MediaPlayer soundA, MediaPlayer soundHit) {
        this.soundA = soundA;
        this.soundHit = soundHit;
    }

    class RefreshHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            _MainView.this.update(HAND_TOP_INI);
        }

        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }
}
