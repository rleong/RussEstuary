package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

import framework.GameObject;
import framework.ObjectId;
import window.Handler;

public class Critter extends GameObject {
	int landCo = 1;
	int waterCo = 2;
	boolean xdir;
	boolean ydir;
	int character;
	int health0;
	int health1;
	int health2;
	int damage;
	public boolean jump;
	public boolean onLand;
	boolean invulnerable;
	int timer = 0;
	int flicker = 0;
	double bounds;
	final int SPRECHARGE = 300;
	int sp0 = SPRECHARGE;
	int sp1 = SPRECHARGE;
	int sp2 = SPRECHARGE;

	/*
	 * Critter constructor
	 * 
	 */
	public Critter(double x, double y, ObjectId id, Handler handler, boolean xdir, boolean ydir, double bounds) {
		super(x, y, id, handler);
		this.xdir = xdir;
		this.ydir = ydir;
		character = 0;
		setDamage();
		health0 = 100;
		health1 = 100;
		health2 = 100;
		jump = false;
		this.bounds = bounds;
	}

	public void setDamage() {
		switch (character) {
		case 0:
			damage = 20;
			break;
		case 1:
			damage = 10;
			break;
		case 2:
			damage = 10;
			break;
		}
	}

	public void changeCharacter() {
		character += 1;
		character = character % 3;
		// System.out.println(character);
		setDamage();
	}

	public int getDamage() {
		return this.damage;
	}

	/*
	 * set character depends on character token
	 */
	public void setCharacter(int character) {
		this.character = character;
	}

	/*
	 * ability switch depends on character token
	 */
	public void ability() {
		switch (character) {
		case 0: // CRAB
			if (sp0 == SPRECHARGE) {
				sp0 = 0;
			}
			break;
		case 1: // OYSTER
			if (sp1 == SPRECHARGE) {
				sp1 = 0;
				handler.addObject(new Bubble(x - 8, y - 8, ObjectId.bubble, handler, bounds));
			}
			break;
		case 2: // HORSESHOE CRAB
			if (sp2 == SPRECHARGE) {
				sp2 = 0;
			}
			break;
		}
	}

	/*
	 * attack enemy
	 */
	public void attack(LinkedList<GameObject> object) {
		for (int i = 0; i < object.size(); i++) {
			GameObject temp = object.get(i);
			if (temp.getId() == ObjectId.waste) {
				Waste waste = (Waste) temp;
				if (!waste.checkDeath()) {
					if (waste.canAttack && !waste.getIsTrapped()) {
						waste.health -= damage;
					}
					if (waste.health <= 0) {
						waste.dead();
					}
				}
			}
		}
	}

	/*
	 * collect item
	 */
	public void collect() {

	}

	public void planT() {
		handler.addObject(new Tree(x, y, ObjectId.tree, handler));
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
		x += velX;
		y += velY;

		if (falling) {
			velY += gravity;
		}

		if (invulnerable) {
			if (timer == 80) {
				invulnerable = false;
				timer = 0;
			}
			timer++;
			flicker++;
		} else {
			flicker = 0;
		}

		if (sp0 < SPRECHARGE)
			sp0++;
		if (sp1 < SPRECHARGE)
			sp1++;
		if (sp2 < SPRECHARGE)
			sp2++;

		collision(object);

	}

	private void collision(LinkedList<GameObject> object) {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject temp = handler.object.get(i);
			if (temp.getId() == ObjectId.landSurface) {
				if (getBoundsBottom().intersects(temp.getBounds())) {
					setY(temp.getY() - 32);
					falling = false;
					onLand = true;
					setVelY(0);

				} else {
					onLand = false;
				}
			}
			if (temp.getId() == ObjectId.seaLevel) {
				if (getBoundsTop().intersects(temp.getBounds())) {

					falling = false;

				} else {
					falling = true;
				}
			}
			if (temp.getId() == ObjectId.waste) {
				Waste waste = (Waste) temp;
				if (getBounds().intersects(temp.getBounds())) {
					waste.canAttack = true;
					// System.out.println("in range");
				}
				if (!getBounds().intersects(temp.getBounds())) {
					waste.canAttack = false;
					// System.out.println("out of range");
				}
				if (getBodyBounds().intersects(temp.getBounds())) {
					if (waste.canAttack && !invulnerable) {
						invulnerable = true;
						switch (character) {
						case 0:
							health0 -= 5;
							break;
						case 1:
							health1 -= 5;
							break;
						case 2:
							health2 -= 5;
							break;
						}
					}
					// System.out.println("in range");
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);
		switch (character) {
		case 0:
			g.drawString(health0 + " | " + sp0 + "/" + SPRECHARGE, 10, 40);
			break;
		case 1:
			g.drawString(health1 + " | " + sp1 + "/" + SPRECHARGE, 10, 40);
			break;
		case 2:
			g.drawString(health2 + " | " + sp2 + "/" + SPRECHARGE, 10, 40);
			break;
		}

		if (flicker == 0 || flicker % 10 == 0) {
			switch (character) {
			case 0:
				g.setColor(Color.RED);
				break;
			case 1:
				g.setColor(Color.CYAN);
				break;
			case 2:
				g.setColor(Color.lightGray);
				break;
			}
		} else {
			g.setColor(Color.BLACK);
		}

		g.fillRect((int) x, (int) y, 32, 32);

		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.green);
		g2d.draw(getBoundsTop());
		g2d.draw(getBoundsBottom());
		g2d.draw(getBoundsLeft());
		g2d.draw(getBoundsRight());

		g.setColor(Color.gray);
		g2d.draw(getBounds());

		// System.out.println(health0 + " " + health1 + " " + health2);
	}

	@Override
	public Rectangle getBounds() {

		return new Rectangle((int) x - 16, (int) y - 16, 64, 64);
	}

	public Rectangle getBodyBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

	public Rectangle getBoundsTop() {

		return new Rectangle((int) x + 6, (int) y, 20, 6);
	}

	public Rectangle getBoundsBottom() {

		return new Rectangle((int) x + 6, (int) y + 26, 20, 6);
	}

	public Rectangle getBoundsLeft() {

		return new Rectangle((int) x, (int) y + 6, 6, 20);
	}

	public Rectangle getBoundsRight() {

		return new Rectangle((int) x + 26, (int) y + 6, 6, 20);
	}

}
