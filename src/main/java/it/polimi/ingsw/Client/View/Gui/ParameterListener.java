package it.polimi.ingsw.Client.View.Gui;


public class ParameterListener {

    private static Object parameter;

    private static ParameterListener instance = null;

    public static ParameterListener getInstance(){
        if(instance == null)
            instance = new ParameterListener();
        return instance;
    }


    public ParameterListener(){
        parameter = null;
    }

    public static Object getParameter() {
        return parameter;
    }

    public void setParameter(Object parameter) {

        synchronized (this){
            ParameterListener.parameter = parameter;
            this.notifyAll();
        }

    }

    public void setToNull(){
        parameter = null;
    }
}