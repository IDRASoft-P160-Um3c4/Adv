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
 * <p>Description: JSP "Cat�logo" de la entidad TRARegReqXTram</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnolog�a InRed S.A. de C.V. </p>
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
  /** Verifica si existe una o m�s sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Guardar" */
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

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "GuardarA" (Actualizar) */
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
 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Cambia" (UpDate) */
  if(oAccion.getCAccion().equals("Cambia")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,cConjunto,iCveModalidad,iCveTramite,iCveRequisito,iCveUsuario");
    
    TVDinRep vPNC = oAccion.setInputs("iEjercicio,iNumSolicitud,iConsecutivoPNC,cObservacion");
    Vector vcPNC = pais.findByCustom("","SELECT p.IEJERCICIO,p.ICONSECUTIVOPNC,p.ICVEPRODUCTO,CP.ICVECAUSAPNC,p.ICVEOFICINAASIGNADO,p.ICVEDEPTOASIGNADO " +
    																		"FROM GRLREGISTROPNC P " +
    																		"join GRLREGCAUSAPNC cp ON CP.IEJERCICIO=P.IEJERCICIO AND CP.ICONSECUTIVOPNC = P.ICONSECUTIVOPNC AND CP.ICVEPRODUCTO = CP.ICVEPRODUCTO " +
    																		"WHERE P.IEJERCICIO="+vPNC.getInt("iEjercicio")+" and P.ICONSECUTIVOPNC="+vPNC.getInt("iConsecutivoPNC"));
    
    try{
        vDinRep.put("iCveUsuRecibe",iCveUsuario);
        dTRARegReqXTram.Cambia(vDinRep,null);    	
    }catch(Exception e){
    	if(e.getMessage().length()>=9 && e.getMessage().substring(0,9).equals("No es pos")){
        	cError = e.getMessage();
    	}
    	else
    	      cError="Guardar";    	
    }
    /*
    try{
      for(int i=0;i<vcPNC.size();i++){
    	  TVDinRep vPNC1 = (TVDinRep) vcPNC.get(i);
    	  vPNC1.put("cComentarios",vPNC.getString("cObservacion"));
    	  vPNC1.put("iCveUsuRegistra",iCveUsuario);
    	  vPNC1.put("iCveUsuCorrige",iCveUsuario);
    	  dGRLSeguimientoPNC.insert(vPNC1,null);
    	  regCausa.resolver(vPNC1,null);
    	  vPNC.remove("iConsecutivoPNC");
        vPNC.put("iConsecutivoPNC",vPNC1.getInt("ICONSECUTIVOPNC"));
      }
      dTRARegReqXTram.FinalizaPNC(vPNC);
    }catch(Exception e){
    	if(e.getMessage().substring(0,9).equals("El tiempo")){
        	cError = e.getMessage();
    	}
    	else
    	      cError="Guardar";
    }*/
    oAccion.setBeanPK(vDinRep.getPK());
  }
  /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Cambia" (UpDate) */
  if(oAccion.getCAccion().equals("GuardarCambios")){
    TDTRARegSolicitud dTRARegSolicitud = new TDTRARegSolicitud();
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,cNomAutorizaRecoger,iCveOficina,cEnviarResolucionA");
    try{
      vDinRep = dTRARegSolicitud.updateAutorizaRec(vDinRep,null);
      if(Integer.parseInt(request.getParameter("iCveOficina")) != -1)
        vDinRep = dTRARegSolicitud.updateEnviarResolucionA(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acci�n a trav�s de hdBot�n es igual a "Borrar" (Ya sea f�sico o l�gico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveRequisito");
    try{
       dTRARegReqXTram.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

  if(oAccion.getCAccion().equals("ValidaNotif")){
    TDGRLDiaNoHabil dDiaNoHabil = new TDGRLDiaNoHabil();
    TFechas dFecha = new TFechas();
    Vector vNotif = new Vector();
    TVDinRep vDinNotif = new TVDinRep();
    java.sql.Date dtFechaFinal, dtFechaHoy;
    TVDinRep vDtNotificacion = oAccion.setInputs("dtNotificacion");
    boolean lNaturales = false;
    String cQuery = "SELECT " +
		"INUMDIASCUBRIRREQ,LDIASNATURALESREQ " +
		"FROM TRACONFIGURATRAMITE " +
		"where ICVETRAMITE = " + request.getParameter("iCveTramite") +
		" and ICVEMODALIDAD = " + request.getParameter("iCveModalidad") +
		" order by DTINIVIGENCIA desc ";

    vNotif = dTRARegReqXTram.findByCustom("ICVETRAMITE,iCveModalidad,dtIniVigencia", cQuery);
    vDinNotif = (TVDinRep)vNotif.get(0);
    if(vDinNotif.getInt("LDIASNATURALESREQ") == 1)
      lNaturales = true;
    dtFechaHoy = dFecha.TodaySQL();
    vDinNotif.put("dtNotificacion",vDtNotificacion.getString("dtNotificacion"));
    dtFechaFinal = dDiaNoHabil.getFechaFinal(vDinNotif.getDate("dtNotificacion"),Integer.parseInt(vParametros.getPropEspecifica("cDiasRecReqNotificados"),10),false);
    if(Integer.parseInt(dFecha.getFechaYYYYMMDDSinSep(dtFechaHoy),10) > Integer.parseInt(dFecha.getFechaYYYYMMDDSinSep(dtFechaFinal),10))
      lValido = 0;

  }
  String cSQL = "";
  if(oAccion.getCAccion().equals("GetSolicitud")){
	    cSQL = "SELECT " +
	           "S.IEJERCICIO,S.INUMSOLICITUD,"+
	           "P.CNOMRAZONSOCIAL||' '|| P.CAPPATERNO||' '|| P.CAPMATERNO AS CSOLICITANTE,P.CRFC, " +
	           "T.CDSCBREVE,M.CDSCMODALIDAD,S.CNOMAUTORIZARECOGER,S.ICVEOFICINAENVIARES,S.CENVIARRESOLUCIONA " +
	           "FROM TRAREGSOLICITUD S " +
	           "JOIN GRLPERSONA P ON P.ICVEPERSONA=S.ICVESOLICITANTE " +
	           "JOIN TRACATTRAMITE T ON T.ICVETRAMITE=S.ICVETRAMITE " +
	           "JOIN TRAMODALIDAD M ON M.ICVEMODALIDAD=S.ICVEMODALIDAD "+
	           oAccion.getCFiltro() + oAccion.getCOrden();	  
  }
  else {
	    cSQL = "SELECT " +
	           "       	DISTINCT(S.ICVETRAMITE),S.ICVEMODALIDAD, " +
	           "       	RR.ICVEREQUISITO,RMT.IORDEN,RP.DTNOTIFICACION, " +
	           "       	RR.DTRECEPCION,R.CDSCREQUISITO,RR.LVALIDO,' ' AS CCA,S.CNOMAUTORIZARECOGER, " +
	           "       	S.ICVEOFICINAENVIARES,S.CENVIARRESOLUCIONA,rc.ICVEREQUISITO " +
	           "FROM TRAREGSOLICITUD S " +
	           "  JOIN TRAREGREQXTRAM RR ON RR.IEJERCICIO = S.IEJERCICIO AND RR.INUMSOLICITUD = S.INUMSOLICITUD " +
	           "  left JOIN TRAREGPNCETAPA PE ON PE.IEJERCICIO = S.IEJERCICIO " +
	           "    AND PE.INUMSOLICITUD = S.INUMSOLICITUD " +
	           "  left JOIN TRAREGREQXCAUSA C ON C.IEJERCICIO = PE.IEJERCICIOPNC " +
	           "    AND C.ICONSECUTIVOPNC=PE.ICONSECUTIVOPNC " +
	           "    AND c.ICVEREQUISITO=rr.ICVEREQUISITO " +
	           "  LEFT JOIN GRLREGISTROPNC RP ON RP.IEJERCICIO=PE.IEJERCICIOPNC " +
	           "    AND RP.ICONSECUTIVOPNC=PE.ICONSECUTIVOPNC " +
	           //" and RP.ICONSECUTIVOPNC= "+request.getParameter("iConsecutivoPNC")+
	           "  left JOIN TRAREQXMODTRAMITE RMT ON RMT.ICVETRAMITE=S.ICVETRAMITE " +
	           "    AND RMT.ICVEMODALIDAD=S.ICVEMODALIDAD " +
	           "    AND RMT.ICVEREQUISITO= RR.ICVEREQUISITO " +
	           "  LEFT JOIN TRAREQUISITO R ON R.ICVEREQUISITO=RR.ICVEREQUISITO " +
	           "  left join TRAREGREQXCAUSA rc on rc.IEJERCICIO=rp.IEJERCICIO and rc.ICONSECUTIVOPNC=rp.ICONSECUTIVOPNC and rc.ICVEREQUISITO=rr.ICVEREQUISITO "+
	           oAccion.getCFiltro() + oAccion.getCOrden();
  }

 vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud");

 /** Se realiza la actualizaci�n de Datos a trav�s de actualizar el vector con el Query */
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
