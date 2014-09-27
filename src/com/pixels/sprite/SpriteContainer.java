package com.pixels.sprite;

// To contain a registered sprite
public class SpriteContainer {
	private Sprite sprite;
	// The differences in the sprites coordinates the the cursor coordinates
	private int xDif, yDif;

	public SpriteContainer(Sprite sprite, int xDif, int yDif){
		this.sprite = sprite;
		this.xDif = xDif;
		this.yDif = yDif;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public int getXDif(){
		return xDif;
	}
	
	public int getYDif(){
		return yDif;
	}
	
}
