package it.polimi.ingsw.server.view;

public interface Observer<T> {

    void update(T message);

}
