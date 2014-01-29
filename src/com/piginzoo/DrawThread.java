package com.piginzoo;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.piginzoo.objects.GameObject;

/**
 * 这个类主要负责各个对象的绘画
 */
public class DrawThread extends Thread {
    private boolean isRunning;
    private SurfaceHolder surfaceHolder;
    
    public void quit(){
    	this.isRunning = false;
    }

    public DrawThread(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.isRunning = true;
    }


    //屏幕图像每隔50毫秒刷新一次
    //1.获得手的位置
    //2.获得头的位置
    //3.碰撞检测
    //if(碰上) 改变头像，准备声音
    //else(未碰上）
    //如果是通过触摸屏触发的拳击，要500毫秒内击出拳头（持续过程）
    //如果击中的话，这个过程中，头像位置锁定，不动了
    //如果是通过滚动装置拳击，要500毫秒，也是
    //目前是等速移动，未来要计算一个加速过程出来
    //4.画头，画手，播放声音（如果需要）
    //其他：
    //a. 头像的移动速度要慢，目前考虑是500毫秒左右
    //b. 触摸屏要计算角度，移动手
    public void run() {
        Canvas c = null;
        
        while (isRunning) {
            long timeSt = System.currentTimeMillis();

            try {
                c = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                	c.drawARGB(255,0,0,0);//必须要擦除整个屏幕，否则，上次画的会残留上面
                	for(GameObject gameObject: GameObject.findAll()){
                	   gameObject.draw(c);
                	}
                }
            } catch (Exception e) {
            	e.printStackTrace();
                System.out.println("run error!!!");
            } finally {
                surfaceHolder.unlockCanvasAndPost(c);
            }

            //停滞一段，并且考虑了lock和draw的时间，保持刷新的均匀
            long timeEnd = System.currentTimeMillis() - timeSt;
            if (timeEnd >= Const.REFRESH_REQUENCY) continue;
            try {
                Thread.sleep(Const.REFRESH_REQUENCY - timeEnd);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

