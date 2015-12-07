package gob.sct.sipmm.dao.reporte;
import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import java.util.Vector;
import com.micper.excepciones.DAOException;

public class TDESTOcupacionYArea extends DAOBase{
  private String cSistema = "44";
  private TParametro VParametros = new TParametro(cSistema);
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private TFechas tFecha = new TFechas(cSistema);
  TExcel rep = new TExcel();

  public TDESTOcupacionYArea(){
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
      TFechas dFechas = new TFechas();
      StringBuffer sbRetorno = new StringBuffer("");
      String [] Filtro = cFiltro.split(",");
      TVDinRep vOcupacion = new TVDinRep();
      TVDinRep vClasificaAreas = new TVDinRep();
      TVDinRep vAreas = new TVDinRep();
      TVDinRep vAreas1 = new TVDinRep();
      int Reng = 10;
        try{
            rep.iniciaReporte();
            String cSQL =
              "SELECT ICONSECUTIVOMUELLE,CNOMMUELLE,DPORCENTAJEOCUPACION "+
              "FROM ESTMUELLEOCUPACION "+
              "WHERE IEJERCICIOSEGUIMIENTO= "+Filtro[0]+
              " AND INUMMOVTOSEGTO= "+Filtro[1];
            rep.comDespliegaAlineado("A",7,""+Filtro[3],true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
            rep.comDespliegaAlineado("A",8,"Correspondiente a "+Filtro[4]+ " del "+Filtro[5],true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());

            Vector vcData = findByCustom("",cSQL);
            rep.comDespliegaCombinado("OCUPACIÓN DE MUELLES","A",Reng,"B",Reng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO(),true, 2, false,false,1,1);
            rep.comDespliegaCombinado("OCUPACIÓN DE ÁREAS DE ALMACENAMIENTO","D",Reng,"E",Reng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO(),true, 2, false,false,1,1);
            rep.comAltoReng("A",Reng,30);
            rep.comFillCells("A",Reng+1,"B",Reng+1,15);
            rep.comBordeTotal("A",Reng+1,"B",Reng+1,1);
            rep.comDespliegaAlineado("A",Reng+1,"TIPO DE MUELLE",true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
            rep.comDespliegaAlineado("B",Reng+1,"Tiempo Ocupado / Tiempo Disponible",true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
            for (int i=0;i<vcData.size();i++){
              vOcupacion = (TVDinRep) vcData.get(i);
              rep.comBordeTotal("A",Reng+2+i,"B",Reng+2+i,1);
              rep.comDespliegaAlineado("A",Reng+2+i,vOcupacion.getString("CNOMMUELLE"),true,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("B",Reng+2+i,vOcupacion.getString("DPORCENTAJEOCUPACION"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
            }
            String cClasificaAreas = "SELECT DISTINCT (ICVECLASIFICAAL) FROM ESTAREAOCUPACION WHERE  IEJERCICIOSEGUIMIENTO= "+Filtro[0]+" AND INUMMOVTOSEGTO= "+Filtro[1];
            Vector vcClasificaAreas = findByCustom("",cClasificaAreas);
            int renglon = Reng +1;
            for(int i=0;i<vcClasificaAreas.size();i++){
              vClasificaAreas = (TVDinRep) vcClasificaAreas.get(i);
              String cAreas = "SELECT AO.ICONSECUTIVOAREA, AO.CNOMAREA, AO.DPORCENTAJEUTILIZACION, AL.CDSCAL FROM ESTAREAOCUPACION AO " +
                              "JOIN CPOCLASIFICAAL AL ON AL.ICVECLASIFICAAL = AO.ICVECLASIFICAAL " +
                              "WHERE  IEJERCICIOSEGUIMIENTO="+Filtro[0]+
                              " AND INUMMOVTOSEGTO="+Filtro[1]+
                              " AND AL.ICVECLASIFICAAL = "+vClasificaAreas.getString("ICVECLASIFICAAL")+
                              " ORDER BY AO.ICVECLASIFICAAL ";
                          Vector vcAreas = findByCustom("",cAreas);
                          vAreas1 = (TVDinRep) vcAreas.get(0);
              rep.comBordeTotal("D",renglon,"E",renglon,1);
              rep.comDespliegaAlineado("D",renglon,"Tipo de "+vAreas1.getString("CDSCAL"),true,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("E",renglon,"M2 disponibles / M2 utilizados",true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());

              rep.comFillCells("D",renglon,"E",renglon,15);
              renglon++;
              for(int j=0;j<vcAreas.size();j++){
                vAreas = (TVDinRep) vcAreas.get(j);
                rep.comBordeTotal("D",renglon,"E",renglon,1);
                rep.comDespliegaAlineado("D",renglon,vAreas.getString("CNOMAREA"),true,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO());
                rep.comDespliegaAlineado("E",renglon,vAreas.getString("DPORCENTAJEUTILIZACION"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                renglon++;
              }
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
