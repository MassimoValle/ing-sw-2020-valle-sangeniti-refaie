package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Action.*;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.*;
import it.polimi.ingsw.Network.Server;

import java.util.List;
import java.util.Random;

public class GameManager {

    final int MIN_CONNECTION_IN = 2;
    final int MAX_CONNECTION_IN = 3;

    private static transient Server server;
    private final Game gameInstance;
    private transient TurnManager turnManager;

    private PossibleGameState gameState;
    private boolean veryFirstRound;             //TRUE until ALL players haven't placed their workers

    public Game getGameInstance() {
        return this.gameInstance;
    }

    public PossibleGameState getGameState() {
        return gameState;
    }

    public GameManager(Server server){
        this.server = server;
        this.gameInstance = Game.getInstance();
        this.turnManager = null;
        this.gameState = PossibleGameState.GAME_INIT;
        this.veryFirstRound = true;
    }

    //public only for test purposes
    public void setGameState(PossibleGameState gameState) {
        this.gameState = gameState;
    }

    //public only for test purposes
    //QUESTO METODO DOVREBBE ESSERE INVOCATO UNA VOLTA SCADUTO IL COUNTDOWN DI INIZIO MATCH;
    //MI METTO IN ATTESA CHE IL GODLIKE PLAYER PRESCELTO MI INVII I GOD DA UTILIZZARE NELLA PARTITA
    public Response notifyTheGodLikePlayer() {

        //scegli un player a caso
        Random rand = new Random();
        int n = rand.nextInt(gameInstance.getNumberOfPlayers());
        Player godLikePlayer = gameInstance.getPlayers().get(n);

        initTurnManager(gameInstance, godLikePlayer);

        setGameState(PossibleGameState.GODLIKE_PLAYER_MOMENT);

        //Notifico al player in questione che deve scegliere i god
        return buildPositiveResponse("Let's chose which gods you want to be part of the game!");

        //Costruisco la ChoseGodsRequest sul client e la invio al server che la gestirà nella handleMessage;
    }



