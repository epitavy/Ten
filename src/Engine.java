import java.util.Random;

public class Engine {
	private Tile board;
	private Tile actual;
	private Player p;
	private String save;
	private Flag winner;
	private Point[] last;

	public Engine(Player p) {
		this.board = new Tile();
		this.last = new Point[2];
		for (int i = 0; i < 2; i++)
			this.last[i] = null;
		this.actual = this.board;
		this.save = "";
		if(p != null) {
			this.p = p;
		} else {
			Random r = new Random();
			int i = r.nextInt(2);
			if (i == 0) {
				this.p = Player.CROSS;
			} else {
				this.p = Player.CIRCLE;
			}
		}
		this.winner = Flag.BOARD;
	}
	
	public Engine(Engine e) {
		this.board = new Tile(e.board);
		if(this.board.getSelected() == null)
			this.actual = this.board;
		else
			this.actual = this.board.getCell(this.board.getSelected());
		this.p = e.p;
		this.last = new Point[2];
		for (int i = 0; i < 2; i++)
			if(this.last[i] != null)
				this.last[i] = new Point(e.last[i]); 
		this.winner = e.winner;
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
	
	public Point[] getLast() {
		return last;
	}

	public boolean isWin() {
		return winner != Flag.BOARD && winner != Flag.EMPTY;
	}

	public Flag getWinner() {
		return winner;
	}

	private void inversePlayer() {
		if (p == Player.CROSS) {
			p = Player.CIRCLE;
		} else {
			p = Player.CROSS;
		}
	}

	public void run(Point selected, boolean press) {
		actual.select(selected);
		if (press) {
			// code executed only if the left button is pressed
			Tile temp = actual;
			if (Point.isValid(selected) && actual.getCell(selected).is_free()) {
				actual = actual.getCell(selected);
				if (actual.insert(p) == 1) {
					updateLast();
					temp.incrementNum();
					temp.select(null);
					computeWinning(temp.winOrTie(p, selected), temp);
					board.select(null);
					inversePlayer();
					resetActual(selected);
				}
			}
		}
	}

	private void computeWinning(Flag f, Tile temp) {
		if (f != Flag.BOARD) {
			temp.changeFlag(f);
			board.incrementNum();
			winner = board.winOrTie(p, board.getSelected());
		}
	}

	private void resetActual(Point selected) {
		if (board.getCell(selected).is_free()) {
			board.select(selected);
			actual = board.getCell(selected);
		} else if (board.getNum() == 8) {
			Point p = new Point();
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					p.change(i, j);
					if (board.getCell(p).getFlag() == Flag.BOARD)
					{
						board.select(p);
						actual = board.getCell(p);
					}
				}
			}
		} else {
			board.select(null);
			actual = board;
		}
	}

	private void updateLast() {
		if (last[0] == null) {
			last[0] = new Point(board.getSelected());
		} else {
			last[0].copy(board.getSelected());
		}
		if (last[1] == null) {
			last[1] = new Point(board.getCell(board.getSelected()).getSelected());
		} else {
			last[1].copy(board.getCell(board.getSelected()).getSelected());
		}
	}
}
