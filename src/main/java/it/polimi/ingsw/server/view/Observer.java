package it.polimi.ingsw.server.view;

public interface Observer<T> {

    public void update(T message);

}
