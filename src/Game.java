public class Game {
	Engine e;
	Window w;
	Point p;
	boolean clic;
	boolean replay;
	Player nextFirstPlayer;

	public Game() {
		this.nextFirstPlayer = null;
		this.w = new Window();
		this.p = new Point();
		this.replay = false;
	}

	void execute() {
		do {
			instancyEngine();
			while (!e.isWin()) {
				clic = w.getInput(p);
				e.run(p, clic);
				w.update(e.getMap(), e.getActual(), e.getPlayer(), e.getLast());
			}
			replay = w.ends(e.getWinner());
			
		} while (replay);
		w.dispose();
	}
	
	private void instancyEngine() {
		this.e = new Engine(this.nextFirstPlayer);
		if (e.getPlayer() == Player.CIRCLE) {
			nextFirstPlayer = Player.CROSS;
		} else {
			nextFirstPlayer = Player.CIRCLE;
		}
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