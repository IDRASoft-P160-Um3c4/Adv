<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgTRADependencia.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRADependencia</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Arturo Lopez Peña
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRAEtapaVinculada dTRAEtapaVinculada = new TDTRAEtapaVinculada();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveTramiteHijo,iCveModalidadHijo,iCveEtapa,iCveEtapaVinc");
    try{
      vDinRep = dTRAEtapaVinculada.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveTramiteHijo,iCveModalidadHijo,iCveTramiteHijo1,iCveModalidadHijo1");
    try{
      vDinRep = dTRAEtapaVinculada.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iTramHijo,iModHijo,iCveEtapa");
    try{
       dTRAEtapaVinculada.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
 String cSql = "SELECT " +
               "  EV.ICVETRAMITE,EV.ICVEMODALIDAD,EV.ICVETRAMITEHIJO,EV.ICVEMODALIDADHIJO,ET.ICVEETAPA,EV.ICVEETAPAVINC, " +
               "  ET.IORDEN,T.CCVEINTERNA,T.CDSCBREVE,M.CDSCMODALIDAD,E.CDSCETAPA,T1.CCVEINTERNA as cCveIntHijo,T1.CDSCBREVE as cDscBreveHijo,M1.cDscMODALIDAD as cDscModalHijo,E1.CDSCETAPA as cEtapaHijo " +
               "FROM TRAETAPAXMODTRAM ET " +
               "JOIN TRACATTRAMITE T ON T.ICVETRAMITE=ET.ICVETRAMITE " +
               "JOIN TRAMODALIDAD M ON M.ICVEMODALIDAD=ET.ICVEMODALIDAD " +
               "JOIN TRAETAPA E ON E.ICVEETAPA=ET.ICVEETAPA " +
               "LEFT JOIN TRAETAPAVINCULADA EV ON EV.ICVETRAMITE=ET.ICVETRAMITE AND EV.ICVEMODALIDAD=ET.ICVEMODALIDAD AND EV.ICVEETAPA=ET.ICVEETAPA " +
               "LEFT JOIN TRACATTRAMITE T1 ON T1.ICVETRAMITE=EV.ICVETRAMITEHIJO " +
               "LEFT JOIN TRAMODALIDAD M1 ON M1.ICVEMODALIDAD=EV.ICVEMODALIDADHIJO " +
               "LEFT JOIN TRAETAPA E1 ON E1.ICVEETAPA=EV.ICVEETAPAVINC "+
               oAccion.getCFiltro() + oAccion.getCOrden();
  Vector vcListado = new Vector();
  vcListado = dTRAEtapaVinculada.findByCustom("",cSql);
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
