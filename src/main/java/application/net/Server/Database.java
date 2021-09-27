package application.net.Server;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.security.crypto.bcrypt.BCrypt;

import application.model.ModelUtility;
import application.modelObject.Cliente;
import application.modelObject.Evento;
import application.modelObject.Fornitore;
import application.modelObject.HelpMessage;
import application.modelObject.Prodotto;
import application.modelObject.Territorio;
import application.net.stuff.User;

public class Database {
	
	private static Database db=null;
	private Connection connection=null;
	
	private Database() {
		try {
			connection=DriverManager.getConnection("jdbc:sqlite:Database.db");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Database getInstance() {
		if(db ==null)
			db=new Database();
		
		return db;
	}
	
	//inseriamo utente
	public synchronized boolean insUser(User u) throws SQLException {
		
	
			if(connection==null ||connection.isClosed() ||u == null) {
					return false;
			}
			
			
			//dopo aver fatto le verifiche inseriamo u	
			PreparedStatement p= connection.prepareStatement("INSERT INTO Users VALUES(?,?);");
			p.setString(1, u.getEmail());
			p.setString(2, BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(13)));
			p.executeUpdate();
			p.close();
			initilizeData(u.getEmail());
			return true;
		
		
		
	}
	
	
	//verifichiamo se utente esiste
	public synchronized boolean exsUser(User u) throws SQLException {
		if(connection==null ||connection.isClosed() ||u == null)
			return false;
		
		String query="SELECT * FROM Users WHERE email=?;";
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, u.getEmail());
		ResultSet result=p.executeQuery();
		
		//next restituisce true se abbiamo un risultato
		boolean esiste=result.next();
		p.close();
		
		return esiste;
		
		
	}
	
	//verifichiamo se le credenziali sono corrette
	public synchronized boolean checkLogin(User u) throws SQLException {
		if(connection==null ||connection.isClosed() ||u == null)
			return false;
		
		String query="SELECT * FROM Users WHERE email=?;";
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, u.getEmail());
		ResultSet result=p.executeQuery();

		boolean ok=false;
		
