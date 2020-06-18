package it.polimi.ingsw.Client.Model.Map;


import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;
import javafx.geometry.Pos;

public class GUImap extends CLIclientMap {

    public GUImap(){
        super();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j].initGUIObj();
            }
        }
    }



}
