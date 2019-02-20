import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class BasicGraphics {
	Graphics g;

	
	public void drawCross(Point pos, float margin, Color cross, Color bg) {
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
	}
	
	public void drawCircle(Point pos, float margin, Color circle, Color bg) {
		g.setColor(circle);
		g.fillOval((int) (pos.x + margin), (int) (pos.y + margin), (int) (8 * margin), (int) (8 * margin));
		g.setColor(bg);
		g.fillOval((int) (pos.x + 2 * margin), (int) (pos.y + 2 * margin), (int) (6 * margin), (int) (6 * margin));
	}
	
	public void drawTie(Point pos, float margin, Color tie, Color bg) {
		g.setColor(tie);
		g.fillRoundRect((int) (pos.x + margin), (int) (pos.y + 4 * margin), (int) (8 * margin), (int) (2 * margin),
				(int) margin, (int) margin);
	}
	
	public void drawRoundSquare(Point p, float length, float arc, Color c) {
		g.setColor(c);
		g.drawRoundRect(p.x, p.y, (int)length, (int)length, (int)arc, (int)arc);
	}
	
	public void fillRoundSquare(Point p, float length, float arc, Color c) {
		g.setColor(c);
		g.fillRoundRect(p.x, p.y, (int)length, (int)length, (int)arc, (int)arc);
	}
	
	public void drawRect(Point p, float width, float height, Color c) {
		g.setColor(c);
		g.drawRect(p.x, p.y, (int)width, (int)height);
	}
	
	public void fillRect(Point p, float width, float height, Color c) {
		g.setColor(c);
		g.fillRect(p.x, p.y, (int)width, (int)height);
	}

	public void drawBorderedRect(Point p, float width, float height, float border, Color bgColor, Color borderColor) {
		g.setColor(borderColor);
		g.fillRoundRect(p.x, p.y, (int)width, (int)height, (int)(width / 10), (int)(height / 10));
		g.setColor(bgColor);
		g.fillRoundRect((int)(p.x + border), (int)(p.y + border), (int)(width - 2 * border), (int)(height - 2 * border), (int)(width / 10), (int)(height / 10));
		
	}

	public void fillRoundRect(Point p, float width, float height, float arc, Color c) {
		g.setColor(c);
		g.fillRoundRect(p.x, p.y, (int)width, (int)height, (int)arc, (int)arc);
	}
	
	public void drawCenteredString(String s, int x, int y, Color c, Font f) {
		FontMetrics metrics = g.getFontMetrics(f);
	    // Determine the X coordinate for the text
	    x -= metrics.stringWidth(s) / 2;
	    g.setFont(f);
	    g.setColor(c);
	    g.drawString(s, x, y);
	}
	
	
}
