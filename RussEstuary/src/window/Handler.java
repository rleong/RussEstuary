package window;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;

import framework.GameObject;
import framework.ObjectId;
import object.Compost;
import object.LandSurface;
import object.Trash;
import object.Tree;

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
			if(temp.getId()==ObjectId.compost){
				Compost compost = (Compost)temp;
				if(compost.checkDeath()){
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
	
	public void creatSurface(Dimension dm){
		int i=0;
		for(;i<dm.getWidth()*3/4;i+=32){
			for(double j=dm.getHeight()*3/5;j<dm.getHeight();j+=32){
				addObject(new LandSurface(i, j, ObjectId.landSurface));
			}
			
		}
		for(double j=dm.getHeight()*3/5;j<dm.getHeight();j+=32){
			addObject(new LandSurface(i-32,j,ObjectId.wall));
		}
		for(;i<=dm.getWidth();i+=32){
			for(double j=dm.getHeight()*3/5;j<dm.getHeight()-64;j+=32){
				addObject(new LandSurface(i,j, ObjectId.seaLevel));
			}
			for(double j=dm.getHeight()-96;j<dm.getHeight();j+=32){
				addObject(new LandSurface(i,j,ObjectId.landSurface));
			}
		}
	}
	
	public double[] habitatLoc(Dimension dm){
		double[] loc = {dm.getWidth()*3/4,dm.getHeight()-96-64};
		//System.out.println(loc[0]+" "+loc[1]);
		return loc;
	}
	
	public double[] boatLoc(Dimension dm){
		double[] loc = {dm.getWidth()*3/4,dm.getHeight()*3/5-40};
		//x location (first boundary), y location, last boundary
		//System.out.println(loc[0]+" "+loc[1]);
		return loc;
	}
}
