package com.kirbydee.hud;

import java.awt.Color;
import java.awt.Graphics2D;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.kirbydee.animation.SmartImageColor;
import com.kirbydee.animation.SmartImage;
import com.kirbydee.entity.Entity.EntityType;
import com.kirbydee.entity.movable.living.LivingEntity;
import com.kirbydee.entity.movable.living.player.Player;
import com.kirbydee.gamestate.play.PlayState;
import com.kirbydee.main.Viewable;
import com.kirbydee.mvc.Viewer.DepthType;
import com.kirbydee.utils.Vector2f;

@Data
@EqualsAndHashCode(callSuper=false)
public abstract class HUDBar<E extends LivingEntity> extends Viewable {
	
	public static final int GAP = 1;
	public static final int BAR_HEIGHT = 7;
	public static final int BAR_WIDTH = 30;
	public static final int ICON_HEIGHT = 7;
	public static final int ICON_WIDTH = 7;
	public static final int OFFSET = 10;
	
	// entity
	protected E livingEntity;
	
	// images
	protected SmartImage barImage;
	protected SmartImage iconImage;
	protected SmartImageColor barColor;
	
	// values
	protected int value;
	protected int maxValue;
	
	// position
	protected Vector2f position;
	protected int offset;
	
	
	public HUDBar(E livingEntity, PlayState playState, String iconPath, Color color) throws Exception {
		super(playState);
		this.iconImage = playState.requestImage(iconPath);
		this.iconImage.setWidth(HUDBar.ICON_WIDTH);
		this.iconImage.setHeight(HUDBar.ICON_HEIGHT);
		this.offset = 0;
		
		// create bar frame
		if (livingEntity instanceof Player)
			this.barImage = playState.requestImage("/sprites/hud/barPlayer.gif");
		else if (livingEntity.getEntityType() == EntityType.FRIENDLY)
			this.barImage = playState.requestImage("/sprites/hud/barFriendly.gif");
		else if (livingEntity.getEntityType() == EntityType.ENEMY)
			this.barImage = playState.requestImage("/sprites/hud/barEnemy.gif");
		else
			this.barImage = playState.requestImage("/sprites/hud/barNeutral.gif");
		this.barImage.setWidth(HUDBar.BAR_WIDTH);
		this.barImage.setHeight(HUDBar.BAR_HEIGHT);
		
		this.barColor = new SmartImageColor(color, HUDBar.BAR_WIDTH, HUDBar.BAR_HEIGHT);
		this.livingEntity = livingEntity;
	}
	
	@Override
	public void destroy() throws Exception {
		barImage = null;
		iconImage = null;
		barColor = null;
		
		super.destroy();
	}
	
	@Override
	public void update() {
		// position
		updatePosition(livingEntity);
		setPosition();
		
		// update values
		updateMaxValue(livingEntity);
		updateValue(livingEntity);
		setValue();
	}

	@Override
	public void render(Graphics2D g) {
		// check if maxValue is the same as value
		if (maxValue != value) {
			// draw bar values
			barColor.render(g, position.sub((HUDBar.BAR_WIDTH - barColor.getWidth()) / 2, 0));
			
			// draw underlying bar
	        barImage.render(g, position, false);

			// draw icon
			iconImage.render(g, position.sub(HUDBar.BAR_WIDTH / 2 + HUDBar.ICON_WIDTH + HUDBar.GAP, 0), false);
		}
	}
	
	protected abstract void updatePosition(E livingEntity);
	protected abstract void updateMaxValue(E livingEntity);
	protected abstract void updateValue(E livingEntity);
	
	public void setPosition() {
		position.subLocal(0, livingEntity.getHeight() + OFFSET + (BAR_HEIGHT+GAP)*offset);
	}
	
	public void setValue() {
		barColor.setWidth((int) ((float) HUDBar.BAR_WIDTH / maxValue * value));
	}
	
	// defensive copy
	public Vector2f getPosition() {
		return new Vector2f(position);
	}
	
	@Override
	public boolean isVisible() {
		return livingEntity.isVisible();
	}
	
	@Override
	public void setDepthType() {
		setDepthType(DepthType.GUI);
	}
}
