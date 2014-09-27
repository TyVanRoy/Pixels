package com.pixels.sprite;

import java.awt.Color;

import com.pixels.master.Game;

public abstract class GravityBoundSprite extends BehavioralSprite{
	private int weight;

	protected GravityBoundSprite(Game game, int x, int y, Color[][] shape, int weight){
		super(game, x, y, shape);
		this.weight = weight;
	}
	
	@Override
	public void behave(){
		y += weight;
	}
}
