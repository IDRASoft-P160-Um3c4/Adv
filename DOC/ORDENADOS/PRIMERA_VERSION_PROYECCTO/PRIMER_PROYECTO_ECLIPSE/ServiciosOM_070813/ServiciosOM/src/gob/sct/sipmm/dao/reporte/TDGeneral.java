package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import java.util.Vector;
import java.sql.SQLException;

/**
 * <p>Title: TDGeneral.java</p>
 * <p>Description: DAO con métodos generales para excel y word</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LSC. Rafael Miranda Blumenkron
 * @version 1.0
 */

public class TDGeneral extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private TFechas    tFecha      = new TFechas("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private String cLeyendaEnc    = VParametros.getPropEspecifica("LeyendaEnc");
  private String cLeyendaPie    = VParametros.getPropEspecifica("LeyendaPie");
  private String cEntidadRemplazaTexto = VParametros.getPropEspecifica("EntidadRemplazaTexto");

  public TDGeneral(){
  }

  public StringBuffer exportaExcel(String cQuery) throws Exception{
    Vector vRegs = new Vector();
    StringBuffer sbRes = new StringBuffer("");
    cError = "";
    int iRengIni = 10;
    char cColIni = 'A', cColAct;
    TExcel rep = new TExcel();

    rep.iniciaReporte();
    if(cQuery != null && !cQuery.equals(""))
      if (!cQuery.toUpperCase().startsWith("SELECT "))
        rep.comDespliega(String.valueOf(cColIni), iRengIni - 2, "ESTE METODO REQUIERE UN QUERY COMPLETO, NO SOLO FILTRO PARA EJECUCION");
      else
        rep.comDespliega(String.valueOf(cColIni),iRengIni - 2,cQuery);
    else
      rep.comDespliega(String.valueOf(cColIni), iRengIni - 2, "NO SE PROPORCIONO QUERY PARA EJECUCION");

    // El try-catch no debe cambiar en cada método, siempre es el mismo
    try{
      vRegs = super.FindByGeneric("", cQuery, dataSourceName);
    }catch(SQLException ex){
      cMensaje = ex.getMessage();
    }catch(Exception ex2){
      cMensaje += ex2.getMessage();
    }
    if (vRegs.size() > 0){
      TVDinRep regAct = (TVDinRep) vRegs.get(0);
      Object[] objTitulos = regAct.toHashMap().keySet().toArray();
      cColAct = cColIni;
      for (int i = 1; i < objTitulos.length; i++, cColAct++);
      cColAct = (cColAct < 'K')?'K':cColAct;
      rep.comAlineaRango(String.valueOf(cColIni), iRengIni-2, String.valueOf(cColAct), iRengIni-2, rep.getAT_COMBINA_IZQUIERDA());
      rep.comAlineaRangoVer(String.valueOf(cColIni), iRengIni-2, String.valueOf(cColAct), iRengIni-2, rep.getAT_VCENTRO());
      rep.comAlineaRangoVer(String.valueOf(cColIni), iRengIni-2, String.valueOf(cColAct), iRengIni-2, rep.getAT_VAJUSTAR());
      sbRes = rep.getSbDatos();
      sbRes.append(exportaExcel(vRegs, objTitulos, iRengIni,cColIni,true,true,true));
    }

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return sbRes;
  }

  public StringBuffer exportaExcel(Vector vRegs, Object[] oTitulos, int iRengIni, char cColIni, boolean lTitulos, boolean lBordes, boolean lFormatos){
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
          if(regAct.get(cTitulo) != null){
          cDato = regAct.get(cTitulo).toString();
          if (cTitulo.toUpperCase().startsWith("L"))
            cDato = cDato.equals("1")?"SI":"NO";
          sCol = String.valueOf(cColAct);
          rep.comDespliega(sCol,iReng,cDato);}
        }
      }
      cColAct = cColIni;
      cTitulo = "";
      for (int i = 0; i < oTitulos.length; i++, cColAct++){ // Despliegue de etiquetas, alineación de columnas
        sCol = String.valueOf(cColAct);
        cTitulo = (String)oTitulos[i];
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

  public Vector generaOficioWord(String cNumFolio,
                                      int iCveOficRemit, int iCveDeptoRemit,
                                      int iCveOficDest, int iCveDeptoDest,
                                      int iCvePersona, int iCveDomicilio, int iCveRepLegal,
                                      String cOficioMemo, String cAsunto,
                                      String cReferencias, String cNumerosInternos,
                                      boolean lCuerpo, String cEtiquetaBaseCuerpo, Vector vcCuerpo,
                                      boolean lCopiasPara, Vector vcCopiasPara,
                                      StringBuffer sbBuscaAdicional, StringBuffer sbRemplazaAdicional,
                                      String cFirmante){
         return this.generaOficioWord(cNumFolio,iCveOficRemit,iCveDeptoRemit,
         iCveOficDest,iCveDeptoDest,
         iCvePersona,iCveDomicilio,iCveRepLegal,
         cOficioMemo,cAsunto,
         cReferencias,cNumerosInternos,
         lCuerpo,cEtiquetaBaseCuerpo,vcCuerpo,
         lCopiasPara,vcCopiasPara,
         sbBuscaAdicional,sbRemplazaAdicional,"REPRESENTANTE LEGAL DE",cFirmante);
  }


  public Vector generaOficioWord(String cNumFolio,
                                      int iCveOficRemit, int iCveDeptoRemit,
                                      int iCveOficDest, int iCveDeptoDest,
                                      int iCvePersona, int iCveDomicilio, int iCveRepLegal,
                                      String cOficioMemo, String cAsunto,
                                      String cReferencias, String cNumerosInternos,
                                      boolean lCuerpo, String cEtiquetaBaseCuerpo, Vector vcCuerpo,
                                      boolean lCopiasPara, Vector vcCopiasPara,
                                      StringBuffer sbBuscaAdicional, StringBuffer sbRemplazaAdicional){
   return this.generaOficioWord(cNumFolio,iCveOficRemit,iCveDeptoRemit,
                                iCveOficDest,iCveDeptoDest,
                                iCvePersona,iCveDomicilio,iCveRepLegal,
                                cOficioMemo,cAsunto,
                                cReferencias,cNumerosInternos,
                                lCuerpo,cEtiquetaBaseCuerpo,vcCuerpo,
                                lCopiasPara,vcCopiasPara,
                                sbBuscaAdicional,sbRemplazaAdicional,"REPRESENTANTE LEGAL DE","");
  }


  public Vector generaOficioWord(String cNumFolio,
                                      int iCveOficRemit, int iCveDeptoRemit,
                                      int iCveOficDest, int iCveDeptoDest,
                                      int iCvePersona, int iCveDomicilio, int iCveRepLegal,
                                      String cOficioMemo, String cAsunto,
                                      String cReferencias, String cNumerosInternos,
                                      boolean lCuerpo, String cEtiquetaBaseCuerpo, Vector vcCuerpo,
                                      boolean lCopiasPara, Vector vcCopiasPara,
                                      StringBuffer sbBuscaAdicional, StringBuffer sbRemplazaAdicional,
                                      String cPuestoRepLegal,String cFirmante){
   TWord rep = new TWord();
   rep.iniciaReporte();
   etiquetasEncabezado(rep,cNumFolio,iCveOficRemit,iCveDeptoRemit,
                       cOficioMemo,cAsunto,cReferencias,cNumerosInternos,cFirmante);


   if(iCveOficDest == 0 && iCveDeptoDest == 0)
     etiquetasDestinatarioExterno(rep,iCvePersona,iCveDomicilio,iCveRepLegal,cPuestoRepLegal);
   else
     etiquetasDestinatarioInterno(rep,iCveOficDest,iCveDeptoDest);
   if(lCuerpo)
     rep.escribeParrafos(cEtiquetaBaseCuerpo,vcCuerpo,"","");
   etiquetasPie(rep,iCveOficRemit,iCveDeptoRemit);
   if(lCopiasPara)
     rep.escribeParrafos("cCopiaPara", vcCopiasPara,"c.c.p. ", "");
   if (sbBuscaAdicional != null && sbRemplazaAdicional != null)
     rep.anexaEtiquetas(sbBuscaAdicional, sbRemplazaAdicional);

   return rep.getVectorDatos(true);
  }

  public void etiquetasEncabezado(TWord rep, String cNumFolio, int iCveOficRemit, int iCveDeptoRemit, String cOficioMemo, String cAsuntoAlterno, String cReferencias, String cNumerosInternos){
      this.etiquetasEncabezado(rep, cNumFolio, iCveOficRemit, iCveDeptoRemit, cOficioMemo, cAsuntoAlterno, cReferencias, cNumerosInternos,"");
  }
  public void etiquetasEncabezado(TWord rep,
                                  String cNumFolio,
                                  int iCveOficRemit,
                                  int iCveDeptoRemit,
                                  String cOficioMemo,
                                  String cAsuntoAlterno,
                                  String cReferencias,
                                  String cNumerosInternos,
                                  String cFirmante){
     String cTemp;
     TDObtenDatos dObten = new TDObtenDatos();
     dObten.dFolio.setDatosFolio(cNumFolio);
     int iOficina = iCveOficRemit,
         iDepto   = iCveDeptoRemit;
     if (iOficina == 0 && iDepto == 0){
       if (dObten.dFolio.getCveOficina() > 0 && dObten.dFolio.getCveDepartamento() > 0){
         iOficina = dObten.dFolio.getCveOficina();
         iDepto   = dObten.dFolio.getCveDepartamento();
       }
     }

     TDObtenDatos dObten2 = new TDObtenDatos();
     dObten2.dOficDepto.setOficinaDepto(iOficina, iDepto);

     if(cFirmante.equals("")) {
       rep.comRemplaza("[cFirmante]",cFirmante);
       rep.comRemplaza("[cLeyendaFirmante]","");
     }
     rep.comRemplaza("[cLeyendaAlusiva]",cLeyendaEnc);

     TDObtenDatos dObten3 = new TDObtenDatos();
     Vector vRutaRemitente = new Vector();
     vRutaRemitente = dObten3.getOrganigrama(iOficina, iDepto, vRutaRemitente);
     rep.comEligeTabla("cRutaRemitente");
     for(int i=0; i<vRutaRemitente.size(); i++)
        rep.comDespliegaCelda((String)vRutaRemitente.get(i));

     cTemp = (cOficioMemo.equalsIgnoreCase("OFICIO"))?"OFICIO ":"";

     rep.comRemplaza("[cNumOficio]", cTemp + "No. " + cNumFolio);
     rep.comRemplaza("[cReferencias]", cReferencias);
     rep.comRemplaza("[cNumerosInternos]", cNumerosInternos);

     cTemp = dObten.dFolio.getAsunto();
     cTemp = (!cTemp.equals(""))?"ASUNTO: " + cTemp:(!cAsuntoAlterno.equals(""))?"ASUNTO: "+cAsuntoAlterno:"";
     rep.comRemplaza("[cAsunto]",cTemp);

     String cMunicipioEmision = dObten2.dOficDepto.vDatoOfic.getNomMunicipio();
     String[] aEntidadRemplaza = cEntidadRemplazaTexto.split("/");
     String[] aDatos;
     for (int i=0; i<aEntidadRemplaza.length; i++){
       aDatos = aEntidadRemplaza[i].split(",");
       if (Integer.parseInt(aDatos[0],10) == dObten2.dOficDepto.vDatoOfic.getCvePais() &&
           Integer.parseInt(aDatos[1],10) == dObten2.dOficDepto.vDatoOfic.getCveEntidadFed()){
         cMunicipioEmision = aDatos[2];
         break;
       }
     }
     cTemp = (cOficioMemo.equalsIgnoreCase("OFICIO"))?"":cOficioMemo;
     rep.comRemplaza("[cOficioMemo]", cTemp);
     rep.comRemplaza("[cMunicipioEmision]", cMunicipioEmision);
     rep.comRemplaza("[cEntidadEmision]", dObten2.dOficDepto.vDatoOfic.getAbrevEntidad() + ".");
     if(dObten.dFolio.getAsignacion() != null)
       rep.comRemplaza("[cFechaEmision]",tFecha.getFechaDDMMMMMYYYY(dObten.dFolio.getAsignacion()," de "));
  }

  public void etiquetasDestinatarioExterno(TWord rep, int iCvePersona, int iCveDomicilio, int iCveRepLegal){
    this.etiquetasDestinatarioExterno(rep,iCvePersona,iCveDomicilio,iCveRepLegal,"REPRESENTANTE LEGAL DE");
  }

  public void etiquetasDestinatarioExterno(TWord rep, int iCvePersona, int iCveDomicilio, int iCveRepLegal, String cLeyendaPuestoDest){
    TDObtenDatos dObten  = new TDObtenDatos();

    String cNombrePersonaDest,cPuestoDest,cNombreEmpresaDest,cCalle,cNumExterior,
        cNumInterior,cColonia,cCodigoPostal,cEntidad,cMunicipio,cPais;
    cNombrePersonaDest = cPuestoDest = cNombreEmpresaDest = cCalle = cNumExterior = cNumInterior = cColonia = cCodigoPostal = cEntidad = cMunicipio = cPais = "";
    if (iCvePersona > 0){
      dObten.dPersona.setPersona(iCvePersona,iCveDomicilio);

      cNombrePersonaDest = dObten.dPersona.getNomCompleto();
      if (iCveRepLegal > 0 && dObten.dPersona.getTipoPersona() > 1){
        TDObtenDatos dObten2 = new TDObtenDatos();
        dObten2.dPersona.setPersona(iCveRepLegal,0);
        cNombreEmpresaDest = cNombrePersonaDest;
        cPuestoDest = cLeyendaPuestoDest;
        cNombrePersonaDest = dObten2.dPersona.getNomCompleto();
      }
      cCalle = dObten.dPersona.getCalle();
      cNumExterior = dObten.dPersona.getNumExterior();
      if (!cNumExterior.equals(""))
        cNumExterior = "No. " + cNumExterior;
      cNumInterior = dObten.dPersona.getNumInterior();
      if (!cNumInterior.equals(""))
        cNumInterior = " - " + cNumInterior;
      cColonia = dObten.dPersona.getColonia();
      cCodigoPostal = dObten.dPersona.getCodPostal();
      cMunicipio = dObten.dPersona.getNomMunicipio();
      cEntidad = dObten.dPersona.getAbrevEntidad() + ".";
      cPais = dObten.dPersona.getNomPais();
    }

    rep.comRemplaza("[cNombrePersonaDest]",cNombrePersonaDest);
    rep.comRemplaza("[cPuestoDest]", cPuestoDest);
    rep.comRemplaza("[cNombreEmpresaDest]", cNombreEmpresaDest);

    rep.comRemplaza("[cCalle]",cCalle);
    rep.comRemplaza("[cNumExterior]",cNumExterior);
    rep.comRemplaza("[cNumInterior]",cNumInterior);
    rep.comRemplaza("[cColonia]",cColonia);
    rep.comRemplaza("[cCodigoPostal]",cCodigoPostal);
    rep.comRemplaza("[cMunicipio]",cMunicipio);
    rep.comRemplaza("[cEntidad]",cEntidad);
    rep.comRemplaza("[cPais]",cPais);
  }

  public void etiquetasDestinatarioInterno(TWord rep, int iCveOficina, int iCveDepartamento){
    TDObtenDatos dObten  = new TDObtenDatos();

    String cNombrePersonaDest,cPuestoDest,cNombreEmpresaDest,cCalle,cNumExterior,
        cNumInterior,cColonia,cCodigoPostal,cEntidad,cMunicipio,cPais;
    cNombrePersonaDest = cPuestoDest = cNombreEmpresaDest = cCalle = cNumExterior = cNumInterior = cColonia = cCodigoPostal = cEntidad = cMunicipio = cPais = "";
    if (iCveOficina > 0){
      dObten.dOficDepto.setOficinaDepto(iCveOficina, iCveDepartamento);

      cNombrePersonaDest = dObten.dOficDepto.getTitular();
      cPuestoDest = dObten.dOficDepto.getPuestoTitular();
      cNombreEmpresaDest = dObten.dOficDepto.vDatoOfic.getDscOficina();
      cCalle = dObten.dOficDepto.vDatoOfic.getCalleYNo();
      cColonia = dObten.dOficDepto.vDatoOfic.getColonia();
      cCodigoPostal = dObten.dOficDepto.vDatoOfic.getCodPostal();
      cMunicipio = dObten.dOficDepto.vDatoOfic.getNomMunicipio();
      cEntidad = dObten.dOficDepto.vDatoOfic.getAbrevEntidad() + ".";
      cPais = dObten.dOficDepto.vDatoOfic.getNomPais();
    }

    rep.comRemplaza("[cNombrePersonaDest]",cNombrePersonaDest);
    rep.comRemplaza("[cPuestoDest]", cPuestoDest);
    rep.comRemplaza("[cNombreEmpresaDest]", cNombreEmpresaDest);

    rep.comRemplaza("[cCalle]",cCalle);
    rep.comRemplaza("[cNumExterior]",cNumExterior);
    rep.comRemplaza("[cNumInterior]",cNumInterior);
    rep.comRemplaza("[cColonia]",cColonia);
    rep.comRemplaza("[cCodigoPostal]",cCodigoPostal);
    rep.comRemplaza("[cMunicipio]",cMunicipio);
    rep.comRemplaza("[cEntidad]",cEntidad);
    rep.comRemplaza("[cPais]",cPais);
  }

  public void etiquetasPie(TWord rep, int iCveOficina, int iCveDepartamento){
    TDObtenDatos dObten = new TDObtenDatos();
    dObten.dOficDepto.setOficinaDepto(iCveOficina,iCveDepartamento);
    rep.comRemplaza("[cLeyendaPie]",cLeyendaPie);
    rep.comRemplaza("[cPuestoTitular]",dObten.dOficDepto.getPuestoTitular());
    rep.comRemplaza("[cNombreRemitente]",dObten.dOficDepto.getTitular());
   }

}
