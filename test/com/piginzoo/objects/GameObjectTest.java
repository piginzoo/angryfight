package com.piginzoo.objects;
import junit.framework.TestCase;
import android.content.Context;
import android.graphics.Canvas;

public class GameObjectTest extends TestCase {

	GameObject go;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		go= new TestGameObject(100,100,100,100);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testIsOverlap() {
		assertFalse(go.isOverlap( new TestGameObject(0,0,50,50)));
		assertTrue(go.isOverlap( new TestGameObject(0,0,150,150)));
		assertTrue(go.isOverlap( new TestGameObject(150,150,100,100)));
		assertFalse(go.isOverlap( new TestGameObject(150,250,100,100)));
		assertFalse(go.isOverlap( new TestGameObject(250,250,100,100)));
		assertTrue(go.isOverlap( new TestGameObject(50,50,200,200)));
	}

	public void testIsInScope() {
		assertFalse(go.isInScope(0,0 ));
		assertFalse(go.isInScope(10,200));
		assertFalse(go.isInScope(200,10));
		assertFalse(go.isInScope(300,300 ));
		assertTrue(go.isInScope(150,150 ));
	}

}

class TestGameObject extends GameObject{
	
	public TestGameObject(int left,int top,int heigh,int width){
		super(null,0,new int[0]);
		super._left = left;
		super._top = top;
		super._height = heigh;
		super._width = width;
	}
	
	@Override
	public void draw(Canvas c) {
	}
	@Override
	public void initialize() {
	}
}