package appli.controller.tab;

import appli.application.Main;
import com.prckt.krowemarf.struct.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import appli.controller.MainController;

import java.rmi.RemoteException;

public class ConnexionController {

    private MainController main;
    public Client client;

    @FXML public Label lblConnexion;
    @FXML private TextField txtLogin;
    @FXML private TextField txtPassword;
    @FXML private Button btnConnexion;



    @FXML private void btn2ConnexionClicked(ActionEvent actionEvent) throws RemoteException {

        int isAuth = 1;
        String login = txtLogin.getText();
        String password = txtPassword.getText();

        client = new Client(1099, "127.0.0.1");
        client.setCredential(login, password);

        try {
            // Setting Credential of Client
            isAuth = client.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(isAuth == 0){

            //Enleve la partie login
            main.vbox.setVisible(false);
            //Affichage du chat
            main.tabPane.setVisible(true);
            //Envoie de l'objet Client
            main.setClient(client);

            main.initTab1Tab2();


        } else{
            lblConnexion.setText("Erreur de connexion");
        }
    }

    public void init(MainController mainController) {
        main = mainController;
    }

}
