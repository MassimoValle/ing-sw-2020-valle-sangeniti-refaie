package it.polimi.ingsw.server.model.god.godspower;

import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.map.Square;
import it.polimi.ingsw.server.model.player.Position;

import java.util.ArrayList;


public class HestiaPower extends Power {

    private transient boolean firstBuild;

    public HestiaPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
        firstBuild = true;
    }


    @Override
    public ActionOutcome build(Square squareWhereTheWorkerIs, Square squareWhereToBuild) {
        ActionOutcome outcome;


        if(firstBuild){
            outcome = super.build(squareWhereTheWorkerIs, squareWhereToBuild);


            if(outcome == ActionOutcome.DONE && hasNewBuildAvailable(squareWhereTheWorkerIs.getPosition())) {
                firstBuild = false;
                return ActionOutcome.DONE_CAN_BE_DONE_AGAIN;
            } else if (outcome == ActionOutcome.DONE)
                return ActionOutcome.DONE;
            else
                return ActionOutcome.NOT_DONE;

        } else if(!squareWhereToBuild.getPosition().isPerimetral()){
            return super.build(squareWhereTheWorkerIs, squareWhereToBuild);
        } else
            return ActionOutcome.NOT_DONE;

    }

    private boolean hasNewBuildAvailable(Position workerPosition) {

        ArrayList<Position> buildables = map.getPlacesWhereYouCanBuildOn(workerPosition);

        buildables.removeIf(Position::isPerimetral);

        return !buildables.isEmpty();
    }

    @Override
    public boolean powerMustBeReset() {
        return true;
    }

    @Override
    public void resetPower() {
        firstBuild = true;
    }
}
