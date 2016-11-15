package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;

import framework.GameObject;
import framework.ObjectId;
import window.Handler;

public class Boat extends GameObject {

	private int direction = 1;
	private double initialY = 0;
	private WasteBin trashBin;
	private WasteBin recyclebin;
	private CompostCounter counter;
	private double boundary1;
	private double boundary2;
	private double[] dropLocations;

	// Clock
	int timer1 = 0;
	int countdown1 = 0;
	int amount = 0;
	Random rand = new Random();

	public Boat(double x, double y, ObjectId id, Handler handler, WasteBin trashBin, WasteBin recyclebin,
			CompostCounter counter, double boundary1, double boundary2) {
		super(x, y, id, handler);
		this.handler = handler;
		initialY = y;
		this.trashBin = trashBin;
		this.recyclebin = recyclebin;
		this.counter = counter;
		this.boundary1 = boundary1;
		this.boundary2 = boundary2;
		setSpawnLocations(boundary1, boundary2);
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
		for(int i = 0; i < 4; i++){
			if(x == dropLocations[i]){
				spawnWaste();
			}
		}
		if (timer1 + 1 == countdown1) {
			countdown1 = rand.nextInt(500);
			spawnWaste();
			timer1 = 0;
		}
		timer1++;
		// System.out.println(timer1 + ", "+ countdown1);
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
	
	public void setSpawnLocations(double boundary1, double boundary2){
		double temp1 = boundary2-boundary1;
		temp1 = temp1 / 4;
		dropLocations =new double[] {boundary1, boundary1+temp1, boundary1+temp1*2, boundary2-32};
	}

}
