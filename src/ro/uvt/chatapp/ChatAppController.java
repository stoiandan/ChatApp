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
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class ChatAppController implements Initializable {
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		ChatAppModel.mainController = this;
		initializeContactsList();
		contactsList.refresh();
	}
	ObservableList<Contact> _contactsList;
	
	@FXML
	ProgressBar ProgressBarBottom;
	
	@FXML
	private ListView<Contact> contactsList;
	
	@FXML
	ContextMenu listViewContextMenu;
	
	@FXML
	private void OnContextMenuEventHandler(Event e){
		if(contactsList.getSelectionModel().getSelectedIndex() != -1){
			listViewContextMenu.show(contactsList.getScene().getWindow());
		}
		else  listViewContextMenu.hide();
	}
	
	@FXML
	private void StartChatEventHandler(Event e) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("./ChatWindow.fxml"));
		Scene scene2 = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene2);
		stage.setResizable(false);
		stage.show();
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
	private void RemoveContactsEventHandler(Event e){
		_contactsList.remove(contactsList.getSelectionModel().getSelectedIndex());
		contactsList.setItems(_contactsList);
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
	
	@FXML
	private void RecheckEventHandler(Event e){
		contactsList.refresh();
	}
	
	@FXML
	private void ChangeRRHandler(Event e) throws IOException{
		
		ChatAppModel.settingThePort=false;
		
		Parent root = FXMLLoader.load(getClass().getResource("./Settings.fxml"));
		Scene scene2 = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene2);
		stage.setResizable(false);
		stage.show();
	}
	
	@FXML
	private void ChangePortHandler(Event e) throws IOException{
		
		ChatAppModel.settingThePort=true;
		
		Parent root = FXMLLoader.load(getClass().getResource("./Settings.fxml"));
		Scene scene2 = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene2);
		stage.setResizable(false);
		stage.show();
	}
	
	@FXML
	private void UsrInfoHandler(Event e){
		System.out.println("UsrInfoHandler - not implemented");
	}
	
	@FXML
	private void AboutHandler(Event e){
		System.out.println("AboutHandler - not implemented");
	}
	
	@FXML
	private void LicenseHandler(Event e){
		System.out.println("LicenseHandler - not implemented");
	}
	
	public void AddContactToList(String name,InetAddress ipAdress){
		Contact tmp = new Contact(name,ipAdress);
		
		_contactsList.add(tmp);
		contactsList.setItems(_contactsList);
		contactsList.refresh();
	}
	
	public void EditContactToList(String name,InetAddress ipAdress,int index){
		Contact tmp = new Contact(name,ipAdress);
		
		_contactsList.set(index,tmp);
		contactsList.setItems(_contactsList);
		contactsList.refresh();
	}
	
	
	@SuppressWarnings("unchecked")
	private void initializeContactsList(){
		try(InputStream os = new FileInputStream(new File(System.getProperty("user.home") + "/.contacts.bin"));
				ObjectInputStream objOut = new ObjectInputStream(os)
				)
			{
				_contactsList = FXCollections.observableList((ArrayList<Contact>) objOut.readObject());
				
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}finally{
				if(_contactsList == null){
					_contactsList = FXCollections.observableArrayList();	
				}
			}
		contactsList.setItems(_contactsList);
		contactsList.setContextMenu(listViewContextMenu);
	}
	
}
