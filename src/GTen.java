import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class GTen extends JPanel {

	private Tile board;
	private Player player;
	private Mouse mouse;
	private BasicGraphics basicG;
	
	final float zoom = 0.15f;
	
	private Color colorBg;

	public GTen() {
		basicG = new BasicGraphics();
		mouse = new Mouse();
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
	}

	public void update(Tile board, Player p) {
		this.board = board;
		this.player = p;
		repaint();
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
		drawBoard(g, this.board, point, maxDim - 2 * margin, false);
	}


	private void drawBoard(Graphics g, Tile cell, Point pos, float length, boolean select) {
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
			drawSymbol(cell.getFlag(), pos, length);
		} else if (f != Flag.EMPTY) {
			float sublength = length * 30 / 100;

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					float posx = pos.x + (i + 1) * cellspacing + i * sublength;
					float posy = pos.y + (j + 1) * cellspacing + j * sublength;
					if (cell.getSelected() != null && cell.getSelected().x == i && cell.getSelected().y == j) {
						drawBoard(g, cell.getCell(new Point(i, j)), new Point((int) posx, (int) posy), sublength, true);
					} else {
						drawBoard(g, cell.getCell(new Point(i, j)), new Point((int) posx, (int) posy), sublength,
								false);
					}
				}
			}
		}
	}

	private void drawSymbol(Flag f, Point pos, float length) {

		float margin = length / 10;
		if (f == Flag.CIRCLE) {
			basicG.drawCircle(pos, margin, TenColors.circle, colorBg);
		} else if (f == Flag.CROSS) {
			basicG.drawCross(pos, margin, TenColors.cross, colorBg);
		} else if (f == Flag.TIE) {
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
