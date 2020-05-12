package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

public class TritonPower extends Power {

    static boolean firstMove;
    static Square startingPlace;



    public TritonPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
        firstMove = true;
        startingPlace = null;
    }

    @Override
    public ActionOutcome move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

            if(startingPlace == null){
                super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
            } else if (startingPlace.equals(squareWhereToMove)) {
                if (positionWhereToMove.isPerimetral()) {
                    super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
                    firstMove = true;
                } else return ActionOutcome.NOT_DONE;
            }

            if(firstMove) {
                firstMove = false;
                startingPlace = squareWhereToMove;
                return ActionOutcome.DONE_CAN_BE_DONE_AGAIN;
            } else {
                return ActionOutcome.DONE;
            }
    }

}

