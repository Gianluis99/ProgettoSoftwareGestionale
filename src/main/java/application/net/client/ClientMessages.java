package application.net.client;

import java.util.ArrayList;

public class ClientMessages {
	
	private static ArrayList<String> messages=new ArrayList<String>();
	
	public synchronized static void addMessage(String m) {
			messages.add(m);
	}


}
