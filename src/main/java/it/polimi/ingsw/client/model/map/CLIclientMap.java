package it.polimi.ingsw.client.model.map;

import it.polimi.ingsw.server.model.action.*;
import it.polimi.ingsw.server.model.god.godspower.ApolloPower;
import it.polimi.ingsw.server.model.god.godspower.MinotaurPower;
import it.polimi.ingsw.server.model.god.godspower.Power;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.map.Square;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.server.model.player.Worker;

/**
 * The type Cl iclient map.
 */
public class CLIclientMap extends GameMap {

    /**
     * Place update.
     *
     * @param player      the player who place this worker
     * @param workerIndex the worker index of player's workers
     * @param position    the position where to place the worker
     */
    public void placeUpdate(Player player, Integer workerIndex, Position position){

        Worker worker = player.getPlayerWorkers().get(workerIndex);

        worker.setPlaced(true);
        worker.setPosition(position);
        Square square = this.getSquare(position);
        square.setWorkerOn(worker);

    }

    /**
     * Move update.
     *
     * @param player      the player who move this worker
     * @param workerIndex the worker index of player's workers
     * @param position    the position where to move the worker
     */
    public void moveUpdate(Player player, Integer workerIndex, Position position){

        Worker worker = player.getPlayerWorkers().get(workerIndex);
        Power power = player.getPlayerGod().getGodPower();

        Square startingSquare = getSquare(worker.getPosition());
        Square squareWhereToMove = getSquare(position);

        //this handles the apollo swap client side
        if(power instanceof ApolloPower && squareWhereToMove.hasWorkerOn()) {
            ApolloSwapAction swap = new ApolloSwapAction((ApolloPower) power, worker, position, startingSquare, squareWhereToMove);
            swap.doAction();
            return;
        }

        //this handle the minotaur push client side
        if (power instanceof MinotaurPower && squareWhereToMove.hasWorkerOn()) {
            MinotaurPushAction pushAction = new MinotaurPushAction((MinotaurPower) power, worker, position, startingSquare, squareWhereToMove);
            pushAction.getBackwardInfo(this);
            pushAction.doAction();
            return;
        }

        MoveAction moveAction = new MoveAction(power, worker, position, startingSquare, squareWhereToMove);
        moveAction.doAction();

    }

    /**
     * Build update.
     *
     * @param player      the player who build with this worker
     * @param workerIndex the worker index of player's workers
     * @param position    the position where to build the worker
     * @param domePresent true if it have to create a dome
     */
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
