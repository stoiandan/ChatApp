package ro.uvt.chatapp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

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
	
	public static String tempN=null;
	public static InetAddress tempA=null;
	public static int tempI=-1;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//listViewContextMenu = new ContextMenu();
		//listViewContextMenu.getItems().add(new MenuItem("ceva"));
		try {
			_contactsList.add(new Contact("tset1", InetAddress.getByName("192.168.0.1")));
			_contactsList.add(new Contact("tset2", InetAddress.getByName("192.168.0.2")));
			_contactsList.add(new Contact("tset3", InetAddress.getByName("192.168.0.3")));
			_contactsList.add(new Contact("tset4", InetAddress.getByName("192.168.0.4")));
			contactsList.setItems(_contactsList);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		ChatAppModel.mainController = this;
		contactsList.setContextMenu(listViewContextMenu);
	}
	
	@FXML
	private MenuItem addContacts;
	
	@FXML
	private MenuItem recheckBtn;
	
	@FXML
	private MenuItem aboutBtn;
	
	@FXML
	ContextMenu listViewContextMenu;
	
	@FXML
	private ListView<Contact> contactsList;
	
	ObservableList<Contact> _contactsList = FXCollections.observableArrayList();
	
	@FXML
	private void RemoveContactsEventHandler(Event e){
		_contactsList.remove(contactsList.getSelectionModel().getSelectedIndex());
		contactsList.setItems(_contactsList);
	}
	
	@FXML
	private void EditContactsEventHandler(Event e) throws IOException{
		/*
		_contactsList.get(contactsList.getSelectionModel().getSelectedIndex()).setName("Edited");
		contactsList.setItems(_contactsList);
		contactsList.refresh();
		*/
		tempN=_contactsList.get(contactsList.getSelectionModel().getSelectedIndex()).getName();
		tempA=_contactsList.get(contactsList.getSelectionModel().getSelectedIndex()).getIpAddress();
		tempI=contactsList.getSelectionModel().getSelectedIndex();
		Parent root = FXMLLoader.load(getClass().getResource("./Contacts.fxml"));
		Scene scene2 = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene2);
		stage.show();	
		
	}
	
	
	@FXML
	private void OnContextMenuEventHandler(Event e){
		listViewContextMenu.show(contactsList.getScene().getWindow());
	}
	
	@FXML
	private void AddContactsEventHandler(Event e) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("./Contacts.fxml"));
		Scene scene2 = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene2);
		stage.show();
	}
	
	@FXML
	private void RecheckBtnEventHandler(Event e){
		System.out.println("Recheck");
	}
	
	@FXML
	private void AboutBtnEventHandler(Event e){
		System.out.println("About");
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
