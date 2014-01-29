package com.piginzoo.objects;

import com.piginzoo.Device;

import android.content.Context;
import android.graphics.Canvas;


public class Hand extends HitObject  {
    @Override
	public void caculateLeftTop() {
        this._left -= this.distanceX;
        this._top -= this.distanceY;
	}

	@Override
	protected void drawMe(Canvas c) {
    	c.drawBitmap(bitmaps[0], this._left, this._top, this.paint);
	}

	@Override
	public void setTop2HeadBottom() {
		this._top = this.headBottom;
	}

	public Hand(Context context, int[] resourceId) {
		super(context, HAND, resourceId);
	}

	@Override
	protected void initializeMe() {
        this._height = 50;
        this._width = 50;
        this._left = (Device.screenWidth / 2) - (this._width / 2) - 100;
        this._top = Device.screenHeight - (this._height * 2); //不知道为什么看不到手，所以往上提提位置
	}
}
