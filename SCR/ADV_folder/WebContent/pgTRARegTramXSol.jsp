<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgTRARegTramXSol.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegTramXSol</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRARegTramXSol dTRARegTramXSol = new TDTRARegTramXSol();
  TDGRLUsuarioXOfic dGRLUsuarioXOfic = new TDGRLUsuarioXOfic();
  TDTRARegEtapasXModTram regEtapasXModTram = new TDTRARegEtapasXModTram();
  
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  int iCveUsuario = 0;
  int iCveOficinaUsr = 0;
  int iCveDepartamentoUsr = 0;
  int iCveDeptoVentanillaUnica = 0;
  int iEtapaRegistro = 0;
  int iCveEtapaRegistroVU = 0;
  int iCveEtapaRecepcion = 0;
  boolean lVentanillaUnica = false;
  String cFiltroOfiDepto = "";
  String cFiltroEtapa = "";
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
     TVUsuario vUsuario = (TVUsuario)request.getSession(true).getAttribute("UsrID");

     if (vUsuario != null){
        iCveUsuario = vUsuario.getICveusuario();
     }

  //Obteniendo el parametro del Departamento de Ventanilla Unica.
  iCveDeptoVentanillaUnica = Integer.parseInt(vParametros.getPropEspecifica("DeptoVentanillaUnica").toString());

  //Obteniendo Parámetro de Etapa de Registro.
  iCveEtapaRegistroVU = Integer.parseInt(vParametros.getPropEspecifica("EtapaRegistro").toString());

  //Obteniendo Parámetro de Etapa de Recepción.
  iCveEtapaRecepcion = Integer.parseInt(vParametros.getPropEspecifica("EtapaRecepcion").toString());

  if (request != null){
    if (request.getParameter("iCveOficinaUsr") != null)
      if (request.getParameter("iCveOficinaUsr").toString().compareTo("") != 0)
        iCveOficinaUsr = Integer.parseInt(request.getParameter("iCveOficinaUsr").toString());
    if (request.getParameter("iCveDeptoUsr") != null)
      if (request.getParameter("iCveDeptoUsr").toString().compareTo("") != 0)
        iCveDepartamentoUsr = Integer.parseInt(request.getParameter("iCveDeptoUsr").toString());
  }


  //Obtener Datos de la Oficina y del Departamento.
  if (iCveUsuario != 0 && iCveOficinaUsr == 0 && iCveDepartamentoUsr == 0){
    Vector vcListadoUsrXOfic = dGRLUsuarioXOfic.findByCustom("iCveOficina,iCveDepartamento,iCveUsuario",
        " select GRLUsuarioXOfic.iCveUsuario, " +
        " cNombre || ' ' || cApPaterno || ' ' || cApMaterno as cNombreCompleto, " +
        " GRLUsuarioXOfic.iCveDepartamento, " +
        " cDscDepartamento, " +
        " GRLDepartamento.cDscBreve, " +
        " GRLUsuarioXOfic.iCveOficina, " +
        " cDscOficina, " +
        " GRLOficina.cDscBreve " +
        " from GRLUsuarioXOfic " +
        " join SEGUsuario on SEGUsuario.iCveUsuario = GRLUsuarioXOfic.iCveUsuario " +
        " join GRLDepartamento on GRLDepartamento.iCveDepartamento = GRLUsuarioXOfic.iCveDepartamento " +
        " join GRLOficina on GRLOficina.iCveOficina = GRLUsuarioXOfic.iCveOficina " +
        "");
     if (vcListadoUsrXOfic.size()>0){
         TVDinRep vDatUsrXOfic = (TVDinRep) vcListadoUsrXOfic.get(0);
         iCveOficinaUsr = vDatUsrXOfic.getInt("iCveOficina");
         iCveDepartamentoUsr = vDatUsrXOfic.getInt("iCveDepartamento");
     }
  }

  if (iCveDepartamentoUsr == iCveDeptoVentanillaUnica)
    lVentanillaUnica = true;

  if (lVentanillaUnica){
     cFiltroOfiDepto = " and TRARegSolicitud.iCveOficina = " + iCveOficinaUsr +
                       " and TRARegSolicitud.iCveDepartamento = " + iCveDepartamentoUsr;
     cFiltroEtapa =   " and TRARegEtapasXModTram.iCveEtapa = " + iCveEtapaRegistroVU;
  }else{
     cFiltroOfiDepto = " and TRARegEtapasXModTram.iCveOficina = " + iCveOficinaUsr +
                       " and TRARegEtapasXModTram.iCveDepartamento = " + iCveDepartamentoUsr;
     cFiltroEtapa = "and TRARegEtapasXModTram.iCveEtapa = " + iCveEtapaRecepcion;
  }

  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveMotivoCancela,iCveUsuario,cObs");
    try{
      vDinRep = dTRARegTramXSol.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveMotivoCancela,iCveUsuario,cObs");
    String cSQLSearchRel = "SELECT SR.iEjercicio, SR.iNumSolicitudPrinc, SR.iNumSolicitudRel "+
                           "FROM TRASolicitudRel SR "+
                           "WHERE SR.iEjercicio = " + vDinRep.getString("iEjercicio") + " "+
                           "  AND SR.iNumSolicitudPrinc = " + vDinRep.getString("iNumSolicitud") + " "+
                           "UNION "+
                           "SELECT SR.iEjercicio, SR.iNumSolicitudPrinc, SR.iNumSolicitudRel "+
                           "FROM TRASolicitudRel SR "+
                           "WHERE SR.iEjercicio = " + vDinRep.getString("iEjercicio") + " "+
                           "  AND SR.iNumSolicitudPrinc = (SELECT SR2.iNumSolicitudPrinc FROM TRASolicitudRel SR2 WHERE SR2.iEjercicio = SR.iEjercicio "+
                           "                                      AND SR2.iNumSolicitudRel = " + vDinRep.getString("iNumSolicitud") + ") "+
                           "ORDER BY iEjercicio, iNumSolicitudPrinc, iNumSolicitudRel ";
    try{
      Vector vPorCancelar = dTRARegTramXSol.findByCustom("", cSQLSearchRel);
      vDinRep.put("iCveUsuario", iCveUsuario+"");
      if(vPorCancelar != null && vPorCancelar.size()>0){
        for(int i=0; i<vPorCancelar.size(); i++){
          if(i==0){
            vDinRep.put("iNumSolicitud", ((TVDinRep)vPorCancelar.get(i)).getString("iNumSolicitudPrinc"));
            vDinRep = dTRARegTramXSol.insert(vDinRep,null);
          }
          vDinRep.put("iNumSolicitud", ((TVDinRep)vPorCancelar.get(i)).getString("iNumSolicitudRel"));
          vDinRep = dTRARegTramXSol.insert(vDinRep,null);
        }
      }else vDinRep = dTRARegTramXSol.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
        
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveTramite,iCveModalidad");
    try{
       dTRARegTramXSol.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }
 
  
 	/*** comprueba el folio que se asignara al reporte ***/
  if(oAccion.getCAccion().equals("compFolCancel")){
	    oAccion.setCAccion("Actual");
	    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud");
	    try{
	    	TDTRARecepcion objRecep = new TDTRARecepcion();
	    	objRecep.comprobarFolioCancel(vDinRep, null);	       
	    }catch(Exception ex){
	      if(ex.getMessage().equals("")==false){
	        cError="compFolioCancelacion";
	      }
	    }
	}
	 
 
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dTRARegTramXSol.findByCustom("iEjercicio,iNumSolicitud",
  "select TRARegSolicitud.iEjercicio, " +		//0
  "TRARegSolicitud.iNumSolicitud, " +			//1
  "TRACatTramite.cDscBreve, " +				//2
  "TRAModalidad.cDscModalidad, " +			//3
  "GRLOficina.cDscOficina, " +				//4
  "GRLDepartamento.cDscDepartamento, " +		//5
  "GRLPersona.cNomRazonSocial || ' ' || GRLPersona.cApPaterno || ' ' || GRLPersona.cApMaterno as Solicitante, " +
  "TRARegSolicitud.dtLimiteEntregaDocs, " +		//7
  "TRARegTramXSol.dtCancelacion, " +			//8
  "TRARegTramXSol.cObs, " +				//9
  "TRARegTramXSol.iCveMotivoCancela, " +		//10
  "'OfiResuelve.cDscOficina' as cOfiResuelve, " +		//11
  "'DeptoResuelve.cDscDepartamento' as cDeptoResuelve, " +	//12
  "TRAREGSOLICITUD.TSREGISTRO "+
  "from TRARegSolicitud " +
  "left join TRARegTramXSol on TRARegTramXSol.iEjercicio = TRARegSolicitud.iEjercicio " +
  "and TRARegTramXSol.iNumSolicitud = TRARegSolicitud.iNumSolicitud " +
  "left join GRLMotivoCancela on GRLMotivoCancela.iCveMotivoCancela = TRARegTramXSol.iCveMotivoCancela " +
  "join TRACatTramite on TRACatTramite.iCveTramite = TRARegSolicitud.iCveTramite " +
  "join TRAModalidad on TRAModalidad.iCveModalidad = TRARegSolicitud.iCveModalidad " +
  "join GRLOficina on GRLOficina.iCveOficina = TRARegSolicitud.iCveOficina " +
  "join GRLDepartamento on GRLDepartamento.iCveDepartamento = TRARegSolicitud.iCveDepartamento " +
  "join GRLPersona on GRLPersona.iCvePersona = TRARegSolicitud.iCveSolicitante " +
  /*ELEL28082006
  "join GRLPersona on GRLPersona.iCvePersona = TRARegSolicitud.iCveSolicitante " +
  "left join TRARegEtapasXModTram on TRARegEtapasXModTram.iEjercicio = TRARegSolicitud.iEjercicio " +
  "and TRARegEtapasXModTram.iNumSolicitud = TRARegSolicitud.iNumSolicitud " +
  "and TRARegEtapasXModTram.iOrden = 1 " + */
  "join TRARegEtapasXModTram on TRARegEtapasXModTram.iEjercicio = TRARegSolicitud.iEjercicio " +
  "and TRARegEtapasXModTram.iNumSolicitud = TRARegSolicitud.iNumSolicitud " +
  " and TRARegEtapasXModTram.iOrden = " +
  "(" +
  "select " +
  " max(TRARegEtapasXModTram.iOrden) " +
  " from TRARegEtapasXModTram " +
  " where TRARegEtapasXModTram.iEjercicio = TRARegSolicitud.IEJERCICIO " +
  " and TRARegEtapasXModTram.iNumSolicitud = TRARegSolicitud.INUMSOLICITUD " +
 // " and TRARegEtapasXModTram.iCveEtapa = 1" +
  ") " +
  /*FINELEL28082006*/
  //"left join GRLOficina OfiResuelve on OfiResuelve.iCveOficina = TRARegEtapasXModTram.iCveOficina " +
  //"left join GRLDepartamento DeptoResuelve on DeptoResuelve.iCveDepartamento = TRARegEtapasXModTram.iCveDepartamento " +
  oAccion.getCFiltro()// +
  //cFiltroOfiDepto
  /*
  //ELEL28082006
  " and TRARegEtapasXModTram.iOrden = " +
  "(" +
  "select " +
  " max(TRARegEtapasXModTram.iOrden) " +
  " from TRARegEtapasXModTram " +
  " where TRARegEtapasXModTram.iEjercicio = TRARegSolicitud.IEJERCICIO " +
  " and TRARegEtapasXModTram.iNumSolicitud = TRARegSolicitud.INUMSOLICITUD " +
 // " and TRARegEtapasXModTram.iCveEtapa = 1" +
  ") "*/ +
  //FINELEL28082006
  oAccion.getCOrden());
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
                '<%=iCveUsuario%>',
                '<%=(lVentanillaUnica)?1:0%>');
</script>
<%}%>
