<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgVEHVehiculo.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad VEHVehiculo</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Sandor Trujillo Q.
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDConsulta dConsulta = new TDConsulta();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
	  
		String cSQL = "";
		vDinRep = oAccion.setInputs("iCvePersona");
		
	  if(oAccion.getCAccion().equals("BuscaRepresentante")){
		  cSQL = "SELECT P.ICVEPERSONA,P.CNOMRAZONSOCIAL||' '|| P.CAPPATERNO||' '|| P.CAPMATERNO AS CNOMBRE, P.CRFC " +
		         "FROM GRLREPLEGAL RL " +
		         "JOIN GRLPERSONA P ON P.ICVEPERSONA = RL.ICVEPERSONA " +
		         "WHERE RL.ICVEEMPRESA= "+vDinRep.getInt("iCvePersona");
	  }
	  if(oAccion.getCAccion().equals("BuscarSolicitud")){
		  cSQL = "SELECT S.IEJERCICIO,S.INUMSOLICITUD,T.CDSCBREVE,M.CDSCMODALIDAD,S.TSREGISTRO " +
		         "FROM TRAREGSOLICITUD S " +
		         "JOIN TRACATTRAMITE T ON T.ICVETRAMITE=S.ICVETRAMITE " +
		         "JOIN TRAMODALIDAD M ON M.ICVEMODALIDAD=S.ICVEMODALIDAD " +
		         "WHERE S.ICVESOLICITANTE = "+vDinRep.getInt("iCvePersona");
	  }
	  if(oAccion.getCAccion().equals("BuscarRPMN")){
		  cSQL = "SELECT R.IFOLIORPMN,C.CSIGLASOFICINA,R.IEJERCICIOINS,R.IPARTIDA,R.DTREGISTRO,R.LHISTORICO,R.CFOLIORPMNANT " +
		         "FROM MYRRPMN R " +
		         "JOIN MYRCAPITANIA C ON C.ICVEOFICINA=R.ICVEOFICINA " +
		         "JOIN MYREMPRESA E ON E.IEJERCICIOINS=R.IEJERCICIOINS AND E.ICVEOFICINA=R.ICVEOFICINA AND E.IFOLIORPMN=R.IFOLIORPMN AND E.IPARTIDA=R.IPARTIDA " +
		         "WHERE E.ICVEPERSONA= "+vDinRep.getInt("iCvePersona");
	  }
	  if(oAccion.getCAccion().equals("Borrar")){
	  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
 Vector vcListado = new Vector();
	if(!oAccion.getCAccion().equals("")){
		vcListado = dConsulta.findByCustom("",cSQL + oAccion.getCFiltro() + oAccion.getCOrden());
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
