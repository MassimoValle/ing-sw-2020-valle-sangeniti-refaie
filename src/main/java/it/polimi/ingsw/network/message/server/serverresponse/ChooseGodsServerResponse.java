package it.polimi.ingsw.network.message.server.serverresponse;

import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.network.message.Enum.ResponseContent;

public class ChooseGodsServerResponse extends ServerResponse {


    public ChooseGodsServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.CHOOSE_GODS, messageStatus, gameManagerSays);
    }

}
