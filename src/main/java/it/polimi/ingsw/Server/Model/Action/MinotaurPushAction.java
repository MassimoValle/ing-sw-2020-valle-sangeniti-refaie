package it.polimi.ingsw.Server.Model.Action;

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
            backwardPosition = getBackwardPosition(playerWorker.getWorkerPosition(), newPosition);
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

    @Override
    public void doAction() {

        new MoveAction(null, otherWorker, backwardPosition, newPositionSquare, backwardSquare ).doAction();

        new MoveAction(null, minotaurWorker, newPosition, oldPositionSquare, newPositionSquare).doAction();
    }

    private Position getBackwardPosition(Position pos1, Position pos2) throws PositionOutsideBoardException {

        int x1 = pos1.getRow();
        int x2 = pos2.getRow();
        int y1 = pos1.getColumn();
        int y2 = pos2.getColumn();
        int x3,y3;


        if ((pos2.isPerimetral() && pos1.isClose(pos2) && !pos1.isPerimetral() ) || pos2.isInCorner() || !pos1.isClose(pos2))
            throw new PositionOutsideBoardException();



        if (x1 == x2){
            if (y1 < y2){
                x3 = x2;
                y3 = y2 + 1;
                return new Position(x3,y3);
            }else {         //y1 > y2 non ha senso vedere quando sono uguali.
                x3 = x2;
                y3 = y2 - 1;
            } return new Position(x3,y3);
        }else if (x1 < x2) {
            if (y1 < y2) {
                x3 = x2 + 1;
                y3 = y2 + 1;
                return new Position(x3,y3);
            }else if (y1 == y2){
                x3 = x2 + 1;
                y3 = y2;
                return new Position(x3,y3);
            }else {
                x3 = x2 + 1;
                y3 = y2 - 1;
                return new Position(x3,y3);
            }
        }else {
            if(y1 < y2){
                x3 = x2 - 1;
                y3 = y2 + 1;
                return new Position(x3,y3);
            }else if (y1 == y2){
                x3 = x2 -1;
                y3 = y2;
                return new Position(x3,y3);
            }else {
                x3 = x2 - 1;
                y3 = y2 - 1;
                return new Position(x3,y3);
            }
        }
    }
}
