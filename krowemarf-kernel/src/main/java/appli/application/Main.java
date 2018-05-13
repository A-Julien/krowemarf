package appli.application;
	
import appli.controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.File;
import java.net.URL;



public class Main extends Application {

    public Scene scene;
    private Stage stage;


	@Override
	public void start(Stage primaryStage) {
	    this.stage = primaryStage;
		try {
			URL url = new File("src\\main\\java\\appli\\view\\Main.fxml").toURI().toURL();
			Parent root = FXMLLoader.load(url);
			scene = new Scene(root);
            stage.setTitle("Krowemarf");
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent windowEvent) {
				    System.out.println("Closing");
                    Platform.exit();
                    System.exit(0);
				}
			});
		} catch(Exception e) { e.printStackTrace(); }
	}
}
