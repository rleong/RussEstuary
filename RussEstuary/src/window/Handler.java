package window;

import java.awt.Graphics;
import java.util.LinkedList;

import framework.GameObject;
import framework.ObjectId;
import object.LandSurface;
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
	
	public void creatSurface(){
		for(int i=0;i<20;i++){
			addObject(new LandSurface(i*30, 500, ObjectId.landSurface));
			
		}
	}
}
