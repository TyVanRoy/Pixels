package com.pixels.sprite;

import java.awt.Color;
import java.awt.Point;

import com.pixels.master.Game;
import com.pixels.master.PixelMap;

public abstract class Sprite {
	protected Game game;
	protected int x, y;
	protected PixelMap shape;

	protected Sprite(Game game, int x, int y, PixelMap shape){
		this.game = game;
		this.x = x;
		this.y = y;
		this.shape = shape;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public PixelMap shape(){
		return shape;
	}
	
	public void setColor(Color color){
		shape.fill(color);
	}
	
	public void setLocation(Point point){
		// Validates the coordinates first
		if(point.x > -1 && point.x <= game.getMap().width() - shape.width()){
			x = point.x;
		}
		if(point.y > -1 && point.y <= game.getMap().height() - shape.height()){
			y = point.y;
		}
	}
	
	public void setShape(PixelMap newShape){
		shape = newShape;
	}
	
	public abstract void revert();
		
}
