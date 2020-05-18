package it.polimi.ingsw.Client.View.Gui;

import it.polimi.ingsw.Client.View.ClientView;
import it.polimi.ingsw.Network.Message.Server.Responses.Response;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.Player.Position;

import java.util.ArrayList;

public class GUI extends ClientView {

    public GUI(){

    }

    @Override
    public void start() {

    }

    @Override
    public String askIpAddress() {
        return null;
    }

    @Override
    public String askUserName() {
        return null;
    }

    @Override
    public int askNumbOfPlayer() {
        return 0;
    }

    @Override
    public void showDeck() {

    }

    @Override
    public ArrayList<God> selectGods(int howMany) {
        return null;
    }


    @Override
    public God pickFromChosenGods(ArrayList<God> hand) {
        return null;
    }

    @Override
    public Position placeWorker(String worker) {
        return null;
    }

    @Override
    public int selectWorker() {
        return 0;
    }

    @Override
    public void errorWhileSelectingWorker(String gameManagerSays) {

    }

    @Override
    public void workerSelectedSuccessfully() {

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
    public void win(boolean winner) {

    }

    @Override
    public void debug(Response response) {

    }
}
