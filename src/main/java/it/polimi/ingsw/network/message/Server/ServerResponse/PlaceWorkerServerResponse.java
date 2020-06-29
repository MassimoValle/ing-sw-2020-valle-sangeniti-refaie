package it.polimi.ingsw.network.message.Server.ServerResponse;

import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.network.message.Enum.ResponseContent;

public class PlaceWorkerServerResponse extends ServerResponse {


    public PlaceWorkerServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.PLACE_WORKER, messageStatus, gameManagerSays);
    }

}
