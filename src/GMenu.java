import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;


public class GMenu extends JPanel {
	
	private GButton main;
	
	public GMenu() {
		this.setLayout(new GridBagLayout());
		//main = new GButton(200, 100);
		this.add(main);
		
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.orange);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}

}
