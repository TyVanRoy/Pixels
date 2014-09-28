package com.pixels.ui;

import java.awt.Color;

import com.pixels.util.DataExtract;

public class StatusBar {
	public static final int ITEMS = 1;
	public static final int ITEM_SIZE = 15;
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

		Color[][][] status = new Color[ITEMS][ITEM_SIZE][ITEM_SIZE];

		status[0] = locked ? DataExtract.getImagePixels("locked.png",
				DataExtract.STATUS) : DataExtract.getImagePixels(
				"unlocked.png", DataExtract.STATUS);

		for (int i = 0; i < status.length; i++) {
			for (int y = 0; y < status[0].length; y++) {
				for (int x = 0; x < status[0][0].length; x++) {
					pixels[y + BORDER_SIZE][x + BORDER_SIZE] = status[i][y][x];
				}
			}
		}

		return pixels;
	}
}
