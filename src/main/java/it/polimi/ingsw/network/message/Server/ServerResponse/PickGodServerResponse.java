package it.polimi.ingsw.network.message.Server.ServerResponse;

import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.network.message.Enum.ResponseContent;

public class PickGodServerResponse extends ServerResponse {


    public PickGodServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.PICK_GOD, messageStatus, gameManagerSays);
    }

}
