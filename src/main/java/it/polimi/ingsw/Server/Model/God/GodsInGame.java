package it.polimi.ingsw.Server.Model.God;

import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.God.GodsPower.Power;
import it.polimi.ingsw.Server.Model.Map.GameMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    // aggiunge il god al game
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

    // ritorna la lista di gods relativi al game
    public ArrayList<God> getGods(Game game){
        return godsInGame.get(game);
    }

    // ritorna la lista di poteri relativa al game
    public ArrayList<Power> getPowers(Game game){

        ArrayList<Power> powers = new ArrayList<>();

        for(God god : godsInGame.get(game)){
            powers.add(god.getGodPower());
        }

        return powers;
    }

    public ArrayList<Power> getPowersByMap(GameMap map) {

        for(Map.Entry<Game, ArrayList<God>> entry : godsInGame.entrySet()) {
            Game key = entry.getKey();
            if (key.getGameMap().equals(map))
                return getPowers(key);
        }

        return null;
    }

}
