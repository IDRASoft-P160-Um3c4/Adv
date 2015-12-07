<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgTRARequisito.jsp</p>
 * <p>Description: JSP "Cat�logo" de la entidad TRARequisito</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRARequisito dTRARequisito = new TDTRARequisito();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o m�s sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){

    //===========================================
    //FCSReq1 -Se Agrego el Campo cMetodoEjecuta-
        //vDinRep = oAccion.setInputs("cDscRequisito,cDscBreve,cFundLegal,lVigente,lDigitaliza,iCveFormato");
        vDinRep = oAccion.setInputs("cDscRequisito,cDscBreve,cFundLegal,lVigente,lDigitaliza,iCveFormato,cMetodoEjecuta,iCveOficinaEval,iCveDeptoEval");
    //FCSReq1 -Agregar Campo cMetodoEjecuta-
    //==========================================

    try{
      vDinRep = dTRARequisito.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
	 
    vDinRep = oAccion.setInputs("iCveRequisito,cDscRequisito,cDscBreve,cFundLegal,lVigente,lDigitaliza,iCveFormato,cMetodoEjecuta,iCveOficinaEval,iCveDeptoEval");
    try{
      vDinRep = dTRARequisito.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Borrar" (Ya sea f�sico o l�gico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveRequisito");
    try{
       dTRARequisito.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualizaci�n de Datos a trav�s de actualizar el vector con el Query */

//===========================================
//FCSReq1 -Se Agrego el Campo cMetodoEjecuta-
  //Vector vcListado = dTRARequisito.findByCustom("iCveRequisito","Select iCveRequisito,cDscRequisito,cDscBreve,cFundLegal,TRARequisito.lVigente,lDigitaliza,TRARequisito.iCveFormato,cDscFormato from TRARequisito join GRLFormato on GRLFormato.iCveFormato = TRARequisito.iCveFormato " + oAccion.getCFiltro() + oAccion.getCOrden());
  //Vector vcListado = dTRARequisito.findByCustom("iCveRequisito","Select iCveRequisito,cDscRequisito,cDscBreve,cFundLegal,TRARequisito.lVigente,lDigitaliza,TRARequisito.iCveFormato,cDscFormato,cMetodoEjecuta from TRARequisito join GRLFormato on GRLFormato.iCveFormato = TRARequisito.iCveFormato " + oAccion.getCFiltro() + oAccion.getCOrden());
  
  Vector vcListado = dTRARequisito.findByCustom("iCveRequisito","Select iCveRequisito,cDscRequisito,TRARequisito.cDscBreve,cFundLegal,TRARequisito.lVigente,lDigitaliza,TRARequisito.iCveFormato,cDscFormato,cMetodoEjecuta,iCveOficinaEval,GRLOficina.cDscBreve as cDscBreveOfi,iCveDeptoEval,GRLDepartamento.cDscBreve as cDscBreveDpto from TRARequisito join GRLFormato on GRLFormato.iCveFormato = TRARequisito.iCveFormato join GRLOficina on GRLOficina.iCveOficina = TRARequisito.iCveOficinaEval join GRLDepartamento on GRLDepartamento.iCveDepartamento = TRARequisito.iCveDeptoEval" + oAccion.getCFiltro() + oAccion.getCOrden());
  
  


//FCSReq1 -Se Agrego el Campo cMetodoEjecuta-
//===========================================



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
