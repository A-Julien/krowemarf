package appli.connexion;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

import static javafx.application.Application.launch;

public class AppliStart {

    public static void start(Stage primaryStage){

        try {

            URL url = new File("").toURL();
            HBox root = (HBox)FXMLLoader.load(url);

//            URL urlCSS = new File("application.css").toURL();
            Scene scene = new Scene(root,800,500);
//            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();



        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
