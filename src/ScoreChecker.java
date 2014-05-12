
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
			//check remaining two are in either winning positions				
			if((moveBoard[WIN_POSITIONS[i][0]]==piece && moveBoard[WIN_POSITIONS[i][1]]==piece)
			|| (moveBoard[WIN_POSITIONS[i][1]]==piece && moveBoard[WIN_POSITIONS[i][2]]==piece)
			|| (moveBoard[WIN_POSITIONS[i][0]]==piece && moveBoard[WIN_POSITIONS[i][2]]==piece)){
				return true;
			}
		}
		
		return false;
	}
	
	public static int preferenceScore(int move){
		
		if(move == 4){
			return 7; //is middle 
		}else if(move == 0 || move == 2 || move == 6 || move == 8){ 
			return 2;  //is corner
		}
		
		return 1;
	}
	
	public static int leadsToBetterPositionScore(int[] moveBoard, int move, int piece){
		int bonus = 1;
		for(int i=0;i<WIN_POSITIONS.length;i++){
			//check that one position is move, one position is empty and one position is already marked					
			if((WIN_POSITIONS[i][0]==move && moveBoard[WIN_POSITIONS[i][1]]==piece && moveBoard[WIN_POSITIONS[i][2]]==Game.EMPTY)
			|| (moveBoard[WIN_POSITIONS[i][0]]==Game.EMPTY && WIN_POSITIONS[i][1]==move && moveBoard[WIN_POSITIONS[i][2]]==piece)
			|| (moveBoard[WIN_POSITIONS[i][0]]==piece && moveBoard[WIN_POSITIONS[i][1]]==Game.EMPTY && WIN_POSITIONS[i][2]==move)){
				bonus *= 2;
			}
		}
		
		return bonus;
	}
}
