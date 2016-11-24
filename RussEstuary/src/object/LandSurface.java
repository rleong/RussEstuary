package object;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import control.Game;
import framework.GameObject;
import framework.ObjectId;
import window.Handler;

public class LandSurface extends GameObject {
	Game game;
	public LandSurface(double x, double y, ObjectId id, Handler handler, Game game) {
		super(x, y, id, handler);
		this.game=game;
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
		
		
	}

	@Override
	public void render(Graphics g) {
		if(id==ObjectId.landSurface){
			g.setColor(Color.yellow);
			g.fillRect((int)x, (int)y, 32, 32);
		}
		if(id==ObjectId.seaLevel){
			switch(game.getCount()){
			case 0:
				g.setColor(Color.CYAN);
				g.fillRect((int)x, (int)y, 32, 32);
				break;
			case 1:
				g.setColor(Color.blue);
				g.fillRect((int)x, (int)y, 32, 32);
				break;
			case 2:
				g.setColor(Color.green);
				g.fillRect((int)x, (int)y, 32, 32);
				break;
			case 3:
				g.setColor(Color.darkGray);
				g.fillRect((int)x, (int)y, 32, 32);
				break;
			default:
				g.setColor(Color.black);
				g.fillRect((int)x, (int)y, 32, 32);
				break;
			}
			
			
		}
		if(id==ObjectId.wall){
			g.setColor(Color.gray);
			g.fillRect((int)x, (int)y, 32, 32);
		}
		if(id==ObjectId.sand){
			g.setColor(Color.ORANGE);
			g.fillRect((int)x, (int)y, 32, 32);
		}
		
	}

	@Override
	public Rectangle getBounds() {
		
		return new Rectangle((int)x, (int)y, 32,32);
	}

}
