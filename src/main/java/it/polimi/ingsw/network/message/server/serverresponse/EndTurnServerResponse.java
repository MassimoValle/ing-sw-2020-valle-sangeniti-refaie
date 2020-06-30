package it.polimi.ingsw.network.message.server.serverresponse;

import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.network.message.Enum.ResponseContent;

public class EndTurnServerResponse extends ServerResponse {

    public EndTurnServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.END_TURN, messageStatus, gameManagerSays);
    }
}
