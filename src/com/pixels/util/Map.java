package com.pixels.util;

import java.awt.Color;
import java.util.Random;

import com.pixels.master.PixelMap;

public abstract class Map {
	public static final int DEFAULT_MAP = 0;

	public static PixelMap generateMap(int width, int height, final int MAP_TYPE) {
		PixelMap map = new PixelMap(width, height);

		switch (MAP_TYPE) {
		case DEFAULT_MAP:
			generateDefaultMap(map, width, height);
			break;
		}

		return map;
	}

	private static void generateDefaultMap(PixelMap map, int width, int height) {
		Random r = new Random();
		double heightMagnitude = .2;
		int baseMax = (int) (height * heightMagnitude);
		int baseMin = (int) (height * (heightMagnitude * .1));

		for (int x = 0; x < width; x++) {

			if (x < baseMin) {
				for (int y = 0; y < height; y++) {
					map.put(x,
							y,
							new Color(r.nextInt(256), r.nextInt(256), r
									.nextInt(256)));
					for (int nX = 0; nX < r.nextInt(baseMax); nX++) {
						map.put(nX, y, new Color(r.nextInt(256),
								r.nextInt(256), r.nextInt(256)));
					}
				}
			}
			for (int y = 0; y < r.nextInt(baseMax); y++) {
				map.put(x,
						height - y - 1,
						new Color(r.nextInt(256), r.nextInt(256), r
								.nextInt(256)));
			}
			for (int y = 0; y < baseMin; y++) {
				map.put(x,
						height - y - 1,
						new Color(r.nextInt(256), r.nextInt(256), r
								.nextInt(256)));
			}
		}
	}
}
