	package application.net.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import application.SceneHandler;
import application.modelObject.Cliente;
import application.modelObject.Evento;
import application.modelObject.Fornitore;
import application.modelObject.HelpMessage;
import application.modelObject.Prodotto;
import application.modelObject.Territorio;
import application.net.stuff.Other;
import application.net.stuff.Protocol;
import application.net.stuff.User;

public class Client   {

	private static Client instance=null;
	
	private ObjectOutputStream out=null;
	private ObjectInputStream in =null;
	
	private Socket socket=null;
	private Check c;
	
	private Client() {
		 c=new Check();
		try {
			socket=new Socket("localhost",9527);
			out = new ObjectOutputStream(socket.getOutputStream());

		}  catch (IOException e) {
			out=null;
			e.printStackTrace();
		}
		
	}
	
	public static Client getInstance() {
		if(instance == null)
			instance = new Client();
		
		return instance;
		
	}
	
	public String checkLog(String email,String password) {
		sendMessage(Protocol.LOGIN);
		sendMessage(new User(email,password));
		
		try {
			if(in==null)
				in= new ObjectInputStream(socket.getInputStream());
			String result = (String) in.readObject();
			return result;
		
		} catch (Exception e) {
			out=null;
			e.printStackTrace();
			return Protocol.ERROR;
		}
		
		
	}
	
	
	public String getEmail() {
		sendMessage(Protocol.GET_EMAIL_USER);
		try {
			String result = (String) in.readObject();
			return result;
		
		} catch (Exception e) {
			out=null;
			e.printStackTrace();
			return Protocol.ERROR;
		}	
		
	}
	
	public String setPasswordUser(String oldPass1,String newpass2,String newPass) {
		if(!newpass2.equals(newPass))
			return "Le nuove  password inserite non corrispondono";
		if(!c.checkPassword(newPass).equals(Other.OK))
			return c.checkPassword(newPass);
		
		sendMessage(Protocol.SET_PASSWORD_USER);
		sendMessage(oldPass1);
		sendMessage(newPass);

		try {
			String result = (String) in.readObject();
			return result;
		
		} catch (Exception e) {
			out=null;
			e.printStackTrace();
			return Protocol.ERROR;
		}	
		
	}
	
	
	
	
	public String checkRegistration(String email,String password) {
		String s=c.checkPassword(password);
		try {
			if(s!=Other.OK)
				return s;
		
			s=c.checkEmail(email);
			if(s!=Other.OK)
				return s;
			
			sendMessage(Protocol.REGISTRATION);
			sendMessage(new User(email,password));
		
		
			if(in==null)
				in= new ObjectInputStream(socket.getInputStream());

			String result = (String) in.readObject();
			return result;

		} catch (Exception e) {
			out=null;
			e.printStackTrace();
			return Protocol.ERROR;
		}
		
		
	}
	
	
	
	public String addCliente( String nome, String cognome, String citta, String telefono) {
		if(c.checkAddCliente(nome, cognome, citta, telefono).equals(Other.OK)) {
		sendMessage(Protocol.INSERT_CLIENTE);
		sendMessage(new Cliente( nome, cognome, citta, telefono));
		
		try {
			String res = (String) in.readObject();
			return res;
		
		} catch (Exception e) {
			out=null;
			e.printStackTrace();
			return "Errore del server";
		}
		}
		else
			return c.checkAddCliente(nome, cognome, citta, telefono);
		
	}
	
	
	
	
	public String addFornitore(String cap, String telefono, String partitaIva, String nome, String cognome, String via,
			String citta, String ragioneSociale) {
		String checkNewFornitore=c.checkAddFornitore(cap, telefono, partitaIva, nome,cognome,via,citta,ragioneSociale);
		if(checkNewFornitore.equals(Other.OK)) {

		sendMessage(Protocol.INSERT_FORNITORE);
		sendMessage(new Fornitore( cap,  telefono,  partitaIva,  nome,  cognome,  via,
				 citta,  ragioneSociale));
		
		try {
			String result = (String) in.readObject();
			return result;
		
		} catch (Exception e) {
			out=null;
			e.printStackTrace();
			return Protocol.ERROR;
		}	
		}
		else
			return checkNewFornitore;
	}
	
