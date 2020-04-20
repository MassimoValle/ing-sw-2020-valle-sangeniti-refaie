package it.polimi.ingsw.Model;

public class God {

    private String godName;
    private String godDescription;
    private Power godPower;
    private boolean taken;


    public God(String godName, String godDescription, Power godPower){
        this.godName = godName;
        this.godDescription = godDescription;
        this.godPower = godPower;
        this.taken = false;

    }


    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }


    public String getGodName() { return godName; }
    public String getGodDescription() {return godDescription; }


    public Power getGodPower() {
        return godPower;
    }

    @Override
    public String toString() {
        String string = "";
        string = string.concat("\nName: " + this.getGodName());
        string = string.concat("\nDescription: " + this.getGodDescription());
        string = string.concat("\nPower description: " + this.getGodPower().getPowerDescription());
        return string;
    }


}
