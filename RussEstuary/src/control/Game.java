package control;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

import javax.swing.Timer;

import framework.KeyInput;
import framework.MouseInput;
import framework.ObjectId;
import gfx.Images;
import object.Boat;
import object.Inventory;
import object.Critter;
import object.Habitat;
import object.RofFactory;
import object.Runoff;
import object.Tree;
import object.WasteBin;
import window.Handler;
import window.Window;

public class Game extends Canvas implements Runnable {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -6771508490304664935L;

	static Toolkit tk = Toolkit.getDefaultToolkit();
	static Dimension dm = new Dimension(tk.getScreenSize());
	private boolean running = false;
	private Thread thread;
	public int count = 0;
	boolean pause = false;
	Timer clock;
	int nRof = 0;

	public int trees = 0;
	// Object
	Handler handler;
	RofFactory factory;
	WasteBin trashBin;
	WasteBin recyclebin;
	Inventory inventory;
	double[] dmBoundaries;
	Critter critter;
	Images images = new Images();

	private void init() {
		// Default Objects
		handler = new Handler(this);
		// 0 1 2 3 4
		// Width, Height, Water Start Width, Water Bottom Height, Water Surface
		// Height
		handler.creatSurface(dm);
		dmBoundaries = handler.spawnLocations(dm);
		// factory = new RofFactory(0, dm.getHeight() * 3 / 5 - 32,
		// ObjectId.RofFactory, handler);
		trashBin = new WasteBin(dm.getWidth() - 50, dm.getHeight() - 70, ObjectId.wasteBin, handler, 0);
		recyclebin = new WasteBin(dm.getWidth() - 100, dm.getHeight() - 70, ObjectId.wasteBin, handler, 1);
		inventory = new Inventory(10, 10, ObjectId.inventory, handler);
		critter = new Critter(600, dm.getHeight() * 3 / 5 - 32, ObjectId.critter, handler, true, true, dmBoundaries[0],
				dmBoundaries[1], inventory, this, images);

		// Game 1 Objects
		handler.addObject(new Boat(dmBoundaries[2], dmBoundaries[4] - 40, ObjectId.boat, handler, trashBin, recyclebin,
				inventory, dmBoundaries[2], dmBoundaries[0]));
		handler.addObject(trashBin);
		handler.addObject(recyclebin);
		handler.addObject(inventory);
		handler.addObject(new Habitat(dmBoundaries[2], dmBoundaries[1] - 96 - 64, ObjectId.habitat, handler, dm));

		// Game 2 Objects
		// handler.addObject(factory);

		// Critter
		handler.addObject(critter);
		inventory.setCritter(critter);
		this.addKeyListener(new KeyInput(handler, this));
		this.addMouseListener(new MouseInput(handler, this));
	}

	public synchronized void start() {
		if (running)
			return;

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {

		init();
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
				// if (nRof < 4) {
				// factory.prodRof(handler, dm);
				// nRof += 1;
				// }
			}

		}

	}

	private void tick() {
		handler.tick();

	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		// Draw here

		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());

		handler.render(g);
		//
		g.dispose();
		bs.show();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getnRof() {
		return nRof;
	}

	public void setnRof(int nRof) {
		this.nRof = nRof;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(int duration) {
		Timer clock = new Timer(duration, listener);
		pause = true;
		critter.setVelX(0);
		critter.setVelY(0);
		clock.start();
	}

	ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			pause = false;
		}
	};

	public static void main(String args[]) {
		Game game = new Game();
		new Window(dm, "Estuary", game);
	}

}
