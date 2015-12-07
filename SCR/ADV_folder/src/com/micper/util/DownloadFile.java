package com.micper.util;

import gob.sct.documentos.DigitalizarDocumento;
import gob.sct.documentos.DigitalizarDocumentoServiceLocator;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DownloadFile extends HttpServlet{
    
  public void init(ServletConfig config) throws ServletException{
    super.init(config);
  }

  protected void processRequest(HttpServletRequest request,
                                HttpServletResponse response) throws ServletException,IOException{
      
      String[] keys = {"userid", "password", "entity", "maxResults", "queryOP","lintiCveDocumen"};
       String[] operators = {"","","","","","="};
      
      byte[] btArchivo = null;
      boolean lSuccess = true;
      
      try{
      
          String[] values = {"USR","PWD","ITEM-TYPE","1","true","IDENTIFICADOR DEL DOCUMENTO A RECUPERAR"};
          
         DigitalizarDocumentoServiceLocator wsSerConSL = new DigitalizarDocumentoServiceLocator();
          DigitalizarDocumento wsSerCon = wsSerConSL.getDigitalizarDocumentoSoapPort(new java.net.URL("URL DEL WS DEL CONTENT MANAGER"));
         
         btArchivo = wsSerCon.obtener(
               "USR", 
               "PWD", 
              "ITEM-TYPE",
              keys, values,operators);;
//      btArchivo = wsSerCon.obtener("icmadmin","lscmv82","ADV",keys, values,operators);
       

         
      } catch(Exception e){
         lSuccess = false;
      } finally{
         if(lSuccess){
            response.setContentType("application/octet-stream");
        OutputStream browser = response.getOutputStream();
       browser.write(btArchivo);
     browser.flush();
            browser.close();                           
         }else{
            response.getWriter().println("Error al descargar el archivo");
          }      
      
      }
   }
   
   
   protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
       processRequest(request,response);
    }
   
 }