package it.polimi.ingsw.Client.View;

import it.polimi.ingsw.Network.Message.Responses.Response;
import it.polimi.ingsw.Server.Model.God.Deck;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.Player.Position;

import java.util.ArrayList;

public interface ClientInterface {


    void start();

    String askIpAddress();

    String askUserName();

    int askNumbOfPlayer();

    ArrayList<God> selectGodsFromDeck(int howMany, String serverSays);

    God pickFromChosenGods(ArrayList<God> hand);

    Position placeWorker(String worker);

    void debug(Response response);


}
