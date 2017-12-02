package com.kirbydee.animation;

import static com.kirbydee.utils.Utils.FILE_DELIMS;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.kirbydee.gamestate.GameState;
import com.kirbydee.main.Controlable;
import com.kirbydee.utils.Vector2f;

/*
 * Animation file:
 * > filename number height width1 width2 width3 ... delay
 */
@Data
public class Animation implements Controlable {

    // empty animation
    public static Animation emptyAnimation() {
        return new EmptyAnimation();
    }
	
	// about the animation
	private List<SmartImage> frames;
	private SmartImage currentFrame;
	private int numFrames;
	private int currentFrameCount;
	private boolean animationStopped;
	private boolean facingRight;
	
	// position
	private Vector2f position;
	
	// dimensions
	private List<Integer> widths;
	private int height;
	
	// timings
	private long startTime;
	private long defaultDelay;
	private long delay;
	private boolean useDefaultDelay;
	
	// if it was played once or not
	private boolean playedOnce;

    public Animation() {}
	
	public Animation(String animFileName, GameState gameState) throws Exception {
		// reads animation dimensions from file
		InputStream inputStream = getClass().getResourceAsStream(animFileName);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader br = new BufferedReader(inputStreamReader);
		
		// get animation details
		String line = br.readLine();
		
		// split line
		String[] tokens = line.split(FILE_DELIMS);
		String spriteFileName = tokens[0];
		
		try {
			numFrames = Integer.parseInt(tokens[1]);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("1. line (number of Animations) in File \"" + animFileName + "\" contains a non-numerical input: " + line);
		}
		
		try {
			height = Integer.parseInt(tokens[2]);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("1. line (height) in File \"" + animFileName + "\" contains a non-numerical input: " + line);
		}
		
		widths = new ArrayList<>(numFrames);
		for (int i = 0; i < numFrames; i++) {
			try {
				widths.add(Integer.parseInt(tokens[i+3]));
			} catch (NumberFormatException e) {
				throw new NumberFormatException("1. line (width) in File \"" + animFileName + "\" contains a non-numerical input: " + line);
			}
		}
		
		frames = new ArrayList<>(numFrames);
		int width_start = 0;
		for (int i = 0; i < numFrames; i++) {
			int width = widths.get(i);
			
			frames.add(gameState.requestImage(spriteFileName, width_start, 0, width, height));
			width_start += width;
		}

		try {
			defaultDelay = Integer.parseInt(tokens[3+numFrames]);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("1. line (defaultDelay) in File \"" + animFileName + "\" contains a non-numerical input: " + line);
		}
		
		this.delay = 0;
		this.useDefaultDelay = true;
		this.playedOnce = false;
		this.animationStopped = false;
		this.facingRight = false;
		this.position = Vector2f.ZERO;
		this.currentFrame = frames.get(0);
	}
	
	public void start() {
		currentFrameCount = 0;
		startTime = System.nanoTime();
		playedOnce = false;
		animationStopped = false;
	}
	
	public void reset() {
		start();
	}
	
	public void stop() {
		animationStopped = true;
	}
	
	public void resume() {
		animationStopped = false;
		startTime = System.nanoTime();
	}
	
	@Override
	public void update() {		
		if (!animationStopped) {
			long realDelay;
			if (useDefaultDelay)
				realDelay = defaultDelay;
			else
				realDelay = delay;
			
			if (realDelay == -1)
				return;
			
			long elapsed = (System.nanoTime() - startTime) / 1000000;
			if (elapsed > realDelay) {
				if (currentFrameCount >= frames.size()-1)
					currentFrameCount = 0;
				else
					currentFrameCount++;
				
				currentFrame = frames.get(currentFrameCount);
				if (currentFrameCount >= frames.size()-1)
					playedOnce = true;
				
				startTime = System.nanoTime();
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		currentFrame.render(g, position, facingRight);
	}
	
	@Override
	public void destroy() {
		frames = null;
	}
	
	// defensive copy
	public Vector2f getPosition() {
		return new Vector2f(position);
	}
	
	public interface AnimationType {
		public abstract String getAnimFileName();
	}
}
