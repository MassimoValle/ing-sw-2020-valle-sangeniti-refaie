package it.polimi.ingsw.Controller.GodsPowerTest;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Network.Message.ClientRequests.*;
import it.polimi.ingsw.Server.Controller.Enum.PossibleGameState;
import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SetupGameUtilityClass {

    protected Worker w1pl1;
    protected Worker w2pl1;
    protected Worker w1pl2;
    protected Worker w2pl2;
    protected GameMap map;

    protected MasterController masterController;

    public void setup(MasterController masterController, int god1, int god2, boolean closeWorkers) {

        this.masterController = masterController;

        map = masterController.getGameInstance().getGameMap();
        ArrayList<God> chosenGod = new ArrayList<>();
        chosenGod.add(masterController.getGameInstance().getDeck().getGod(god1));
        chosenGod.add(masterController.getGameInstance().getDeck().getGod(god2));
        masterController.getGameInstance().setChosenGodsFromDeck(chosenGod);

        //assegno i god ai rispettivi giocatori
        masterController.getGameInstance().getPlayers().get(0).setPlayerGod(chosenGod.get(0));
        masterController.getGameInstance().getChosenGodsFromDeck().get(0).setAssigned(true);
        //game.getChosenGodsFromDeck().get(0).setAssigned(true);
        masterController.getGameInstance().getPlayers().get(1).setPlayerGod(chosenGod.get(1));
        masterController.getGameInstance().getChosenGodsFromDeck().get(1).setAssigned(true);


        w1pl1 = masterController.getGameInstance().getPlayers().get(0).getPlayerWorkers().get(0);
        Square sq22;

        if (closeWorkers) {
            sq22 = masterController.getGameInstance().getGameMap().getSquare(new Position(2, 2));
            w1pl1.setPosition(new Position(2, 2));
        } else {
            sq22 = masterController.getGameInstance().getGameMap().getSquare(new Position(0, 0));
            w1pl1.setPosition(new Position(0, 0));
        }

        sq22.setWorkerOn(w1pl1);
        w1pl1.setPlaced(true);


       w2pl1 = masterController.getGameInstance().getPlayers().get(0).getPlayerWorkers().get(1);
        Square sq23;

        if (closeWorkers) {
            sq23 = masterController.getGameInstance().getGameMap().getSquare(new Position(2, 3));
            w2pl1.setPosition(new Position(2, 3));
        } else {
            sq23 = masterController.getGameInstance().getGameMap().getSquare(new Position(0, 4));
            w2pl1.setPosition(new Position(0, 4));
        }

        sq23.setWorkerOn(w2pl1);
        w2pl1.setPlaced(true);



        //giocatore 2 piazza il primo worker
        w1pl2 = masterController.getGameInstance().getPlayers().get(1).getPlayerWorkers().get(0);
        Square sq32;

        if (closeWorkers) {
            sq32 = masterController.getGameInstance().getGameMap().getSquare(new Position(3, 2));
            w1pl2.setPosition(new Position(3, 2));
        } else {
            sq32 = masterController.getGameInstance().getGameMap().getSquare(new Position(4, 0));
            w1pl2.setPosition(new Position(4, 0));
        }

        sq32.setWorkerOn(w1pl2);
        w1pl2.setPlaced(true);


        //giocatore 2 piazza il seoondo worker
        w2pl2 = masterController.getGameInstance().getPlayers().get(1).getPlayerWorkers().get(1);
        Square sq33;

        if (closeWorkers) {
            sq33 = masterController.getGameInstance().getGameMap().getSquare(new Position(3, 3));
            w2pl2.setPosition(new Position(3, 3));
        } else {
            sq33 = masterController.getGameInstance().getGameMap().getSquare(new Position(4, 4));
            w2pl2.setPosition(new Position(4, 4));
        }

        sq33.setWorkerOn(w2pl2);
        w2pl2.setPlaced(true);

        masterController._getActionManager()._setGameState(PossibleGameState.START_ROUND);
        masterController._getTurnManager().updateTurnState(PossibleGameState.START_ROUND);
        masterController._getActionManager()._setActivePlayer(masterController.getGameInstance().getPlayers().get(0));
        masterController._getTurnManager().nextTurn(masterController.getGameInstance().getPlayers().get(0));

    }

    public void setupSurrounded(MasterController masterController, int god1, int god2, boolean closeWorkers) throws DomePresentException {

        this.masterController = masterController;

        map = masterController.getGameInstance().getGameMap();
        ArrayList<God> chosenGod = new ArrayList<>();
        chosenGod.add(masterController.getGameInstance().getDeck().getGod(god1));
        chosenGod.add(masterController.getGameInstance().getDeck().getGod(god2));
        masterController.getGameInstance().setChosenGodsFromDeck(chosenGod);

        //assegno i god ai rispettivi giocatori
        masterController.getGameInstance().getPlayers().get(0).setPlayerGod(chosenGod.get(0));
        masterController.getGameInstance().getChosenGodsFromDeck().get(0).setAssigned(true);
        //game.getChosenGodsFromDeck().get(0).setAssigned(true);
        masterController.getGameInstance().getPlayers().get(1).setPlayerGod(chosenGod.get(1));
        masterController.getGameInstance().getChosenGodsFromDeck().get(1).setAssigned(true);


        w1pl1 = masterController.getGameInstance().getPlayers().get(0).getPlayerWorkers().get(0);
        Square sq22;

        if (closeWorkers) {
            sq22 = masterController.getGameInstance().getGameMap().getSquare(new Position(2, 2));
            w1pl1.setPosition(new Position(2, 2));
        } else {
            sq22 = masterController.getGameInstance().getGameMap().getSquare(new Position(0, 0));
            w1pl1.setPosition(new Position(0, 0));
        }

        sq22.setWorkerOn(w1pl1);
        w1pl1.setPlaced(true);


        w2pl1 = masterController.getGameInstance().getPlayers().get(0).getPlayerWorkers().get(1);
        Square sq23;

        if (closeWorkers) {
            sq23 = masterController.getGameInstance().getGameMap().getSquare(new Position(2, 3));
            w2pl1.setPosition(new Position(2, 3));
        } else {
            sq23 = masterController.getGameInstance().getGameMap().getSquare(new Position(0, 4));
            w2pl1.setPosition(new Position(0, 4));
        }

        sq23.setWorkerOn(w2pl1);
        w2pl1.setPlaced(true);



        //giocatore 2 piazza il primo worker
        w1pl2 = masterController.getGameInstance().getPlayers().get(1).getPlayerWorkers().get(0);
        Square sq32;

        if (closeWorkers) {
            sq32 = masterController.getGameInstance().getGameMap().getSquare(new Position(3, 2));
            w1pl2.setPosition(new Position(3, 2));
        } else {
            sq32 = masterController.getGameInstance().getGameMap().getSquare(new Position(4, 0));
            w1pl2.setPosition(new Position(4, 0));
        }

        sq32.setWorkerOn(w1pl2);
        w1pl2.setPlaced(true);


        //giocatore 2 piazza il seoondo worker
        w2pl2 = masterController.getGameInstance().getPlayers().get(1).getPlayerWorkers().get(1);
        Square sq33;

        if (closeWorkers) {
            sq33 = masterController.getGameInstance().getGameMap().getSquare(new Position(3, 3));
            w2pl2.setPosition(new Position(3, 3));
        } else {
            sq33 = masterController.getGameInstance().getGameMap().getSquare(new Position(4, 4));
            w2pl2.setPosition(new Position(4, 4));
        }

        sq33.setWorkerOn(w2pl2);
        w2pl2.setPlaced(true);

        masterController._getActionManager()._setGameState(PossibleGameState.START_ROUND);
        masterController._getTurnManager().updateTurnState(PossibleGameState.START_ROUND);
        masterController._getActionManager()._setActivePlayer(masterController.getGameInstance().getPlayers().get(0));
        masterController._getTurnManager().nextTurn(masterController.getGameInstance().getPlayers().get(0));

        if (closeWorkers) {
            //build around
        } else {
            Square sq01 = masterController.getGameInstance().getGameMap().getSquare(0,1);
            sq01.addBlock(true);
            Square sq11 = masterController.getGameInstance().getGameMap().getSquare(1,1);
            sq11.addBlock(true);
            Square sq10 = masterController.getGameInstance().getGameMap().getSquare(1,0);
            sq10.addBlock(true);
        }
    }

    public void setupDifferentHeight(MasterController masterController, int god1, int god2, boolean oneSameLevel) throws DomePresentException {

        this.masterController = masterController;

        map = masterController.getGameInstance().getGameMap();

        ArrayList<God> chosenGod = new ArrayList<>();
        chosenGod.add(masterController.getGameInstance().getDeck().getGod(god1));
        chosenGod.add(masterController.getGameInstance().getDeck().getGod(god2));
        masterController.getGameInstance().setChosenGodsFromDeck(chosenGod);

        //assegno i god ai rispettivi giocatori
        masterController.getGameInstance().getPlayers().get(0).setPlayerGod(chosenGod.get(0));
        masterController.getGameInstance().getChosenGodsFromDeck().get(0).setAssigned(true);
        //game.getChosenGodsFromDeck().get(0).setAssigned(true);
        masterController.getGameInstance().getPlayers().get(1).setPlayerGod(chosenGod.get(1));
        masterController.getGameInstance().getChosenGodsFromDeck().get(1).setAssigned(true);

        //player1 piazza primo worker
        w1pl1 = masterController.getGameInstance().getPlayers().get(0).getPlayerWorkers().get(0);
        Square sq22;

        sq22 = masterController.getGameInstance().getGameMap().getSquare(new Position(2, 2));
        w1pl1.setPosition(new Position(2, 2));
        sq22.setWorkerOn(w1pl1);
        w1pl1.setPlaced(true);

        //player1 piazza secondo worker al livello 3
        w2pl1 = masterController.getGameInstance().getPlayers().get(0).getPlayerWorkers().get(1);
        Square sq23;

        sq23 = masterController.getGameInstance().getGameMap().getSquare(new Position(2, 3));
        sq23.addBlock(false);
        sq23.addBlock(false);
        sq23.addBlock(false);

        w2pl1.setPosition(new Position(2, 3));
        sq23.setWorkerOn(w2pl1);
        w2pl1.setPlaced(true);



        //player2 piazza primo worker al livello 2
        w1pl2 = masterController.getGameInstance().getPlayers().get(1).getPlayerWorkers().get(0);
        Square sq32;

        sq32 = masterController.getGameInstance().getGameMap().getSquare(new Position(3, 2));
        sq32.addBlock(false);
        sq32.addBlock(false);

        w1pl2.setPosition(new Position(3, 2));
        sq32.setWorkerOn(w1pl2);
        w1pl2.setPlaced(true);


        //player2 piazza secondo worker al livello 1



        w2pl2 = masterController.getGameInstance().getPlayers().get(1).getPlayerWorkers().get(1);
        Square sq33;

        if (!oneSameLevel) {
            sq33 = masterController.getGameInstance().getGameMap().getSquare(new Position(3, 3));
            sq33.addBlock(false);
        } else {
            sq33 = masterController.getGameInstance().getGameMap().getSquare(new Position(3, 3));
        }

        w2pl2.setPosition(new Position(3, 3));
        sq33.setWorkerOn(w2pl2);
        w2pl2.setPlaced(true);

        Square sq12 = masterController.getGameInstance().getGameMap().getSquare(new Position(1, 2));
        sq12.addBlock(false);
        sq12.addBlock(false);

        Square sq01 = masterController.getGameInstance().getGameMap().getSquare(new Position(0, 1));
        sq01.addBlock(false);
        sq01.addBlock(false);
        sq01.addBlock(false);

        masterController._getActionManager()._setGameState(PossibleGameState.START_ROUND);
        masterController._getTurnManager().updateTurnState(PossibleGameState.START_ROUND);
        masterController._getActionManager()._setActivePlayer(masterController.getGameInstance().getPlayers().get(0));
        masterController._getTurnManager().nextTurn(masterController.getGameInstance().getPlayers().get(0));

    }

    public void setupCompleteTowers(MasterController masterController, int god1, int god2) throws DomePresentException {

        this.masterController = masterController;

        map = masterController.getGameInstance().getGameMap();
        ArrayList<God> chosenGod = new ArrayList<>();
        chosenGod.add(masterController.getGameInstance().getDeck().getGod(god1));
        chosenGod.add(masterController.getGameInstance().getDeck().getGod(god2));
        masterController.getGameInstance().setChosenGodsFromDeck(chosenGod);

        //assegno i god ai rispettivi giocatori
        masterController.getGameInstance().getPlayers().get(0).setPlayerGod(chosenGod.get(0));
        masterController.getGameInstance().getChosenGodsFromDeck().get(0).setAssigned(true);
        //game.getChosenGodsFromDeck().get(0).setAssigned(true);
        masterController.getGameInstance().getPlayers().get(1).setPlayerGod(chosenGod.get(1));
        masterController.getGameInstance().getChosenGodsFromDeck().get(1).setAssigned(true);


        w1pl1 = masterController.getGameInstance().getPlayers().get(0).getPlayerWorkers().get(0);
        Square sq00;

        sq00 = masterController.getGameInstance().getGameMap().getSquare(new Position(0, 0));
        w1pl1.setPosition(new Position(0, 0));


        sq00.setWorkerOn(w1pl1);
        w1pl1.setPlaced(true);


        w2pl1 = masterController.getGameInstance().getPlayers().get(0).getPlayerWorkers().get(1);
        Square sq04;


        sq04 = masterController.getGameInstance().getGameMap().getSquare(new Position(0, 4));
        sq04.addBlock(false);
        sq04.addBlock(false);
        sq04.addBlock(false);

        w2pl1.setPosition(new Position(0, 4));


        sq04.setWorkerOn(w2pl1);
        w2pl1.setPlaced(true);



        //giocatore 2 piazza il primo worker
        w1pl2 = masterController.getGameInstance().getPlayers().get(1).getPlayerWorkers().get(0);
        Square sq40;


        sq40 = masterController.getGameInstance().getGameMap().getSquare(new Position(4, 0));
        w1pl2.setPosition(new Position(4, 0));


        sq40.setWorkerOn(w1pl2);
        w1pl2.setPlaced(true);


        //giocatore 2 piazza il seoondo worker
        w2pl2 = masterController.getGameInstance().getPlayers().get(1).getPlayerWorkers().get(1);
        Square sq44;


        sq44 = masterController.getGameInstance().getGameMap().getSquare(new Position(4, 4));
        sq44.addBlock(false);
        sq44.addBlock(false);
        sq44.addBlock(false);



        w2pl2.setPosition(new Position(4, 4));


        sq44.setWorkerOn(w2pl2);
        w2pl2.setPlaced(true);

        masterController._getActionManager()._setGameState(PossibleGameState.START_ROUND);
        masterController._getTurnManager().updateTurnState(PossibleGameState.START_ROUND);
        masterController._getActionManager()._setActivePlayer(masterController.getGameInstance().getPlayers().get(0));
        masterController._getTurnManager().nextTurn(masterController.getGameInstance().getPlayers().get(0));

        Square sq21 = masterController.getGameInstance().getGameMap().getSquare(new Position(2, 1));
        sq21.addBlock(false);
        sq21.addBlock(false);
        sq21.addBlock(false);
        sq21.addBlock(false);

        Square sq31 = masterController.getGameInstance().getGameMap().getSquare(new Position(3, 1));
        sq31.addBlock(false);
        sq31.addBlock(false);
        sq31.addBlock(false);
        sq31.addBlock(false);

        Square sq33 = masterController.getGameInstance().getGameMap().getSquare(new Position(3, 3));
        sq33.addBlock(false);
        sq33.addBlock(false);
        sq33.addBlock(false);
        sq33.addBlock(false);

        Square sq22 = masterController.getGameInstance().getGameMap().getSquare(new Position(2, 2));
        sq22.addBlock(false);
        sq22.addBlock(false);
        sq22.addBlock(false);
        sq22.addBlock(false);



    }

    public void selectWorker(String pl, int i) {
        MasterController.dispatcher(
                new SelectWorkerRequest(pl, i)
        );
        map.printBoard();
    }

    public void move(String pl, int i, int j) {
        MasterController.dispatcher(
                new MoveRequest(pl, new Position(i,j))
        );

        if(getOutcome() == ActionOutcome.NOT_DONE){}

        else map.printBoard();
    }

    public void build(String pl, int i, int j) {
        MasterController.dispatcher(
                new BuildRequest(pl, new Position(i,j))
        );

        if(getOutcome() == ActionOutcome.NOT_DONE){}

        else map.printBoard();
    }

    public void buildDome(String pl, int i, int j){
        MasterController.dispatcher(
                new BuildDomeRequest(pl, new Position(i,j))
        );

        if(getOutcome() == ActionOutcome.NOT_DONE){}

        else map.printBoard();
    }

    public void powerButton(String pl){
        MasterController.dispatcher((
                new PowerButtonRequest(pl))
        );
    }

    public void endTurn(String pl) {
        MasterController.dispatcher(
                new EndTurnRequest(pl)
        );
    }

    public void endMove(String pl) {
        MasterController.dispatcher(
                new EndMoveRequest(pl)
        );
    }

    public void endBuild(String pl) {
        MasterController.dispatcher(
                new EndBuildRequest(pl)
        );
    }

    public ActionOutcome getOutcome() {
        return masterController._getActionManager()._getActionOutcome();
    }

    public Player getWinner(){return masterController._getActionManager().getWinner();}

    public void addBlock(int i, int j) throws DomePresentException {
        masterController.getGameInstance().getGameMap().getSquare(i,j).addBlock(false);
    }

    public void addDome(int i, int j) throws DomePresentException {
        masterController.getGameInstance().getGameMap().getSquare(i,j).addBlock(true);
    }

}
