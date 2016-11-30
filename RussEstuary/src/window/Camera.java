package window;

import java.awt.Dimension;

import framework.GameObject;

public class Camera {
	
	private double x, y;
	public Dimension dm;
	
	public Camera(double x, double y, Dimension dm){
		this.x=x;
		this.y=y;
		this.dm=dm;
	}
	
	public double getX(){
		return x;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public double getY(){
		return y;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public void tick(GameObject critter) {
		x = -critter.getX() + dm.getWidth() / 2;
		y = -critter.getY() + dm.getHeight() / 2;
	}

}
