import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class GTen extends JPanel {

	private Tile board;
	private Player player;
	private Point[] previous;
	private Mouse mouse;
	private BasicGraphics basicG;
	
	final float zoom = 0.15f;
	
	private Color colorBg;

	public GTen() {
		basicG = new BasicGraphics();
		mouse = new Mouse();
		previous = null;
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
	}

	public void update(Tile board, Player p) {
		this.board = board;
		this.player = p;
		repaint();
	}
	
	public void update(Tile board, Player p, Point[] previous) {
		this.previous = previous;
		update(board, p);
	}
	
	public void paintComponent(Graphics g) {
		if (this.board == null)
			return;
		
		this.basicG.g = g;
		// Force drawing in a square
		int maxDim = this.getWidth() > this.getHeight() ? this.getHeight() : this.getWidth();

		int margin = maxDim / 10;
		
		if(this.player == Player.CIRCLE)
			colorBg = TenColors.circleBg;
		else
			colorBg = TenColors.crossBg;
		
		//Draw uniform overriding background all over the panel
		basicG.fillRect(new Point(), this.getWidth(), this.getHeight(), colorBg);

		Point point = new Point(margin, margin);
		drawBoard(g, this.board, point, maxDim - 2 * margin, false, false);
	}


	private void drawBoard(Graphics g, Tile cell, Point pos, float length, boolean select, boolean previous) {
		// General cell border
		float cellspacing = length / 30;
		
		//Make the cell bigger if selected
		if(select) {
			pos.x -= length * zoom / 2;
			pos.y -= length * zoom / 2;
			length *= (1 + zoom);
		}	

		if (cell.getLevel() == 0)
			basicG.drawRoundSquare(pos, length, length / 10, TenColors.black);

		Flag f = cell.getFlag();
		if (f != Flag.BOARD && f != Flag.EMPTY) {
			drawSymbol(cell.getFlag(), pos, length, previous);
		} else if (f != Flag.EMPTY) {
			float sublength = length * 30 / 100;

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					float posx = pos.x + (i + 1) * cellspacing + i * sublength;
					float posy = pos.y + (j + 1) * cellspacing + j * sublength;
					
					boolean selected = false;
					boolean isPreviousMove = false;
					if (cell.getSelected() != null && cell.getSelected().x == i && cell.getSelected().y == j)
						selected = true;
					if(this.previous != null && this.previous[0] != null) {
						if(cell.getLevel() == 2 && this.previous[0].x == i && this.previous[0].y == j)
							isPreviousMove = true;
						else if(previous && cell.getLevel() == 1 && this.previous[1].x == i && this.previous[1].y == j)
							isPreviousMove = true;
					}
					
					drawBoard(g, cell.getCell(new Point(i, j)), new Point((int) posx, (int) posy), sublength,
							selected, isPreviousMove);
				}
			}
		}
	}

	private void drawSymbol(Flag f, Point pos, float length, boolean previous) {

		float margin = length / 10;
		if (f == Flag.CIRCLE) {
			if(previous)
				basicG.drawCircle(pos, margin, TenColors.circleLast, colorBg);
			else
				basicG.drawCircle(pos, margin, TenColors.circle, colorBg);
		} else if (f == Flag.CROSS) {
			if(previous)
				basicG.drawCross(pos, margin, TenColors.crossLast, colorBg);
			else
				basicG.drawCross(pos, margin, TenColors.cross, colorBg);
		} else if (f == Flag.TIE) {
			//Flag TIE and previous set to true should never happen!
			basicG.drawTie(pos, margin, TenColors.tie, colorBg);
		}
	}

	public Point getCellOn() {
		int maxDim = this.getWidth() > this.getHeight() ? this.getHeight() : this.getWidth();
		int margin = maxDim / 10;
		maxDim -= 2 * margin;
		int x = mouse.pos.x - margin;
		int y = mouse.pos.y - margin;
		if(x < 0 || y < 0)
			return null;
		return new Point(x * 3 / maxDim, y * 3 / maxDim);
	}

	public boolean isClicked() {
		if (mouse.clickedLeft) {
			mouse.clickedLeft = false;
			return true;
		}
		return false;
	}
}
