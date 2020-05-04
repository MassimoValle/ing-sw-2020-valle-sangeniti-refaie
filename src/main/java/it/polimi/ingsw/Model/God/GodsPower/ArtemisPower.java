package it.polimi.ingsw.Model.God.GodsPower;

import it.polimi.ingsw.Model.Action.Action;
import it.polimi.ingsw.Model.Action.MoveAction;
import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

public class ArtemisPower extends Power {

    static boolean firstMove;
    static Square startingPlace;

    public ArtemisPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
        firstMove = true;
        startingPlace = null;
    }

    @Override
    public boolean[] move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

        if (startingPlace == null) {
            super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
        } else if (startingPlace.equals(squareWhereToMove)) {
            return actionNotDone();
        } else {
            super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
        }



        if (firstMove) {
            firstMove = false;
            startingPlace = squareWhereTheWorkerIs;
            return actionDoneCanBeDoneAgain();
        } else {
            return actionDone();
        }
    }

}