    public Response handleMessage(Message message) {
        //SONO PRONTO A PARTIRE ==> INIZIALIZZO IL TURN MANAGER
        if (gameState == PossibleGameState.GODLIKE_PLAYER_MOMENT && veryFirstRound ) {
            return handleGodsChosen((ChoseGodsRequest) message);
        }
        switch(message.getMessageContent()) {
            case GODS_CHOSE:
                if (gameState == PossibleGameState.GODLIKE_PLAYER_MOMENT) {
                    return handleGodsChosen((ChoseGodsRequest) message);
                }
                break;
            //CONTROLLO IL PROSEGUO DELLA PARTITA
            case SELECT_WORKER:
                //Seleziono il worker da muovere/posizionare
                if (gameState == PossibleGameState.PLACING_WORKERS || gameState == PossibleGameState.SELECTING_WORKER ) {
                    return handleSelectWorkerAction((SelectWorkerRequest) message);
                }
                break;
            case PLACE_WORKER:
                //Piazzo i worker del singolo player
                if (gameState == PossibleGameState.PLACING_WORKERS && veryFirstRound) {
                    return handlePlaceWorkerAction((PlaceWorkerRequest) message );
                }
                break;
            case MOVE:
                //FACCIO MUOVERE IL GIOCATORE
                if (gameState == PossibleGameState.READY_TO_PLAY || gameState == PossibleGameState.FIRST_MOVE || gameState == PossibleGameState.WORKER_SELECTED) {
                    return handleMoveAction((MoveRequest) message);
                }
                break;
            case BUILD: //
                if (gameState == PossibleGameState.WORKER_MOVED) {
                    return handleBuildAction((BuildRequest) message);
                }
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
        return buildNegativeResponse("Non si dovrebbe mai arrivare qui");
    }

    public void initTurnManager(Game gameInstance, Player godlikePlayer) {
        this.turnManager = new TurnManager(gameInstance, godlikePlayer);
    }


    /**
     * Assign the {@link God god} chosen by the {@link Player player}
     *
     * @param player player
     * @param god    god
     */
    public void assignGodToPlayer(Player player, God god) {
        god.setAssigned(true);
        player.setPlayerGod(god);
    }
    /*
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

     */


    //Metodo invocato dalla lobby una volta raggiunti i player necessari
    public void addPLayersToGame(List<String> players) {

        //UPDATE THE GAME STATE
        gameState = PossibleGameState.READY_TO_PLAY;

        //Init players in game
        for (String playerToAdd : players) {
            gameInstance.addPlayer(playerToAdd);
        }


    }


    private Response handleGodsChosen(ChoseGodsRequest request) {
        gameInstance.setChosenGodsFromDeck(request.getChosenGod());
        turnManager.setGodsInGame(request.getChosenGod());

        setGameState(PossibleGameState.ACTION_DONE);
        return buildPositiveResponse("Gods selezionati");
    }


    private Response handleSelectWorkerAction(SelectWorkerRequest request) {

        String requestSender = request.getMessageSender();
        Player activePlayer = turnManager.getActivePlayer();
        Worker workerFromRequest = request.getWorkerToSelect();

        //Controllo che il player sia nel suo turno
        if(!checkTurnOwnership(requestSender) ) { //The player who sent the request isn't the playerActive
            return buildNegativeResponse("It's not your turn!");
        }

        //When a handleSelectWorkerRequest occurs the activeWorker in the turn must be set tu null
        //or has to be the other player's worker
        Worker activeWorker = turnManager.getActiveWorker();

        //You get into this statement only if the activeWorker is own by someone else.
        if (activeWorker != null) {
            if (!(activeWorker == activePlayer.getPlayerWorkers().get(0) || activeWorker == activePlayer.getPlayerWorkers().get(1))) {
                return buildNegativeResponse("There's something wrong with the worker selection");
            }
        }



        Action selectWorkerAction = new SelectWorkerAction(activePlayer, workerFromRequest);

        //
        if (!workerFromRequest.isPlaced()) {
            selectWorkerAction.doAction();
        } else if ( workerFromRequest.isPlaced() ) {

            if ( ! gameInstance.getGameMap().isWorkerStuck(workerFromRequest) ) {
                selectWorkerAction.doAction();
            }
            else {
                return buildNegativeResponse("Worker Stuck!");
            }

        } else {
            return buildNegativeResponse("You cannot select this worker");
        }

        turnManager.setActiveWorker(workerFromRequest);
        setGameState(PossibleGameState.ACTION_DONE);
        return buildPositiveResponse("Worker Selected");

    }

    private Response handlePlaceWorkerAction(PlaceWorkerRequest request) {
        Player activePlayer = turnManager.getActivePlayer();
        Worker activeWorker = turnManager.getActiveWorker();
        Position positionToPlaceWorker = request.getPositionToPlaceWorker();

        //Controllo che il worker che il player vuole piazzare sia lo stesso che ha selezionato inizialmente
        if (request.getWorkerToPlace() != activeWorker) {
            return buildNegativeResponse("The worker you want to place isn't the worker selected!");
        }

        //Passo direttamente lo square su cui piazzare il worker dal controller, per ovviare alle getInstance in classi che non c'èentrano nulla
        Square squareWhereToPlaceWorker = gameInstance.getGameMap().getSquare(positionToPlaceWorker);

        Action placeWorkerAction = new PlaceWorkerAction(activePlayer, activeWorker, positionToPlaceWorker, squareWhereToPlaceWorker);

        if (placeWorkerAction.isValid()) {
            placeWorkerAction.doAction();
        } else {
            return buildNegativeResponse("You cannot place the worker here");
        }

        setGameState(PossibleGameState.ACTION_DONE);
        return buildPositiveResponse("Worker Placed");

    }

    private Response handleMoveAction(MoveRequest request) {

        String requestSender = request.getMessageSender();


        Player activePlayer = turnManager.getActivePlayer();
        Worker activeWorker = turnManager.getActiveWorker();
        Position positionWhereToMove = request.getSenderMovePosition();

        //Controllo che il player sia nel suo turno
        if(!checkTurnOwnership(requestSender) ) { //The player who sent the request isn't the playerActive
            return buildNegativeResponse("It's not your turn!");
        }

        Square squareWhereTheWorkerIs = gameInstance.getGameMap().getSquare(activeWorker.getWorkerPosition());
        Square squareWhereToMove = gameInstance.getGameMap().getSquare(positionWhereToMove);

        Action moveAction = new MoveAction(activePlayer, activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);


        if (moveAction.isValid()) {
            moveAction.doAction();
        } else {
            return buildNegativeResponse("You cannot move here!");
        }


        setGameState(PossibleGameState.ACTION_DONE);
        return buildPositiveResponse("Worker Moved!");
    }

    private Response handleBuildAction(BuildRequest request) {

        String requestSender = request.getMessageSender();

        Player activePlayer = turnManager.getActivePlayer();
        Worker activeWorker = turnManager.getActiveWorker();
        Position positionWhereToBuild = request.getPositionWhereToBuild();

        //Controllo che il player sia nel suo turno
        if(!checkTurnOwnership(requestSender) ) { //The player who sent the request isn't the playerActive
            return buildNegativeResponse("It's not your turn!");
        }

        Square squareWhereToBuild = gameInstance.getGameMap().getSquare(positionWhereToBuild);

        Action buildAction = new BuildAction(squareWhereToBuild);


        if (buildAction.isValid()) {
            buildAction.doAction();
        } else {
            return buildNegativeResponse("You cannot move here!");
        }


        setGameState(PossibleGameState.ACTION_DONE);
        return buildPositiveResponse("Worker Moved!");
    }

    /**
     * It checks if the {@link Player#getPlayerName()} is the turn owner
     *
     * @param username the username of the player we want to know if is the turn owner
     *
     * @return true if he is the turn owner, false otherwise
     */
    private boolean checkTurnOwnership(String username) {
        if ( turnManager.getActivePlayer().getPlayerName().equals(username) )
            return true;
        else
            return false;
    }

    private Response buildNegativeResponse(String gameManagerSays) {

        String activePlayerUsername = turnManager.getActivePlayer().getPlayerName();
        return new Response(activePlayerUsername, gameManagerSays, MessageStatus.ERROR);

    }

    private Response buildPositiveResponse(String gameManagaerSays) {

        String activePlayerUsername = turnManager.getActivePlayer().getPlayerName();
        return new Response(activePlayerUsername, gameManagaerSays , MessageStatus.OK);
    }





    //Methods below only used for test Purpose
    public void addPlayerToCurrentGame(String username) {
        gameInstance.addPlayer(username);
    }

    public void setNewActivePlayer(Player player) {
        turnManager.setActivePlayer(player);
    }

    public TurnManager getTurnManager() {
        return this.turnManager;
    }

}