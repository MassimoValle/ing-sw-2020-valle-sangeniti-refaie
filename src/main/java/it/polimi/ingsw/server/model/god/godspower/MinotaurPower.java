package it.polimi.ingsw.server.model.god.godspower;

import it.polimi.ingsw.server.model.action.Action;
import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.action.MinotaurPushAction;
import it.polimi.ingsw.server.model.map.Square;
import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.server.model.player.Worker;

import java.util.List;


public class MinotaurPower extends Power {

    public MinotaurPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }

    @Override
    public ActionOutcome move(Worker minotaurWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

        if(squareWhereToMove.hasWorkerOn() && !squareWhereToMove.getWorkerOnSquare().getColor().equals(minotaurWorker.getColor()) ){

            Action minotaurPushAction = new MinotaurPushAction(this, minotaurWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);

            if (!minotaurPushAction.isValid(map))
                return ActionOutcome.NOT_DONE;
            else
                minotaurPushAction.doAction();

            return ActionOutcome.DONE;

        }return super.move(minotaurWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);

    }

    @Override
    public boolean isWorkerStuck(Worker worker) {

        List<Square> pushable = super.getPowerSquares(worker);

        Square workerSquare = map.getSquare(worker.getPosition());

        for (Square opponentSquare: pushable) {
            MinotaurPushAction minotaurPushAction = new MinotaurPushAction(this, worker, opponentSquare.getPosition(), workerSquare, opponentSquare);
            if (minotaurPushAction.isValid(map))
                return false;
        }

        return super.isWorkerStuck(worker);
    }
}

