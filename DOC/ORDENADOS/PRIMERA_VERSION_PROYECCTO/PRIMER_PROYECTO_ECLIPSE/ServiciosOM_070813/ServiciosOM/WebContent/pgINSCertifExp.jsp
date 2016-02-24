<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgINSCertifExp.jsp</p>
 * <p>Description: JSP "Cat�logo" de la entidad INSCertifExpedidos</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
 * @author Arturo Lopez Pe�a
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDINSCertifExp dINSCertifExpedidos = new TDINSCertifExp();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o m�s sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveVehiculo,iNumSolicitud,iEjercicio,iTipoCertificado,dtExpedicionCert,dtIniVigencia,dtFinVigencia,lVigente,lExpedicion,iCveGrupoCertif,iCveUsuario,iCveOficina,cNomEmbarcacion,cObses");
    try{
      vDinRep = dINSCertifExpedidos.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("GuardarCI")){
    vDinRep = oAccion.setInputs("iCveVehiculo,iNumSolicitud,iEjercicio,"+
    		"iTipoCertificado,dtExpedicionCert,dtIniVigencia,dtFinVigencia,"+
    		"lVigente,lExpedicion,iCveGrupoCertif,iCveUsuario,iCveOficina,"+
    		"cNomEmbarcacion,cObses,iCvePuerto");
    try{
      vDinRep = dINSCertifExpedidos.insertCI(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  if(oAccion.getCAccion().equals("GuardarCara")){
    vDinRep = oAccion.setInputs("iCveInspeccion,iCveUsuario,cCampos,cValores,cConceptos,cCaracteristicas");
    try{
      vDinRep = dINSCertifExpedidos.insertCara(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveVehiculo,iConsecutivoCertExp,iNumSolicitud,"+
    		"iEjercicio,iTipoCertificado,iConsecutivoCertifExp,dtExpedicionCert,"+
    		"dtIniVigencia,dtFinVigencia,lVigente,lExpedicion,iCveGrupoCertif,"+
    		"cObses,iCvePuerto,iCveInspProg");
    try{
      vDinRep = dINSCertifExpedidos.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Borrar" (Ya sea f�sico o l�gico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveVehiculo,iConsecutivoCertExp");
    try{
       dINSCertifExpedidos.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }
    /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Guardar" */
  if(oAccion.getCAccion().equals("GuardarCert")){
    vDinRep = oAccion.setInputs("iCveVehiculo,iNumSolicitud,iEjercicio,iTipoCertificado,dtIniVigencia,dtFinVigencia,iCveGrupoCertif,iCveUsuario,iCveInspProg,iNumCertificado");
    try{
      vDinRep = dINSCertifExpedidos.insertCert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Se realiza la actualizaci�n de Datos a trav�s de actualizar el vector con el Query */
  Vector vcListado = dINSCertifExpedidos.findByCustom("iCveVehiculo,iConsecutivoCertExp",
                     "SELECT " +
                     "       CE.ICVEVEHICULO, " + //0
                     "       CE.ICONSECUTIVOCERTEXP, " +
                     "       CE.INUMSOLICITUD, " +
                     "       CE.IEJERCICIO, " +
                     "       CE.ITIPOCERTIFICADO, " +
                     "       ICONSECUTIVOCERTIFEXP, " + //5
                     "       DTEXPEDICIONCERT, " +
                     "       DTINIVIGENCIA, " +
                     "       DTFINVIGENCIA, " +
                     "       CE.LVIGENTE, " +
                     "       LEXPEDICION, " + //10
                     "       CE.ICVEGRUPOCERTIF, " +
                     "       TC.CDSCCERTIFICADO, " +
                     "       TC.INUMREPORTE, " +
                     "       CE.COBSES, " +
                     "       I.ICVEINSPECCION, " +  //15
                     "       PI.ICVEINSPPROG, " +
                     "       CI.INUMCERTIFICADO, " +
                     "       CI.LAPROBADA, "+
                     "       PI.ICVEPUERTO "+
                     "FROM INSCERTIFEXPEDIDOS CE " +
                     "  JOIN INSTIPOCERTIF TC ON TC.ITIPOCERTIFICADO = CE.ITIPOCERTIFICADO " +
                     "    AND TC.ICVEGRUPOCERTIF = CE.ICVEGRUPOCERTIF " +
                     "  LEFT JOIN INSPROGINSP PI ON PI.IEJERCICIO=CE.IEJERCICIO AND PI.INUMSOLICITUD=CE.INUMSOLICITUD " +
                     "  LEFT JOIN INSINSPECCION I ON I.ICVEINSPPROG=PI.ICVEINSPPROG " +
                     "  LEFT JOIN INSCERTXINSPECCION CI ON CI.ICVEINSPPROG=I.ICVEINSPPROG "
                     + oAccion.getCFiltro() + oAccion.getCOrden());
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