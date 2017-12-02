package com.kirbydee.entity;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import lombok.Data;

import com.kirbydee.animation.Animation;
import com.kirbydee.gamestate.play.PlayState;
import com.kirbydee.main.Controlable;
import com.kirbydee.main.IOListener;
import com.kirbydee.sound.Sound;

@Data
public abstract class EntityState<E extends Entity> implements IOListener, Controlable {

	// main information
	protected E entity;
	protected PlayState playState;
	protected EntityStateType stateType;
	
	// possible animation
	protected Animation animation;
	
	// possible sound
	protected Sound sound;
	protected boolean playRandomSound;
	
	public EntityState(final E entity, final EntityStateType stateType) throws Exception {
		this.entity = entity;
		this.stateType = stateType;
		this.playState = entity.playState;
		
		create();
	}
	
	protected Animation setAnimation() throws Exception {
        return Animation.emptyAnimation();
    }

	protected Sound setSound() throws Exception {
        return Sound.emptySound();
    }
	
	protected void setNewStateOfEntity(final EntityStateType entityStateType) {
		if (this.entity.getEntityStates().containsKey(entityStateType)) {
            // stop animation / sound of current state
            this.animation.stop();
            this.sound.stop();
			
			// set new state
            this.entity.setCurrentEntityState(entityStateType);

            // start animation of new state
            EntityState<? extends Entity> newState = this.entity.getCurrentEntityState();
            newState.animation.start();
		}
	}
	
	@Override
	public void create() throws Exception {
        // set animation
		this.animation = setAnimation();

        // set sound
		this.sound = setSound();
	}
	
	@Override
	public void destroy() throws Exception {
		// destroy animation
        this.animation.destroy();
        this.animation = null;
		
		// destroy sound
        this.sound.destroy();
        this.sound = null;
	}
	
	@Override
	public void update() throws Exception {
        // update animation
        this.animation.setPosition(this.entity.getPositionScreen());
        this.animation.setFacingRight(this.entity.isFacingRight());
        this.animation.update();
		
		// play sound
		if (this.playRandomSound)
            this.sound.playRandom();
        else
            this.sound.play();
	}
	
	@Override
	public void render(Graphics2D g) throws Exception {
        this.animation.render(g);
	}

    public abstract EntityStateType[] getTypes();


    // TODO: not yet wired
	@Override
	public void mouseClicked(MouseEvent e) {
		// default nothing
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// default nothing
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// default nothing
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// default nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// default nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// default nothing
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// default nothing
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// default nothing
	}
}
