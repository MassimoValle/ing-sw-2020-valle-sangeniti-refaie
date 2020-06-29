package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.model.BabyGame;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.message.clientrequests.*;
import it.polimi.ingsw.network.message.Enum.ResponseContent;
import it.polimi.ingsw.network.message.Server.ServerResponse.*;
import it.polimi.ingsw.network.message.Server.ServerMessage;
import it.polimi.ingsw.network.message.Server.ServerRequests.*;
import it.polimi.ingsw.network.message.Server.UpdateMessage.*;
import it.polimi.ingsw.server.model.God.God;
import it.polimi.ingsw.server.model.Player.Player;
import it.polimi.ingsw.server.model.Player.Position;
import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.network.message.Enum.Dispatcher;
import it.polimi.ingsw.network.message.Enum.RequestContent;
import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.server.model.Player.Worker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The type Client manager.
 */
public class ClientManager {

    public static ClientView clientView = null;

    private PossibleClientState clientState;

    // model
    public static Player me;
    private final BabyGame babyGame;

    //Its use is intended to deal with message when the player is not the turn owner
    private final ServerMessageManager serverMessageManager;

    private final ClientBoardUpdater clientBoardUpdater;

    // variabili di controllo
    private Integer workerSelected;
    private boolean myTurn = false;
    private boolean canMoveAgain = false;
    private boolean canBuildAgain = false;
    private boolean playerHasBuilt = false;
    private boolean playerHasMoved = false;
    private boolean powerUsed = false;
    private int workerPlaced = 0;
    private boolean workersPlaced = false;




    ServerMessage currentMessage = null;            //Qualsiasi messaggio che il client riceve dal server
    Request currentRequest = null;                  //la richiesta corrente inviata da questo client
    ServerRequest currentServerRequest = null;      //la richiesta corrente che il client sta gestendo





    /**
     * Instantiates a new Client manager.
     *
     * @param clientView the client view
     */
    public ClientManager(ClientView clientView){

        ClientManager.clientView = clientView;

        this.serverMessageManager = new ServerMessageManager();

        this.babyGame = BabyGame.getInstance();

        this.clientBoardUpdater = new ClientBoardUpdater(babyGame);

        clientState = PossibleClientState.LOGIN;
    }


    /**
     * Call the {@link ClientManager#takeMessage(ServerMessage)} and execute the instructions inside the message
     *
     * @param serverMessage the server message
     */
    public void handleMessageFromServer(ServerMessage serverMessage){
        takeMessage(serverMessage);
    }

    //mette il messaggio in arrivo in current message e
    // setta il client sul messaggio per poter fare l'azione desiderata sul client
    private void takeMessage(ServerMessage updateServerMessage) {
        this.currentMessage = updateServerMessage;
        currentMessage.setClientManager(this);
        currentMessage.update();

    }




    /**
     * Whenever a {@link ServerRequest} is received by the client it will be handled by this method
     *
     * @param serverRequest the server request
     */
    public void handleServerRequest(ServerRequest serverRequest) {
        currentServerRequest = serverRequest;


        if(serverRequest instanceof StartTurnServerRequest && messageForMe(serverRequest) ) {
            startTurn((StartTurnServerRequest) serverRequest);
            return;
        } else if (!messageForMe(serverRequest)) {
            serverMessageManager.serverRequestNotForYou(serverRequest);
            return;
        }


        switch (serverRequest.getContent()) {

            case CHOOSE_GODS_SERVER_REQUEST -> chooseGodFromDeck((ChooseGodsServerRequest) serverRequest);

            case PICK_GOD -> pickGod((PickGodServerRequest) serverRequest);

            case PLACE_WORKER -> placeWorker((PlaceWorkerServerRequest) serverRequest);

            case START_TURN -> startTurn((StartTurnServerRequest) serverRequest);

            case SELECT_WORKER -> selectWorker();

            case MOVE_WORKER -> handleMoveWorkerServerRequest((MoveWorkerServerRequest) serverRequest);

            case BUILD -> handleBuildServerRequest((BuildServerRequest) serverRequest);

            case END_TURN -> endTurn();
        }

    }

