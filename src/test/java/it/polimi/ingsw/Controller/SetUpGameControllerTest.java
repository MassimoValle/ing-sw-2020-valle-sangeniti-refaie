package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.God.Power;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Requests.AssignGodRequest;
import it.polimi.ingsw.Network.Message.Requests.ChoseGodsRequest;
import it.polimi.ingsw.Network.Message.Requests.PlaceWorkerRequest;
import it.polimi.ingsw.Network.Message.Requests.Request;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SetUpGameControllerTest {

    SuperMegaController superMegaController;    // need to build a response
    SetUpGameController setUpGameController;
    Player activePlayer;
    Game game;
    ArrayList<Player> players = new ArrayList<>();

    int idx = 0;

    ArrayList<God> gods;

    @Before
    public void setUp() {

        Player p1 = new Player("client1");
        Player p2 = new Player("client2");
        Player p3 = new Player("client3");

        players.add(p1); players.add(p2); players.add(p3);
        activePlayer = players.get(0);

        game = new Game();
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        gods = new ArrayList<>();
        gods.add(new God("apello", "desc", new Power("Your move", "desc")));
        gods.add(new God("figlio", "desc", new Power("Your move", "desc")));
        gods.add(new God("apollo", "desc", new Power("Your move", "desc")));


        superMegaController = new SuperMegaController(game, activePlayer);
        setUpGameController = new SetUpGameController(game, activePlayer);

    }


    @Test
    public void assignignGod(){

        God god = new God("apollo", "desc", new Power.ApolloPower("Your move", "desc"));
        setUpGameController.assignGodToPlayer(activePlayer, god);

        int index = game.getPlayers().indexOf(activePlayer);

        assertEquals(game.getPlayers().get(index).getPlayerGod(), god);
    }

    @Test
    public void discardRequest(){
        Player bobby = new Player("bobby");
        game.addPlayer(bobby);

        Request request = new Request("bobby", Dispatcher.SETUP_GAME, MessageContent.CHECK, MessageStatus.OK);
        setUpGameController.handleMessage(request);
    }

    @Test
    public void assigningGodRequest(){

        SuperMegaController.gameState = PossibleGameState.ASSIGNING_GOD;

        God god = gods.get(0);
        Request request = new AssignGodRequest(activePlayer.getPlayerName(), MessageStatus.OK, god);
        setUpGameController.handleMessage(request);

        assertEquals(activePlayer.getPlayerGod(), god);

        God playerGod = game.getPlayers().get(game.getPlayers().indexOf(activePlayer)).getPlayerGod();
        assertEquals(playerGod, god);

    }

    @Test
    public void chosenGodRequest(){
        SuperMegaController.gameState = PossibleGameState.GODLIKE_PLAYER_MOMENT;

        Request request = new ChoseGodsRequest(activePlayer.getPlayerName(), MessageStatus.OK, gods);
        setUpGameController.handleMessage(request);

        assertEquals(game.getChosenGodsFromDeck(), gods);
    }

    @Test
    public void placeWorkerRequest(){

        SuperMegaController.gameState = PossibleGameState.FILLING_BOARD;
        Worker worker = new Worker(1);
        Position position = new Position(1, 1);
        Request request = new PlaceWorkerRequest(activePlayer.getPlayerName(), MessageStatus.OK, worker, position);

        setUpGameController.handleMessage(request);

        assertEquals(game.getGameMap().getWorkerOnSquare(position), worker);
    }

    @Ignore
    public void flow_assigningGodRequest(){
        God god = gods.get(idx);
        idx++;
        if(idx == players.size()) idx = 0;

        Request request = new AssignGodRequest(players.get(idx).getPlayerName(), MessageStatus.OK, god);
        setUpGameController.handleMessage(request);

        assertEquals(players.get(idx).getPlayerGod(), god);
        assertEquals(game.getPlayers().get( game.getPlayers().indexOf(players.get(idx)) ).getPlayerGod(), god);

        if(idx != 0)
            assertEquals(SuperMegaController.gameState, PossibleGameState.ASSIGNING_GOD);
        else
            assertEquals(SuperMegaController.gameState, PossibleGameState.FILLING_BOARD);
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
        SuperMegaController.gameState = PossibleGameState.FILLING_BOARD;
        List<Worker> workers = players.get(idx).getPlayerWorkers();
        activePlayer = players.get(idx);
        Position position1 = new Position(idx, idx);
        Position position2 = new Position(idx, idx+1);

        Request request1 = new PlaceWorkerRequest(activePlayer.getPlayerName(), MessageStatus.OK, workers.get(0), position1);
        setUpGameController.handleMessage(request1);
        assertEquals(game.getGameMap().getWorkerOnSquare(position1), workers.get(0));

        Request request2 = new PlaceWorkerRequest(activePlayer.getPlayerName(), MessageStatus.OK, workers.get(1), position2);
        setUpGameController.handleMessage(request2);
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


}