package ro.uvt.chatapp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * This class represents a server that listens for other clients that wish to chat with the current user
 */
public final class Listener extends Thread {

	private static Listener instance;
	
	private ServerSocket _server;
	
		
	/* static block, used to initialize the singleton instance
	 provides the needed mechanism to handle exceptions
	 this block gets executed only once, before any class constructor
	 */
	static{
			try {
				instance = new Listener();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	private Listener() throws IOException   {
		_server = new ServerSocket(ChatAppModel.port);
		setDaemon(true); // this is set in order for the thread to exit when the jvm exits
	}
	
	public static Listener getInstance(){
		return instance;
	}
	
	private boolean isRunning = false;
	
	public void stopListening(){
		isRunning = false;
	}
	
	/*
	 * Initializes a conversation, oncer a client requests it
	 * synchronized method in order to make it thread safe
	 */
	private synchronized void initializeConversation(Socket client){
		ChatAppModel.getInstance().mainController.intializeChat(client);
	}
	
	@Override
	public void run() {
		isRunning = true;
		while(isRunning){
			try {
				Socket client = _server.accept();
				initializeConversation(client);
			} catch (IOException e) {
				System.out.println("Connection error, failed to retrieve client");
			}
		}
	}
}
