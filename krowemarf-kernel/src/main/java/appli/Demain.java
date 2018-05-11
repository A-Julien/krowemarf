package appli;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Demain {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(){
        try {
            Stage primaryStage = new Stage();
            VBox root = (VBox)FXMLLoader.load(getClass().getResource("demo.fxml"));
            Scene scene = new Scene(root,800,500);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
