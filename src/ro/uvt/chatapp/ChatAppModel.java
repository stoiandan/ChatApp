package ro.uvt.chatapp;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class ChatAppModel {
		private static final ChatAppModel _instance = new ChatAppModel();
	
		public static ChatAppModel getInstance(){
			return _instance;
		}
	
		private ChatAppModel(){
			
		}
		
		public static int refreshRate=200;//need to be Serializable
		public static int port=12345;//need to be Serializable
		
		public static boolean settingThePort=false;
		public static ChatAppController mainController;
		public static ContactsController contactsController;
		public static SettingController settingController;
		public static Contact currentSelectedContact;
		public static int currentContactIndex = -1;
		public static List<Socket> clients = new ArrayList<>();
		
		private boolean isContactsWindowOn = false;
		
		public void getContactsWindow(){
			if(isContactsWindowOn) return;
			isContactsWindowOn = true;
			
			Platform.runLater( () -> {
				Parent root;
				try {
					root = FXMLLoader.load(getClass().getResource("./Contacts.fxml"));
					
					Scene scene2 = new Scene(root);
					Stage stage = new Stage();
					stage.setScene(scene2);
					
					stage.show();	
				} catch (IOException e1) {
					e1.printStackTrace();
					}
				});
		}
		

}	
