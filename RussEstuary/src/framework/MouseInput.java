package framework;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import control.Game;
import object.Critter;
import object.Inventory;
import window.Handler;

public class MouseInput implements MouseListener {

	Handler handler;
	Game gm;

	public MouseInput(Handler handler, Game gm) {
		this.handler = handler;
		this.gm = gm;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (!gm.isPause()) {
			for (int i = 0; i < handler.object.size(); i++) {
				GameObject temp = handler.object.get(i);

				if (temp.getId() == ObjectId.inventory) {
					gm.setPause(2000);
					Inventory inv = (Inventory) temp;
					inv.build(x, y);
				}

			}
		}

		// Test
		System.out.println(x + "," + y);
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
