package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Map.Square;

public class HephaestusPower extends Power {

    private static boolean firstBuild;
    private static Square firstBlockBuilt;

    public HephaestusPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
        firstBuild = true;
        firstBlockBuilt = null;
    }


    @Override
    public ActionOutcome build(Square squareWhereTheWorkerIs, Square squareWhereToBuild) {
        ActionOutcome outcome;

        if (firstBlockBuilt == null) {
            if (squareWhereToBuild.getHeight() == 2) {
                return super.build(squareWhereTheWorkerIs, squareWhereToBuild);
            } else if (squareWhereToBuild.getHeight() == 3) {
                return super.buildDome(squareWhereToBuild);
            }

            outcome = super.build(squareWhereTheWorkerIs, squareWhereToBuild);
            if (outcome == ActionOutcome.DONE) {
                firstBuild = false;
                firstBlockBuilt = squareWhereToBuild;
                return ActionOutcome.DONE_CAN_BE_DONE_AGAIN;
            } else {
                return ActionOutcome.NOT_DONE;
            }
        } else if (firstBlockBuilt.equals(squareWhereToBuild)) {
            outcome = super.build(squareWhereTheWorkerIs, squareWhereToBuild);
            if (outcome == ActionOutcome.DONE) {
                //RESET
                firstBuild = true;
                firstBlockBuilt = null;
                return outcome;
            } else {
                //NON DOVREBBE MAI ESSERE RAGGIUNTO
                return ActionOutcome.NOT_DONE;
            }
        } else {
            return ActionOutcome.NOT_DONE;
        }

    }

}

