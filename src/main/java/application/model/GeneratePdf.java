package application.model;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import application.modelObject.Cliente;
import application.modelObject.Fattura;
import application.modelObject.Fornitore;
import javafx.stage.DirectoryChooser;

public class GeneratePdf {

	
	private static GeneratePdf instance=null;
	
	private GeneratePdf() {}
	
	public static GeneratePdf getInstance() {
		if(instance==null)
			instance=new GeneratePdf();
		
		return instance;
	}
	
	
	
	public int generatePdfClienti(ArrayList<Cliente> clienti) {

		
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File res=directoryChooser.showDialog(null);
		 if(res==null)
			 return 0;
		 
		 
		ArrayList<Cliente> cli=clienti;
		String s=res.getAbsolutePath();

		LocalDate dt = LocalDate.now(); 
		String st="ClientiTable"+dt.toString();
		
		
		 try {
			
					
			 Document document=new Document();
			 Font fontEdit = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD );
			 Font fontEdit2 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD );

			 PdfWriter.getInstance(document, new FileOutputStream(s+"\\"+st+".pdf"));
			
			 document.open();
			 Image img=Image.getInstance(getClass().getResource("/application/images/LogoWhite.png"));
		     img.scaleAbsolute(50f, 50f);
		     img.setAlignment(Element.ALIGN_CENTER);

			 document.add(img);
			 
			 Paragraph p=new Paragraph(st,fontEdit);
			 
			 p.setAlignment(Element.ALIGN_CENTER);
			 document.add(p);
			 document.add(new Paragraph("\n\n"));
			 
			 PdfPTable tab= new PdfPTable(4);
			 PdfPCell tabCell=null;
			 
			 
			 tab.addCell(new Paragraph("Nome",fontEdit2));
			 tab.addCell(new Paragraph("Cognome",fontEdit2));
			 tab.addCell(new Paragraph("Città",fontEdit2));
			 tab.addCell(new Paragraph("Telefono",fontEdit2));
			 
