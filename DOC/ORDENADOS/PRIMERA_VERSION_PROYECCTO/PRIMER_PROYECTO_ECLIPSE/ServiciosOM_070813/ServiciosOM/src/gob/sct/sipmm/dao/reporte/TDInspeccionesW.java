package gob.sct.sipmm.dao.reporte;

import com.micper.util.*;
import com.micper.ingsw.TParametro;
import com.micper.sql.*;
import java.util.*;
import java.sql.SQLException;
import com.micper.seguridad.vo.TVDinRep;
import gob.sct.sipmm.dao.reporte.TDGeneral;

/**
 * <p>Title: TDOficiosConcesiones.java</p>
 * <p>Description: DAO de oficios generales empleados en el sistema</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author LSC. Arturo López Peña
 * @version 1.0
 */

public class TDInspeccionesW extends DAOBase{
  private TParametro VParametros = new TParametro("44");
  private String dataSourceName = VParametros.getPropEspecifica("ConDBModulo");
  public TDInspeccionesW(){
  }
public StringBuffer fAprovacionMaritima(String cQuery){
  Vector vcCuerpo = new Vector();
  Vector vcCopiasPara = new Vector();
  Vector vRegs = new Vector();
  Vector vRegsSerPor = new Vector();
  Vector vRegsSerCon = new Vector();
  TWord rep = new TWord();
  TFechas tFecha = new TFechas();
  int INUMDISPOSITIVO=0,IFOLIO;
  String CNORMA="",CGPODISP="",CDSCTIPODISPOSITIVO= "",CSOL="",cNumTitulo="",CCALLE="",CNUMEXTERIOR="",CCOLONIA="",CRFC="";
  String CCODPOSTAL="",CPAIS="",CENTF="",CDSCMODELODISPOSITIVO="",CDSCMARCADISPOSITIVO="",cMunicipio="", cLocalidad="";
  String DTAPROBACION="",DTVENCIMIENTO="",cFecAprovacion="",CABREVIATURA,CFOLIO="",IEJERSOLICITUD="",INUMSOLICITUD="";
  String CCATEGORIA="",CCARACTERISTICA="";
  int iDiaAprobacion=0,iAnioAprobacion=0,iDiaVencimiento=0,iAnioVencimiento=0;
  String cMesAprobacion="",cMesVencimiento="";
  String[] cParametros = cQuery.split(",");
  String cDirector = "";
  Vector vcDirector = new Vector();
  TVDinRep vDirector = new TVDinRep();


  String cQuery1 =
                  "SELECT D.ICVEPERSONA,D.INUMDISPOSITIVO,d.INUMSOLICITUD,d.iEjercicio, " +
                  "       GD.CDSCGRUPODISPOSITIVO,TD.CDSCTIPODISPOSITIVO,MAR.CDSCMARCADISPOSITIVO,MD.CDSCMODELODISPOSITIVO, " +
                  "       P.CNOMRAZONSOCIAL||' '|| P.CAPPATERNO||' '|| P.CAPMATERNO AS CSOL,p.CRFC, " +
                  "       DOM.CCALLE,DOM.CNUMEXTERIOR,DOM.CCOLONIA, DOM.CCODPOSTAL,PAIS.CNOMBRE AS CPAIS, EF.CNOMBRE AS CENTF, " +
                  "       D.DTAPROBACION, D.DTVENCIMIENTO, td.CABREVIATURA, td.IFOLIO,TD.CNORMA, MU.CNOMBRE AS CMUNICIPIO, LO.CNOMBRE AS CLOCALIDAD, " +
                  "       D.ICVECATEGORIA,CAT.CDSCCATEGORIA,D.ICVECARACTERISTICA,CAR.CDSCCARACTERISTICA, "+
                  "       D.IFOLIOASIGNADO "+
                  "FROM INSDISPOSITIVOS D " +
                  "Join TRAREGSOLICITUD SOL ON SOL.IEJERCICIO = D.IEJERCICIOSOL " +
                  "  AND SOL.INUMSOLICITUD = D.INUMSOLICITUD " +
                  "JOIN INSTIPODISPOSITIVO TD ON TD.ICVETIPODISPOSITIVO =  D.ICVETIPODISPOSITIVO " +
                  "JOIN INSMARCADISPOSITIVO MAR ON MAR.ICVEMARCADISPOSITIVO = D.ICVEMARCADISPOSITIVO " +
                  "  AND MAR.ICVETIPODISPOSITIVO=D.ICVETIPODISPOSITIVO " +
                  "JOIN INSMODELODISPOSITIVO MD ON MD.ICVEMARCADISPOSITIVO = D.ICVEMARCADISPOSITIVO " +
                  "  AND MD.ICVEMODELODISPOSITIVO = D.ICVEMODELODISPOSITIVO " +
                  "  AND MD.ICVETIPODISPOSITIVO=D.ICVETIPODISPOSITIVO " +
                  "LEFT JOIN GRLPERSONA P ON P.ICVEPERSONA = D.ICVEPERSONA " +
                  "LEFT JOIN GRLDOMICILIO DOM ON P.ICVEPERSONA = DOM.ICVEPERSONA " +
                  "  AND DOM.ICVEDOMICILIO = SOL.ICVEDOMICILIOSOLICITANTE " +
                  "LEFT JOIN GRLPAIS PAIS ON PAIS.ICVEPAIS = DOM.ICVEPAIS " +
                  "LEFT JOIN GRLENTIDADFED EF ON EF.ICVEPAIS = DOM.ICVEPAIS AND EF.ICVEENTIDADFED = DOM.ICVEENTIDADFED " +
                  "JOIN INSGRUPODISPOSITIVO GD ON GD.ICVEGRUPODISPOSITIVO = TD.ICVEGRUPODISPOSITIVO " +
                  "LEFT JOIN GRLMUNICIPIO MU ON MU.ICVEPAIS=DOM.ICVEPAIS AND MU.ICVEENTIDADFED=DOM.ICVEENTIDADFED AND MU.ICVEMUNICIPIO=DOM.ICVEMUNICIPIO " +
                  "LEFT JOIN GRLLOCALIDAD LO ON LO.ICVEPAIS=DOM.ICVEPAIS AND LO.ICVEENTIDADFED=DOM.ICVEENTIDADFED AND LO.ICVEMUNICIPIO=DOM.ICVEMUNICIPIO AND LO.ICVELOCALIDAD=DOM.ICVELOCALIDAD "+
                  "LEFT JOIN INSDISPCATEGORIA CAT ON CAT.ICVECATEGORIA = D.ICVECATEGORIA "+
                  "LEFT JOIN INSDISPCARACTERISTICA CAR ON CAR.ICVECARACTERISTICA = D.ICVECARACTERISTICA "+
                  "Where d.iEjercicio = " + cParametros[0]+" AND D.INUMDISPOSITIVO = "+cParametros[1];
  System.out.println("*****    \n\n"+cQuery1+"\n\n");
  try{
    vRegs = super.FindByGeneric("",cQuery1, dataSourceName);
  }catch(SQLException ex){
    cMensaje = ex.getMessage();
  }catch(Exception ex2){
    ex2.printStackTrace();
  }

  rep.iniciaReporte();
  if (vRegs.size() > 0){
    TVDinRep vDatos       = (TVDinRep) vRegs.get(0);
    INUMDISPOSITIVO       = vDatos.getInt("INUMDISPOSITIVO");
    INUMSOLICITUD         = ""+vDatos.getInt("INUMSOLICITUD");
    IEJERSOLICITUD        = ""+vDatos.getInt("iEjercicio");
    CSOL                  = vDatos.getString("CSOL").equals("null")?"":vDatos.getString("CSOL");
    CRFC                  = vDatos.getString("CRFC").equals("null")?"":vDatos.getString("CRFC");
    CGPODISP              = vDatos.getString("CDSCGRUPODISPOSITIVO").equals("null")?"":vDatos.getString("CDSCGRUPODISPOSITIVO");
    CDSCTIPODISPOSITIVO   = vDatos.getString("CDSCTIPODISPOSITIVO").equals("null")?"":vDatos.getString("CDSCTIPODISPOSITIVO");
    CDSCMARCADISPOSITIVO  = vDatos.getString("CDSCMARCADISPOSITIVO").equals("null")?"":vDatos.getString("CDSCMARCADISPOSITIVO");
    CDSCMODELODISPOSITIVO = vDatos.getString("CDSCMODELODISPOSITIVO").equals("null")?"":vDatos.getString("CDSCMODELODISPOSITIVO");
    CSOL                  = vDatos.getString("CSOL").equals("null")?"":vDatos.getString("CSOL");
    cNumTitulo            = vDatos.getString("cNumTitulo").equals("null")?"":vDatos.getString("cNumTitulo");
    CCALLE                = vDatos.getString("CCALLE").equals("null")?"":vDatos.getString("CCALLE");
    CNUMEXTERIOR          = vDatos.getString("CNUMEXTERIOR").equals("null")?"":vDatos.getString("CNUMEXTERIOR");
    CCOLONIA              = vDatos.getString("CCOLONIA").equals("null")?"":vDatos.getString("CCOLONIA");
    CCODPOSTAL            = vDatos.getString("CCODPOSTAL").equals("null")?"":vDatos.getString("CCODPOSTAL");
    CPAIS                 = vDatos.getString("CPAIS").equals("null")?"":vDatos.getString("CPAIS");
    CENTF                 = vDatos.getString("CENTF").equals("null")?"":vDatos.getString("CENTF");
    IFOLIO                = vDatos.getInt("IFOLIOASIGNADO");
    cMunicipio            = vDatos.getString("CMUNICIPIO").equals("null")?"":vDatos.getString("CMUNICIPIO");
    cLocalidad            = vDatos.getString("CLOCALIDAD").equals("null")?"":vDatos.getString("CLOCALIDAD");
    CCATEGORIA            = vDatos.getString("CDSCCATEGORIA").equals("null")?"":vDatos.getString("CDSCCATEGORIA");
    CCARACTERISTICA       = vDatos.getString("CDSCCARACTERISTICA").equals("null")?"":vDatos.getString("CDSCCARACTERISTICA");
    if(vDatos.getInt("ICVECATEGORIA")==0) CCATEGORIA ="";
    if(vDatos.getInt("ICVECARACTERISTICA")==0) CCARACTERISTICA ="";

    if(IFOLIO>=0&&IFOLIO<10) CFOLIO = "00"+IFOLIO;
    if(IFOLIO>9&&IFOLIO<100) CFOLIO = "0"+IFOLIO;
    if(IFOLIO>99)           CFOLIO = ""+IFOLIO;
    CABREVIATURA          = vDatos.getString("CABREVIATURA");
    DTAPROBACION          = tFecha.getFechaTexto(vDatos.getDate("DTAPROBACION"),"").toUpperCase();
    iDiaAprobacion        = tFecha.getIntDay(vDatos.getDate("DTAPROBACION"));
    iAnioAprobacion       = tFecha.getIntYear(vDatos.getDate("DTAPROBACION"));
    cMesAprobacion        = tFecha.getMonthName(vDatos.getDate("DTVENCIMIENTO")).toUpperCase();
    CNORMA                = vDatos.getString("CNORMA");
    try{
      vcDirector = FindByGeneric("","SELECT CTITULAR FROM GRLDEPTOXOFIC WHERE ICVEOFICINA = 1 AND ICVEDEPARTAMENTO = "+VParametros.getPropEspecifica("DireccionGeneralMM"),dataSourceName);
    }catch (Exception e){
      e.printStackTrace();
    }
    if(vcDirector.size()>0){
      vDirector = (TVDinRep) vcDirector.get(0);
      cDirector = vDirector.getString("CTITULAR");
    }
    String cSeparador = "/";

    DTVENCIMIENTO        = tFecha.getFechaTexto(vDatos.getDate("DTVENCIMIENTO"),"").toUpperCase();
    cMesVencimiento      = tFecha.getMonthName(vDatos.getDate("DTVENCIMIENTO")).toUpperCase();
    iDiaVencimiento      = tFecha.getIntDay(vDatos.getDate("DTVENCIMIENTO"));
    iAnioVencimiento      = tFecha.getIntYear(vDatos.getDate("DTVENCIMIENTO"));

    if(CSOL!="")                   rep.comRemplaza("[cNombreSol]",CSOL);                         else rep.comRemplaza("[cNombreSol]","");
    if(CRFC!="")                   rep.comRemplaza("[cRFC]",CRFC);                               else rep.comRemplaza("[cRFC]","");
    if(CGPODISP!="")               rep.comRemplaza("[cGpoDisp]",CGPODISP);                       else rep.comRemplaza("[cGpoDisp]","");
    if(CDSCTIPODISPOSITIVO!="")    rep.comRemplaza("[cTipo]",CDSCTIPODISPOSITIVO);               else rep.comRemplaza("[cTipo]","");
    if(CDSCMARCADISPOSITIVO!="")   rep.comRemplaza("[cMarca]",CDSCMARCADISPOSITIVO);             else rep.comRemplaza("[cMarca]","");
    if(CDSCMODELODISPOSITIVO!="")  rep.comRemplaza("[cModelo]",CDSCMODELODISPOSITIVO);           else rep.comRemplaza("[cModelo]","");
    if(CDSCTIPODISPOSITIVO!="")    rep.comRemplaza("[cNorma]",CNORMA);  else rep.comRemplaza("[Norma]","");
    if(CCALLE!="")                 rep.comRemplaza("[cDireccion]",CCALLE+" Nº."+CNUMEXTERIOR);     else rep.comRemplaza("[cDireccion]","");
    if(CCOLONIA!="")               rep.comRemplaza("[cColonia]",CCOLONIA);                       else rep.comRemplaza("[cColonia]","");
    if(CCODPOSTAL!="")             rep.comRemplaza("[cCP]",CCODPOSTAL);                          else rep.comRemplaza("[cCP]","");
    if(CENTF!="")                  rep.comRemplaza("[cEntidadF]",CENTF);                         else rep.comRemplaza("[cEntidadF]","");
    if(DTVENCIMIENTO!="")          rep.comRemplaza("[cFechaVig]",DTVENCIMIENTO);                 else rep.comRemplaza("[cFechaVig]","");
    if(CDSCTIPODISPOSITIVO!="")    rep.comRemplaza("[cEntidadF]",CDSCTIPODISPOSITIVO);           else rep.comRemplaza("[cEntidadF]","");
    if(DTAPROBACION!="")           rep.comRemplaza("[cFechaExp]",DTAPROBACION);                  else rep.comRemplaza("[cFechaExp]","");
                                   rep.comRemplaza("[cInfraescrito]",cDirector);
    if(CABREVIATURA!="")           rep.comRemplaza("[cAbrev]",CABREVIATURA);                     else rep.comRemplaza("[cClave2]","");
    if(""+IFOLIO!="")              rep.comRemplaza("[iConsCert]",""+CFOLIO);                     else rep.comRemplaza("[cClave2]","");
    if(cMunicipio!="")             rep.comRemplaza("[cMunicipio]",cMunicipio);                   else rep.comRemplaza("[cMunicipio]","");
    if(cLocalidad!="")             rep.comRemplaza("[cLocalidad]",cLocalidad);                   else rep.comRemplaza("[cLocalidad]","");
    if(DTVENCIMIENTO!="")          rep.comRemplaza("[diaVencimiento]",""+iDiaVencimiento);       else rep.comRemplaza("[diaVencimiento]","");
    if(DTVENCIMIENTO!="")          rep.comRemplaza("[mesVencimiento]",cMesVencimiento);          else rep.comRemplaza("[mesVencimiento]","");
    if(DTVENCIMIENTO!="")          rep.comRemplaza("[anioVencimiento]",""+iAnioVencimiento);     else rep.comRemplaza("[anioVencimiento]","");
    if(DTAPROBACION!="")           rep.comRemplaza("[diaAprobacion]",""+iDiaAprobacion);         else rep.comRemplaza("[diaAprobacion]","");
    if(DTAPROBACION!="")           rep.comRemplaza("[mesAprobacion]",cMesAprobacion);            else rep.comRemplaza("[mesAprobacion]","");
    if(DTAPROBACION!="")           rep.comRemplaza("[anioAprobacion]",""+iAnioAprobacion);       else rep.comRemplaza("[anioAprobacion]","");
    if(INUMSOLICITUD!="")          rep.comRemplaza("[INUMSOLICITUD]",""+INUMSOLICITUD);          else rep.comRemplaza("[INUMSOLICITUD]","");
    if(IEJERSOLICITUD!="")         rep.comRemplaza("[IEJERSOLICITUD]",""+IEJERSOLICITUD);        else rep.comRemplaza("[IEJERSOLICITUD]","");

    if(CCARACTERISTICA!="")        rep.comRemplaza("[cCaracterisitica]",""+CCARACTERISTICA);     else rep.comRemplaza("[cCaracterisitica]","");
    if(CCATEGORIA!="")             rep.comRemplaza("[cCategoria]",""+CCATEGORIA);                else rep.comRemplaza("[cCategoria]","");
    //if(cFolio!="")                 rep.comRemplaza("[cFolio]",""+CCATEGORIA);                    else rep.comRemplaza("[cFolio]","");

    rep.comRemplaza("[cClave1]","DGMM");
    rep.comRemplaza("[cQuery]",cQuery1);
  } else {
    rep.comRemplaza("[cNombreSol]","");
    rep.comRemplaza("[cGpoDisp]","");
    rep.comRemplaza("[cMarca]","");
    rep.comRemplaza("[cAbrev]","");
    rep.comRemplaza("[iConsCert]","");
    rep.comRemplaza("[cTipo]","");
    rep.comRemplaza("[cRFC]","");
    rep.comRemplaza("[cModelo]","");
    rep.comRemplaza("[cNorma]","");
    rep.comRemplaza("[cDireccion]","");
    rep.comRemplaza("[cColonia]","Col: "+"");
    rep.comRemplaza("[cCP]","C.P.: "+"");
    rep.comRemplaza("[cEntidadF]","");
    rep.comRemplaza("[cFechaVig]","");
    rep.comRemplaza("[cEntidadF]","");
    rep.comRemplaza("[cFechaExp]","");
    rep.comRemplaza("[cInfraescrito]","");
    rep.comRemplaza("[cMunicipio]","");
    rep.comRemplaza("[cLocalidad]","");
    rep.comRemplaza("[cQuery]",cQuery1);

  }
  // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
  return rep.getEtiquetas(true);

 }

