package appli.controller;


import com.prckt.krowemarf.components.DocumentLibrary.FileTypes.Text;
import com.prckt.krowemarf.components.Messenger._Messenger;
import com.prckt.krowemarf.components.Messenger.£MessengerClient;
import com.prckt.krowemarf.components.Posts._Posts;
import com.prckt.krowemarf.components.TypeMessage;
import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.components._DefaultMessage;
import com.prckt.krowemarf.services.ComponentManagerSevices._ComponentManager;
import com.prckt.krowemarf.services.UserManagerServices._User;
import com.prckt.krowemarf.struct.Client;
import javafx.fxml.FXML;
import appli.controller.tab.ConnexionController;
import appli.controller.tab.Tab1Controller;
import appli.controller.tab.Tab2Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.SerializationUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;

public class MainController {
    public TabPane tabPane;
    public VBox vbox;
    public Label label;

    public Client client;

    @FXML ConnexionController conController;
	@FXML Tab1Controller tab1Controller;
    @FXML Tab2Controller tab2Controller;
	
	@FXML public void initialize() throws IOException, SQLException, ClassNotFoundException {
        System.out.println("Application started");


        //tab1Controller.init(this);
		//tab2Controller.init(this);
        this.conController.init(this);
        this.tabPane.setVisible(false);



	}

	public void initTab1Tab2() throws IOException, SQLException, ClassNotFoundException {
        this.tab1Controller.init(this);
        this.tab2Controller.init(this);

        initPost();
        initMP();
    }

    /*
    Initialise les posts pour le panneau de gauche
     */
    public void initPost() throws SQLException, IOException, ClassNotFoundException {
        _ComponentManager cmp = client.getComponentManager();
        _Posts post = (_Posts) cmp.getComponantByName("commentaires");
        HashMap<Integer,_DefaultMessage> hm =  post.loadPost();

        for (_DefaultMessage message: hm.values()) {

            label.setText(label.getText() + "Le : " + message.getDate().toString() +
                            " Par : " + message.getSender().getLogin() +
                            " \n Contenu : " + message.getContent() + "\n\n\n");
        }
    }

    public void initMP() throws IOException {
        Tab newTab = new Tab("MP : Seb");
        tabPane.getTabs().add(newTab);

        VBox newVbox = new VBox();

        TextArea newTextArea = new TextArea();
        newTextArea.setEditable(false);

        newTextArea.setText("le message privee");
        newTextArea.setVisible(true);

        HBox newHbox = new HBox();


        TextArea txtMessage = new TextArea();
        txtMessage.setPromptText("Votre message");

        Button btnEnvoyer = new Button();
        btnEnvoyer.setText("Envoyer");

        newTab.setContent(newVbox);

        newHbox.getChildren().addAll(txtMessage, btnEnvoyer);

        newVbox.getChildren().addAll(newTextArea, newHbox);


    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return this.client;
    }


    //TODO BOUTON CROIX rouge ==> deconnecter le client . client.stop(), et fermer appli system.exit(1)..
}