	public String editFornitore(Fornitore fold,Fornitore fnew) {
		String checkFornitore=c.checkAddFornitore(fnew.getCap(), fnew.getTelefono(), fnew.getPartitaIva(), fnew.getNome(), 
				fnew.getCognome(),fnew.getVia(),fnew.getCitta(), fnew.getRagioneSociale());
				
		if(!checkFornitore.equals(Other.OK)) 
			return checkFornitore;

		sendMessage(Protocol.FORNITORE_EDIT);
		sendMessage(fold);
		sendMessage(fnew);

		
		try {
			String result = (String) in.readObject();
			return result;
		
		} catch (Exception e) {
			out=null;
			e.printStackTrace();
			return Protocol.ERROR;
		}	
		
	}
	
	
	
	public String addProdotto(String id, String nome, String cat, String prezzo, 
			String data, String numd, String nomeF, String cognomeF, byte [] file) {
		
		if(!c.checkAddProdotto(id,nome,cat,prezzo,data,numd).equals(Other.OK)) 
			return c.checkAddProdotto(id,nome,cat,prezzo,data,numd);

		
		sendMessage(Protocol.INSERT_PRODOTTO);
		sendMessage(new Prodotto(id, nome, cat, Integer.parseInt(prezzo),data,Integer.parseInt(numd),nomeF,cognomeF,file));
		//updateCont(Integer.parseInt(prezzo)*Integer.parseInt(numd), true);
		try {
			String result = (String) in.readObject();
			return result;
		
		} catch (Exception e) {
			out=null;
			e.printStackTrace();
			return Protocol.ERROR;
		}	
		
			
	}
	
	
	
