<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%
/**
 * <p>Title: pgARBOLTRAMITES.jsp</p>
 * <p>Description: JSP "Catálogo" de los tramites agrupados</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: INFOTEC</p>
 * @author ISM
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  //TDLICTIPOLICENCIA dLICTIPOLICENCIA = new TDLICTIPOLICENCIA();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  String cSQL = "SELECT ICVETRAMITE, ICVETRAMITEPADRE, LTRAMITEFINAL, CCVEINTERNA || ' '||CDSCBREVE AS CDSCBREVE, CCVEINTERNA,CBIENBUSCAR FROM TRACATTRAMITE where LVIGENTE = 1 order by CCVEINTERNA";
  TDTRACatTramite Informacion = new TDTRACatTramite();
  Vector vcListado = Informacion.findByCustom("ICVETRAMITE",cSQL);
  Vector respuesta = new Vector();
  Vector vcTipo = new Vector();

  if(vcListado.size() > 0){
	    for(int i=0;i<vcListado.size();i++){
	      TVDinRep vTipo = (TVDinRep)vcListado.get(i);
	      TVDinRep vTipoArbol = new TVDinRep();
	      vTipoArbol.put("ICVEPADRE",vTipo.getInt("ICVETRAMITEPADRE"));
	      vTipoArbol.put("ICVENODO",vTipo.getInt("ICVETRAMITE"));
	      vTipoArbol.put("CDSCTIPOLICENCIA",vTipo.getString("CDSCBREVE"));
	      vTipoArbol.put("iDato",vTipo.getString("ICVETRAMITE"));
	      if(vTipo.getInt("LTRAMITEFINAL")==1)
	         vTipoArbol.put("LTIPOFINAL",2);
	      else
	         vTipoArbol.put("LTIPOFINAL",0);
	      vTipoArbol.put("iDenta",0);
	      vTipoArbol.put("cCveIntTramite",vTipo.getString("CCVEINTERNA"));
          vTipoArbol.put("CBIENBUSCAR",vTipo.getString("CBIENBUSCAR"));
	      vcTipo.add(vTipoArbol);
	    }
	  }else
	  vcListado = new Vector();
	respuesta = new TOrdenaArbol().getAreaOrdenada(vcTipo);
  oAccion.navega(respuesta);
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
