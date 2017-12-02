package com.kirbydee.tilemap;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import lombok.Data;

import com.kirbydee.animation.SmartImage;
import com.kirbydee.gamestate.play.PlayState;
import com.kirbydee.main.Creatable;

import static com.kirbydee.utils.Utils.*;

import com.kirbydee.tilemap.Tile.TileType;
import com.kirbydee.utils.Vector2f;

@Data
public class TileMap implements Creatable {
		
	// play State
	private PlayState playState;
	
	// position
	private Vector2f position;
	
	// map
	private String mapPath;
	private Tile[][] tileMap;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	// tileset
	private String tileSetPath;
	
	
	public TileMap(PlayState playState, int tileSize, String mapPath, String tileSetPath)  throws Exception {
		this.playState = playState;
		this.tileSize = tileSize;
		this.position = new Vector2f();
		this.mapPath = mapPath;
		this.tileSetPath = tileSetPath;
		
		create();
	}
	
	public void create() throws Exception {
		// reads the map
		InputStream inputStream = getClass().getResourceAsStream(mapPath);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader br = new BufferedReader(inputStreamReader);
		
		// get dimensions
		numCols = Integer.parseInt(br.readLine());
		numRows = Integer.parseInt(br.readLine());
		tileMap = new Tile[numRows][numCols];
		width = numCols * tileSize;
		height = numRows * tileSize;
		
		// pre-load image to get its dimensions
		SmartImage image = playState.requestImage(tileSetPath);
		int numTilesAcross = image.getWidth() / tileSize;
		
		// create tiles
		for (int row = 0; row < numRows; row++) {
			String line = br.readLine();
			String[] tokens = line.split(FILE_DELIMS);
			for (int col = 0; col < numCols; col++) {
				int rc = Integer.parseInt(tokens[col]);
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
				// time image
				SmartImage tileImage = playState.requestImage(tileSetPath, c * tileSize, r * tileSize, tileSize, tileSize);
				
				// create tile
				tileMap[row][col] = new Tile(tileImage, TileType.values()[r], tileSize, new Vector2f(col * tileSize, row * tileSize), playState);
			}
		}		
	}
	
	@Override
	public void destroy() throws Exception {		
		// map
		for (Tile[] tiles : tileMap)
			for (Tile tile : tiles)
				tile.destroy();
		tileMap = null;
		
		// position
		position = null;
	}
	
	public TileType getType(int row, int col) {
		return tileMap[row][col].getType();
	}
	
	// defensive copy
	public Vector2f getPosition() {
		return new Vector2f(position);
	}
}
