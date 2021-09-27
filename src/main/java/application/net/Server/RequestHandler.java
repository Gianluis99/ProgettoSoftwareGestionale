 package application.net.Server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import application.modelObject.Cliente;
import application.modelObject.Evento;
import application.modelObject.Fornitore;
import application.modelObject.HelpMessage;
import application.modelObject.Prodotto;
import application.modelObject.Territorio;
import application.net.stuff.Other;
import application.net.stuff.Protocol;
import application.net.stuff.User;

public class RequestHandler implements Runnable {

	//gestisce le richiesteb del client
	private Socket socket;
	
	//Scambio oggetti 
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private String userIn="";

	
	public RequestHandler(Socket s) {
		this.socket=s;
		
		try {
			this.output=new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		
		try {
			this.input=new ObjectInputStream(socket.getInputStream());
			String in=(String) input.readObject(); 
			
			
			//caso registrazione
			 if(in.equals(Protocol.REGISTRATION)) {
				User user =(User) input.readObject();
				
				if(Database.getInstance().exsUser(user)) {
					sendMessage(Protocol.REGISTRATION_EXISTS_ERROR);
					closeStreams();
					return;
				}

				else {
					if(!Database.getInstance().insUser(user)) {
						sendMessage(Protocol.ERROR);
						closeStreams();
						return;
						}
				}
				userIn = user.getEmail();
			}
			
			//caso Login client
			else if(in.equals(Protocol.LOGIN)) {
				User user =(User) input.readObject();

					if(!Database.getInstance().checkLogin(user)) {
						sendMessage(Protocol.AUTHENTICATION_NO_VALID_ERROR);
						closeStreams();
						return;
					}
					userIn = user.getEmail();
				    Database.getInstance().setDataNumeroAccessi(userIn);//incremento numero accessi ad ogni login

	
			}
			
			
			else {
				sendMessage(Protocol.ERROR);
				closeStreams();
				return;
			}
			
			
			 
			
			//il client è loggato ed è ora nel server
			sendMessage(Protocol.SUCCESS);
			
			if(Database.getInstance().getNumAccessi(userIn)==1)
				sendMessagesToClient("Benvenuto! Qui potrai ricevere assistenza!");
			
			//ora gestiamo tutte le richieste
			
			
			
			while(!Thread.currentThread().isInterrupted()) {
				String requestClient = (String) input.readObject();
				
				//qui gestisce ogni tipo di richiesta
				switch (requestClient) {
				case Protocol.HELP_MESSAGES:
					receivedMessagesClient();
					break; 
				case Protocol.GET_HELP_MESSAGES:
					getHelpMessages();
					break; 
				case Protocol.INSERT_CLIENTE:
					insertClienteHandle();
					break;
				case Protocol.INSERT_FORNITORE:
					insertFornitoreHandle();
					break;
				case Protocol.GET_ALL_CLIENTI:
					getClientiHandle();
					break;
				case Protocol.CLIENTE_EDIT:
					editClientiHandle();
					break;
				case Protocol.DELETE_CLIENTE:
					deleteClientiHandle();
					break;
				case Protocol.CLIENTE_SEARCH:
					searchClienteHandle();
					break;
				case Protocol.GET_ALL_FORNITORI:
					getFornitoriHandle();
					break;
				case Protocol.FORNITORE_DELITE:
					deleteFornitoreHandle();
					break;
				case Protocol.FORNITORE_SEARCH:
					searchFornitoreHandle();
					break;
				case Protocol.FORNITORE_EDIT:
					editFornitoreHandle();
					break;
				case Protocol.INSERT_PRODOTTO:
					insertProdottoHandle();
					break;
				case Protocol.GET_ALL_PRODOTTI:
					getProdottiHandle();
					break;
				case Protocol.GET_NUM_PRODOTTI:
					getNumProdotti();
					break;
				case Protocol.GET_NUM_CLIENTI:
					getNumClienti();
					break;
				case Protocol.GET_NUM_FORNITORI:
					getNumFornitori();
					break;
				case Protocol.GET_EMAIL_USER:
					getEmailUser();
					break;	
				case Protocol.SET_PASSWORD_USER:
					setPasswordUser();
					break;	
				case Protocol.ADD_TERRITORIO:
					insertTerritorio();
					break;	
				case Protocol.GET_TERRITORI:
					getAllTerritori();
					break;
				case Protocol.EDIT_TERRITORIO:
					editTerritorio();
					break;
				case Protocol.ASSEGNA_TERRITORIO:
					assegnaTerritorio();
					break;
				case Protocol.CONSEGNA_TERRITORIO:
					consegnaTerritorio();
					break;
				case Protocol.GET_ASSEGNAMENTO_TERRITORIO:
					getInfoTerritorio();
					break;
				case Protocol.ADD_EVENTO:
					addEventHandle();
					break;
				case Protocol.GET_EVENTI:
					getEventi();
					break;
				case Protocol.DELETE_EVENTO:
					deleteEventoHandle();
					break;
				case Protocol.UPDATE_CONT:
					updateContHandle();
					break;
				case Protocol.UPDATE_PRODOTTO:
					updateProdottoHandle();
					break;
					
					
					
					
					
					default:
						sendMessage(Protocol.REQUEST_ERROR);
						break;
				}
			}
		
			
			
		} 
		
		catch (IOException e) {
			sendMessage(Protocol.ERROR);
			removeUser();
			e.printStackTrace();
			closeStreams();
			return;
		}
		catch (ClassNotFoundException e) {
			sendMessage(Protocol.SERVER_ERROR);
			removeUser();
			e.printStackTrace();
			closeStreams();
			return;
		}
		catch (SQLException e) {
			sendMessage(Protocol.SERVER_ERROR_DB);
			e.printStackTrace();
			return;
		}
	
		
		
	}
	
	
	
	private void receivedMessagesClient() throws ClassNotFoundException, IOException, SQLException{
		String message =(String) input.readObject();
		
		Database.getInstance().insertMessage(userIn, true, message);

	}
	
	private void  sendMessagesToClient(String message) throws ClassNotFoundException, IOException, SQLException{
		
		Database.getInstance().insertMessage(userIn, false, message);
	/*	sendMessage(Protocol.HELP_MESSAGES);
		sendMessage(message);
	 */
	}

	private void getHelpMessages() throws ClassNotFoundException, IOException, SQLException {
		
		ArrayList<HelpMessage> mexs=Database.getInstance().getMessages(userIn);
		sendMessage(mexs);
	
		}
	
	
	private void insertClienteHandle() throws ClassNotFoundException, IOException, SQLException {
		
		Cliente c =(Cliente) input.readObject();
	
		if(Database.getInstance().exsCliente(c, userIn)) {
			sendMessage(Protocol.CLIENTE_EXISTS);
			return;
		}
		
		if(!Database.getInstance().insCliente(c,userIn)) {
			sendMessage(Protocol.CLIENTE_ERROR);
			return;
			
		}
		Database.getInstance().setDataNumeroClienti(userIn,1);
		sendMessage(Protocol.CLIENTE_SUCCESS);
		
	 
	}
	

	private void insertFornitoreHandle() throws ClassNotFoundException, IOException, SQLException {
		Fornitore f =(Fornitore) input.readObject();
		
		if(Database.getInstance().exsFornitore(f, userIn)) {
			sendMessage(Protocol.FORNITORE_EXISTS);
			return;
		}
		if(!Database.getInstance().insFornitore(f,userIn)) {
			sendMessage(Protocol.ERROR);
			return;
		}	
		Database.getInstance().setDataNumeroFornitori(userIn,1);
		sendMessage(Protocol.FORNITORE_SUCCESS);
	 
	}

	private void insertProdottoHandle() throws ClassNotFoundException, IOException, SQLException {
		Prodotto p =(Prodotto) input.readObject();
		if(Database.getInstance().exsProdottoId(p, userIn)) {
			sendMessage("Codice prodotto già esistente!");
			return;
		}
			
		if(Database.getInstance().insProdotto(p,userIn)) {
			sendMessage(Protocol.PRODOTTO_SUCCESS);
			Database.getInstance().setDataNumeroProdotti(userIn,1);
			return;
		}	
		sendMessage(Protocol.PRODOTTO_ERROR);
	}
	
	
	private void getClientiHandle() throws SQLException {
		
		ArrayList<Cliente> cli=Database.getInstance().getClienti(userIn);
			sendMessage(cli);
			
		
		
	}

	
		
	
	private void editClientiHandle() throws ClassNotFoundException, IOException, SQLException {
		Cliente old=(Cliente)input.readObject();
		Cliente cnew=(Cliente)input.readObject();

		if(!Database.getInstance().editCliente(old, cnew, userIn)) {
			sendMessage("Non è stato possibile modificare il cliente!");
			return;
			
		}
		sendMessage(Protocol.CLIENTE_EDIT_SUCCESS);
		
	 
	}
	
	private void deleteClientiHandle() throws ClassNotFoundException, IOException, SQLException {
		Cliente c=(Cliente)input.readObject();
		
		if(!Database.getInstance().deleteCliente(c,userIn)) {
			sendMessage(Protocol.CLIENTE_DELETE_ERROR);
			return;
		}
		sendMessage(Protocol.CLIENTE_DELITE_SUCCESS);
		Database.getInstance().setDataNumeroClienti(userIn,-1);

		
	 
	}
	private void searchClienteHandle() throws SQLException, ClassNotFoundException, IOException {
		String nome=(String)input.readObject();
		String cognome=(String)input.readObject();

		ArrayList<Cliente> cli=Database.getInstance().searchCliente(userIn,nome,cognome);
			sendMessage(cli);
			
		
		
	}

	
	private void getFornitoriHandle() throws SQLException {
		ArrayList<Fornitore> fornitor=Database.getInstance().getFornitori(userIn);
			sendMessage(fornitor);
			
		
	}
	
	private void searchFornitoreHandle() throws SQLException, ClassNotFoundException, IOException {
		String nome=(String)input.readObject();
		String cognome=(String)input.readObject();

		ArrayList<Fornitore> forni=Database.getInstance().searchFornitore(userIn,nome,cognome);
			sendMessage(forni);
			
	}
			
		
	private void editFornitoreHandle() throws ClassNotFoundException, IOException, SQLException {
		Fornitore foold=(Fornitore)input.readObject();
		Fornitore fonew=(Fornitore)input.readObject();

		if(!Database.getInstance().editFornitore(foold, fonew, userIn)) {
			sendMessage("Non è stato possibile modificare il Fornitore!");
			return;
			
		}
		sendMessage(Protocol.FORNITORE_EDIT_SUCCESS);
		
	 
	}	
	
	
	private void deleteFornitoreHandle() throws ClassNotFoundException, IOException, SQLException {
		Fornitore f=(Fornitore)input.readObject();
		
		if(!Database.getInstance().deleteFornitore(f,userIn)) {
			sendMessage(Protocol.FORNITORE_DELITE_ERROR);
			return;
		}
		sendMessage(Protocol.FORNITORE_DELITE_SUCCESS);
		Database.getInstance().setDataNumeroFornitori(userIn,-1);

		
	 
	}
	
	private void getProdottiHandle() throws SQLException, IOException {
		
		ArrayList<Prodotto> prodotti=Database.getInstance().getProdotti(userIn);
		sendMessage(prodotti);
			
		
	}
	
	private void  getNumProdotti() throws SQLException, IOException {
		
		Integer numProdotti=Database.getInstance().getNumProdotti(userIn);
		sendMessage(numProdotti);
			
		
	}
	

	private void  getNumClienti() throws SQLException, IOException {
		Integer numClienti=Database.getInstance().getNumClienti(userIn);
		sendMessage(numClienti);
			
	}
	

	private void  getNumFornitori() throws SQLException, IOException {
		Integer numFornitori=Database.getInstance().getNumFornitori(userIn);
		sendMessage(numFornitori);
			
	}
	
	private void getEmailUser()throws SQLException, IOException {
		String emailUser=Database.getInstance().getEmailUser(userIn);
		sendMessage(emailUser);
			
	}
	
	
	private void insertTerritorio() throws ClassNotFoundException, IOException, SQLException {
		Territorio t =(Territorio) input.readObject();
		
		if(Database.getInstance().exsTerritorioNum(t.getNumeroTerritorio(), userIn)) {
			sendMessage("Numero Territorio già esistente!");
			return;
		}
			
		if(Database.getInstance().insertTerritorio(t,userIn)) {
			sendMessage(Protocol.SUCCESS_TERRITORIO);
			return;
		}	
		sendMessage(Protocol.ERROR);
	}
	
	
	private void getAllTerritori() throws SQLException {
		
		ArrayList<Territorio> t=Database.getInstance().getTerritori(userIn);
			sendMessage(t);
			
		
		
	}
	private void editTerritorio() throws ClassNotFoundException, IOException, SQLException {
		Territorio t=(Territorio)input.readObject();

		if(!Database.getInstance().editTerritorio( userIn,t)) {
			sendMessage(Protocol.ERROR);
			return;
			
		}
		sendMessage(Protocol.SUCCESS);
		
	 
	}
	
	private void assegnaTerritorio() throws ClassNotFoundException, IOException, SQLException {
		Territorio t=(Territorio)input.readObject();
		String nome=(String)input.readObject();
		String cognome=(String)input.readObject();
		String data=(String)input.readObject();
		
		if(!Database.getInstance().assegnaTerritorio( userIn,t,nome,cognome,data)) {
			sendMessage(Protocol.ERROR);
			return;
			
		}
		sendMessage(Protocol.SUCCESS);
		
		
	 
	}
	
	private void consegnaTerritorio() throws ClassNotFoundException, IOException, SQLException {
		Territorio ter=(Territorio)input.readObject();
		String data=(String)input.readObject();
		
		if(!Database.getInstance().consegnaTerritorio( userIn,ter,data)) {
			sendMessage(Protocol.ERROR);
			return;
			
		}
		sendMessage(Protocol.SUCCESS);
		
	 
	}
	
	private void getInfoTerritorio() throws ClassNotFoundException, IOException, SQLException {
		Territorio t=(Territorio)input.readObject();
		
		sendMessage(Database.getInstance().getInfoAssegnamento(userIn, t));
			
		
	 
	}
	
	private void addEventHandle() throws ClassNotFoundException, IOException, SQLException {
		Evento event =(Evento) input.readObject();
		
		if(Database.getInstance().existsEvent(event,userIn)) {
			sendMessage(Protocol.EXIST_EVENTO);
			return;
		}
		if(Database.getInstance().insertEvento(event,userIn)) {
			sendMessage(Protocol.SUCCESS);
			return;
		}	
		sendMessage(Protocol.PRODOTTO_ERROR);
	}
	
	
	private void getEventi() throws SQLException {
		
		ArrayList<Evento> eventi=Database.getInstance().getEventi(userIn);
			sendMessage(eventi);
		
	}

	private void deleteEventoHandle() throws ClassNotFoundException, IOException, SQLException {
		Evento e=(Evento)input.readObject();
		
		if(!Database.getInstance().deleteEvento(e,userIn)) {
			sendMessage(Protocol.ERROR);
			return;
		}
		sendMessage(Protocol.SUCCESS);
			 
	}
	
	
	
	private void updateContHandle()throws ClassNotFoundException, IOException, SQLException {
		int num=(int)input.readObject();
		boolean uscite=(boolean) input.readObject();
		if(!Database.getInstance().setCont(userIn, num, uscite)) {
			sendMessage(Protocol.ERROR);
			return;
		}
		sendMessage(Protocol.SUCCESS);
			 
	}
	
	private void updateProdottoHandle()throws ClassNotFoundException, IOException, SQLException {
		Prodotto prodotto=(Prodotto)input.readObject();
		
		if(Database.getInstance().editProdotto(userIn, prodotto)) {
			sendMessage(Protocol.SUCCESS);
			return;
		}
		sendMessage(Protocol.ERROR);			 
	}
	
	
	
	
	
	
	private void setPasswordUser()throws SQLException, IOException, ClassNotFoundException {
		String oldp=(String)input.readObject();
		String newp=(String)input.readObject();
		
		if(!Database.getInstance().checkPassword(userIn,oldp)) {
			sendMessage(Other.LOGIN_PASSWORD_NOTVALID);
			return;
		}
		
		if(Database.getInstance().editPassword(userIn, newp)) {
			sendMessage(Other.OK);
		}
	}
	
	private void removeUser() {
		if(!userIn.equals("")) {
			UsersHandler.removeUser(userIn);
		}
	}
	
	public void sendMessage(Object message) {
		if(output == null)
			return;
		
		try {
			output.writeObject(message);
			output.flush();//inviamo

		} catch (IOException e) {
			e.printStackTrace();
			removeUser();
			return;
		}
	}
	
	private void closeStreams()  {
		try {
			if (output != null)
				output.close();
			output = null;
			if (input != null)
				input.close();
			input = null;
			if (socket != null)
				socket.close();
			socket = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
