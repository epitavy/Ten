public class Tile {
	private Tile[][] board;
	private Flag type;
	private int level;
	private Point selected;

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

	public Flag getFlag() {
		return type;
	}

	public Tile[][] getBoard() {
		return board;
	}

	public int getLevel() {
		return level;
	}

	public Point getSelected() {
		return selected;
	}
	
	

	private boolean is_win(Flag player, Point pos) {
		if (board == null)
			return false;
		if (wining_row(player, pos.x))
			return true;
		if (wining_col(player, pos.y))
			return true;
		return wining_diag_0(player) || wining_diag_1(player);
	}

	private boolean wining_row(Flag player, int row) {
		return board[row][0].type == player && board[row][1].type == player && board[row][2].type == player;
	}

	private boolean wining_col(Flag player, int col) {
		return board[0][col].type == player && board[1][col].type == player && board[2][col].type == player;
	}

	private boolean wining_diag_0(Flag player) {
		return board[0][0].type == player && board[1][1].type == player && board[2][2].type == player;
	}

	private boolean wining_diag_1(Flag player) {
		return board[2][0].type == player && board[1][1].type == player && board[0][2].type == player;
	}

}