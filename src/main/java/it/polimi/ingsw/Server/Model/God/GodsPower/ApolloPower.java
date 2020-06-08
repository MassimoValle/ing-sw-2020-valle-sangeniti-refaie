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
        ArrayList<Position> adjacent = worker.getPosition().getAdjacentPlaces();
        ArrayList<Position> reachable = (ArrayList<Position>) map.getReachableAdjacentPlaces(worker.getPosition());

        if (!reachable.isEmpty() && !map.forcedToMoveUp(reachable, worker.getPosition()))
            return false;

        if (athenaPowerActivated() && canSwapOnlyGoingUp(map, worker))
            return true;

        if (!reachable.isEmpty())
            return false;


        for (Position pos : adjacent) {
            if (map.getDifferenceInAltitude(worker.getPosition(), pos) >= -1 && map.getSquare(pos).hasWorkerOn() && !map.getSquare(pos).getWorkerOnSquare().getColor().equals(worker.getColor()) && !map.getPlacesWhereYouCanBuildOn(pos).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public boolean canSwapOnlyGoingUp(GameMap map, Worker worker) {
        ArrayList<Position> adjacent = worker.getPosition().getAdjacentPlaces();
        ArrayList<Position> swapNotHigherAvailable = new ArrayList<>();
        ArrayList<Position> swapHigherAvailable = new ArrayList<>();


        for (Position pos : adjacent) {
            Square square = map.getSquare(pos);
            if (square.hasWorkerOn() && !square.getWorkerOnSquare().getColor().equals(worker.getColor()) ) {
                if (map.getDifferenceInAltitude(worker.getPosition(), pos) >= 0)
                    swapNotHigherAvailable.add(pos);
                else if (map.getDifferenceInAltitude(worker.getPosition(), pos) == -1) {
                        swapHigherAvailable.add(pos);
                }
            }
        }

        if (!swapNotHigherAvailable.isEmpty())
            return false;
        else if (!swapHigherAvailable.isEmpty()) {
            return true;
        }

        return false;
    }
}

