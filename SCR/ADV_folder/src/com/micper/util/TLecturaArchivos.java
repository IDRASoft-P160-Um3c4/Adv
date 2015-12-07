package com.micper.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.Part;
import com.oreilly.servlet.multipart.ParamPart;

import com.micper.seguridad.vo.TVDinRep;

/**
 * Esta clase se encarga de hacer la lectura de archivos de texto y regresar una Lista o Vector con las cadenas resultantes.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author LSC Rafael Miranda Blumenkron
 * @version 1.0
 */
public class TLecturaArchivos{
  TVDinRep vDatosRequest = new TVDinRep();
  public TLecturaArchivos(){
  }

  /**
   * Este m�todo se encarga de obtener un OutputStream desde el request.
   * @param httpReq HttpServletRequest
   * @return ByteArrayOutputStream
   */
  public ByteArrayOutputStream getFileFromRequest(HttpServletRequest httpReq){
    MultipartParser multiParser = null;
    Part part = null;
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    try{
      multiParser = new MultipartParser(httpReq,Integer.MAX_VALUE);
      while( (part = multiParser.readNextPart()) != null){
        if(part.isFile()){
          FilePart filePart = (FilePart) part;
          filePart.writeTo(outputStream);
          outputStream.close();
        }
        if(part.isParam()){
          ParamPart paramPart = (ParamPart) part;
          vDatosRequest.put(part.getName(),paramPart.getStringValue());
        }
      }
    } catch(Exception e){}
    return outputStream;
  }

  /**
   * este m�todo se encarga de obtener un arreglo de bytes desde un output stream
   * @param outputStream ByteArrayOutputStream
   * @return byte[]
   */
  public byte[] getByteArrayFromOutputStream(ByteArrayOutputStream outputStream){
    return outputStream.toByteArray();
  }

  /**
   * Este m�todo se encarga de obtener un arreglo de bytes desde el request.
   * @param httpReq HttpServletRequest
   * @return byte[]
   */
  public byte[] getByteArrayFromRequest(HttpServletRequest httpReq){
    return this.getFileFromRequest(httpReq).toByteArray();
  }

  /**
   * Este m�todo se encarga de obtener un InputStream desde un arreglo de bytes.
   * @param fileData byte[]
   * @return InputStream
   */
  public InputStream getInputStreamFromByteArray(byte[] fileData){
    return new ByteArrayInputStream(fileData);
  }

  /**
   * Este m�todo se encarga de obtener un InputStream desde el request.
   * @param httpReq HttpServletRequest
   * @return InputStream
   */
  public InputStream getInputStreamFromRequest(HttpServletRequest httpReq){
    return new ByteArrayInputStream(this.getByteArrayFromRequest(httpReq));
  }

  /**
   * Este m�todo regresa una Lista de las l�neas leidas de un input stream
   * @param inputStream InputStream
   * @return List
   */
public List getListFromInputStream(InputStream inputStream){
    List lineasArchivo = null;
    InputStreamReader streamReader = new InputStreamReader(inputStream);
    BufferedReader bufferedReader = new BufferedReader(streamReader);
    try{
      lineasArchivo = new ArrayList();
      String cLinea = bufferedReader.readLine();
      while(cLinea != null){
        lineasArchivo.add(cLinea);
        cLinea = bufferedReader.readLine();
      }
    } catch(IOException ioException){
    }
    return lineasArchivo;
  }

  /**
   * Este m�todo regresa una lista de las l�neas le�das desde el archivo indicado
   * @param inputFile File
   * @return List
   */
  public List getListFromFile(File inputFile){
    List listaRegresa = null;
    try{
      listaRegresa = this.getListFromInputStream(new FileInputStream(inputFile));
    }catch(IOException ioExc){}
    return listaRegresa;
  }

  /**
   * Este m�todo regresa una lista de las l�neas le�das desde el archivo del request
   * @param httpReq HttpServletRequest
   * @return List
   */
  public List getListFromRequestFile(HttpServletRequest httpReq){
    return this.getListFromInputStream(this.getInputStreamFromRequest(httpReq));
  }

  /**
   * Este m�todo convierte genera un vector con las l�neas obtenidas de la lista proporcionada
   * @param lineasArchivo List
   * @return Vector
   */
public Vector getVectorFromList(List lineasArchivo){
    Vector vLineasArchivo = new Vector();
    for(int i = 0;i < lineasArchivo.size();i++)
      vLineasArchivo.addElement(lineasArchivo.get(i));
    return vLineasArchivo;
  }

  /**
   * Este m�todo regresa un vector con las l�neas obtenidas del archivo proporcionado
   * @param inputFile File
   * @return Vector
   */
  public Vector getVectorFromFile(File inputFile){
    return this.getVectorFromList(this.getListFromFile(inputFile));
  }

  /**
   * Este m�todo regresa un vector con las l�neas obtenidas del archivo del request
   * @param httpReq HttpServletRequest
   * @return Vector
   */
  public Vector getVectorFromRequestFile(HttpServletRequest httpReq){
    return this.getVectorFromList(this.getListFromRequestFile(httpReq));
  }

  /**
   * Este m�todo regresa los elementos de una forma y sus valores se llena en el m�todo ByteArrayOutputStream
   * @return TVDinRep
   */
  public TVDinRep getVDatosRequest(){
    return vDatosRequest;
  }

  /**
   * M�todo principal para prueba de lectura de archivos, despliega linea a linea el archivo enviado por parametro
   * @param args String[]
   */
  public static void main(String[] args){
    TLecturaArchivos procesaArchivo = new TLecturaArchivos();
    try{
      Vector vDatosArchivo = procesaArchivo.getVectorFromFile(new File(args[0]));
//      for(int i=0; i<vDatosArchivo.size(); i++)
//        //System.out.print("Renglon " + i + " ==> " + vDatosArchivo.get(i));
    } catch(Exception e){
    }
  }
}
