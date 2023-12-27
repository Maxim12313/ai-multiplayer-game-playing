package games.othello;


import main.*;

public class OthelloEvaluatorMK1 implements Evaluator {

    // You may want to use these functions from OthelloState:
    //    boolean isGameOver()
    //    Side getWinner()   // only call this when isGameOver() returns True
    //    int countBlackPieces()
    //    int countWhitePieces()
    //    Side getCell(int row, int col)
    //    boolean isBlackCell(int row, int col)
    //    boolean isWhiteCell(int row, int col)
    //    Side getSideToPlay()
    //    Side getOtherSide()

//    ideas came from https://www.cs.cornell.edu/~yuli/othello/othello.html
    @Override
    public int evaluate(State state) {
        OthelloState board = (OthelloState) state;
        int diskScore = getDiskScore(board);
        int legalMoveScore = getLegalMoveScore(board);
        int cornerSquareScore = getCornerSquareScore(board);

//        System.out.println("diskScore: "+diskScore);
//        System.out.println("legalMoveScore: "+legalMoveScore);
//        System.out.println("cornerSquareScore: "+cornerSquareScore);

        return diskScore+legalMoveScore+cornerSquareScore;
    }

    public int getCornerSquareScore(OthelloState board){
        int weight = 1000;

        int[] pos = new int[]{0,7};

        int blackSquares = 0;
        int whiteSquares = 0;
        for (int row:pos){
            for (int col:pos){
                if (board.isBlackCell(row,col))blackSquares++;
                if (board.isWhiteCell(row,col))whiteSquares++;
            }
        }
        return (blackSquares-whiteSquares)*weight;
    }


    public int getLegalMoveScore(OthelloState board){
        int weight = 100;

        int thisSidesMoves = board.listChildren().size();
        if (board.getSideToPlay()==Side.TWO) { //min white
            thisSidesMoves*=-1;
        }

        OthelloState otherBoard = new OthelloState(board.getOtherSide(),board);
        int otherSidesMoves = otherBoard.listChildren().size();
        if (otherBoard.getSideToPlay()==Side.TWO){
            otherSidesMoves*=-1;
        }


        return (thisSidesMoves+otherSidesMoves)*weight;
    }

    public int getDiskScore(OthelloState board){
        return (board.countBlackPieces() - board.countWhitePieces());
    }

    public String toString() {
        return "O-MK1";
    }

}