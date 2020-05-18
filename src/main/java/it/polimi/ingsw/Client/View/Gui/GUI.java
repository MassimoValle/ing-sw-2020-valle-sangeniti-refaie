package it.polimi.ingsw.Client.View.Gui;

import it.polimi.ingsw.Client.View.ClientView;
import it.polimi.ingsw.Network.Message.Responses.Response;
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
    public ArrayList<God> selectGodsFromDeck(int howMany, String serverSays) {
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
    public Position moveWorker(ArrayList<Position> nearlyPosValid) {
        return null;
    }

    @Override
    public boolean askMoveAgain() {
        return false;
    }

    @Override
    public Position build(ArrayList<Position> possiblePosToBuild) {
        return null;
    }

    @Override
    public boolean askBuildAgain() {
        return false;
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
