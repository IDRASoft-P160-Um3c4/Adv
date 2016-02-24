package com.micper.sql;

import java.sql.*;
import java.io.*;

/**
 * Esta clase se encarga de generar un valor secuencial y de buscar el actual en la tabla de secuencias.
 * <p>Ingeniería de Software generada en JAVA (J2EE).
 * <p>Es importante indicar que las secuencias se obtienen en base al valor de un campo y su valor consecutivo en otro campo..
 * @version 1.0
 * <p>
 * @author Tecnología Inred S.A. de C.V.
 * @author <dd>José Luis López Hernández
 * <p>
 * @see <small><a href="./../general/db/TGrlEntidadFed.html">com.micper.general.db.TGrlentidadFed</a></small>
 * @see <small><a href="./../general/db/TGrlMunicipio.html">com.micper.general.db.TGrlMunicipio</a></small>
 * @see <small><a href="./../general/db/TGrlPais.html">com.micper.general.db.TGrlPais</a></small>
 * @see <small><a href="./../seguridad/db/TSegUnidadOrg.html">com.micper.seguridad.db.TSegUnidadOrg</a></small>
 * </dd>
 * <p></p><dt><strong>Diagrama de Clase:</strong>
 * <p><img src="TablaSecuencia.png">
 */

public class TablaSecuenciaXAnio implements Serializable {
  /**
	 *
	 */
	private static final long serialVersionUID = 2209734588584077976L;
/** Número mínimo de intentos a realizar en la búsqueda y actualización de secuencia. */
  public static final int MIN_RETRIES = 1;
  /** Número máximo de intentos a realizar en la búsqueda y actualización de secuencia. */
  public static final int MAX_RETRIES = 10;
  /** Número de intentos personalizados a realizar en la búsqueda y actualización de secuencia. */
  private static int RETRIES = 3;

  /**
   * Constructor encargado de crear el objeto y asignar los números de intentos.
   * @param pRetries Número de intentos personalizado.
   */
  public static void setRetries(int pRetries) {
    if (pRetries >= MAX_RETRIES && pRetries <= MAX_RETRIES)
      RETRIES = pRetries;
    
  }

  /**
   * Método encargado de devolver el valor de una secuencia para una tabla proporcionada.
   * @param pConn Objeto de conexión al DataSource para hacer la búsqueda de la secuencia.
   * @param pTableName Nombre de la tabla cuya secuencia deseamos consultar.
   * @return Valor entero de la secuencia.
   * @throws SQLException El error que puede arrojarse se debe a problemas con SQL.
   */
  public static int getSecuencia(Connection pConn, String pTableName, int iEjercicio) throws SQLException {
    Connection lConn = pConn;
    Statement stmtUpdate = null;
    Statement stmtSelect = null;
    ResultSet rSet = null;
    PreparedStatement lPStmt = null;
    int lId = 0;

	int currentTransaction = Connection.TRANSACTION_READ_COMMITTED;
    try {
      currentTransaction = lConn.getTransactionIsolation();
    } catch (Exception ex){
    }

    for (int i = MIN_RETRIES; i <= RETRIES; i++) {
      try {
        stmtUpdate = lConn.createStatement();
        System.out.print("Tabla:" + pTableName + " Iejercicio_" + iEjercicio);
        stmtUpdate.executeUpdate("update SECUENCIASXANIO set INDICE = INDICE+1 where TABLA = '" + pTableName + "' and iEjercicio = " + iEjercicio );
        stmtSelect = lConn.createStatement();
        rSet = stmtSelect.executeQuery("select INDICE from SECUENCIASXANIO where TABLA = '" + pTableName + "' and iEjercicio = " + iEjercicio );
        if(rSet.next())
          lId = rSet.getInt(1);
        else{
        	String cSQLReq = "INSERT INTO SECUENCIASXANIO (TABLA,IEJERCICIO,INDICE)" +
            " VALUES('"+pTableName+"',"+iEjercicio+",1)";
        	
        	stmtUpdate.executeUpdate(cSQLReq);   
        	lId = 1;
          //throw new SQLException("TablaSecuencia: secuencia no existe para la tabla: " + pTableName);
        }
        lConn.commit();
        break;
      } catch (SQLException ex) {
        try {
          lConn.rollback();
        } catch(Exception ex1) {
        }
        try {
          Thread.sleep(300L);
        } catch(Exception ex2) {
        }
        if(i >= RETRIES)
          throw ex;
      } finally {
        if(rSet != null)
          try {
            rSet.close();
          } catch(Exception ex) {
          }
        if(stmtUpdate != null)
          try {
            stmtUpdate.close();
          } catch(Exception ex) {
          }
        if(stmtSelect != null)
          try {
            stmtSelect.close();
          } catch(Exception ex) {
          }
      }
    }
    return lId;
  }
  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }
  private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }
}
