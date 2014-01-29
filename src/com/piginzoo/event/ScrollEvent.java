package com.piginzoo.event;

public class ScrollEvent extends Event {
	
	public ScrollEvent(EventType type,float x,float y,float distanceX,float distanceY) {
		super(type);
		this.x = x;
		this.y = y;
		this.distanceX = distanceX;
		this.distanceY = distanceY;
	}
	public float distanceX;
	public float distanceY;
	public float x;
	public float y;
}
