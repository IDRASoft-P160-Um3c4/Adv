package com.micper.util;

import java.lang.reflect.*;
import java.util.StringTokenizer;

/**
 * Esta clase se encarga de ejecutar din�micamente un m�todo de una clase proporcionados como cadena con los par�metros proporcionados.
 * <p>Title: Ejecuci�n din�mica de clases</p>
 * <p>Description: ejecuci�n din�mica de clases con valores proporcionados.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author LSC Rafael Miranda Blumenkron
 * @version 1.0
 */
public class runMethodInClass{

  /**
   * M�todo encargado de hacer la ejecuci�n del m�todo y clase proporiconados.
   * Ejemplo de llamado: new runMethodFromClass().runMethodFromClass("com.micper.util.clase2", "metodo1", new Object[]);
   * @param cClase String Nombre de la clase a ejecutar con ruta (com.micper.util.TFechas)
   * @param cMetodo String Nombre del m�todo que desea ejecutar.
   * @param cParams String Cadena de par�metros a pasar al m�todo indicado.
   * @throws Exception
   * @return Object Resultado arrojado por la ejecuci�n del m�todo, null si es void.
   */
  public static Object runMethodInClass(String cClase,String cMetodo, Object[] oParams) throws Exception{
    Class toRun = null;
    Method theMethod = null;
    String cMensaje = "";
    Object theAnswer = null;
    try{
      toRun = Thread.currentThread().getContextClassLoader().loadClass(cClase);
    } catch(ClassNotFoundException classEx){
      cMensaje += classEx.getException().getMessage();
    }
    if(toRun != null){
      theMethod = findMethod(toRun,cMetodo);
      if(theMethod != null){
        try{
          theAnswer = theMethod.invoke(toRun.newInstance(),oParams);
        } catch(InvocationTargetException invEx){
          invEx.printStackTrace();
          cMensaje += invEx.getTargetException().getMessage();
        } catch(Exception generalEx){
          generalEx.printStackTrace();
          cMensaje += generalEx.getMessage();
        }
      }
      else
        cMensaje = "No se proporcion� el M�todo a ejecutar";
    }
    else
      cMensaje = "No se pudo llamar la clase a ejecutar de forma din�mica";
    if (!cMensaje.equals(""))
      throw new Exception(cMensaje);
    else
      return theAnswer;
  }

  /**
   * M�todo encargado de hacer la ejecuci�n del m�todo y clase proporiconados.
   * Ejemplo de llamado: new runMethodFromClass().runMethodFromClass("com.micper.util.clase2", "metodo1", "cPar1^Cadena�iPar2^10�dPar3^1234567.89�dtPar4^12/12/2005�");
   * @param cClase String Nombre de la clase a ejecutar con ruta (com.micper.util.TFechas)
   * @param cMetodo String Nombre del m�todo que desea ejecutar.
   * @param cParams String Cadena de par�metros a pasar al m�todo indicado.
   * @throws Exception
   * @return Object Resultado arrojado por la ejecuci�n del m�todo, null si es void.
   */
  public static Object runMethodInClass(String cClase,String cMetodo, String cParams) throws Exception{
    String cMensaje = "";
    Object theAnswer = null;
    try{
      theAnswer = runMethodInClass(cClase, cMetodo, getParams(cParams));
    } catch(Exception generalEx){
      cMensaje += generalEx.getMessage();
    }
    if (!cMensaje.equals(""))
      throw new Exception(cMensaje);
    else
      return theAnswer;
  }

  /**
   * M�todo encargado de regresar un arreglo de objetos con los par�metros proporcionados de acuerdo al estandar.
   * @param cParams String parametros que se desea reclasifiquen de acuerdo al est�ndar.
   * @return Object[] Arreglo de objetos con los objetos de cada par�metro.
   */
  private static Object[] getParams(String cParams){
    int iTokens = 0;
    StringTokenizer stCad = new StringTokenizer(cParams,"$");
    String cToken = "",cParam = "",cValor = "";
    StringTokenizer stParamValor;
    Object[] oParams = new Object[stCad.countTokens()];
    iTokens = stCad.countTokens();
    for(int i = 1;i <= iTokens;i++){
      cToken = stCad.nextToken();
      stParamValor = new StringTokenizer(cToken,"^");
      cParam = stParamValor.nextToken();
      cValor = stParamValor.nextToken();
      if(cParam.startsWith("cPar"))
        oParams[i - 1] = String.valueOf(cValor);
      else if(cParam.startsWith("iPar"))
        oParams[i - 1] = Integer.valueOf(cValor);
      else if(cParam.startsWith("dPar"))
        oParams[i - 1] = Double.valueOf(cValor);
      else if(cParam.startsWith("lPar"))
        oParams[i - 1] = Long.valueOf(cValor);
      else if(cParam.startsWith("dtPar"))
        oParams[i - 1] = new TFechas().getDateSQL(cValor); // (dd/mm/aaaa)
      else if(cParam.startsWith("tsPar"))
        oParams[i - 1] = new TFechas().getTimeStamp(cValor); // (dd/mm/aaaa hh:mm:ss)
      else
        oParams[i - 1] = String.valueOf(cValor);
    }
    return oParams;
  }

  /**
   * M�todo encargado de buscar el m�todo solicitado por nombre y regresar el objeto M�todo.
   * @param whereFind Class Objeto Clase en la cual se desea buscar el m�todo a ejecutar.
   * @param cMethod String Nombre del m�todo que se est� buscando.
   * @throws Exception
   * @return Method Objeto M�todo localizado en la clase proporcionada.
   */
  private static Method findMethod(Class whereFind,String cMethod) throws
      Exception{
    Method[] methods = whereFind.getMethods();
    for(int i = 0;i < methods.length;i++)
      if(methods[i].getName().equals(cMethod))
        return methods[i];
    return null;
  }
}
