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

	@Override
	public void behave() {
		int distance = distanceFromGround(weight);
		
		if (distance >= weight || distance == -1) {
			y += weight;
		} else if (distance == 0) {
			return;
		} else if (distance < weight) {
			y += distance - 1;
		}
	}
}
