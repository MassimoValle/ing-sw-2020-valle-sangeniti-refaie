package it.polimi.ingsw.Client.Model;

public abstract class IimageDecorator extends Object {

    private final Object object;

    public IimageDecorator(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }
}
