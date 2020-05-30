package it.polimi.ingsw.Server.Model.Action;

import it.polimi.ingsw.Client.Model.CLIclientMap;
import it.polimi.ingsw.Exceptions.PositionOutsideBoardException;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.God.GodsPower.MinotaurPower;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

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
    public boolean isValid() {

        GameMap map = Game.getInstance().getGameMap();

        try {
            backwardPosition = playerWorker.getWorkerPosition().getBackwardPosition(newPosition);
            backwardSquare = map.getSquare(backwardPosition);
        } catch (PositionOutsideBoardException e) {
            return false;
        }

        otherWorker = map.getWorkerOnSquare(newPosition);

        int heightDifference = map.getDifferenceInAltitude(playerWorker.getWorkerPosition(), newPosition);
        ArrayList<Position> adjacent = oldPositionSquare.getPosition().getAdjacentPlaces();

        return heightDifference >= -1 && adjacent.contains(newPosition) && !super.godsPowerActive(minotaurPower) &&
                !backwardSquare.hasWorkerOn() && !backwardSquare.hasDome();
    }

    public boolean clientValidation(CLIclientMap clientMap) {

        try {
            backwardPosition = playerWorker.getWorkerPosition().getBackwardPosition(newPosition);
            backwardSquare = clientMap.getSquare(backwardPosition);
        } catch (PositionOutsideBoardException e) {
            return false;
        }

        otherWorker = clientMap.getWorkerOnSquare(newPosition);

        int heightDifference = clientMap.getDifferenceInAltitude(playerWorker.getWorkerPosition(), newPosition);
        ArrayList<Position> adjacent = oldPositionSquare.getPosition().getAdjacentPlaces();

        return heightDifference >= -1 && adjacent.contains(newPosition) &&
                !backwardSquare.hasWorkerOn() && !backwardSquare.hasDome();

    }


    @Override
    public void doAction() {

        new MoveAction(null, otherWorker, backwardPosition, newPositionSquare, backwardSquare ).doAction();

        new MoveAction(null, minotaurWorker, newPosition, oldPositionSquare, newPositionSquare).doAction();
    }

}
