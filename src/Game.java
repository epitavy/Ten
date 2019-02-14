public class Game {
	Tile t;
	int player;
	String save;
	int state;
	
	public Game()
	{
		this.t = new Tile();
		this.player = 1;
		this.save = "";
		this.state = 2;
	}
	
	void execute()
	{
		while(t.type == Flag.BOARD)
		{
		//TODO
		}
	}
}
