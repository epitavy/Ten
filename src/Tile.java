public class Tile {
	private Tile[][] board;
	private Flag type;
	private int level;
	private Point selected;
	private int num;

	private Tile(int level) {
		this.level = level;
		this.selected = null;
		if (level == 0) {
			this.type = Flag.EMPTY;
			this.board = null;
		} else {
			this.type = Flag.BOARD;
			this.board = new Tile[3][3];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					this.board[i][j] = new Tile(this.level - 1);
				}
			}
		}
		this.num = 0;
	}

	public Tile() {
		this.level = 2;
		this.selected = null;
		this.type = Flag.BOARD;
		this.board = new Tile[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.board[i][j] = new Tile(this.level - 1);
			}
		}
	}

	public int getNum() {
		return num;
	}

	public void incrementNum() {
		this.num++;
	}

	public Flag getFlag() {
		return type;
	}

	public Tile getCell(Point pos) {
		if (this.type == Flag.BOARD && pos != null && pos.x >= 0 && pos.x < 3 && pos.y >= 0 && pos.y < 3)
			return this.board[pos.x][pos.y];
		return null;
	}

	public int getLevel() {
		return level;
	}

	public Point getSelected() {
		return selected;
	}

	public boolean is_free() {
		return type == Flag.BOARD || type == Flag.EMPTY;
	}

	public void select(Point pos) {
		if (type == Flag.BOARD && pos != null && pos.x >= 0 && pos.x < 3 && pos.y >= 0 && pos.y < 3) {
			if (this.selected == null) {
				this.selected = new Point(pos);
			} else {
				this.selected.copy(pos);
			}

		} else {
			this.selected = null;
		}
	}

	public int insert(Player p) {
		if (type == Flag.EMPTY) {
			this.type = p.toFlag();
			return 1;
		}
		return 0;
	}

	public boolean winOrTie(Player player, Point pos) {
		if (isWin(player, pos)) {
			this.board = null;
			this.type = player.toFlag();
			return true;
		} else if (num == 9) {
			this.board = null;
			this.type = Flag.TIE;
			return true;
		} else {
			return false;
		}
	}

	private boolean isWin(Player player, Point pos) {
		if (num < 3) {
			return false;
		}
		if (board == null)
			return false;
		if (winingRow(player, pos.x))
			return true;
		if (winingCol(player, pos.y))
			return true;
		return winingDiag0(player) || winingDiag1(player);
	}

	private boolean winingRow(Player player, int row) {
		Flag p = player.toFlag();
		return board[row][0].type == p && board[row][1].type == p && board[row][2].type == p;
	}

	private boolean winingCol(Player player, int col) {
		Flag p = player.toFlag();
		return board[0][col].type == p && board[1][col].type == p && board[2][col].type == p;
	}

	private boolean winingDiag0(Player player) {
		Flag p = player.toFlag();
		return board[0][0].type == p && board[1][1].type == p && board[2][2].type == p;
	}

	private boolean winingDiag1(Player player) {
		Flag p = player.toFlag();
		return board[2][0].type == p && board[1][1].type == p && board[0][2].type == p;
	}
}