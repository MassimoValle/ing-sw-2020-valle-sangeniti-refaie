package it.polimi.ingsw.Client.Controller;

import it.polimi.ingsw.Client.Model.BabyGame;
import it.polimi.ingsw.Client.Model.Map.CLIclientMap;
import it.polimi.ingsw.Network.Message.Server.UpdateMessage.UpdateBoardMessage;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;

public class ClientBoardUpdater {

    BabyGame babyGame;
    CLIclientMap clientMap;

    public ClientBoardUpdater(BabyGame babyGame) {
        this.babyGame = babyGame;
        this.clientMap = babyGame.getClientMap();
    }


    void boardUpdate(UpdateBoardMessage message) {

        //String playerName = message.getPlayerName();
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
