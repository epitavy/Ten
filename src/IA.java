
public class IA {
	
	Engine realGame;
	Player player;
	private static final int posCoeff[][] = 
		{
			{3, 1, 3},
			{1, 5, 1},
			{3, 1, 3}
		};
	private static final int doubleCoeff = 3;
	private static final int takenCoeff = 15;
		
	public IA(Engine engine, Player p) {
		this.realGame = engine;
		this.player = p;
	}
	
	public void play() {
		Point[] bestMove = findBestMove(realGame);
		if(bestMove[1] == null) {
			realGame.run(bestMove[0], true);
		} else {
			realGame.run(bestMove[0], true);
			realGame.run(bestMove[1], true);
		}
	}
	
	private Point[] findBestMove(Engine e) {
		Point[] move = new Point[2];
		move[1] = null;
		//must differentiate move at level 1 and level 2
		move[0] = minimax(e);
		
		return move;
	}
	
	private Point minimax(Engine e) {
		Point[] possibleMoves = getAllPossibleMoves(e.getActual());
		int n = possibleMoves.length;
		int moveValues[] = new int[n];
		long time = System.currentTimeMillis();
		
		int bestIndex = 0;
		int bestValue = Integer.MIN_VALUE;
		int currValue;
		for(int i = 0; i < n; i++) {
			Engine temp = new Engine(e);
			temp.run(possibleMoves[i], true);
			currValue = minimaxBest(temp, 2);
			System.out.print("Move ");
			possibleMoves[i].print();
			System.out.println("has a value of " + currValue);
			if(currValue > bestValue) {
				bestValue = currValue;
				bestIndex = i;
			}
		}
		
		return possibleMoves[bestIndex];
	}
	
	private int minimaxBest(Engine e, int depth) {
		Integer returnIfEnd = evaluateIfEnd(e, depth);
		if(returnIfEnd != null)
			return returnIfEnd;
		
		Point[] possibleMoves = getAllPossibleMoves(e.getActual());
		//Get the maximum value of next node
		int bestMoveValue = Integer.MIN_VALUE;
		int currentMoveValue;
		for(int i = 0; i < possibleMoves.length; i++) {
			Engine temp = new Engine(e);
			temp.run(possibleMoves[i], true);
			
			if(e.getState() == 2)
				currentMoveValue = minimaxBest(temp, depth);
			else
				currentMoveValue = minimaxWorst(temp, depth - 1);
			if(currentMoveValue > bestMoveValue)
				bestMoveValue = currentMoveValue;
		}
		return bestMoveValue;
	}
	
	private int minimaxWorst(Engine e, int depth) {
		Integer returnIfEnd = evaluateIfEnd(e, depth);
		if(returnIfEnd != null)
			return returnIfEnd;
		
		Point[] possibleMoves = getAllPossibleMoves(e.getActual());
		//Get the minimum value of next node
		int worstMoveValue = Integer.MAX_VALUE;
		int currentMoveValue;
		for(int i = 0; i < possibleMoves.length; i++) {
			Engine temp = new Engine(e);
			temp.run(possibleMoves[i], true);
			
			if(e.getState() == 2)
				currentMoveValue = minimaxWorst(temp, depth);
			else
				currentMoveValue = minimaxBest(temp, depth - 1);
			if(currentMoveValue < worstMoveValue)
				worstMoveValue = currentMoveValue;
		}
		return worstMoveValue;
	}
	
	private Integer evaluateIfEnd(Engine e, int depth) {
		if(depth == 0 || e.isWin())
			return evaluate(e);
		
		return null;
	}

	private int evaluate(Engine e) {
		int ia = evaluate(e, player);
		int opponent;
		if(player == Player.CIRCLE)
			opponent = evaluate(e, Player.CROSS);
		else
			opponent = evaluate(e, Player.CIRCLE);
		
		return ia - opponent;
	}
	
	int evaluate(Engine e, Player p) {
		return evaluateTakenPos(e.getMap(), p);
	}
	
	private int evaluateTakenPos(Tile t, Player p) {
		int value = 0;
		if(t.getLevel() == 2) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++)
					value += evaluateTakenPos(t.getCell(new Point(i, j)), p) * posCoeff[i][j];
			}
			return value;
		}
		else {
			if(t.getFlag() == p.toFlag())
				return takenCoeff;
			else if(t.getFlag() != Flag.BOARD)
				return 0;
			else 
				return getCellValue(t, p);
		}
	}
	
	private int getCellValue(Tile cell, Player p) {
		int value = 0;
		Point pos = new Point();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				pos.change(i, j);
				if(cell.getCell(pos) == null)
					return 0;
				if(cell.getCell(pos).getFlag() == p.toFlag())
					value += posCoeff[i][j];
			}
		}
		return value;
	}
	private int evaluateDouble(Tile t, Player p) {
		//TODO
		return 0;
	}
	
	private Point[] getAllPossibleMoves(Tile board) {
		int n = getNumberOfPossibleMove(board);
		Point[] moves = new Point[n];
		n  = 0;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				Point p = new Point(i, j);
				if(board.getCell(p).is_free())
					moves[n++] = p;
			}
		}
		
		return moves;
	}
	
	private int getNumberOfPossibleMove(Tile board) {
		return 9 - board.getNum();
	}
	
	public boolean testGetCellValue() {
		/**
		 * Expected cell (0, 0)
		 *  -------
		 *  |x| | |
		 *  | |x| |
		 *  |x|x| |
		 *  ------- 
		 */
		Tile[][] board = new Tile[3][3];
		for(int i = 0; i < 3; i ++) {
			for(int j = 0; j < 3; j++) {
				board[i][j] = new Tile(0);
			}
		}
		Point[] positions = {new Point(0, 0), new Point(2, 0), new Point(1, 1), new Point(2, 1)};
		Tile t = new Tile(board, 1);
		int expectedValue = 0;
		if(getCellValue(t, Player.CROSS) != expectedValue)
			return false;
		for(Point p : positions) {		
			board[p.x][p.y].changeFlag(Flag.CROSS);
			expectedValue += posCoeff[p.x][p.y];
			if(getCellValue(t, Player.CROSS) != expectedValue)
				return false;
		}
		board[1][0].changeFlag(Flag.CIRCLE);
		if(getCellValue(t, Player.CROSS) != expectedValue)
			return false;
		
		return true;
	}
}
