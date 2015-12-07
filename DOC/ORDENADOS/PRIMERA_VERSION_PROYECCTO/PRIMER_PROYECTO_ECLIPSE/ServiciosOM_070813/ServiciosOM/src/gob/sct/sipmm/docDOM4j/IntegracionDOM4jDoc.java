package gob.sct.sipmm.docDOM4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import org.dom4j.Document;
import org.xml.sax.SAXException;

public class IntegracionDOM4jDoc {
	
	public boolean generaDocSustText(String plantilla, String rutaPlantilla, String nomArchivoFinal, 
			   String rutaDepArchivoFinal, HashMap parametros) throws Exception{
		boolean res = false;
		
		String archivoOriginal = plantilla;
		String rutaArchivoOriginal = rutaPlantilla;
		ControllerDOM controller;
		String archivoFinal = nomArchivoFinal;
		String rutaArchivoFinal = rutaDepArchivoFinal;
		int count = 0;
		Document document;
		String archivoAplicar = "";
		String rutaArchivoAplicar = "";
		if(!parametros.isEmpty()){			
			  Iterator iterator = parametros.keySet().iterator();
				try {
				   while (iterator.hasNext()) {
					  
					 if(count == 0){
						 archivoAplicar = archivoOriginal;
						 rutaArchivoAplicar = rutaArchivoOriginal;
					 }
						 					 
					 if(count != 0){
						 archivoAplicar = archivoFinal;
						 rutaArchivoAplicar = rutaArchivoFinal;
					 }
						 
					 
					  controller = new ControllerDOM();	
					  document = controller.cargaXML(archivoAplicar, rutaArchivoAplicar);
					  
				      String key = (String) iterator.next();		 			 
				      res = controller.remplazaTextoDocumento(document, key, (String)parametros.get(key), archivoFinal, rutaArchivoFinal);
				      
				      count++;
				   }		  
				}			
				catch (FileNotFoundException e) {			
						e.printStackTrace();
				}						
			} 		  		    								
		return res;
	}						
}
