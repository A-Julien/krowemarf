package appli.controller;


import com.prckt.krowemarf.struct.Client;
import javafx.fxml.FXML;
import appli.controller.tab.ConnexionController;
import appli.controller.tab.Tab1Controller;
import appli.controller.tab.Tab2Controller;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class MainController {
    public TabPane tabPane;
    public VBox vbox;
    public Label label;

    public Client client;

    @FXML ConnexionController conController;
	@FXML Tab1Controller tab1Controller;
    @FXML Tab2Controller tab2Controller;
	
	@FXML public void initialize() throws RemoteException {
        System.out.println("Application started");


        //tab1Controller.init(this);
		//tab2Controller.init(this);
        this.conController.init(this);
        this.tabPane.setVisible(false);

	}

	public void initTab1Tab2() throws IOException, SQLException, ClassNotFoundException {
        this.tab1Controller.init(this);
        this.tab2Controller.init(this);

        this.label.setText("Bienvenue dans le chat " + client.getUser().getLogin());
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return this.client;
    }


    //TODO BOUTON CROIX rouge ==> deconnecter le client . client.stop(), et fermer appli system.exit(1)..
}
