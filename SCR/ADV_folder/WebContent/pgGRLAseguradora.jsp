<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgGRLAseguradora.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLAseguradora</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Arturo LP
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLAseguradora dGRLAseguradora = new TDGRLAseguradora();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
       vDinRep = oAccion.setInputs("iCvePersona,iCveDomicilio,cObses,lActivo");
    try{
      vDinRep = dGRLAseguradora.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveAseguradora,iCvePersona,iCveDomicilio,cObses,lActivo");
    try{
      vDinRep = dGRLAseguradora.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveAseguradora");
    try{
       dGRLAseguradora.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
 	String cSql="SELECT iCveAseguradora,A.iCvePersona,A.iCveDomicilio,A.cObses,A.lActivo, " +
		"P.CNOMRAZONSOCIAL||' '|| P.CAPPATERNO||' '|| P.CAPMATERNO as cAseguradora,P.CRFC, " +
		"D.CCALLE, D.CNUMEXTERIOR,D.CNUMINTERIOR,D.CCOLONIA, " +
		"PA.CNOMBRE AS cPais,EF.CNOMBRE as cEntidadFed,MU.CNOMBRE as CMunicipio " +
		"FROM GRLASEGURADORA A " +
		"JOIN GRLPERSONA P ON P.iCvePersona = A.iCvePersona " +
		"LEFT JOIN GRLDOMICILIO D ON D.iCvePersona=A.iCvePersona AND D.iCveDomicilio=A.iCveDomicilio " +
		"LEFT JOIN GRLPAIS PA ON PA.ICVEPAIS = D.ICVEPAIS " +
		"LEFT JOIN GRLENTIDADFED EF ON EF.ICVEPAIS=D.ICVEPAIS AND EF.ICVEENTIDADFED=D.ICVEENTIDADFED " +
		"LEFT JOIN GRLMUNICIPIO MU ON MU.ICVEPAIS=D.ICVEPAIS AND MU.ICVEENTIDADFED=D.ICVEENTIDADFED AND MU.ICVEMUNICIPIO=D.ICVEMUNICIPIO "+
             	oAccion.getCFiltro() + oAccion.getCOrden();
  Vector vcListado = dGRLAseguradora.findByCustom("iEjercicioPermiso,iConsecutivoPermiso",cSql );
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
