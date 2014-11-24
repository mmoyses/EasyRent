package edu.wpi.easyrent.security;

public class SecurityContext {
	
	private static SecurityContext instance;
	
	private String username;
	
	private SecurityContext() {
		
	}
	
	public void setUser(String username) {
		this.username = username;
	}
	
	public boolean isLandlord() {
		return username.equals("landlord");
	}
	
	public static SecurityContext getInstance() {
		if (instance == null) {
			instance = new SecurityContext();
		}
		return instance;
	}

}
