package players;

import main.*;

import java.util.List;

public class MiniMaxPlayer extends Player {
    final Evaluator evaluator;
    final int maxDepth;

    public MiniMaxPlayer(int maxDepth, Evaluator evaluator) {
        this.evaluator = evaluator;
        this.maxDepth = maxDepth;
    }

    public SearchNode pickMove(State currentState) {
        List<SearchNode> children = currentState.listChildren();
        if (currentState.getSideToPlay()==Side.ONE){ //max
            int max = Integer.MIN_VALUE;
            int maxIndex = 0;
            for (int i=0;i<children.size();i++){
                SearchNode child = children.get(i);
                int evaluation = minChildEvaluation(child.state,1);
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
                int evaluation = maxChildEvaluation(child.state,1);
                if (evaluation<min){
                    min = evaluation;
                    minIndex = i;
                }
            }
//            System.out.println(min);
            return children.get(minIndex);
        }
    }

    private int minChildEvaluation(State currentState, int depth){
        if (depth==maxDepth || currentState.isGameOver()){
            return evaluator.evaluate(currentState);
        }
        else{
            int min = Integer.MAX_VALUE;
            for (SearchNode nextNode:currentState.listChildren()){
                min = Math.min(min,maxChildEvaluation(nextNode.state,depth+1));
            }
            return min;
        }
    }

    private int maxChildEvaluation(State currentState,int depth){
        if (depth==maxDepth || currentState.isGameOver()){
            return evaluator.evaluate(currentState);
        }
        else{
            int max = Integer.MIN_VALUE;
            for (SearchNode nextNode:currentState.listChildren()){
                max = Math.max(max,minChildEvaluation(nextNode.state,depth+1));
            }
            return max;
        }
    }

    public String toString() {
        return "MM:" + maxDepth + " " + evaluator;
    }
}
