package it.polimi.ingsw.server.model.action;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.model.map.CLIclientMap;
import it.polimi.ingsw.exceptions.PositionOutsideBoardException;
import it.polimi.ingsw.server.model.god.godspower.MinotaurPower;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.map.Square;
import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.server.model.player.Worker;

import java.util.ArrayList;

public class MinotaurPushAction extends MoveAction {

    private final MinotaurPower minotaurPower;

    private final Worker minotaurWorker;
    private Position backwardPosition = null;
    private Square backwardSquare = null;
    private Worker otherWorker = null;

    public MinotaurPushAction(MinotaurPower godsPowerPerformingAction, Worker minotaurWorker, Position newPosition, Square oldPositionSquare, Square newPositionSquare) {
        super(godsPowerPerformingAction, minotaurWorker, newPosition, oldPositionSquare, newPositionSquare);
        this.minotaurPower = godsPowerPerformingAction;
        this.minotaurWorker = minotaurWorker;
    }

    @Override
    public boolean isValid(GameMap map) {

        try {
            backwardPosition = playerWorker.getPosition().getBackwardPosition(newPosition);
            backwardSquare = map.getSquare(backwardPosition);
        } catch (PositionOutsideBoardException e) {
            return false;
        }

        otherWorker = map.getWorkerOnSquare(newPosition);

        int heightDifference = map.getDifferenceInAltitude(playerWorker.getPosition(), newPosition);
        ArrayList<Position> adjacent = oldPositionSquare.getPosition().getAdjacentPlaces();

        return heightDifference >= -1 && adjacent.contains(newPosition) && super.checkGodsPowerActive(minotaurPower, map) &&
                !backwardSquare.hasWorkerOn() && !backwardSquare.hasDome();
    }

    public void getBackwardInfo(CLIclientMap map) {
        try {
            backwardPosition = playerWorker.getPosition().getBackwardPosition(newPosition);
            backwardSquare = map.getSquare(backwardPosition);
            otherWorker = map.getWorkerOnSquare(newPosition);
        } catch (PositionOutsideBoardException e) {
            //It must be never reached unless data misalignment
            ClientManager.LOGGER.severe("Data misalignment!");
            Thread.currentThread().interrupt();
        }
    }


    @Override
    public void doAction() {

        new MoveAction(null, otherWorker, backwardPosition, newPositionSquare, backwardSquare ).doAction();

        new MoveAction(null, minotaurWorker, newPosition, oldPositionSquare, newPositionSquare).doAction();
    }

}
