package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import framework.GameObject;
import framework.ObjectId;
import window.Handler;

public class Inventory extends GameObject {

	private int countCompost = 0;
	private int countOyster = 0;
	private int countConcrete = 0;
	private int countSmallSeed = 0;
	private int countBigSeed = 0;
	Critter critter;

	public Inventory(double x, double y, ObjectId id, Handler handler) {
		super(x, y, id, handler);
	}

	public void setCritter(Critter critter) {
		this.critter = critter;
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
	}

	// Compost
	public void addCompost() {
		countCompost++;
	}
	// public void subtractCompost(){
	// countCompost--;
	// }

	// Oyster
	public void addOyster() {
		countOyster++;
	}
	// public void subtractOysterCounter(){
	// countOyster--;
	// }

	// Concrete
	public void addConcrete() {
		countConcrete++;
	}
	// public void subtractConcrete(){
	// countConcrete--;
	// }

	// Small Seed
	public void addSmallSeed() {
		countSmallSeed++;
	}

	// Big Seed
	public void addBigSeed() {
		countBigSeed++;
	}

	@Override
	public void render(Graphics g) {
		// Compost
		String output0 = "x" + countCompost + " Compost";
		g.setColor(Color.white);
		g.drawString(output0, (int) x + 31, (int) y + 23);
		g.setColor(Color.orange);
		g.fillRect((int) x + 10, (int) y + 10, 16, 16);

		// Oyster Shells
		String output1 = "x" + countOyster + " Oyster Shells";
		g.setColor(Color.white);
		g.drawString(output1, (int) x + 31, (int) y + 43);
		g.setColor(Color.blue);
		g.fillRect((int) x + 10, (int) y + 30, 16, 16);

		// Concrete
		String output2 = "x" + countConcrete + " Concrete Material";
		g.setColor(Color.white);
		g.drawString(output2, (int) x + 31, (int) y + 63);
		g.setColor(Color.red);
		g.fillRect((int) x + 10, (int) y + 50, 16, 16);

		// Concrete
		String output3 = "x" + countSmallSeed + " Small Seeds";
		g.setColor(Color.white);
		g.drawString(output3, (int) x + 31, (int) y + 83);
		g.setColor(Color.gray);
		g.fillRect((int) x + 10, (int) y + 70, 16, 16);

		// Concrete
		String output4 = "x" + countBigSeed + " Big Seeds";
		g.setColor(Color.white);
		g.drawString(output4, (int) x + 31, (int) y + 103);
		g.setColor(Color.green);
		g.fillRect((int) x + 10, (int) y + 90, 16, 16);

		// Menu Box
		g.setColor(Color.green);
		g.drawRect((int) x - 5, (int) y - 3, 200, 120);

		// Choices
	}

	public void build(double xx, double yy) {
		if (new Rectangle((int) xx, (int) yy, (int) xx + 1, (int) yy + 1).intersects(getWeakGrass())) {
			critter.planT(0);
		}
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

	public Rectangle getWeakGrass() {
		return new Rectangle((int) x + 10, (int) y + 10, 16, 16);
	}

}
