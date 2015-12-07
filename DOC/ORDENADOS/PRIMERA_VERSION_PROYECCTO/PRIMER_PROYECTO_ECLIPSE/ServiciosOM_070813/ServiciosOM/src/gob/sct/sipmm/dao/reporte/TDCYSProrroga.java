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
 * @author ABarrientos
 * @version 1.0
 */

public class TDCYSProrroga extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private TFechas    tFecha      = new TFechas("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private String cLeyendaEnc    = VParametros.getPropEspecifica("LeyendaEnc");
  private String cLeyendaPie    = VParametros.getPropEspecifica("LeyendaPie");
  private String cEntidadRemplazaTexto = VParametros.getPropEspecifica("EntidadRemplazaTexto");
  private int iEjercicioOficio = 0;
  private String cDigitosFolio = "";
  private int iConsecutivo=0;
  public TDCYSProrroga(){
  }


   public Vector genProrroga(String cQuery,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen){
     Vector vcCuerpo = new Vector();
     Vector vcCopiasPara = new Vector();
     Vector vRegs = new Vector();
     TDCYSSegProcedimiento dSegPRoc = new TDCYSSegProcedimiento();
     TDCYSFolioProc dCYSFolioProc = new TDCYSFolioProc();
     TWord rep = new TWord();
     TFechas Fechas = new TFechas("44");

     int iCveOficinaDest=0,iCveDeptoDest=0,iCvePersona=0,iAniosDuracion=0,iCveDom=0;
     String cUsoTitulo="";
     String cPuerto="", cAsunto="Se concede prórroga de plazo en el Procedimiento administrativo de Sanción";
     String[] aFiltros = cQuery.split(",");
     StringTokenizer st = null;
     int iCveTitulo = Integer.parseInt(aFiltros[0]);
     int iEjercicio = Integer.parseInt(aFiltros[1]);
     int iMovProcedimiento = Integer.parseInt(aFiltros[2]);
     int iMovFolioProc = Integer.parseInt(aFiltros[3]);
     String cNumEscrito = aFiltros[4];
     Vector vNotifRec = new Vector();
     this.setDatosFolio(cNumFolio);
     try{
       vNotifRec = dSegPRoc.findByOficioNotRec(Integer.parseInt(aFiltros[1]),Integer.parseInt(aFiltros[2]),0);
     }catch(Exception eNotifRec){
       eNotifRec.printStackTrace();
     }
     String cSQL = "select CPATitulo.iCveTitulo, " +
              "GRLPersona.iTipoPersona, " +
              "GRLPersona.iCvePersona, " +
              "GRLPersona.cNomRazonSocial, " +
              "GRLPersona.cApPaterno, " +
              "GRLPersona.cApMaterno, " +
              "GRLPersona.cRFC, " +
              "CPAActaNacimiento.cNumActaNacimiento, " +
              "CPAActaNacimiento.dtNacimiento, " +
              "CPAActaNacimiento.cNumJuzgado, " +
              "GRLDomicilio.iCveDomicilio, " +
              "CPATitulo.cNumTitulo, " +
              "CPATitulo.dtIniVigenciaTitulo, " +
              "CPATitulo.cObjetoTitulo, " +
              "CPATitulo.iCveClasificacion, " +
              "CPAUsoTitulo.iCveUsoTitulo, " +
              "CPAUsoTitulo.cDscUsoTitulo, " +
              "CPATitulo.iCveTituloAnterior, " +
              "CPATitulo.dtVigenciaTitulo, " +
              "CPATitulo.iCveTipoTitulo, "+
              " GRLPuerto.cDscPuerto " +
              "from CPATitulo " +
              "join CPATipoTitulo on CPATipoTitulo.iCveTipoTitulo = CPATitulo.iCveTipoTitulo " +
              "join CPAUsoTitulo on CPAUsoTitulo.iCveUsoTitulo = CPATitulo.iCveUsoTitulo " +
              "join CPATituloUbicacion on CPATituloUbicacion.iCveTitulo = CPATitulo.iCveTitulo " +
              "left join GRLEntidadFed entTitUbic on entTitUbic.iCvePais = CPATituloUbicacion.iCvePais " +
              "and entTitUbic.iCveEntidadFed = CPATituloUbicacion.iCveEntidadFed " +
              " left join GRLPuerto on GRLPuerto.iCvePuerto = CPATituloUbicacion.iCvePuerto " +
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

       if(vNotifRec.size()>0){
         TVDinRep vDinNotifRec = new TVDinRep();
         vDinNotifRec = (TVDinRep) vNotifRec.get(0);
         rep.comRemplaza("[NumOficio]",vDinNotifRec.getString("cDigitosFolio")+"."+vDinNotifRec.getString("iConsecutivo")+"/"+vDinNotifRec.getString("iEjercicioFol"));
         rep.comRemplaza("[FechaOficio]",Fechas.getDateSPN(vDinNotifRec.getDate("dtAsignacion")));

       }
       rep.comRemplaza("[NumEscrito]",cNumEscrito);

       rep.comRemplaza("[cObjetoTitulo]",vDatos.getString("cObjetoTitulo").toLowerCase());

       if(vDatos.getString("cDscUsoTitulo").indexOf("USO")==-1)
         cUsoTitulo = "uso "+vDatos.getString("cDscUsoTitulo").toLowerCase();
       else
         cUsoTitulo = vDatos.getString("cDscUsoTitulo").toLowerCase();
       rep.comRemplaza("[cUsoTitulo]",cUsoTitulo);

       st = new StringTokenizer(vDatos.getString("cDscPuerto"));
       cPuerto="";

       while (st.hasMoreTokens()) {
          String cToken = st.nextToken();
          cPuerto += " " + cToken.charAt(0) + cToken.substring(1,cToken.length()).toLowerCase();
       }
       //rep.comRemplaza("[cEntidadFed]",cEntidad);
       //System.out.println("***************************---vDatos.getDate(dtIniVigenciaTitulo): "+vDatos.getDate("dtIniVigenciaTitulo"));
       //System.out.println("***************************---vDatos.getDate(dtVigenciaTitulo): "+vDatos.getDate("dtVigenciaTitulo"));
       iAniosDuracion = Fechas.getDaysBetweenDates(vDatos.getDate("dtIniVigenciaTitulo"),vDatos.getDate("dtVigenciaTitulo"));
       iAniosDuracion = iAniosDuracion/365;

       rep.comRemplaza("[cFechaIniVigencia]",Fechas.getDateSPN( vDatos.getDate("dtIniVigenciaTitulo") ));
       rep.comRemplaza("[cPuerto]",cPuerto);
       rep.comRemplaza("[cAniosDuracion]",""+iAniosDuracion);
       iCvePersona = vDatos.getInt("iCvePersona");
       iCveDom = vDatos.getInt("iCveDomicilio");


     } else {
       //Sin Valores en la Base.



     }

     //return ;
     // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
     Vector vRetorno = new TDGeneral().generaOficioWord(cNumFolio,
                            Integer.parseInt(cCveOficinaOrigen),Integer.parseInt(cCveDeptoOrigen),
                            iCveOficinaDest,iCveDeptoDest,
                            iCvePersona,iCveDom,0,
                            "",cAsunto,
                            "", "",
                            true,"cCuerpo",vcCuerpo,
                            true, vcCopiasPara,
                            rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

     TVDinRep vDinRep = new TVDinRep();
     vDinRep.put("iEjercicio",iEjercicio);
     vDinRep.put("iMovProcedimiento",iMovProcedimiento);
     vDinRep.put("iMovFolioProc",iMovFolioProc);
     vDinRep.put("iEjercicioFol",iEjercicioOficio);
     vDinRep.put("iCveOficina",Integer.parseInt(cCveOficinaOrigen));
     vDinRep.put("iCveDepartamento",Integer.parseInt(cCveDeptoOrigen));
     vDinRep.put("cDigitosFolio",cDigitosFolio);
     vDinRep.put("iConsecutivo",iConsecutivo);
     vDinRep.put("lEsProrroga",1);
     vDinRep.put("lEsAlegato",0);
     vDinRep.put("lEsSinEfecto",0);
     vDinRep.put("lEsNotificacion",0);
     vDinRep.put("lEsRecordatorio",0);
     vDinRep.put("lEsResolucion",0);

     try{
       vDinRep = dCYSFolioProc.update(vDinRep,null);
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
