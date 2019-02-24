public class Game {
	Engine e;
	Window w;
	Point p;
	boolean clic;
	boolean replay;
	boolean playerIsIA;
	Player nextFirstPlayer;

	public Game() {
		this.nextFirstPlayer = null;
		this.w = new Window();
		this.p = new Point();
		this.replay = false;
		this.playerIsIA = true;
	}
	

	public void execute() {
		do {
			if(playerIsIA)
				replay = playerVSIA();
			else
				replay = playerVSplayer();
		} while (replay);
		w.dispose();
	}
	
	public boolean playerVSIA() {
		instancyEngine();
		IA ia = new IA(e, Player.CIRCLE);
		while (!e.isWin()) {
			
			if(e.getPlayer() == Player.CIRCLE)
				ia.play();
			else {
				clic = w.getInput(p);
				e.run(p, clic);
			}
			
			w.update(e.getMap(), e.getActual(), e.getPlayer(), e.getLast());
		}
		return w.ends(e.getWinner());
	}
	
	public boolean playerVSplayer() {
		instancyEngine();
		while (!e.isWin()) {
			clic = w.getInput(p);
			e.run(p, clic);
			w.update(e.getMap(), e.getActual(), e.getPlayer(), e.getLast());
		}
		return w.ends(e.getWinner());
	}
	private void instancyEngine() {
		this.e = new Engine(this.nextFirstPlayer);
		if (e.getPlayer() == Player.CIRCLE) {
			nextFirstPlayer = Player.CROSS;
		} else {
			nextFirstPlayer = Player.CIRCLE;
		}
	}
}