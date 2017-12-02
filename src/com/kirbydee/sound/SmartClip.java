package com.kirbydee.sound;

import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import lombok.Data;

import com.kirbydee.main.Creatable;

@Data
public class SmartClip implements Creatable {

	private Clip clip;
	
	
	public SmartClip(Clip clip) {
		this.clip = clip;
	}
	
	public SmartClip(SmartClip smartClip) {
		this.clip = smartClip.getClip();
	}
	
	public void setVolume(float gain) {
		if (clip == null) 
			return;
		
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	    float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
	    gainControl.setValue(dB);
	}
	
	public float getVolume() {
		if (clip == null) 
			return 0;
		
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		return gainControl.getValue();
	}
	
	public void setMute(boolean mute) {
		if (clip == null) 
			return;
		
		BooleanControl muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
		muteControl.setValue(mute);
	}
	
	public boolean getMute() {
		if (clip == null) 
			return false;
		
		BooleanControl muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
		return muteControl.getValue();
	}
	
	public void play() {
		if (clip == null) 
			return;
		
		stop();
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void reset() {
		setFramePosition(0);
	}
	
	public void setFramePosition(int frameNumber) {
		clip.setFramePosition(frameNumber);
	}
	
	public void stop() {
		if (clip == null) 
			return;
		
		if (clip.isRunning())
			clip.stop();
	}
	
	@Override
	public void destroy() {
		clip = null;
	}
}