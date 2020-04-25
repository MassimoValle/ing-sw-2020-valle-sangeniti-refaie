package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.Game.PlayerNotFoundException;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    Game game;

    //Sono un deficiente ma non mi è venuto altro in mente
    Position pos00 = new Position(0,0);
    Position pos01 = new Position(0,1);
    Position pos02 = new Position(0,2);
    Position pos03 = new Position(0,3);
    Position pos04 = new Position(0,4);
    Position pos10 = new Position(1,0);
    Position pos11 = new Position(1,1);
    Position pos12 = new Position(1,2);
    Position pos13 = new Position(1,3);
    Position pos14 = new Position(1,4);
    Position pos20 = new Position(2,0);
    Position pos21 = new Position(2,1);
    Position pos22 = new Position(2,2);
    Position pos23 = new Position(2,3);
    Position pos24 = new Position(2,4);
    Position pos30 = new Position(3,0);
    Position pos31 = new Position(3,1);
    Position pos32 = new Position(3,2);
    Position pos33 = new Position(3,3);
    Position pos34 = new Position(3,4);
    Position pos40 = new Position(4,0);
    Position pos41 = new Position(4,1);
    Position pos42 = new Position(4,2);
    Position pos43 = new Position(4,3);
    Position pos44 = new Position(4,4);


    @Before
    public void initClass() {
        game = new Game();
    }

    @Test
    public void gameNotNull(){
        assertNotNull(game);
    }

    @Test
    public void playersListIsEmpty() {
        assertEquals(game.getPlayers().size(), 0);
    }

    @Test
    public void addPlayer() throws PlayerNotFoundException {
        String name = "player";
        game.addPlayer(name);

        assertNotNull(game.searchPlayerByName(name));
    }

    
    public void ensureEverythingIsInitialized() {
        assertNotNull("Game deck", game.getDeck());
        assertNotNull("Game Map", game.getGameMap());
        assertEquals(0, game.getPlayers().size());
        assertEquals(0, game.getChosenGodsFromDeck().size());
        assertEquals(0, game.getNumberOfPlayers());
    }

    /*
    @Test
    public void checkIfWorkerIsStuck() {
        //Aggiungo un nuovo giocatore
        Player player1 = game.addPlayer("Simone");
        //posiziono il primo dei suoi worker
        game.placeWorker(player1, player1.getPlayerWorkers().get(0), pos00);

        //Blocking the worker
        game.getGameMap().setSquareHeight(pos01, 3);
        game.getGameMap().setSquareHeight(pos11, 3);
        game.getGameMap().setSquareHeight(pos10, 3);

        //Check if it's stuck
        assertTrue(game.isWorkerStuck(player1.getPlayerWorkers().get(0)));

    }

     */

    /*
    @Test
    public void checkClone(){
        assertEquals(game.getPlayers(), game.clone().getPlayers());
        assertEquals(game.getGameMap(), game.clone().getGameMap());
        assertEquals(game.getDeck(), game.clone().getDeck());
        assertEquals(game.getNumberOfPlayers(), game.clone().getNumberOfPlayers());
    }
     */

    /*
    @Test
    public void pickGodFromDeck(){

        assertEquals(0, game.getChosenGodsFromDeck().size());

        game.chooseGodFromDeck(1);
        assertNotNull(game.getChosenGodsFromDeck());

    }

     */

    /*

    public void startGame() throws PlayerNotFoundException {
        Player simone = game.addPlayer("Simone");
        Player max  = game.addPlayer("Max");
        Player magdy = game.addPlayer("Magdy");

        assertEquals(3, game.getNumberOfPlayers());

        //Il giocatore più GODLIKE prende le carte dal mazzo
        for (int i = 1; i < game.getNumberOfPlayers() + 1; i++) {
            game.chooseGodFromDeck(i);
        }

        assertEquals(3, game.getChosenGodsFromDeck().size());

        //Assegno i god scelti ai player
        game.assignGodToPlayer(simone, game.getChosenGodsFromDeck().get(2));
        game.assignGodToPlayer(max, game.getChosenGodsFromDeck().get(2));
        game.assignGodToPlayer(magdy, game.getChosenGodsFromDeck().get(2));

        game.setGodsChosen(true);

        //Piazzo i worker
        game.placeWorker(simone, simone.getPlayerWorkers().get(0), pos00);
        game.placeWorker(simone, simone.getPlayerWorkers().get(1), pos01);
        game.placeWorker(max, max.getPlayerWorkers().get(0), pos11);
        game.placeWorker(max, max.getPlayerWorkers().get(1), pos10);
        game.placeWorker(magdy, magdy.getPlayerWorkers().get(0), pos43);
        game.placeWorker(magdy, magdy.getPlayerWorkers().get(1), pos44);

        //Controllo che effettivamente il worker in pos1 che leggo sulla mappa è il worker #1 di simone
        assertSame(simone.getPlayerWorkers().get(0), game.getGameMap().getWorkerOnSquare(pos00));
        assertSame(simone.getPlayerWorkers().get(1), game.getGameMap().getWorkerOnSquare(pos01));
        assertSame(max.getPlayerWorkers().get(0), game.getGameMap().getWorkerOnSquare(pos11));
        assertSame(max.getPlayerWorkers().get(1), game.getGameMap().getWorkerOnSquare(pos10));
        assertSame(magdy.getPlayerWorkers().get(0), game.getGameMap().getWorkerOnSquare(pos43));
        assertSame(magdy.getPlayerWorkers().get(1), game.getGameMap().getWorkerOnSquare(pos44));


        game.getGameMap().printBoard();

        //Tocca al giocatore 1

        System.out.println("Inizio partita\nTurno di Simone");
        game.initPlayerState(simone);

        //mi aspetto che il worker #1 sia bloccato
        assertTrue(game.isWorkerStuck(simone.getPlayerWorkers().get(0)));
        //ma non il #2
        assertFalse(game.isWorkerStuck(simone.getPlayerWorkers().get(1)));

        game.moveWorker(simone, simone.getPlayerWorkers().get(1), pos02);
        game.buildBlock(simone, simone.getPlayerWorkers().get(1), pos12);

        game.getGameMap().printBoard();


        //Turno giocatore 2

        System.out.println("Turno di Max");
        game.initPlayerState(max);

        game.moveWorker(max, max.getPlayerWorkers().get(0), pos12);
        game.buildBlock(max, max.getPlayerWorkers().get(0), pos23);

        game.getGameMap().printBoard();


        //Turno giocatore 3

        System.out.println("Turno di Magdy");
        game.initPlayerState(magdy);

        game.moveWorker(magdy, magdy.getPlayerWorkers().get(0), pos42);
        game.buildBlock(magdy, magdy.getPlayerWorkers().get(0), pos41);

        game.getGameMap().printBoard();





    }

     */

}