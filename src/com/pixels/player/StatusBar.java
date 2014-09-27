package com.pixels.player;

import java.awt.Color;

public class StatusBar {
	public static final int ITEMS = 1;
	public static final int ITEM_SIZE = 10;
	public static final int BORDER_SIZE = 2;
	public static final int WIDTH = ITEMS * ITEM_SIZE + BORDER_SIZE * 2;
	public static final int HEIGHT = ITEMS * ITEM_SIZE + BORDER_SIZE * 2;
	public static final Color BORDER_COLOR = Color.gray;
	private boolean locked;

	public StatusBar(boolean locked) {
		this.locked = locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Color[][] getPixels() {
		Color[][] pixels = new Color[HEIGHT][WIDTH];

		for (int y = 0; y < pixels.length; y++) {
			for (int x = 0; x < pixels[0].length; x++) {
				pixels[y][x] = BORDER_COLOR;
			}
		}

		return pixels;
	}
}
