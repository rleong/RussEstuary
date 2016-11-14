package window;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import control.Game;

public class Window {
	
	public Window(Dimension dm, String title, Game game) {
		game.setPreferredSize(dm);
		game.setMaximumSize(dm);
		game.setMinimumSize(dm);
		
		JFrame frame = new JFrame(title);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		
		game.start();
	}
}
