public class Game {
	Tile t;
	Player p;
	String save;
	int state;
	Window w;

	public Game() {
		this.t = new Tile();
		this.p = Player.CROSS;
		this.save = "";
		this.state = 2;
		w = new Window();
	}

	void execute() {
		while (t.getFlag() == Flag.BOARD) {
			// TODO
		}
	}
}