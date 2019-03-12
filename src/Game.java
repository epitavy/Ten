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
	
	/**
	 * Use only for genetics algorithme
	 * return the flag of winning ia
	 */
	public Flag iaVSia(IA ia1, IA ia2) {
		instancyEngine();
		ia1.realGame = e;
		ia2.realGame = e;
		if(ia1.player == ia2.player)
			return Flag.TIE;
		e.setPlayer(ia1.player);
		while (!e.isWin()) {
			if(e.getPlayer() == ia1.player)
				ia1.play();
			else
				ia2.play();
		}
		return e.getWinner();
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