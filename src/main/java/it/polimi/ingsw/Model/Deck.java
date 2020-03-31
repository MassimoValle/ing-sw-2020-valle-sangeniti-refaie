package it.polimi.ingsw.Model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Deck extends ArrayList<God>{

    private static Deck instance=null;
    //private List<God> gods;

    public Deck() {
        loadXML();
    }

    public static Deck getInstance() {
        if(instance==null)
            instance = new Deck();
        return instance;
    }

    private boolean loadXML(){
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

                God tmp = new God(godName, godDescription, new Power(godPowerType, godPowerDescription));

                this.add(tmp);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void removeGod(God selectedGod) {
        this.remove(selectedGod);
    }

    public God getGod(int choice) {
        God selectedGod = Deck.getInstance().get(choice-1);
        removeGod(Deck.getInstance().get(choice-1));
        return selectedGod;
    }


    @Override
    public String toString() {
        for (God god : instance) {
            System.out.println( (this.indexOf(god) + 1) + ") \nName: " + god.getGodName());
            System.out.println("Description: " + god.getGodDescription());
            System.out.println("Power description: " + god.getGodPower().getPowerDescription());
        }
        return null;
        }



}
