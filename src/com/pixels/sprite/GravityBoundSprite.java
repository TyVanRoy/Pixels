package com.pixels.sprite;

import java.awt.Color;

import com.pixels.master.Game;

public abstract class GravityBoundSprite extends BehavioralSprite {
	protected int weight;

	protected GravityBoundSprite(Game game, int x, int y, Color[][] shape,
			int weight) {
		super(game, x, y, shape);
		this.weight = weight;
	}

	// Checks sor bottom contact and also makes last minute adjustments to the
	// position of needed
	protected synchronized boolean checkForBottomContact() {		
		int dif = weight;
		
		Color[][] map = game.getMap();
		for (int y = 0; y < shape.length; y++) {
			for (int x = 0; x < shape[0].length; x++) {
				if (shape[y][x] != null) {
					if (y == shape.length - 1 || shape[y + 1][x] == null) {
						for(int i = 1; i < weight; i++){
							if(map[this.y + y + i][this.x + x] != null){
								if(i < dif){
									dif = i;
								}
							}
						}
					}
				}
			}
		}
		
		if(dif < weight){
			if(dif > 1){
				y += dif - 1;
			}
			return true;
		}
		return false;
	}
	@Override
	public void behave() {
		if (!checkForBottomContact())
			if (!this.equals(game.getRegisteredSprite()))
				y += weight;
	}
}
