package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.Timer;

import control.Game;
import framework.GameObject;
import framework.ObjectId;
import window.Handler;

public class Critter extends GameObject {

	// Character Main Attributes
	int character;
	int damage;
	Inventory inventory;
	Game game;

	// Character Health
	int health0;
	int health1;
	int health2;
	int flicker = 0;
	boolean invulnerable;
	Timer clockInvincible;

	// Character Physics
	int landCo = 1;
	int waterCo = 2;
	public boolean jump;
	public boolean onLand;
	public boolean inWater;

	// Character Special Power
	final int SPRECHARGE = 300;
	int sp0 = SPRECHARGE;
	int sp1 = SPRECHARGE;
	int sp2 = SPRECHARGE;

	// Location on Screen
	double xbounds;
	double ybounds;
	boolean xdir;
	boolean ydir;

	// Location of Interface
	int healthBarXLocation;
	int healthBarYLocation;
	int nameXLocation;
	int nameYLocation;

	// Menu
	// boolean menuActivation = false;

	// Debugging
	boolean debugging = false;

	public Critter(double x, double y, ObjectId id, Handler handler, boolean xdir, boolean ydir, double xbounds,
			double ybounds, Inventory inventory, Game game) {
		// Basics
		super(x, y, id, handler);
		this.xdir = xdir;
		this.ydir = ydir;
		character = 0;
		setDamage();
		health0 = 100;
		health1 = 100;
		health2 = 100;
		this.inventory = inventory;
		this.game = game;

		// Physics
		jump = false;
		inWater = false;

		// Boundaries of Dimensions
		this.xbounds = xbounds;
		this.ybounds = ybounds;

		// Health Bar & Name Locations
		nameXLocation = (int) (xbounds - (xbounds * 18 / 100));
		nameYLocation = (int) (ybounds - (ybounds * 94 / 100));
		healthBarXLocation = (int) (xbounds - (xbounds * 1 / 8));
		healthBarYLocation = (int) (ybounds - (ybounds * 95 / 100));

	}

