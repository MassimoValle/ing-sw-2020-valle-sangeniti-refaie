package it.polimi.ingsw.network.message.server.serverresponse;

import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.network.message.Enum.ResponseContent;

public class PowerButtonServerResponse extends ServerResponse {
    public PowerButtonServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.POWER_BUTTON, messageStatus, gameManagerSays);
    }
}
