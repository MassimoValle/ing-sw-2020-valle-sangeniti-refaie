package it.polimi.ingsw.Client.Model.Map;

import it.polimi.ingsw.Server.Model.Action.*;
import it.polimi.ingsw.Server.Model.God.GodsPower.ApolloPower;
import it.polimi.ingsw.Server.Model.God.GodsPower.MinotaurPower;
import it.polimi.ingsw.Server.Model.God.GodsPower.Power;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

public class CLIclientMap extends GameMap {

    public void placeUpdate(Player player, Integer workerIndex, Position position){

        Worker worker = player.getPlayerWorkers().get(workerIndex);

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
        Worker workerOnSquareWhereToMove = null;

        //this handles the apollo swap client side
        if(power instanceof ApolloPower && squareWhereToMove.hasWorkerOn()) {
            ApolloSwapAction swap = new ApolloSwapAction((ApolloPower) power, worker, positionWhereToMove, startingSquare, squareWhereToMove);
            swap.clientValidation(this);
            swap.doAction();
            return;
        }

        //this should handle the minotaur push client side
        if (power instanceof MinotaurPower && squareWhereToMove.hasWorkerOn()) {
            MinotaurPushAction pushAction = new MinotaurPushAction((MinotaurPower) power, worker, positionWhereToMove, startingSquare, squareWhereToMove);

            pushAction.clientValidation(this);

            pushAction.doAction();
            return;
        }

        MoveAction moveAction = new MoveAction(power, worker, positionWhereToMove, startingSquare, squareWhereToMove);

        moveAction.clientValidation(this);

        moveAction.doAction();

    }

    public void buildUpdate(Player player, Integer workerIndex, Position position, boolean domePresent) {

        Square startingSquare = getSquare(player.getPlayerWorkers().get(workerIndex).getPosition());
        Square squareWhereToBuildOn = getSquare(position);

        if (domePresent) {
            BuildDomeAction buildDomeAction = new BuildDomeAction(startingSquare, squareWhereToBuildOn);

            buildDomeAction.clientValidation();

            buildDomeAction.doAction();
        } else {
            BuildAction buildAction = new BuildAction(startingSquare, squareWhereToBuildOn);

            buildAction.clientValidation();

            buildAction.doAction();
        }

    }


}
