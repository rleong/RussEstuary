package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.Timer;

import framework.GameObject;
import framework.ObjectId;
import window.Handler;

public class Boat extends GameObject {

	private int direction = 1;
	private double initialY = 0;
	private WasteBin trashBin;
	private WasteBin recyclebin;
	private Inventory counter;
	private double boundary1;
	private double boundary2;

	// Random Droppings
	int amount = 0;
	Random rand = new Random();
	
	// Swing Timer
	Timer clock;

	public Boat(double x, double y, ObjectId id, Handler handler, WasteBin trashBin, WasteBin recyclebin,
			Inventory counter, double boundary1, double boundary2) {
		super(x, y, id, handler);
		this.handler = handler;
		initialY = y;
		this.trashBin = trashBin;
		this.recyclebin = recyclebin;
		this.counter = counter;
		this.boundary1 = boundary1;
		this.boundary2 = boundary2;
		clock = new Timer(2000, listener);
		clock.start();
	}
	
	ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			spawnWaste();
			clock.restart();
		}
	};

	@Override
	public void tick(LinkedList<GameObject> object) {
		x += direction;
		y += .25 * Math.sin(x / 25);
		collide();
	}

	public void collide() {
		if (x + 128 >= boundary2 || x <= boundary1) {
			direction *= -1;
			y = initialY;
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.MAGENTA);
		g.fillRect((int) x, (int) y, 128, 48);
	}

	@Override
	public Rectangle getBounds() {

		return null;
	}

	public void spawnWaste() {
		amount = rand.nextInt(4);
		//System.out.println(amount);
		switch (amount) {
		case 0:
			//Nothing
			break;
		case 1:
			//1 Trash
			handler.addObject(new Waste(x, y, ObjectId.waste, handler, trashBin, recyclebin, counter, 0));
			break;
		case 2:
			//1 Recycle
			handler.addObject(new Waste(x, y, ObjectId.waste, handler, trashBin, recyclebin, counter, 1));
			break;
		case 3:
			//1 Compost
			handler.addObject(new Waste(x, y, ObjectId.waste, handler, trashBin, recyclebin, counter, 2));
			break;
		default:
			System.out.println("Something went wrong!");
			break;
		}
	}

}
