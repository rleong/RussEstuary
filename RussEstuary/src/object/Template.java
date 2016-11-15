package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import framework.GameObject;
import framework.ObjectId;
import window.Handler;

public class Template extends GameObject{
	double theta=Math.PI/2;
	public Template(double x, double y, ObjectId id, Handler handler) {
		super(x, y, id, handler);
		
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
		
		
		double sy = 5*Math.sin(theta);
		theta+=0.1;
		this.setVelY(sy);
		this.y+=this.getVelY();
	}

	@Override
	public void render(Graphics g) {
		
		g.setColor(Color.RED);
		g.fillRect((int)x, (int)y, 30, 30);
	}

	@Override
	public Rectangle getBounds() {
		
		return null;
	}

}
