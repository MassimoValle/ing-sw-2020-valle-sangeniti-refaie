package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;

public class PickGodRequest extends Request {

    private final God pickedGod;

    public PickGodRequest(String messageSender, MessageStatus messageStatus, God pickedGod) {

        super(messageSender, Dispatcher.SETUP_GAME, MessageContent.PICK_GOD, messageStatus);
        this.pickedGod = pickedGod;

    }

    public God getPickedGod() {
        return this.pickedGod;
    }
}
