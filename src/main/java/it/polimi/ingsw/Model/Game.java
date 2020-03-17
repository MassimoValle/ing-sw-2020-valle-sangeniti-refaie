package it.polimi.ingsw.Model;

import java.util.List;

public class Game {

    private List<Player> players;
    private CliffPedestral cliffPedestral;
    private Player playerActive;
    private Deck deck;
    private int numberOfPlayers;

    public Game() {
        newGame();
    }


    public God choseGods() { return new God(); };

    public Game newGame() { return new Game(); };

    public Player addPlayer(String name) {
        return new Player(name, choseGods());
    };

    public void checkWin() { };


}
