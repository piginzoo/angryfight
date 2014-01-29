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
 * 程序的主入口
 */
public class AngryFightActivity extends Activity {
    GameMediaPlayer mediaPalyer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSetting();
        MainView mainView = new MainView(this);
        setContentView(mainView);

        /* 必须引用 android.util.DisplayMetrics */
        // metric 米制的，公制的  
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        System.out.println("手机屏幕分辨率为：" + dm.widthPixels + " x " +
            dm.heightPixels);
        Device.screenHeight = dm.heightPixels;
        Device.screenWidth = dm.widthPixels;

        //new Ball(this,GameObject.BALL,null);
        //准备手和头
        new Head(this,
            new int[] { R.drawable.avatar_jy1, R.drawable.avatar_jy2 });
        new Hand(this, new int[] { R.drawable.hand });
        new Ball(this);

        //启动处理事件的线程
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
     * 点击Menu时，系统调用当前Activity的onCreateOptionsMenu方法，并传一个实现了一个Menu接口的menu对象供你使用
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
         * add()方法的四个参数，依次是：
         * 1、组别，如果不分组的话就写Menu.NONE,
         * 2、Id，这个很重要，Android根据这个Id来确定不同的菜单
         * 3、顺序，那个菜单现在在前面由这个参数的大小决定
         * 4、文本，菜单的显示文本
         */

        // android.R开头的资源是系统提供的，我们自己提供的资源是以R开头的
        menu.add(Menu.NONE, Const.MENU_PEREFERNCE, Menu.FIRST, "设置")
            .setIcon(android.R.drawable.ic_menu_preferences);
        menu.add(Menu.NONE, Const.MENU_EXIT, Menu.FIRST + 1, "退出")
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
        //Activity1 跳转到 Activity2  但是还需要在Activity2 再回到 Activity1呢？ 可能有人说： 那我在Activity2  再使用 startActivity() 不就可以了 是的 但是 startActivityForResult() 能够直接完成这项工作
        startActivityForResult(new Intent(this, PreferencesActivity.class), 10);
        
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
        case RESULT_CANCELED:
           this.loadSetting();//按Back返回键的时候，来重新加载一下设置的值
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
