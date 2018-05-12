package appli.controller.tab;


import com.prckt.krowemarf.components.Messenger._Messenger;

import com.prckt.krowemarf.components.Messenger.£MessengerClient;
import com.prckt.krowemarf.components.TypeMessage;
import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.components._DefaultMessage;
import com.prckt.krowemarf.services.ComponentManagerSevices._ComponentManager;
import com.prckt.krowemarf.services.UserManagerServices._User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import appli.controller.MainController;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class Tab2Controller {

	private MainController main;

	@FXML
	TextArea txtMessage;
	@FXML
	TextArea txtChat;

	public void init(MainController mainController) throws IOException, SQLException, ClassNotFoundException {
		main = mainController;

		_ComponentManager cmp = main.client.getComponentManager();
		_Component messaging = cmp.getComponantByName("chat2");
		_Messenger chat = (_Messenger) messaging;
		chat.subscribe(new £MessengerClient() {
			@Override
			public void onReceive(_DefaultMessage message) throws RemoteException {
				txtChat.setText(txtChat.getText() + "\n" + message.toStrings());
			}

			@Override
			public void onLeave(_User user) throws RemoteException {
				txtMessage.setText(txtChat.getText() + "\n" + user.getLogin() + " a quitté");
			}
		}, main.client.getUser());


		chat.reLoadMessage(main.client.getUser());
	}


	public void btn2EnvoyerClicked(ActionEvent actionEvent) throws RemoteException, SQLException {
		_ComponentManager cmp = main.client.getComponentManager();
		_Component messaging = cmp.getComponantByName("chat2");
		_Messenger chat = (_Messenger) messaging;

		if(!txtMessage.getText().isEmpty()){
			TypeMessage typeMessage = new TypeMessage(this.txtMessage.getText(), main.client.getUser());

			chat.postMessage(main.client.getUser(), typeMessage);
			chat.saveMessage(SerializationUtils.serialize(typeMessage));

			//remise a zero de l'input text
			txtMessage.setText("");
		}



	}

}
