package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.LinkedList;

import framework.GameObject;
import framework.ObjectId;
import window.Handler;

public class WasteBin extends GameObject {
	int type = 0;
	
	public WasteBin(double x, double y, ObjectId id, Handler handler, int type) {
		super(x, y, id, handler);
		this.type=type;
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
		
		collision(object);
	}

	@Override
	public void render(Graphics g) {
		if(type == 0){
			g.setColor(Color.PINK);
		}else if(type == 1){
			g.setColor(Color.CYAN);
		}else{
			System.out.println("Something went wrong you baffoon!");
		}
		g.fillRect((int)x, (int)y, 32, 32);

	}
	
	private void collision(LinkedList<GameObject> object){
		Iterator<GameObject> itr=object.iterator();
		for(;itr.hasNext();){
			GameObject temp = itr.next();
			if(temp.getId()==ObjectId.waste){
				if(getBounds().intersects(temp.getBounds()))
					itr.remove();
			}
		}
	}
	
	public int getType(){
		return type;
	}
	
	@Override
	public Rectangle getBounds() {
		
		return new Rectangle((int)x, (int)y, 32, 32);
	}

}
