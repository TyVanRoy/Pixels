package com.pixels.sprite;

import java.awt.Color;
import java.awt.Point;

import com.pixels.master.Game;

public abstract class Sprite {
	protected Game game;
	protected int x, y;
	protected Color[][] shape;

	protected Sprite(Game game, int x, int y, Color[][] shape){
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
	
	public Color[][] shape(){
		return shape;
	}
	
	protected boolean checkForBottomContact(){
		
	}
	
	public void setColor(Color color){
		for(int y = 0; y < shape.length; y++){
			for(int x = 0; x < shape[0].length; x++){
				if(shape[y][x] != null){
					shape[y][x] = color;
				}
			}
		}
	}
	
	public void setLocation(Point point){
		// Validates the coordinates first
		if(point.x > -1 && point.x <= game.getMap()[0].length - shape[0].length){
			x = point.x;
		}
		if(point.y > -1 && point.y <= game.getMap().length - shape.length){
			y = point.y;
		}
	}
	
	public void setShape(Color[][] newShape){
		shape = newShape;
	}
	
	public abstract void revert();
		
}
