package com.pixels.master;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.pixels.player.Player;
import com.pixels.player.StatusBar;
import com.pixels.sprite.BehavioralSprite;
import com.pixels.sprite.Sprite;
import com.pixels.sprite.SpriteContainer;
import com.pixels.ui.ContentFrame;
import com.pixels.ui.InputListener;
import com.pixels.util.Map;

public class Game implements Runnable {
	public static final String WINDOW_TITLE = "•Pixels•";
	public static final double ASPECT_RATIO = 2;
	public static final double DENSITY = ASPECT_RATIO * 300;
	public static final double LEFT_FOCUS_FACTOR = .2;
	public static final int SCREEN_MARGIN = 50;
	public static final int MAP_SIZE = 2000;
	// In milliseconds
	public static final int UPDATE_RATE = 120;
	public static final int SHIFT_SPEED = 50;
	// Speed to complete one full screen turnover in milliseconds
	public static final int SCROLL_SPEED = 2000;
	public static final Color CURSOR_COLOR = Color.white;
	public static final Color REGISTER_COLOR = Color.red;
	private JFrame window;
	private ContentFrame content;
	private int width, height;
	private boolean running = false;
	private Color[][] baseMap;
	private Color[][] map;
	private Color[][] visiblePixels;
	private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private InputListener input;
	private SpriteContainer registeredSpriteContainer;
	private int mapCursor = 0;
	private int focusTimer = 0;
	private StatusBar statusBar;

	// Private to avoid instantiation outside of this class
	public Game() {
		width = (int) nativeDimension().getWidth();
		height = (int) nativeDimension().getHeight();

		window = new JFrame(WINDOW_TITLE);
		content = new ContentFrame(this);

		// Prototypical Swing protocol...
		window.setBackground(content.getBackground());
		window.add(content);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);

		// Listening for window size-change
		content.addHierarchyBoundsListener(new HierarchyBoundsListener() {
			private boolean firstResize = true;

			@Override
			public void ancestorMoved(HierarchyEvent e) {
			}

			@Override
			public void ancestorResized(HierarchyEvent e) {
				if (e.getSource().equals(content)) {
					Dimension size;
					double width = window.getContentPane().getWidth();
					if (width < DENSITY) {
						width = DENSITY;
					} else {
						width = width - (width % DENSITY);
					}

					double height = width / ASPECT_RATIO;

					size = new Dimension((int) width, (int) height);
					content.resize(size);
					window.pack();

					setDimension((int) width, (int) height);

					if (firstResize) {
						window.setLocationRelativeTo(null);
						firstResize = false;
					}
				}
			}
		});

