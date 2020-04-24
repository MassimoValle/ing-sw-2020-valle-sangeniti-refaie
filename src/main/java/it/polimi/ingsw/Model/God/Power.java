package it.polimi.ingsw.Model.God;

public class Power {

    private String powerDescription;
    private PowerType powerType;


    public Power(String powerType, String powerDescription) {
        this.powerType = PowerType.matchFromXml(powerType);
        this.powerDescription = powerDescription;
    }

    public PowerType getPowerType() {
        return powerType;
    }

    public String getPowerDescription() {
        return powerDescription;
    }



    public static class ApolloPower extends Power {

        private boolean canTakeOpponentsPlace;

        public ApolloPower(String powerType, String powerDescription) {
            super(powerType, powerDescription);
            canTakeOpponentsPlace = true;
        }
    }


    public static class ArtemisPower extends Power {

        private boolean canTakeOpponentsPlace;

        public ArtemisPower(String powerType, String powerDescription) {
            super(powerType, powerDescription);
            canTakeOpponentsPlace = false;
        }
    }


    public static class AthenaPower extends Power {

        private boolean canTakeOpponentsPlace;

        public AthenaPower(String powerType, String powerDescription) {
            super(powerType, powerDescription);
            canTakeOpponentsPlace = false;
        }
    }


    public static class AtlasPower extends Power {

        private boolean canTakeOpponentsPlace;

        public AtlasPower(String powerType, String powerDescription) {
            super(powerType, powerDescription);
            canTakeOpponentsPlace = false;
        }
    }


    public static class DemeterPower extends Power {

        private boolean canTakeOpponentsPlace;

        public DemeterPower(String powerType, String powerDescription) {
            super(powerType, powerDescription);
            canTakeOpponentsPlace = false;
        }
    }


    public static class HephaestusPower extends Power {

        private boolean canTakeOpponentsPlace;

        public HephaestusPower(String powerType, String powerDescription) {
            super(powerType, powerDescription);
            canTakeOpponentsPlace = false;
        }
    }


    public static class MinotaurPower extends Power {

        private boolean canTakeOpponentsPlace;

        public MinotaurPower(String powerType, String powerDescription) {
            super(powerType, powerDescription);
            canTakeOpponentsPlace = false;
        }
    }


    public static class PanPower extends Power {

        public PanPower(String powerType, String powerDescription) {
            super(powerType, powerDescription);
        }

    }


    public static class PrometheusPower extends Power {

        public PrometheusPower(String powerType, String powerDescription) {
            super(powerType, powerDescription);
        }

    }


    public static class ChronusPower extends Power {

        public ChronusPower(String powerType, String powerDescription) {
            super(powerType, powerDescription);
        }

    }

    public static class HeraPower extends Power {

        public HeraPower(String powerType, String powerDescription) {
            super(powerType, powerDescription);
        }

    }


    public static class HestiaPower extends Power {

        public HestiaPower(String powerType, String powerDescription) {
            super(powerType, powerDescription);
        }

    }

    public static class PoseidonPower extends Power {

        public PoseidonPower(String powerType, String powerDescription) {
            super(powerType, powerDescription);
        }

    }


    public static class TritonPower extends Power {

        public TritonPower(String powerType, String powerDescription) {
            super(powerType, powerDescription);
        }

    }
}