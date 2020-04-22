package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Action.Action;
import it.polimi.ingsw.Model.Action.BuildAction;
import it.polimi.ingsw.Model.God.Deck;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.*;
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

    private PossibleGameState gameState;


    public GameManager(Server server){
        this.server = server;
        this.lobby = null;
        this.gameInstance = new Game();
        this.turnManager = null;
        this.gameState = PossibleGameState.GAME_INIT;
    }

    private void setGameState(PossibleGameState gameState) {
        this.gameState = gameState;
    }


    //NOI VOGLIAMO CHE LA HANDLE MESSAGE RITORNI UNA RISPOSTA IN SEGUITO AD UNA RICHIESTA
    //UNA SORTA DI TUNNEL SEMPRE APERTO
    public void handleMessage(Message message) {

        //SI PUO' GESTIRE LA COSA COME UNA SORTA DI FAR "HOSTARE" UNA PARTITA AL PLAYER (SOLO SULLA CARTA, NON EFFETTIVAMENTE)

        //FASE INIZIALE CREO LA LOBBY
        //DA DECEIDERE SE: -1)CREARLA SUBITO COL COSTRUTTORE DEL GAME MANAGER OPPURE
        //                 -2)UNA VOLTA RICEVUTO IL PRIMO MESSAGGIO DI RICHIESTA DA UN CLIENT


        //CASO 1: CREO LA LOBBY E AGGIORNO LO STATO DEL GIOCO PossibleGameState.IN_LOBBY

        if (gameState == PossibleGameState.GAME_INIT) {

            //GESTISCO IL !!PRIMO MESSAGGIO!! TI TIPO LOGIN DA UN CLIENT E CREO LA LOBBY
            if (message.getMessageContent() == MessageContent.LOGIN) {
                lobby.handleMessage(message);

                setGameState(PossibleGameState.IN_LOBBY);

            }

        }


        //QUI GESTISCO I FUTURI LOGIN DAI PROSSIMI PLAYER

        //!!!IMPORTANTE SARA' LA LOBBY A DIRMI QUANDO LA PARTITA POTRAÌ INIZIARE
        //Quindi ad esempio nel momento in cui una volta raggiunto il numero di player nella lobby
        //che assumiamo venga settato nel momento in cui venga lancaito il server da riga di comando
        //allora nella lobby partirà un COUNTDOWN (gameState settato a READY_TO_PLAY) ad es 5 secondi
        //successivamente gameState verrà settato a GODLIKE_PLAYER_MOMENT e inizia il setup di gioco

        if (gameState == PossibleGameState.IN_LOBBY ) {

            if (message.getMessageContent() == MessageContent.LOGIN) {
                lobby.handleMessage(message);

            }

        }

        switch(message.getMessageContent()) {

            //CONTROLLO IL PROSEGUO DELLA PARTITA

            case MOVE:
                //FACCIO MUOVERE IL GIOCATORE
                if (gameState == PossibleGameState.READY_TO_PLAY || gameState == PossibleGameState.FIRST_MOVE) {

                    //da cambiare il tipo di ritorno da handle message in Message
                    //return handleMoveAction((MoveRequest) message);
                }

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
        for (String playerToAdd : players) {
            gameInstance.addPlayer(playerToAdd);
        }


    }


    private Response handleMoveAction(MoveRequest request) {
        Player activePlayer = turnManager.getActivePlayer();
        Worker activeWorker = turnManager.getActiveWorker();

        Action buildAction = new BuildAction(activePlayer, activeWorker, request.getSenderMovePosition());


        if (buildAction.isValid()) {
            buildAction.doAction();
        } else {
            return buildNegativeResponse("Action not allowed!");
        }


        setGameState(PossibleGameState.ACTION_DONE);
        return buildPositiveResponse("Action done!");
    }



    private Response buildNegativeResponse(String gameManagerSays) {

        String activePlayerUsername = turnManager.getActivePlayer().getPlayerName();
        return new Response(activePlayerUsername, gameManagerSays, MessageStatus.ERROR);

    }

    private Response buildPositiveResponse(String gameManagaerSays) {

        String activePlayerUsername = turnManager.getActivePlayer().getPlayerName();
        return new Response(activePlayerUsername, gameManagaerSays , MessageStatus.OK);
    }

}