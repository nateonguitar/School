package rain;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import rain.graphics.Screen;
import rain.input.Keyboard;

	// implements Runnable lets us use "this" in thread
	// Canvas represents a blank rectangular portion of the screen
		// can manipulate it
	// game is now a subclass of Canvas
public class Game extends Canvas implements Runnable  { // because Game is "Runnable" it automatically starts
	private static final long serialVersionUID = 1L;   // the thread, so run() is called, we don't have to call
													   // it to run it.
	public static int width = 300;
	public static int height = width / 16 * 9;
	public static int scale = 3;
	public static String title = "Rain";
	
	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private boolean running = false;
	
	private Screen screen;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
				/* converting "image" into an array of integers.
				 * signals which pixel gets which color
				 * created an image
				 * accessed the image */
		// constructor
	public Game() {
		Dimension size = new Dimension(width*scale, height*scale);
		setPreferredSize(size); // setPreferredSize is part of "Component" and Canvas extends Component
		
		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		setFocusable(true);
		
		addKeyListener(key);
	}
	
		// synchronized ensures there's no overlaps of instructions for this object
	public synchronized void start() {
		running = true;
		thread = new Thread( this, "Display");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
		thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println();
				frame.setTitle(title + " | " + updates + "ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	int x = 0, y = 0;
	
	public void update() {
		key.update();
		if (key.up) y--;
		if (key.down) y++;
		if (key.left) x--;
		if (key.right) x++;
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3); // triple buffering
			return;
		}
		
		screen.clear();
		screen.render(x, y);
		
		for (int i=0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics(); // creates a link between Graphics and the buffer strategy
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight()); // 0, 0 is top left corner
		g.drawImage(image,  0, 0, getWidth(), getHeight(), null);
		g.dispose(); // dispose() disposes of the current graphics.
			// renter() is in a loop, at the end of each frame need to be removed
			// so new graphics can be displayed (60 times per seconds or whatever we set the refresh rate)
			// buffer that needs to be displayed is computed, but not displayed
			// "buffer-swapping"
		bs.show(); // makes the next buffer visible
		}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(Game.title);
		game.frame.add(game); 					// add() adds a component with something, fills the window
		game.frame.pack(); 						// pack sets the size of the frame to the size of the component
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // kills the app when you press the X in the 
												// corner of the window, not just closing the window.
		game.frame.setLocationRelativeTo(null); // moves the frame from the top corner to the center of screen
		game.frame.setVisible(true);
		
		game.start();
	} // end main
} // end class Game
