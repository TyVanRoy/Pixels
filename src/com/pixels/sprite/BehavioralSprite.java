package com.pixels.sprite;

import java.awt.Color;

import com.pixels.master.Game;

public abstract class BehavioralSprite extends Sprite{

	protected BehavioralSprite(Game game, int x, int y, Color[][] shape){
		super(game, x, y, shape);
	}
	
	public abstract void behave();
	
}
