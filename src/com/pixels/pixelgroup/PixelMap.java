package com.pixels.pixelgroup;

import java.awt.Color;
import java.awt.Point;

public class PixelMap {
	private Color[][] pixels;
	private int width, height;

	public PixelMap(int width, int height) {
		pixels = new Color[height][width];
		this.width = width;
		this.height = height;
	}
	
	// Translates this pixelmap onto the output.
	public void translate(PixelMap output, int inXMargin, int inYMargin, int outXMargin, int outYMargin){
		int xIterations;
		int yIterations;
		
		if(output.width() - outXMargin > width - inXMargin){
			xIterations = width - inXMargin;
		}else{
			xIterations = output.width() - outXMargin;
		}
		
		if(output.height() - outYMargin > height - inYMargin){
			yIterations = height - inYMargin;
		}else{
			yIterations = output.height() - outYMargin;
		}
		
		for(int y = 0; y < yIterations; y++){
			for(int x = 0; x < xIterations; x++){
				output.put(x + outXMargin, y + outYMargin, pixels[y + inYMargin][x + inXMargin]);
			}
		}
	}
	
	public synchronized PixelMap getHorizontalFlip(){
		PixelMap output = new PixelMap(width, height);
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				Color pixel = get(x, y);
				if(pixel != null){
					output.put(width - x - 1, y, pixel);
				}
			}
		}
		
		return output;
	}

	public void fill(Color color) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (pixels[y][x] != null)
					pixels[y][x] = color;
			}
		}
	}

	public void fillSolid(Color color) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[y][x] = color;
			}
		}
	}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

	public Color get(int x, int y) {
		return pixels[y][x];
	}

	public void put(Point point, Color color) {
		pixels[point.y][point.x] = color;
	}

	public void put(int x, int y, Color color) {
		pixels[y][x] = color;
	}

	/*
	 * Statics
	 */

	// Generates a sphere. Diameter should be odd
	public static PixelMap generateBall(Color color, int diameter) {
		PixelMap shape = new PixelMap(diameter, diameter);

		int c = diameter / 2;
		int r = (diameter / 2) + 1;

		for (int y = 0; y < diameter; y++) {
			for (int x = 0; x < diameter; x++) {
				int a = y - c;
				int b = x - c;

				if (a * a + b * b <= r * r) {
					shape.put(x, y, color);
				}
			}
		}

		return shape;
	}

}
