package com.pixels.ui;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.pixels.master.Game;

public class InputListener implements KeyListener, MouseListener, MouseMotionListener {
	private Game game;
	private ContentFrame content;
	private boolean downDown = false;
	private boolean leftDown = false;
	private boolean rightDown = false;
	private boolean locked = false;

	public InputListener(Game game) {
		this.game = game;
		this.content = this.game.getContent();
	}

	// Returns the status of the input
	public boolean[] getStatus() {
		return new boolean[] { downDown, leftDown, rightDown, locked };
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == content) {
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_W:
				game.getPlayer().jump();
				break;
			case KeyEvent.VK_S:
				downDown = true;
				break;
			case KeyEvent.VK_A:
				leftDown = true;
				break;
			case KeyEvent.VK_D:
				rightDown = true;
				break;
			case KeyEvent.VK_Q:
				game.shiftLeft();
				break;
			case KeyEvent.VK_E:
				game.center();
				break;
			case KeyEvent.VK_R:
				content.switchOutlineVisible();
				break;
			case KeyEvent.VK_F:
				locked = !locked;
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() == content) {
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_S:
				downDown = false;
				break;
			case KeyEvent.VK_A:
				leftDown = false;
				break;
			case KeyEvent.VK_D:
				rightDown = false;
				break;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point point = getScaledPoint(game, e);
		game.registerCursor(point);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		game.registerCursor(null);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point point = getScaledPoint(game, e);
		game.registerDrag(point);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	// Scales the point in order to find the map coordinate
	public static Point getScaledPoint(Game game, MouseEvent e){
		int aWidth = (int) game.getDimensions().getWidth();
		int aHeight = (int) game.getDimensions().getHeight();
		int pWidth = game.getVisiblePixels().width();
		int pHeight = game.getVisiblePixels().height();
		int x = (e.getX() / (aWidth / pWidth)) + game.getMapCursor();
		int y = e.getY() / (aHeight / pHeight);
		
		return new Point(x, y);
	}
}
