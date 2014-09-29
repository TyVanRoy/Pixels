package com.pixels.sprite;

import com.pixels.master.Game;
import com.pixels.master.PixelMap;

public abstract class BehavioralSprite extends Sprite{

	protected BehavioralSprite(Game game, int x, int y, PixelMap shape){
		super(game, x, y, shape);
	}

	public abstract void behave();
	
}
