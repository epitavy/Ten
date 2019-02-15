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

	public Tile getCell(Point pos) {
		if (type == Flag.BOARD && pos != null && pos.x >= 0 && pos.x < 3 && pos.y >= 0 && pos.y < 3)
			return board[pos.x][pos.y];
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
			selected = pos;
		} else {
			selected = null;
		}
	}

	public int insert(Player p) {
		if (type == Flag.EMPTY) {
			this.type = p.toFlag();
			return 1;
		}
		return 0;
	}

	public boolean win(Player player, Point pos) {
		if (isWin(player, pos)) {
			this.board = null;
			this.type = player.toFlag();
			return true;
		}
		return false;
	}

	public boolean isWin(Player player, Point pos) {
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

	// TO REMOVE

	public void draw0() {
		switch (type) {
		case EMPTY:
			System.out.print("_ ");
			break;
		case CIRCLE:
			System.out.print("o ");
			break;
		case CROSS:
			System.out.print("x ");
			break;
		default:
			break;
		}
	}

	public void draw1(int a ) {
		switch(type) {
		case BOARD:
			for(int i = 0; i < 3; i++) {
				board[a][i].draw0();
			}
			System.out.print(" ");
			break;
		case CROSS:
			if(a == 1) {
				System.out.print("  X    ");
			}
			else System.out.print("       ");
			break;
		case CIRCLE:
			if(a == 1) {
				System.out.print("  O    ");
			}
			else System.out.print("       ");
			break;
		case TIE:
			if(a == 1) {
				System.out.print("  -    ");
			}
			else System.out.print("       ");
			break;
		default:
			break;
		}
	}
	
	public void draw2() {
		if(type == Flag.BOARD) {
			for(int i = 0; i < 3; i++) {
				for(int a = 0; a < 3; a++) {
					for(int j = 0; j < 3; j++) {
						board[i][j].draw1(a);
					}
					System.out.println("");
				}
				System.out.println("");
			}
		} else if(type == Flag.CROSS) {
			System.out.println("Cross win");
		} else if(type == Flag.CIRCLE) {
			System.out.println("Circle win");
		} else {
			System.out.println("It's a tie");
		}
	}
	
	public void draw() {
		draw2();
		System.out.println("");
		if(selected != null) {
			System.out.print("Main : ");
			selected.print();
		}
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(board[i][j].selected != null) {
					System.out.print(i + " " + j + " : ");
					board[i][j].selected.print();
				}
			}
		}
	}
}