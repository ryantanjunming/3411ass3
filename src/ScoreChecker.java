
public class ScoreChecker {
	
	private int[][] WIN_POSITIONS = {
			{0,1,2},
			{3,4,5},
			{6,7,8},
			{0,3,6},
			{1,4,7},
			{2,5,8},
			{0,4,8},
			{2,4,6}
		};
	
	public ScoreChecker(){
		
	}
	
	public boolean gonnaWin(int[] moveBoard, int move, int piece){
		for(int i=0;i<WIN_POSITIONS.length;i++){
			
			//check that move is inside win_positions
			if(move==WIN_POSITIONS[i][0]||move==WIN_POSITIONS[i][1]
					||move==WIN_POSITIONS[i][2]){
				//check remaining two are winning positions				
				if((moveBoard[WIN_POSITIONS[i][0]]==piece && moveBoard[WIN_POSITIONS[i][1]]==piece)
				|| (moveBoard[WIN_POSITIONS[i][1]]==piece && moveBoard[WIN_POSITIONS[i][2]]==piece)
				|| (moveBoard[WIN_POSITIONS[i][0]]==piece && moveBoard[WIN_POSITIONS[i][2]]==piece)){
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	public int preferenceScoreForRandomMoves(int move){
		
		if(move == 4){
			return 7; //is middle 
		}else if(move == 0 || move == 2 
				|| move == 6 || move == 8){
			return 2;  //is corner
		}
		
		return 0;
	}
	
	public int adjacentDiagonalScore(int[] moveBoard, int move, int piece){
		
		if(moveBoard[move+1] != Game.EMPTY){
			if(moveBoard[move+1] == piece){
				
			}
		}
		
		
		return 0;
	}
}
