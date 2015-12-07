<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgTRAReqXModTramite.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad TRAReqXModTramite</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Jorge Arturo Wong Mozqueda
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRAReqXModTramite dTRAReqXModTramite = new TDTRAReqXModTramite();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  String cURLDespliegaFormato = vParametros.getPropEspecifica("URLDespliegaFormato");
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveRequisito,iOrden");
    try{
      vDinRep = dTRAReqXModTramite.insert(vDinRep,null);
    }catch(Exception e){
      cError=e.getMessage();
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveRequisito,iOrden");
    try{
      vDinRep = dTRAReqXModTramite.update(vDinRep,null);
    }catch(Exception e){
      cError=e.getMessage();
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveRequisito");
    try{
       dTRAReqXModTramite.delete(vDinRep,null);
    }catch(Exception e){
      cError=e.getMessage();
    }
  }

   vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad");

  /* String cSql1 = "select icvetramitehijo, icvemodalidadhijo " +
                 "from TRADEPENDENCIA " +
                 "where ICVETRAMITE = "+ vDinRep.getInt("iCveTramite")+ " and  ICVEMODALIDAD = " + vDinRep.getInt("iCveModalidad")+
                 " order by icvetramite ";
   Vector vcHijos = new Vector();
   try{
     vcHijos = dTRAReqXModTramite.findByCustom("",cSql1);
   }catch(Exception e){
     cError = e.getMessage();
   }*/
    String cSql2 = "SELECT distinct TRAREQXMODTRAMITE.ICVETRAMITE,TRAREQXMODTRAMITE.ICVEModalidad, trareqxmodtramite.ICVEREQUISITO,cdscrequisito, cdsctramite, cdscmodalidad,TRACatTramite.cCveInterna || ' - ' || TRACatTramite.cDscBreve AS cCveDesc, TRARequisito.cDscBreve AS cDscBreveRequisito, TRAREQXMODTRAMITE.iOrden, TRAREQXMODTRAMITE.lRequerido "+
                 "FROM TRAREQXMODTRAMITE "+
                 "join TRAREQUISITO on trareqxmodtramite.ICVEREQUISITO = trarequisito.ICVEREQUISITO "+
                 "join TRACATTRAMITE on TRAREQXMODTRAMITE.ICVETRAMITE = tracattramite.ICVETRAMITE "+
                 "join TRAMODALIDAD on TRAREQXMODTRAMITE.ICVEMODALIDAD = tramodalidad.ICVEMODALIDAD "+
                 "where trareqxmodtramite.ICVETRAMITE = "+vDinRep.getInt("iCveTramite")+" and trareqxmodtramite.ICVEMODALIDAD = "+vDinRep.getInt("iCveModalidad")+
                 " order by TRAREQXMODTRAMITE.iOrden";

     Vector vcListado = new Vector();
     try{
       vcListado = dTRAReqXModTramite.findByCustom("iCveTramite,iCveModalidad,iCveRequisito",cSql2);
     }catch(Exception e){
       cError += e.getMessage();
     }
     /*if(vcHijos.size() > 0){
      for(int i=0; i <= vcHijos.size() -1 ; i++){
        String  cSql =  " SELECT  TRAREQXMODTRAMITE.ICVETRAMITE,TRAREQXMODTRAMITE.ICVEModalidad, trareqxmodtramite.ICVEREQUISITO,cdscrequisito, cdsctramite, cdscmodalidad,TRACatTramite.cCveInterna || ' - ' || TRACatTramite.cDscBreve AS cCveDesc, TRARequisito.cDscBreve AS cDscBreveRequisito, TRAREQXMODTRAMITE.iOrden, TRAREQXMODTRAMITE.lRequerido,CT.ICVEFORMATO "+                "FROM TRAREQXMODTRAMITE " +
                "join TRAREQUISITO on trareqxmodtramite.ICVEREQUISITO = trarequisito.ICVEREQUISITO " +
                "join TRACATTRAMITE on TRAREQXMODTRAMITE.ICVETRAMITE = tracattramite.ICVETRAMITE " +
                "join TRAMODALIDAD on TRAREQXMODTRAMITE.ICVEMODALIDAD = tramodalidad.ICVEMODALIDAD " +
                "JOIN TRACONFIGURATRAMITE CT ON CT.ICVETRAMITE=TRAREQXMODTRAMITE.ICVETRAMITE AND CT.ICVEMODALIDAD=TRAREQXMODTRAMITE.ICVEMODALIDAD "+
                "where TRAREQXMODTRAMITE.icvetramite = "+ ( (TVDinRep) vcHijos.get(i)).getInt("iCveTramiteHijo")+" and TRAREQXMODTRAMITE.ICVEMODALIDAD = " +  ( (TVDinRep) vcHijos.get(i)).getInt("iCveModalidadHijo")+
                " order by TRAREQXMODTRAMITE.iCveTramite, TRAREQXMODTRAMITE.iCvemodalidad, TRAREQXMODTRAMITE.lRequerido DESC, TRAREQXMODTRAMITE.iOrden";
        Vector vcArreglo = new Vector();
        try{
          vcArreglo = dTRAReqXModTramite.findByCustom("",cSql);
          vcListado.addAll(vcArreglo);
        }catch(Exception e){
          cError += e.getMessage();
        }
      }
    }*/
  oAccion.navega(vcListado);
  String cNavStatus = oAccion.getCNavStatus();
%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
<script language="JavaScript">
<%
   out.print(oAccion.getArrayCD());
   System.out.print("*****     "+cURLDespliegaFormato);
%>
  fEngResultado('<%=request.getParameter("cNombreFRM")%>',
                '<%=request.getParameter("cId")%>',
                '<%=cError%>',
                '<%=cNavStatus%>',
                '<%=oAccion.getIRowPag()%>',
                '<%=oAccion.getBPK()%>',
                '<%=cURLDespliegaFormato%>');
</script>
<%}%>
