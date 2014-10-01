package com.pixels.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import com.pixels.master.Game;
import com.pixels.master.PixelMap;
import com.pixels.util.Log;

public class ContentFrame extends Canvas implements Runnable {
	private static final long serialVersionUID = -8944654323818652165L;
	public static final int FRAME_RATE = 60;
	private Game game;
	private BufferStrategy buffer;
	private Color backgroundColor = Color.black;
	private Color outlineColor = Color.green;
	private int width, height;
	private boolean rendering = false;
	private PixelMap pixels;
	private boolean showingOutline = false;

	public ContentFrame(Game game) {
		this.game = game;

		// Setting sizes
		Dimension size = Game.nativeDimension();
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		width = size.width;
		height = size.height;
	}

	public void initGraphics() {
		// For safety reasons
		if (buffer == null) {
			createBufferStrategy(2);
			buffer = getBufferStrategy();
		}
		Log.log("Starting main graphics thread...");
		new Thread(this).start();
	}

	public synchronized void switchOutlineVisible() {
		showingOutline = !showingOutline;
	}

	// Attempting to minimize method-calls
	public void render() {
		Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Drawing background
		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);

		pixels = game.getVisiblePixels();

		// Rendering pixels
		for (int y = 0; y < pixels.height(); y++) {
			for (int x = 0; x < pixels.width(); x++) {
				int px = x * (width / pixels.width());
				int py = y * (height / pixels.height());

				if (pixels.get(x, y) == null) {

					// Rendering the outline of the pixels
					if (showingOutline) {
						g.setColor(outlineColor);
						g.drawRect(px, py, width / pixels.width(), height
								/ pixels.height());
					}
				} else {
					g.setColor(pixels.get(x, y));
					g.fillRect(px, py, width / pixels.width(),
							height / pixels.height());
				}
			}
		}

		g.dispose();
		buffer.show();
		Toolkit.getDefaultToolkit().sync();
	}

	// For resizing
	@Override
	public void resize(Dimension size) {
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		width = size.width;
		height = size.height;
	}

	public boolean isRendering() {
		return rendering;
	}

	public Color backgroundColor() {
		return backgroundColor;
	}

	// Rendering thread
	@Override
	public void run() {
		rendering = true;
		while (rendering) {
			render();
			try {
				Thread.sleep(1000 / FRAME_RATE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
