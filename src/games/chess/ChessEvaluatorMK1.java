package games.chess;



import main.Evaluator;
import main.Side;
import main.State;

import java.util.ArrayList;

public class ChessEvaluatorMK1 implements Evaluator {

    // You may want to use these functions from ChessState:
    //    boolean isGameOver()
    //    Side getWinner()     // only call this when isGameOver() returns True
    //    ChessPiece getPiece(int row, int col)
    //    boolean inCheck(Side side)
    //    boolean attackChecker(Side targetSide, int targetRow, int targetCol)
    //    int getKingRow(Side side)
    //    int getKingCol(Side side)
    //    Side getSideToPlay()
    //    Side getOtherSide()


    @Override
    public int evaluate(State state) {
        ChessState board = (ChessState) state;
        if (board.isGameOver()) {
            Side winningSide = board.getWinner();
            if (winningSide == ChessState.WHITE) {
                return Integer.MAX_VALUE;
            } else if (winningSide == ChessState.BLACK) {
                return Integer.MIN_VALUE;
            }
        }


        int boardLength = 8;
        int whiteMaterialScore = 0;
        int blackMaterialScore = 0;
        int positionScore = 0;
        ArrayList<Pos> afterPieces = new ArrayList<>();

        for (int r=0;r<boardLength;r++){
            for (int c=0;c<boardLength;c++){
                ChessPiece piece = board.getPiece(r,c);

                if (piece == null){
                    continue;
                }

                int material = getMaterialScore(piece);
                int position = 0;


                if (!piece.isPawn()){
                    position = getPositionScore(r,c);
                }

                if (piece.isKing() || piece.isPawn()){
                    afterPieces.add(new Pos(r,c));
                }


                if (piece.side==Side.ONE){
                    whiteMaterialScore+=material*10;
                    positionScore+=position;
                }
                else{
                    blackMaterialScore+=material*10;
                    positionScore-=position;
                }
            }
        }

        int materialScore = whiteMaterialScore-blackMaterialScore;
        int minMaterial = Math.min(whiteMaterialScore,Math.abs(blackMaterialScore)); //least point
        int startMaterial = 39*10; //8 + 4*3 + 2*5 + 9 adding up all pieces

//        for (Pos position:afterPieces){
//            int r = position.r;
//            int c = position.c;
//            ChessPiece piece = board.getPiece(r,c);
//
//            if (piece.isKing()){
//                int kingSafety = getKingSafety(r,c,minMaterial);
//            }
//            else{
//                int passedPawn;
//            }
//        }



        return materialScore+positionScore;
    }

    public int getPositionScore(int r,int c){
        int rowDist = Math.abs(4-r);
        int colDist = Math.abs(4-c);
//        if (rowDist<=1)rowDist = 0;
//        if (colDist<=1)rowDist = 0; //make all middle 2x2 = 0

        return (4-rowDist + 4-colDist);
    }

//    public int getKingSafety(int r, int c, int endGameWeight){
//        if (r<=2){
//
//        }
//        else if (r>=7-2){
//
//        }
//    }


    public int getMaterialScore(ChessPiece piece){
        int material = 0;
        if (piece.isPawn()){
            material = 1;
        }
        else if (piece.isRook()){
            material = 5;
        }
        else if (piece.isQueen()){
            material = 9;
        }
        else if (piece.isBishop()){
            material = 3;
        }
        else if (piece.isKnight()){
            material = 3;
        }

        return material;
    }

    public String toString() {
        return "C-MK1";
    }



    static class Pos{
        int r;
        int c;
        Pos(int a, int b){
            r = a;
            c = b;
        }
    }
}
