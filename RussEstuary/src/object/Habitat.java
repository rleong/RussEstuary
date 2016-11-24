package object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.Timer;

import framework.GameObject;
import framework.ObjectId;
import window.Handler;

public class Habitat extends GameObject{
	
	private double health = 30;
	private final int TRASHDAMAGE = 1;
	private final double COMPOSTDAMAGE = .5;
	private int numberOfHazards = 0;
	private int numberOfCompost = 0;
	private double width;
	
	Timer clock;

	public Habitat(double x, double y, ObjectId id, Handler handler, Dimension dm) {
		super(x, y, id,handler);
		width = dm.getWidth()-dm.getWidth()*3/4;
		clock = new Timer(7000, listener);
		clock.start();
	}
	
	ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			//collision(object);
			health -= (numberOfHazards * TRASHDAMAGE) + (numberOfCompost * COMPOSTDAMAGE);
			clock.restart();
		}
	};

	@Override
	public void tick(LinkedList<GameObject> object) {
		collision(object);
		if(health<=0){
			clock.stop();
		}
	}

	@Override
	public void render(Graphics g) {
		if(health<=0)
			g.setColor(Color.RED);
		else
			g.setColor(Color.GRAY);
		g.fillRect((int) x, (int) y, (int)width, 64);
		g.setColor(Color.red);
		g.fillRect((int) x, (int) y-8, (int) ((health / 30) * width), 6);
	}
	
	private void collision(LinkedList<GameObject> object) {
		numberOfHazards = 0;
		numberOfCompost = 0;
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject temp = handler.object.get(i);
			if (temp.getId() == ObjectId.waste) {
				Waste waste = (Waste) temp;
				if (getBounds().intersects(temp.getBounds()) && !waste.checkDeath() && !waste.getIsTrapped()) {
					if(waste.getType()!=2)
						numberOfHazards += 1;
					else
						numberOfCompost += 1;
				}
			}
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x + 6, (int) y + 26, (int)width, 64);
	}

}
