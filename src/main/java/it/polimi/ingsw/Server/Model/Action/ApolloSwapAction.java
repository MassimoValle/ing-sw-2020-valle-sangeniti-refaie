package it.polimi.ingsw.Server.Model.Action;

import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.God.GodsPower.ApolloPower;
import it.polimi.ingsw.Server.Model.God.GodsPower.Power;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

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
    public boolean isValid() {

        GameMap map = Game.getInstance().getGameMap();

        Worker opponentWorker = map.getWorkerOnSquare(opponentWorkerPosition);
        int heightDifference = map.getDifferenceInAltitude(apolloStartingSquare.getPosition(), opponentWorkerPosition);
        ArrayList<Position> adjacent = apolloStartingSquare.getPosition().getAdjacentPlaces();
        ArrayList<Position> buildable = map.getPlacesWhereYouCanBuildOn(opponentWorkerPosition);

        return heightDifference >= -1 && adjacent.contains(opponentWorkerPosition) && !super.godsPowerActive(apolloPower) && !apolloPower.isWorkerStuck(opponentWorker) && !buildable.isEmpty();
    }

    public boolean clientValidation(GameMap clientMap) {

        int heightDifference = clientMap.getDifferenceInAltitude(apolloStartingSquare.getPosition(), opponentWorkerPosition);
        ArrayList<Position> adjacent = apolloStartingSquare.getPosition().getAdjacentPlaces();

        return heightDifference >= -1 && adjacent.contains(opponentWorkerPosition);
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
