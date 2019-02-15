public class Engine {
	private Tile board;
	private Tile actual;
	private Player p;
	private String save;
	private boolean win;

	public Engine() {
		this.board = new Tile();

		this.actual = this.board;
		this.save = "";
		this.p = Player.CROSS;
	}

	public int getState() {
		return actual.getLevel();
	}

	public Tile getMap() {
		return board;
	}

	public Player getPlayer() {
		return p;
	}

	public String getSave() {
		return save;
	}

	public Flag getFlag() {
		return board.getFlag();
	}

	public Tile getActual() {
		return actual;
	}

	public boolean isWin() {
		return win;
	}

	private void inverse() {
		if (p == Player.CROSS) {
			p = Player.CIRCLE;
		} else {
			p = Player.CROSS;
		}
	}

	public void run(Point selected, boolean press) {
		actual.select(selected);
		if (press) {
			Tile temp = actual;
			if (Point.isValid(selected) && actual.getCell(selected).is_free()) {
				actual = actual.getCell(selected);
				if (actual.insert(p) == 1) {
					temp.incrementNum();
					temp.select(null);
					if (temp.winOrTie(p, selected))
						win = board.winOrTie(p, board.getSelected());
					board.select(null);
					inverse();
					if (board.getCell(selected).is_free()) {
						board.select(selected);
						actual = board.getCell(selected);
					} else {
						board.select(null);
						actual = board;
					}
				}
			}
		}
	}
}
