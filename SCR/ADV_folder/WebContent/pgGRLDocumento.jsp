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
 * <p>Title: pgGRLDocumento.jsp</p>
 * <p>Description: JSP "Cat�logo" de la entidad GRLDocumento</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author cabrito
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TVDinRep vDinRepDin = new TVDinRep();
  TDGRLDocumento dGRLDocumento = new TDGRLDocumento();
  TDGRLDocRegistrado dGRLDocRegistrado = new TDGRLDocRegistrado();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  TFechas Fecha = new TFechas("44");
  int iEjercicio = 0;
  iEjercicio = Fecha.getIntYear(Fecha.TodaySQL());
  String cJoin = "";

  /** Verifica si existe una o m�s sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Guardar" */

  if (request.getParameter("hdJoin") != null)
     if (request.getParameter("hdJoin").toString().compareTo("") != 0)
       cJoin = request.getParameter("hdJoin");

  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iEjercicio,iCveTipoDocumento,iIdGestorDocumento,iCveUsuario,dtRegistro,cDscDocumento,cObses,cNomArchivo,cSQL,cExtensionArchivo,cMimeType");
    try{
      vDinRep = dGRLDocumento.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());

    vDinRepDin.put("iEjercicio",vDinRep.getInt("iEjercicio"));
    vDinRepDin.put("iCveTipoDocumento",vDinRep.getInt("iCveTipoDocumento"));
    vDinRepDin.put("iIdDocumento",vDinRep.getInt("iIdDocumento"));
    String SQL = vDinRep.getString("cSQL");
    try{
      dGRLDocumento.insertDinamico(SQL,vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,iCveTipoDocumento,iIdDocumento,iIdGestorDocumento,iCveUsuario,dtRegistro,cDscDocumento,cObses,cNomArchivo");
    try{
      vDinRep = dGRLDocumento.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Borrar" (Ya sea f�sico o l�gico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iEjercicio,iCveTipoDocumento,iIdDocumento,cSQLDel,iIdGestorDocumento");

    try{
      dGRLDocRegistrado.delete(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }

    String SQL = vDinRep.getString("cSQLDel");
    try{
      dGRLDocumento.insertDinamico(SQL,vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }

    try{
       dGRLDocumento.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualizaci�n de Datos a trav�s de actualizar el vector con el Query */
  Vector vcListado = dGRLDocumento.findByCustom("GRLDocumento.iEjercicio,GRLDocumento.iCveTipoDocumento,GRLDocumento.iIdDocumento",
         " Select GRLDocumento.iEjercicio, " +
         " GRLDocumento.iCveTipoDocumento, "+
         " GRLDocumento.iIdDocumento, " +
         " GRLDocumento.iIdGestorDocumento, " +
         " GRLDocumento.iCveUsuario, " +
         " GRLDocumento.dtRegistro, " +
         " GRLDocumento.cDscDocumento, " +
         " GRLDocumento.cObses, " +
         " GRLDocumento.cNomArchivo, " +
         " GRLDocumento.cExtensionArchivo, " +
         " GRLDocumento.lBaseGestor, " +
         " GRLDocumento.cMimeType " +
         " from GRLDocumento " +
         cJoin +
         oAccion.getCFiltro() + oAccion.getCOrden());
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
                '<%=iEjercicio%>');
</script>
<%}%>
