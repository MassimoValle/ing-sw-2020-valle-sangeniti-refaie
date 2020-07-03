package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.god.godspower.Power;
import it.polimi.ingsw.server.model.map.GameMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This static class is used to store the information about every match's God's power in game
 */
public class GodsInGame {

    private static GodsInGame istance = null; // riferimento all' istanza
    private final Map<Game, ArrayList<God>> godsInGame;

    private GodsInGame() {
        godsInGame = new HashMap<>();
    } // costruttore

    public static GodsInGame getIstance() {
        if(istance==null)
            istance = new GodsInGame();
        return istance;
    }

    /**
     * Add a God to the Game given as parameter
     *
     * @param game the game to which add the God
     * @param god the god to be added to the game
     */
    public void addGodToGame(Game game, God god){

        if(godsInGame.containsKey(game)) {  // se il game è già registrato nell'HashMap...
            godsInGame.get(game).add(god);  // ... allora aggiunge il god rispetto al game
        }

        else {  // altrimenti
            ArrayList<God> gods = new ArrayList<>();
            gods.add(god);
            godsInGame.put(game, gods); // aggiunge il game e la godList alla HashMap
        }
    }

    /**
     * It gets the powers in the given Game
     *
     * @param game the game we want the powers from
     * @return a list containing all the powers
     */
    public ArrayList<Power> getPowers(Game game){

        ArrayList<Power> powers = new ArrayList<>();

        for(God god : godsInGame.get(game)){
            powers.add(god.getGodPower());
        }

        return powers;
    }

    /**
     * It gets the powers in the game based on which {@link GameMap} you give in
     *
     * @param map the game's gameMap you want information from
     * @return a list containing all the powers
     */
    public ArrayList<Power> getPowersByMap(GameMap map) {

        for(Map.Entry<Game, ArrayList<God>> entry : godsInGame.entrySet()) {
            Game key = entry.getKey();
            if (key.getGameMap().equals(map))
                return getPowers(key);
        }

        return null;
    }

}
