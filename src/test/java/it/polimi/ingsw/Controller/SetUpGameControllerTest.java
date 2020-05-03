package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.God.GodsPower.ApolloPower;
import it.polimi.ingsw.Model.God.GodsPower.Power;
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

import java.awt.*;
import java.util.ArrayList;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SetUpGameControllerTest {

    SuperMegaController superMegaController;    // need to build a response
    SetUpGameController setUpGameController;
    Player activePlayer;
    Game game;

    @Before
    public void setUp() {

        activePlayer = new Player("client1");
        game = new Game();
        game.addPlayer(activePlayer);
        Player nextPlayer = new Player("pippo");
        game.addPlayer(nextPlayer);

        superMegaController = new SuperMegaController(game, activePlayer);
        setUpGameController = new SetUpGameController(game, activePlayer);

    }


    @Test
    public void assignignGod(){

        God god = new God("apollo", "desc", new ApolloPower("Your move", "desc"));
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
        God god = new God("apollo", "desc", new ApolloPower("Your move", "desc"));

        Request request = new AssignGodRequest(activePlayer.getPlayerName(), MessageStatus.OK, god);
        setUpGameController.handleMessage(request);

        assertEquals(activePlayer.getPlayerGod(), god);
        assertEquals(game.getPlayers().get( game.getPlayers().indexOf(activePlayer) ).getPlayerGod(), god);
        assertEquals(SuperMegaController.gameState, PossibleGameState.ASSIGNING_GOD);
    }

    @Test
    public void chosenGodRequest(){
        ArrayList<God> gods = new ArrayList<>(){
            final God apello = new God("apello", "desc", new ApolloPower("Your move", "desc"));
            final God figlio = new God("figlio", "desc", new ApolloPower("Your move", "desc"));
            final God apollo = new God("apollo", "desc", new ApolloPower("Your move", "desc"));

        };

        Request request = new ChoseGodsRequest(activePlayer.getPlayerName(), gods);
        setUpGameController.handleMessage(request);

        assertEquals(game.getChosenGodsFromDeck(), gods);
    }


    @Test
    public void placeWorkerRequest(){
        Worker worker = new Worker(1, Color.RED);
        Position position = new Position(1, 1);
        Request request = new PlaceWorkerRequest(activePlayer.getPlayerName(), MessageStatus.OK, worker, position);

        setUpGameController.handleMessage(request);

        assertEquals(game.getGameMap().getWorkerOnSquare(position), worker);
    }

}