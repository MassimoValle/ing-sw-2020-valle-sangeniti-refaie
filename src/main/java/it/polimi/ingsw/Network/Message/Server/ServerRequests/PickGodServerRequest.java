package it.polimi.ingsw.Network.Message.Server.ServerRequests;

import it.polimi.ingsw.Network.Message.Enum.ServerRequestContent;
import it.polimi.ingsw.Server.Model.God.God;

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
