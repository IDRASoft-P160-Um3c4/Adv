<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%@ page import="com.micper.util.*" %>
<%
/**
 * <p>Title: pgMYRRPMN.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad MYRRPMN</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ocastrejon <DD> ABarrientos
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TVDinRep vDinRep2;
  TVDinRep vDinRep3;
  TDMYRRPMN dMYRRPMN = new TDMYRRPMN();
  TDMYREmbarcacion DMYREmbarcacion = new TDMYREmbarcacion();
  TDMYREmpresa DMYREmpresa = new TDMYREmpresa();
  TFechas Fecha = new TFechas("44");
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveOficina,iEjercicio,iNumSolicitud,dtRegistro,dtIngreso,iCvePersona,cSintesis,"+
                                "iCveRamo,iCveSeccion,iCveTipoAsiento,cTitular,iCalificacion,lTieneRPMN,iCveUsuario,"+
                                "lRegEmbarcacion,hdEmbarcacion,iCveVehiculo,iCvePersona,dEslora,dManga,dPuntal,"+
                                "dArqueoBruto,dArqueoNeto,iUniMedEslora,iUniMedManga,iUniMedPuntal,cRFC,cCalle,"+
                                "cNumExterior,cNomRazonSocial,cNumInterior,cColonia,cCodPostal,iCveEntidadFed,"+
                                "iCveMunicipio,iCveLocalidad,iCvePais,dPartExtranjera,iCveTipoActo,dPartExtranjera,"+
                                "lRegFinalizado,hdPartida,hdFolioRPMNCancelar,hdCancelar,cDscDocumento,"+
                                "iCveTipoEmbarcacion,iCveEntFedMatricula,iCveMunicMatricula,cMatricula,"+
                                "iCveOficinaFolioAnt,cFolioRPMNAnt,iCvePropietario,cPropietario,lHistorico,iCveOficinaReg,iEjercicioReg,iOficinaReg");
    try{
      vDinRep = dMYRRPMN.insert(vDinRep,null);
      int lRegistraEmb = vDinRep.getInt("lRegEmbarcacion");

    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveOficina,iEjercicio,iNumSolicitud,dtRegistro,dtIngreso,iCvePersona,cSintesis,iCveRamo,"+
                                "iCveSeccion,iCveTipoAsiento,cTitular,iCalificacion,lTieneRPMN,iCveUsuario,lRegEmbarcacion,"+
                                "hdEmbarcacion,iCveVehiculo,iCvePersona,dEslora,dManga,dPuntal,dArqueoBruto,dArqueoNeto,"+
                                "iUniMedEslora,iUniMedManga,iUniMedPuntal,cRFC,cCalle,cNumExterior,cNomRazonSocial,cNumInterior,"+
                                "cColonia,cCodPostal,iCveEntidadFed,iCveMunicipio,iCveLocalidad,iCvePais,dPartExtranjera,"+
                                "iCveTipoActo,dPartExtranjera,lRegFinalizado,hdPartida,hdFolioRPMNCancelar,hdCancelar,hdOficReg,"+
                                "hdPartReg,cDscDocumento,iEjercicioIns,iCveTipoEmbarcacion,iCveEntFedMatricula,iCveMunicMatricula,"+
                                "cMatricula,iCveOficinaFolioAnt,cFolioRPMNAnt,iCvePropietario,cPropietario,iCveObsLarga,iEjercicioObsLarga");

    try{
      vDinRep = dMYRRPMN.update(vDinRep,null);

    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarAdmin")){
    vDinRep = oAccion.setInputs("iCveRamo,iFolioRPMN,iCveCapitania,iEjercicio,iPartida,iCveOficinaReg,cTitular,iEjercSolReg,"+
                                "iNumSolReg,iCveSeccion,iCveTipoActo,iCveTipoAsiento,dtIngreso,dtRegistro,cDscDocumento,cSintesis,"+
                                "dPartExtranjera,iPartidaCancela,lRegFinalizado,iCveOficinaFolioAnt,cFolioRPMNAnt,iCveObsLarga,iEjercicioObs,iCveOficina,iEjercicioIns");

    try{
      vDinRep = dMYRRPMN.updateAdmin(vDinRep,null);

    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("CancelaFolio")){
    System.out.println("*****    Llega al JSP");
    vDinRep = oAccion.setInputs("iEjercicioIns,iOficinaReg,iFolioReg");

    try{
      vDinRep = dMYRRPMN.cancelaFolio(vDinRep,null);

    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveOficina,iFolioRPMN,iPartida");
    try{
       dMYRRPMN.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

  Vector vcListado = new Vector();
  oAccion.navega(vcListado);
  String cNavStatus = oAccion.getCNavStatus();
%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
<script language="JavaScript">
<%
   out.print(oAccion.getArrayCD());
%>
  fEngResultado('<%=request.getParameter("cNombreFRM")%>',
                '<%=request.getParameter("cId")%>',
                '<%=cError%>',
                '<%=cNavStatus%>',
                '<%=oAccion.getIRowPag()%>',
                '<%=oAccion.getBPK()%>',
                '<%=Fecha.getFechaDDMMYYYY(Fecha.TodaySQL(),"/")%>');
</script>
<%}%>
