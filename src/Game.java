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
			w.update(e.getMap(), e.getActual());
		}
	}
	
	void display(Point[] game, int size) {
		for(int i = 0; i < size; i++) {
			e.run(game[i], true);
			e.getMap().draw();
			System.out.println("-------------------------------------------------");
		}
		
	}
	void display() {
		Scanner S = new Scanner(System.in);
		int x = 0;
		int y = 0;
		Point p;
		e.getMap().draw();
		System.out.println("-------------------------------------------------");
		System.out.println("turn to : " + e.getPlayer());
		while(!e.isWin()) {
			System.out.println("x : ");
			x = S.nextInt();
			System.out.println("y : ");
			y = S.nextInt();
			p = new Point(x, y);
			e.run(p, true);
			e.getMap().draw();
			System.out.println("-------------------------------------------------");
			System.out.println("turn to : " + e.getPlayer());
		}
		
		S.close();
	}
}