	@Override
	public void tick(LinkedList<GameObject> object) {

		// Character Physics
		x += velX;
		y += velY;

		if (x > xbounds * 3 / 4 - 64) {
			onLand = false;
			falling = true;
		}

		if (falling || (falling && x > xbounds * 3 / 4)) {
			velY += gravity;
		}

		if (y < ybounds * 3 / 5 - 48 && x > xbounds * 3 / 4 - 32) {
			setY(ybounds * 3 / 5 - 48);
			setVelY(0);
			jump = true;
		}

		// Character Status
		if (invulnerable) {
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

		// Collisions
		collision(object);

	}

	@Override
	public void render(Graphics g) {

		// Character Flickering & Normal
		if (flicker == 0 || flicker % 10 == 0) {
			switch (character) {
			case 0:
				g.setColor(Color.RED);
				break;
			case 1:
				g.setColor(Color.DARK_GRAY);
				break;
			case 2:
				g.setColor(Color.lightGray);
				break;
			}
		} else {
			g.setColor(Color.BLACK);
		}
		g.fillRect((int) x, (int) y, 32, 32);

		// Character Inner Bounds
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.green);
		g2d.draw(getBoundsTop());
		g2d.draw(getBoundsBottom());
		g2d.draw(getBoundsLeft());
		g2d.draw(getBoundsRight());

		// Character Attack Range Bounds
		g.setColor(Color.gray);
		g2d.draw(getBounds());

		// Character Name
		g.setColor(Color.WHITE);
		switch (character) {
		case 0:
			g.drawString("Blue Crab", (int) x - 10, (int) y - 10);
			break;
		case 1:
			g.drawString("Eastern Oyster", (int) x - 22, (int) y - 10);
			break;
		case 2:
			g.drawString("Horseshoe Crab", (int) x - 26, (int) y - 10);
			break;
		default:
			g.drawString("Debug Godmode", (int) x - 24, (int) y - 10);
			break;
		}

		// Crab Health & SP Display
		g.setColor(Color.BLACK);
		if (health0 <= 0) {
			g.setColor(Color.RED);
		}
		g.setColor(Color.WHITE);
		g.drawString("Blue Crab", nameXLocation, nameYLocation);
		g.setColor(Color.green);
		g.drawRect(healthBarXLocation, healthBarYLocation, 200, 10);
		g.drawRect(healthBarXLocation, healthBarYLocation + 20, 200, 10);
		g.setColor(Color.RED);
		g.fillRect(healthBarXLocation + 1, healthBarYLocation + 2, (int) (health0 * 2) - 1, 7);
		g.setColor(Color.YELLOW);
		g.fillRect(healthBarXLocation + 1, healthBarYLocation + 2 + 20, (int) sp0 * 2 / 3 - 1, 7);

		// Oyster Health & SP Display
		g.setColor(Color.BLACK);
		if (health1 <= 0) {
			g.setColor(Color.RED);
		}
		g.setColor(Color.WHITE);
		g.drawString("Eastern Oyster", nameXLocation, nameYLocation + 70);
		g.setColor(Color.green);
		g.drawRect(healthBarXLocation, healthBarYLocation + 70, 200, 10);
		g.drawRect(healthBarXLocation, healthBarYLocation + 90, 200, 10);
		g.setColor(Color.RED);
		g.fillRect(healthBarXLocation + 1, healthBarYLocation + 72, (int) (health1 * 2) - 1, 7);
		g.setColor(Color.YELLOW);
		g.fillRect(healthBarXLocation + 1, healthBarYLocation + 2 + 90, (int) sp1 * 2 / 3 - 1, 7);

		// Horseshoe Crab Health & SP Display
		g.setColor(Color.BLACK);
		if (health2 <= 0) {
			g.setColor(Color.RED);
		}
		g.setColor(Color.WHITE);
		g.drawString("Horseshoe Crab", nameXLocation, nameYLocation + 140);
		g.setColor(Color.green);
		g.drawRect(healthBarXLocation, healthBarYLocation + 140, 200, 10);
		g.drawRect(healthBarXLocation, healthBarYLocation + 160, 200, 10);
		g.setColor(Color.RED);
		g.fillRect(healthBarXLocation + 1, healthBarYLocation + 142, (int) (health2 * 2) - 1, 7);
		g.setColor(Color.YELLOW);
		g.fillRect(healthBarXLocation + 1, healthBarYLocation + 2 + 160, (int) sp2 * 2 / 3 - 1, 7);

		// Menu
		// if(menuActivation)
		// drawMenu(g);

		// Debugging Purposes Only
		if (debugging) {
			g.setColor(Color.red);
			switch (character) {
			case 0:
				g.drawString(health0 + " | " + sp0 + "/" + SPRECHARGE, (int) xbounds / 2, (int) ybounds / 2);
				break;
			case 1:
				g.drawString(health1 + " | " + sp1 + "/" + SPRECHARGE, (int) xbounds / 2, (int) ybounds / 2);
				break;
			case 2:
				g.drawString(health2 + " | " + sp2 + "/" + SPRECHARGE, (int) xbounds / 2, (int) ybounds / 2);
				break;
			}
			// test(g);
		}

	}

	// Menu (Currently not using)
	/*
	 * public void drawMenu(Graphics g){ g.setColor(Color.WHITE);
	 * g.drawRect((int)x-32, (int)y-96, 96, 48); }
	 * 
	 * public void toggleMenu(){ if(menuActivation) menuActivation = false; else
	 * menuActivation = true; }
	 */

	// Set Character Damage
	public void setDamage() {
		switch (character) {
		case 0:
			damage = 10;
			break;
		case 1:
			damage = 10;
			break;
		case 2:
			damage = 10;
			break;
		}
	}

	public int getDamage() {
		return this.damage;
	}

