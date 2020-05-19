package it.polimi.ingsw.Network.Message.Server.Responses;

import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

import java.util.ArrayList;

public class PickGodResponse extends Response{

    private final ArrayList<God> gods;

    public PickGodResponse(String messageSender, String gameManagerSays, ArrayList<God> gods) {
        super(messageSender, ResponseContent.PICK_GOD, MessageStatus.OK, gameManagerSays);
        this.gods = gods;
    }

    public ArrayList<God> getGods() {
        return gods;
    }
}
