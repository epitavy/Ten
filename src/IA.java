import java.util.Arrays;
import java.util.Random;

public class IA {
	

	Engine realGame;
	Player player;	
	int maxDepth = 3;
	int strongness = 0;
	private final long minWaitingTime = 2000;
	//The third point is the complementary point supposed to be free
	private static final Point[][] alignedWinningCell = {
			{new Point(0, 0), new Point(1, 0), new Point(2, 0)},
			{new Point(0, 0), new Point(1, 1), new Point(2, 2)},
			{new Point(0, 0), new Point(0, 1), new Point(0, 2)},
			{new Point(1, 0), new Point(2, 0), new Point(0, 0)},
			{new Point(1, 0), new Point(1, 1), new Point(1, 2)},
			{new Point(0, 1), new Point(0, 2), new Point(0, 0)},
			{new Point(0, 1), new Point(1, 1), new Point(2, 1)},
			{new Point(1, 1), new Point(1, 2), new Point(1, 0)},
			{new Point(1, 1), new Point(2, 1), new Point(0, 1)},
			{new Point(1, 1), new Point(0, 2), new Point(2, 0)},
			{new Point(1, 1), new Point(2, 2), new Point(0, 0)},
			{new Point(0, 2), new Point(1, 2), new Point(2, 2)},
			{new Point(1, 2), new Point(2, 2), new Point(0, 2)},
			{new Point(2, 0), new Point(1, 1), new Point(0, 2)},
			{new Point(2, 0), new Point(2, 1), new Point(2, 2)},
			{new Point(2, 1), new Point(2, 2), new Point(2, 0)},
			
			{new Point(0, 0), new Point(2, 0), new Point(1, 0)},
			{new Point(0, 1), new Point(2, 1), new Point(1, 1)},
			{new Point(0, 2), new Point(2, 2), new Point(1, 2)},
			{new Point(0, 0), new Point(0, 2), new Point(0, 1)},
			{new Point(1, 0), new Point(1, 2), new Point(1, 1)},
			{new Point(2, 0), new Point(2, 2), new Point(2, 1)},
			{new Point(0, 0), new Point(2, 2), new Point(1, 1)},
			{new Point(0, 2), new Point(2, 0), new Point(1, 1)},
	};
	int posCoeff[][] = 
		{
			{3, 1, 3},
			{1, 5, 1},
			{3, 1, 3}
		};
	int lookUpCoeff[] = {0, 1, 2, 3, 4};
	int doubleValue = 7;
	int takenCoeff = 20;
		
	public IA(Engine engine, Player p) {
		this.realGame = engine;
		this.player = p;
	}
	
	public IA(Player p) {
		this.player = p;
	}
	
	public void play() {
		boolean isFirstMove = false;
		if(realGame.getState() == 2) {
			isFirstMove = true;
			for(int i = 0; isFirstMove && i < 3; i++)
				for(int j = 0; isFirstMove && j < 3; j++)
					if(realGame.getActual().getCell(new Point(i, j)).getNum() != 0)
						isFirstMove = false;
		}
		
		if(isFirstMove) {
			realGame.run(new Point(1, 1), true);
			realGame.run(new Point(1, 1), true);
			return;
		}
				
		long time = System.currentTimeMillis();
		Point[] bestMove = new Point[2];
		findBestMove(realGame, bestMove);
		
		//Wait until lapse is over
		while(System.currentTimeMillis() - time < minWaitingTime);
		
		if(bestMove[1] == null) {
			realGame.run(bestMove[0], true);
		} else {
			realGame.run(bestMove[0], true);
			realGame.run(bestMove[1], true);
		}
	}
	
	/**
	 * Here, the move to play is passed by reference and modified directly inside the function
	 * The return value is used in case of state at level 2, where 2 moves must be returned
	 */
	private int findBestMove(Engine e, Point[] bestMove) {
		Point[] possibleMoves = getAllPossibleMoves(e.getActual());
		int n = possibleMoves.length;
		int bestMoveValue = 0;
		int[] weights = new int[n];
		
		if(e.getState() == 2) {
			Point[] currMove = new Point[2];
			for(int i = 0; i < n; i++) {
				Engine temp = new Engine(e);
				temp.run(possibleMoves[i], true);
				//Best move is stored in currMove[0] at this point
				int currMoveValue = findBestMove(temp, currMove);
				if(currMoveValue > bestMoveValue) {
					bestMoveValue = currMoveValue;
					bestMove[0] = possibleMoves[i];
					bestMove[1] = currMove[0];
				}
			}
			bestMove[0] = currMove[0];
			int index = pickRandomWeighted(weights);
			bestMove[1] = possibleMoves[index];
		}
		else {
			bestMoveValue = Integer.MIN_VALUE;
			for(int i = 0; i < n; i++) {
				Engine temp = new Engine(e);
				temp.run(possibleMoves[i], true);
				weights[i] = minimaxWorst(temp, maxDepth);
			}
			int index = pickRandomWeighted(weights);
			bestMoveValue = weights[index];
			bestMove[0] = possibleMoves[index];
		}
		return bestMoveValue;
	}
	
