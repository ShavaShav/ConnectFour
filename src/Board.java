
public class Board {
	// connect four board has 6 rows and 7 cols
	protected static final int ROWS = 6;
	protected static final int COLS = 7;
	private static final int WIN = 4;
	
	private int board[][]; // Tokens will be integers, representing player
	
	public Board(){
		board = new int[6][7];
	}
	
	// return row that token was placed in
	public int placeToken(int token, int column){
		if (column < COLS){
			int place = ROWS - 1; // start at bottom
			while (place >= 0){
				// if spot is free, place token
				if (board[place][column] == 0){
					board[place][column] = token;
					return place;
				}
				place--;
			}
		}
		return -1; // unable to place token
	}
	
	public int getCell(int row, int col){
		if (inBounds(row, col)) {
			return board[row][col];
		} else {
			return -1;
		}
	}
	
	private boolean inBounds(int row, int col){
		if (row >= ROWS || row < 0 || col < 0 || col >= COLS)
	        return false;
	    else
	        return true; 
	}
	
	public boolean checkWin(int value, int row, int col){
		int start, end;
		
		// horizontal
		int count = 0;
		start = col - WIN + 1;
		end = col + WIN;
		// bring to back to boundary
		if (start < 0) start = 0;
		if (end > COLS) end = COLS;
		while (start < end){
			if (board[row][start] == value)
				count++;
			else 
				count = 0;
			if (count == 4) 
				return true;
			start++;
		}
		
		// vertical
		count = 0;
		start = row - WIN + 1;
		end = row + WIN;
		// bring to back to boundary
		if (start < 0) start = 0;
		if (end > ROWS) end = ROWS;
		while (start < end){
			if (board[start][col] == value)
				count++;
			else 
				count = 0;
			if (count == 4) 
				return true;
			start++;
		}
		
		// forward diagonal -- checks from topright to bottom left
		count = 0;
		int startR = row - (WIN-1);
		int startC = col + (WIN-1);
		while (!inBounds(startR, startC)){
			startR++; // bring to back to boundary
			startC--;
		}
		int endR = row + (WIN-1);
		int endC = col - (WIN-1);
		while (!inBounds(endR, endC)){
			endR--; // bring to back to boundary
			endC++;
		}
		while (startR <= endR && startC >= endC){
			if (board[startR][startC] == value)
				count++;
			else 
				count = 0;
			if (count == WIN) 
				return true;		
			startR++;
			startC--;
		}
		
		// backward diagonal -- checks from top left to bottom right
		count = 0;
		startR = row - (WIN-1);
		startC = col - (WIN-1);
		while (!inBounds(startR, startC)){
			startR++; // bring to back to boundary
			startC++;
		}
		endR = row + (WIN-1);
		endC = col + (WIN-1);
		while (!inBounds(endR, endC)){
			endR--; // bring to back to boundary
			endC--;
		}
		while (startR <= endR){
			if (board[startR][startC] == value)
				count++;
			else 
				count = 0;
			if (count == WIN) 
				return true;		
			startR++;
			startC++;
		}
		
		// false by default
		return false;
	}
}
