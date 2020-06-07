package it.polimi.ingsw.Network.Message.ClientRequests;

import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;

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