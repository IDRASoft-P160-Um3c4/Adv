package gob.sct.sipmm.dao.reporte;
import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.sql.*;
import java.util.*;
import java.sql.SQLException;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.excepciones.DAOException;
import java.sql.Date;
import gob.sct.sipmm.dao.reporte.TDGeneral;

/**
 * <p>Title: TDOficiosConcesiones.java</p>
 * <p>Description: DAO de oficios generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LSC. Arturo López Peña
 * @version 1.0
 */

public class TDInsCodigoPBIP extends DAOBase{
   private String cSistema = "44";
   private TParametro VParametros = new TParametro(cSistema);
   private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
   TExcel rep = new TExcel();
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

  public StringBuffer fCodigoPBIP(String iConsecutivoCertExp) throws Exception{
      cError = "";
      TFechas dFechas = new TFechas();
      StringBuffer sbRetorno = new StringBuffer("");


        try{

//Obras de Protección
          rep.comEligeHoja(1);
            rep.iniciaReporte();
 String[] aParam = iConsecutivoCertExp.split(",");
 Vector vcData = findByCustom("",
                              "SELECT INUMCERTIFICADO,PER.CNOMRAZONSOCIAL ||' '|| PER.CAPPATERNO||' '|| PER.CAPMATERNO AS CSOLICITANTE, CER.DTINICIOVIGENCIA, " +
                           "CER.DTFINVIGENCIA, DOM.CCALLE || '  #'|| DOM.CNUMEXTERIOR || '  COL. ' || DOM.CCOLONIA ||', '|| MUN.CNOMBRE AS CDIRECCION, " +
                           "EMB.CNOMEMBARCACION, EMB.DARQUEOBRUTO, TE.CDSCTIPOEMBARCACION, EMB.CNUMOMI " +
                           "FROM INSCERTIFICADOSPBIP CER " +
                           "JOIN TRAREGSOLICITUD RS ON CER.IEJERCICIO = RS.IEJERCICIO AND CER.INUMSOLICITUD = RS.INUMSOLICITUD " +
                           "JOIN GRLPERSONA PER ON RS.ICVESOLICITANTE = PER.ICVEPERSONA " +
                           "JOIN GRLDOMICILIO DOM ON DOM.ICVEDOMICILIO = RS.ICVEDOMICILIOSOLICITANTE AND DOM.ICVEPERSONA = RS.ICVESOLICITANTE " +
                           "JOIN GRLPAIS PAIS ON PAIS.ICVEPAIS = DOM.ICVEPAIS " +
                           "JOIN GRLENTIDADFED EF ON EF.ICVEENTIDADFED = DOM.ICVEENTIDADFED AND EF.ICVEPAIS = DOM.ICVEPAIS " +
                           "JOIN GRLMUNICIPIO MUN ON MUN.ICVEPAIS = DOM.ICVEPAIS AND MUN.ICVEENTIDADFED = DOM.ICVEENTIDADFED AND MUN.ICVEMUNICIPIO = DOM.ICVEMUNICIPIO " +
                           "LEFT JOIN VEHEMBARCACION EMB ON EMB.ICVEVEHICULO = CER.ICVEVEHICULO " +
                           "LEFT JOIN VEHTIPOEMBARCACION TE ON EMB.ICVETIPOEMBARCACION = TE.ICVETIPOEMBARCACION " +
                           "LEFT JOIN VEHVEHICULO VEH ON VEH.ICVEVEHICULO = EMB.ICVEVEHICULO "+
                           "WHERE INUMCERTIFICADO = "+aParam[0]);

              TVDinRep vCertf = (TVDinRep) vcData.get(0);

              if(!vCertf.getString("INUMCERTIFICADO").equals("null")) rep.comDespliegaAlineado("b",1,vCertf.getString("INUMCERTIFICADO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("b",1,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              if(!vCertf.getString("CSOLICITANTE").equals("null")) rep.comDespliegaAlineado("b",2,vCertf.getString("CSOLICITANTE"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("b",2,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              if(!vCertf.getString("DTINICIOVIGENCIA").equals("null")) rep.comDespliegaAlineado("b",4,dFechas.getFechaDDMMMMMYYYY(vCertf.getDate("DTINICIOVIGENCIA")," de "),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("b",4,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              if(!vCertf.getString("CDIRECCION").equals("null")) rep.comDespliegaAlineado("b",3,vCertf.getString("CDIRECCION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("b",3,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              if(!vCertf.getDate("DTFINVIGENCIA").equals("null")) rep.comDespliegaAlineado("b",5,dFechas.getFechaDDMMMMMYYYY(vCertf.getDate("DTFINVIGENCIA")," de "),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("b",5,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              if(!vCertf.getString("CNOMEMBARCACION").equals("null")) rep.comDespliegaAlineado("b",6,vCertf.getString("CNOMEMBARCACION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("b",6,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              if(!vCertf.getString("DARQUEOBRUTO").equals("null")) rep.comDespliegaAlineado("b",7,vCertf.getString("DARQUEOBRUTO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("b",7,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              if(!vCertf.getString("CDSCTIPOEMBARCACION").equals("null")) rep.comDespliegaAlineado("b",8,vCertf.getString("CDSCTIPOEMBARCACION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("b",8,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              if(!vCertf.getString("CNUMOMI").equals("null")) rep.comDespliegaAlineado("b",9,vCertf.getString("CNUMOMI"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("b",9,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              sbRetorno = rep.getSbDatos();
        }catch(Exception e){
          e.printStackTrace();
          cMensaje += e.getMessage();
        }
      if(!cMensaje.equals(""))
        throw new Exception(cMensaje);
      return sbRetorno;
     }

}

