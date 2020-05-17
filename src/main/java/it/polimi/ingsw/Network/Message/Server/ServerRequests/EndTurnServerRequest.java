package it.polimi.ingsw.Network.Message.Server.ServerRequests;

import it.polimi.ingsw.Network.Message.Server.ServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerRequestContent;

public class EndTurnServerRequest extends ServerRequest {

    public EndTurnServerRequest() {
        super(ServerRequestContent.END_TURN);
    }

}
