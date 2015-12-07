package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import java.util.Vector;
import java.util.HashMap;
import gob.sct.sipmm.dao.*;
import java.sql.SQLException;
import com.micper.excepciones.DAOException;
import java.sql.Date;

/**
 * <p>Title: TDInspección.java</p>
 * <p>Description: DAO con métodos para reportes de Boletin Informativo</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ALopez
 * @version 1.0
 */

public class TDDEMBoletin
    extends DAOBase{
  private String cSistema = "44";
  private TParametro VParametros = new TParametro(cSistema);
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private TFechas tFecha = new TFechas(cSistema);
  TExcel rep = new TExcel();

  public TDDEMBoletin(){
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

  public StringBuffer fBoletin(String iConsecutivoCertExp) throws Exception{
    cError = "";
    TFechas dFechas = new TFechas();
    StringBuffer sbRetorno = new StringBuffer("");
    try{

//Obras de Protección
      rep.iniciaReporte();
      String[] aParam = iConsecutivoCertExp.split(",");
      int iEjercicio = Integer.parseInt(aParam[0]);
      int iNumReporte = Integer.parseInt(aParam[1]);
      int iCveLitoral = Integer.parseInt(aParam[3]);
      String tsRegistro = aParam[2];
      TDObtenDatos dObten3 = new TDObtenDatos();
      Vector vRutaRemitente = new Vector();
      vRutaRemitente = dObten3.getOrganigrama(1,84,vRutaRemitente);
      for(int i = 0;i < vRutaRemitente.size();i++){
        // TVDinRep vOficinas = (TVDinRep) vRutaRemitente.get(i);
        // (String)vRutaRemitente.get(i)
        rep.comDespliegaCombinado( (String) vRutaRemitente.get(i),"D",1 + i,"I",1 + i,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
      }

      Vector vcData = findByCustom("",
                                   "SELECT ZI.CDSCZONAINFLUENCIA,CZ.CREPORTE FROM DEMCONDICIONESZONA CZ " +
                                   "JOIN DEMZONASINFLUENCIA ZI ON ZI.ICVEZONAINFLUENCIA = CZ.ICVEZONAINFLUENCIA " +
                                   "WHERE CZ.IEJERCICIO = " + iEjercicio + " AND CZ.INUMREPORTE = " + iNumReporte + " AND CZ.ICVELITORAL = " + iCveLitoral);

      //    TVDinRep vCertifIA = (TVDinRep) vcData.get(0);
      Vector vcLugar = findByCustom("",
                                    "SELECT C.CDSCCAPITANIA, " +
                                    "CASE WHEN LABIERTOEMBMAYOR = 1 THEN 'ABIERTO' WHEN LABIERTOEMBMAYOR = 0 THEN 'CERRADO' END AS CONDICION, " +
                                    "CM.DPRESIONATMOSFERICA, CONDC.CDSCCONDICIONXTIPO AS CCielo,CM.DGRADOS, " +
                                    "CONDV.CDSCCONDICIONXTIPO AS cViento,CM.DVELOCIDADMINVIENTO,CM.DVELOCIDADMAXVIENT, " +
                                    "CONDM.CDSCCONDICIONXTIPO AS cMar,CM.DALTURAMINOLAS,CM.DALTURAMAXOLAS,cm.LABIERTOEMBMENOR " +
                                    "FROM DEMCONDICIONESMET CM " +
                                    "JOIN MYRCAPITANIA C ON C.ICVEOFICINA = CM.ICVEOFICINA " +
                                    "JOIN NAVCONDICIONXTIPO CONDC ON CONDC.ICVECONDICIONXTIPO = CM.ICONDICIONCIELO " +
                                    "JOIN NAVCONDICIONXTIPO CONDV ON CONDV.ICVECONDICIONXTIPO = CM.ICONDICIONVIENTO " +
                                    "JOIN NAVCONDICIONXTIPO CONDM ON CONDM.ICVECONDICIONXTIPO = CM.ICONDICIONMAR " +
                                    "WHERE CM.IEJERCICIO = " + iEjercicio + " AND CM.INUMREPORTE = " + iNumReporte + " AND CM.ICVELITORAL = " + iCveLitoral);
      Vector vcAvisos = findByCustom("",
                                     "SELECT CACTIVIDAD FROM DEMACTIVIDADESESP " +
                                     "WHERE DTINICIOACTIVIDAD <= '" + tsRegistro.substring(0,4) + "-" + tsRegistro.substring(5,7) + "-" + tsRegistro.substring(8,10) + "'" +
                                     " AND DTFINACTIVIDAD >= '" + tsRegistro.substring(0,4) + "-" + tsRegistro.substring(5,7) + "-" + tsRegistro.substring(8,10) + "'");

      String ColInicial = "A",ColFinal = "I";
      int RenglonInicial = 10;
      int TamTotal = 0,NumReng = 0;
      for(int i = 0;i < vcData.size();i++){
        RenglonInicial += NumReng + 1;
        TVDinRep vCertifIA = (TVDinRep) vcData.get(i);
        TamTotal = vCertifIA.getString("CDSCZONAINFLUENCIA").length() + vCertifIA.getString("CREPORTE").length();
        NumReng = (TamTotal / 90);
        rep.comDespliegaCombinado(vCertifIA.getString("CDSCZONAINFLUENCIA") + "   " + vCertifIA.getString("CREPORTE"),ColInicial,RenglonInicial,ColFinal,RenglonInicial + NumReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR(),false,2,false,false,1,1);
      }
      rep.comBordeRededor("A",8,"I", (RenglonInicial + NumReng),1,1);

      RenglonInicial = (RenglonInicial + NumReng + 1);
      rep.comFontSize("a",RenglonInicial,"i",RenglonInicial,8);
      rep.comDespliegaCombinado("CONDICIONES DE PUERTO  PARA EMBARCACIONES MAYORES Y OBSERVACIONES METEREOLOGICAS LOCALES",ColInicial,RenglonInicial,ColFinal,RenglonInicial,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR(),false,2,false,false,1,1);
      rep.comDespliegaAlineado("A",RenglonInicial + 1,"CAPITANIA",true,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO());
      rep.comDespliegaAlineado("B",RenglonInicial + 1,"CONDICIÓN",true,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO());
      rep.comDespliegaAlineado("C",RenglonInicial + 1,"P.B. (HPA)",false,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO());
      rep.comDespliegaAlineado("D",RenglonInicial + 1,"CIELO",false,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO());
      rep.comDespliegaAlineado("E",RenglonInicial + 1,"TEMP",false,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO());
      rep.comDespliegaAlineado("F",RenglonInicial + 1,"G",RenglonInicial + 1,"VIENTO",false,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO());
      rep.comDespliegaAlineado("H",RenglonInicial + 1,"I",RenglonInicial + 1,"MAR",false,rep.getAT_COMBINA_CENTRO(),rep.getAT_VCENTRO());
      String cPuertoCerradoMenores = "";
      for(int i = 0;i < vcLugar.size();i++){
        TVDinRep vCondicion = (TVDinRep) vcLugar.get(i);
        rep.comDespliegaAlineado("A",RenglonInicial + 2 + i,vCondicion.getString("CDSCCAPITANIA"),false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO());
        rep.comDespliegaAlineado("B",RenglonInicial + 2 + i,vCondicion.getString("CONDICION"),false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO());
        rep.comDespliegaAlineado("C",RenglonInicial + 2 + i,vCondicion.getString("DPRESIONATMOSFERICA"),false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO());
        rep.comDespliegaAlineado("D",RenglonInicial + 2 + i,vCondicion.getString("CCielo"),false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO());
        rep.comDespliegaAlineado("E",RenglonInicial + 2 + i,vCondicion.getString("DGRADOS") + " ºC",false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO());
        rep.comDespliegaAlineado("F",RenglonInicial + 2 + i,"G",RenglonInicial + 2 + i,vCondicion.getString("cViento") + " VIENTOS DE " + vCondicion.getString("DVELOCIDADMINVIENTO") + " A " + vCondicion.getString("DVELOCIDADMAXVIENT"),false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO());
        rep.comDespliegaAlineado("H",RenglonInicial + 2 + i,"I",RenglonInicial + 2 + i,vCondicion.getString("cMar") + " CON OLAS DE " + vCondicion.getString("DALTURAMINOLAS") + " A " + vCondicion.getString("DALTURAMAXOLAS"),false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO());
        if(vCondicion.getInt("LABIERTOEMBMENOR") == 0){
          if(cPuertoCerradoMenores != "")
            cPuertoCerradoMenores += ", " + vCondicion.getString("CDSCCAPITANIA");
          else cPuertoCerradoMenores = vCondicion.getString("CDSCCAPITANIA");
        }
      }
      rep.comBordeRededor("A",RenglonInicial + 1,"A", (RenglonInicial + NumReng + 2),1,1);
      rep.comBordeRededor("b",RenglonInicial + 1,"B", (RenglonInicial + NumReng + 2),1,1);
      rep.comBordeRededor("C",RenglonInicial + 1,"C", (RenglonInicial + NumReng + 2),1,1);
      rep.comBordeRededor("D",RenglonInicial + 1,"D", (RenglonInicial + NumReng + 2),1,1);
      rep.comBordeRededor("E",RenglonInicial + 1,"E", (RenglonInicial + NumReng + 2),1,1);
      rep.comBordeRededor("F",RenglonInicial + 1,"H", (RenglonInicial + NumReng + 2),1,1);
      rep.comBordeRededor("H",RenglonInicial + 1,"I", (RenglonInicial + NumReng + 2),1,1);
      if(cPuertoCerradoMenores != "") rep.comDespliegaAlineado("A",RenglonInicial + 2 + vcLugar.size(),"I",RenglonInicial + 2 + vcLugar.size(),"CONDICIONES DEL PUERTO PARA EMBARCACIONES MENORES: " + cPuertoCerradoMenores,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO());
      if(cPuertoCerradoMenores == "") rep.comDespliegaAlineado("A",RenglonInicial + 2 + vcLugar.size(),"I",RenglonInicial + 2 + vcLugar.size(),"CONDICIONES DEL PUERTO PARA EMBARCACIONES MENORES: " + "SE ENCUENTRAN ABIERTOS TODOS LOS PUERTOS",false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO());

      RenglonInicial += NumReng + 5;
      NumReng = 0;
      rep.comDespliegaCombinado("AVISOS A LOS MARINOS","A",RenglonInicial - 2,"I",RenglonInicial - 2,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
      rep.comDespliegaCombinado("ESTOS AVISOS SE EMITIRAN DURANTE 15 DIAS UNICAMENTE","A",RenglonInicial - 1,"I",RenglonInicial - 1,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
      for(int i = 0;i < vcAvisos.size();i++){
        TVDinRep vAvisos = (TVDinRep) vcAvisos.get(i);
        NumReng = (vAvisos.getString("CACTIVIDAD").length() / 63);
        rep.comDespliegaCombinado("·  " + vAvisos.getString("CACTIVIDAD"),"A",RenglonInicial + i,"I",RenglonInicial + i,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VCENTRO(),false,2,true,false,1,1);
      }
      sbRetorno = rep.getSbDatos();
    } catch(Exception e){
      e.printStackTrace();
      cMensaje += e.getMessage();
    }
    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return sbRetorno;
  }

  public HashMap boletinMetJasper(String cFiltro) throws Exception{
    HashMap hParametros = new HashMap();
    String[] cDatos = cFiltro.split(",");
    hParametros.put("iEjercicio",new Integer(cDatos[0]));
    hParametros.put("iNumBoletin",new Integer(cDatos[1]));
    return hParametros;
  }

  public HashMap boletinAvisoNotaJasper(String cFiltro) throws Exception{
    HashMap hParametros = new HashMap();
    return hParametros;
  }

}
