package com.pixels.ui;

import java.awt.Color;

import com.pixels.pixelgroup.PixelMap;
import com.pixels.util.DataExtract;

public class StatusBar {
	public static final int ITEMS = 1;
	public static final int ITEM_SIZE = 15;
	public static final int BORDER_SIZE = 2;
	public static final int WIDTH = ITEMS * ITEM_SIZE + BORDER_SIZE * 2;
	public static final int HEIGHT = ITEMS * ITEM_SIZE + BORDER_SIZE * 2;
	public static final Color BORDER_COLOR = Color.gray;
	private PixelMap lockedMap, unlockedMap;
	private boolean locked;

	public StatusBar(boolean locked) {
		this.locked = locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public PixelMap getPixels() {
		if (lockedMap == null) {
			lockedMap = DataExtract.getImagePixels("locked.png",
					DataExtract.STATUS);
		}
		if (unlockedMap == null) {
			unlockedMap = DataExtract.getImagePixels("unlocked.png",
					DataExtract.STATUS);
		}

		PixelMap pixels = new PixelMap(WIDTH, HEIGHT);

		pixels.fillSolid(BORDER_COLOR);

		PixelMap[] status = new PixelMap[ITEMS];

		status[0] = locked ? lockedMap : unlockedMap;

		for (int i = 0; i < status.length; i++) {
			status[i].translate(pixels, 0, 0, BORDER_SIZE, BORDER_SIZE);
		}

		return pixels;
	}
}
