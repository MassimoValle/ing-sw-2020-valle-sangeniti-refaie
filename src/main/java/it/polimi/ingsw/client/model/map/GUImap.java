package it.polimi.ingsw.client.model.map;


/**
 * The type Gu imap.
 */
public class GUImap extends CLIclientMap {

    /**
     * Instantiates a new GUI map.
     * Initialize graphics resource of squares
     */
    public GUImap(){
        super();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j].initGUIObj();
            }
        }
    }

}
