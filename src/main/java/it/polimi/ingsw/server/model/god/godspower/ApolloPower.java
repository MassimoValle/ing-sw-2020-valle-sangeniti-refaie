package it.polimi.ingsw.server.model.god.godspower;

import it.polimi.ingsw.server.model.action.Action;
import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.action.ApolloSwapAction;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.map.Square;
import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.server.model.player.Worker;

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

            if (!apolloSwap.isValid(map))
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

        List<Square> swappable = super.getPowerSquares(worker);

        Square apolloStartingSquare = map.getSquare(worker.getPosition());


        for (Square opponentSquare: swappable) {
            ApolloSwapAction apolloSwapAction = new ApolloSwapAction(this, worker, opponentSquare.getPosition(), apolloStartingSquare, opponentSquare);
            if (apolloSwapAction.isValid(map))
                return false;
        }

        return super.isWorkerStuck(worker);
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
        else return !swapHigherAvailable.isEmpty();
    }
}
