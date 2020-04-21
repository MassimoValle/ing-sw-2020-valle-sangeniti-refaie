package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.God.Deck;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Server;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    final int MIN_CONNECTION_IN = 2;
    final int MAX_CONNECTION_IN = 3;

    private static transient Server server;
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
                break;
            case END_OF_TURN: //
                break;
            case WORKER_MOVED: //
                break;
            case WORKER_CHOSEN: //
                break;
            case PLAYERS_HAS_BUILT: //
                break;
            case CHECK:
                // broadcast
                server.broadcast(message);
                break;
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