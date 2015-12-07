package gob.sct.sipmm.dao.reporte;
import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import java.util.Vector;
import com.micper.excepciones.DAOException;

public class TDESTEmpleoGenerado extends DAOBase{
  private String cSistema = "44";
  private TParametro VParametros = new TParametro(cSistema);
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private TFechas tFecha = new TFechas(cSistema);
  TExcel rep = new TExcel();

  public TDESTEmpleoGenerado(){
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
      int count=0;
      TVDinRep vContratados = new TVDinRep();
      TVDinRep vConcepto = new TVDinRep();
      int SumT = 0,SumP=0;

      int Reng = 13,renini=1,renfin=0;
      int iEne=0,iFeb=0,iMar=0,iAbr=0,iMay=0,iJun=0,iJul=0,iAgo=0,iSep=0,iOct=0,iNov=0,iDic=0;
      int jEne=0,jFeb=0,jMar=0,jAbr=0,jMay=0,jJun=0,jJul=0,jAgo=0,jSep=0,jOct=0,jNov=0,jDic=0;
      int reng=13;
        try{
            rep.iniciaReporte();

              String cConcepto = "SELECT ICVETIPOPROGRAMAOBRA,CDSCTIPOPROGRAMAOBRA FROM ESTTIPOPROGOBRA where LVIGENTE =1";
              Vector vcConcepto = findByCustom("",cConcepto);
              for (int i=0;i<vcConcepto.size();i++){
                vConcepto = (TVDinRep) vcConcepto.get(i);
                rep.comDespliegaAlineado("A",reng+vConcepto.getInt("ICVETIPOPROGRAMAOBRA")*2,vConcepto.getString("CDSCTIPOPROGRAMAOBRA"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                rep.comBordeTotal("A",reng+vConcepto.getInt("ICVETIPOPROGRAMAOBRA")*2,"AA",reng+vConcepto.getInt("ICVETIPOPROGRAMAOBRA")*2,1);
              }

              String cSQL =
                  "select SF.iPeriodoReportado,EG.ICVETIPOPROGRAMAOBRA, SF.ICVETITULO, SF.ICVETITULAR, " +
                  "EG.INUMEMPLEOSTEMPORALES, EG.INUMEMPLEOSPERMANENTES, " +
                  "PO.CDSCTIPOPROGRAMAOBRA " +
                  "FROM ESTSegtoFormulario SF " +
                  "JOIN ESTEmpleoGenerado EG ON EG.IEJERCICIOSEGUIMIENTO = SF.IEJERCICIOSEGUIMIENTO AND EG.INUMMOVTOSEGTO = SF.INUMMOVTOSEGTO " +
                  "LEFT JOIN ESTTipoProgObra PO ON PO.ICVETIPOPROGRAMAOBRA = EG.ICVETIPOPROGRAMAOBRA " +
                  "WHERE SF.IEJERCICIOREPORTADO = "+Filtro[0]+" AND SF.IPERIODOREPORTADO <= " +Filtro[1] +
                  " AND SF.ICVETITULO = " +Filtro[2]+
                  " AND SF.ICVETITULAR = " +Filtro[3]+
                  " ORDER BY SF.IEJERCICIOREPORTADO, SF.IPERIODOREPORTADO ";

              Vector vcData = findByCustom("",cSQL);

              for(int j=0;j<vcData.size();j++){
                vContratados = (TVDinRep) vcData.get(j);
                renini= vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2<renini?vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2:renini;
                renfin= vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2>renfin?vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2:renfin;
                SumT += vContratados.getInt("INUMEMPLEOSTEMPORALES");
                SumP += vContratados.getInt("INUMEMPLEOSTEMPORALES");

                switch(vContratados.getInt("iPeriodoReportado")){
                  case 1:
                    rep.comDespliegaAlineado("B",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSTEMPORALES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("C",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSPERMANENTES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    iEne+=vContratados.getInt("INUMEMPLEOSTEMPORALES");
                    jEne+=vContratados.getInt("INUMEMPLEOSPERMANENTES");
                    break;
                  case 2:
                    rep.comDespliegaAlineado("D",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSTEMPORALES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("E",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSPERMANENTES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    iFeb+=vContratados.getInt("INUMEMPLEOSTEMPORALES");
                    jFeb+=vContratados.getInt("INUMEMPLEOSPERMANENTES");
                    break;
                  case 3:
                    rep.comDespliegaAlineado("F",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSTEMPORALES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("G",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSPERMANENTES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    iMar+=vContratados.getInt("INUMEMPLEOSTEMPORALES");
                    jMar+=vContratados.getInt("INUMEMPLEOSPERMANENTES");
                    break;
                  case 4:
                    rep.comDespliegaAlineado("H",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSTEMPORALES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("I",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSPERMANENTES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    iAbr+=vContratados.getInt("INUMEMPLEOSTEMPORALES");
                    jAbr+=vContratados.getInt("INUMEMPLEOSPERMANENTES");
                    break;
                  case 5:
                    rep.comDespliegaAlineado("J",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSTEMPORALES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("K",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSPERMANENTES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    iMay+=vContratados.getInt("INUMEMPLEOSTEMPORALES");
                    jMay+=vContratados.getInt("INUMEMPLEOSPERMANENTES");
                    break;
                  case 6:
                    rep.comDespliegaAlineado("L",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSTEMPORALES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("M",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSPERMANENTES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    iJun+=vContratados.getInt("INUMEMPLEOSTEMPORALES");
                    jJun+=vContratados.getInt("INUMEMPLEOSPERMANENTES");
                    break;
                  case 7:
                    rep.comDespliegaAlineado("N",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSTEMPORALES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("O",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSPERMANENTES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    iJul+=vContratados.getInt("INUMEMPLEOSTEMPORALES");
                    jJul+=vContratados.getInt("INUMEMPLEOSPERMANENTES");
                    break;
                  case 8:
                    rep.comDespliegaAlineado("P",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSTEMPORALES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("Q",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSPERMANENTES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    iAgo+=vContratados.getInt("INUMEMPLEOSTEMPORALES");
                    jAgo+=vContratados.getInt("INUMEMPLEOSPERMANENTES");
                    break;
                  case 9:
                    rep.comDespliegaAlineado("R",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSTEMPORALES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("Q",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSPERMANENTES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    iSep+=vContratados.getInt("INUMEMPLEOSTEMPORALES");
                    jSep+=vContratados.getInt("INUMEMPLEOSPERMANENTES");
                    break;
                  case 10:
                    rep.comDespliegaAlineado("T",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSTEMPORALES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("U",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSPERMANENTES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    iOct+=vContratados.getInt("INUMEMPLEOSTEMPORALES");
                    jOct+=vContratados.getInt("INUMEMPLEOSPERMANENTES");
                    break;
                  case 11:
                    rep.comDespliegaAlineado("V",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSTEMPORALES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("W",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSPERMANENTES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    iNov+=vContratados.getInt("INUMEMPLEOSTEMPORALES");
                    jNov+=vContratados.getInt("INUMEMPLEOSPERMANENTES");
                    break;
                  case 12:
                    rep.comDespliegaAlineado("X",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSTEMPORALES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    rep.comDespliegaAlineado("Y",reng+vContratados.getInt("ICVETIPOPROGRAMAOBRA")*2,vContratados.getString("INUMEMPLEOSPERMANENTES"),true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
                    iDic+=vContratados.getInt("INUMEMPLEOSTEMPORALES");
                    jDic+=vContratados.getInt("INUMEMPLEOSPERMANENTES");
                    break;
                }
                count = j*2 -1;
              }
             // rep.comCreaFormula("B",(renfin+2+reng),iEne);
              rep.comDespliegaAlineado("B",(renfin+2+reng),""+iEne,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("d",(renfin+2+reng),""+iFeb,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("f",(renfin+2+reng),""+iMar,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("h",(renfin+2+reng),""+iAbr,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("j",(renfin+2+reng),""+iMay,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("l",(renfin+2+reng),""+iJun,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("n",(renfin+2+reng),""+iJul,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("p",(renfin+2+reng),""+iAgo,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("r",(renfin+2+reng),""+iSep,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("t",(renfin+2+reng),""+iOct,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("v",(renfin+2+reng),""+iNov,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("x",(renfin+2+reng),""+iDic,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());

              rep.comDespliegaAlineado("c",(renfin+2+reng),""+jEne,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("e",(renfin+2+reng),""+jFeb,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("g",(renfin+2+reng),""+jMar,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("i",(renfin+2+reng),""+jAbr,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("k",(renfin+2+reng),""+jMay,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("m",(renfin+2+reng),""+jJun,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("o",(renfin+2+reng),""+jJul,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("q",(renfin+2+reng),""+jAgo,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("s",(renfin+2+reng),""+jSep,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("u",(renfin+2+reng),""+jOct,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("w",(renfin+2+reng),""+jNov,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              rep.comDespliegaAlineado("y",(renfin+2+reng),""+jDic,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());

              //rep.comDespliegaAlineado("z",(renfin+2+reng),""+SumT,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
              //rep.comDespliegaAlineado("aa",(renfin+2+reng),""+SumP,true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());


            //rep.comDespliegaAlineado("",(renfin+2+reng),"=SUMA(B"+(reng+2)+":B"+(renfin+reng)+")",true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
            rep.comDespliegaAlineado("A",7,""+Filtro[4],true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
            if(Integer.parseInt(Filtro[1])==1){
              rep.comDespliegaAlineado("A",8,"Correspondiente a " + Filtro[5] + " del " +Filtro[6],true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
            }else{
              rep.comDespliegaAlineado("A",8,"Correspondiente al periodo de ENERO a "+Filtro[5]+ " del "+Filtro[6],true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
            }
            rep.comDespliegaAlineado("A",(renfin+2+reng),"TOTAL",true,rep.getAT_HCENTRO(),rep.getAT_VCENTRO());
            rep.comBordeTotal("A",(renfin+2+reng),"AA",(renfin+2+reng),1);
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
