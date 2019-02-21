import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GTopBar extends JPanel{
	
	private BasicGraphics basicG;
	private String text;
	private Color textColor;
	private GButton replayB;
	private GButton quitB;
	
	private boolean quit = false;
	private boolean replay = false;
	
	public GTopBar() {
		ActionButton quitGame = (GTopBar gtbar) -> {gtbar.setQuit(true);};
		ActionButton replayGame = (GTopBar gtbar) -> {gtbar.setReplay(true);};
		
		replayB = new GButton(120, 100, replayGame, this);
		replayB.setText("Replay");
		quitB = new GButton(120, 100, quitGame, this);
		quitB.setText("Exit game");
		
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
			quitB.setBackground(TenColors.crossBg);
			replayB.setBackground(TenColors.circleBg);
			break;
		case CROSS:
			text = "Player cross win!";
			textColor = TenColors.crossBg;
			quitB.setBackground(TenColors.circleBg);
			replayB.setBackground(TenColors.crossBg);
			break;
		case TIE:
			text = "Nobody win! Draw game";
			textColor = TenColors.tieWin;
			quitB.setBackground(TenColors.tieWin);
			replayB.setBackground(TenColors.tieWin);
			break;
		default:
			text = "Error";
			textColor = Color.red;
			break;
		}
		
		this.setLayout(new BorderLayout());
		
		this.add(quitB, BorderLayout.EAST);
		this.add(replayB, BorderLayout.WEST);
		validate();
		repaint();
	}
	
	public void setQuit(boolean b) {
		this.quit = b;
	}
	
	public void setReplay(boolean b) {
		this.replay = b;
	}
	
	public boolean quitOrReplay() {
		while(! (this.replay || this.quit)) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		boolean ret = !quit || replay;
		quit = false;
		replay = false;
		return ret;
	}
	
}
