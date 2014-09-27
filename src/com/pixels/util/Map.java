package com.pixels.util;

import java.awt.Color;
import java.util.Random;

import com.pixels.master.Game;

public abstract class Map {
	public static final int DEFAULT_MAP = 0;

	public static Color[][] generateMap(int width, int height,
			final int MAP_TYPE) {
		Color[][] map = new Color[height][width];

		switch (MAP_TYPE) {
		case DEFAULT_MAP:
			generateDefaultMap(map, width, height);
			break;
		}

		return map;
	}

	private static void generateDefaultMap(Color[][] map, int width, int height) {
		Random r = new Random();
		double heightMagnitude = .1;
		int baseMax = (int) (height * heightMagnitude);
		int baseMin = (int) (height * (heightMagnitude * .2));

		for (int x = 0; x < width; x++) {

			if (x < baseMin) {
				for (int y = 0; y < height; y++) {
					map[y][x] = new Color(r.nextInt(256), r.nextInt(256),
							r.nextInt(256));
					for (int nX = 0; nX < r.nextInt(baseMax); nX++) {
						map[y][nX] = new Color(r.nextInt(256), r.nextInt(256),
								r.nextInt(256));
					}
				}
			}
			for (int y = 0; y < r.nextInt(baseMax); y++) {
				map[height - y - 1][x] = new Color(r.nextInt(256),
						r.nextInt(256), r.nextInt(256));
			}
			for (int y = 0; y < baseMin; y++) {
				map[height - y - 1][x] = new Color(r.nextInt(256),
						r.nextInt(256), r.nextInt(256));
			}
		}
	}

}
