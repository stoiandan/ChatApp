package ro.uvt.chatapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
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
		
		public static int refreshRate;
		public static int port;
		
		public static boolean settingThePort=false;
		public static ChatAppController mainController;
		public static ContactsController contactsController;
		public static SettingController settingController;
		public static Contact currentSelectedContact;
		public static int currentContactIndex = -1;
		
		public boolean isContactsWindowOn = false;
		
		public void getContactsWindow(){
			if(isContactsWindowOn) return;
			isContactsWindowOn = true;
			
			Platform.runLater( () -> {
				Parent root;
				try {
					root = FXMLLoader.load(getClass().getResource("/ro/uvt/chatapp/Contacts.fxml"));
					
					Scene scene2 = new Scene(root);
					Stage stage = new Stage();
					stage.setScene(scene2);
					if(ChatAppModel.currentSelectedContact != null){
						stage.setTitle("Edit Contact");
					}
					else{
						stage.setTitle("Add Contact");
					}
					stage.setOnCloseRequest(event ->{
						ChatAppModel.getInstance().isContactsWindowOn = false;
						ChatAppModel.currentSelectedContact = null;		
						ChatAppModel.currentContactIndex = -1;
					});
					stage.show();	
				} catch (IOException e1) {
					e1.printStackTrace();
					}
				});
			
		}
		
		public static void initializeSettings(){
			try(InputStream os = new FileInputStream(new File(System.getProperty("user.home") + "/.settings.bin"));
					ObjectInputStream objOut = new ObjectInputStream(os)
					)
				{
					ChatAppModel.port=objOut.readInt();
					ChatAppModel.refreshRate=objOut.readInt();
				} catch (FileNotFoundException e) {
					ChatAppModel.port=12345;
					ChatAppModel.refreshRate=200;
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

}	
