package it.polimi.ingsw.Model.God.GodsPower;

import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

public class PoseidonPower extends Power {


    private static Square squareFromToBuild;
    private static final int buildsLimit = 3;
    private static int buildCounter = 0;
    private boolean canBuildUpToThreeTimes = false;

    public PoseidonPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }



    @Override
    public boolean[] move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {
        squareFromToBuild = squareWhereToMove;
        return super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
    }

    @Override
    public boolean[] build(Square squareWhereToBuild) {

        if ( canBuildUpToThreeTimes ) {
            super.build(squareWhereToBuild);
            buildCounter++;

            if (buildCounter == buildsLimit) {
                return actionDone();
            }

            return actionDoneCanBeDoneAgain();

        } else if (squareFromToBuild.getHeight() == 0) {

            canBuildUpToThreeTimes = true;
            super.build(squareWhereToBuild);
            buildCounter++;

            return actionDoneCanBeDoneAgain();

        } else {
            super.build(squareWhereToBuild);
            return actionDone();
        }

    }
}
