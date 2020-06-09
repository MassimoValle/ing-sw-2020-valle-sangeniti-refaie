package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;

import java.util.ArrayList;


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


            if(outcome == ActionOutcome.DONE && hasNewBuildAvailable(squareWhereTheWorkerIs.getPosition(), squareWhereToBuild)) {
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

    private boolean hasNewBuildAvailable(Position workerPosition, Square squareWhereToBuild) {
        GameMap map = Game.getInstance().getGameMap();
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
