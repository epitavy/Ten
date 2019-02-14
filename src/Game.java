public class Game {
	Tile t;
	int player;
	String save;
	int state;
	Window w;

	public Game() {
		this.t = new Tile();
		this.player = 1;
		this.save = "";
		this.state = 2;
		w = new Window();
	}

	void execute() {
		while (t.type == Flag.BOARD) {
			// TODO
		}
	}
}
