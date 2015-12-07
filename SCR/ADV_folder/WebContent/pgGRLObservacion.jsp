<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLObservacion.jsp</p>
 * <p>Description: JSP "Cat�logo" de la entidad GRLObservacion</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRARegTramXSol dTRARegTramXSol = new TDTRARegTramXSol();
  TDGRLObservacion dGRLObservacion = new TDGRLObservacion();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o m�s sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{

 TVUsuario vUsuario = (TVUsuario)request.getSession(true).getAttribute("UsrID");
  /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){

    vDinRep = oAccion.setInputs("cObsRegistrada,iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveFormatoReg,iCveMotivoCancela");
    vDinRep.put("iCveUsuario",vUsuario.getICveusuario());
    System.out.print("Usuario " +vUsuario.getICveusuario());
    try{

      String cSql2 = "SELECT * FROM TRAREGTRAMXSOL " +
                   " join TRAREGSOLICITUD on traregtramxsol.ICVETRAMITE = traregsolicitud.ICVETRAMITE " +
                   "and traregtramxsol.ICVEMODALIDAD = traregsolicitud.ICVEMODALIDAD " +
                   "and traregtramxsol.IEJERCICIO = traregsolicitud.IEJERCICIO " +
                   "and traregtramxsol.INUMSOLICITUD = traregsolicitud.INUMSOLICITUD " +
                   "where TRAREGTRAMXSOL.IEJERCICIO = "+vDinRep.getInt("iEjercicio")+" and TRAREGTRAMXSOL.INUMSOLICITUD =  " +vDinRep.getInt("iNumSolicitud")+
                   " and dtcancelacion is null ";
       Vector vcCancela = dGRLObservacion.findByCustom("",cSql2);
        if(vcCancela.size()>0){
            vDinRep = dTRARegTramXSol.update(vDinRep,null);
        }else{
            vDinRep = dGRLObservacion.insert(vDinRep,null);
        }
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,iCveObservacion,cObsRegistrada");
    try{
      vDinRep = dGRLObservacion.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Borrar" (Ya sea f�sico o l�gico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iEjercicio,iCveObservacion");
    try{
       dGRLObservacion.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualizaci�n de Datos a trav�s de actualizar el vector con el Query */
  Vector vcListado = dGRLObservacion.findByCustom("iEjercicio,iCveObservacion","Select iEjercicio,iCveObservacion,cObsRegistrada from GRLObservacion " + oAccion.getCFiltro() + oAccion.getCOrden());
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
