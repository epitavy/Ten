public class Point {
	int x;
	int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point() {
		this.x = 0;
		this.y = 0;
	}
	
	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	public void print() {
		System.out.print(x + " " + y);
	}
	
	public static boolean isValid(Point p) {
		return p != null && p.x >= 0 && p.x < 3 && p.y >= 0 && p.y < 3;
	}
	
	public void copy(Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	public void change(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Point p) {
		return this.x == p.x && this.y == p.y;
	}
	
}