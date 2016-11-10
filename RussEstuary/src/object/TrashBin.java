package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.LinkedList;

import framework.GameObject;
import framework.ObjectId;
import window.Handler;

public class TrashBin extends GameObject {
	Handler handler;
	public TrashBin(double x, double y, ObjectId id,Handler handler) {
		super(x, y, id);
		this.handler=handler;
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
		
		collision(object);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.PINK);
		g.fillRect((int)x, (int)y, 32, 32);

	}
	private void collision(LinkedList<GameObject> object){
		Iterator<GameObject> itr=object.iterator();
		for(;itr.hasNext();){
			GameObject temp = itr.next();
			if(temp.getId()==ObjectId.trash){
				if(getBounds().intersects(temp.getBounds()))
					itr.remove();
			}
		}
	}
	
	@Override
	public Rectangle getBounds() {
		
		return new Rectangle((int)x, (int)y, 32, 32);
	}

}
