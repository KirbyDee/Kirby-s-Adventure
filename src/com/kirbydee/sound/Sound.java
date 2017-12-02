package com.kirbydee.sound;

import static com.kirbydee.utils.Utils.FILE_DELIMS;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Data;

import com.kirbydee.exception.SmartIndexOutOfBoundsException;
import com.kirbydee.gamestate.GameState;
import com.kirbydee.main.Creatable;

@Data
public class Sound implements Creatable {

    // empty animation
    public static Sound emptySound() {
        return new EmptySound();
    }

	private List<SmartClip> clips;
	private int numSounds;
	private int currentSound;

    public Sound() {}
	
	public Sound(String fileName, GameState gameState) throws Exception {
		// reads animation dimensions from file
		InputStream inputStream = getClass().getResourceAsStream(fileName);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader br = new BufferedReader(inputStreamReader);
		
		// get sound details
		String line = br.readLine();
		
		// split line
		String[] tokens = line.split(FILE_DELIMS);

		try {
			numSounds = Integer.parseInt(tokens[0]);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("1. line (number of Sounds) in File \"" + fileName + "\" contains a non-numerical input: " + line);
		}

		clips = new ArrayList<>(numSounds);
		for (int i = 0; i < numSounds; i++)
			clips.add(gameState.requestClip(tokens[i + 1]));

		this.currentSound = 0;
	}
	
	public void setVolume(float gain) {
		SmartClip clip = clips.get(currentSound);
		clip.setVolume(gain);
	}
	
	public float getVolume() {
		SmartClip clip = clips.get(currentSound);
		return clip.getVolume();
	}
	
	public void setMute(boolean mute) {
		SmartClip clip = clips.get(currentSound);
		clip.setMute(mute);
	}
	
	public boolean getMute() {
		SmartClip clip = clips.get(currentSound);
		return clip.getMute();
	}
	
	public void play() {
		play(0);
	}
	
	public void playRandom() {
		if (numSounds == 1) {
			play();
		} else {
			Random ran = new Random();
			int index = ran.nextInt(numSounds);
			play(index);
		}
	}
	
	public void play(int index) {
		if (index >= numSounds)
			throw new SmartIndexOutOfBoundsException(0, numSounds, index);
		
		SmartClip clip = clips.get(index);
		clip.play();
		currentSound = index;
	}
	
	public void stop() {
		SmartClip clip = clips.get(currentSound);
		clip.stop();
	}
	
	public void reset() {
		setFramePosition(0);
	}
	
	public void setFramePosition(int frameNumber) {
		SmartClip clip = clips.get(currentSound);
		clip.setFramePosition(frameNumber);
	}
	
	@Override
	public void destroy() {
		clips = null;
	}
	
	public interface SoundType {
		public abstract String getSoundFileName();
	}
}