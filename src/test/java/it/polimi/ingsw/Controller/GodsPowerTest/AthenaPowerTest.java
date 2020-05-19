package it.polimi.ingsw.Controller.GodsPowerTest;

import it.polimi.ingsw.Network.Message.ClientRequests.*;
import it.polimi.ingsw.Server.Controller.Enum.PossibleGameState;
import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class AthenaPowerTest {

    MasterController masterController;
    Player player1, player2;
    Game game;

    @Before
    public void setUp() {

        Game.resetInstance();
        game = Game.getInstance();



        player1 = new Player("Simone");
        player2 = new Player("Massimo");
        Game.getInstance().addPlayer(player1);
        Game.getInstance().addPlayer(player2);

        masterController = new MasterController(game, player1);
    }

    @Test
    public void AthenaPowerTest() {

        String pl1 = player1.getPlayerName();
        String pl2 = player2.getPlayerName();

        assertEquals(2, masterController.getGameInstance().getNumberOfPlayers());


        //Aggiungo i god alla partita
        ArrayList<God> chosenGod = new ArrayList<>();
        chosenGod.add(game.getDeck().getGod(2));
        chosenGod.add(game.getDeck().getGod(1));
        game.setChosenGodsFromDeck(chosenGod);

        //assegno i god ai rispettivi giocatori
        player1.setPlayerGod(chosenGod.get(0));
        Game.getInstance().getChosenGodsFromDeck().get(1).setAssigned(true);
        //game.getChosenGodsFromDeck().get(0).setAssigned(true);
        player2.setPlayerGod(chosenGod.get(1));
        game.getChosenGodsFromDeck().get(1).setAssigned(true);

        //giocatore 1 piazza il primo worker
        Worker w1pl1 = player1.getPlayerWorkers().get(0);
        Square sq22 = game.getGameMap().getSquare(new Position(2, 2));

        w1pl1.setPosition(new Position(2, 2));
        sq22.setWorkerOn(w1pl1);
        w1pl1.setPlaced(true);

        //giocatore 2 piazza il primo worker
        Worker w1pl2 = player2.getPlayerWorkers().get(0);
        Square sq32 = game.getGameMap().getSquare(new Position(3, 2));

        w1pl2.setPosition(new Position(3, 2));
        sq32.setWorkerOn(w1pl2);
        w1pl2.setPlaced(true);

        //giocatore 1 piazza il secondo worker
        Worker w2pl1 = player1.getPlayerWorkers().get(1);
        Square sq23 = game.getGameMap().getSquare(new Position(2, 3));

        w2pl1.setPosition(new Position(2, 3));
        sq23.setWorkerOn(w2pl1);
        w2pl1.setPlaced(true);

        //giocatore 2 piazza il seoondo worker
        Worker w2pl2 = player2.getPlayerWorkers().get(1);
        Square sq33 = game.getGameMap().getSquare(new Position(3, 3));

        w2pl2.setPosition(new Position(3, 3));
        sq33.setWorkerOn(w2pl2);
        w2pl2.setPlaced(true);

        game.getGameMap().printBoard();

        masterController._getActionManager().setGameState(PossibleGameState.START_ROUND);
        masterController._getTurnManager().updateTurnState(PossibleGameState.START_ROUND);
        masterController._getTurnManager().nextTurn(player1);


        //Tocca al player1
        //seleziona un worker...
        MasterController.dispatcher(
                new SelectWorkerRequest(pl1, 0)
        );
        //lo muovo...
        masterController.dispatcher(
                new MoveRequest(pl1, new Position(1, 2))
        );
        //e costruisco
        masterController.dispatcher(
                new BuildRequest(pl1, new Position(0, 2))
        );
        //passo il turno
        masterController.dispatcher(
                new EndTurnRequest(pl1)
        );

        game.getGameMap().printBoard();

        //Tocca al player2
        masterController.dispatcher(
                new SelectWorkerRequest(pl2, 0)
        );
        //lo muovo
        masterController.dispatcher(
                new MoveRequest(pl2, new Position(4, 2))
        );

        masterController.dispatcher(
                new EndMoveRequest(pl2)
        );

        masterController.dispatcher(
                new BuildRequest(pl2, new Position(4, 3))
        );
        //passo il turno
        masterController.dispatcher(
                new EndTurnRequest(pl2)
        );

        game.getGameMap().printBoard();

        // 2 TUNRO PLAYER ATHENA
        //seleziona un worker...
        masterController.dispatcher(
                new SelectWorkerRequest(pl1, 0)
        );
        //lo muovo...
        masterController.dispatcher(
                new MoveRequest(pl1, new Position(0, 2))
        );
        //e costruisco
        masterController.dispatcher(
                new BuildRequest(pl1, new Position(0, 1))
        );
        //passo il turno
        masterController.dispatcher(
                new EndTurnRequest(pl1)
        );

        game.getGameMap().printBoard();

        //Tocca al player2
        masterController.dispatcher(
                new SelectWorkerRequest(pl2, 0)
        );
        //lo muovo la prima volta
        masterController.dispatcher(
                new MoveRequest(pl2, new Position(4, 3))
        );
        //La move request da error perchè athena nel turno precedente è salita
        //di conseguenza sono costretto a muovere in un altra posizine
        masterController.dispatcher(
                new MoveRequest(pl2, new Position(3, 2))
        );

        masterController.dispatcher(
                new EndMoveRequest(pl2)
        );
        masterController.dispatcher(
                new BuildRequest(pl2, new Position(3, 1))
        );
        //passo il turno
        masterController.dispatcher(
                new EndTurnRequest(pl2)
        );

        game.getGameMap().printBoard();
        //FUNZIONAAAAA
    }
}
