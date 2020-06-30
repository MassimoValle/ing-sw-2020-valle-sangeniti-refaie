package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.model.BabyGame;
import it.polimi.ingsw.client.model.map.CLIclientMap;
import it.polimi.ingsw.network.message.server.updatemessage.UpdateBoardMessage;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Position;

public class ClientBoardUpdater {

    BabyGame babyGame;
    CLIclientMap clientMap;

    public ClientBoardUpdater(BabyGame babyGame) {
        this.babyGame = babyGame;
        this.clientMap = babyGame.getClientMap();
    }


    void boardUpdate(UpdateBoardMessage message) {

        Player player = babyGame.getPlayerByName(message.getPlayerName());
        Integer workerIndex = message.getWorkerIndex();
        Position position = message.getPosition();

        switch (message.getUpdateType()){
            case PLACE -> clientMap.placeUpdate(player, workerIndex, position);
            case MOVE -> clientMap.moveUpdate(player, workerIndex, position);
            case BUILD -> clientMap.buildUpdate(player, workerIndex,position, message.isDomePresent());
        }



        }

}
