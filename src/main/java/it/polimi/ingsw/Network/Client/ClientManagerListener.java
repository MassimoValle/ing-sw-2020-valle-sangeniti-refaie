package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Network.Message.Responses.Response;

public interface ClientManagerListener {

    public void update(Response response);
}
