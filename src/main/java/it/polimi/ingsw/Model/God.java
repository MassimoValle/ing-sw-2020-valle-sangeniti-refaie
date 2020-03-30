package it.polimi.ingsw.Model;

public class God {

    private String godName;
    private String godDescription;
    private Power godPower;


    public God(String godName, String godDescription, Power godPower){
        this.godName = godName;
        this.godDescription = godDescription;
        this.godPower = godPower;
    }

    public void usePower() {};

    public String getGodName() { return godName; }
    public String getGodDescription() {return godDescription; }


}
