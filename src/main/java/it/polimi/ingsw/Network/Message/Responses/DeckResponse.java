package it.polimi.ingsw.Network.Message.Responses;

import it.polimi.ingsw.Model.God.Deck;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class DeckResponse extends Response {

    private Deck deck;

    public DeckResponse(String messageSender, MessageStatus messageStatus, String gameManagerSays, Deck deck) {
        super(messageSender, ResponseContent.CHOOSE_GODS, messageStatus, gameManagerSays);
        this.deck = deck;
    }

    public Deck getDeck() {
        return deck;
    }
}
