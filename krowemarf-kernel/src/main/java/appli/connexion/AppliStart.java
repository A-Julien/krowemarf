package appli.connexion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class AppliStart extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        try {

            URL url = new File("D:\\Travail\\Projets_git\\Krowemarf\\krowemarf-kernel\\src\\main\\java\\appli\\connexion\\demo.fxml").toURL();
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
