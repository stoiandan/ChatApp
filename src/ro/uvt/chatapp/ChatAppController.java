package ro.uvt.chatapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class ChatAppController implements Initializable {
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		ChatAppModel.mainController = this;
		ChatAppModel.contactsList =  _contactsList;
		ArrayList<Contact> tmp = new ArrayList<>();
		try(InputStream os = new FileInputStream(new File(System.getProperty("user.home") + "/.contacts.bin"));
				ObjectInputStream objOut = new ObjectInputStream(os)
				)
			{
				tmp = (ArrayList<Contact>)  objOut.readObject();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		_contactsList =FXCollections.observableList(tmp);
		contactsList.setItems(_contactsList);
		contactsList.setContextMenu(listViewContextMenu);
	}
	
	@FXML
	private MenuItem addContacts;
	
	@FXML
	ContextMenu listViewContextMenu;
	
	@FXML
	private ListView<Contact> contactsList;
	
	ObservableList<Contact> _contactsList;
	
	@FXML
	private void RemoveContactsEventHandler(Event e){
		_contactsList.remove(contactsList.getSelectionModel().getSelectedIndex());
		contactsList.setItems(_contactsList);
	}
	
	@FXML
	private void EditContactsEventHandler(Event e) throws IOException{
		
		ChatAppModel.currentSelectedContact = _contactsList.get(contactsList.getSelectionModel().getSelectedIndex());
		ChatAppModel.currentContactIndex = contactsList.getSelectionModel().getSelectedIndex();

		
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
	
	
	@FXML
	private void OnContextMenuEventHandler(Event e){
		listViewContextMenu.show(contactsList.getScene().getWindow());
	}
	
	
	@FXML
	private void AddContactsEventHandler(Event e) throws IOException
	{
		ChatAppModel.currentSelectedContact = null;
		ChatAppModel.currentContactIndex = -1;
		Parent root = FXMLLoader.load(getClass().getResource("./Contacts.fxml"));
		
		Scene scene2 = new Scene(root);
		
		Stage stage = new Stage();
		stage.setScene(scene2);
		stage.show();
	}
	
	public void EditContactToList(String name,InetAddress ipAdress,int index){
		Contact tmp = new Contact(name,ipAdress);
		
		_contactsList.set(index,tmp);
		contactsList.setItems(_contactsList);
		contactsList.refresh();
	}
	
	public void AddContactToList(String name,InetAddress ipAdress){
		Contact tmp = new Contact(name,ipAdress);
		
		_contactsList.add(tmp);
		contactsList.setItems(_contactsList);
		contactsList.refresh();
	}
}
