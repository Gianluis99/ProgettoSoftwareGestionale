package application.net.client;

import java.util.regex.Pattern;

import application.net.stuff.Other;
import application.net.stuff.Protocol;

public class Check {
	
	private static boolean macthes(String regex,String p) {
		boolean res=Pattern.matches(regex, p);
		return res;
	}
	
	 Check() {}
	
	 String checkPassword(String p){
		
		if(!macthes("((?=.*)[a-z]*)(?=.*[A-Z]*).{6,30}", p))
			return Other.REGISTRATION_PASSWORD_NOTVALID3;
		
		
	
		 return Other.OK;
			
		
	}
	
	 String checkEmail(String e){
		
		if(!macthes("([a-z0-9])+([a-z0-9])*@([a-z])+\\.([a-z]+)" ,e))
			return Protocol.REGISTRATION_EMAIL_ERROR;
			
		 return Other.OK;
		
	}
	 
	 String checkAddCliente(String nome, String cognome, String citta, String telefono) {
		 if(nome.equals(""))
			 return  Other.ADD_CLIENTE_NOVALID1;
		 if(cognome.equals(""))
			 return  Other.ADD_CLIENTE_NOVALID2;
		
		 if(citta.equals(""))
			 return  Other.ADD_CLIENTE_NOVALID3;
		
		 if(!macthes("[0-9]+",telefono ))
			 return  Other.ADD_CLIENTE_NOVALID4;
		 
		 return Other.OK;
		 
	 }
	 
	 String checkAddFornitore(String cap, String telefono, String partitaIva, String nome, String cognome, String via,
				String citta, String ragioneSociale ) {
		 if(nome.equals(""))
			 return  Other.ADD_FORNITORE_NOVALID1;
		 if(cognome.equals(""))
			 return  Other.ADD_FORNITORE_NOVALID2;
		
		 if(citta.equals(""))
			 return  Other.ADD_FORNITORE_NOVALID3;
		
		 if(!macthes("[0-9]+",cap ))
			 return  Other.ADD_FORNITORE_NOVALID4;
		
		 if(via.equals(""))
			 return  Other.ADD_FORNITORE_NOVALID5;
		
		 if(!macthes("[0-9]+",partitaIva ))
			 return  Other.ADD_FORNITORE_NOVALID6;
		
		 if(ragioneSociale.equals(""))
			 return  Other.ADD_FORNITORE_NOVALID8;
		
		 if(!macthes("[0-9]+",telefono ))
			 return  Other.ADD_FORNITORE_NOVALID7;
		 
		 return Other.OK;
		 
	 }

	String  checkAddProdotto(String id, String nome, String cat, String prezzo, String data, String numd) {
		if(id.equals(""))
			return "Codice prodotto non valido!";
		
		if(nome.equals(""))
			return "Nome prodotto non valido!";
		
		if(cat.equals(""))
			return "Categoria prodotto non valido!";
		
		if(!macthes("[0-9]+",prezzo))
			return "Prezzo prodotto non valido!";
		if(data.equals(""))
			return "Data prodotto non valido!";
		
		if(!macthes("[0-9]+",numd))
			return "Quantità prodotto non valido!";
		
		
		return Other.OK;
	}
	
	boolean checkNum(String n) {
		
			return macthes("[0-9]+",n);
	}
	

}
