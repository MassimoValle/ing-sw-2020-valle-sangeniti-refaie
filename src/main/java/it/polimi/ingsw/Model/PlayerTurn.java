package it.polimi.ingsw.Model;

public class PlayerTurn {

    private final Player player;

    private boolean moved;
    private boolean built;

    public PlayerTurn(Player player){
        this.player = player;
        this.moved = false;
        this.built = false;
    }

    public boolean hasMoved() { return moved; }
    public boolean hasBuilt() { return built; }

    public void moveWorker(){
        // do something
        moved = true;
    }
    public void buildWithWorker(){
        // do something
        built = true;
    }
}
