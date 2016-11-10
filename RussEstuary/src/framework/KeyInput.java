package framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import object.Critter;
import window.Handler;

public class KeyInput extends KeyAdapter {
	Handler handler;
	
	
	public KeyInput(Handler handler){
		this.handler=handler;
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		for(int i=0;i<handler.object.size();i++){
			GameObject temp = handler.object.get(i);
			
			if(temp.getId() == ObjectId.critter){
				if(key == KeyEvent.VK_A){
					temp.setVelX(-3);
				}
				if(key == KeyEvent.VK_D){
					temp.setVelX(3);
				}
				if(key == KeyEvent.VK_W){
					temp.setVelY(-3);
				}
				if(key == KeyEvent.VK_S){
					temp.setVelY(3);
				}
				
			}
			
		}
		if(key == KeyEvent.VK_ESCAPE){
			System.exit(1);
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		for(int i=0;i<handler.object.size();i++){
			GameObject temp = handler.object.get(i);
			
			if(temp.getId() == ObjectId.critter){
				Critter t = (Critter)temp;
				if(key == KeyEvent.VK_A){
					temp.setVelX(0);
				}
				if(key == KeyEvent.VK_D){
					temp.setVelX(0);
				}
				if(key == KeyEvent.VK_W){
					temp.setVelY(0);
				}
				if(key == KeyEvent.VK_S){
					temp.setVelY(0);
				}
				if(key == KeyEvent.VK_SPACE){
					
					t.attack(handler.object);
					
				}
				if(key== KeyEvent.VK_I){
					t.changeCharacter();
				}
				if(key==KeyEvent.VK_P){
					t.planT();
				}
				
			}
			
		}

	}
}
