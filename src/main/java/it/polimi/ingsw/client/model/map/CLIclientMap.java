package it.polimi.ingsw.client.model.map;

import it.polimi.ingsw.server.model.action.*;
import it.polimi.ingsw.server.model.God.GodsPower.ApolloPower;
import it.polimi.ingsw.server.model.God.GodsPower.MinotaurPower;
import it.polimi.ingsw.server.model.God.GodsPower.Power;
import it.polimi.ingsw.server.model.Map.GameMap;
import it.polimi.ingsw.server.model.Map.Square;
import it.polimi.ingsw.server.model.Player.Player;
import it.polimi.ingsw.server.model.Player.Position;
import it.polimi.ingsw.server.model.Player.Worker;

public class CLIclientMap extends GameMap {

    public void placeUpdate(Player player, Integer workerIndex, Position position){

        Worker worker = player.getPlayerWorkers().get(workerIndex);

        worker.setPlaced(true);
        worker.setPosition(position);
        Square square = this.getSquare(position);
        square.setWorkerOn(worker);

    }

    public void moveUpdate(Player player, Integer workerIndex, Position position){

        Worker worker = player.getPlayerWorkers().get(workerIndex);
        Power power = player.getPlayerGod().getGodPower();

        moveWorker(worker, position, power);

    }

    private void moveWorker(Worker worker, Position positionWhereToMove, Power power){

        Square startingSquare = getSquare(worker.getPosition());
        Square squareWhereToMove = getSquare(positionWhereToMove);

        //this handles the apollo swap client side
        if(power instanceof ApolloPower && squareWhereToMove.hasWorkerOn()) {
            ApolloSwapAction swap = new ApolloSwapAction((ApolloPower) power, worker, positionWhereToMove, startingSquare, squareWhereToMove);
            swap.doAction();
            return;
        }

        //this handle the minotaur push client side
        if (power instanceof MinotaurPower && squareWhereToMove.hasWorkerOn()) {
            MinotaurPushAction pushAction = new MinotaurPushAction((MinotaurPower) power, worker, positionWhereToMove, startingSquare, squareWhereToMove);
            pushAction.getBackwardInfo(this);
            pushAction.doAction();
            return;
        }

        MoveAction moveAction = new MoveAction(power, worker, positionWhereToMove, startingSquare, squareWhereToMove);
        moveAction.doAction();

    }

    public void buildUpdate(Player player, Integer workerIndex, Position position, boolean domePresent) {

        Square startingSquare = getSquare(player.getPlayerWorkers().get(workerIndex).getPosition());
        Square squareWhereToBuildOn = getSquare(position);

        if (domePresent) {
            BuildDomeAction buildDomeAction = new BuildDomeAction(startingSquare, squareWhereToBuildOn);
            buildDomeAction.doAction();

        } else {
            BuildAction buildAction = new BuildAction(startingSquare, squareWhereToBuildOn);

            buildAction.doAction();
        }

    }


}
