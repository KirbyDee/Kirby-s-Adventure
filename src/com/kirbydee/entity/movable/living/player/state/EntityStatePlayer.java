package com.kirbydee.entity.movable.living.player.state;

import com.kirbydee.entity.EntityState;
import com.kirbydee.entity.EntityStateType;
import com.kirbydee.entity.movable.living.player.Player;

public abstract class EntityStatePlayer extends EntityState<Player> {

    public enum State implements EntityStateType {
        IDLE,
        TURN,
        WALK,
        JUMP,
        SALTO,
        FALL;
    }

	public EntityStatePlayer(final Player player, final State stateType) throws Exception {
		super(player, stateType);
	}

    @Override
    public EntityStateType[] getTypes() {
        return State.values();
    }
}
