package gob.sct.sipmm.dao.reporte;
import gob.sct.sipmm.dao.*;
import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import java.util.Vector;
import com.micper.excepciones.DAOException;


public class TDESTPlanFin extends DAOBase{
  private String cSistema = "44";
  private TParametro VParametros = new TParametro(cSistema);
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private TFechas tFecha = new TFechas(cSistema);
  TExcel rep = new TExcel();

  public TDESTPlanFin(){
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
      double[] V  = new double[5];
      double[] V1 = new double[5];
      double[] V2 = new double[5];
      double[] V3 = new double[5];
      double[] V4 = new double[5];
      double[] T1 = new double[5];
      double[] T2 = new double[5];
      double[] T3 = new double[5];
      double[] T4 = new double[5];
      double[] T5 = new double[5];
      int[] D = new int[5];
      //FGAccion oAccion = new CFGAccion(pageContext.getRequest());
      int rengIni=13;
      String ColIni="";
      TVDinRep vDinRep = new TVDinRep();
      vDinRep.put("iEjercicioSeguimiento",Filtro[0]);
      vDinRep.put("iNumMovToSegto",Filtro[1]);
      TDESTPlanFinanciera PF = new TDESTPlanFinanciera();
      Vector vPlanFin = PF.regresaArbol(vDinRep);
      rep.iniciaReporte();
      int i = 0;
      int tipoAnte=1;
      for (int cons = 0; cons<vPlanFin.size();cons++){
        TVDinRep vPlan = (TVDinRep) vPlanFin.get(cons);
        if(vPlan.getInt("ICVETIPOMOVCONTABLE")>tipoAnte){
          switch (tipoAnte){
            case 1:
              rep.comDespliegaCombinado("Suma de Ingresos","A",rengIni+i,"E",rengIni+i,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,0,true,false,1,0);
              rep.comDespliegaAlineado("F",rengIni+i,""+T1[1],true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("G",rengIni+i,""+T2[1],true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("H",rengIni+i,""+T3[1],true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("I",rengIni+i,""+T4[1],true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("J",rengIni+i,""+T5[1],true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comBordeTotal("A",rengIni+i,"J",rengIni+i,1);
              break;
            case 2:
              rep.comDespliegaCombinado("Total de Costos","A",rengIni+i,"E",rengIni+i,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,0,true,false,1,0);
              rep.comDespliegaAlineado("F",rengIni+i,""+T1[2],true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("G",rengIni+i,""+T2[2],true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("H",rengIni+i,""+T3[2],true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("I",rengIni+i,""+T4[2],true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("J",rengIni+i,""+T5[2],true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comBordeTotal("A",rengIni+i,"J",rengIni+i,1);
              i++;
              rep.comDespliegaCombinado("Utilidad Bruta","A",rengIni+i,"E",rengIni+i,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,0,true,false,1,0);
              rep.comDespliegaAlineado("F",rengIni+i,""+(-T1[2]+T1[1]),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("G",rengIni+i,""+(-T2[2]+T2[1]),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("H",rengIni+i,""+(-T3[2]+T3[1]),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("I",rengIni+i,""+(-T4[2]+T4[1]),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("J",rengIni+i,""+(-T5[2]+T5[1]),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comBordeTotal("A",rengIni+i,"J",rengIni+i,1);
              break;
            case 3:
              rep.comDespliegaCombinado("Resultado Antes de Impuestos","A",rengIni+i,"E",rengIni+i,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,0,true,false,1,0);
              rep.comDespliegaAlineado("F",rengIni+i,""+(T1[1]-T1[2]-T1[3]),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("G",rengIni+i,""+(T2[1]-T2[2]-T2[3]),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("H",rengIni+i,""+(T3[1]-T3[2]-T3[3]),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("I",rengIni+i,""+(T4[1]-T4[2]-T4[3]),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("J",rengIni+i,""+(T5[1]-T5[2]-T5[3]),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comBordeTotal("A",rengIni+i,"J",rengIni+i,1);
              break;
          }
          i++;
          tipoAnte = vPlan.getInt("ICVETIPOMOVCONTABLE");
        }
        switch (vPlan.getInt("iNivel")){
          case 1:
            ColIni = "A";
            D[0]=rengIni+i;
            for(int j=0;j<5;j++){V[j]=0;V1[j]=0;V2[j]=0;V3[j]=0;V4[j]=0;}
            break;
          case 2:
            ColIni = "B";
            for(int j=0;j<2;j++){
              D[1]=rengIni+i;
              V[j] += vPlan.getDouble("DPROGRAMADOACUMULADO");
              V1[j] += vPlan.getDouble("DREALACUMULADO");
              V2[j] += vPlan.getDouble("DPROGRAMADOMES");
              V3[j] += vPlan.getDouble("DREALMES");
              V4[j] += vPlan.getDouble("DESTIMADOCIERREANUAL");
              rep.comDespliegaAlineado("F",D[j],""+V[j],false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("G",D[j],""+V1[j],false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("H",D[j],""+V2[j],false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("I",D[j],""+V3[j],false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("J",D[j],""+V4[j],false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
            }
            for(int j=1;j<5;j++){V[j]=0;V1[j]=0;V2[j]=0;V3[j]=0;V4[j]=0;}
            break;
          case 3:
            ColIni = "C";
            for(int j=0;j<3;j++){
              D[2]=rengIni+i;
              V[j] += vPlan.getDouble("DPROGRAMADOACUMULADO");
              V1[j] += vPlan.getDouble("DREALACUMULADO");
              V2[j] += vPlan.getDouble("DPROGRAMADOMES");
              V3[j] += vPlan.getDouble("DREALMES");
              V4[j] += vPlan.getDouble("DESTIMADOCIERREANUAL");
              rep.comDespliegaAlineado("F",D[j],""+V[j],false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("G",D[j],""+V1[j],false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("H",D[j],""+V2[j],false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("I",D[j],""+V3[j],false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("J",D[j],""+V4[j],false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());

            }
            for(int j=2;j<5;j++){V[j]=0;V1[j]=0;V2[j]=0;V3[j]=0;V4[j]=0;}
            break;
          case 4:
            ColIni = "D";
            for(int j=0;j<4;j++){
              D[2]=rengIni+i;
              V[j] += vPlan.getDouble("DPROGRAMADOACUMULADO");
              V1[j] += vPlan.getDouble("DREALACUMULADO");
              V2[j] += vPlan.getDouble("DPROGRAMADOMES");
              V3[j] += vPlan.getDouble("DREALMES");
              V4[j] += vPlan.getDouble("DESTIMADOCIERREANUAL");
              rep.comDespliegaAlineado("F",D[j],""+V[j],false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("G",D[j],""+V1[j],false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("H",D[j],""+V2[j],false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("I",D[j],""+V3[j],false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("J",D[j],""+V4[j],false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
            }
            for(int j=3;j<5;j++){V[j]=0;V1[j]=0;V2[j]=0;V3[j]=0;V4[j]=0;}
            break;
          case 5:
            ColIni = "E";
            for(int j=0;j<5;j++){
              D[2]=rengIni+i;
              V[j] += vPlan.getDouble("DPROGRAMADOACUMULADO");
              V1[j] += vPlan.getDouble("DREALACUMULADO");
              V2[j] += vPlan.getDouble("DPROGRAMADOMES");
              V3[j] += vPlan.getDouble("DREALMES");
              V4[j] += vPlan.getDouble("DESTIMADOCIERREANUAL");
              rep.comDespliegaAlineado("F",D[j],""+V[j],false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("G",D[j],""+V1[j],false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("H",D[j],""+V2[j],false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("I",D[j],""+V3[j],false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("J",D[j],""+V4[j],false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
            }
            for(int j=4;j<5;j++){V[j]=0;V1[j]=0;V2[j]=0;V3[j]=0;V4[j]=0;}
            break;
        }
        rep.comDespliegaCombinado(vPlan.getString("CDSCCONCEPTOCONTABLE"),ColIni,rengIni+i,"E",rengIni+i,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,0,false,false,1,1);
        rep.comDespliegaAlineado("F",rengIni+i,""+vPlan.getDouble("DPROGRAMADOACUMULADO"),false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
        rep.comDespliegaAlineado("G",rengIni+i,""+vPlan.getDouble("DREALACUMULADO"),false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
        rep.comDespliegaAlineado("H",rengIni+i,""+vPlan.getDouble("DPROGRAMADOMES"),false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
        rep.comDespliegaAlineado("I",rengIni+i,""+vPlan.getDouble("DREALMES"),false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
        rep.comDespliegaAlineado("J",rengIni+i,""+vPlan.getDouble("DESTIMADOCIERREANUAL"),false,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
        rep.comBordeRededor("A",rengIni+i,"E",rengIni+i,1,1);
        rep.comBordeTotal("F",rengIni+i,"j",rengIni+i,1);
        T1[vPlan.getInt("ICVETIPOMOVCONTABLE")] += vPlan.getDouble("DPROGRAMADOACUMULADO");
        T2[vPlan.getInt("ICVETIPOMOVCONTABLE")] += vPlan.getDouble("DREALACUMULADO");
        T3[vPlan.getInt("ICVETIPOMOVCONTABLE")] += vPlan.getDouble("DPROGRAMADOMES");
        T4[vPlan.getInt("ICVETIPOMOVCONTABLE")] += vPlan.getDouble("DREALMES");
        T5[vPlan.getInt("ICVETIPOMOVCONTABLE")] += vPlan.getDouble("DESTIMADOCIERREANUAL");
        i++;
      }
              rep.comDespliegaCombinado("Resultado Neto","A",rengIni+i,"E",rengIni+i,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),true,0,true,false,1,0);
              rep.comDespliegaAlineado("F",rengIni+i,""+(T1[1]-T1[2]-T1[3]-T1[4]),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("G",rengIni+i,""+(T2[1]-T2[2]-T2[3]-T2[4]),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("H",rengIni+i,""+(T3[1]-T3[2]-T3[3]-T3[4]),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("I",rengIni+i,""+(T4[1]-T4[2]-T4[3]-T4[4]),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("J",rengIni+i,""+(T5[1]-T5[2]-T5[3]-T5[4]),true,rep.getAT_HDERECHA(),rep.getAT_VCENTRO());
              rep.comBordeTotal("A",rengIni+i,"J",rengIni+i,1);
              rep.comDespliegaAlineado("A",11,Filtro[2],true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("A",8,"CORRESPONDIENTE AL MES DE "+Filtro[3]+" DEL "+Filtro[4],true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());


        try{
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
