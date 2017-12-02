package com.kirbydee.entity.immovable;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.kirbydee.entity.Entity;
import com.kirbydee.gamestate.play.PlayState;

@Data
@EqualsAndHashCode(callSuper=false)
public abstract class ImmovableEntity extends Entity {
	
	// image
	BufferedImage image;
	
	// Builder
	public static abstract class Builder extends Entity.Builder {
		// constructor
		public Builder(PlayState gameState) {
			super(gameState);
		}
	}
	
	public ImmovableEntity(Builder builder) throws Exception {
		super(builder);
		this.image = null;
	}
	
	@Override
	public void render(Graphics2D g) throws Exception {
		// if there is an image to it
		if (image != null)
			g.drawImage(image, (int) (positionScreen.getX() - width / 2), (int) (positionScreen.getY() - height / 2), null);
	}
}
