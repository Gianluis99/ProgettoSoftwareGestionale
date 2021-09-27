package application.net.stuff;

import java.io.Serializable;

public class User implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2605069751905657613L;

	
	
	private String email;
	private String password;
	
	
	public User(String email,String pass) {
		this.email=email;
		this.password=pass;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	
	
}
