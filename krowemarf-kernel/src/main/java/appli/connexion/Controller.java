package appli.connexion;

import com.prckt.krowemarf.struct.Client.Client;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.rmi.RemoteException;

public class Controller {


    public Button buttonConnexion;
    public TextField textFieldIdentifiant;
    public PasswordField textFieldPassword;
    public Text textFieldMessage;


    public void onActionButtonConnexion() throws RemoteException {
        //Connexion
        int isAuth = 1;
        String login = textFieldIdentifiant.getText();
        String password = textFieldPassword.getText();


        Client client = new Client(1099, "127.0.0.1");
        client.setCredential(login, password);

        try {
            isAuth = client.run();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if(isAuth == 0){
            //Redirection vers =>
            textFieldMessage.setText("Vous êtes connecté " + login);
        } else{
            textFieldMessage.setText("Erreur de connexion");
        }


    }


}
