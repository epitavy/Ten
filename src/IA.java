
public class IA {
	
	Engine realGame;
	Engine test;
	public IA(Engine engine) {
		this.realGame = engine;
		this.test = new Engine(engine);
	}
	
	public void play(Point p) {
		Point[] bestMove = findBestMove();
		if(bestMove[1] == null) {
			realGame.run(bestMove[0], true);
		} else {
			realGame.run(bestMove[0], true);
			realGame.run(bestMove[1], true);
		}
	}
	
	private Point[] findBestMove() {
		Point[] move = new Point[2];
		
		return move;
	}
	
	
	
}
