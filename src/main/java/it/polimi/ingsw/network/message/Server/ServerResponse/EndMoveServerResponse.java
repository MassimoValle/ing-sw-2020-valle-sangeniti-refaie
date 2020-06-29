package it.polimi.ingsw.network.message.Server.ServerResponse;

import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.network.message.Enum.ResponseContent;

public class EndMoveServerResponse extends ServerResponse {

    public EndMoveServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.END_MOVE, messageStatus, gameManagerSays);
    }

}
