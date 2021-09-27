package application.net.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import application.net.stuff.Protocol;
 
//riceviamo le connessioni
public class Server implements Runnable {

	private ServerSocket server;
	private ExecutorService ex;
	
	public String startServer() {
		try {
			server= new ServerSocket(9527);
			ex=Executors.newCachedThreadPool();
			Thread t=new Thread(this);
			t.start();
			return Protocol.SUCCESS;
		} catch (IOException e) {
			e.printStackTrace();
			return Protocol.ERROR;
			
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				System.out.println("[SERVER] Waiting for connections...");

				Socket socket=server.accept();
				System.out.println("[SERVER] New client connected...");
				
				
				//connessi
				RequestHandler ds= new RequestHandler(socket);
				ex.submit(ds);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}
	
	
}
