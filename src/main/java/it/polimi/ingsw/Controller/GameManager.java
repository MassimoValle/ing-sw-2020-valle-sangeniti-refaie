package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Deck;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageContent;
import it.polimi.ingsw.Network.Server;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class GameManager {

    final int MIN_CONNECTION_IN = 2;
    final int MAX_CONNECTION_IN = 3;

    private final transient Server server;
    private final Game gameInstance;
    private final LobbyManager lobby;
    private transient TurnManager turnManager;
    private static PossibleGameState gameState;


    public GameManager(Server server){
        this.server = server;
        this.lobby = new LobbyManager();
        this.gameInstance = new Game();
        this.turnManager = null;
        this.gameState = PossibleGameState.IN_LOBBY;
    }


    public static void handleMessage(Message message) {

        switch(message.getMessageContent()) {
            case YOUR_TURN: //
            case END_OF_TURN: //
            case WORKER_MOVED: //
            case WORKER_CHOSEN: //
            case PLAYERS_HAS_BUILT: //
        }
    }


    public ArrayList<God> choseGodsFromDeck(int[] indices) {
        ArrayList<God> selectedGods = new ArrayList<>();

        for (int i = 0; i < lobby.getPlayersInLobby().size(); i++) {
            //Mostro a video gli dei disponibili per la scelta
            System.out.println(Deck.getInstance().toString());

            for (int j = 0; j < indices.length; j++) {
                selectedGods.add(Deck.getInstance().getGod(j));
            }
        }

        return selectedGods;
    }
    
    public void startGame(List<String> players) {
        //UPDATE THE GAME STATE
        gameState = PossibleGameState.READY_TO_PLAY;

        //Init players in game
        for (String playerToAdd: players) {
            gameInstance.addPlayer(playerToAdd);
        }

        gameState = PossibleGameState.GODLIKE_PLAYER_MOMENT;

        Player playerToChoseGods = gameInstance.getPlayers().get(1);








    }

}