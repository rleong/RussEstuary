package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import framework.GameObject;
import framework.ObjectId;
import window.Handler;

public class Seed extends GameObject {
	int type;
	public Seed(double x, double y, ObjectId id, Handler handler, int type) {
		super(x, y, id, handler);
		this.type=type;
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {
		switch(type){
		case 0:
			g.setColor(Color.green);
			break;
		case 1:
			g.setColor(Color.PINK);
			break;
		}
		g.fillOval((int)x, (int)y, 32, 32);
		

	}

	@Override
	public Rectangle getBounds() {
		
		return new Rectangle((int)x, (int)y, 32,32);
	}

}
