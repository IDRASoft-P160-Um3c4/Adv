package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import java.util.Vector;
import gob.sct.sipmm.dao.*;
import com.micper.excepciones.DAOException;

/**
 * <p>Title: TDVentanilla.java</p>
 * <p>Description: DAO con métodos para reportes de Ventanilla Única</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LSC. Rafael Miranda Blumenkron
 * @version 1.0
 */

public class TDGRLRepPNC
    extends DAOBase{
  private String cSistema = "44";
  private TParametro VParametros = new TParametro(cSistema);
  private TFechas tFecha = new TFechas(cSistema);
  private TDTramite tTramite = new TDTramite();

  private String cNombreReporte = "";
  private int iNumeroReporte = 0;
  private int iNumeroCopias = 1;
  private int iTiempoEsperaAcuses = 1500;
  private boolean lAutoImprimir = false;
  TExcel rep = new TExcel();

  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");

  public TDGRLRepPNC(){
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


  public StringBuffer GenReportePNC(String cFiltro) throws Exception{
    int iCveOficina, iCveDepto, iCveProceso, iAnio, iMes;
    int iEjercicioSolicitud, iNumSolicitud, iCveReglaOperacion;
    int iCveSolicitante, iCveRepLegal, iCveDomicilio;
    int iReng = 5;
    String cOficina, cDepto, cProceso;
    String cNumOficioSol,cdtOficioSol,cDscBien, cRequisito = "";
    String cCalle, cNumExt, cNumInt, cColonia, cCodPostal, cPais, cEntidad, cMunicipio = "";
    StringBuffer sbRetorno = new StringBuffer("");
    StringBuffer sbBuscaAdic = new StringBuffer(), sbReemplazaAdic = new StringBuffer();
    java.sql.Date dtOficioSol,dtToday;
    int iCvePMDP;
    java.sql.Date dtInicio, dtFin;
    java.sql.Timestamp tsRegistro;
//    TWord rep1 = new TWord();
    TDObtenDatos dObtenDatos = new TDObtenDatos();
    TVDinRep vData = new TVDinRep();
    Vector vctOficio = new Vector();

    String[] aFiltros = cFiltro.split(",");
    iCveOficina = Integer.parseInt(aFiltros[0]);
    iCveDepto = Integer.parseInt(aFiltros[1]);
    iCveProceso = Integer.parseInt(aFiltros[2]);
    iMes = Integer.parseInt(aFiltros[3]);
    iAnio = Integer.parseInt(aFiltros[4]);
    cOficina = aFiltros[5];
    cDepto = aFiltros[6];
    cProceso = aFiltros[7];
    dtInicio = tFecha.getDateSQL(Integer.valueOf("1"),Integer.valueOf(aFiltros[3]),Integer.valueOf(aFiltros[4]));
    dtFin = getMesProx(iMes,iAnio);

    Vector vDatos = new  Vector();

    try{
      rep.iniciaReporte();
    //  rep.comEligeHoja(1);
      dtToday = tFecha.TodaySQL();
      rep.comDespliegaAlineado("H",2,tFecha.getMonthAbrev(dtInicio),false,rep.getAT_HDERECHA(),"");
      rep.comDespliegaAlineado("I",2,String.valueOf(iAnio),false,rep.getAT_HIZQUIERDA(),"");
      rep.comDespliegaAlineado("B",1,cDepto,false,rep.getAT_HIZQUIERDA(),"");
      rep.comDespliegaAlineado("B",2,cProceso,false,rep.getAT_HIZQUIERDA(),"");

  /*    sbBuscaAdic.append("[cMes]|[cAnio]|[cArea]|[cProceso]|")
              //   .append("[cAsunto]|[cCalle]|[cNumExterior]|")
                 ;
      sbReemplazaAdic.append(tFecha.getMonthAbrev(dtInicio)+"|"+String.valueOf(iAnio)+"|"+cDepto+"|"+cProceso+"|")
                  //   .append(cdtOficioSol+"|"+cDscBien+"|Notificación|"+"|"+cCalle+"|"+cNumExt+"|"+cNumInt+"|"+cColonia+"|")
          ; */
    //  rep.anexaEtiquetas(sbBuscaAdic,sbReemplazaAdic);
    //  rep.comEligeTabla("cReporte");

  /*    Vector vcDataRep = findByCustom("","SELECT " +
             "GRLREGISTROPNC.ICONSECUTIVOPNC, " +
             "GRLPRODUCTO.CDSCPRODUCTO as cProducto, " +
             "GRLCAUSAPNC.CDSCCAUSAPNC as cCausa, " +
             "GRLREGCAUSAPNC.CDSCOTRACAUSA as cOtraCausa, " +
             "GRLREGISTROPNC.DTREGISTRO as dtRegistro, " +
             "GRLPERSONA.CNOMRAZONSOCIAL||' '||GRLPERSONA.CAPPATERNO||' '||GRLPERSONA.CAPMATERNO AS cNombre, " +
             "ofic1.CTITULAR as cCorrige, " +
             "GRLREGCAUSAPNC.DTRESOLUCION as dtCorrige, " +
             "GRLSEGUIMIENTOPNC.CCOMENTARIOS as cInstruccion, " +
             "GRLDEPTOXOFIC.CTITULAR as cAutoriza, " +
             "GRLREGCAUSAPNC.LRESUELTO as lSituacion " +
             "FROM GRLREGISTROPNC " +
             "JOIN GRLPRODUCTO ON GRLPRODUCTO.ICVEPRODUCTO = GRLREGISTROPNC.ICVEPRODUCTO " +
             "JOIN GRLREGCAUSAPNC ON GRLREGCAUSAPNC.IEJERCICIO = GRLREGISTROPNC.IEJERCICIO " +
             " AND GRLREGCAUSAPNC.ICONSECUTIVOPNC = GRLREGISTROPNC.ICONSECUTIVOPNC " +
             " AND GRLREGCAUSAPNC.ICVEPRODUCTO = GRLREGISTROPNC.ICVEPRODUCTO " +
             "JOIN GRLPRODXOFICDEPTO ON GRLPRODXOFICDEPTO.ICVEOFICINA = " + iCveOficina +
             " AND GRLPRODXOFICDEPTO.ICVEDEPARTAMENTO = " + iCveDepto +
             " AND GRLPRODXOFICDEPTO.ICVEPROCESO = " + iCveProceso +
             " AND GRLPRODXOFICDEPTO.ICVEPRODUCTO = GRLREGCAUSAPNC.ICVEPRODUCTO " +
             "JOIN GRLCAUSAPNC ON GRLCAUSAPNC.ICVEPRODUCTO = GRLREGISTROPNC.ICVEPRODUCTO " +
             " AND GRLCAUSAPNC.ICVECAUSAPNC = GRLREGCAUSAPNC.ICVECAUSAPNC " +
             "JOIN GRLPERSONA ON GRLREGISTROPNC.ICVEUSUREGISTRO = GRLPERSONA.ICVEPERSONA " +
             "JOIN GRLDEPTOXOFIC ofic1 ON ofic1.ICVEOFICINA = GRLREGISTROPNC.ICVEOFICINAASIGNADO " +
             " AND ofic1.ICVEDEPARTAMENTO = GRLREGISTROPNC.ICVEDEPTOASIGNADO " +
             "JOIN GRLSEGUIMIENTOPNC ON GRLSEGUIMIENTOPNC.IEJERCICIO = GRLREGCAUSAPNC.IEJERCICIO " +
             " AND GRLSEGUIMIENTOPNC.ICONSECUTIVOPNC = GRLREGCAUSAPNC.ICONSECUTIVOPNC " +
             " AND GRLSEGUIMIENTOPNC.ICVEPRODUCTO = GRLREGCAUSAPNC.ICVEPRODUCTO " +
             " AND GRLSEGUIMIENTOPNC.ICVECAUSAPNC = GRLREGCAUSAPNC.ICVECAUSAPNC " +
             "JOIN GRLDEPTODEPEND ON GRLDEPTODEPEND.ICVEDEPTOHIJO = GRLREGISTROPNC.ICVEDEPTOASIGNADO " +
             "JOIN GRLDEPTOXOFIC ON GRLDEPTOXOFIC.ICVEOFICINA = GRLDEPTODEPEND.ICVEOFICINA " +
             " AND GRLDEPTOXOFIC.ICVEDEPARTAMENTO = GRLDEPTODEPEND.ICVEDEPARTAMENTO " +
             "WHERE GRLREGISTROPNC.DTREGISTRO >= " + "'" + dtInicio + "'" +
             " AND GRLREGISTROPNC.DTREGISTRO < " + "'" + dtFin + "'" +
             " AND GRLREGISTROPNC.ICVEOFICINA = " + iCveOficina +
             " AND GRLREGISTROPNC.ICVEDEPARTAMENTO = " + iCveDepto +
             " and GRLREGISTROPNC.ICVEPROCESO = " + iCveProceso); */

      Vector vcDataRep = findByCustom("","SELECT " +
             "TRAREGPNCETAPA.IEJERCICIO as iEjercicio, " +
             "TRAREGPNCETAPA.INUMSOLICITUD as iNumSolicitud, " +
             "GRLREGISTROPNC.ICONSECUTIVOPNC, " +
             "GRLPRODUCTO.CDSCPRODUCTO as cProducto, " +
             "GRLREGCAUSAPNC.ICVECAUSAPNC as iCausa, " +
             "GRLCAUSAPNC.CDSCCAUSAPNC as cCausa, " +
             "GRLREGCAUSAPNC.CDSCOTRACAUSA as cOtraCausa, " +
             "GRLREGISTROPNC.DTREGISTRO as dtRegistro, " +
             "SEGUSUARIO.CNOMBRE||' '||SEGUSUARIO.CAPPATERNO||' '||SEGUSUARIO.CAPMATERNO AS cNombre, " +
             "ofic1.CTITULAR as cCorrige, " +
             "GRLREGCAUSAPNC.DTRESOLUCION as dtCorrige, " +
             "GRLDEPTOXOFIC.CTITULAR as cAutoriza, " +
             "GRLSEGUIMIENTOPNC.CCOMENTARIOS as cInstruccion, " +
             "GRLREGCAUSAPNC.LRESUELTO as lSituacion " +
             "FROM GRLREGISTROPNC " +
             "LEFT JOIN TRAREGPNCETAPA ON TRAREGPNCETAPA.IEJERCICIOPNC = GRLREGISTROPNC.IEJERCICIO " +
             "AND TRAREGPNCETAPA.ICONSECUTIVOPNC = GRLREGISTROPNC.ICONSECUTIVOPNC " +
             "JOIN GRLPRODUCTO ON GRLPRODUCTO.ICVEPRODUCTO = GRLREGISTROPNC.ICVEPRODUCTO " +
             "JOIN GRLREGCAUSAPNC ON GRLREGCAUSAPNC.IEJERCICIO = GRLREGISTROPNC.IEJERCICIO " +
             "AND GRLREGCAUSAPNC.ICONSECUTIVOPNC = GRLREGISTROPNC.ICONSECUTIVOPNC " +
             "AND GRLREGCAUSAPNC.ICVEPRODUCTO = GRLREGISTROPNC.ICVEPRODUCTO " +
             "JOIN GRLCAUSAPNC ON GRLCAUSAPNC.ICVEPRODUCTO = GRLREGISTROPNC.ICVEPRODUCTO " +
             "AND GRLCAUSAPNC.ICVECAUSAPNC = GRLREGCAUSAPNC.ICVECAUSAPNC " +
             "JOIN SEGUSUARIO ON GRLREGISTROPNC.ICVEUSUREGISTRO = SEGUSUARIO.ICVEUSUARIO " +
             "JOIN GRLDEPTOXOFIC ofic1 ON ofic1.ICVEOFICINA = GRLREGISTROPNC.ICVEOFICINAASIGNADO " +
             "AND ofic1.ICVEDEPARTAMENTO = GRLREGISTROPNC.ICVEDEPTOASIGNADO " +
             "JOIN GRLDEPTODEPEND ON GRLDEPTODEPEND.ICVEDEPTOHIJO = GRLREGISTROPNC.ICVEDEPTOASIGNADO " +
             "JOIN GRLDEPTOXOFIC ON GRLDEPTOXOFIC.ICVEOFICINA = GRLDEPTODEPEND.ICVEOFICINA " +
             "AND GRLDEPTOXOFIC.ICVEDEPARTAMENTO = GRLDEPTODEPEND.ICVEDEPARTAMENTO " +
             "LEFT JOIN GRLSEGUIMIENTOPNC ON GRLSEGUIMIENTOPNC.IEJERCICIO = GRLREGCAUSAPNC.IEJERCICIO " +
             "AND GRLSEGUIMIENTOPNC.ICONSECUTIVOPNC = GRLREGCAUSAPNC.ICONSECUTIVOPNC " +
             "AND GRLSEGUIMIENTOPNC.ICVEPRODUCTO = GRLREGCAUSAPNC.ICVEPRODUCTO " +
             "AND GRLSEGUIMIENTOPNC.ICVECAUSAPNC = GRLREGCAUSAPNC.ICVECAUSAPNC " +
             "WHERE GRLREGISTROPNC.DTREGISTRO >= " + "'" + dtInicio + "'" +
             " AND GRLREGISTROPNC.DTREGISTRO < " + "'" + dtFin + "'" +
             " AND GRLREGISTROPNC.ICVEOFICINA = " + iCveOficina +
             " AND GRLREGISTROPNC.ICVEDEPARTAMENTO = " + iCveDepto +
             " and GRLREGISTROPNC.ICVEPROCESO = " + iCveProceso +
             " ORDER BY GRLREGISTROPNC.IEJERCICIO, " +
             " GRLREGISTROPNC.ICONSECUTIVOPNC, " +
             " GRLREGCAUSAPNC.ICVECAUSAPNC, " +
             " GRLSEGUIMIENTOPNC.ICVESEGUIMIENTO ");

      String cEjercicio = "";
      String cSolicitud = "";
      String cConsecutivo = "";
      String cDescripPNC = "";
      String cDtRegistro = "";
      String cUsuarioReg = "";
      String cCorrige = "";
      String cDtCorrige = "";
      String cInstruccion = "";
      String cAutoriza = "";
      String cSituacion = "";
      int iNumPNC,iNumPNCAnt = 0;
      int iCausa,iCausaAnt = 0;

      for(int ind=0; ind < vcDataRep.size(); ind++){
         rep.comBordeTotal("A",iReng,"I",iReng,1);
         TVDinRep vRep = (TVDinRep) vcDataRep.get(ind);
         iNumPNC = vRep.getInt("ICONSECUTIVOPNC");
         iCausa = vRep.getInt("iCausa");
      /*   if(ind == 0)
            iNumPNCAnt = iNumPNC; */


         cConsecutivo = String.valueOf(ind + 1);
      //   rep.comDespliegaAlineado("A",iReng,cConsecutivo,false,rep.getAT_HIZQUIERDA(),"");

         if(iNumPNC != iNumPNCAnt || iCausa != iCausaAnt){
           if(iNumPNC != iNumPNCAnt)
              iNumPNCAnt = iNumPNC;
           if(iCausa != iCausaAnt)
             iCausaAnt = iCausa;
           cDescripPNC = vRep.getString("cProducto") + ", ";
           if(vRep.getString("cCausa").compareTo("null") != 0)
             cDescripPNC = cDescripPNC + vRep.getString("cCausa");
           cDescripPNC = cDescripPNC + vRep.getString("cOtraCausa");

           cEjercicio = String.valueOf(vRep.getInt("iEjercicio"));
           cSolicitud = String.valueOf(vRep.getInt("iNumSolicitud"));
           if(cSolicitud != "0")
              rep.comDespliegaAlineado("A",iReng,cEjercicio + "-" + cSolicitud,false,rep.getAT_HIZQUIERDA(),"");
           rep.comDespliegaAlineado("B",iReng,cDescripPNC,false,
                                    rep.getAT_HIZQUIERDA(),"");
           if(vRep.getDate("dtRegistro") != null)
             cDtRegistro = tFecha.getFechaDDMMYYYY(vRep.getDate("dtRegistro"),
                                                   "/");
           else
             cDtRegistro = "";
           rep.comDespliegaAlineado("C",iReng,cDtRegistro,false,
                                    rep.getAT_HIZQUIERDA(),"");
           cUsuarioReg = vRep.getString("cNombre");
           rep.comDespliegaAlineado("D",iReng,cUsuarioReg,false,
                                    rep.getAT_HIZQUIERDA(),"");
           cCorrige = vRep.getString("cCorrige");
           if(cCorrige.compareTo("null") == 0)
             cCorrige = "";
           rep.comDespliegaAlineado("E",iReng,cCorrige,false,
                                    rep.getAT_HIZQUIERDA(),"");
           if(vRep.getDate("dtCorrige") != null)
             cDtCorrige = tFecha.getFechaDDMMYYYY(vRep.getDate("dtCorrige"),"/");
           else
             cDtCorrige = "";
           rep.comDespliegaAlineado("F",iReng,cDtCorrige,false,
                                    rep.getAT_HIZQUIERDA(),"");
         }
         cInstruccion = vRep.getString("cInstruccion");
         if(cInstruccion.compareTo("null") == 0)
           cInstruccion = "";
         rep.comDespliegaAlineado("G",iReng,cInstruccion,false,rep.getAT_HIZQUIERDA(),"");
         cAutoriza = vRep.getString("cAutoriza");
         if(cAutoriza.compareTo("null") == 0)
           cAutoriza = "";
         rep.comDespliegaAlineado("H",iReng,cAutoriza,false,rep.getAT_HIZQUIERDA(),"");
         if(vRep.getInt("lSituacion") == 0)
            cSituacion = "No Resuelto";
         else
            cSituacion = "Resuelto";
         rep.comDespliegaAlineado("I",iReng,cSituacion,false,rep.getAT_HIZQUIERDA(),"");
         iReng++;
      }

      sbRetorno = rep.getSbDatos();

      //Falta sacar las referencias y Numeros Internos

/*    vDatos = new  TDGeneral().generaOficioWord(cFolio,
                                              Integer.parseInt(cCveOfic,10),
                                              Integer.parseInt(cCveDepto,10),
                                              0,0,
                                              iCveSolicitante,iCveDomicilio,iCveRepLegal,
                                              "","","","",
                                              false,"",new Vector(),
                                              false,new Vector(),
                                              rep1.getEtiquetasBuscar(),
                                              rep1.getEtiquetasRemplazar());

*/
    } catch(Exception e){
      e.printStackTrace();
      cMensaje += e.getMessage();
    }


    Vector vDatosTemp = new Vector();
   // vDatosTemp = (Vector)vDatos.get(1);
/*    vDatosTemp = rep.getVectorDatos(true);
    Vector vDatosTablas = rep.getDatosTablas();
    for(int iCont=0;iCont<vDatosTablas.size();iCont++){
      vDatosTemp.add(vDatosTablas.get(iCont));
    }
//    vDatos.add(1, vDatosTemp); */

    return sbRetorno; // vDatosTemp; //rep.getVectorDatos(true);

/*
    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return rep1.getVectorDatos(true);
*/



  }

 java.sql.Date getMesProx(int iMes, int iAnio){
   if(iMes == 12){
      iMes = 1;
      iAnio++;
   }else{
      iMes++;
   }
   return tFecha.getDateSQL(Integer.valueOf("1"),Integer.valueOf(String.valueOf(iMes)),Integer.valueOf(String.valueOf(iAnio)));
 }


}
