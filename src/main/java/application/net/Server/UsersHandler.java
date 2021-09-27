package application.net.Server;

import java.util.HashMap;


public class UsersHandler {

	
	
	private static HashMap<String, RequestHandler> users=new HashMap<String, RequestHandler>();
	
	public  synchronized static boolean addUser(String name, RequestHandler handler) {
		if(users.containsKey(name))
			return false;
		
		users.put(name, handler);
		return true;
			
			
	}
	
	public  synchronized static void removeUser(String name) {
		users.remove(name);
		
	}
	
	
}
