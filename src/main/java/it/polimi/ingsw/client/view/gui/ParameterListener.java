package it.polimi.ingsw.client.view.gui;


public class ParameterListener {

    private static Object parameter;

    private static ParameterListener instance = null;

    public static ParameterListener getInstance(){
        if(instance == null)
            instance = new ParameterListener();
        return instance;
    }


    public static synchronized Object getParameter() {
        return parameter;
    }

    public synchronized void setParameter(Object parameter) {
            ParameterListener.parameter = parameter;
            this.notifyAll();
    }

    public static synchronized void setToNull(){
        parameter = null;
    }
}
