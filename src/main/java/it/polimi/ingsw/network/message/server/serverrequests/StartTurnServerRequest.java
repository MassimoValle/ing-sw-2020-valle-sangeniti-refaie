package it.polimi.ingsw.network.message.server.serverrequests;

import it.polimi.ingsw.network.message.Enum.ServerRequestContent;

public class StartTurnServerRequest extends ServerRequest{

    public StartTurnServerRequest() {
        super(ServerRequestContent.START_TURN);
    }

}
