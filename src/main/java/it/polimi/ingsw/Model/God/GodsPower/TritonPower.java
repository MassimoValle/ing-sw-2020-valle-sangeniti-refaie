package it.polimi.ingsw.Model.God.GodsPower;

import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

public class TritonPower extends Power {

    static boolean firstMove;
    static Square startingPlace;



    public TritonPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
        firstMove = true;
        startingPlace = null;
    }

    @Override
    public boolean[] move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

            if(startingPlace == null){
                super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
            } else if (startingPlace.equals(squareWhereToMove)) {
                if (positionWhereToMove.isPerimetral()) {
                    super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
                    firstMove = true;
                } else return actionNotDone();
            }

            if(firstMove) {
                firstMove = false;
                startingPlace = squareWhereToMove;
                return actionDoneCanBeDoneAgain();
            } else {
                return actionDone();
            }
    }

}

