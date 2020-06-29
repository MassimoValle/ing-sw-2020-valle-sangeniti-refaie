package it.polimi.ingsw.network.message.Server.ServerRequests;

import it.polimi.ingsw.network.message.Enum.ServerRequestContent;

public class EndTurnServerRequest extends ServerRequest {

    public EndTurnServerRequest() {
        super(ServerRequestContent.END_TURN);
    }

}
