package ro.uvt.chatapp;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ChatWindowController implements Initializable {
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		reciver=ChatAppModel.currentSelectedContact;
		
	}

	private Contact reciver;
	
	@FXML 
	public void BtnEventHandler(Event e){
		System.out.println(reciver.getIpAddress().toString());
	}

	
	
}
