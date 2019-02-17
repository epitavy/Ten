import javax.swing.JComponent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;

public class GButton extends JComponent implements MouseListener, MouseMotionListener{
	
	private ActionButton onClick;
	private BasicGraphics basicG;
	
	private Point pos;
	private int width, height;
	
	private boolean mouseEntered;
	private boolean mousePressed;
	
	
	public GButton(Point pos, int width, int height){
		this.width = width;
		this.height = height;
		this.pos = pos;
		
		enableInputMethods(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		basicG = new BasicGraphics();
	}
	
	public void triggerIfClicked() {
		if(mousePressed && mouseEntered) {
			onClick.triggerAction();
		}
	}
	
	public void paintComponent(Graphics g) {
		System.out.println("Before instanciation");
		basicG.g = g;
		System.out.println("After instanciation");
		basicG.fillRoundSquare(new Point(), width, width / 10, Color.black);
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
