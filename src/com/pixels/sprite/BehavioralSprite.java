package com.pixels.sprite;

import java.awt.Dimension;

import com.pixels.master.Game;
import com.pixels.pixelgroup.PixelMap;

public abstract class BehavioralSprite extends Sprite {
	protected int movementDistance, weight;
	protected double verticalCapacity;

	protected BehavioralSprite(Game game, int x, int y, int movementDistance,
			double verticalCapacity, PixelMap shape, int weight) {
		super(game, x, y, shape);
		this.movementDistance = movementDistance;
		this.weight = weight;
	}

	/**
	 * Returns the height of the object that is making contact with the right
	 * side of the sprite if there is one. And the distance to that object if
	 * it's within the movement distance.
	 * 
	 * If contactWidth is 0, then there is no contact and the contactHeight will
	 * be 0 and should be ignored.
	 * 
	 * If contactWidth > 0, then is indicates the distance to contact, in which
	 * case contactHeight will indicate the height of the object which is
	 * closest to contact (from the bottom-up).
	 */
	@Deprecated
	protected synchronized Dimension getRightContactMagnitude() {
		int contactWidth = 0;
		int contactHeight = 0;

		PixelMap map = game.getMap();
		for (int y = 0; y < shape.height(); y++) {
			for (int x = 0; x < shape.width(); x++) {
				if (shape.get(x, y) != null) {
					if (x == shape.width() - 1 || shape.get(x + 1, y) == null) {
						if (movementDistance > 1) {
							for (int i = 1; i < movementDistance; i++) {
								if (map.get(this.x + x + i, this.y + y) != null) {
									if (i < movementDistance) {
										if (contactWidth == 0) {
											contactWidth = i;
										} else if (i < contactWidth && i != 0)
											contactWidth = i;
									}
								}
							}
						} else {
							if (map.get(this.x + x + 1, this.y + y) != null) {
								contactWidth = 1;
							}
						}
					}
				}
			}
		}

		if (!isGrounded()) {
			contactWidth--;
		}
		return new Dimension(contactWidth, contactHeight);
	}

	/**
	 * Checks for bottom contact and also makes last minute adjustments to the
	 * position of needed
	 */
	@Deprecated
	protected synchronized boolean isGrounded() {
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

	public abstract void behave();

}
