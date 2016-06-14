<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%@ page import="com.micper.excepciones.*" %>
<%
/**
 * <p>Title: pgTRARegEtapasXModTram.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegEtapasXModTram</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ocastrejon
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TVDinRep vDinRep2;
  TDTRARegEtapasXModTram dTRARegEtapasXModTram = new TDTRARegEtapasXModTram();
  TDTRARegSolicitud dTRARegSolicitud = new TDTRARegSolicitud();
  TDTRARegEtapasXModTram regEtapasXModTram = new TDTRARegEtapasXModTram();
  
  String cError = "";
  String cFiltroUsr = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  TVUsuario vUsuario = (TVUsuario)request.getSession(true).getAttribute("UsrID");
  int iCveUsuario = 0;

  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID"))){

    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  }else{

   if (vUsuario != null){
      iCveUsuario = vUsuario.getICveusuario();

    }

    cFiltroUsr = " and GRLUsuarioXOfic.iCveUsuario = " + request.getParameter("iCveUsuario");
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iCveUsuario,lAnexo,lResolucion,cObsCIS");
    try{
      vDinRep = dTRARegEtapasXModTram.insert(vDinRep,null);
    }
    catch(Exception e1){
      cError= e1.getMessage();
      System.out.println("?????????????????     "+cError);
    }
    
    System.out.println("::: Siguiente Etapa ::: " + vDinRep.getInt("iCveEtapa"));
   // if(vDinRep.getInt("iCveEtapa") == 7){
   // 	 System.out.println("::: Entro Cambio de Etapa Conlcusion en el AREA - CIS     " + vDinRep.getInt("iCveEtapa"));
   // 	 System.out.println("::: Parametros ::: " + vDinRep.toString());
   // 	regEtapasXModTram.cambiaEtapaWSCIS(vDinRep,null);
   // }
            
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iOrden,iCveOficina,iCveDepartamento,iCveUsuario,tsRegistro,cObsCIS");
    try{
      vDinRep = dTRARegEtapasXModTram.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "CambiaEtapa" (Actualizar) */
  if(oAccion.getCAccion().equals("CambiaEtapa")){
	  vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iCveOficina,iCveDepartamento,iCveUsuario,lAnexo,cObsCIS");
	  TVDinRep vDinObs = oAccion.setInputs("cObservaciones");
	  String cPNC="SELECT rp.IEJERCICIO,rp.ICONSECUTIVOPNC " +
	              "FROM TRAREGPNCETAPA ep " +
	              "left join GRLREGISTROPNC rp on ep.IEJERCICIOPNC=rp.IEJERCICIO and ep.ICONSECUTIVOPNC=rp.ICONSECUTIVOPNC " +
	              "where ep.iejercicio="+vDinRep.getInt("iEjercicio")+
	              " and ep.INUMSOLICITUD="+vDinRep.getInt("iNumSolicitud")+
	              " and rp.LRESUELTO=0 ";
	  Vector vPNC = dTRARegEtapasXModTram.findByCustom("",cPNC);
    if(vPNC.size()==0){
	    try{
	      dTRARegEtapasXModTram.cambiarEtapa(vDinRep,false,vDinObs.getString("cObservaciones"));
	    }catch(Exception e){
	      //e.printStackTrace();
	    	cError=e.getMessage();
	    }
    }
    else cError = "Solicitud con PNC";
    oAccion.setBeanPK(vDinRep.getPK());
  }

  if(oAccion.getCAccion().equals("CambiaEtapaA")){
	  vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iCveOficina,iCveDepartamento,iCveUsuario,lAnexo,cObsCIS");
	  TVDinRep vDinObs = oAccion.setInputs("cObservaciones");
	  
	    try{
	      dTRARegEtapasXModTram.cambiarEtapa(vDinRep,false,vDinObs.getString("cObservaciones"));
	    }catch(Exception e){
	    	cError=e.getMessage();
	    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  
  
 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iOrden");
    try{
       dTRARegEtapasXModTram.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dTRARegEtapasXModTram.findByCustom("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad,iCveEtapa,iOrden,",
         "select distinct(TRARegEtapasXModTram.iEjercicio) as iEjercicio, " +
         "TRARegEtapasXModTram.iNumSolicitud as iNumSolicitud, " +
	 "TRARegEtapasXModTram.iCveTramite as iCveTramite, " +
	 "Tracattramite.cDscBreve as cDscBreve, " +
         "TRARegEtapasXModTram.iCveModalidad as iCveModalidad, " +
         "cDscModalidad, " +
         "TRARegEtapasXModTram.iCveEtapa as iCveEtapa, " +
         "cDscEtapa, " +
         "TRARegEtapasXModTram.iOrden as iOrden, " +
         "TRARegEtapasXModTram.iCveOficina as iCveOficina, " +
         "TRARegEtapasXModTram.iCveDepartamento as iCveDepartamento, " +
         "TRARegEtapasXModTram.iCveUsuario as iCveUsuario, " +
         "cNombre || ' ' || cApPaterno || ' ' || cApMaterno as cNombre, " +
         "TRARegEtapasXModTram.tsRegistro as tsRegistro, " +
         "TRARegEtapasXModTram.lAnexo as clAnexo, " +
         "OfiEtapa.cDscBreve as cOfiBreve, " +
         "DeptoEtapa.cDscBreve as cDeptoBreve, " +
         "TRARegSolicitud.lResolucionPositiva as ilResPositiva, " +
         "TRARegEtapasXModTram.cObsEnviadaCIS " +
         "from TRARegEtapasXModTram " +
         "join TRACatTramite on TRACatTramite.iCveTramite = TRARegEtapasXModTram.iCveTramite " +
         "join TRAModalidad on TRAModalidad.iCveModalidad = TRARegEtapasXModTram.iCveModalidad " +
         "join TRAEtapa on TRAEtapa.iCveEtapa = TRARegEtapasXModTram.iCveEtapa " +
         "left join SEGUsuario on SEGUsuario.iCveUsuario = TRARegEtapasXModTram.iCveUsuario " +
         "join TRARegEtapasXModTram Fte on Fte.iEjercicio = TRARegEtapasXModTram.iEjercicio " +
         "and Fte.iNumSolicitud = TRARegEtapasXModTram.iNumSolicitud " +
         "join GRLUsuarioXOfic on GRLUsuarioXOfic.iCveoficina = Fte.iCveOficina " +
         "and GRLUsuarioXOfic.iCveDepartamento = Fte.iCveDepartamento " +
         "join GRLOficina OfiEtapa on OfiEtapa.iCveOficina = TRARegEtapasXModTram.iCveOficina " +
         "join GRLDepartamento DeptoEtapa on DeptoEtapa.iCveDepartamento = TRARegEtapasXModTram.iCveDepartamento " +
         "join TRARegSolicitud on TRARegSolicitud.iEjercicio = TRARegEtapasXModTram.iEjercicio " +
         "AND TRARegSolicitud.iNumSolicitud = TRARegEtapasXModTram.iNumSolicitud " +
         "AND TRARegSolicitud.lImpreso = 1 " +
         " AND TRARegSolicitud.LABANDONADA = 0 " +
         //cFiltroUsr +
         oAccion.getCFiltro() + oAccion.getCOrden());
  if(!oAccion.getCAccion().equals("CambiaEtapa"))
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
