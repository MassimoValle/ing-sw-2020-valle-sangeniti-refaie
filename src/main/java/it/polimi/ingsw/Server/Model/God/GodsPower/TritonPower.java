package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Action.SelectWorkerAction;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

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

