package com.piginzoo;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.piginzoo.objects.GameObject;

/**
 * �������Ҫ�����������Ļ滭
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


    //��Ļͼ��ÿ��50����ˢ��һ��
    //1.����ֵ�λ��
    //2.���ͷ��λ��
    //3.��ײ���
    //if(����) �ı�ͷ��׼������
    //else(δ���ϣ�
    //�����ͨ��������������ȭ����Ҫ500�����ڻ���ȭͷ���������̣�
    //������еĻ�����������У�ͷ��λ��������������
    //�����ͨ������װ��ȭ����Ҫ500���룬Ҳ��
    //Ŀǰ�ǵ����ƶ���δ��Ҫ����һ�����ٹ��̳���
    //4.��ͷ�����֣����������������Ҫ��
    //������
    //a. ͷ����ƶ��ٶ�Ҫ����Ŀǰ������500��������
    //b. ������Ҫ����Ƕȣ��ƶ���
    public void run() {
        Canvas c = null;
        
        while (isRunning) {
            long timeSt = System.currentTimeMillis();

            try {
                c = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                	c.drawARGB(255,0,0,0);//����Ҫ����������Ļ�������ϴλ��Ļ��������
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

            //ͣ��һ�Σ����ҿ�����lock��draw��ʱ�䣬����ˢ�µľ���
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

