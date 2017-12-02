package com.kirbydee.sound;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Data;

import com.kirbydee.gamestate.GameState;
import com.kirbydee.main.Creatable;
import com.kirbydee.sound.Sound.SoundType;

@Data
public class Sounds implements Creatable { 
	
	private Map<SoundType, Sound> sounds;
	private Sound currentSound;
	private Sound previousSound;
	private SoundType currentSoundType;
	private SoundType previousSoundType;
	private int numSounds;
	
	public Sounds(Class<? extends SoundType> soundSet, GameState gameState) throws Exception {		
		this.sounds = new ConcurrentHashMap<>(soundSet.getEnumConstants().length);
		for (SoundType soundType : soundSet.getEnumConstants()) {
			String soundFileName = soundType.getSoundFileName();
			sounds.put(soundType, new Sound(soundFileName, gameState));
		}
	}
	
	@Override
	public void destroy() {
		for (SoundType type : sounds.keySet()) {
			Sound sound = sounds.remove(type);
			sound.destroy();
			sound = null;
		}
	}
	
	public static SoundType getSoundTypeByString(Class<? extends SoundType> soundSet, String soundType) {
		if (soundSet == null)
			throw new NullPointerException("Enum Set SoundType is null!");
		
		for (SoundType sound : soundSet.getEnumConstants())
			if (sound.toString().compareTo(soundType) == 0)
				return sound;
		
		return null;
	}
	
	public void playSound(SoundType sound) {
		playSound(sound, 0);
	}
	
	public void playSoundRandom(SoundType sound) {
		playSound(sound, -1);
	}
	
	public void playSound(SoundType sound, int index) {
//		if (currentSoundType != sound) {
			previousSound = currentSound;
			previousSoundType = currentSoundType;
			currentSound = sounds.get(sound);
			currentSoundType = sound;
			if (index < 0)
				currentSound.playRandom();
			else
				currentSound.play(index);
//		}
	}
}
