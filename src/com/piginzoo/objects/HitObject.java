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
 * ���ǻ���Ķ���ĸ��࣬��������/��
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
            return; //���µĲ�����
        }

        //�����ָͷ����ͼƬ�����������Ͳ�����
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
        //�����ͣ״̬����Ҫ���ǻ�����ͣ��timer�ڣ�����ǣ���ɶҲ���������֣�����
        // 						����Ѿ���������ͣʱ��涨��Χ����Ҫ���ֻ�ȥ��Ȼ����
        if (isPause) {
            if (System.currentTimeMillis() > pauseTime) {
                this.initialize();
                isPause = false;
            }

            this.drawMe(c);

            return;
        }

        //��������ƶ������У�
        if (this.isMoving) {

        	this.caculateLeftTop();
            //�����ײ��⣬������ͷ���ͷ���һ����ײ�¼������ҿ�ʼ��ͣ����ʼֹͣ�ƶ�״̬
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

            //�����ײ���˱߽磬�ͷ��س�ʼ״̬
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
     * �㷨�������ֵ��ĸ����Ƿ��ڹ����ľ��η�Χ�ڡ�
     * �������λ�õ�ͷ�����أ����Ҳ������¹�����_bottom����ʡ��
     */
    public boolean isReachBorder() {
        if (this._left <= 0) {
            return true;
        }

        if (this._top <= this.headBottom) {//������͵�ͷ������
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