	private int minimaxBest(Engine e, int depth) {
		
		Integer returnIfEnd = evaluateIfEnd(e, depth);
		if(returnIfEnd != null)
			return returnIfEnd;

		Point[] possibleMoves = getAllPossibleMoves(e.getActual());
		int n = possibleMoves.length;
		int bestMoveValue = Integer.MIN_VALUE;
		for(int i = 0; i < n; i++) {
			int currMoveValue;
			Engine temp = new Engine(e);
			temp.run(possibleMoves[i], true);
			if(e.getState() == 2)
				currMoveValue = minimaxBest(temp, depth);
			else
				currMoveValue = minimaxWorst(temp, depth - 1);
			if(currMoveValue > bestMoveValue)
				bestMoveValue = currMoveValue;
		}
		return bestMoveValue;
	}
	
	private int minimaxWorst(Engine e, int depth) {
		Integer returnIfEnd = evaluateIfEnd(e, depth);
		if(returnIfEnd != null)
			return returnIfEnd;

		Point[] possibleMoves = getAllPossibleMoves(e.getActual());
		int n = possibleMoves.length;
		int worstMoveValue = Integer.MAX_VALUE;
		for(int i = 0; i < n; i++) {
			int currMoveValue;
			Engine temp = new Engine(e);
			temp.run(possibleMoves[i], true);
			if(e.getState() == 2)
				currMoveValue = minimaxWorst(temp, depth);
			else
				currMoveValue = minimaxBest(temp, depth - 1);
			if(currMoveValue < worstMoveValue)
				worstMoveValue = currMoveValue;
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
		int opponent = evaluate(e, player.opponent());
		
		return ia - opponent;
	}
	
	int evaluate(Engine e, Player p) {
		if(e.getWinner() == p.toFlag())
			return 10000;
		return evaluateTakenPos(e.getMap(), p);
	}
	
	private int evaluateTakenPos(Tile t, Player p) {
		int value = 0;
		Point pos = new Point();
		if(t.getLevel() == 2) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					pos.change(i,  j);
					int cellCoeff = computeCoeff(t, pos, p);
					value += evaluateTakenPos(t.getCell(pos), p) * lookUpCoeff[cellCoeff];
				}
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
				if(cell.getCell(pos).getFlag() == p.toFlag())
					value += posCoeff[i][j];
			}
		}
		value += computeAlignmentCoeff(cell, p);
		return value;
	}
	
	private int computeAlignmentCoeff(Tile t, Player p) {
		int coeff = 0;
		Point pos = new Point();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				pos.change(i, j);
				if(t.getCell(pos).is_free() && isInAlignment(t, pos, p))
					coeff += doubleValue;
			}
		}
		
		return coeff;
	}
	
	/**
	 * Must be called with tile of level 2
	 * Compute the number of three in a row the cell can be part of
	 */
	private int computeCoeff(Tile t, Point pos, Player p) {
		int coeff = 0;
		for(int i = 0; i < 24; i++) {
			if(alignedWinningCell[i][2].equals(pos) && 
					(t.getCell(alignedWinningCell[i][0]).getFlag() == p.toFlag() ||
							t.getCell(alignedWinningCell[i][0]).getFlag() == Flag.BOARD) &&
					(t.getCell(alignedWinningCell[i][1]).getFlag() == p.toFlag() ||
					t.getCell(alignedWinningCell[i][1]).getFlag() == Flag.BOARD))
				coeff++;
		}
		return coeff;
	}
	
	private boolean isInAlignment(Tile t, Point pos, Player p) {
		//There are 24 winning double alignments
		for(int i = 0; i < 24; i++) {
			if(alignedWinningCell[i][2].equals(pos) && 
					t.getCell(alignedWinningCell[i][0]).getFlag() == p.toFlag() &&
					t.getCell(alignedWinningCell[i][1]).getFlag() == p.toFlag())
				return true;
		}
		return false;
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
	
	private int pickRandomWeighted(int[] weights) {
		int n = weights.length;
		int minValue = Integer.MAX_VALUE;
		int maxValue = Integer.MIN_VALUE;
		for(int i = 0; i < n; i++) {
			minValue = minValue > weights[i] ? weights[i] : minValue;
			maxValue = maxValue < weights[i] ? weights[i] : maxValue;
		}
		if(maxValue == minValue)
			return (new Random()).nextInt(n);
		maxValue -= minValue;
		for(int i = 1; i < n; i++) weights[i] = (weights[i] - minValue) * 100 / maxValue;
		int r = (new Random()).nextInt(100);
		
		//Search the corresponding index
		for(int i = 0; i < n; i++)
			if(r < weights[i])
				return i;

		return n - 1;
	}
}
