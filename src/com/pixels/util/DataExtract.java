package com.pixels.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.pixels.pixelgroup.PixelMap;

public abstract class DataExtract {
	private static final String S = System.getProperty("os.name").equals(
			"Mac OS X") ? "//" : "\\";
	private static final String R = "res";
	public static final String UI = "ui";
	public static final String STATUS = "status";
	public static final String SPRITES = "sprites";
	public static final String PLAYER = "player";
	public static final String MAPS = "maps";

	/**
	 * I need to find a better way to do this...
	 */
	public synchronized static PixelMap getImagePixels(String imageName,
			final String DIRECTORY) {
		PixelMap pixels;
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
			case MAPS:
				file = new File(String.format("%s%s%s%s%s", R, S, MAPS, S,
						imageName));
				break;
			default:
				file = null;
				break;
			}
			
			BufferedImage image = ImageIO.read(file);
			
			Log.log("Successful read at: " + file);
			
			pixels = new PixelMap(image.getWidth(), image.getHeight());
			for (int y = 0; y < pixels.height(); y++) {
				for (int x = 0; x < pixels.width(); x++) {
					
					int value = image.getRGB(x, y);

					if (value != 0){
						pixels.put(x, y, new Color(value));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			pixels = null;
		}

		return pixels;
	}
}
