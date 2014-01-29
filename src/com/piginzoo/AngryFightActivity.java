package com.piginzoo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.piginzoo.event.GameEventHandler;
import com.piginzoo.event.GameMediaPlayer;
import com.piginzoo.objects.Ball;
import com.piginzoo.objects.Hand;
import com.piginzoo.objects.Head;


/**
 * ����������
 */
public class AngryFightActivity extends Activity {
    GameMediaPlayer mediaPalyer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSetting();
        MainView mainView = new MainView(this);
        setContentView(mainView);

        /* �������� android.util.DisplayMetrics */
        // metric ���Ƶģ����Ƶ�  
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        System.out.println("�ֻ���Ļ�ֱ���Ϊ��" + dm.widthPixels + " x " +
            dm.heightPixels);
        Device.screenHeight = dm.heightPixels;
        Device.screenWidth = dm.widthPixels;

        //new Ball(this,GameObject.BALL,null);
        //׼���ֺ�ͷ
        new Head(this,
            new int[] { R.drawable.avatar_jy1, R.drawable.avatar_jy2 });
        new Hand(this, new int[] { R.drawable.hand });
        new Ball(this);

        //���������¼����߳�
        gameEventHander = new GameEventHandler();
        gameEventHander.start();
        mediaPalyer = new GameMediaPlayer(this);
    }
    
    GameEventHandler gameEventHander;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameEventHander.isRunning = false;
        mediaPalyer.destroy();
    }

    /**
     * ���Menuʱ��ϵͳ���õ�ǰActivity��onCreateOptionsMenu����������һ��ʵ����һ��Menu�ӿڵ�menu������ʹ��
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
         * add()�������ĸ������������ǣ�
         * 1��������������Ļ���дMenu.NONE,
         * 2��Id���������Ҫ��Android�������Id��ȷ����ͬ�Ĳ˵�
         * 3��˳���Ǹ��˵�������ǰ������������Ĵ�С����
         * 4���ı����˵�����ʾ�ı�
         */

        // android.R��ͷ����Դ��ϵͳ�ṩ�ģ������Լ��ṩ����Դ����R��ͷ��
        menu.add(Menu.NONE, Const.MENU_PEREFERNCE, Menu.FIRST, "����")
            .setIcon(android.R.drawable.ic_menu_preferences);
        menu.add(Menu.NONE, Const.MENU_EXIT, Menu.FIRST + 1, "�˳�")
            .setIcon(android.R.drawable.ic_menu_close_clear_cancel);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle all of the possible menu actions.
        switch (item.getItemId()) {
        case Const.MENU_PEREFERNCE:
            onMenuPereference();

            break;

        case Const.MENU_EXIT:
            onMenuExit();

            break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onMenuExit() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void onMenuPereference() {
        //Activity1 ��ת�� Activity2  ���ǻ���Ҫ��Activity2 �ٻص� Activity1�أ� ��������˵�� ������Activity2  ��ʹ�� startActivity() ���Ϳ����� �ǵ� ���� startActivityForResult() �ܹ�ֱ����������
        startActivityForResult(new Intent(this, PreferencesActivity.class), 10);
        
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
        case RESULT_CANCELED:
           this.loadSetting();//��Back���ؼ���ʱ�������¼���һ�����õ�ֵ
        }
    }
    
    private void loadSetting(){
    	int i = getPreference(Const.KEY_HEAD_MOVE_REQUENCY);
    	if(i!=-1) Const.HEAD_MOVE_REQUENCY = i;
    	
    	i = getPreference(Const.KEY_HEAD_MOVE_STEP);
    	if(i!=-1) Const.HEAD_MOVE_STEP = i;
    	
    	i = getPreference(Const.KEY_PAUSE_TIME);
    	if(i!=-1) Const.PAUSE_TIME = i;
    	
    	i = getPreference(Const.KEY_REFRESH_REQUENCY);
    	if(i!=-1) Const.REFRESH_REQUENCY = i;
    }
    
    private int getPreference(String key){
    	String val = PreferenceManager.getDefaultSharedPreferences(this).getString(key,null);
    	if(val==null) {
    		return -1;
    	}
	    try{
	    	int i = Integer.parseInt(val);
	    	Log.d("piginzoo","Key["+key+"] return new int value["+ val +"]");
	    	return i;
		}catch(Exception e){
			return -1;
		}
    }
    
    
}
