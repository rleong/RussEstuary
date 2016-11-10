package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import framework.GameObject;
import framework.ObjectId;

public class Runoff extends GameObject {

	public Runoff(double x, double y, ObjectId id) {
		super(x, y, id);
		setVelX(3);
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
		x+=velX;
		if(x>632){
			x=0;
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.magenta);
		g.fillRect((int)x, (int)y, 32, 32);

	}

	@Override
	public Rectangle getBounds() {
		
		return new Rectangle((int)x,(int)y,32,32);
	}

}
