package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Action.Action;
import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Action.MinotaurPushAction;
import it.polimi.ingsw.Server.Model.Action.MoveAction;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MinotaurPower extends Power {

    private  Position backWardPosition;

    public MinotaurPower(String powerType, String powerDescription, GameMap map) {
        super(powerType, powerDescription, map);
    }

    @Override
    public ActionOutcome move(Worker minotaurWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

        if(squareWhereToMove.hasWorkerOn() && !squareWhereToMove.getWorkerOnSquare().getColor().equals(minotaurWorker.getColor()) ){

            Action minotaurPushAction = new MinotaurPushAction(this, minotaurWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);

            if (!minotaurPushAction.isValid(map))
                return ActionOutcome.NOT_DONE;
            else
                minotaurPushAction.doAction();

            return ActionOutcome.DONE;

        }return super.move(minotaurWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);

    }

    @Override
    public boolean isWorkerStuck(Worker worker) {

        ArrayList<Square> adjacents = new ArrayList<>();

        for (Position position: worker.getPosition().getAdjacentPlaces())
            adjacents.add(map.getSquare(position));

        List<Square> pushable = adjacents.stream()
                .filter((Square::hasWorkerOn))
                .filter((square -> !square.getWorkerOnSquare().getColor().equals(worker.getColor())))
                .collect(Collectors.toList());

        Square workerSquare = map.getSquare(worker.getPosition());

        for (Square opponentSquare: pushable) {
            MinotaurPushAction minotaurPushAction = new MinotaurPushAction(this, worker, opponentSquare.getPosition(), workerSquare, opponentSquare);
            if (minotaurPushAction.isValid(map))
                return false;
        }

        return super.isWorkerStuck(worker);
    }
}

