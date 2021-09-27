package application.net.Server;


import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import application.net.stuff.Protocol;

public class MainServer  {


	
	public static void main(String[] args) {
		
		JFrame f=new JFrame("Server");
		f.setSize(500,500);

		
		
		JTextArea text=new JTextArea();
		text.setLineWrap(true);
		text.setEditable(false);
		text.setBackground(Color.gray);
		f.add(text);		
		
		
		
		Server server= new Server();
		if(server.startServer().equals(Protocol.SUCCESS))
			text.setText("Server is started successfully");
		else
			text.setText("Error while the server was starting ");

		
			
		
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
