package com.piginzoo;

import android.util.Log;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

import com.piginzoo.event.Event;
import com.piginzoo.event.EventType;
import com.piginzoo.event.GameEventHandler;
import com.piginzoo.event.ScrollEvent;

/**
 * 触摸屏控制，主要是使用了onScroll，之所以不用onFling，主要是。。。
 */
public class MyGesture extends SimpleOnGestureListener{
	
		/**
		 * 必须 return true，否则，其他的事件都不能被触发！！！
		 */
		public boolean onDown(MotionEvent e) {
			return true;
		}

        // 用户按下触摸屏、快速移动后松开,这个时候，你的手指运动是有加速度的。   
        // 由1个MotionEvent ACTION_DOWN,     
        // 多个ACTION_MOVE, 1个ACTION_UP触发     
        // e1：第1个ACTION_DOWN MotionEvent     
        // e2：最后一个ACTION_MOVE MotionEvent     
        // velocityX：X轴上的移动速度，像素/秒     
        // velocityY：Y轴上的移动速度，像素/秒		
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			
			
			
			return true;
		}
		
		// 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发 
		public void onLongPress(MotionEvent e) {
//			text = "长按-long press";
//			show();
		}

        // 滚动事件，当在触摸屏上迅速的移动，会产生onScroll。由ACTION_MOVE产生   
        // e1：第1个ACTION_DOWN MotionEvent   
        // e2：最后一个ACTION_MOVE MotionEvent     
        // distanceX：距离上次产生onScroll事件后，X抽移动的距离   
        // distanceY：距离上次产生onScroll事件后，Y抽移动的距离   		
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			//System.out.println(distanceX+"-"+distanceY);
			
			Event e = new ScrollEvent(EventType.SCROLL,e1.getX(),e1.getY(),distanceX,distanceY);
			GameEventHandler.publish(e);
			
			Log.d("piginzoo", "scroll trigger:x,y="+distanceX+","+distanceY);
			return true;
		}
		
        //点击了触摸屏，但是没有移动和弹起的动作。onShowPress和onDown的区别在于   
        //onDown是，一旦触摸屏按下，就马上产生onDown事件，但是onShowPress是onDown事件产生后，   
        //一段时间内，如果没有移动鼠标和弹起事件，就认为是onShowPress事件。
		public void onShowPress(MotionEvent e) {
		}

        // 轻击触摸屏后，弹起。如果这个过程中产生了onLongPress、onScroll和onFling事件，就不会   
        // 产生onSingleTapUp事件。   		
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}
}