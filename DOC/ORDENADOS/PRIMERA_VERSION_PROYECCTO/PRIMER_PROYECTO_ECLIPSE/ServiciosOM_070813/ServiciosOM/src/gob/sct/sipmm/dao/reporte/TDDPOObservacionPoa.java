package gob.sct.sipmm.dao.reporte;
import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import java.util.Vector;
import gob.sct.sipmm.dao.*;
import java.sql.SQLException;
import com.micper.excepciones.DAOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * <p>Title: TDDPOObservacionPoa.java</p>
 * <p>Description: DAO con métodos para reportes de OBSERVACIONES POA</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ALopez
 * @author amendoza
 * @version 1.0
*
 */

public class TDDPOObservacionPoa extends DAOBase{
  private String cSistema = "44";
  private TParametro VParametros = new TParametro(cSistema);
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private TFechas tFecha = new TFechas(cSistema);

  TExcel rep = new TExcel();

  public TDDPOObservacionPoa(){
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
   }
   return vcRecords;

  }

    public StringBuffer fReporteEstatusPOAAnual(String iEjercicioSolicitud) throws Exception{
    	System.out.println("*************** fReporteEstatusPOAAnual");
        cError = "";
        TFechas dFechas = new TFechas();
        StringBuffer sbRetorno = new StringBuffer("");
          try{
              rep.iniciaReporte();
              String cSql = "SELECT p.CSOLICITANTE,p.DTOFICIOAPI,p.CNUMOFICIOAPI,DATE(s.TSREGISTRO) as DTREGISTROSOLICITUD,p.INUMSOLICITUD, " +
            		  "o.CAPARTADO,o.COBSERVACIONPOA,p.DTREGISTRO,p.CNUMOFICREG,o.CCOMENTARIOOBSPOA, " +
            		  "o.DTOBSERVACIONPOA, o.CNUMOFICIOOBS "+
            		  "FROM DPOOBSERVACIONPOA o " +
            		  "join DPOREGISTROPOA p on o.ICONSECUTIVOPOA = p.ICONSECUTIVOPOA " +
            		  "join TRAREGSOLICITUD s on p.IEJERCICIOSOL = s.IEJERCICIO and p.INUMSOLICITUD = s.INUMSOLICITUD " +
            		  "where p.IEJERCICIOSOL = "+iEjercicioSolicitud;
              
              Vector vcData = findByCustom("",cSql);

                if(vcData.size()>0){
                	rep.comDespliegaAlineado("A",1,iEjercicioSolicitud,false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                	
                	for (int i = 0; i < vcData.size(); i++) {
	                    TVDinRep vDinRep = (TVDinRep) vcData.get(i);
	                    
	                    if(vDinRep.getString("DTOFICIOAPI").equalsIgnoreCase("null")){
	                    	vDinRep.remove("DTOFICIOAPI");
	                    	vDinRep.put("DTOFICIOAPI", "");
	                    }
	                    if(vDinRep.getString("DTREGISTRO").equalsIgnoreCase("null")){
	                    	vDinRep.remove("DTREGISTRO");
	                    	vDinRep.put("DTREGISTRO", "");
	                    }
	                    if(vDinRep.getString("CNUMOFICREG").equalsIgnoreCase("null")){
	                    	vDinRep.remove("CNUMOFICREG");
	                    	vDinRep.put("CNUMOFICREG", "");
	                    }
	                    String cA = vDinRep.getString("CSOLICITANTE");
	                    String cB = vDinRep.getString("DTOFICIOAPI")+"     "+vDinRep.getString("CNUMOFICIOAPI");
	                    String cC = vDinRep.getString("DTREGISTROSOLICITUD")+"     "+vDinRep.getString("INUMSOLICITUD");
	                    String cD = vDinRep.getString("DTOBSERVACIONPOA")+"     "+vDinRep.getString("CNUMOFICIOOBS");
	                    String cE = vDinRep.getString("DTREGISTRO")+"     "+vDinRep.getString("CNUMOFICREG");
	                    String cF = vDinRep.getString("CCOMENTARIOOBSPOA");
	                    
	                    //Debe de empezar a escribir en A
	                    rep.comDespliegaAlineado("A",i+5,cA.toUpperCase(),false,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("B",i+5,cB.toUpperCase(), false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("C",i+5,cC.toUpperCase(), false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());          
	                    rep.comDespliegaAlineado("D",i+5,cD.toUpperCase(), false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
	                    rep.comDespliegaAlineado("E",i+5,cE.toUpperCase(), false, rep.getAT_HCENTRO(),rep.getAT_VCENTRO());          
	                    rep.comDespliegaAlineado("F",i+5,cF.toUpperCase(), false, rep.getAT_HJUSTIFICA(),rep.getAT_VCENTRO());          
                	}

                  sbRetorno = rep.getSbDatos();
                }

          }catch(Exception e){
            e.printStackTrace();
            cMensaje += e.getMessage();
          }
        if(!cMensaje.equals(""))
          throw new Exception(cMensaje);
        return sbRetorno;
       }


public StringBuffer fReportGenCertificadoWord(String cQuery){
     Vector vcData = new Vector();
     TWord rep = new TWord();

     try{
    	 vcData = FindByGeneric("",
                      "Aqui va el querie ",dataSourceName);

     }catch(SQLException ex){
    	 cMensaje = ex.getMessage();
     }catch(Exception ex2){
    	 ex2.printStackTrace();
     }

     rep.iniciaReporte();

	 if (vcData.size() > 0){
		 TVDinRep vDatos = (TVDinRep) vcData.get(0);
		
		 if(!vDatos.getString("CNOMEMBARCACION").equals(""))
		   rep.comRemplaza("[NombreEmbarcacion]",vDatos.getString("CNOMEMBARCACION"));
		 else rep.comRemplaza("[NombreEmbarcacion]","_______________");
	
	}
 return rep.getEtiquetas(true);
}
}
