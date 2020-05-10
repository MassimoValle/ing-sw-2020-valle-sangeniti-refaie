package it.polimi.ingsw.ServerView;

public interface Observer<T> {

    public void update(T message);

}
