package games.connect4;

import main.*;

public class Connect4EvaluatorMK1 implements Evaluator {

    // You may want to use these functions from Connect4State:
    //    boolean blackWon()
    //    boolean redWon()
    //    Side getCell(int row, int col)
    //    boolean isBlackCell(int row, int col)
    //    boolean isRedCell(int row, int col)
    //    Side getSideToPlay()
    //    Side getOtherSide()

    // You may also want to use this function from Connect4Utility:
    //    static int countRuns(Connect4State state, int length, int black, int red, int empty)

    public int evaluate(State state) {
        Connect4State board = (Connect4State) state;
        if (board.blackWon())
            return Integer.MAX_VALUE;
        else if (board.redWon())
            return Integer.MIN_VALUE;


        int length = 4;
        int blackScore = 0;
        int redScore = 0;

        for (int i=1;i<length;i++){
            int emptyPieces = length-i;
            int blackSetups = Connect4Utility.countRuns(board,length,i,0,emptyPieces)*i;
            int redSetups = Connect4Utility.countRuns(board,length,0,i,emptyPieces)*i;
            blackScore +=blackSetups;
            redScore += redSetups;
        }

        int factor = 5;
        if (board.getSideToPlay()==Side.ONE){ //maxi black
            blackScore+=factor;
        }
        else{ //mini red
            redScore+=factor;
        }

        return blackScore-redScore;

    }

    public String toString() {
        return "C4-MK1";
    }

}
