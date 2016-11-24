package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.LinkedList;

import control.Game;
import framework.GameObject;
import framework.ObjectId;
import window.Handler;

public class Tree extends GameObject {
	public int hp;
	public int type;
	Game game;
	public Tree(double x, double y, ObjectId id, Handler handler, int type, Game game) {
		super(x, y, id, handler);
		hp=3;
		this.type=type;
		this.game=game;
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
				Runoff rof=(Runoff)temp;
				if(getBounds().intersects(temp.getBounds())){
					if(this.type==rof.type){
						game.setnRof(game.getnRof()-1);
						hp-=1;
						itr.remove();
					}
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		switch(type){
		case 0:
			g.setColor(Color.green);
			g.fillRect((int)x, (int)y-96, 32, 32);
			g.setColor(Color.darkGray);
			g.fillRect((int)x, (int)y-64, 32, 96);
			break;
		case 1:
			g.setColor(Color.blue);
			g.fillRect((int)x, (int)y-96, 32, 32);
			g.setColor(Color.darkGray);
			g.fillRect((int)x, (int)y-64, 32, 96);
			break;
		}
		
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y-96, (int)(hp*32/3), 2);
	}

	@Override
	public Rectangle getBounds() {
		
		return new Rectangle((int)x,(int)y-96,32,128);
	}

}
