package com.piginzoo.event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

import android.util.Log;

public class GameEventHandler extends Thread {
    private static SynchronousQueue<Event> eventQueue = new SynchronousQueue<Event>();
    
    public static void publish(Event event) {
        eventQueue.offer(event);
    }

    public boolean isRunning = true;
    
    public void run(){
    	while(isRunning){
    		Event e = null;
			try {
				e = eventQueue.take();
			} catch (InterruptedException e1) {
				Log.i("piginzoo","The event thread interrupt.");
			}
    		if(e==null) continue;
    		
    		for(EventListener el : eventListeners){
    			el.onEvent(e);
    		}
    	}
    }
    
    private static List<EventListener> eventListeners = new ArrayList<EventListener>();
    public static void registListener(EventListener el){
    	eventListeners.add(el);
    }
    
    
}
