package it.polimi.ingsw.server.model.God.GodsPower;

import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.Map.GameMap;
import it.polimi.ingsw.server.model.Map.Square;
import it.polimi.ingsw.server.model.Player.Position;
import it.polimi.ingsw.server.model.Player.Worker;

public class TritonPower extends Power {


    public TritonPower(String powerType, String powerDescription, GameMap map) {
        super(powerType, powerDescription, map);
    }

    @Override
    public ActionOutcome move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

        ActionOutcome actionOutcome = super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
        if ( actionOutcome == ActionOutcome.DONE && positionWhereToMove.isPerimetral() && !isWorkerStuck(activeWorker))
            return ActionOutcome.DONE_CAN_BE_DONE_AGAIN;

        return actionOutcome;

    }

}

