package object;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;
import window.Handler;

import framework.GameObject;
import framework.ObjectId;

public class Clock extends GameObject{
	
	private Handler handler;
	int timer1 = 0;
	int countdown1 = 0;
	int amount = 0;
	Random rand = new Random();
	TrashBin trashbin;
	RecycleBin recyclebin;

	public Clock(double x, double y, ObjectId id, Handler handler, TrashBin trashbin, RecycleBin recyclebin) {
		super(x, y, id);
		this.handler = handler;
		this.trashbin=trashbin;
		this.recyclebin=recyclebin;
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
		if(timer1==countdown1){
			countdown1 = rand.nextInt(500);
			spawnTrash();
			spawnRecycle();
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
	
	public void spawnTrash(){
		amount = rand.nextInt(3);
		for(int i = 0; i < amount; i++){
			handler.addObject(new Trash(rand.nextInt(600), rand.nextInt(200), ObjectId.trash,trashbin,handler));
		}
	}
	
	public void spawnRecycle(){
		amount = rand.nextInt(3);
		for(int i = 0; i < amount; i++){
			handler.addObject(new Recycle(rand.nextInt(600), rand.nextInt(200), ObjectId.recycle,recyclebin,handler));
		}
	}

}
