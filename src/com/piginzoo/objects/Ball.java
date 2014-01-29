package com.piginzoo.objects;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import com.piginzoo.Device;

public class Ball extends HitObject{

	public Ball(Context context) {
		super(context, BALL, null);
	    super.paint.setColor(Color.RED);
	
	}
	
	private float radius;
	private float x;
	private float y;
	
	public void caculateLeftTop(){
		this.x -= this.distanceX; 
		this.y -=this.distanceY;
		this._left = this.x - this.radius;
		this._top  = this.y - this.radius;		
	}
	
	@Override
	public void setTop2HeadBottom() {
		this._top = this.headBottom;
		this.y = this._top + this.radius;
	}
	
	public void drawMe(Canvas c) {
		c.drawCircle(this.x,this.y,this.radius,paint);
	}

	public void initializeMe() {
		this.radius = 50;
		this.x = Device.screenWidth/2 + 50;
		this.y = Device.screenHeight - 2*this.radius;//不知道为什么要×2，看屏幕调整出来的
		this._height = 2* this.radius;
		this._width = 2 * this.radius;
		this._left = x - this.radius;
		this._top = y - this.radius;			
	}
}
