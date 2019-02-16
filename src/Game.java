import java.util.Scanner;

public class Game {
	Engine e;
	Window w;
	Point p;
	boolean clic;

	public Game() {
		this.e = new Engine();
		w = new Window();
		p = new Point();
	}

	void execute() {
		while (!e.isWin()) {
			clic = w.getInput(p);
			e.run(p, clic);
			w.update(e.getMap(), e.getActual(), e.getPlayer(), e.getLast());
		}
		printWinner(e.getWinner());
	}
	
	void printWinner(Flag f) {
		switch (f) {
		case TIE:
			System.out.println("It's a Tie");
			break;
		case CROSS:
			System.out.println("The Crosses win this");
			break;
		case CIRCLE:
			System.out.println("The Circles win this");
			break;
		default:
			break;
		}
	}
}