			 for(var e:cli) {
				 	tabCell=new PdfPCell(new Phrase(e.getNome()));
				 	tab.addCell(tabCell);
				 	tabCell=new PdfPCell(new Phrase(e.getCognome()));
				 	tab.addCell(tabCell);
				 	tabCell=new PdfPCell(new Phrase(e.getCitta()));
				 	tab.addCell(tabCell);
				 	tabCell=new PdfPCell(new Phrase(e.getTelefono()));
				 	tab.addCell(tabCell);
			 }
			 document.add(tab);
			 document.close();
			 return 1;
		}  catch (Exception e) {
			e.printStackTrace();
			return 2;
		}
	}
	
	
	
	public int generatePdfFornitori(ArrayList<Fornitore> fornitori) {

		
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File res=directoryChooser.showDialog(null);
		 if(res==null)
			 return 0;
		 String s=res.getAbsolutePath();

		 
		ArrayList<Fornitore> forni=fornitori;

		 LocalDate dt = LocalDate.now();
		 
		String st="FornitoriTable"+dt.toString();
		
		 try {
			
					
			 Document document=new Document();
			 Font fontEdit = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD );
			 Font fontEdit2 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD );
			 Font fontEdit3 = new Font(Font.FontFamily.TIMES_ROMAN, 8 );

			 PdfWriter.getInstance(document, new FileOutputStream(s+"\\"+st+".pdf"));
			
			 document.open();
			 Image img=Image.getInstance(getClass().getResource("/application/images/LogoWhite.png"));
		     img.scaleAbsolute(50f, 50f);
		     img.setAlignment(Element.ALIGN_CENTER);

			 document.add(img);
			 
			 Paragraph p=new Paragraph(st,fontEdit);
			 
			 p.setAlignment(Element.ALIGN_CENTER);
			 document.add(p);
			 document.add(new Paragraph("\n\n"));
			 
			 PdfPTable tab= new PdfPTable(8);
			 tab.setWidthPercentage(100);
			 tab.setWidths(new int[]{90,90,90,90,90,90,90,90});
			 tab.setHorizontalAlignment(Element.ALIGN_LEFT);
			 PdfPCell tabCell=null;
			 
			 
			 tab.addCell(new Paragraph("Nome",fontEdit2));
			 tab.addCell(new Paragraph("Cognome",fontEdit2));
			 tab.addCell(new Paragraph("Città",fontEdit2));
			 tab.addCell(new Paragraph("Cap",fontEdit2));
			 tab.addCell(new Paragraph("Via",fontEdit2));
			 tab.addCell(new Paragraph("Telefono",fontEdit2));
			 tab.addCell(new Paragraph("Ragione Sociale",fontEdit2));
			 tab.addCell(new Paragraph("Partita Iva",fontEdit2));
			 
			 for(var e:forni) {
				 	tabCell=new PdfPCell(new Phrase(e.getNome(),fontEdit3));
				 	tab.addCell(tabCell);
				 	tabCell=new PdfPCell(new Phrase(e.getCognome(),fontEdit3));
				 	tab.addCell(tabCell);
				 	tabCell=new PdfPCell(new Phrase(e.getCitta(),fontEdit3));
				 	tab.addCell(tabCell);
				 	tabCell=new PdfPCell(new Phrase(e.getCap(),fontEdit3));
				 	tab.addCell(tabCell);
				 	tabCell=new PdfPCell(new Phrase(e.getVia(),fontEdit3));
				 	tab.addCell(tabCell);
				 	tabCell=new PdfPCell(new Phrase(e.getTelefono(),fontEdit3));
				 	tab.addCell(tabCell);
				 	tabCell=new PdfPCell(new Phrase(e.getRagioneSociale(),fontEdit3));
				 	tab.addCell(tabCell);
					tabCell=new PdfPCell(new Phrase(e.getPartitaIva(),fontEdit3));
				 	tab.addCell(tabCell);
			 }
			 document.add(tab);
			 document.close();
			 return 1;
		}  catch (Exception e) {
			e.printStackTrace();
			return 2;

		}
	}
	
	
	public int generatePdfFattura(Fattura fattura) {

		DirectoryChooser directoryChooser = new DirectoryChooser();
		File res=directoryChooser.showDialog(null);
		 if(res==null)
			 return 0;
		 
		String result=res.getAbsolutePath();
		

		 try {
			
					
			 Document document=new Document();
			 Font fontEdit = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD );
			 Font fontEdit2 = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD );
			 Font fontEdit3 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD );
			 Font fontEdit4 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
			 Font fontEdit5 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL,BaseColor.RED);

			 PdfWriter.getInstance(document, new FileOutputStream(result+"\\"+"Fattura"+fattura.getDataOrdine()+"_"+fattura.getNumeroRicevuta()+".pdf"));
			
			 document.open();
			 Image img=Image.getInstance(getClass().getResource("/application/images/LogoWhite.png"));
		     img.scaleAbsolute(50f, 50f);
		     img.setAlignment(Element.ALIGN_LEFT);

			 document.add(img);
			 Paragraph title=new Paragraph("Fattura",fontEdit);
			 title.setAlignment(Element.ALIGN_CENTER);
			 document.add(title);
			 
			 Paragraph p=new Paragraph("Data | ",fontEdit3);
			 p.setFont(fontEdit4);
			 p.add(fattura.getDataOrdine());
			 
			 p.setFont(fontEdit3);
			 p.add("\n"+"Numero fattura: ");
			 p.setFont(fontEdit4);
			 p.add(fattura.getNumeroRicevuta());
			 
			 p.setFont(fontEdit3);
			 p.add("\n"+"Numero ordine: ");
			 p.setFont(fontEdit4);
			 p.add(fattura.getNumeroOrdine());
			 
			 p.setAlignment(Element.ALIGN_RIGHT);
			 document.add(p);
			 
			 LineSeparator separe=new LineSeparator();
			 Chunk line = new Chunk(separe);
			 document.add(line);

			 p.clear();
			 
			 p.setFont(fontEdit3);
			 p.add("Intestatario:"+"\n");
			 p.add("Nome: ");
			 p.setFont(fontEdit4);
			 p.add(fattura.getCliente().getNome()+" "+fattura.getCliente().getCognome());
			 
			 p.setFont(fontEdit3);
			 p.add("\n"+"Città: ");
			 p.setFont(fontEdit4);
			 p.add(fattura.getCliente().getCitta());
			 
			 p.setFont(fontEdit3);
			 p.add("\n"+"Iva: ");
			 p.setFont(fontEdit4);
			 p.add(fattura.getCliente().getIva());
			 
			 
			 p.setAlignment(Element.ALIGN_LEFT);
			 document.add(p);
			 
			 document.add(new Paragraph("\n\n"));
			 
			 
			 
			
			 p.clear();
			 p.add("Prodotti in fattura"+"\n\n");
			 p.setAlignment(Element.ALIGN_CENTER);
			 p.setFont(fontEdit4);
			 document.add(p);
			 
			 PdfPTable tab= new PdfPTable(6);
			 
			 
			 PdfPCell tabCell=null;
			 
			 
			 tab.addCell(new Paragraph("ID prodotto",fontEdit2));
			 tab.addCell(new Paragraph("Nome",fontEdit2));
			 tab.addCell(new Paragraph("Categoria",fontEdit2));
			 tab.addCell(new Paragraph("Quantità",fontEdit2));
			 tab.addCell(new Paragraph("Prezzo",fontEdit2));
			 tab.addCell(new Paragraph("Iva",fontEdit2));

			 
			 for(var e:fattura.getProdotti()) {
				 	tabCell=new PdfPCell(new Phrase(e.getId()));
				 	tab.addCell(tabCell);
				 	tabCell=new PdfPCell(new Phrase(e.getNome()));
				 	tab.addCell(tabCell);
				 	tabCell=new PdfPCell(new Phrase(e.getCategoria()));
				 	tab.addCell(tabCell);
				 	tabCell=new PdfPCell(new Phrase(Integer.toString(e.getQuantita())));
				 	tab.addCell(tabCell);
				 	tabCell=new PdfPCell(new Phrase(Integer.toString(e.getPrezzo())));
				 	tab.addCell(tabCell);
				 	tabCell=new PdfPCell(new Phrase(Integer.toString(e.getIva()	)));
				 	tab.addCell(tabCell);
			 }
			 document.add(tab);
			 
			 p.clear();
			 
			 p.setFont(fontEdit3);
			 p.add("\n\n"+"Imponibile:                             ");
			 p.setFont(fontEdit4);
			 p.add(fattura.getImponibile()+"\n");
			 
			 p.setFont(fontEdit3);
			 p.add("Iva totale:                             ");
			 p.setFont(fontEdit4);
			 p.add(Integer.toString(fattura.getTotaleIva())+"\n");
			 
			 p.setFont(fontEdit3);
			 p.add("Prezzo:                             ");
			 p.setFont(fontEdit4);
			 p.add(Integer.toString(fattura.getTotalePrezzo())+"\n");
			 
			 p.setFont(fontEdit3);
			 p.add("Sconto:                             ");
			 p.setFont(fontEdit4);
			 p.add(fattura.getSconto()+"\n");
			 
			 
			 p.setFont(fontEdit3);
			 p.add("Totale:                             ");
			 p.setFont(fontEdit5);
			 p.add(Integer.toString((fattura.getTotalePrezzo()+fattura.getTotaleIva()+
					 Integer.parseInt(fattura.getImponibile()))-
					 Integer.parseInt(fattura.getSconto()))+"\n");
			 
			 p.setFont(fontEdit3);
			 p.add("\n\n\n"+"Firma Cliente:\n\n\n\n\n");
			
			 
			 p.setAlignment(Element.ALIGN_RIGHT);
			 document.add(p);
			 
			 p.clear();
			 
			 
			 p.setFont(fontEdit3);
			 p.add("Scadenza pagamento | ");
			 p.setFont(fontEdit4);
			 p.add(fattura.getDataScadenzaPagamento()+"\n");
			 
			 p.setFont(fontEdit3);
			 p.add("Totale importo | ");
			 p.setFont(fontEdit4);
			 p.add(Integer.toString((fattura.getTotalePrezzo()+fattura.getTotaleIva()+
					 Integer.parseInt(fattura.getImponibile()))-
					 Integer.parseInt(fattura.getSconto()))+"\n");
			 
			 p.add("Note:"+"\n\n\n\n\n");
			 p.setAlignment(Element.ALIGN_LEFT);
			 document.add(p);
			 
			 
			 document.add(line);

			 document.close();
			 return 1;
		}  catch (Exception e) {
			e.printStackTrace();
			return 2;
		}
		
		
	}
	
	
	
	
	
}
