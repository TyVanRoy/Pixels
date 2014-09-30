package com.pixels.sprite;

import com.pixels.master.Game;
import com.pixels.master.PixelMap;

public abstract class GravityBoundSprite extends BehavioralSprite {
	protected int weight;

	protected GravityBoundSprite(Game game, int x, int y, int movementDistance,
			double verticalCapacity, PixelMap shape, int weight) {
		super(game, x, y, movementDistance, verticalCapacity, shape);
		this.weight = weight;
	}

	/**
	 * Checks for bottom contact and also makes last minute adjustments to the
	 * position of needed
	 */
	protected boolean isGrounded() {
		int dif = weight;

		PixelMap map = game.getMap();
		for (int y = 0; y < shape.height(); y++) {
			for (int x = 0; x < shape.width(); x++) {
				if (shape.get(x, y) != null) {
					if (y == shape.height() - 1 || shape.get(x, y + 1) == null) {
						if (weight > 1) {
							for (int i = 1; i < weight; i++) {
								if (map.get(this.x + x, this.y + y + i) != null) {
									if (i < dif) {
										dif = i;
									}
								}
							}
						} else {
							if (map.get(this.x + x, this.y + y + 1) != null) {
								return true;
							}
						}
					}
				}
			}
		}

		if (dif < weight) {
			if (dif > 1) {
				y += dif - 1;
			}
			return true;
		}
		return false;
	}

	@Override
	public void behave() {
		if (!isGrounded())
			if (!this.equals(game.getRegisteredSprite()))
				y += weight;
	}
}
