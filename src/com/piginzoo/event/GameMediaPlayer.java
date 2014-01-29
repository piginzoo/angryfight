package com.piginzoo.event;

import android.content.Context;
import android.media.MediaPlayer;

import com.piginzoo.R;

public class GameMediaPlayer implements EventListener{
    private MediaPlayer soundA;
    private MediaPlayer soundHit;

	public GameMediaPlayer(Context context){
        soundA = MediaPlayer.create(context, R.raw.a);
        soundHit = MediaPlayer.create(context, R.raw.hit);
        GameEventHandler.registListener(this);
	}
	public void onEvent(Event e) {
		if(e.type!=EventType.COLLISION) return;
		this.soundA.start();
	}

	public void destroy(){
        if (soundA != null) {
            soundA.release();
            soundA = null;
        }

        if (soundHit != null) {
            soundHit.release();
            soundHit = null;
        }
	}
}
