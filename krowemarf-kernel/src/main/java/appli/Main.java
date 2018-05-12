package appli;

import com.prckt.krowemarf.struct.Client;
import com.prckt.krowemarf.struct.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class Main extends Application {
    private static VBox main;
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        try {
            URL url = new File("src\\main\\java\\appli\\sample.fxml").toURI().toURL();
            main = (VBox) FXMLLoader.load(url);

            primaryStage.setTitle("Projet MIAGE");
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(main, 900, 300));
            primaryStage.show();

            Server server = new Server(1099, "127.0.0.1");
            server.run();
        } catch (Exception e){ e.printStackTrace(); }
    }

    public static void showClientView(Client client) throws IOException { // Taking the client-object as an argument from LoginViewController
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("src\\main\\java\\appli\\demo.fxml"));
        main = loader.load();

        DemoController cvc = loader.getController(); // This did the "trick"
        cvc.setClient(client); // Passing the client-object to the ClientViewController

        Scene scene = new Scene(main, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
