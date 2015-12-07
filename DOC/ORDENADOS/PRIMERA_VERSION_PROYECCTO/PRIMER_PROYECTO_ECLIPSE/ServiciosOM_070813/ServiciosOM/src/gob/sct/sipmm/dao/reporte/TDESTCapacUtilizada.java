package gob.sct.sipmm.dao.reporte;
import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import java.util.Vector;
import com.micper.excepciones.DAOException;
import gob.sct.sipmm.dao.TDESTUsoAtraque;

public class TDESTCapacUtilizada extends DAOBase{
  private String cSistema = "44";
  private TParametro VParametros = new TParametro(cSistema);
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private TFechas tFecha = new TFechas(cSistema);
  TExcel rep = new TExcel();

  public TDESTCapacUtilizada(){
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
  public StringBuffer fReporteGen(String cFiltro) throws Exception{
        cError = "";
        StringBuffer sbRetorno = new StringBuffer("");
        String [] Filtro = cFiltro.split(",");
        TVDinRep vCapacidad = new TVDinRep();
        TVDinRep vTotalesCapacidad = new TVDinRep();
        int reng=12;

        try{
              rep.iniciaReporte();

              StringBuffer sbSQL1 = new StringBuffer();

              sbSQL1.append(" SELECT EF.CABREVIATURA, "); // 0
              sbSQL1.append(" P.CDSCPUERTO, ");
              sbSQL1.append(" OA.CNOMOBRAATRAQUE, ");
              sbSQL1.append(" PA.CNOMPOSICIONATRAQUE, ");
              sbSQL1.append(" CI.DCAPACIDADINSTALADA, ");
              sbSQL1.append(" A.dToneladasOperadas AS dcToneladasOperadas, "); // 5
              sbSQL1.append(" A.dCapacidadUtilizada, ");
              sbSQL1.append(" A.IEJERCICIOSEGUIMIENTO, ");
              sbSQL1.append(" A.INUMMOVTOSEGTO, ");
              sbSQL1.append(" A.IEJERCICIOPOA, ");
              sbSQL1.append(" A.ICVETITULO, "); // 10
              sbSQL1.append(" A.ICVETITULAR, ");
              sbSQL1.append(" A.ICVEBALANCEDSC, ");
              sbSQL1.append(" A.INUMINDICADORINS, ");
              sbSQL1.append(" A.INUMCAPACIDADATRAQUE ");
              sbSQL1.append(" FROM ESTUsoAtraque A ");
              sbSQL1.append(" JOIN ESTCAPACINSTALADA CI ON CI.IEJERCICIOPOA = A.IEJERCICIOPOA ");
              sbSQL1.append(" AND CI.ICVETITULO = A.ICVETITULO ");
              sbSQL1.append(" AND CI.ICVETITULAR = A.ICVETITULAR ");
              sbSQL1.append(" AND CI.ICVEBALANCEDSC = A.ICVEBALANCEDSC ");
              sbSQL1.append(" AND CI.INUMINDICADORINS = A.INUMINDICADORINS ");
              sbSQL1.append(" AND CI.INUMCAPACIDADATRAQUE = A.INUMCAPACIDADATRAQUE ");
              sbSQL1.append(" JOIN CPOOBRAATRAQUE OA ON OA.IEJERCICIO = CI.IEJERCICIO ");
              sbSQL1.append(" AND OA.ICVEPUERTO = CI.ICVEPUERTO ");
              sbSQL1.append(" AND OA.ICONSECUTIVOOA = CI.ICONSECUTIVOOA ");
              sbSQL1.append(" JOIN CPOPOSICIONATRAQUE PA ON PA.IEJERCICIO = CI.IEJERCICIO ");
              sbSQL1.append(" AND PA.ICVEPUERTO = CI.ICVEPUERTO ");
              sbSQL1.append(" AND PA.ICONSECUTIVOOA = CI.ICONSECUTIVOOA ");
              sbSQL1.append(" AND PA.INUMPOSICIONATRAQUE = CI.INUMPOSICIONATRAQUE ");
              sbSQL1.append(" JOIN GRLPUERTO P ON P.ICVEPUERTO = CI.ICVEPUERTO ");
              sbSQL1.append(" JOIN GRLENTIDADFED EF ON EF.ICVEPAIS = P.ICVEPAIS ");
              sbSQL1.append(" AND EF.ICVEENTIDADFED = P.ICVEENTIDADFED ");
              sbSQL1.append(" WHERE A.iEjercicioSeguimiento = " + Filtro[0]);
              sbSQL1.append(" AND A.iNumMovtoSegto = " + Filtro[1]);
              sbSQL1.append(" AND A.iEjercicioPOA  = " + Filtro[2]);
              sbSQL1.append(" AND A.iCveTitulo     = " + Filtro[3]);
              sbSQL1.append(" AND A.iCveTitular    = " + Filtro[4]);

              TDESTUsoAtraque dESTUsoAtraque = new TDESTUsoAtraque();

              Vector vcData = dESTUsoAtraque.findByCustom1("iEjercicioSeguimiento,iNumMovtoSegto,iEjercicioPOA,iCveTitulo,iCveTitular,iCveBalancedSC,iNumIndicadorIns,iNumCapacidadAtraque",sbSQL1.toString());

              // Encabezado de datos
              rep.comDespliegaAlineado("A",7,""+Filtro[7],true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("A",8,"Correspondiente a " + Filtro[8] + " del " + Filtro[5],true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());

              // Detalle de datos
              int j=0;
              for(;j<vcData.size();j++){
                 vCapacidad = (TVDinRep) vcData.get(j);

                 rep.comDespliegaAlineado("A",reng+j,vCapacidad.getString("CDSCPUERTO") + " - " + vCapacidad.getString("CNOMOBRAATRAQUE") + " - " + vCapacidad.getString("CNOMPOSICIONATRAQUE"),true,rep.getAT_HJUSTIFICA(),rep.getAT_VCENTRO());
                 rep.comDespliegaAlineado("B",reng+j,vCapacidad.getString("DCAPACIDADINSTALADA"),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
                 rep.comDespliegaAlineado("C",reng+j,vCapacidad.getString("dcToneladasOperadas"),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
                 rep.comDespliegaAlineado("D",reng+j,vCapacidad.getString("dCapacidadUtilizada"),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
                 rep.comDespliegaAlineado("E",reng+j,vCapacidad.getString("cTipoCarga").toUpperCase().replaceAll("<BR>",""),true,rep.getAT_HJUSTIFICA(),rep.getAT_VCENTRO());
                 rep.comBordeTotal("A",reng+j,"E",reng+j,1);
              }

              // Si hubo datos en el detalle, poner también los datos del Pie
              if(vcData.size()>0){
                // Pie de datos - Totales
                StringBuffer sbSQL2 = new StringBuffer();

                sbSQL2.append(" SELECT DTOTALCAPACIDADINSTALADA AS DCTOTALCAPACIDADINSTALADA, ");
                sbSQL2.append(" DTOTALTONELADASOPERADAS AS DCTOTALTONELADASOPERADAS, ");
                sbSQL2.append(" DTOTALCAPACIDADUTILIZADA AS DCTOTALCAPACIDADUTILIZADA ");
                sbSQL2.append(" FROM ESTCAPACUTILTOTAL WHERE IEJERCICIOSEGUIMIENTO = " + Filtro[0]);
                sbSQL2.append(" AND INUMMOVTOSEGTO = " + Filtro[1]);

                Vector vcTotales = findByCustom("",sbSQL2.toString());

                if(vcTotales.size()>0){
                  vTotalesCapacidad = (TVDinRep) vcTotales.get(0);
                  rep.comDespliegaAlineado("A",reng+j+1,"TOTAL",true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                  rep.comDespliegaAlineado("B",reng+j+1,vTotalesCapacidad.getString("DCTOTALCAPACIDADINSTALADA"),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
                  rep.comDespliegaAlineado("C",reng+j+1,vTotalesCapacidad.getString("DCTOTALTONELADASOPERADAS"),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
                  rep.comDespliegaAlineado("D",reng+j+1,vTotalesCapacidad.getString("DCTOTALCAPACIDADUTILIZADA"),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
                  rep.comBordeTotal("A",reng+j+1,"D",reng+j+1,1);
                } // if(vcTotales.size()>0)
              } // if(vcData.size()>0)

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
