package com.kirbydee.entity.movable.living.player.state;

import com.kirbydee.animation.Animation;
import com.kirbydee.entity.movable.living.player.Player;

public class PlayerSaltoState extends EntityStatePlayer{

	public PlayerSaltoState(final Player player, final State stateType) throws Exception {
		super(player, stateType);
	}

    @Override
    protected Animation setAnimation() throws Exception {
        return new Animation("/sprites/player/kirby_normal/salto.anim", this.playState);
    }

    @Override
    public void update() throws Exception {
        super.update();

        // if animation is finished, go into fall state
        if (this.animation.isPlayedOnce())
            setNewStateOfEntity(State.FALL);
    }
}
