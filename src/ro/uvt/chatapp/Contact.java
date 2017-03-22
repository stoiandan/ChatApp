package ro.uvt.chatapp;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;

public class Contact implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private InetAddress ipAddress;
	
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
	
	public Contact(String name, InetAddress ipAddress) {
		 this.name = name;
		 this.ipAddress = ipAddress;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(name);
		
		try {
			if(ipAddress.isReachable(100)){
				str.append("   (Online)");
			}
			else{
				str.append("   (Offline)");
			}
		} catch (IOException e) {
		}
		
		return str.toString();
	}
	
	
	
}
