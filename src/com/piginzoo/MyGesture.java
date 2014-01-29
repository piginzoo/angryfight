package com.piginzoo;

import android.util.Log;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

import com.piginzoo.event.Event;
import com.piginzoo.event.EventType;
import com.piginzoo.event.GameEventHandler;
import com.piginzoo.event.ScrollEvent;

/**
 * ���������ƣ���Ҫ��ʹ����onScroll��֮���Բ���onFling����Ҫ�ǡ�����
 */
public class MyGesture extends SimpleOnGestureListener{
	
		/**
		 * ���� return true�������������¼������ܱ�����������
		 */
		public boolean onDown(MotionEvent e) {
			return true;
		}

        // �û����´������������ƶ����ɿ�,���ʱ�������ָ�˶����м��ٶȵġ�   
        // ��1��MotionEvent ACTION_DOWN,     
        // ���ACTION_MOVE, 1��ACTION_UP����     
        // e1����1��ACTION_DOWN MotionEvent     
        // e2�����һ��ACTION_MOVE MotionEvent     
        // velocityX��X���ϵ��ƶ��ٶȣ�����/��     
        // velocityY��Y���ϵ��ƶ��ٶȣ�����/��		
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			
			
			
			return true;
		}
		
		// �û��������������ɶ��MotionEvent ACTION_DOWN���� 
		public void onLongPress(MotionEvent e) {
//			text = "����-long press";
//			show();
		}

        // �����¼������ڴ�������Ѹ�ٵ��ƶ��������onScroll����ACTION_MOVE����   
        // e1����1��ACTION_DOWN MotionEvent   
        // e2�����һ��ACTION_MOVE MotionEvent     
        // distanceX�������ϴβ���onScroll�¼���X���ƶ��ľ���   
        // distanceY�������ϴβ���onScroll�¼���Y���ƶ��ľ���   		
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			//System.out.println(distanceX+"-"+distanceY);
			
			Event e = new ScrollEvent(EventType.SCROLL,e1.getX(),e1.getY(),distanceX,distanceY);
			GameEventHandler.publish(e);
			
			Log.d("piginzoo", "scroll trigger:x,y="+distanceX+","+distanceY);
			return true;
		}
		
        //����˴�����������û���ƶ��͵���Ķ�����onShowPress��onDown����������   
        //onDown�ǣ�һ�����������£������ϲ���onDown�¼�������onShowPress��onDown�¼�������   
        //һ��ʱ���ڣ����û���ƶ����͵����¼�������Ϊ��onShowPress�¼���
		public void onShowPress(MotionEvent e) {
		}

        // ����������󣬵��������������в�����onLongPress��onScroll��onFling�¼����Ͳ���   
        // ����onSingleTapUp�¼���   		
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}
}