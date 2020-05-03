package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Requests.Request;

import org.junit.Before;
import org.junit.Test;

public class SuperMegaControllerTest {

    SuperMegaController superMegaController;
    Game game;
    Player player;

    @Before
    public void setUp() {
        game = new Game();
        player = new Player("client1");
        superMegaController = new SuperMegaController(game, player);

    }


    @Test
    public void checkDispatcher(){
        Request request = new Request(player.getPlayerName(), Dispatcher.SETUP_GAME, MessageContent.CHECK, MessageStatus.OK);
        superMegaController.dispatcher(request);

        //request = new Request(player.getPlayerName(), Dispatcher.TURN, MessageContent.CHECK, MessageStatus.OK);
        //superMegaController.dispatcher(request);
    }


}