package players;

import main.*;

public class GreedyPlayer extends Player {
    final Evaluator evaluator;
    MiniMaxPlayer player;

    public GreedyPlayer(Evaluator evaluator) {
        this.evaluator = evaluator;
        player = new MiniMaxPlayer(1,evaluator);
    }

    public SearchNode pickMove(State currentState) {
        // Use the given Evaluator to select the child with the best evaluation
        // You may want to use:
        //    List<SearchNode> listChildren() from the State class
        //    Side getSide() from the Player class
        //    int evalSign() from the Side class
        return player.pickMove(currentState);
//        return new SearchNode(null, null); // dummy placeholder
    }


    public String toString() {
        return "Greedy:" + evaluator.toString();
    }
}