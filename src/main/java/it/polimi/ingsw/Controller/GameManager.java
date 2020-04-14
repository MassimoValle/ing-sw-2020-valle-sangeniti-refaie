package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageContent;
import it.polimi.ingsw.Network.Server;

public class GameManager {

    final int MIN_CONNECTION_IN = 2;
    final int MAX_CONNECTION_IN = 3;

    private final transient Server server;
    private final Game gameInstance;
    private final LobbyManager lobby;
    private transient TurnManager turnManager;


    public GameManager(Server server){
        this.server = server;
        this.lobby = new LobbyManager();
        this.gameInstance = new Game();
        this.turnManager = null;
    }

    /*

    //E mo so cazzi, facciamo girare sto controller
    //TUTTO DEVE PASSARE PER DI QUA! OGNI SINGOLA COSA!
     */
    public void handleMessage(Message message) {
        switch(message.getMessageContent()) {
            case YOUR_TURN: //
            case END_OF_TURN: //
            case WORKER_MOVED: //
            case WORKER_CHOSEN: //
            case PLAYERS_HAS_BUILT: //
        }
    }

}