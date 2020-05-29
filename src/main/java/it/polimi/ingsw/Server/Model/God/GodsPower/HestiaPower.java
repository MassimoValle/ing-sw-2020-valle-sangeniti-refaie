package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Map.Square;


public class HestiaPower extends Power {

    private boolean firstBuild = true;

    public HestiaPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
        firstBuild = true;
    }


    @Override
    public ActionOutcome build(Square squareWhereTheWorkerIs, Square squareWhereToBuild) {
        ActionOutcome outcome;


        if(firstBuild){
            outcome = super.build(squareWhereTheWorkerIs, squareWhereToBuild);
            if(outcome == ActionOutcome.DONE) {
                firstBuild = false;
                return ActionOutcome.DONE_CAN_BE_DONE_AGAIN;
            } else return ActionOutcome.NOT_DONE;

        } else if(!squareWhereToBuild.getPosition().isPerimetral()){
            return super.build(squareWhereTheWorkerIs, squareWhereToBuild);
        } else
            return ActionOutcome.NOT_DONE;

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