    private void handleMoveWorkerServerRequest(MoveWorkerServerRequest serverRequest) {
        if (canMoveAgain && !clientView.wantMoveAgain()) {
            canMoveAgain = false;
            sendEndMoveRequest();
            return;
        }

        clientState = PossibleClientState.MOVING_WORKER;

        //TODO: da controllare sul client se ha la possibilità di fare una PowerButtonRequest

        //Prendiamo tutte le azioni disponibili che ha il giocatore
        ArrayList<PossibleClientAction> possibleActionList = (ArrayList<PossibleClientAction>) getPossibleActionBeforeMoving();

        PossibleClientAction actionToDo;

        if (possibleActionList.size() > 1) {
            actionToDo = whatToDo(possibleActionList);
        } else {
            actionToDo = possibleActionList.get(0);
        }

        switch (actionToDo) {
            case SELECT_WORKER -> selectWorker();
            case MOVE -> moveWorker(serverRequest);
            case POWER_BUTTON -> usePowerButton();
        }

    }

    private void handleBuildServerRequest(BuildServerRequest serverRequest) {

        if (canBuildAgain && !clientView.wantBuildAgain()) {
            canBuildAgain = false;
            sendEndBuildRequest();
            return;
        }

        clientState = PossibleClientState.BUILDING;

        //TODO: da controllare sul client se ha la possibilità di fare una PowerButtonRequest

        //Prendiamo tutte le azioni disponibili che ha il giocatore
        ArrayList<PossibleClientAction> possibleActionList = (ArrayList<PossibleClientAction>) getPossibleActionBeforeBuilding();

        PossibleClientAction actionToDo;

        if (possibleActionList.size() > 1) {
            actionToDo = whatToDo(possibleActionList);
        } else {
            actionToDo = possibleActionList.get(0);
        }

        switch (actionToDo) {
            case SELECT_WORKER -> selectWorker();
            case POWER_BUTTON -> usePowerButton();
            case BUILD -> build(serverRequest);
        }



    }



    /**
     * It handles the {@link ServerResponse} from the Server based on previous {@link Request} by the client
     *
     * @param serverResponse the server response
     */
    public void handleServerResponse(ServerResponse serverResponse) {
        MessageStatus responseStatus = serverResponse.getResponseStatus();
        ResponseContent responseContent = serverResponse.getResponseContent();

        if (responseContent == ResponseContent.PLAYER_HAS_LOST && !myTurn) {
            someoneHasLost((LostServerResponse) serverResponse);
            return;
        }

        if (!myTurn && responseContent != ResponseContent.LOGIN && responseContent != ResponseContent.NUM_PLAYER) {
            serverMessageManager.serverResponseNotForYou(serverResponse);
            return;
        }


        if(responseContent != null) {

            switch (responseContent){
                case LOGIN -> {
                    if(responseStatus == MessageStatus.OK) {
                        clientState = PossibleClientState.LOGIN_DONE;
                    }
                    else login();
                }

                case NUM_PLAYER                         -> chooseNumPlayers();

                case CHOOSE_GODS                        -> handleChooseGodsServerResponse((ChooseGodsServerResponse) serverResponse);
                case PICK_GOD                           -> handlePickGodServerResponse((PickGodServerResponse) serverResponse);
                case PLACE_WORKER                       -> handlePlaceWorkerServerResponse((PlaceWorkerServerResponse) serverResponse);

                case SELECT_WORKER                      -> handleSelectWorkerServerResponse((SelectWorkerServerResponse) serverResponse);

                case POWER_BUTTON                       -> handlePowerButtonServerResponse((PowerButtonServerResponse) serverResponse);

                case MOVE_WORKER, MOVE_WORKER_AGAIN     -> handleMoveWorkerServerResponse((MoveWorkerServerResponse) serverResponse);
                case END_MOVE                           -> handleEndMoveServerResponse((EndMoveServerResponse) serverResponse);

                case BUILD, BUILD_AGAIN                 -> handleBuildServerResponse((BuildServerResponse) serverResponse);
                case END_BUILD                          ->handleEndBuildServerResponse((EndBuildServerResponse) serverResponse);

                case PLAYER_HAS_WON                     -> playerWon((WonServerResponse) serverResponse);
                case PLAYER_HAS_LOST                    -> playerLost((LostServerResponse) serverResponse);
            }
        }
    }


