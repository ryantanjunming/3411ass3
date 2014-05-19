
public class ScoreChecker {
	
	private static int[][] WIN_POSITIONS = {
			{0,1,2},
			{3,4,5},
			{6,7,8},
			{0,3,6},
			{1,4,7},
			{2,5,8},
			{0,4,8},
			{2,4,6}
		};
	
	
	public static boolean gonnaWin(int[] moveBoard, int piece){
		
		for(int i=0;i<WIN_POSITIONS.length;i++){
			if (moveBoard[WIN_POSITIONS[i][0]] == piece &&
				moveBoard[WIN_POSITIONS[i][1]] == piece &&
				moveBoard[WIN_POSITIONS[i][2]] == piece)
				return true;
		}
		
		return false;
	}
	
	public static int preferenceScore(int move){
		
		if(move == 4){
			return 5; //is middle 
		}else if(move == 0 || move == 2 || move == 6 || move == 8){ 
			return 2;  //is corner
		}
		
		return 1;
	}
	
	public static int leadsToBetterPositionScore(int[] moveBoard, int piece){
		int bonus = 1;
		for(int i=0;i<WIN_POSITIONS.length;i++){
			//check if there are two of the same pieces in a row already on the board
			if((moveBoard[WIN_POSITIONS[i][0]]==piece && moveBoard[WIN_POSITIONS[i][1]]==piece && moveBoard[WIN_POSITIONS[i][2]]==Game.EMPTY)
			|| (moveBoard[WIN_POSITIONS[i][0]]==Game.EMPTY && moveBoard[WIN_POSITIONS[i][1]]==piece && moveBoard[WIN_POSITIONS[i][2]]==piece)
			|| (moveBoard[WIN_POSITIONS[i][0]]==piece && moveBoard[WIN_POSITIONS[i][1]]==Game.EMPTY && moveBoard[WIN_POSITIONS[i][2]]==piece)){
				bonus += 10;
			}
		}
		return bonus;
	}
	
	public static int leadsToBlockingScore(int[] moveBoard, int piece){
		int bonus = 1;
		int opponent = -piece;
		for(int i=0;i<WIN_POSITIONS.length;i++){

			//check that the move attributes to a successful block
			if((moveBoard[WIN_POSITIONS[i][0]]==opponent && moveBoard[WIN_POSITIONS[i][1]]==opponent && moveBoard[WIN_POSITIONS[i][2]]==piece)
			|| (moveBoard[WIN_POSITIONS[i][0]]==piece && moveBoard[WIN_POSITIONS[i][1]]==opponent && moveBoard[WIN_POSITIONS[i][2]]==piece)
			|| (moveBoard[WIN_POSITIONS[i][0]]==piece && moveBoard[WIN_POSITIONS[i][1]]==piece && moveBoard[WIN_POSITIONS[i][2]]==opponent)){
				bonus += 20;
			}
			
		}
		return bonus;
	}
	
}
