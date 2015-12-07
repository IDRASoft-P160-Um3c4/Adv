package gob.sct.sipmm.docDOM4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.xml.sax.SAXException;

public class ControllerDOM {
	
	private Document document = null;
	
	public static final String inicioWord = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";	
	public static final String PRIMERA_LINEA_ARCHIVO = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"yes\"?>";
	public static final String APLICACION_WORD = "<?mso-application progid=\"Word.Document\"?>";
	public static final String INICIO_BODY = "<w:body>";
	public static final String FIN_BODY = "</w:body>";
	public static final String INICIO_ARCHIVO_WORD = "<w:wordDocument ";
	public static final String FIN_ARCHIVO_WORD = "</w:wordDocument>";
	public static final String ATRIBUTO_1 = "xmlns:aml=\"http://schemas.microsoft.com/aml/2001/core\"";
	public static final String ATRIBUTO_2 = " xmlns:dt=\"uuid:C2F41010-65B3-11d1-A29F-00AA00C14882\" xmlns:ve=\"http://schemas.openxmlformats.org/markup-compatibility/2006\"";
	public static final String ATRIBUTO_3 = " xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\"";
	public static final String ATRIBUTO_4 = " xmlns:w10=\"urn:schemas-microsoft-com:office:word\" xmlns:w=\"http://schemas.microsoft.com/office/word/2003/wordml\"";
	public static final String ATRIBUTO_5 = " xmlns:wx=\"http://schemas.microsoft.com/office/word/2003/auxHint\"";
	public static final String ATRIBUTO_6 = " xmlns:wsp=\"http://schemas.microsoft.com/office/word/2003/wordml/sp2\"";
	public static final String ATRIBUTO_7 = " xmlns:sl=\"http://schemas.microsoft.com/schemaLibrary/2003/core\"";
	public static final String ATRIBUTO_8 = " w:macrosPresent=\"no\" w:embeddedObjPresent=\"no\" w:ocxPresent=\"no\"";
	public static final String ATRIBUTO_9 = " xml:space=\"preserve\">";
	
	private static final String finWord = "</w:wordDocument>";
	
	public String valorXML = ""; 
	
	public static ControllerDOM getInstance() {
		return new ControllerDOM();
	}	
	
	public Document cargaXML(String nomArchivo, String rutaArchivoXML) {

		File infile = new File (rutaArchivoXML + nomArchivo);
		SAXReader saxReader = new SAXReader();
                        
		try {
			Document documento = saxReader.read(infile);
			document = documento;
			return documento;
		}
		catch (DocumentException e) {
			e.printStackTrace();

		}								
		return null;
	}
	
	public boolean remplazaTextoDocumento(Document document, String cadenaBusqueda, String textoReemplazar, String archivoFinal, String archivoRutaFinal) throws SAXException, IOException {
		boolean res=false;
				
		if (textoReemplazar == null) {
			textoReemplazar = "";
			res = false;
			return res;
		}
		
	    FileWriter fichero = null;
        PrintWriter pw = null;
                
		Element root = document.getRootElement();						
		try
        {			
            fichero = new FileWriter(archivoRutaFinal+archivoFinal,false);
            pw = new PrintWriter(fichero); 
            
            pw.println(PRIMERA_LINEA_ARCHIVO);
            pw.println(APLICACION_WORD);
            pw.println(INICIO_ARCHIVO_WORD);
            
            pw.println(ATRIBUTO_1);
            pw.println(ATRIBUTO_2);
            pw.println(ATRIBUTO_3);
            pw.println(ATRIBUTO_4);
            pw.println(ATRIBUTO_5);
            pw.println(ATRIBUTO_6);
            pw.println(ATRIBUTO_7);
            pw.println(ATRIBUTO_8);
            pw.println(ATRIBUTO_9);            
            
    		for(Iterator i = root.elements().iterator(); i.hasNext();){
    		   Element elem = (Element)i.next(); 
    		    			
    		  if(!(elem.asXML().contains("<o:SmartTagType"))){    				    		
    				 valorXML = elem.asXML();
    				 valorXML = valorXML.replace("?",  "\'\'" );
    				 valorXML = valorXML.replace(cadenaBusqueda, textoReemplazar);
    				 pw.println(valorXML);    					    				
    		  }    			     			     		
    		}
    		
    		pw.println(finWord);
        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        } finally {
           try {
        	   
           if (null != fichero)
              fichero.close();               
              res = true;           
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
        
		return res;
	}	
}
