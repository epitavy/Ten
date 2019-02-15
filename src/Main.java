public class Main {
	public static void main(String[] args) {
		
		Game game = new Game();
//		g.execute();
		Point a = new Point(0, 0);
		Point b = new Point(0, 1);
		Point c = new Point(0, 2);
		Point d = new Point(1, 0);
		Point e = new Point(1, 1);
		Point f = new Point(1, 2);
		Point g = new Point(2, 0);
		Point h = new Point(2, 1);
		Point i = new Point(2, 2);
		
		Point[] arr = {a, d, a, g, a, a};
//		game.display(arr, arr.length);
		game.display();
	}

}