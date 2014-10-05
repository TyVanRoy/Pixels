package com.pixels.sprite;

import java.awt.Dimension;
import java.awt.Point;

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

		return new Dimension(contactWidth, contactHeight);
	}

	/**
	 * Checks the sprite's distance to the ground in its y field based on the
	 * closest point. Returns 1 if the sprite is already touching the ground. If
	 * there is no ground beneath it, returns -1.
	 */
	protected synchronized int distanceFromGround() {
		int distance = -1;
		PixelMap map = game.getMap();
		Point[] startingPoints = new Point[shape.width()];

		for (int y = 0; y < shape.height(); y++) {
			for (int x = 0; x < shape.width(); x++) {
				if (y == shape.height() - 1
						|| map.get(this.x + x, this.y + y + 1) != null) {
					startingPoints[x] = new Point(this.x + x, this.y + y);
				}
			}
		}

		for (Point point : startingPoints) {
			if (point == null)
				continue;
			int x = point.x;
			int y = point.y;

			for (int i = 1; i < map.height() - y; i++) {
				if (map.get(x, y + i) != null) {
					if (distance == -1 || i < distance) {
						distance = i;
					}
				}
			}
		}

		return distance;
	}

	/**
	 * Checks the sprite's distance to the ground in its y field up until the
	 * input (to put less work on the machine) based on the closest point.
	 * Returns 1 if the sprite is already touching the ground. If there is no
	 * ground beneath it, returns -1.
	 */
	protected synchronized int distanceFromGround(int limit) {
		int distance = -1;
		PixelMap map = game.getMap();
		Point[] startingPoints = new Point[shape.width()];

		for (int y = 0; y < shape.height(); y++) {
			for (int x = 0; x < shape.width(); x++) {
				if (y == shape.height() - 1
						|| map.get(this.x + x, this.y + y + 1) != null) {
					startingPoints[x] = new Point(this.x + x, this.y + y);
				}
			}
		}

		for (Point point : startingPoints) {
			if (point == null)
				continue;
			int x = point.x;
			int y = point.y;

			for (int i = 1; i < limit; i++) {
				if (map.get(x, y + i) != null) {
					if (distance == -1 || i < distance) {
						distance = i;
					}
				}
			}
		}

		return distance;
	}

	public abstract void behave();

}
