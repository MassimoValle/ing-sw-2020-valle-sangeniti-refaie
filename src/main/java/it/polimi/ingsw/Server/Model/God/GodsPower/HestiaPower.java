package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Map.Square;


public class HestiaPower extends Power {

    private boolean firstBuild = true;

    public HestiaPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }


    @Override
    public ActionOutcome build(Square squareWhereToBuild) {
        ActionOutcome outcome;


        if(firstBuild){
            outcome = super.build(squareWhereToBuild);
            if(outcome == ActionOutcome.DONE) {
                firstBuild = false;
                return ActionOutcome.DONE_CAN_BE_DONE_AGAIN;
            } else return ActionOutcome.NOT_DONE;

        } else if(!squareWhereToBuild.getPosition().isPerimetral()){        //firstBuild = false
            outcome = super.build(squareWhereToBuild);
            firstBuild = true; //resetto firstBuild
            return ActionOutcome.DONE;
        } else return ActionOutcome.NOT_DONE; //cannot build because square is perimetral

    }
}
