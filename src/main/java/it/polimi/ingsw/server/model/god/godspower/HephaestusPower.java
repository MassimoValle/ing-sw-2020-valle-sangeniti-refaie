package it.polimi.ingsw.server.model.god.godspower;

import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.map.Square;

public class HephaestusPower extends Power {

    private transient Square firstBlockBuilt;

    public HephaestusPower(String powerType, String powerDescription, GameMap map) {
        super(powerType, powerDescription);
        firstBlockBuilt = null;
    }


    @Override
    public ActionOutcome build(Square squareWhereTheWorkerIs, Square squareWhereToBuild) {
        ActionOutcome outcome;

        if (firstBlockBuilt == null) {
            if (squareWhereToBuild.getHeight() >= 2)
                return super.build(squareWhereTheWorkerIs, squareWhereToBuild);

            outcome = super.build(squareWhereTheWorkerIs, squareWhereToBuild);
            if (outcome == ActionOutcome.DONE) {
                firstBlockBuilt = squareWhereToBuild;
                return ActionOutcome.DONE_CAN_BE_DONE_AGAIN;
            } else {
                return ActionOutcome.NOT_DONE;
            }
        } else if (firstBlockBuilt.equals(squareWhereToBuild)) {
            return super.build(squareWhereTheWorkerIs, squareWhereToBuild);
        } else {
            return ActionOutcome.NOT_DONE;
        }

    }

    @Override
    public boolean powerMustBeReset() {
        return true;
    }

    @Override
    public void resetPower() {
        firstBlockBuilt = null;
    }
}

