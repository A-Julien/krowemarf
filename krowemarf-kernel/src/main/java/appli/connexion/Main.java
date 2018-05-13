package appli.connexion;

import com.prckt.krowemarf.struct.Server.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            URL url = new File("C:\\Users\\Maxime\\IdeaProjects\\L3\\krowemarf\\krowemarf-kernel\\src\\main\\java\\appli\\connexion\\sample.fxml").toURL();
            VBox root = (VBox) FXMLLoader.load(url);
            primaryStage.setTitle("Projet MIAGE");
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root, 900, 300));
            primaryStage.show();


            Server server = new Server(1099, "127.0.0.1");
            server.run();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //voici un commentaire


    public static void main(String[] args) {
        launch(args);
    }
}