	public ArrayList<Cliente> getClienti(){
		sendMessage(Protocol.GET_ALL_CLIENTI);
		try {
			@SuppressWarnings("unchecked")
			ArrayList<Cliente> cl=(ArrayList<Cliente>) in.readObject();
			return cl;
		} catch (Exception e) {
			SceneHandler.getInstance().showInfo("Errore dal server") ;
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	public ArrayList<Cliente> searchClienti(String n,String co){
		sendMessage(Protocol.CLIENTE_SEARCH);
		sendMessage(n);
		sendMessage(co);

		
		try {
			@SuppressWarnings("unchecked")
			ArrayList<Cliente> cl=(ArrayList<Cliente>) in.readObject();
			return cl;
		} catch (Exception e) {
			SceneHandler.getInstance().showInfo("Errore dal server") ;
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	public String editCliente(Cliente cold,Cliente cnew) {
		String checkNew=c.checkAddCliente(cnew.getNome(), cnew.getCognome(), cnew.getCitta(), cnew.getTelefono());
		if(!checkNew.equals(Other.OK)) 
			return checkNew;

		sendMessage(Protocol.CLIENTE_EDIT);
		sendMessage(cold);
		sendMessage(cnew);

		
		try {
			String result = (String) in.readObject();
			return result;
		
		} catch (Exception e) {
			out=null;
			e.printStackTrace();
			return Protocol.ERROR;
		}	
		
	}
	

	public String deleteCliente(Cliente c) {
		sendMessage(Protocol.DELETE_CLIENTE);
		sendMessage(c);

		try {
			String result = (String) in.readObject();
			return result;
		
		} catch (Exception e) {
			out=null;
			e.printStackTrace();
			return Protocol.ERROR;
		}	
		
	}
	
	public ArrayList<Fornitore> getFornitori(){
		sendMessage(Protocol.GET_ALL_FORNITORI);
		try {
			@SuppressWarnings("unchecked")
			ArrayList<Fornitore> forni=(ArrayList<Fornitore>) in.readObject();
			return forni;
		} catch (Exception e) {
			SceneHandler.getInstance().showInfo("Errore dal server") ;
			e.printStackTrace();
		}
		return null;
	}
	
	
	public ArrayList<Fornitore> searchFornitori(String nome,String co){
		sendMessage(Protocol.FORNITORE_SEARCH);
		sendMessage(nome);
		sendMessage(co);

		
		try {
			@SuppressWarnings("unchecked")
			ArrayList<Fornitore> forni=(ArrayList<Fornitore>) in.readObject();
			return forni;
		} catch (Exception e) {
			SceneHandler.getInstance().showInfo("Errore dal server") ;
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String deleteFornitore(Fornitore f) {
		sendMessage(Protocol.FORNITORE_DELITE);
		sendMessage(f);

		try {
			String result = (String) in.readObject();
			return result;
		
		} catch (Exception e) {
			out=null;
			e.printStackTrace();
			return Protocol.ERROR;
		}	
		
	}
	
	
	public ArrayList<Prodotto> getProdotti(){
		sendMessage(Protocol.GET_ALL_PRODOTTI);
		try {
			@SuppressWarnings("unchecked")
			ArrayList<Prodotto> pro=(ArrayList<Prodotto>) in.readObject();
			return pro;
		} catch (Exception e) {
			SceneHandler.getInstance().showInfo("Errore dal server") ;
			e.printStackTrace();
			return null;
		}
	
	}
	
	public String updateCont(int num,boolean uscite) {
		sendMessage(Protocol.UPDATE_CONT);
		sendMessage(num);
		sendMessage(uscite);

		try {
			String result = (String) in.readObject();
			return result;
		
		} catch (Exception e) {
			out=null;
			e.printStackTrace();
			return Protocol.ERROR;
		}	
		
	}
	
	public String updateProdotto(Prodotto prodotto) {
		sendMessage(Protocol.UPDATE_PRODOTTO);
		sendMessage(prodotto);

		try {
			String result = (String) in.readObject();
			return result;
		
		} catch (Exception e) {
			out=null;
			e.printStackTrace();
			return Protocol.ERROR;
		}	
		
	}
	
	
	public Integer getNumProdotti(){
		sendMessage(Protocol.GET_NUM_PRODOTTI);
		try {
			Integer num= (Integer) in.readObject();
			return num;
		} catch (Exception e) {
			SceneHandler.getInstance().showInfo(Protocol.ERROR) ;
			e.printStackTrace();
			return (Integer) null;
		}
	
	}

	public Integer getNumClienti(){
		sendMessage(Protocol.GET_NUM_CLIENTI);
		try {
			Integer num= (Integer) in.readObject();
			return num;
		} catch (Exception e) {
			SceneHandler.getInstance().showInfo(Protocol.ERROR) ;
			e.printStackTrace();
			return (Integer) null;
		}
	
	}
	
	public Integer getNumFornitori(){
		sendMessage(Protocol.GET_NUM_FORNITORI);
		try {
			Integer num= (Integer) in.readObject();
			return num;
		} catch (Exception e) {
			SceneHandler.getInstance().showInfo(Protocol.ERROR) ;
			e.printStackTrace();
			return (Integer) null;
		}
	
	}
	
	
	
	
	
	public String addTerritorio( String nome, String numero, String nota) {
		if(!c.checkNum(numero))
			return "Numero non valido!";
		
		int num=Integer.parseInt(numero);
		
		sendMessage(Protocol.ADD_TERRITORIO);
		Territorio t=new Territorio(nome, num);
		t.setNota(nota);
		t.setAssegnato("Non assegnato");
		
		sendMessage(t);
		
		try {
			String res = (String) in.readObject();
			return res;
		
		} catch (Exception e) {
			out=null;
			e.printStackTrace();
			return "Errore del server";
		}
		
		
	}
		
	public ArrayList<Territorio> getTerritori(){
		sendMessage(Protocol.GET_TERRITORI);
		try {
			@SuppressWarnings("unchecked")
			ArrayList<Territorio> terri=(ArrayList<Territorio>) in.readObject();
			return terri;
			
		} catch (Exception e) {
			SceneHandler.getInstance().showInfo("Errore dal server") ;
			e.printStackTrace();
			return null;
		}
	
	}
	
	public String editTerritorio(Territorio t) {
		sendMessage(Protocol.EDIT_TERRITORIO);
		sendMessage(t);

		
		try {
			String result = (String) in.readObject();
			return result;
		
		} catch (Exception e) {
			out=null;
			e.printStackTrace();
			return Protocol.ERROR;
		}	
		
	}
	
	public String assegnaTerritorio(Territorio t,String nome,String cognome,String data) {
		sendMessage(Protocol.ASSEGNA_TERRITORIO);
		sendMessage(t);
		sendMessage(nome);
		sendMessage(cognome);
		sendMessage(data);
		
		try {
			String result = (String) in.readObject();
			return result;
		
		} catch (Exception e) {
			out=null;
			e.printStackTrace();
			return Protocol.ERROR;
		}	
		
	}
	
	public String consegnaTerritorio(Territorio t,String data) {
		sendMessage(Protocol.CONSEGNA_TERRITORIO);
		sendMessage(t);
		sendMessage(data);

		try {
			String result = (String) in.readObject();
			return result;
		
		} catch (Exception e) {
			out=null;
			e.printStackTrace();
			return Protocol.ERROR;
		}	
		
	}
	
	public String getInfoTerritorio(Territorio t) {
		sendMessage(Protocol.GET_ASSEGNAMENTO_TERRITORIO);
		sendMessage(t);

		try {
			String result = (String) in.readObject();
			return result;
		
		} catch (Exception e) {
			out=null;
			e.printStackTrace();
			return Protocol.ERROR;
		}	
		
	}
	
	
	public String insEvent(Evento evento) {
		
		sendMessage(Protocol.ADD_EVENTO);
		sendMessage(evento);
		
		try {
			String result = (String) in.readObject();
			return result;
		
		} catch (Exception e) {
			out=null;
			e.printStackTrace();
			return Protocol.ERROR;
		}	
		
			
	}
	
	public String deleteEvent(Evento eve) {
		sendMessage(Protocol.DELETE_EVENTO);
		sendMessage(eve);

		try {
			String result = (String) in.readObject();
			return result;
		
		} catch (Exception e) {
			out=null;
			e.printStackTrace();
			return Protocol.ERROR;
		}	
		
	}
	
	
	public ArrayList<Evento> getEventi(){
		
		sendMessage(Protocol.GET_EVENTI);
		try {
			@SuppressWarnings("unchecked")
			ArrayList<Evento> eventi=(ArrayList<Evento>) in.readObject();
			return eventi;
			
		} catch (Exception e) {
			SceneHandler.getInstance().showInfo(Protocol.ERROR) ;
			e.printStackTrace();
			return null;
		}
	
	}
	
	
	
	
	//manda oggetti 
	private boolean sendMessage(Object mex) {
		if(out == null)
				return false;
		try {
			out.writeObject(mex);
			out.flush(); //flusso  ci permette di inviare l'oggetto

		} catch (IOException e) {
			e.printStackTrace();
			out =null;
			return false;
		}
		return true;
		
	}
	
	
	public boolean sendHelpMessage(String mess) {
		sendMessage(Protocol.HELP_MESSAGES);
		return sendMessage(mess);
	}

	public ArrayList<HelpMessage> getMessages(){
		sendMessage(Protocol.GET_HELP_MESSAGES);
		try {
			@SuppressWarnings("unchecked")
			ArrayList<HelpMessage> mexs=(ArrayList<HelpMessage>) in.readObject();
			return mexs;
		} catch (Exception e) {
			SceneHandler.getInstance().showInfo(Protocol.ERROR) ;
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	public void reset() {
		instance=null;
		out=null;
		in=null;
		socket=null;
		
	}

}
