<%@page import="java.util.*"%>
<%@page import="com.micper.ingsw.*"%>
<%@page import="com.micper.util.logging.*"%>
<%@page import="com.micper.seguridad.vo.*"%>
<%@page import="com.micper.seguridad.dao.*"%>
<%@page import="gob.sct.sipmm.dao.*"%>
<%@page import="com.micper.sql.*"%>
<%
  /**
   * <p>Title: pgTRAConceptoXTramMod.jsp</p>
   * <p>Description: JSP "Catálogo" de la entidad TRAConceptoXTramMod</p>
   * <p>Copyright: Copyright (c) 2005 </p>
   * <p>Company: Tecnología InRed S.A. de C.V. </p>
   * @author Rafael Miranda Blumenkron
   * @version 1.0
   */
  TLogger.setSistema("44");
  TParametro vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRARelacionCIS dRelacionCIS = new TDTRARelacionCIS();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if (!oAccion.unaSesion(vParametros, (CFGSesiones) application.getAttribute("Sesiones"), (TVUsuario) request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else {


    Vector vDatos = new Vector();
    if (oAccion.getCAccion().equals("BuscaCitasCIS")) {
      vDinRep = oAccion.setInputs("dtInicial,dtFinal");
      try{
        vDatos = dRelacionCIS.buscaCitasCIS(vDinRep);
        oAccion.setINumReg(vDatos.size());
      }catch(Exception e){
        cError = e.getMessage();
      }
    }

    if (oAccion.getCAccion().equals("BuscaSolicitanteCIS")) {
      vDinRep = oAccion.setInputs("iCvePersonaCIS,cRFC,cCURP");
      try{
        vDatos = dRelacionCIS.buscaUsuarioCIS(vDinRep);
        oAccion.setINumReg(vDatos.size());
      }catch(Exception e){
        cError = e.getMessage();
      }
    }

    if (oAccion.getCAccion().equals("RegistraSolicitanteCIS")) {
      vDinRep = oAccion.setInputs("cRFC,cCURP,cNombre,cApPaterno,cApMateno,iCvePais,iCveEntidadFed,iCveMunicipio,cColonia,cCalle,cNumExterior,cNumInterior,cCodPostal,cTelefono,cCorreoE");
      try{
        vDatos = dRelacionCIS.insertaUsuarioCIS(vDinRep);
        oAccion.setINumReg(vDatos.size());
      }catch(Exception e){
        cError = e.getMessage();
      }
    }

    /*
    if (oAccion.getCAccion().equals("RegistraSolIng")) {
      vDinRep = oAccion.setInputs("iCvePersona,cRPA,cRFC,cNombre,cApPaterno,cApMaterno,iTipoPersona,cCalle,cColonia,cCodPostal,cTelefono,iCvePais,iCveEntidadFed,iCveMunicipio,cCURP,cNumExterior,cNumInterior");
      vImportesCalculados = dTRAConceptoXTramMod.registraUsuIng(vDinRep);
    }*/

    oAccion.navega(vDatos);
    String cNavStatus = oAccion.getCNavStatus();
%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js" type="" ></SCRIPT>
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
