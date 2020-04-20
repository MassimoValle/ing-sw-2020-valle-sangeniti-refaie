package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Position;

public class GodPower {


    public void ApolloPower(Position from, Position to){
        Position tmp;
        tmp = to;
        to = from;
        from = tmp;
    }

    public boolean ArtemisPower(){
       return true; // da riassegnare a move
    }

    public boolean AthenaPower(){
        return false;   // possibilità di salire
    }

    public Dome AtlasPower(){
        return new Dome();   // possibilità di salire
    }

    public void DemeterPower(Square cell){
        Square build;
        //build = chooseSquare();
        if(build != cell){
            //build
        }
    }
}
