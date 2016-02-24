package com.micper.ingsw;

import com.micper.util.*;
import java.util.*;
import java.sql.*;

import com.micper.excepciones.*;
import com.micper.sql.*;
import com.micper.util.logging.*;


/**
 * Clase encargada de obtener los valores del archivo globales.properties.
 * <p>En esta clase se conservarán los parámetros de configuración de cada módulo en un solo archivo de propiedades.
 * @version 1.0
 * <p>
 * @author Tecnología Inred S.A. de C.V.
 * @author <dd>José Luis López Hernández
 * @author <dd>LSC. Rafael Miranda Blumenkron
 * <p>
 * @see <small><a href="JavaScript:alert('Consulte los archivos:\n-*.jsp\n-pgXXXXXXXCFG.java\n-Archivos de ingenieria de software com.micper.ingsw.*.java');">Click para mas información</a></small>
 * </dd>
 * <p></p><DT><strong>Diagrama de Clase:</strong>
 * <p><img src="TParametro.png">
 */

public class TGetADMSEG {
  /** Variable que contendrá todos los atributos del archivo de propiedades. */
  private TFileProperties vPropiedades = null;
  /** Variable que contiene el nombre del archivo de propiedades a emplear. */
  private final String APP_CONFIG_FILE="globales";

  /**
   * Constructor que se encarga de cargar los parámetros de configuración del archivo globales.properties.
   * @param cId Identificador que se asignará al objeto que contiene los parámetros cargados.
   */
  public TGetADMSEG() {
    try {
      vPropiedades = new TFileProperties(APP_CONFIG_FILE);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Este método se encarga de obtener el valor asignado a un parámetro en el archivo de propiedades.
   * <br>En este caso se obtendrá el valor del parámetro que tenga el prefijo que se estableció en el identificador.
   * @param cProp Nombre del parámetro cuyo valor se desea obtener.
   * @return Valor del parámetro deseado (correspondiente al que tenga el prefijo).
   */
  public String getPropEspecifica(String cProp){
    String cValor = "";
    try{
      cValor = vPropiedades.getProperty(cProp);
    }catch(Exception e){
      e.printStackTrace();
//      //System.out.print("TGetADMSEG.La Propiedad Específica: " + cProp + " No Fue Encontrada!");
    }
    return cValor;
  }

  private String getPropEspecificaNoError(String cProp){
    String cValor = "";
    try{
      cValor = vPropiedades.getProperty(cProp);
    }catch(Exception e){
      cValor = "";
    }
    return cValor;
  }

public HashMap getPropiedades(int iId, String cDataSource) throws DAOException{
    DbConnection dbConn = null;
    Connection conn = null;
    PreparedStatement lPStmt = null;
    ResultSet lRSet = null;
    HashMap hmPropiedades = new HashMap();
    try {
      dbConn = new DbConnection(cDataSource);
      conn = dbConn.getConnection();
      String lSQL = "", cKey, cProp, cTemp;
      conn.setAutoCommit(false);
      conn.setTransactionIsolation(2);
      lSQL = "select * " +
          "from  segpropiedad " +
          "where segpropiedad.icvesistema = ? " +
          "";

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1, iId);

      lRSet = lPStmt.executeQuery();
      while (lRSet.next()) {
        cTemp = lRSet.getString("cPropiedad");
        cKey  = lRSet.getString("icvesistema") + "." + cTemp;
        cProp = lRSet.getString("cValor");
        if (!getPropEspecificaNoError(cTemp).equals("")){
          cProp = getPropEspecificaNoError(cTemp);
          hmPropiedades.put(cKey, cProp);
        }
        else
          hmPropiedades.put(cKey,cProp);
      }
    }
    catch (Exception ex) {
      TLogger.log(this, TLogger.WARN, "No se efectuó la Búsqueda por RFC", ex);
      throw new DAOException("Error en DAO Ciudadano");
    }
    finally {
      try {
        lRSet.close();
        lPStmt.close();
        conn.close();
        dbConn.closeConnection();
      }
      catch (Exception ex2) {
//        System.out.print("TGetADMSEG.getPropiedades");
        ex2.printStackTrace();
      }
      return hmPropiedades;
    }
  }

}