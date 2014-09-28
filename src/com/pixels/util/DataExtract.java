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

	public static Color[][] getImagePixels(String imageName, final String DIRECTORY) {
		Color[][] pixels = null;
		try {
			switch (DIRECTORY) {
			case STATUS:
				File file = new File(String.format("%s%s%s%s%s%s%s", R, S, UI, S,
						STATUS, S, imageName));
				BufferedImage image = ImageIO.read(file);
				pixels = new Color[image.getHeight()][image.getWidth()];
				for(int y = 0; y < pixels.length; y++){
					for(int x = 0; x < pixels[0].length; x++){
						pixels[y][x] = new Color(image.getRGB(x, y));
					}
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pixels;
	}
}
