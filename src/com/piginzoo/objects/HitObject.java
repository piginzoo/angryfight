package com.piginzoo.objects;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Paint;

import android.util.Log;

import com.piginzoo.Const;
import com.piginzoo.Device;

import com.piginzoo.event.Event;
import com.piginzoo.event.EventListener;
import com.piginzoo.event.EventType;
import com.piginzoo.event.GameEventHandler;
import com.piginzoo.event.ScrollEvent;


/**
 * 这是击打的对象的父类，子类有手/球
 */
public abstract class HitObject extends GameObject implements EventListener {
    private boolean isMoving;
    protected float distanceX;
    protected float distanceY;
    private long pauseTime = 0;
    private boolean isPause = false;

    public HitObject(Context context, int id, int[] resourceId) {
        super(context, id, resourceId);
        super.paint = new Paint();
        GameEventHandler.registListener(this);
        this.headBottom  = GameObject.findById(HEAD)._bottom();
        this.initialize();
    }

    public void onEvent(Event event) {
        if (event.type != EventType.SCROLL) {
            return;
        }

        ScrollEvent e = (ScrollEvent) event;

        if (e.distanceY < 0) {
            return; //向下的不处理
        }

        //如果手指头不在图片上做动作，就不处理
        if (!this.isInScope(e.x, e.y)) {
            return;
        }

        if (isMoving) {
            Log.d("piginzoo", "ball still moving, return");

            return;
        } else {
            isMoving = true;
            Log.d("piginzoo", "ball begin to move");
        }

        this.distanceY = e.distanceY;
        this.distanceX = e.distanceX;
    }

    public void initialize() {
        this.distanceX = 0;
        this.distanceY = 0;
        this.isMoving = false;
        this.initializeMe();
    }

    protected abstract void initializeMe();

    public void draw(Canvas c) {
        //如果暂停状态，就要看是还在暂停的timer内，如果是，就啥也不动，画手，返回
        // 						如果已经超过了暂停时间规定范围，就要让手回去，然后画手
        if (isPause) {
            if (System.currentTimeMillis() > pauseTime) {
                this.initialize();
                isPause = false;
            }

            this.drawMe(c);

            return;
        }

        //如果正在移动过程中，
        if (this.isMoving) {

        	this.caculateLeftTop();
            //如果碰撞检测，碰到了头，就发布一个碰撞事件，并且开始暂停，开始停止移动状态
            if (this.isOverlap(GameObject.findById(HEAD))) {
                Event e = new Event(EventType.COLLISION);
                GameEventHandler.publish(e);
                Log.d("piginzoo", "hand hit the head!!!");

                pauseTime = System.currentTimeMillis() + Const.PAUSE_TIME;
                this.isPause = true;

                this.isMoving = false;
                setTop2HeadBottom();
                return;
            }

            //如果碰撞到了边界，就返回初始状态
            if (this.isReachBorder()) {
                Log.d("piginzoo", "ball reach to border, go back");
                this.initialize();
                return;
            }
            
        }

        this.drawMe(c);
    }

    public abstract void caculateLeftTop();
    public abstract void setTop2HeadBottom();

    /**
     * 算法，看看手的四个点是否在攻击的矩形范围内。
     * 上面最高位置到头的下沿，而且不会往下攻击，_bottom检测就省了
     */
    public boolean isReachBorder() {
        if (this._left <= 0) {
            return true;
        }

        if (this._top <= this.headBottom) {//最上面就到头的下沿
            return true;
        }

        if (this._right() >= Device.screenWidth) {
            return true;
        }

        //if(this._bottom()>=Device.screenHeight) return true;
        return false;
    }
    
    protected float headBottom;

    protected abstract void drawMe(Canvas c);
}
