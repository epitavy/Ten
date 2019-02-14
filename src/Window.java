import javax.swing.JFrame;

public class Window extends JFrame{
	private GTen pan;
	
	public Window(){
	    this.setTitle("Ten");
	    this.setSize(500, 500);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
	    this.setContentPane(pan);
	    this.setVisible(true);
	}
	
}
