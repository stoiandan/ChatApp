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
			Parent root = FXMLLoader.load(getClass().getResource("./ChatApp.fxml"));
			
			Scene scene = new Scene(root);
			
			stage.setTitle("ChatApp");
			stage.setScene(scene);
			
			stage.setOnCloseRequest( event ->{
				
				try(OutputStream os = new FileOutputStream(new File(System.getProperty("user.home") + "/.contacts.bin"));
					ObjectOutputStream objOut = new ObjectOutputStream(os)
					)
				{
					objOut.writeObject( (ArrayList<Contact>) ChatAppModel.mainController._contactsList.stream().collect(Collectors.toList()));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}) ;
			
			
			stage.show();
			
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
