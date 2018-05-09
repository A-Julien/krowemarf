package appli.connexion;

import com.prckt.krowemarf.struct.Client;
import com.prckt.krowemarf.struct.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.rmi.RemoteException;


public class Main extends Application {

    private Client client;

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            URL url = new File("D:\\Travail\\Projets_git\\Krowemarf\\krowemarf-kernel\\src\\main\\java\\appli\\connexion\\sample.fxml").toURL();
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

    public void initPortAndAddr(int port, String addr) throws RemoteException {
        this.client = new Client(port, addr);
    }

    public void initClient(String login, String password) throws RemoteException {
        this.client.setCredential(login, password);
    }

    public Client getClient(){
        return this.client;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
