public class Engine {
	private Tile board;
	private Tile actual;
	private Player p;
	private String save;
	private boolean win;
	private Point[] last;

	public Engine() {
		this.board = new Tile();
		this.last = new Point[2];
		for(int i = 0; i < 2; i++)
			last[i] = null;
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
					updateLast();
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

	public void updateLast()
	{
		if (last[0] == null)
		{
			last[0] = new Point(board.getSelected());
		} else {
			last[0].copy(board.getSelected());
		}
		if (last[1] == null)
		{
			last[1] = new Point(board.getCell(board.getSelected()).getSelected());
		} else {
			last[1].copy(board.getCell(board.getSelected()).getSelected());
		}
	}
}
