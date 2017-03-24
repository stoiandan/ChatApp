package ro.uvt.chatapp;

public final class ChatAppModel {
		private static ChatAppModel _instance = new ChatAppModel();
	
		public static ChatAppModel getInstance(){
			return _instance;
		}
	
		private ChatAppModel(){
			
		}
		
		public static int refreshRate=200;//need to be Serializable
		public static int port=12345;//need to be Serializable
		
		public static boolean settingThePort=false;
		public static ChatAppController mainController;
		public static ContactsController contactsController;
		public static SettingController settingController;
		public static Contact currentSelectedContact;
		public static int currentContactIndex = -1;
}	
