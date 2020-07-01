package it.polimi.ingsw.server.model.god.godspower;

import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.map.Square;
import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.server.model.player.Worker;

public class TritonPower extends Power {


    public TritonPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }

    @Override
    public ActionOutcome move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

        ActionOutcome actionOutcome = super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
        if ( actionOutcome == ActionOutcome.DONE && positionWhereToMove.isPerimetral() && !isWorkerStuck(activeWorker))
            return ActionOutcome.DONE_CAN_BE_DONE_AGAIN;

        return actionOutcome;

    }

}

