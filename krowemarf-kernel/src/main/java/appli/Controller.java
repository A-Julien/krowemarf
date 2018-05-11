package appli;

import com.prckt.krowemarf.struct.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Controller {

    public Button buttonConnexion;
    public TextField textFieldIdentifiant;
    public PasswordField textFieldPassword;
    public Text textFieldMessage;

    public Client client;

    @FXML
    private Main main;



    public void onActionButtonConnexion() throws IOException {
        //Connexion
        int isAuth = 1;
        String login = textFieldIdentifiant.getText();
        String password = textFieldPassword.getText();

        client = new Client(1099, "127.0.0.1");
        client.setCredential(login, password);

        try {
            // Setting Credential of Client
            isAuth = client.run();

        } catch (Exception e) {
            e.printStackTrace();
        }


        if(isAuth == 0){

            Main.showClientView(client);
            //Redirection vers application demo.xml
            /*Stage stageDemo = new Stage();
            URL url = new File("C:\\Users\\Maxime\\IdeaProjects\\L3\\krowemarf\\krowemarf-kernel\\src\\main\\java\\appli\\demo.fxml").toURL();
            HBox root = (HBox)FXMLLoader.load(url);

            Scene scene = new Scene(root,800,500);

            stageDemo.setTitle("Application");
            stageDemo.setResizable(false);
            stageDemo.setScene(scene);
            stageDemo.show();
            */



            //
            Stage closedStage = (Stage) this.textFieldIdentifiant.getScene().getWindow();
            closedStage.close();

        } else{
            textFieldMessage.setText("Erreur de connexion");
        }

    }

}
