package com.micper.sql;

import java.sql.*;
import java.util.*;
import javax.sql.*;

import com.micper.excepciones.*;
import com.micper.ingsw.*;
import com.micper.seguridad.vo.*;
import com.micper.util.logging.*;
import com.micper.util.TFechas;

/**
 * <p>Title: Clase que hace conexion a una base de datos</p>
 * <p>Description: Clase que hace conexion a una base de datos </p>
 * <p>
 * @author Tecnología Inred S.A. de C.V.
 * @author <dd>Ing. Miguel Angel Magos Ruiz<dd>Lic. Jaime Enrique Suárez Romero<dd>LSC. Rafael Miranda Blumenkron
 * <p>
 * @version 1.0
 */

public class DAOBaseSerializable{

  private TParametro vParametros = null;
  private String cSistema = "44";
  private String cDBManager = "";
  public  String cMensaje = "";
  protected String dataSourceName = null;
  protected String cError = "";
  protected DbConnection dbConn;
  protected Connection conn;
  protected DataSource dataSource;

  public DAOBaseSerializable(){
    try{
      this.setSistema("44");
    }catch(Exception e){}
  }

  /**
   * Método que se encarga de cerrar una conexión previamente establecida.
   * @param cModulo Objeto que es el módulo general del Sistema
   */

  public void setSistema(String cModulo){
    vParametros = new TParametro(cModulo);
    dataSourceName = vParametros.getPropEspecifica("ConDBModulo");
  }

  /**
   * Método que permite la posibilidad de llamar al DAO a través del AppCacheManager.
       * @param String, permite pasar una lista de parametros para hacer la búsqueda.
   * @return boolean, permite conocer el resultado de la búsqueda.
   */
  public boolean findCollection(String cCollName,String cKey){
    return false;
  }

  /**
   * Método que permite la posibilidad de llamar al DAO a través del AppCacheManager.
   * @return Object, Objeto que regresa el resultado de la búsqueda.
   */
  public Object getCollection(){
    return "";
  }

  /**
   * Método Find By All Genérico
   */
  public final Vector FindByGeneric(String cKey,String cSQL,String dataSourceName) throws
      SQLException{
    this.dataSourceName = dataSourceName;
    DbConnection dbConn = null;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rset = null;
    Vector vcDinRep = new Vector();
    TFechas fecha = new TFechas();
    boolean lDebug = false;

    try{
      if(vParametros != null && vParametros.getPropEspecifica("lDebug") != null)
        if(vParametros.getPropEspecifica("lDebug").equalsIgnoreCase("true"))
           lDebug = true;
    }catch(Exception e){}

    if(lDebug && lDebug==true){
      String cSQL1 = cSQL.toUpperCase();
      cSQL1 = cSQL1.trim();
      cSQL1 = cSQL1.replaceAll("LEFT JOIN","LEFTJ");
      cSQL1 = cSQL1.replaceAll("SELECT ","SELECT\n       ");
      cSQL1 = cSQL1.replaceAll(", ",",");
      cSQL1 = cSQL1.replaceAll(",",",\n       ");
      cSQL1 = cSQL1.replaceAll(" WHERE ","\n  WHERE " );
      cSQL1 = cSQL1.replaceAll(" JOIN ","\n  JOIN ");
      cSQL1 = cSQL1.replaceAll(" LEFTJ ","\n  LEFT JOIN ");
      cSQL1 = cSQL1.replaceAll(" ORDER ","\n  ORDER ");
      cSQL1 = cSQL1.replaceAll(" AND ","\n    AND ");
      cSQL1 = cSQL1.replaceAll(" FROM ","\nFROM ");
      cSQL1 = cSQL1.replaceAll(" WHEN ","\n          WHEN ");
      cSQL1 = cSQL1.replaceAll(" ELSE ","\n          ELSE ");
      cSQL1 = cSQL1.replaceAll(" END "," END\n        ");

      System.out.print("\n--------------------------------------------\nClase:  "+this.getClass().getName()+"\n"+cSQL1 + "\n--------------------------------------------\n");
    }

    try{
      dbConn = new DbConnection(dataSourceName);
      conn = dbConn.getConnection();
      conn.setAutoCommit(false);
      conn.setTransactionIsolation(8);

      pstmt = conn.prepareStatement(cSQL);
      rset = pstmt.executeQuery();

      vcDinRep = this.getVO(cKey,rset);

    } catch(SQLException ex){
      if(lDebug)
        ex.printStackTrace();
      cError = cMensaje = getSQLMessages(ex);
    } catch(Exception ex){
      cError = ex.toString();
      if(lDebug)
        ex.printStackTrace();
    } finally{
      try{
        if(rset != null){
          rset.close();
        }
        if(pstmt != null){
          pstmt.close();
        }
        if(conn != null){
          conn.close();
        }
        dbConn.closeConnection();
      } catch(Exception ex2){
        warn("FindByGeneric.close",ex2);
      }
      if(!cError.equals(""))
        throw new SQLException(cError);
      return vcDinRep;
    }
  }
  public final Vector findByNessted(String cKey,String cSQL,Connection cnNested) throws
      DAOException{
    ResultSet rset = null;
    Vector vcDinRep = new Vector();
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement pstmt = null;
    boolean lSuccess = true;
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(8);
      }
      String cSQL1 = cSQL.toUpperCase();
      cSQL1 = cSQL1.replaceAll("SELECT ","SELECT\n       ");
      cSQL1 = cSQL1.replaceAll(", ",",");
      cSQL1 = cSQL1.replaceAll(",",",\n       ");
      cSQL1 = cSQL1.replaceAll(" WHERE ","\n  WHERE " );
      cSQL1 = cSQL1.replaceAll(" JOIN ","\n  JOIN ");
      cSQL1 = cSQL1.replaceAll(" ORDER ","\n  ORDER ");
      cSQL1 = cSQL1.replaceAll(" AND ","\n    AND ");
      cSQL1 = cSQL1.replaceAll(" FROM ","\nFROM ");
      System.out.print("\n============================================\n"+cSQL1 + "\n============================================\n");

