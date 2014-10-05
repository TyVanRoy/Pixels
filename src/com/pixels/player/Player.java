package com.pixels.player;

import com.pixels.master.Game;
import com.pixels.pixelgroup.Animation;
import com.pixels.pixelgroup.PixelMap;
import com.pixels.sprite.GravityBoundSprite;
import com.pixels.util.DataExtract;

public class Player extends GravityBoundSprite {
	public static final int MOVEMENT_DISTANCE = 2;
	public static final double VERTICAL_CAPACITY = .2;
	/**
	 * The higher the jump power, the faster the jump height is reached.
	 */
	public static final int JUMP_POWER = 5;
	/**
	 * Jump height should be divisible by the jump power
	 */
	public static final int JUMP_HEIGHT = 50;
	public static final int WEIGHT = 4;
	public static final PixelMap STILL_RIGHT = Player.getPlayerShape();
	public static final PixelMap STILL_LEFT = STILL_RIGHT.getHorizontalFlip();
	private int jumpTimer = 0;
	private Animation movementRight;
	private Animation movementLeft;
	private boolean leftOffRight = true;

	public Player(Game game) {
		super(game, (int) (Game.LEFT_FOCUS_FACTOR * Game.DENSITY + game
				.getMapCursor()), 50, MOVEMENT_DISTANCE, VERTICAL_CAPACITY,
				STILL_RIGHT, WEIGHT);
		movementRight = Player.getPlayerMovementAnimation(false);
		movementLeft = Player.getPlayerMovementAnimation(true);
	}

	public void dispatchInstructions(boolean leftDown, boolean rightDown) {
		int width = shape.width();
		// int height = shape.height();

		if (leftDown) {
			if (animation != movementLeft) {
				swapAnimation(movementLeft);
			}
			if (x > 0) {
				x -= movementDistance;
			}
			leftOffRight = false;
		} else if (rightDown) {
			if (animation != movementRight) {
				swapAnimation(movementRight);
			}
			if (x < game.getMap().width() - width) {
				x += movementDistance;
			}
			leftOffRight = true;
		} else {
			if (animation != null || shape != STILL_RIGHT || shape != STILL_LEFT) {
				swapAnimation(null);
				this.revert();
			}
		}

	}

	public void jump() {
		if (distanceFromGround() == 1) {
			jumpTimer = JUMP_HEIGHT / JUMP_POWER;
		}
	}

	@Override
	public void behave() {
		if (animation != null) {
			setShape(animation.getFrame());
		}
		if (jumpTimer == 0) {
			super.behave();
		} else {
			y -= JUMP_POWER;
			jumpTimer--;
		}
	}

	@Override
	public void revert() {
		setShape(leftOffRight ? STILL_RIGHT : STILL_LEFT);
	}

	// Returns the default player shape.
	public static PixelMap getPlayerShape() {
		return DataExtract.getImagePixels("still.png", DataExtract.PLAYER);
	}

	public static Animation getPlayerMovementAnimation(boolean orientLeft) {
		int frameCount = 4;
		PixelMap[] frames = new PixelMap[frameCount];
		for (int i = 0; i < frameCount - 1; i++) {
			frames[i] = DataExtract.getImagePixels("move" + i + ".png",
					DataExtract.PLAYER);
		}

		if (orientLeft) {
			for (int i = 0; i < frameCount - 1; i++) {
				frames[i] = frames[i].getHorizontalFlip();
			}
		}
		
		frames[3] = frames[1];

		return new Animation(frames, MOVEMENT_DISTANCE * 3);
	}

}
