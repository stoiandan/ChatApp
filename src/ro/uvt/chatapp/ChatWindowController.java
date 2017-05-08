package ro.uvt.chatapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class ChatWindowController implements Initializable {
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		receiver=ChatAppModel.currentSelectedContact;
		conn = new NetwokConnection();	
		conn.start();
	}
	private NetwokConnection conn;
	private Contact receiver;
	
	@FXML
	TextField messageTextField;
	
	@FXML 
	public void BtnEventHandler(Event e){
		conn.writeToServer(messageTextField.getText());
		}
	
	/*
	 *  Inner class that handles data transfer
	 */
	private class NetwokConnection extends Thread {
		
		Socket client;
		OutputStream out; // used to write to receiver;
		InputStream in; // used to read incoming text;
		
		public  NetwokConnection(){
			setDaemon(true);
			try{
			client  = new Socket(receiver.getIpAddress(), ChatAppModel.port); // connects the client to the server
			out = client.getOutputStream();
			in  = client.getInputStream();
			}catch(IOException e){
				System.out.println("Network stream initailization problem");
			}
		}
		
		@Override
		/* 
		 *
		 */
		public void run() {
			byte[] buffer = new byte[4096];
			while(true){
				 try{
				 in.read(buffer);
				 System.out.println(new String(buffer));
				 System.out.println("ajunge");
				 }catch (IOException e) {
					 System.out.println("Data stream error");
					 break;
				}
			}
			
		}
		
		public void writeToServer(String message){
			try{
			out.write(message.getBytes());
			}catch(IOException e){
				System.out.println("Network receiver stream error");
			}
		}
	}
	
}
