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
		
		//Set color according to player color
		basicG.setBgColor(this.player);
		
		//Draw uniform overriding background all over the panel
		basicG.fillRect(new Point(), this.getWidth(), this.getHeight(), ColorType.BACKGROUND);

		Point point = new Point(margin, margin);
		drawBoard(g, this.board, point, maxDim - 2 * margin, false);
	}


	private void drawBoard(Graphics g, Tile cell, Point pos, float length, boolean select) {
		// General cell border
		float cellspacing = length / 40;

		if (cell.getLevel() == 0) {
			basicG.drawRoundSquare(pos, length, length / 10, ColorType.BLACK);
		}

		Flag f = cell.getFlag();
		if (f != Flag.BOARD && f != Flag.EMPTY) {
			/*// Make the background grey
			g.setColor(colorBg);
			g.fillRoundRect(pos.x + 1, pos.y + 1, (int) length - 2, (int) length - 2, (int) (length / 10),
					(int) (length / 10));
			basicG.fillRoundSquare(pos, length - 2, length / 10, ColorType.BACKGROUND);*/
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
		if (select) {
			pos.x -= cellspacing;
			pos.y -= cellspacing;
			basicG.fillRoundSquare(pos, length + 2 * cellspacing, cellspacing, ColorType.SELECTED);
		}
	}

	private void drawSymbol(Flag f, Point pos, float length) {

		float margin = length / 10;
		if (f == Flag.CIRCLE) {
			basicG.drawCircle(pos, margin);
		} else if (f == Flag.CROSS) {
			basicG.drawCross(pos, margin);
		} else if (f == Flag.TIE) {
			basicG.drawTie(pos, margin);
		}
	}

	public Point getCellOn() {
		int maxDim = this.getWidth() > this.getHeight() ? this.getHeight() : this.getWidth();
		int margin = maxDim / 10;
		maxDim -= 2 * margin;
		return new Point(((mouse.pos.x - margin) * 3) / maxDim, ((mouse.pos.y - margin) * 3) / maxDim);
	}

	public boolean isClicked() {
		if (mouse.clickedLeft) {
			mouse.clickedLeft = false;
			return true;
		}
		return false;
	}
}
