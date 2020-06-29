package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.controller.PossibleClientAction;
import it.polimi.ingsw.client.model.BabyGame;
import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.client.view.gui.viewcontrollers.MainViewController;
import it.polimi.ingsw.client.view.gui.viewcontrollers.PickGodController;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.message.Server.ServerResponse.SelectWorkerServerResponse;
import it.polimi.ingsw.network.message.Server.ServerResponse.ServerResponse;
import it.polimi.ingsw.server.model.God.Deck;
import it.polimi.ingsw.server.model.God.God;
import it.polimi.ingsw.server.model.Map.GameMap;
import it.polimi.ingsw.server.model.Player.Player;
import it.polimi.ingsw.server.model.Player.Position;
import it.polimi.ingsw.server.model.Player.Worker;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuiController extends ClientView {

    private static final String ERROR_DIALOG = "Error dialog";
    public static final String INFORMATION_DIALOG = "Information Dialog";


    private static final boolean ENA_POPUP = true;
    private Player me = null;
    private Worker selectedWorker = null;
    private Worker opponentWorkerSelected = null;

    private final ParameterListener parameterListener;
    private MainViewController controller;

    public GuiController(){
        parameterListener = ParameterListener.getInstance();
    }


    private Object getFromUser() {

        while (ParameterListener.getParameter() == null){

            synchronized (parameterListener){
                try {
                    parameterListener.wait();
                }catch (InterruptedException e){
                    //Logger.getLogger(LOGGER).log(Level.SEVERE, "Unexpected error!", e);
                    Thread.currentThread().interrupt();
                }
            }
        }

        Object ret = ParameterListener.getParameter();
        parameterListener.setToNull();

        return ret;
    }

    private void waitingOpponents(){
        GUI.setRoot("waiting");
    }


    @Override
    public String askIpAddress() {

        GUI.setRoot("askIpAddr");

        return (String) getFromUser();
    }

    @Override
    public String askUserName() {

        GUI.setRoot("askUsername");

        String username = (String) getFromUser();

        // se 1o player allora non fare la wait
        waitingOpponents();

        return username;
    }

    @Override
    public int askNumbOfPlayer() {

        GUI.setRoot("askLobbySize");

        int numbOfPlayers = (int) getFromUser();

        waitingOpponents();

        return numbOfPlayers;
    }

    @Override
    public void youAreNotTheGodLikePlayer(String godLikePlayer) {
        // do nothing in GUI
    }

    @Override
    public void youAreTheGodLikePlayer() {
        // do nothing in GUI
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
            godsChosen.add(god);
            godsChosenNum++;

        } while (godsChosenNum < howMany);

        waitingOpponents();

        return godsChosen;
    }

    @Override
    public void errorWhileChoosingGods(String gameManagerSays) {
        if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG);
            alert.setHeaderText("There was a problem with the Gods you selected");
            alert.setContentText("Game Manager says: " + gameManagerSays + "\nPlease select the correct Gods");

            alert.showAndWait();
        });

    }

    @Override
    public void godsSelectedSuccesfully() {
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
        PickGodController controller = fxmlLoader.getController();
        controller.setHand(hand);

        God ret = null;
        String godName = (String) getFromUser();

        for(God god : hand){
            if(god.getGodName().equals(godName))
                ret = god;
        }

        waitingOpponents();

        return ret;
    }

    @Override
    public void errorWhilePickinUpGod(String gameManagerSays) {
        if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG);
            alert.setHeaderText("There was a problem with the God you selected");
            alert.setContentText("Game Manager says: " + gameManagerSays + "\nPlease select the correct God");

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

        parameterListener.setToNull();

        return (Position) getFromUser();

    }

    @Override
    public void errorWhilePlacingYourWorker(String gameManagerSays) {
        if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG);
            alert.setHeaderText("There was a problem with the worker you wanted to place");
            alert.setContentText("Game Manager says: " + gameManagerSays + "\nPlease try place it again");

            alert.showAndWait();
        });

    }

    @Override
    public void workerPlacedSuccesfully() {
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
        if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(INFORMATION_DIALOG);
            alert.setHeaderText("It's your turn!");

            alert.showAndWait();
        });
    }

    @Override
    public int selectWorker(List<Position> workersPositions) {

        parameterListener.setToNull();
        boolean again = false;

        do {
            //Logger.getLogger(LOGGER).log(Level.INFO, "Selecting a worker");

            if(again) {
                parameterListener.setToNull();
                selectedWorker = null;
            }

            if (selectedWorker == null) {

                Position pos = (Position) getFromUser();
                selectedWorker = BabyGame.getInstance().getClientMap().getWorkerOnSquare(pos);
            }

            again = selectedWorker == null || !selectedWorker.getColor().equals(me.getColor());
        }
        while (again);

        //da spostare sulla risposta positiva alla select worker
        selectedWorker.selectedOnGUI();

        parameterListener.setToNull();

        return selectedWorker.getNumber();
    }

    @Override
    public void errorWhileSelectingWorker(String gameManagerSays) {

        if (selectedWorker != null) {
            selectedWorker.deselectedOnGUI();
            selectedWorker = null;
        }

        if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG);
            alert.setHeaderText("There was a problem with the worker you selected");
            alert.setContentText("Game Manager says: " + gameManagerSays + "\nPlease select another worker");

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

        if(possibleActions.contains(PossibleClientAction.POWER_BUTTON)){
            controller.enablePowerButton();
        }
        else controller.disablePowerButton();


        Object parameter = getFromUser();


        if(parameter instanceof String){
            if(parameter.equals("power")) {
                parameterListener.setToNull();
                return PossibleClientAction.POWER_BUTTON;
            }
        }
        else
        if(parameter instanceof Position){

            for (Worker worker : me.getPlayerWorkers()){
                if(worker.getPosition().equals(parameter)) {

                    selectedWorker.deselectedOnGUI();

                    selectedWorker = BabyGame.getInstance().getClientMap().getWorkerOnSquare((Position) parameter);

                    parameterListener.setToNull();
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
        if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG);
            alert.setHeaderText("There was a problem with the power you want to use");
            alert.setContentText("Game Manager says: " + gameManagerSays + "\nPlease do a different action");

            alert.showAndWait();
        });
    }

    @Override
    public void powerActivated(God god) {
        if(!ENA_POPUP) return;

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

        //controller.enablePosition(nearlyPosValid);

        //controller.disablePosition();

        return (Position) getFromUser();

    }

    @Override
    public void errorWhileMovingWorker(String gameManagerSays) {

        if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG);
            alert.setHeaderText("There was a problem with the move you performed");
            alert.setContentText("Game Manager says: " + gameManagerSays + "\nPlease move again");

            alert.showAndWait();
        });
    }

    @Override
    public boolean wantMoveAgain() {

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(INFORMATION_DIALOG);
            alert.setHeaderText("Do you want to move again?");
            alert.setContentText("Choose your option.");

            ButtonType buttonTypeOne = new ButtonType("Yes");
            ButtonType buttonTypeTwo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            Optional<ButtonType> result = alert.showAndWait();

            // setta true se result.get() == buttonTypeOne, altrimenti false
            parameterListener.setParameter(result.get() == buttonTypeOne);
        });

        return (boolean) getFromUser();
    }

    @Override
    public void printCanMoveAgain(String gameManagerSays) {

        //Logger.getLogger(LOGGER).log(Level.INFO, "Player can move again");

    }

    @Override
    public void workerMovedSuccessfully() {

        //Logger.getLogger(LOGGER).log(Level.INFO, "Worker moved successfully");

    }

    @Override
    public void endMoveRequestError(String gameManagerSays) {

        if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG);
            alert.setHeaderText("End Move Request Error");
            alert.setContentText("Game Manager says: " + gameManagerSays);

            alert.showAndWait();
        });

    }

    @Override
    public void endMovingPhase(String gameManagerSays) {

        //Logger.getLogger(LOGGER).log(Level.INFO, gameManagerSays);

    }

    @Override
    public Position build(ArrayList<Position> possiblePosToBuild) {

        //controller.enablePosition(possiblePosToBuild);

        //controller.disablePosition();

        return (Position) getFromUser();

    }

    @Override
    public boolean wantBuildAgain() {

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(INFORMATION_DIALOG);
            alert.setHeaderText("Do you want to build again?");
            alert.setContentText("Choose your option.");

            ButtonType buttonTypeOne = new ButtonType("Yes");
            ButtonType buttonTypeTwo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            Optional<ButtonType> result = alert.showAndWait();

            //setta true se result.get() == buttonTypeOne, altrimenti false

            parameterListener.setParameter(result.orElse(null) == buttonTypeOne);
        });

        return (boolean) getFromUser();

    }

    @Override
    public void printCanBuildAgain(String gameManagerSays) {

        //Logger.getLogger(LOGGER).log(Level.INFO, gameManagerSays);

    }

    @Override
    public void errorWhileBuilding(String gameManagerSays) {

        if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG);
            alert.setHeaderText("There was a problem with the built you performed");
            alert.setContentText("Game Manager says: " + gameManagerSays + "\nPlease build again");

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

        if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG);
            alert.setHeaderText("End Build Request Error");
            alert.setContentText("Game Manager says: " + gameManagerSays);

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
            alert.setContentText("Game Manager says: " + gameManagerSays);

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

    }

    @Override
    public void anotherPlayerIsPickingUpGod(String turnOwner) {

    }

    @Override
    public void anotherPlayerIsPlacingWorker(String turnOwner) {

    }

    @Override
    public void startingPlayerTurn(String turnOwner) {

    }

    @Override
    public void anotherPlayerIsSelectingWorker(String turnOwner) {

    }

    @Override
    public void anotherPlayerIsMoving(String turnOwner) {

    }

    @Override
    public void anotherPlayerIsBuilding(String turnOwner) {

    }

    @Override
    public void anotherPlayerHasSelectedGods(String turnOwner) {

    }

    @Override
    public void anotherPlayerHasPickedUpGod(String turnOwner) {

    }

    @Override
    public void anotherPlayerHasPlacedWorker(String turnOwner) {

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

    }

    @Override
    public void anotherPlayerHasBuilt(String turnOwner) {

    }

    @Override
    public void anotherPlayerHasEndedHisTurn(String turnOwner) {
        if (opponentWorkerSelected != null)
            opponentWorkerSelected.deselectedOnGUI();

        opponentWorkerSelected = null;
    }

    @Override
    public void doNothing() {

    }

    @Override
    public void youWon() {

        if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(INFORMATION_DIALOG);
            alert.setHeaderText("YOU WIN");

            alert.showAndWait();
        });

    }

    @Override
    public void iLost() {

    }

    @Override
    public void someoneHasLost(String loser) {

    }

    @Override
    public void youLose(String winner) {

        if(!ENA_POPUP) return;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(INFORMATION_DIALOG);
            alert.setHeaderText("YOU LOSE");

            alert.showAndWait();
        });

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
        } catch (IOException ex) {
            //Logger.getLogger(LOGGER).log(Level.SEVERE, "Ops, app just crashed", ex);
            run();
        }
    }
}
