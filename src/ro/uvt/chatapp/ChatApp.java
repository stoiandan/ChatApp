package ro.uvt.chatapp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatApp extends Application {

	@Override
	public void start(Stage stage) throws Exception {
			Parent root = FXMLLoader.load(getClass().getResource("/ro/uvt/chatapp/ChatApp.fxml"));
			
			Scene scene = new Scene(root);
			
			stage.setTitle("ChatApp");
			stage.setScene(scene);
			
			stage.setOnCloseRequest( event ->{
				Listener.getInstance().stopListening(); // stops the thread, not really required because thread is daemon
				try{
					OutputStream os = new FileOutputStream(new File(System.getProperty("user.home") + "/.contacts.bin"));
					ObjectOutputStream objOut = new ObjectOutputStream(os);
					OutputStream os2 = new FileOutputStream(new File(System.getProperty("user.home") + "/.settings.bin"));
					ObjectOutputStream objOut2 = new ObjectOutputStream(os2);
					objOut.writeObject( (ArrayList<Contact>) ChatAppModel.mainController._contactsList.stream().collect(Collectors.toList()));
					objOut2.writeInt(ChatAppModel.port);
					objOut2.writeInt(ChatAppModel.refreshRate);
					objOut.close();
					objOut2.close();
					os.close();
					os2.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}) ;
			
			
			stage.show();
			
	}
	
	public static void main(String[] args) {
		ChatAppModel.initializeSettings();
		launch(args);
	}

}
