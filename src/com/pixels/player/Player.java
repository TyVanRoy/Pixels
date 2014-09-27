package com.pixels.player;

import java.awt.Color;

import com.pixels.master.Game;
import com.pixels.sprite.GravityBoundSprite;
import com.pixels.sprite.Sprite;
import com.pixels.util.Shape;

public class Player extends GravityBoundSprite {
	public static final Color COLOR = Color.green;
	public static final int DIAMETER = 11;
	public static final int MOVEMENT_DISTANCE = 1;
	public static final int WEIGHT = 1;

	public Player(Game game) {
		super(game, (int) (Game.LEFT_FOCUS_FACTOR * Game.DENSITY + game
				.getMapCursor()), 50, Shape.generateBall(COLOR, DIAMETER),
				WEIGHT);
	}

	public void dispatchInstructions(boolean[] instructions) {
		if (instructions[0]) {
			if (y > 0)
				y -= MOVEMENT_DISTANCE;
		}
		if (instructions[1]) {
			if (y < game.getMap().length - DIAMETER)
				y += MOVEMENT_DISTANCE;
		}
		if (instructions[2]) {
			if (x > 0)
				x -= MOVEMENT_DISTANCE;
		}
		if (instructions[3]) {
			if (x < game.getMap()[0].length - DIAMETER)
				x += MOVEMENT_DISTANCE;
		}
	}
	
	@Override
	public void behave(){
		super.behave();
	}

	@Override
	public void revert() {
		setShape(Shape.generateBall(COLOR, DIAMETER));
	}

}
