package com.kirbydee.gamestate;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.kirbydee.animation.SmartImage;
import com.kirbydee.sound.SmartClip;
import com.kirbydee.utils.Log;
import lombok.Data;

import com.kirbydee.cache.Cache;
import com.kirbydee.main.Controlable;
import com.kirbydee.main.IOListener;
import com.kirbydee.main.Viewable;
import com.kirbydee.mvc.Controller;
import com.kirbydee.mvc.Viewer;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

@Data
public abstract class GameState implements IOListener, Controlable {
	
	// game states
	public enum State {
		LOADING_STATE,
        MENU_STATE,
        PLAY_STATE,
        EDITOR_STATE
	}
	
	// game state manager
	protected GameStateManager gsm;

    // caches
	protected Cache<Clip> soundCache;
	protected Cache<BufferedImage> imageCache;
	
	// MVC
	private Controller controller;
	private Viewer viewer;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
		this.soundCache = new Cache<>();
		this.imageCache = new Cache<>();
		
		this.controller = new Controller(this);
		this.viewer = new Viewer(this, controller);
	}
	
	@Override
	public void create() throws Exception {
		// create Sound Cache
		soundCache.create();

		// create Image Cache
		imageCache.create();
		
		// create controller
		controller.create();
		
		// create viewer
		viewer.create();
	}
	
	@Override
	public void destroy() throws Exception {
		// destroy Sound Cache
		soundCache.destroy();

		// destroy Image Cache
		imageCache.destroy();
		
		// destroy controller
		controller.destroy();
		
		// destroy viewer
		viewer.destroy();
	}
	
	@Override
	public void update() throws Exception {
		// update controller
		controller.update();
		
		// update viewer
		viewer.update();
	}
	
	@Override
	public void render(Graphics2D g) throws Exception {
		// render viewer
		viewer.render(g);
	}
	
	public int create(Viewable v) throws Exception {
		// create viewable
		return controller.create(v);
	}
	
	public int destroy(Viewable v) throws Exception {
		// destroy viewable
		return controller.destroy(v);
	}

	public boolean onScreen(Viewable v) {
//		Vector2f screenPosition = v.getPositionScreen();
//		int width = v.getWidth();
//		int height = v.getHeight();
//		
//		return screenPosition.getX() + width > 0 &&
//				screenPosition.getX() - width < GamePanel.WIDTH &&
//				screenPosition.getY() + height > 0 &&
//				screenPosition.getY() - height < GamePanel.HEIGHT;
		
		return true;
	}

    public SmartImage requestImage(String fileName) throws Exception {
        // request image from cache
        BufferedImage image = imageCache.request(fileName);

        // if image is not existing, create a new one and store it in cache
        if (image == null) {
            image = ImageIO.read(getClass().getResourceAsStream(fileName));
            imageCache.put(fileName, image);
        }

        // create Smart Image
        return new SmartImage(image);
    }

    public SmartImage requestImage(String fileName, int x, int y, int w, int h) throws Exception {
        // get smart image
        SmartImage image = requestImage(fileName);

        // return sub image
        return image.subImage(x, y, w, h);
    }

    public SmartClip requestClip(String fileName) throws Exception {
        // request sound from cache
        Clip clip = soundCache.request(fileName);

        // if sound is not existing, create a new one and store it in cache
        if (clip == null) {
            AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(fileName));
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false);
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(dais);
            soundCache.put(fileName, clip);
        }

        // return smart clip
        return new SmartClip(clip);
    }

    public void debug(String message) {
        Log.d(message);
    }

    public void error(String message) {
        Log.e(message);
    }

    public void error(String message, Throwable e) {
        Log.e(message, e);
    }

    public void info(String message) {
        Log.i(message);
    }

    public void verbose(String message) {
        Log.v(message);
    }

    public void warn(String message) {
        Log.w(message);
    }

    public void warn(String message, Throwable e) {
        Log.w(message, e);
    }

    public void hubu(String message) {
        Log.hubu(message);
    }
	
	@Override
	public void keyPressed(KeyEvent key) {
		// do nothing
	}

	@Override
	public void keyReleased(KeyEvent key) {
		// do nothing
	}
	
	@Override
	public void keyTyped(KeyEvent key) {
		// do nothing
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// do nothing
	}
}