package control;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import framework.KeyInput;
import framework.ObjectId;
import object.Boat;
import object.Clock;
import object.Critter;
import object.Habitat;
import object.RecycleBin;
import object.Runoff;
import object.Trash;
import object.TrashBin;
import object.Tree;
import window.Handler;
import window.Window;

public class Game extends Canvas implements Runnable{
	
	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -6771508490304664935L;
	
	private boolean running = false;
	private Thread thread;
	
	//Object
	Handler handler;
	TrashBin trashBin=new TrashBin(550, 550, ObjectId.trashBin,handler);
	RecycleBin recyclebin=new RecycleBin(500, 550, ObjectId.recycleBin,handler);
	
	 
	private void init(){
		
		handler = new Handler();
		handler.creatSurface();
		handler.addObject(trashBin);
		handler.addObject(recyclebin);
		handler.addObject(new Habitat(110, 436, ObjectId.habitat,handler));
		handler.addObject(new Boat(0, 50, ObjectId.boat,600,handler,trashBin, recyclebin));
		
		handler.addObject(new Runoff(0,500-32,ObjectId.runOff));
		handler.addObject(new Runoff(50,500-32,ObjectId.runOff));
		handler.addObject(new Runoff(-50,500-32,ObjectId.runOff));
		//handler.addObject(new Clock(1,1,ObjectId.clock,handler,trashBin,recyclebin));
		
		handler.addObject(new Critter(250,250,ObjectId.critter, false, false,handler));
		this.addKeyListener(new KeyInput(handler));
	}
	public synchronized void start(){
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	@Override
	public void run() {
		
		init();
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
//				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
		
	}
	private void tick(){
		handler.tick();
	}
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		//Drow here
		
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		
		handler.render(g);
		//
		g.dispose();
		bs.show();
	}
	
	public static void main(String args[]){
		Game game = new Game();
		new Window(600,600,"Estuary",game);
	}
	
}
