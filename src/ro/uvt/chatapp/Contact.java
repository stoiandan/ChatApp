package ro.uvt.chatapp;

import java.net.InetAddress;

public class Contact {
	private String name;
	private InetAddress ipAddress;
	private boolean isOnline;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public InetAddress getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(InetAddress ipAddress) {
		this.ipAddress = ipAddress;
	}
	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	
	public Contact(String name, InetAddress ipAddress) {
		 this.name = name;
		 this.ipAddress = ipAddress;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(name);
		
		if(isOnline == true){
			str.append("   (Online)");
		}
		else{
			str.append("   (Offline)");
		}
		
		return str.toString();
	}
	
	
	
}
