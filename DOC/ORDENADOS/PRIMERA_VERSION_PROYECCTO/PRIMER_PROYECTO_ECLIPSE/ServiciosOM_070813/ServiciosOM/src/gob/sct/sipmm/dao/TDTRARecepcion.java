package gob.sct.sipmm.dao;
import java.sql.*; import java.util.*; import com.micper.excepciones.*;
import com.micper.ingsw.*; import com.micper.seguridad.vo.*; import com.micper.sql.*;
/**
 * <p>Title: TDGRLSegtoEntidad.java</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ICaballero
 * @version 1.0
 */
public class TDTRARecepcion extends DAOBase{
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
   * Inserta el/los registros dados por la entidad vData utilizando el método cambiarEtapa.
   * @param vData TVDinRep      - VO Dinámico que contiene a la entidad a Insertar.
   * @param cnNested Connection - Conexión anidada que permite que el método se encuentre dentro de una transacción mayor.
   * @throws DAOException       - Excepción de tipo DAO
   * @return TVDinRep           - VO Dinámico que contiene a la entidad a Insertada, así como a la llave de esta entidad.
   */
  public TVDinRep recepcionTramite(TVDinRep vData,Connection cnNested) throws
      DAOException{
    DbConnection dbConn = null;
    Connection conn = cnNested;
    PreparedStatement lPStmt = null;
    boolean lSuccess = true;
    String[] aEjercicio, aNumSolicitud, aCveTramite, aCveModalidad, alAnexo;
    TDTRARegEtapasXModTram dEtapas = new TDTRARegEtapasXModTram();
    Vector vOficDepto = new Vector();
    Vector vUser = new Vector();
    try{
      if(cnNested == null){
        dbConn = new DbConnection(dataSourceName);
        conn = dbConn.getConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(2);
      }

      aEjercicio    = vData.getString("iEjercicio").split(",");
      aNumSolicitud = vData.getString("iNumSolicitud").split(",");
      aCveTramite   = vData.getString("iCveTramite").split(",");
      aCveModalidad = vData.getString("iCveModalidad").split(",");
      alAnexo       = vData.getString("lAnexo").split(",");
      System.out.println("+++++++++++++iEtapaOficialia:"+vData.getInt("iEtapaOficialia")+
    		  "  +++++EtapaEntregarOficialia:"+VParametros.getPropEspecifica("EtapaEntregarOficialia"));
      if(vData.getInt("iEtapaOficialia") == Integer.parseInt(VParametros.getPropEspecifica("EtapaEntregarOficialia"))){
        for(int i = 0;i < aEjercicio.length;i++){
          String cOficDep = "SELECT " +
              "iCveOficinaEnviaRes " +
              "FROM TRARegSolicitud " +
              "where IEJERCICIO = " + aEjercicio[i] +
              " and INUMSOLICITUD = " + aNumSolicitud[i];

          vOficDepto = this.findByCustom("",cOficDep);
          TVDinRep vOficDep = (TVDinRep) vOficDepto.get(0);

          TVDinRep vCambiaEtapa = new TVDinRep();
          vCambiaEtapa.put("iEjercicio",Integer.parseInt(aEjercicio[i],10));
          vCambiaEtapa.put("iNumSolicitud",Integer.parseInt(aNumSolicitud[i],10));
          vCambiaEtapa.put("iCveTramite",Integer.parseInt(aCveTramite[i],10));
          vCambiaEtapa.put("iCveModalidad",Integer.parseInt(aCveModalidad[i],10));
          vCambiaEtapa.put("iCveEtapa",vData.getInt("iCveEtapa"));
          vCambiaEtapa.put("iCveOficina",vOficDep.getInt("iCveOficinaEnviaRes"));
          vCambiaEtapa.put("iCveDepartamento",VParametros.getPropEspecifica("DeptoVentanillaUnica")); // Cambia oficialia por vent unica
          vCambiaEtapa.put("iCveUsuario",vData.getInt("hdCveUsuario"));
          vCambiaEtapa.put("lAnexo",Integer.parseInt(alAnexo[i]));

          dEtapas.cambiarEtapa(vCambiaEtapa,false," ",conn);
        }
      }else if(vData.getInt("iCveEtapa") == Integer.parseInt(VParametros.getPropEspecifica("EtapaResEnviadaOficialia"))){
    	  System.out.println("+++++++++++++++  EtapaResEnviadaOficialia");
        for(int i = 0;i < aEjercicio.length;i++){
          String cOficDep = "SELECT " +
              "iCveOficinaEnviaRes " +
              "FROM TRARegSolicitud " +
              "where IEJERCICIO = " + aEjercicio[i] +
              " and INUMSOLICITUD = " + aNumSolicitud[i];

          vOficDepto = this.findByCustom("",cOficDep);
          TVDinRep vOficDep = (TVDinRep) vOficDepto.get(0);

          TVDinRep vCambiaEtapa = new TVDinRep();
          vCambiaEtapa.put("iEjercicio",Integer.parseInt(aEjercicio[i],10));
          vCambiaEtapa.put("iNumSolicitud",Integer.parseInt(aNumSolicitud[i],10));
          vCambiaEtapa.put("iCveTramite",Integer.parseInt(aCveTramite[i],10));
          vCambiaEtapa.put("iCveModalidad",Integer.parseInt(aCveModalidad[i],10));
          vCambiaEtapa.put("iCveEtapa",vData.getInt("iCveEtapa"));
          vCambiaEtapa.put("iCveOficina",vOficDep.getInt("iCveOficinaEnviaRes"));
          vCambiaEtapa.put("iCveDepartamento",VParametros.getPropEspecifica("DeptoVentanillaUnica"));
          vCambiaEtapa.put("iCveUsuario",vData.getInt("hdCveUsuario"));
          vCambiaEtapa.put("lAnexo",Integer.parseInt(alAnexo[i]));

          dEtapas.cambiarEtapa(vCambiaEtapa,false," ");
        }
      }else if(vData.getInt("iCveEtapa") == Integer.parseInt(VParametros.getPropEspecifica("EtapaEntregaResol"))){
    	  System.out.println("+++++++++++++++++ EtapaEntregaResol");
        for(int j = 0; j <2; j++){
          int iCveEtapa=0;
          int iCveDepto = 0;
          int iCveUser = 0;
          if(j == 0){
            iCveEtapa = Integer.parseInt(VParametros.getPropEspecifica("EtapaDocRetorno"));
            iCveDepto = vData.getInt("iCveDeptoUsr");
          } else if(j == 1){
            iCveEtapa = Integer.parseInt(VParametros.getPropEspecifica("EtapaConclusionTramite"));
            iCveDepto = vData.getInt("iCveDeptoUsr");

          }

          for(int i = 0;i < aEjercicio.length;i++){
            if(j==0){
              String cUser = " SELECT " +
                             " ICVEUSUARIO " +
                             "FROM TRAREGETAPASXMODTRAM " +
                             "where IEJERCICIO = " + aEjercicio[i] +
                             " and INUMSOLICITUD = " + aNumSolicitud[i] +
                             " and iCveEtapa = " +
                             VParametros.getPropEspecifica("EtapaRecibeResolucion");
              vUser = this.findByCustom("",cUser);
              TVDinRep vDatosUser = (TVDinRep) vUser.get(0);
              iCveUser = vDatosUser.getInt("ICVEUSUARIO");
            }else
              iCveUser = vData.getInt("hdCveUsuario");
            TVDinRep vCambiaEtapa = new TVDinRep();
            vCambiaEtapa.put("iEjercicio",Integer.parseInt(aEjercicio[i],10));
            vCambiaEtapa.put("iNumSolicitud",Integer.parseInt(aNumSolicitud[i],10));
            vCambiaEtapa.put("iCveTramite",Integer.parseInt(aCveTramite[i],10));
            vCambiaEtapa.put("iCveModalidad",Integer.parseInt(aCveModalidad[i],10));
            vCambiaEtapa.put("iCveEtapa",iCveEtapa);
            vCambiaEtapa.put("iCveOficina",vData.getInt("iCveOficinaUsr"));
            vCambiaEtapa.put("iCveDepartamento",iCveDepto);
            vCambiaEtapa.put("iCveUsuario",iCveUser);
            vCambiaEtapa.put("lAnexo",Integer.parseInt(alAnexo[i]));
            dEtapas.cambiarEtapa(vCambiaEtapa,false," ");
          }
        }
      }else if(vData.getInt("iCveEtapa") == Integer.parseInt(VParametros.getPropEspecifica("EtapaVerificaRequisito"))){
    	  System.out.println("++++++++++++++++++   EtapaVerificaRequisito");
        TDTRARegReqXTramb dTRARegReqXTra = new TDTRARegReqXTramb();
        for(int i = 0;i < aEjercicio.length;i++){
          TVDinRep vCambiaEtapa = new TVDinRep();
          vCambiaEtapa.put("iEjercicio",Integer.parseInt(aEjercicio[i],10));
          vCambiaEtapa.put("iNumSolicitud",Integer.parseInt(aNumSolicitud[i],10));
          vCambiaEtapa.put("iCveTramite",Integer.parseInt(aCveTramite[i],10));
          vCambiaEtapa.put("iCveModalidad",Integer.parseInt(aCveModalidad[i],10));
          vCambiaEtapa.put("iCveEtapa",vData.getInt("iCveEtapa"));
          vCambiaEtapa.put("iCveOficina",vData.getInt("iCveOficinaUsr"));
          vCambiaEtapa.put("iCveDepartamento",vData.getInt("iCveDeptoUsr"));
          vCambiaEtapa.put("iCveUsuario",vData.getInt("hdCveUsuario"));
          vCambiaEtapa.put("lAnexo",Integer.parseInt(alAnexo[i]));

          dEtapas.cambiarEtapa(vCambiaEtapa,false," ");

          StringBuffer sbActualizaTramite = new StringBuffer();
          sbActualizaTramite.append("SELECT ");
          sbActualizaTramite.append("iCveRequisito ");
          sbActualizaTramite.append("FROM TRAREGREQXTRAM ");
          sbActualizaTramite.append(" where iEjercicio = " + Integer.parseInt(aEjercicio[i],10));
          sbActualizaTramite.append(" and iNumSolicitud = " + Integer.parseInt(aNumSolicitud[i],10));
          sbActualizaTramite.append(" and ICVETRAMITE = " + Integer.parseInt(aCveTramite[i],10));
          sbActualizaTramite.append(" and ICVEMODALIDAD = " + Integer.parseInt(aCveModalidad[i],10));
          sbActualizaTramite.append(" and DTNOTIFICACION is not null ");
          Vector vDatosTra = findByCustom("",sbActualizaTramite.toString());
          String cRequisitos = "";
          for(int k = 0;k < vDatosTra.size();k++){
            TVDinRep vDatosTramite = (TVDinRep) vDatosTra.get(k);
            cRequisitos += (cRequisitos.equalsIgnoreCase("")) ?
                vDatosTramite.getString("iCveRequisito") :
                "," + vDatosTramite.getString("iCveRequisito");
          }
          vCambiaEtapa.put("cConjunto",cRequisitos);
          vCambiaEtapa.put("iCveUsuRecibe",vData.getInt("hdCveUsuario"));
        }

      }else if(vData.getInt("iCveEtapa") == Integer.parseInt(VParametros.getPropEspecifica("EtapaDocRetorno"))){
        System.out.println("*****    EtapaDocRetorno");
        int iCveEtapa = Integer.parseInt(VParametros.getPropEspecifica("EtapaConclusionTramite"));
        for(int i = 0;i < aEjercicio.length;i++){
          TVDinRep vCambiaEtapa = new TVDinRep();
          vCambiaEtapa.put("iEjercicio",Integer.parseInt(aEjercicio[i]));
          vCambiaEtapa.put("iNumSolicitud",Integer.parseInt(aNumSolicitud[i]));
          vCambiaEtapa.put("iCveTramite",Integer.parseInt(aCveTramite[i]));
          vCambiaEtapa.put("iCveModalidad",Integer.parseInt(aCveModalidad[i]));
          vCambiaEtapa.put("iCveEtapa",iCveEtapa);
          vCambiaEtapa.put("iCveOficina",vData.getInt("iCveOficinaUsr"));
          vCambiaEtapa.put("iCveDepartamento",vData.getInt("iCveDeptoUsr"));
          vCambiaEtapa.put("iCveUsuario",vData.getInt("hdCveUsuario"));
          vCambiaEtapa.put("lAnexo",Integer.parseInt(alAnexo[i]));

          dEtapas.cambiarEtapa(vCambiaEtapa,false," ");
        }
      }
      else{
        for(int i = 0;i < aEjercicio.length;i++){
          System.out.println("*****    ELSE     *****");
          TVDinRep vCambiaEtapa = new TVDinRep();
          vCambiaEtapa.put("iEjercicio",Integer.parseInt(aEjercicio[i],10));
          vCambiaEtapa.put("iNumSolicitud",Integer.parseInt(aNumSolicitud[i],10));
          vCambiaEtapa.put("iCveTramite",Integer.parseInt(aCveTramite[i],10));
          vCambiaEtapa.put("iCveModalidad",Integer.parseInt(aCveModalidad[i],10));
          vCambiaEtapa.put("iCveEtapa",vData.getInt("iCveEtapa"));
          vCambiaEtapa.put("iCveOficina",vData.getInt("iCveOficinaUsr"));
          if(vData.getInt("iCveEtapa") == Integer.parseInt(VParametros.getPropEspecifica("EtapaRegistro")))
            vCambiaEtapa.put("iCveDepartamento",vData.getInt("iCveDepartamentoAsg"));
          else
            vCambiaEtapa.put("iCveDepartamento",vData.getInt("iCveDeptoUsr"));
          vCambiaEtapa.put("iCveUsuario",vData.getInt("hdCveUsuario"));
          vCambiaEtapa.put("lAnexo",Integer.parseInt(alAnexo[i]));
          dEtapas.cambiarEtapa(vCambiaEtapa,false," ",true);
        }
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
        if(lPStmt != null)
          lPStmt.close();

        if(cnNested == null){
          if(conn != null)
            conn.close();

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
}
