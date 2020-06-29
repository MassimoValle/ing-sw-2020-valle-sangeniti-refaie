package it.polimi.ingsw.network.message.clientrequests;

import it.polimi.ingsw.server.model.God.God;
import it.polimi.ingsw.network.message.Enum.Dispatcher;
import it.polimi.ingsw.network.message.Enum.RequestContent;

public class PickGodRequest extends Request {

    private final God pickedGod;

    public PickGodRequest(String messageSender, God pickedGod) {

        super(messageSender, Dispatcher.SETUP_GAME, RequestContent.PICK_GOD);
        this.pickedGod = pickedGod;

    }

    public God getPickedGod() {
        return this.pickedGod;
    }

    public God getGod() {
        return pickedGod;
    }
}
