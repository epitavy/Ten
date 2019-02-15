import java.util.Scanner;

public class Game {
	Engine e;
	Window w;

	public Game() {
		this.e = new Engine();
		w = new Window();
	}

	void execute() {
		while (!e.isWin()) {
			Point pos = new Point(0, 0);
			e.run(pos, false);
//			update()
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