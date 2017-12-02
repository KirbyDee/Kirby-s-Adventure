package com.kirbydee.animation;

import java.awt.*;
import java.awt.image.BufferedImage;

import lombok.Data;

import com.kirbydee.gamestate.GameState;
import com.kirbydee.utils.Vector2f;

@Data
public class SmartImage {

    // the game state
    private GameState gameState;

	// the image itself
	private BufferedImage image;

    // quality of the image
    private int jpegQuality;
	
	// dimensions
	private int width;
	private int height;
	
	public SmartImage(BufferedImage image) {
		this.image = image;
        this.jpegQuality = 0;
		updateDimensions();
	}
	
	public SmartImage(SmartImage smartImage) {
		this.image = smartImage.getImage();
        this.jpegQuality = 0;
        updateDimensions();
	}
	
	private SmartImage updateDimensions() {
		this.width = image.getWidth();
		this.height = image.getHeight();

        return this;
	}

    public boolean isValid() {
        return image != null;
    }
	
	public void render(Graphics2D g, Vector2f position, boolean facingRight) {
        int width = this.width * (facingRight ? 1 : -1);
        g.drawImage(image,
                (int) (position.getX() - width / 2),
                (int) (position.getY() - height / 2),
                width,
                height,
                null);
	}

    public SmartImage subImage(int x, int y, int w, int h) {
        this.image = this.image.getSubimage(x, y, w, h);
        updateDimensions();

        return this;
    }

    public SmartImage fill(int r, int g, int b, int a) {
        return fill(new Color(r, g, b, a));
    }

    public SmartImage fill(Color color) {
        return fillRect(color, 0, 0, image.getWidth(), image.getHeight());
    }

    public SmartImage fillRect(int r, int g, int b, int a, int x, int y, int w, int h) {
        return fillRect(new Color(r, g, b, a), x, y, w, h);
    }

    public SmartImage fillRect(Color color, int x, int y, int w, int h) {
        Graphics2D g = image.createGraphics();
        g.setBackground(color);
        g.clearRect(x, y, w, h);
        g.dispose();

        return this;
    }

    public SmartImage rotate(double angle) {
        // angles
        double sin = Math.abs(Math.sin(Math.toRadians(angle)));
        double cos = Math.abs(Math.cos(Math.toRadians(angle)));

        // new width and height
        int neww = (int) Math.floor(this.width * cos + this.height * sin);
        int newh = (int) Math.floor(this.height * cos + this.width * sin);

        // create new image
        BufferedImage image = createImage(neww, newh);
        Graphics2D g = image.createGraphics();

        // rotate
        g.translate((neww - this.width) / 2, (newh - this.height) / 2);
        g.rotate(Math.toRadians(angle), this.width / 2, this.height / 2);
        g.drawRenderedImage(this.image, null);
        g.dispose();

        // set new image
        this.image = image;
        updateDimensions();

        return this;
    }

    private BufferedImage createImage(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public SmartImage scaleHeight(int h) {
        // return if height is the same
        if (h == this.height)
            return this;

        // get new width
        int w = (this.width * h) / (this.height);
        return scale(w, h);
    }

    public SmartImage scaleWidth(BufferedImage bufferedImage, int w) {
        // return if width is the same
        if (w == this.width)
            return this;

        // get new height
        int h = (this.height * w) / (this.width);
        return scale(w, h);
    }

    public SmartImage scale(float percent) {
        // get new width and height
        int w = (int) ((float) this.width * percent);
        int h = (int) ((float) this.height * percent);
        return scale(w, h);
    }

    public SmartImage scale(int w, int h) {
        // scale image
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = image.createGraphics();
        graphics2D.drawImage(this.image.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
        graphics2D.dispose();

        // set new image
        this.image = image;
        updateDimensions();

        return this;
    }

    public SmartImage cropRect() {
        return cropRect(1.0f, true);
    }

    public SmartImage cropRect(float ratio) {
        return cropRect(ratio, true);
    }

    public SmartImage crop(float ratio) {
        return cropRect(ratio, true);
    }

    public SmartImage cropRect(float ratio, boolean centered) {
        float imageRatio = (float) this.width / this.height;
        if (ratio > imageRatio) {
            int offset = 0;
            int h = (int) (this.height * imageRatio / ratio);
            if (centered)
                offset = (this.height - h) / 2;
            return subImage(0, offset, this.width, h);
        } else {
            int offset = 0;
            int w = (int) (this.width * ratio / imageRatio);
            if (centered)
                offset = (this.width - w) / 2;
            return subImage(offset, 0, w, this.height);
        }
    }

    public SmartImage expand(int w, int h) {
        return expand(w, h, (w - this.width) / 2, (h - this.height) / 2);
    }

    public SmartImage expand(int w, int h, int x, int y) {
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = image.createGraphics();
        graphics2D.drawImage(this.image, x, y, this.width, this.height, null);
        graphics2D.dispose();
        this.image = image;
        updateDimensions();

        return this;
    }
}
