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
 * <p>Title: pgTRARegRefPago.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRARegRefPago</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ahernandez
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRARegRefPago dTRARegRefPago = new TDTRARegRefPago();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveConcepto,iRefNumerica,cRefAlfaNum,cImportePagar,dImportePagado,dtPago,iCveBanco,cFormatoSAT,lPagado,iCveConcepto,iNumUnidades,");
    try{
      vDinRep = dTRARegRefPago.insertPagoSimple(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
    oAccion.setCAccion("ObtenerPagos");
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iConsecutivo,cRefAlfaNum,dImportePagado,dtPago,iCveBanco,cFormatoSAT,lPagado");
    try{
      vDinRep = dTRARegRefPago.updateDatosPago(vDinRep,null);
    }catch(Exception e){
      cError=e.getMessage();
    }
    oAccion.setBeanPK(vDinRep.getPK());
    oAccion.setCAccion("ObtenerPagos");
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iConsecutivo");
    try{
       dTRARegRefPago.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }

  if(oAccion.getCAccion().equals("CreaRegPago")){
    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,Mov_iRefNumerica,Mov_iNumUnidades,Mov_cRefAlfaNum,Mov_dImportePagar,Mov_iCveConcepto,Mov_iCveSolicitanteIngresos");
    vDinRep.put("Mov_dFechaRef",request.getParameter("Mov_dFechaRef"));
    try{
      vDinRep = dTRARegRefPago.insertBatch(vDinRep,null);
    }catch(Exception e){
      cError=e.getMessage();
    }
    oAccion.setBeanPK(vDinRep.getPK());
    oAccion.setCAccion("ObtenerPagos");
  }


  Vector vcListado = new Vector();
  if(oAccion.getCAccion().equalsIgnoreCase("ObtenerPagos")){
    Vector vcTemp = new Vector();
    try{
      vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud");
      String cEjercicio = vDinRep.getString("iEjercicio"),
             cNumSolicitud = vDinRep.getString("iNumSolicitud");
      if(cEjercicio == null || cNumSolicitud == null)
        cEjercicio = cNumSolicitud = "";

      String cSelect = "SELECT "+
         "   RRP.iEjercicio, " +	//0
         "   RRP.iNumSolicitud, "+	//1
         "   RRP.iConsecutivo, " +	//2
         "   RRP.iNumUnidades, " +	//3
         "   RRP.iRefNumerica, " +	//4
         "   RRP.dImportePagar, " +	//5
         "   RRP.lPagado, "      +	//6
         "   RRP.dtPago,  "      +  	//7
         "   RRP.cRefAlfaNum, "  +	//8
         "   RRP.dImportePagado, " +	//9
         "   RRP.iCveBanco, " + 	//10
         "   RRP.cFormatoSAT, " +	//11
         "   B.cDscBanco, " +		//12
         "   P.cDscConcepto, " +	//13
         "   RRP.dtMovimiento " +	//14
         " FROM TRARegRefPago RRP"+
         " LEFT JOIN GRLBanco B ON B.iCveBanco = RRP.iCveBanco"+
         " JOIN TRAConceptoPago P ON P.iCveConcepto = RRP.iCveConcepto"+
         " WHERE RRP.iEjercicio = " + cEjercicio +
         "  AND RRP.iNumSolicitud = " + cNumSolicitud +
         " ORDER BY RRP.iEjercicio, RRP.iNumSolicitud, RRP.dtPago, RRP.iRefNumerica";
      String cLlave = "iEjercicio,iNumSolicitud,iConsecutivo";
      if(cEjercicio.length()>0 && cNumSolicitud.length()>0)
        vcTemp = dTRARegRefPago.findByCustom(cLlave, cSelect);
      if (vcTemp != null){
        TVDinRep vDatos;
        TNumeros tNum = new TNumeros();
        TFechas tFec = new TFechas();
        for (int i=0; i<vcTemp.size(); i++){
          vDatos = (TVDinRep)vcTemp.get(i);
          vDatos.put("cRefNumerica", tNum.getNumeroSinSeparador(new Integer(vDatos.getInt("iRefNumerica")),8)); //15
          vDatos.put("cImportePagar", tNum.getNumeroDecimal(vDatos.getDouble("dImportePagar"),2,true));         //16
          vDatos.put("cImportePagado", tNum.getNumeroDecimal(vDatos.getDouble("dImportePagado"),2,true));       //17
          vDatos.put("dtActual", tFec.TodaySQL());                                                              //18
          vcListado.add(vDatos);
        }
      }
    }catch(Exception ex){
      cError = ex.getMessage();
    }
    oAccion.setINumReg(vcListado.size());
  }else{
   /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
    String cSql= "Select TraRegSolicitud.iNumSolicitud,cRefNumerica,cRefAlfaNum,dImportePagar,dImportePagado,dtPago,cFormatoSAT,cCveCofemer,TraRegSolicitud.iCveTramite,cDscTramite,TraRegSolicitud.icveDepartamento,cdscdepartamento,GRLBanco.icvebanco,GRLBanco.cdscbanco " +
                 "from TRARegRefPago  " +
                 "join TraRegSolicitud on TRARegRefPago.iNumSolicitud=TraRegSolicitud.iNumSolicitud  " +
                 "join TraCofemer on TRARegRefPago.iEjercicio=TraCofemer.iEjercicio " +
                 "join TraCatTramite on TraRegSolicitud.iCveTramite=TraCatTramite.iCveTramite " +
                 "join GRLBanco on TRARegRefPago.icvebanco=GRLBanco.icvebanco " +
                 "join grldepartamento on TraRegSolicitud.iCveDepartamento=grldepartamento.iCveDepartamento " +
                  oAccion.getCFiltro() + oAccion.getCOrden();
    vcListado = dTRARegRefPago.findByCustom("iEjercicio,iNumSolicitud,iConsecutivo",cSql);
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
                '<%=oAccion.getBPK()%>');
</script>
<%}%>
