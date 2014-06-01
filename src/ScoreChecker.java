
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
			return 7; //is middle 
		}else if(move == 0 || move == 2 || move == 6 || move == 8){ 
			return 3;  //is corner
		}
		return 1;
	}
	
	public static int leadsToBetterPositionScore(Game g, int bNum, int move, boolean x_move) {
        int piece = (x_move) ? Game.X_PIECE : Game.Y_PIECE;
        int opponent = (x_move) ? Game.Y_PIECE: Game.X_PIECE;
        int mul = 0;
		for (int i = 0; i < 9; i++) {
			if (g.board[i][bNum] == Game.EMPTY)
				mul++;
		}
		int bonus = 1;
		for (int i = 0; i < WIN_POSITIONS.length; i++) {
            if (g.board[bNum][WIN_POSITIONS[i][0]]==piece && g.board[bNum][WIN_POSITIONS[i][1]]==piece && g.board[bNum][WIN_POSITIONS[i][2]]==Game.EMPTY) {
                bonus += 30*mul;                 
            } else if (g.board[bNum][WIN_POSITIONS[i][0]]==Game.EMPTY && g.board[bNum][WIN_POSITIONS[i][1]]==piece && g.board[bNum][WIN_POSITIONS[i][2]]==piece) {
                bonus += 30*mul;
            } else if (g.board[bNum][WIN_POSITIONS[i][0]]==piece && g.board[bNum][WIN_POSITIONS[i][1]]==piece && g.board[bNum][WIN_POSITIONS[i][2]]==Game.EMPTY) {
                bonus += 30*mul;
            } else if((g.board[bNum][WIN_POSITIONS[i][0]]==opponent && g.board[bNum][WIN_POSITIONS[i][1]]==opponent && WIN_POSITIONS[i][2]==move)
                       || (WIN_POSITIONS[i][0]==move && g.board[bNum][WIN_POSITIONS[i][1]]==opponent && g.board[bNum][WIN_POSITIONS[i][2]]==opponent)
                       || (g.board[bNum][WIN_POSITIONS[i][0]]==opponent && WIN_POSITIONS[i][1]==move && g.board[bNum][WIN_POSITIONS[i][2]]==opponent)){
                bonus += 10*mul; //theres a possibility of blocking
            } else if(g.board[bNum][WIN_POSITIONS[i][0]]==piece || g.board[bNum][WIN_POSITIONS[i][1]]==piece && g.board[bNum][WIN_POSITIONS[i][0]]==piece){
                bonus += 10*mul; //theres just a single piece alike
            }
		}
		return bonus;
	}
}
