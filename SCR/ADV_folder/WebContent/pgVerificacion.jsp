<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%@ page import="com.micper.util.*" %>
<%
/**
 * <p>Title: pgVerificacion.jsp</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Sandor Trujillo Q.
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDVerificacion dVerificacion = new TDVerificacion();
  String cError = "";
  int iAux = 0;
  boolean lPNC = false;
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{

  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("VelidaDespuesNotif")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito");
    try{
      vDinRep = dVerificacion.validaNotificados(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,lValido,dtNotificacion,iCveTramite,iCveModalidad,iCveRequisito,dtOficio,cNumOficio,dtRecepcion");
    try{
      vDinRep = dVerificacion.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iHDEjercicio,iNumVerifica,lConcAprobado,cObs,iCveProceso,iCveProducto,iCveConcVerifica,lAprobada");
    try{
      vDinRep = dVerificacion.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "ActReqXTram" (Actualizar) */
  if(oAccion.getCAccion().equals("ActReqXTram")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito");
    try{
      lPNC = true;
      vDinRep = dVerificacion.updateReq(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GOficio")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito,cNumOfReg,dtOfReg");
    try{
      lPNC = true;
      vDinRep = dVerificacion.upOficio(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  } 
 
  if(oAccion.getCAccion().equals("saveFolioPNC")){
	    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,cFolioPNC");
	    try{
	      lPNC = true;
	      vDinRep = dVerificacion.saveFolioPNC(vDinRep,null);
	    }catch(Exception e){
	      cError="Guardar";
	    }
	    oAccion.setBeanPK(vDinRep.getPK());
	  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
//  " SELECT iEjercicio, iNumSolicitud, iCveTramite,iCveModalidad,iCveSolicitante,iCveRepLegal,cNomAutorizaRecoger,iCveUsuRegistra,tsRegistro,dtLimiteEntregaDocs,dtEstimadaEntrega,lPagado,dtEntrega,iCveUsuEntrega,lResolucionPositiva,lDatosPreregistro,iIdBien,iCveOficina,iCveDepartamento,iEjercicioCita,iIdCita,iCveForma "
  Vector vcListado = null;
  String cSQL = "";
  if(request.getParameter("hdBotonAux") != null){
    if(request.getParameter("hdBotonAux").equals("VERIFICACION") || lPNC ||
      request.getParameter("hdBotonAux").equals("GOficio")){//System.out.print("VERIFICACION");

      Vector vcVerPNC = dVerificacion.findByCustom("","SELECT count(1) as iCuenta FROM TRAREGPNCETAPA "+
    		                                              "where IEJERCICIO="+request.getParameter("iEjercicio")+
    		                                              " and INUMSOLICITUD="+request.getParameter("iNumSolicitud"));
      TVDinRep vVerPNC = (TVDinRep) vcVerPNC.get(0);
      boolean lTienePNC = vVerPNC.getInt("iCuenta")>0;
      
      
        cSQL = 	"SELECT " +
		"  TRAREGSOLICITUD.IEJERCICIO, " + //0
		"  TRAREGSOLICITUD.INUMSOLICITUD, " +
		"  TRAREGSOLICITUD.ICVETRAMITE, " +
		"  TRAREGSOLICITUD.ICVEMODALIDAD, " +
		"  TRARegReqXTram.dtRecepcion, " +
		"  TRARegReqXTram.lValido, " + //5
		"  COALESCE(TRAREGREQXTRAM.DTNOTIFICACION, GRLREGISTROPNC.DTNOTIFICACION) AS DTNOTIFICACION , " +
		"  COALESCE(TRAREGREQXTRAM.DTOFICIO,GRLREGISTROPNC.DTOFICIO) AS DTOFICIO, " +
		"  COALESCE(TRAREGREQXTRAM.CNUMOFICIO,GRLREGISTROPNC.CNUMOFICIO) CNUMOFICIO, " +
		"  TRARegReqXTram.ICVEREQUISITO, " +
		"  TRARequisito.cDscRequisito, " +  //10
		"  TRACATTRAMITE.CDSCBREVE, " +//11
		"  TRAMODALIDAD.CDSCMODALIDAD, " +//12
		"  TRARegReqXTram.lTienePNC, " +//13
    "( " +
		  "SELECT COUNT(DOC.IEJERCICIOREQ) AS ITRA " +
                  "FROM TRADOCXREQUIS DOC " +
		  "WHERE TRARegReqxTram.IEJERCICIO = DOC.IEJERCICIOREQ " +
		  "AND TRARegReqxTram.INUMSOLICITUD = DOC.INUMSOLICITUD " +
		  "AND TRARegReqxTram.ICVETRAMITE = DOC.ICVETRAMITE " +
		  "AND TRARegReqxTram.ICVEMODALIDAD = DOC.ICVEMODALIDAD " +
      "AND TRARegReqxTram.ICVEREQUISITO = DOC.ICVEREQUISITO " +
		") as lAdj, "+//14
    " TRARegReqxTram.LRECNOTIFICADO, "+ //15
    " RM.LREQUERIDO,  "+
    " GRLREGISTROPNC.CNUMOFICIO, "+//17
    " GRLREGISTROPNC.DTOFICIO, "+//18
    " GRLREGISTROPNC.dtNotificacion, "+//19
  //  " TRAREGEVAREQXAREA.ICVEEVALXAREA, "+//20
    " TRARegReqxTram.DTEVALUACION, "+//20
    " GRLDEPARTAMENTO.CDSCDEPARTAMENTO, "+//21
    " TRARegReqxTram.lfisico "+
		"FROM TRAREGSOLICITUD " +
		"JOIN TRARegReqxTram ON TRAREGSOLICITUD.iEjercicio = TRARegReqxTram.iEjercicio AND TRAREGSOLICITUD.iNumSolicitud = TRAREGREQXTRAM.iNumSolicitud "+ 
		"JOIN TRAREQXMODTRAMITE rm ON RM.ICVETRAMITE = TRARegReqxTram.ICVETRAMITE AND RM.ICVEMODALIDAD= TRARegReqxTram.ICVEMODALIDAD AND RM.ICVEREQUISITO= TRARegReqxTram.ICVEREQUISITO " +
		"JOIN TRARequisito ON TRAREQUISITO.ICVEREQUISITO = TRARegReqxTram.ICVEREQUISITO " +
		"JOIN TRACATTRAMITE ON TRAREGSOLICITUD.ICVETRAMITE = TRACATTRAMITE.ICVETRAMITE " +
		"JOIN TRAMODALIDAD ON TRAREGSOLICITUD.ICVEMODALIDAD = TRAMODALIDAD.ICVEMODALIDAD ";
    if(lTienePNC){ 
    	cSQL +=		"JOIN TRAREGPNCETAPA ON TRAREGPNCETAPA.IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO AND TRAREGPNCETAPA.INUMSOLICITUD=TRAREGSOLICITUD.INUMSOLICITUD " +
                " and TRAREGPNCETAPA.IORDEN = (select max (pe1.iorden) from TRAREGPNCETAPA pe1 where pe1.IEJERCICIO=TRAREGPNCETAPA.IEJERCICIO and pe1.INUMSOLICITUD=TRAREGPNCETAPA.INUMSOLICITUD)";
    } else {
    	cSQL +=		"LEFT JOIN TRAREGPNCETAPA ON TRAREGPNCETAPA.IEJERCICIO = TRAREGSOLICITUD.IEJERCICIO AND TRAREGPNCETAPA.INUMSOLICITUD=TRAREGSOLICITUD.INUMSOLICITUD ";
    }
    cSQL +=	"LEFT JOIN GRLREGISTROPNC ON GRLREGISTROPNC.IEJERCICIO = TRAREGPNCETAPA.IEJERCICIOPNC AND GRLREGISTROPNC.ICONSECUTIVOPNC=TRAREGPNCETAPA.ICONSECUTIVOPNC ";
    cSQL += "JOIN GRLDEPARTAMENTO ON GRLDEPARTAMENTO.ICVEDEPARTAMENTO = TRAREQUISITO.ICVEDEPTOEVAL ";
    //cSQL += "LEFT JOIN TRAREGEVAREQXAREA ON TRAREGREQXTRAM.IEJERCICIO=TRAREGEVAREQXAREA.IEJERCICIO AND TRAREGREQXTRAM.INUMSOLICITUD=TRAREGEVAREQXAREA.INUMSOLICITUD AND TRAREGREQXTRAM.ICVEREQUISITO=TRAREGEVAREQXAREA.ICVEREQUISITO AND TRAREGEVAREQXAREA.ICONSECUTIVOPNC IS NULL";
    }
    else if(request.getParameter("hdBotonAux").equals("CONCEPTOS")){//System.out.print("CONCEPTOS");
    cSQL =
      " SELECT cv.cDscConcVerifica , pr.iCveProceso, pr.iCveProducto , cvxp.iCveConcVerifica, "+
      " vp.dtVerifica, vp.iEjercicio, vp.iNumVerifica, cxvp.lConcAprobado, cxvp.cObs, "+
      " case char(cxvp.lConcAprobado)  when '0' then 'NO' "+
                              " when '1' then 'SI' "+
      " end AS cAprobado "+
      " FROM GRLProceso pro "+
      " JOIN GRLProdRequisit pr ON pr.iCveProceso = pro.iCveProceso "+
                             " AND pr.iCveTramite = pro.iCveTramite "+
                             " AND pr.iCveModalidad = pro.iCveModalidad "+
      " JOIN GRLConcVerXProd cvxp ON cvxp.iCveProceso = pr.iCveProceso "+
                               " AND cvxp.iCveProducto = pr.iCveProducto "+
      " JOIN GRLConcVerifica cv ON cv.iCveConcVerifica = cvxp.iCveConcVerifica "+
      " JOIN GRLConcXVerProd cxvp ON cxvp.iCveProceso = pr.iCveProceso "+
                               " AND cxvp.iCveProducto = pr.iCveProducto "+
                               " AND cxvp.iCveConcVerifica = cvxp.iCveConcVerifica "+
      " JOIN GRLVerifProduc vp ON vp.iEjercicio = cxvp.iEjercicio "+
                            " AND vp.iNumVerifica = cxvp.iNumVerifica "+
                            " AND vp.lAprobada = 0 ";
    }
    else if(request.getParameter("hdBotonAux").equals("folPNC")){
        cSQL =
          " SELECT ADV.CFOLIOPNC FROM TRAREGDATOSADVXSOL WHERE IEJERCICIO = "+ request.getParameter("iEjercicio") +" AND INUMSOLICITUD = "+request.getParameter("iNumSolicitud");
        }
  }

  if(!cSQL.equals(""))
  	vcListado = dVerificacion.findByCustom("iCveRequisito", cSQL + oAccion.getCFiltro() + oAccion.getCOrden());

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
