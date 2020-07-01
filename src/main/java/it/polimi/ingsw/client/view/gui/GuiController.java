package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.PossibleClientAction;
import it.polimi.ingsw.client.model.BabyGame;
import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.client.view.gui.viewcontrollers.MainViewController;
import it.polimi.ingsw.client.view.gui.viewcontrollers.PickGodController;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.message.server.serverresponse.SelectWorkerServerResponse;
import it.polimi.ingsw.network.message.server.serverresponse.ServerResponse;
import it.polimi.ingsw.server.model.god.Deck;
import it.polimi.ingsw.server.model.god.God;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.server.model.player.Worker;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class GuiController extends ClientView {

    private static final String ERROR_DIALOG = "Error dialog";
    private static final String INFORMATION_DIALOG = "Information Dialog";
    private static final String GAME_MANAGER_SAYS = "Game Manager says: ";


    private static final boolean ENA_POPUP = false;
    private Player me = null;
    private Worker selectedWorker = null;
    private Worker opponentWorkerSelected = null;

    private final ParameterListener parameterListener;
    private MainViewController controller;

    public GuiController(){
        parameterListener = ParameterListener.getInstance();
    }



    // helper

    private Object getFromUser() {

        while (ParameterListener.getParameter() == null){

            synchronized (parameterListener){
                try {
                    parameterListener.wait();
                }catch (InterruptedException e){
                    ClientManager.LOGGER.severe(e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        }

        return ParameterListener.getParameter();
    }

    private void waitingOpponents(){
        GUI.setRoot("waiting");
    }

    /**
     * Based on the parameter change what to show
     *
     * @param i 1 for move, 2 for build
     */
    private void choicePopup(int i) {

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(INFORMATION_DIALOG);

            if (i == 1)
                alert.setHeaderText("Do you want to move again?");
            else if (i == 2)
                alert.setHeaderText("Do you want to build again?");

            alert.setContentText("Choose your option.");

            ButtonType buttonTypeOne = new ButtonType("Yes");
            ButtonType buttonTypeTwo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            Optional<ButtonType> result = alert.showAndWait();

            //setta true se result.get() == buttonTypeOne, altrimenti false
            parameterListener.setParameter(result.orElse(null) == buttonTypeOne);
        });

    }

    private void lose(){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(INFORMATION_DIALOG);
            alert.setHeaderText("YOU LOSE");
            alert.setContentText("Click OK to quit");

            alert.showAndWait();

            Platform.exit();
            System.exit(0);
        });
    }




    // main methods

    @Override
    public String askIpAddress() {

        GUI.setRoot("askIpAddr");

        String ret = (String) getFromUser();
        ParameterListener.setToNull();

        return ret;
    }

    @Override
    public String askUserName() {

        GUI.setRoot("askUsername");

        String username = (String) getFromUser();
        ParameterListener.setToNull();

        return username;
    }

    @Override
    public int askNumbOfPlayer() {

        GUI.setRoot("askLobbySize");

        int numbOfPlayers = (int) getFromUser();
        ParameterListener.setToNull();

        waitingOpponents();

        return numbOfPlayers;
    }

    @Override
    public void youAreNotTheGodLikePlayer(String godLikePlayer) {
        // do nothing in GUI

        // se 1o player allora non fare la wait
        waitingOpponents();
    }

    @Override
    public void youAreTheGodLikePlayer() {

    }

    @Override
    public void showDeck() {
        GUI.setRoot("showDeck");

    }

    @Override
    public ArrayList<God> selectGods(int howMany) {
        Deck deck = BabyGame.getInstance().getDeck();
        ArrayList<God> godsChosen = new ArrayList<>();
        int godsChosenNum = 0;

        do {

            God god = deck.getGodByName((String) getFromUser());
            ParameterListener.setToNull();
            godsChosen.add(god);
            godsChosenNum++;

        } while (godsChosenNum < howMany);

        waitingOpponents();

        return godsChosen;
    }

    @Override
    public void errorWhileChoosingGods(String gameManagerSays) {
        //if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG);
            alert.setHeaderText("There was a problem with the Gods you selected");
            alert.setContentText(GAME_MANAGER_SAYS + gameManagerSays + "\nPlease select the correct Gods");

            alert.showAndWait();
        });

    }

    @Override
    public void godsSelectedSuccessfully() {
        if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(INFORMATION_DIALOG);
            alert.setHeaderText("Gods selected successfully");

            alert.showAndWait();
        });
    }

    @Override
    public God pickFromChosenGods(ArrayList<God> hand) {

        GUI.setRoot("pickGod");
        FXMLLoader fxmlLoader = GUI.getFXMLLoader();
        PickGodController pickGodController = fxmlLoader.getController();
        pickGodController.setHand(hand);

        God ret = null;
        String godName = (String) getFromUser();
        ParameterListener.setToNull();

        for(God god : hand){
            if(god.getGodName().equals(godName))
                ret = god;
        }

        waitingOpponents();

        return ret;
    }

    @Override
    public void errorWhilePickingUpGod(String gameManagerSays) {
        //if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG);
            alert.setHeaderText("There was a problem with the God you selected");
            alert.setContentText(GAME_MANAGER_SAYS + gameManagerSays + "\nPlease select the correct God");

            alert.showAndWait();
        });

    }

    @Override
    public void godPickedUpSuccessfully() {
        if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(INFORMATION_DIALOG);
            alert.setHeaderText("God picked successfully");

            alert.showAndWait();
        });
    }

    @Override
    public void showAllPlayersInGame(Set<Player> playerSet, Player user) {

        GUI.setRoot("mainView");
        FXMLLoader fxmlLoader = GUI.getFXMLLoader();
        controller = fxmlLoader.getController();
        controller.setPlayers(playerSet);
        this.me = user;
        controller.init();

    }

    @Override
    public Position placeWorker(String worker) {

        controller.changePhase("PlaceWorker");

        ParameterListener.setToNull();

        Position ret = (Position) getFromUser();
        ParameterListener.setToNull();

        return ret;

    }

    @Override
    public void errorWhilePlacingYourWorker(String gameManagerSays) {
        //if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG);
            alert.setHeaderText("There was a problem with the worker you wanted to place");
            alert.setContentText(GAME_MANAGER_SAYS + gameManagerSays + "\nPlease try place it again");

            alert.showAndWait();
        });

    }

    @Override
    public void workerPlacedSuccessfully() {
        if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(INFORMATION_DIALOG);
            alert.setHeaderText("Worker placed successfully!");

            alert.showAndWait();
        });
    }

    @Override
    public void startingTurn() {
        //if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(INFORMATION_DIALOG);
            alert.setHeaderText("It's your turn!");

            alert.showAndWait();
        });
    }

    @Override
    public int selectWorker(List<Position> workersPositions) {

        controller.changePhase("SelectWorker");

        ParameterListener.setToNull();
        boolean again = false;

        do {
            ClientManager.LOGGER.info("Selecting a worker");

            if(again) {
                ParameterListener.setToNull();
                selectedWorker = null;
            }

            if (selectedWorker == null) {

                Position pos = (Position) getFromUser();
                ParameterListener.setToNull();
                selectedWorker = BabyGame.getInstance().getClientMap().getWorkerOnSquare(pos);
            }

            again = selectedWorker == null || !selectedWorker.getColor().equals(me.getColor());
        }
        while (again);

        //da spostare sulla risposta positiva alla select worker
        selectedWorker.selectedOnGUI();

        ParameterListener.setToNull();

        return selectedWorker.getNumber();
    }

    @Override
    public void errorWhileSelectingWorker(String gameManagerSays) {

        if (selectedWorker != null) {
            selectedWorker.deselectedOnGUI();
            selectedWorker = null;
        }

        //if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG);
            alert.setHeaderText("There was a problem with the worker you selected");
            alert.setContentText(GAME_MANAGER_SAYS + gameManagerSays + "\nPlease select another worker");

            alert.showAndWait();
        });

    }

    @Override
    public void workerSelectedSuccessfully() {
        if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(INFORMATION_DIALOG);
            alert.setHeaderText("Worker selected succesfully!");

            alert.showAndWait();
        });
    }

    @Override
    public PossibleClientAction choseActionToPerform(List<PossibleClientAction> possibleActions) {



        ParameterListener.setToNull();

        if(possibleActions.contains(PossibleClientAction.POWER_BUTTON)){
            controller.enablePowerButton();
            controller.changePhase("MultipleAction");
        }
        else {
            controller.disablePowerButton();
            controller.changePhase("MoveWorker");
        }


        Object parameter = getFromUser();
        // don't set to null parameterListener

        if(parameter instanceof String){
            if(parameter.equals("power")) {
                ParameterListener.setToNull();
                return PossibleClientAction.POWER_BUTTON;
            }
        }
        else
        if(parameter instanceof Position){

            for (Worker worker : me.getPlayerWorkers()){
                if(worker.getPosition().equals(parameter)) {

                    selectedWorker.deselectedOnGUI();

                    selectedWorker = BabyGame.getInstance().getClientMap().getWorkerOnSquare((Position) parameter);

                    ParameterListener.setToNull();
                    return PossibleClientAction.SELECT_WORKER;
                }
            }

        }

        possibleActions.removeIf(x -> x.equals(PossibleClientAction.POWER_BUTTON));
        possibleActions.removeIf(x -> x.equals(PossibleClientAction.SELECT_WORKER));

        controller.disablePowerButton();

        return possibleActions.get(0);

    }

    @Override
    public void errorWhileActivatingPower(String gameManagerSays) {
        //if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG);
            alert.setHeaderText("There was a problem with the power you want to use");
            alert.setContentText(GAME_MANAGER_SAYS + gameManagerSays + "\nPlease do a different action");

            alert.showAndWait();
        });
    }

    @Override
    public void powerActivated(God god) {
        //if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(INFORMATION_DIALOG);
            alert.setHeaderText("Power activated!");
            alert.setContentText("Now you can:  " + god.getGodPower().getPowerDescription());

            alert.showAndWait();
        });
    }

    @Override
    public Position moveWorker(List<Position> nearlyPosValid) {

        controller.changePhase("MoveWorker");

        //controller.enablePosition(nearlyPosValid);

        //controller.disablePosition();

        Position ret =  (Position) getFromUser();
        ParameterListener.setToNull();

        return  ret;

    }

    @Override
    public void errorWhileMovingWorker(String gameManagerSays) {

        //if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG);
            alert.setHeaderText("There was a problem with the move you performed");
            alert.setContentText(GAME_MANAGER_SAYS + gameManagerSays + "\nPlease move again");

            alert.showAndWait();
        });
    }

    @Override
    public boolean wantMoveAgain() {


        ParameterListener.setToNull();
        choicePopup(1);

        boolean ret = (boolean) getFromUser();
        ParameterListener.setToNull();

        return ret;
    }

    @Override
    public void printCanMoveAgain(String gameManagerSays) {
        //nothing to show
    }

    @Override
    public void workerMovedSuccessfully() {
        //nothing to show
    }

    @Override
    public void endMoveRequestError(String gameManagerSays) {

        if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG);
            alert.setHeaderText("End Move Request Error");
            alert.setContentText(GAME_MANAGER_SAYS + gameManagerSays);

            alert.showAndWait();
        });

    }

    @Override
    public void endMovingPhase(String gameManagerSays) {
        //nothing to show
    }

    @Override
    public Position build(ArrayList<Position> possiblePosToBuild) {

        controller.changePhase("BuildWorker");

        //controller.enablePosition(possiblePosToBuild);

        //controller.disablePosition();

        Position ret =( Position) getFromUser();
        ParameterListener.setToNull();

        return ret;

    }

    @Override
    public boolean wantBuildAgain() {

        ParameterListener.setToNull();
        choicePopup(2);

        boolean ret = (boolean) getFromUser();
        ParameterListener.setToNull();

        return ret;

    }

    @Override
    public void printCanBuildAgain(String gameManagerSays) {
        //nothing to show
    }

    @Override
    public void errorWhileBuilding(String gameManagerSays) {

        //if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG);
            alert.setHeaderText("There was a problem with the built you performed");
            alert.setContentText(GAME_MANAGER_SAYS + gameManagerSays + "\nPlease build again");

            alert.showAndWait();
        });

    }

    @Override
    public void builtSuccessfully() {

        if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(INFORMATION_DIALOG);
            alert.setHeaderText("Built successfully!");

            alert.showAndWait();
        });

    }

    @Override
    public void endBuildRequestError(String gameManagerSays) {

        //if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG);
            alert.setHeaderText("End Build Request Error");
            alert.setContentText(GAME_MANAGER_SAYS + gameManagerSays);

            alert.showAndWait();
        });

    }

    @Override
    public void endBuildingPhase(String gameManagerSays) {

        if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG);
            alert.setHeaderText("End Building Phase");
            alert.setContentText(GAME_MANAGER_SAYS + gameManagerSays);

            alert.showAndWait();
        });

    }

    @Override
    public void endTurn() {

        selectedWorker.deselectedOnGUI();
        selectedWorker = null;
        controller.disablePowerButton();

        if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(INFORMATION_DIALOG);
            alert.setHeaderText("Ending turn!");

            alert.showAndWait();
        });

    }

    @Override
    public void someoneElseDoingStuff() {
        //nothing to show
    }

    @Override
    public void anotherPlayerIsPickingUpGod(String turnOwner) {
        //nothing to show
    }

    @Override
    public void anotherPlayerIsPlacingWorker(String turnOwner) {
        //nothing to show
    }

    @Override
    public void startingPlayerTurn(String turnOwner) {

        controller.changePhase("NotYourTurn");

    }

    @Override
    public void anotherPlayerIsSelectingWorker(String turnOwner) {
        //nothing to show
    }

    @Override
    public void anotherPlayerIsMoving(String turnOwner) {
        //nothing to show
    }

    @Override
    public void anotherPlayerIsBuilding(String turnOwner) {
        //nothing to show
    }

    @Override
    public void anotherPlayerHasSelectedGods(String turnOwner) {
        //nothing to show
    }

    @Override
    public void anotherPlayerHasPickedUpGod(String turnOwner) {
        //nothing to show
    }

    @Override
    public void anotherPlayerHasPlacedWorker(String turnOwner) {
        //nothing to show
    }

    @Override
    public void anotherPlayerHasSelectedWorker(SelectWorkerServerResponse serverResponse) {
        String playerName = serverResponse.getMessageRecipient();

        int indexWorker = serverResponse.getWorkerSelected()-1;

        if(opponentWorkerSelected != null) opponentWorkerSelected.deselectedOnGUI();

        opponentWorkerSelected = BabyGame.getInstance().getPlayerByName(playerName).getPlayerWorkers().get(indexWorker);

        opponentWorkerSelected.selectedOnGUI();
    }

    @Override
    public void anotherPlayerHasMoved(String turnOwner) {
        //nothing to show
    }

    @Override
    public void anotherPlayerHasBuilt(String turnOwner) {
        //nothing to show
    }

    @Override
    public void anotherPlayerHasEndedHisTurn(String turnOwner) {
        if (opponentWorkerSelected != null)
            opponentWorkerSelected.deselectedOnGUI();

        opponentWorkerSelected = null;
    }

    @Override
    public void youWin() {

        //if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(INFORMATION_DIALOG);
            alert.setHeaderText("YOU WIN");

            alert.showAndWait();

            Platform.exit();
            System.exit(0);
        });

    }

    @Override
    public void iLose() {
        lose();
    }

    @Override
    public void someoneHasLost(String loser) {
        //nothing to show
    }

    @Override
    public void playerLeftTheGame(String user) {
        
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG);
            alert.setHeaderText(user + " left the game\nClick OK to quit");

            alert.showAndWait();

            Platform.exit();
            System.exit(0);
        });
    }

    @Override
    public void closeClient() {
        //nothing to show
    }

    @Override
    public void youLose(String winner) {

        //if(!ENA_POPUP) return;

        lose();

    }

    @Override
    public void debug(ServerResponse serverResponse) {

        // do nothing

    }

    @Override
    public void showMap(GameMap clientMap) {

        // do nothing

    }

    @Override
    public synchronized void run() {
        String ipAddress = askIpAddress();

        Client client = new Client(ipAddress, 8080, this);

        try {
            client.run();
        } catch (EOFException ex) {
            //this is thrown when the end of the stream is reached
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(ERROR_DIALOG);
                alert.setHeaderText("Server disconnected while sending message to it!");

                alert.showAndWait();
            });

        } catch (UnknownHostException ex) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(ERROR_DIALOG);
                alert.setHeaderText("Unknown host, please insert again!");

                alert.showAndWait();
            });

            run();

        } catch (ConnectException ex) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(ERROR_DIALOG);
                alert.setHeaderText("Server refused to connect");

                alert.showAndWait();
            });

            run();

        } catch (IOException ex) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(ERROR_DIALOG);
                alert.setHeaderText("Error server side");

                alert.showAndWait();
            });
            ClientManager.LOGGER.severe(ex.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
