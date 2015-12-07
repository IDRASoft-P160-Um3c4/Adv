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

/**
 * <p>Title: TDDPOPOA.java</p>
 * <p>Description: DAO con métodos para reportes de POA</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ICaballero
 * @version 1.0
 */

public class TDDPOPOA
    extends DAOBase{
  private String cSistema = "44";
  private TParametro VParametros = new TParametro(cSistema);
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  TExcel rep = new TExcel();

  private int iEjercicio = 0;
  private int iCveTitulo = 0;
  private int iCveTitular = 0;
  private TFechas    tFecha      = new TFechas("44");

  public TDDPOPOA(){
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

  public StringBuffer GenPOA(String cSolicitud) throws Exception{
    cError = "";

    StringBuffer sbRetorno = new StringBuffer("");
    String iCveFormatoC = VParametros.getPropEspecifica("FormatoCPOA");
    String iCveFormatoD = VParametros.getPropEspecifica("FormatoDPOA");
    String iCveFormatoE = VParametros.getPropEspecifica("FormatoEPOA");

    String[] aSolicitudes = cSolicitud.split(",");

    iEjercicio = Integer.parseInt(aSolicitudes[0],10);
    iCveTitulo = Integer.parseInt(aSolicitudes[1],10);
    iCveTitular = Integer.parseInt(aSolicitudes[2],10);
    int lPOAA = Integer.parseInt(aSolicitudes[3],10);
    int lPOAB = Integer.parseInt(aSolicitudes[4],10);
    int lPOAC = Integer.parseInt(aSolicitudes[5],10);
    int lPOAD = Integer.parseInt(aSolicitudes[6],10);
    int lPOAE = Integer.parseInt(aSolicitudes[7],10);

    try{
      if(lPOAA == 1 && lPOAB == 1 && lPOAC == 1 && lPOAD == 1 && lPOAE == 1){
        sbRetorno.append(POAC(iCveFormatoC,iEjercicio,iCveTitulo,iCveTitular));
        sbRetorno.append(POAD(iCveFormatoD,iEjercicio,iCveTitulo,iCveTitular));
        sbRetorno.append(POAE(iCveFormatoE,iEjercicio,iCveTitulo,iCveTitular));
      } else{
        if(lPOAC == 1)
          sbRetorno.append(POAC(iCveFormatoC,iEjercicio,iCveTitulo,iCveTitular));
        if(lPOAD == 1)
          sbRetorno.append(POAD(iCveFormatoD,iEjercicio,iCveTitulo,iCveTitular));
        if(lPOAE == 1)
          sbRetorno.append(POAE(iCveFormatoE,iEjercicio,iCveTitulo,iCveTitular));
      }

    } catch(Exception e){
      e.printStackTrace();
      cMensaje += e.getMessage();
    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return sbRetorno;
  }

  public StringBuffer GenSegtoPOA(String cSolicitud) throws Exception{
    cError = "";

    StringBuffer sbRetorno = new StringBuffer("");
    int iCveFormatoI = Integer.parseInt(VParametros.getPropEspecifica("FormatoIPOA"));
    int iCveFormatoIII = Integer.parseInt(VParametros.getPropEspecifica("FormatoIIIPOA"));

    String[] aSolicitudes = cSolicitud.split(",");

    iEjercicio = Integer.parseInt(aSolicitudes[0],10);
    iCveTitulo = Integer.parseInt(aSolicitudes[1],10);
    iCveTitular = Integer.parseInt(aSolicitudes[2],10);
    int lFormatoI = Integer.parseInt(aSolicitudes[3],10);
    int lFormatoIII = Integer.parseInt(aSolicitudes[4],10);

    try{
      if(lFormatoI == 1 && lFormatoIII == 1){
        sbRetorno.append(FormatoI(iCveFormatoI,iEjercicio,iCveTitulo,iCveTitular));
        sbRetorno.append(FormatoIII(iCveFormatoIII,iEjercicio,iCveTitulo,iCveTitular));
      } else{
        if(lFormatoI == 1)
          sbRetorno.append(FormatoI(iCveFormatoI,iEjercicio,iCveTitulo,iCveTitular));
        if(lFormatoIII == 1)
          sbRetorno.append(FormatoIII(iCveFormatoIII,iEjercicio,iCveTitulo,iCveTitular));
      }

    } catch(Exception e){
      e.printStackTrace();
      cMensaje += e.getMessage();
    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return sbRetorno;
  }

  public Vector GenFormatoAPOA(String cFiltro) throws Exception{
     TWord rep = new TWord();
     String[] aFiltro = cFiltro.split("=");

     rep.iniciaReporte();

     try{
       rep.iniciaReporte();

       rep.comRemplaza("[No_Oficio_Registro]",aFiltro[0]);
       rep.comRemplaza("[Registro_interno]",aFiltro[1]);
       rep.comRemplaza("[No_Oficio_API]", aFiltro[2]);
       rep.comRemplaza("[iNumSolicitud]", aFiltro[3]);
       rep.comRemplaza("[fecha_actual]",tFecha.getDateSPN(tFecha.TodaySQL()) );
       rep.comRemplaza("[anio_actual]",tFecha.getStringYear(tFecha.TodaySQL()) );
       rep.comRemplaza("[API]",aFiltro[4]);

       /*if(cObservacion.length() > 2000){
         String cObsTemp = cObservacion.substring(0,2000);
         String cObsTemp2 = cObservacion.substring(2001,cObservacion.length());
         rep.comRemplaza("[cObservacion]",cObsTemp+"[cObservacion2]");
         rep.comRemplaza("[cObservacion2]",cObsTemp2);
       }else
         rep.comRemplaza("[cObservacion]",cObservacion);*/


     }catch(Exception ex){
       ex.printStackTrace();
       cMensaje = ex.getMessage();
     }
     if(!cMensaje.equals(""))
       throw new Exception(cMensaje);
     return rep.getVectorDatos(true);

   }

   public Vector GenFormatoBPOA(String cFiltro) throws Exception{
     TWord rep = new TWord();
     String[] aFiltro = cFiltro.split(",");
     iEjercicio = Integer.parseInt(aFiltro[0],10);
     iCveTitulo = Integer.parseInt(aFiltro[1],10);
     iCveTitular = Integer.parseInt(aFiltro[2],10);
     String cDscFormato = "", cDscSeccion = "";
     int iCveFormato = Integer.parseInt(VParametros.getPropEspecifica("FormatoBPOA"));


     rep.iniciaReporte();

     try{
       rep.iniciaReporte();

       StringBuffer cSQL = new StringBuffer();

       cSQL.append("SELECT ICVEFORMATO, CDSCFORMATO,CDSCSECCION,LVIGENTE ");
       cSQL.append("FROM DPOFORMATOS ");
       cSQL.append("WHERE ICVEFORMATO = " + iCveFormato);

       Vector vcData = findByCustom("",cSQL.toString());

       TVDinRep vNomReporteA = (TVDinRep) vcData.get(0);
       cDscFormato = vNomReporteA.getString("CDSCFORMATO");
       cDscSeccion = vNomReporteA.getString("CDSCSECCION");

       StringBuffer cSQL2 = new StringBuffer();

       cSQL2.append("SELECT ");
       cSQL2.append("GRLOBSERVALARGA.CDSCOBSLARGA as observacion, ");
       cSQL2.append("DPOTIPODIAGNOSTICO.CDSCTIPODIAGNOSTICO as tipoDiagnostico ");
       cSQL2.append("FROM DPOFORMATOBPOA ");
       cSQL2.append("join GRLOBSERVALARGA on DPOFORMATOBPOA.IEJERCICIOOBSLARGA = GRLOBSERVALARGA.IEJERCICIOOBSLARGA ");
       cSQL2.append("and DPOFORMATOBPOA.ICVEOBSLARGA = GRLOBSERVALARGA.ICVEOBSLARGA ");
       cSQL2.append("join DPOTIPODIAGNOSTICO on DPOTIPODIAGNOSTICO.ICVEFORMATO = DPOFORMATOBPOA.ICVEFORMATO ");
       cSQL2.append("and DPOTIPODIAGNOSTICO.INUMDIAGNOSTICO = dpoformatobpoa.INUMDIAGNOSTICO ");
       cSQL2.append("where DPOFORMATOBPOA.IEJERCICIOPOA = " +iEjercicio);
       cSQL2.append(" and DPOFORMATOBPOA.icvetitulo = " + iCveTitulo);
       cSQL2.append(" and DPOFORMATOBPOA.ICVETITULAR = " + iCveTitular);

       Vector vcData1 = findByCustom("",cSQL2.toString());

       rep.comEligeTabla("cMarcadorCuerpo");
       for(int i=0; i<vcData1.size(); i++){
         TVDinRep vDatosGenerales = (TVDinRep) vcData1.get(i);
         rep.comDespliegaCelda(vDatosGenerales.getString("tipoDiagnostico"));
         rep.comDespliegaCelda(" ");
         rep.comDespliegaCelda(vDatosGenerales.getString("observacion"));
         rep.comDespliegaCelda(" ");
       }


       StringBuffer cSQL3 = new StringBuffer();

       cSQL3.append("SELECT ");
       cSQL3.append("IORDEN, ");
       cSQL3.append("CDSCINSTRUCTIVO ");
       cSQL3.append("FROM DPOINSTRUCTIVOF ");
       cSQL3.append("where ICVEFORMATO = "+ iCveFormato);
       cSQL3.append(" order by iOrden ");


       Vector vcData2 = findByCustom("",cSQL3.toString());
       rep.comEligeTabla("cMarcadorInstructivo");
       for(int i = 0;i < vcData2.size(); i++){
         TVDinRep vInstructivo = (TVDinRep) vcData2.get(i);
         rep.comDespliegaCelda("(" + vInstructivo.getString("IORDEN") + ")" +
                               vInstructivo.getString("CDSCINSTRUCTIVO"));
       }
       rep.comRemplaza("[cTitulo]",cDscFormato);
       rep.comRemplaza("[cSeccion]",cDscSeccion);

     }catch(Exception ex){
       ex.printStackTrace();
       cMensaje = ex.getMessage();
     }
     if(!cMensaje.equals(""))
       throw new Exception(cMensaje);
     return rep.getVectorDatos(true);

   }

  public StringBuffer POAC(String iCveFormatoC,int iEjercicio,int iCveTitulo,
                           int iCveTitular) throws Exception{
    String cDscTemp = "";
    cError = "";
    int iBorde = 1,
        iFondoCeldaPrin = -1, iNumObjetivoC=0, iRengFormatoC,iRengDetalleC,iCveBalanceDscC;
    String cDscFormatoC="", cDscSeccionC="", cDscSubTituloC="", cCodigoObejtivoC="",
        cObejtivoC="", cDescripcionObjC="",cCodigoIndicadorC="", cIndicadorC="",
        cDscBscC="",dMetaAnualC="", dPrimerSemestreC="", dSegundoSemestreC="",
        cObsDetalleC="", cOrdenC="", cInstructivoC="";

    StringBuffer sbRetorno = new StringBuffer("");

    try{
      rep.iniciaReporte();
      rep.comEligeHoja(2);

      Vector vcData = findByCustom("",
          "SELECT ICVEFORMATO, CDSCFORMATO,CDSCSECCION,LVIGENTE " +
                                   "FROM DPOFORMATOS " +
                                   "WHERE ICVEFORMATO = " + iCveFormatoC);

      TVDinRep vNomReporteC = (TVDinRep) vcData.get(0);
      cDscFormatoC = vNomReporteC.getString("CDSCFORMATO");
      cDscSeccionC = vNomReporteC.getString("CDSCSECCION");

      Vector vcData1 = findByCustom("","SELECT " +
                                    "CDSCTIPODIAGNOSTICO " +
                                    "FROM DPOTIPODIAGNOSTICO " +
                                    "where ICVEFORMATO = " + iCveFormatoC);

      TVDinRep vNomSubTituloC = (TVDinRep) vcData1.get(0);
      cDscSubTituloC = vNomSubTituloC.getString("CDSCTIPODIAGNOSTICO");

      rep.comDespliegaCombinado(cDscFormatoC,"B",1,"K",1,
                                rep.getAT_COMBINA_CENTRO(),"",true,
                                iFondoCeldaPrin,true,true,iBorde,1);
      rep.comDespliegaCombinado(cDscSeccionC,"B",3,"K",3,
                                rep.getAT_COMBINA_IZQUIERDA(),"",true,
                                iFondoCeldaPrin,true,true,iBorde,1);
      rep.comDespliegaCombinado(cDscSubTituloC,"B",5,"F",5,
                                rep.getAT_COMBINA_IZQUIERDA(),"",true,
                                iFondoCeldaPrin,true,true,iBorde,1);

      Vector vcData2 = findByCustom("","SELECT " +
                                    "DPOFORMATOCPOA.ICVEBALANCEDSC as ICVEBALANCEDSC,INUMOBJETIVO,CCODIGOOBJETIVO,COBJETIVO," +
                                    "cObsRegistrada,DPOBALANCEDSC.CDSCBSC as cDscBsc " +
                                    "FROM DPOFORMATOCPOA " +
                                    "join GRLOBSERVACION on DPOFORMATOCPOA.IEJERCICIOC = GRLOBSERVACION.IEJERCICIO " +
                                    "and DPOFORMATOCPOA.ICVEOBSERVACIONC = GRLOBSERVACION.ICVEOBSERVACION " +
                                    "join DPOBALANCEDSC on DPOFORMATOCPOA.ICVEBALANCEDSC = DPOBALANCEDSC.ICVEBALANCEDSC  " +
                                    "where IEJERCICIOPOA = " + iEjercicio +
                                    " and ICVETITULO = " + iCveTitulo +
                                    " and ICVETITULAR = " + iCveTitular+
                                    " order by ICVEBALANCEDSC");

      iRengFormatoC = 9;
      iRengDetalleC = 9;
      int conRengAnt = 0;
      for(int i = 0;i < vcData2.size();i++){
        TVDinRep vFormatoC = (TVDinRep) vcData2.get(i);
        if(i>0){
          TVDinRep vFormatoCtemp = (TVDinRep) vcData2.get(i - 1);
          cDscTemp = vFormatoCtemp.getString("cDscBsc");
        }
        iCveBalanceDscC = vFormatoC.getInt("ICVEBALANCEDSC");
        iNumObjetivoC = vFormatoC.getInt("INUMOBJETIVO");
        cCodigoObejtivoC = vFormatoC.getString("CCODIGOOBJETIVO");
        cObejtivoC = vFormatoC.getString("COBJETIVO");
        cDescripcionObjC = vFormatoC.getString("cObsRegistrada");
        cDscBscC = vFormatoC.getString("cDscBsc");

        rep.comDespliegaAlineado("C",iRengFormatoC,cCodigoObejtivoC,false,rep.getAT_HCENTRO(),"");
        rep.comDespliegaAlineado("D",iRengFormatoC,cObejtivoC,false,rep.getAT_HIZQUIERDA(),"");
        rep.comDespliegaAlineado("E",iRengFormatoC,cDescripcionObjC,false,rep.getAT_HIZQUIERDA(),"");

        Vector vcData3 = findByCustom("","SELECT "+
                                      "CCODIGOINDICADOR,"+
                                      "CINDICADOR,"+
                                      "dMetaAnual as cMetaAnual,"+
                                      "dPrimerSemestre as cPrimerSemestre,"+
                                      "dSegundoSemestre as cSegundoSemestre,"+
                                      "COBSREGISTRADA " +
                                      "FROM DPODETALLECPOA " +
                                      "join GRLOBSERVACION on DPODETALLECPOA.IEJERCICIOREGISTRO = GRLOBSERVACION.IEJERCICIO " +
                                      "and DPODETALLECPOA.ICVEOBSERVACIONREG = GRLOBSERVACION.ICVEOBSERVACION " +
                                      "where IEJERCICIOPOA = " + iEjercicio +
                                      " and icvetitulo = " + iCveTitulo +
                                      " and iCveTitular = " + iCveTitular +
                                      " and iCveBalancedSC = " + iCveBalanceDscC +
                                      " and iNumObjetivo = " + iNumObjetivoC +
                                      " order by CCODIGOINDICADOR");
        if(vcData3.size() > 0){
          for(int j = 0;j < vcData3.size();j++){
            TVDinRep vDetalleC = (TVDinRep) vcData3.get(j);
            cCodigoIndicadorC = vDetalleC.getString("CCODIGOINDICADOR");
            cIndicadorC = vDetalleC.getString("CINDICADOR");
            dMetaAnualC = vDetalleC.getString("cMetaAnual");
            dPrimerSemestreC = vDetalleC.getString("cPrimerSemestre");
            dSegundoSemestreC = vDetalleC.getString("cSegundoSemestre");
            cObsDetalleC = vDetalleC.getString("COBSREGISTRADA");

            rep.comDespliegaAlineado("F",iRengDetalleC,cCodigoIndicadorC,false,rep.getAT_HCENTRO(),"");
            rep.comDespliegaAlineado("G",iRengDetalleC,cIndicadorC,false,rep.getAT_HIZQUIERDA(),"");
            rep.comDespliegaAlineado("H",iRengDetalleC,dMetaAnualC,false,rep.getAT_HDERECHA(),"");
            rep.comDespliegaAlineado("I",iRengDetalleC,dPrimerSemestreC,false,rep.getAT_HDERECHA(),"");
            rep.comDespliegaAlineado("J",iRengDetalleC,dSegundoSemestreC,false,rep.getAT_HDERECHA(),"");
            rep.comDespliegaAlineado("K",iRengDetalleC,cObsDetalleC,false,rep.getAT_HIZQUIERDA(),"");
            rep.comBordeRededor("F",iRengDetalleC,"F",iRengDetalleC,iBorde,1);
            rep.comBordeRededor("G",iRengDetalleC,"G",iRengDetalleC,iBorde,1);
            rep.comBordeRededor("H",iRengDetalleC,"H",iRengDetalleC,iBorde,1);
            rep.comBordeRededor("I",iRengDetalleC,"I",iRengDetalleC,iBorde,1);
            rep.comBordeRededor("J",iRengDetalleC,"J",iRengDetalleC,iBorde,1);
            rep.comBordeRededor("K",iRengDetalleC,"K",iRengDetalleC,iBorde,1);
            iRengDetalleC++;
          }
          int tempC = 0;
          if(iRengFormatoC < iRengDetalleC){
            tempC = iRengDetalleC - iRengFormatoC;
            if(tempC > 1){
              rep.comBordeRededor("B",iRengFormatoC,"B",iRengDetalleC - 1,iBorde,1);
              rep.comBordeRededor("C",iRengFormatoC,"C",iRengDetalleC - 1,iBorde,1);
              rep.comBordeRededor("D",iRengFormatoC,"D",iRengDetalleC - 1,iBorde,1);
              rep.comBordeRededor("E",iRengFormatoC,"E",iRengDetalleC - 1,iBorde,1);
            }else{
              rep.comBordeRededor("B",iRengFormatoC,"B",iRengFormatoC,iBorde,1);
              rep.comBordeRededor("C",iRengFormatoC,"C",iRengFormatoC,iBorde,1);
              rep.comBordeRededor("D",iRengFormatoC,"D",iRengFormatoC,iBorde,1);
              rep.comBordeRededor("E",iRengFormatoC,"E",iRengFormatoC,iBorde,1);
            }
          }

        } else{
          rep.comDespliegaAlineado("F",iRengDetalleC," ",false,
                                   rep.getAT_HCENTRO(),"");
          rep.comDespliegaAlineado("G",iRengDetalleC," ",false,
                                   rep.getAT_HCENTRO(),"");
          rep.comDespliegaAlineado("H",iRengDetalleC," ",false,
                                   rep.getAT_HCENTRO(),"");
          rep.comDespliegaAlineado("I",iRengDetalleC," ",false,
                                   rep.getAT_HCENTRO(),"");
          rep.comDespliegaAlineado("J",iRengDetalleC," ",false,
                                   rep.getAT_HCENTRO(),"");
          rep.comDespliegaAlineado("K",iRengDetalleC," ",false,
                                   rep.getAT_HCENTRO(),"");
          rep.comBordeRededor("F",iRengDetalleC,"F",iRengDetalleC,iBorde,1);
          rep.comBordeRededor("G",iRengDetalleC,"G",iRengDetalleC,iBorde,1);
          rep.comBordeRededor("H",iRengDetalleC,"H",iRengDetalleC,iBorde,1);
          rep.comBordeRededor("I",iRengDetalleC,"I",iRengDetalleC,iBorde,1);
          rep.comBordeRededor("J",iRengDetalleC,"J",iRengDetalleC,iBorde,1);
          rep.comBordeRededor("K",iRengDetalleC,"K",iRengDetalleC,iBorde,1);
          rep.comBordeRededor("B",iRengFormatoC,"B",iRengFormatoC,iBorde,1);
          rep.comBordeRededor("C",iRengFormatoC,"C",iRengFormatoC,iBorde,1);
          rep.comBordeRededor("D",iRengFormatoC,"D",iRengFormatoC,iBorde,1);
          rep.comBordeRededor("E",iRengFormatoC,"E",iRengFormatoC,iBorde,1);

          iRengDetalleC++;

        }
        if(cDscBscC.equalsIgnoreCase(cDscTemp)){
          rep.comDespliegaCombinado(cDscBscC,"B",conRengAnt,"B",
                                    iRengDetalleC-1,rep.getAT_COMBINA_IZQUIERDA(),"",false,
                                    iFondoCeldaPrin,true,false,iBorde,1);

        }else{
          rep.comDespliegaAlineado("B",iRengFormatoC,cDscBscC,false,rep.getAT_HIZQUIERDA(),"");
          rep.comBordeRededor("B",iRengFormatoC,"B",iRengDetalleC - 1,iBorde,1);
        }

        conRengAnt = iRengFormatoC;

        if(iRengFormatoC < iRengDetalleC)
          iRengFormatoC = iRengDetalleC;
        else
          iRengFormatoC++;

      }

      //Datos Instructivo
      Vector vcData4 = findByCustom("",
          "SELECT ICVEFORMATO, INUMINSTRUCTIVO, CDSCINSTRUCTIVO, IORDEN " +
                                    "FROM  DPOINSTRUCTIVOF " +
                                    "where ICVEFORMATO = " + iCveFormatoC +
                                    " order by iOrden ");

      iRengFormatoC += 2;
      rep.comFontColor("B",iRengFormatoC,"C",iRengFormatoC,1);
      rep.comDespliegaCombinado("INSTRUCTIVO","B",iRengFormatoC,"C",
                                iRengFormatoC,rep.getAT_COMBINA_IZQUIERDA(),"",true,
                                iFondoCeldaPrin,false,true,iBorde,1);
      iRengFormatoC++;
      for(int k = 0;k < vcData4.size();k++){
        TVDinRep vDatosInstructivoC = (TVDinRep) vcData4.get(k);
        cOrdenC = vDatosInstructivoC.getString("IORDEN");
        cInstructivoC = vDatosInstructivoC.getString("CDSCINSTRUCTIVO");
        rep.comFontColor("B",iRengFormatoC,"C",iRengFormatoC,1);
        rep.comDespliegaAlineado("B",iRengFormatoC,"'(" + cOrdenC + ")",false,
                                 rep.getAT_HDERECHA(),"");
        rep.comDespliegaCombinado(cInstructivoC,"C",iRengFormatoC,"K",
                                  iRengFormatoC,rep.getAT_COMBINA_IZQUIERDA(),
                                  "",false,iFondoCeldaPrin,false,true,iBorde,1);
        iRengFormatoC++;
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

  public StringBuffer POAD(String iCveFormatoD,int iEjercicio,int iCveTitulo,
                           int iCveTitular) throws Exception{
    String cDscTemp = "";
    cError = "";
    int iReng,iBorde = 1,iFondoCeldaPrin = -1, iRengFormatoD;
    String cDscFormatoD,cDscSeccionD,cDscSubTituloD,cPerspectivaD,cIndicadorD,cObsD,
        cOrdenD="", cInstructivoD="";
    double dMetaAnualD,dUnoTriD,dDosTriD,dTresTriD,dCuartoTriD;
    StringBuffer sbRetorno = new StringBuffer("");

    try{
      rep.iniciaReporte();
      rep.comEligeHoja(3);

      Vector vcData = findByCustom("",
          "SELECT ICVEFORMATO, CDSCFORMATO,CDSCSECCION,LVIGENTE " +
                                   "FROM DPOFORMATOS " +
                                   "WHERE ICVEFORMATO = " + iCveFormatoD);

      TVDinRep vNomReporteD = (TVDinRep) vcData.get(0);
      cDscFormatoD = vNomReporteD.getString("CDSCFORMATO");
      cDscSeccionD = vNomReporteD.getString("CDSCSECCION");

      Vector vcData1 = findByCustom("","SELECT " +
                                    "CDSCTIPODIAGNOSTICO " +
                                    "FROM DPOTIPODIAGNOSTICO " +
                                    "where ICVEFORMATO = " + iCveFormatoD);

      TVDinRep vNomSubTituloD = (TVDinRep) vcData1.get(0);
      cDscSubTituloD = vNomSubTituloD.getString("CDSCTIPODIAGNOSTICO");

      rep.comDespliegaCombinado(cDscFormatoD,"B",1,"I",1,
                                rep.getAT_COMBINA_CENTRO(),"",true,
                                iFondoCeldaPrin,true,true,iBorde,1);
      rep.comDespliegaCombinado(cDscSeccionD,"B",3,"I",3,
                                rep.getAT_COMBINA_IZQUIERDA(),"",true,
                                iFondoCeldaPrin,true,true,iBorde,1);
      rep.comDespliegaCombinado(cDscSubTituloD,"B",5,"D",5,
                                rep.getAT_COMBINA_IZQUIERDA(),"",true,
                                iFondoCeldaPrin,true,true,iBorde,1);
      //la cadena "Indicadores Institucionales" debe de ser sustituida por una propiedad
      rep.comDespliegaCombinado("Indicadores Institucionales","B",7,"I",7,
                                rep.getAT_COMBINA_CENTRO(),"",true,
                                iFondoCeldaPrin,true,true,iBorde,1);

      Vector vcData2 = findByCustom("","SELECT " +
                                    "DPOBALANCEDSC.CDSCBSC as cPerspectivaD, " +
                                    "DPOINDICADORINS.CDSCINDICADOR as cIndicadorD, " +
                                    "DPOFORMATODPOA.DMETAANUAL as cMetaAnualD, " +
                                    "DPOFORMATODPOA.DPRIMERTRIMESTRE as cUnoTriD, " +
                                    "DPOFORMATODPOA.DSEGUNDOTRIMESTRE as cDosTriD, " +
                                    "DPOFORMATODPOA.DTERCERTRIMESTRE as cTresTriD, " +
                                    "DPOFORMATODPOA.DCUARTOTRIMESTRE as cCuartoTriD, " +
                                    "GRLOBSERVACION.COBSREGISTRADA as cObsD, " +
                                    "DPOFORMATODPOA.ICVEBALANCEDSC as iCveBalan,"+
                                    "DPOFORMATODPOA.INUMINDICADORINS as iNumInd "+
                                    "FROM DPOFORMATODPOA " +
                                    "join DPOINDICADORINS on DPOFORMATODPOA.ICVEBALANCEDSC = DPOINDICADORINS.ICVEBALANCEDSC " +
                                    "and DPOFORMATODPOA.INUMINDICADORINS = DPOINDICADORINS.INUMINDICADORINS " +
                                    "join DPOBALANCEDSC on DPOINDICADORINS.ICVEBALANCEDSC = DPOBALANCEDSC.ICVEBALANCEDSC " +
                                    "join GRLOBSERVACION on DPOFORMATODPOA.IEJERCICIOREGISTRO = GRLOBSERVACION.IEJERCICIO " +
                                    "and DPOFORMATODPOA.ICVEOBSERVACIONREG = GRLOBSERVACION.ICVEOBSERVACION " +
                                    "where DPOFORMATODPOA.IEJERCICIOPOA = " + iEjercicio +
                                    " and DPOFORMATODPOA.ICVETITULO = " + iCveTitulo +
                                    " and DPOFORMATODPOA.ICVETITULAR = " +  iCveTitular);
      iRengFormatoD = 11;

      for(int i = 0;i < vcData2.size();i++){
        TVDinRep vFormatoD = (TVDinRep) vcData2.get(i);
        if(i>0){
                  TVDinRep vFormatoDtemp = (TVDinRep) vcData2.get(i - 1);
                  cDscTemp = vFormatoDtemp.getString("cPerspectivaD");
        }

        cPerspectivaD = vFormatoD.getString("cPerspectivaD");
        cIndicadorD = vFormatoD.getString("cIndicadorD");
        dMetaAnualD = vFormatoD.getDouble("cMetaAnualD");
        dUnoTriD = vFormatoD.getDouble("cUnoTriD");
        dDosTriD = vFormatoD.getDouble("cDosTriD");
        dTresTriD = vFormatoD.getDouble("cTresTriD");
        dCuartoTriD = vFormatoD.getDouble("cCuartoTriD");
        cObsD = vFormatoD.getString("cObsD");

        rep.comDespliegaAlineado("C",iRengFormatoD,cIndicadorD,false,rep.getAT_HIZQUIERDA(),"");
        rep.comDespliegaAlineado("D",iRengFormatoD,"" + dMetaAnualD,false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaAlineado("E",iRengFormatoD,"" + dUnoTriD,false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaAlineado("F",iRengFormatoD,"" + dDosTriD,false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaAlineado("G",iRengFormatoD,"" + dTresTriD,false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaAlineado("H",iRengFormatoD,"" + dCuartoTriD,false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaAlineado("I",iRengFormatoD,cObsD,false,rep.getAT_HIZQUIERDA(),"");
        rep.comBordeRededor("B",iRengFormatoD,"B",iRengFormatoD,iBorde,1);
        rep.comBordeRededor("C",iRengFormatoD,"C",iRengFormatoD,iBorde,1);
        rep.comBordeRededor("D",iRengFormatoD,"D",iRengFormatoD,iBorde,1);
        rep.comBordeRededor("E",iRengFormatoD,"E",iRengFormatoD,iBorde,1);
        rep.comBordeRededor("F",iRengFormatoD,"F",iRengFormatoD,iBorde,1);
        rep.comBordeRededor("G",iRengFormatoD,"G",iRengFormatoD,iBorde,1);
        rep.comBordeRededor("H",iRengFormatoD,"H",iRengFormatoD,iBorde,1);
        rep.comBordeRededor("I",iRengFormatoD,"I",iRengFormatoD,iBorde,1);

        Vector vcDataDetalle = findByCustom("","SELECT " +
                                            "DET.CNOMDETALLEINDICADOR as cNomDetalleInd," +
                                            "DET.DMETAANUAL AS cMETANUAL, " +
                                            "DET.DPRIMERTRIMESTRE AS cUNOTRI, " +
                                            "DET.DSEGUNDOTRIMESTRE AS cDOSTRI, " +
                                            "DET.DTERCERTRIMESTRE AS cTRESTRI, " +
                                            "DET.DCUARTOTRIMESTRE AS cCUATROTRI, " +
                                            "OBS.COBSREGISTRADA AS COBS " +
                                            "FROM DPODETALLEDPOA DET " +
                                            "JOIN GRLOBSERVACION  OBS ON DET.IEJERCICIOMETAREGISTRO = OBS.IEJERCICIO " +
                                            "AND DET.ICVEOBSMETAREGISTRO = OBS.ICVEOBSERVACION "+
                                            "WHERE IEJERCICIOPOA = "+iEjercicio +
                                            " AND ICVETITULO = "+iCveTitulo +
                                            " AND ICVETITULAR = "+iCveTitular+
                                            " AND ICVEBALANCEDSC = "+vFormatoD.getInt("iCveBalan")+
                                            " AND INUMINDICADORINS = "+vFormatoD.getInt("iNumInd"));
        int iContDet = 0;
        for(int j=0; j<vcDataDetalle.size(); j++){
          iRengFormatoD += 1;
          TVDinRep vDetalleD = (TVDinRep) vcDataDetalle.get(j);
          rep.comDespliegaAlineado("C",iRengFormatoD,"   " + vDetalleD.getString("cNomDetalleInd"),false,rep.getAT_HIZQUIERDA(),"");
          rep.comDespliegaAlineado("D",iRengFormatoD,vDetalleD.getString("cMETANUAL"),false,rep.getAT_HDERECHA(),"");
          rep.comDespliegaAlineado("E",iRengFormatoD,vDetalleD.getString("cUNOTRI"),false,rep.getAT_HDERECHA(),"");
          rep.comDespliegaAlineado("F",iRengFormatoD,vDetalleD.getString("cDOSTRI"),false,rep.getAT_HDERECHA(),"");
          rep.comDespliegaAlineado("G",iRengFormatoD,vDetalleD.getString("cTRESTRI"),false,rep.getAT_HDERECHA(),"");
          rep.comDespliegaAlineado("H",iRengFormatoD,vDetalleD.getString("cCUATROTRI"),false,rep.getAT_HDERECHA(),"");
          rep.comDespliegaAlineado("I",iRengFormatoD,vDetalleD.getString("COBS"),false,rep.getAT_HIZQUIERDA(),"");
          rep.comBordeRededor("B",iRengFormatoD,"B",iRengFormatoD,iBorde,1);
          rep.comBordeRededor("C",iRengFormatoD,"C",iRengFormatoD,iBorde,1);
          rep.comBordeRededor("D",iRengFormatoD,"D",iRengFormatoD,iBorde,1);
          rep.comBordeRededor("E",iRengFormatoD,"E",iRengFormatoD,iBorde,1);
          rep.comBordeRededor("F",iRengFormatoD,"F",iRengFormatoD,iBorde,1);
          rep.comBordeRededor("G",iRengFormatoD,"G",iRengFormatoD,iBorde,1);
          rep.comBordeRededor("H",iRengFormatoD,"H",iRengFormatoD,iBorde,1);
          rep.comBordeRededor("I",iRengFormatoD,"I",iRengFormatoD,iBorde,1);
          iContDet++;
        }

        if(cPerspectivaD.equalsIgnoreCase(cDscTemp)){
          rep.comDespliegaCombinado(cPerspectivaD,"B",iRengFormatoD-1-iContDet,"B",
                                    iRengFormatoD,rep.getAT_COMBINA_IZQUIERDA(),"",false,
                                    iFondoCeldaPrin,true,false,iBorde,1);

        }else
          rep.comDespliegaCombinado(cPerspectivaD,"B",iRengFormatoD-iContDet,"B",
                                    iRengFormatoD,rep.getAT_COMBINA_IZQUIERDA(),"",false,
                                    iFondoCeldaPrin,true,false,iBorde,1);



        iRengFormatoD++;
      }
      //Datos Instructivo
      Vector vcData3 = findByCustom("",
          "SELECT ICVEFORMATO, INUMINSTRUCTIVO, CDSCINSTRUCTIVO, IORDEN " +
                                    "FROM  DPOINSTRUCTIVOF " +
                                    "where ICVEFORMATO = " + iCveFormatoD +
                                    " order by iOrden ");

      iRengFormatoD += 2;
      rep.comDespliegaCombinado("INSTRUCTIVO","B",iRengFormatoD,"C",
                                iRengFormatoD,rep.getAT_COMBINA_IZQUIERDA(),"",true,
                                iFondoCeldaPrin,false,true,iBorde,1);
      iRengFormatoD++;
      for(int k = 0;k < vcData3.size();k++){
        TVDinRep vDatosInstructivoD = (TVDinRep) vcData3.get(k);
        cOrdenD = vDatosInstructivoD.getString("IORDEN");
        cInstructivoD = vDatosInstructivoD.getString("CDSCINSTRUCTIVO");
        rep.comFontColor("B",iRengFormatoD,"I",iRengFormatoD,1);
        rep.comDespliegaAlineado("B",iRengFormatoD,"'(" + cOrdenD + ")",false,
                                 rep.getAT_HDERECHA(),"");
        rep.comDespliegaCombinado(cInstructivoD,"C",iRengFormatoD,"I",
                                  iRengFormatoD,rep.getAT_COMBINA_IZQUIERDA(),
                                  "",false,iFondoCeldaPrin,false,true,iBorde,1);
        iRengFormatoD++;
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

  public StringBuffer POAE(String iCveFormatoE,int iEjercicio,int iCveTitulo,
                           int iCveTitular) throws Exception{
    cError = "";
    int iBorde = 1,iFondoCeldaPrin = -1,iNumIniE,iRengDetalleE,iRengFormatoE;
    String cDscFormatoE,cDscSeccionE,cDscSubTituloE,cCodigoIniE,cActPrinE,cInstructivoE,cOrdenE,cIni;
    double dPresAsignadoE,dPresAsigDetE;
    Date dtFechaIniE,dtFechaFinE;

    StringBuffer sbRetorno = new StringBuffer("");

    try{
      rep.iniciaReporte();
      rep.comEligeHoja(4);

      Vector vcData = findByCustom("",
          "SELECT ICVEFORMATO, CDSCFORMATO,CDSCSECCION,LVIGENTE " +
                                   "FROM DPOFORMATOS " +
                                   "WHERE ICVEFORMATO = " + iCveFormatoE);
      TVDinRep vNomReporteE = (TVDinRep) vcData.get(0);
      cDscFormatoE = vNomReporteE.getString("CDSCFORMATO");
      cDscSeccionE = vNomReporteE.getString("CDSCSECCION");

      Vector vcData1 = findByCustom("","SELECT " +
                                    "CDSCTIPODIAGNOSTICO " +
                                    "FROM DPOTIPODIAGNOSTICO " +
                                    "where ICVEFORMATO = " + iCveFormatoE);

      TVDinRep vNomSubTituloE = (TVDinRep) vcData1.get(0);
      cDscSubTituloE = vNomSubTituloE.getString("CDSCTIPODIAGNOSTICO");

      rep.comDespliegaCombinado(cDscFormatoE,"B",1,"K",1,
                                rep.getAT_COMBINA_CENTRO(),"",true,
                                iFondoCeldaPrin,true,true,iBorde,1);
      rep.comDespliegaCombinado(cDscSeccionE,"B",3,"K",3,
                                rep.getAT_COMBINA_IZQUIERDA(),"",true,
                                iFondoCeldaPrin,true,true,iBorde,1);
      rep.comDespliegaCombinado(cDscSubTituloE,"B",5,"E",5,
                                rep.getAT_COMBINA_IZQUIERDA(),"",true,
                                iFondoCeldaPrin,true,true,iBorde,1);

      Vector vcData2 = findByCustom("","SELECT " +
                                    "DPOFORMATOEPOA.CCODIGOINICIATIVA as cCodigoIniE, " +
                                    "DPOFORMATOEPOA.INUMINICIATIVA as iNumIniE, " +
                                    "DPOFORMATOEPOA.DTOTALPRESUPUESTOASIGNADO as cPresAsignadoE, " +
                                    "DPOFORMATOEPOA.CINICIATIVA as cIni, " +
                                    "GRLOBSERVACION.COBSREGISTRADA as cObs " +
                                    "FROM DPOFORMATOEPOA " +
                                    "JOIN GRLOBSERVACION ON DPOFORMATOEPOA.IEJERCICIOREGISTRO = GRLOBSERVACION.IEJERCICIO " +
                                    "AND DPOFORMATOEPOA.ICVEOBSREGISTRO = GRLOBSERVACION.ICVEOBSERVACION " +
                                    "where DPOFORMATOEPOA.IEJERCICIOPOA = " + iEjercicio +
                                    " and DPOFORMATOEPOA.ICVETITULO = " + iCveTitulo +
                                    " and DPOFORMATOEPOA.ICVETITULAR = " + iCveTitular);

      iRengFormatoE = 8;
      iRengDetalleE = 8;
      int iRengTemp = 8;
      int iRengObjetivos = 8;
/*Muestra Actividades de cada Iniciativa*/
      for(int i = 0;i < vcData2.size();i++){
        TVDinRep vFormatoE = (TVDinRep) vcData2.get(i);
        cCodigoIniE = vFormatoE.getString("cCodigoIniE");
        iNumIniE = vFormatoE.getInt("iNumIniE");
        dPresAsignadoE = vFormatoE.getDouble("cPresAsignadoE");
        cIni = vFormatoE.getString("cIni");

        rep.comDespliegaAlineado("B",iRengFormatoE,cCodigoIniE,false,rep.getAT_HCENTRO(),"");
        rep.comDespliegaAlineado("C",iRengFormatoE,cIni,false,rep.getAT_HIZQUIERDA(),"");
        rep.comDespliegaAlineado("F",iRengFormatoE,"" + dPresAsignadoE,false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaAlineado("K",iRengFormatoE,vFormatoE.getString("cObs"),false,rep.getAT_HIZQUIERDA(),"");

        Vector vcData3 = findByCustom("","SELECT " +
                                      "DPODETALLEEPOA.CPRINCIPALACTIVIDAD AS cPrinActE, " +
                                      "DPODETALLEEPOA.DPRESUPUESTOASIGNADO as dPresAsigDetE, " +
                                      "DPODETALLEEPOA.DTINI as dtFechaIni, " +
                                      "DPODETALLEEPOA.DTFIN as dtFechaFin " +
                                      "FROM DPODETALLEEPOA " +
                                      "where DPODETALLEEPOA.IEJERCICIOPOA = " + iEjercicio +
                                      " and DPODETALLEEPOA.icvetitulo = " + iCveTitulo +
                                      " and DPODETALLEEPOA.iCveTitular = " + iCveTitular +
                                      " and DPODETALLEEPOA.INUMINICIATIVA = " + iNumIniE);
        /*Detalle de cada registro del Formato E*/
        if(vcData3.size() > 0){
          for(int j = 0;j < vcData3.size();j++){
            iRengDetalleE = (iRengFormatoE>iRengDetalleE)?iRengFormatoE:iRengDetalleE;
            TVDinRep vDetalleE = (TVDinRep) vcData3.get(j);
            cActPrinE = vDetalleE.getString("cPrinActE");
            dPresAsigDetE = vDetalleE.getDouble("dPresAsigDetE");
            dtFechaIniE = vDetalleE.getDate("dtFechaIni");
            dtFechaFinE = vDetalleE.getDate("dtFechaFin");

            rep.comDespliegaAlineado("G",iRengDetalleE,cActPrinE,false,rep.getAT_HIZQUIERDA(),"");
            rep.comDespliegaAlineado("H",iRengDetalleE,"" + dPresAsigDetE,false,rep.getAT_HDERECHA(),"");
            rep.comDespliegaAlineado("I",iRengDetalleE,"" + dtFechaIniE,false,rep.getAT_HCENTRO(),"");
            rep.comDespliegaAlineado("J",iRengDetalleE,"" + dtFechaFinE,false,rep.getAT_HCENTRO(),"");
            rep.comBordeRededor("G",iRengDetalleE,"G",iRengDetalleE,iBorde,1);
            rep.comBordeRededor("H",iRengDetalleE,"H",iRengDetalleE,iBorde,1);
            rep.comBordeRededor("I",iRengDetalleE,"I",iRengDetalleE,iBorde,1);
            rep.comBordeRededor("J",iRengDetalleE,"J",iRengDetalleE,iBorde,1);
            iRengDetalleE++;

          }

        } else{
          rep.comDespliegaAlineado("G",iRengDetalleE,"",false,rep.getAT_HCENTRO(),"");
          rep.comDespliegaAlineado("H",iRengDetalleE,"",false,rep.getAT_HCENTRO(),"");
          rep.comDespliegaAlineado("I",iRengDetalleE,"",false,rep.getAT_HCENTRO(),"");
          rep.comDespliegaAlineado("J",iRengDetalleE,"",false,rep.getAT_HCENTRO(),"");
          rep.comBordeRededor("G",iRengDetalleE,"G",iRengDetalleE,iBorde,1);
          rep.comBordeRededor("H",iRengDetalleE,"H",iRengDetalleE,iBorde,1);
          rep.comBordeRededor("I",iRengDetalleE,"I",iRengDetalleE,iBorde,1);
          rep.comBordeRededor("J",iRengDetalleE,"J",iRengDetalleE,iBorde,1);
          rep.comBordeRededor("B",iRengFormatoE,"B",iRengFormatoE,iBorde,1);
          rep.comBordeRededor("C",iRengFormatoE,"C",iRengFormatoE,iBorde,1);
          rep.comBordeRededor("F",iRengFormatoE,"F",iRengFormatoE,iBorde,1);

          iRengDetalleE++;
        }

        Vector vcDataObejtivos = findByCustom("","SELECT " +
                                              "COBJETIVOIMPACTADO, " +
                                              "CLOGROESPERADO " +
                                              "FROM DPOOBJXINICIATIVA " +
                                              "WHERE IEJERCICIOPOA = " + iEjercicio +
                                              " AND ICVETITULO = " + iCveTitulo +
                                              " AND ICVETITULAR = " + iCveTitular +
                                              " AND INUMINICIATIVA = " +iNumIniE);
        /*Objetivos por cada registro del formato e*/
        for(int k = 0; k < vcDataObejtivos.size(); k++){
          TVDinRep vDataObjetivos = (TVDinRep) vcDataObejtivos.get(k);
          iRengObjetivos = (iRengFormatoE>iRengObjetivos)?iRengFormatoE:iRengObjetivos;
          rep.comDespliegaAlineado("D",iRengObjetivos,vDataObjetivos.getString("COBJETIVOIMPACTADO"),false,rep.getAT_HIZQUIERDA(),"");
          rep.comDespliegaAlineado("E",iRengObjetivos,vDataObjetivos.getString("CLOGROESPERADO"),false,rep.getAT_HIZQUIERDA(),"");
          rep.comBordeRededor("D",iRengObjetivos,"D",iRengObjetivos,iBorde,1);
          rep.comBordeRededor("E",iRengObjetivos,"E",iRengObjetivos,iBorde,1);
          iRengObjetivos++;

          if(iRengTemp < iRengObjetivos){
            if((iRengObjetivos - iRengTemp) > 1){
              rep.comBordeRededor("D",iRengTemp,"D",iRengObjetivos - 1,iBorde,1);
              rep.comBordeRededor("E",iRengTemp,"E",iRengObjetivos - 1,iBorde,1);
            }else{
              rep.comBordeRededor("D",iRengTemp,"D",iRengTemp,iBorde,1);
              rep.comBordeRededor("E",iRengTemp,"E",iRengTemp,iBorde,1);
            }
          }
        }

        if(vcDataObejtivos.size() <= 0){
          rep.comDespliegaAlineado("D",iRengObjetivos,"",false,rep.getAT_HCENTRO(),"");
          rep.comDespliegaAlineado("E",iRengObjetivos,"",false,rep.getAT_HCENTRO(),"");
          rep.comBordeRededor("D",iRengObjetivos,"D",iRengObjetivos,iBorde,1);
          rep.comBordeRededor("E",iRengObjetivos,"E",iRengObjetivos,iBorde,1);
          iRengObjetivos++;
        }
        /*Variabe iRengUltimo para ver quien tuvo mas registros de el detalle o los objetivos,
         para asi ver de donde a donde se pinta el borde de cada registro del formato E*/
        int temp = 0;
        int iRengUltimo = (iRengDetalleE > iRengObjetivos)?iRengDetalleE:iRengObjetivos;

        if(iRengFormatoE < iRengUltimo){
          temp = iRengUltimo - iRengFormatoE;
          if(temp > 1){
            rep.comBordeRededor("B",iRengFormatoE,"B",iRengUltimo - 1,iBorde,1);
            rep.comBordeRededor("C",iRengFormatoE,"C",iRengUltimo - 1,iBorde,1);
            rep.comBordeRededor("F",iRengFormatoE,"F",iRengUltimo - 1,iBorde,1);
            rep.comBordeRededor("K",iRengFormatoE,"K",iRengUltimo - 1,iBorde,1);
            if(vcDataObejtivos.size() > vcData3.size()){
              rep.comBordeRededor("G",iRengDetalleE,"G",iRengUltimo ,iBorde,1);
              rep.comBordeRededor("H",iRengDetalleE,"H",iRengUltimo ,iBorde,1);
              rep.comBordeRededor("I",iRengDetalleE,"I",iRengUltimo,iBorde,1);
              rep.comBordeRededor("J",iRengDetalleE,"J",iRengUltimo,iBorde,1);
            }else if(vcDataObejtivos.size() < vcData3.size()){
              rep.comBordeRededor("D",iRengObjetivos-1,"D",iRengUltimo - 1,iBorde,1);
              rep.comBordeRededor("E",iRengObjetivos-1,"E",iRengUltimo - 1,iBorde,1);
            }
          }else{
            rep.comBordeRededor("B",iRengFormatoE,"B",iRengFormatoE,iBorde,1);
            rep.comBordeRededor("C",iRengFormatoE,"C",iRengFormatoE,iBorde,1);
            rep.comBordeRededor("F",iRengFormatoE,"F",iRengFormatoE,iBorde,1);
            rep.comBordeRededor("K",iRengFormatoE,"K",iRengFormatoE,iBorde,1);
          }
        }
        /*Para ver en que renglon va a empezar en la siguiente iteración del ciclo cada renglon*/
        if(iRengFormatoE < iRengUltimo)
          iRengFormatoE = iRengUltimo;
        else
          iRengFormatoE++;

        if(iRengTemp < iRengUltimo)
          iRengTemp = iRengUltimo;
        else
          iRengTemp++;
      }
      //Datos Instructivo
      Vector vcData4 = findByCustom("",
          "SELECT ICVEFORMATO, INUMINSTRUCTIVO, CDSCINSTRUCTIVO, IORDEN " +
                                    "FROM  DPOINSTRUCTIVOF " +
                                    "where ICVEFORMATO = " + iCveFormatoE +
                                    " order by iOrden ");
      int iRengIns = (iRengFormatoE > iRengTemp)?iRengFormatoE:iRengTemp;
      iRengIns += 2;
      rep.comDespliegaCombinado("INSTRUCTIVO","B",iRengIns,"D",
                                iRengIns,rep.getAT_COMBINA_IZQUIERDA(),"",true,
                                iFondoCeldaPrin,false,true,iBorde,1);
      iRengIns++;
      for(int k = 0;k < vcData4.size();k++){
        TVDinRep vDatosInstructivoE = (TVDinRep) vcData4.get(k);
        cOrdenE = vDatosInstructivoE.getString("IORDEN");
        cInstructivoE = vDatosInstructivoE.getString("CDSCINSTRUCTIVO");
        rep.comDespliegaAlineado("B",iRengIns,"'(" + cOrdenE + ")",false,
                                 rep.getAT_HCENTRO(),"");
        rep.comDespliegaCombinado(cInstructivoE,"C",iRengIns,"K",
                                  iRengIns,rep.getAT_COMBINA_IZQUIERDA(),
                                  "",false,iFondoCeldaPrin,false,true,iBorde,1);
        iRengIns++;
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

  public StringBuffer FormatoI(int iCveFormatoI,int iEjercicio,int iCveTitulo,
                           int iCveTitular) throws Exception{
  String cInstructivoFI = "";
  String cOrdenFI = "";
  String dVariacionAcumuladoFI = "";
  String dVariacionDosFI = "";
  String dVariacionUnoFI = "";
  String dAvanceRealDoaFI = "";
  String dAvanceRealUnoFI = "";
  String cObsDetalleFI = "";
  String dSegundoSemestreFI = "";
  String dPrimerSemestreFI = "";
  String dMetaAnualFI = "";
  String cIndicadorFI = "";
  String cCodigoIndicadorFI = "";
  int iRengDetalleFI = 0;
  int iRengFormatoFI = 0;
  String cDscTempFI = "";
  String cDscBscFI = "";
  String cObejtivoFI = "";
  String cCodigoObejtivoFI = "";
  int iNumObjetivoFI = 0;
  int iCveBalanceDscFI = 0;
  String cDscFormatoFI = "";
  String cDscTemp = "";
  cError = "";
  int iBorde = 1,
        iFondoCeldaPrin = -1;

    StringBuffer sbRetorno = new StringBuffer("");

    try{
      rep.iniciaReporte();
      rep.comEligeHoja(2);

      Vector vcData = findByCustom("",
          "SELECT ICVEFORMATO, CDSCFORMATO,CDSCSECCION,LVIGENTE " +
                                   "FROM DPOFORMATOS " +
                                   "WHERE ICVEFORMATO = " + iCveFormatoI);

      TVDinRep vNomReporteFI = (TVDinRep) vcData.get(0);
      cDscFormatoFI = vNomReporteFI.getString("CDSCFORMATO");

      rep.comDespliegaCombinado(cDscFormatoFI,"B",1,"O",1,rep.getAT_COMBINA_CENTRO(),"",true,iFondoCeldaPrin,true,true,iBorde,1);

      Vector vcData1 = findByCustom("","SELECT " +
                                    "DPOFORMATOCPOA.ICVEBALANCEDSC as ICVEBALANCEDSC, " +
                                    "INUMOBJETIVO, " +
                                    "CCODIGOOBJETIVO, " +
                                    "COBJETIVO, " +
                                    "DPOBALANCEDSC.CDSCBSC as cDscBsc " +
                                    "FROM DPOFORMATOCPOA " +
                                    "join DPOBALANCEDSC on DPOFORMATOCPOA.ICVEBALANCEDSC = DPOBALANCEDSC.ICVEBALANCEDSC "+
                                    "where IEJERCICIOPOA = " + iEjercicio +
                                    " and ICVETITULO = " + iCveTitulo +
                                    " and ICVETITULAR = " + iCveTitular +
                                    " order by ICVEBALANCEDSC");

      iRengFormatoFI = 6;
      iRengDetalleFI = 6;
      int conRengAnt = 0;

      for(int i = 0;i < vcData1.size();i++){
        TVDinRep vFormatoFI = (TVDinRep) vcData1.get(i);
        if(i>0){
          TVDinRep vFormatoCtemp = (TVDinRep) vcData1.get(i - 1);
          cDscTempFI = vFormatoCtemp.getString("cDscBsc");
        }

        iCveBalanceDscFI = vFormatoFI.getInt("ICVEBALANCEDSC");
        iNumObjetivoFI = vFormatoFI.getInt("INUMOBJETIVO");
        cCodigoObejtivoFI = vFormatoFI.getString("CCODIGOOBJETIVO");
        cObejtivoFI = vFormatoFI.getString("COBJETIVO");
        cDscBscFI = vFormatoFI.getString("cDscBsc");

//        if(!cDscBscC.equalsIgnoreCase(cDscTemp))
//          rep.comDespliegaAlineado("B",iRengFormatoFI,cDscBscC,false,rep.getAT_HIZQUIERDA(),"");
        rep.comDespliegaAlineado("C",iRengFormatoFI,cCodigoObejtivoFI,false,rep.getAT_HCENTRO(),"");
        rep.comDespliegaAlineado("D",iRengFormatoFI,cObejtivoFI,false,rep.getAT_HIZQUIERDA(),"");


        Vector vcData2 = findByCustom("","SELECT " +
                                      "CCODIGOINDICADOR, " +
                                      "CINDICADOR, " +
                                      "dMetaAnual as cMetaAnual, " +
                                      "dPrimerSemestre as cPrimerSemestre, " +
                                      "dSegundoSemestre as cSegundoSemestre, " +
                                      "dPrimerSemestreReal as cPrimerSemestreReal, " +
                                      "dSegundoSemestreReal as cSegundoSemestreReal, " +
                                      "DVARIACIONPRIMERSEM, " +
                                      "DVARIACIONSEGUNDOSEM, " +
                                      "DVARIACIONACUMULADO, " +
                                      "COBSREGISTRADA " +
                                      "FROM DPODETALLECPOA " +
                                      "join GRLOBSERVACION on DPODETALLECPOA.IEJERCICIOSEG = GRLOBSERVACION.IEJERCICIO " +
                                      "and DPODETALLECPOA.ICVEOBSERVACIONSEG = GRLOBSERVACION.ICVEOBSERVACION " +
                                      "where IEJERCICIOPOA = " + iEjercicio +
                                      " and icvetitulo = " + iCveTitulo +
                                      " and iCveTitular = " + iCveTitular +
                                      " and iCveBalancedSC = " +iCveBalanceDscFI +
                                      " and iNumObjetivo = " + iNumObjetivoFI +
                                      " order by CCODIGOINDICADOR");

        if(vcData2.size() > 0){
          for(int j = 0;j < vcData2.size();j++){
            TVDinRep vDetalleFI = (TVDinRep) vcData2.get(j);
            cCodigoIndicadorFI = vDetalleFI.getString("CCODIGOINDICADOR");
            cIndicadorFI = vDetalleFI.getString("CINDICADOR");
            dMetaAnualFI = vDetalleFI.getString("cMetaAnual");
            dPrimerSemestreFI = vDetalleFI.getString("cPrimerSemestre");
            dSegundoSemestreFI = vDetalleFI.getString("cSegundoSemestre");
            dAvanceRealUnoFI = vDetalleFI.getString("cPrimerSemestreReal");
            dAvanceRealDoaFI = vDetalleFI.getString("cSegundoSemestreReal");
            dVariacionUnoFI = vDetalleFI.getString("DVARIACIONPRIMERSEM");
            dVariacionDosFI = vDetalleFI.getString("DVARIACIONSEGUNDOSEM");
            dVariacionAcumuladoFI = vDetalleFI.getString("DVARIACIONACUMULADO");
            cObsDetalleFI = vDetalleFI.getString("COBSREGISTRADA");

            rep.comDespliegaAlineado("E",iRengDetalleFI,cCodigoIndicadorFI,false,rep.getAT_HCENTRO(),"");
            rep.comDespliegaAlineado("F",iRengDetalleFI,cIndicadorFI,false,rep.getAT_HIZQUIERDA(),"");
            rep.comDespliegaAlineado("G",iRengDetalleFI,dMetaAnualFI,false,rep.getAT_HDERECHA(),"");
            rep.comDespliegaAlineado("H",iRengDetalleFI,dPrimerSemestreFI,false,rep.getAT_HDERECHA(),"");
            rep.comDespliegaAlineado("I",iRengDetalleFI,dSegundoSemestreFI,false,rep.getAT_HDERECHA(),"");
            rep.comDespliegaAlineado("J",iRengDetalleFI,dAvanceRealUnoFI,false,rep.getAT_HDERECHA(),"");
            rep.comDespliegaAlineado("K",iRengDetalleFI,dAvanceRealDoaFI,false,rep.getAT_HDERECHA(),"");
            rep.comDespliegaAlineado("L",iRengDetalleFI,dVariacionUnoFI +" %",false,rep.getAT_HDERECHA(),"");
            rep.comDespliegaAlineado("M",iRengDetalleFI,dVariacionDosFI +" %",false,rep.getAT_HDERECHA(),"");
            rep.comDespliegaAlineado("N",iRengDetalleFI,dVariacionAcumuladoFI +" %",false,rep.getAT_HDERECHA(),"");
            rep.comDespliegaAlineado("O",iRengDetalleFI,cObsDetalleFI,false,rep.getAT_HIZQUIERDA(),"");
            rep.comBordeRededor("E",iRengDetalleFI,"E",iRengDetalleFI,iBorde,1);
            rep.comBordeRededor("F",iRengDetalleFI,"F",iRengDetalleFI,iBorde,1);
            rep.comBordeRededor("G",iRengDetalleFI,"G",iRengDetalleFI,iBorde,1);
            rep.comBordeRededor("H",iRengDetalleFI,"H",iRengDetalleFI,iBorde,1);
            rep.comBordeRededor("I",iRengDetalleFI,"I",iRengDetalleFI,iBorde,1);
            rep.comBordeRededor("J",iRengDetalleFI,"J",iRengDetalleFI,iBorde,1);
            rep.comBordeRededor("K",iRengDetalleFI,"K",iRengDetalleFI,iBorde,1);
            rep.comBordeRededor("L",iRengDetalleFI,"L",iRengDetalleFI,iBorde,1);
            rep.comBordeRededor("M",iRengDetalleFI,"M",iRengDetalleFI,iBorde,1);
            rep.comBordeRededor("N",iRengDetalleFI,"N",iRengDetalleFI,iBorde,1);
            rep.comBordeRededor("O",iRengDetalleFI,"O",iRengDetalleFI,iBorde,1);
            iRengDetalleFI++;
          }
          int tempC = 0;
          if(iRengFormatoFI < iRengDetalleFI){
            tempC = iRengDetalleFI - iRengFormatoFI;
            if(tempC > 1){
              rep.comBordeRededor("B",iRengFormatoFI,"B",iRengDetalleFI - 1,iBorde,1);
              rep.comBordeRededor("C",iRengFormatoFI,"C",iRengDetalleFI - 1,iBorde,1);
              rep.comBordeRededor("D",iRengFormatoFI,"D",iRengDetalleFI - 1,iBorde,1);
            }else{
              rep.comBordeRededor("B",iRengFormatoFI,"B",iRengFormatoFI,iBorde,1);
              rep.comBordeRededor("C",iRengFormatoFI,"C",iRengFormatoFI,iBorde,1);
              rep.comBordeRededor("D",iRengFormatoFI,"D",iRengFormatoFI,iBorde,1);
            }
          }

        } else{
          rep.comDespliegaAlineado("F",iRengDetalleFI," ",false,rep.getAT_HCENTRO(),"");
          rep.comDespliegaAlineado("G",iRengDetalleFI," ",false,rep.getAT_HCENTRO(),"");
          rep.comDespliegaAlineado("H",iRengDetalleFI," ",false,rep.getAT_HCENTRO(),"");
          rep.comDespliegaAlineado("I",iRengDetalleFI," ",false,rep.getAT_HCENTRO(),"");
          rep.comDespliegaAlineado("J",iRengDetalleFI," ",false,rep.getAT_HCENTRO(),"");
          rep.comDespliegaAlineado("K",iRengDetalleFI," ",false,rep.getAT_HCENTRO(),"");
          rep.comDespliegaAlineado("L",iRengDetalleFI," ",false,rep.getAT_HCENTRO(),"");
          rep.comDespliegaAlineado("M",iRengDetalleFI," ",false,rep.getAT_HCENTRO(),"");
          rep.comDespliegaAlineado("N",iRengDetalleFI," ",false,rep.getAT_HCENTRO(),"");
          rep.comDespliegaAlineado("O",iRengDetalleFI," ",false,rep.getAT_HCENTRO(),"");
          rep.comBordeRededor("F",iRengDetalleFI,"F",iRengDetalleFI,iBorde,1);
          rep.comBordeRededor("G",iRengDetalleFI,"G",iRengDetalleFI,iBorde,1);
          rep.comBordeRededor("H",iRengDetalleFI,"H",iRengDetalleFI,iBorde,1);
          rep.comBordeRededor("I",iRengDetalleFI,"I",iRengDetalleFI,iBorde,1);
          rep.comBordeRededor("J",iRengDetalleFI,"J",iRengDetalleFI,iBorde,1);
          rep.comBordeRededor("K",iRengDetalleFI,"K",iRengDetalleFI,iBorde,1);
          rep.comBordeRededor("L",iRengDetalleFI,"L",iRengDetalleFI,iBorde,1);
          rep.comBordeRededor("M",iRengDetalleFI,"M",iRengDetalleFI,iBorde,1);
          rep.comBordeRededor("N",iRengDetalleFI,"N",iRengDetalleFI,iBorde,1);
          rep.comBordeRededor("O",iRengDetalleFI,"O",iRengDetalleFI,iBorde,1);
//          rep.comBordeRededor("B",iRengFormatoFI,"B",iRengFormatoFI,iBorde,1);
          rep.comBordeRededor("C",iRengFormatoFI,"C",iRengFormatoFI,iBorde,1);
          rep.comBordeRededor("D",iRengFormatoFI,"D",iRengFormatoFI,iBorde,1);
          rep.comBordeRededor("E",iRengFormatoFI,"E",iRengFormatoFI,iBorde,1);

          iRengDetalleFI++;

        }
        if(cDscBscFI.equalsIgnoreCase(cDscTempFI)){
          rep.comDespliegaCombinado(cDscBscFI,"B",conRengAnt,"B",
                                    iRengDetalleFI-1,rep.getAT_COMBINA_IZQUIERDA(),"",false,
                                    iFondoCeldaPrin,true,false,iBorde,1);
        }else{
          rep.comDespliegaAlineado("B",iRengFormatoFI,cDscBscFI,false,rep.getAT_HIZQUIERDA(),"");
          rep.comBordeRededor("B",iRengFormatoFI,"B",iRengDetalleFI - 1,iBorde,1);
        }
        conRengAnt = iRengFormatoFI;

        if(iRengFormatoFI < iRengDetalleFI)
          iRengFormatoFI = iRengDetalleFI;
        else
          iRengFormatoFI++;

      }

      //Datos Instructivo
      Vector vcData4 = findByCustom("",
          "SELECT ICVEFORMATO, INUMINSTRUCTIVO, CDSCINSTRUCTIVO, IORDEN " +
                                    "FROM  DPOINSTRUCTIVOF " +
                                    "where ICVEFORMATO = " + iCveFormatoI +
                                    " order by iOrden ");

      iRengFormatoFI += 2;
      rep.comFontColor("B",iRengFormatoFI,"C",iRengFormatoFI,1);
      rep.comDespliegaCombinado("INSTRUCTIVO","B",iRengFormatoFI,"D",
                                iRengFormatoFI,rep.getAT_COMBINA_IZQUIERDA(),"",true,
                                iFondoCeldaPrin,false,true,iBorde,1);
      iRengFormatoFI++;
      for(int k = 0;k < vcData4.size();k++){
        TVDinRep vDatosInstructivoFI = (TVDinRep) vcData4.get(k);
        cOrdenFI = vDatosInstructivoFI.getString("IORDEN");
        cInstructivoFI = vDatosInstructivoFI.getString("CDSCINSTRUCTIVO");
        rep.comFontColor("B",iRengFormatoFI,"C",iRengFormatoFI,1);
        rep.comDespliegaAlineado("B",iRengFormatoFI,"'(" + cOrdenFI + ")",false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaCombinado(cInstructivoFI,"C",iRengFormatoFI,"O",iRengFormatoFI,rep.getAT_COMBINA_IZQUIERDA(),
                                  "",false,iFondoCeldaPrin,false,true,iBorde,1);
        iRengFormatoFI++;
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


  public StringBuffer FormatoIII(int iCveFormatoIII,int iEjercicio,int iCveTitulo,
                           int iCveTitular) throws Exception{
    String cInstructivoIII = "";
    String cOrdenIII = "";
    double dVariaAcumuladoIII;
    double dVariaCuatroIII;
    double dVariaTresIII;
    double dVariaDosIII;
    double dVariaUnoIII;
    String dCuatroRealIII, dTresRealIII, dDosRealIII, dUnoRealIII, cObsIII = "", dCuartoTriIII, dTresTriIII,
        dDosTriIII, dUnoTriIII, dMetaAnualIII;
    String cIndicadorIII = "";
    String cPerspectivaIII = "";
    int iRengFormatoIII = 0;
    String cDscFormatoIII = "";
    String cDscTemp =
      "";
    cError = "";
    int iReng,iBorde = 1,
        iFondoCeldaPrin = -1;

    StringBuffer sbRetorno = new StringBuffer("");

    try{
      rep.iniciaReporte();
      rep.comEligeHoja(3);

      Vector vcData = findByCustom("",
          "SELECT ICVEFORMATO, CDSCFORMATO,CDSCSECCION,LVIGENTE " +
                                   "FROM DPOFORMATOS " +
                                   "WHERE ICVEFORMATO = " + iCveFormatoIII);

      TVDinRep vNomReporteD = (TVDinRep) vcData.get(0);
      cDscFormatoIII = vNomReporteD.getString("CDSCFORMATO");

      rep.comDespliegaCombinado(cDscFormatoIII,"B",1,"R",1,rep.getAT_COMBINA_CENTRO(),"",true,iFondoCeldaPrin,true,true,iBorde,1);


      Vector vcData2 = findByCustom("","SELECT " +
                                    "DPOBALANCEDSC.CDSCBSC as cPerspectivaD, " +
                                    "DPOINDICADORINS.CDSCINDICADOR as cIndicadorD, " +
                                    "DPOFORMATODPOA.DMETAANUAL as cMetaAnualD, " +
                                    "DPOFORMATODPOA.DPRIMERTRIMESTRE as cUnoTriD, " +
                                    "DPOFORMATODPOA.DSEGUNDOTRIMESTRE as cDosTriD, " +
                                    "DPOFORMATODPOA.DTERCERTRIMESTRE as cTresTriD, " +
                                    "DPOFORMATODPOA.DCUARTOTRIMESTRE as cCuartoTriD, " +
                                    "DPOFORMATODPOA.DPRIMERTRIMESTREREAL as cUnoReal, " +
                                    "DPOFORMATODPOA.DSEGUNDOTRIMESTREREAL as cDosReal, " +
                                    "DPOFORMATODPOA.DTERCERTRIMESTREREAL as cTresReal, " +
                                    "DPOFORMATODPOA.DCUARTOTRIMESTREREAL as cCuatroReal, " +
                                    "DPOFORMATODPOA.DVARIACIONPRIMERTRIM as dVariaUno, " +
                                    "DPOFORMATODPOA.DVARIACIONSEGUNDOTRIM as dVariaDos, " +
                                    "DPOFORMATODPOA.DVARIACIONTERCERTRIM as dVariaTres, " +
                                    "DPOFORMATODPOA.DVARIACIONCUARTOTRIM as dVariaCuatro, " +
                                    "DPOFORMATODPOA.DVARIACIONACUMULADO as dVariaAcumulado, " +
                                    "GRLOBSERVACION.COBSREGISTRADA as cObsD, " +
                                    "DPOFORMATODPOA.ICVEBALANCEDSC as iCveBalan, " +
                                    "DPOFORMATODPOA.INUMINDICADORINS as iNumInd " +
                                    "FROM DPOFORMATODPOA " +
                                    "join DPOINDICADORINS on DPOFORMATODPOA.ICVEBALANCEDSC = DPOINDICADORINS.ICVEBALANCEDSC " +
                                    "and DPOFORMATODPOA.INUMINDICADORINS = DPOINDICADORINS.INUMINDICADORINS " +
                                    "join DPOBALANCEDSC on DPOINDICADORINS.ICVEBALANCEDSC = DPOBALANCEDSC.ICVEBALANCEDSC " +
                                    "join GRLOBSERVACION on DPOFORMATODPOA.IEJERCICIOSEG = GRLOBSERVACION.IEJERCICIO " +
                                    "and DPOFORMATODPOA.ICVEOBSERVACIONSEG = GRLOBSERVACION.ICVEOBSERVACION " +
                                    "where DPOFORMATODPOA.IEJERCICIOPOA = " +iEjercicio +
                                    " and DPOFORMATODPOA.ICVETITULO = " +iCveTitulo +
                                    " and DPOFORMATODPOA.ICVETITULAR = " +iCveTitular);
      iRengFormatoIII = 6;

      for(int i = 0;i < vcData2.size();i++){
        TVDinRep vFormatoIII = (TVDinRep) vcData2.get(i);
        if(i>0){
                  TVDinRep vFormatoIIItemp = (TVDinRep) vcData2.get(i - 1);
                  cDscTemp = vFormatoIIItemp.getString("cPerspectivaD");
        }

        cPerspectivaIII = vFormatoIII.getString("cPerspectivaD");
        cIndicadorIII = vFormatoIII.getString("cIndicadorD");
        dMetaAnualIII = vFormatoIII.getString("cMetaAnualD");
        dUnoTriIII = vFormatoIII.getString("cUnoTriD");
        dDosTriIII = vFormatoIII.getString("cDosTriD");
        dTresTriIII = vFormatoIII.getString("cTresTriD");
        dCuartoTriIII = vFormatoIII.getString("cCuartoTriD");
        dUnoRealIII = vFormatoIII.getString("cUnoReal");
        dDosRealIII = vFormatoIII.getString("cDosReal");
        dTresRealIII = vFormatoIII.getString("cTresReal");
        dCuatroRealIII = vFormatoIII.getString("cCuatroReal");
        dVariaUnoIII = vFormatoIII.getDouble("dVariaUno");
        dVariaDosIII = vFormatoIII.getDouble("dVariaDos");
        dVariaTresIII = vFormatoIII.getDouble("dVariaTres");
        dVariaCuatroIII = vFormatoIII.getDouble("dVariaCuatro");
        dVariaAcumuladoIII = vFormatoIII.getDouble("dVariaAcumulado");;
        cObsIII = vFormatoIII.getString("cObsD");

        rep.comDespliegaAlineado("C",iRengFormatoIII,cIndicadorIII,false,rep.getAT_HIZQUIERDA(),"");
        rep.comDespliegaAlineado("D",iRengFormatoIII,dMetaAnualIII,false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaAlineado("E",iRengFormatoIII,dUnoTriIII,false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaAlineado("F",iRengFormatoIII,dDosTriIII,false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaAlineado("G",iRengFormatoIII,dTresTriIII,false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaAlineado("H",iRengFormatoIII,dCuartoTriIII,false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaAlineado("I",iRengFormatoIII,(dUnoRealIII.equals("null") || dUnoRealIII.equals(""))?"-":dUnoRealIII,false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaAlineado("J",iRengFormatoIII,(dDosRealIII.equals("null") || dDosRealIII.equals(""))?"-":dDosRealIII,false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaAlineado("K",iRengFormatoIII,(dTresRealIII.equals("null") || dTresRealIII.equals(""))?"-":dTresRealIII,false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaAlineado("L",iRengFormatoIII,(dCuatroRealIII.equals("null") || dCuatroRealIII.equals(""))?"-":dCuatroRealIII,false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaAlineado("M",iRengFormatoIII,"" + dVariaUnoIII + " %",false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaAlineado("N",iRengFormatoIII,"" + dVariaDosIII + " %",false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaAlineado("O",iRengFormatoIII,"" + dVariaTresIII + " %",false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaAlineado("P",iRengFormatoIII,"" + dVariaCuatroIII + " %",false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaAlineado("Q",iRengFormatoIII,"" + dVariaAcumuladoIII + " %",false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaAlineado("R",iRengFormatoIII,cObsIII,false,rep.getAT_HIZQUIERDA(),"");
        rep.comBordeRededor("B",iRengFormatoIII,"B",iRengFormatoIII,iBorde,1);
        rep.comBordeRededor("C",iRengFormatoIII,"C",iRengFormatoIII,iBorde,1);
        rep.comBordeRededor("D",iRengFormatoIII,"D",iRengFormatoIII,iBorde,1);
        rep.comBordeRededor("E",iRengFormatoIII,"E",iRengFormatoIII,iBorde,1);
        rep.comBordeRededor("F",iRengFormatoIII,"F",iRengFormatoIII,iBorde,1);
        rep.comBordeRededor("G",iRengFormatoIII,"G",iRengFormatoIII,iBorde,1);
        rep.comBordeRededor("H",iRengFormatoIII,"H",iRengFormatoIII,iBorde,1);
        rep.comBordeRededor("I",iRengFormatoIII,"I",iRengFormatoIII,iBorde,1);
        rep.comBordeRededor("J",iRengFormatoIII,"J",iRengFormatoIII,iBorde,1);
        rep.comBordeRededor("K",iRengFormatoIII,"K",iRengFormatoIII,iBorde,1);
        rep.comBordeRededor("L",iRengFormatoIII,"L",iRengFormatoIII,iBorde,1);
        rep.comBordeRededor("M",iRengFormatoIII,"M",iRengFormatoIII,iBorde,1);
        rep.comBordeRededor("N",iRengFormatoIII,"N",iRengFormatoIII,iBorde,1);
        rep.comBordeRededor("O",iRengFormatoIII,"O",iRengFormatoIII,iBorde,1);
        rep.comBordeRededor("P",iRengFormatoIII,"P",iRengFormatoIII,iBorde,1);
        rep.comBordeRededor("Q",iRengFormatoIII,"Q",iRengFormatoIII,iBorde,1);
        rep.comBordeRededor("R",iRengFormatoIII,"R",iRengFormatoIII,iBorde,1);

        Vector vcDataDetalle = findByCustom("","SELECT " +
                                            "DET.CNOMDETALLEINDICADOR as cNomDetalleInd, " +
                                            "DET.DMETAANUAL AS cMETANUAL, " +
                                            "DET.DPRIMERTRIMESTRE AS cUNOTRI, " +
                                            "DET.DSEGUNDOTRIMESTRE AS cDOSTRI, " +
                                            "DET.DTERCERTRIMESTRE AS cTRESTRI, " +
                                            "DET.DCUARTOTRIMESTRE AS cCUATROTRI, " +
                                            "DET.DPRIMERTRIMESTREREAL AS cUNOTRIREAL, " +
                                            "DET.DSEGUNDOTRIMESTREREAL AS cDOSTRIREAL, " +
                                            "DET.DTERCERTRIMESTREREAL AS cTRESTRIREAL, " +
                                            "DET.DCUARTOTRIMESTREREAL AS cCUATROTRIREAL, " +
                                            "DET.DVARIACIONPRIMERTRIM AS DVARIAUNO, " +
                                            "DET.DVARIACIONSEGUNDOTRIM AS DVARIADOS, " +
                                            "DET.DVARIACIONTERCERTRIM AS DVARIATRES, " +
                                            "DET.DVARIACIONCUARTOTRIM AS DVARIACUATRO, " +
                                            "DET.DVARIACIONACUMULADO AS DVARIAACUM, " +
                                            "OBS.COBSREGISTRADA AS COBS " +
                                            "FROM DPODETALLEDPOA DET " +
                                            "JOIN GRLOBSERVACION  OBS ON DET.IEJERCICIOMETAREGISTRO = OBS.IEJERCICIO " +
                                            "AND DET.ICVEOBSMETAREGISTRO = OBS.ICVEOBSERVACION "+
                                            "WHERE IEJERCICIOPOA = "+iEjercicio +
                                            " AND ICVETITULO = "+iCveTitulo +
                                            " AND ICVETITULAR = "+iCveTitular+
                                            " AND ICVEBALANCEDSC = "+vFormatoIII.getInt("iCveBalan")+
                                            " AND INUMINDICADORINS = "+vFormatoIII.getInt("iNumInd"));
        int iContDet = 0;
        for(int j=0; j<vcDataDetalle.size(); j++){
          iRengFormatoIII += 1;
          TVDinRep vDetalleD = (TVDinRep) vcDataDetalle.get(j);

          rep.comDespliegaAlineado("C",iRengFormatoIII,"   " + vDetalleD.getString("cNomDetalleInd"),false,rep.getAT_HIZQUIERDA(),"");
          rep.comDespliegaAlineado("D",iRengFormatoIII,vDetalleD.getString("cMETANUAL"),false,rep.getAT_HDERECHA(),"");
          rep.comDespliegaAlineado("E",iRengFormatoIII,vDetalleD.getString("cUNOTRI"),false,rep.getAT_HDERECHA(),"");
          rep.comDespliegaAlineado("F",iRengFormatoIII,vDetalleD.getString("cDOSTRI"),false,rep.getAT_HDERECHA(),"");
          rep.comDespliegaAlineado("G",iRengFormatoIII,vDetalleD.getString("cTRESTRI"),false,rep.getAT_HDERECHA(),"");
          rep.comDespliegaAlineado("H",iRengFormatoIII,vDetalleD.getString("cCUATROTRI"),false,rep.getAT_HDERECHA(),"");
          rep.comDespliegaAlineado("I",iRengFormatoIII,(vDetalleD.getString("cUNOTRIREAL").equals("null") ||
                                                        vDetalleD.getString("cUNOTRIREAL").equals(""))?"-":vDetalleD.getString("cUNOTRIREAL"),false,rep.getAT_HDERECHA(),"");
          rep.comDespliegaAlineado("J",iRengFormatoIII,(vDetalleD.getString("cDOSTRIREAL").equals("null") ||
                                                        vDetalleD.getString("cDOSTRIREAL").equals(""))?"-":vDetalleD.getString("cDOSTRIREAL"),false,rep.getAT_HDERECHA(),"");
          rep.comDespliegaAlineado("K",iRengFormatoIII,(vDetalleD.getString("cTRESTRIREAL").equals("null") ||
                                                        vDetalleD.getString("cTRESTRIREAL").equals(""))?"-":vDetalleD.getString("cTRESTRIREAL"),false,rep.getAT_HDERECHA(),"");
          rep.comDespliegaAlineado("L",iRengFormatoIII,(vDetalleD.getString("cCUATROTRIREAL").equals("null") ||
                                                        vDetalleD.getString("cCUATROTRIREAL").equals(""))?"-":vDetalleD.getString("cCUATROTRIREAL"),false,rep.getAT_HDERECHA(),"");
          rep.comDespliegaAlineado("M",iRengFormatoIII,vDetalleD.getString("DVARIAUNO") + " %",false,rep.getAT_HDERECHA(),"");
          rep.comDespliegaAlineado("N",iRengFormatoIII,vDetalleD.getString("DVARIADOS") + " %",false,rep.getAT_HDERECHA(),"");
          rep.comDespliegaAlineado("O",iRengFormatoIII,vDetalleD.getString("DVARIATRES") + " %",false,rep.getAT_HDERECHA(),"");
          rep.comDespliegaAlineado("P",iRengFormatoIII,vDetalleD.getString("DVARIACUATRO") + " %",false,rep.getAT_HDERECHA(),"");
          rep.comDespliegaAlineado("Q",iRengFormatoIII,vDetalleD.getString("DVARIAACUM") + " %",false,rep.getAT_HDERECHA(),"");
          rep.comDespliegaAlineado("R",iRengFormatoIII,vDetalleD.getString("COBS"),false,rep.getAT_HIZQUIERDA(),"");
          rep.comBordeRededor("B",iRengFormatoIII,"B",iRengFormatoIII,iBorde,1);
          rep.comBordeRededor("C",iRengFormatoIII,"C",iRengFormatoIII,iBorde,1);
          rep.comBordeRededor("D",iRengFormatoIII,"D",iRengFormatoIII,iBorde,1);
          rep.comBordeRededor("E",iRengFormatoIII,"E",iRengFormatoIII,iBorde,1);
          rep.comBordeRededor("F",iRengFormatoIII,"F",iRengFormatoIII,iBorde,1);
          rep.comBordeRededor("G",iRengFormatoIII,"G",iRengFormatoIII,iBorde,1);
          rep.comBordeRededor("H",iRengFormatoIII,"H",iRengFormatoIII,iBorde,1);
          rep.comBordeRededor("I",iRengFormatoIII,"I",iRengFormatoIII,iBorde,1);
          rep.comBordeRededor("J",iRengFormatoIII,"J",iRengFormatoIII,iBorde,1);
          rep.comBordeRededor("K",iRengFormatoIII,"K",iRengFormatoIII,iBorde,1);
          rep.comBordeRededor("L",iRengFormatoIII,"L",iRengFormatoIII,iBorde,1);
          rep.comBordeRededor("M",iRengFormatoIII,"M",iRengFormatoIII,iBorde,1);
          rep.comBordeRededor("N",iRengFormatoIII,"N",iRengFormatoIII,iBorde,1);
          rep.comBordeRededor("O",iRengFormatoIII,"O",iRengFormatoIII,iBorde,1);
          rep.comBordeRededor("P",iRengFormatoIII,"P",iRengFormatoIII,iBorde,1);
          rep.comBordeRededor("Q",iRengFormatoIII,"Q",iRengFormatoIII,iBorde,1);
          rep.comBordeRededor("R",iRengFormatoIII,"R",iRengFormatoIII,iBorde,1);

          iContDet++;
        }


        if(cPerspectivaIII.equalsIgnoreCase(cDscTemp)){

          rep.comDespliegaCombinado(cPerspectivaIII,"B",iRengFormatoIII-1-iContDet,"B",iRengFormatoIII,rep.getAT_COMBINA_IZQUIERDA(),"",false,
                                    iFondoCeldaPrin,true,false,iBorde,1);

        }else
          rep.comDespliegaCombinado(cPerspectivaIII,"B",iRengFormatoIII-iContDet,"B",iRengFormatoIII,rep.getAT_COMBINA_IZQUIERDA(),"",false,
                                    iFondoCeldaPrin,true,false,iBorde,1);



        iRengFormatoIII++;
      }
      //Datos Instructivo
      Vector vcData3 = findByCustom("",
          "SELECT ICVEFORMATO, INUMINSTRUCTIVO, CDSCINSTRUCTIVO, IORDEN " +
                                    "FROM  DPOINSTRUCTIVOF " +
                                    "where ICVEFORMATO = " + iCveFormatoIII +
                                    " order by iOrden ");

      iRengFormatoIII += 2;
      rep.comDespliegaCombinado("INSTRUCTIVO","B",iRengFormatoIII,"C",iRengFormatoIII,rep.getAT_COMBINA_IZQUIERDA(),"",true,
                                iFondoCeldaPrin,false,true,iBorde,1);
      iRengFormatoIII++;
      for(int k = 0;k < vcData3.size();k++){
        TVDinRep vDatosInstructivoIII = (TVDinRep) vcData3.get(k);
        cOrdenIII = vDatosInstructivoIII.getString("IORDEN");
        cInstructivoIII = vDatosInstructivoIII.getString("CDSCINSTRUCTIVO");
        rep.comFontColor("B",iRengFormatoIII,"I",iRengFormatoIII,1);
        rep.comDespliegaAlineado("B",iRengFormatoIII,"'(" + cOrdenIII + ")",false,rep.getAT_HDERECHA(),"");
        rep.comDespliegaCombinado(cInstructivoIII,"C",iRengFormatoIII,"I",iRengFormatoIII,rep.getAT_COMBINA_IZQUIERDA(),
                                  "",false,iFondoCeldaPrin,false,true,iBorde,1);
        iRengFormatoIII++;
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


  public StringBuffer GenInfoReg(String cFiltros) throws Exception{
    Date dtRegistroReg = null;
    int iEstatus;
    int iEst0 = 0;
    int iEst1 = 0;
    int iEst2 = 0;
    int iSinReg = 0;
    int iEjercicioReg;
    int iCveTituloReg;
    int iCveTitularReg ;
    Date dtFechaRecepcion = null;
    String cAPI = "";
    String cFiltro = "";
    String lTodas;
    String cEstatus;
    float fLetra = 9.0F;
    cError = "";
    int iReng,iBorde = 1,
        iFondoCeldaPrin = -1;

    String[] aFiltros = cFiltros.split(",");


    lTodas = aFiltros[0];
    iEjercicioReg = Integer.parseInt(aFiltros[1],10);
    iCveTituloReg = Integer.parseInt(aFiltros[2],10);
    iCveTitularReg = Integer.parseInt(aFiltros[3],10);

    StringBuffer sbRetorno = new StringBuffer("");

    try{
      rep.iniciaReporte();
      rep.comEligeHoja(2);

      if(lTodas.equalsIgnoreCase("false")){
        cFiltro = "where DPOFORMATOAPOA.IEJERCICIOPOA = " + iEjercicioReg +
            " and DPOFORMATOAPOA.ICVETITULO = " + iCveTituloReg +
            " and DPOFORMATOAPOA.ICVETITULAR = " + iCveTitularReg;
      }else
        cFiltro = "and DPOFORMATOAPOA.IEJERCICIOPOA = " + iEjercicioReg;
      rep.comDespliegaCombinado("REGISTRO DE LOS PROGRAMAS OPERATIVOS ANUALES "+iEjercicioReg,"A",8,"E",8,rep.getAT_COMBINA_CENTRO(),"",false,iFondoCeldaPrin,false,false,iBorde,1);

      Vector vcData = findByCustom("",
                                   "SELECT " +
                                   "GRLPERSONA.CNOMRAZONSOCIAL || ' ' || GRLPERSONA.CAPPATERNO || ' ' || GRLPERSONA.CAPMATERNO as cnombre, " +
                                   "DPOFORMATOAPOA.DTENVIOPOA as dtEnv, " +
                                   "DPOFORMATOAPOA.IESTATUSPOA as cEstatus, " +
                                   "DPOFORMATOAPOA.DTREGISTROPOA as dtRegistro, " +
                                   "TPA.CDSCTIPOPARTACC as CDSCTIPOPARTACC, " +
                                   "pto.ICVEPUERTO, " +
                                   "e.CABREVIATURA as cAbreviatura, ef.CABREVIATURA as cAbrevPto " +
                                   "FROM DPOUSUARIOTITULO " +
                                   "join GRLPERSONA on GRLPERSONA.ICVEPERSONA = DPOUSUARIOTITULO.ICVETITULAR " +
                                   "left join CPATITULO T on DPOUSUARIOTITULO.ICVETITULO = T.ICVETITULO " +
                                   "left join CPATIPOPARTACC TPA on t.ICVETIPOPARTACC = TPA.ICVETIPOPARTACC " +
                                   "  JOIN CPATITULOUBICACION U ON U.ICVETITULO = DPOUSUARIOTITULO.ICVETITULO " +
                                   "LEFT JOIN GRLENTIDADFED E ON U.ICVEPAIS = E.ICVEPAIS " +
                                   "    AND E.ICVEENTIDADFED = U.ICVEENTIDADFED " +
                                   "LEFT JOIN GRLPUERTO PTO ON PTO.ICVEPUERTO = U.ICVEPUERTO " +
                                   "LEFT JOIN GRLENTIDADFED EF ON PTO.ICVEPAIS = EF.ICVEPAIS " +
                                   "    AND EF.ICVEENTIDADFED = PTO.ICVEENTIDADFED " +
                                   "left join DPOFORMATOAPOA on DPOUSUARIOTITULO.ICVETITULO = DPOFORMATOAPOA.ICVETITULO " +
                                   "and DPOUSUARIOTITULO.ICVETITULAR = DPOFORMATOAPOA.ICVETITULAR " +
                                   cFiltro);
      iReng = 14;

      for(int i = 0;i < vcData.size();i++){
        TVDinRep vInfoReg = (TVDinRep) vcData.get(i);
        cAPI = vInfoReg.getString("cnombre");
        if(vInfoReg.getInt("ICVEPUERTO")>0)
          cAPI += " - " + vInfoReg.getString("cAbrevPto");
          else cAPI += " - " + vInfoReg.getString("cAbreviatura");
        dtFechaRecepcion = vInfoReg.getDate("dtEnv");
        cEstatus = vInfoReg.getString("cEstatus");
        if(!cEstatus.equalsIgnoreCase("null"))
          iEstatus = Integer.parseInt(cEstatus);
        else
          iEstatus = 3;

        dtRegistroReg = vInfoReg.getDate("dtRegistro");

        rep.comDespliegaAlineado("A",iReng,cAPI,false,rep.getAT_HIZQUIERDA(),"");
        if(vInfoReg.getString("CDSCTIPOPARTACC")!=null && !vInfoReg.getString("CDSCTIPOPARTACC").equals("null"))
           rep.comDespliegaAlineado("B",iReng,vInfoReg.getString("CDSCTIPOPARTACC"),false,rep.getAT_HIZQUIERDA(),"");
        if(dtFechaRecepcion != null)
          rep.comDespliegaAlineado("C",iReng,""+dtFechaRecepcion,false,rep.getAT_HCENTRO(),"");
        if(iEstatus == 0){
          rep.comDespliegaAlineado("D",iReng,"EN ELABORACIÓN",false,rep.getAT_HCENTRO(),"");
          iEst0++;
        }
        if(iEstatus == 1){
          rep.comDespliegaAlineado("D",iReng,"PEND. REGISTRAR",false,rep.getAT_HCENTRO(),"");
          iEst1++;
        }
        if(iEstatus == 2){
          rep.comDespliegaAlineado("D",iReng,"REGISTRADO",false,rep.getAT_HCENTRO(),"");
          iEst2++;
        }
        if(iEstatus == 3){
          rep.comDespliegaAlineado("D",iReng,"SIN REGISTRAR",false,rep.getAT_HCENTRO(),"");
          iSinReg++;
        }
        if(dtRegistroReg != null)
          rep.comDespliegaAlineado("E",iReng,""+dtRegistroReg,false,rep.getAT_HCENTRO(),"");


        rep.comBordeRededor("A",iReng,"A",iReng,iBorde,1);
        rep.comBordeRededor("B",iReng,"B",iReng,iBorde,1);
        rep.comBordeRededor("C",iReng,"C",iReng,iBorde,1);
        rep.comBordeRededor("D",iReng,"D",iReng,iBorde,1);
        rep.comBordeRededor("E",iReng,"E",iReng,iBorde,1);
        iReng++;
      }


      if(vcData.size()>0){
        iReng += 2;
        rep.comDespliegaAlineado("B",iReng,"SIN REGISTRAR",true,rep.getAT_HCENTRO(),"");
        rep.comDespliegaAlineado("C",iReng,"" + iSinReg,false,rep.getAT_HCENTRO(),"");
        rep.comDespliegaAlineado("B",iReng + 1,"EN ELABORACIÓN",true,rep.getAT_HCENTRO(),"");
        rep.comDespliegaAlineado("C",iReng + 1,"" + iEst0,false,rep.getAT_HCENTRO(),"");
        rep.comDespliegaAlineado("B",iReng + 2,"POR REGISTRAR",true,rep.getAT_HCENTRO(),"");
        rep.comDespliegaAlineado("C",iReng + 2,"" + iEst1,false,rep.getAT_HCENTRO(),"");
        rep.comDespliegaAlineado("B",iReng + 3,"REGISTRADO",true,rep.getAT_HCENTRO(),"");
        rep.comDespliegaAlineado("C",iReng + 3,"" + iEst2,false,rep.getAT_HCENTRO(),"");
        rep.comDespliegaAlineado("B",iReng + 4,"TOTAL",true,rep.getAT_HCENTRO(),"");
        rep.comDespliegaAlineado("C",iReng + 4,"" + (iEst2 + iEst1 + iEst0 + iSinReg),false,rep.getAT_HCENTRO(),"");
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

  public StringBuffer GenInfoSeg(String cFiltros) throws Exception{
    Date dtEnvio1S = null;
    Date dtEnvio2S = null;
    Date dtEnvio1T = null;
    Date dtEnvio2T = null;
    Date dtEnvio3T = null;
    Date dtEnvio4T = null;
    Date dtAprob1S = null;
    Date dtAprob2S = null;
    Date dtAprob1T = null;
    Date dtAprob2T = null;
    Date dtAprob3T = null;
    Date dtAprob4T = null;
    int iEjercicioSeg;
    int iCveTituloSeg;
    int iCveTitularSeg ;
    String cAPISeg = "";
    String cFiltro = "";
    String lTodas;
    float fLetra = 9.0F;
    cError = "";
    int iReng,iBorde = 1,
        iFondoCeldaPrin = -1;

    String[] aFiltros = cFiltros.split(",");


    lTodas = aFiltros[0];
    iEjercicioSeg = Integer.parseInt(aFiltros[1],10);
    iCveTituloSeg = Integer.parseInt(aFiltros[2],10);
    iCveTitularSeg = Integer.parseInt(aFiltros[3],10);

    StringBuffer sbRetorno = new StringBuffer("");

    try{
      rep.iniciaReporte();
      rep.comEligeHoja(2);


      if(lTodas.equalsIgnoreCase("false")){
        cFiltro = "where DPOFORMATOAPOA.IEJERCICIOPOA = " + iEjercicioSeg +
            " and DPOFORMATOAPOA.ICVETITULO = " + iCveTituloSeg +
            " and DPOFORMATOAPOA.ICVETITULAR = " + iCveTitularSeg;
      }else{
        cFiltro = "and DPOFORMATOAPOA.IEJERCICIOPOA = " + iEjercicioSeg;
      }

      rep.comDespliegaCombinado("SEGUIMIENTO DE LOS PROGRAMAS OPERATIVOS ANUALES "+iEjercicioSeg,"A",8,"M",8,rep.getAT_COMBINA_CENTRO(),"",false,iFondoCeldaPrin,false,false,iBorde,1);

      Vector vcData = findByCustom("","SELECT " +
                                   "GRLPERSONA.CNOMRAZONSOCIAL || ' ' || GRLPERSONA.CAPPATERNO || ' ' || GRLPERSONA.CAPMATERNO as cnombre, " +
                                   "DPOFORMATOAPOA.DTENVIO1ERSEMESTRESEGTO as dtEnvio1S, " +
                                   "DPOFORMATOAPOA.DTAPROBACION1ERSEMESTRESEGTO as dtAprob1S, " +
                                   "DPOFORMATOAPOA.DTENVIO2DOSEMESTRESEGTO as dtEnvio2S, " +
                                   "DPOFORMATOAPOA.DTAPROBACION2DOSEMESTRESEGTO as dtAprob2S, " +
                                   "DPOFORMATOAPOA.DTENVIO1ERTRIMESTRESEGTO as dtEnvio1T, " +
                                   "DPOFORMATOAPOA.DTAPROBACION1ERTRIMESTRESEGTO as dtAprob1T, " +
                                   "DPOFORMATOAPOA.DTENVIO2DOTRIMESTRESEGTO as dtEnvio2T, " +
                                   "DPOFORMATOAPOA.DTAPROBACION2DOTRIMESTRESEGTO as dtAprob2T, " +
                                   "DPOFORMATOAPOA.DTENVIO3ERTRIMESTRESEGTO as dtEnvio3T, " +
                                   "DPOFORMATOAPOA.DTAPROBACION3ERTRIMESTRESEGTO as dtAprob3T, " +
                                   "DPOFORMATOAPOA.DTENVIO4TOTRIMESTRESEGTO as dtEnvio4T, " +
                                   "DPOFORMATOAPOA.DTAPROBACION4TOTRIMESTRESEGTO as dtAprob4T, " +
                                   "pto.ICVEPUERTO, " +
                                   "e.CABREVIATURA as cAbreviatura, ef.CABREVIATURA as cAbrevPto " +
                                   "FROM DPOUSUARIOTITULO " +
                                   "join GRLPERSONA on GRLPERSONA.ICVEPERSONA = DPOUSUARIOTITULO.ICVETITULAR " +
                                   "  JOIN CPATITULOUBICACION U ON U.ICVETITULO = DPOUSUARIOTITULO.ICVETITULO " +
                                   "LEFT JOIN GRLENTIDADFED E ON U.ICVEPAIS = E.ICVEPAIS " +
                                   "    AND E.ICVEENTIDADFED = U.ICVEENTIDADFED " +
                                   "LEFT JOIN GRLPUERTO PTO ON PTO.ICVEPUERTO = U.ICVEPUERTO " +
                                   "LEFT JOIN GRLENTIDADFED EF ON PTO.ICVEPAIS = EF.ICVEPAIS " +
                                   "    AND EF.ICVEENTIDADFED = PTO.ICVEENTIDADFED " +
                                   "left join DPOFORMATOAPOA on DPOUSUARIOTITULO.ICVETITULO = DPOFORMATOAPOA.ICVETITULO "+
                                   "and DPOUSUARIOTITULO.ICVETITULAR = DPOFORMATOAPOA.ICVETITULAR "+
                                   cFiltro);
      iReng = 15;

      for(int i = 0;i < vcData.size();i++){
        TVDinRep vInfoSeg = (TVDinRep) vcData.get(i);
        cAPISeg = vInfoSeg.getString("cnombre");
        if(vInfoSeg.getInt("ICVEPUERTO")>0)
          cAPISeg += " - " + vInfoSeg.getString("cAbrevPto");
          else cAPISeg += " - " + vInfoSeg.getString("cAbreviatura");

        dtEnvio1S = vInfoSeg.getDate("dtEnvio1S");
        dtEnvio2S = vInfoSeg.getDate("dtEnvio2S");
        dtEnvio1T = vInfoSeg.getDate("dtEnvio1T");
        dtEnvio2T = vInfoSeg.getDate("dtEnvio2T");
        dtEnvio3T = vInfoSeg.getDate("dtEnvio3T");
        dtEnvio4T = vInfoSeg.getDate("dtEnvio4T");
        dtAprob1S = vInfoSeg.getDate("dtAprob1S");
        dtAprob2S = vInfoSeg.getDate("dtAprob2S");
        dtAprob1T = vInfoSeg.getDate("dtAprob1T");
        dtAprob2T = vInfoSeg.getDate("dtAprob2T");
        dtAprob3T = vInfoSeg.getDate("dtAprob3T");
        dtAprob4T = vInfoSeg.getDate("dtAprob4T");

        rep.comDespliegaAlineado("A",iReng,cAPISeg,false,rep.getAT_HIZQUIERDA(),"");
        if(dtEnvio1S != null)
          rep.comDespliegaAlineado("B",iReng,""+dtEnvio1S,false,rep.getAT_HCENTRO(),"");
        if(dtAprob1S != null)
          rep.comDespliegaAlineado("C",iReng,""+dtAprob1S,false,rep.getAT_HCENTRO(),"");
        if(dtEnvio2S != null)
          rep.comDespliegaAlineado("D",iReng,""+dtEnvio2S,false,rep.getAT_HCENTRO(),"");
        if(dtAprob2S != null)
          rep.comDespliegaAlineado("E",iReng,""+dtAprob2S,false,rep.getAT_HCENTRO(),"");
        if(dtEnvio1T != null)
          rep.comDespliegaAlineado("F",iReng,""+dtEnvio1T,false,rep.getAT_HCENTRO(),"");
        if(dtAprob1T != null)
          rep.comDespliegaAlineado("G",iReng,""+dtAprob1T,false,rep.getAT_HCENTRO(),"");
        if(dtEnvio2T != null)
          rep.comDespliegaAlineado("H",iReng,""+dtEnvio2T,false,rep.getAT_HCENTRO(),"");
        if(dtAprob2T != null)
          rep.comDespliegaAlineado("I",iReng,""+dtAprob2T,false,rep.getAT_HCENTRO(),"");
        if(dtEnvio3T != null)
          rep.comDespliegaAlineado("J",iReng,""+dtEnvio3T,false,rep.getAT_HCENTRO(),"");
        if(dtAprob3T != null)
          rep.comDespliegaAlineado("K",iReng,""+dtAprob3T,false,rep.getAT_HCENTRO(),"");
        if(dtEnvio4T != null)
          rep.comDespliegaAlineado("L",iReng,""+dtEnvio4T,false,rep.getAT_HCENTRO(),"");
        if(dtAprob4T != null)
          rep.comDespliegaAlineado("M",iReng,""+dtAprob4T,false,rep.getAT_HCENTRO(),"");

        rep.comBordeRededor("A",iReng,"A",iReng,iBorde,1);
        rep.comBordeRededor("B",iReng,"B",iReng,iBorde,1);
        rep.comBordeRededor("C",iReng,"C",iReng,iBorde,1);
        rep.comBordeRededor("D",iReng,"D",iReng,iBorde,1);
        rep.comBordeRededor("E",iReng,"E",iReng,iBorde,1);
        rep.comBordeRededor("F",iReng,"F",iReng,iBorde,1);
        rep.comBordeRededor("G",iReng,"G",iReng,iBorde,1);
        rep.comBordeRededor("H",iReng,"H",iReng,iBorde,1);
        rep.comBordeRededor("I",iReng,"I",iReng,iBorde,1);
        rep.comBordeRededor("J",iReng,"J",iReng,iBorde,1);
        rep.comBordeRededor("K",iReng,"K",iReng,iBorde,1);
        rep.comBordeRededor("L",iReng,"L",iReng,iBorde,1);
        rep.comBordeRededor("M",iReng,"M",iReng,iBorde,1);

        iReng++;
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

}
