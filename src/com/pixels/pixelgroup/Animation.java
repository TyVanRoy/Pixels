package com.pixels.pixelgroup;

public class Animation implements Runnable {
	private PixelMap[] frames;
	private int current;
	private int frameRate;
	private boolean running = false;

	public Animation(int frameRate, PixelMap... frames) {
		this.frameRate = frameRate;
		this.frames = frames;
	}

	public Animation(PixelMap[] frames, int frameRate) {
		this.frameRate = frameRate;
		this.frames = frames;
	}

	public PixelMap[] getFrames() {
		return frames;
	}

	public synchronized void start() {
		current = -1;
		running = true;
		new Thread(this).start();
	}

	public synchronized void stop() {
		running = false;
	}

	public int getFrameRate() {
		return frameRate;
	}

	public synchronized PixelMap getFrame() {
		return frames[current];
	}

	public void run() {
		while (running) {
			if (current < frames.length - 1) {
				current++;
			} else {
				current = 0;
			}
			try {
				Thread.sleep(1000 / frameRate);
			} catch (Exception e) {
				e.printStackTrace();
				running = false;
			}
		}
	}

}
