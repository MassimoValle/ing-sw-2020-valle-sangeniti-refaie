package it.polimi.ingsw.network.message.server.serverrequests;

import it.polimi.ingsw.network.message.Enum.ServerRequestContent;
import it.polimi.ingsw.server.model.god.God;

import java.util.List;

public class PickGodServerRequest extends ServerRequest {

    private final List<God> godsToPick;

    public PickGodServerRequest(List<God> godsToPick) {
        super(ServerRequestContent.PICK_GOD);
        this.godsToPick = godsToPick;
    }

    public List<God> getGods() {
        return godsToPick;
    }
}
