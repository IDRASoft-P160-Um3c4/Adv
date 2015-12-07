<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pg1001102010A.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLDEPARTAMENTO</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Enrique Moreno Belmares
 * @version 1.0
 */
  
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLPais dVerNotifica = new TDGRLPais();
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */      
     if(oAccion.getCAccion().equals("Contesta")){
           try{
             request.getSession(true).removeAttribute("Notifica");
           }catch (Exception exc){}
           try{
             Vector vcCon = dVerNotifica.findByCustom("","SELECT " +
            		                                         "       TD.IEJERCICIO, " +
            		                                         "       TD.INUMSOLICITUD, " +
            		                                         "       TD.ICVEDOCDIG, " +
            		                                         "       S.IIDCITA " +
            		                                         "FROM INTTRAMITEDOCS TD " +
            		                                         "join TRAREGSOLICITUD s ON TD.IEJERCICIO=S.IEJERCICIO AND TD.INUMSOLICITUD=S.INUMSOLICITUD " + 
            		                                         "where ICVEDOCDIG = "+request.getParameter("ID")+
            		                                         " and ICVEUSUNOTIFICA = "+request.getParameter("IC")+ 
            		                                         " and ccampo = ''");
             if(vcCon.size() > 0){
                TVDinRep vcNota = (TVDinRep) vcCon.get(0);
                request.getSession(true).setAttribute("Notifica",vcNota.toHashMap()); 
             } 
           }catch(Exception e){}
%>           
           <SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>prop.js"></SCRIPT>
           <SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/componentes.js"></SCRIPT>
           <SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/INTfrmmi.js"></SCRIPT>
           <script language="JavaScript">var cPagNva='pgContestaNota.js';fPag();</script>
<%           
     }
  
     if(oAccion.getCAccion().equals("Show")){
         String cError = "", cNavStatus = "", cSQL = "";
         HashMap hmNotifica = (HashMap) request.getSession(true).getAttribute("Notifica");
             System.out.print(""+hmNotifica);
             cSQL =
      			" SELECT " +
      			  "TD.iEjercicio,"+
      			  "TD.iNumSolicitud, " +
      			  "ICVEDOCDIG, " +
      			  "TD.TSREGISTRO, " +
      			  "CDOCUMENTO, " +
      			  "CNOMARCHIVO, " +
      			  "CCAMPO, " +
      			  "ICVEUSUNOTIFICA, " +
      			  "TSREGNOTIFICA, " +
      			  "CTIPO, " +
      			  "COBSERVACION, " +
      			  "CDSCESTATUS, " +
      			  "S.IIDCITA "+
      			" FROM INTTRAMITEDOCS TD " +
      			" join TRAREGSOLICITUD s on s.iejercicio=TD.iejercicio and TD.iNumsolicitud=s.INUMSOLICITUD "+
      		  " LEFT JOIN INTESTATUS on TD.ICVEESTATUS = INTESTATUS.ICVEESTATUS " +
      			" where S.iEjercicio = " + hmNotifica.get("IEJERCICIO") +
      			" AND S.iNumSolicitud = " + hmNotifica.get("INUMSOLICITUD") +
      			" and TD.ICVEESTATUS = 38";
      			//" and cOBSERVACION like '%" + hmNotifica.get("ICVEDOCDIG") + "%'";
      		   // " and ICVEDOCDIG like '%" + hmNotifica.get("ICVEDOCDIG") + "%'";

             System.out.print(cSQL);
             
             Vector vcListado = dVerNotifica.findByCustom("",cSQL);
             TVDinRep vList = new TVDinRep();
             int iNumCita = 0;
             if(vcListado.size()>0){
            	 vList = (TVDinRep) vcListado.get(0);
            	 iNumCita = vList.getInt("IIDCITA");
            	 
             }
             oAccion.navega(vcListado);
             cNavStatus = oAccion.getCNavStatus();
         
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
                          <%=hmNotifica.get("IEJERCICIO")%>,
                          <%=hmNotifica.get("INUMSOLICITUD")%>,
                          <%=hmNotifica.get("IIDCITA")%>);
          </script>
          <%}%>
  
%>
<%}%>
