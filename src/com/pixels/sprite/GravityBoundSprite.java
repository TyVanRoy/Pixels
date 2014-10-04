package com.pixels.sprite;

import com.pixels.master.Game;
import com.pixels.pixelgroup.PixelMap;

public abstract class GravityBoundSprite extends BehavioralSprite {
	protected int weight;

	protected GravityBoundSprite(Game game, int x, int y, int movementDistance,
			double verticalCapacity, PixelMap shape, int weight) {
		super(game, x, y, movementDistance, verticalCapacity, shape, weight);
		this.weight = weight;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void behave() {
		if (!this.equals(game.getRegisteredSprite()))
			if (!isGrounded())
				y += weight;
	}
}
