<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%@ page import="com.micper.excepciones.*" %>
<%
/**
 * <p>Title: pgTRARegEtapasXModTram.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegEtapasXModTram</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ocastrejon
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  
  TDTRARegEtapasXModTram dTRARegEtapasXModTram = new TDTRARegEtapasXModTram();
  
  String cError = "";
  String cFiltroUsr = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  TVUsuario vUsuario = (TVUsuario)request.getSession(true).getAttribute("UsrID");
  int iCveUsuario = 0;

  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID"))){

    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  }else{

   if (vUsuario != null){
      iCveUsuario = vUsuario.getICveusuario();
    }

   
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
	  
   vDinRep = oAccion.setInputs("iCveUsuario,iEjercicio,iNumSolicitud,lResolucion,cObs,cAcuerdos,iCveOfic,iCveDpto,iCveTram,iCveMod,cRepCSCT,cCargoCSCT,cComentCSCT,cRepProm,cCargoProm,cComentProm,cRepConce,cCargoConce,cComentConce");  

    try{
     dTRARegEtapasXModTram.etapaResVTec(vDinRep,null);
    }
    catch(Exception e1){
      cError= e1.getMessage();
    }
    
  }

  

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
 
  String cSQL = "";
  Vector vcListado=null;
 
  if(oAccion.getCAccion().equals("DiasVT")){
	  String diasVt = vParametros.getPropEspecifica("DiasVT");
	  vcListado = new Vector();
	  vDinRep = new TVDinRep();
	  vDinRep.put("DiasVT", diasVt); 
	  vcListado.addElement(vDinRep);
 }else{
	 cSQL=  "SELECT"+
			" TRACATTRAMITE.CCVEINTERNA,"+ //0 
			" TRACATTRAMITE.CDSCBREVE,"+ //1
			" TRAMODALIDAD.CDSCMODALIDAD,"+//2
			" GRLPERSONA.CNOMRAZONSOCIAL || ' ' || GRLPERSONA.CAPPATERNO || ' ' || GRLPERSONA.CAPMATERNO AS NOMSOLICITANTE,"+ //3
			" TRAREGDATOSADVXSOL.DTVISITA,"+//4
			" TRACATTRAMITE.ICVETRAMITE,"+ //5
			" TRAMODALIDAD.ICVEMODALIDAD"+//6
			" FROM TRAREGSOLICITUD" +
			" JOIN TRACATTRAMITE ON TRAREGSOLICITUD.ICVETRAMITE = TRACATTRAMITE.ICVETRAMITE"+
			" JOIN TRAMODALIDAD ON TRAREGSOLICITUD.ICVEMODALIDAD = TRAMODALIDAD.ICVEMODALIDAD"+
			" JOIN GRLPERSONA ON TRAREGSOLICITUD.ICVESOLICITANTE = GRLPERSONA.ICVEPERSONA"+
			" JOIN TRAREGETAPASXMODTRAM ON TRAREGSOLICITUD.IEJERCICIO = TRAREGETAPASXMODTRAM.IEJERCICIO"+ 
			" AND TRAREGSOLICITUD.INUMSOLICITUD = TRAREGETAPASXMODTRAM.INUMSOLICITUD"+
			" AND TRAREGETAPASXMODTRAM.ICVEETAPA ="+ vParametros.getPropEspecifica("EtapaVisita") +
			" JOIN TRAREGDATOSADVXSOL ON TRAREGSOLICITUD.IEJERCICIO = TRAREGDATOSADVXSOL.IEJERCICIO"+
    		" AND TRAREGSOLICITUD.INUMSOLICITUD = TRAREGDATOSADVXSOL.INUMSOLICITUD"+
		     oAccion.getCFiltro();
	 
	 vcListado = dTRARegEtapasXModTram.findByCustom("",cSQL);
     //oAccion.getCFiltro() + oAccion.getCOrden());
 }
  
  
  
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
