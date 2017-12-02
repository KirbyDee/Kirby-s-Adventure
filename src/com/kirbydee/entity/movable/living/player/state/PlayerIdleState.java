package com.kirbydee.entity.movable.living.player.state;

import com.kirbydee.animation.Animation;
import com.kirbydee.entity.EntityState;
import com.kirbydee.entity.EntityStateType;
import com.kirbydee.entity.movable.living.player.Player;
import com.kirbydee.sound.Sound;
import com.kirbydee.utils.Vector2f;

import java.awt.event.KeyEvent;
import java.util.Random;

public class PlayerIdleState extends EntityStatePlayer {

    protected boolean isIdle;
    protected Random rand;

	public PlayerIdleState(final Player player, final State stateType) throws Exception {
		super(player, stateType);

        // init idle state
        this.isIdle = false;
        this.rand = new Random();
	}

	@Override
	protected Animation setAnimation() throws Exception {
		return new Animation("/sprites/player/kirby_normal/idle.anim", this.playState);
	}

    @Override
    public void update() throws Exception {
        super.update();

        // randomly start idle state
        if (!this.isIdle && this.rand.nextInt(50) == 0) {
            // start idle animation
            this.isIdle = true;
            this.animation.start();
        } else if (this.animation.isPlayedOnce()) {
            // stop idle animation
            this.isIdle = false;
            this.animation.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                setNewStateOfEntity(State.WALK);
                break;
            case KeyEvent.VK_RIGHT:
                setNewStateOfEntity(State.WALK);
                break;
            case KeyEvent.VK_SPACE:
                setNewStateOfEntity(State.JUMP);
                break;
        }
    }
}
