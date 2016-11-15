package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import framework.GameObject;
import framework.ObjectId;
import window.Handler;

public class Waste extends GameObject {
	int damage = 5;
	double health = 20;
	boolean canAttack = false;
	boolean isDead = false;
	int type = 0;

	CompostCounter counter;
	
	WasteBin trashBin;
	WasteBin recycleBin;

	public Waste(double x, double y, ObjectId id, Handler handler, WasteBin trashBin, WasteBin recycleBin,
			CompostCounter counter, int type) {
		super(x, y, id, handler);
		this.trashBin = trashBin;
		this.recycleBin = recycleBin;
		this.counter = counter;
		this.type = type;
		setVelY(1);
	}

	/*
	 * get damage
	 */
	public int getDamage() {
		return this.damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void dead() {
		if (health <= 0) {
			switch (type) {
			case 0: // TRASH & RECYCLE
			case 1:
				setVelX((trashBin.getX() - x) / 50);
				setVelY(-15);
				break;
			case 2: // COMPOST
				counter.setCount();
				break;
			default: // ERROR
				System.out.println("SOMETHING WENT WRONG YO");
				break;
			}
			canAttack = false;
			isDead = true;
		}
	}

	public boolean checkDeath() {
		return isDead;
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
		x += velX;
		y += velY;
		if (!isDead)
			collision(object);
		else {
			velY += gravity * 10;
			switch (type) {
			case 0: // TRASH
				if (x >= trashBin.getX() && x <= trashBin.getX() + 32) {
					velX = 0;
					//System.out.println("Success" + x + " " + trashBin.getX());
				}
				break;
			case 1: // RECYCLE
				if (x >= recycleBin.getX() && x <= recycleBin.getX() + 32) {
					velX = 0;
					//System.out.println("Success" + x + " " + recycleBin.getX());
				}
				break;
			case 2: // COMPOST
				break;
			default: // ERROR
				System.out.println("SOMETHING WENT WRONG YO");
				break;
			}
		}
	}

	@Override
	public void render(Graphics g) {
		switch (type) {
		case 0: // TRASH
			g.setColor(Color.BLUE);
			break;
		case 1: // RECYCLE
			g.setColor(Color.GREEN);
			break;
		case 2: // COMPOST
			g.setColor(Color.ORANGE);
			break;
		default: // ERROR
			g.setColor(Color.RED);
			System.out.println("SOMETHING WENT WRONG YO");
			break;
		}
		g.fillRect((int) x, (int) y, 32, 32);
		g.setColor(Color.red);
		g.fillRect((int) x, (int) y, (int) ((health / 20) * 32), 2);
	}

	@Override
	public Rectangle getBounds() {

		return new Rectangle((int) x, (int) y, 32, 32);
	}

	public int getType() {
		return type;
	}

	private void collision(LinkedList<GameObject> object) {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject temp = handler.object.get(i);
			if (temp.getId() == ObjectId.landSurface) {
				if (getBoundsBottom().intersects(temp.getBounds())) {
					setY(temp.getY() - 32);
					setVelY(0);
				}
			}
		}
	}

	public Rectangle getBoundsBottom() {

		return new Rectangle((int) x + 6, (int) y + 26, 20, 6);
	}

}
