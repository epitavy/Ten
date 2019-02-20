import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GTopBar extends JPanel{
	
		
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.white);
		g.setFont(new Font("URW Gothic L Book", Font.BOLD, 30));
		g.drawString("Ten", this.getWidth() / 2 - 10, this.getHeight() / 2);
	}
	
}
