package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.Action;
import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Action.MinotaurPushAction;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;


public class MinotaurPower extends Power {

    private  Position backWardPosition;

    public MinotaurPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }

    @Override
    public ActionOutcome move(Worker minotaurWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

        if(squareWhereToMove.hasWorkerOn() && !squareWhereToMove.getWorkerOnSquare().getColor().equals(minotaurWorker.getColor()) ){

            Action minotaurPushAction = new MinotaurPushAction(this, minotaurWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);

            if (!minotaurPushAction.isValid())
                return ActionOutcome.NOT_DONE;
            else
                minotaurPushAction.doAction();

            return ActionOutcome.DONE;

        }return super.move(minotaurWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);

    }
}

