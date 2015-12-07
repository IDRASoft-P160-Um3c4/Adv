package gob.sct.sipmm.dao.reporte;
import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import java.util.Vector;
import com.micper.excepciones.DAOException;

public class TDESTComSocial extends DAOBase{
  private String cSistema = "44";
  private TParametro VParametros = new TParametro(cSistema);
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private TFechas tFecha = new TFechas(cSistema);
  TExcel rep = new TExcel();

  public TDESTComSocial(){
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
      int count=0;
      TVDinRep vComSocial = new TVDinRep();
      TVDinRep vConcepto = new TVDinRep();
      int reng=12;

        try{
            rep.iniciaReporte();

              String cSQL =
                  "SELECT F.ICVETITULO,F.ICVETITULAR,F.ICVEFORMULARIO,F.LVIGENTE,P.CNOMRAZONSOCIAL||' '|| P.CAPPATERNO||' '|| P.CAPMATERNO AS CNOMBRE, " +
                  "CS.DPRESUPUESTOAUTORIZADOANUAL,CS.DPRESUPUESTOPROGRAMADOALMES, " +
                  "CS.DEJERCIDOENELMES,CS.DEJERCIDOALMES AS cEJERCIDOALMES,CS.DPORCENTAJEEJERCIDOALMES as cPorcentajeEjercidoAlMes, " +
                  "CS.DPOREJERCER as cPorEjercer,CS.DPORCENTAJEPOREJERCER as cPORCENTAJEPOREJERCER,CS.IEJERCICIOSEGUIMIENTO,CS.INUMMOVTOSEGTO, " +
                  "SF.dtLimitePresentacion,SF.dtEnvioDGFYDP, " +
                  "U.ICVEPUERTO, E.CABREVIATURA, EF.CABREVIATURA AS CEFED " +
                  "FROM ESTFORMXCONCESION F " +
                  "JOIN CPATITULOUBICACION U ON U.ICVETITULO = F.ICVETITULO " +
                  "JOIN GRLPERSONA P ON P.ICVEPERSONA = F.ICVETITULAR " +
                  "LEFT JOIN GRLENTIDADFED E ON U.ICVEPAIS = E.ICVEPAIS AND E.ICVEENTIDADFED = U.ICVEENTIDADFED " +
                  "LEFT JOIN GRLPUERTO PTO ON PTO.ICVEPUERTO = U.ICVEPUERTO " +
                  "LEFT JOIN GRLENTIDADFED EF ON PTO.ICVEPAIS = EF.ICVEPAIS AND EF.ICVEENTIDADFED = PTO.ICVEENTIDADFED " +
                  "LEFT JOIN ESTSEGTOFORMULARIO SF ON SF.ICVETITULO = F.ICVETITULO AND SF.ICVETITULAR = F.ICVETITULAR " +
                  "AND SF.ICVEFORMULARIO= F.ICVEFORMULARIO AND IEJERCICIOREPORTADO = " + Filtro[0]+
                  " AND IPERIODOREPORTADO = " + Filtro[1]+
                  " LEFT JOIN ESTCOMUNICASOCIAL CS ON CS.IEJERCICIOSEGUIMIENTO = SF.IEJERCICIOSEGUIMIENTO AND CS.INUMMOVTOSEGTO = SF.INUMMOVTOSEGTO " +
                  "WHERE F.ICVEFORMULARIO = 6 AND F.LVIGENTE = 1 ";

              Vector vcData = findByCustom("",cSQL);
              int j=0,ipe=0,ie=0;
              for(j=0;j<vcData.size();j++){
                vComSocial = (TVDinRep) vcData.get(j);
                if(vComSocial.getInt("ICVEPUERTO")==0){
                  cNombre = vComSocial.getString("CNOMBRE")+" - "+vComSocial.getString("CABREVIATURA");
                }
                else{
                  cNombre = vComSocial.getString("CNOMBRE")+" - "+vComSocial.getString("CEFED");
                }

                if((!cNombre.equals("")) && cNombre!=null)
                  rep.comDespliegaAlineado("A",reng+j,cNombre,true,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO());
                if(vComSocial.getDouble("DPRESUPUESTOAUTORIZADOANUAL")>0)
                  rep.comDespliegaAlineado("B",reng+j,""+vComSocial.getDouble("DPRESUPUESTOAUTORIZADOANUAL"),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
                if(vComSocial.getDouble("dPresupuestoProgramadoAlMes")>0)
                  rep.comDespliegaAlineado("C",reng+j,""+vComSocial.getDouble("dPresupuestoProgramadoAlMes"),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
                if(vComSocial.getDouble("DEJERCIDOENELMES")>0)
                  rep.comDespliegaAlineado("D",reng+j,""+vComSocial.getDouble("DEJERCIDOENELMES"),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
                if(vComSocial.getDouble("cEJERCIDOALMES")>0)
                  rep.comDespliegaAlineado("E",reng+j,""+vComSocial.getDouble("cEJERCIDOALMES"),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
                if(vComSocial.getDouble("cPorcentajeEjercidoAlMes")>0){
                  rep.comDespliegaAlineado("F",reng+j,""+vComSocial.getDouble("cPorcentajeEjercidoAlMes"),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
                  ie++;
                }
                if(vComSocial.getDouble("cPorEjercer")>0)
                  rep.comDespliegaAlineado("G",reng+j,""+vComSocial.getDouble("cPorEjercer"),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
                if(vComSocial.getDouble("cPORCENTAJEPOREJERCER")>0){
                  rep.comDespliegaAlineado("H",reng+j,""+vComSocial.getDouble("cPORCENTAJEPOREJERCER"),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
                  ipe++;
                }

                rep.comBordeTotal("A",reng+j,"H",reng+j,1);
              }

              rep.comDespliegaAlineado("A",(reng+j+1),"TOTAL",true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("B",(reng+j+1),"=SUM(B"+reng+":B"+(reng+j)+")",true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("C",(reng+j+1),"=SUM(C"+reng+":C"+(reng+j)+")",true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("D",(reng+j+1),"=SUM(D"+reng+":D"+(reng+j)+")",true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("E",(reng+j+1),"=SUM(E"+reng+":E"+(reng+j)+")",true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("F",(reng+j+1),"=(E"+(reng+j+1)+")/(B"+(reng+j+1)+")*100",true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("G",(reng+j+1),"=SUM(G"+reng+":G"+(reng+j)+")",true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("H",(reng+j+1),"=(G"+(reng+j+1)+")/(B"+(reng+j+1)+")*100",true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comBordeTotal("A",reng+j+1,"H",reng+j+1,1);


            //rep.comDespliegaAlineado("A",7,""+Filtro[2],true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
            rep.comDespliegaAlineado("A",8,"EJERCIDO EN COMUNICACIÓN SOCIAL AL MES DE " + Filtro[2] + " DEL " +Filtro[0],true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());

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