		if(result.next()) {
			String password=result.getString("password");
			ok=BCrypt.checkpw(u.getPassword(), password);
			//ok = true se password uguali
		}
		p.close();
		return ok; //ritorna true se le password corrispondono
	}
	
	
	public synchronized boolean initilizeData(String idCliente) throws SQLException {
	
			if(connection==null ||connection.isClosed() ) {
					return false;
			}
			PreparedStatement p= connection.prepareStatement("INSERT INTO ContaDati(emailAzienda,numeroAccessi,numeroClienti,numeroFornitori,numeroProdotti) VALUES(?,?,?,?,?);");
			p.setString(1, idCliente);
			p.setInt(2,1);
			p.setInt(3,0);
			p.setInt(4,0);
			p.setInt(5,0);
			
			p.executeUpdate();
			p.close();
			return true;
		
		

	}
	
	public synchronized boolean insertMessage(String idCliente,boolean sender,String message) throws SQLException {
		
		if(connection==null ||connection.isClosed() ) {
				return false;
		}
		
		PreparedStatement p;
		String date=ModelUtility.getInstance().currentDateToString();
		
		
		p= connection.prepareStatement("INSERT INTO HelpMessages(emailAzienda,message,date,sender) VALUES(?,?,?,?);");
		p.setString(1, idCliente);
		p.setString(2,message);
		p.setString(3,date);
		p.setInt(4,sender ? 1:0);
		
		
		
		p.executeUpdate();
		p.close();
		return true;
	
	

}
	
	
	
	public synchronized ArrayList<HelpMessage> getMessages(String idAzienda) throws SQLException{
		String query="SELECT * FROM HelpMessages WHERE emailAzienda=?;";
		ArrayList<HelpMessage> messages=new ArrayList<HelpMessage>();
		
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, idAzienda);
		
		ResultSet result=p.executeQuery();
		while(result.next()) {
			boolean sender;
			if(result.getInt("sender")==0)
				sender=false;
			else
				sender=true;
				
			HelpMessage mex=new HelpMessage(result.getString("message"),sender,
			result.getString("date"));
			messages.add(mex);
			
		}
		result.close();
		p.close();
		return messages;
		
	}
	
	
	public synchronized boolean setDataNumeroAccessi(String idCliente) throws SQLException {
		
		
			if(connection==null ||connection.isClosed() ) {
					return false;
			} 
			PreparedStatement p= connection.prepareStatement("UPDATE ContaDati SET numeroAccessi=numeroAccessi + ? WHERE emailAzienda=? ;");
			p.setInt(1, 1);
			p.setString(2, idCliente);
			
			p.executeUpdate();
			p.close();
			return true;
		
		

	}
	
	public synchronized boolean setDataNumeroClienti(String idCliente,int num) throws SQLException {
		
	
			if(connection==null ||connection.isClosed() ) {
					return false;
			} 
			PreparedStatement p= connection.prepareStatement("UPDATE ContaDati SET numeroClienti=numeroClienti + ? WHERE emailAzienda=? ;");
			p.setInt(1, num);
			p.setString(2, idCliente);
			
			p.executeUpdate();
			p.close();
			return true;
	
		

	}
	
	
	public synchronized boolean setDataNumeroFornitori(String idCliente,int num) throws SQLException {
		
	
			if(connection==null ||connection.isClosed() ) {
					return false;
			} 
			PreparedStatement p= connection.prepareStatement("UPDATE ContaDati SET numeroFornitori=numeroFornitori + ? WHERE emailAzienda=? ;");
			p.setInt(1, num);
			p.setString(2, idCliente);
			
			p.executeUpdate();
			p.close();
			return true;
		
		

	}
	
	public synchronized boolean setDataNumeroProdotti(String idCliente,int num) throws SQLException {
		
		
			if(connection==null ||connection.isClosed() ) {
					return false;
			} 
			PreparedStatement p= connection.prepareStatement("UPDATE ContaDati SET numeroProdotti=numeroProdotti +? WHERE emailAzienda=? ;");
			p.setInt(1, num);
			p.setString(2, idCliente);
			
			p.executeUpdate();
			p.close();
			return true;
	
		

	}
	
	
	
	
	public synchronized boolean insCliente(Cliente  c,String idCliente) throws SQLException {

			if(connection==null ||connection.isClosed() ||c == null) {
					return false;
			}
			//dopo aver fatto le verifiche inseriamo u	
			PreparedStatement p= connection.prepareStatement("INSERT INTO Clienti(emailAzienda,nome,cognome,citta,telefono) VALUES(?,?,?,?,?);");
			p.setString(1, idCliente);
			p.setString(2,c.getNome());
			p.setString(3,c.getCognome());
			p.setString(4,c.getCitta());
			p.setString(5,c.getTelefono());
			
			p.executeUpdate();
			p.close();
			return true;
		
		

	}
	
	public synchronized boolean exsCliente(Cliente cliente,String idAzienda) throws SQLException {
		if(connection==null ||connection.isClosed() ||cliente == null)
			return false;
		
		String query="SELECT * FROM Clienti WHERE emailAzienda=? AND nome=?  AND cognome=?  AND telefono=?;";
		PreparedStatement pre=connection.prepareStatement(query);
		pre.setString(1, idAzienda);
		pre.setString(2, cliente.getNome());
		pre.setString(3, cliente.getCognome());
		pre.setString(4, cliente.getTelefono());

		ResultSet result=pre.executeQuery();
	
		
		boolean esiste=result.next();
		pre.close();
	
		return esiste;
	
	
	}
	

	public synchronized boolean insFornitore(Fornitore  f,String idAzienda) throws SQLException {
		
		
			if(connection==null ||connection.isClosed() ||f == null) {
					return false;
			}
			
			//dopo aver fatto le verifiche inseriamo u	
			PreparedStatement p= connection.prepareStatement("INSERT INTO Fornitori(emailAzienda,nome,cognome,via,cap,citta,telefono,ragioneSociale,partitaIva) VALUES(?,?,?,?,?,?,?,?,?);");
			p.setString(1, idAzienda);
			p.setString(2,f.getNome());
			p.setString(3,f.getCognome());
			p.setString(4,f.getVia());
			p.setString(5,f.getCap());
			p.setString(6,f.getCitta());
			p.setString(7,f.getTelefono());
			p.setString(8,f.getRagioneSociale());
			p.setString(9,f.getPartitaIva());
			
			p.executeUpdate();
			p.close();
			return true;
		
		
		
	}
	
	public synchronized boolean exsFornitore(Fornitore fornitore,String idAzienda) throws SQLException {
		if(connection==null ||connection.isClosed() ||fornitore == null)
			return false;
		
		String query="SELECT * FROM Fornitori WHERE emailAzienda=? AND nome=?  AND cognome=?  AND telefono=?;";
		PreparedStatement pre=connection.prepareStatement(query);
		pre.setString(1, idAzienda);
		pre.setString(2, fornitore.getNome());
		pre.setString(3, fornitore.getCognome());
		pre.setString(4, fornitore.getTelefono());

		ResultSet result=pre.executeQuery();
	
		
		boolean esiste=result.next();
		pre.close();
	
		return esiste;
	
	
	}
	
	
	
	public synchronized boolean insProdotto(Prodotto  p,String idAzienda) throws SQLException {
		
			
				
			PreparedStatement prep= connection.prepareStatement("INSERT INTO Prodotti(emailAzienda,id,nome,categoria,image,prezzo,numDisponibili,"
					+ "data,nomeFornitore,cognomeFornitore) VALUES(?,?,?,?,?,?,?,?,?,?);");
			prep.setString(1, idAzienda);
			prep.setString(2, p.getId());
			prep.setString(3, p.getNome());
			prep.setString(4, p.getCategoria());
		    prep.setBytes(5, p.getImgProdotto());// IMAGE
			prep.setInt(6, p.getPrezzo());
			prep.setInt(7, p.getNumDisponibili());
			prep.setString(8, p.getData());
			prep.setString(9, p.getNomeFornitore	());
			prep.setString(10, p.getCognomeFornitore());

			prep.executeUpdate();
			prep.close();
			return true;
			
		
	}
	
	
	public synchronized ArrayList<Prodotto> getProdotti(String idAzienda) throws SQLException, IOException{
		String query="SELECT * FROM Prodotti WHERE emailAzienda=?;";
		ArrayList<Prodotto> prodotti=new ArrayList<Prodotto>();
		
	  
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, idAzienda);
		
		ResultSet result=p.executeQuery();
		  
	       

		while(result.next()) {


			Prodotto prodotto=new Prodotto(result.getString("id"),result.getString("nome"),
			result.getString("categoria"),result.getInt("prezzo"),result.getString("data"),
			result.getInt("numDisponibili"),result.getString("nomeFornitore"),result.getString("cognomeFornitore"),
			result.getBytes("image"));
			
			prodotti.add(prodotto);
			
		}
		result.close();
		p.close();
		return prodotti;
		
	}
	

	
	
	

	public synchronized boolean exsProdottoId(Prodotto p,String idAzienda) throws SQLException {
		if(connection==null ||connection.isClosed() ||p == null)
			return false;
		
		String query="SELECT * FROM Prodotti WHERE emailAzienda=? AND id=?;";
		PreparedStatement pre=connection.prepareStatement(query);
		pre.setString(1, idAzienda);
		pre.setString(2, p.getId());
		ResultSet result=pre.executeQuery();
	
		
		boolean esiste=result.next();
		pre.close();
	
		return esiste;
	
	
	}
	
	//aggiornamento  prodotti
	public synchronized boolean editProdotto(String emailAzienda,Prodotto prodotto) throws SQLException {
		
	
		String query="SELECT numDisponibili FROM Prodotti WHERE emailAzienda=? AND id=?;";
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, emailAzienda);
		p.setString(2, prodotto.getId());
		ResultSet result=p.executeQuery();
		
		int num = 0;
		if(result.next()) {
			num=result.getInt("numDisponibili");
		}
		
		num-=prodotto.getQuantita();
		
		//ritorna true se viene eliminato o aggiornato 
		if(num==0) {
			deleteProdotto(prodotto, emailAzienda);
			p.close();
			result.close();
			return true;
		}
		
		editNumDispProdotto(emailAzienda, prodotto, num);
			p.close();
			result.close();
			return true;
		

	}
	
	
	public synchronized boolean editNumDispProdotto(String emailAzienda,Prodotto prodotto,int num) throws SQLException {
	
		String query="UPDATE Prodotti SET numDisponibili = ? WHERE emailAzienda = ? AND id = ?;";
		PreparedStatement p=connection.prepareStatement(query);
		p.setInt(1,num);
		p.setString(2, emailAzienda);
		p.setString(2, prodotto.getId());
		
		p.executeUpdate();
		p.close();
		
		return true;
	}
	
	//aggiornamento contabilita
	public synchronized boolean setCont(String emailAzienda,int num,boolean uscite) throws SQLException {
		
		if(connection==null ||connection.isClosed() ) {
				return false;
		} 
		PreparedStatement p;
		if(uscite) {
			
				 p= connection.prepareStatement("UPDATE  Contabilita SET uscite = uscite +? WHERE emailAzienda=? ;");
				 p.setInt(1, num);
				 p.setString(2, emailAzienda);
			
		}
		else {
			
			 	p= connection.prepareStatement("UPDATE Contabilita SET entrate = entrate +? WHERE emailAzienda=? ;");
			 	p.setInt(1, num);
			 	p.setString(2, emailAzienda);
			 
			
		}
		p.executeUpdate();
		p.close();
		return true;
	

}


	
	public synchronized boolean deleteProdotto(Prodotto prodotto,String emailAzienda) throws SQLException {
		String query="DELETE  FROM Prodotti WHERE emailAzienda=? AND id=? ;";
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, emailAzienda);
		p.setString(2, prodotto.getId());
		
		int result=0;
		result=p.executeUpdate();
		p.close();
		
		return result==1;
		

	}
	
	
	
	
	public synchronized ArrayList<Cliente> getClienti(String idAzienda) throws SQLException{
		String query="SELECT * FROM Clienti WHERE emailAzienda=?;";
		ArrayList<Cliente> clienti=new ArrayList<Cliente>();
		
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, idAzienda);
		
		ResultSet result=p.executeQuery();
		
		while(result.next()) {
			Cliente c=new Cliente(result.getString("nome"),result.getString("cognome"),
			result.getString("citta"),result.getString("telefono"));
			clienti.add(c);
			
		}
		result.close();
		p.close();
		return clienti;
		
	}
	
	
	
	
	public synchronized boolean deleteCliente(Cliente cli,String emailAzienda) throws SQLException {
		String query="DELETE  FROM Clienti WHERE emailAzienda=? AND nome=? AND cognome=? AND citta=? AND telefono=? ;";
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, emailAzienda);
		p.setString(2, cli.getNome());
		p.setString(3, cli.getCognome());
		p.setString(4, cli.getCitta());
		p.setString(5, cli.getTelefono());
		int result=0;
		result=p.executeUpdate();
		p.close();
		
		return result==1;
		

	}
	
	public synchronized boolean editCliente(Cliente cliold,Cliente clinew,String emailAzienda) throws SQLException {
		String query="UPDATE  Clienti SET   nome=?,cognome=?,citta=?,telefono=? WHERE emailAzienda=? "
				+ "AND nome=? AND cognome=? AND citta=? AND telefono=? ;";
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, clinew.getNome());
		p.setString(2, clinew.getCognome());
		p.setString(3, clinew.getCitta());
		p.setString(4, clinew.getTelefono());
		p.setString(5, emailAzienda);
		p.setString(6, cliold.getNome());
		p.setString(7, cliold.getCognome());
		p.setString(8, cliold.getCitta());
		p.setString(9, cliold.getTelefono());

		int result=0;
		result=p.executeUpdate();
		p.close();
		
		return result==1;
		

	}
	
	public synchronized ArrayList<Fornitore> getFornitori(String idAzienda) throws SQLException{
		String query="SELECT * FROM Fornitori WHERE emailAzienda=?;";
		ArrayList<Fornitore> fornitori=new ArrayList<Fornitore>();
		
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, idAzienda);
		
		ResultSet result=p.executeQuery();
		
		while(result.next()) {
			Fornitore f=new Fornitore(result.getString("cap"),result.getString("telefono"),result.getString("partitaIva"),result.getString("nome"),
					result.getString("cognome"),result.getString("via"),result.getString("citta"),result.getString("ragioneSociale"));
			fornitori.add(f);
			
		}
		result.close();
		p.close();
		return fornitori;
		
	}
	
	
	public synchronized ArrayList<Cliente> searchCliente(String idAzienda,String nome,String cognome) throws SQLException{
		String query="SELECT * FROM Clienti WHERE emailAzienda=? AND Nome=? AND Cognome=?;";
		ArrayList<Cliente> clienti=new ArrayList<Cliente>();
		
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, idAzienda);
		p.setString(2, nome);
		p.setString(3, cognome);
		
		ResultSet result=p.executeQuery();
		
		while(result.next()) {
			Cliente c=new Cliente(result.getString("nome"),result.getString("cognome"),
			result.getString("citta"),result.getString("telefono"));
			clienti.add(c);
			
		}
		result.close();
		p.close();
		return clienti;
		
	}
	
	
	

	public synchronized boolean deleteFornitore(Fornitore fo,String emailAzienda) throws SQLException {
		String query="DELETE  FROM Fornitori WHERE emailAzienda=? AND nome=? AND cognome=? AND citta=? AND telefono=? AND via=? AND cap=? AND ragioneSociale=? AND partitaIva=?;";
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, emailAzienda);
		p.setString(2, fo.getNome());
		p.setString(3, fo.getCognome());
		p.setString(4, fo.getCitta());
		p.setString(5, fo.getTelefono());
		p.setString(6, fo.getVia());
		p.setString(7, fo.getCap());
		p.setString(8, fo.getRagioneSociale());
		p.setString(9, fo.getPartitaIva());

		int result=0;
		result=p.executeUpdate();
		p.close();
		
		return result==1;
		

	}
	
	
	
	public synchronized boolean editFornitore(Fornitore foold,Fornitore fonew,String emailAzienda) throws SQLException {
		String query="UPDATE  Fornitori SET   nome=?,cognome=?,citta=?,telefono=?,via=?,cap=?,ragioneSociale=?,partitaIva=? WHERE emailAzienda=? "
				+" AND nome=? AND cognome=? AND citta=? AND telefono=? AND via=? AND cap=? AND ragioneSociale=? AND partitaIva=? ;";
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, fonew.getNome());
		p.setString(2, fonew.getCognome());
		p.setString(3, fonew.getCitta());
		p.setString(4, fonew.getTelefono());
		p.setString(5, fonew.getVia());
		p.setString(6, fonew.getCap());
		p.setString(7, fonew.getRagioneSociale());
		p.setString(8, fonew.getPartitaIva());
		p.setString(9, emailAzienda);
		p.setString(10, foold.getNome());
		p.setString(11, foold.getCognome());
		p.setString(12, foold.getCitta());
		p.setString(13, foold.getTelefono());
		p.setString(14, foold.getVia());
		p.setString(15, foold.getCap());
		p.setString(16, foold.getRagioneSociale());
		p.setString(17, foold.getPartitaIva());

		int result=0;
		result=p.executeUpdate();
		p.close();
		
		return result==1;
		

	}
	
	public synchronized ArrayList<Fornitore> searchFornitore(String idAzienda,String nome,String cognome) throws SQLException{
		String query="SELECT * FROM Fornitori WHERE emailAzienda=? AND Nome=? AND Cognome=?;";
		ArrayList<Fornitore> fornitori=new ArrayList<Fornitore>();
		
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, idAzienda);
		p.setString(2, nome);
		p.setString(3, cognome);
		
		ResultSet result=p.executeQuery();
		

		while(result.next()) {
			Fornitore f=new Fornitore(result.getString("cap"),result.getString("telefono"),result.getString("partitaIva"),result.getString("nome"),
			 result.getString("cognome"),result.getString("via"),result.getString("citta"),result.getString("ragioneSociale"));
			fornitori.add(f);
			
		}
		result.close();
		p.close();
		return fornitori;
		
	}
	
	
	public synchronized Integer getNumProdotti(String idAzienda) throws SQLException{
		String query="SELECT numeroProdotti FROM ContaDati WHERE emailAzienda=?;";
		
		
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, idAzienda);
		
		ResultSet result=p.executeQuery();
		

		if(	result.next()) {
			Integer num=result.getInt("numeroProdotti");
			return num;
		}
		
		result.close();
		p.close();
		return -1;
		
	}
	
	
	
	public synchronized int getNumClienti(String idAzienda) throws SQLException{
		String query="SELECT * FROM ContaDati WHERE emailAzienda=?;";
		
		
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, idAzienda);
		
		ResultSet result=p.executeQuery();
		
		if(	result.next()) {
			int num=result.getInt("numeroClienti");
			return num;
		}
		result.close();
		p.close();
		return -1;
		
		
	}
	
	public synchronized int getNumFornitori(String idAzienda) throws SQLException{
		String query="SELECT * FROM ContaDati WHERE emailAzienda=?;";  //1 query
		
		
		PreparedStatement p=connection.prepareStatement(query); //2 preparedStatement con connessione al DB
		p.setString(1, idAzienda);
		
		ResultSet result=p.executeQuery(); //3 risultato con executeQuery
		
		if(	result.next()) {
			int num=result.getInt("numeroFornitori"); //4 prendiamo il risultato con result get
			return num;
		}
		result.close();		//5 chiudiamo  result e preparedStatement
		p.close();
		return -1;
		
		
	}

	public synchronized int getNumAccessi(String idAzienda) throws SQLException{
		String query="SELECT numeroAccessi FROM ContaDati WHERE emailAzienda=?;";  
		
		
		PreparedStatement p=connection.prepareStatement(query); 
		p.setString(1, idAzienda);
		
		ResultSet result=p.executeQuery(); 
		
		if(	result.next()) {
			int num=result.getInt("numeroAccessi");
			return num;
		}
		result.close();		
		p.close();
		return -1;
		
		
	}
	
	public synchronized String getEmailUser(String idAzienda) throws SQLException{
		String query="SELECT email FROM Users WHERE email=?;";
		
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, idAzienda);
		
		ResultSet result=p.executeQuery();
		
		String email=null;
		if(result.next()) 
			email=result.getString("email");
		
		
		result.close();
		p.close();
		return email;
		
		
	}
	
	
	
	public synchronized boolean insertTerritorio(Territorio  t,String idAzienda) throws SQLException {
		PreparedStatement prep= connection.prepareStatement("INSERT INTO territori(emailAzienda,nomeTerritorio,numeroTerritorio,"
				+ "notaTerritorio,assegnato) VALUES(?,?,?,?,?);");
		
		prep.setString(1, idAzienda);
		prep.setString(2, t.getNome());
		prep.setInt(3, t.getNumeroTerritorio());
		prep.setString(4, t.getNota());
		prep.setString(5, t.getAssegnato());
	   
		prep.executeUpdate();
		
		prep= connection.prepareStatement("INSERT INTO territoriAssegnamenti(emailAzienda,nomeTerritorio,numeroTerritorio,assegnato) VALUES(?,?,?,?);");
		prep.setString(1, idAzienda);
		prep.setString(2, t.getNome());
		prep.setInt(3, t.getNumeroTerritorio());
		prep.setString(4, t.getAssegnato());

		prep.executeUpdate();

		prep.close();
		return true;
	
	}


	public  synchronized boolean exsTerritorioNum(int  t,String idAzienda) throws SQLException {
	
		String query="SELECT * FROM territori WHERE emailAzienda=? AND numeroTerritorio=?;";
		PreparedStatement pre=connection.prepareStatement(query);
		pre.setString(1, idAzienda);
		pre.setInt(2, t);
		ResultSet result=pre.executeQuery();
	
		
		boolean esiste=result.next();
		pre.close();
	
		return esiste;
	
	
	}
	public synchronized boolean editTerritorio(String emailAzienda,Territorio t) throws SQLException {
		String query="UPDATE territori SET nomeTerritorio=?,notaTerritorio=? WHERE emailAzienda=? AND numeroTerritorio=?;";
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, t.getNome());
		p.setString(2, t.getNota());
		p.setString(3, emailAzienda);
		p.setInt(4, t.getNumeroTerritorio());

		int result=0;
		result=p.executeUpdate();
		p.close();
		
		return result==1;
		

	}
	
	public synchronized ArrayList<Territorio> getTerritori(String idAzienda) throws SQLException{
		String query="SELECT * FROM Territori WHERE emailAzienda=?;";
		ArrayList<Territorio> terri=new ArrayList<Territorio>();
		
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, idAzienda);
		
		ResultSet result=p.executeQuery();
		
		while(result.next()) {
			Territorio t=new Territorio(result.getString("nomeTerritorio"),result.getInt("numeroTerritorio"));
			terri.add(t);
			t.setAssegnato(result.getString("assegnato"));
			t.setNota(result.getString("notaTerritorio"));
			
		}
		result.close();
		p.close();
		return terri;
		
	}
	
	public synchronized boolean assegnaTerritorio(String emailAzienda,Territorio t,String nome,String cognome,String date) throws SQLException {
		String query="UPDATE territoriAssegnamenti SET nomeDipendente=?,dataAssegnamento=?,assegnato=? WHERE emailAzienda=? AND numeroTerritorio=?;";
		PreparedStatement p=connection.prepareStatement(query);
		
		p.setString(1, nome+" "+cognome);
		p.setString(2, date);
		p.setString(3, t.getAssegnato());
		p.setString(4, emailAzienda);
		p.setInt(5, t.getNumeroTerritorio());
		p.executeUpdate();
		
		p=connection.prepareStatement("UPDATE territori SET assegnato=? WHERE emailAzienda=? AND numeroTerritorio=?;");
		
		p.setString(1, t.getAssegnato());
		p.setString(2, emailAzienda);
		p.setInt(3, t.getNumeroTerritorio());
		
		int result=0;
		result=p.executeUpdate();
		p.close();
		
		return result==1;
		

	}
	
	

	
	public synchronized boolean insertEvento(Evento e,String idCliente) throws SQLException {

			if(connection==null ||connection.isClosed() ||e == null) {
					return false;
			}
			//dopo aver fatto le verifiche inseriamo u	
			PreparedStatement p= connection.prepareStatement("INSERT INTO Eventi(emailAzienda,titolo,descrizione,luogo,data) VALUES(?,?,?,?,?);");
			p.setString(1, idCliente);
			p.setString(2,e.getTitolo());
			p.setString(3,e.getDescrizione());
			p.setString(4,e.getLuogo());
			p.setString(5,e.getData());
			
			p.executeUpdate();
			p.close();
			return true;
		
		

	}
	
	public synchronized boolean existsEvent(Evento evento,String idAzienda) throws SQLException {
		if(connection==null ||connection.isClosed() ||evento == null)
			return false;
		
		String query="SELECT * FROM Eventi WHERE emailAzienda=? AND titolo=?  AND data=?;";
		PreparedStatement pre=connection.prepareStatement(query);
		pre.setString(1, idAzienda);
		pre.setString(2, evento.getTitolo());
		pre.setString(3, evento.getData());

		ResultSet result=pre.executeQuery();
	
		
		boolean esiste=result.next();
		pre.close();
	
		return esiste;
	
	
	}
	
	public synchronized ArrayList<Evento> getEventi(String idAzienda) throws SQLException{
		String query="SELECT * FROM Eventi WHERE emailAzienda=?;";
		ArrayList<Evento> eventi=new ArrayList<Evento>();
		
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, idAzienda);
		
		ResultSet result=p.executeQuery();
		
		while(result.next()) {
			Evento evento=new Evento(result.getString("titolo"),result.getString("luogo"),result.getString("descrizione"),
					result.getString("data"));
			eventi.add(evento);
			
		}
		result.close();
		p.close();
		return eventi;
		
	}
	
	public synchronized boolean deleteEvento(Evento evento,String emailAzienda) throws SQLException {
		String query="DELETE FROM Eventi WHERE emailAzienda=? AND titolo=? AND descrizione=? AND luogo=? AND data=? ;";
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, emailAzienda);
		p.setString(2, evento.getTitolo());
		p.setString(3, evento.getDescrizione());
		p.setString(4, evento.getLuogo());
		p.setString(5, evento.getData());
		int result=0;
		result=p.executeUpdate();
		p.close();
		
		return result==1;
		

	}
	
	public synchronized String getInfoAssegnamento(String emailAzienda,Territorio t) throws SQLException {
		String query="SELECT nomeDipendente FROM territoriAssegnamenti WHERE emailAzienda=? AND numeroTerritorio=?;";
		PreparedStatement prep=connection.prepareStatement(query);
		prep.setString(1, emailAzienda);
		prep.setInt(2, t.getNumeroTerritorio());
		
		ResultSet res=prep.executeQuery();
		String nome=null;
		
		if(res.next()) {
			nome=res.getString("nomeDipendente");
		}
		res.close();
		prep.close();
		return nome;
	}
	
	
	public synchronized boolean consegnaTerritorio(String emailAzienda,Territorio t,String date) throws SQLException {
		String query="UPDATE territoriAssegnamenti SET nomeDipendente=?,dataConsegna=?,assegnato=? WHERE emailAzienda=? AND numeroTerritorio=?;";
		PreparedStatement p=connection.prepareStatement(query);
		
		p.setString(1, null);
		p.setString(2, date);
		p.setString(3, "Non assegnato");	
		p.setString(4, emailAzienda);
		p.setInt(5, t.getNumeroTerritorio());
		p.executeUpdate();
		
		p=connection.prepareStatement("UPDATE territori SET assegnato=? WHERE emailAzienda=? AND numeroTerritorio=?;");

		p.setString(1, "Non assegnato");
		p.setString(2, emailAzienda);
		p.setInt(3, t.getNumeroTerritorio());
		
		
		int result=0;
		result=p.executeUpdate();
		p.close();
		
		return result==1;
		

	}
	
	
	
	
	
	public synchronized boolean editPassword(String emailAzienda,String newPass) throws SQLException {
		String query="UPDATE Users SET password=? WHERE email=?;";
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, BCrypt.hashpw(newPass, BCrypt.gensalt(13)));
		p.setString(2, emailAzienda);

		int result=0;
		result=p.executeUpdate();
		p.close();
		
		return result==1;
		

	}
	
	public synchronized boolean checkPassword(String emailAzienda,String oldPass ) throws SQLException {
		String query="SELECT password FROM Users WHERE email=?;";
		PreparedStatement p=connection.prepareStatement(query);
		p.setString(1, emailAzienda);
		ResultSet result=p.executeQuery();

		boolean res=false;
		
		if(result.next()) {
			String password=result.getString("password");
			res=BCrypt.checkpw(oldPass, password);
		}
		p.close();
		return res;
	}
	
	
	
	
	
	
	
	
}
