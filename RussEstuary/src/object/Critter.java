package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

import framework.GameObject;
import framework.ObjectId;
import window.Handler;

public class Critter extends GameObject {
	int landCo=1;
	int waterCo=2;
	boolean xdir;
	boolean ydir;
	int character;
	int health0;
	int health1;
	int health2;
	int damage;
	private Handler handler;
	/*
	 * Critter constructor
	 * 
	 */
	public Critter(double x, double y, ObjectId id,boolean xdir, boolean ydir,Handler handler) {
		super(x, y, id);
		this.xdir=xdir;
		this.ydir=ydir;
		character=0;
		setDamage();
		health0=100;
		health1=100;
		health2=100;
		this.handler=handler;
	}
	
	public void setDamage(){
		switch(character){
		case 0:
			damage=20;
			break;
		case 1:
			damage=10;
			break;
		case 2:
			damage=10;
			break;
		}
	}
	public void changeCharacter(){
		character+=1;
		character=character%3;
		//System.out.println(character);
		setDamage();
	}
	public int getDamage(){
		return this.damage;
	}
	/*
	 * set character depends on character token
	 */
	public void setCharacter(int character){
		this.character=character;
	}
	
	/*
	 * ability switch depends on character token
	 */
	private void ability(int character){
		switch(character){
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		}
	}
	
	/*
	 * attack enemy
	 */
	public void attack(LinkedList<GameObject> object){
		for(int i=0;i<object.size();i++){
			GameObject temp = object.get(i);
			if(temp.getId()==ObjectId.trash){
				Trash trash = (Trash)temp;
				if(trash.canAttack){
					trash.health-=damage;
				}
				if(trash.health<=0){
					trash.dead();
				}
				
			}
		}
	}
	
	/*
	 * collect item
	 */
	public void collect(){
		
	}
	
	public void planT(){
		handler.addObject(new Tree(300,100,ObjectId.tree));
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
		x+=velX;
		y+=velY;
		
		if(falling){
			velY+=gravity;
		}
		
		collision(object);

	}
	
	private void collision(LinkedList<GameObject> object){
		for(int i = 0; i < handler.object.size();i++){
			GameObject temp = handler.object.get(i);
			if(temp.getId() == ObjectId.landSurface){
				if(getBoundsBottom().intersects(temp.getBounds())){
					setY(temp.getY()-32);
					
					setVelY(0);
					falling=false;
				}
			}
			if(temp.getId() == ObjectId.trash){
				Trash trash = (Trash)temp;
				if(getBounds().intersects(temp.getBounds())){
					trash.canAttack=true;
					//System.out.println("in range");
				}
				if(!getBounds().intersects(temp.getBounds())){
					trash.canAttack=false;
					//System.out.println("out of range");
				}
			}
		}
	}
	
	

	@Override
	public void render(Graphics g) {
		switch(character){
		case 0:
			g.setColor(Color.RED);
			g.fillRect((int)x, (int)y, 32, 32);
			break;
		case 1:
			g.setColor(Color.CYAN);
			g.fillRect((int)x, (int)y, 32, 32);
			break;
		case 2:
			g.setColor(Color.lightGray);
			g.fillRect((int)x, (int)y, 32, 32);
			break;
		}
		
		
		
		Graphics2D g2d = (Graphics2D)g;
		g.setColor(Color.green);
		g2d.draw(getBoundsTop());
		g2d.draw(getBoundsBottom());
		g2d.draw(getBoundsLeft());
		g2d.draw(getBoundsRight());
		
		g.setColor(Color.gray);
		g2d.draw(getBounds());
	}

	@Override
	public Rectangle getBounds() {
		
		return new Rectangle((int)x-16,(int)y-16, 64,64);
	}
	
	public Rectangle getBoundsTop() {
		
		return new Rectangle((int)x+6,(int)y, 20,6);
	}
	public Rectangle getBoundsBottom() {
		
		return new Rectangle((int)x+6,(int)y+26, 20,6);
	}
	
	public Rectangle getBoundsLeft() {
		
		return new Rectangle((int)x,(int)y+6, 6,20);
	}
	public Rectangle getBoundsRight() {
		
		return new Rectangle((int)x+26,(int)y+6, 6,20);
	}

}
