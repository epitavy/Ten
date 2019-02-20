public class Game {
	Engine e;
	Window w;
	Point p;
	boolean clic;
	boolean stop;
	Player firstPlayer;

	public Game() {
		this.e = new Engine(null);
		this.firstPlayer = e.getPlayer();
		w = new Window();
		p = new Point();
		stop = false;
	}

	void execute() {
		do {
			while (!e.isWin()) {
				clic = w.getInput(p);
				e.run(p, clic);
				w.update(e.getMap(), e.getActual(), e.getPlayer(), e.getLast());
				//break;
			}
			w.ends(e.getWinner());
			//stop = true;
			
			
		} while (!stop);
		//ici faut faire fermer la fenêtre
		//w.dispatchEvent(new WindowEvent(w, WindowEvent.WINDOW_CLOSING));
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