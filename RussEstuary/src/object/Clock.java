package control;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;
import window.Handler;

import framework.GameObject;
import framework.ObjectId;
import object.Trash;
import object.TrashBin;
import object.Tree;

public class Clock extends GameObject{
	
	private Handler handler;
	int timer1 = 0;
	int countdown1 = 0;
	int amount = 0;
	Random rand = new Random();

	public Clock(double x, double y, ObjectId id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
		if(timer1==countdown1){
			countdown1 = rand.nextInt(500);
			amount = rand.nextInt(2);
			switch(amount){
			case 0:
				spawnTrash();
				break;
			case 1:
				spawnTrash();
				spawnTrash();
				break;
			case 2:
				spawnTrash();
				spawnTrash();
				spawnTrash();
				break;
			default:
				spawnTrash();
				break;
			}
			timer1 = 0;
		}
		timer1 ++;
		System.out.println(timer1 + ", "+ countdown1);
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void countDown(){
		
	}
	
	public void spawnTrash(){
		TrashBin trashBin=new TrashBin(550, 550, ObjectId.trashBin,handler);
		handler.addObject(new Trash(rand.nextInt(600), rand.nextInt(200), ObjectId.trash,trashBin,handler));
	}

}
