package com.piginzoo.objects;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;


/**
 * 实际上对象包含了一个矩形
 * @author LiuC1
 *
 */
public abstract class GameObject {
    private static Map<Integer, GameObject> gameObjects = new HashMap<Integer, GameObject>();
    public float _left;
    public float _top;
    public float _width;
    public float _height;
    protected Bitmap[] bitmaps;
	protected Paint paint;

	public abstract void initialize(); 
	
    public GameObject(Context context, int id, int[] resourceId) {
    	this.initialize();
        this.loadHeadBitmap(context, resourceId);
        gameObjects.put(id, this);
    }

    public float _right() {
        return _left + _width;
    }

    public float _bottom() {
        return _top + _height;
    }
    


    /**
     * 算法，看看obj的四个点是否在我的矩形范围内
     * (0,0)-------------->
     *   |  
     *   |  
     *   \/
     */
    public boolean isOverlap(GameObject obj) {
        if (this.isInScope(obj._left, obj._top)) {
            return true;
        }

        if (this.isInScope(obj._right(), obj._top)) {
            return true;
        }

        if (this.isInScope(obj._left, obj._bottom())) {
            return true;
        }

        if (this.isInScope(obj._right(), obj._bottom())) {
            return true;
        }

        //还要考虑obj很大，完全包含我的情况,
        //那么只需要验证其中的一个点，我选择了左上角
        if (obj.isInScope(_left, _top)) {
            return true;
        }

        return false;
    }

    /**
     * 判断一个点是否在矩形内
     * @param x
     * @param y
     * @return
     */
    public boolean isInScope(float x, float y) {
        return 	(x > _left) && 
        		(y > _top) &&
        		(x < _right()) && 
        		(y < _bottom());
        
    }

    private void loadHeadBitmap(Context context, int[] resourceIds) {
    	if(context ==null || resourceIds==null) return;
        Resources r = context.getResources();
        this.bitmaps = new Bitmap[resourceIds.length];
        for (int i = 0; i < resourceIds.length; i++) {
            Bitmap originalBitmap = BitmapFactory.decodeResource(r,resourceIds[i]);
            this.bitmaps[i] = Bitmap.createScaledBitmap(originalBitmap,
                    (int)this._width, (int)this._height, true);
        }
    }

    public abstract void draw(Canvas c);

    public static GameObject findById(int id) {
        return gameObjects.get(id);
    }

    private static GameObject[]  gameObjectArray;
    public static GameObject[] findAll() {
    	if(gameObjectArray!=null) return gameObjectArray;
    	
    	//如果是第一次，就把东西都灌到数组里，这样做是为了提速
    	gameObjectArray = new GameObject[gameObjects.values().size()];
    	int i=0;
    	for(GameObject go: gameObjects.values()){
    		gameObjectArray[i++] = go;
    	}
    	return gameObjectArray;
    }
    
    public static final int HEAD = 0;
    public static final int HAND = 1;
    public static final int BALL = 2;
    
}
