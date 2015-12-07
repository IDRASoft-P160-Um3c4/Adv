package gob.sct.sipmm.dao.reporte;
import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import java.util.Vector;
import com.micper.excepciones.DAOException;

public class TDESTRendimCarga extends DAOBase{
  private String cSistema = "44";
  private TParametro VParametros = new TParametro(cSistema);
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private TFechas tFecha = new TFechas(cSistema);
  TExcel rep = new TExcel();

  public TDESTRendimCarga(){
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
      String cNombre="";
      int count=0,rengIni=0;
      TVDinRep vDetalle = new TVDinRep();
      TVDinRep vRendimiento = new TVDinRep();
      int reng=15;

        try{
            rep.iniciaReporte();

              String cSQL =
                  "SELECT " +
                  "       dpo.INUMDETALLEINDICADOR, " +
                  "       TC.CDSCTIPOCARGA, " +
                  "       STC.CDESCRIP_100 " +
                  "FROM DPODETALLEDPOA DPO " +
                  "  JOIN VEHTIPOCARGA TC ON DPO.ICVETIPOCARGA = TC.ICVETIPOCARGA LEFT " +
                  "  JOIN VEHSUBTIPOCARGA STC ON STC.ICVETIPOCARGA = DPO.ICVETIPOCARGA " +
                  "    AND STC.INUMSUBTIPOCARGA = DPO.INUMSUBTIPOCARGA " +
                  "  WHERE  IEJERCICIOPOA = " +Filtro[0]+
                  "    AND ICVETITULO = " +Filtro[1]+
                  "    AND ICVETITULAR = " +Filtro[2]+
                  "    AND ICVEBALANCEDSC = " +Filtro[3]+
                  "    AND INUMINDICADORINS = "+Filtro[4];
              rep.comDespliegaAlineado("A",7,Filtro[8],true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("A",8,"CORRESPONDIENTE AL MES DE "+Filtro[7]+" DEL "+Filtro[0],true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              Vector vcData = findByCustom("",cSQL);
              int j=0,ipe=0,ie=0,i=0;
              for(j=0;j<vcData.size();j++){
                vDetalle = (TVDinRep) vcData.get(j);
                rep.comDespliegaCombinado(vDetalle.getString("cDscTipoCarga")+ "  " +vDetalle.getString("CDESCRIP_100"),"A",reng+i,"E",reng+i,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,15,false,false,1,1);
//                rep.comDespliegaCombinado("cValor","ColIni",1,"ColFin",2,"cAlineacionH,","cAlineacionV","lNegritas","iColFondo",false,false,1,1);
                i++;
                rengIni = reng+i;
                String cRendimiento =
                    "SELECT " +
"       PR.ICVETIPORENDIMIENTO, " +
"       PR.DRENDIMIENTOPROGRAMADO, " +
"       TR.CDSCTIPORENDIMIENTO, " +
"       RR.DRENDIMIENTOREAL, " +
"       RR.DVARIACIONPORCENTUAL " +
"FROM ESTPROGRENDIMIENTO PR LEFT " +
"  JOIN ESTRENDIMIENTOREAL RR ON     RR.IEJERCICIOSEGUIMIENTO = " +Filtro[5]+
"    AND RR.INUMMOVTOSEGTO = " +Filtro[6]+
"    AND RR.IEJERCICIOPOA = PR.IEJERCICIOPOA " +
"    AND RR.ICVETITULO = PR.ICVETITULO " +
"    AND RR.ICVETITULAR = PR.ICVETITULAR " +
"    AND RR.ICVEBALANCEDSC = PR.ICVEBALANCEDSC " +
"    AND RR.INUMINDICADORINS = PR.INUMINDICADORINS " +
"    AND RR.INUMDETALLEINDICADOR = PR.INUMDETALLEINDICADOR " +
"    AND RR.ICVETIPORENDIMIENTO = PR.ICVETIPORENDIMIENTO " +
"  JOIN ESTTIPORENDIMIENTO TR ON TR.ICVETIPORENDIMIENTO = PR.ICVETIPORENDIMIENTO "+
                    "  WHERE  PR.IEJERCICIOPOA = " +Filtro[0]+
                    "    AND PR.ICVETITULO = " +Filtro[1]+
                    "    AND PR.ICVETITULAR = " +Filtro[2]+
                    "    AND PR.ICVEBALANCEDSC = " +Filtro[3]+
                    "    AND PR.INUMINDICADORINS = "+Filtro[4]+
                    "    AND PR.INUMDETALLEINDICADOR = "+vDetalle.getInt("INUMDETALLEINDICADOR");
                Vector vcRendimiento = findByCustom("",cRendimiento);
                for(int k=0;k<vcRendimiento.size();k++){
                  vRendimiento = (TVDinRep) vcRendimiento.get(k);
                  rep.comDespliegaAlineado("B",reng+i,vRendimiento.getString("CDSCTIPORENDIMIENTO"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO());
                  rep.comDespliegaAlineado("C",reng+i,""+vRendimiento.getDouble("DRENDIMIENTOREAL"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO());
                  rep.comDespliegaAlineado("D",reng+i,""+vRendimiento.getDouble("DRENDIMIENTOPROGRAMADO"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO());
                  rep.comDespliegaAlineado("E",reng+i,"'"+vRendimiento.getDouble("DVARIACIONPORCENTUAL")+" %",false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
                  i++;
                }
                rep.comBordeRededor("A",rengIni-1,"B",reng+i-1,1,0);
                rep.comBordeRededor("c",rengIni-1,"c",reng+i-1,1,0);
                rep.comBordeRededor("d",rengIni-1,"d",reng+i-1,1,0);
                rep.comBordeRededor("e",rengIni-1,"e",reng+i-1,1,0);
                //rep.comBordeTotal("A",reng+j,"H",reng+j,1);
              }

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
