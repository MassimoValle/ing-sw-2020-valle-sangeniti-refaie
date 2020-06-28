package it.polimi.ingsw.Client.View.Gui;

import it.polimi.ingsw.Client.Controller.ClientManager;
import it.polimi.ingsw.Client.Controller.PossibleClientAction;
import it.polimi.ingsw.Client.GUImain;
import it.polimi.ingsw.Client.Model.BabyGame;
import it.polimi.ingsw.Client.View.ClientView;
import it.polimi.ingsw.Client.View.Gui.ViewControllers.MainViewController;
import it.polimi.ingsw.Client.View.Gui.ViewControllers.PickGodController;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Network.Message.Server.ServerResponse.SelectWorkerServerResponse;
import it.polimi.ingsw.Network.Message.Server.ServerResponse.ServerResponse;
import it.polimi.ingsw.Server.Model.God.Deck;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class GUI extends ClientView {

    private final boolean enaPopup = false;
    private final boolean mainPlayer = false;
    private Worker selectedWorker = null;

    private final ParameterListener parameterListener;

    private MainViewController controller;

    public GUI(){
        parameterListener = ParameterListener.getInstance();
    }


    private void waitingOpponents(){
        try {
            GUImain.setRoot("waiting", null);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public String askIpAddress() {

        try {
            GUImain.setRoot("askIpAddr", null);
        }catch (IOException e){
            e.printStackTrace();
        }

        while (ParameterListener.getParameter() == null){

            synchronized (parameterListener){
                try {
                    parameterListener.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        String ipAddress = (String) ParameterListener.getParameter();
        parameterListener.setToNull();

        return ipAddress;
    }

    @Override
    public String askUserName() {

        try {
            GUImain.setRoot("askUsername", null);
        }catch (IOException e){
            e.printStackTrace();
        }

        while (ParameterListener.getParameter() == null){

            synchronized (parameterListener){
                try {
                    parameterListener.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        String username = (String) ParameterListener.getParameter();
        parameterListener.setToNull();

        // se 1o player allora non fare la wait
        waitingOpponents();

        return username;
    }

    @Override
    public int askNumbOfPlayer() {

        try {
            GUImain.setRoot("askLobbySize", null);
        }catch (IOException e){
            e.printStackTrace();
        }

        while (ParameterListener.getParameter() == null){

            synchronized (parameterListener){
                try {
                    parameterListener.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        int ret = (int) ParameterListener.getParameter();
        parameterListener.setToNull();

        if(!mainPlayer)
            waitingOpponents();

        return ret;
    }

    @Override
    public void youAreNotTheGodLikePlayer(String godLikePlayer) {

    }

    @Override
    public void youAreTheGodLikePlayer() {

    }

    @Override
    public void showDeck() {
        try {
            GUImain.setRoot("showDeck", null);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<God> selectGods(int howMany) {
        ArrayList<God> godsChosen = new ArrayList<>();
        int godsChosenNum = 0;

        do {

            while (ParameterListener.getParameter() == null){

                synchronized (parameterListener){
                    try {
                        parameterListener.wait();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }

            Deck deck = BabyGame.getInstance().getDeck();

            God god = deck.getGodByName((String) ParameterListener.getParameter());
            System.out.println("You choose " + god.getGodName() + "!");
            godsChosen.add(god);
            godsChosenNum++;

            parameterListener.setToNull();

        }while (godsChosenNum < howMany);

        waitingOpponents();

        return godsChosen;
    }

    @Override
    public void errorWhileChoosingGods(String gameManagerSays) {
        if(!enaPopup) return;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("There was a problem with the Gods you selected");
        alert.setContentText("Game Manager says: " + gameManagerSays + "Please select the correct Gods");

        alert.showAndWait();
    }

    @Override
    public void godsSelectedSuccesfully() {
        Platform.runLater(this::print_godsSelectedSuccesfully);
    }

    private void print_godsSelectedSuccesfully(){
        if(!enaPopup) return;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Gods selected successfully");

        alert.showAndWait();
    }


    @Override
    public God pickFromChosenGods(ArrayList<God> hand) {

        try {
            GUImain.setRoot("pickGod", null);
            FXMLLoader fxmlLoader = GUImain.getFXMLLoader();
            PickGodController controller = fxmlLoader.getController();
            controller.setHand(hand);

        }catch (IOException e){
            e.printStackTrace();
        }

        while (ParameterListener.getParameter() == null){

            synchronized (parameterListener){
                try {
                    parameterListener.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        God ret = null;

        String godName = (String) ParameterListener.getParameter();
        for(God god : hand){
            if(god.getGodName().equals(godName))
                ret = god;
        }
        parameterListener.setToNull();

        waitingOpponents();

        return ret;
    }

    @Override
    public void errorWhilePickinUpGod(String gameManagerSays) {
        if(!enaPopup) return;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("There was a problem with the God you selected");
        alert.setContentText("Game Manager says: " + gameManagerSays + "Please select the correct God");

        alert.showAndWait();
    }

    @Override
    public void godPickedUpSuccessfully() {
        Platform.runLater(this::print_godPickedUpSuccessfully);
    }

    private void print_godPickedUpSuccessfully(){
        if(!enaPopup) return;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("God picked successfully");

        alert.showAndWait();
    }

    @Override
    public void showAllPlayersInGame(Set<Player> playerSet) {

        try {
            GUImain.setRoot("main2", null);
            FXMLLoader fxmlLoader = GUImain.getFXMLLoader();
            controller = fxmlLoader.<MainViewController>getController();
            controller.setPlayers(playerSet);
            controller.init();

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public Position placeWorker(String worker) {

        while (ParameterListener.getParameter() == null){

            synchronized (parameterListener){
                try {
                    parameterListener.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        Position ret = (Position) ParameterListener.getParameter();
        parameterListener.setToNull();

        return ret;

    }

    @Override
    public void errorWhilePlacingYourWorker(String gameManagerSays) {
        if(!enaPopup) return;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("There was a problem with the worker you wanted to place");
        alert.setContentText("Game Manager says: " + gameManagerSays + "Please try place it again");

        alert.showAndWait();
    }

    @Override
    public void workerPlacedSuccesfully() {
        if(!enaPopup) return;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Worker placed successfully!");

        alert.showAndWait();
    }

    @Override
    public void startingTurn() {
        Platform.runLater(this::print_startingTurn);
    }

    private void print_startingTurn(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("It's your turn!");

        alert.showAndWait();
    }

    @Override
    public int selectWorker() {

        if (selectedWorker == null) {

            while (ParameterListener.getParameter() == null) {

                synchronized (parameterListener) {
                    try {
                        parameterListener.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        Position pos = (Position) ParameterListener.getParameter();

        selectedWorker = BabyGame.getInstance().getClientMap().getWorkerOnSquare(pos);

        controller.selectWorker(selectedWorker);

        parameterListener.setToNull();

        return selectedWorker.getNumber();
    }

    @Override
    public void errorWhileSelectingWorker(String gameManagerSays) {
        if(!enaPopup) return;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("There was a problem with the worker you selected");
        alert.setContentText("Game Manager says: " + gameManagerSays + "Please select another worker");

        alert.showAndWait();
    }

    @Override
    public void workerSelectedSuccessfully() {
        if(!enaPopup) return;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Worker selected succesfully!");

        alert.showAndWait();
    }

    @Override
    public PossibleClientAction choseActionToPerform(List<PossibleClientAction> possibleActions) {

        while (ParameterListener.getParameter() == null){

            synchronized (parameterListener){
                try {
                    parameterListener.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        Object parameter = ParameterListener.getParameter();


        if(parameter instanceof String){
            if(parameter.equals("power")) {
                parameterListener.setToNull();
                return PossibleClientAction.POWER_BUTTON;
            }
        }
        else
        if(parameter instanceof Position){

            for (Worker worker : ClientManager.me.getPlayerWorkers()){
                if(worker.getPosition().equals(parameter)) {

                    controller.deselectWorker(selectedWorker);

                    selectedWorker = BabyGame.getInstance().getClientMap().getWorkerOnSquare((Position) parameter);

                    controller.selectWorker(selectedWorker);

                    parameterListener.setToNull();
                    return PossibleClientAction.SELECT_WORKER;
                }
            }

        }

        return PossibleClientAction.MOVE;

    }

    @Override
    public void errorWhileActivatingPower(String gameManagerSays) {
        if(!enaPopup) return;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("There was a problem with the power you want to use");
        alert.setContentText("Game Manager says: " + gameManagerSays + "Please do a different action");

        alert.showAndWait();
    }

    @Override
    public void powerActivated(God god) {
        if(!enaPopup) return;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Power activated!");
        alert.setContentText("Now you can:  " + god.getGodPower().getPowerDescription());

        alert.showAndWait();
    }

    @Override
    public Position moveWorker(ArrayList<Position> nearlyPosValid) {

        //controller.enablePosition(nearlyPosValid);

        while (ParameterListener.getParameter() == null){

            synchronized (parameterListener){
                try {
                    parameterListener.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }


        Position ret = (Position) ParameterListener.getParameter();
        parameterListener.setToNull();

        //controller.disablePosition();

        return ret;

    }

    @Override
    public void errorWhileMovingWorker(String gameManagerSays) {

        if(!enaPopup) return;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("There was a problem with the move you performed");
        alert.setContentText("Game Manager says: " + gameManagerSays + "Please move again");

        alert.showAndWait();
    }

    @Override
    public boolean wantMoveAgain() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Do you want to move again?");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeTwo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeOne) {
            return true;
        } else { //if (result.get() == buttonTypeTwo) {
            return false;
        }
    }

    @Override
    public void printCanMoveAgain(String gameManagerSays) {

        System.out.println(gameManagerSays);
        System.out.println();

    }

    @Override
    public void workerMovedSuccessfully() {

        System.out.println("Worker moved successfully!");
        System.out.println();

    }

    @Override
    public void endMoveRequestError(String gameManagerSays) {

        if(!enaPopup) return;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("End Move Request Error");
        alert.setContentText("Game Manager says: " + gameManagerSays);

        alert.showAndWait();

    }

    @Override
    public void endMovingPhase(String gameManagerSays) {

        System.out.println(gameManagerSays);
        System.out.println();

    }

    @Override
    public Position build(ArrayList<Position> possiblePosToBuild) {

        //controller.enablePosition(possiblePosToBuild);

        while (ParameterListener.getParameter() == null){

            synchronized (parameterListener){
                try {
                    parameterListener.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        Position ret = (Position) ParameterListener.getParameter();
        parameterListener.setToNull();

        controller.deselectWorker(selectedWorker);
        selectedWorker = null;

        //controller.disablePosition();

        return ret;

    }

    @Override
    public boolean wantBuildAgain() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Do you want to build again?");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeTwo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeOne) {
            return true;
        } else { //if (result.get() == buttonTypeTwo) {
            return false;
        }

    }

    @Override
    public void printCanBuildAgain(String gameManagerSays) {

        System.out.println(gameManagerSays);
        System.out.println();

    }

    @Override
    public void errorWhileBuilding(String gameManagerSays) {

        if(!enaPopup) return;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("There was a problem with the built you performed");
        alert.setContentText("Game Manager says: " + gameManagerSays + "Please build again");

        alert.showAndWait();

    }

    @Override
    public void builtSuccessfully() {

        if(!enaPopup) return;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Built successfully!");

        alert.showAndWait();

    }

    @Override
    public void endBuildRequestError(String gameManagerSays) {

        if(!enaPopup) return;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("End Build Request Error");
        alert.setContentText("Game Manager says: " + gameManagerSays);

        alert.showAndWait();

    }

    @Override
    public void endBuildingPhase(String gameManagerSays) {

        if(!enaPopup) return;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("End Building Phase");
        alert.setContentText("Game Manager says: " + gameManagerSays);

        alert.showAndWait();

    }

    @Override
    public void endTurn() {

        if(!enaPopup) return;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Ending turn!");

        alert.showAndWait();

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

    }

    @Override
    public void anotherPlayerHasMoved(String turnOwner) {

    }

    @Override
    public void anotherPlayerHasBuilt(String turnOwner) {

    }

    @Override
    public void doNothing() {

    }

    @Override
    public void youWon() {

        if(!enaPopup) return;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("YOU WIN");

        alert.showAndWait();

    }

    @Override
    public void iLost() {

    }

    @Override
    public void someoneHasLost(String loser) {

    }

    @Override
    public void youLose(String winner) {

        if(!enaPopup) return;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("YOU LOSE");

        alert.showAndWait();

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
            //ex.printStackTrace();
            run();
        }
    }
}
