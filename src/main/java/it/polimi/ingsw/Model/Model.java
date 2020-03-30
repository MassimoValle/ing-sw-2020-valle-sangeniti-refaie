package it.polimi.ingsw.Model;

import it.polimi.ingsw.View.Observable;

import java.util.List;

public class Model extends Observable<Model> implements Cloneable{

    private List<Player> players;
    private CliffPedestral cliffPedestral;
    private Player playerActive;
    private Deck deck;
    private int numberOfPlayers;

    public Model(){ }


    public /*God*/ void choseGods() { /*return new God();*/ };

    /*public Player addPlayer(String name) {
        return new Player(name, choseGods());
    };*/

    public void checkWin() { };

    @Override
    public Model clone(){
        Model model = new Model();
        model.players = players;
        model.cliffPedestral = cliffPedestral;
        model.playerActive = playerActive;
        model.deck = deck;
        model.numberOfPlayers = numberOfPlayers;
        return model;
    }


}
