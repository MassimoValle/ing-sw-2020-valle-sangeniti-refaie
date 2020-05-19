package it.polimi.ingsw.Client.Model;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

import java.util.Set;

public class CLIclientMap extends GameMap {

    public void placeUpdate(String playerName, Integer workerIndex, Position position){

        Set<Player> players = BabyGame.getInstance().players;

        for(Player player : players){
            if(player.getPlayerName().equals(playerName)){

                Worker worker = player.getPlayerWorkers().get(workerIndex);

                worker.setPosition(position);
                Square square = this.getSquare(position);
                square.setWorkerOn(worker);

            }
        }
    }

    public void moveUpdate(String playerName, Integer workerIndex, Position position){

        Set<Player> players = BabyGame.getInstance().players;

        for(Player player : players){
            if(player.getPlayerName().equals(playerName)){
                Worker worker = player.getPlayerWorkers().get(workerIndex);

                moveWorker(worker, position);

            }
        }
    }

    private void moveWorker(Worker worker, Position position){

        this.getSquare(worker.getWorkerPosition()).freeSquare();
        worker.setPosition(position);

        Square square = this.getSquare(position);
        square.setWorkerOn(worker);

    }

    public void buildUpdate(Position position) {

        try {
            getSquare(position).addBlock(false);
        }catch (DomePresentException e){
            e.printStackTrace();
        }

    }

    public void printBoard() {
        String string = "";
        string = string.concat("\t0"+"       1"+"       2"+"       3"+"       4");
        string = string.concat("\n_________________________________________\n");
        for (int i=0; i<ROWS; i++) {
            for ( int j=0; j<COLUMNS; j++) {
                string = string.concat("|"+ board[i][j].toString() + "\t");
            }
            string = string.concat("|" + "  " + i +"\n");
            string = string.concat("_________________________________________\n");
        }
        System.out.println(string);
    }
}
