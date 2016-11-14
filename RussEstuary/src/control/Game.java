package control;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import framework.KeyInput;
import framework.ObjectId;
import object.Boat;
import object.CompostCounter;
import object.Critter;
import object.Habitat;
import object.RecycleBin;
import object.RofFactory;
import object.Runoff;
import object.Trash;
import object.TrashBin;
import object.Tree;
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

	int nRof = 0;
	// Object
	Handler handler;
	RofFactory factory;
	TrashBin trashBin=new TrashBin(550, 550, ObjectId.trashBin,handler);
	RecycleBin recyclebin=new RecycleBin(500, 550, ObjectId.recycleBin,handler);
	CompostCounter counter = new CompostCounter(10,10, ObjectId.compostCounter);
	double[] tempLoc;

	private void init() {

		//Default Objects
		handler = new Handler();
		factory = new RofFactory(0, dm.getHeight() * 3 / 5 - 32, ObjectId.RofFactory);
		handler.creatSurface(dm);
		handler.addObject(factory);
		
		//Game 1 Objects
		tempLoc = handler.boatLoc(dm);
		handler.addObject(new Boat(tempLoc[0], tempLoc[1], ObjectId.boat,handler,trashBin, recyclebin, counter,tempLoc[0],dm.getWidth()));		
		handler.addObject(trashBin);
		handler.addObject(recyclebin);
		handler.addObject(counter);
		tempLoc = handler.habitatLoc(dm);
		handler.addObject(new Habitat(tempLoc[0], tempLoc[1], ObjectId.habitat,handler, dm));
		
		//Critter
		handler.addObject(new Critter(600, dm.getHeight() * 3 / 5 - 32, ObjectId.critter, true, true, handler));
		this.addKeyListener(new KeyInput(handler));
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
				if (nRof < 4) {
					factory.prodRof(handler, dm);
					nRof += 1;
				}
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

	public static void main(String args[]) {
		Game game = new Game();
		new Window(dm, "Estuary", game);
	}

}
