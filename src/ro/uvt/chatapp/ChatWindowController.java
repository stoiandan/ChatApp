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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatWindowController implements Initializable {
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		conn =  client != null ? new NetwokConnection(client) :
								 new NetwokConnection();
		conn.start();
	}
	private NetwokConnection conn;
	private Contact receiver=null;
	private Socket client;
	
	// Initializes controller with the client that 
	// requested the conversation
    public ChatWindowController(Socket client){
    	this.client = client;
    	if(ChatAppModel.mainController.contactTaken(client.getInetAddress())==-1)
			receiver=new Contact("Unknown("+client.getInetAddress()+")",client.getInetAddress());
		else
			receiver=ChatAppModel.mainController._contactsList.get(ChatAppModel.mainController.contactTaken(client.getInetAddress()));
    }
    
    public ChatWindowController() {
    	receiver=ChatAppModel.currentSelectedContact;
    	ChatAppModel.currentSelectedContact=null;
	}
	
	@FXML
	TextField messageTextField;
	
	@FXML
	TextArea messageTextArea;
	
	@FXML 
	public void BtnEventHandler(Event e){
		if(messageTextField.getText()!=null){
			conn.writeToServer(messageTextField.getText());
			messageTextArea.appendText("You: "+messageTextField.getText()+"\n");
			messageTextField.setText(null);
		}
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
				messageTextArea.appendText("\nNetwork stream initailization problem \n");
				System.out.println("Network stream initailization problem");
			}
		}
		
		public NetwokConnection(Socket client){
			this.client = client;
			try{
			out = client.getOutputStream();
			in = client.getInputStream();
			} catch (IOException e) {
				messageTextArea.appendText("\nNetwork stream initailization problem \n");
				System.out.println("Network stream initailization problem ");
			}
		}
		
		/* 
		 * Thread method used to read data from the other end of the connection
		 */
		@Override
		public void run() {
			byte[] buffer = new byte[4096];
			while(true){
				 try{
				 in.read(buffer);
				 messageTextArea.appendText(receiver.getName()+": "+new String(buffer)+"\n");
				 
				 }catch (IOException e) {
					 messageTextArea.appendText("\nData stream error \n");
					 System.out.println("Data stream error ");
					 break;
				}
			}
			
		}
		
		// Method used to write messages to the other end of the connection
		public void writeToServer(String message){
			try{
			out.write(message.getBytes());
			}catch(IOException e){
				messageTextArea.appendText("\nNetwork receiver stream error \n");
				System.out.println("Network receiver stream error ");
			}
		}
	}
	
}
