package object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;

import framework.GameObject;
import framework.ObjectId;
import window.Handler;

public class WaterTree extends GameObject {
	Dimension dm;
	public boolean canAttack;
	public int hp;
	int type;

	public WaterTree(double x, double y, ObjectId id, Handler handler, Dimension dm) {
		super(x, y, id, handler);
		this.dm=dm;
		hp=20;
		Random random =  new Random();
		
		type=random.nextInt(10)%2;
	}
//	public void change(){
//		type+=1;
//		type=type%2;
//	}

	public void dead(){
			
		canAttack=false;
		drop();
		
	}
	public void drop(){
		handler.addObject(new Seed(x,y-64,ObjectId.seed, handler, type));
	}
	

	@Override
	public void tick(LinkedList<GameObject> object) {
		
		x+=velX;
		y+=velY;
//		if(hp<=0){
//			velY+=gravity*10;
//		}
	}

	@Override
	public void render(Graphics g) {
		switch(type){
		case 0:
			g.setColor(Color.blue);
			g.fillRect((int)x, (int)y, 32, 32);
			g.setColor(Color.BLACK);
			g.fillRect((int)x, (int)y+32, 32, 64);
			break;
		case 1:
			g.setColor(Color.white);
			g.fillRect((int)x, (int)y, 32, 32);
			g.setColor(Color.BLACK);
			g.fillRect((int)x, (int)y+32, 32, 64);
			
			break;
		}
			
		
		
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y, 32, 5);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 96);
	}

}
