package com.pixels.master;

import java.awt.Color;

import com.pixels.util.DataExtract;

public class PixelMap {
	private Color[][] pixels;
	
	public PixelMap(int width, int height){
		
	}
	
	// Diameter should be odd
	public static Color[][] generateBall(Color color, int diameter) {
		Color[][] shape = new Color[diameter][diameter];

		int c = diameter / 2;
		int r = (diameter / 2) + 1;
		
		for (int y = 0; y < diameter; y++) {
			for (int x = 0; x < diameter; x++) {
				int a = y - c;
				int b = x - c;
				
				if(a * a + b * b <= r*r){
					shape[y][x] = color;
				}
			}
		}
		
		return shape;
	}
	
	public static Color[][] getPlayerShape(){
		return DataExtract.getImagePixels("player.png", DataExtract.PLAYER);
	}
	
}
