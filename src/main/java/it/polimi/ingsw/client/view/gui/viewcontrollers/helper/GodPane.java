package it.polimi.ingsw.client.view.gui.viewcontrollers.helper;

import it.polimi.ingsw.client.view.gui.ParameterListener;
import it.polimi.ingsw.server.model.god.God;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class GodPane extends AnchorPane {

    ParameterListener parameterListener = ParameterListener.getInstance();
    private String parameter = null;


    public GodPane(AnchorPane container, ImageView imageView, God god){

        this.getChildren().add(imageView);
        this.setId(god.getGodName());

        imageView.fitHeightProperty().bind(container.heightProperty().divide(3.5));
        imageView.fitWidthProperty().bind(container.widthProperty().divide(6));

        AnchorPane.setTopAnchor(imageView, 0.0);
        AnchorPane.setLeftAnchor(imageView, 0.0);
        AnchorPane.setRightAnchor(imageView, 0.0);
        AnchorPane.setBottomAnchor(imageView, 0.0);

        this.setOnMouseClicked(event -> {
            AnchorPane pane1 = (AnchorPane) event.getSource();
            parameter = pane1.getId();
            pane1.setDisable(true);
            pane1.setOpacity(0.7);
            parameterListener.setParameter(parameter);
        });

    }
}
