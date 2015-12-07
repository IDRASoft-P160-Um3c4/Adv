package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.seguridad.vo.TVDinRep;
import com.micper.sql.*;
import java.util.Vector;
import gob.sct.sipmm.dao.*;
import java.util.HashMap;

/**
 * <p>Title: TDSenalamiento.java</p>
 * <p>Description: DAO con métodos para reportes de Señalamiento Marítimo</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LSC. Rafael Miranda Blumenkron
 * @author iCaballero
 * @version 1.0
 */

public class TDSenalamiento
    extends DAOBase{
  private String cSistema = "44";
  private TParametro VParametros = new TParametro(cSistema);
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  private TFechas tFecha = new TFechas(cSistema);
  private TNumeros tNum = new TNumeros();

  private String cNombreReporte = "";
  private int iNumeroReporte = 0;
  private int iNumeroCopias = 1;
  private int iTiempoEspera = 1500;
  private boolean lAutoImprimir = false;
  private String cRutaImgReporte = VParametros.getPropEspecifica("ExcelRutaOrig") + VParametros.getPropEspecifica("RutaImgReporteSenalamiento");

  public TDSenalamiento(){
  }

  public void inicializaDatos(String cNumReporte){
    String cNumModuloSenalamiento = VParametros.getPropEspecifica("SenalamientoModulo");

    String cSQL = "SELECT " +
                  "  GRLReporte.iCveSistema, " +
                  "  GRLReporte.iCveModulo, " +
                  "  GRLReporte.iNumReporte, " +
                  "  GRLReporte.cNomReporte, " +
                  "  GRLReporte.iNumCopias, " +
                  "  GRLReporte.lAutoImprimir " +
                  "FROM GRLReporte " +
                  "WHERE iCveSistema = " + cSistema +
                  "  AND iCveModulo  = " + cNumModuloSenalamiento +
                  "  AND iNumReporte = " + cNumReporte;
    try{
      TDGRLReporte dReporte = new TDGRLReporte();
      Vector vDatosReporte = dReporte.findByCustom("iCveSistema,iCveModulo,iNumReporte",cSQL);
      if(vDatosReporte != null){
        TVDinRep dRegistro = (TVDinRep) vDatosReporte.get(0);
        cNombreReporte = dRegistro.getString("cNomReporte");
        iNumeroReporte = dRegistro.getInt("iNumReporte");
        iNumeroCopias = dRegistro.getInt("iNumCopias");
        lAutoImprimir = (dRegistro.getInt("lAutoImprimir") == 0) ? false : true;
      }
    } catch(Exception e){
    }
  }

  public HashMap cuadernoFarosJasper(String cFiltro) throws Exception{
    HashMap hParametros = new HashMap();
    hParametros.put("cPuestoSecretario", VParametros.getPropEspecifica("SecretarioPuesto"));
    hParametros.put("cSecretarioTitular", VParametros.getPropEspecifica("SecretarioTitular"));
    String cNota = VParametros.getPropEspecifica("AlcanceGeogNota1") + " " +
                   VParametros.getPropEspecifica("AlcanceGeogNota2") + " " +
                   VParametros.getPropEspecifica("AlcanceGeogNota3");
    hParametros.put("cLeyendaAlcances", cNota);

    // Inicializa variables y valores recibidos en filtro para armar parametros correctos
    int iLitoral = 0,iEntidadFed = 0,iPuerto = -1,iCvePersona=-1;
    String[] aFiltro;
    String cTemp = "";
    if(!cFiltro.trim().equals("")){
      aFiltro = cFiltro.split(",");
      if(aFiltro.length == 4){
        iLitoral = Integer.parseInt(aFiltro[0]);
        iEntidadFed = Integer.parseInt(aFiltro[1]);
        iPuerto = Integer.parseInt(aFiltro[2]);
        iCvePersona = Integer.parseInt(aFiltro[3]);
      }
      if(aFiltro.length == 1){
        iCvePersona = Integer.parseInt(aFiltro[0].trim(),10);
      }

    }
    hParametros.put("cConstruccionClause", " AND S.lEnConstruccion = 0 ");
    hParametros.put("cLitoralClause", ((iLitoral > 0)?" AND EFL.iCveLitoral = " + iLitoral + " ":""));
    hParametros.put("cEntidadFedClause", ((iEntidadFed > 0)?" AND EFL.iCveEntidadFed = " + iEntidadFed + " ":""));
    hParametros.put("cPuertoClause", ((iPuerto >= 0)?" AND P.iCvePuerto = " + iPuerto + " ":""));
    if(iCvePersona >= 0){
      hParametros.put("cJoinPersona",   " LEFT JOIN RGSRESPONSABLESENAL RS ON S.ICVESENAL = RS.ICVESENAL ");
      hParametros.put("cPersonaClause", " AND RS.iCvePersona = " + iCvePersona + " ");
    }else{
      hParametros.put("cJoinPersona",   " ");
      hParametros.put("cPersonaClause", " ");
    }

    return hParametros;
  }

  public StringBuffer alcanceGeografico(String cFiltro) throws Exception{
    return this.despliegaAlcanceGeografico(cFiltro,9,'B');
  }

  public StringBuffer cuadernoFaros(String cFiltro) throws Exception{
    TDRGSSenal dSenal = new TDRGSSenal();
    TDRGSUbicacion dUbicacion = new TDRGSUbicacion();
    TDRGSAlturaLuz dAlturaLuz = new TDRGSAlturaLuz();
    TDRGSCarLuminosa dCarLuminosa = new TDRGSCarLuminosa();
    TDRGSLuzEclipse dLuzEclipse = new TDRGSLuzEclipse();
    Vector vcSenales = new Vector(),
                       vcUbicacion = new Vector(),
                                     vcAlturaLuz = new Vector(),
        vcCarLuminosa = new Vector(),
                        vcLuzEclipse = new Vector();
    cError = "";
    StringBuffer sbResultado = new StringBuffer(""),
                               sbTemp = new StringBuffer("");
    TExcel rep = new TExcel();
    int iLitoral = 0,iEntidadFed = 0,iPuerto = -1,iCvePersona=-1;
    String[] aFiltro;
    String cTemp = "";
    if(!cFiltro.trim().equals("")){
      aFiltro = cFiltro.split(",");
      if(aFiltro.length == 4){
        iLitoral = Integer.parseInt(aFiltro[0]);
        iEntidadFed = Integer.parseInt(aFiltro[1]);
        iPuerto = Integer.parseInt(aFiltro[2]);
        iCvePersona = Integer.parseInt(aFiltro[3]);
      }
      if(aFiltro.length == 1){
        iCvePersona = Integer.parseInt(aFiltro[0].trim(),10);
      }

    }
    // Búsqueda de señales a desplegar
    try{
      if(iCvePersona >= 0){
        vcSenales = dSenal.findCuadernoFaros(iLitoral,iEntidadFed,iPuerto,iCvePersona,false);
      }
      else{
        vcSenales = dSenal.findCuadernoFaros(iLitoral,iEntidadFed,iPuerto,false);
      }
    } catch(Exception e){
      e.printStackTrace();
      cMensaje += e.getMessage();
    }

    //Despliegue de datos básicos
    int iLitoralAnt = 0,
        iEntidadAnt = 0,
        iCardinalAnt = 0,
        iSenalAnt = 0,
        iRengIni = 6,
        iReng = iRengIni,
        iRengIniSenal = iReng,
        iRengFinSenal = iReng,
        iHojaFinal = 4,
        iLetrasNombre = 30,
        iLetrasDatos = 31,
        iRenglones = 0,
        iRengCarLumin = 0;
    TVDinRep vSenal;
    String cCardinal = "";
    if(vcSenales != null && vcSenales.size() > 0){
      rep.iniciaReporte();
      // Imagenes en Introducción de Cuaderno de Faros
      rep.comEligeHoja(3);
      cTemp = cRutaImgReporte + "RGSRegionIALA.jpg";
      //rep.comInsertaImagen("C",Integer.parseInt(VParametros.getPropEspecifica("RenglonImagenRegionIALA")),cTemp,630,385,0,0);
      cTemp = cRutaImgReporte + "RGSRacones.jpg";
      //rep.comInsertaImagen("C",Integer.parseInt(VParametros.getPropEspecifica("RenglonImagenRacones")),cTemp,630,385,0,0);
      cTemp = cRutaImgReporte + "RGSSectores.jpg";
      //rep.comInsertaImagen("C",Integer.parseInt(VParametros.getPropEspecifica("RenglonImagenSectorSacrificios")),cTemp,630,385,0,0);

      // Tabla de alcances geográficos en cuaderno de faros.
      rep.comEligeHoja(4);
      try{
        sbTemp = this.despliegaAlcanceGeografico(cFiltro,13,'C');
      } catch(Exception e){
        e.printStackTrace();
        cMensaje += e.getMessage();
      }
      sbResultado.append(rep.getSbDatos()).append(sbTemp);

      // Inicia ciclo para despliegue de datos de señal
      rep.iniciaReporte();
      for(int i = 0;i < vcSenales.size();i++){
        vSenal = (TVDinRep) vcSenales.get(i);
        switch(vSenal.getInt("iPuntoCardinal")){
          case 0:
            cCardinal = "";
            break;
          case 1:
            cCardinal = "NORTE";
            break;
          case 2:
            cCardinal = "SUR";
            break;
          case 3:
            cCardinal = "ORIENTE";
            break;
          case 4:
            cCardinal = "PONIENTE";
            break;
        }
        if(iLitoralAnt != vSenal.getInt("iCveLitoral")){ // Cambio de Litoral, crea portada
          rep.comCopiaHoja(1,rep.getAT_POSFINAL(),"Portada_" + vSenal.getString("cDscLitoral"));
          iHojaFinal++;
          rep.comEligeHoja(iHojaFinal);
          rep.comDespliega("C",9,vSenal.getString("cDscLitoral"));
          iLitoralAnt = vSenal.getInt("iCveLitoral");
        }
        if(iEntidadAnt != vSenal.getInt("iCveEntidadFed") || iCardinalAnt != vSenal.getInt("iPuntoCardinal")){ // cambio de Entidad o punto cardinal, hoja nueva
          if(iEntidadAnt != vSenal.getInt("iCveEntidadFed")){
            //Despliega imagen del estado correspondiente
            rep.comCopiaHoja(1,rep.getAT_POSFINAL(),"Plano_" + vSenal.getString("cDscEntidad"));
            iHojaFinal++;
            rep.comEligeHoja(iHojaFinal);
            cTemp = cRutaImgReporte + "RGSPlano" + tNum.getNumeroSinSeparador(new Integer(vSenal.getInt("iCveEntidadFed")),2) + ".jpg";
            //rep.comInsertaImagen("C",9,cTemp,630,425,0,0);
          }
          //Despliega encabezado de señales
          rep.comCopiaHoja(2,rep.getAT_POSFINAL(),vSenal.getString("cDscEntidad") + "_" + cCardinal);
          iHojaFinal++;
          rep.comEligeHoja(iHojaFinal);
          rep.comDespliega("B",1,vSenal.getString("cDscLitoral"));
          rep.comDespliega("D",1,vSenal.getString("cDscEntidad"));
          rep.comDespliega("N",1,cCardinal);
          iEntidadAnt = vSenal.getInt("iCveEntidadFed");
          iCardinalAnt = vSenal.getInt("iPuntoCardinal");
          iReng = iRengIni;
        }

        // Despliegue de datos de señal
        if(iSenalAnt != vSenal.getInt("iCveSenal")){
          iReng++;
          iRengIniSenal = iRengFinSenal = iRengCarLumin = iReng;
          iSenalAnt = vSenal.getInt("iCveSenal");

          //Busca ubicaciones de señal ordenados descendentemente
          vcUbicacion = new Vector();
          try{
            vcUbicacion = dUbicacion.findUbicacionSenal(iSenalAnt);
          } catch(Exception e){}

          // Busca alturas de luz de la señal ordenados de acuerdo al consecutivo
          vcAlturaLuz = new Vector();
          vcCarLuminosa = new Vector();
          vcLuzEclipse = new Vector();
          if(vSenal.getInt("lLuminosa") == 1){
            try{
              vcAlturaLuz = dAlturaLuz.findAlturaLuz(iSenalAnt);
            } catch(Exception e){}
            try{
              vcCarLuminosa = dCarLuminosa.findCarLuminosa(iSenalAnt);
            } catch(Exception e){}
            try{
              vcLuzEclipse = dLuzEclipse.findLuzEclipse(iSenalAnt);
            } catch(Exception e){}
          }

          // Despliega Número nacional de señal
          cTemp = tNum.getNumeroSinSeparador(new Integer(vSenal.getInt("iCveEntidadFed")),2);
          cTemp += "-" + tNum.getNumeroSinSeparador(new Integer(vSenal.getInt("iNumNacional")),3);
          if(vSenal.getInt("iConsecNumNacional") > 0)
            cTemp += "." + vSenal.getInt("iConsecNumNacional");
          rep.comDespliegaAlineado("B",iReng,cTemp,false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());

          // Despliega Número internacional de señal en caso de existir
          cTemp = "";
          if(vSenal.getInt("iNumInternacional") > 0){
            cTemp = vSenal.getString("cLetraInternacional") + tNum.getNumeroSinSeparador(new Integer(vSenal.getInt("iNumInternacional")),4);
            rep.comDespliegaAlineado("B",iReng + 1,cTemp,false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
            iRengFinSenal = iReng + 1;
          }

          // Despliega Nombre de señal
          cTemp = vSenal.getString("cNomSenal");
          iRenglones = 1;
          if(cTemp.length() > 0){
            iRenglones = (int) (cTemp.length() / iLetrasNombre);
            if(cTemp.length() % iLetrasNombre > 0)
              iRenglones++;
          }
          rep.comDespliegaAlineado("C",iReng,"D",iReng + iRenglones - 1,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
          rep.comAlineaRango("C",iReng,"D",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
          if(iRengFinSenal < (iReng + iRenglones - 1))
            iRengFinSenal = iReng + iRenglones - 1;

            // Despliega Latitud y Longitud
          if(vcUbicacion != null && vcUbicacion.size() > 0){
            TVDinRep vUbicacion = (TVDinRep) vcUbicacion.get(0);
            rep.comDespliegaAlineado("E",iReng,vUbicacion.getString("iGradosLatitud"),false,rep.getAT_HDERECHA(),rep.getAT_VSUPERIOR());
            rep.comDespliegaAlineado("F",iReng,vUbicacion.getString("iMinutosLatitud"),false,rep.getAT_HDERECHA(),rep.getAT_VSUPERIOR());
            rep.comDespliegaAlineado("G",iReng,vUbicacion.getString("dSegundosLatitud"),false,rep.getAT_HDERECHA(),rep.getAT_VSUPERIOR());
            rep.comDespliegaAlineado("E",iReng + 1,vUbicacion.getString("iGradosLongitud"),false,rep.getAT_HDERECHA(),rep.getAT_VSUPERIOR());
            rep.comDespliegaAlineado("F",iReng + 1,vUbicacion.getString("iMinutosLongitud"),false,rep.getAT_HDERECHA(),rep.getAT_VSUPERIOR());
            rep.comDespliegaAlineado("G",iReng + 1,vUbicacion.getString("dSegundosLongitud"),false,rep.getAT_HDERECHA(),rep.getAT_VSUPERIOR());
            rep.comCellFormat("E",iReng,"E",iReng + 1,"0°");
            rep.comCellFormat("F",iReng,"F",iReng + 1,"00'");
            rep.comCellFormat("G",iReng,"G",iReng + 1,"00''");
            if(iRengFinSenal < (iReng + 1))
              iRengFinSenal = iReng + 1;
          }
          if(vSenal.getInt("lLuminosa") == 1){ // solo para señales luminosas
            // Despliega Características Luminosas
            if(vcCarLuminosa != null && vcCarLuminosa.size() > 0){
              TVDinRep vCarLuminosa;
              for(int j = 0;j < vcCarLuminosa.size();j++){
                vCarLuminosa = (TVDinRep) vcCarLuminosa.get(j);
                if(vCarLuminosa.getInt("lDestellante") == 1){
                  if(vCarLuminosa.getInt("iNumDestellos") > 0){
                    cTemp = vCarLuminosa.getString("iNumDestellos") + " D." + vCarLuminosa.getString("cAbrevColor") + ".";
                    if(vCarLuminosa.getInt("iCveTipoDestello") > 0)
                      cTemp += "  " + vCarLuminosa.getString("cAbrevDestello");
                    rep.comDespliegaAlineado("I",iRengCarLumin,"J",iRengCarLumin,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
                    iRengCarLumin++;
                  }
                  if(vCarLuminosa.getInt("iNumDestellosMinuto") > 0){
                    cTemp = vCarLuminosa.getString("iNumDestellos") + " D.P.M.";
                    rep.comDespliegaAlineado("I",iRengCarLumin,"J",iRengCarLumin,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
                    iRengCarLumin++;
                  }
                } else{
                  cTemp = "LUZ FIJA " + vCarLuminosa.getString("cDscColor");
                  rep.comDespliegaAlineado("I",iRengCarLumin,"J",iRengCarLumin,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
                  iRengCarLumin++;
                }
              }
            }

            // Despliegue del Período de la señal
            if(vSenal.getDouble("dPeriodo") > 0.0d){
              rep.comDespliegaAlineado("I",iRengCarLumin,"Período",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
              rep.comDespliegaAlineado("J",iRengCarLumin,tNum.getNumeroDecimal(vSenal.getDouble("dPeriodo"),1,false) + " seg.",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
              iRengCarLumin++;
            }

            // Despliegue de pares de valores de Luz y Eclipse
            if(vcLuzEclipse != null && vcLuzEclipse.size() > 0){
              TVDinRep vLuzEclipse;
              rep.comDespliegaAlineado("I",iRengCarLumin,"Luz",false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
              rep.comDespliegaAlineado("J",iRengCarLumin,"Eclipse",false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
              iRengCarLumin++;
              for(int j = 0;j < vcLuzEclipse.size();j++){
                vLuzEclipse = (TVDinRep) vcLuzEclipse.get(j);
                rep.comDespliegaAlineado("I",iRengCarLumin,tNum.getNumeroDecimal(vLuzEclipse.getDouble("dLuz"),1,false),false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
                rep.comDespliegaAlineado("J",iRengCarLumin,tNum.getNumeroDecimal(vLuzEclipse.getDouble("dEclipse"),1,false),false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
                rep.comCellFormat("I",iRengCarLumin,"J",iRengCarLumin,"0.0");
                iRengCarLumin++;
              }
            }
            if(iRengFinSenal < (iRengCarLumin - 1))
              iRengFinSenal = iRengCarLumin - 1;

              // Despliega Alturas de Luz
            if(vcAlturaLuz != null && vcAlturaLuz.size() > 0){
              TVDinRep vAlturaLuz;
              for(int j = 0;j < vcAlturaLuz.size();j++){
                vAlturaLuz = (TVDinRep) vcAlturaLuz.get(j);
                rep.comDespliegaAlineado("K",iReng + j,vAlturaLuz.getString("dAlturaLuz"),false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
                rep.comCellFormat("K",iReng + j,"K",iReng + j,"0.0");
                if(iRengFinSenal < (iReng + j))
                  iRengFinSenal = iReng + j;
              }
            }

            // Despliega Alcance Geográfico
            rep.comDespliegaAlineado("L",iReng,vSenal.getString("dAlcanceGeografico"),false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
            rep.comCellFormat("L",iReng,"L",iReng,"0.0");

            // Despliega Alcances Luminosos
            rep.comDespliegaAlineado("M",iReng,vSenal.getString("dAlcanceLuminosoMin"),false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
            rep.comCellFormat("M",iReng,"M",iReng,"0.0");
            if(vSenal.getDouble("dAlcanceLuminosoMax") > 0.0d){
              rep.comDespliegaAlineado("M",iReng + 1,"A",false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
              rep.comDespliegaAlineado("M",iReng + 2,vSenal.getString("dAlcanceLuminosoMax"),false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
              rep.comCellFormat("M",iReng + 2,"M",iReng + 2,"0.0");
              if(iRengFinSenal < (iReng + 2))
                iRengFinSenal = iReng + 2;
            }
          }
          if(vSenal.getInt("lLuminosa") == 0 && vSenal.getInt("lRadioelectrica") == 0)
            rep.comDespliegaAlineado("I",iRengCarLumin,"J",iRengCarLumin,"C I E G A",false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
          if(vSenal.getInt("lRadioelectrica") == 1){ // solo para señales radioelectricas
            iRengCarLumin++;
            rep.comDespliegaAlineado("I",iRengCarLumin - 1,"J",iRengCarLumin - 1,"Característica",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
            rep.comDespliegaAlineado("I",iRengCarLumin,"J",iRengCarLumin,"Código Morse",false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
            rep.comDespliegaAlineado("I",iRengCarLumin + 1,"Letra:",false,rep.getAT_HDERECHA(),rep.getAT_VSUPERIOR());
            rep.comDespliegaAlineado("J",iRengCarLumin + 1,"''" + vSenal.getString("cCodigoMorse") + "'",false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
            if(iRengFinSenal < (iRengCarLumin + 1))
              iRengFinSenal = iRengCarLumin + 1;
          }

          // Despliega Descripción de Localización de señal
          cTemp = vSenal.getString("cDscUbicacion");
          iRenglones = 1;
          if(cTemp.length() > 0){
            iReng += iRenglones;
            iRenglones = (int) (cTemp.length() / iLetrasNombre);
            if(cTemp.length() % iLetrasNombre > 0)
              iRenglones++;
            rep.comDespliegaAlineado("C",iReng,"D",iReng + iRenglones - 1,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
            rep.comAlineaRango("C",iReng,"D",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
            if(iRengFinSenal < (iReng + iRenglones - 1))
              iRengFinSenal = iReng + iRenglones - 1;
          }

          // Despliega Descripción y Datos Complementarios
          cTemp = "";
          if(vSenal.getInt("lLuminosa") == 1){ // solo para señales luminosas
//            if(vSenal.getInt("iCveTipoSenal") > 0)
//              cTemp += vSenal.getString("cDscTipoSenal") + ". ";
            if(vSenal.getInt("iCveForma") > 0)
//              cTemp += "DE FORMA " + vSenal.getString("cDscForma") + ". ";
              cTemp += vSenal.getString("cDscForma") + ". ";
            if(vSenal.getInt("iCveMaterial") > 0)
              cTemp += "DE " + vSenal.getString("cDscMaterial") + ". ";
            if(vSenal.getDouble("dAlturaEstructura") > 0.0d)
              cTemp += "DE " + vSenal.getString("dAlturaEstructura") + " m. DE ALTURA. ";
            if(!vSenal.getString("cDscColor").equals(""))
              cTemp += "COLOR " + vSenal.getString("cDscColor") + ". ";
            if(vSenal.getInt("iOptica") > 0)
              cTemp += "CON ÓPTICA DE " + vSenal.getString("iOptica") + "mm. ";
            if(vSenal.getInt("iCveSistEnergia") > 0)
              cTemp += "SISTEMA DE ENERGIA " + vSenal.getString("cDscSistEnergia") + ". ";
            if(vSenal.getInt("lMotogenerador") == 1)
              cTemp += "CUENTA CON PLANTA MOTOGENERADORA. ";
            if(!vSenal.getString("cDscMarcaDiurna").equals(""))
              cTemp += "MARCA DIURNA: " + vSenal.getString("cDscMarcaDiurna") + ". ";
            if(!vSenal.getString("cDatosAdicionales").equals(""))
              cTemp += vSenal.getString("cDatosAdicionales") + ". ";
          }
          if(vSenal.getInt("lRadioelectrica") == 1){ // solo para señales radioelectricas
            cTemp += vSenal.getString("cDscTipoSenal") + ". ";
            cTemp += "CON RESPUESTA AZIMUTAL DE " + vSenal.getString("iRespuestaAzimutal") + "° ";
            cTemp += "COBERTURA DE " + vSenal.getString("iCobertura") + " M.N. ";
            if(!vSenal.getString("cDatosAdicionales").equals(""))
              cTemp += " " + vSenal.getString("cDatosAdicionales") + ". ";
          }
          if(vSenal.getInt("iCvePuerto") > 0)
            cTemp += " UBICADA EN EL PUERTO " + vSenal.getString("cDscPuerto") + ".";
          if(cTemp.length() > 0){
            iRenglones = (int) (cTemp.length() / iLetrasDatos);
            if(cTemp.length() % iLetrasDatos > 0)
              iRenglones++;
            rep.comDespliegaAlineado("N",iRengIniSenal,"N",iRengIniSenal + iRenglones - 1,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
            rep.comAlineaRango("N",iRengIniSenal,"N",iRengIniSenal + iRenglones - 1,rep.getAT_HJUSTIFICA());
            if(iRengFinSenal < (iRengIniSenal + iRenglones - 1))
              iRengFinSenal = iRengIniSenal + iRenglones - 1;
          }

          // Dibuja bordes correspondientes
          rep.comBordeRededor("B",iRengIniSenal,"B",iRengFinSenal,1,1);
          rep.comBordeRededor("C",iRengIniSenal,"D",iRengFinSenal,1,1);
          rep.comBordeRededor("E",iRengIniSenal,"H",iRengFinSenal,1,1);
          rep.comBordeRededor("I",iRengIniSenal,"J",iRengFinSenal,1,1);
          rep.comBordeRededor("K",iRengIniSenal,"K",iRengFinSenal,1,1);
          rep.comBordeRededor("L",iRengIniSenal,"L",iRengFinSenal,1,1);
          rep.comBordeRededor("M",iRengIniSenal,"M",iRengFinSenal,1,1);
          rep.comBordeRededor("N",iRengIniSenal,"N",iRengFinSenal,1,1);
          iReng = iRengFinSenal;
        }
      }
    }
    sbResultado.append(rep.getSbDatos());

    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return sbResultado;
  }

  public StringBuffer despliegaAlcanceGeografico(String cFiltro,int iRengInicial,char cColInicial) throws Exception{
    TDRGSAlcanceGeografico dAlcanceGeog = new TDRGSAlcanceGeografico();
    Vector vAlcances = new Vector();
    cError = "";
    int iRengIni = iRengInicial,iReng,iNumGrupos = 7,iRengGrupo;
    char cColIni = cColInicial,cCol = cColIni;
    TExcel rep = new TExcel();
    rep.iniciaReporte();
    try{
      vAlcances = dAlcanceGeog.findByCustom("dAlturaMetro","SELECT dAlturaMetro, dAlcanceMillaNautica FROM RGSAlcanceGeografico ORDER BY dAlturaMetro");
    } catch(Exception e){
      e.printStackTrace();
      cMensaje += e.getMessage();
    }
    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    TVDinRep vData;
    iReng = iRengIni;
    iRengGrupo = vAlcances.size() / iNumGrupos;
    if( (vAlcances.size() % iNumGrupos) > 0)
      iRengGrupo++;
    for(int i = 0;i < vAlcances.size();i++){
      vData = (TVDinRep) vAlcances.get(i);
      rep.comDespliegaAlineado(String.valueOf(cCol),iReng,vData.getString("dAlturaMetro"),false,rep.getAT_HCENTRO(),"");
      rep.comDespliegaAlineado(String.valueOf( (char) (cCol + 1)),iReng,vData.getString("dAlcanceMillaNautica"),false,rep.getAT_HCENTRO(),"");
      rep.comCellFormat(String.valueOf(cCol),iReng,String.valueOf( (char) (cCol + 1)),iReng,"0.0");
      rep.comBordeTotal(String.valueOf(cCol),iReng,String.valueOf( (char) (cCol + 1)),iReng,1);
      iReng++;
      if(iReng >= (iRengIni + iRengGrupo)){
        iReng = iRengIni;
        cCol = (char) (cCol + 3);
      }
    }
    iReng = iRengIni + iRengGrupo + 1;
    String cNota = VParametros.getPropEspecifica("AlcanceGeogNota1") + " " +
                   VParametros.getPropEspecifica("AlcanceGeogNota2") + " " +
                   VParametros.getPropEspecifica("AlcanceGeogNota3");
    if(cNota == null)
      cNota = "";
    if(!cNota.equals("")){
      rep.comDespliegaCombinado(cNota,String.valueOf(cColIni),iReng,String.valueOf( (char) (cColIni + (iNumGrupos * 3) - 2)),iReng + 2,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR(),false, -1,true,false,1,1);
      rep.comAlineaRango(String.valueOf(cColIni),iReng,String.valueOf( (char) (cColIni + (iNumGrupos * 3) - 2)),iReng + 2,rep.getAT_HJUSTIFICA());
    }
    return rep.getSbDatos();
  }

  public StringBuffer solicitudComunicacionSF(String cFiltro) throws Exception{
    return this.solicitudComunicacion(cFiltro,"","","");
  }

  public StringBuffer solicitudComunicacion(String cFiltro,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen) throws Exception{
    String cLeyendaEnc = "",
           cLeyendaPie = "",
           cEntidadRemplazaTexto = "",
           cTemp = "";
    int iCveOficinaOrigen = 0,
        iCveDeptoOrigen = 0,
        iReng = 1,
        iRengRuta = 0,
        iLetrasRengParrafo = 90,
        iLetrasRengParrafoCP = 100,
        iRenglones = 0;
    String[] aFiltro = null;
    int iEjercicio = 0;
    int iSolCom = 0;
    String cCodigo = "";

    cError = "";
    TExcel rep = new TExcel();
    rep.iniciaReporte();
    if(!cFiltro.equals("")){
      TDObtenDatos dDatos = new TDObtenDatos();
      cLeyendaEnc = VParametros.getPropEspecifica("LeyendaEnc");
      cLeyendaPie = VParametros.getPropEspecifica("LeyendaPie");
      cEntidadRemplazaTexto = VParametros.getPropEspecifica("EntidadRemplazaTexto");

      try{
        aFiltro = cFiltro.split(",");
        iEjercicio = Integer.parseInt(aFiltro[0]);
        iSolCom = Integer.parseInt(aFiltro[1]);
        cCodigo = aFiltro[2];
      } catch(Exception e){
        cError += "\\n - No se cuenta con ejercicio o número de solicitud";
      }
      // Busca datos de solicitud de comunicacion
      Vector vDatosSolCom = new Vector();
      TVDinRep dDatosSolCom = new TVDinRep();
      String cSql = "SELECT " +
                    "SC.iEjercicio,SC.iNumSolicitud,SC.iCveOficSolicita,SC.iCveDeptoSolicita,SC.iCveUsuSolicita," +
                    "SC.dtSolComunicacion,SC.cDscFinalidad,SC.iCveVehiculo,SC.cObsSolicitud,SC.lPreregistroConcluido," +
                    "SC.tsFechaPreregistro,SC.iCveUsuAutOficSolic,SC.lAutorizaOficSolicitante,SC.tsFechaAutorSolicitante,SC.lAutorizaOficCentral," +
                    "SC.tsFechaAutorCentral,SC.iNumOrdenOperacion,SC.iCveOficAutoriza,SC.iCveDeptoAutoriza,SC.iCveUsuAutoriza," +
                    "SC.tsIniComunicacion,SC.lConcluido,SC.tsFinComunicacion,SC.lCancelado,SC.tsFechaCancelacion," +
                    "SC.iEjercicioObservacion,SC.iCveObservacion,SC.iEjercicioSolicNavega,SC.iNumSolicNavega, " +
                    "V.lPropioSCT,V.iCveTipoVeh,V.iCveMarcaVeh,V.iCveSubmarca,V.iModelo,V.cMatricula, " +
                    "V.cNumEconomico,V.cNumSerie,TV.cDscTipoVeh,TV.lMotorUnico,TV.lRequiereOrdOpera, " +
                    "M.cDscMarcaVeh,SM.cDscSubMarca,E.cNomEmbarcacion, " +
                    "Obs.cObsRegistrada AS cObsRegistrada " +
                    " FROM CBBSolComunicacion SC" +
                    " JOIN VEHVehiculo V ON V.iCveVehiculo = SC.iCveVehiculo" +
                    " JOIN VEHTipoVehiculo TV ON TV.iCveTipoVeh = V.iCveTipoVeh" +
                    " JOIN VEHMarcaVeh M ON M.iCveTipoVeh = V.iCveTipoVeh AND M.iCveMarcaVeh = V.iCveMarcaVeh" +
                    " JOIN VEHSubmarcaVeh SM ON SM.iCveTipoVeh = V.iCveTipoVeh AND SM.iCveMarcaVeh = V.iCveMarcaVeh AND SM.iCveSubmarca = V.iCveSubmarca" +
                    " LEFT JOIN VEHEmbarcacion E ON E.iCveVehiculo = V.iCveVehiculo" +
                    " LEFT JOIN GRLObservacion Obs ON Obs.iEjercicio = SC.iEjercicioObservacion AND Obs.iCveObservacion = SC.iCveObservacion" +
                    " WHERE SC.iEjercicio = " + iEjercicio + " AND SC.iNumSolicitud = " + iSolCom;
      try{
        vDatosSolCom = super.FindByGeneric("iEjercicio,iNumSolicitud",cSql,dataSourceName);
      } catch(Exception e){
        cError += "\\n - No se localizaron los datos de la solicitud.";
      }
      if(vDatosSolCom == null || vDatosSolCom.size() == 0)
        cError += "\\n - No se localizaron los datos de la solicitud.";
      else{
        dDatosSolCom = (TVDinRep) vDatosSolCom.get(0);
        if(cCveOficinaOrigen != "" && cCveDeptoOrigen != ""){
          iCveOficinaOrigen = Integer.parseInt(cCveOficinaOrigen,10);
          iCveDeptoOrigen = Integer.parseInt(cCveDeptoOrigen,10);
        } else{
          iCveOficinaOrigen = dDatosSolCom.getInt("iCveOficSolicita");
          iCveDeptoOrigen = dDatosSolCom.getInt("iCveDeptoSolicita");
        }
      }
      // Busca señales de solicitud
      Vector vSenalSolCom = new Vector();
      if(cError.equals("")){
        cSql = "SELECT SC.iEjercicio,SC.iNumSolicitud,SC.iCveSenal,SC.iOrden,SC.dtIniServicio," +
               "SC.dtFinServicio,SC.iKilometrajeAnterior,SC.iKilometrajeFinal,SC.lConcluido," +
               "SC.iCveObservacion,S.iCvePais,S.iCveEntidadFed,S.iNumNacional,S.iConsecNumNacional," +
               "S.cNomSenal,S.iCveTipoSenal,TS.cDscTipoSenal,EF.cNombre AS cDscEntidadFed," +
               "M.cNombre AS cDscMunicipio " +
               " FROM CBBSenalXCom SC " +
               " JOIN RGSSenal S ON S.iCveSenal = SC.iCveSenal " +
               " JOIN RGSTipoSenal TS ON TS.iCveTipoSenal = S.iCveTipoSenal " +
               " JOIN GRLEntidadFed EF ON EF.iCvePais = S.iCvePais AND EF.iCveEntidadFed = S.iCveEntidadFed " +
               " JOIN GRLMunicipio M ON M.iCvePais = S.iCvePais AND M.iCveEntidadFed = S.iCveEntidadFed AND M.iCveMunicipio = S.iCveMunicipio " +
               " WHERE SC.iEjercicio = " + iEjercicio +
               " AND SC.iNumSolicitud = " + iSolCom +
               " ORDER BY SC.iEjercicio, SC.iNumSolicitud, SC.iOrden";
        try{
          vSenalSolCom = super.FindByGeneric("iEjercicio,iNumSolicitud,iCveSenal",cSql,dataSourceName);
        } catch(Exception e){
          cError += "\\n - No se encontraron señales de la solicitud.";
        }
      }
      if(vSenalSolCom == null || vSenalSolCom.size() == 0)
        cError += "\\n - No se encontraron señales de la solicitud.";

      if(!cError.equals(""))
        throw new Exception(cError);

      // Encabezado de datos de oficina solicitante
      Vector vRutaRemitente = new Vector();
      vRutaRemitente = dDatos.getOrganigrama(iCveOficinaOrigen,iCveDeptoOrigen,vRutaRemitente);
      dDatos.dOficina.setOficina(iCveOficinaOrigen);
      vRutaRemitente.add(0,dDatos.dOficina.getDscOficina());
      String[] aOficDeptoDepCap = VParametros.getPropEspecifica("SMCapitaniaDependeOficDeptoCentral").split(",");
      vRutaRemitente = dDatos.getOrganigrama(Integer.parseInt(aOficDeptoDepCap[0],10),
                                             Integer.parseInt(aOficDeptoDepCap[1],10),vRutaRemitente);
      for(int i = 0;i < vRutaRemitente.size();i++){
        iReng++;
        String cRuta = vRutaRemitente.get(i).toString().equals("(NO ESPECIFICADO)")?"":vRutaRemitente.get(i).toString();
        rep.comDespliegaAlineado("D",iReng,"F",iReng,cRuta,true,rep.getAT_COMBINA_IZQUIERDA(),"");
      }
      iReng ++;
      // Datos del folio
      cTemp = "OFICIO No.   ";
      if(cNumFolio.equals("") || cCveOficinaOrigen.equals("") || cCveDeptoOrigen.equals(""))
        cTemp += "P R E L I M I N A R";
      else
        cTemp += cNumFolio;
      rep.comDespliegaAlineado("D",iReng,"F",iReng,cTemp,true,rep.getAT_COMBINA_IZQUIERDA(),"");
      iReng++;
      if(cNumFolio.equals(""))
        rep.comFillCells("D",iReng,"F",iReng,15);
        // Datos del código de formato
        if(!cCodigo.equals("")){
          iReng++;
          rep.comDespliegaAlineado("D",iReng,"F",iReng,cCodigo,true,rep.getAT_COMBINA_IZQUIERDA(),"");
//          iReng++;
        }
      iReng += 2;
      // Arma fecha correspondiente
      dDatos.dOficDepto.setOficinaDepto(iCveOficinaOrigen,iCveDeptoOrigen);
      String cMunicipioEmision = dDatos.dOficDepto.vDatoOfic.getNomMunicipio();
      String[] aEntidadRemplaza = cEntidadRemplazaTexto.split("/");
      String[] aDatos;
      for(int i = 0;i < aEntidadRemplaza.length;i++){
        aDatos = aEntidadRemplaza[i].split(",");
        if(Integer.parseInt(aDatos[0],10) == dDatos.dOficDepto.vDatoOfic.getCvePais() &&
           Integer.parseInt(aDatos[1],10) == dDatos.dOficDepto.vDatoOfic.getCveEntidadFed()){
          cMunicipioEmision = aDatos[2];
          break;
        }
      }
      cTemp = cMunicipioEmision + ", " + dDatos.dOficDepto.vDatoOfic.getAbrevEntidad() + ". A " +
              tFecha.getFechaDDMMMMMYYYY(dDatosSolCom.getDate("dtSolComunicacion")," de ").toUpperCase();
      rep.comDespliegaAlineado("D",iReng,"F",iReng,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),"");
      // Leyenda alusiva
      if(!cLeyendaEnc.equals("")){
        iReng++;
        rep.comDespliegaAlineado("D",iReng,"F",iReng + 1,"'" + cLeyendaEnc,true,rep.getAT_COMBINA_IZQUIERDA(),"");
        rep.comFontSize("D",iReng,"F",iReng + 1, (float) 7.0);
        rep.comAlineaRango("D",iReng,"F",iReng + 1,rep.getAT_HJUSTIFICA());
        iReng++;
      }
      // Asunto
      iReng++;
      cTemp = "ORDEN DE OPERACIÓN ";
      if(cNumFolio.equals("") || cCveOficinaOrigen.equals("") || cCveDeptoOrigen.equals(""))
        cTemp = "COMUNICACIÓN ";
      rep.comDespliegaAlineado("D",iReng,"F",iReng,"ASUNTO:   SOLICITUD DE " + cTemp + iSolCom + "/" + iEjercicio,true,rep.getAT_COMBINA_IZQUIERDA(),"");
      // Datos de Destinatario
      String[] aOficDeptoDirige = VParametros.getPropEspecifica("SMSolComDirigeOficDepto").split(",");
      dDatos.dOficDepto.setOficinaDepto(Integer.parseInt(aOficDeptoDirige[0],10),Integer.parseInt(aOficDeptoDirige[1],10));
      iReng += 2;
      rep.comDespliegaAlineado("A",iReng,dDatos.dOficDepto.getTitular(),true,rep.getAT_HIZQUIERDA(),"");
      iReng++;
      rep.comDespliegaAlineado("A",iReng,dDatos.dOficDepto.getPuestoTitular(),true,rep.getAT_HIZQUIERDA(),"");
      Vector vRutaDirige = new Vector();
      /*vRutaDirige = dDatos.getOrganigrama(Integer.parseInt(aOficDeptoDirige[0],10),Integer.parseInt(aOficDeptoDirige[1],10),vRutaDirige);
      for(int i = vRutaDirige.size() - 2;i > 0;i--){
        iReng++;
        rep.comDespliegaAlineado("A",iReng,vRutaDirige.get(i).toString(),true,rep.getAT_HIZQUIERDA(),"");
      }*/
      dDatos.dOficina.setOficina(Integer.parseInt(aOficDeptoDirige[0],10));
      iReng++;
      rep.comDespliegaAlineado("A",iReng,dDatos.dOficina.getCalleYNo(),true,rep.getAT_HIZQUIERDA(),"");
      iReng++;
      cTemp = "COL. " + dDatos.dOficina.getColonia() + "   C.P. " + dDatos.dOficina.getCodPostal();
      rep.comDespliegaAlineado("A",iReng,cTemp,true,rep.getAT_HIZQUIERDA(),"");
      iReng++;
      cTemp = dDatos.dOficina.getNomMunicipio();
      for(int i = 0;i < aEntidadRemplaza.length;i++){
        aDatos = aEntidadRemplaza[i].split(",");
        if(Integer.parseInt(aDatos[0],10) == dDatos.dOficina.getCvePais() &&
           Integer.parseInt(aDatos[1],10) == dDatos.dOficina.getCveEntidadFed()){
          cTemp = aDatos[2];
          break;
        }
      }
      cTemp += ", " + dDatos.dOficina.getAbrevEntidad() + ".";// + dDatos.dOficina.getNomPais();
      rep.comDespliegaAlineado("A",iReng,cTemp,true,rep.getAT_HIZQUIERDA(),"");
      //iReng++;
      //rep.comDespliegaAlineado("A",iReng,"P R E S E N T E.",true,rep.getAT_HIZQUIERDA(),"");
      // Primer párrafo del cuerpo del oficio
      iReng += 2;
      cTemp = "CON LA FINALIDAD DE DAR CUMPLIMIENTO A LOS PROGRAMAS DE MANTENIMIENTO Y " +
              "CONSERVACIÓN, ASÍ COMO MANTENIMIENTO PREVENTIVO Y/O CORRECTIVO AL SEÑALAMIENTO " +
              "MARÍTIMO, ME PERMITO SOLICITAR A USTED SU AUTORIZACIÓN PARA REALIZAR EL VIAJE " +
              "QUE A CONTINUACIÓN SE DESCRIBE.";
      iRenglones = (int) (cTemp.length() / iLetrasRengParrafo);
      if(cTemp.length() % iLetrasRengParrafo > 0)
        iRenglones++;
      rep.comDespliegaAlineado("A",iReng,"F",iReng + iRenglones - 1,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      rep.comAlineaRango("A",iReng,"F",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
      // Segundo párrafo, solo en caso de contar con una finalidad en datos de solicitud
      if(!dDatosSolCom.getString("cDscFinalidad").equals("")){
        iReng += iRenglones + 1;
        cTemp = "ADICIONALMENTE SE EMPLEARÁ PARA LAS SIGUIENTES TAREAS:  " +
                dDatosSolCom.getString("cDscFinalidad");
        iRenglones = (int) (cTemp.length() / iLetrasRengParrafo);
        if(cTemp.length() % iLetrasRengParrafo > 0)
          iRenglones++;
        rep.comDespliegaAlineado("A",iReng,"F",iReng + iRenglones - 1,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
        rep.comAlineaRango("A",iReng,"F",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
      }
      // Tercer párrafo
      iReng += iRenglones + 1;
      cTemp = "PARA TAL COMUNICACIÓN SE EMPLEARÁ EL VEHÍCULO " + dDatosSolCom.getString("cDscTipoVeh");
      if(dDatosSolCom.getInt("lMotorUnico") == 0)
        cTemp += " DE NOMBRE " + dDatosSolCom.getString("cNomEmbarcacion");
      cTemp += " CON LOS SIGUIENTES DATOS: " +
          " MARCA: " + dDatosSolCom.getString("cDscMarcaVeh") + "." +
          " MATRICULA " + dDatosSolCom.getString("cMatricula") + "." +
          " No. ECONÓMICO ACTUAL: " + dDatosSolCom.getString("cNumEconomico") + "." +
          " No. SERIE: " + dDatosSolCom.getString("cNumSerie") + ".";
      iRenglones = (int) (cTemp.length() / iLetrasRengParrafo);
      if(cTemp.length() % iLetrasRengParrafo > 0)
        iRenglones++;
      rep.comDespliegaAlineado("A",iReng,"F",iReng + iRenglones - 1,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      rep.comAlineaRango("A",iReng,"F",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
      // Cuarto párrafo
      iReng += iRenglones + 1;
      cTemp = "LA RUTA QUE SE SEGUIRÁ PARA EL FIN INDICADO ES LA SIGUIENTE:";
      iRenglones = (int) (cTemp.length() / iLetrasRengParrafo);
      if(cTemp.length() % iLetrasRengParrafo > 0)
        iRenglones++;
      rep.comDespliegaAlineado("A",iReng,"F",iReng + iRenglones - 1,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      rep.comAlineaRango("A",iReng,"F",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
      iReng++;
      iRengRuta = iReng;
      rep.comDespliegaAlineado("A",iReng,"Orden",true,rep.getAT_HCENTRO(),"");
      rep.comDespliegaAlineado("B",iReng,"Número",true,rep.getAT_HCENTRO(),"");
      rep.comDespliegaAlineado("C",iReng,"Nombre",true,rep.getAT_HCENTRO(),"");
      rep.comDespliegaAlineado("D",iReng,"Tipo",true,rep.getAT_HCENTRO(),"");
      rep.comDespliegaAlineado("E",iReng,"Entidad",true,rep.getAT_HCENTRO(),"");
      rep.comDespliegaAlineado("F",iReng,"Municipio",true,rep.getAT_HCENTRO(),"");
      rep.comBordeTotal("A",iReng,"F",iReng,1);
      // Señales de la ruta
      TVDinRep dSenal = new TVDinRep();
      for(int i = 0;i < vSenalSolCom.size();i++){
        iReng++;
        dSenal = (TVDinRep) vSenalSolCom.get(i);
        rep.comDespliegaAlineado("A",iReng,"" + (i + 1),false,rep.getAT_HCENTRO(),"");
        cTemp = tNum.getNumeroSinSeparador(new Integer(dSenal.getInt("iCveEntidadFed")),2);
        cTemp += "-" + tNum.getNumeroSinSeparador(new Integer(dSenal.getInt("iNumNacional")),3);
        if(dSenal.getInt("iConsecNumNacional") > 0)
          cTemp += "." + dSenal.getString("iConsecNumNacional");
        rep.comDespliegaAlineado("B",iReng,cTemp,false,rep.getAT_HCENTRO(),"");
        rep.comDespliegaAlineado("C",iReng,dSenal.getString("cNomSenal"),false,rep.getAT_HIZQUIERDA(),"");
        rep.comDespliegaAlineado("D",iReng,dSenal.getString("cDscTipoSenal"),false,rep.getAT_HIZQUIERDA(),"");
        rep.comDespliegaAlineado("E",iReng,dSenal.getString("cDscEntidadFed"),false,rep.getAT_HIZQUIERDA(),"");
        rep.comDespliegaAlineado("F",iReng,dSenal.getString("cDscMunicipio"),false,rep.getAT_HIZQUIERDA(),"");
        rep.comAlineaRangoVer("A",iReng,"F",iReng,rep.getAT_VAJUSTAR());
        rep.comFontSize("A",iReng,"F",iReng, (float) 8.0);
        rep.comBordeRededor("A",iReng,"A",iReng,1,1);
        rep.comBordeRededor("B",iReng,"B",iReng,1,1);
        rep.comBordeRededor("C",iReng,"C",iReng,1,1);
        rep.comBordeRededor("D",iReng,"D",iReng,1,1);
        rep.comBordeRededor("E",iReng,"E",iReng,1,1);
        rep.comBordeRededor("F",iReng,"F",iReng,1,1);
      }
      rep.comBordeTotal("A",iRengRuta,"F",iRengRuta,1);
      // Párrafo posterior a ruta
      iReng += 2;
      cTemp = "DICHA COMISIÓN SE EFECTUARÁ EL DÍA: " +
              tFecha.getFechaDDMMMMMYYYY(dDatosSolCom.getDate("dtSolComunicacion")," de ").toUpperCase() + ", " +
              "CON LA TRIPULACIÓN Y PERSONAL DE SEÑALAMIENTO MARÍTIMO, SI LAS CONDICIONES " +
              "METEOROLÓGICAS LO PERMITEN, ADEMÁS DE HABER OBTENIDO LOS RECURSOS Y APOYOS " +
              "LOGÍSTICOS NECESARIOS.";
      iRenglones = (int) (cTemp.length() / iLetrasRengParrafo);
      if(cTemp.length() % iLetrasRengParrafo > 0)
        iRenglones++;
      rep.comDespliegaAlineado("A",iReng,"F",iReng + iRenglones - 1,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      rep.comAlineaRango("A",iReng,"F",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
      // Párrafo de observaciones
      cTemp = dDatosSolCom.getString("cObsSolicitud");
      if(dDatosSolCom.getInt("iCveObservacion") > 0)
        cTemp = dDatosSolCom.getString("cObsRegistrada");
      else
        cTemp = dDatosSolCom.getString("cObsSolicitud");
      if(cTemp != null && !cTemp.equals("")){
        iReng += iRenglones + 1;
        cTemp = "OBSERVACIONES: " + cTemp;
        iRenglones = (int) (cTemp.length() / iLetrasRengParrafo);
        if(cTemp.length() % iLetrasRengParrafo > 0)
          iRenglones++;
        rep.comDespliegaAlineado("A",iReng,"F",iReng + iRenglones - 1,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
        rep.comAlineaRango("A",iReng,"F",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
      }
      // Despedida
      iReng += iRenglones + 1;
      cTemp = "SIN MAS POR EL MOMENTO, APROVECHO LA OCASIÓN PARA ENVIARLE UN CORDIAL SALUDO.";
      iRenglones = (int) (cTemp.length() / iLetrasRengParrafo);
      if(cTemp.length() % iLetrasRengParrafo > 0)
        iRenglones++;
      rep.comDespliegaAlineado("A",iReng,"F",iReng + iRenglones - 1,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      rep.comAlineaRango("A",iReng,"F",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
      // Datos de Firmante
      iReng += iRenglones + 1;
      rep.comDespliegaAlineado("A",iReng,"A T E N T A M E N T E.",true,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      iReng++;
      rep.comDespliegaAlineado("A",iReng,cLeyendaPie,true,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      iReng++;
      dDatos.dOficDepto.setOficinaDepto(dDatosSolCom.getInt("iCveOficSolicita"),0);
      rep.comDespliegaAlineado("A",iReng,dDatos.dOficDepto.getPuestoTitular()+".",true,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      iReng += 4;
      rep.comDespliegaAlineado("A",iReng,dDatos.dOficDepto.getTitular(),true,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      // Datos de copia a
      iReng ++;
      //rep.comDespliegaAlineado("A",iReng,"c.c.p.",true,rep.getAT_HIZQUIERDA(),"");
      String cCopiasPara = VParametros.getPropEspecifica("SMSolComCopiaOficDepto");
      String[] aCopiasPara = cCopiasPara.split("/");
      iRenglones = 1;
      for(int i = 0;i < aCopiasPara.length;i++){
        String[] aOficDepto = aCopiasPara[i].split(",");
        dDatos.dOficDepto.setOficinaDepto(Integer.parseInt(aOficDepto[0],10),Integer.parseInt(aOficDepto[1],10));
        iReng += iRenglones;
        cTemp = "c.c.p.  "+dDatos.dOficDepto.getTitular() + ".  " + dDatos.dOficDepto.getPuestoTitular() + ".";
        iRenglones = (int) (cTemp.length() / iLetrasRengParrafoCP);
        if(cTemp.length() % iLetrasRengParrafoCP > 0)
          iRenglones++;
        rep.comDespliegaAlineado("A",iReng,"F",iReng + iRenglones - 1,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
        rep.comAlineaRango("A",iReng,"F",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
        rep.comFontSize("A",iReng,"A",iReng, (float) 8.0);
      }
      dDatos.dOficDepto.setOficinaDepto(dDatosSolCom.getInt("iCveOficSolicita"),dDatosSolCom.getInt("iCveDeptoSolicita"));
      iReng += iRenglones;
      cTemp = dDatos.dOficDepto.getTitular() + ".  " + dDatos.dOficDepto.getPuestoTitular() + ".";
      iRenglones = (int) (cTemp.length() / iLetrasRengParrafoCP);
      if(cTemp.length() % iLetrasRengParrafoCP > 0)
        iRenglones++;
      rep.comDespliegaAlineado("A",iReng,"F",iReng + iRenglones - 1,"c.c.p.  "+cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      rep.comAlineaRango("A",iReng,"F",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
      rep.comFontSize("A",iReng,"A",iReng, (float) 8.0);
      iReng += iRenglones;
      rep.comDespliegaAlineado("A",iReng,"c.c.p.  EXPEDIENTE",false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      rep.comFontSize("A",iReng,"A",iReng, (float) 8.0);
    }
    return rep.getSbDatos();
  }

  public StringBuffer ordenOperacionSF(String cFiltro) throws Exception{
    return this.ordenOperacion(cFiltro,"","","");
  }

  public StringBuffer ordenOperacion(String cFiltro,String cNumFolio,String cCveOficinaOrigen,String cCveDeptoOrigen) throws Exception{
    String cLeyendaEnc = "",
           cLeyendaPie = "",
           cEntidadRemplazaTexto = "",
           cTemp = "";
    int iCveOficinaOrigen = 0,
        iCveDeptoOrigen = 0,
        iReng = 1,
        iRengRuta = 0,
        iLetrasRengParrafo = 90,
        iLetrasRengParrafoCP = 100,
        iRenglones = 0;
    String[] aFiltro = null;
    int iEjercicio = 0;
    int iSolCom = 0;
    String cCodigo = "";

    cError = "";
    TExcel rep = new TExcel();
    rep.iniciaReporte();
    if(!cFiltro.equals("")){
      TDObtenDatos dDatos = new TDObtenDatos();
      cLeyendaEnc = VParametros.getPropEspecifica("LeyendaEnc");
      cLeyendaPie = VParametros.getPropEspecifica("LeyendaPie");
      cEntidadRemplazaTexto = VParametros.getPropEspecifica("EntidadRemplazaTexto");

      try{
        aFiltro = cFiltro.split(",");
        iEjercicio = Integer.parseInt(aFiltro[0]);
        iSolCom = Integer.parseInt(aFiltro[1]);
        cCodigo = aFiltro[4];
      } catch(Exception e){
        cError += "\\n - No se cuenta con ejercicio o número de solicitud";
      }
      // Busca datos de solicitud de comunicacion
      Vector vDatosSolCom = new Vector();
      TVDinRep dDatosSolCom = new TVDinRep();
      String cSql = "SELECT " +
                    "SC.iEjercicio,SC.iNumSolicitud,SC.iCveOficSolicita,SC.iCveDeptoSolicita,SC.iCveUsuSolicita," +
                    "SC.dtSolComunicacion,SC.cDscFinalidad,SC.iCveVehiculo,SC.cObsSolicitud,SC.lPreregistroConcluido," +
                    "SC.tsFechaPreregistro,SC.iCveUsuAutOficSolic,SC.lAutorizaOficSolicitante,SC.tsFechaAutorSolicitante,SC.lAutorizaOficCentral," +
                    "SC.tsFechaAutorCentral,SC.iNumOrdenOperacion,SC.iCveOficAutoriza,SC.iCveDeptoAutoriza,SC.iCveUsuAutoriza," +
                    "SC.tsInicioComunicacion,SC.lConcluido,SC.tsFinComunicacion,SC.lCancelado,SC.tsFechaCancelacion," +
                    "SC.iEjercicioObservacion,SC.iCveObservacion,SC.iEjercicioSolicNavega,SC.iNumSolicNavega, " +
                    "V.lPropioSCT,V.iCveTipoVeh,V.iCveMarcaVeh,V.iCveSubmarca,V.iModelo,V.cMatricula, " +
                    "V.cNumEconomico,V.cNumSerie,TV.cDscTipoVeh,TV.lMotorUnico,TV.lRequiereOrdOpera, " +
                    "M.cDscMarcaVeh,SM.cDscSubMarca,E.cNomEmbarcacion, " +
                    "Obs.cObsRegistrada AS cObsRegistrada " +
                    " FROM CBBSolComunicacion SC" +
                    " JOIN VEHVehiculo V ON V.iCveVehiculo = SC.iCveVehiculo" +
                    " JOIN VEHTipoVehiculo TV ON TV.iCveTipoVeh = V.iCveTipoVeh" +
                    " JOIN VEHMarcaVeh M ON M.iCveTipoVeh = V.iCveTipoVeh AND M.iCveMarcaVeh = V.iCveMarcaVeh" +
                    " JOIN VEHSubmarcaVeh SM ON SM.iCveTipoVeh = V.iCveTipoVeh AND SM.iCveMarcaVeh = V.iCveMarcaVeh AND SM.iCveSubmarca = V.iCveSubmarca" +
                    " LEFT JOIN VEHEmbarcacion E ON E.iCveVehiculo = V.iCveVehiculo" +
                    " LEFT JOIN GRLObservacion Obs ON Obs.iEjercicio = SC.iEjercicioObservacion AND Obs.iCveObservacion = SC.iCveObservacion" +
                    " WHERE SC.iEjercicio = " + iEjercicio + " AND SC.iNumSolicitud = " + iSolCom;
      try{
        vDatosSolCom = super.FindByGeneric("iEjercicio,iNumSolicitud",cSql,dataSourceName);
      } catch(Exception e){
        cError += "\\n - No se localizaron los datos de la solicitud.";
      }
      if(vDatosSolCom == null || vDatosSolCom.size() == 0)
        cError += "\\n - No se localizaron los datos de la solicitud.";
      else{
        dDatosSolCom = (TVDinRep) vDatosSolCom.get(0);
        if(cCveOficinaOrigen != "" && cCveDeptoOrigen != ""){
          iCveOficinaOrigen = Integer.parseInt(cCveOficinaOrigen,10);
          iCveDeptoOrigen = Integer.parseInt(cCveDeptoOrigen,10);
        } else{
          iCveOficinaOrigen = dDatosSolCom.getInt("iCveOficAutoriza");
          iCveDeptoOrigen = dDatosSolCom.getInt("iCveDeptoAutoriza");
          if(iCveOficinaOrigen == 0 || iCveDeptoOrigen == 0){
            iCveOficinaOrigen = Integer.parseInt(aFiltro[2]);
            iCveDeptoOrigen = Integer.parseInt(aFiltro[3]);
          }
        }
      }
      // Busca señales de solicitud
      Vector vSenalSolCom = new Vector();
      if(cError.equals("")){
        cSql = "SELECT SC.iEjercicio,SC.iNumSolicitud,SC.iCveSenal,SC.iOrden,SC.dtIniServicio," +
               "SC.dtFinServicio,SC.iKilometrajeAnterior,SC.iKilometrajeFinal,SC.lConcluido," +
               "SC.iCveObservacion,S.iCvePais,S.iCveEntidadFed,S.iNumNacional,S.iConsecNumNacional," +
               "S.cNomSenal,S.iCveTipoSenal,TS.cDscTipoSenal,EF.cNombre AS cDscEntidadFed," +
               "M.cNombre AS cDscMunicipio " +
               " FROM CBBSenalXCom SC " +
               " JOIN RGSSenal S ON S.iCveSenal = SC.iCveSenal " +
               " JOIN RGSTipoSenal TS ON TS.iCveTipoSenal = S.iCveTipoSenal " +
               " JOIN GRLEntidadFed EF ON EF.iCvePais = S.iCvePais AND EF.iCveEntidadFed = S.iCveEntidadFed " +
               " JOIN GRLMunicipio M ON M.iCvePais = S.iCvePais AND M.iCveEntidadFed = S.iCveEntidadFed AND M.iCveMunicipio = S.iCveMunicipio " +
               " WHERE SC.iEjercicio = " + iEjercicio +
               " AND SC.iNumSolicitud = " + iSolCom +
               " ORDER BY SC.iEjercicio, SC.iNumSolicitud, SC.iOrden";
        try{
          vSenalSolCom = super.FindByGeneric("iEjercicio,iNumSolicitud,iCveSenal",cSql,dataSourceName);
        } catch(Exception e){
          cError += "\\n - No se encontraron señales de la solicitud.";
        }
      }
      if(vSenalSolCom == null || vSenalSolCom.size() == 0)
        cError += "\\n - No se encontraron señales de la solicitud.";

      if(!cError.equals(""))
        throw new Exception(cError);

      // Encabezado de datos de oficina solicitante
      Vector vRutaRemitente = new Vector();
      vRutaRemitente = dDatos.getOrganigrama(iCveOficinaOrigen,iCveDeptoOrigen,vRutaRemitente);
      dDatos.dOficina.setOficina(iCveOficinaOrigen);
      for(int i = 0;i < vRutaRemitente.size();i++){
        iReng++;
        String cRuta = vRutaRemitente.get(i).toString().equals("(NO ESPECIFICADO)")?"":vRutaRemitente.get(i).toString();
        rep.comDespliegaAlineado("D",iReng,"F",iReng,cRuta,true,rep.getAT_COMBINA_IZQUIERDA(),"");
      }
      iReng ++;
      // Datos del folio
      cTemp = "OFICIO No.   ";
      if(cNumFolio.equals("") || cCveOficinaOrigen.equals("") || cCveDeptoOrigen.equals(""))
        cTemp += "P R E L I M I N A R";
      else
        cTemp += cNumFolio;
      rep.comDespliegaAlineado("D",iReng,"F",iReng,cTemp,true,rep.getAT_COMBINA_IZQUIERDA(),"");
      iReng += 2;
      // Datos del código de formato
      if(!cCodigo.equals("")){
        rep.comDespliegaAlineado("D",iReng,"F",iReng,cCodigo,true,rep.getAT_COMBINA_IZQUIERDA(),"");
        iReng++;
      }
      if(cNumFolio.equals(""))
        rep.comFillCells("D",iReng,"F",iReng,15);
        // Datos del número de orden de operación
      iReng++;
      cTemp = "ORDEN DE OPERACIÓN No.   ";
      if(dDatosSolCom.getInt("iNumOrdenOperacion") == 0)
        cTemp += "P R E L I M I N A R";
      else
        cTemp += tNum.getNumeroSinSeparador(new Integer(dDatosSolCom.getInt("iNumOrdenOperacion")),2) +
            "/" + dDatosSolCom.getInt("iEjercicio");
      rep.comDespliegaAlineado("D",iReng,"F",iReng,cTemp,true,rep.getAT_COMBINA_IZQUIERDA(),"");
      if(dDatosSolCom.getInt("iNumOrdenOperacion") == 0)
        rep.comFillCells("D",iReng,"F",iReng,15);
        // Arma fecha correspondiente
      iReng += 2;
      dDatos.dOficDepto.setOficinaDepto(iCveOficinaOrigen,iCveDeptoOrigen);
      String cMunicipioEmision = dDatos.dOficDepto.vDatoOfic.getNomMunicipio();
      String[] aEntidadRemplaza = cEntidadRemplazaTexto.split("/");
      String[] aDatos;
      for(int i = 0;i < aEntidadRemplaza.length;i++){
        aDatos = aEntidadRemplaza[i].split(",");
        if(Integer.parseInt(aDatos[0],10) == dDatos.dOficDepto.vDatoOfic.getCvePais() &&
           Integer.parseInt(aDatos[1],10) == dDatos.dOficDepto.vDatoOfic.getCveEntidadFed()){
          cMunicipioEmision = aDatos[2];
          break;
        }
      }
      cTemp = cMunicipioEmision + ", " + dDatos.dOficDepto.vDatoOfic.getAbrevEntidad() + ". A " +
              tFecha.getFechaDDMMMMMYYYY(dDatosSolCom.getDate("dtSolComunicacion")," de ").toUpperCase();
      rep.comDespliegaAlineado("D",iReng,"F",iReng,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),"");
      // Leyenda alusiva
      if(!cLeyendaEnc.equals("")){
        iReng++;
        rep.comDespliegaAlineado("D",iReng,"F",iReng + 1,"'" + cLeyendaEnc,true,rep.getAT_COMBINA_IZQUIERDA(),"");
        rep.comFontSize("D",iReng,"F",iReng + 1, (float) 7.0);
        rep.comAlineaRango("D",iReng,"F",iReng + 1,rep.getAT_HJUSTIFICA());
        iReng++;
      }
      // Asunto
      iReng++;
      rep.comDespliegaAlineado("D",iReng,"F",iReng + 1,"ASUNTO:  REFERENTE A LA SOLICITUD DE ORDEN DE OPERACIÓN " + iSolCom + "/" + iEjercicio,true,rep.getAT_COMBINA_IZQUIERDA(),"");
      rep.comAlineaRango("D",iReng,"F",iReng + 1,rep.getAT_HJUSTIFICA());
      // Datos de Destinatario
      dDatos.dOficDepto.setOficinaDepto(dDatosSolCom.getInt("iCveOficSolicita"),0);
      iReng += 3;
      rep.comDespliegaAlineado("A",iReng,dDatos.dOficDepto.getTitular(),true,rep.getAT_HIZQUIERDA(),"");
      iReng++;
      rep.comDespliegaAlineado("A",iReng,dDatos.dOficDepto.getPuestoTitular(),true,rep.getAT_HIZQUIERDA(),"");
      Vector vRutaDirige = new Vector();
      String[] aOficDeptoDepCap = VParametros.getPropEspecifica("SMCapitaniaDependeOficDeptoCentral").split(",");
      vRutaDirige = dDatos.getOrganigrama(Integer.parseInt(aOficDeptoDepCap[0],10),Integer.parseInt(aOficDeptoDepCap[1],10),vRutaDirige);
      /*for(int i = vRutaDirige.size() - 1;i >= 0;i--){
        iReng++;
        rep.comDespliegaAlineado("A",iReng,vRutaDirige.get(i).toString(),true,rep.getAT_HIZQUIERDA(),"");
      }*/
      dDatos.dOficina.setOficina(dDatosSolCom.getInt("iCveOficSolicita"));
      iReng++;
      rep.comDespliegaAlineado("A",iReng,dDatos.dOficina.getCalleYNo(),true,rep.getAT_HIZQUIERDA(),"");
      iReng++;
      cTemp = "COL. " + dDatos.dOficina.getColonia() + "   C.P. " + dDatos.dOficina.getCodPostal();
      rep.comDespliegaAlineado("A",iReng,cTemp,true,rep.getAT_HIZQUIERDA(),"");
      iReng++;
      cTemp = dDatos.dOficina.getNomMunicipio();
      for(int i = 0;i < aEntidadRemplaza.length;i++){
        aDatos = aEntidadRemplaza[i].split(",");
        if(Integer.parseInt(aDatos[0],10) == dDatos.dOficina.getCvePais() &&
           Integer.parseInt(aDatos[1],10) == dDatos.dOficina.getCveEntidadFed()){
          cTemp = aDatos[2];
          break;
        }
      }
      cTemp += ", " + dDatos.dOficina.getAbrevEntidad() + ".";
      rep.comDespliegaAlineado("A",iReng,cTemp,true,rep.getAT_HIZQUIERDA(),"");
      //iReng++;
      //rep.comDespliegaAlineado("A",iReng,"P R E S E N T E.",true,rep.getAT_HIZQUIERDA(),"");
      // Primer párrafo del cuerpo del oficio
      iReng += 2;
      cTemp = "AGRADECERÉ GIRAR SUS APRECIABLES INSTRUCCIONES PARA QUE EL VEHÍCULO MENCIONADO " +
              "A CONTINUACIÓN, LLEVE A CABO LA ORDEN DE OPERACIÓN " +
              tNum.getNumeroSinSeparador(new Integer(dDatosSolCom.getInt("iNumOrdenOperacion")),2) +
              "/" + dDatosSolCom.getInt("iEjercicio") +
              " CON LA RUTA INDICADA.";
      iRenglones = (int) (cTemp.length() / iLetrasRengParrafo);
      if(cTemp.length() % iLetrasRengParrafo > 0)
        iRenglones++;
      rep.comDespliegaAlineado("A",iReng,"F",iReng + iRenglones - 1,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      rep.comAlineaRango("A",iReng,"F",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
      // Tercer párrafo
      iReng += iRenglones + 1;
      cTemp = "PARA DICHA ORDEN DE OPERACIÓN SE EMPLEARÁ EL VEHÍCULO " + dDatosSolCom.getString("cDscTipoVeh");
      if(dDatosSolCom.getInt("lMotorUnico") == 0)
        cTemp += " DE NOMBRE " + dDatosSolCom.getString("cNomEmbarcacion");
      cTemp += " CON LOS SIGUIENTES DATOS: " +
          " MARCA: " + dDatosSolCom.getString("cDscMarcaVeh") + "." +
          " MATRICULA " + dDatosSolCom.getString("cMatricula") + "." +
          " No. ECONÓMICO ACTUAL: " + dDatosSolCom.getString("cNumEconomico") + "." +
          " No. SERIE: " + dDatosSolCom.getString("cNumSerie") + ".";
      iRenglones = (int) (cTemp.length() / iLetrasRengParrafo);
      if(cTemp.length() % iLetrasRengParrafo > 0)
        iRenglones++;
      rep.comDespliegaAlineado("A",iReng,"F",iReng + iRenglones - 1,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      rep.comAlineaRango("A",iReng,"F",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
      // Cuarto párrafo
      iReng += iRenglones + 1;
      cTemp = "LA RUTA QUE SE SEGUIRÁ PARA LA ORDEN DE OPERACIÓN ES LA SIGUIENTE:";
      iRenglones = (int) (cTemp.length() / iLetrasRengParrafo);
      if(cTemp.length() % iLetrasRengParrafo > 0)
        iRenglones++;
      rep.comDespliegaAlineado("A",iReng,"F",iReng + iRenglones - 1,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      rep.comAlineaRango("A",iReng,"F",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
      iReng++;
      iRengRuta = iReng;
      rep.comDespliegaAlineado("A",iReng,"Orden",true,rep.getAT_HCENTRO(),"");
      rep.comDespliegaAlineado("B",iReng,"Número",true,rep.getAT_HCENTRO(),"");
      rep.comDespliegaAlineado("C",iReng,"Nombre",true,rep.getAT_HCENTRO(),"");
      rep.comDespliegaAlineado("D",iReng,"Tipo",true,rep.getAT_HCENTRO(),"");
      rep.comDespliegaAlineado("E",iReng,"Entidad",true,rep.getAT_HCENTRO(),"");
      rep.comDespliegaAlineado("F",iReng,"Municipio",true,rep.getAT_HCENTRO(),"");
      rep.comBordeTotal("A",iReng,"F",iReng,1);
      // Señales de la ruta
      TVDinRep dSenal = new TVDinRep();
      for(int i = 0;i < vSenalSolCom.size();i++){
        iReng++;
        dSenal = (TVDinRep) vSenalSolCom.get(i);
        rep.comDespliegaAlineado("A",iReng,"" + (i + 1),false,rep.getAT_HCENTRO(),"");
        cTemp = tNum.getNumeroSinSeparador(new Integer(dSenal.getInt("iCveEntidadFed")),2);
        cTemp += "-" + tNum.getNumeroSinSeparador(new Integer(dSenal.getInt("iNumNacional")),3);
        if(dSenal.getInt("iConsecNumNacional") > 0)
          cTemp += "." + dSenal.getString("iConsecNumNacional");
        rep.comDespliegaAlineado("B",iReng,cTemp,false,rep.getAT_HCENTRO(),"");
        rep.comDespliegaAlineado("C",iReng,dSenal.getString("cNomSenal"),false,rep.getAT_HIZQUIERDA(),"");
        rep.comDespliegaAlineado("D",iReng,dSenal.getString("cDscTipoSenal"),false,rep.getAT_HIZQUIERDA(),"");
        rep.comDespliegaAlineado("E",iReng,dSenal.getString("cDscEntidadFed"),false,rep.getAT_HIZQUIERDA(),"");
        rep.comDespliegaAlineado("F",iReng,dSenal.getString("cDscMunicipio"),false,rep.getAT_HIZQUIERDA(),"");
        rep.comAlineaRangoVer("A",iReng,"F",iReng,rep.getAT_VAJUSTAR());
        rep.comFontSize("A",iReng,"F",iReng, (float) 8.0);
        rep.comBordeRededor("A",iReng,"A",iReng,1,1);
        rep.comBordeRededor("B",iReng,"B",iReng,1,1);
        rep.comBordeRededor("C",iReng,"C",iReng,1,1);
        rep.comBordeRededor("D",iReng,"D",iReng,1,1);
        rep.comBordeRededor("E",iReng,"E",iReng,1,1);
        rep.comBordeRededor("F",iReng,"F",iReng,1,1);
      }
      rep.comBordeTotal("A",iRengRuta,"F",iRengRuta,1);
      // Primer párrafo del cuerpo del oficio
      iReng += 2;
      cTemp = "CON LA FINALIDAD DE DAR CUMPLIMIENTO A LOS PROGRAMAS DE MANTENIMIENTO Y " +
              "CONSERVACIÓN, ASÍ COMO MANTENIMIENTO PREVENTIVO Y/O CORRECTIVO AL SEÑALAMIENTO " +
              "MARÍTIMO, ADEMAS DE LAS SIGUIENTES TAREAS:  " +
              dDatosSolCom.getString("cDscFinalidad");
      iRenglones = (int) (cTemp.length() / iLetrasRengParrafo);
      if(cTemp.length() % iLetrasRengParrafo > 0)
        iRenglones++;
      rep.comDespliegaAlineado("A",iReng,"F",iReng + iRenglones - 1,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      rep.comAlineaRango("A",iReng,"F",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
      // Parrafo de fecha de salida
      iReng += iRenglones + 1;
      cTemp = "LA COMUNICACIÓN A LOS DESTINOS CITADOS DEBERÁ INICIAR EL DÍA: " +
              tFecha.getFechaDDMMMMMYYYY(dDatosSolCom.getDate("dtSolComunicacion")," de ").toUpperCase() + ", " +
              "CON LA TRIPULACIÓN Y PERSONAL DE SEÑALAMIENTO MARÍTIMO, UNA VEZ QUE SE HAYAN " +
              "OBTENIDO LOS RECURSOS Y APOYOS LOGÍSTICOS NECESARIOS, SIEMPRE Y CUANDO LAS " +
              "CONDICIONES DEL VEHÍCULO Y METEOROLÓGICAS LO PERMITAN.";
      iRenglones = (int) (cTemp.length() / iLetrasRengParrafo);
      if(cTemp.length() % iLetrasRengParrafo > 0)
        iRenglones++;
      rep.comDespliegaAlineado("A",iReng,"F",iReng + iRenglones - 1,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      rep.comAlineaRango("A",iReng,"F",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
      // Parrafo de informar a capitan y oficina central
      iReng += iRenglones + 1;
      cTemp = "EL CAPITÁN DE LA EMBARCACIÓN DEBERÁ INFORMAR A LA CAPITANÍA DE PUERTO, " +
              "LA HORA DE SALIDA Y ESTABLECERÁ CONTACTO POR RADIOTELEFONÍA CON LA MISMA, " +
              "ADEMÁS CON LA OFICINA DE RADIOCOMUNICACIÓN DE ESTA DIRECCIÓN GENERAL. " +
              "AL TÉRMINO DE ESTA OPERACIÓN RENDIRÁ EL INFORME CORRESPONDIENTE A LA CAPITANÍA, " +
              "MARCANDO COPIA A ÉSTA SUBDIRECCIÓN.";
      iRenglones = (int) (cTemp.length() / iLetrasRengParrafo);
      if(cTemp.length() % iLetrasRengParrafo > 0)
        iRenglones++;
      rep.comDespliegaAlineado("A",iReng,"F",iReng + iRenglones - 1,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      rep.comAlineaRango("A",iReng,"F",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
      // Párrafo de observaciones
      cTemp = dDatosSolCom.getString("cObsSolicitud");
      if(dDatosSolCom.getInt("iCveObservacion") > 0)
        cTemp = dDatosSolCom.getString("cObsRegistrada");
      else
        cTemp = dDatosSolCom.getString("cObsSolicitud");
      if(cTemp != null && !cTemp.equals("")){
        iReng += iRenglones + 1;
        cTemp = "OBSERVACIONES: " + cTemp;
        iRenglones = (int) (cTemp.length() / iLetrasRengParrafo);
        if(cTemp.length() % iLetrasRengParrafo > 0)
          iRenglones++;
        rep.comDespliegaAlineado("A",iReng,"F",iReng + iRenglones - 1,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
        rep.comAlineaRango("A",iReng,"F",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
      }
      // Despedida
      iReng += iRenglones + 1;
      cTemp = "SIN MAS POR EL MOMENTO, APROVECHO LA OCASIÓN PARA ENVIARLE UN CORDIAL SALUDO.";
      iRenglones = (int) (cTemp.length() / iLetrasRengParrafo);
      if(cTemp.length() % iLetrasRengParrafo > 0)
        iRenglones++;
      rep.comDespliegaAlineado("A",iReng,"F",iReng + iRenglones - 1,cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      rep.comAlineaRango("A",iReng,"F",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
      // Datos de Firmante
      iReng += iRenglones + 1;
      rep.comDespliegaAlineado("A",iReng,"A T E N T A M E N T E.",true,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      iReng++;
      rep.comDespliegaAlineado("A",iReng,cLeyendaPie,true,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      iReng++;
      String[] aOficDeptoDirige = VParametros.getPropEspecifica("SMSolComDirigeOficDepto").split(",");
      dDatos.dOficDepto.setOficinaDepto(Integer.parseInt(aOficDeptoDirige[0],10),Integer.parseInt(aOficDeptoDirige[1],10));
      rep.comDespliegaAlineado("A",iReng,dDatos.dOficDepto.getPuestoTitular()+".",true,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      iReng += 4;
      rep.comDespliegaAlineado("A",iReng,dDatos.dOficDepto.getTitular(),true,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      // Datos de copia a
      iReng ++;
      //rep.comDespliegaAlineado("A",iReng,"c.c.p.",true,rep.getAT_HIZQUIERDA(),"");
      String cCopiasPara = VParametros.getPropEspecifica("SMOrdOperaCopiaOficDepto");
      String[] aCopiasPara = cCopiasPara.split("/");
      iRenglones = 1;
      for(int i = 0;i < aCopiasPara.length;i++){
        String[] aOficDepto = aCopiasPara[i].split(",");
        dDatos.dOficDepto.setOficinaDepto(Integer.parseInt(aOficDepto[0],10),Integer.parseInt(aOficDepto[1],10));
        iReng += iRenglones;
        cTemp = dDatos.dOficDepto.getTitular() + ".  " + dDatos.dOficDepto.getPuestoTitular() + ".";
        iRenglones = (int) (cTemp.length() / iLetrasRengParrafoCP);
        if(cTemp.length() % iLetrasRengParrafoCP > 0)
          iRenglones++;
        rep.comDespliegaAlineado("A",iReng,"F",iReng + iRenglones - 1,"c.c.p.  "+cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
        rep.comAlineaRango("A",iReng,"F",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
        rep.comFontSize("A",iReng,"A",iReng, (float) 8.0);
      }
      dDatos.dOficDepto.setOficinaDepto(dDatosSolCom.getInt("iCveOficSolicita"),dDatosSolCom.getInt("iCveDeptoSolicita"));
      iReng += iRenglones;
      cTemp = dDatos.dOficDepto.getTitular() + ".  " + dDatos.dOficDepto.getPuestoTitular() + ".";
      iRenglones = (int) (cTemp.length() / iLetrasRengParrafoCP);
      if(cTemp.length() % iLetrasRengParrafoCP > 0)
        iRenglones++;
      rep.comDespliegaAlineado("A",iReng,"F",iReng + iRenglones - 1,"c.c.p.  "+cTemp,false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      rep.comAlineaRango("A",iReng,"F",iReng + iRenglones - 1,rep.getAT_HJUSTIFICA());
      rep.comFontSize("A",iReng,"A",iReng, (float) 8.0);
      iReng += iRenglones;
      rep.comDespliegaAlineado("A",iReng,"c.c.p.  EXPEDIENTE",false,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR());
      rep.comFontSize("A",iReng,"A",iReng, (float) 8.0);
    }
    return rep.getSbDatos();
  }

  public StringBuffer totalizadoOrdenOperacion(String cFiltro) throws Exception{
    cError = "";
    TExcel rep = new TExcel();
    rep.iniciaReporte();
    int iEjercicioFiltro = 0;
    int iEjerIni = 0,iEjerFin = 0;
    if(!cFiltro.trim().equals(""))
      iEjercicioFiltro = Integer.parseInt(cFiltro,10);
    if(iEjercicioFiltro > 0)
      iEjerIni = iEjerFin = iEjercicioFiltro;
    else{
      String cSql = "SELECT DISTINCT(iEjercicio) FROM CBBSolComunicacion " +
                    "WHERE iNumOrdenOperacion IS NOT NULL " +
                    "ORDER BY iEjercicio ";
      Vector vEjercicios = new Vector();
      try{
        vEjercicios = super.FindByGeneric("",cSql,dataSourceName);
      } catch(Exception e){}
      if(vEjercicios != null && vEjercicios.size() > 0){
        iEjerIni = ( (TVDinRep) vEjercicios.get(0)).getInt("iEjercicio");
        iEjerFin = ( (TVDinRep) vEjercicios.get(vEjercicios.size() - 1)).getInt("iEjercicio");
      }
    }
    Vector vDatos = new Vector();
    String cSqlIni = "SELECT V.iCveVehiculo, E.cNomEmbarcacion, COUNT(iNumSolicitud) AS iCountSolicitudes " +
                     "FROM VEHVehiculo V " +
                     "LEFT JOIN VEHEmbarcacion E ON E.iCveVehiculo = V.iCveVehiculo " +
                     "LEFT JOIN VEHTipoVehiculo TV ON TV.iCveTipoVeh = V.iCveTipoVeh " +
                     "LEFT JOIN CBBSolComunicacion SC ON SC.iCveVehiculo = V.iCveVehiculo " +
                     "      AND SC.iNumOrdenOperacion IS NOT NULL " +
                     "      AND SC.iEjercicio = ";
    String cSqlFin = " WHERE V.lPropioSCT = 1 " +
                     "  AND V.iCveVehiculo > 0 " +
                     "  AND TV.lRequiereOrdOpera = 1 " +
                     "GROUP BY V.iCveVehiculo, E.cNomEmbarcacion " +
                     "ORDER BY E.cNomEmbarcacion ";
    char cCol = 'A';
    char cColIni = cCol;
    String sCol = String.valueOf(cCol);
    int iRengIni = 10;
    int iReng = iRengIni;
    //Ciclo para despliegue de datos, solo en primera iteración despliega datos buque
    TVDinRep dDatos;
    for(int i = iEjerIni;i <= iEjerFin;i++){
      try{
        vDatos = super.FindByGeneric("",cSqlIni + i + cSqlFin,dataSourceName);
      } catch(Exception e){}
      if(vDatos != null && vDatos.size() > 0){
        iReng = iRengIni;
        sCol = String.valueOf(cCol);
        if(i == iEjerIni){
          for(int j = 0;j < vDatos.size();j++){
            dDatos = (TVDinRep) vDatos.get(j);
            if(j == 0)
              rep.comDespliegaAlineado(sCol,iReng - 1,"BUQUE BALIZADOR",true,rep.getAT_HCENTRO(),rep.getAT_VINFERIOR());
            rep.comDespliegaAlineado(sCol,iReng,dDatos.getString("cNomEmbarcacion"),false,rep.getAT_HIZQUIERDA(),rep.getAT_VAJUSTAR());
            iReng++;
          }
          rep.comDespliegaAlineado(sCol,iReng,"TOTAL ORDENES OPERACION:",true,rep.getAT_HDERECHA(),rep.getAT_VINFERIOR());
          rep.comFillCells(sCol,iRengIni - 1,sCol,iRengIni - 1,15);
          rep.comFillCells(sCol,iReng,sCol,iReng,15);
          rep.comBordeTotal(sCol,iRengIni - 1,sCol,iReng,1);
        }
        iReng = iRengIni;
        cCol++;
        sCol = String.valueOf(cCol);
        for(int j = 0;j < vDatos.size();j++){
          dDatos = (TVDinRep) vDatos.get(j);
          if(j == 0)
            rep.comDespliegaAlineado(sCol,iReng - 1,"" + i,true,rep.getAT_HCENTRO(),rep.getAT_VINFERIOR());
          rep.comDespliegaAlineado(sCol,iReng,dDatos.getString("iCountSolicitudes"),false,rep.getAT_HCENTRO(),rep.getAT_VAJUSTAR());
          iReng++;
        }
        rep.comFillCells(sCol,iRengIni - 1,sCol,iRengIni - 1,15);
        rep.comFillCells(sCol,iReng,sCol,iReng,15);
        rep.comBordeTotal(sCol,iRengIni - 1,sCol,iReng,1);
        rep.comCreaFormula(sCol,iReng,"=SUM(" + sCol + iRengIni + ":" + sCol + (iReng - 1) + ")");
      }
    }
    char cColFormula = cCol;
    cCol++;
    sCol = String.valueOf(cCol);
    rep.comDespliegaAlineado(sCol,iRengIni - 1,"TOTAL",true,rep.getAT_HCENTRO(),rep.getAT_VINFERIOR());
    cColIni++;
    for(int i = iRengIni;i < iReng;i++)
      rep.comCreaFormula(sCol,i,"=SUM(" + String.valueOf(cColIni) + i + ":" + String.valueOf(cColFormula) + i + ")");
    rep.comCreaFormula(sCol,iReng,"=SUM(" + sCol + iRengIni + ":" + sCol + (iReng - 1) + ")");
    rep.comFillCells(sCol,iRengIni - 1,sCol,iReng,15);
    rep.comBordeTotal(sCol,iRengIni - 1,sCol,iReng,1);
    rep.comFontBold(sCol,iRengIni - 1,sCol,iReng);

    return rep.getSbDatos();
  }

  public StringBuffer resumenOrdenOperacion(String cDatos) throws Exception{
    cError = "";
    TExcel rep = new TExcel();
    rep.iniciaReporte();

    int iEjercicio = 0;
    String cOrden = "";
    int iRengIni = 10;
    int iReng = iRengIni;
    int iRenglones = 1;
    int iLetrasRuta = 75;
    int iLetrasTrabRealizado = 43;
    String cEntidadRemplazaTexto = VParametros.getPropEspecifica("EntidadRemplazaTexto");

    try{
      String[] aFiltro = cDatos.split("/");
      iEjercicio = (aFiltro[0].trim().equals("")) ? 0 : Integer.parseInt(aFiltro[0].trim(),10);
      cOrden = aFiltro[1];
    } catch(Exception e){}
    if(iEjercicio <= 0 || cDatos.trim().equals(""))
      iEjercicio = tFecha.getIntYear(tFecha.TodaySQL());
    String cSql = "SELECT E.cNomEmbarcacion, M.cNombre AS cDscMunicipio, EF.cAbreviatura, " +
                  "SC.iEjercicio, SC.iNumSolicitud, SC.iNumOrdenOperacion, SC.dtSolcomunicacion, "+
                  "SC.cDscFinalidad, O.iCvePais, O.iCveEntidadFed, O.iCveMunicipio " +
                  "FROM VEHVehiculo V " +
                  "LEFT JOIN VEHEmbarcacion E ON E.iCveVehiculo = V.iCveVehiculo " +
                  "LEFT JOIN VEHTipoVehiculo TV ON TV.iCveTipoVeh = V.iCveTipoVeh " +
                  "LEFT JOIN CVHAsignacion A ON A.iCveVehiculo = V.iCveVehiculo AND A.dtFinAsignacion IS NULL " +
                  "LEFT JOIN GRLOficina O ON O.iCveOficina = A.iCveOficina " +
                  "LEFT JOIN GRLEntidadFed EF ON EF.iCvePais = O.iCvePais AND EF.iCveEntidadFed = O.iCveEntidadFed " +
                  "LEFT JOIN GRLMunicipio M ON M.iCvePais = O.iCvePais AND M.iCveEntidadFed = O.iCveEntidadFed AND M.iCveMunicipio = O.iCveMunicipio " +
                  "LEFT JOIN CBBSolComunicacion SC ON SC.iCveVehiculo = V.iCveVehiculo AND SC.iNumOrdenOperacion IS NOT NULL " +
                  "      AND SC.iEjercicio = " + iEjercicio +
                  " WHERE V.lPropioSCT = 1 " +
                  "  AND V.iCveVehiculo > 0 " +
                  "  AND TV.lRequiereOrdOpera = 1 " +
                  "ORDER BY " + cOrden;
    Vector vOrdenes = new Vector();
    try{
      vOrdenes = super.FindByGeneric("",cSql,dataSourceName);
    } catch(Exception e){}
    rep.comDespliegaAlineado("A",iRengIni - 1,"Ejercicio:   " + iEjercicio,true,rep.getAT_HIZQUIERDA(),rep.getAT_VCENTRO());
    if(vOrdenes != null && vOrdenes.size() > 0){
      int iRengIniOrden, iRengFinOrden;
      TVDinRep dOrdenes;
      String cTemp = "";
      for(int i = 0;i < vOrdenes.size();i++){
        dOrdenes = (TVDinRep) vOrdenes.get(i);
        iReng++;
        iRengIniOrden = iReng;
        iRengFinOrden = iReng+1;
        cTemp = dOrdenes.getString("iNumOrdenOperacion");
        if(cTemp == null || cTemp.equals("0")) cTemp = "";
        rep.comDespliegaAlineado("A",iReng,cTemp,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());
        cTemp = dOrdenes.getString("cNomEmbarcacion");
        if(cTemp == null) cTemp = "";
        rep.comDespliegaAlineado("B",iReng,cTemp,false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        cTemp = dOrdenes.getString("cDscMunicipio");
        if(cTemp == null || cTemp.equalsIgnoreCase("null") || dOrdenes.getInt("iCveMunicipio")<=0) cTemp = "";
        String cEntidad = dOrdenes.getString("cAbreviatura");
        String[] aEntidadRemplaza = cEntidadRemplazaTexto.split("/");
        String[] aDatos;
        for(int x = 0;x < aEntidadRemplaza.length;x++){
          aDatos = aEntidadRemplaza[x].split(",");
          if(Integer.parseInt(aDatos[0],10) == dOrdenes.getInt("iCvePais") &&
             Integer.parseInt(aDatos[1],10) == dOrdenes.getInt("iCveEntidadFed")){
            cTemp = aDatos[2];
            break;
          }
        }
        if(cEntidad != null && !cEntidad.equalsIgnoreCase("null"))
          cTemp += ", " + cEntidad + ".";
        rep.comDespliegaAlineado("B",iReng + 1,cTemp,false,rep.getAT_HIZQUIERDA(),rep.getAT_VSUPERIOR());
        cTemp = dOrdenes.getString("dtSolComunicacion");
        if(cTemp == null || cTemp.equalsIgnoreCase("null")) cTemp = "";
        rep.comDespliegaAlineado("C",iReng,cTemp,false,rep.getAT_HCENTRO(),rep.getAT_VSUPERIOR());

        String cSqlRuta = "SELECT S.iCveEntidadFed, S.iNumNacional, S.iConsecNumNacional, S.cNomSenal, "+
                          "M.iCveMunicipio, M.cNombre AS cDscMunicipio, EF.iCveEntidadFed, EF.cAbreviatura "+
                          "FROM CBBSenalXCom SC "+
                          "LEFT JOIN RGSSenal S ON S.iCveSenal = SC.iCveSenal "+
                          "LEFT JOIN GRLEntidadFed EF ON EF.iCvePais = S.iCvePais AND EF.iCveEntidadFed = S.iCveEntidadFed "+
                          "LEFT JOIN GRLMunicipio M ON M.iCvePais = S.iCvePais AND M.iCveEntidadFed = S.iCveEntidadFed AND M.iCveMunicipio = S.iCveMunicipio "+
                          "WHERE SC.iEjercicio = " + iEjercicio + " AND SC.iNumSolicitud = " + dOrdenes.getInt("iNumSolicitud") + " "+
                          "ORDER BY SC.iOrden, SC.iCveSenal ";
        Vector vSenales = new Vector();
        try{
          vSenales = super.FindByGeneric("", cSqlRuta, dataSourceName);
        }catch(Exception e){}
        if(vSenales != null && vSenales.size()>0){
          cTemp = "";
          TVDinRep dSenal;
          for(int j=0; j<vSenales.size(); j++){
            dSenal = (TVDinRep)vSenales.get(j);
            if(j > 0)
              cTemp += " / ";
            cTemp += tNum.getNumeroSinSeparador(new Integer(dSenal.getInt("iCveEntidadFed")),2);
            cTemp += "-" + tNum.getNumeroSinSeparador(new Integer(dSenal.getInt("iNumNacional")),3);
            cTemp += (dSenal.getInt("iConsecNumNacional")>0)?"."+dSenal.getInt("iConsecNumNacional"):"";
            cTemp += " " + dSenal.getString("cNomSenal");
            cTemp += (dSenal.getInt("iCveMunicipio")>0)?" "+dSenal.getString("cDscMunicipio")+",":"";
            cTemp += (dSenal.getInt("iCveEntidadFed")>0)?" "+dSenal.getString("cAbreviatura")+".":"";
          }
        }
        iRenglones = 1;
        if(cTemp.length() > 0){
          iRenglones = (int) (cTemp.length() / iLetrasRuta);
          if(cTemp.length() % iLetrasRuta > 0)
            iRenglones++;
        }
        rep.comAlineaRango("D",iReng,"D",iReng+iRenglones-1,rep.getAT_COMBINA_IZQUIERDA());
        rep.comDespliegaAlineado("D",iReng,cTemp,false,rep.getAT_HJUSTIFICA(),rep.getAT_VSUPERIOR());
        if(iRengFinOrden < (iReng + iRenglones - 1))
          iRengFinOrden = iReng + iRenglones - 1;

        cTemp = "";
        if(dOrdenes.getInt("iNumOrdenOperacion")>0){
          cTemp = dOrdenes.getString("cDscFinalidad");
          if(cTemp == null || cTemp.equalsIgnoreCase("null")) cTemp = "";
          cTemp = "DAR CUMPLIMIENTO A PROGRAMAS DE MANTENIMIENTO PREVENTIVO Y/O CORRECTIVO AL SEÑALAMIENTO" +
                  ( (cTemp.equals("")) ? "." : ", ADEMÁS DE LAS SIGUIENTES TAREAS: " + cTemp);
        }
        iRenglones = 1;
        if(cTemp.length() > 0){
          iRenglones = (int) (cTemp.length() / iLetrasTrabRealizado);
          if(cTemp.length() % iLetrasTrabRealizado > 0)
            iRenglones++;
        }
        rep.comAlineaRango("E",iReng,"E",iReng+iRenglones-1,rep.getAT_COMBINA_IZQUIERDA());
        rep.comDespliegaAlineado("E",iReng,cTemp,false,rep.getAT_HJUSTIFICA(),rep.getAT_VSUPERIOR());
        if(iRengFinOrden < (iReng + iRenglones - 1))
          iRengFinOrden = iReng + iRenglones - 1;
        rep.comBordeRededor("A",iRengIniOrden,"A",iRengFinOrden,1,1);
        rep.comBordeRededor("B",iRengIniOrden,"B",iRengFinOrden,1,1);
        rep.comBordeRededor("C",iRengIniOrden,"C",iRengFinOrden,1,1);
        rep.comBordeRededor("D",iRengIniOrden,"D",iRengFinOrden,1,1);
        rep.comBordeRededor("E",iRengIniOrden,"E",iRengFinOrden,1,1);
        iReng = iRengFinOrden;
      }
    }
    return rep.getSbDatos();
  }

  public StringBuffer subidaVaradero(String cDatos) throws Exception{
    cError = "";
    TExcel rep = new TExcel();
    rep.iniciaReporte();

    int iRengIni = 8;
    int iReng = iRengIni;
    int iVehiculoAnt = -1;
    int iRengIniVeh = 0;
    String cTemp = "", cCeldas = "";
    String cSql = "SELECT E.iCveVehiculo, E.cNomEmbarcacion, UV.dtIniUsoVaradero, UV.dtEstimadaFinUso, "+
                  "UV.dtFinUsoVaradero, UV.dCostoUsoVaradero, UV.cDscMotivo, UV.cObses "+
                  "FROM VEHVehiculo V "+
                  "LEFT JOIN VEHEmbarcacion E ON E.iCveVehiculo = V.iCveVehiculo "+
                  "LEFT JOIN VEHTipoVehiculo TV ON TV.iCveTipoVeh = V.iCveTipoVeh "+
                  "LEFT JOIN CVHUsoVaradero UV ON UV.iCveVehiculo = V.iCveVehiculo "+
                  "WHERE V.lPropioSCT = 1 AND V.iCveVehiculo > 0  AND TV.lMotorUnico = 0 "+
                  "ORDER BY E.cNomEmbarcacion, UV.dtIniUsoVaradero DESC";
    Vector vDatos = new Vector();
    try {
      vDatos = super.FindByGeneric("", cSql, dataSourceName);
    }catch (Exception ex) {}
    if(vDatos != null && vDatos.size()>0){
      TVDinRep dDatos;
      for(int i=0; i<vDatos.size(); i++){
        dDatos = (TVDinRep)vDatos.get(i);
        if(iVehiculoAnt != dDatos.getInt("iCveVehiculo")){
          iVehiculoAnt = dDatos.getInt("iCveVehiculo");
          if(iRengIniVeh != 0){
            rep.comBordeRededor("A", iRengIniVeh, "A", iReng-1, 1, 1);
            rep.comDespliegaAlineado("D", iReng, "D", iReng, "Total:", true, rep.getAT_HDERECHA(), "");
            rep.comCreaFormula("E", iReng, "=SUM(E"+iRengIniVeh+":E"+(iReng-1)+")");
            cCeldas += "E" + iReng + ",";
            rep.comFillCells("D", iReng, "E", iReng, 15);
            rep.comFontBold("E", iReng, "E", iReng);
            rep.comCellFormat("E", iReng, "E", iReng, "$ #,##0.00;[Rojo]-$ #,##.00");
            rep.comBordeTotal("D", iReng, "E", iReng, 1);
          }
          iReng+=2;
          rep.comDespliega("A", iReng, "Buque Balizador");
          rep.comDespliega("B", iReng, "Subida");
          rep.comDespliega("C", iReng, "Bajada Estimada");
          rep.comDespliega("D", iReng, "Bajada Real");
          rep.comDespliega("E", iReng, "Costo");
          rep.comDespliega("F", iReng, "Motivo");
          rep.comFillCells("A", iReng, "F", iReng, 15);
          rep.comFontBold("A", iReng, "F", iReng);
          rep.comBordeTotal("A", iReng, "F", iReng, 1);
          rep.comAlineaRango("A", iReng, "F", iReng, rep.getAT_HCENTRO());
          rep.comAlineaRangoVer("A", iReng, "F", iReng, rep.getAT_VINFERIOR());
          iReng++;
          iRengIniVeh = iReng;
          rep.comDespliega("A", iReng, dDatos.getString("cNomEmbarcacion"));
        }
        cTemp = dDatos.getString("dtIniUsoVaradero");
        if(cTemp.equalsIgnoreCase("")|| cTemp.equalsIgnoreCase("null")) cTemp = "";
        rep.comDespliegaAlineado("B", iReng, cTemp, false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
        cTemp = dDatos.getString("dtEstimadaFinUso");
        if(cTemp.equalsIgnoreCase("")|| cTemp.equalsIgnoreCase("null")) cTemp = "";
        rep.comDespliegaAlineado("C", iReng, cTemp, false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
        cTemp = dDatos.getString("dtFinUsoVaradero");
        if(cTemp.equalsIgnoreCase("")|| cTemp.equalsIgnoreCase("null")) cTemp = "";
        rep.comDespliegaAlineado("D", iReng, cTemp, false, rep.getAT_HCENTRO(), rep.getAT_VSUPERIOR());
        rep.comCellFormat("B", iReng, "D", iReng, "dd/mm/aaaa");
        cTemp = tNum.getNumeroDecimal(dDatos.getDouble("dCostoUsoVaradero"),2,true);
        if(cTemp.equalsIgnoreCase("")|| cTemp.equalsIgnoreCase("null")) cTemp = "";
        rep.comDespliegaAlineado("E", iReng, cTemp, false, rep.getAT_HDERECHA(), rep.getAT_VSUPERIOR());
        rep.comCellFormat("E", iReng, "E", iReng, "$ #,##0.00;[Rojo]-$ #,##.00");
        cTemp = dDatos.getString("cDscMotivo");
        if(cTemp == null || cTemp.equalsIgnoreCase("") || cTemp.equalsIgnoreCase("null")) cTemp = "";
        if(dDatos.getString("cObses") != null && !dDatos.getString("cObses").equalsIgnoreCase("") && !dDatos.getString("cObses").equalsIgnoreCase("null"))
          cTemp += " OBSERVACIONES: " + dDatos.getString("cObses");
        rep.comDespliegaAlineado("F",iReng,cTemp,false,rep.getAT_HJUSTIFICA(),rep.getAT_VAJUSTAR());
        rep.comBordeRededor("B", iReng, "B", iReng, 1, 1);
        rep.comBordeRededor("C", iReng, "C", iReng, 1, 1);
        rep.comBordeRededor("D", iReng, "D", iReng, 1, 1);
        rep.comBordeRededor("E", iReng, "E", iReng, 1, 1);
        rep.comBordeRededor("F", iReng, "F", iReng, 1, 1);
        iReng++;
      }
      rep.comBordeRededor("A", iRengIniVeh, "A", iReng-1, 1, 1);
      rep.comDespliegaAlineado("D", iReng, "D", iReng, "Total:", true, rep.getAT_HDERECHA(), "");
      rep.comCreaFormula("E", iReng, "=SUM(E"+iRengIniVeh+":E"+(iReng-1)+")");
      cCeldas += "E" + iReng;
      rep.comFillCells("D", iReng, "E", iReng, 15);
      rep.comFontBold("E", iReng, "E", iReng);
      rep.comCellFormat("E", iReng, "E", iReng, "$ #,##0.00;[Rojo]-$ #,##.00");
      rep.comBordeTotal("D", iReng, "E", iReng, 1);
      iReng+=3;
      rep.comDespliegaAlineado("A", iReng, "D", iReng, "Gran Total:", true, rep.getAT_COMBINA_DERECHA(), "");
      rep.comCreaFormula("E", iReng, "=SUM("+cCeldas+")");
      rep.comFillCells("A", iReng, "E", iReng, 15);
      rep.comFontBold("E", iReng, "E", iReng);
      rep.comCellFormat("E", iReng, "E", iReng, "$ #,##0.00;[Rojo]-$ #,##.00");
      rep.comBordeTotal("A", iReng, "E", iReng, 1);
    }
    return rep.getSbDatos();
  }

  public Vector repMensualCarGenerales(int iVehiculo, int iRengIni) throws Exception{
    Vector vResultado = new Vector();
    cError = "";
    TExcel rep = new TExcel();
    rep.iniciaReporte();

    int iReng = iRengIni;
    String cTemp = "";
    String cSql = "SELECT V.iCveVehiculo, V.iCveTipoVeh, V.iCveMarcaVeh, V.iCveSubMarca, V.iModelo, "+
                  "V.cMatricula, V.cNumEconomico, V.cNumSerie, TV.cDscTipoVeh, TV.lMotorUnico, "+
                  "TV.lRequiereOrdOpera, M.cDscMarcaVeh, SM.cDscSubmarca, E.cNomEmbarcacion, E.dEslora, "+
                  "E.dManga, E.dPuntal, UM1.cAbreviatura AS cDscUniMedEslora, UM2.cAbreviatura AS cDscUniMedManga, "+
                  "UM2.cAbreviatura AS cDscUniMedPuntal, O.cDscOficina, D.cDscDepartamento, MOT.iConsecutivo AS iConsecutivoMotor, "+
                  "MM.cDscMarcaMotor, MOT.cNumSerie AS cNumSerieMotor, MOT.iPotencia, MOT.iRevXMinuto, "+
                  "UM4.cAbreviatura AS cDscUniMedMotor "+
                  "FROM VEHVehiculo V "+
                  "LEFT JOIN VEHTipoVehiculo TV ON TV.iCveTipoVeh = V.iCveTipoVeh "+
                  "LEFT JOIN VEHMarcaVeh M ON M.iCveTipoVeh = V.iCveTipoVeh AND M.iCveMarcaVeh = V.iCveMarcaVeh "+
                  "LEFT JOIN VEHSubMarcaVeh SM ON SM.iCveTipoVeh = V.iCveTipoVeh AND SM.iCveMarcaVeh = V.iCveMarcaVeh AND SM.iCveSubMarca = V.iCveSubMarca "+
                  "LEFT JOIN VEHEmbarcacion E ON E.iCveVehiculo = V.iCveVehiculo "+
                  "LEFT JOIN VEHUnidadMedida UM1 ON UM1.iCveUnidadMedida = E.iUniMedEslora "+
                  "LEFT JOIN VEHUnidadMedida UM2 ON UM2.iCveUnidadMedida = E.iUniMedManga "+
                  "LEFT JOIN VEHUnidadMedida UM3 ON UM3.iCveUnidadMedida = E.iUniMedPuntal "+
                  "LEFT JOIN CVHAsignacion AV ON AV.iCveVehiculo = V.iCveVehiculo AND AV.dtFinAsignacion IS NULL "+
                  "LEFT JOIN GRLOficina O ON O.iCveOficina = AV.iCveOficina "+
                  "LEFT JOIN GRLDepartamento D ON D.iCveDepartamento = AV.iCveDepartamento "+
                  "LEFT JOIN VEHMotor MOT ON MOT.iCveVehiculo = V.iCveVehiculo "+
                  "LEFT JOIN VEHMarcaMotor MM ON MM.iCveMarcaMotor = MOT.iCveMarcaMotor "+
                  "LEFT JOIN VEHUnidadMedida UM4 ON UM4.iCveUnidadMedida = MOT.iCveUniMedPotencia "+
                  "WHERE V.iCveVehiculo = " + iVehiculo + " " +
                  "ORDER BY V.iCveVehiculo, MOT.iConsecutivo ";
    Vector vDatos = new Vector();
    try {
      vDatos = super.FindByGeneric("", cSql, dataSourceName);
    }catch (Exception ex) {}
    if(vDatos != null && vDatos.size()>0){
      TVDinRep dDatos = (TVDinRep)vDatos.get(0);
      rep.comDespliegaCombinado("DATOS GENERALES DEL VEHÍCULO", "A", iReng, "AM", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      iReng++;
      rep.comDespliegaAlineado("A", iReng, "E", iReng, "Tipo de Vehículo:", true, rep.getAT_COMBINA_DERECHA(), rep.getAT_VSUPERIOR());
      rep.comDespliegaAlineado("F", iReng, "AA", iReng, dDatos.getString("cDscTipoVeh"), false, rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VSUPERIOR());
      iReng++;
      rep.comDespliegaAlineado("A", iReng, "E", iReng, "Nombre:", true, rep.getAT_COMBINA_DERECHA(), rep.getAT_VSUPERIOR());
      rep.comDespliegaAlineado("F", iReng, "AA", iReng, dDatos.getString("cNomEmbarcacion"), false, rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VSUPERIOR());
      rep.comDespliegaAlineado("AB", iReng, "AF", iReng, "No. Econ. Actual:", true, rep.getAT_COMBINA_DERECHA(), rep.getAT_VSUPERIOR());
      rep.comDespliegaAlineado("AG", iReng, "AM", iReng, dDatos.getString("cNumEconomico"), false, rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VSUPERIOR());
      iReng++;
      rep.comDespliegaAlineado("A", iReng, "E", iReng, "Marca:", true, rep.getAT_COMBINA_DERECHA(), rep.getAT_VSUPERIOR());
      rep.comDespliegaAlineado("F", iReng, "R", iReng, dDatos.getString("cDscMarcaVeh"), false, rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VSUPERIOR());
      rep.comDespliegaAlineado("S", iReng, "U", iReng, "SubMarca:", true, rep.getAT_COMBINA_DERECHA(), rep.getAT_VSUPERIOR());
      rep.comDespliegaAlineado("V", iReng, "AA", iReng, dDatos.getString("cDscSubmarca"), false, rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VSUPERIOR());
      rep.comDespliegaAlineado("AB", iReng, "AF", iReng, "No. Serie:", true, rep.getAT_COMBINA_DERECHA(), rep.getAT_VSUPERIOR());
      rep.comDespliegaAlineado("AG", iReng, "AM", iReng, dDatos.getString("cNumSerie"), false, rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VSUPERIOR());
      iReng++;
      rep.comDespliegaAlineado("A", iReng, "E", iReng, "Matricula:", true, rep.getAT_COMBINA_DERECHA(), rep.getAT_VSUPERIOR());
      rep.comDespliegaAlineado("F", iReng, "R", iReng, dDatos.getString("cMatricula"), false, rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VSUPERIOR());
      rep.comDespliegaAlineado("S", iReng, "U", iReng, "Modelo:", true, rep.getAT_COMBINA_DERECHA(), rep.getAT_VSUPERIOR());
      rep.comDespliegaAlineado("V", iReng, "AA", iReng, dDatos.getString("iModelo"), false, rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VSUPERIOR());
      if(dDatos.getInt("iCveTipoVeh")!=2){
        rep.comDespliegaAlineado("AB",iReng,"AF",iReng,"Eslora:",true,
                                 rep.getAT_COMBINA_DERECHA(),
                                 rep.getAT_VSUPERIOR());
        rep.comDespliegaAlineado("AG",iReng,"AM",iReng,
                                 dDatos.getString("dEslora") + " " +
                                 dDatos.getString("cDscUniMedEslora"),false,
                                 rep.getAT_COMBINA_IZQUIERDA(),
                                 rep.getAT_VSUPERIOR());
        iReng++;
        rep.comDespliegaAlineado("A",iReng,"E",iReng,"Asigndo a Oficina:",true,
                                 rep.getAT_COMBINA_DERECHA(),
                                 rep.getAT_VSUPERIOR());
        rep.comDespliegaAlineado("F",iReng,"AA",iReng,
                                 dDatos.getString("cDscOficina"),false,
                                 rep.getAT_COMBINA_IZQUIERDA(),
                                 rep.getAT_VSUPERIOR());
        rep.comDespliegaAlineado("AB",iReng,"AF",iReng,"Manga:",true,
                                 rep.getAT_COMBINA_DERECHA(),
                                 rep.getAT_VSUPERIOR());
        rep.comDespliegaAlineado("AG",iReng,"AM",iReng,
                                 dDatos.getString("dManga") + " " +
                                 dDatos.getString("cDscUniMedManga"),false,
                                 rep.getAT_COMBINA_IZQUIERDA(),
                                 rep.getAT_VSUPERIOR());
        iReng++;
        rep.comDespliegaAlineado("AB", iReng, "AF", iReng, "Puntal:", true, rep.getAT_COMBINA_DERECHA(), rep.getAT_VSUPERIOR());
        rep.comDespliegaAlineado("AG", iReng, "AM", iReng, dDatos.getString("dPuntal") + " " + dDatos.getString("cDscUniMedPuntal"), false, rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VSUPERIOR());
        rep.comBordeRededor("A", iRengIni, "AM", iReng, 1, 1);

      }
      iReng++;
      rep.comDespliegaAlineado("A", iReng, "E", iReng, "Departamento:", true, rep.getAT_COMBINA_DERECHA(), rep.getAT_VSUPERIOR());
      rep.comDespliegaAlineado("F", iReng, "AA", iReng, dDatos.getString("cDscDepartamento"), false, rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VSUPERIOR());
      iReng++;
      rep.comDespliegaCombinado("MOTOR(ES) DEL VEHÍCULO", "A", iReng, "AM", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 0, true, false, 1, 1);
      iReng++;
      for(int i=0; i<vDatos.size(); i++){
        if(i==0){
          rep.comDespliegaAlineado("A", iReng, "B", iReng, "No.", true, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR());
          rep.comBordeRededor("A", iReng, "B", iReng, 1, 1);
          rep.comDespliegaAlineado("C", iReng, "H", iReng, "Marca", true, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR());
          rep.comBordeRededor("C", iReng, "H", iReng, 1, 1);
          rep.comDespliegaAlineado("I", iReng, "N", iReng, "No. Serie", true, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR());
          rep.comBordeRededor("I", iReng, "N", iReng, 1, 1);
          rep.comDespliegaAlineado("O", iReng, "Q", iReng, "Potencia", true, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR());
          rep.comBordeRededor("O", iReng, "Q", iReng, 1, 1);
          rep.comDespliegaAlineado("R", iReng, "S", iReng, "R.P.M.", true, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR());
          rep.comBordeRededor("R", iReng, "S", iReng, 1, 1);
          if(vDatos.size() > 1){
            rep.comDespliegaAlineado("U", iReng, "V", iReng, "No.", true, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR());
            rep.comBordeRededor("U", iReng, "V", iReng, 1, 1);
            rep.comDespliegaAlineado("W", iReng, "AB", iReng, "Marca", true, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR());
            rep.comBordeRededor("W", iReng, "AB", iReng, 1, 1);
            rep.comDespliegaAlineado("AC", iReng, "AH", iReng, "No. Serie", true, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR());
            rep.comBordeRededor("AC", iReng, "AH", iReng, 1, 1);
            rep.comDespliegaAlineado("AI", iReng, "AK", iReng, "Potencia", true, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR());
            rep.comBordeRededor("AI", iReng, "AK", iReng, 1, 1);
            rep.comDespliegaAlineado("AL", iReng, "AM", iReng, "R.P.M.", true, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR());
            rep.comBordeRededor("AL", iReng, "AM", iReng, 1, 1);
          }
          iReng++;
        }
        dDatos = (TVDinRep)vDatos.get(i);
        if((i % 2) == 0){
          rep.comDespliegaAlineado("A", iReng, "B", iReng, dDatos.getString("iConsecutivoMotor"), false, rep.getAT_COMBINA_CENTRO(), rep.getAT_VSUPERIOR());
          rep.comBordeRededor("A", iReng, "B", iReng, 1, 1);
          rep.comDespliegaAlineado("C", iReng, "H", iReng, dDatos.getString("cDscMarcaMotor"), false, rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VINFERIOR());
          rep.comBordeRededor("C", iReng, "H", iReng, 1, 1);
          rep.comDespliegaAlineado("I", iReng, "N", iReng, dDatos.getString("cNumSerieMotor"), false, rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VINFERIOR());
          rep.comBordeRededor("I", iReng, "N", iReng, 1, 1);
          rep.comDespliegaAlineado("O", iReng, "Q", iReng, dDatos.getString("iPotencia") + " " + dDatos.getString("cDscUniMedMotor"), false, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR());
          rep.comBordeRededor("O", iReng, "Q", iReng, 1, 1);
          rep.comDespliegaAlineado("R", iReng, "S", iReng, dDatos.getString("iRevXMinuto"), false, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR());
          rep.comBordeRededor("R", iReng, "S", iReng, 1, 1);
        }else{
          rep.comDespliegaAlineado("U", iReng, "V", iReng, dDatos.getString("iConsecutivoMotor"), false, rep.getAT_COMBINA_CENTRO(), rep.getAT_VSUPERIOR());
          rep.comBordeRededor("U", iReng, "V", iReng, 1, 1);
          rep.comDespliegaAlineado("W", iReng, "AB", iReng, dDatos.getString("cDscMarcaMotor"), false, rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VINFERIOR());
          rep.comBordeRededor("W", iReng, "AB", iReng, 1, 1);
          rep.comDespliegaAlineado("AC", iReng, "AH", iReng, dDatos.getString("cNumSerieMotor"), false, rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VINFERIOR());
          rep.comBordeRededor("AC", iReng, "AH", iReng, 1, 1);
          rep.comDespliegaAlineado("AI", iReng, "AK", iReng, dDatos.getString("iPotencia") + " " + dDatos.getString("cDscUniMedMotor"), false, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR());
          rep.comBordeRededor("AI", iReng, "AK", iReng, 1, 1);
          rep.comDespliegaAlineado("AL", iReng, "AM", iReng, dDatos.getString("iRevXMinuto"), false, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR());
          rep.comBordeRededor("AL", iReng, "AM", iReng, 1, 1);
          iReng++;
        }
      }
      iReng++;
    }
    vResultado.addElement(new Integer(iReng));
    vResultado.addElement(rep.getSbDatos());
    return vResultado;
  }

  public Vector repCondicionesGenerales(int iVehiculo, int iRengIni, int iEjercicio, int iMes) throws Exception{
    Vector vResultado = new Vector();
    cError = "";
    TExcel rep = new TExcel();
    rep.iniciaReporte();

    int iReng = iRengIni;
    String cTemp = "";
    String cSqlNiv = "SELECT N.iCveNivel, N.iOrden, N.cDscNivel, N.cAcronimo FROM CVHNivelCalificacion N ORDER BY N.iOrden";
    String cSql = "SELECT P.iCveTipoVeh, P.iCveParte, P.cDscParte, P.lActivo, "+
                  "DC.iCveVehiculo, DC.iEjercicioCalifica, DC.iMesCalifica, DC.iCveNivel, "+
                  "N.iOrden, N.cDscNivel, N.cAcronimo, C.lConcluido, O.cObsRegistrada "+
                  "FROM CVHPartesCalificar P "+
                  "LEFT JOIN CVHDatosCalifica DC ON DC.iCveTipoVeh = P.iCveTipoVeh "+
                  "      AND DC.iCveParte = P.iCveParte "+
                  "      AND DC.iCveVehiculo = " + iVehiculo +
                  "      AND DC.iEjercicioCalifica = " + iEjercicio +
                  "      AND DC.iMesCalifica = " + iMes + " " +
                  "LEFT JOIN VEHVehiculo V ON V.iCveVehiculo = " + iVehiculo + " " +
                  "LEFT JOIN VEHTipoVehiculo TV ON TV.iCveTipoVeh = V.iCveTipoVeh "+
                  "LEFT JOIN CVHNivelCalificacion N ON N.iCveNivel = DC.iCveNivel "+
                  "LEFT JOIN CVHCalificacion C ON C.iCveVehiculo = DC.iCveVehiculo AND C.iEjercicioCalifica = DC.iEjercicioCalifica AND C.iMesCalifica = DC.iMesCalifica "+
                  "LEFT JOIN GRLObservacion O ON O.iEjercicio = C.iEjercicioObs AND O.iCveObservacion = C.iCveObservacion "+
                  "WHERE P.iCveTipoVeh = TV.iCveTipoVeh " +
                  "ORDER BY P.iCveTipoVeh, P.cDscParte";


    Vector vDatos = new Vector();
    Vector vNivel = new Vector();
    try {
      vNivel = super.FindByGeneric("", cSqlNiv, dataSourceName);
      vDatos = super.FindByGeneric("", cSql, dataSourceName);
    }catch (Exception ex) {}
    if(vNivel != null && vNivel.size()>0 && vDatos != null && vDatos.size()>0){
      rep.comDespliegaCombinado("CONDICIONES GENERALES DEL VEHÍCULO", "A", iReng, "AM", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      iReng++;
      rep.comDespliegaCombinado("Descripción", "A", iReng, "J", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      char cCol = 'K';
      String sCol = String.valueOf(cCol);
      for(int i=0; i<vNivel.size(); i++){
        cTemp = ((TVDinRep)vNivel.get(i)).getString("cDscNivel");
        rep.comRotar(sCol, iReng, sCol, iReng, 90);
        rep.comDespliegaCombinado(cTemp, sCol, iReng, sCol, iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
        cCol++;
        sCol = String.valueOf(cCol);
        if(cCol == 91){
          cCol = 'A';
          sCol = "A" + String.valueOf(cCol);
        }
      }
      rep.comAlineaRango(sCol, iReng, "AM", iReng, rep.getAT_COMBINA_IZQUIERDA());
      String sColIniObs = sCol;
      int iRengIniObs = iReng;
      iReng++;
      if(((TVDinRep)vDatos.get(0)).getInt("lConcluido") == 0)
        rep.comDespliega(sColIniObs, iRengIniObs, "NO SE HA CONCLUIDO EL REGISTRO DE ESTE MES");
      else{
        TVDinRep dDatos;
        int iNivelActual,iNivelParte;
        for(int i = 0;i < vDatos.size();i++){
          dDatos = (TVDinRep) vDatos.get(i);
          rep.comDespliegaCombinado(dDatos.getString("cDscParte"),"A",iReng,"J",iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR(),false,0,true,false,1,1);
          cCol = 'K';
          sCol = String.valueOf(cCol);
          for(int j = 0;j < vNivel.size();j++){
            iNivelActual = ( (TVDinRep) vNivel.get(j)).getInt("iCveNivel");
            iNivelParte = dDatos.getInt("iCveNivel");
            cTemp = (iNivelActual == iNivelParte) ? "X" : "";
            if(dDatos.getInt("iEjercicioCalifica") == 0)
              cTemp = "'--";
            rep.comDespliegaCombinado(cTemp,sCol,iReng,sCol,iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            cCol++;
            sCol = String.valueOf(cCol);
            if(cCol == 91){
              cCol = 'A';
              sCol = "A" + String.valueOf(cCol);
            }
          }
          iReng++;
        }
        cTemp = ( (TVDinRep) vDatos.get(0)).getString("cObsRegistrada");
        if(cTemp == null || cTemp.equalsIgnoreCase("null"))
          cTemp = "";
        if(!cTemp.equalsIgnoreCase(""))
          rep.comDespliega(sColIniObs,iRengIniObs,"OBSERVACIONES:  " + cTemp);
      }
      rep.comAlineaRango(sColIniObs,iRengIniObs,"AM",iReng - 1,rep.getAT_COMBINA_IZQUIERDA());
      rep.comAlineaRango(sColIniObs,iRengIniObs,"AM",iReng - 1,rep.getAT_HJUSTIFICA());
      rep.comBordeRededor(sColIniObs,iRengIniObs,"AM",iReng - 1,1,1);
    }
    vResultado.addElement(new Integer(iReng));
    vResultado.addElement(rep.getSbDatos());
    return vResultado;
  }

  public Vector repReparacionesServicios(int iVehiculo, int iRengIni, int iEjercicio, int iMes) throws Exception{
    Vector vResultado = new Vector();
    cError = "";
    TExcel rep = new TExcel();
    rep.iniciaReporte();

    int iReng = iRengIni;
    String cTemp = "";
    String cSql = "SELECT R.iCveTipoVeh, R.iCveTipoRepara, R.cDscTipoRepara, R.lActivo, "+
                  "DR.iCveVehiculo, DR.iEjercicioRepara, DR.iMesRepara, DR.lEfectuadaPendiente, "+
                  "DR.dtReparacion, DR.dImporte, C.lConcluido, O.cObsRegistrada "+
                  "FROM CVHTipoRepVeh R "+
                  "LEFT JOIN CVHDatosRepara DR ON DR.iCveTipoVeh = R.iCveTipoVeh "+
                  "      AND DR.iCveTipoRepara = R.iCveTipoRepara "+
                  "      AND DR.iCveVehiculo = " + iVehiculo +
                  "      AND DR.iEjercicioRepara = " + iEjercicio +
                  "      AND DR.iMesRepara = " + iMes + " " +
                  "LEFT JOIN CVHReparacion C ON C.iCveVehiculo = DR.iCveVehiculo AND C.iEjercicioRepara = DR.iEjercicioRepara AND C.iMesRepara = DR.iMesRepara "+
                  "LEFT JOIN VEHVehiculo V ON V.iCveVehiculo = " + iVehiculo + " " +
                  "LEFT JOIN VEHTipoVehiculo TV ON TV.iCveTipoVeh = V.iCveTipoVeh "+
                  "LEFT JOIN GRLObservacion O ON O.iEjercicio = C.iEjercicioObs AND O.iCveObservacion = C.iCveObservacion "+
                  "WHERE R.iCveTipoVeh = TV.iCveTipoVeh " +
                  "ORDER BY R.iCveTipoVeh, R.cDscTipoRepara ";
    Vector vDatos = new Vector();
    try {
      vDatos = super.FindByGeneric("", cSql, dataSourceName);
    }catch (Exception ex) {ex.printStackTrace();}
    if(vDatos != null && vDatos.size()>0){
      rep.comDespliegaCombinado("REPARACIONES Y SERVICIOS PENDIENTES Y EFECTUADOS DEL VEHÍCULO", "A", iReng, "AM", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      iReng++;
      rep.comDespliegaCombinado("Descripción", "A", iReng, "J", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comRotar("K", iReng, "L", iReng, 90);
      rep.comDespliegaCombinado("Efectuada", "K", iReng, "K", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comDespliegaCombinado("Pendiente", "L", iReng, "L", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comDespliegaCombinado("Fecha", "M", iReng, "P", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comDespliegaCombinado("Importe", "Q", iReng, "U", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comAlineaRango("V", iReng, "AM", iReng, rep.getAT_COMBINA_IZQUIERDA());
      int iRengIniObs = iReng;
      double dPendiente = 0.0d;
      double dEfectuado = 0.0d;
      double dImporte;
      iReng++;
      if(((TVDinRep)vDatos.get(0)).getInt("lConcluido") == 0){
        rep.comAlineaRango("V",iRengIniObs,"AM",iReng,rep.getAT_COMBINA_IZQUIERDA());
        rep.comAlineaRango("V",iRengIniObs,"AM",iReng,rep.getAT_HJUSTIFICA());
        rep.comBordeRededor("V",iRengIniObs,"AM",iReng,1,1);
        rep.comDespliega("V", iRengIniObs, "NO SE HA CONCLUIDO EL REGISTRO DE ESTE MES");
      }else{
        TVDinRep dDatos;
        for(int i = 0;i < vDatos.size();i++){
          dDatos = (TVDinRep) vDatos.get(i);
          rep.comDespliegaCombinado(dDatos.getString("cDscTipoRepara"),"A",iReng,"J",iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR(),false,0,true,false,1,1);
          if(dDatos.getInt("iEjercicioRepara") == 0){
            rep.comDespliegaCombinado("'--","K",iReng,"K",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            rep.comDespliegaCombinado("'--","L",iReng,"L",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            rep.comDespliegaCombinado("","M",iReng,"P",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VSUPERIOR(),false,0,true,false,1,1);
            rep.comDespliegaCombinado("","Q",iReng,"U",iReng,rep.getAT_COMBINA_DERECHA(),rep.getAT_VSUPERIOR(),false,0,true,false,1,1);
          } else{
            dImporte = dDatos.getDouble("dImporte");
            if(dDatos.getInt("lEfectuadaPendiente") == 1){
              rep.comDespliegaCombinado("X","K",iReng,"K",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
              rep.comDespliegaCombinado("","L",iReng,"L",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
              dEfectuado += dImporte;
              cTemp = tFecha.getFechaDDMMMYYYY(dDatos.getDate("dtReparacion"),"/");
              rep.comDespliegaCombinado(cTemp,"M",iReng,"P",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VSUPERIOR(),false,0,true,false,1,1);
            } else{
              rep.comDespliegaCombinado("","K",iReng,"K",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
              rep.comDespliegaCombinado("X","L",iReng,"L",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
              dPendiente += dImporte;
              rep.comDespliegaCombinado("","M",iReng,"P",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VSUPERIOR(),false,0,true,false,1,1);
            }
            cTemp = tNum.getNumeroDecimal(dImporte,2,false);
            rep.comDespliegaCombinado(cTemp,"Q",iReng,"U",iReng,rep.getAT_COMBINA_DERECHA(),rep.getAT_VSUPERIOR(),false,0,true,false,1,1);
            rep.comCellFormat("M",iReng,"P",iReng,"dd/Mmm/aaaa");
            rep.comCellFormat("Q",iReng,"U",iReng,"$ #,##0.00");
          }
          iReng++;
        }
        rep.comAlineaRango("V",iRengIniObs,"AM",iReng - 1,rep.getAT_COMBINA_IZQUIERDA());
        rep.comAlineaRango("V",iRengIniObs,"AM",iReng - 1,rep.getAT_HJUSTIFICA());
        rep.comBordeRededor("V",iRengIniObs,"AM",iReng - 1,1,1);
        cTemp = ( (TVDinRep) vDatos.get(0)).getString("cObsRegistrada");
        if(cTemp == null || cTemp.equalsIgnoreCase("null"))
          cTemp = "";
        if(!cTemp.equalsIgnoreCase(""))
          rep.comDespliega("V",iRengIniObs,"OBSERVACIONES:  " + cTemp);
        rep.comDespliegaCombinado("Total Pendiente:","Q",iReng,"V",iReng,rep.getAT_COMBINA_DERECHA(),rep.getAT_VSUPERIOR(),true,15,true,false,1,1);
        rep.comDespliegaCombinado(tNum.getNumeroDecimal(dPendiente,2,true),"W",iReng,"AA",iReng,rep.getAT_COMBINA_DERECHA(),rep.getAT_VSUPERIOR(),true,15,true,false,1,1);
        rep.comCellFormat("W",iReng,"AA",iReng,"$ #,##0.00");
        rep.comDespliegaCombinado("Total Efectuado:","AC",iReng,"AH",iReng,rep.getAT_COMBINA_DERECHA(),rep.getAT_VSUPERIOR(),true,15,true,false,1,1);
        rep.comDespliegaCombinado(tNum.getNumeroDecimal(dEfectuado,2,true),"AI",iReng,"AM",iReng,rep.getAT_COMBINA_DERECHA(),rep.getAT_VSUPERIOR(),true,15,true,false,1,1);
        rep.comCellFormat("AI",iReng,"AM",iReng,"$ #,##0.00");
      }
      iReng++;
    }
    vResultado.addElement(new Integer(iReng));
    vResultado.addElement(rep.getSbDatos());
    return vResultado;
  }

  public Vector repEquipoNavegacion(int iVehiculo, int iRengIni, int iEjercicio, int iMes) throws Exception{
    Vector vResultado = new Vector();
    cError = "";
    TExcel rep = new TExcel();
    rep.iniciaReporte();

    int iReng = iRengIni;
    int iLetrasObservacion = 130;

    String cTemp = "";
    String cSql = "SELECT E.iCveVehiculo, E.iCveTipoEquipoNav, E.dtAsignacion, E.dtBaja, E.cMarca, "+
                  "E.cCaracteristicas, TE.cDscEquipoNav, TE.lActivo, DR.iEjercicioReporte, "+
                  "DR.iMesReporte, DR.lEnServicio, C.lConcluido, O.cObsRegistrada "+
                  "FROM CVHEquipoNavXVeh E "+
                  "LEFT JOIN CVHTipoEquipoNav TE ON TE.iCveTipoEquipoNav = E.iCveTipoEquipoNav "+
                  "LEFT JOIN CVHDatosRepEqNav DR ON DR.iCveVehiculo = E.iCveVehiculo "+
                  "      AND DR.iCveTipoEquipoNav = E.iCveTipoEquipoNav "+
                  "      AND DR.iEjercicioReporte = " + iEjercicio +
                  "      AND DR.iMesReporte = " + iMes + " " +
                  "LEFT JOIN CVHRepEquipoNav C ON C.iCveVehiculo = DR.iCveVehiculo AND C.iEjercicioReporte = DR.iEjercicioReporte AND C.iMesReporte = DR.iMesReporte "+
                  "LEFT JOIN VEHVehiculo V ON V.iCveVehiculo = " + iVehiculo + " " +
                  "LEFT JOIN VEHTipoVehiculo TV ON TV.iCveTipoVeh = V.iCveTipoVeh "+
                  "LEFT JOIN GRLObservacion O ON O.iEjercicio = C.iEjercicioObs AND O.iCveObservacion = C.iCveObservacion "+
                  "WHERE E.iCveVehiculo = " + iVehiculo +
                  "  AND E.dtBaja IS NULL "+
                  "ORDER BY E.iCveVehiculo, TE.cDscEquipoNav ";
    Vector vDatos = new Vector();
    try {
      vDatos = super.FindByGeneric("", cSql, dataSourceName);
    }catch (Exception ex) {ex.printStackTrace();}
    if(vDatos != null && vDatos.size()>0){
      rep.comDespliegaCombinado("CONDICIONES DEL EQUIPO DE NAVEGACIÓN DEL VEHÍCULO", "A", iReng, "AM", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      iReng++;
      rep.comDespliegaCombinado("Equipo", "A", iReng, "M", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comDespliegaCombinado("Marca", "N", iReng, "V", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comDespliegaCombinado("Características", "W", iReng, "AG", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comRotar("AH", iReng, "AI", iReng, 90);
      rep.comDespliegaCombinado("En Serv.", "AH", iReng, "AH", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comDespliegaCombinado("Fuera Serv.", "AI", iReng, "AI", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comAlineaRango("AJ", iReng, "AM", iReng, rep.getAT_COMBINA_CENTRO());
      rep.comDespliegaCombinado("Fecha de Inicio de Asignación", "AJ", iReng, "AM", iReng, rep.getAT_HJUSTIFICA(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      iReng++;
      if(((TVDinRep)vDatos.get(0)).getInt("lConcluido") == 0)
        rep.comDespliegaCombinado("NO SE HA CONCLUIDO EL REGISTRO DE ESTE MES", "A", iReng, "AM", iReng, rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VSUPERIOR(), false, 0, true, false, 1, 1);
      else{
        TVDinRep dDatos;
        for(int i = 0;i < vDatos.size();i++){
          dDatos = (TVDinRep) vDatos.get(i);
          rep.comDespliegaCombinado(dDatos.getString("cDscEquipoNav"),"A",iReng,"M",iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR(),false,0,true,false,1,1);
          rep.comDespliegaCombinado(dDatos.getString("cMarca"),"N",iReng,"V",iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR(),false,0,true,false,1,1);
          rep.comDespliegaCombinado(dDatos.getString("cCaracteristicas"),"W",iReng,"AG",iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR(),false,0,true,false,1,1);
          cTemp = tFecha.getFechaDDMMMYYYY(dDatos.getDate("dtAsignacion"),"/");
          rep.comDespliegaCombinado(cTemp,"AJ",iReng,"AM",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VSUPERIOR(),false,0,true,false,1,1);
          rep.comCellFormat("AJ",iReng,"AM",iReng,"dd/Mmm/aaaa");
          if(dDatos.getInt("iEjercicioReporte") == 0){
            rep.comDespliegaCombinado("'--","AH",iReng,"AH",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            rep.comDespliegaCombinado("'--","AI",iReng,"AI",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
          } else{
            if(dDatos.getInt("lEnServicio") == 1){
              rep.comDespliegaCombinado("X","AH",iReng,"AH",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
              rep.comDespliegaCombinado("","AI",iReng,"AI",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            } else{
              rep.comDespliegaCombinado("","AH",iReng,"AH",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
              rep.comDespliegaCombinado("X","AI",iReng,"AI",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            }
          }
          iReng++;
        }
        cTemp = ( (TVDinRep) vDatos.get(0)).getString("cObsRegistrada");
        if(cTemp == null || cTemp.equalsIgnoreCase("null"))
          cTemp = "";
        if(!cTemp.equalsIgnoreCase("")){
          cTemp = "OBSERVACIONES:  " + cTemp;
          int iRenglones = 1;
          if(cTemp.length() > 0){
            iRenglones = (int) (cTemp.length() / iLetrasObservacion);
            if(cTemp.length() % iLetrasObservacion > 0)
              iRenglones++;
          }
          rep.comAlineaRango("A",iReng,"AM",iReng + iRenglones - 1,rep.getAT_COMBINA_IZQUIERDA());
          rep.comDespliegaAlineado("A",iReng,cTemp,false,rep.getAT_HJUSTIFICA(),rep.getAT_VSUPERIOR());
          iReng += iRenglones - 1;
        }
      }
    }
    vResultado.addElement(new Integer(iReng));
    vResultado.addElement(rep.getSbDatos());
    return vResultado;
  }

  public Vector repEquipoSeguridad(int iVehiculo, int iRengIni, int iEjercicio, int iMes) throws Exception{
    Vector vResultado = new Vector();
    cError = "";
    TExcel rep = new TExcel();
    rep.iniciaReporte();

    int iReng = iRengIni;
    String cTemp = "";
    String cSql = "SELECT TE.iCveTipoEquipoSeg, TE.cDscEquipoSeg, TE.lCaduca, TE.lActivo, "+
                  "DR.iCveVehiculo, DR.iEjercicioReporte, DR.iMesReporte, DR.iCantidad, "+
                  "DR.dtExpiracion, DR.lCompleto, DR.iFaltante, DR.lEnServicio, C.lConcluido, "+
                  "O.cObsRegistrada "+
                  "FROM CVHTipoEquipoSeg TE "+
                  "LEFT JOIN CVHDatosRepSeg DR ON DR.iCveTipoEquipoSeg = TE.iCveTipoEquipoSeg "+
                  "      AND DR.iCveVehiculo = " + iVehiculo +
                  "      AND DR.iEjercicioReporte = "+ iEjercicio +
                  "      AND DR.iMesReporte = " + iMes + " " +
                  "LEFT JOIN CVHRepEquipoSeg C ON C.iCveVehiculo = DR.iCveVehiculo AND C.iEjercicioReporte = DR.iEjercicioReporte AND C.iMesReporte = DR.iMesReporte "+
                  "LEFT JOIN VEHVehiculo V ON V.iCveVehiculo = " + iVehiculo + " " +
                  "LEFT JOIN VEHTipoVehiculo TV ON TV.iCveTipoVeh = V.iCveTipoVeh "+
                  "LEFT JOIN GRLObservacion O ON O.iEjercicio = C.iEjercicioObs AND O.iCveObservacion = C.iCveObservacion "+
                  "ORDER BY DR.iCveVehiculo, TE.cDscEquipoSeg";
    Vector vDatos = new Vector();
    try {
      vDatos = super.FindByGeneric("", cSql, dataSourceName);
    }catch (Exception ex) {ex.printStackTrace();}
    if(vDatos != null && vDatos.size()>0){
      rep.comDespliegaCombinado("CONDICIONES DEL EQUIPO DE SEGURIDAD DEL VEHÍCULO", "A", iReng, "AM", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      iReng++;
      rep.comDespliegaCombinado("Equipo", "A", iReng, "O", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comRotar("P", iReng, "U", iReng, 90);
      rep.comDespliegaCombinado("En Serv.", "P", iReng, "P", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comDespliegaCombinado("Fuera Serv.", "Q", iReng, "Q", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comDespliegaCombinado("Cantidad", "R", iReng, "S", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comDespliegaCombinado("Faltante", "T", iReng, "U", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comDespliegaCombinado("Fecha Cad.", "V", iReng, "Y", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comAlineaRango("Z", iReng, "AM", iReng, rep.getAT_COMBINA_IZQUIERDA());
      int iRengIniObs = iReng;
      iReng++;
      if(((TVDinRep)vDatos.get(0)).getInt("lConcluido") == 0)
        rep.comDespliega("Z", iRengIniObs, "NO SE HA CONCLUIDO EL REGISTRO DE ESTE MES");
      else{
        TVDinRep dDatos;
        for(int i=0; i < vDatos.size(); i++){
          dDatos = (TVDinRep) vDatos.get(i);
          rep.comDespliegaCombinado(dDatos.getString("cDscEquipoSeg"),"A",iReng,"O",iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR(),false,0,true,false,1,1);
          if(dDatos.getInt("iEjercicioReporte") == 0){
            rep.comDespliegaCombinado("'--","P",iReng,"P",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            rep.comDespliegaCombinado("'--","Q",iReng,"Q",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            rep.comDespliegaCombinado("'----","R",iReng,"S",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            rep.comDespliegaCombinado("'----","T",iReng,"U",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            rep.comDespliegaCombinado("'------","V",iReng,"Y",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
          }else{
            if(dDatos.getInt("lEnServicio") == 1){
              rep.comDespliegaCombinado("X","P",iReng,"P",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
              rep.comDespliegaCombinado("","Q",iReng,"Q",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            }else{
              rep.comDespliegaCombinado("","P",iReng,"P",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
              rep.comDespliegaCombinado("X","Q",iReng,"Q",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            }
            rep.comDespliegaCombinado(dDatos.getString("iCantidad"),"R",iReng,"S",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            if(dDatos.getInt("lCompleto") == 0)
              rep.comDespliegaCombinado(dDatos.getString("iFaltante"),"T",iReng,"U",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            else
              rep.comDespliegaCombinado("COMP.","T",iReng,"U",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            rep.comCellFormat("R", iReng, "U", iReng, "_-#,##0_-;-#,##0_-;_-??_----");
            if(dDatos.getInt("lCaduca") == 1){
              cTemp = dDatos.getString("dtExpiracion");
              if(cTemp == null || cTemp.equals("") || cTemp.equalsIgnoreCase("null"))
                rep.comDespliegaCombinado("NO REG.","V",iReng,"Y",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
              else
                rep.comDespliegaCombinado(tFecha.getFechaDDMMMYYYY(dDatos.getDate("dtExpiracion"),"/"),"V",iReng,"Y",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            }else
              rep.comDespliegaCombinado("NO CADUCA","V",iReng,"Y",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            rep.comCellFormat("V", iReng, "Y", iReng, "dd/Mmm/aaaa");
          }
          iReng++;
        }
        cTemp = ((TVDinRep)vDatos.get(0)).getString("cObsRegistrada");
        if(cTemp == null || cTemp.equalsIgnoreCase("null"))
          cTemp = "";
        if(!cTemp.equalsIgnoreCase(""))
          rep.comDespliega("Z", iRengIniObs, "OBSERVACIONES:  "+cTemp);
      }
      rep.comAlineaRango("Z",iRengIniObs,"AM",iReng - 1,rep.getAT_COMBINA_IZQUIERDA());
      rep.comAlineaRango("Z",iRengIniObs,"AM",iReng - 1,rep.getAT_HJUSTIFICA());
      rep.comBordeRededor("Z",iRengIniObs,"AM",iReng - 1,1,1);
    }
    vResultado.addElement(new Integer(iReng));
    vResultado.addElement(rep.getSbDatos());
    return vResultado;
  }

  public Vector repMaquinaAuxiliar(int iVehiculo, int iRengIni, int iEjercicio, int iMes) throws Exception{
    Vector vResultado = new Vector();
    cError = "";
    TExcel rep = new TExcel();
    rep.iniciaReporte();

    int iReng = iRengIni;
    int iLetrasObservacion = 130;

    String cTemp = "";
    String cSql = "SELECT E.iCveVehiculo, E.iCveTipoMaquinaAux, E.dtAsignacion, E.dtBaja, E.cMarca, "+
                  "E.cCaracteristicas, TE.cDscMaquinaAux, TE.lActivo, DR.iEjercicioReporte, "+
                  "DR.iMesReporte, DR.lEnServicio, DR.iHorasTrabajadas, C.lConcluido, O.cObsRegistrada "+
                  "FROM CVHMaqAuxXVeh E "+
                  "LEFT JOIN CVHTipoMaquinaAux TE ON TE.iCveTipoMaquinaAux = E.iCveTipoMaquinaAux "+
                  "LEFT JOIN CVHDatosRepMaqAux DR ON DR.iCveVehiculo = E.iCveVehiculo "+
                  "      AND DR.iCveTipoMaquinaAux = E.iCveTipoMaquinaAux "+
                  "      AND DR.iEjercicioReporte = " + iEjercicio +
                  "      AND DR.iMesReporte = " + iMes + " " +
                  "LEFT JOIN CVHRepMaquinaAux C ON C.iCveVehiculo = DR.iCveVehiculo AND C.iEjercicioReporte = DR.iEjercicioReporte AND C.iMesReporte = DR.iMesReporte "+
                  "LEFT JOIN VEHVehiculo V ON V.iCveVehiculo = " + iVehiculo + " " +
                  "LEFT JOIN VEHTipoVehiculo TV ON TV.iCveTipoVeh = V.iCveTipoVeh "+
                  "LEFT JOIN GRLObservacion O ON O.iEjercicio = C.iEjercicioObs AND O.iCveObservacion = C.iCveObservacion "+
                  "WHERE E.iCveVehiculo = " + iVehiculo +
                  "  AND E.dtBaja IS NULL "+
                  "ORDER BY E.iCveVehiculo, TE.cDscMaquinaAux";
    Vector vDatos = new Vector();
    try {
      vDatos = super.FindByGeneric("", cSql, dataSourceName);
    }catch (Exception ex) {ex.printStackTrace();}
    if(vDatos != null && vDatos.size()>0){
      rep.comDespliegaCombinado("CONDICIONES DE LAS MÁQUINAS AUXILIARES DEL VEHÍCULO", "A", iReng, "AM", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      iReng++;
      rep.comDespliegaCombinado("Equipo", "A", iReng, "M", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comDespliegaCombinado("Marca", "N", iReng, "V", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comDespliegaCombinado("Características", "W", iReng, "AE", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comRotar("AF", iReng, "AI", iReng, 90);
      rep.comDespliegaCombinado("Horas Trabajadas", "AF", iReng, "AG", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comAlineaRango("AF", iReng, "AG", iReng, rep.getAT_HJUSTIFICA());
      rep.comDespliegaCombinado("En Serv.", "AH", iReng, "AH", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comDespliegaCombinado("Fuera Serv.", "AI", iReng, "AI", iReng, rep.getAT_COMBINA_CENTRO(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      rep.comAlineaRango("AJ", iReng, "AM", iReng, rep.getAT_COMBINA_CENTRO());
      rep.comDespliegaCombinado("Fecha de Inicio de Asignación", "AJ", iReng, "AM", iReng, rep.getAT_HJUSTIFICA(), rep.getAT_VINFERIOR(), true, 15, true, false, 1, 1);
      iReng++;
      if(((TVDinRep)vDatos.get(0)).getInt("lConcluido") == 0)
        rep.comDespliegaCombinado("NO SE HA CONCLUIDO EL REGISTRO DE ESTE MES", "A", iReng, "AM", iReng, rep.getAT_COMBINA_IZQUIERDA(), rep.getAT_VSUPERIOR(), false, 0, true, false, 1, 1);
      else{
        TVDinRep dDatos;
        for(int i = 0;i < vDatos.size();i++){
          dDatos = (TVDinRep) vDatos.get(i);
          rep.comDespliegaCombinado(dDatos.getString("cDscMaquinaAux"),"A",iReng,"M",iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR(),false,0,true,false,1,1);
          rep.comDespliegaCombinado(dDatos.getString("cMarca"),"N",iReng,"V",iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR(),false,0,true,false,1,1);
          rep.comDespliegaCombinado(dDatos.getString("cCaracteristicas"),"W",iReng,"AE",iReng,rep.getAT_COMBINA_IZQUIERDA(),rep.getAT_VSUPERIOR(),false,0,true,false,1,1);
          cTemp = tFecha.getFechaDDMMMYYYY(dDatos.getDate("dtAsignacion"),"/");
          rep.comDespliegaCombinado(cTemp,"AJ",iReng,"AM",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VSUPERIOR(),false,0,true,false,1,1);
          rep.comCellFormat("AJ",iReng,"AM",iReng,"dd/Mmm/aaaa");
          if(dDatos.getInt("iEjercicioReporte") == 0){
            rep.comDespliegaCombinado("'----","AF",iReng,"AG",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            rep.comDespliegaCombinado("'--","AH",iReng,"AH",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            rep.comDespliegaCombinado("'--","AI",iReng,"AI",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
          } else{
            rep.comDespliegaCombinado(dDatos.getString("iHorasTrabajadas"),"AF",iReng,"AG",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            rep.comCellFormat("AF", iReng, "AG", iReng, "_-#,##0_-;-#,##0_-;_-??_----");
            if(dDatos.getInt("lEnServicio") == 1){
              rep.comDespliegaCombinado("X","AH",iReng,"AH",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
              rep.comDespliegaCombinado("","AI",iReng,"AI",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            } else{
              rep.comDespliegaCombinado("","AH",iReng,"AH",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
              rep.comDespliegaCombinado("X","AI",iReng,"AI",iReng,rep.getAT_COMBINA_CENTRO(),rep.getAT_VINFERIOR(),false,0,true,false,1,1);
            }
          }
          iReng++;
        }
        cTemp = ( (TVDinRep) vDatos.get(0)).getString("cObsRegistrada");
        if(cTemp == null || cTemp.equalsIgnoreCase("null"))
          cTemp = "";
        if(!cTemp.equalsIgnoreCase("")){
          cTemp = "OBSERVACIONES:  " + cTemp;
          int iRenglones = 1;
          if(cTemp.length() > 0){
            iRenglones = (int) (cTemp.length() / iLetrasObservacion);
            if(cTemp.length() % iLetrasObservacion > 0)
              iRenglones++;
          }
          rep.comAlineaRango("A",iReng,"AM",iReng + iRenglones - 1,rep.getAT_COMBINA_IZQUIERDA());
          rep.comDespliegaAlineado("A",iReng,cTemp,false,rep.getAT_HJUSTIFICA(),rep.getAT_VSUPERIOR());
          iReng += iRenglones - 1;
        }
      }
    }
    vResultado.addElement(new Integer(iReng));
    vResultado.addElement(rep.getSbDatos());
    return vResultado;
  }

  public Vector repDatosFirmantes(int iVehiculo, int iRengIni) throws Exception{
    Vector vResultado = new Vector();
    cError = "";
    TExcel rep = new TExcel();
    rep.iniciaReporte();

    int iReng = iRengIni;
    int iLetrasObservacion = 130;

    String cTemp = "";
    String cSql = "SELECT V.iCveVehiculo, O.cDscOficina, DXO1.cTitular AS cTitularOfic, "+
                  "DXO1.cPuestoTitular AS cPuestoTitularOfic, D.cDscDepartamento, "+
                  "DXO2.cTitular AS cTitularDepto, DXO2.cPuestoTitular AS cPuestoTitularDepto "+
                  "FROM VEHVehiculo V "+
                  "LEFT JOIN CVHAsignacion AV ON AV.iCveVehiculo = V.iCveVehiculo AND AV.dtFinAsignacion IS NULL "+
                  "LEFT JOIN GRLOficina O ON O.iCveOficina = AV.iCveOficina "+
                  "LEFT JOIN GRLDepartamento D ON D.iCveDepartamento = AV.iCveDepartamento "+
                  "LEFT JOIN GRLDeptoXOfic DXO1 ON DXO1.iCveOficina = AV.iCveOficina AND DXO1.iCveDepartamento = 0 "+
                  "LEFT JOIN GRLDeptoXOfic DXO2 ON DXO2.iCveOficina = AV.iCveOficina AND DXO2.iCveDepartamento = AV.iCveDepartamento "+
                  "WHERE V.iCveVehiculo = " + iVehiculo;
    Vector vDatos = new Vector();
    try {
      vDatos = super.FindByGeneric("", cSql, dataSourceName);
    }catch (Exception ex) {ex.printStackTrace();}
    if(vDatos != null && vDatos.size()>0){
      TVDinRep dDatos = (TVDinRep)vDatos.get(0);
      rep.comDespliegaAlineado("A", iReng, "S", iReng, "ELABORÓ / REVISÓ", false, rep.getAT_COMBINA_CENTRO(), rep.getAT_VSUPERIOR());
      rep.comDespliegaAlineado("U", iReng, "AM", iReng, "VALIDÓ / AUTORIZÓ", false, rep.getAT_COMBINA_CENTRO(), rep.getAT_VSUPERIOR());
      iReng++;
      rep.comDespliegaAlineado("A", iReng, "S", iReng, dDatos.getString("cDscDepartamento"), false, rep.getAT_COMBINA_CENTRO(), rep.getAT_VSUPERIOR());
      rep.comDespliegaAlineado("U", iReng, "AM", iReng, dDatos.getString("cDscOficina"), false, rep.getAT_COMBINA_CENTRO(), rep.getAT_VSUPERIOR());
      iReng++;
      rep.comDespliegaAlineado("A", iReng, "S", iReng, dDatos.getString("cPuestoTitularDepto"), false, rep.getAT_COMBINA_CENTRO(), rep.getAT_VSUPERIOR());
      rep.comDespliegaAlineado("U", iReng, "AM", iReng, dDatos.getString("cPuestoTitularOfic"), false, rep.getAT_COMBINA_CENTRO(), rep.getAT_VSUPERIOR());
      iReng+=4;
      rep.comDespliegaAlineado("A", iReng, "S", iReng, dDatos.getString("cTitularDepto"), false, rep.getAT_COMBINA_CENTRO(), rep.getAT_VSUPERIOR());
      rep.comDespliegaAlineado("U", iReng, "AM", iReng, dDatos.getString("cTitularOfic"), false, rep.getAT_COMBINA_CENTRO(), rep.getAT_VSUPERIOR());
    }
    vResultado.addElement(new Integer(iReng));
    vResultado.addElement(rep.getSbDatos());
    return vResultado;
  }

  public StringBuffer repMensualVehiculos(String cWhere) throws Exception{
    StringBuffer sbResultado = new StringBuffer("");
    int iReng = 10;
    String[] aDatos = cWhere.split(",");
    if(aDatos.length != 3)
      cError = "No ha proporcionado Vehículo, Ejercicio o Mes.";
    if(cError.equals("")){
      int iVehiculo  = Integer.parseInt(aDatos[0],10);
      int iEjercicio = Integer.parseInt(aDatos[1],10);
      int iMes       = Integer.parseInt(aDatos[2],10);
      TExcel rep = new TExcel();
      rep.iniciaReporte();

      //Despliega el título del reporte
      rep.comDespliega("I", 7, "REPORTE MENSUAL PARA CONTROL Y DICTÁMEN TÉCNICO OPERATIVO");
      rep.comDespliega("I", 8, "VEHÍCULOS MARÍTIMOS Y TERRESTRES (CONDICIONES Y NOVEDADES)");
      // Despliega datos del Ejercicio y mes en los datos generales del vehículo
      rep.comDespliegaAlineado("AB", iReng+1, "AF", iReng+1, "Reporte del mes:", true, rep.getAT_COMBINA_DERECHA(), rep.getAT_VSUPERIOR());
      rep.comDespliegaAlineado("AG", iReng+1, "AI", iReng+1, tFecha.getVNombresMes().get(iMes-1).toString(), false, rep.getAT_COMBINA_CENTRO(), rep.getAT_VSUPERIOR());
      rep.comDespliegaAlineado("AJ", iReng+1, "AK", iReng+1, "año:", true, rep.getAT_COMBINA_DERECHA(), rep.getAT_VSUPERIOR());
      rep.comDespliegaAlineado("AL", iReng+1, "AM", iReng+1, ""+iEjercicio, false, rep.getAT_COMBINA_CENTRO(), rep.getAT_VSUPERIOR());
      rep.comFillCells("AB", iReng+1, "AM", iReng+1, 15);
      rep.comBordeRededor("AB", iReng+1, "AM", iReng+1, 1, 1);
      //Despliega datos generales del vehículo
      Vector vGenerales = this.repMensualCarGenerales(iVehiculo,iReng);
      iReng = ( (Integer) vGenerales.get(0)).intValue();
      sbResultado.append( (StringBuffer) vGenerales.get(1));
      iReng++;
      // Despliega datos de condiciones generales
      Vector vCondiciones = this.repCondicionesGenerales(iVehiculo, iReng, iEjercicio, iMes);
      iReng = ( (Integer) vCondiciones.get(0)).intValue();
      sbResultado.append( (StringBuffer) vCondiciones.get(1));
      iReng++;
      // Despliega datos de reparaciones y servicios
      Vector vReparaciones = this.repReparacionesServicios(iVehiculo, iReng, iEjercicio, iMes);
      iReng = ( (Integer) vReparaciones.get(0)).intValue();
      sbResultado.append( (StringBuffer) vReparaciones.get(1));
      iReng++;
      // Despliega datos de estado de equipo de navegación del vehículo
      Vector vEdoNavega = this.repEquipoNavegacion(iVehiculo, iReng, iEjercicio, iMes);
      iReng = ( (Integer) vEdoNavega.get(0)).intValue();
      sbResultado.append( (StringBuffer) vEdoNavega.get(1));
      iReng++;
      // Despliega datos de estado de equipo de seguridad del vehículo
      Vector vEquipoSeg = this.repEquipoSeguridad(iVehiculo, iReng, iEjercicio, iMes);
      iReng = ( (Integer) vEquipoSeg.get(0)).intValue();
      sbResultado.append( (StringBuffer) vEquipoSeg.get(1));
      iReng++;
      // Despliega datos de estado de maquinas auxiliares del vehículo
      Vector vMaqAux = this.repMaquinaAuxiliar(iVehiculo, iReng, iEjercicio, iMes);
      iReng = ( (Integer) vMaqAux.get(0)).intValue();
      sbResultado.append( (StringBuffer) vMaqAux.get(1));
      iReng+=2;
      // Despliega datos de firmantes del reporte (el jefe del depto y oficina al que esta asignado el vehiculo
      Vector vFirma = this.repDatosFirmantes(iVehiculo, iReng);
      iReng = ( (Integer) vFirma.get(0)).intValue();
      sbResultado.append( (StringBuffer) vFirma.get(1));
      iReng++;

      sbResultado.append(rep.getSbDatos());
    }
    return sbResultado;
  }
  /**
   * Método encargado de generar el oficio correspondiente a dictaminación y Autrorizacion de Señalamiento Marítimo
   * @param cFiltro String
   * @param cFolio String
   * @param cCveOfic String
   * @param cCveDepto String
   * @throws Exception
   * @return Vector
   * @author iCaballero
   * Fecha: 11 - Oct - 2006
   */
  public Vector dictaminaAutoriza(String cFiltro,String cFolio,String cCveOfic,
                                 String cCveDepto) throws Exception{

    TWord rep = new TWord();
    TDGeneral dGeneral = new TDGeneral();
    TDObtenDatos dObten = new TDObtenDatos();
    TLetterNumberFormat dNum = new TLetterNumberFormat();
    String aFiltro[] = cFiltro.split(",");
    String aOficinaDepto[] = VParametros.getPropEspecifica("SMRemiteOficioDictaminacion").split(",");
    int iCveOficinaRemite = Integer.parseInt(aOficinaDepto[0]);
    int iCveDeptoRemite = Integer.parseInt(aOficinaDepto[1]);
    int iEjercicioDictamen = Integer.parseInt(aFiltro[0]);
    int iConsecutivoDictamen = Integer.parseInt(aFiltro[1]);
    int iCveSolicitante=0;
    Vector vRegsSol = new Vector(), vRegsSenxDic = new Vector();
    String cObs = "", cCapitan = "", cPuestoCapitan = "", cSolicitante = "", cOficina = "", cDepto = "", cDomicilio = "",
        dImportePagar = "";
    TVDinRep vDatosSol = new TVDinRep(), vDatosSenxDic = new TVDinRep();

    try{
      rep.iniciaReporte();
      StringBuffer sb = new StringBuffer();
      sb.append("SELECT ");
      sb.append("DIMPORTETOTALPAGAR, ");
      sb.append("COBSREGISTRADA, ");
      sb.append("DXO.CTITULAR AS CTITULAR, ");
      sb.append("CPUESTOTITULAR, ");
      sb.append("ICVESOLICITANTE, ");
      sb.append("ICVEDOMICILIOSOLICITANTE, ");
      sb.append("ICVEREPLEGAL, ");
      sb.append("CDSCOFICINA, ");
      sb.append("DEPTO.CDSCBREVE AS CDEPTO, ");
      sb.append("OFI.CCALLEYNO || ' COL. ' || OFI.CCOLONIA || ' C.P. ' || OFI.CCODPOSTAL || ' ' || MUN.CNOMBRE || ', ' || EF.CNOMBRE as CDOMICILIO, ");
      sb.append("SOL.iCveOficina as iCveOficina, ");
      sb.append("SOL.iCveOficina as iCveDepartamento, ");
      sb.append("DIC.iEjercicio as iEjercicio, ");
      sb.append("DIC.iConsecutivoSolicitud as iConsecutivo ");
      sb.append("FROM RGSDICTAMINAAUTORIZA DIC ");
      sb.append("JOIN GRLOBSERVACION OBS ON DIC.IEJERCICIOOBSERVACION = OBS.IEJERCICIO AND DIC.ICVEOBSERVACION = OBS.ICVEOBSERVACION ");
      sb.append("JOIN GRLDEPTOXOFIC DXO ON DIC.ICVEOFICINA = DXO.ICVEOFICINA AND DIC.ICVEDEPARTAMENTO = DXO.ICVEDEPARTAMENTO ");
      sb.append("JOIN GRLOFICINA OFI ON DXO.ICVEOFICINA = OFI.ICVEOFICINA ");
      sb.append("JOIN GRLENTIDADFED EF ON OFI.ICVEPAIS = EF.ICVEPAIS ");
      sb.append("and OFI.ICVEENTIDADFED = EF.ICVEENTIDADFED ");
      sb.append("join GRLMUNICIPIO MUN on OFI.ICVEPAIS = MUN.ICVEPAIS ");
      sb.append("and OFI.ICVEENTIDADFED = MUN.ICVEENTIDADFED ");
      sb.append("and OFI.ICVEMUNICIPIO = MUN.ICVEMUNICIPIO ");
      sb.append("JOIN GRLDEPARTAMENTO DEPTO ON DXO.ICVEDEPARTAMENTO = DEPTO.ICVEDEPARTAMENTO ");
      sb.append("JOIN TRAREGSOLICITUD SOL ON DIC.IEJERCICIOSOLTRAMITE = SOL.IEJERCICIO AND DIC.INUMSOLTRAMITE = SOL.INUMSOLICITUD ");
      sb.append("WHERE DIC.IEJERCICIO = "+iEjercicioDictamen);
      sb.append(" AND DIC.ICONSECUTIVOSOLICITUD = "+iConsecutivoDictamen);

      vRegsSol = super.FindByGeneric("",sb.toString(), dataSourceName);
      if(vRegsSol.size() > 0){
        vDatosSol = (TVDinRep) vRegsSol.get(0);
        dImportePagar = vDatosSol.getString("DIMPORTETOTALPAGAR");
        cObs = vDatosSol.getString("COBSREGISTRADA");
        cCapitan = vDatosSol.getString("CTITULAR");
        cPuestoCapitan = vDatosSol.getString("CPUESTOTITULAR");
        iCveSolicitante = vDatosSol.getInt("ICVESOLICITANTE");
        cOficina = vDatosSol.getString("CDSCOFICINA");
        cDepto = vDatosSol.getString("CDEPTO");
        cDomicilio = vDatosSol.getString("CDOMICILIO");
      }

      dObten.dPersona.setPersona(iCveSolicitante,0);
      cSolicitante = dObten.dPersona.getNomCompleto();

      dObten.dFolio.setDatosFolio(cFolio);

      rep.comRemplaza("[cNumOficio]",cFolio);
      rep.comRemplaza("[cNumOficialiaPartes]", dObten.dFolio.getNumOficialiaPartes());
      rep.comRemplaza("[cCapitan]",cCapitan);
      rep.comRemplaza("[cOficinaCapitan]",cPuestoCapitan);
      rep.comRemplaza("[cDomicilio]",cDomicilio);
      rep.comRemplaza("[cSolicitante]",cSolicitante);
      rep.comRemplaza("[cObservacionesDictamen]",cObs);
      rep.comRemplaza("[cCapitaniaOficina]",cOficina);
      rep.comRemplaza("[cJefeDepto]",cDepto);
      rep.comRemplaza("[cCantidadNum]","$" + dImportePagar);
      String[] aImportePagar = dImportePagar.replace('.',',').split(",");
      rep.comRemplaza("[cCantidadLetras]",dNum.getCantidad(Long.parseLong(aImportePagar[0])).toUpperCase() + " PESOS 00/100 M.N.");
      rep.comRemplaza("[cEjercicioConsecutivo]",""+ iConsecutivoDictamen + "/" + iEjercicioDictamen);

      StringBuffer sb2 = new StringBuffer();
      sb2.append("SELECT ");
      sb2.append("CNOMSENAL, ");
      sb2.append("COBSREGISTRADA ");
      sb2.append("FROM RGSSENALXDICTAMEN SXD ");
      sb2.append("join GRLOBSERVACION OBS ON SXD.IEJERCICIOOBSERVACION = OBS.IEJERCICIO ");
      sb2.append("AND SXD.ICVEOBSERVACION = OBS.ICVEOBSERVACION ");
      sb2.append("JOIN RGSSENAL SEN ON SXD.ICVESENAL = SEN.ICVESENAL ");
      sb2.append("WHERE SXD.IEJERCICIO = "+iEjercicioDictamen);
      sb2.append(" AND SXD.ICONSECUTIVOSOLICITUD = "+iConsecutivoDictamen);

      vRegsSenxDic = super.FindByGeneric("",sb2.toString(), dataSourceName);

      rep.comEligeTabla("cSenalMaritima");
      for(int i = 0; i < vRegsSenxDic.size(); i++){
        vDatosSenxDic = (TVDinRep) vRegsSenxDic.get(i);
        rep.comDespliegaCelda(vDatosSenxDic.getString("CNOMSENAL"));
        rep.comDespliegaCelda(vDatosSenxDic.getString("COBSREGISTRADA"));
        rep.comDespliegaCelda(" ");
      }

      String cTotalSen = (vRegsSenxDic.size() > 1)?vRegsSenxDic.size() + " señales":vRegsSenxDic.size() + " señal";
      rep.comRemplaza("[cTotalSenales]",cTotalSen);
      dGeneral.etiquetasEncabezado(rep, cFolio, 0,0,"","","","");
      dGeneral.etiquetasPie(rep,iCveOficinaRemite,iCveDeptoRemite);


    }catch(Exception e){
      e.printStackTrace();
      cMensaje += e.getMessage();
    }
    if(!cMensaje.equals(""))
      throw new Exception(cMensaje);
    return rep.getVectorDatos(true);
  }

}
