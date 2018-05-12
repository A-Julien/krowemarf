package appli.application;
	
import com.prckt.krowemarf.struct.Server;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;



public class Main extends Application {

    public Scene scene;
    private Stage stage;


	@Override
	public void start(Stage primaryStage) {
	    this.stage = primaryStage;
		try {
			URL url = new File("C:\\Users\\Maxime\\IdeaProjects\\L3\\krowemarf\\krowemarf-kernel\\src\\main\\java\\appli\\view\\Main.fxml").toURL();
			Parent root = FXMLLoader.load(url);
			scene = new Scene(root);
            stage.setTitle("Krowemarf");
            stage.setScene(scene);
            stage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		launch(args);

	}
}
