import javax.swing.JFrame;

public class Window extends JFrame{
	private GTen pan;
	
	public Window(){
	    this.setTitle("Ten");
	    this.setSize(700, 700);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    pan = new GTen();
	    this.setContentPane(pan);
	    this.setVisible(true);
	}
}
