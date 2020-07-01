package it.polimi.ingsw.client.model.map;


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
