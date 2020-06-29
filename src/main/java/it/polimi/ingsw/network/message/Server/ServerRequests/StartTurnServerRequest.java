package it.polimi.ingsw.network.message.Server.ServerRequests;

import it.polimi.ingsw.network.message.Enum.ServerRequestContent;

public class StartTurnServerRequest extends ServerRequest{

    public StartTurnServerRequest() {
        super(ServerRequestContent.START_TURN);
    }

}
