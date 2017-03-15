package ro.uvt.chatapp;



import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

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
		if(ChatAppController.tempA !=null && ChatAppController.tempN !=null){
			ipTextField.setText(ChatAppController.tempA.getCanonicalHostName());
			nameTextField.setText(ChatAppController.tempN);
		}
		
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
		if(ChatAppController.tempA !=null && ChatAppController.tempN !=null){
			ChatAppModel.mainController.EditContactToList(nameTextField.getText(), InetAddress.getByName(ipTextField.getText()),ChatAppController.tempI);
			ChatAppController.tempN=null;
			ChatAppController.tempA=null;
			ChatAppController.tempI=-1;
		}
		else{
		ChatAppModel.mainController.AddContactToList(nameTextField.getText(), InetAddress.getByName(ipTextField.getText()));
		}
		Stage window = (Stage) nameTextField.getScene().getWindow();
		window.close();
	}
	
}