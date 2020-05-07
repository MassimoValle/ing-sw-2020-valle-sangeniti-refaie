package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;

public class PickGodRequest extends Request {

    private final God pickedGod;

    public PickGodRequest(String messageSender, God pickedGod) {

        super(messageSender, Dispatcher.SETUP_GAME, RequestContent.PICKED_GOD);
        this.pickedGod = pickedGod;

    }

    public God getPickedGod() {
        return this.pickedGod;
    }
}
