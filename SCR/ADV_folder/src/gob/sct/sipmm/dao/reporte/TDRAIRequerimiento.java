package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.excepciones.DAOException;
import com.micper.ingsw.*;
import com.micper.sql.*;
import com.micper.util.*;
import java.util.*;
import java.sql.SQLException;
import com.micper.seguridad.vo.TVDinRep;
import gob.sct.sipmm.dao.reporte.TDGeneral;

/**
 * <p>Title: TDRAIRequerimiento.java</p>
 * <p>Description: DAO de oficios generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LCI. Oscar Castrejón Adame.
 * @version 1.0
 */

public class TDRAIRequerimiento extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private TFechas    tFecha      = new TFechas("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private String cLeyendaEnc    = VParametros.getPropEspecifica("LeyendaEnc");
  private String cLeyendaPie    = VParametros.getPropEspecifica("LeyendaPie");
  private String cEntidadRemplazaTexto = VParametros.getPropEspecifica("EntidadRemplazaTexto");
  public TDRAIRequerimiento(){
  }

  public Vector genRequerimiento(String cQuery, String cNumFolio, String iCveOficinaOrigen, String iCveDeptoOrigen){
     Vector vcCuerpo = new Vector();
     Vector vcCopiasPara = new Vector();
     Vector vRegs = new Vector();
     Vector vRegsUbicacion = new Vector();
     TWord rep = new TWord();
     TFechas Fecha = new TFechas("44");
     int iCvePersona=0,iCveDomicilio=0,iCveRepLegal=0,iCveTipoContrato=0;
     String cNombreAPI="",cNumContrato= "",cNomCesionario="",cNumTitulo="",cAsunto="";
     String cSerPor="";
     String cSerCon="";
     String[] aFiltros = cQuery.split(",");
     TLetterNumberFormat tLetterNumberFormat = new TLetterNumberFormat();

     //Informacion de la Regularizacion de Areas e Instalaciones Portuarias.
     try{
       vRegs = super.FindByGeneric("", "select RAIRegularizacion.iCveRegularizacion, " +
                                       "RAIRegularizacion.dtDocumento, " +
                                       "RAIRegularizacion.cDocumento, " +
                                       "RAIRegularizacion.cNumRegularizacion, " +
                                       "RAIRegularizacion.iCveOcupante, " +
                                       "Ocupante.cNomRazonSocial || ' ' || Ocupante.cApPaterno || ' ' || Ocupante.cApMaterno as Ocupante, " +
                                       "DomOcupante.iCveDomicilio as iCveDomicilio, " +
                                       "RAIRegularizacion.iCveRepLegalOcupante, " +
                                       "RepLegalOcu.cNomRazonSocial || ' '  || RepLegalOcu.cApPaterno || ' ' || RepLegalOcu.cApMaterno as RepLegal, " +
                                       "RAIRegularizacion.cObsRegularizacion, " +
                                       "RAIRegularizacion.dtComision, " +
                                       "RAIRegularizacion.cHoraComision " +
                                       "from RAIRegularizacion " +
                                       "join GRLPersona Ocupante on Ocupante.iCvePersona = RAIRegularizacion.iCveOcupante " +
                                       "join GRLDomicilio DomOcupante on DomOcupante.iCvePersona = Ocupante.iCvePersona " +
                                       "join GRLPersona RepLegalOcu on RepLegalOcu.iCvePersona = RAIRegularizacion.iCveRepLegalOcupante " +
                                       "where RAIRegularizacion.iCveRegularizacion = " + cQuery,
                                       dataSourceName);
     }catch(SQLException ex){
       cMensaje = ex.getMessage();
     }catch(Exception ex2){
       ex2.printStackTrace();
     }

     try{
       vRegsUbicacion = super.FindByGeneric("", "select RAIUbicacion.iCveRegularizacion, " +
                                       "RAIUbicacion.cObraEncontrada, " +
                                       "RAIUbicacion.cPredioColindante, " +
                                       "RAIUbicacion.lDentroRecintoPortuario, " +
                                       "RAIUbicacion.iCvePuerto, " +
                                       "GRLPuerto.cDscPuerto, " +
                                       "GRLPais.cNombre as cPais, " +
                                       "GRLEntidadFed.cNombre as cEntidadFed, " +
                                       "GRLMunicipio.cNombre as cMunicipio, " +
                                       "GRLLocalidad.cNombre as cLocalidad, " +
                                       "RAIUbicacion.cUbicacion, " +
                                       "RAIUbicacion.cSuperficieTierra, " +
                                       "RAIUbicacion.cSuperficieAgua " +
                                       "from RAIUbicacion " +
                                       "left join GRLPuerto on GRLPuerto.iCvePuerto = RAIUbicacion.iCvePuerto " +
                                       "left join GRLPais on GRLPais.iCvePais = GRLPuerto.iCvePais " +
                                       "left join GRLEntidadFed on GRLEntidadFed.iCvePais = GRLPuerto.iCvePais " +
                                       "and GRLEntidadFed.iCveEntidadFed = GRLPuerto.iCveEntidadFed " +
                                       "left join GRLMunicipio on GRLMunicipio.iCvePais = GRLPuerto.iCvePais " +
                                       "and GRLMunicipio.iCveEntidadFed = GRLPuerto.iCveEntidadFed " +
                                       "and GRLMunicipio.iCveMunicipio = GRLPuerto.iCveMunicipio " +
                                       "left join GRLLocalidad on GRLLocalidad.iCvePais = GRLPuerto.iCvePais " +
                                       "and GRLLocalidad.iCveEntidadFed = GRLPuerto.iCveEntidadFed " +
                                       "and GRLLocalidad.iCveMunicipio = GRLPuerto.iCveMunicipio " +
                                       "and GRLLocalidad.iCveLocalidad = GRLPuerto.iCveLocalidad " +
                                       "where RAIUbicacion.iCveRegularizacion = " + cQuery,
                                       dataSourceName);
     }catch(SQLException ex){
       cMensaje = ex.getMessage();
     }catch(Exception ex2){
       ex2.printStackTrace();
     }

     //Asunto del Oficio.
     cAsunto = "REQUERIMIENTO";
     rep.iniciaReporte();
     if (!vRegs.isEmpty()){
       TVDinRep vDatos = (TVDinRep) vRegs.get(0);
       iCvePersona   = vDatos.getInt("iCveOcupante");
       iCveDomicilio = vDatos.getInt("iCveDomicilio");
       iCveRepLegal  = vDatos.getInt("iCveRepLegalOcupante");

       if (!vRegsUbicacion.isEmpty()){
         TVDinRep vDatosUbicacion = (TVDinRep) vRegsUbicacion.get(0);

         rep.comRemplaza("[cDscObra]",vDatosUbicacion.getString("cObraEncontrada").toLowerCase());
         if (vDatosUbicacion.getInt("lDentroRecintoPortuario") == 1)
           rep.comRemplaza("[cDscUbicacion]",vDatosUbicacion.getString("cDscPuerto").toLowerCase());
         else
           rep.comRemplaza("[cDscUbicacion]",vDatosUbicacion.getString("cUbicacion").toLowerCase());
         rep.comRemplaza("[cDscLocalidad]",vDatosUbicacion.getString("cLocalidad").toLowerCase());
       } else {
         rep.comRemplaza("[cDscObra]","");
         rep.comRemplaza("[cDscUbicacion]","");
         rep.comRemplaza("[cDscLocalidad]","");
       }
     }
     
     TVDinRep vInsertaFolio = new TVDinRep();
     TDObtenDatos dObten = new TDObtenDatos();
     TDRAIFolio dFolio = new TDRAIFolio();
     dObten.dFolio.setDatosFolio(cNumFolio);
     vInsertaFolio.put("iCveRegularizacion", Integer.parseInt(cQuery));
     vInsertaFolio.put("iEjercicio", dObten.dFolio.getCveEjercicio());
     vInsertaFolio.put("iCveOficina", dObten.dFolio.getCveOficina());
     vInsertaFolio.put("iCveDepartamento", dObten.dFolio.getCveDepartamento());
     vInsertaFolio.put("cDigitosFolio", dObten.dFolio.getCveDigitosFolio());
     vInsertaFolio.put("iConsecutivoFolio", dObten.dFolio.getCveConsecutivo());
     vInsertaFolio.put("iCveTipoDocto", 1);
     
     try {
		dFolio.insert(vInsertaFolio, null);
	} catch (DAOException e) {
		e.printStackTrace();
	}
     

     // Ejemplo de llamado empleado para oficina y depto fijos destino externo
     return new TDGeneral().generaOficioWord(cNumFolio,
                                     Integer.parseInt(iCveOficinaOrigen),Integer.parseInt(iCveDeptoOrigen),
                                     0,0,
                                     iCvePersona,iCveDomicilio,iCveRepLegal,
                                     "",cAsunto,
                                     "", "",
                                     true,"cCuerpo",vcCuerpo,
                                     true, vcCopiasPara,
                                     rep.getEtiquetasBuscar(), rep.getEtiquetasRemplazar());

  }

}

