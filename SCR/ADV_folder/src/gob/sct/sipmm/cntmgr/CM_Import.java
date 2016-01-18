package gob.sct.sipmm.cntmgr;

import java.io.*;
import java.util.*;

import com.micper.ingsw.TParametro;

import gob.sct.documentos.*;

public class CM_Import {

  public CM_Import() {
  }

  public String connect(String[] keys, String[] values, byte[] bytes) throws Exception {

    boolean lError = false;
    String cReturn = "";
    TParametro VParametros = new TParametro("44");
    try {
        
        DigitalizarDocumentoServiceLocator wsSerConSL = new DigitalizarDocumentoServiceLocator();
//        DigitalizarDocumento wsSerCon = wsSerConSL.getDigitalizarDocumentoSoapPort(new java.net.URL("http://aplicaciones7.sct.gob.mx/Documentos/DigitalizarDocumento"));
        VParametros.getPropEspecifica("ConDBModulo");
        DigitalizarDocumento wsSerCon = wsSerConSL.getDigitalizarDocumentoSoapPort(new java.net.URL(VParametros.getPropEspecifica("URL_WS_ContentM"))); 
        cReturn = wsSerCon.asignar(
                "icmadmin", 
                "lscmv82", 
                "ADV",
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
