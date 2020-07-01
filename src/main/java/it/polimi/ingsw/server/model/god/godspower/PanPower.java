package it.polimi.ingsw.server.model.god.godspower;

import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.map.Square;
import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.server.model.player.Worker;


public class PanPower extends Power {


    public PanPower(String powerType, String powerDescription, GameMap map) {
        super(powerType, powerDescription);
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
