package com.pixels.sprite;

import java.awt.Color;
import java.awt.Point;

import com.pixels.master.Game;
import com.pixels.pixelgroup.Animation;
import com.pixels.pixelgroup.PixelMap;

public abstract class Sprite {
	protected Game game;
	protected int x, y;
	protected PixelMap shape;
	protected Animation animation;

	protected Sprite(Game game, int x, int y, PixelMap shape) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.shape = shape;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public synchronized PixelMap shape() {
		return shape;
	}

	public void setColor(Color color) {
		shape.fill(color);
	}

	public void setLocation(Point point) {
		// Validates the coordinates first
		if (point.x > -1 && point.x <= game.getMap().width() - shape.width()) {
			x = point.x;
		}
		if (point.y > -1 && point.y <= game.getMap().height() - shape.height()) {
			y = point.y;
		}
	}

	public synchronized void setShape(PixelMap newShape) {
		int oldWidth = shape.width();
		int oldHeight = shape.height();
		int width = newShape.width();
		int height = newShape.height();
		shape = newShape;
		
		int xDif = (width - oldWidth) / 2;
		int yDif = (height - oldHeight) / 2;
		
		x -= xDif;
		y -= yDif;
	}

	protected void swapAnimation(Animation animation) {
		if (this.animation != null)
			this.animation.stop();
		this.animation = animation;
		if (this.animation != null)
			this.animation.start();
	}

	public abstract void revert();

}
