package ro.uvt.chatapp;

import java.util.List;

public final class ChatAppModel {
		private static ChatAppModel _instance = new ChatAppModel();
	
		public static ChatAppModel getInstance(){
			return _instance;
		}
	
		private ChatAppModel(){
			
		}
		
		public static ChatAppController mainController;
		public static ContactsController contactsController;
		public static Contact currentSelectedContact;
		public static int currentContactIndex = -1;
}	
