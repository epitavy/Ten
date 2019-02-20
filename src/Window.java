import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class Window extends JFrame {
	
	private GTen game;
	private GTopBar bar;
	private GTen map;
	private GMenu menu;
	final int width = 1200;
	final int height = 900;
	

	public Window() {
		this.setTitle("Ten");
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Panels for game
		game = new GTen();
		game.setBackground(Color.red);
		map = new GTen();
		map.setPreferredSize(new Dimension(width / 3, 0));
		map.setBackground(Color.blue);
		bar = new GTopBar();
		bar.setPreferredSize(new Dimension(0, height / 9));
		bar.setBackground(Color.green);
		
		//Panel for menu
		/*menu = new GMenu();
		menu.setPreferredSize(new Dimension(110, 100));
		menu.setBackground(Color.orange);
		this.getContentPane().add(menu, BorderLayout.CENTER);*/
		
		
		this.setLayout(new BorderLayout());
		
		this.getContentPane().add(game, BorderLayout.CENTER);
		this.getContentPane().add(bar, BorderLayout.NORTH);
		this.getContentPane().add(map, BorderLayout.EAST);
			
		this.setVisible(true);
	}
	
	public void update(Tile board, Tile actual, Player p, Point[] last) {
		boolean unused = board.equals(actual);
		game.update(actual, p, unused);
		map.update(board, p, last, unused);
	}
	
	public boolean getInput(Point p) {
		Point temp = game.getCellOn();
		if(temp == null) {
			p.change(-1, -1);
		} else {
			p.copy(temp);
		}
		return game.isClicked();
	}
	
	public void ends(Flag winner) {
		game.drawEnd(winner);
		map.setBackgroundEnd(winner);
		bar.drawEnd(winner);
	}
}
