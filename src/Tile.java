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
	
}