package players;

import main.*;

import java.util.List;

public class AlphaBetaPlayer extends Player {
    final Evaluator evaluator;
    final int maxDepth;

    public AlphaBetaPlayer(int maxDepth, Evaluator evaluator) {
        this.evaluator = evaluator;
        this.maxDepth = maxDepth;
    }

    public SearchNode pickMove(State currentState) {
        List<SearchNode> children = currentState.listChildren();

        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        if (currentState.getSideToPlay()==Side.ONE){ //max
            int max = Integer.MIN_VALUE;
            int maxIndex = 0;
            for (int i=0;i<children.size();i++){
                SearchNode child = children.get(i);
                int evaluation = minChildEvaluation(child.state,1,alpha,beta);
                if (evaluation>max){
                    max = evaluation;
                    maxIndex = i;
                }
            }
//            System.out.println(max);
            return children.get(maxIndex);
        }
        else{ //min
            int min = Integer.MAX_VALUE;
            int minIndex = 0;
            for (int i=0;i<children.size();i++){
                SearchNode child = children.get(i);
                int evaluation = maxChildEvaluation(child.state,1,alpha,beta);
                if (evaluation<min){
                    min = evaluation;
                    minIndex = i;
                }
            }
//            System.out.println(min);
            return children.get(minIndex);
        }
    }

    private int minChildEvaluation(State currentState, int depth, int alpha, int beta){
        if (depth==maxDepth || currentState.isGameOver()){
            return evaluator.evaluate(currentState);
        }
        else{
            int min = Integer.MAX_VALUE;
            for (SearchNode nextNode:currentState.listChildren()){
                int nodeEval = maxChildEvaluation(nextNode.state,depth+1,alpha,beta);
                min = Math.min(min,nodeEval);
                beta = Math.min(beta,nodeEval);
                if (nodeEval<=alpha){
                    return min;
                }
            }
            return min;
        }
    }

    private int maxChildEvaluation(State currentState,int depth, int alpha, int beta){
        if (depth==maxDepth || currentState.isGameOver()){
            return evaluator.evaluate(currentState);
        }
        else{
            int max = Integer.MIN_VALUE;
            for (SearchNode nextNode:currentState.listChildren()){
                int nodeEval = minChildEvaluation(nextNode.state,depth+1,alpha,beta);
                max = Math.max(max,nodeEval);
                alpha = Math.max(alpha,nodeEval);
                if (nodeEval>=beta){
                    return max;
                }
            }
            return max;
        }
    }



    public String toString() {
        return "AB" + maxDepth + ":" + evaluator;
    }
}
