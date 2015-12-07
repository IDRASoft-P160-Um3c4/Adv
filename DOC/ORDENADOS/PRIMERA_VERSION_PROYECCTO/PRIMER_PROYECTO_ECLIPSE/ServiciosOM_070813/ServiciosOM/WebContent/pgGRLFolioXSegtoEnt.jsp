<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%@ page import=" gob.sct.sipmm.dao.reporte.TDObtenDatos" %>
<%
/**
 * <p>Title: pgGRLFolioXSegtoEnt.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLFolioXSegtoEnt</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ICaballero
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLFolioXSegtoEnt dGRLFolioXSegtoEnt = new TDGRLFolioXSegtoEnt();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveSegtoEntidad,lFolioReferenInterno,cNumOficioRef,lExterno");
    try{
      vDinRep = dGRLFolioXSegtoEnt.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

  /** Verifica si la Acción a través de hdBotón es igual a "GuardarB Externos" */
  if(oAccion.getCAccion().equals("GuardarB")){
    vDinRep = oAccion.setInputs("iCveSegtoEntidad,cNumOfPartesRefExterna,cNumOficioRefExterna,lExterno");
    try{
      vDinRep = dGRLFolioXSegtoEnt.insertB(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("hdConsecutivoSegtoRef,hdCveSegtoEntidad,cNumOficioRef,lExterno");
    try{
      vDinRep = dGRLFolioXSegtoEnt.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
/** Verifica si la Acción a través de hdBotón es igual a "GuardarC" (Actualizar externos) */
  if(oAccion.getCAccion().equals("GuardarC")){
    vDinRep = oAccion.setInputs("hdConsecutivoSegtoRef,hdCveSegtoEntidad,cNumOfPartesRefExterna,cNumOficioRefExterna,lExterno");
    try{
      vDinRep = dGRLFolioXSegtoEnt.updateB(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("hdConsecutivoSegtoRef,hdCveSegtoEntidad");
    try{
       dGRLFolioXSegtoEnt.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */


 String cSQL = "Select GRLFolioXSegtoEnt.iCveSegtoEntidad,GRLFolioXSegtoEnt.iConsecutivoSegtoRef,GRLFolioXSegtoEnt.lFolioReferenInterno, " +//2
		"GRLFolioXSegtoEnt.cNumOfPartesRefExterna, GRLFolioXSegtoEnt.cNumOficioRefExterna,GRLFolioXSegtoEnt.iEjercicioFolio, " +//5
		"GRLFolioXSegtoEnt.iCveOficina,GRLFolioXSegtoEnt.iCveDepartamento,GRLFolioXSegtoEnt.cDigitosFolio,GRLFolioXSegtoEnt.iConsecutivo, " +//9
		"GRLFOLIO.IEJERCICIO, GRLFOLIO.ICVEOFICINA, GRLFOLIO.ICVEDEPARTAMENTO, GRLFOLIO.CDIGITOSFOLIO, GRLFOLIO.ICONSECUTIVO, " +//14
		"GRLFOLIO.CNUMOFICIALIAPARTES, GRLFolioXSegtoEnt.lExterno, GRLFolioXSegtoEnt.CNUMOFICIOREFEXTERNA,GRLFOLIOXSEGTOENT.CNUMOFPARTESREFEXTERNA " +//18
		"from GRLFolioXSegtoEnt " +
		"left join GRLFOLIO on GRLFOLIO.IEJERCICIO = GRLFOLIOXSEGTOENT.IEJERCICIOFOLIO " +
		"and GRLFOLIo.ICVEOFICINA = GRLFOLIOXSEGTOENT.ICVEOFICINA " +
		"and grlfolio.ICVEDEPARTAMENTO = GRLFOLIOXSEGTOENT.ICVEDEPARTAMENTO " +
		"and GRLFOLIO.CDIGITOSFOLIO = GRLFOLIOXSEGTOENT.CDIGITOSFOLIO " +
		"and grlfolio.ICONSECUTIVO = GRLFOLIOXSEGTOENT.ICONSECUTIVO ";
  Vector vcListado = dGRLFolioXSegtoEnt.findByCustom("iCveSegtoEntidad,iConsecutivoSegtoRef",cSQL+ oAccion.getCFiltro() + oAccion.getCOrden());
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
                '<%=oAccion.getBPK()%>');
</script>
<%}%>
