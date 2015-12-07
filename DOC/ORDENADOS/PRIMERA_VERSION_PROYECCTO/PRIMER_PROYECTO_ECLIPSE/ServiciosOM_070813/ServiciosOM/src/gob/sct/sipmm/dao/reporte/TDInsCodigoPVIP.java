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

public class TDInsCodigoPVIP extends DAOBase{
   private String cSistema = "44";
   private TParametro VParametros = new TParametro(cSistema);
   private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
   private TFechas tFecha = new TFechas(cSistema);
//  private TDTramite tTramite = new TDTramite();
   TExcel rep = new TExcel();

   public TDInsCodigoPVIP(){
   }
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

  public StringBuffer fARecSolTram(String iConsecutivoCertExp) throws Exception{
      cError = "";
      TFechas dFechas = new TFechas();
      StringBuffer sbRetorno = new StringBuffer("");


        try{

//Obras de Protección
            rep.iniciaReporte();
 String[] aParam = iConsecutivoCertExp.split(",");
 Vector vcData = findByCustom("",
   "SELECT INUMCERTIFICADO,PER.CNOMRAZONSOCIAL ||' '|| PER.CAPPATERNO||' '|| PER.CAPMATERNO AS CSOLICITANTE, CER.DTINICIOVIGENCIA, " +
   "CER.DTFINVIGENCIA, DOM.CCALLE || '  #'|| DOM.CNUMEXTERIOR || '  COL. ' || DOM.CCOLONIA ||', '|| MUN.CNOMBRE AS CDIRECCION " +
   "FROM INSCERTIFICADOSPBIP CER " +
   "JOIN TRAREGSOLICITUD RS ON CER.IEJERCICIO = RS.IEJERCICIO AND CER.INUMSOLICITUD = RS.INUMSOLICITUD " +
   "JOIN GRLPERSONA PER ON RS.ICVESOLICITANTE = PER.ICVEPERSONA " +
   "JOIN GRLDOMICILIO DOM ON DOM.ICVEDOMICILIO = RS.ICVEDOMICILIOSOLICITANTE AND DOM.ICVEPERSONA = RS.ICVESOLICITANTE " +
   "JOIN GRLPAIS PAIS ON PAIS.ICVEPAIS = DOM.ICVEPAIS " +
   "JOIN GRLENTIDADFED EF ON EF.ICVEENTIDADFED = DOM.ICVEENTIDADFED AND EF.ICVEPAIS = DOM.ICVEPAIS " +
   "JOIN GRLMUNICIPIO MUN ON MUN.ICVEPAIS = DOM.ICVEPAIS AND MUN.ICVEENTIDADFED = DOM.ICVEENTIDADFED AND MUN.ICVEMUNICIPIO = DOM.ICVEMUNICIPIO "+
   aParam[0]);

              TVDinRep vCertf = (TVDinRep) vcData.get(0);

              if(!vCertf.getString("INUMCERTIFICADO").equals("null")) rep.comDespliegaAlineado("b",1,vCertf.getString("INUMCERTIFICADO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("b",1,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              if(!vCertf.getString("CSOLICITANTE").equals("null")) rep.comDespliegaAlineado("b",2,vCertf.getString("CSOLICITANTE"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("b",2,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              if(!vCertf.getString("DTINICIOVIGENCIA").equals("null")) rep.comDespliegaAlineado("b",3,dFechas.getFechaDDMMMMMYYYY(vCertf.getDate("DTINICIOVIGENCIA"),"/"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("b",3,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              if(!vCertf.getString("CDIRECCION").equals("null")) rep.comDespliegaAlineado("b",4,vCertf.getString("CDIRECCION"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("b",4,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              if(!vCertf.getDate("DTFINVIGENCIA").equals("null")) rep.comDespliegaAlineado("b",5,dFechas.getFechaDDMMMMMYYYY(vCertf.getDate("DTFINVIGENCIA"),"/"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("b",5,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              //if(!vCertf.getString("INUMCERTIFICADO").equals("null")) rep.comDespliegaAlineado("b",1,vCertf.getString("INUMCERTIFICADO"),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());else rep.comDespliegaAlineado("b",1,"--",false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
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

