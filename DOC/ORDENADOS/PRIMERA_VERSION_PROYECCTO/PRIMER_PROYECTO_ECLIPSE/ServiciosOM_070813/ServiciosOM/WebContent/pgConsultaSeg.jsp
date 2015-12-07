<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgConsulta.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad CPAAcreditacionRepLegal</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Arturo Lopez Peña
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDConsultaSeg dConsulta = new TDConsultaSeg();
  String cError = "";
  String PropEspecifica1 = "";
  String PropEspecifica2 = "";
  String PropEspecifica3 = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  if(request.getParameter("hdPropEspecifica1")!=null){
     PropEspecifica1 = vParametros.getPropEspecifica(request.getParameter("hdPropEspecifica1"));
  }
  if(request.getParameter("hdPropEspecifica2")!=null){
     PropEspecifica2 = vParametros.getPropEspecifica(request.getParameter("hdPropEspecifica2"));
  }
  if(request.getParameter("hdPropEspecifica3")!=null){
     PropEspecifica3 = vParametros.getPropEspecifica(request.getParameter("hdPropEspecifica3"));
  }
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{


 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = new Vector();
  if(request.getParameter("hdSelect")!=null && request.getParameter("hdLlave")!=null && request.getParameter("cId")!=null){
    if(!request.getParameter("hdSelect").equals("")){
      try{
        TVDinRep vHdSelect = oAccion.setInputs("hdSelect,hdLlave");
	String cSelect = vHdSelect.getString("hdSelect");
        String cLlave = vHdSelect.getString("hdLlave");
        vcListado = dConsulta.findByCustom(cLlave,(cSelect.replaceAll("#hdPropEspecifica1#",PropEspecifica1)).replaceAll("#hdPropEspecifica2#",PropEspecifica2).replaceAll("#hdPropEspecifica3#",PropEspecifica3));
        if(request.getParameter("hdSinSize")==null)
          oAccion.setINumReg(vcListado.size());
      }catch(Exception e){
        cError = e.getMessage();
        e.printStackTrace();
      }
    }
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
                '<%=oAccion.getBPK()%>',
                '<%=PropEspecifica1%>',
                '<%=PropEspecifica2%>',
                '<%=PropEspecifica3%>');
</script>
<%}%>
