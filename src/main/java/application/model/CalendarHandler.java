package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

import application.SceneHandler;
import application.modelObject.Evento;
import application.net.client.Client;
import application.net.stuff.Protocol;

public class CalendarHandler {
	 //se evento passato viene eliminato automaticamente
	

	public CalendarHandler() {}

    public boolean isOldDate(String dateStrINg) {
    	LocalDate dateNow=LocalDate.now();
    	LocalDate dateIn=LocalDate.parse(dateStrINg);
    	
    	return dateIn.compareTo(dateNow)<0;
    		
    }
    
    //elimina le date vecchie dal DB
	  public void deleteOldDate() {
		  ArrayList<Evento> eventi=Client.getInstance().getEventi();
	    	
	    	if(eventi==null)
	    	{
	    		SceneHandler.getInstance().errorInfoSound(Protocol.ERROR);
	    		return;
	    	}
	    	for(var e:eventi) {
	    		if(isOldDate(e.getData()))
	    			Client.getInstance().deleteEvent(e);
	    			
	    	}
	    }
	  
	  //ritorna la differenza tra giorno corrente e giorno inserito
	  public int differenceOfDay(String dateStrINg) {
	    	LocalDate dateNow=LocalDate.now();
	    	LocalDate dateIn=LocalDate.parse(dateStrINg);
	    	
	    	return dateIn.compareTo(dateNow);
	    		
	    }
	  public ArrayList<Evento> getEvents(){
		  return Client.getInstance().getEventi();
	  }
}
