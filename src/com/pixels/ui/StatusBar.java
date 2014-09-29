package com.pixels.ui;

import java.awt.Color;

import com.pixels.master.PixelMap;
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

	public PixelMap getPixels() {
		PixelMap pixels = new PixelMap(WIDTH, HEIGHT);

		pixels.fillSolid(BORDER_COLOR);

		PixelMap[] status = new PixelMap[ITEMS];

		status[0] = locked ? DataExtract.getImagePixels("locked.png",
				DataExtract.STATUS) : DataExtract.getImagePixels(
				"unlocked.png", DataExtract.STATUS);

		for (int i = 0; i < status.length; i++) {
			status[i].translate(pixels, 0, 0, BORDER_SIZE, BORDER_SIZE);
		}

		return pixels;
	}
}
