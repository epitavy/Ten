import javax.swing.JComponent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;

public class GButton extends JComponent implements MouseListener, MouseMotionListener{
	
	private ActionButton onClick;
	private BasicGraphics basicG;
	
	private int width, height, border;
	
	private ButtonType type;
	private Color bgColor;
	private Color fgColor;
	private String label;
	private Font font;
	private GTopBar gtbar;
	
	
	private boolean mouseEntered;
	private boolean mousePressed;
	
	public GButton(ActionButton onClick, GTopBar gtbar) {
		this(100, 100, onClick, gtbar);
	}
	
	public GButton(int width, int height, ActionButton onClick, GTopBar gtbar){
		this.onClick = onClick;
		this.width = width;
		this.height = height;
		this.border = width > height ? height / 20 : width / 20;
		this.gtbar = gtbar;
		
		enableInputMethods(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		//Default setting for button
		type = ButtonType.RECTANGLE;
		bgColor = new Color(150, 150, 150);
		fgColor = Color.black;
		label = "Click me";
		font = new Font("Serif", Font.BOLD, 18);
		
		basicG = new BasicGraphics();
	}
	
	public void triggerIfClicked() {
		if(mousePressed && mouseEntered) {
			onClick.triggerAction(gtbar);
		}
	}
	
	public void paintComponent(Graphics g) {
		basicG.g = g;
		if(mouseEntered)
			basicG.fillRoundRect(new Point(), width, height, border, bgColor);
		else
			basicG.fillRoundRect(new Point(border, border), (float)(width - 2 * border), (float)(height - 2 * border), (float)width / 10, bgColor);
		basicG.drawCenteredString(label, this.getWidth() / 2, this.getHeight() / 2, fgColor, font);
		
	}
	
	public void setBackground(Color c) {
		bgColor = c;
	}
	
	public void setForegroundColor(Color c) {
		fgColor = c;
	}
	
	public void setText(String s) {
		label = s;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		mouseEntered = true;
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		mouseEntered = false;
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		mousePressed = true;
		triggerIfClicked();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		mousePressed = false;
	}
	
	public Dimension getPreferredSize()
    {
        return new Dimension(width, height);
    }
	
    public Dimension getMinimumSize()
    {
        return new Dimension(10, 10);
    }
    
    public Dimension getMaximumSize()
    {
        return new Dimension(1000, 800);
    }
}
