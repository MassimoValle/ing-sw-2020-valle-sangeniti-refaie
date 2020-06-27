package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.Action;
import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Action.ApolloSwapAction;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApolloPower extends Power {

    public ApolloPower(String powerType, String powerDescription, GameMap map) {
        super(powerType, powerDescription, map);
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

        ArrayList<Square> adjacents = new ArrayList<>();

        for (Position position: worker.getPosition().getAdjacentPlaces())
            adjacents.add(map.getSquare(position));

        List<Square> swappable = adjacents.stream()
                .filter((Square::hasWorkerOn))
                .filter((square -> !square.getWorkerOnSquare().getColor().equals(worker.getColor())))
                .collect(Collectors.toList());

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