		// Input listener
		input = new InputListener(this);
		content.addKeyListener(input);
		content.addMouseListener(input);
		content.addMouseMotionListener(input);
	}

	// When the mouse is pressed, the point is send in through this method
	public synchronized void registerCursor(Point point) {
		// Registers the pointed clicked by the user
		if (point != null) {
			// Going through the entire map
			for (int y = 0; y < map.length; y++) {
				for (int x = 0; x < map[0].length; x++) {

					// Checking sprites to see if any starting points lie on the
					// current coordinate
					for (Sprite sprite : sprites) {
						if (sprite.getX() == x && sprite.getY() == y) {

							// Now checking the sprite's shape for matches
							Color[][] shape = sprite.shape();
							for (int shapeY = 0; shapeY < sprite.shape().length; shapeY++) {
								for (int shapeX = 0; shapeX < sprite.shape()[0].length; shapeX++) {
									if (shape[shapeY][shapeX] != null) {
										if (x + shapeX == point.x
												&& y + shapeY == point.y) {
											registerSprite(sprite, point);
										}
									}
								}
							}
						}
					}
				}
			}
		} else {
			// Null point is registered when the mouse is released
			unregisterSprite();
		}
	}

	// Is a sprite is found when the cursor is registered, it is registered here
	public void registerSprite(Sprite sprite, Point registerPoint) {
		// Changes it's color and puts it in a sprite container
		sprite.setColor(REGISTER_COLOR);

		// The differences between the sprite's coordinates and the cursor's
		int xDif = registerPoint.x - sprite.getX();
		int yDif = registerPoint.y - sprite.getY();
		registeredSpriteContainer = new SpriteContainer(sprite, xDif, yDif);
	}

	// Unregisters the registered sprite if there is one
	public void unregisterSprite() {
		if (registeredSpriteContainer != null) {

			// Reverts the sprite back to it's original shape and color
			registeredSpriteContainer.getSprite().revert();
			registeredSpriteContainer = null;
		}
	}

	// When the mouse is dragged
	public synchronized void registerDrag(Point point) {
		// If there is a sprite currently registered, it moves it to the new
		// location using the xDif & yDif
		if (registeredSpriteContainer != null) {
			int x = point.x - registeredSpriteContainer.getXDif();
			int y = point.y - registeredSpriteContainer.getYDif();

			registeredSpriteContainer.getSprite().setLocation(new Point(x, y));
		}
	}

	private void setDimension(int width, int height) {
		this.width = width;
		this.height = height;
	}

	// Initiation method
	public void launch() {
		statusBar = new StatusBar(false);

		createSprites();
		calculateMap();
		calculateVisiblePixels();

		// Starts the main game thread
		new Thread(this).start();
		window.setVisible(true);

		// Initializes content graphics
		content.initGraphics();
	}

	private void createSprites() {
		sprites.add(new Player(this));
	}

	public ContentFrame getContent() {
		return content;
	}

	// Calculates the map
	private void calculateMap() {
		// Initialization of the basemap
		if (baseMap == null) {
			baseMap = Map.generateMap(MAP_SIZE, (int) (DENSITY / ASPECT_RATIO),
					Map.DEFAULT_MAP);
		}

		// Creating a new map from scratch
		map = new Color[baseMap.length][baseMap[0].length];

		// Copying the basemap onto the new map
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				map[y][x] = baseMap[y][x];
			}
		}

		// Iterating through the map
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {

				// Checking to see if the starting points of any of the sprites
				// lies on this point
				for (Sprite sprite : sprites) {
					if (sprite.getX() == x && sprite.getY() == y) {

						// Drawing out the sprite's shape
						Color[][] shape = sprite.shape();
						for (int shapeY = 0; shapeY < shape.length; shapeY++) {
							for (int shapeX = 0; shapeX < shape[0].length; shapeX++) {
								// Adjusted coordinates
								int aX = x + shapeX;
								int aY = y + shapeY;

								// Last minute check to make sure the pixel is
								// in bounds
								if ((aX > -1 && aX < MAP_SIZE)
										&& (aY > -1 && aY < (int) (DENSITY / ASPECT_RATIO))) {
									if (shape[shapeY][shapeX] != null) {
										map[y + shapeY][x + shapeX] = shape[shapeY][shapeX];
									}
								}
							}
						}
					}
				}
			}
		}
	}

	// Calculates the pixels which will appear on the screen
	private synchronized void calculateVisiblePixels() {
		// Reseting the array to eliminate overlay
		visiblePixels = new Color[(int) (DENSITY / ASPECT_RATIO)][(int) DENSITY];

		// Translating the map data into the visible data array using the map
		// cursor
		for (int y = 0; y < visiblePixels.length; y++) {
			for (int x = 0; x < visiblePixels[0].length; x++) {
				visiblePixels[y][x] = map[y][x + mapCursor];
			}
		}
		
		Color[][] statusPixels = statusBar.getPixels();
		for (int y = 0; y < statusPixels.length; y++) {
			for (int x = 0; x < statusPixels[0].length; x++) {
				visiblePixels[y][x] = statusPixels[y][x];
			}
		}
		
	}

	// Calculates the amount of iterations needed to center the map on the
	// player.
	public synchronized void center() {
		int startPosition = sprites.get(0).getX() - mapCursor;
		int endPosition = visiblePixels[0].length / 2
				- sprites.get(0).shape()[0].length / 2;
		focusTimer = endPosition - startPosition;

		if (mapCursor - focusTimer < 0) {
			focusTimer = mapCursor;
		}

		focusTimer /= 1000 / SHIFT_SPEED;
	}

	public synchronized void shiftLeft() {
		int startPosition = sprites.get(0).getX() - mapCursor;
		int endPosition = (int) (visiblePixels[0].length * LEFT_FOCUS_FACTOR)
				- sprites.get(0).shape()[0].length / 2;
		focusTimer = endPosition - startPosition;

		if (mapCursor - focusTimer < 0) {
			focusTimer = mapCursor;
		}

		focusTimer /= 1000 / SHIFT_SPEED;
	}

	public synchronized Color[][] getVisiblePixels() {
		return visiblePixels;
	}

	public synchronized Color[][] getMap() {
		return map;
	}

	public synchronized int getMapCursor() {
		return mapCursor;
	}

	// There will never be individual method calls for the width and height.
	public Dimension getDimensions() {
		return new Dimension(width, height);
	}

	// Returns the default dimensions. (Screen-dependent)
	public static Dimension nativeDimension() {
		Dimension maximumNative = Toolkit.getDefaultToolkit().getScreenSize();
		double maxWidth = maximumNative.getWidth() - SCREEN_MARGIN;
		double maxHeight = maximumNative.getHeight() - SCREEN_MARGIN;

		int finalWidth = 0;
		int finalHeight = 0;

		if (maxWidth / maxHeight > ASPECT_RATIO) {
			finalWidth = (int) (maxHeight * ASPECT_RATIO);
			finalHeight = (int) maxHeight;
		} else if (maxWidth / maxHeight < ASPECT_RATIO) {
			finalWidth = (int) maxWidth;
			finalHeight = (int) (maxWidth * ASPECT_RATIO);
		} else {
			finalWidth = (int) maxWidth;
			finalHeight = (int) maxHeight;
		}

		return new Dimension(finalWidth, finalHeight);
	}

	// For map scrolling
	public void calculateScrolling(Player player, boolean[] instructions) {

		// Shifting the focus of the map to the desired position
		if (focusTimer != 0) {
			if (focusTimer > 0) {
				mapCursor -= 1000 / SHIFT_SPEED;
				focusTimer--;
			} else if (focusTimer < 0) {
				mapCursor += 1000 / SHIFT_SPEED;
				focusTimer++;
			}
		}

		if (instructions[4]) {
			if (instructions[2]) {
				if (mapCursor > 0)
					mapCursor -= Player.MOVEMENT_DISTANCE;
			}
			if (instructions[3]) {
				if (mapCursor < map[0].length)
					mapCursor += Player.MOVEMENT_DISTANCE;
			}
		}
	}
	
	public void induceSpriteBehavior(){
		for(Sprite sprite : sprites){
			if(sprite instanceof BehavioralSprite){
				((BehavioralSprite)sprite).behave();
			}
		}
	}

	// Main game thread runnable
	@Override
	public void run() {
		running = true;
		while (running) {
			// Making sure the content is able to take input
			if (!content.hasFocus())
				content.requestFocus();

			// Getting the input status
			boolean[] status = input.getStatus();
			// Sending the input to the player. Sprites at 0 will always be the
			// player.
			Player player = (Player) sprites.get(0);
			player.dispatchInstructions(status);
			
			induceSpriteBehavior();

			calculateScrolling(player, status);

			// Calculating the positions and graphics
			calculateMap();
			calculateVisiblePixels();
			try {
				Thread.sleep(1000 / UPDATE_RATE);
			} catch (Exception e) {
				running = false;
				e.printStackTrace();
			}
		}
	}
}
