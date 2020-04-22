package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Network.Message.MoveRequest;
import it.polimi.ingsw.Network.Server;
import org.junit.Test;

import static org.junit.Assert.*;
import java.io.IOException;

public class GameManagerTest {

    GameManager gameManager;



    @Test
    public void GameManagerTest() throws IOException {
        try {
            gameManager = new GameManager(new Server());

        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(PossibleGameState.GAME_INIT, gameManager.getGameState());

    }
}