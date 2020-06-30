package it.polimi.ingsw.network.message.server.updatemessage;

import it.polimi.ingsw.server.model.god.God;
import it.polimi.ingsw.server.model.player.ColorEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class UpdatePlayersMessage extends UpdateMessage{

    private final ArrayList<ClientPlayer> clientPlayers;

    public UpdatePlayersMessage(List<ClientPlayer> clientPlayers) {
        super();
        this.clientPlayers = (ArrayList<ClientPlayer>) clientPlayers;
    }

    public List<ClientPlayer> getClientPlayers() {
        return clientPlayers;
    }



    @Override
    public void execute() {
        super.getClientManager().updatePlayerInfo(this);
    }


    public static class ClientPlayer implements Serializable {

        private final God playerGod;
        private final ColorEnum color;
        private final String playerName;


        public ClientPlayer(God playerGod, ColorEnum color, String playerName) {
            this.playerGod = playerGod;
            this.color = color;
            this.playerName = playerName;
        }

        public ColorEnum getColor() {
            return color;
        }

        public God getGod() {
            return playerGod;
        }

        public String getName() {
            return playerName;
        }
    }
}
