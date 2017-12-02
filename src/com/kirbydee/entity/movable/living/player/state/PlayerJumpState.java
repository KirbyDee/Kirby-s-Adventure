package com.kirbydee.entity.movable.living.player.state;

import com.kirbydee.animation.Animation;
import com.kirbydee.entity.movable.living.player.Player;
import com.kirbydee.sound.Sound;

public class PlayerJumpState extends EntityStatePlayer{

	public PlayerJumpState(final Player player, final State stateType) throws Exception {
		super(player, stateType);
	}

    @Override
    protected Animation setAnimation() throws Exception {
        return new Animation("/sprites/player/kirby_normal/jump.anim", this.playState);
    }

    @Override
    protected Sound setSound() throws Exception {
        return new Sound("/sounds/player/jump.sound", this.playState);
    }

    @Override
    public void update() throws Exception {
        super.update();

        // check velocity
        if (this.entity.getVelocity().getY() > -0.1)
            setNewStateOfEntity(State.SALTO);
    }
}
