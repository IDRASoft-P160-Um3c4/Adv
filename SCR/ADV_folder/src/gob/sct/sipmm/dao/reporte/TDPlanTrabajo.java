package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import java.util.Vector;
import java.sql.SQLException;
import com.micper.util.TFechas;

/**
 * <p>Title: TDPlanTrabajo.java</p>
 * <p>Description: DAO con métodos generales para excel y word</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LSC. Rafael Miranda Blumenkron
 * @version 1.0
 */

public class TDPlanTrabajo extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");

  public TDPlanTrabajo(){
  }

  public StringBuffer GenAcumPlanTabajo(String cQuery) throws Exception{
    Vector vRegs = new Vector();
    StringBuffer sbRes = new StringBuffer("");
    cError = "";
    int iRengIni = 10, iFondoCeldaPrin = -1, iBorde = 1;
    int iFondoCeldaTit=15;
    char cColIni = 'A', cColAct;
    TExcel rep = new TExcel();
    TFechas Fecha = new TFechas("44");
    int iEjercicio = Fecha.getIntYear(Fecha.TodaySQL());
    int iMes       = Fecha.getIntMonth(Fecha.TodaySQL());
    String cResponsable="";
    String cNomMes = Fecha.getStringMonth(Fecha.TodaySQL());
    int iRengs=1;
    int iCveOficinaAnt = 0;
    int iNumActividadAnt = 0;
    int iCantidadEjec=0;
    int iRealizadoMes=0;
    int iRealizadoAcumMes=0;
    String cletra="";

    try{
      vRegs = super.FindByGeneric("", cQuery, dataSourceName);
    }catch(SQLException ex){
      cMensaje = ex.getMessage();
    }catch(Exception ex2){
      cMensaje += ex2.getMessage();
    }




    rep.iniciaReporte();
    rep.comEligeHoja(1);
    rep.comDespliegaCombinado("DIRECCION GENERAL DE MARINA MERCANTE",  "A",iRengs,"T",iRengs, rep.getAT_COMBINA_CENTRO(),"",true,iFondoCeldaPrin,true,true,0,1);
    iRengs=iRengs + 1;
    rep.comDespliegaCombinado("SUBDIRECCION DE PROGRAMAS Y POLITICAS","A",iRengs,"T",iRengs, rep.getAT_COMBINA_CENTRO(),"",true,iFondoCeldaPrin,true,true,0,1);
    iRengs=iRengs + 1;
    rep.comDespliegaCombinado("PROGRAMA DE TRABAJO: " + iEjercicio,   "A",iRengs,"T",iRengs, rep.getAT_COMBINA_CENTRO(),"",true,iFondoCeldaPrin,true,true,0,1);


    if (vRegs.size() > 0){
      for(int i=0;i<vRegs.size();i++){
        TVDinRep regAct = (TVDinRep) vRegs.get(i);

        if (iCveOficinaAnt == 0 || (iCveOficinaAnt != regAct.getInt("iOficinaEjecuta"))){
          //Responsable de Cada Bloque.
          iRengs=iRengs + 2;
          rep.comFontSize("A",iRengs,"A",iRengs,9);
          rep.comDespliegaAlineado("A",iRengs,"Responsable: " + regAct.getString("cDscOficina"),false,"IZQUIERDA","CENTRO");
          iRengs=iRengs +1;
          rep.comFontSize("A",iRengs,"T",iRengs,9);
          rep.comFontBold("A",iRengs,"T",iRengs);
          rep.comDespliegaCombinado("CLAVE","A",iRengs,"A",iRengs+2, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          rep.comDespliegaCombinado("ACTIVIDAD","B",iRengs,"B",iRengs+2, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          rep.comDespliegaCombinado("UNIDAD DE MEDIDA","C",iRengs,"C",iRengs+2, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          rep.comDespliegaCombinado("META ANUAL","D",iRengs,"D",iRengs+2, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          rep.comDespliegaCombinado("ENE","E",iRengs,"E",iRengs+2, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          rep.comDespliegaCombinado("FEB","F",iRengs,"F",iRengs+2, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          rep.comDespliegaCombinado("MAR","G",iRengs,"G",iRengs+2, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          rep.comDespliegaCombinado("ABR","H",iRengs,"H",iRengs+2, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          rep.comDespliegaCombinado("MAY","I",iRengs,"I",iRengs+2, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          rep.comDespliegaCombinado("JUN","J",iRengs,"J",iRengs+2, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          rep.comDespliegaCombinado("JUL","K",iRengs,"K",iRengs+2, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          rep.comDespliegaCombinado("AGO","L",iRengs,"L",iRengs+2, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          rep.comDespliegaCombinado("SEP","M",iRengs,"M",iRengs+2, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          rep.comDespliegaCombinado("OCT","N",iRengs,"N",iRengs+2, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          rep.comDespliegaCombinado("NOV","O",iRengs,"O",iRengs+2, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          rep.comDespliegaCombinado("DIC","P",iRengs,"P",iRengs+2, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          rep.comDespliegaCombinado("ACUMULADO","Q",iRengs,"R",iRengs, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          rep.comDespliegaCombinado("PROGRAMADO","Q",iRengs+1,"Q",iRengs+2, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          rep.comDespliegaCombinado("REALIZADO","R",iRengs+1,"R",iRengs+2, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          rep.comDespliegaCombinado("REALIZADO EN EL MES DE" + cNomMes,"S",iRengs,"S",iRengs+2, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          rep.comDespliegaCombinado("OBSERVACIONES","T",iRengs,"T",iRengs+2, rep.getAT_COMBINA_CENTRO(),"CENTRO",true,iFondoCeldaTit,true,true,12,1);
          iRengs=iRengs +2;
        }

        if (regAct.getInt("iMes") == 1 && (
            iNumActividadAnt == 0 ||
            iNumActividadAnt != regAct.getInt("iNumActividad") )){
          rep.comDespliegaAlineado("B",iRengs, regAct.getString("cDscActividad"),false,"IZQUIERDA","CENTRO");
          rep.comDespliegaAlineado("C",iRengs, regAct.getString("cDscUnidadMedida"),false,"CENTRO","CENTRO");
        }

        if (regAct.getInt("iMes")>=1 && regAct.getInt("iMes")<=12){
          iCantidadEjec = iCantidadEjec + regAct.getInt("dCantidadEjerc");
          //if (regAct.getInt("iMes")==iMes)
          //  iRealizadoMes = regAct.getInt("iCantidadEjec");
        }

        if (regAct.getInt("iMes")==1)
          rep.comDespliegaAlineado("E",iRengs, Integer.toString(regAct.getInt("dCantidadProg")),false,"DERECHA","CENTRO");
        if (regAct.getInt("iMes")==2)
          rep.comDespliegaAlineado("F",iRengs, Integer.toString(regAct.getInt("dCantidadProg")),false,"DERECHA","CENTRO");
        if (regAct.getInt("iMes")==3)
          rep.comDespliegaAlineado("G",iRengs, Integer.toString(regAct.getInt("dCantidadProg")),false,"DERECHA","CENTRO");
        if (regAct.getInt("iMes")==4)
          rep.comDespliegaAlineado("H",iRengs, Integer.toString(regAct.getInt("dCantidadProg")),false,"DERECHA","CENTRO");
        if (regAct.getInt("iMes")==5)
          rep.comDespliegaAlineado("I",iRengs, Integer.toString(regAct.getInt("dCantidadProg")),false,"DERECHA","CENTRO");
        if (regAct.getInt("iMes")==6)
          rep.comDespliegaAlineado("J",iRengs, Integer.toString(regAct.getInt("dCantidadProg")),false,"DERECHA","CENTRO");
        if (regAct.getInt("iMes")==7)
          rep.comDespliegaAlineado("K",iRengs, Integer.toString(regAct.getInt("dCantidadProg")),false,"DERECHA","CENTRO");
        if (regAct.getInt("iMes")==8)
          rep.comDespliegaAlineado("L",iRengs, Integer.toString(regAct.getInt("dCantidadProg")),false,"DERECHA","CENTRO");
        if (regAct.getInt("iMes")==9)
          rep.comDespliegaAlineado("M",iRengs, Integer.toString(regAct.getInt("dCantidadProg")),false,"DERECHA","CENTRO");
        if (regAct.getInt("iMes")==10)
          rep.comDespliegaAlineado("N",iRengs, Integer.toString(regAct.getInt("dCantidadProg")),false,"DERECHA","CENTRO");
        if (regAct.getInt("iMes")==11)
          rep.comDespliegaAlineado("O",iRengs, Integer.toString(regAct.getInt("dCantidadProg")),false,"DERECHA","CENTRO");
        if (regAct.getInt("iMes")==12){
          rep.comDespliegaAlineado("P",iRengs, Integer.toString(regAct.getInt("dCantidadProg")),false,"DERECHA","CENTRO");
          //Meta Anual.
          rep.comCreaFormula("D",iRengs,"=SUM(E" + iRengs + ":P" + iRengs + ")");
          rep.comAlineaRango("D",iRengs,"D",iRengs,"DERECHA");
          rep.comAlineaRangoVer("D",iRengs,"D",iRengs,"CENTRO");

          //Acumulado: Programado y Realizado.
          cletra = (iMes==1)?"E":(iMes==2)?"F":(iMes==3)?"G":(iMes==4)?"H":(iMes==5)?"I":(iMes==6)?"J":(iMes==7)?"K":(iMes==8)?"L":(iMes==9)?"M":(iMes==10)?"N":(iMes==11)?"O":(iMes==12)?"P":"";
          rep.comCreaFormula("Q",iRengs,"=SUM(E" + iRengs + ":" + cletra + "" + iRengs + ")");
          rep.comAlineaRango("Q",iRengs,"Q",iRengs,"DERECHA");
          rep.comAlineaRangoVer("Q",iRengs,"Q",iRengs,"CENTRO");
          rep.comDespliegaAlineado("R",iRengs, Integer.toString(iCantidadEjec),false,"DERECHA","CENTRO");

          //Realizado en el Mes de ...
          rep.comDespliegaAlineado("S",iRengs, Integer.toString(iRealizadoMes),false,"DERECHA","CENTRO");
          //Observaciones.
          rep.comDespliegaAlineado("T",iRengs, regAct.get("cObsActividad")!=null?regAct.getString("cObsActividad"):"",false,"IZQUIERDA","CENTRO");
        }


        if (regAct.getInt("iMes")==12){
          rep.comBordeTotal("A",iRengs,"T",iRengs,1);
          iRengs = iRengs + 1;
          iCantidadEjec = 0;
        }
        iNumActividadAnt = regAct.getInt("iNumActividad");
        iCveOficinaAnt = regAct.getInt("iOficinaEjecuta");

      }



      sbRes = rep.getSbDatos();

      //sbRes.append(GenAcumPlanTabajo(vRegs, objTitulos, iRengIni,cColIni,true,true,true));
    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return sbRes;
  }

  public StringBuffer GenAcumPlanTabajo(Vector vRegs, Object[] oTitulos, int iRengIni, char cColIni, boolean lTitulos, boolean lBordes, boolean lFormatos){
    TVDinRep regAct;
    TExcel rep = new TExcel();

    rep.iniciaReporte();
    if (vRegs.size() > 0){
      char cColAct = cColIni;
      String sCol = String.valueOf(cColAct), cDato, cTitulo;
      int iReng = iRengIni + 1;
      // Inicia procesamiento de despliegue de datos en reporte
      for (int i = 0; i < vRegs.size(); i++, iReng++){ // Despliegue de datos de registros
        cColAct = cColIni;
        regAct = (TVDinRep) vRegs.get(i);
        for (int j=0; j < regAct.size(); j++, cColAct++){
          cTitulo = (String)oTitulos[j];

//System.out.print("Valor del Titulo en la Primer Toma: " + cTitulo);

          cDato = regAct.get(cTitulo).toString();
          if (cTitulo.toUpperCase().startsWith("L"))
            cDato = cDato.equals("1")?"SI":"NO";
          sCol = String.valueOf(cColAct);
          rep.comDespliega(sCol,iReng,cDato);
        }
      }
      cColAct = cColIni;
      cTitulo = "";
      for (int i = 0; i < oTitulos.length; i++, cColAct++){ // Despliegue de etiquetas, alineación de columnas
        sCol = String.valueOf(cColAct);
        cTitulo = (String)oTitulos[i];
//System.out.print("Valor del Titulo: " + cTitulo );

        if (lTitulos){
          rep.comDespliega(sCol,iRengIni,cTitulo);
          rep.comAlineaRango(sCol,iRengIni,sCol,iRengIni,rep.getAT_HCENTRO());
          rep.comFontBold(sCol,iRengIni,sCol,iRengIni);
          rep.comFillCells(sCol,iRengIni,sCol,iRengIni,15);
          rep.comAlineaRangoVer(sCol, iRengIni, sCol, iRengIni, rep.getAT_VAJUSTAR());
        }
        if (lFormatos){
          rep.comAlineaRangoVer(sCol,iRengIni + 1,sCol,iReng-1,rep.getAT_VAJUSTAR());
          switch(super.getITipo(cTitulo)){
            case 1: // Entero o lógico
              rep.comAlineaRango(sCol,iRengIni + 1,sCol,iReng-1,rep.getAT_HCENTRO());
              break;
            case 2: // Caracter
              rep.comAlineaRango(sCol,iRengIni + 1,sCol,iReng-1, rep.getAT_HJUSTIFICA());
              break;
            case 3: // Fecha
              rep.comAlineaRango(sCol,iRengIni + 1,sCol,iReng-1,rep.getAT_HCENTRO());
              break;
            case 4: // Decimal
              rep.comAlineaRango(sCol,iRengIni + 1,sCol,iReng-1, rep.getAT_HDERECHA());
              break;
            case 6: // TimeStamp
              rep.comAlineaRango(sCol,iRengIni + 1,sCol,iReng-1,rep.getAT_HCENTRO());
              break;
            default: // Tipo de default interpretadoc como cadena
              rep.comAlineaRango(sCol,iRengIni + 1,sCol,iReng, rep.getAT_HJUSTIFICA());
              break;
          }
        }
      }
      cColAct = cColIni;
      for (int i = 1; i < oTitulos.length; i++, cColAct++);
      sCol = String.valueOf(cColAct);
      if (lBordes)
        if (lTitulos)
          rep.comBordeTotal(String.valueOf(cColIni), iRengIni, sCol, iReng-1, 1);
        else
          rep.comBordeTotal(String.valueOf(cColIni), iRengIni+1, sCol, iReng-1, 1);
      // Termina procesamiento de despliegue de datos en reporte
    }
    return rep.getSbDatos();
  }
}
