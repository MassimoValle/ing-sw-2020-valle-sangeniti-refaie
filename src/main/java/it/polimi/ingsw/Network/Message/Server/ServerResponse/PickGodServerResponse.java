package it.polimi.ingsw.Network.Message.Server.ServerResponse;

import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

import java.util.ArrayList;

public class PickGodServerResponse extends ServerResponse {


    public PickGodServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.PICK_GOD, messageStatus, gameManagerSays);
    }

}
