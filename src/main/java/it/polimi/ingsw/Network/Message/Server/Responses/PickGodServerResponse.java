package it.polimi.ingsw.Network.Message.Server.Responses;

import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

import java.util.ArrayList;

public class PickGodServerResponse extends ServerResponse {

    private final ArrayList<God> gods;

    public PickGodServerResponse(String gameManagerSays, ArrayList<God> gods) {
        super(ResponseContent.PICK_GOD, MessageStatus.OK, gameManagerSays);
        this.gods = gods;
    }

    public ArrayList<God> getGods() {
        return gods;
    }
}
