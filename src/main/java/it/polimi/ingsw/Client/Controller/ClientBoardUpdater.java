package it.polimi.ingsw.Client.Controller;

import it.polimi.ingsw.Client.Model.BabyGame;
import it.polimi.ingsw.Client.Model.CLIclientMap;
import it.polimi.ingsw.Network.Message.Server.UpdateMessage.UpdateBoardMessage;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.View.Observer;

public class ClientBoardUpdater {

    BabyGame babyGame;
    CLIclientMap clientMap;

    public ClientBoardUpdater(BabyGame babyGame) {
        this.babyGame = babyGame;
        this.clientMap = babyGame.getClientMap();
    }


    void boardUpdate(UpdateBoardMessage message) {

            String playerName = message.getPlayerName();
            Integer worker = message.getWorkerIndex();
            Position position = message.getPosition();

            switch (message.getUpdateType()){
                case PLACE -> clientMap.placeUpdate(playerName, worker, position, babyGame.getPlayers());
                case MOVE -> clientMap.moveUpdate(playerName, worker, position, babyGame.getPlayers());
                case BUILD -> clientMap.buildUpdate(position, message.isDomePresent());
            }



        }

}
