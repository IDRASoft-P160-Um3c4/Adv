package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDMYREmpresa.java</p>
 * <p>Description: DAO de la entidad MYREmpresa</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ocastrejon
 * @version 1.0
 */
public class TDMYREmpresa extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  /**
   * Ejecuta cualquier query enviado en cWhere y regresa a las entidades encontradas.
   * @param cKey String    - Cadena de Campos que definen la llave de cada entidad encontrada.
   * @param cWhere String  - Cadena que contiene al query a ejecutar.
   * @throws DAOException  - Excepción de tipo DAO
   * @return Vector        - Arreglo que contiene a las entidades (VOs) encontrados por el query.
   */
  public Vector findByCustom(String cKey,String cWhere) throws DAOException{
    Vector vcRecords = new Vector();
    cError = "";
    try{
      String cSQL = cWhere;
      vcRecords = this.FindByGeneric(cKey,cSQL,dataSourceName);
    } catch(Exception e){
      cError = e.toString();
    } finally{
      if(!cError.equals(""))
        throw new DAOException(cError);
      return vcRecords;
    }
 }
  /**
   * Inserta el registro dado por la entidad vData.
   * <p><b> insert into MYREmpresa(iCveOficina,iFolioRPMN,iPartida,cRFC,iCvePersona,cCalle,cNumExterior,cNomRazonSocial,cNumInterior,cColonia,cCodPostal,iCveEntidadFed,iCveMunicipio,iCveLocalidad,iCvePais) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) </b></p>
   * <p><b> Campos Llave: iCveOficina,iFolioRPMN,iPartida, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep insert(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    TDMYRRPMN DMYRRPMN = new TDMYRRPMN();
    TVDinRep VMYRRPMN = new TVDinRep();
    int iFolioRPMN = 0;
    int iPartida = 0;

    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      //Obtencion del Folio del RPMN correspondiente.
      Vector vcMYRRPMN = DMYRRPMN.findByCustom("iCveOficina,iFolioRPMN,iPartida",
                         " select distinct(iFolioRPMN) as iFolioRPMN " +
                         " from MYRRPMN " +
                         " where iCvePersona = " + vData.getInt("iCvePersona"));

      if (vcMYRRPMN.size() > 0){
        VMYRRPMN = (TVDinRep) vcMYRRPMN.get(0);
        iFolioRPMN = VMYRRPMN.getInt("iFolioRPMN");
      } else {
        vcMYRRPMN = DMYRRPMN.findByCustom("iCveOficina,iFolioRPMN,iPartida",
                    " select max(iFolioRPMN) as iFolioRPMN " +
                    " from MYRRPMN " +
                    " where iCveOficina = " + vData.getInt("iCveOficina"));
        if (vcMYRRPMN.size() > 0){
          VMYRRPMN = (TVDinRep) vcMYRRPMN.get(0);
          iFolioRPMN = VMYRRPMN.getInt("iFolioRPMN");
          iFolioRPMN = iFolioRPMN + 1;
        } else {
          iFolioRPMN = 1;
        }
      }

      //Obtencion de la Partida que le toca.
      if (iFolioRPMN == 1){
        vcMYRRPMN = DMYRRPMN.findByCustom("iCveOficina,iFolioRPMN,iPartida",
                   " select max(iPartida) as iPartida " +
                   " from MYRRPMN " +
                   " where iCveOficina = " + vData.getInt("iCveOficina") +
                   " and iFolioRPMN    = " + iFolioRPMN);
        if (vcMYRRPMN.size() > 0){
          VMYRRPMN = (TVDinRep) vcMYRRPMN.get(0);
          iPartida = VMYRRPMN.getInt("iPartida");
          iPartida = iPartida + 1;
        } else
          iPartida = 1;
      } else
        iPartida = 1;

      //Insert en el RPMN.
      VMYRRPMN.put("iCveOficina",      vData.getInt("iCveOficina"));
      VMYRRPMN.put("iFolioRPMN",       iFolioRPMN);
      VMYRRPMN.put("iPartida",         iPartida);
      VMYRRPMN.put("iEjercicio",       vData.getInt("iEjercicio"));
      VMYRRPMN.put("iNumSolicitud",    vData.getInt("iNumSolicitud"));
      VMYRRPMN.put("dtRegistro",       vData.getDate("dtRegistro"));
      VMYRRPMN.put("iRamo",            vData.getInt("iRamo"));
      VMYRRPMN.put("cDscDocumento",    "");
      VMYRRPMN.put("iCvePersona",      vData.getInt("iCvePersona"));
      VMYRRPMN.put("cInscripcion",     "");
      VMYRRPMN.put("cSintesis",        vData.getString("cSintesis"));
      VMYRRPMN.put("iCveSeccion",      vData.getInt("iCveSeccion"));
      VMYRRPMN.put("iCveTipoAsiento",  vData.getInt("iCveTipoAsiento"));
      VMYRRPMN.put("cTitular",         vData.getString("cNombre"));
      VMYRRPMN.put("iCalificacion",    0);
      VMYRRPMN.put("cDscCalificacion", vData.getString("cDscCalificacion"));
      VMYRRPMN.put("iPartidaCancela",  0);
      VMYRRPMN = DMYRRPMN.insert(VMYRRPMN,conn);


      String lSQL = " insert into MYREmpresa("+
                    " iEjercicioIns,"+//1
                    " iCveOficina, " +
                    " iFolioRPMN, " +
                    " iPartida, " +
                    " cRFC, " +//5
                    " iCvePersona, " +
                    " cCalle, " +
                    " cNumExterior, " +
                    " cNomRazonSocial, " +
                    " cNumInterior, " +//10
                    " cColonia, " +
                    " cCodPostal, " +
                    " iCveEntidadFed, " +
                    " iCveMunicipio, " +
                    " iCveLocalidad, " +
                    " iCvePais ) " + //16
                    " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      vData.addPK(vData.getString("iCveOficina"));
      vData.addPK(String.valueOf(iFolioRPMN));
      vData.addPK(String.valueOf(iPartida));
      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setInt(1,vData.getInt("iEjercicio"));
      lPStmt.setInt(2,vData.getInt("iCveOficina"));
      lPStmt.setInt(3,iFolioRPMN);
      lPStmt.setInt(4,iPartida);
      lPStmt.setString(5,vData.getString("cRFC"));
      lPStmt.setInt(6,vData.getInt("iCvePersona"));
      lPStmt.setString(7,vData.getString("cCalle"));
      lPStmt.setString(8,vData.getString("cNumExterior"));
      lPStmt.setString(9,vData.getString("cNombre"));
      lPStmt.setString(10,vData.getString("cNumInterior"));
      lPStmt.setString(11,vData.getString("cColonia"));
      lPStmt.setString(12,vData.getString("cCodPostal"));
      lPStmt.setInt(13,vData.getInt("iCveEntidadFed"));
      lPStmt.setInt(14,vData.getInt("iCveMunicipio"));
      lPStmt.setInt(15,vData.getInt("iCveLocalidad"));
      lPStmt.setInt(16,vData.getInt("iCvePais"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      warn("insert",ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("insert.rollback",e);
        }
      }
      lSuccess = false;
    } finally{
      try{
        if(lPStmt != null){
          lPStmt.close();
        }
        if(cnNested == null){
          if(conn != null){
            conn.close();
          }
          dbConn.closeConnection();
        }
      } catch(Exception ex2){
        warn("insert.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException("");
      return vData;
    }
  }
  /**
   * Elimina al registro a través de la entidad dada por vData.
   * <p><b> delete from MYREmpresa where iCveOficina = ? AND iFolioRPMN = ? AND iPartida = ?  </b></p>
   * <p><b> Campos Llave: iCveOficina,iFolioRPMN,iPartida, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return boolean            - En caso de ser o no eliminado el registro.
   */
  public boolean delete(TVDinRep vData,Connection cnNested)throws
    DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    String cMsg = "";
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      //Ajustar Where de acuerdo a requerimientos...
      String lSQL = "delete from MYREmpresa where iCveOficina = ? AND iFolioRPMN = ? AND iPartida = ?  ";
      //...

      lPStmt = conn.prepareStatement(lSQL);

      lPStmt.setInt(1,vData.getInt("iCveOficina"));
      lPStmt.setInt(2,vData.getInt("iFolioRPMN"));
      lPStmt.setInt(3,vData.getInt("iPartida"));

      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(SQLException sqle){
      lSuccess = false;
      cMsg = ""+sqle.getErrorCode();
    } catch(Exception ex){
      warn("delete",ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("delete.rollback",e);
        }
      }
      lSuccess = false;
    } finally{
      try{
        if(lPStmt != null){
          lPStmt.close();
        }
        if(cnNested == null){
          if(conn != null){
            conn.close();
          }
          dbConn.closeConnection();
        }
      } catch(Exception ex2){
        warn("delete.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException(cMsg);
      return lSuccess;
    }
  }
  /**
   * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
   * <p><b> update MYREmpresa set cRFC=?, iCvePersona=?, cCalle=?, cNumExterior=?, cNomRazonSocial=?, cNumInterior=?, cColonia=?, cCodPostal=?, iCveEntidadFed=?, iCveMunicipio=?, iCveLocalidad=?, iCvePais=? where iCveOficina = ? AND iFolioRPMN = ? AND iPartida = ?  </b></p>
   * <p><b> Campos Llave: iCveOficina,iFolioRPMN,iPartida, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep update(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    TDMYRRPMN DMYRRPMN = new TDMYRRPMN();
    TVDinRep VMYRRPMN = new TVDinRep();

    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      VMYRRPMN.put("iCveOficina",      vData.getInt("iCveOficina"));
      VMYRRPMN.put("iFolioRPMN",       vData.getInt("iFolioRPMN"));
      VMYRRPMN.put("iPartida",         vData.getInt("iPartida"));
      VMYRRPMN.put("iEjercicio",       vData.getInt("iEjercicio"));
      VMYRRPMN.put("iNumSolicitud",    vData.getInt("iNumSolicitud"));
      VMYRRPMN.put("dtRegistro",       vData.getDate("dtRegistro"));
      VMYRRPMN.put("iRamo",            vData.getInt("iRamo"));
      VMYRRPMN.put("cDscDocumento",    "");
      VMYRRPMN.put("iCvePersona",      vData.getInt("iCvePersona"));
      VMYRRPMN.put("cInscripcion",     "");
      VMYRRPMN.put("cSintesis",        vData.getString("cSintesis"));
      VMYRRPMN.put("iCveSeccion",      vData.getInt("iCveSeccion"));
      VMYRRPMN.put("iCveTipoAsiento",  vData.getInt("iCveTipoAsiento"));
      VMYRRPMN.put("cTitular",         vData.getString("cNombre"));
      VMYRRPMN.put("iCalificacion",    0);
      VMYRRPMN.put("cDscCalificacion", vData.getString("cDscCalificacion"));
      VMYRRPMN.put("iPartidaCancela",  0);

      VMYRRPMN = DMYRRPMN.update(VMYRRPMN,null);

      String lSQL = " update MYREmpresa set cRFC=?, " +
                    " iCvePersona=?, " +
                    " cCalle=?, " +
                    " cNumExterior=?, " +
                    " cNomRazonSocial=?, " +
                    " cNumInterior=?, " +
                    " cColonia=?, " +
                    " cCodPostal=?, " +
                    " iCveEntidadFed=?, " +
                    " iCveMunicipio=?, " +
                    " iCveLocalidad=?, " +
                    " iCvePais=? " +
                    " where iEjercicioIns = ? " +
                    " AND iCveOficina = ? " +
                    " AND iFolioRPMN = ? " +
                    " AND iPartida = ? ";

      vData.addPK(vData.getString("iCveOficina"));
      vData.addPK(vData.getString("iFolioRPMN"));
      vData.addPK(vData.getString("iPartida"));

      lPStmt = conn.prepareStatement(lSQL);
      lPStmt.setString(1,vData.getString("cRFC"));
      lPStmt.setInt(2,vData.getInt("iCvePersona"));
      lPStmt.setString(3,vData.getString("cCalle"));
      lPStmt.setString(4,vData.getString("cNumExterior"));
      lPStmt.setString(5,vData.getString("cNomRazonSocial"));
      lPStmt.setString(6,vData.getString("cNumInterior"));
      lPStmt.setString(7,vData.getString("cColonia"));
      lPStmt.setString(8,vData.getString("cCodPostal"));
      lPStmt.setInt(9,vData.getInt("iCveEntidadFed"));
      lPStmt.setInt(10,vData.getInt("iCveMunicipio"));
      lPStmt.setInt(11,vData.getInt("iCveLocalidad"));
      lPStmt.setInt(12,vData.getInt("iCvePais"));
      lPStmt.setInt(13,vData.getInt("iEjercicioINS"));
      lPStmt.setInt(14,vData.getInt("iCveOficina"));
      lPStmt.setInt(15,vData.getInt("iFolioRPMN"));
      lPStmt.setInt(16,vData.getInt("iPartida"));
      lPStmt.executeUpdate();
      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      warn("update",ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("update.rollback",e);
        }
      }
      lSuccess = false;
    } finally{
      try{
        if(lPStmt != null){
          lPStmt.close();
        }
        if(cnNested == null){
          if(conn != null){
            conn.close();
          }
          dbConn.closeConnection();
        }
      } catch(Exception ex2){
        warn("update.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException("");

      return vData;
    }
 }


  /**
   * Actualiza al registro con las modificaciones enviadas sobre la entidad (vData).
   * <p><b> update MYREmpresa set cRFC=?, iCvePersona=?, cCalle=?, cNumExterior=?, cNomRazonSocial=?, cNumInterior=?, cColonia=?, cCodPostal=?, iCveEntidadFed=?, iCveMunicipio=?, iCveLocalidad=?, iCvePais=? where iCveOficina = ? AND iFolioRPMN = ? AND iPartida = ?  </b></p>
   * <p><b> Campos Llave: iCveOficina,iFolioRPMN,iPartida, </b></p>
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - Entidad Modificada.
   */
  public TVDinRep integra(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    TDMYRRPMN DMYRRPMN = new TDMYRRPMN();
    TVDinRep VMYRRPMN = new TVDinRep();

    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }
      String cSQL1 = "Select " +
      		         "  1 " +
      		         "FROM MYREMPRESA " +
      		         "WHERE ICVEPERSONA = "+vData.getInt("iCvePersonaD");
      String cSQL =  "SELECT " +
                     "	R.iCveOficina,E.cRFC,E.iCvePersona,E.CNOMRAZONSOCIAL AS cNombre,R.iEjercicio, " +
                     "	R.iNumSolicitud,R.dtRegistro,R.ICVERAMO AS iRamo,O.CDSCOBSLARGA AS cSintesis,R.iCveSeccion, " +
                     "	iCveTipoAsiento,cDscCalificacion,cCalle,cNumExterior,cNumInterior, " +
                     "	cColonia,cCodPostal,iCveEntidadFed,iCveMunicipio,iCveLocalidad, " +
                     "	iCvePais " +
                     "FROM MYRRPMN R " +
                     "JOIN MYREMPRESA E ON E.IEJERCICIOINS=R.IEJERCICIOINS AND E.ICVEOFICINA=R.ICVEOFICINA AND E.IFOLIORPMN=R.IFOLIORPMN AND E.IPARTIDA=R.IPARTIDA " +
                     "JOIN GRLOBSERVALARGA O ON R.IEJERCICIOOBSLARGA=O.IEJERCICIOOBSLARGA AND R.ICVEOBSLARGA=O.ICVEOBSLARGA " +
                     "where E.ICVEPERSONA = "+vData.getInt("iCvePersonaO");
      String cSQL2 = "SELECT " +
                     "	P.iCvePersona,P.cRFC,D.cCalle,D.cNumExterior,P.cNomRazonSocial, " +
                     "	D.cNumInterior,D.cColonia,D.cCodPostal,D.iCveEntidadFed,D.iCveMunicipio, " +
                     "	D.iCveLocalidad,D.iCvePais " +
                     "FROM GRLPERSONA P " +
                     "JOIN GRLDOMICILIO D ON D.ICVEPERSONA=P.ICVEPERSONA " +
                     "WHERE P.ICVEPERSONA = ";
      
      Vector vcRPMNDestino = this.findByNessted("", cSQL1, conn);
      Vector vcRPMNOrigen = this.findByNessted("", cSQL, conn);
      Vector vcRPMNPersona = this.findByNessted("", cSQL, conn);
      
      if(vcRPMNDestino.size()>0){
    	  if(vcRPMNOrigen.size()>0){
	    	  TVDinRep vRPMNDestino = (TVDinRep) vcRPMNDestino.get(0);
	    	  this.insert(vRPMNDestino, conn);
    	  }
      }else{
    	  if(vcRPMNPersona.size()>0){
	    	  TVDinRep vRPMNPersona = (TVDinRep) vcRPMNPersona.get(0);
	          String lSQL =   " update MYREmpresa set cRFC=?, " +
					          " iCvePersona=?, " +
					          " cCalle=?, " +
					          " cNumExterior=?, " +
					          " cNomRazonSocial=?, " +
					          " cNumInterior=?, " +
					          " cColonia=?, " +
					          " cCodPostal=?, " +
					          " iCveEntidadFed=?, " +
					          " iCveMunicipio=?, " +
					          " iCveLocalidad=?, " +
					          " iCvePais=? " +
					          " where iEjercicioIns = ?" +
					          " AND iCveOficina = ? " +
					          " AND iFolioRPMN = ? " +
					          " AND iPartida = ? ";
	          vData.addPK(vData.getString("iEjercicioIns"));
	          vData.addPK(vData.getString("iCveOficina"));
	          vData.addPK(vData.getString("iFolioRPMN"));
	          vData.addPK(vData.getString("iPartida"));
	          
	          lPStmt = conn.prepareStatement(lSQL);
	          lPStmt.setString(1,vRPMNPersona.getString("cRFC"));
	          lPStmt.setInt(2,vRPMNPersona.getInt("iCvePersona"));
	          lPStmt.setString(3,vRPMNPersona.getString("cCalle"));
	          lPStmt.setString(4,vRPMNPersona.getString("cNumExterior"));
	          lPStmt.setString(5,vRPMNPersona.getString("cNomRazonSocial"));
	          lPStmt.setString(6,vRPMNPersona.getString("cNumInterior"));
	          lPStmt.setString(7,vRPMNPersona.getString("cColonia"));
	          lPStmt.setString(8,vRPMNPersona.getString("cCodPostal"));
	          lPStmt.setInt(9,vRPMNPersona.getInt("iCveEntidadFed"));
	          lPStmt.setInt(10,vRPMNPersona.getInt("iCveMunicipio"));
	          lPStmt.setInt(11,vRPMNPersona.getInt("iCveLocalidad"));
	          lPStmt.setInt(12,vRPMNPersona.getInt("iCvePais"));
	          lPStmt.setInt(13,vData.getInt("iEjercicioIns"));
	          lPStmt.setInt(14,vData.getInt("iCveOficina"));
	          lPStmt.setInt(15,vData.getInt("iFolioRPMN"));
	          lPStmt.setInt(16,vData.getInt("iPartida"));
	          lPStmt.executeUpdate();
	      }

      }
      
      





      if(cnNested == null){
        conn.commit();
      }
    } catch(Exception ex){
      warn("update",ex);
      if(cnNested == null){
        try{
          conn.rollback();
        } catch(Exception e){
          fatal("update.rollback",e);
        }
      }
      lSuccess = false;
    } finally{
      try{
        if(lPStmt != null){
          lPStmt.close();
        }
        if(cnNested == null){
          if(conn != null){
            conn.close();
          }
          dbConn.closeConnection();
        }
      } catch(Exception ex2){
        warn("update.close",ex2);
      }
      if(lSuccess == false)
        throw new DAOException("");

      return vData;
    }
 }

	public TVDinRep updEmpresa(TVDinRep vData,Connection cnNested) throws
	  DAOException{
		DbConnection dbConn = null;
		Connection conn = cnNested;
		PreparedStatement lPStmt = null;
		boolean lSuccess = true;
		try{
			if(cnNested == null){
				dbConn = new DbConnection(dataSourceName);
				conn = dbConn.getConnection();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(2);
			}	
			String lSQL =
					" update MYREmpresa set " +
	                " iCvePersona=? " +
	                " where iEjercicioIns = ? " +
	                " AND iCveOficina = ? " +
	                " AND iFolioRPMN = ? " +
	                " AND iPartida = ? ";
			vData.addPK(vData.getString("iCveOficina"));
			vData.addPK(vData.getString("iFolioRPMN"));
			vData.addPK(vData.getString("iPartida"));
			
			lPStmt = conn.prepareStatement(lSQL);
			lPStmt.setInt(1,vData.getInt("iCvePersona"));
			lPStmt.setInt(2,vData.getInt("iEjercicioINS"));
			lPStmt.setInt(3,vData.getInt("iCveOficina"));
			lPStmt.setInt(4,vData.getInt("iFolioRPMN"));
			lPStmt.setInt(5,vData.getInt("iPartida"));
			lPStmt.executeUpdate();
			if(cnNested == null){
				conn.commit();
			}
		} catch(Exception ex){
			warn("update",ex);
			if(cnNested == null){
				try{
					conn.rollback();
				} catch(Exception e){
					fatal("update.rollback",e);
				}
			}
			lSuccess = false;
		} finally{
			try{
				if(lPStmt != null){
					lPStmt.close();
				}
				if(cnNested == null){
					if(conn != null){
						conn.close();
					}
					dbConn.closeConnection();
				}
			} catch(Exception ex2){
				warn("update.close",ex2);
			}
			if(lSuccess == false)
				throw new DAOException("");
			return vData;
		}
	}
  
}
