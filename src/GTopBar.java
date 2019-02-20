import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GTopBar extends JPanel{
	
	private BasicGraphics basicG;
	private String text;
	private Color textColor;
	
	public GTopBar() {
		text = "Ten";
		basicG = new BasicGraphics();
		textColor = Color.white;
	}
	
	public void paintComponent(Graphics g) {
		basicG.g = g;
		
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		Font f = new Font("URW Gothic L Book", Font.BOLD, 30);
		basicG.drawCenteredString(text, this.getWidth() / 2, this.getHeight() / 2, textColor, f);
	}
	
	public void drawEnd(Flag winner) {
		switch(winner){
		case CIRCLE:
			text = "Player circle win!";
			textColor = TenColors.circleBg;
			break;
		case CROSS:
			text = "Player cross win!";
			textColor = TenColors.crossBg;
			break;
		case TIE:
			text = "Nobody win! Draw game";
			textColor = TenColors.tieWin;
			break;
		default:
			text = "Error";
			textColor = Color.red;
			break;
		}
		repaint();
	}
	
}
