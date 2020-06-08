package it.polimi.ingsw.Client.View.Gui;

import it.polimi.ingsw.Client.Controller.PossibleClientAction;
import it.polimi.ingsw.Client.GUImain;
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
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GUI extends ClientView {

    private final boolean enaPopup = false;
    private final boolean mainPlayer = false;

    private final ParameterListener parameterListener;

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

            God god = Deck.getInstance().getGodByName((String) ParameterListener.getParameter());
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
            GUImain.setRoot("mainView", null);
            FXMLLoader fxmlLoader = GUImain.getFXMLLoader();
            MainViewController controller = fxmlLoader.<MainViewController>getController();
            controller.setPlayers(playerSet);
            controller.init();

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

        parameterListener.setToNull();

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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("It's your turn!");
    }

    @Override
    public int selectWorker() {

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

        return ret;
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
        return null;
    }

    @Override
    public void errorWhileActivatingPower(String gameManagerSays) {

    }

    @Override
    public void powerActivated(God god) {

    }

    @Override
    public Position moveWorker(ArrayList<Position> nearlyPosValid) {
        return null;
    }

    @Override
    public void errorWhileMovingWorker(String gameManagerSays) {

    }

    @Override
    public boolean wantMoveAgain() {
        return false;
    }

    @Override
    public void printCanMoveAgain(String gameManagaerSays) {

    }

    @Override
    public void workerMovedSuccessfully() {

    }

    @Override
    public void endMoveRequestError(String gameManagerSays) {

    }

    @Override
    public void endMovingPhase(String gameManagerSays) {

    }

    @Override
    public Position build(ArrayList<Position> possiblePosToBuild) {
        return null;
    }

    @Override
    public boolean wantBuildAgain() {
        return false;
    }

    @Override
    public void printCanBuildAgain(String gameManagerSays) {

    }

    @Override
    public void errorWhileBuilding(String gameManagerSays) {

    }

    @Override
    public void builtSuccessfully() {

    }

    @Override
    public void endBuildRequestError(String gameManagerSays) {

    }

    @Override
    public void endBuildingPhase(String gameManagerSays) {

    }

    @Override
    public void endTurn() {

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

    }

    @Override
    public void iLost() {

    }

    @Override
    public void someoneHasLost(String loser) {

    }

    @Override
    public void youLose(String winner) {

    }

    @Override
    public void debug(ServerResponse serverResponse) {

    }

    @Override
    public void showMap(GameMap clientMap) {

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
