package com.pixels.sprite;

import java.awt.Dimension;

import com.pixels.master.Game;
import com.pixels.master.PixelMap;

public abstract class BehavioralSprite extends Sprite {
	protected int movementDistance;
	protected double verticalCapacity;

	protected BehavioralSprite(Game game, int x, int y, int movementDistance,
			double verticalCapacity, PixelMap shape) {
		super(game, x, y, shape);
		this.movementDistance = movementDistance;
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
	protected Dimension getRightContactMagnitude() {
		int contactWidth = movementDistance;
		int contactHeight = 0;

		PixelMap map = game.getMap();
		for (int y = 0; y < shape.height(); y++) {
			for (int x = 0; x < shape.width(); x++) {
				if (shape.get(x, y) != null) {
					if (x == shape.width() || shape.get(x + 1, y) == null) {
						if (movementDistance != 1) {
							for (int i = 0; i < movementDistance; i++) {
								// if(map.get(this.x + x + i, y))
							}
						}else{
							if(map.get(this.x + x + 1, y) != null){
								
							}
						}
					}
				}
			}
		}

		return new Dimension(contactWidth, contactHeight);
	}

	public abstract void behave();

}
