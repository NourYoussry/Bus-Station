package models;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SceneHelper {


    private FXMLLoader loader;
    private Stage stage;


    public SceneHelper() {

        loader = new FXMLLoader();
        stage = new Stage();
    }


    public void initStage(String fxml, String styleSheet, int width, int height) throws IOException {

        Parent root;
        Scene scene;

        loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();

        scene = new Scene(root, width, height);
        scene.getStylesheets().add(styleSheet);
        scene.setFill(Color.TRANSPARENT);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
    }


    public FXMLLoader getLoader() {
        return loader;
    }

    public Stage getStage() {
        return stage;
    }

}
