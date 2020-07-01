package it.polimi.ingsw.server.model.action;

import it.polimi.ingsw.server.model.god.godspower.ApolloPower;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.map.Square;
import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.server.model.player.Worker;

import java.util.ArrayList;

public class ApolloSwapAction extends MoveAction {

    private final ApolloPower apolloPower;

    private final Worker apolloWorker;
    private final Square apolloStartingSquare;
    private final Square opponentWorkerStartingSquare;
    private final Position apolloStartingPosition;
    private final Position opponentWorkerPosition;

    public ApolloSwapAction(ApolloPower apolloPower, Worker apolloWorker, Position opponentWorkerPosition, Square apolloStartingSquare, Square opponentWorkerStartingSquare) {
        super(apolloPower, apolloWorker, opponentWorkerPosition, apolloStartingSquare, opponentWorkerStartingSquare);
        this.apolloWorker = apolloWorker;
        this.apolloPower = apolloPower;
        this.apolloStartingSquare = apolloStartingSquare;
        this.opponentWorkerStartingSquare = opponentWorkerStartingSquare;
        this.apolloStartingPosition = apolloStartingSquare.getPosition();
        this.opponentWorkerPosition = opponentWorkerStartingSquare.getPosition();

    }

    @Override
    public boolean isValid(GameMap map) {

        int heightDifference = map.getDifferenceInAltitude(apolloStartingSquare.getPosition(), opponentWorkerPosition);
        ArrayList<Position> adjacent = apolloStartingSquare.getPosition().getAdjacentPlaces();
        ArrayList<Position> buildable = map.getPlacesWhereYouCanBuildOn(opponentWorkerPosition);

        return heightDifference >= -1 && adjacent.contains(opponentWorkerPosition) && super.checkGodsPowerActive(apolloPower, map) && !buildable.isEmpty();
    }

    @Override
    public void doAction() {



        Worker opponentWorker = opponentWorkerStartingSquare.getWorkerOnSquare();

        opponentWorker.setPosition(null);
        opponentWorkerStartingSquare.freeSquare();

        new MoveAction(apolloPower,apolloWorker, opponentWorkerPosition, apolloStartingSquare, opponentWorkerStartingSquare).doAction();

        new MoveAction(null, opponentWorker, apolloStartingPosition, opponentWorkerStartingSquare, apolloStartingSquare).doAction();

        opponentWorkerStartingSquare.setWorkerOn(apolloWorker);
        apolloStartingSquare.setWorkerOn(opponentWorker);

    }
}
