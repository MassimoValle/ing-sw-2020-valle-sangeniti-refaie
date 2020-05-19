package it.polimi.ingsw.Network.Message.Server.Responses;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class EndTurnResponse extends Response {

    public EndTurnResponse(String messageSender, MessageStatus messageStatus, String gameManagerSays) {
        super(messageSender, ResponseContent.END_TURN, messageStatus, gameManagerSays);
    }
}
