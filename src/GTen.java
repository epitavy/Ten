import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class GTen extends JPanel{

	private int level;
	private Tile board;
	
	private int mousePosX;
	private int mousePosY;
	private boolean clicked = false;
	
	private Color greyBg = new Color(200, 200, 200);
	private Color circle = Color.red;
	private Color cross = Color.blue;
	private Color tie = new Color(50, 200, 0);
	private Color black = Color.black;
	private Color selected = new Color(255, 100, 200, 200);
	
	public GTen() {
		this.addMouseListener(new MouseAdapter(){
		      public void mouseClicked(MouseEvent e){
		    	  mousePosX = e.getX();
		    	  mousePosY = e.getY();
		    	  clicked = true;
		      }
		    });
		this.addMouseMotionListener(new MouseAdapter(){
		      public void mouseMoved(MouseEvent e){
		    	  mousePosX = e.getX();
		    	  mousePosY = e.getY();
		      }
		    });
	}
	
	public void paintComponent(Graphics g) {
		if(board == null)
			return;
		//Force drawing in a square
		int maxDim = this.getWidth() > this.getHeight() ? this.getHeight() : this.getWidth();
		
		int margin = maxDim / 10;
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		Point point = new Point(margin, margin);
		drawBoard(g, this.board, point, maxDim - 2 * margin);
	}
	
	public void update(Tile board, int level) {
		this.level = level;
		this.board = board;
		repaint();
	}
	
	private void drawBoard(Graphics g, Tile cell, Point pos, float length) {
		//General cell border
		if(cell.getLevel() != 1) {
			g.setColor(black);
			g.drawRoundRect(pos.x, pos.y, (int)length, (int)length, (int)(length / 10), (int)(length / 10));
		}
		
		
		Flag f = cell.getFlag();
		if(f == Flag.EMPTY)
			return;
		else if(f != Flag.EMPTY && f != Flag.BOARD) {
			//Make the background grey
			g.setColor(greyBg);
			g.fillRoundRect(pos.x + 1, pos.y + 1, (int)length - 2, (int)length - 2, (int)(length / 10), (int)(length / 10));
			drawSymbol(g, cell.getFlag(), pos, length);
		}
		else if(f == Flag.BOARD) {
			float cellspacing = length / 40;
			float sublength = length * 30 / 100;
			
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(cell.getSelected() != null && cell.getSelected().x == i && cell.getSelected().y == j) {
						g.setColor(selected);
						g.fillRoundRect((int)(pos.x - cellspacing), (int)(pos.y - cellspacing), (int)(length + 2 * cellspacing), (int)(length + 2 * cellspacing),
								(int)cellspacing, (int)cellspacing);
						g.setColor(Color.white);
						g.fillRoundRect(pos.x + 1, pos.y + 1, (int)length - 2, (int)length - 2, (int)(length / 10), (int)(length / 10));
						
					}
					float posx = pos.x + (i + 1) * cellspacing + i * sublength;
					float posy = pos.y + (j + 1) * cellspacing + j * sublength;
					drawBoard(g, cell.getBoard()[i][j],new Point((int)posx, (int)posy), sublength);
				}
			}
		}
	}
	
	private void drawSymbol(Graphics g, Flag f, Point pos, float length) {
		
		float margin = length / 10;
		if(f == Flag.CIRCLE) {
			g.setColor(circle);
			g.fillOval((int)(pos.x + margin), (int)(pos.y + margin), (int)(8 * margin), (int)(8 * margin));
			g.setColor(greyBg);
			g.fillOval((int)(pos.x + 2 * margin), (int)(pos.y + 2 * margin), (int)(6 * margin), (int)(6 * margin));
		}
		else if(f == Flag.CROSS) {
			g.setColor(cross);
			margin /= 2;
			int[] xPoints1 = {(int)(pos.x + margin), (int)(pos.x + 3 * margin), (int)(pos.x + 19 * margin), (int)(pos.x + 17 * margin)};
			int[] yPoints1 = {(int)(pos.y + 3 * margin), (int)(pos.y + margin), (int)(pos.y + 17 * margin), (int)(pos.y + 19 * margin)};
			int[] xPoints2 = {(int)(pos.x + 17 * margin), (int)(pos.x + 19 * margin), (int)(pos.x + 3 * margin), (int)(pos.x + margin)};
			int[] yPoints2 = {(int)(pos.y + margin), (int)(pos.y + 3 * margin), (int)(pos.y + 19 * margin), (int)(pos.y + 17 * margin)};
			g.fillPolygon(xPoints1, yPoints1, 4);
			g.fillPolygon(xPoints2, yPoints2, 4);		
		}
		else if(f == Flag.TIE) {
			g.setColor(tie);
			g.fillRoundRect((int)(pos.x + margin), (int)(pos.y + 4 * margin), (int)(8 * margin), (int)(2 * margin), (int)margin, (int)margin);
		}
	}
	
	public Point getCellOn() {
		int maxDim = this.getWidth() > this.getHeight() ? this.getHeight() : this.getWidth();
		int margin = maxDim / 10;
		return new Point((this.mousePosX - margin) / 3, (this.mousePosY - margin) / 3);
	}
	
	public boolean isClicked() {
		return clicked;
	}
}
