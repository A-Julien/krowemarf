package appli.controller;


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
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.lang.reflect.Array;
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

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return this.client;
    }


    //TODO BOUTON CROIX rouge ==> deconnecter le client . client.stop(), et fermer appli system.exit(1)..
}