      pstmt = conn.prepareStatement(cSQL);
      rset = pstmt.executeQuery();
      vcDinRep = this.getVO(cKey,rset);

    } catch(Exception ex){
      cError = ex.toString();
      ex.printStackTrace();
    } finally{
      try{
        if(rset != null){
          rset.close();
        }
        if(pstmt != null){
          pstmt.close();
        }
      } catch(Exception ex3){
        warn("FindByGeneric.rset.close",ex3);
      }
      if(cnNested == null){
        try{
          if(conn != null){
            conn.close();
          }
          dbConn.closeConnection();
        } catch(Exception ex2){
          warn("FindByGeneric.close",ex2);
        }
      }
      if(!cError.equals(""))
        throw new DAOException(cError);
      return vcDinRep;
    }
  }


  public final Vector FindByGenericReadUncommitted(String cKey,String cSQL,String dataSourceName) throws
      SQLException{
    this.dataSourceName = dataSourceName;
    DbConnection dbConn = null;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rset = null;
    Vector vcDinRep = new Vector();
    boolean lDebug = false;

    try{
      if(vParametros != null && vParametros.getPropEspecifica("lDebug") != null)
        if(vParametros.getPropEspecifica("lDebug").equalsIgnoreCase("true"))
           lDebug = true;
    }catch(Exception e){}

    if(lDebug){
      String cSQL1 = cSQL.toUpperCase();
      cSQL1 = cSQL1.replaceAll("SELECT ","SELECT\n       ");
      cSQL1 = cSQL1.replaceAll(", ",",");
      cSQL1 = cSQL1.replaceAll(",",",\n       ");
      cSQL1 = cSQL1.replaceAll(" WHERE ","\n  WHERE " );
      cSQL1 = cSQL1.replaceAll(" JOIN ","\n  JOIN ");
      cSQL1 = cSQL1.replaceAll(" ORDER ","\n  ORDER ");
      cSQL1 = cSQL1.replaceAll(" AND ","\n    AND ");
      cSQL1 = cSQL1.replaceAll(" FROM ","\nFROM ");
      System.out.print("\n--------------------------------------------\n"+cSQL1 + "\n--------------------------------------------\n");
    }

    try{
      dbConn = new DbConnection(dataSourceName);
      conn = dbConn.getConnection();
      conn.setAutoCommit(false);
      conn.setTransactionIsolation(conn.TRANSACTION_READ_UNCOMMITTED);

      pstmt = conn.prepareStatement(cSQL);
      rset = pstmt.executeQuery();

      vcDinRep = this.getVO(cKey,rset);

    } catch(SQLException ex){
      if(lDebug)
        ex.printStackTrace();
      cError = cMensaje = getSQLMessages(ex);
    } catch(Exception ex){
      cError = ex.toString();
      if(lDebug)
        ex.printStackTrace();
    } finally{
      try{
        if(rset != null){
          rset.close();
        }
        if(pstmt != null){
          pstmt.close();
        }
        if(conn != null){
          conn.close();
        }
        dbConn.closeConnection();
      } catch(Exception ex2){
        warn("FindByGeneric.close",ex2);
      }
      if(!cError.equals(""))
        throw new SQLException(cError);
      return vcDinRep;
    }
  }


  /**
   * Método que permite dado un resultset crear un VO dinámico con los nombres de los campos
   * @return Vector de VODinámicos.
   */
  public Vector getVO(String cKey,ResultSet rset) throws DAOException{
    Vector vcDinRep = new Vector();
    int iTipo = 0;
    try{
      ResultSetMetaData rsetMD = rset.getMetaData();

      TVDinRep vDinRep;
      int iNumCols = rsetMD.getColumnCount();

      while(rset.next()){
        vDinRep = new TVDinRep();
        vDinRep.setLlave(cKey.toUpperCase());
        for(int count = 1;count <= iNumCols;count++){
          if(count >= 1){
            iTipo = this.getITipo(rsetMD.getColumnName(count));
            switch(iTipo){
              case 1:
                vDinRep.put(rsetMD.getColumnName(count),rset.getInt(count));
                break;
              case 2:
                vDinRep.put(rsetMD.getColumnName(count),rset.getString(count));
                break;
              case 3:
                vDinRep.put(rsetMD.getColumnName(count),rset.getDate(count));
                break;
              case 4:
                vDinRep.put(rsetMD.getColumnName(count),rset.getFloat(count));
                break;
              case 6:
                vDinRep.put(rsetMD.getColumnName(count),rset.getTimestamp(count));
                break;
              case 7:
                vDinRep.put(rsetMD.getColumnName(count), rset.getBytes(count));
                break;
              default:
                vDinRep.put(rsetMD.getColumnName(count),rset.getString(count));
            }
          }
        }
        vcDinRep.addElement(vDinRep);
      }
    } catch(Exception e){
      warn("setVO",e);
      throw new DAOException("setVO");
    }
    return vcDinRep;

  }

  /**
   * Método que permite obtener el tipo de dato en base al estandar para identificadores
   * @return tipo 1:Entero, 2: String, 3: SQL Fecha, 4: Float, 0: No reconocido -> String.
   */
  public int getITipo(String cColumnName){
    int iTipo = 0;

    if(cColumnName.length() > 1){

      String cTipo1 = cColumnName.substring(0,1);
      String cTipo2 = cColumnName.substring(0,2);
      if(cTipo1.equals("I") || cTipo1.equals("L")){
        iTipo = 1;
      }
      if(cTipo1.equals("C")){
        iTipo = 2;
      }
      if(cTipo1.equals("D")){
        if(cTipo2.equals("DT")){
          iTipo = 3;
        } else{
          iTipo = 4;
        }
      }
      if(cTipo2.equals("TS")){
        iTipo = 6;
      }
      if(cTipo1.equals("B")){
        iTipo = 7;
      }
    }else{
      if(cColumnName.length() == 1){
        iTipo = 2;
      }
    }
    return iTipo;
  }

  /**
       * Método encargado de enviar mensajes de advertencia que el programador decida
   * enviar, dentro del metodo catch.
   * @param Aviso se refiere al mensaje que el programador decida poner.
   * @param ex se refiere a la Excepcion que envia el metodo catch.
   */
  protected void warn(String Aviso,Exception ex){
    TLogger.log(this,TLogger.WARN,Aviso,ex);
  }

  /**
   * Método encargado de enviar mensajes informativos que el programador decida
   * enviar. Se pueden poner en cualquier parte del código.
   * @param Aviso se refiere al mensaje que el programador decida poner.
   */
  public void info(String Aviso){
    TLogger.log(this,TLogger.INFO,Aviso,null);
  }

  /**
   * Método encargado de enviar mensajes de debugueo que el programador decida
   * enviar. Se pueden poner en cualquier parte del código y tendrán la opcion
   * de poderse apagar mediante una bandera.
   * @param Aviso se refiere al mensaje que el programador decida poner.
   */
  public void debug(String Aviso){
    TLogger.log(this,TLogger.DEBUG,Aviso,null);
  }

  /**
   * Método encargado de enviar mensajes de error que el programador decida
   * enviar, dentro del metodo catch.
   * @param Aviso se refiere al mensaje que el programador decida poner.
   * @param ex se refiere a la Excepcion que envia el metodo catch.
   */
  public void error(String Aviso,Exception ex){
    TLogger.log(this,TLogger.ERROR,Aviso,ex);
  }

  /**
   * Método encargado de enviar mensajes de errores fatales que el programador decida
   * enviar, dentro del metodo catch.
   * @param obj se refiere al modulo en donde ocurre el error.
   * @param Aviso se refiere al mensaje que el programador decida poner.
   * @param ex se refiere a la Excepcion que envia el metodo catch.
   */
  public void fatal(String Aviso,Exception ex){
    TLogger.log(this,TLogger.FATAL,Aviso,ex);
  }

  /**
   * Este método se encarga de regresar los mensajes de error generados por un SQLException,
   *  clasificados de acuerdo al manejador de BD, en caso de no estar catalogados, regresará
   * el número de error y el SQLState correspondientes.
   * @param ex SQLException Excepcion del tipo SQL que desea clasificar.
   * @return String Cadena resultante de mensajes generados por SQL.
   */
  public String getSQLMessages(SQLException ex){
    String cOrigen = "";
    String cMensaje = "";
    StackTraceElement[] stElements = ex.getStackTrace();
    for (int i = 0; i < stElements.length; i++)
      if (stElements[i].getClassName().toUpperCase().startsWith("COM.MICPER")||
          stElements[i].getClassName().toUpperCase().startsWith("GOB.SCT")){
        cOrigen = stElements[i].getClassName() + "." + stElements[i].getMethodName();
        i = stElements.length;
      }
      else
        cOrigen = "Desconocido";
    if (cDBManager == "")
      cDBManager = new TParametro(cSistema).getPropEspecifica("DBManager").toString().toUpperCase();
    if (cDBManager.equals("DB2")){ // Especifico por cada manejador de BD.
      if(ex.getErrorCode() != 0)
        cMensaje = "Error(es) generado(s) en: " + cOrigen + this.DB2sqlMessages(ex);
    }else
      cMensaje = "Error(es) generado(s) en: " + cOrigen + "\\nManejador de BD (" + cDBManager + ") no catálogado en DAOException" + "\\nError Code (" + ex.getErrorCode() + ") SQLState=(" + ex.getSQLState() + ")";
    return cMensaje;
  }

  /**
   * Este método se encarga de regresar los mensajes catálogados específicamente para DB2.
   * @param ex SQLException Excepcion del tipo SQL que desea catalogar los mensajes.
   * @return String Mensajes resultantes de la clasificación en DB2.
   */
  private String DB2sqlMessages(SQLException ex){
    String cMensaje = "";
    String cTemp = "";
    int i = 0;
    do{
      switch(ex.getErrorCode()){
        case -803: cTemp = "La llave primaria está duplicada"; break;
        case -532: cTemp = "No puede eliminar registros que tengan datos dependientes"; break;
        case -530: cTemp = "Se ha proporcionado un valor de llave foránea inexistente en tabla relacionada"; break;
        case -205: cTemp = "El nombre de la columna no existe."; break;
        case -204: cTemp = "Existe un nombre no definido."; break;
        case -203: cTemp = "La referencia a una columna es ambiguo."; break;
        case -117: cTemp = "El número de valores proporcionados no coincide con el número de columnas."; break;
          // Datos obtenidos de internet,
          // al pasar a español favor de colocarlo en la parte superior de forma ordenada
          // y eliminarlo de lista inferior.
        case -991: cTemp = "CALL ATTACH WAS UNABLE TO ESTABLISH AN IMPLICIT CONNECT OR OPEN TO DB2."; break;
        case -981: cTemp = "THE SQL STATEMENT FAILED BECAUSE THE RRSAF CONNECTION IS NOT IN A STATE THAT ALLOWS SQL OPERATIONS, REASON."; break;
        case -950: cTemp = "THE LOCATION NAME SPECIFIED IN THE CONNECT STATEMENT IS INVALID OR NOT LISTED IN THECOMMUNICATIONS DATABASE."; break;
        case -948: cTemp = "DISTRIBUTED OPERATION IS INVALID."; break;
        case -947: cTemp = "THE SQL STATEMENT FAILED BECAUSE IT WILL CHANGE A TABLE DEFINED WITH DATA CAPTURECHANGES, BUT THE DATA CANNOT BE PROPAGATED."; break;
        case -939: cTemp = "ROLLBACK REQUIRED DUE TO UNREQUESTED ROLLBACK OF A REMOTE SERVER."; break;
        case -929: cTemp = "FAILURE IN A CHANGED DATA CAPTURE EXIT."; break;
        case -927: cTemp = "THE LANGUAGE INTERFACE (LI) WAS CALLED WHEN THE CONNECTING ENVIRONMENT WAS NOTESTABLISHED. THE PROGRAM SHOULD BE INVOKED UNDER THE DSN COMMAND."; break;
        case -926: cTemp = "ROLLBACK NOT VALID IN IMS OR CICS ENVIRONMENT."; break;
        case -925: cTemp = "COMMIT NOT VALID IN IMS OR CICS ENVIRONMENT."; break;
        case -924: cTemp = "DB2 CONNECTION INTERNAL ERROR."; break;
        case -923: cTemp = "CONNECTION NOT ESTABLISHED: DB2, REASON, TYPE, NAME."; break;
        case -922: cTemp = "AUTHORIZATION FAILURE:  ERROR. REASON."; break;
        case -919: cTemp = "A ROLLBACK OPERATION IS REQUIRED."; break;
        case -918: cTemp = "THE SQL STATEMENT CANNOT BE EXECUTED BECAUSE A CONNECTION HAS BEEN LOST."; break;
        case -917: cTemp = "BIND PACKAGE FAILED."; break;
        case -913: cTemp = "UNSUCCESSFUL EXECUTION CAUSED BY DEADLOCK OR TIMEOUT. REASON CODE, TYPE OF RESOURCE, AND RESOURCE NAME."; break;
        case -911: cTemp = "THE CURRENT UNIT OF WORK HAS BEEN ROLLED BACK DUE TO DEADLOCK OR TIMEOUT.  REASON, TYPE OF RESOURCE, AND RESOURCE NAME."; break;
        case -910: cTemp = "THE SQL STATEMENT CANNOT ACCESS AN OBJECT ON WHICH A DROP OR ALTER IS PENDING."; break;
        case -909: cTemp = "THE OBJECT HAS BEEN DELETED OR ALTERED."; break;
        case -908: cTemp = "ERROR USING  AUTHORITY.  BIND, REBIND, OR AUTO-REBIND OPERATION IS NOT ALLOWED."; break;
        case -906: cTemp = "THE SQL STATEMENT CANNOT BE EXECUTED BECAUSE THIS FUNCTION IS DISABLED DUE TO A PRIORERROR."; break;
        case -905: cTemp = "UNSUCCESSFUL EXECUTION DUE TO RESOURCE LIMIT BEING EXCEEDED, RESOURCE NAME =  LIMIT =CPU SECONDS ( SERVICE UNITS) DERIVED FROM."; break;
        case -904: cTemp = "UNSUCCESSFUL EXECUTION CAUSED BY AN UNAVAILABLE RESOURCE. REASON, TYPE OF RESOURCE, AND RESOURCE NAME."; break;
        case -902: cTemp = "POINTER TO ESSENTIAL CONTROL BLOCK(RDA/CT) HAS VALUE 0, REBIND REQUIRED."; break;
        case -901: cTemp = "UNSUCCESSFUL EXECUTION CAUSED BY A SYSTEM ERROR THAT DOES NOT PRECLUDE THE SUCCESSFULEXECUTION OF SUBSEQUENT SQL STATEMENTS."; break;
        case -900: cTemp = "THE SQL STATEMENT CANNOT BE EXECUTED BECAUSE THE APPLICATION PROCESS IS NOT CONNECTEDTO AN APPLICATION SERVER."; break;
        case -882: cTemp = "SAVEPOINT DOES NOT EXIST."; break;
        case -881: cTemp = "A SAVEPOINT WITH NAME  ALREADY EXISTS, BUT THIS SAVEPOINT NAME CANNOT BE REUSED."; break;
        case -880: cTemp = "SAVEPOINT  DOES NOT EXIST OR IS INVALID IN THIS CONTEXT."; break;
        case -879: cTemp = "CREATE OR ALTER STATEMENT FOR  CANNOT DEFINE A COLUMN, DISTINCT TYPE, FUNCTION ORSTORED PROCEDURE PARAMETER AS MIXED OR GRAPHIC WITH ENCODING SCHEME."; break;
        case -878: cTemp = "THE PLAN_TABLE USED FOR EXPLAIN CANNOT BE ASCII."; break;
        case -877: cTemp = "CCSID ASCII IS NOT ALLOWED FOR THIS DATABASE OR TABLE SPACE."; break;
        case -876: cTemp = "CANNOT BE CREATED, REASON."; break;
        case -875: cTemp = "CANNOT BE USED WITH THE ASCII DATA REFERENCED."; break;
        case -874: cTemp = "THE ENCODING SCHEME SPECIFIED FOR THE  MUST BE THE SAME AS THE CONTAINING TABLE SPACEOR OTHER PARAMETERS."; break;
        case -873: cTemp = "DATA ENCODED WITH DIFFERENT CCSIDS CANNOT BE REFERENCED IN THE SAME SQL STATEMENT."; break;
        case -872: cTemp = "A VALID CCSID HAS NOT YET BEEN SPECIFIED FOR THIS SUBSYSTEM."; break;
        case -870: cTemp = "THE NUMBER OF HOST VARIABLES IN THE STATEMENT IS NOT EQUAL TO THE NUMBER OFDESCRIPTORS."; break;
        case -867: cTemp = "INVALID SPECIFICATION OF A ROWID COLUMN."; break;
        case -846: cTemp = "INVALID SPECIFICATION OF AN IDENTITY COLUMN."; break;
        case -843: cTemp = "THE SET CONNECTION OR RELEASE STATEMENT MUST SPECIFY AN EXISTING CONNECTION."; break;
        case -842: cTemp = "A CONNECTION TO  ALREADY EXISTS."; break;
        case -840: cTemp = "TOO MANY ITEMS RETURNED IN A SELECT OR INSERT LIST."; break;
        case -822: cTemp = "THE SQLDA CONTAINS AN INVALID DATA ADDRESS OR INDICATOR VARIABLE ADDRESS."; break;
        case -820: cTemp = "THE SQL STATEMENT CANNOT BE PROCESSED BECAUSE  CONTAINS A VALUE THAT IS NOT VALID INTHIS RELEASE."; break;
        case -819: cTemp = "THE VIEW CANNOT BE PROCESSED BECAUSE THE LENGTH OF ITS PARSE TREE IN THE CATALOG ISZERO."; break;
        case -818: cTemp = "THE PRECOMPILER-GENERATED TIMESTAMP  IN THE LOAD MODULE IS DIFFERENT FROM THE BINDTIMESTAMP  BUILT FROM THE DBRM."; break;
        case -817: cTemp = "THE SQL STATEMENT CANNOT BE EXECUTED BECAUSE THE STATEMENT WILL RESULT IN A PROHIBITEDUPDATE OPERATION."; break;
        case -815: cTemp = "A GROUP BY OR HAVING CLAUSE IS IMPLICITLY OR EXPLICITLY SPECIFIED IN A SUBSELECT OF ABASIC PREDICATE OR A SET CLAUSE OF AN UPDATE STATEMENT."; break;
        case -812: cTemp = "THE SQL STATEMENT CANNOT BE PROCESSED BECAUSE A BLANK COLLECTION-ID WAS FOUND IN THECURRENTPACKAGESET SPECIAL REGISTER WHILE TRYING TO FORM A QUALIFIED PACKAGE NAME FOR PROGRAM  USING PLAN."; break;
        case -811: cTemp = "THE RESULT OF AN EMBEDDED SELECT STATEMENT OR A SUBSELECT IN THE SET CLAUSE OF ANUPDATE STATEMENT IS A TABLE OF MORE THAN ONE ROW, OR THE RESULT OF A SUBQUERY OF A BASIC PREDICATE IS MORETHAN VALUE."; break;
        case -808: cTemp = "THE CONNECT STATEMENT IS NOT CONSISTENT WITH THE FIRST CONNECT STATEMENT."; break;
        case -807: cTemp = "ACCESS DENIED: PACKAGE  IS NOT ENABLED FOR ACCESS FROM."; break;
        case -805: cTemp = "DBRM OR PACKAGE NAME  NOT FOUND IN PLAN . REASON."; break;
        case -804: cTemp = "AN ERROR WAS FOUND IN THE APPLICATION PROGRAM INPUT PARAMETERS FOR THE SQL STATEMENT,REASON."; break;
        case -802: cTemp = "EXCEPTION ERROR  HAS OCCURRED DURING  OPERATION ON  DATA, POSITION."; break;
        case -798: cTemp = "YOU CANNOT INSERT A VALUE INTO A COLUMN THAT IS DEFINED WITH THE OPTION GENERATEDALWAYS.  COLUMN NAME."; break;
        case -797: cTemp = "ATTEMPT TO CREATE TRIGGER  WITH AN UNSUPPORTED TRIGGERED SQL STATEMENT."; break;
        case -771: cTemp = "INVALID SPECIFICATION OF A ROWID COLUMN."; break;
        case -770: cTemp = "TABLE  CANNOT HAVE A LOB COLUMN UNLESS IT ALSO HAS A ROWID COLUMN."; break;
        case -769: cTemp = "SPECIFICATION OF CREATE AUX TABLE DOES NOT MATCH THE CHARACTERISTICS OF THE BASE TABLE."; break;
        case -768: cTemp = "AN AUXILIARY TABLE ALREADY EXISTS FOR THE SPECIFIED COLUMN OR PARTITION."; break;
        case -767: cTemp = "MISSING OR INVALID COLUMN SPECIFICATION FOR INDEX."; break;
        case -766: cTemp = "THE OBJECT OF A STATEMENT IS AN AUXILIARY TABLE FOR WHICH THE REQUESTED OPERATION ISNOT PERMITTED."; break;
        case -765: cTemp = "TABLE IS NOT COMPATIBLE WITH DATABASE."; break;
        case -764: cTemp = "A LOB TABLE SPACE AND ITS ASSOCIATED BASE TABLE SPACE MUST BE IN THE SAME DATABASE."; break;
        case -763: cTemp = "INVALID TABLE SPACE NAME."; break;
        case -752: cTemp = "THE CONNECT STATEMENT IS INVALID BECAUSE THE PROCESS IS NOT IN THE CONNECTABLE STATE."; break;
        case -751: cTemp = "(SPECIFIC NAME ) ATTEMPTED TO EXECUTE AN SQL STATEMENT  THAT IS NOT ALLOWED."; break;
        case -750: cTemp = "THE SOURCE TABLE  CANNOT BE RENAMED BECAUSE IT IS REFERENCED IN EXISTING VIEWDEFINITIONS OR TRIGGER DEFINITIONS."; break;
        case -748: cTemp = "AN INDEX ALREADY EXISTS ON AUXILIARY TABLE."; break;
        case -747: cTemp = "TABLE  IS NOT AVAILABLE UNTIL THE AUXILIARY TABLES AND INDEXES FOR ITS EXTERNALLYSTORED COLUMNS HAVE BEEN CREATED."; break;
        case -746: cTemp = "THE SQL STATEMENT IN AN EXTERNAL FUNCTION, TRIGGER, OR IN STORED PROCEDURE  VIOLATESTHE NESTING SQL RESTRICTION."; break;
        case -742: cTemp = "DSNDB07 IS THE IMPLICIT WORK FILE DATABASE."; break;
        case -741: cTemp = "A DATABASE IS ALREADY DEFINED FOR MEMBER."; break;
        case -740: cTemp = "FUNCTION  IS DEFINED WITH THE OPTION MODIFIES SQL DATA WHICH IS NOT VALID IN THECONTEXT IN WHICH IT WAS INVOKED."; break;
        case -739: cTemp = "ALTER FUNCTION  FAILED BECAUSE FUNCTIONS CANNOT MODIFY DATA WHEN THEY ARE PROCESSED INPARALLEL."; break;
        case -737: cTemp = "IMPLICIT TABLE SPACE NOT ALLOWED."; break;
        case -736: cTemp = "INVALID OBID  SPECIFIED."; break;
        case -735: cTemp = "DATABASE  CANNOT BE ACCESSED BECAUSE IT IS NO LONGER A SHARED DATABASE."; break;
        case -734: cTemp = "THE ROSHARE ATTRIBUTE OF A DATABASE CANNOT BE ALTERED FROM ROSHARE READ."; break;
        case -733: cTemp = "THE DESCRIPTION OF A TABLE SPACE, INDEX SPACE, OR TABLE IN A ROSHARE READ DATABASEMUST BE CONSISTENT WITH ITS DESCRIPTION IN THE OWNER SYSTEM."; break;
        case -732: cTemp = "THE DATABASE IS DEFINED ON THIS SUBSYSTEM WITH THE ROSHARE READ ATTRIBUTE BUT THETABLE SPACE OR INDEX SPACE HAS NOT BEEN DEFINED ON THE OWNING SUBSYSTEM."; break;
        case -731: cTemp = "USER-DEFINED DATASET  MUST BE DEFINED WITH SHAREOPTIONS (1,3)."; break;
        case -730: cTemp = "THE PARENT OF A TABLE IN A READ-ONLY SHARED DATABASE MUST ALSO BE A TABLE IN AREAD-ONLY SHARED DATABASE."; break;
        case -729: cTemp = "A STORED PROCEDURE SPECIFYING COMMIT ON RETURN CANNOT BE THE TARGET OF A NESTED CALLSTATEMENT."; break;
        case -728: cTemp = "DATA TYPE  IS NOT ALLOWED IN DB2 PRIVATE PROTOCOL PROCESSING."; break;
        case -726: cTemp = "BIND ERROR ATTEMPTING TO REPLACE PACKAGE = .  THERE ARE ENABLE OR DISABLE ENTRIESCURRENTLY ASSOCIATED WITH THE PACKAGE."; break;
        case -725: cTemp = "THE SPECIAL REGISTER  AT LOCATION  WAS SUPPLIED AN INVALID VALUE."; break;
        case -724: cTemp = "THE ACTIVATION OF THE  OBJECT  WOULD EXCEED THE MAXIMUM LEVEL OF INDIRECT SQLCASCADING."; break;
        case -723: cTemp = "AN ERROR OCCURRED IN A TRIGGERED SQL STATEMENT IN TRIGGER, SECTION NUMBER .INFORMATION RETURNED: SQLCODE, SQLSTATE, AND MESSAGE TOKENS."; break;
        case -722: cTemp = "ERROR USING  AUTHORITY PACKAGE DOES NOT EXIST."; break;
        case -721: cTemp = "BIND ERROR FOR PACKAGE =  CONTOKEN = X IS NOT UNIQUE SO IT CANNOT BE CREATED."; break;
        case -720: cTemp = "BIND ERROR, ATTEMPTING TO REPLACE PACKAGE =  WITH VERSION =  BUT THIS VERSION ALREADYEXISTS."; break;
        case -719: cTemp = "BIND ADD ERROR USING  AUTHORITY PACKAGE  ALREADY EXISTS."; break;
        case -718: cTemp = "REBIND OF PACKAGE  FAILED BECAUSE IBMREQD OF  IS INVALID."; break;
        case -717: cTemp = "FOR WITH MARK  FAILED BECAUSE  DEPENDS ON FUNCTIONS OF THE RELEASE FROM WHICH FALLBACK HAS OCCURRED."; break;
        case -716: cTemp = "PROGRAM  PRECOMPILED WITH INCORRECT LEVEL FOR THIS RELEASE."; break;
        case -715: cTemp = "PROGRAM  WITH MARK  FAILED BECAUSE IT DEPENDS ON FUNCTIONS OF THE RELEASE FROM WHICHFALLBACK HAS OCCURRED."; break;
        case -713: cTemp = "THE REPLACEMENT VALUE  FOR  IS INVALID."; break;
        case -697: cTemp = "OLD OR NEW CORRELATION NAMES ARE NOT ALLOWED IN A TRIGGER DEFINED WITH THE FOR EACHSTATEMENT CLAUSE.  OLD_TABLE OR NEW_TABLE NAMES ARE NOT ALLOWED IN A TRIGGER WITH THE BEFORE CLAUSE."; break;
        case -696: cTemp = "THE DEFINITION OF TRIGGER  INCLUDES AN INVALID USE OF CORRELATION NAME OR TRANSITIONTABLE NAME .  REASON CODE=."; break;
        case -694: cTemp = "THE DDL STATEMENT CANNOT BE EXECUTED BECAUSE A DROP IS PENDING ON THE DDL REGISTRATIONTABLE."; break;
        case -693: cTemp = "THE COLUMN  IN DDL REGISTRATION TABLE OR INDEX  IS NOT DEFINED PROPERLY."; break;
        case -692: cTemp = "THE REQUIRED UNIQUE INDEX  FOR DDL REGISTRATION TABLE  DOES NOT EXIST."; break;
        case -691: cTemp = "THE REQUIRED REGISTRATION TABLE  DOES NOT EXIST."; break;
        case -690: cTemp = "THE STATEMENT IS REJECTED BY DATA DEFINITION CONTROL SUPPORT.  REASON."; break;
        case -689: cTemp = "TOO MANY COLUMNS DEFINED FOR A DEPENDENT TABLE."; break;
        case -688: cTemp = "INCORRECT DATA RETURNED FROM FIELD PROCEDURE, ."; break;
        case -687: cTemp = "FIELD TYPES INCOMPARABLE."; break;
        case -686: cTemp = "COLUMN DEFINED WITH A FIELD PROCEDURE CAN NOT COMPARE WITH ANOTHER COLUMN WITHDIFFERENT FIELD PROCEDURE."; break;
        case -685: cTemp = "INVALID FIELD TYPE."; break;
        case -684: cTemp = "THE LENGTH OF LITERAL LIST BEGINNING  IS TOO LONG."; break;
        case -683: cTemp = "THE SPECIFICATION FOR COLUMN, DISTINCT TYPE, FUNCTION, OR PROCEDURE  CONTAINSINCOMPATIBLE CLAUSES."; break;
        case -682: cTemp = "FIELD PROCEDURE  COULD NOT BE LOADED."; break;
        case -681: cTemp = "COLUMN  IN VIOLATION OF INSTALLATION DEFINED FIELD PROCEDURE  RT:, RS:, MSG."; break;
        case -680: cTemp = "TOO MANY COLUMNS SPECIFIED FOR A TABLE, VIEW, OR TABLE FUNCTION."; break;
        case -679: cTemp = "THE OBJECT  CANNOT BE CREATED BECAUSE A DROP IS PENDING ON THE OBJECT."; break;
        case -678: cTemp = "THE LITERAL  SPECIFIED FOR THE INDEX LIMIT KEY MUST CONFORM TO THE DATA TYPE  OF THECORRESPONDING COLUMN."; break;
        case -677: cTemp = "INSUFFICIENT VIRTUAL STORAGE FOR BUFFERPOOL EXPANSION."; break;
        case -676: cTemp = "ONLY A 4K PAGE BUFFERPOOL CAN BE USED FOR AN INDEX."; break;
        case -672: cTemp = "OPERATION DROP NOT ALLOWED ON TABLE."; break;
        case -671: cTemp = "THE BUFFERPOOL ATTRIBUTE OF THE TABLE SPACE CANNOT BE ALTERED AS SPECIFIED BECAUSE ITWOULD CHANGE THE PAGE SIZE OF THE TABLE SPACE."; break;
        case -670: cTemp = "THE RECORD LENGTH OF THE TABLE EXCEEDS THE PAGE SIZE LIMIT."; break;
        case -669: cTemp = "A TABLE IN A PARTITIONED TABLE SPACE CANNOT BE EXPLICITLY DROPPED."; break;
        case -668: cTemp = "THE COLUMN CANNOT BE ADDED TO THE TABLE BECAUSE THE TABLE HAS AN EDIT PROCEDURE."; break;
        case -667: cTemp = "THE CLUSTERING INDEX FOR A PARTITIONED TABLE SPACE CANNOT BE EXPLICITLY DROPPED."; break;
        case -666: cTemp = "CANNOT BE EXECUTED BECAUSE  IS IN PROGRESS."; break;
        case -665: cTemp = "THE PART CLAUSE OF AN ALTER STATEMENT IS OMITTED OR INVALID."; break;
        case -663: cTemp = "THE NUMBER OF KEY LIMIT VALUES IS EITHER ZERO, OR GREATER THAN THE NUMBER OF COLUMNSIN THE KEY OF INDEX."; break;
        case -662: cTemp = "A PARTITIONED INDEX CANNOT BE CREATED ON A NON-PARTITIONED TABLE SPACE."; break;
        case -661: cTemp = "INDEX  CANNOT BE CREATED ON PARTITIONED TABLE SPACE  BECAUSE THE NUMBER OF PARTSPECIFICATIONS IS NOT EQUAL TO THE NUMBER OF PARTITIONS OF THE TABLE SPACE."; break;
        case -660: cTemp = "INDEX  CANNOT BE CREATED ON PARTITIONED TABLE SPACE  BECAUSE KEY LIMITS ARE NOTSPECIFIED."; break;
        case -658: cTemp = "A  CANNOT BE DROPPED USING THE  STATEMENT."; break;
        case -655: cTemp = "THE CREATE OR ALTER STOGROUP IS INVALID BECAUSE THE STORAGE GROUP WOULD HAVE BOTHSPECIFIC AND NON-SPECIFIC VOLUME IDS."; break;
        case -653: cTemp = "TABLE  IN PARTITIONED TABLE SPACE  IS NOT AVAILABLE BECAUSE ITS PARTITIONED INDEX HASNOT BEEN CREATED."; break;
        case -652: cTemp = "VIOLATION OF INSTALLATION DEFINED EDIT OR VALIDATION PROCEDURE."; break;
        case -651: cTemp = "TABLE DESCRIPTION EXCEEDS MAXIMUM SIZE OF OBJECT DESCRIPTOR."; break;
        case -650: cTemp = "THE ALTER INDEX CANNOT BE EXECUTED, REASON."; break;
        case -647: cTemp = "BUFFERPOOL  CANNOT BE SPECIFIED BECAUSE IT HAS NOT BEEN ACTIVATED."; break;
        case -646: cTemp = "TABLE  CANNOT BE CREATED IN SPECIFIED TABLE SPACE  BECAUSE IT ALREADY CONTAINS A TABLE."; break;
        case -644: cTemp = "INVALID VALUE SPECIFIED FOR KEYWORD  IN  STATEMENT."; break;
        case -643: cTemp = "CHECK CONSTRAINT EXCEEDS MAXIMUM ALLOWABLE LENGTH."; break;
        case -642: cTemp = "TOO MANY COLUMNS IN UNIQUE CONSTRAINTS."; break;
        case -640: cTemp = "LOCKSIZE ROW CANNOT BE SPECIFIED BECAUSE TABLE IN THIS TABLESPACE HAS TYPE 1 INDEX."; break;
        case -639: cTemp = "A NULLABLE COLUMN OF A FOREIGN KEY WITH A DELETE RULE OF SET NULL CANNOT BE A COLUMNOF THE KEY OF A PARTITIONED INDEX."; break;
        case -638: cTemp = "TABLE  CANNOT BE CREATED BECAUSE COLUMN DEFINITION IS MISSING."; break;
        case -637: cTemp = "DUPLICATE  KEYWORD."; break;
        case -636: cTemp = "THE PARTITIONING KEYS FOR PARTITION  ARE NOT SPECIFIED IN ASCENDING OR DESCENDINGORDER."; break;
        case -635: cTemp = "THE DELETE RULES CANNOT BE DIFFERENT OR CANNOT BE SET NULL."; break;
        case -634: cTemp = "THE DELETE RULE MUST NOT BE CASCADE."; break;
        case -633: cTemp = "THE DELETE RULE MUST BE."; break;
        case -632: cTemp = "THE TABLE CANNOT BE DEFINED AS A DEPENDENT OF  BECAUSE OF DELETE RULE RESTRICTIONS."; break;
        case -631: cTemp = "FOREIGN KEY  IS TOO LONG OR HAS TOO MANY COLUMNS."; break;
        case -630: cTemp = "THE WHERE NOT NULL SPECIFICATION IS INVALID FOR TYPE 1 INDEXES."; break;
        case -629: cTemp = "SET NULL CANNOT BE SPECIFIED BECAUSE FOREIGN KEY  CANNOT CONTAIN NULL VALUES."; break;
        case -628: cTemp = "THE CLAUSES ARE MUTUALLY EXCLUSIVE."; break;
        case -627: cTemp = "THE ALTER STATEMENT IS INVALID BECAUSE THE PAGESET HAS USER-MANAGED DATA SETS."; break;
        case -626: cTemp = "THE ALTER STATEMENT IS NOT EXECUTABLE BECAUSE THE PAGE SET IS NOT STOPPED."; break;
        case -625: cTemp = "TABLE  DOES NOT HAVE AN INDEX TO ENFORCE THE UNIQUENESS OF THE PARENT KEY."; break;
        case -624: cTemp = "TABLE  ALREADY HAS A PRIMARY KEY."; break;
        case -623: cTemp = "A CLUSTERING INDEX ALREADY EXISTS ON TABLE."; break;
        case -622: cTemp = "FOR MIXED DATA IS INVALID BECAUSE THE MIXED DATA INSTALL OPTION IS NO."; break;
        case -621: cTemp = "DUPLICATE DBID  WAS DETECTED AND PREVIOUSLY ASSIGNED TO."; break;
        case -620: cTemp = "KEYWORD  IN  STATEMENT IS NOT PERMITTED FOR A  SPACE IN THE  DATABASE."; break;
        case -619: cTemp = "OPERATION DISALLOWED BECAUSE THE WORK FILE DATABASE IS NOT STOPPED."; break;
        case -618: cTemp = "OPERATION  IS NOT ALLOWED ON SYSTEM DATABASES."; break;
        case -617: cTemp = "A TYPE 1 INDEX IS NOT VALID FOR TABLE."; break;
        case -616: cTemp = "CANNOT BE DROPPED BECAUSE IT IS REFERENCED BY."; break;
        case -615: cTemp = "IS NOT ALLOWED ON A PACKAGE IN USE."; break;
        case -614: cTemp = "THE INDEX CANNOT BE CREATED OR THE LENGTH OF THE COLUMN CANNOT BE CHANGED BECAUSE THESUM OF THE INTERNAL LENGTHS OF THE IDENTIFIED COLUMNS IS GREATER THAN THE ALLOWABLE MAXIMUM."; break;
        case -613: cTemp = "THE PRIMARY KEY OR A UNIQUE CONSTRAINT IS TOO LONG OR HAS TOO MANY COLUMNS."; break;
        case -612: cTemp = "IS A DUPLICATE COLUMN NAME."; break;
        case -611: cTemp = "ONLY LOCKMAX 0 CAN BE SPECIFIED WHEN THE LOCK SIZE OF THE TABLESPACE IS TABLESPACE ORTABLE."; break;
        case -607: cTemp = "OPERATION OR OPTION  IS NOT DEFINED FOR THIS OBJECT."; break;
        case -604: cTemp = "A DATA TYPE DEFINITION SPECIFIES AN INVALID LENGTH, PRECISION, OR SCALE ATTRIBUTE."; break;
        case -603: cTemp = "A UNIQUE INDEX CANNOT BE CREATED BECAUSE THE TABLE CONTAINS ROWS WHICH ARE DUPLICATESWITH RESPECT TO THE VALUES OF THE IDENTIFIED COLUMNS."; break;
        case -602: cTemp = "TOO MANY COLUMNS SPECIFIED IN A CREATE INDEX."; break;
        case -601: cTemp = "THE NAME OF THE OBJECT TO BE CREATED OR THE TARGET OF A RENAME STATEMENT IS IDENTICALTO THE EXISTING NAME  OF THE OBJECT TYPE."; break;
        case -594: cTemp = "ATTEMPT TO CREATE A NULLABLE ROWID OR DISTINCT TYPE COLUMN."; break;
        case -593: cTemp = "NOT NULL MUST BE SPECIFIED FOR ROWID OR DISTINCT TYPE COLUMN."; break;
        case -592: cTemp = "NOT AUTHORIZED TO CREATE FUNCTIONS OR PROCEDURES IN WLM ENVIRONMENT."; break;
        case -590: cTemp = "PARAMETER NAME  IS NOT UNIQUE IN THE CREATE FOR ROUTINE."; break;
        case -586: cTemp = "THE TOTAL LENGTH OF THE CURRENT PATH SPECIAL REGISTER CANNOT EXCEED 254 CHARACTERS."; break;
        case -585: cTemp = "THE SCHEMA NAME  CANNOT APPEAR MORE THAN ONCE IN THE CURRENT PATH."; break;
        case -582: cTemp = "THE SEARCH-CONDITION IN A SEARCHED-WHEN-CLAUSE CANNOT BE A QUANTIFIED PREDICATE, INPREDICATE, OR AN EXISTS PREDICATE."; break;
        case -581: cTemp = "THE DATA TYPES OF THE RESULT-EXPRESSIONS OF A CASE EXPRESSION ARE NOT COMPATIBLE."; break;
        case -580: cTemp = "THE RESULT-EXPRESSIONS OF A CASE EXPRESSION CANNOT ALL BE NULL."; break;
        case -579: cTemp = "ATTEMPTED TO READ DATA WHEN THE DEFINITION OF THE FUNCTION OR PROCEDURE DID NOTSPECIFY THIS ACTION."; break;
        case -577: cTemp = "ATTEMPTED TO MODIFY DATA WHEN THE DEFINITION OF THE FUNCTION OR PROCEDURE DID NOTSPECIFY THIS ACTION."; break;
        case -574: cTemp = "THE SPECIFIED DEFAULT VALUE OR IDENTITY ATTRIBUTE VALUE CONFLICTS WITH THE DEFINITIONOF COLUMN."; break;
        case -573: cTemp = "TABLE  DOES NOT HAVE A UNIQUE KEY WITH THE SPECIFIED COLUMN NAMES."; break;
        case -571: cTemp = "THE STATEMENT WOULD RESULT IN A MULTIPLE SITE UPDATE."; break;
        case -567: cTemp = "AUTHORIZATION ERROR USING  AUTHORITY PACKAGE =  PRIVILEGE =."; break;
        case -559: cTemp = "ALL AUTHORIZATION FUNCTIONS HAVE BEEN DISABLED."; break;
        case -558: cTemp = "INVALID CLAUSE OR COMBINATION OF CLAUSES ON A GRANT OR REVOKE."; break;
        case -557: cTemp = "INCONSISTENT GRANT/REVOKE KEYWORD .  PERMITTED KEYWORDS ARE."; break;
        case -556: cTemp = "CANNOT HAVE THE  PRIVILEGE REVOKED BY  BECAUSE THE REVOKEE DOES NOT POSSESS THEPRIVILEGE OR THE REVOKER DID NOT MAKE THE GRANT."; break;
        case -555: cTemp = "AN AUTHORIZATION ID CANNOT REVOKE A PRIVILEGE FROM ITSELF."; break;
        case -554: cTemp = "AN AUTHORIZATION ID CANNOT GRANT A PRIVILEGE TO ITSELF."; break;
        case -553: cTemp = "SPECIFIED IS NOT ONE OF THE VALID AUTHORIZATION IDS."; break;
        case -552: cTemp = "DOES NOT HAVE THE PRIVILEGE TO PERFORM OPERATION."; break;
        case -551: cTemp = "DOES NOT HAVE THE PRIVILEGE TO PERFORM OPERATION  ON OBJECT."; break;
        case -549: cTemp = "THE  STATEMENT IS NOT ALLOWED FOR   BECAUSE THE BIND OPTION DYNAMICRULES(RUN) IS NOTIN EFFECT FOR."; break;
        case -548: cTemp = "A CHECK CONSTRAINT THAT IS DEFINED WITH  IS INVALID."; break;
        case -546: cTemp = "THE CHECK CONSTRAINT  IS INVALID."; break;
        case -545: cTemp = "THE REQUESTED OPERATION IS NOT ALLOWED BECAUSE A ROW DOES NOT SATISFY THE CHECKCONSTRAINT."; break;
        case -544: cTemp = "THE CHECK CONSTRAINT SPECIFIED IN THE ALTER TABLE STATEMENT CANNOT BE ADDED BECAUSE ANEXISTING ROW VIOLATES THE CHECK CONSTRAINT."; break;
        case -543: cTemp = "A ROW IN A PARENT TABLE CANNOT BE DELETED BECAUSE THE CHECK CONSTRAINT  RESTRICTS THEDELETION."; break;
        case -542: cTemp = "CANNOT BE A COLUMN OF A PRIMARY KEY, A UNIQUE CONSTRAINT, OR A PARENT KEY BECAUSE ITCAN CONTAIN NULL VALUES."; break;
        case -540: cTemp = "THE DEFINITION OF TABLE  IS INCOMPLETE BECAUSE IT LACKS A PRIMARY INDEX OR A REQUIREDUNIQUE INDEX."; break;
        case -539: cTemp = "TABLE  DOES NOT HAVE A PRIMARY KEY."; break;
        case -538: cTemp = "FOREIGN KEY  DOES NOT CONFORM TO THE DESCRIPTION OF A PARENT KEY OF TABLE."; break;
        case -537: cTemp = "THE PRIMARY KEY CLAUSE, A FOREIGN KEY CLAUSE, OR A UNIQUE CLAUSE IDENTIFIES COLUMNMORE THAN ONCE."; break;
        case -536: cTemp = "THE DELETE STATEMENT IS INVALID BECAUSE TABLE  CAN BE AFFECTED BY THE OPERATION."; break;
        case -534: cTemp = "THE PRIMARY KEY CANNOT BE UPDATED BECAUSE OF MULTIPLE-ROW UPDATE."; break;
        case -533: cTemp = "INVALID MULTIPLE-ROW INSERT."; break;
        case -531: cTemp = "PARENT KEY IN A PARENT ROW CANNOT BE UPDATED BECAUSE IT HAS ONE OR MORE DEPENDENT ROWSIN RELATIONSHIP."; break;
        case -526: cTemp = "THE REQUESTED OPERATION OR USAGE DOES NOT APPLY TO  TEMPORARY TABLE."; break;
        case -525: cTemp = "THE SQL STATEMENT CANNOT BE EXECUTED BECAUSE IT WAS IN ERROR AT BIND TIME FOR SECTION=  PACKAGE =  CONSISTENCY TOKEN = X."; break;
        case -519: cTemp = "THE PREPARE STATEMENT IDENTIFIES THE SELECT STATEMENT OF THE OPENED CURSOR."; break;
        case -518: cTemp = "THE EXECUTE STATEMENT DOES NOT IDENTIFY A VALID PREPARED STATEMENT."; break;
        case -517: cTemp = "CURSOR  CANNOT BE USED BECAUSE ITS STATEMENT NAME DOES NOT IDENTIFY A PREPARED SELECTSTATEMENT."; break;
        case -516: cTemp = "THE DESCRIBE FOR STATIC STATEMENT DOES NOT IDENTIFY A PREPARED STATEMENT."; break;
        case -514: cTemp = "THE CURSOR  IS NOT IN A PREPARED STATE."; break;
        case -513: cTemp = "THE ALIAS  MUST NOT BE DEFINED ON ANOTHER LOCAL OR REMOTE ALIAS."; break;
        case -512: cTemp = "STATEMENT REFERENCE TO REMOTE OBJECT IS INVALID."; break;
        case -511: cTemp = "THE FOR UPDATE CLAUSE CANNOT BE SPECIFIED BECAUSE THE TABLE DESIGNATED BY THE CURSORCANNOT BE MODIFIED."; break;
        case -510: cTemp = "THE TABLE DESIGNATED BY THE CURSOR OF THE UPDATE OR DELETE STATEMENT CANNOT BEMODIFIED."; break;
        case -509: cTemp = "THE TABLE IDENTIFIED IN THE UPDATE OR DELETE STATEMENT IS NOT THE SAME TABLEDESIGNATED BY THE CURSOR."; break;
        case -508: cTemp = "THE CURSOR IDENTIFIED IN THE UPDATE OR DELETE STATEMENT IS NOT POSITIONED ON A ROW."; break;
        case -507: cTemp = "THE CURSOR IDENTIFIED IN THE UPDATE OR DELETE STATEMENT IS NOT OPEN."; break;
        case -504: cTemp = "THE CURSOR NAME  IS NOT DEFINED."; break;
        case -503: cTemp = "A COLUMN CANNOT BE UPDATED BECAUSE IT IS NOT IDENTIFIED IN THE UPDATE CLAUSE OF THESELECT STATEMENT OF THE CURSOR."; break;
        case -502: cTemp = "THE CURSOR IDENTIFIED IN AN OPEN STATEMENT IS ALREADY OPEN."; break;
        case -501: cTemp = "THE CURSOR IDENTIFIED IN A FETCH OR CLOSE STATEMENT IS NOT OPEN."; break;
        case -500: cTemp = "THE IDENTIFIED CURSOR WAS CLOSED WHEN THE CONNECTION WAS DESTROYED."; break;
        case -499: cTemp = "CURSOR  HAS ALREADY BEEN ASSIGNED TO THIS OR ANOTHER RESULT SET FROM PROCEDURE."; break;
        case -497: cTemp = "THE MAXIMUM LIMIT OF INTERNAL IDENTIFIERS HAS BEEN EXCEEDED FOR DATABASE."; break;
        case -496: cTemp = "THE SQL STATEMENT CANNOT BE EXECUTED BECAUSE IT REFERENCES A RESULT SET THAT WAS NOTCREATED BY THE CURRENT SERVER."; break;
        case -495: cTemp = "ESTIMATED PROCESSOR COST OF  PROCESSOR SECONDS ( SERVICE UNITS) IN COST CATEGORYEXCEEDS A RESOURCE LIMIT ERROR THRESHOLD OF  SERVICE UNITS."; break;
        case -492: cTemp = "THE CREATE FUNCTION FOR  HAS A PROBLEM WITH PARAMETER NUMBER .  IT MAY INVOLVE AMISMATCH WITH A SOURCE FUNCTION."; break;
        case -491: cTemp = "CREATE STATEMENT FOR USER-DEFINED FUNCTION  MUST HAVE A RETURNS CLAUSE, AND EITHER THEEXTERNAL CLAUSE (WITH OTHER REQUIRED KEYWORDS) OR THE SOURCE CLAUSE."; break;
        case -490: cTemp = "NUMBER  DIRECTLY SPECIFIED IN AN SQL STATEMENT IS OUTSIDE THE RANGE OF ALLOWABLEVALUES IN THIS CONTEXT (, )."; break;
        case -487: cTemp = "ATTEMPTED TO EXECUTE AN SQL STATEMENT WHEN THE DEFINITION OF THE FUNCTION ORPROCEDURE DID NOT SPECIFY THIS ACTION."; break;
        case -483: cTemp = "IN CREATE FUNCTION FOR  STATEMENT, THE NUMBER OF PARAMETERS DOES NOT MATCH THE NUMBEROF PARAMETERS OF THE SOURCE FUNCTION."; break;
        case -482: cTemp = "THE PROCEDURE  RETURNED NO LOCATORS."; break;
        case -480: cTemp = "THE PROCEDURE  HAS NOT YET BEEN CALLED."; break;
        case -478: cTemp = "DROP OR REVOKE ON OBJECT TYPE  CANNOT BE PROCESSED BECAUSE OBJECT  OF TYPE  ISDEPENDENT ON IT."; break;
        case -476: cTemp = "REFERENCE TO FUNCTION  WAS NAMED WITHOUT A SIGNATURE, BUT THE FUNCTION IS NOT UNIQUEWITHIN ITS SCHEMA."; break;
        case -475: cTemp = "THE RESULT TYPE  OF THE SOURCE FUNCTION CANNOT BE CAST TO THE RETURNS TYPE  OF THEUSER-DEFINED FUNCTION."; break;
        case -473: cTemp = "A USER DEFINED DATA TYPE CANNOT BE CALLED THE SAME NAME AS A SYSTEM PREDEFINED TYPE(BUILT-IN TYPE)."; break;
        case -472: cTemp = "CURSOR  WAS LEFT OPEN BY EXTERNAL FUNCTION  (SPECIFIC NAME )."; break;
        case -471: cTemp = "INVOCATION OF FUNCTION OR PROCEDURE  FAILED DUE TO REASON."; break;
        case -470: cTemp = "SQL CALL STATEMENT SPECIFIED A NULL VALUE FOR INPUT PARAMETER, BUT THE STOREDPROCEDURE DOES NOT SUPPORT NULL VALUES."; break;
        case -469: cTemp = "SQL CALL STATEMENT MUST SPECIFY AN OUTPUT HOST VARIABLE FOR PARAMETER."; break;
        case -463: cTemp = "EXTERNAL FUNCTION  (SPECIFIC NAME ) HAS RETURNED AN INVALID SQLSTATE, WITH DIAGNOSTICTEXT."; break;
        case -461: cTemp = "A VALUE WITH DATA TYPE  CANNOT BE CAST TO TYPE."; break;
        case -458: cTemp = "IN A REFERENCE TO FUNCTION  BY SIGNATURE, A MATCHING FUNCTION COULD NOT BE FOUND."; break;
        case -457: cTemp = "A FUNCTION OR DISTINCT TYPE CANNOT BE CALLED  SINCE IT IS RESERVED FOR SYSTEM USE."; break;
        case -456: cTemp = "IN CREATE FUNCTION FOR, THE SPECIFIC NAME  ALREADY EXISTS IN THE SCHEMA."; break;
        case -455: cTemp = "IN CREATE FUNCTION FOR, THE SCHEMA NAME  PROVIDED FOR THE SPECIFIC NAME DOES NOTMATCH THE SCHEMA NAME  OF THE FUNCTION."; break;
        case -454: cTemp = "THE SIGNATURE PROVIDED IN THE CREATE FUNCTION STATEMENT FOR  MATCHES THE SIGNATURE OFSOME OTHER FUNCTION ALREADY EXISTING IN THE SCHEMA."; break;
        case -453: cTemp = "THERE IS A PROBLEM WITH THE RETURNS CLAUSE IN THE CREATE FUNCTION STATEMENT FOR."; break;
        case -451: cTemp = "THE  DEFINITION, IN THE CREATE FUNCTION FOR  CONTAINS DATA TYPE  WHICH IS NOTAPPROPRIATE FOR AN EXTERNAL FUNCTION WRITTEN IN THE GIVEN LANGUAGE."; break;
        case -450: cTemp = "USER-DEFINED FUNCTION OR STORED PROCEDURE, PARAMETER NUMBER, OVERLAYED STORAGEBEYOND ITS DECLARED LENGTH."; break;
        case -449: cTemp = "CREATE OR ALTER STATEMENT FOR FUNCTION OR PROCEDURE  CONTAINS AN INVALID FORMAT OF THEEXTERNAL NAME CLAUSE OR IS MISSING THE EXTERNAL NAME CLAUSE."; break;
        case -444: cTemp = "USER PROGRAM  COULD NOT BE FOUND."; break;
        case -443: cTemp = "EXTERNAL FUNCTION  (SPECIFIC NAME ) HAS RETURNED AN ERROR SQLSTATE WITH DIAGNOSTICTEXT."; break;
        case -441: cTemp = "INVALID USE OF DISTINCT OR ALL WITH SCALAR FUNCTION."; break;
        case -440: cTemp = "NO  BY THE NAME  HAVING COMPATIBLE ARGUMENTS WAS FOUND IN THE CURRENT PATH."; break;
        case -438: cTemp = "APPLICATION RAISED ERROR WITH DIAGNOSTIC TEXT."; break;
        case -435: cTemp = "AN INVALID SQLSTATE  IS SPECIFIED IN THE FUNCTION RAISE_ERROR OR IN A SIGNAL SQLSTATESTATEMENT."; break;
        case -433: cTemp = "VALUE  IS TOO LONG."; break;
        case -430: cTemp = "(SPECIFIC NAME ) HAS ABNORMALLY TERMINATED."; break;
        case -427: cTemp = "DYNAMIC ROLLBACK NOT VALID AT AN APPLICATION SERVER WHERE UPDATES ARE NOT ALLOWED."; break;
        case -426: cTemp = "DYNAMIC COMMIT NOT VALID AT AN APPLICATION SERVER WHERE UPDATES ARE NOT ALLOWED."; break;
        case -423: cTemp = "INVALID VALUE FOR LOCATOR IN POSITION."; break;
        case -421: cTemp = "THE OPERANDS OF A UNION OR UNION ALL DO NOT HAVE THE SAME NUMBER OF COLUMNS."; break;
        case -420: cTemp = "THE VALUE OF A CHARACTER STRING ARGUMENT WAS NOT ACCEPTABLE TO THE  FUNCTION."; break;
        case -419: cTemp = "A DECIMAL DIVIDE OPERATION IS INVALID BECAUSE THE RESULT WOULD HAVE A NEGATIVE SCALE."; break;
        case -418: cTemp = "A STATEMENT STRING TO BE PREPARED CONTAINS AN INVALID USE OF PARAMETER MARKERS."; break;
        case -417: cTemp = "A STATEMENT STRING TO BE PREPARED INCLUDES PARAMETER MARKERS AS THE OPERANDS OF THESAME OPERATOR."; break;
        case -416: cTemp = "AN OPERAND OF A UNION CONTAINS A LONG STRING COLUMN."; break;
        case -415: cTemp = "THE CORRESPONDING COLUMNS,  OF THE OPERANDS OF A UNION OR A UNION ALL DO NOT HAVECOMPARABLE COLUMN DESCRIPTIONS."; break;
        case -414: cTemp = "A LIKE PREDICATE IS INVALID BECAUSE THE FIRST OPERAND IS NOT A STRING."; break;
        case -413: cTemp = "OVERFLOW OCCURRED DURING NUMERIC DATA TYPE CONVERSION."; break;
        case -412: cTemp = "THE SELECT CLAUSE OF A SUBQUERY SPECIFIES MULTIPLE COLUMNS."; break;
        case -411: cTemp = "CURRENT SQLID CANNOT BE USED IN A STATEMENT THAT REFERENCES REMOTE OBJECTS."; break;
        case -410: cTemp = "THE FLOATING POINT LITERAL  CONTAINS MORE THAN 30 CHARACTERS."; break;
        case -409: cTemp = "INVALID OPERAND OF A COUNT FUNCTION."; break;
        case -408: cTemp = "THE VALUE IS NOT COMPATIBLE WITH THE DATA TYPE OF ITS TARGET."; break;
        case -407: cTemp = "AN UPDATE, INSERT, OR SET VALUE IS NULL, BUT THE OBJECT COLUMN  CANNOT CONTAIN NULLVALUES."; break;
        case -406: cTemp = "A CALCULATED OR DERIVED NUMERIC VALUE IS NOT WITHIN THE RANGE OF ITS OBJECT COLUMN."; break;
        case -405: cTemp = "THE NUMERIC LITERAL  CANNOT BE USED AS SPECIFIED BECAUSE IT IS OUT OF RANGE."; break;
        case -404: cTemp = "THE SQL STATEMENT SPECIFIES A STRING THAT IS TOO LONG."; break;
        case -402: cTemp = "AN ARITHMETIC FUNCTION OR OPERATOR  IS APPLIED TO CHARACTER OR DATETIME DATA."; break;
        case -401: cTemp = "THE OPERANDS OF AN ARITHMETIC OR COMPARISON OPERATION ARE NOT COMPARABLE."; break;
        case -400: cTemp = "THE CATALOG HAS THE MAXIMUM NUMBER OF USER DEFINED INDEXES."; break;
        case -399: cTemp = "ATTEMPTED TO INSERT AN INVALID VALUE INTO A ROWID COLUMN."; break;
        case -398: cTemp = "A LOCATOR WAS REQUESTED FOR HOST VARIABLE NUMBER  BUT THE VARIABLE IS NOT A LOB."; break;
        case -397: cTemp = "THE OPTION GENERATED IS SPECIFIED WITH A COLUMN THAT IS NOT A ROW ID OR DISTINCT TYPEBASED ON A ROW ID."; break;
        case -396: cTemp = "ATTEMPTED TO EXECUTE AN SQL STATEMENT DURING FINAL CALL PROCESSING."; break;
        case -392: cTemp = "SQLDA PROVIDED FOR CURSOR  HAS BEEN CHANGED FROM THE PREVIOUS FETCH."; break;
        case -390: cTemp = "THE FUNCTION NAME, SPECIFIC NAME, IS NOT VALID IN THE CONTEXT IN WHICH IT OCCURS."; break;
        case -373: cTemp = "DEFAULT CANNOT BE SPECIFIED FOR IDENTITY COLUMN."; break;
        case -372: cTemp = "ONLY ONE ROWID OR IDENTITY COLUMN IS ALLOWED IN A TABLE."; break;
        case -359: cTemp = "THE RANGE OF VALUES FOR THE IDENTITY COLUMN IS EXHAUSTED."; break;
        case -355: cTemp = "A LOB COLUMN IS TOO LARGE TO BE LOGGED."; break;
        case -352: cTemp = "AN UNSUPPORTED SQLTYPE WAS ENCOUNTERED IN POSITION  OF THE INPUT-LIST."; break;
        case -351: cTemp = "AN UNSUPPORTED SQLTYPE WAS ENCOUNTERED IN POSITION  OF THE SELECT-LIST."; break;
        case -350: cTemp = "INVALID SPECIFICATION OF A LARGE OBJECT COLUMN."; break;
        case -339: cTemp = "THE SQL STATEMENT CANNOT BE EXECUTED FROM AN ASCII BASED DRDA APPLICATION REQUESTOR TOA V2R2 DB2 SUBSYSTEM."; break;
        case -338: cTemp = "AN ON CLAUSE IS INVALID."; break;
        case -333: cTemp = "THE SUBTYPE OF A STRING VARIABLE IS NOT THE SAME AS THE SUBTYPE KNOWN AT BIND TIME ANDTHE DIFFERENCE CANNOT BE RESOLVED BY TRANSLATION."; break;
        case -332: cTemp = "SYSSTRINGS DOES NOT DEFINE A TRANSLATION FROM CCSID  TO."; break;
        case -331: cTemp = "A STRING CANNOT BE ASSIGNED TO A HOST VARIABLE BECAUSE IT CANNOT BE TRANSLATED.REASON, CHARACTER, POSITION."; break;
        case -330: cTemp = "A STRING CANNOT BE USED BECAUSE IT CANNOT BE TRANSLATED.  REASON, CHARACTER, HOSTVARIABLE."; break;
        case -327: cTemp = "THE ROW CANNOT BE INSERTED BECAUSE IT IS OUTSIDE THE BOUND OF THE PARTITION RANGE FORTHE LAST PARTITION."; break;
        case -314: cTemp = "THE STATEMENT CONTAINS AN AMBIGUOUS HOST VARIABLE REFERENCE."; break;
        case -313: cTemp = "THE NUMBER OF HOST VARIABLES SPECIFIED IS NOT EQUAL TO THE NUMBER OF PARAMETER MARKERS."; break;
        case -312: cTemp = "IS AN UNDEFINED OR UNUSABLE HOST VARIABLE OR IS USED IN A DYNAMIC SQL STATEMENT OR ATRIGGER DEFINITION."; break;
        case -311: cTemp = "THE LENGTH OF INPUT HOST VARIABLE NUMBER  IS NEGATIVE OR GREATER THAN THE MAXIMUM."; break;
        case -310: cTemp = "DECIMAL HOST VARIABLE OR PARAMETER  CONTAINS NON-DECIMAL DATA."; break;
        case -309: cTemp = "A PREDICATE IS INVALID BECAUSE A REFERENCED HOST VARIABLE HAS THE NULL VALUE."; break;
        case -305: cTemp = "THE NULL VALUE CANNOT BE ASSIGNED TO OUTPUT HOST VARIABLE NUMBER  BECAUSE NO INDICATORVARIABLE IS SPECIFIED."; break;
        case -304: cTemp = "A VALUE WITH DATA TYPE  CANNOT BE ASSIGNED TO A HOST VARIABLE BECAUSE THE VALUE IS NOTWITHIN THE RANGE OF THE HOST VARIABLE IN POSITION  WITH DATA TYPE."; break;
        case -303: cTemp = "A VALUE CANNOT BE ASSIGNED TO OUTPUT HOST VARIABLE NUMBER  BECAUSE THE DATA TYPES ARENOT COMPARABLE."; break;
        case -302: cTemp = "THE VALUE OF INPUT VARIABLE OR PARAMETER NUMBER  IS INVALID OR TOO LARGE FOR THETARGET COLUMN OR THE TARGET VALUE."; break;
        case -301: cTemp = "THE VALUE OF INPUT HOST VARIABLE OR PARAMETER NUMBER  CANNOT BE USED AS SPECIFIEDBECAUSE OF ITS DATA TYPE."; break;
        case -300: cTemp = "THE STRING CONTAINED IN HOST VARIABLE OR PARAMETER  IS NOT NUL-TERMINATED."; break;
        case -251: cTemp = "TOKEN  IS NOT VALID."; break;
        case -250: cTemp = "THE LOCAL LOCATION NAME IS NOT DEFINED WHEN PROCESSING A THREE-PART OBJECT NAME."; break;
        case -240: cTemp = "THE PART CLAUSE OF A LOCK TABLE STATEMENT IS INVALID."; break;
        case -229: cTemp = "THE LOCALE  SPECIFIED IN A SET LOCALE OR OTHER STATEMENT THAT IS LOCALE SENSITIVE WASNOT FOUND."; break;
        case -221: cTemp = "SET OF OPTIONAL COLUMNS- IN EXPLANATION TABLE  IS INCOMPLETE.  OPTIONAL COLUMN  ISMISSING."; break;
        case -220: cTemp = "THE COLUMN  IN EXPLANATION TABLE  IS NOT DEFINED PROPERLY."; break;
        case -219: cTemp = "THE REQUIRED EXPLANATION TABLE  DOES NOT EXIST."; break;
        case -214: cTemp = "AN EXPRESSION STARTING WITH  IN THE  CLAUSE IS NOT VALID.  REASON CODE =."; break;
        case -212: cTemp = "IS SPECIFIED MORE THAN ONCE IN THE REFERENCING CLAUSE OF A TRIGGER DEFINITION."; break;
        case -208: cTemp = "THE ORDER BY CLAUSE IS INVALID BECAUSE COLUMN  IS NOT PART OF THE RESULT TABLE."; break;
        case -206: cTemp = "IS NOT A COLUMN OF AN INSERTED TABLE, UPDATED TABLE, OR ANY TABLE IDENTIFIED IN AFROM CLAUSE, OR IS NOT A COLUMN OF THE TRIGGERING TABLE OF A TRIGGER."; break;
        case -199: cTemp = "ILLEGAL USE OF KEYWORD, TOKEN  WAS EXPECTED."; break;
        case -198: cTemp = "THE OPERAND OF THE PREPARE OR EXECUTE IMMEDIATE STATEMENT IS BLANK OR EMPTY."; break;
        case -197: cTemp = "QUALIFIED COLUMN NAMES IN ORDER BY CLAUSE NOT PERMITTED WHEN UNION OR UNION ALLSPECIFIED."; break;
        case -191: cTemp = "A STRING CANNOT BE USED BECAUSE IT IS INVALID MIXED DATA."; break;
        case -190: cTemp = "ATTRIBUTES OF COLUMN  IN TABLE  ARE NOT COMPATIBLE WITH THE EXISTING COLUMN."; break;
        case -189: cTemp = "CCSID  IS UNKNOWN OR INVALID FOR THE DATA TYPE OR SUBTYPE."; break;
        case -188: cTemp = "THE STRING REPRESENTATION OF A NAME IS INVALID."; break;
        case -187: cTemp = "A REFERENCE TO A CURRENT DATE/TIME SPECIAL REGISTER IS INVALID BECAUSE THE MVS TODCLOCK IS BAD OR THE MVS PARMTZ IS OUT OF RANGE."; break;
        case -186: cTemp = "THE LOCAL DATE LENGTH OR LOCAL TIME LENGTH HAS BEEN INCREASED AND EXECUTING PROGRAMRELIES ON THE OLD LENGTH."; break;
        case -185: cTemp = "THE LOCAL FORMAT OPTION HAS BEEN USED WITH A DATE OR TIME AND NO LOCAL EXIT HAS BEENINSTALLED."; break;
        case -184: cTemp = "AN ARITHMETIC EXPRESSION WITH A DATETIME VALUE CONTAINS A PARAMETER MARKER."; break;
        case -183: cTemp = "AN ARITHMETIC OPERATION ON A DATE OR TIMESTAMP HAS A RESULT THAT IS NOT WITHIN THEVALID RANGE OF DATES."; break;
        case -182: cTemp = "AN ARITHMETIC EXPRESSION WITH A DATETIME VALUE IS INVALID."; break;
        case -181: cTemp = "THE STRING REPRESENTATION OF A DATETIME VALUE IS NOT A VALID DATETIME VALUE."; break;
        case -180: cTemp = "THE DATE, TIME, OR TIMESTAMP VALUE  IS INVALID."; break;
        case -173: cTemp = "UR IS SPECIFIED ON THE WITH CLAUSE BUT THE CURSOR IS NOT READ-ONLY."; break;
        case -171: cTemp = "THE DATA TYPE, LENGTH, OR VALUE OF ARGUMENT  OF  IS INVALID."; break;
        case -170: cTemp = "THE NUMBER OF ARGUMENTS SPECIFIED FOR  IS INVALID."; break;
        case -164: cTemp = "DOES NOT HAVE THE PRIVILEGE TO CREATE A VIEW WITH QUALIFICATION."; break;
        case -161: cTemp = "THE INSERT OR UPDATE IS NOT ALLOWED BECAUSE A RESULTING ROW DOES NOT SATISFY THE VIEWDEFINITION."; break;
        case -160: cTemp = "THE WITH CHECK OPTION CANNOT BE USED FOR THE SPECIFIED VIEW."; break;
        case -159: cTemp = "DROP OR COMMENT ON  IDENTIFIES A(N)  RATHER THAN A(N)."; break;
        case -158: cTemp = "THE NUMBER OF COLUMNS SPECIFIED FOR THE VIEW IS NOT THE SAME AS THE NUMBER OF COLUMNSSPECIFIED BY THE SELECT CLAUSE,OR THE NUMBER OF COLUMNS SPECIFIED IN THE CORRELATION CLAUSE IN A FROM CLAUSE IS NOT THE SAME AS THE NUMBER OFCOLUMNS IN THE CORRESPONDING TABLE, VIEW, TABLE EXPRESSION, OR TABLE FUNCTION."; break;
        case -157: cTemp = "ONLY A TABLE NAME CAN BE SPECIFIED IN A FOREIGN KEY CLAUSE.   IS NOT THE NAME OF ATABLE."; break;
        case -156: cTemp = "THE STATEMENT DOES NOT IDENTIFY A TABLE."; break;
        case -154: cTemp = "THE STATEMENT IS INVALID BECAUSE THE VIEW OR TABLE DEFINITION IS NOT VALID."; break;
        case -153: cTemp = "THE STATEMENT IS INVALID BECAUSE THE VIEW OR TABLE DEFINITION DOES NOT INCLUDE AUNIQUE NAME FOR EACH COLUMN."; break;
        case -152: cTemp = "THE DROP  CLAUSE IN THE ALTER STATEMENT IS INVALID BECAUSE  IS A."; break;
        case -151: cTemp = "THE UPDATE STATEMENT IS INVALID BECAUSE THE CATALOG DESCRIPTION OF COLUMN  INDICATESTHAT IT CANNOT BE UPDATED."; break;
        case -150: cTemp = "THE OBJECT OF THE INSERT, DELETE, OR UPDATE STATEMENT IS A VIEW OR TRANSITION TABLEFOR WHICH THE REQUESTED OPERATION IS NOT PERMITTED."; break;
        case -148: cTemp = "THE SOURCE TABLE  CANNOT BE RENAMED OR ALTERED."; break;
        case -147: cTemp = "ALTER FUNCTION  FAILED BECAUSE SOURCE FUNCTIONS CANNOT BE ALTERED."; break;
        case -144: cTemp = "INVALID SECTION NUMBER."; break;
        case -142: cTemp = "THE SQL STATEMENT IS NOT SUPPORTED."; break;
        case -138: cTemp = "THE SECOND OR THIRD ARGUMENT OF THE SUBSTR FUNCTION IS OUT OF RANGE."; break;
        case -137: cTemp = "THE LENGTH RESULTING FROM  IS GREATER THAN."; break;
        case -136: cTemp = "SORT CANNOT BE EXECUTED BECAUSE THE SORT KEY LENGTH IS GREATER THAN 4000 BYTES."; break;
        case -134: cTemp = "IMPROPER USE OF LONG STRING COLUMN  OR AN EXPRESSION OF MAXIMUM LENGTH GREATER THAN255."; break;
        case -133: cTemp = "A COLUMN FUNCTION IN A SUBQUERY OF A HAVING CLAUSE IS INVALID BECAUSE ALL COLUMNREFERENCES IN ITS ARGUMENT ARE NOT CORRELATED TO THE GROUP BY RESULT THAT THE HAVING CLAUSE IS APPLIED TO."; break;
        case -132: cTemp = "AN OPERAND OF  IS NOT VALID."; break;
        case -131: cTemp = "STATEMENT WITH LIKE PREDICATE HAS INCOMPATIBLE DATA TYPES."; break;
        case -130: cTemp = "THE ESCAPE CLAUSE CONSISTS OF MORE THAN ONE CHARACTER, OR THE STRING PATTERN CONTAINSAN INVALID OCCURRENCE OF THE ESCAPE CHARACTER."; break;
        case -129: cTemp = "THE STATEMENT CONTAINS TOO MANY TABLE NAMES."; break;
        case -128: cTemp = "INVALID USE OF NULL IN A PREDICATE."; break;
        case -127: cTemp = "DISTINCT IS SPECIFIED MORE THAN ONCE IN A SUBSELECT."; break;
        case -126: cTemp = "THE SELECT STATEMENT CONTAINS BOTH AN UPDATE CLAUSE AND AN ORDER BY CLAUSE."; break;
        case -125: cTemp = "AN INTEGER IN THE ORDER BY CLAUSE DOES NOT IDENTIFY A COLUMN OF THE RESULT."; break;
        case -123: cTemp = "THE PARAMETER IN POSITION  IN THE FUNCTION  MUST BE A CONSTANT OR KEYWORD."; break;
        case -122: cTemp = "A SELECT STATEMENT WITH NO GROUP BY CLAUSE CONTAINS A COLUMN NAME AND A COLUMNFUNCTION IN THE SELECT CLAUSE OR A COLUMN NAME IS CONTAINED IN THE SELECT CLAUSE BUT NOT IN THE GROUP BYCLAUSE."; break;
        case -121: cTemp = "THE COLUMN  IS IDENTIFIED MORE THAN ONCE IN THE INSERT OR UPDATE OR SET TRANSITIONVARIABLE STATEMENT."; break;
        case -120: cTemp = "A WHERE CLAUSE, SET CLAUSE, VALUES CLAUSE, OR A SET ASSIGNMENT STATEMENT INCLUDES ACOLUMN FUNCTION."; break;
        case -119: cTemp = "A COLUMN IDENTIFIED IN A HAVING CLAUSE IS NOT INCLUDED IN THE GROUP BY CLAUSE."; break;
        case -118: cTemp = "THE OBJECT TABLE OR VIEW OF THE DELETE OR UPDATE STATEMENT IS ALSO IDENTIFIED IN AFROM CLAUSE."; break;
        case -115: cTemp = "A PREDICATE IS INVALID BECAUSE THE COMPARISON OPERATOR  IS FOLLOWED BY A PARENTHESIZEDLIST OR BY ANY OR ALL WITHOUT A SUBQUERY."; break;
        case -114: cTemp = "THE LOCATION NAME  DOES NOT MATCH THE CURRENT SERVER."; break;
        case -113: cTemp = "INVALID CHARACTER FOUND IN: . REASON CODE."; break;
        case -112: cTemp = "THE OPERAND OF A COLUMN FUNCTION IS ANOTHER COLUMN FUNCTION."; break;
        case -111: cTemp = "A COLUMN FUNCTION DOES NOT INCLUDE A COLUMN NAME."; break;
        case -110: cTemp = "INVALID HEXADECIMAL LITERAL BEGINNING."; break;
        case -109: cTemp = "CLAUSE IS NOT PERMITTED."; break;
        case -108: cTemp = "THE NAME  IS QUALIFIED INCORRECTLY."; break;
        case -107: cTemp = "THE NAME  IS TOO LONG.  MAXIMUM ALLOWABLE SIZE IS."; break;
        case -105: cTemp = "INVALID STRING."; break;
        case -104: cTemp = "ILLEGAL SYMBOL --. SOME SYMBOLS THAT MIGHT BE LEGAL ARE."; break;
        case -103: cTemp = "IS AN INVALID NUMERIC LITERAL."; break;
        case -102: cTemp = "LITERAL STRING IS TOO LONG.  STRING BEGINS."; break;
        case -101: cTemp = "THE STATEMENT IS TOO LONG OR TOO COMPLEX."; break;
        case -97: cTemp  = "THE USE OF LONG VARCHAR OR LONG VARGRAPHIC IS NOT ALLOWED IN THIS CONTEXT."; break;
        case -84: cTemp  = "UNACCEPTABLE SQL STATEMENT."; break;
        case -79: cTemp  = "QUALIFIER FOR DECLARED GLOBAL TEMPORARY TABLE OR INDEX   MUST BE SESSION, NOT."; break;
        case -60: cTemp  = "INVALID  SPECIFICATION ."; break;
        case -29: cTemp  = "INTO CLAUSE REQUIRED."; break;
        case -10: cTemp  = "THE STRING CONSTANT BEGINNING  IS NOT TERMINATED."; break;
        case -7: cTemp   = "STATEMENT CONTAINS THE ILLEGAL CHARACTER."; break;
        default:
          cTemp = "No Catalogado";
      }
      cMensaje += "\\n\\n(" + ex.getErrorCode() + ")___" + cTemp + ".";
      cMensaje += "\\nSQLState=(" + ex.getSQLState() + ")";
      cMensaje += (ex.getCause() != null)?"\\nCausa: " + ex.getCause():"";
      i++;
    }while(ex.getNextException() != null && i<=10);
    return cMensaje;
  }

}