 public StringBuffer fReportGenInspeccion(String cQuery){
   Vector vcData = new Vector();
   TWord rep = new TWord();
   TFechas tFecha = new TFechas();
   String[] cParametros = cQuery.split(",");
   java.sql.Date dtFechaApr=null,dtFechaVal=null;

   try{
     vcData = FindByGeneric("",
                                  "SELECT " +
                                  "V.ICVEVEHICULO, " +
                                  "VE.CNOMEMBARCACION, " +
                                  "V.CMATRICULA, " +
                                  "TE.CDSCTIPOEMBARCACION, " +
                                  "VE.CNUMOMI, " +
                                  "(MSD.CGRUPOSENALFIJO ||'-'|| MSD.CGRUPOSENALCONSEC ) As cSenialDist, " +
                                  "VE.DTCONSTRUCCION, " +
                                  "VE.CLUGARCONSTRUCCION, " +
                                  "VE.DESLORA, " +
                                  "VE.DMANGA, " +
                                  "VE.DPUNTAL, " +
                                  "VE.DARQUEOBRUTO, " +
                                  "VE.DARQUEONETO, " +
                                  "PU.CDSCPUERTO, " +
                                  "CE.ICONSECUTIVOCERTIFEXP, " +
                                  "CE.DTEXPEDICIONCERT, " +
                                  "CE.DTINIVIGENCIA, " +
                                  "CE.DTFINVIGENCIA, " +
                                  "TC.CDSCCERTIFICADO, "+
                                  "UME.CABREVIATURA AS UESLORA, " +
                                  "UMM.CABREVIATURA AS UMANGA, " +
                                  "UMP.CABREVIATURA AS UPUNTAL, " +
                                  "UMAB.CABREVIATURA AS UARQUEOBRUTO, " +
                                  "UMAN.CABREVIATURA AS UARQUEONETO, " +
                                  "PU.CDSCPUERTO, " +
                                  "PER.CNOMRAZONSOCIAL || ' ' || PER.CAPPATERNO||' '|| PER.CAPMATERNO AS CPROPIETARIO, " +
                                  "PER.CRFC AS CPROPRFC, " +//
                                  "DOM.CCALLE||' #'|| DOM.CNUMEXTERIOR AS CPROPDOM, " +
                                  "'NUM. INT. '|| DOM.CNUMINTERIOR AS CPROPINTERIOR, " +
                                  "'COL. '|| DOM.CCOLONIA AS CPROPCOLONIA, " +
                                  "'C.P. '|| DOM.CCODPOSTAL AS CPROPCP, " +
                                  "PAIS.CNOMBRE AS CPROPPAIS, " +
                                  "EFED.CNOMBRE AS CPROPENTFED, " +
                                  "MUN.CNOMBRE AS CPROPMUN " +
                                  "FROM VEHEMBARCACION VE JOIN INSCERTIFEXPEDIDOS CE ON CE.ICVEVEHICULO = VE.ICVEVEHICULO " +
                                  "AND CE.ICONSECUTIVOCERTEXP = " + cParametros[0] +
                                  " JOIN INSTIPOCERTIF TC ON CE.ITIPOCERTIFICADO = TC.ITIPOCERTIFICADO AND CE.ICVEGRUPOCERTIF = TC.ICVEGRUPOCERTIF "+
                                  "JOIN VEHVEHICULO V ON V.ICVEVEHICULO = VE.ICVEVEHICULO " +
                                  "LEFT JOIN MYRMATRICULA MA ON MA.ICVEEMBARCACION = VE.ICVEVEHICULO " +
                                  "LEFT JOIN GRLPUERTO PU ON MA.ICVEPUERTOMAT = PU.ICVEPUERTO " +
                                  "Left JOIN VEHSENALDISTINTIVA SD ON SD.ICVEVEHICULO = vE.ICVEVEHICULO " +
                                  "Left JOIN MYRSENALDISTINT MSD ON MSD.ICONSECUTIVSENAL = SD.ICVESENAL " +
                                  "lEFT JOIN VEHUNIDADMEDIDA UME ON UME.ICVEUNIDADMEDIDA = VE.IUNIMEDESLORA " +
                                  "lEFT JOIN VEHUNIDADMEDIDA UMM ON UMM.ICVEUNIDADMEDIDA = VE.IUNIMEDMANGA " +
                                  "lEFT JOIN VEHUNIDADMEDIDA UMP ON UMP.ICVEUNIDADMEDIDA = VE.IUNIMEDPUNTAL " +
                                  "lEFT JOIN VEHUNIDADMEDIDA UMAB ON UMAB.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEOBRUTO " +
                                  "lEFT JOIN VEHUNIDADMEDIDA UMAN ON UMAN.ICVEUNIDADMEDIDA = VE.IUNIMEDARQUEONETO " +
                                  "LEFT JOIN VEHTIPOEMBARCACION TE ON VE.ICVETIPOEMBARCACION = TE.ICVETIPOEMBARCACION " +
                                  "LEFT JOIN GRLPERSONA PER ON VE.ICVEPROPIETARIO = PER.ICVEPERSONA " +
                                  "LEFT JOIN GRLDOMICILIO DOM ON PER.ICVEPERSONA = DOM.ICVEPERSONA AND LPREDETERMINADO = 1 " +
                                  "LEFT JOIN GRLPAIS PAIS ON DOM.ICVEPAIS = PAIS.ICVEPAIS " +
                                  "LEFT JOIN GRLENTIDADFED EFED ON DOM.ICVEENTIDADFED = EFED.ICVEENTIDADFED AND DOM.ICVEPAIS = EFED.ICVEPAIS " +
                                  "LEFT JOIN GRLMUNICIPIO MUN ON DOM.ICVEPAIS = MUN.ICVEPAIS AND DOM.ICVEENTIDADFED = MUN.ICVEENTIDADFED AND DOM.ICVEMUNICIPIO = MUN.ICVEMUNICIPIO " +
                                  "LEFT JOIN MYRMATRICULA MAT ON VE.ICVEVEHICULO = MAT.ICVEEMBARCACION " +
                                  "where VE.iCveVehiculo = "+cParametros[1],dataSourceName);

   }catch(SQLException ex){
     cMensaje = ex.getMessage();
   }catch(Exception ex2){
     ex2.printStackTrace();
   }

   rep.iniciaReporte();

   if (vcData.size() > 0){
     TVDinRep vDatos       = (TVDinRep) vcData.get(0);

     if(!vDatos.getString("CNOMEMBARCACION").equals(""))
       rep.comRemplaza("[NombreEmbarcacion]",vDatos.getString("CNOMEMBARCACION"));
       else rep.comRemplaza("[NombreEmbarcacion]","_______________");
     if(!vDatos.getString("CMATRICULA").equals(""))
       rep.comRemplaza("[Matricula]",vDatos.getString("CMATRICULA"));
       else rep.comRemplaza("[Matricula]","_______________");
     if(!vDatos.getString("CDSCTIPOEMBARCACION").equals(""))
       rep.comRemplaza("[TipoEmbarcacion]",vDatos.getString("CDSCTIPOEMBARCACION"));
       else rep.comRemplaza("[TipoEmbarcacion]","_______________");
     if(!vDatos.getString("CNUMOMI").equals(""))
       rep.comRemplaza("[NumeroOMI]",vDatos.getString("CNUMOMI"));
       else rep.comRemplaza("[NumeroOMI]","_______________");
     if(!vDatos.getString("cSenialDist").equals(""))
       rep.comRemplaza("[LetrasDistintivas]",vDatos.getString("cSenialDist"));
       else rep.comRemplaza("[LetrasDistintivas]","_______________");
     if(!vDatos.getString("DTCONSTRUCCION").equals(""))
       rep.comRemplaza("[FechaContruccion]",tFecha.getDateSPN(vDatos.getDate("DTCONSTRUCCION")).toLowerCase());
       else rep.comRemplaza("[FechaContruccion]","_______________");
     if(!vDatos.getString("CLUGARCONSTRUCCION").equals(""))
       rep.comRemplaza("[LugarConstruccion]",vDatos.getString("CLUGARCONSTRUCCION"));
       else rep.comRemplaza("[LugarConstruccion]","_______________");
     if(!vDatos.getString("DESLORA").equals(""))
       rep.comRemplaza("[Eslora]",vDatos.getString("DESLORA"));
       else rep.comRemplaza("[Eslora]","_______________");
     if(!vDatos.getString("DMANGA").equals(""))
       rep.comRemplaza("[Manga]",vDatos.getString("DMANGA"));
       else rep.comRemplaza("[Manga]","_______________");
     if(!vDatos.getString("DPUNTAL").equals(""))
       rep.comRemplaza("[Puntal]",vDatos.getString("DPUNTAL"));
       else rep.comRemplaza("[Puntal]","_______________");
     if(!vDatos.getString("DARQUEOBRUTO").equals(""))
       rep.comRemplaza("[ArqueoBruto]",vDatos.getString("DARQUEOBRUTO"));
       else rep.comRemplaza("[ArqueoBruto]","_______________");
     if(!vDatos.getString("DARQUEONETO").equals(""))
       rep.comRemplaza("[ArqueoNeto]",vDatos.getString("DARQUEONETO"));
       else rep.comRemplaza("[ArqueoNeto]","_______________");
     if(!vDatos.getString("CDSCPUERTO").equals(""))
       rep.comRemplaza("[PuertoMatricula]",vDatos.getString("CDSCPUERTO"));
       else rep.comRemplaza("[PuertoMatricula]","_______________");


 if(vDatos.getString("ICONSECUTIVOCERTIFEXP").equals(""))
   rep.comRemplaza("[FolioCertificado]",vDatos.getString("ICONSECUTIVOCERTIFEXP"));
   else rep.comRemplaza("[FolioCertificado]","_______________");
 if(vDatos.getString("DTEXPEDICIONCERT").equals(""))
   rep.comRemplaza("[FechaExpedicionCertif]",vDatos.getString("DTEXPEDICIONCERT"));
   else rep.comRemplaza("[FechaExpedicionCertif]","_______________");
 if(vDatos.getString("DTINIVIGENCIA").equals(""))
   rep.comRemplaza("[FechaInicioVigencia]",vDatos.getString("DTINIVIGENCIA"));
   else rep.comRemplaza("[FechaInicioVigencia]","_______________");
 if(vDatos.getString("DTFINVIGENCIA").equals(""))
   rep.comRemplaza("[FechaFinVigencia]",vDatos.getString("DTFINVIGENCIA"));
   else rep.comRemplaza("[FechaFinVigencia]","_______________");
 if(vDatos.getString("CDSCCERTIFICADO").equals(""))
   rep.comRemplaza("[TipoCertificado]",vDatos.getString("CDSCCERTIFICADO"));
 else rep.comRemplaza("[TipoCertificado]","_______________");


if(vDatos.getString("CPROPIETARIO").equals(""))
 rep.comRemplaza("[Propietario]",vDatos.getString("CPROPIETARIO"));
 else rep.comRemplaza("[Propietario]","_______________");
if(vDatos.getString("CPROPRFC").equals(""))
 rep.comRemplaza("[RFCPropietario]",vDatos.getString("CPROPRFC"));
 else rep.comRemplaza("[RFCPropietario]","_______________");
if(vDatos.getString("CPROPDOM").equals(""))
 rep.comRemplaza("[DomicilioPropietario]",vDatos.getString("CPROPDOM"));
 else rep.comRemplaza("[DomicilioPropietario]","_______________");
if(vDatos.getString("NumInteriorPropietario").equals(""))
 rep.comRemplaza("[NumInteriorPropietario]",vDatos.getString("CPROPINTERIOR"));
 else rep.comRemplaza("[NumInteriorPropietario]","");
if(vDatos.getString("CPROPCOLONIA").equals(""))
 rep.comRemplaza("[ColoniaPropietario]",vDatos.getString("CPROPCOLONIA"));
 else rep.comRemplaza("[ColoniaPropietario]","");
if(vDatos.getString("CPROPCP").equals(""))
 rep.comRemplaza("[CodPostalPropietario]",vDatos.getString("CPROPCP"));
 else rep.comRemplaza("[CodPostalPropietario]","");





   } else {
     rep.comRemplaza("[_______________]","");

   }
   // Ejemplo de llamado 2 empleado para oficina y depto fijos destino externo
   return rep.getEtiquetas(true);

  }

}
