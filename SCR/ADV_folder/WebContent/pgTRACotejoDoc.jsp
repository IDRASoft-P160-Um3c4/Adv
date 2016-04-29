<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.micper.util.TFechas" %>
<%
/**
 * <p>Title: pg111020080.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegReqXTram</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Hanksel Fierro Medina
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRARegReqXTramb dTRARegReqXTram = new TDTRARegReqXTramb();
  TDGRLPais pais = new TDGRLPais(); 
  TDGRLSeguimientoPNC dGRLSeguimientoPNC = new TDGRLSeguimientoPNC();
  TDGRLRegCausaPNC1 regCausa = new TDGRLRegCausaPNC1();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());

  //TVUsuario vUsuario = (TVUsuario)request.getSession(true).getAttribute("UsrID");
  int iCveUsuario = Integer.parseInt(request.getParameter("iCveUsuario"));
  int lValido = 1;

  out.write("Clave del usuario"+iCveUsuario);
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iNumSolicitud,iEjercicio,iCveTramite,iCveModalidad,iCveRequisito");
    try{
      vDinRep.put("iCveUsuRecibe",iCveUsuario);
      vDinRep = dTRARegReqXTram.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito");
    try{
      vDinRep.put("iCveUsuRecibe",iCveUsuario);
      vDinRep = dTRARegReqXTram.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
 /** Verifica si la Acción a través de hdBotón es igual a "Cambia" (UpDate) */
  if(oAccion.getCAccion().equals("Cambia")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,cConjunto,iCveUsuario,iCveOficina,iCveDepartamento,iCveTramite,iCveModalidad,HDcPropietario,HDcInstalacion");
 
    try{
	     dTRARegReqXTram.cotejoDocs(vDinRep,null);
    }catch(Exception e){
         cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  /** Verifica si la Acción a través de hdBotón es igual a "Cambia" (UpDate) */
  String cSQL="";
  String CID=  (String)request.getParameter("cId");
  
  if(CID.equals("DatosAfec")){
  cSQL="SELECT " +
			"AF.CPROPIETARIO, " +
			"AF.CINSTALACION, " +
			"AF.COBSERVACION " +
			"FROM TRAREGSOLICITUD S  " +
			"JOIN TRADATOSNOAFEC AF ON AF.IEJERCICIO = S.IEJERCICIO " +
					"AND AF.INUMSOL = S.INUMSOLICITUD " +
	          oAccion.getCFiltro() + oAccion.getCOrden();
  }else{
  cSQL="SELECT " +
			"S.ICVETRAMITE," +
			"S.ICVEMODALIDAD," +
			"RR.ICVEREQUISITO," +
			"RR.DTRECEPCION," +
			"RR.DTCOTEJO," +
			"R.CDSCREQUISITO," +
			"RR.LVALIDO," +
			"S.CNOMAUTORIZARECOGER, " +
			"RR.LFISICO " +
			"FROM TRAREGSOLICITUD S  " +
			"JOIN TRAREGREQXTRAM RR ON RR.IEJERCICIO = S.IEJERCICIO " +
				"AND RR.INUMSOLICITUD = S.INUMSOLICITUD " +
			"JOIN TRAREQXMODTRAMITE RMT ON RMT.ICVETRAMITE=S.ICVETRAMITE " +
				"AND RMT.ICVEMODALIDAD=S.ICVEMODALIDAD " +
				"AND RMT.ICVEREQUISITO= RR.ICVEREQUISITO " +
			"JOIN TRAREQUISITO R ON R.ICVEREQUISITO=RR.ICVEREQUISITO "+
	          oAccion.getCFiltro() + oAccion.getCOrden();
  }

 vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud");

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
 Vector vcListado = new Vector();
 try{
     vcListado = dTRARegReqXTram.findByCustom("iEjercicio,iNumSolicitud",cSQL);
 }catch(Exception ex){ex.printStackTrace();  cError = ex.getMessage(); }
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
                '<%=lValido%>');
</script>
<%}%>
