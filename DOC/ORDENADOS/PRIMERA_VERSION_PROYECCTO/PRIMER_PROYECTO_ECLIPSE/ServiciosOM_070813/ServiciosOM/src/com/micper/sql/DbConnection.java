package com.micper.sql;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;

/**
 * Crea una conexión hacia un DataSource.
 * <p>Ingeniería de Software generada en JAVA (J2EE).
 * @version 1.0
 * <p>
 * @author Tecnología Inred S.A. de C.V.
 * @author <dd>José Luis López Hernández
 * <p>
 * @see <small><a href="JavaScript:alert('Consulte los archivos:\n-pg9900002.jsp\n-pg9900010.jsp');">Click para mas información</a></small>
 * </dd>
 * <p></p><dt><strong>Diagrama de Clase:</strong>
 * <p><img src="DbConnection.png">
 */

public class DbConnection {
  /** Objeto DataSource que contendrá el nombre del DataSource. */
  private Connection conn;
  private DataSource dataSource;
  /** Variable que contendrá la conexión al nombre del DataSource. */
private DbConnection dbConnection;
  private String dataSourceName;

  /**
   * Constructor encargado de iniciar el contexto y el objeto DataSource.
   * @param dataSourceName Nombre del DataSource que se empleará
   * @throws Exception La excepción que puede arrojarse se debe a que no se cree el objeto DataSource.
   */
  public DbConnection(String dataSourceName) throws Exception {
 try{
   this.dataSourceName = dataSourceName;
   InitialContext iCtx = new InitialContext();
   this.dataSource = (javax.sql.DataSource) iCtx.lookup(dataSourceName);
 }catch(Exception e){
   System.out.println("Error en constructor de DBConnection: ");
   e.printStackTrace();
 }
  }

   /**
   * Método encargado de obtener el objeto connection del DataSource previamente inicializado.
   * @return Objeto Connection que corresponde al dataSource.
   * @throws Exception La excepción se puede presentar si el dataSource no ha sido inicializado.
   */
  public Connection getConnection() throws Exception {
    conn=dataSource.getConnection();
    return conn;
  }

  /**
   * Método que se encarga de cerrar una conexión previamente establecida.
   * @param pConn Objeto que se desea emplear para cerrar la conexión.
   */
  public void closeConnection() {
    if (conn != null)
      try {
        conn.close();
      } catch (Exception ex) {
        System.out.print("no se pudo cerrar connection: "+dataSourceName);
        ex.printStackTrace();
      }

  }
}

