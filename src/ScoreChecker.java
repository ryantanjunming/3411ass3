
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
	
	public static int leadsToBetterPositionScore(Game g, int bNum, int piece) {

		int mul = 0;
		for (int i = 0; i < 9; i++) {
			if (g.board[i][bNum] == Game.EMPTY)
				mul++;
		}
		int bonus = 1;
		for (int i = 0; i < WIN_POSITIONS.length; i++) {
			if (g.board[bNum][WIN_POSITIONS[i][0]]==piece && g.board[bNum][WIN_POSITIONS[i][1]]==piece && g.board[bNum][WIN_POSITIONS[i][2]]==Game.EMPTY) {
				bonus += 10*mul;
			} else if (g.board[bNum][WIN_POSITIONS[i][0]]==Game.EMPTY && g.board[bNum][WIN_POSITIONS[i][1]]==piece && g.board[bNum][WIN_POSITIONS[i][2]]==piece) {
				 bonus += 10*mul;
			} else if (g.board[bNum][WIN_POSITIONS[i][0]]==piece && g.board[bNum][WIN_POSITIONS[i][1]]==piece && g.board[bNum][WIN_POSITIONS[i][2]]==Game.EMPTY) {
				bonus += 10*mul;
			}
		}
		return bonus;
	}
	
	public static int leadsToBlockingScore(int[] moveBoard, int move, int opponent){
		int bonus = 1;
		for(int i=0;i<WIN_POSITIONS.length;i++){
		
			//check that the move attributes to a successful block
			if((moveBoard[WIN_POSITIONS[i][0]]==opponent && moveBoard[WIN_POSITIONS[i][1]]==opponent && WIN_POSITIONS[i][2]==move)
			|| (WIN_POSITIONS[i][0] ==move && moveBoard[WIN_POSITIONS[i][1]]==opponent && moveBoard[WIN_POSITIONS[i][2]]==opponent)
			|| (moveBoard[WIN_POSITIONS[i][0]]==opponent && WIN_POSITIONS[i][1]==move && moveBoard[WIN_POSITIONS[i][2]]==opponent)){
				bonus += 10;
			}
		
		}
		return bonus;
	}
}
