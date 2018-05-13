package appli.controller.tab;


import appli.controller.MainController;
import com.prckt.krowemarf.services.UserManagerServices._User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;


public class Tab3Controller {

	private MainController main;

	private int rowIndex = 0;

	public ArrayList<_User> listUsers = new ArrayList<_User>();

    @FXML
    AnchorPane anchorPane;
    @FXML
    GridPane gridPane;


	public void init(MainController mainController) throws RemoteException {
		main = mainController;

		ArrayList<_User> users = main.client.getAllUsersConnected();

		for(_User user : users){
		    if(!user.getLogin().equals(main.client.getUser().getLogin())){

		        if(!listUsers.contains(user)){
                    listUsers.add(user);
                    Button button = new Button();
                    button.setText("Envoyer un MP à " + user.getLogin());
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                main.sendMP(user.getLogin());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    gridPane.addRow(rowIndex, button);
                    rowIndex++;
                }

            }
        }


	}
}
