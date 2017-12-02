package com.kirbydee.entity.movable.living.player.state;

import com.kirbydee.animation.Animation;
import com.kirbydee.entity.movable.living.player.Player;
import com.kirbydee.sound.Sound;
import com.kirbydee.utils.Vector2f;

import java.awt.event.KeyEvent;

public class PlayerWalkState extends EntityStatePlayer {

    // direction
    protected boolean isRight;

	public PlayerWalkState(Player player, State stateType) throws Exception {
		super(player, stateType);
	}
	
	@Override
	public void update() throws Exception {
		super.update();
		
//		computeNextAcceleration();
	}
	
//	public void computeNextAcceleration() throws Exception {
//		float ddx = 0; // TODO
//
//		// x-direction
//		if (entity.isLeft())
//			// active left acceleration
//			ddx -= entity.getAccelerationValues().getX();
//		else if (entity.isRight())
//			// active right acceleration
//			ddx += entity.getAccelerationValues().getX();
//
//        entity.setAcceleration(new Vector2f(ddx, 0));
//	}

	@Override
	protected Animation setAnimation() throws Exception {
		return new Animation("/sprites/player/kirby_normal/walk.anim", playState);
	}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                setNewStateOfEntity(State.JUMP);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                setNewStateOfEntity(State.IDLE);
                break;

            case KeyEvent.VK_RIGHT:
                setNewStateOfEntity(State.IDLE);
                break;
        }
    }
}
