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

		float length = maxDim - 2 * margin;
		int marginx = (int) ((this.getWidth() - length) / 2);
		int marginy = (int) ((this.getHeight() - length) / 2);
		Point point = new Point(marginx, marginy);
		drawBoard(g, this.board, point, length, false, false);
	}


	private void drawBoard(Graphics g, Tile cell, Point pos, float length, boolean select, boolean isPrevious) {
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
			drawSymbol(cell.getFlag(), pos, length, isPrevious);
		} else if (f != Flag.EMPTY) {
			float sublength = length * 30 / 100;

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					float posx = pos.x + (i + 1) * cellspacing + i * sublength;
					float posy = pos.y + (j + 1) * cellspacing + j * sublength;
					
					boolean selected = isSelected(cell, i, j);
					boolean isPrev = isPreviousMove(cell, this.previous, isPrevious, i, j);

					drawBoard(g, cell.getCell(new Point(i, j)), new Point((int) posx, (int) posy), sublength,
							selected, isPrev);
				}
			}
		}
	}
	
	private boolean isSelected(Tile cell, int i, int j) {
		return cell.getSelected() != null && cell.getSelected().x == i && cell.getSelected().y == j;
	}
	
	private boolean isPreviousMove(Tile cell, Point[] prev, boolean isPrevious, int i, int j) {
		if(prev != null && prev[0] != null) {
			if(cell.getLevel() == 2 && prev[0].x == i && prev[0].y == j)
				return true;
			else if(isPrevious && cell.getLevel() == 1 && prev[1].x == i && prev[1].y == j)
				return true;
		}
		return false;
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
		float length = maxDim - 2 * margin;
		int marginx = (int) ((this.getWidth() - length) / 2);
		int marginy = (int) ((this.getHeight() - length) / 2);
		maxDim -= 2 * margin;
		int x = mouse.pos.x - marginx;
		int y = mouse.pos.y - marginy;
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
