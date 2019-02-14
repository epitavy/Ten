public class Tile
{
	Tile[][] board;
	Flag type;
	int level;
	Point selected;
	
	Tile(int level)
	{
		this.level = level;
		this.selected = null;
		if(level == 0)
		{
			this.type = Flag.EMPTY;
			this.board = null;
		}
		else
		{
			this.type = Flag.BOARD;
			this.board = new Tile[3][3];
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j <3; j++)
				{
					this.board[i][j] = new Tile(this.level - 1);
				}
			}
		}
	}
	
	Tile()
	{
		this.level = 2;
		this.selected = null;
		this.type = Flag.BOARD;
		this.board = new Tile[3][3];
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j <3; j++)
			{
				this.board[i][j] = new Tile(this.level - 1);
			}
		}
	}
}