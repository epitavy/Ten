import java.awt.Color;
import java.awt.Graphics;

public class BasicGraphics {
	Graphics g;
	private Color colorBg;
	final private Color circle = new Color(255, 0, 0);
	final private Color circleBg = new Color(255, 180, 180);
	final private Color cross = new Color(0, 0, 255);
	final private Color crossBg = new Color(180, 180, 255);
	final private Color tie = new Color(30, 150, 10);
	final private Color black = Color.black;
	final private Color selected = new Color(255, 100, 200, 200);

	
	public void drawCross(Point pos, float margin) {
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
	
	public void drawCircle(Point pos, float margin) {
		g.setColor(circle);
		g.fillOval((int) (pos.x + margin), (int) (pos.y + margin), (int) (8 * margin), (int) (8 * margin));
		g.setColor(colorBg);
		g.fillOval((int) (pos.x + 2 * margin), (int) (pos.y + 2 * margin), (int) (6 * margin), (int) (6 * margin));
	}
	
	public void drawTie(Point pos, float margin) {
		g.setColor(tie);
		g.fillRoundRect((int) (pos.x + margin), (int) (pos.y + 4 * margin), (int) (8 * margin), (int) (2 * margin),
				(int) margin, (int) margin);
	}
	
	public void drawRoundSquare(Point p, float length, float arc, ColorType cType) {
		g.setColor(getColor(cType));
		g.drawRoundRect(p.x, p.y, (int)length, (int)length, (int)arc, (int)arc);
	}
	
	public void fillRoundSquare(Point p, float length, float arc, ColorType cType) {
		g.setColor(getColor(cType));
		g.fillRoundRect(p.x, p.y, (int)length, (int)length, (int)arc, (int)arc);
	}
	
	public void drawRect(Point p, float width, float height, ColorType cType) {
		g.setColor(getColor(cType));
		g.drawRect(p.x, p.y, (int)width, (int)height);
	}
	
	public void fillRect(Point p, float width, float height, ColorType cType) {
		g.setColor(getColor(cType));
		g.fillRect(p.x, p.y, (int)width, (int)height);
	}
	
	private Color getColor(ColorType cType) {
		switch(cType) {
		case BACKGROUND:
			return colorBg;
		case BLACK:
			return black;
		case SELECTED:
			return selected;
		default:
			return black;
		}
	}
	public void setBgColor(Player p) {
		if (p == Player.CIRCLE)
			colorBg = circleBg;
		else
			colorBg = crossBg;
	}
	
	
}
