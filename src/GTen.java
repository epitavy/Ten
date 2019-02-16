import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class GTen extends JPanel {

	private Tile board;
	private Player player;
	private Mouse mouse;

	private Color colorBg;
	final private Color circle = new Color(255, 0, 0);
	final private Color circleBg = new Color(255, 180, 180);
	final private Color cross = new Color(0, 0, 255);
	final private Color crossBg = new Color(180, 180, 255);
	final private Color tie = new Color(30, 150, 10);
	final private Color black = Color.black;
	final private Color selected = new Color(255, 100, 200, 200);

	public GTen() {
		mouse = new Mouse();
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
	}

	public void paintComponent(Graphics g) {
		if (board == null)
			return;
		// Force drawing in a square
		int maxDim = this.getWidth() > this.getHeight() ? this.getHeight() : this.getWidth();

		int margin = maxDim / 10;
		if (this.player == Player.CIRCLE)
			colorBg = circleBg;
		else
			colorBg = crossBg;
		g.setColor(colorBg);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		Point point = new Point(margin, margin);
		drawBoard(g, this.board, point, maxDim - 2 * margin, false);
	}

	public void update(Tile board, Player p) {
		this.board = board;
		this.player = p;
		repaint();
	}

	private void drawBoard(Graphics g, Tile cell, Point pos, float length, boolean select) {
		// General cell border
		float cellspacing = length / 40;

		if (cell.getLevel() == 0) {
			g.setColor(black);
			g.drawRoundRect(pos.x, pos.y, (int) length, (int) length, (int) (length / 10), (int) (length / 10));
		}

		Flag f = cell.getFlag();
		if (f != Flag.BOARD && f != Flag.EMPTY) {
			// Make the background grey
			g.setColor(colorBg);
			g.fillRoundRect(pos.x + 1, pos.y + 1, (int) length - 2, (int) length - 2, (int) (length / 10),
					(int) (length / 10));
			drawSymbol(g, cell.getFlag(), pos, length);
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
			g.setColor(this.selected);
			g.fillRoundRect((int) (pos.x - cellspacing), (int) (pos.y - cellspacing), (int) (length + 2 * cellspacing),
					(int) (length + 2 * cellspacing), (int) cellspacing, (int) cellspacing);
		}
	}

	private void drawSymbol(Graphics g, Flag f, Point pos, float length) {

		float margin = length / 10;
		if (f == Flag.CIRCLE) {
			g.setColor(circle);
			g.fillOval((int) (pos.x + margin), (int) (pos.y + margin), (int) (8 * margin), (int) (8 * margin));
			g.setColor(colorBg);
			g.fillOval((int) (pos.x + 2 * margin), (int) (pos.y + 2 * margin), (int) (6 * margin), (int) (6 * margin));
		} else if (f == Flag.CROSS) {
			g.setColor(cross);
			margin /= 2;
			int[] xPoints1 = { (int) (pos.x + margin), (int) (pos.x + 3 * margin), (int) (pos.x + 19 * margin),
					(int) (pos.x + 17 * margin) };
			int[] yPoints1 = { (int) (pos.y + 3 * margin), (int) (pos.y + margin), (int) (pos.y + 17 * margin),
					(int) (pos.y + 19 * margin) };
			int[] xPoints2 = { (int) (pos.x + 17 * margin), (int) (pos.x + 19 * margin), (int) (pos.x + 3 * margin),
					(int) (pos.x + margin) };
			int[] yPoints2 = { (int) (pos.y + margin), (int) (pos.y + 3 * margin), (int) (pos.y + 19 * margin),
					(int) (pos.y + 17 * margin) };
			g.fillPolygon(xPoints1, yPoints1, 4);
			g.fillPolygon(xPoints2, yPoints2, 4);
		} else if (f == Flag.TIE) {
			g.setColor(tie);
			g.fillRoundRect((int) (pos.x + margin), (int) (pos.y + 4 * margin), (int) (8 * margin), (int) (2 * margin),
					(int) margin, (int) margin);
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
