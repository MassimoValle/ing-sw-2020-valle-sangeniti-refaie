package it.polimi.ingsw.Server.Model.Action;

import it.polimi.ingsw.Server.Model.God.GodsPower.Power;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

public class MinotaurPushAction extends MoveAction{

    public MinotaurPushAction(Power godsPowerPerformingAction, Worker playerWorker, Position newPosition, Square oldPositionSquare, Square newPositionSquare) {
        super(godsPowerPerformingAction, playerWorker, newPosition, oldPositionSquare, newPositionSquare);
    }

}
