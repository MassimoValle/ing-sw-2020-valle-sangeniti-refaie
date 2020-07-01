package it.polimi.ingsw.server.model.god.godspower;

import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.map.Square;

public class DemeterPower extends Power {

    private transient boolean firstBuild = true;
    private transient Square firstBlockBuilt = null;


    public DemeterPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }

    @Override
    public ActionOutcome build(Square squareWhereTheWorkerIs, Square squareWhereToBuild) {
        ActionOutcome outcome;

        if (firstBlockBuilt == null) {
            outcome = super.build(squareWhereTheWorkerIs, squareWhereToBuild);
            if (outcome == ActionOutcome.DONE) {
                firstBuild = false;
                firstBlockBuilt = squareWhereToBuild;
                return ActionOutcome.DONE_CAN_BE_DONE_AGAIN;
            } else return ActionOutcome.NOT_DONE;

        } else if (firstBlockBuilt.equals(squareWhereToBuild)) {
            return ActionOutcome.NOT_DONE;
        } else {
            return super.build(squareWhereTheWorkerIs, squareWhereToBuild);
        }
    }

    @Override
    public boolean powerMustBeReset() {
        return true;
    }

    @Override
    public void resetPower() {
        firstBuild = true;
        firstBlockBuilt = null;
    }
}
