import javax.swing.JComponent;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;

public class GButton extends JComponent implements MouseListener, MouseMotionListener{
	
	private ActionButton onClick;
	private BasicGraphics basicG;
	
	private int width, height, border;
	
	ButtonType type;
	Color bgColor;
	Color fgColor;
	Color hlColor;
	JLabel label;
	
	
	private boolean mouseEntered;
	private boolean mousePressed;
	
	public GButton(ActionButton onClick) {
		this(100, 100, onClick);
	}
	
	public GButton(int width, int height, ActionButton onClick){
		this.onClick = onClick;
		this.width = width;
		this.height = height;
		this.border = width > height ? height / 10 : width / 10;
		
		enableInputMethods(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		//Default setting for button
		type = ButtonType.RECTANGLE;
		bgColor = new Color(150, 150, 150);
		fgColor = Color.black;
		hlColor = new Color(100, 255, 50);
		label = new JLabel("Click me");
		label.setForeground(fgColor);
		this.add(label);
		
		basicG = new BasicGraphics();
	}
	
	public void triggerIfClicked() {
		if(mousePressed && mouseEntered) {
			onClick.triggerAction();
		}
	}
	
	public void paintComponent(Graphics g) {
		basicG.g = g;
		if(mouseEntered)
			basicG.drawBorderedRect(new Point(), width, height, border, bgColor, hlColor);
		else
			basicG.fillRoundRect(new Point(border, border), (float)(width - 2 * border), (float)(height - 2 * border), (float)width / 10, bgColor);
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