	// Timer for invincibility of Character after getting hurt
	ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			invulnerable = false;
			clockInvincible.stop();
		}
	};

	// Sets & Changes Characters
	public void changeCharacter() {
		character += 1;
		character = character % 3;
		setDamage();
	}

	public void setCharacter(int character) {
		this.character = character;
	}

	/**
	 * Crab has a huge swing that increases hitbox, and damage.
	 * 
	 * Oyster has a bubble that sucks in Recyclables and drops them in the bins.
	 * 
	 * Horseshoe Crab can heal other teammates whilst taking damage by itself.
	 */
	// Special Abilities
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
				handler.addObject(new Bubble(x - 8, y - 8, ObjectId.bubble, handler, xbounds));
			}
			break;
		case 2: // HORSESHOE CRAB
			if (sp2 == SPRECHARGE) {
				sp2 = 0;
			}
			break;
		}
	}

	// Attacking an enemy
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
			/*
			 * if (temp.getId() == ObjectId.waterTree) { WaterTree wt =
			 * (WaterTree) temp; if (wt.canAttack) { wt.hp -= damage; } if
			 * (wt.hp <= 0) wt.dead(); }
			 */
		}
	}

	// Collects Items
	public void collect() {

	}

	// Plants plants
	public void planT(int type) {
		handler.addObject(new Tree(x, y, ObjectId.tree, handler, type, game));
	}

	/**
	 * Collision
	 * 
	 * @param object
	 * 
	 *            Collision with land so it doesn't phase through.
	 * 
	 *            If hit by Trash or Recycle, loses 5 health. Compost does not
	 *            hurt critter.
	 * 
	 */
	// Collision
	private void collision(LinkedList<GameObject> object) {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject temp = handler.object.get(i);
			if (temp.getId() == ObjectId.landSurface) {
				if (getBoundsBottom().intersects(temp.getBounds())) {
					setY(temp.getY() - 31);
					falling = false;
					setVelY(0);
					jump = false;
					onLand = true;
				} else {
					onLand = false;
				}
			}
			if (temp.getId() == ObjectId.seaLevel) {
				if (getBoundsSelf().intersects(temp.getBounds())) {

					jump = false;

				}

			}
			if (temp.getId() == ObjectId.sand) {
				if (getBoundsBottom().intersects(temp.getBounds())) {
					setY(temp.getY() - 31);
					setVelY(0);
					jump = false;
				}
			}
			if (temp.getId() == ObjectId.waterTree) {
				WaterTree tree = (WaterTree) temp;
				if (getBounds().intersects(temp.getBounds())) {
					tree.canAttack = true;
				}
				if (!getBounds().intersects(temp.getBounds())) {
					tree.canAttack = false;
				}
			}
			if (temp.getId() == ObjectId.wall) {
				if (getBoundsLeft().intersects(temp.getBounds())) {
					setX(xbounds * 3 / 4);
					setVelX(0);
				}
			}
			if (temp.getId() == ObjectId.seed) {
				Seed seed = (Seed) temp;
				if (getBoundsSelf().intersects(temp.getBounds())) {
					switch (seed.type) {
					case 0:
						inventory.addSmallSeed();
						break;
					case 1:
						inventory.addBigSeed();
						break;
					}
					object.remove(temp);
				}
			}
			if (temp.getId() == ObjectId.waste) {
				Waste waste = (Waste) temp;
				if (getBounds().intersects(temp.getBounds())) {
					waste.canAttack = true;
				}
				if (!getBounds().intersects(temp.getBounds())) {
					waste.canAttack = false;
				}
				if (getBodyBounds().intersects(temp.getBounds())) {
					if (waste.canAttack && !invulnerable && waste.getType() != 2) {
						invulnerable = true;
						clockInvincible = new Timer(3000, listener);
						clockInvincible.start();
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
				}
			}
		}
	}

	// Debug Mode
	public void setDebug() {
		if (debugging)
			debugging = false;
		else
			debugging = true;
	}

	public void test(Graphics g) {
		g.drawString("haha", (int) xbounds / 2, (int) ybounds / 2);
	}

	// Hitboxes and Collision boxes
	@Override
	public Rectangle getBounds() {

		return new Rectangle((int) x - 16, (int) y - 16, 64, 64);
	}

	public Rectangle getBoundsSelf() {
		return new Rectangle((int) x, (int) y, 32, 32);
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
