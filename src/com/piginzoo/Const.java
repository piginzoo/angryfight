package com.piginzoo;

/**
 * 常量类
 */
public class Const {
	/** 屏幕绘画频率 */
	public static int REFRESH_REQUENCY = 15;
	
	/** 头左右移动的位移（像素） */
	public static int HEAD_MOVE_STEP = 30;
	
	/** 头左右移动的位移的周期，3意味着 3*REFRESH_REQUENCY 才移动一下 */
	public static int HEAD_MOVE_REQUENCY = 3;
	
	/** 击中后要暂停一下，这是暂停时间（毫秒） */
	public static int PAUSE_TIME = 1000;
	
	public static final int MENU_PEREFERNCE = 1;
	public static final int MENU_EXIT = 2;
	
	public final static String  KEY_REFRESH_REQUENCY = "REFRESH_REQUENCY";
	public final static String  KEY_HEAD_MOVE_STEP = "HEAD_MOVE_STEP";
	public final static String  KEY_HEAD_MOVE_REQUENCY = "HEAD_MOVE_REQUENCY";
	public final static String  KEY_PAUSE_TIME = "PAUSE_TIME";
}
