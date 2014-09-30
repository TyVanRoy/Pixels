package com.pixels.player;

import java.awt.Color;

import com.pixels.master.Game;
import com.pixels.master.PixelMap;
import com.pixels.sprite.GravityBoundSprite;

public class Player extends GravityBoundSprite {
	public static final Color COLOR = Color.green;
	public static final int MOVEMENT_DISTANCE = 1;
	public static final double VERTICAL_CAPACITY = .2;
	// The higher the jump power, the faster the jump height is reached.
	public static final int JUMP_POWER = 10;
	// Jump height should be divisible by the jump power
	public static final int JUMP_HEIGHT = 60;
	public static final int WEIGHT = 5;
	private int jumpTimer = 0;

	public Player(Game game) {
		super(game, (int) (Game.LEFT_FOCUS_FACTOR * Game.DENSITY + game
				.getMapCursor()), 50, MOVEMENT_DISTANCE, VERTICAL_CAPACITY, PixelMap.generateBall(Color.blue, 21), WEIGHT);
	}

	public void dispatchInstructions(boolean[] instructions) {
		int width = shape.width();
		int height = shape.height();
		
		if (instructions[0]) {
			if (y < game.getMap().height() - height)
				y += movementDistance;
		}
		if (instructions[1]) {
			if (x > 0)
				x -= MOVEMENT_DISTANCE;
		}
		if (instructions[2]) {
			if (x < game.getMap().width() - width)
				x += MOVEMENT_DISTANCE;
		}
	}
	 
	public void jump(){
		if(isGrounded()){
			jumpTimer = JUMP_HEIGHT / JUMP_POWER;
		}
	}

	@Override
	public void behave() {
		if(jumpTimer == 0){
			super.behave();
		}else{
			y -= JUMP_POWER;
			jumpTimer--;
		}
	}

	@Override
	public void revert() {
		setShape(PixelMap.getPlayerShape());
	}

}
