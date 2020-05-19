package it.polimi.ingsw.Network.Message.Server;

import it.polimi.ingsw.Client.Controller.Updater;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Message;

public abstract class ServerMessage extends Message implements Updater {

    public ServerMessage() {
        super("### SERVER ###");
    }

    public ServerMessage(MessageStatus messageStatus) {
        super("### SERVER ###", messageStatus);
    }

}
