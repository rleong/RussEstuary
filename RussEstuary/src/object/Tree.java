package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.LinkedList;

import framework.GameObject;
import framework.ObjectId;

public class Tree extends GameObject {
	public int hp;
	public Tree(double x, double y, ObjectId id) {
		super(x, y, id);
		hp=3;
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
		
		collision(object);
	}
	
	private void collision(LinkedList<GameObject> object){
		Iterator<GameObject> itr = object.iterator();
		GameObject temp;
		for(;itr.hasNext();){
			temp=itr.next();
			if(temp.getId()==ObjectId.runOff){
				if(getBounds().intersects(temp.getBounds())){
					hp-=1;
					itr.remove();
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect((int)x, (int)y-96, 32, 32);
		g.setColor(Color.darkGray);
		g.fillRect((int)x, (int)y-64, 32, 96);
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y-96, (int)(hp*32/3), 2);
	}

	@Override
	public Rectangle getBounds() {
		
		return new Rectangle((int)x,(int)y-96,32,128);
	}

}
