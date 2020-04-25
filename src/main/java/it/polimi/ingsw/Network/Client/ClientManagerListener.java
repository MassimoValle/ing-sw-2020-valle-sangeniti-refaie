package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Network.Message.Response;

public interface ClientManagerListener {

    public void update(Response response);
}
