public class Engine {
	private Tile board;
/*	private Point[] state;
	private int index;*/
	private Tile actual;
	private Player p;
	private String save;
//	private int level;
	private boolean win;

	public Engine() {
//		this.level = 2;
		this.board = new Tile();
/*		this.state = new Point[this.level];
		this.index = 0;
		for(int i = 0; i < level; i++)
			state[i] = null;*/
		this.actual = this.board;
		this.save = "";
		this.p = Player.CROSS;
	}

	public int getState() {
//		return level - index;
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
		if(p == Player.CROSS) {
			p = Player.CIRCLE;
		} else {
			p = Player.CROSS;
		}
	}

	public void run(Point selected, boolean press) {
		actual.select(selected);
		if (press) {
			Tile temp = actual;
			if(Point.isValid(selected) && actual.getCell(selected).is_free()) {
				actual = actual.getCell(selected);
				if(actual.insert(p) == 1) {
					temp.select(null);
					if(temp.win(p, selected))
						win = board.win(p, board.getSelected());
						board.select(null);
					inverse();
					if(board.getCell(selected).is_free()) {
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
