package it.polimi.ingsw.network.message.Server.ServerRequests;

import it.polimi.ingsw.network.message.Enum.ServerRequestContent;
import it.polimi.ingsw.server.model.God.God;

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
