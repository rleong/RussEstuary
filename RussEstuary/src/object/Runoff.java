package object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import control.Game;
import framework.GameObject;
import framework.ObjectId;
import window.Handler;

public class Runoff extends GameObject {
	public int type;
	Dimension dm;
	boolean active;
	Game game;
	public Runoff(double x, double y,Dimension dm, Handler handler, ObjectId id, int type,Game game) {
		super(x, y, id, handler);
		setVelX(1.2);
		this.type=type;
		this.dm=dm;
		active=true;
		this.game=game;
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
		if(falling)
			velY+=gravity;
		x+=velX;
		y+=velY;
		if(x>dm.getWidth()+64){
			x=-32;
			active=true;
		}
		collision(handler.object);
	}

	@Override
	public void render(Graphics g) {
		switch(type){
		case 0:
			g.setColor(Color.magenta);
			break;
		case 1:
			g.setColor(Color.white);
			break;
		}
		
		g.fillRect((int)x, (int)y, 32, 32);

	}

	@Override
	public Rectangle getBounds() {
		
		return new Rectangle((int)x,(int)y,32,32);
	}
	
	private void collision(LinkedList<GameObject> object){
		for(int i = 0; i < handler.object.size();i++){
			GameObject temp = handler.object.get(i);
			if(temp.getId() == ObjectId.landSurface){
				if(getBounds().intersects(temp.getBounds())){
					falling=false;
					velY=0;
					setY(temp.getY()-32);
				}
				else{
					falling=true;
				}
			}
			if(temp.getId() == ObjectId.sand){
				if(getBounds().intersects(temp.getBounds())){
					falling=false;
					velY=0;
					setY(temp.getY()-32);
				}
				else{
					falling=true;
				}
			}
			if(temp.getId() == ObjectId.seaLevel){
				
				if(getBounds().intersects(temp.getBounds())){
					
					if(active){
						game.count+=1;
						active=false;
						System.out.println(game.count);
					}
				}
			}
			
		}	
	}

}
