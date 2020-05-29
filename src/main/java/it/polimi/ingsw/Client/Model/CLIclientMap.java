package it.polimi.ingsw.Client.Model;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Server.Model.Action.ApolloSwapAction;
import it.polimi.ingsw.Server.Model.God.GodsPower.ApolloPower;
import it.polimi.ingsw.Server.Model.God.GodsPower.MinotaurPower;
import it.polimi.ingsw.Server.Model.God.GodsPower.Power;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

import java.util.Set;

public class CLIclientMap extends GameMap {

    public void placeUpdate(String playerName, Integer workerIndex, Position position, Set<Player> playersInGame){

        for(Player player : playersInGame){
            if(player.getPlayerName().equals(playerName)){

                Worker worker = player.getPlayerWorkers().get(workerIndex);

                worker.setPosition(position);
                Square square = this.getSquare(position);
                square.setWorkerOn(worker);

            }
        }
    }

    public void moveUpdate(String playerName, Integer workerIndex, Position position, Set<Player> playersInGame){

        for(Player player : playersInGame){
            if(player.getPlayerName().equals(playerName)){
                Worker worker = player.getPlayerWorkers().get(workerIndex);
                Power power = player.getPlayerGod().getGodPower();

                moveWorker(worker, position, power);

            }
        }
    }

    private void moveWorker(Worker worker, Position positionWhereToMove, Power power){

        Square startingSquare = getSquare(worker.getWorkerPosition());
        Square squareWhereToMove = getSquare(positionWhereToMove);
        Worker workerOnSquareWhereToMove = null;


        //this handles the apollo swap client side
        if(power instanceof ApolloPower && squareWhereToMove.hasWorkerOn()) {
            workerOnSquareWhereToMove = squareWhereToMove.getWorkerOnSquare();
            startingSquare.freeSquare();
            worker.setPosition(positionWhereToMove);
            squareWhereToMove.setWorkerOn(worker);
            startingSquare.setWorkerOn(workerOnSquareWhereToMove);
            workerOnSquareWhereToMove.setPosition(startingSquare.getPosition());
            return;
        }

        //this should handle the minotaur push client side
        if (power instanceof MinotaurPower && squareWhereToMove.hasWorkerOn()) {

        }

        //move atomica da fare sempre
        startingSquare.freeSquare();
        worker.setPosition(positionWhereToMove);
        squareWhereToMove.setWorkerOn(worker);

    }

    public void buildUpdate(Position position, boolean domePresent) {

        try {
            getSquare(position).addBlock(domePresent);
        }catch (DomePresentException e){
            e.printStackTrace();
        }

    }


}
