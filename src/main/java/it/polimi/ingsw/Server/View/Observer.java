package it.polimi.ingsw.Server.View;

public interface Observer<T> {

    public void update(T message);

}
