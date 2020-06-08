package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.Action;
import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Action.ApolloSwapAction;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

import java.util.ArrayList;
import java.util.List;

public class ApolloPower extends Power {

    public ApolloPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }

    @Override
    public ActionOutcome move(Worker apolloWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

        //Controllo se attiva il potere di Apollo
        if (squareWhereToMove.hasWorkerOn() && !squareWhereToMove.getWorkerOnSquare().getColor().equals(apolloWorker.getColor()) ) {

            Action apolloSwap = new ApolloSwapAction(this, apolloWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);

            if (!apolloSwap.isValid())
                return ActionOutcome.NOT_DONE;
            else
                apolloSwap.doAction();

            return ActionOutcome.DONE;

        }

        //Faccio la classica moveAction
        return super.move(apolloWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
    }

    @Override
    public boolean isWorkerStuck(Worker worker) {
        GameMap map = Game.getInstance().getGameMap();
        ArrayList<Position> adjacent = worker.getWorkerPosition().getAdjacentPlaces();
        ArrayList<Position> reachable = (ArrayList<Position>) map.getReachableAdjacentPlaces(worker.getWorkerPosition());

        for (Position pos : reachable) {
            if (!map.getSquare(pos).hasWorkerOn())
                return false;
        }

        for (Position pos : adjacent) {
            if (map.getDifferenceInAltitude(worker.getWorkerPosition(), pos) >= -1 && map.getSquare(pos).hasWorkerOn() && !map.getPlacesWhereYouCanBuildOn(pos).isEmpty()) {
                return false;
            }
        }

        return true;
    }
}

