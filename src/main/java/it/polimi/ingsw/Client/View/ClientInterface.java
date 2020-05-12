package it.polimi.ingsw.Client.View;

public interface ClientInterface {


    void start();

    String askIpAddress();

    String askUserName();

    int askNumbOfPlayer();

    void showDeck();

    void showChosenGods();

    void pickFromChosenGods();

    void placeWorker();


}
