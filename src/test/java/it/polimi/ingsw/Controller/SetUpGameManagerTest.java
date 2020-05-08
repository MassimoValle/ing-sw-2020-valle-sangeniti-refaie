package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.God.Deck;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.God.GodsPower.ApolloPower;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Requests.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SetUpGameManagerTest {

    MasterController masterController;    // need to build a response
    SetUpGameManager setUpGameManager;
    Player activePlayer;
    Game game;
    ArrayList<Player> players = new ArrayList<>();

    int idx = 0;

    ArrayList<God> gods;

    @Before
    public void setUp() {
        Game.resetInstance();

        Player p1 = new Player("client1");
        Player p2 = new Player("client2");
        Player p3 = new Player("client3");

        players.add(p1); players.add(p2); players.add(p3);
        activePlayer = players.get(0);

        game = Game.getInstance();
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        gods = new ArrayList<>();
        gods.add(new God("apello", "desc", new ApolloPower("Your move", "desc")));
        gods.add(new God("figlio", "desc", new ApolloPower("Your move", "desc")));
        gods.add(new God("apollo", "desc", new ApolloPower("Your move", "desc")));


        masterController = new MasterController(game, activePlayer);
        setUpGameManager = masterController._getSetUpGameController();

    }


    @Test
    public void assignignGod(){

        God god = new God("apollo", "desc", new ApolloPower("Your move", "desc"));
        setUpGameManager.assignGodToPlayer(activePlayer, god);

        int index = game.getPlayers().indexOf(activePlayer);

        assertEquals(game.getPlayers().get(index).getPlayerGod(), god);
    }


    @Test
    public void assigningGodRequest(){

        setUpGameManager.setSetupGameState(PossibleGameState.ASSIGNING_GOD);

        God god = gods.get(0);
        Request request = new PickGodRequest(activePlayer.getPlayerName(), god);
        setUpGameManager.handleMessage(request);

        assertEquals(activePlayer.getPlayerGod(), god);

    }

    @Test
    public void chosenGodRequest(){

        //quando il setupGameManager viene inizializzato lo stato del game viene messo a GODLIKE_PLAYER_MOMENT
        assertEquals(PossibleGameState.GODLIKE_PLAYER_MOMENT, setUpGameManager.getSetupGameState());

        Request request = new ChoseGodsRequest(activePlayer.getPlayerName(), gods);
        setUpGameManager.handleMessage(request);

        assertEquals(game.getChosenGodsFromDeck(), gods);
    }

    @Test
    public void placeWorkerRequest(){

        setUpGameManager.setSetupGameState(PossibleGameState.FILLING_BOARD);
        Worker worker = new Worker(1);
        Position position = new Position(1, 1);
        Request request = new PlaceWorkerRequest(activePlayer.getPlayerName(), worker, position);

        setUpGameManager.handleMessage(request);

        assertEquals(game.getGameMap().getWorkerOnSquare(position), worker);
    }

    @Ignore
    public void flow_assigningGodRequest(){
        God god = gods.get(idx);
        idx++;
        if(idx == players.size()) idx = 0;

        Request request = new PickGodRequest(players.get(idx).getPlayerName(), god);
        setUpGameManager.handleMessage(request);

        assertEquals(players.get(idx).getPlayerGod(), god);
        assertEquals(game.getPlayers().get( game.getPlayers().indexOf(players.get(idx)) ).getPlayerGod(), god);

        if(idx != 0)
            assertEquals(PossibleGameState.ASSIGNING_GOD, setUpGameManager.getSetupGameState());
        else
            assertEquals(PossibleGameState.FILLING_BOARD, setUpGameManager.getSetupGameState());
    }

    @Test
    public void assigningGodFlow(){
        chosenGodRequest();

        flow_assigningGodRequest();
        flow_assigningGodRequest();
        flow_assigningGodRequest();

    }

    @Ignore
    public void flow_placingWorkerRequest(){
        setUpGameManager.setSetupGameState(PossibleGameState.FILLING_BOARD);
        List<Worker> workers = players.get(idx).getPlayerWorkers();
        activePlayer = players.get(idx);
        Position position1 = new Position(idx, idx);
        Position position2 = new Position(idx, idx+1);

        Request request1 = new PlaceWorkerRequest(activePlayer.getPlayerName(), workers.get(0), position1);
        setUpGameManager.handleMessage(request1);
        assertEquals(game.getGameMap().getWorkerOnSquare(position1), workers.get(0));

        Request request2 = new PlaceWorkerRequest(activePlayer.getPlayerName(), workers.get(1), position2);
        setUpGameManager.handleMessage(request2);
        assertEquals(game.getGameMap().getWorkerOnSquare(position2), workers.get(1));

        idx++;
    }

    @Test
    public void placingWorkerFlow(){

        // player1
        flow_placingWorkerRequest();

        //player2
        flow_placingWorkerRequest();

        //player3
        flow_placingWorkerRequest();

    }


    @Test
    public void settingUpGame() {

        ArrayList<God> godsChosen = new ArrayList<>();

        godsChosen.add(Deck.getInstance().getGod(0));
        godsChosen.add(Deck.getInstance().getGod(1));
        godsChosen.add(Deck.getInstance().getGod(2));

        //il godlike player invia i god scelti
        masterController.dispatcher(
                new ChoseGodsRequest(players.get(0).getPlayerName(), godsChosen)
        );

        int i=0;
        for (God god: game.getChosenGodsFromDeck()) {
            assertEquals(god, godsChosen.get(i));
            i++;
        }

        //player1 sceglie il suo god
        masterController.dispatcher(
                new PickGodRequest(players.get(1).getPlayerName(), Deck.getInstance().getGod(0))
        );

        assertEquals(Deck.getInstance().getGod(0), players.get(1).getPlayerGod());


        //player2 sceglie il suo god
        masterController.dispatcher(
                new PickGodRequest(players.get(2).getPlayerName(), Deck.getInstance().getGod(1))
        );

        assertEquals(Deck.getInstance().getGod(1), players.get(2).getPlayerGod());


        //player0 sceglie il suo god
        masterController.dispatcher(
                new PickGodRequest(players.get(0).getPlayerName(), Deck.getInstance().getGod(2))
        );

        assertEquals(Deck.getInstance().getGod(2), players.get(0).getPlayerGod());


        //player1 piazza i suoi god
        masterController.dispatcher(
                new PlaceWorkerRequest(players.get(1).getPlayerName(), players.get(1).getPlayerWorkers().get(0), new Position(0,0) )
        );

        assertTrue(game.getGameMap().getSquare(0,0).hasWorkerOn());
        assertEquals(game.getGameMap().getWorkerOnSquare(0,0), players.get(1).getPlayerWorkers().get(0));


        masterController.dispatcher(
                new PlaceWorkerRequest(players.get(1).getPlayerName(), players.get(1).getPlayerWorkers().get(1), new Position(0,1) )
        );

        assertTrue(game.getGameMap().getSquare(0,1).hasWorkerOn());
        assertEquals(game.getGameMap().getWorkerOnSquare(0,1), players.get(1).getPlayerWorkers().get(1));


        //player2 piazza i suoi god
        masterController.dispatcher(
                new PlaceWorkerRequest(players.get(2).getPlayerName(), players.get(2).getPlayerWorkers().get(0), new Position(2,0) )
        );

        assertTrue(game.getGameMap().getSquare(2,0).hasWorkerOn());
        assertEquals(game.getGameMap().getWorkerOnSquare(2,0), players.get(2).getPlayerWorkers().get(0));

        masterController.dispatcher(
                new PlaceWorkerRequest(players.get(2).getPlayerName(), players.get(2).getPlayerWorkers().get(1), new Position(2,1) )
        );

        assertTrue(game.getGameMap().getSquare(2,1).hasWorkerOn());
        assertEquals(game.getGameMap().getWorkerOnSquare(2,1), players.get(2).getPlayerWorkers().get(1));



        //player3 piazza i suoi god
        masterController.dispatcher(
                new PlaceWorkerRequest(players.get(0).getPlayerName(), players.get(0).getPlayerWorkers().get(0), new Position(4,0) )
        );

        assertTrue(game.getGameMap().getSquare(4,0).hasWorkerOn());
        assertEquals(game.getGameMap().getWorkerOnSquare(4,0), players.get(0).getPlayerWorkers().get(0));

        masterController.dispatcher(
                new PlaceWorkerRequest(players.get(0).getPlayerName(), players.get(0).getPlayerWorkers().get(1), new Position(4,1) )
        );

        assertTrue(game.getGameMap().getSquare(4,1).hasWorkerOn());
        assertEquals(game.getGameMap().getWorkerOnSquare(4,1), players.get(0).getPlayerWorkers().get(1));

        game.getGameMap().printBoard();




    }

}