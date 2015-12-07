package gob.sct.sipmm.cntmgr;

import gob.sct.documentos.*;

public class CM_GetContent{

  public CM_GetContent(){
    
  }

  public byte[] connect(String[] keys,String[] values,String[] operators) throws Exception{
    byte[] attachment = null;
    boolean lError = false;
    try{      
      DigitalizarDocumentoServiceLocator wsSerConSL = new DigitalizarDocumentoServiceLocator();
      // Baja de 8.3
      DigitalizarDocumento wsSerCon = wsSerConSL.getDigitalizarDocumentoSoapPort(new java.net.URL("http://aplicaciones7.sct.gob.mx/Documentos/DigitalizarDocumento"));
      //DigitalizarDocumento wsSerCon = wsSerConSL.getDigitalizarDocumentoSoapPort(new java.net.URL("http://10.33.169.117:7001/Documentos/DigitalizarDocumento"));
      
      attachment = wsSerCon.obtener(
              "icmadmin", 
              "lscmv82", 
              "ADV",
              keys, values,operators);
      
      return attachment;
      
    } catch(Exception ex){      
      ex.printStackTrace();
      lError = true;
    } finally{
      if(lError == true){
        throw new Exception("Content Manager Error");
      }
    }
    return null;
  }
}