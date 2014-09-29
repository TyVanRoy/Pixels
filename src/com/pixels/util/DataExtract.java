package com.pixels.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public abstract class DataExtract {
	private static final String S = System.getProperty("os.name").equals(
			"Mac OS X") ? "//" : "\\";
	private static final String R = "res";
	public static final String UI = "ui";
	public static final String STATUS = "status";
	public static final String SPRITES = "sprites";
	public static final String PLAYER = "player";

	// I need to find a better way to do this...
	public static Color[][] getImagePixels(String imageName,
			final String DIRECTORY) {
		Color[][] pixels = null;
		try {
			File file;
			switch (DIRECTORY) {
			case STATUS:
				file = new File(String.format("%s%s%s%s%s%s%s", R, S, UI, S,
						STATUS, S, imageName));
				break;
			case PLAYER:
				file = new File(String.format("%s%s%s%s%s%s%s", R, S, SPRITES,
						S, PLAYER, S, imageName));
				break;
			default:
				file = null;
				break;
			}

			BufferedImage image = ImageIO.read(file);

			pixels = new Color[image.getHeight()][image.getWidth()];
			for (int y = 0; y < pixels.length; y++) {
				for (int x = 0; x < pixels[0].length; x++) {
					int value = image.getRGB(x, y);

					if (value != 0)
						pixels[y][x] = new Color(value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pixels;
	}
}
