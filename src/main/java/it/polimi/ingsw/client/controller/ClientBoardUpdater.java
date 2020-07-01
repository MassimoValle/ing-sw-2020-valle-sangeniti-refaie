package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.model.BabyGame;
import it.polimi.ingsw.client.model.map.CLIclientMap;
import it.polimi.ingsw.network.message.server.updatemessage.UpdateBoardMessage;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Position;

/**
 * The type Client board updater.
 */
public class ClientBoardUpdater {

    /**
     * The Baby game.
     */
    BabyGame babyGame;
    /**
     * The Client map.
     */
    CLIclientMap clientMap;

    /**
     * Instantiates a new Client board updater.
     *
     * @param babyGame the baby game
     */
    public ClientBoardUpdater(BabyGame babyGame) {
        this.babyGame = babyGame;
        this.clientMap = babyGame.getClientMap();
    }


    /**
     * Board update.
     * It manages the {@link UpdateBoardMessage} to client modifying element on board
     *
     * @param message the message contains the update to do on the board
     */
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
