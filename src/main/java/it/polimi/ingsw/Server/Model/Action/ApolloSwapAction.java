package it.polimi.ingsw.Server.Model.Action;

import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.God.GodsPower.ApolloPower;
import it.polimi.ingsw.Server.Model.God.GodsPower.Power;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

import java.util.ArrayList;

public class ApolloSwapAction extends MoveAction {

    private final Power apolloPower;

    private final Square apolloStartingSquare;
    private final Square opponentWorkerStartingSquare;
    private final Position apolloStartingPosition;
    private final Position opponentWorkerPosition;

    public ApolloSwapAction(Power apolloPower, Worker apolloWorker, Position opponentWorkerPosition, Square apolloStartingSquare, Square opponentWorkerStartingSquare) {
        super(apolloPower, apolloWorker, opponentWorkerPosition, apolloStartingSquare, opponentWorkerStartingSquare);
        this.apolloPower = apolloPower;
        this.apolloStartingSquare = apolloStartingSquare;
        this.opponentWorkerStartingSquare = opponentWorkerStartingSquare;
        this.apolloStartingPosition = apolloStartingSquare.getPosition();
        this.opponentWorkerPosition = opponentWorkerStartingSquare.getPosition();

    }

    @Override
    public boolean isValid() {

        GameMap map = Game.getInstance().getGameMap();

        int heightDifference = map.getDifferenceInAltitude(oldPositionSquare.getPosition(), newPosition);
        ArrayList<Position> adjacent = oldPositionSquare.getPosition().getAdjacentPlaces();

        return heightDifference >= -1 && adjacent.contains(newPosition) && !super.godsPowerActive(apolloPower) && !newPositionSquare.hasDome();
    }

    @Override
    public void doAction() {

        Worker opponentWorker = opponentWorkerStartingSquare.getWorkerOnSquare();

        opponentWorker.setPosition(null);
        opponentWorkerStartingSquare.freeSquare();

        new MoveAction(apolloPower,playerWorker, opponentWorkerPosition, apolloStartingSquare, opponentWorkerStartingSquare).doAction();

        new MoveAction(null, opponentWorker, apolloStartingPosition, opponentWorkerStartingSquare, apolloStartingSquare);

        opponentWorkerStartingSquare.setWorkerOn(playerWorker);
        apolloStartingSquare.setWorkerOn(opponentWorker);

    }
}
