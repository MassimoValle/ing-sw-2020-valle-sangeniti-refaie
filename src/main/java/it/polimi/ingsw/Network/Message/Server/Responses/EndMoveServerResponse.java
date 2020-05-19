package it.polimi.ingsw.Network.Message.Server.Responses;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class EndMoveServerResponse extends ServerResponse {

    public EndMoveServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.END_MOVE, messageStatus, gameManagerSays);
    }

}
