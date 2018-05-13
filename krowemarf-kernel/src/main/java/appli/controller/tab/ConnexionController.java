package appli.controller.tab;

import com.prckt.krowemarf.struct.Client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import appli.controller.MainController;
import java.io.IOException;
import java.sql.SQLException;

public class ConnexionController {
    private MainController main;
    public Client client;

    @FXML public Label lblConnexion;
    @FXML private TextField txtLogin;
    @FXML private TextField txtPassword;
    @FXML private Button btnConnexion;

    @FXML private void btn2ConnexionClicked(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        int isAuth = 1;
        String login = txtLogin.getText();
        String password = txtPassword.getText();

        this.client = new Client(1099, "127.0.0.1");
        this.client.setCredential(login, password);

        try {
            // Setting Credential of Client
            isAuth = this.client.run();
        } catch (Exception e) { e.printStackTrace(); }

        if(isAuth == 0){

            //Enleve la partie login
            main.vbox.setVisible(false);
            //Affichage du chat
            main.tabPane.setVisible(true);
            //Envoie de l'objet Client
            main.setClient(client);

            main.initTab1Tab2();
        } else{ lblConnexion.setText("Erreur de connexion"); }
    }

    public void init(MainController mainController) { this.main = mainController; }
}
