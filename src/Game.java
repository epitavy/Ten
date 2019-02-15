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
			w.update(e.getMap(), e.getActual(), e.getPlayer());
		}
	}
}