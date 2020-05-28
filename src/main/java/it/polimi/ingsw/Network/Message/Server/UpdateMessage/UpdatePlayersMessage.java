package it.polimi.ingsw.Network.Message.Server.UpdateMessage;

import it.polimi.ingsw.Client.Controller.ClientManager;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.Player.ColorEnum;
import it.polimi.ingsw.Server.Model.Player.Player;

import java.io.Serializable;
import java.util.ArrayList;


public class UpdatePlayersMessage extends UpdateMessage{

    private ClientManager clientManager;

    private ArrayList<ClientPlayer> clientPlayers;

    public UpdatePlayersMessage(ArrayList<ClientPlayer> clientPlayers) {
        super();
        this.clientPlayers = clientPlayers;
    }

    public ArrayList<ClientPlayer> getClientPlayers() {
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
