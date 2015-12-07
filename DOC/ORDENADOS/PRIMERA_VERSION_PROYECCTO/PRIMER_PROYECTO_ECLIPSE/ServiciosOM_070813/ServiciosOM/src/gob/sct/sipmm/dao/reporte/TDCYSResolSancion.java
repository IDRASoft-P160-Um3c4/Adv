package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.sql.*;
import java.util.*;
import java.sql.SQLException;
import com.micper.seguridad.vo.TVDinRep;
import gob.sct.sipmm.dao.reporte.TDGeneral;
import gob.sct.sipmm.dao.*;

/**
 * <p>Title: TDCYSReversion.java</p>
 * <p>Description: DAO de oficios generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author mbeano
 * @version 1.0
 */

public class TDCYSResolSancion extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private TFechas    tFecha      = new TFechas("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private String cLeyendaEnc    = VParametros.getPropEspecifica("LeyendaEnc");
  private String cLeyendaPie    = VParametros.getPropEspecifica("LeyendaPie");
  private String cEntidadRemplazaTexto = VParametros.getPropEspecifica("EntidadRemplazaTexto");
  private int iEjercicioOficio = 0;
  private String cDigitosFolio = "";
  private int iConsecutivo=0, iDiasSalMin = 0;
  public TDCYSResolSancion(){
  }


  //public Vector genReversionPFP(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
    public Vector genResolucionSancion(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
      Vector vcCuerpo = new Vector();
      Vector vcCopiasPara = new Vector();
      Vector vRegs = new Vector();
      Vector vcProrroga = new Vector();
      TDCYSSegProcedimiento dSegPRoc = new TDCYSSegProcedimiento();
      TWord rep = new TWord();
      TFechas Fechas = new TFechas("44");
      TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();
      TDCYSDiligenciaReversion dCYSDilRev = new TDCYSDiligenciaReversion();
      TVDinRep vRegDilig = new TVDinRep();

      int iCveOficinaDest=0,iCveDeptoDest=0,iEjercicio=0,iMovProcedimiento,iAniosDuracion=0;
      String cTitular="", cFechaNotif="",cIniVigencia="";
      String cTipoTitulo="", cDscOtorgado="",cObjetoTitulo="";
      String cMunicipioEnt="", cPuerto="",cAsunto="Resolución de Sanción. ";
      String[] aFiltros = cQuery.split(",");
      StringTokenizer st = null;
      Vector vDataSancionAd  = new Vector();
      java.sql.Date dtFin = null;

      int iCveTitulo = Integer.parseInt(aFiltros[0]);
      iEjercicio = Integer.parseInt(aFiltros[1]);
      iMovProcedimiento = Integer.parseInt(aFiltros[2]);
      //java.sql.Timestamp tsDiligencia = Fechas.getTimeStampTC(aFiltros[1]);
      Vector vNotifRec = new Vector();
      this.setDatosFolio(cNumFolio);
      try{

        vNotifRec = dSegPRoc.findByOficioNotRec(iEjercicio,iMovProcedimiento,0);
        vcProrroga = dSegPRoc.findByProrrogaNotRec(iEjercicio,iMovProcedimiento);
//      vNotifRec = dSegPRoc.findByOfiRevNotRec(iEjercicio,iMovProcedimiento);
        //vNotifRec = dSegPRoc.findByOficioNotRec(Integer.parseInt(aFiltros[1]),Integer.parseInt(aFiltros[2]));
//        vRegDilig = dCYSDilRev.regresaDiligencia(Integer.parseInt(aFiltros[1]),Integer.parseInt(aFiltros[2]));
      }catch(Exception eNotifRec){
        eNotifRec.printStackTrace();
      }
      
      String cSQLSancionAdmva = "SELECT " +
      "DTREGISTRO,DTSANCION,IDIASSMGDF " +
      "FROM CYSSANCIONADMVA " +
      "where IEJERCICIO = " + iEjercicio +
      " and IMOVPROCEDIMIENTO = " + iMovProcedimiento;
      
      try {
    	  vDataSancionAd = super.FindByGeneric("", cSQLSancionAdmva, dataSourceName);
      } catch (SQLException e) {
    	  e.printStackTrace();
      }
      
      if(vDataSancionAd.size() > 0){
    	  TVDinRep tvDataSanAd = (TVDinRep)vDataSancionAd.get(0);
    	  iDiasSalMin = tvDataSanAd.getInt("IDIASSMGDF");
      }
      
      String cSQL = "select CPATitulo.iCveTitulo, " +
               "GRLPersona.iCvePersona, " +
               "GRLPersona.iTipoPersona, " +
               "GRLPersona.cNomRazonSocial, " +
               "GRLPersona.cApPaterno, " +
               "GRLPersona.cApMaterno, " +
               "GRLPersona.cRFC, " +
               "CPAActaNacimiento.cNumActaNacimiento, " +
               "CPAActaNacimiento.dtNacimiento, " +
               "CPAActaNacimiento.cNumJuzgado, " +
               "Juez.cNomRazonSocial cNomJuez, " +
               "Juez.cApPaterno cApPatJuez, " +
               "Juez.cApMaterno cApMatJuez, " +
               "CPAActaNacimiento.cNumLibroActa, " +
               "CPAActaNacimiento.cNumFojaActa, " +
               "GRLDomicilio.cCalle, " +
               "GRLDomicilio.cNumExterior, " +
               "GRLDomicilio.cNumInterior, " +
               "GRLDomicilio.cColonia, " +
               "GRLDomicilio.cCodPostal, " +
               "GRLDomicilio.cTelefono, " +
               "GRLPais.cNombre as cNomPais, " +
               "GRLEntidadFed.cNombre as cNomEntidadFed, " +
               "GRLMunicipio.cNombre as cNomMunicipio, " +
               "GRLLocalidad.cNombre as cNomLocalidad, " +
               "CPATitulo.cNumTitulo, " +
               "CPATitulo.cNumTituloAnterior, " +
               "CPATitulo.dtIniVigenciaTitulo, " +
               "CPATitulo.cZonaFedAfectadaTerrestre, " +
               "CPATitulo.cZonaFedAfectadaAgua, " +
               "CPATitulo.cObjetoTitulo, " +
               "CPATitulo.cMontoInversion, " +
               "CPATitulo.dtPublicacionDOF, " +
               "CPATitulo.iCveClasificacion, " +
               "CPAUsoTitulo.iCveUsoTitulo, " +
               "CPAUsoTitulo.cDscUsoTitulo, " +
               "CPATitulo.iCveTituloAnterior, " +
               "CPATitulo.dtVigenciaTitulo, " +
               "CPATitulo.iCveTipoTitulo, "+
               "CPATitulo.iPropiedad, "+
               "CPATituloUbicacion.cCalleTitulo, " +
               "CPATituloUbicacion.cKm, " +
               "CPATituloUbicacion.cColoniaTitulo, " +
               "entTitUbic.cNombre as cEntidadTitUbic, " +
               "munTitUbi.cNombre as cMunicipioTitUbic, "+
               "PaisTitUbic.cNombre AS cPaisTitUbi, "+
               "GRLPuerto.cDscPuerto " +
               "FROM CPATitulo " +
               "join CPATipoTitulo on CPATipoTitulo.iCveTipoTitulo = CPATitulo.iCveTipoTitulo " +
               "join CPAUsoTitulo on CPAUsoTitulo.iCveUsoTitulo = CPATitulo.iCveUsoTitulo " +
               "left join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
               "left join GRLPais PaisTitUbic on PaisTitUbic.iCvePais = CPATituloUbicacion.iCvePais " +
               "left join GRLEntidadFed entTitUbic on entTitUbic.iCvePais = CPATituloUbicacion.iCvePais " +
               "and entTitUbic.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " +
               "left join GRLMunicipio munTitUbi on munTitUbi.iCvePais = CPATituloUbicacion.iCvePais " + //--
               "and munTitUbi.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " + //--
               "and munTitUbi.iCveMunicipio = CPATituloUbicacion.iCveMunicipio " + //--
               "join CPATitular on CPAtitular.iCveTitulo = CPATitulo.iCveTitulo " +
               "join GRLPersona on GRLPersona.iCvePersona = CPATitular.iCveTitular " +
               "left join CPAActaNacimiento on CPAActaNacimiento.iCvePersona = GRLPersona.iCvePersona " +
               "left join GRLDomicilio on GRLDomicilio.iCvePersona = GRLPersona.iCvePersona " +
               "and GRLDomicilio.lPredeterminado = 1 " +
               "left join GRLPais on GRLPais.iCvePais = GRLDomicilio.iCvePais " +
               "left join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLDomicilio.iCvePais " +
               "and GRLEntidadFed.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
               "left join GRLMunicipio on GRLMunicipio.iCvePais = GRLDomicilio.iCvePais " +
               "and GRLMunicipio.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
               "and GRLMunicipio.iCveMunicipio = GRLDomicilio.iCveMunicipio " +
               "left join GRLLocalidad on GRLLocalidad.iCvePais = GRLDomicilio.iCvePais " +
               "and GRLLocalidad.iCveEntidadFed = GRLDomicilio.iCveEntidadFed " +
               "and GRLLocalidad.iCveMunicipio = GRLDomicilio.iCveMunicipio " +
               "and GRLLocalidad.iCveLocalidad = GRLDomicilio.iCveLocalidad " +
               "left join GRLPersona Juez on Juez.iCvePersona = CPAActaNacimiento.iCveJuez " +
               "left join GRLPuerto on GRLPuerto.iCvePuerto = CPATituloUbicacion.iCvePuerto " +
               "where CPATitulo.iCveTitulo = " + iCveTitulo;


      // Query de Consulta del Reporte.
      try{
        vRegs = super.FindByGeneric("", cSQL, dataSourceName);

      }catch(SQLException ex){
        ex.printStackTrace();
        cMensaje = ex.getMessage();
      }catch(Exception ex2){
        ex2.printStackTrace();
      }

      // Elaboración del Reporte.
      rep.iniciaReporte();
      if (vRegs.size() > 0){
        TVDinRep vDatos = (TVDinRep) vRegs.get(0);

        //Destinatario
        cTitular = vDatos.getString("cNomRazonSocial")+" "+vDatos.getString("cApPaterno")+" "+vDatos.getString("cApMaterno");
        rep.comRemplaza("[cDirigidoA]",cTitular);
        rep.comRemplaza("[cCalle1]",vDatos.getString("cCalle"));
        rep.comRemplaza("[cNumExterior1]",vDatos.getString("cNumExterior"));
        if(vDatos.get("cNumInterior") != null || vDatos.getString("cNumInterior") != "")
           rep.comRemplaza("[cNumInterior1]",vDatos.getString("cNumInterior"));
        else
           rep.comRemplaza("[cNumInterior1]"," ");
        rep.comRemplaza("[cColonia1]",vDatos.getString("cColonia"));
        rep.comRemplaza("[cCodigoPostal1]",vDatos.getString("cCodPostal"));
        rep.comRemplaza("[cMunicipio1]",vDatos.getString("cNomMunicipio"));
        rep.comRemplaza("[cEntidad1]",vDatos.getString("cNomEntidadFed"));
        rep.comRemplaza("[cPais1]",vDatos.getString("cNomPais"));

        st = new StringTokenizer(cTitular);
        cTitular="";
        while (st.hasMoreTokens()) {
           String cToken = st.nextToken();
           if(cToken.indexOf("S.A.")==-1 && cToken.indexOf("C.V.")==-1)
             cTitular += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
           else
             cTitular += " " + cToken;
        }

        rep.comRemplaza("[cTitular]",cTitular);
        //Identificación que se trata de Concesión.

        if (vDatos.getInt("iCveTipoTitulo") == 1){
          cTipoTitulo = "la Concesión";
          cDscOtorgado = "El Presidente de la República a través de la Dirección General de Puertos";
        }
        //Identificación que se trata de Permiso.
        if (vDatos.getInt("iCveTipoTitulo") == 2){
          cTipoTitulo = "el Permiso";
          cDscOtorgado = "La Dirección General de Puertos";
        }

        if (vDatos.getInt("iCveTipoTitulo") == 3){
          cTipoTitulo = "la Autorización";
          cDscOtorgado = "La Dirección General de Puertos";
        }

        rep.comRemplaza("[cTipoTitulo]",cTipoTitulo);
        rep.comRemplaza("[cDscOtorgado]",cDscOtorgado);
        if(vDatos.get("dtIniVigenciaTitulo")!=null)
          cIniVigencia = Fechas.getDateSPN(vDatos.getDate("dtIniVigenciaTitulo"));
        else
           cIniVigencia ="";
        rep.comRemplaza("[cIniVigencia]",cIniVigencia);

        cObjetoTitulo = vDatos.getString("cObjetoTitulo");
        st = new StringTokenizer(cObjetoTitulo);
        cObjetoTitulo="";
        while (st.hasMoreTokens()) {
           String cToken = st.nextToken();
           cObjetoTitulo += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
        }
        rep.comRemplaza("[cObjetoTitulo]",cObjetoTitulo);

        iAniosDuracion = Fechas.getDaysBetweenDates(vDatos.getDate("dtIniVigenciaTitulo"),vDatos.getDate("dtVigenciaTitulo"));
        iAniosDuracion = iAniosDuracion/365;
        rep.comRemplaza("[cAniosDuracion]",""+iAniosDuracion);

        st = new StringTokenizer(vDatos.getString("cDscPuerto"));
        cPuerto="";
        while (st.hasMoreTokens()) {
           String cToken = st.nextToken();
           cPuerto += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
        }
        rep.comRemplaza("[cPuerto]",cPuerto);

        st = new StringTokenizer(vDatos.getString("cNomMunicipio"));
        cMunicipioEnt="";
         while (st.hasMoreTokens()) {
           String cToken = st.nextToken();
           cMunicipioEnt += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
        }
        rep.comRemplaza("[cMunicipioEnt]",cMunicipioEnt);

        if(vNotifRec.size()>0){
           TVDinRep vDinNotifRec = new TVDinRep();
           vDinNotifRec = (TVDinRep) vNotifRec.get(0);

           rep.comRemplaza("[cFolio]",vDinNotifRec.getString("cDigitosFolio")+"."+vDinNotifRec.getString("iConsecutivo")+"/"+vDinNotifRec.getString("iEjercicioFol"));

           if(vDinNotifRec.get("dtAsignacion")!=null)
              rep.comRemplaza("[cFechaFolio]",Fechas.getDateSPN(vDinNotifRec.getDate("dtAsignacion")));
           else
              rep.comRemplaza("[cFechaFolio]"," ");

           if(vDinNotifRec.getDate("dtRecAsignacion")!=null){
             //cFechaNotif = Fechas.getDateSPN(vDinNotifRec.getDate("dtRecAsignacion"));
             rep.comRemplaza("[cFechaNotif]",Fechas.getDateSPN(vDinNotifRec.getDate("dtRecAsignacion")));
           }
           if(vDinNotifRec.getDate("dtNotAsignacion")!=null){
//             cFechaNotif = Fechas.getDateSPN(vDinNotifRec.getDate("dtNorAsignacion"));
             rep.comRemplaza("[cFechaNotif]",Fechas.getDateSPN(vDinNotifRec.getDate("dtNotAsignacion")));
           }
           //else
//             cFechaNotif = " ";
              //rep.comRemplaza("[cFechaNotif]"," ");

         }
         else{
           rep.comRemplaza("[cFolio]"," ");
           rep.comRemplaza("[cFechaFolio]"," ");
           rep.comRemplaza("[cFechaNotif]"," ");
         }

         if(vcProrroga.size()>0){
           TVDinRep vProrroga = new TVDinRep();
            vProrroga = (TVDinRep) vcProrroga.get(0);

            if(vProrroga.get("dtAsignacion") != null){
               rep.comRemplaza("[cOfProrro]",vProrroga.getString("cDigitosFolio")+"."+vProrroga.getString("iConsecutivo")+"/"+vProrroga.getString("iEjercicioFol") );
               rep.comRemplaza("[cIniPro]",Fechas.getDateSPN(vProrroga.getDate("dtAsignacion")));
               dtFin = Fechas.aumentaDisminuyeDias(vProrroga.getDate("dtAsignacion"),vProrroga.getInt("IDIASOTORGADOS"));
               rep.comRemplaza("[cFinPro]",Fechas.getDateSPN(dtFin));
            }
             else {
                 rep.comRemplaza("[cOfProrro]"," ");
                 rep.comRemplaza("[cIniPro]"," ");
                 rep.comRemplaza("[cFinPro]"," ");
             }
         }

      } else {
        //Sin Valores en la Base.
    	  rep.comRemplaza("[cOfProrro]"," ");
          rep.comRemplaza("[cIniPro]"," ");
          rep.comRemplaza("[cFinPro]"," ");
      }
      
      rep.comRemplaza("[iDiasSalMin]", String.valueOf(iDiasSalMin));


      //return ;
      // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
      Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
                             Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                             0,0,
                             0,0,0,
                             "",cAsunto,
                             "", "",
                             true,"cCuerpo",vcCuerpo,
                             true, vcCopiasPara,
                             rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

       TVDinRep vDinRep = new TVDinRep();
       vDinRep.put("iEjercicio",iEjercicio);
       vDinRep.put("iMovProcedimiento",iMovProcedimiento);
       vDinRep.put("iEjercicioFol",iEjercicioOficio);
       vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
       vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
       vDinRep.put("cDigitosFolio",cDigitosFolio);
       vDinRep.put("iConsecutivo",iConsecutivo);
       vDinRep.put("lEsProrroga",0);
       vDinRep.put("lEsAlegato",0);
       vDinRep.put("lEsSinEfecto",0);
       vDinRep.put("lEsNotificacion",0);
       vDinRep.put("lEsRecordatorio",0);
       vDinRep.put("lEsResolucion",1);

       try{
          vDinRep = dCYSFolioProc.insertSinUpdate(vDinRep,null);
       } catch(Exception ex){
          ex.printStackTrace();
       }

     return vRetorno;

    }

  private void setDatosFolio(String cNumFolio){
     String[] cDatosFolio = cNumFolio.split("/");
     String cTemp = cDatosFolio[0].replace('.', '/');
     String[] cDigitosTemp = cTemp.split("/");
     int Ejercicio = Integer.parseInt(cDatosFolio[cDatosFolio.length-1], 10);
     int Consecutivo = Integer.parseInt(cDigitosTemp[cDigitosTemp.length-1], 10);
     String Digitos = "";
     for(int i=0; i<cDigitosTemp.length-1; i++){
       Digitos += cDigitosTemp[i];
       if (i<cDigitosTemp.length-2)
         Digitos += ".";
     }
     iEjercicioOficio = Ejercicio;
     cDigitosFolio = Digitos;
     iConsecutivo = Consecutivo;
   }
}
