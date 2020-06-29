package it.polimi.ingsw.server.model.God.GodsPower;

import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.Map.GameMap;
import it.polimi.ingsw.server.model.Map.Square;
import it.polimi.ingsw.server.model.Player.Position;
import it.polimi.ingsw.server.model.Player.Worker;


public class PanPower extends Power {


    public PanPower(String powerType, String powerDescription, GameMap map) {
        super(powerType, powerDescription, map);
    }

    @Override
    public ActionOutcome move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

        ActionOutcome outcome = super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);

        int a = squareWhereTheWorkerIs.getHeight();
        int b = squareWhereToMove.getHeight();
        if ((a-b) > 1) {
            if (outcome == ActionOutcome.DONE) {
                return ActionOutcome.WINNING_MOVE;
            } else {
                return ActionOutcome.NOT_DONE;
            }
        } return outcome;

    }



}
