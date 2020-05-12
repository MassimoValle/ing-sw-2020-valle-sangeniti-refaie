package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

import java.io.Serializable;

public class PoseidonPower extends Power implements Serializable {


    private Square squareFromToBuild;
    private final int buildsLimit = 3;
    private int buildCounter = 0;
    private boolean canBuildUpToThreeTimes = false;

    public PoseidonPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }



    @Override
    public ActionOutcome move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {
        squareFromToBuild = squareWhereToMove;
        return super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
    }

    @Override
    public ActionOutcome build(Square squareWhereToBuild) {

        if ( canBuildUpToThreeTimes ) {
            super.build(squareWhereToBuild);
            buildCounter++;

            if (buildCounter == buildsLimit) {
                return ActionOutcome.DONE;
            }

            return ActionOutcome.DONE_CAN_BE_DONE_AGAIN;

        } else if (squareFromToBuild.getHeight() == 0) {

            canBuildUpToThreeTimes = true;
            super.build(squareWhereToBuild);
            buildCounter++;

            return ActionOutcome.DONE_CAN_BE_DONE_AGAIN;

        } else {
            super.build(squareWhereToBuild);
            return ActionOutcome.DONE;
        }

    }
}
