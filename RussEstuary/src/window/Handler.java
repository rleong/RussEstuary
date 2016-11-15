package window;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;

import framework.GameObject;
import framework.ObjectId;
import object.LandSurface;
import object.Tree;
import object.Waste;

public class Handler {
	
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	private GameObject temp;
	
	public void tick(){
		
		for(int i=0; i<object.size();i++){
			temp = object.get(i);
			
			temp.tick(object);
		}
		
		for(int i=0;i<object.size();i++){
			temp = object.get(i);
			if(temp.getId()==ObjectId.tree){
				Tree tree = (Tree)temp;
				if(tree.hp<=0){
					object.remove(temp);
				}
			}
		}
		
		for(int i=0;i<object.size();i++){
			temp = object.get(i);
			if(temp.getId()==ObjectId.waste){
				Waste waste = (Waste)temp;
				if(waste.getType() == 2 && waste.checkDeath()){
					object.remove(temp);
				}
			}
		}
		
	}
	
	
	public void render(Graphics g){
		for(int i=0; i<object.size();i++){
			temp=object.get(i);
			temp.render(g);
		}
	}
	
	
	public void addObject(GameObject object){
		this.object.add(object);
	}
	public void removeObject(GameObject object){
		this.object.remove(object);
	}
	
	public void creatSurface(Dimension dm, Handler handler){
		int i=0;
		for(;i<dm.getWidth()*3/4;i+=32){
			for(double j=dm.getHeight()*3/5;j<dm.getHeight();j+=32){
				addObject(new LandSurface(i, j, ObjectId.landSurface, handler));
			}
			
		}
		for(double j=dm.getHeight()*3/5;j<dm.getHeight();j+=32){
			addObject(new LandSurface(i-32,j,ObjectId.wall, handler));
		}
		for(;i<=dm.getWidth();i+=32){
			for(double j=dm.getHeight()*3/5;j<dm.getHeight()-64;j+=32){
				addObject(new LandSurface(i,j, ObjectId.seaLevel, handler));
			}
			for(double j=dm.getHeight()-96;j<dm.getHeight();j+=32){
				addObject(new LandSurface(i,j,ObjectId.landSurface, handler));
			}
		}
	}
	
	public double[] spawnLocations(Dimension dm){
		//0		 1		 2					3					 4				
		//Width, Height, Water Start Width, Water Bottom Height, Water Surface Height
		double [] loc = {dm.getWidth(), dm.getHeight(), dm.getWidth()*3/4, dm.getHeight()-96, dm.getHeight()*3/5};
		return loc;
	}
}
