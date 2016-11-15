package window;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import control.Game;
import control.Game;

public class Window {
	
	private JFrame frame;
	
	public Window(Dimension dm, String title, Game game) {
		game.setPreferredSize(dm);
		game.setMaximumSize(dm);
		game.setMinimumSize(dm);
		
		frame = new JFrame(title);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		
		game.start();
	}
	
	public void changeWindow(int w, int h, String title, Game gamec){
		
		//Dimension dm = new Dimension(w,h);
		
		gamec.setPreferredSize(new Dimension(w, h));
		gamec.setMaximumSize(new Dimension(w,h));
		gamec.setMinimumSize(new Dimension(w,h));
		
		//frame = new JFrame(title);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(gamec);
		frame.getContentPane().revalidate();
		frame.repaint();
		gamec.start();
		
	}
}
