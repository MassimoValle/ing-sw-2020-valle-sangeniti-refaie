package it.polimi.ingsw.Model.God.GodsPower;

import it.polimi.ingsw.Model.Action.Action;
import it.polimi.ingsw.Model.Action.MoveAction;
import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

public class ArtemisPower extends Power {

    static boolean firstMove;

    public ArtemisPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
        firstMove = true;
    }

    @Override
    public boolean move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

        Action moveAction = new MoveAction(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
        moveAction.doAction();

        if (firstMove) {
            firstMove = false;
            return true;
        } else {
            return firstMove;
        }
    }

}
