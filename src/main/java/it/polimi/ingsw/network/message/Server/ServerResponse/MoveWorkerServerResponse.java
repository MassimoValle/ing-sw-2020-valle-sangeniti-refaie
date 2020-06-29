package it.polimi.ingsw.network.message.Server.ServerResponse;

import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.network.message.Enum.ResponseContent;

public class MoveWorkerServerResponse extends ServerResponse {


    public MoveWorkerServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.MOVE_WORKER, messageStatus, gameManagerSays);
    }

    public MoveWorkerServerResponse(ResponseContent responseContent,MessageStatus messageStatus, String gameManagerSays) {
        super(responseContent, messageStatus, gameManagerSays);
    }

}
