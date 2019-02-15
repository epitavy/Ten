import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class Window extends JFrame {
	
	private GTen game;
	private GTopBar bar;
	private GTen map;
	final int width = 1200;
	final int height = 900;
	

	public Window() {
		this.setTitle("Ten");
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		game = new GTen();
		game.setBackground(Color.red);
		map = new GTen();
		map.setPreferredSize(new Dimension(width / 3, 0));
		map.setBackground(Color.blue);
		bar = new GTopBar();
		bar.setPreferredSize(new Dimension(0, height / 9));
		bar.setBackground(Color.green);
		
		this.setLayout(new BorderLayout());
		
		this.getContentPane().add(game, BorderLayout.CENTER);
		this.getContentPane().add(bar, BorderLayout.NORTH);
		this.getContentPane().add(map, BorderLayout.EAST);
			
		this.setVisible(true);
	}
	
	public void update(Tile board, Tile actual, Player p) {
		game.update(actual, p);
		map.update(board, p);
		//bar.uppdate ??
	}
	
	public boolean getInput(Point p) {
		p.copy(game.getCellOn());
		return game.isClicked();
	}
}
