package it.polimi.ingsw.Network.Message.Server.ServerRequests;

import it.polimi.ingsw.Network.Message.Enum.ServerRequestContent;

public class StartTurnServerRequest extends ServerRequest{

    public StartTurnServerRequest() {
        super(ServerRequestContent.START_TURN);
    }

}
