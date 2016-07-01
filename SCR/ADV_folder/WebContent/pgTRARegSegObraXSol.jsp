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
  TVDinRep vDinRep2;
  TDTRARegEtapasXModTram dTRARegEtapasXModTram = new TDTRARegEtapasXModTram();
  TDTRARegSolicitud dTRARegSolicitud = new TDTRARegSolicitud();
  TDTRARegEtapasXModTram regEtapasXModTram = new TDTRARegEtapasXModTram();
  
  String cError = "";
  String cFiltroUsr = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  TVUsuario vUsuario = (TVUsuario)request.getSession(true).getAttribute("UsrID");
  int iCveUsuario = 0;

  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID"))){

    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  }else{
	  
	  //System.out.println("la accion--"+oAccion.getCAccion());

   if (vUsuario != null){
      iCveUsuario = vUsuario.getICveusuario();
    }

  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,hdValSigReg,iCveUsuario");
    try{
      vDinRep = regEtapasXModTram.insertSeg(vDinRep,null);
    }
    catch(Exception e1){
      cError= e1.getMessage();
    }
    
    oAccion.setBeanPK(vDinRep.getPK());
  }

 
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
 
 Vector vcListado = new Vector();
 
 if(request.getParameter("hdBotonAux").equals("Solicitud")){
	 vcListado = dTRARegEtapasXModTram.findByCustom("",
			    "SELECT "+
				" TRACATTRAMITE.CCVEINTERNA,"+ //0 
				" TRACATTRAMITE.CDSCBREVE,"+ //1
				" TRAMODALIDAD.CDSCMODALIDAD,"+//2
				" GRLPERSONA.CNOMRAZONSOCIAL || ' ' || GRLPERSONA.CAPPATERNO || ' ' || GRLPERSONA.CAPMATERNO AS NOMSOLICITANTE,"+ //3
				" TRACATTRAMITE.ICVETRAMITE,"+ //4
				" TRAMODALIDAD.ICVEMODALIDAD"+//5
				" FROM TRAREGSOLICITUD" +
				" JOIN TRACATTRAMITE ON TRAREGSOLICITUD.ICVETRAMITE = TRACATTRAMITE.ICVETRAMITE"+
				" JOIN TRAMODALIDAD ON TRAREGSOLICITUD.ICVEMODALIDAD = TRAMODALIDAD.ICVEMODALIDAD"+
				" JOIN GRLPERSONA ON TRAREGSOLICITUD.ICVESOLICITANTE = GRLPERSONA.ICVEPERSONA"+
				" JOIN TRAREGETAPASXMODTRAM ON TRAREGSOLICITUD.IEJERCICIO = TRAREGETAPASXMODTRAM.IEJERCICIO"+ 
				" AND TRAREGSOLICITUD.INUMSOLICITUD = TRAREGETAPASXMODTRAM.INUMSOLICITUD"+
				" AND TRAREGETAPASXMODTRAM.ICVEETAPA ="+ vParametros.getPropEspecifica("EtapaEntregaPermiso") +
				" AND TRAREGSOLICITUD.LRESOLUCIONPOSITIVA = 1 "+
			     oAccion.getCFiltro());
 }
 else if (request.getParameter("hdBotonAux").equals("Seguimiento")) {
      vcListado = dTRARegEtapasXModTram.findByCustom("",
		"SELECT SEG.ICVETIPO, SEG.DTREGISTRO, OFIC.CDSCBREVE FROM TRAREGSEGOBRAXSOL SEG "+
		"JOIN TRAREGSOLICITUD SOL ON SEG.IEJERCICIO = SOL.IEJERCICIO AND SEG.INUMSOLICITUD = SOL.INUMSOLICITUD AND SOL.LRESOLUCIONPOSITIVA = 1 "+
		"JOIN GRLUSUARIOXOFIC USRXOF ON USRXOF.ICVEUSUARIO = SEG.ICVEUSUARIO "+
		"JOIN GRLOFICINA OFIC ON OFIC.ICVEOFICINA = USRXOF.ICVEOFICINA "+ 
		 oAccion.getCFiltro() +	 oAccion.getCOrden());
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