    /**
     * Ask a name and send a {@link LoginRequest} to the Server
     */
    public void login(){

        me = new Player(askUsername());

        try {
            Client.sendMessage(
                    new LoginRequest(me.getPlayerName())
            );
        }catch (IOException e){
            //e.printStackTrace();
            System.out.println("Server disconnected while sending message to it");
        }



    }

    /**
     * Ask a username to the player
     *
     * @return the username chosen
     */
    private String askUsername() {

        return clientView.askUserName();

    }

    private void chooseNumPlayers(){

        clientState = PossibleClientState.SETTING_UP_PLAYERS;

        Integer num = clientView.askNumbOfPlayer();

        try {
            Client.sendMessage(
                    new SetPlayersRequest(me.getPlayerName(), Dispatcher.SETUP_GAME, RequestContent.NUM_PLAYER, num.toString())
            );
        }catch (IOException e){
            e.printStackTrace();
        }

        //Possibile dividere in modo tale che il server comunichi che effettivamente
        // ha ricevuto il messaggio e abbia settato la partita su n giocatori
        clientState = PossibleClientState.PLAYERS_SET_UP;

    }

    private void chooseGodFromDeck(ChooseGodsServerRequest serverRequest){

        myTurn = true;

        clientView.youAreTheGodLikePlayer();

        clientState = PossibleClientState.CHOOSING_GODS;

        int howMany = serverRequest.getHowMany();

        clientView.showDeck();

        ArrayList<God> godChoosen = clientView.selectGods(howMany);

        try {

            Client.sendMessage(
                    new ChoseGodsRequest(me.getPlayerName(), godChoosen)
            );

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void pickGod(PickGodServerRequest serverRequest) {

        myTurn = true;
        clientState = PossibleClientState.PICKING_UP_GOD;

        ArrayList<God> hand = (ArrayList<God>) serverRequest.getGods();

        God myGod = clientView.pickFromChosenGods(hand);
        me.setPlayerGod(myGod);

        try {
            Client.sendMessage(
                    new PickGodRequest(me.getPlayerName(), myGod)
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void placeWorker(PlaceWorkerServerRequest serverRequest) {

        myTurn = true;

        clientState = PossibleClientState.PLACING_WORKERS;

        Position p = clientView.placeWorker(serverRequest.getWorker().toString());

        try {
            Client.sendMessage(
                    new PlaceWorkerRequest(me.getPlayerName(), serverRequest.getWorker(), p)
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void startTurn(StartTurnServerRequest serverRequest) {

        playerHasMoved = false;
        playerHasBuilt = false;
        powerUsed = false;
        clientView.startingTurn();
        this.myTurn = true;

        clientState = PossibleClientState.STARTING_TURN;
    }

    private void selectWorker(){

        clientState = PossibleClientState.SELECTING_WORKER;

        List<Position> workersPosition = new ArrayList<>();

        for (Worker worker: me.getPlayerWorkers())
            workersPosition.add(worker.getPosition());


        this.workerSelected = clientView.selectWorker(workersPosition);

        try {
            Client.sendMessage(
                    new SelectWorkerRequest(me.getPlayerName(), this.workerSelected)
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * It sends a {@link PowerButtonRequest} to the server
     */
    private void usePowerButton() {

        clientState = PossibleClientState.ACTIVATING_POWER;

        try {
            Client.sendMessage(
                    new PowerButtonRequest(me.getPlayerName())
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void moveWorker(MoveWorkerServerRequest serverRequest){

        Position workerPosition = me.getPlayerWorkers().get(workerSelected).getPosition();
        List<Position> nearlyPositionsValid = babyGame.getClientMap().getReachableAdjacentPlaces(workerPosition);

        Position position = clientView.moveWorker(nearlyPositionsValid);

        try {
            Client.sendMessage(
                    new MoveRequest(me.getPlayerName(), position)
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void sendEndMoveRequest() {
        try {
            Client.sendMessage(
                    new EndMoveRequest(me.getPlayerName())
            );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void build(BuildServerRequest serverRequest) {

        Position myWorkerPosition = me.getPlayerWorkers().get(workerSelected).getPosition();
        ArrayList<Position> nearlyPositionsValid = myWorkerPosition.getAdjacentPlaces();
        nearlyPositionsValid.add(myWorkerPosition);


        Position position = clientView.build(nearlyPositionsValid);

        God playerGod = me.getPlayerGod();
        boolean isAtlas = playerGod.is("Atlas");


        try {
            if(powerUsed && isAtlas) {
                Client.sendMessage(
                        new BuildDomeRequest(me.getPlayerName(), position)
                );
            } else {
                Client.sendMessage(
                        new BuildRequest(me.getPlayerName(), position)
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private void sendEndBuildRequest() {
        try {
            Client.sendMessage(
                    new EndBuildRequest(me.getPlayerName())
            );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void endTurn() {
        this.myTurn = false;
        clientView.endTurn();

        try {
            Client.sendMessage(
                    new EndTurnRequest(me.getPlayerName())
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * It's called when the server inform you that you won
     *
     * @param response
     */
    private void playerWon(WonServerResponse response){
        clientView.youWon();
    }

    /**
     * It's called when the server inform you that you lost (due to being unable to complete a round)
     *
     * @param serverResponse
     */
    private void playerLost(LostServerResponse serverResponse) {
        clientView.iLost();

        //REMOVE THIS PLAYER FROM THE MATCH
        removePlayerFromMatch(me);

        //POSSIAMO FAR SI CHE IL CLIENT POSSA CHIEDERE SE VOGLIA RIMANERE A VEDERE IL PROSEGUIO DELLA PARTITA INVIANDOGLI SEMPRE LE INFORMAZIONI
        //OPPURE POSSIAMO SEMPLICEMENTE TAGLIARLO FUORI
    }


    private void someoneHasLost(LostServerResponse serverResponse) {
        clientView.someoneHasLost(serverResponse.getMessageRecipient());
        removePlayerFromMatch(babyGame.getPlayerByName(serverResponse.getMessageRecipient()));
    }

    private void removePlayerFromMatch(Player playerToRemove) {
        babyGame.getClientMap().removePlayerWorkers(playerToRemove);
        playerToRemove.removeWorkers();
        babyGame.getPlayers().remove(playerToRemove);

    }


    //SERVER RESPONSE
    private void handleChooseGodsServerResponse(ChooseGodsServerResponse serverResponse) {

        if (serverResponse.getMessageStatus() == MessageStatus.ERROR) {
            clientView.errorWhileChoosingGods(serverResponse.getGameManagerSays());
            chooseGodFromDeck((ChooseGodsServerRequest) currentServerRequest);
            return;
        }

        clientView.godsSelectedSuccesfully();
        myTurn = false;
        clientState = PossibleClientState.GODS_CHOSEN;
    }

    private void handlePickGodServerResponse(PickGodServerResponse serverResponse) {

        if (serverResponse.getMessageStatus() == MessageStatus.ERROR) {
            clientView.errorWhilePickinUpGod(serverResponse.getGameManagerSays());
            pickGod((PickGodServerRequest) currentServerRequest);
            return;
        }
        clientView.godPickedUpSuccessfully();
        myTurn = false;
        clientState = PossibleClientState.GOD_ASSIGNED;
    }

    private void handlePlaceWorkerServerResponse(PlaceWorkerServerResponse serverResponse) {

        if (serverResponse.getMessageStatus() == MessageStatus.ERROR) {
            clientView.errorWhilePlacingYourWorker(serverResponse.getGameManagerSays());
            placeWorker((PlaceWorkerServerRequest) currentServerRequest);
            return;
        }

        clientView.workerPlacedSuccesfully();
        workerPlaced++;

        if(workerPlaced == 2) {
            workersPlaced = true;
            myTurn = false;
            clientState = PossibleClientState.WORKERS_PLACED;
        }

    }

    private void handleSelectWorkerServerResponse(SelectWorkerServerResponse serverResponse) {

        if (serverResponse.getMessageStatus() == MessageStatus.ERROR) {
            clientView.errorWhileSelectingWorker(serverResponse.getGameManagerSays());
            selectWorker();
            return;
        }

        clientView.workerSelectedSuccessfully();

        clientState = PossibleClientState.WORKER_SELECTED;
    }

    private void handleMoveWorkerServerResponse(MoveWorkerServerResponse serverResponse) {

        switch (serverResponse.getResponseContent()) {

            case MOVE_WORKER -> {

                if (serverResponse.getMessageStatus() == MessageStatus.ERROR) {
                    clientView.errorWhileMovingWorker(serverResponse.getGameManagerSays());
                    moveWorker((MoveWorkerServerRequest) currentServerRequest);
                    break;
                }

                clientState = PossibleClientState.WORKER_MOVED;

                this.playerHasMoved = true;
                this.canMoveAgain = false;
                clientView.workerMovedSuccessfully();

            }

            case MOVE_WORKER_AGAIN -> {

                clientState = PossibleClientState.WORKER_MOVED;

                this.playerHasMoved = true;
                this.canMoveAgain = true;
                clientView.workerMovedSuccessfully();
                clientView.printCanMoveAgain(serverResponse.getGameManagerSays());

            }
        }
    }

    private void handleEndMoveServerResponse(EndMoveServerResponse serverResponse) {
        if (serverResponse.getMessageStatus() == MessageStatus.ERROR) {
            clientView.endMoveRequestError(serverResponse.getGameManagerSays());
            return;
        }
        clientView.endMovingPhase(serverResponse.getGameManagerSays());
    }

    private void handleBuildServerResponse(BuildServerResponse serverResponse) {

        switch (serverResponse.getResponseContent()) {

            case BUILD -> {

                if (serverResponse.getMessageStatus() == MessageStatus.ERROR) {
                    clientView.errorWhileBuilding(serverResponse.getGameManagerSays());
                    build((BuildServerRequest) currentServerRequest);
                    break;
                }

                playerHasBuilt = true;
                this.canBuildAgain = false;
                clientView.builtSuccessfully();
            }

            case BUILD_AGAIN -> {

                playerHasBuilt = true;
                clientView.builtSuccessfully();
                this.canBuildAgain = true;
                clientView.printCanBuildAgain(serverResponse.getGameManagerSays());
            }

        }
    }

    private void handleEndBuildServerResponse(EndBuildServerResponse serverResponse) {

        if (serverResponse.getMessageStatus() == MessageStatus.ERROR) {
            clientView.endBuildRequestError(serverResponse.getGameManagerSays());
            return;
        }
        clientView.endBuildingPhase(serverResponse.getGameManagerSays());
    }

    /**
     * It check if the {@link PowerButtonRequest} previously sent by the player was correct or not
     *
     * @param serverResponse the server response
     */
    private void handlePowerButtonServerResponse(PowerButtonServerResponse serverResponse) {

        if (serverResponse.getMessageStatus() == MessageStatus.ERROR) {

            clientView.errorWhileActivatingPower(serverResponse.getGameManagerSays());

            if (clientState == PossibleClientState.MOVING_WORKER) {
                handleMoveWorkerServerRequest((MoveWorkerServerRequest) currentServerRequest);
                return;
            }


            if (clientState == PossibleClientState.BUILDING) {
                handleBuildServerRequest((BuildServerRequest) currentServerRequest);
                return;
            }
        }

        powerUsed = true;
        God playerGod = me.getPlayerGod();
        clientView.powerActivated(playerGod);
        clientState = PossibleClientState.POWER_ACTIVATED;

    }






    /**
     * This method provide all the {@link PossibleClientAction Actions} the player can perform when his {@link ClientManager#clientState}
     *  is equal to {@link PossibleClientState#MOVING_WORKER} (action not done yet)
     *
     * @return a {@link List<PossibleClientAction> } containing all the possible actions
     */
    private List<PossibleClientAction> getPossibleActionBeforeMoving() {
        List<PossibleClientAction> possibleActions = new ArrayList<>(
                List.of(PossibleClientAction.MOVE)
        );

        if (!playerHasBuilt && !playerHasMoved) {
            possibleActions.add(PossibleClientAction.SELECT_WORKER);
        }

        if (me.getPlayerGod().getGodPower().canUsePowerBeforeMoving() && !powerUsed)
            possibleActions.add(PossibleClientAction.POWER_BUTTON);

        return possibleActions;
    }

    /**
     * This method provide all the {@link PossibleClientAction Actions} the player can perform when his {@link ClientManager#clientState}
     *  is equal to {@link PossibleClientState#BUILDING} (action not done yet)
     *
     * @return a {@link List<PossibleClientAction> } containing all the possible actions
     */
    private List<PossibleClientAction> getPossibleActionBeforeBuilding() {

        List<PossibleClientAction> possibleActions = new ArrayList<>(
                List.of(PossibleClientAction.BUILD)
        );

        if (!playerHasMoved) {
            possibleActions.add(PossibleClientAction.SELECT_WORKER);
        }

        if (me.getPlayerGod().getGodPower().canUsePowerBeforeBuilding() && !powerUsed)
            possibleActions.add(PossibleClientAction.POWER_BUTTON);

        return possibleActions;
    }

    /**
     * It asks the {@link PossibleClientAction Action} the player want to perform,
     * it is called only when the players has two or more action to choose between
     *
     * @param possibleActions - {@link List<PossibleClientAction> } containing all the available action
     * @return Action - the {@link PossibleClientAction Action} chosen by the player
     */
    private PossibleClientAction whatToDo(List<PossibleClientAction> possibleActions) {

        return clientView.choseActionToPerform(possibleActions);
    }


    /**
     * Check if the username is the messageRecipient
     *
     * @return true if the username equals the message recipient false otherwise
     */
    private boolean messageForMe(ServerMessage serverMessage) {
        return serverMessage.getMessageRecipient().equals(me.getPlayerName());
    }





    //UPDATER

    /**
     * Update player info.
     *
     * @param updatePlayersMessage the update players message
     */
    public void updatePlayerInfo(UpdatePlayersMessage updatePlayersMessage) {
        ArrayList<UpdatePlayersMessage.ClientPlayer> clientPlayers = updatePlayersMessage.getClientPlayers();

        for(UpdatePlayersMessage.ClientPlayer clientPlayer : clientPlayers)
            babyGame.addPlayer(clientPlayer);

        Set<Player> players = babyGame.getPlayers();
        updateMyInfo();

        clientView.showAllPlayersInGame(players);
    }

    /**
     * Update {@link Player} 'me' with the real info
     */
    private void updateMyInfo() {
        if (babyGame.getPlayerByName(me.getPlayerName()) != null)
            me = babyGame.getPlayerByName(me.getPlayerName());
    }

    /**
     * Board update.
     *
     * @param updateBoardMessage the update board message
     */
    public void boardUpdate(UpdateBoardMessage updateBoardMessage) {
        clientBoardUpdater.boardUpdate(updateBoardMessage);
        clientView.showMap(babyGame.getClientMap());
    }


}
