package com.kirbydee.entity.movable.living.player.state;

import com.kirbydee.animation.Animation;
import com.kirbydee.entity.movable.living.player.Player;
import com.kirbydee.sound.Sound;

public class PlayerFallState extends EntityStatePlayer {

	public PlayerFallState(final Player player, final State stateType) throws Exception {
		super(player, stateType);
	}

    @Override
    protected Animation setAnimation() throws Exception {
        return new Animation("/sprites/player/kirby_normal/fall.anim", this.playState);
    }

    @Override
    protected Sound setSound() throws Exception {
        return new Sound("/sounds/player/fall.sound", this.playState);
    }

    @Override
    public void update() throws Exception {
        super.update();

        // if there is no velocity anymore in y direction, we are in idle again
        if (this.entity.getVelocity().getY() == 0)
            setNewStateOfEntity(State.IDLE);
    }
}
