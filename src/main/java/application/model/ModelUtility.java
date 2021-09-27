package application.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class ModelUtility {

	private static ModelUtility instance=null;
	
	 	
	private ModelUtility() {
	}
	
	public static ModelUtility getInstance() {
		if(instance==null)
			instance=new ModelUtility();
		
		return instance;
	}
	
	public void initTimeAndDate(Label data) {

	    Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
	    	//DateTime sia data che tempo
	        DateTimeFormatter d = DateTimeFormatter.ofPattern("dd-MM-yyyy     HH:mm:ss");
	        data.setText(LocalDateTime.now().format(d));//date e tempo formattati
	    }), new KeyFrame(Duration.seconds(1))); //aggiornamento ogni secondo
	    
	    
	    clock.setCycleCount(Animation.INDEFINITE);//settiamo animazione indefinita
	    clock.play();
	}
	
	public String currentDateToString() {
		 SimpleDateFormat sDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date date = new Date();  
		    return(sDate.format(date));  
	}
	 
	public boolean isCurrentDate(String dat) {
		 SimpleDateFormat sDate = new SimpleDateFormat("dd/MM/yyyy");  
		    Date date = new Date();  
		    return  sDate.format(date)==dat;
	}
	 
	public LocalDate getCurrentDate() {
		
		 LocalDate dt = LocalDate.now();
		 return  dt;
		    
	}
	 
	
	public static byte[] fileToByte(File file) {
		if(file == null)
			return null;
		
		ByteArrayOutputStream out = null;//ByteArrayOutputStream ci permette di ottenere un array di byte
		
        byte[] b = new byte[(int) file.length()];//b prende la len del file
 
        try {
            FileInputStream fileStream = new FileInputStream(file);
            out = new ByteArrayOutputStream();
            
            int i;
            while ( (i = fileStream.read(b)) != -1) { 
            	out.write(b, 0, i);		//leggiamo dal file su byteOutput
            }
            
            fileStream.close(); //stream close
        } catch (Exception e) {
            return null;
        } 
        
        return  out.toByteArray();
	}
	 
	public boolean isNumber(String num) {
		return Pattern.matches("[0-9]+", num);
	}
}
