package com.kirbydee.gamestate.play;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.kirbydee.gamestate.GameStateManager;
import com.kirbydee.gamestate.play.PlayState;

@Data
@EqualsAndHashCode(callSuper=false)
public class PlayStateCustom extends PlayState {
	
	// custom level
	private String customLevelFileName;
	
	// Level builder
	public static class Builder extends PlayState.Builder {
		// mandatory fields
		private final String customLevelFileName;
		
		// constructor
		public Builder(GameStateManager gsm, String customLevelFileName) {
			super(gsm, 0, 0);
			this.customLevelFileName = customLevelFileName;
		}
		
		// build
		@Override
		public PlayStateCustom build() { return new PlayStateCustom(this); }
	}

	public PlayStateCustom(Builder builder) {
		super(builder);
		this.customLevelFileName = builder.customLevelFileName;
	}
	
	@Override
	public void createMap() throws Exception {	
		createMap("/maps/custom/" + customLevelFileName);
	}
}
