package it.polimi.ingsw.network.message.server.updatemessage;

import it.polimi.ingsw.network.message.server.ServerMessage;

/**
 * The update message contains information on how the game is evolving, every game-related thing on the client change based on this messages
 */
public abstract class UpdateMessage extends ServerMessage {

    public UpdateMessage() {
        super();
    }
}
