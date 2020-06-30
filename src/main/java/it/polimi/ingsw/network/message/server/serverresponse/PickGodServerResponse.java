package it.polimi.ingsw.network.message.server.serverresponse;

import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.network.message.Enum.ResponseContent;

public class PickGodServerResponse extends ServerResponse {


    public PickGodServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.PICK_GOD, messageStatus, gameManagerSays);
    }

}
