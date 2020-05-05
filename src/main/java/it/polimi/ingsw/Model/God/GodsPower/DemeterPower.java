package it.polimi.ingsw.Model.God.GodsPower;

import it.polimi.ingsw.Model.Map.Square;

public class DemeterPower extends Power {

    static boolean firstBuild;
    static Square firstBlockBuilt;


    public DemeterPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
        firstBuild = true;
        firstBlockBuilt = null;
    }

    @Override
    public boolean[] build(Square squareWhereToBuild) {

        if (firstBlockBuilt == null) {
            super.build(squareWhereToBuild);
        } else if (firstBlockBuilt.equals(squareWhereToBuild)) {
            return actionNotDone();
        }

        if (firstBuild) {
            firstBuild = false;
            firstBlockBuilt = squareWhereToBuild;
            return actionDoneCanBeDoneAgain();
        } else {
            return actionDone();
        }


    }
}