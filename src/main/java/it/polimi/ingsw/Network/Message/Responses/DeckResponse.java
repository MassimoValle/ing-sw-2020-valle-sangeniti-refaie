package it.polimi.ingsw.Network.Message.Responses;

import it.polimi.ingsw.Model.God.Deck;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;

public class DeckResponse extends Response {

    Deck deck;

    public DeckResponse(String messageSender, MessageContent messageContent, MessageStatus messageStatus, String gameManagerSays, Deck deck) {
        super(messageSender, messageContent, messageStatus, gameManagerSays);
        this.deck = deck;
    }
}
