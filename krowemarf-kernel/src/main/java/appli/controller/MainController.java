package appli.controller;


import appli.controller.tab.Tab3Controller;
import com.prckt.krowemarf.components.Messenger._Messenger;
import com.prckt.krowemarf.components.Messenger.£MessengerClient;
import com.prckt.krowemarf.components.Posts._Posts;
import com.prckt.krowemarf.components._DefaultMessage;
import com.prckt.krowemarf.services.ClientListenerManagerServices.£ClientListener;
import com.prckt.krowemarf.services.ComponentManagerSevices._ComponentManager;
import com.prckt.krowemarf.services.UserManagerServices._User;
import com.prckt.krowemarf.struct.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import appli.controller.tab.ConnexionController;
import appli.controller.tab.Tab1Controller;
import appli.controller.tab.Tab2Controller;
import javafx.scene.control.*;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;

public class MainController {
    public TabPane tabPane;
    public VBox vbox;
    public Label label;
    public Button rafraichir;

    public Client client;

    @FXML ConnexionController conController;
	@FXML Tab1Controller tab1Controller;
    @FXML Tab2Controller tab2Controller;
    @FXML Tab3Controller tab3Controller;
	
	@FXML public void initialize() throws IOException, SQLException, ClassNotFoundException {
        System.out.println("Application started");

        this.conController.init(this);
        this.tabPane.setVisible(false);



	}

	public void initTab1Tab2() throws IOException, SQLException, ClassNotFoundException {
        this.tab1Controller.init(this);
        this.tab2Controller.init(this);


        initPost();

        this.tab3Controller.init(this);
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

    public void sendMP(String destinataire) throws IOException {
        Tab newTab = new Tab("MP : " + destinataire);
        tabPane.getTabs().add(newTab);

        VBox newVbox = new VBox();

        TextArea newTextArea = new TextArea();
        newTextArea.setEditable(false);

        newTextArea.setVisible(true);

        HBox newHbox = new HBox();

        TextArea txtMessage = new TextArea();
        txtMessage.setPromptText("Votre message");

        Button btnEnvoyer = new Button();

        btnEnvoyer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(txtMessage.getText() + " à " + destinataire );
            }
        });
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

    public void refresh(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        initPost();
        this.tab3Controller.init(this);

    }
}
