package gob.sct.sipmm.cntmgr;

import java.io.*;
import java.util.*;
import gob.sct.documentos.*;

public class CM_Import {

  public CM_Import() {
  }

  public String connect(String[] keys, String[] values, byte[] bytes) throws Exception {

    boolean lError = false;
    String cReturn = "";
    try {
        
        DigitalizarDocumentoServiceLocator wsSerConSL = new DigitalizarDocumentoServiceLocator();
        DigitalizarDocumento wsSerCon = wsSerConSL.getDigitalizarDocumentoSoapPort(new java.net.URL("http://aplicaciones7.sct.gob.mx/Documentos/DigitalizarDocumento"));
        
        cReturn = wsSerCon.asignar(
                "icmadmin", 
                "lscmv82", 
                "CGPMMDoc",
                keys, values, bytes);
    }catch (Exception ex) {
      ex.printStackTrace();
      lError = true;
    }
    finally {
      if(lError == true){
        throw new Exception("Content Manager Error");
      }
      return cReturn;
    }
  }
}
