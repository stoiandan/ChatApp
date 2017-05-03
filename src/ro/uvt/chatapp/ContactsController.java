package ro.uvt.chatapp;



import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ContactsController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ChatAppModel.contactsController = this;
		
			Platform.runLater( () -> {
				if(ChatAppModel.currentSelectedContact != null){
				ipTextField.setText(ChatAppModel.currentSelectedContact.getIpAddress().getHostName());
				nameTextField.setText(ChatAppModel.currentSelectedContact.getName());
				}
			});
	}
	
	@FXML
    TextField ipTextField;
	
	@FXML
	 TextField nameTextField;
	
	@FXML
	private void AddContactsEnterPressEventHandler(Event e) throws UnknownHostException{
		
		if( (ipTextField.getText().length() < 7) || (nameTextField.getText().length() <3) ){	
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ChatApp");
			alert.setHeaderText("Warning");
			alert.setContentText("No IP or Name provided, please provide information");
			alert.showAndWait();
			return;
		}
		if(ChatAppModel.currentSelectedContact !=null){
			ChatAppModel.mainController.EditContactToList(nameTextField.getText(), InetAddress.getByName(ipTextField.getText()),ChatAppModel.currentContactIndex);
		}
		else{
		ChatAppModel.mainController.AddContactToList(nameTextField.getText(), InetAddress.getByName(ipTextField.getText()));
		}
		Stage window = (Stage) nameTextField.getScene().getWindow();
		window.close();
		ChatAppModel.currentSelectedContact = null;		
		ChatAppModel.currentContactIndex = -1;
		ChatAppModel.getInstance().isContactsWindowOn = false;
	}

	
}
