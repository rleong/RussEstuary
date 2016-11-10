package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import framework.GameObject;
import framework.ObjectId;
import window.Handler;

public class Trash extends GameObject {
	int damage = 5;
	double health = 20;
	boolean canAttack = false;
	int fallvalue = 1;
	private Handler handler;
	boolean isDead = false;

	TrashBin trashBin;

	public Trash(double x, double y, ObjectId id, TrashBin trashBin, Handler handler) {
		super(x, y, id);
		this.trashBin = trashBin;
		this.handler=handler;
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
			setVelX((trashBin.getX() - x) / 50);
			setVelY(-5);

			canAttack = false;
			isDead = true;
		}

	}

	@Override
	public void tick(LinkedList<GameObject> object) {

		x += velX;
		y += velY;
		if (health <= 0) {
			velY += gravity * 10;
		}

		y += fallvalue;
		
		if(!isDead)
			collision(object);

	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect((int) x, (int) y, 32, 32);
		g.setColor(Color.red);
		g.fillRect((int) x, (int) y, (int) ((health / 20) * 32), 2);
	}

	@Override
	public Rectangle getBounds() {

		return new Rectangle((int) x, (int) y, 32, 32);
	}

	private void collision(LinkedList<GameObject> object) {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject temp = handler.object.get(i);
			if (temp.getId() == ObjectId.landSurface) {
				if (getBoundsBottom().intersects(temp.getBounds())) {
					setY(temp.getY() - 32);

					setVelY(0);
					falling = false;
				}
			}
		}
	}

	public Rectangle getBoundsBottom() {

		return new Rectangle((int) x + 6, (int) y + 26, 20, 6);
	}

}
