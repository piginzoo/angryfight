package com.piginzoo.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.piginzoo.Const;
import com.piginzoo.Device;
import com.piginzoo.event.Event;
import com.piginzoo.event.EventListener;
import com.piginzoo.event.EventType;
import com.piginzoo.event.GameEventHandler;


public class Head extends GameObject implements EventListener {
    private int headIndex = 0; //默认是一般的头
    private long pauseTime;
    private boolean isPause;
    private int movingFrenquency;
    private int movingStep = Const.HEAD_MOVE_STEP;

    public Head(Context context, int[] resourceId) {
        super(context, HEAD, resourceId);
        GameEventHandler.registListener(this);
    }

    public void onEvent(Event e) {
        if (e.type != EventType.COLLISION) {
            return;
        }

        this.headIndex = 1;//更换头像为被击中的头像
        this.pauseTime = System.currentTimeMillis() + Const.PAUSE_TIME;
        this.isPause = true;
        Log.d("piginzoo","Oh, head(me) is hitted!");
    }

    @Override
    public void initialize() {
        this._height = 120;
        this._width = 120;
        this._left = (Device.screenWidth / 2) - (this._width / 2);
        this._top = 0;
        this.headIndex = 0;
    }

    @Override
    public void draw(Canvas c) {
        if (isPause) {
            if (System.currentTimeMillis() > pauseTime) {
                isPause = false;
                this.headIndex = 0;//恢复头像回去
            }
            c.drawBitmap(bitmaps[headIndex],_left,_top,paint);
            return;
        }

        if (this.movingFrenquency >= Const.HEAD_MOVE_REQUENCY) {
            this._left += movingStep;
            this.movingFrenquency = 0;
        } else {
            this.movingFrenquency++;
        }

        if (this._right() >= Device.screenWidth) {
            this.movingStep = -Const.HEAD_MOVE_STEP;
        }

        if (this._left <= 0) {
            this.movingStep = Const.HEAD_MOVE_STEP;
        }

        c.drawBitmap(bitmaps[headIndex], this._left, this._top, this.paint);
    }
}
