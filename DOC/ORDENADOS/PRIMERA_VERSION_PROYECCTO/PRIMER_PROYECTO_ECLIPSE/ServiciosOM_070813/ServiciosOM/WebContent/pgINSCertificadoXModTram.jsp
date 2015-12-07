<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgINSCertificadoXModTram.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad INSCertificadoXModTram</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ALopez
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDINSCertificadoXModTram dINSCertificadoXModTram = new TDINSCertificadoXModTram();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iTipoCertificado,iCveGrupoCertif");
    try{
      vDinRep = dINSCertificadoXModTram.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iTipoCertificado,iCveGrupoCertif,iTipoCertificado1,iCveGrupoCertif1");
    try{
      vDinRep = dINSCertificadoXModTram.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,dtIniVigencia,iTipoCertificado1,iCveGrupoCertif1");
    try{
       dINSCertificadoXModTram.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  String cSql = "";
  if(oAccion.getCAccion().equals("ConsultaTipoCertificado")){
    cSql = "SELECT " +
           "       TC.INUMREPORTE, " +
           "       CMT.ITIPOCERTIFICADO, " +
           "       TS.ICVETRAMITE, " +
           "       TS.ICVEMODALIDAD " +
           "FROM INSCERTIFICADOXMODTRAM CMT " +
           "  JOIN TRAREGSOLICITUD TS ON TS.ICVETRAMITE = CMT.ICVETRAMITE " +
           "    AND TS.ICVEMODALIDAD = CMT.ICVEMODALIDAD " +
           "  JOIN INSTIPOCERTIF TC ON TC.ITIPOCERTIFICADO = CMT.ITIPOCERTIFICADO " +
           "    AND TC.ICVEGRUPOCERTIF = CMT.ICVEGRUPOCERTIF "+
           "where ts.IEJERCICIO = " +request.getParameter("iEjercicioSol")+
           " and ts.INUMSOLICITUD = "+request.getParameter("iNumSolicitudSol");
  }
  else{
    cSql = "SELECT " +
           "CTM.ICVETRAMITE,CTM.ICVEMODALIDAD,CTM.DTINIVIGENCIA,CTM.ITIPOCERTIFICADO,CTM.ICVEGRUPOCERTIF, " +
           "T.CCVEINTERNA||' - '|| T.CDSCBREVE AS CTRAMITE,M.CDSCMODALIDAD,TC.CDSCCERTIFICADO,GC.CDSCGRUPOCERTIF " +
           "FROM INSCERTIFICADOXMODTRAM CTM " +
           "JOIN TRACATTRAMITE T ON T.ICVETRAMITE=CTM.ICVETRAMITE " +
           "JOIN TRAMODALIDAD M ON M.ICVEMODALIDAD=CTM.ICVEMODALIDAD " +
           "JOIN INSGRUPOCERTIF GC ON GC.ICVEGRUPOCERTIF=CTM.ICVEGRUPOCERTIF " +
           "JOIN INSTIPOCERTIF TC ON TC.ICVEGRUPOCERTIF=CTM.ICVEGRUPOCERTIF AND TC.ITIPOCERTIFICADO=CTM.ITIPOCERTIFICADO "+
           oAccion.getCFiltro() + oAccion.getCOrden();
  }
  Vector vcListado = dINSCertificadoXModTram.findByCustom("iCveTramite,iCveModalidad,dtIniVigencia,iTipoCertificado,iCveGrupoCertif",cSql);
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
