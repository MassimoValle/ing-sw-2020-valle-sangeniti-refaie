package it.polimi.ingsw.Server.Model.God;

import it.polimi.ingsw.Server.Model.God.GodsPower.*;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Deck extends ArrayList<God> implements Serializable {

    private static Deck instance=null;
    protected GameMap map;

    public Deck() { }

    public void setMap(GameMap map){
        this.map = map;
        loadXML();
    }

    public static Deck getInstance() {
        if(instance==null)
            instance = new Deck();
        return instance;
    }

    protected boolean loadXML(){
        File file = new File("src/main/Resources/XMLs/gods.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("god");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element eElement = (Element) nodeList.item(i);

                String godName = eElement.getElementsByTagName("name").item(0).getTextContent();
                String godDescription = eElement.getElementsByTagName("description").item(0).getTextContent();
                String godPowerType = eElement.getElementsByTagName("powerType").item(0).getTextContent();
                String godPowerDescription = eElement.getElementsByTagName("powerDescription").item(0).getTextContent();

                God tmp = createGod(godName, godDescription, godPowerType, godPowerDescription);

                this.add(tmp);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected God createGod(String godName, String godDescription, String godPowerType, String godPowerDescription) {

        return switch (godName) {
            case "Apollo" -> new God(godName, godDescription, new ApolloPower(godPowerType, godPowerDescription, map));
            case "Artemis" -> new God(godName, godDescription, new ArtemisPower(godPowerType, godPowerDescription, map));
            case "Athena" -> new God(godName, godDescription, new AthenaPower(godPowerType, godPowerDescription, map));
            case "Atlas" -> new God(godName, godDescription, new AtlasPower(godPowerType, godPowerDescription, map));
            case "Demeter" -> new God(godName, godDescription, new DemeterPower(godPowerType, godPowerDescription, map));
            case "Hephaestus" -> new God(godName, godDescription, new HephaestusPower(godPowerType, godPowerDescription, map));
            case "Minotaur" -> new God(godName, godDescription, new MinotaurPower(godPowerType, godPowerDescription, map));
            case "Pan" -> new God(godName, godDescription, new PanPower(godPowerType, godPowerDescription, map));
            case "Prometheus" -> new God(godName, godDescription, new PrometheusPower(godPowerType, godPowerDescription, map));
            case "Chronus" -> new God(godName, godDescription, new ChronusPower(godPowerType, godPowerDescription, map));
            case "Hera" -> new God(godName, godDescription, new HeraPower(godPowerType, godPowerDescription, map));
            case "Hestia" -> new God(godName, godDescription, new HestiaPower(godPowerType, godPowerDescription, map));
            case "Zeus" -> new God(godName, godDescription, new ZeusPower(godPowerType, godPowerDescription, map));
            case "Triton" -> new God(godName, godDescription, new TritonPower(godPowerType, godPowerDescription, map));
            default -> null;
        };
    }

    private void removeGod(God selectedGod) {
        this.remove(selectedGod);
    }


    /**
     * Return the selected {@link God god}.
     *
     * @param choice index
     * @return selectedGod god
     */
    public God getGod(int choice) {
        God selectedGod = Deck.getInstance().get(choice);
        return selectedGod;
    }

    public God getGodByName(String string) {

        for (God god : Deck.getInstance()) {
            if (god.getGodName().equals(string))
                return god;
        }

        return null;
    }



    //Print to screen all the gods inside the Deck
    @Override
    public String toString() {
        String string = "Vuota";
        for ( int i = 0; i < getInstance().size(); i++) {
            string = string.concat(getInstance().getGod(i).toString() + "\n");
        }
        return string;
        }



}
