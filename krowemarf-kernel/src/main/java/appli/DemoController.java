package appli;

import com.prckt.krowemarf.struct.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class DemoController {
    @FXML
    private javafx.scene.control.TextArea messageArea;

    @FXML
    private ListView<String> listConnected;

    @FXML
    private javafx.scene.control.TextField inputField;

    @FXML
    private TabPane tabGrouper;

    @FXML
    private Tab globalTab;

    @FXML
    private javafx.scene.control.Button refreshList;

    @FXML
    private javafx.scene.control.Button sendButton;

    @FXML
    private VBox postGrouper;

    private Main main;

    @FXML Controller controller;

    private Client client;




    public void sendText() throws Exception {

        String sent = this.inputField.getText();


        int i = 1;



    }

    public void setClient(Client client) { // Setting the client-object in ClientViewController
        this.client = client;
    }

    public void refreshConnected(){

        // Get all user online

        // Actualiser la List View avec tous les Users


    }

    public void addTab(){

        Tab newTab = new Tab();
      //  newTab.setContent();

        tabGrouper.getTabs().add(newTab);
    }


    public void addPost(){

    }


    public void init(Main main) {
        this.main = main;
    }
}
