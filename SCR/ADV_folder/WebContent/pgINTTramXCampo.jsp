<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%
/**
 * <p>Title: pgINTCampos.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad INTCampos</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: SCT </p>
 * @author Arturo López Peña
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDINTTramXCampo dINTCampos = new TDINTTramXCampo();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("iCveCampoI,iCveTramite,iCveModalidad,iCveRequisito");
    try{
      vDinRep = dINTCampos.insert(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
  
 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveRequisito,iCveCampo,cCarpeta");
    try{
      vDinRep = dINTCampos.update(vDinRep,null);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
 
  if(oAccion.getCAccion().equals("Subir")){
	    vDinRep = oAccion.setInputs("iOrden,iOrden2,iCveTramite,iCveModalidad,iCveRequisito,iCveCampo");
	    
	    try{
	    	vDinRep.put("iLugar1",vDinRep.getInt("iOrden"));
	    	vDinRep.put("iLugar2",0);
	      vDinRep = dINTCampos.Posicion(vDinRep,null);
	      
	      vDinRep.remove("iLugar1");
	      vDinRep.remove("iLugar2");
	    	vDinRep.put("iLugar1",vDinRep.getInt("iOrden2"));
	    	vDinRep.put("iLugar2",vDinRep.getInt("iOrden"));
		    vDinRep = dINTCampos.Posicion(vDinRep,null);
		    
		    vDinRep.remove("iLugar1");
		    vDinRep.remove("iLugar2");
	    	vDinRep.put("iLugar1",0);
	    	vDinRep.put("iLugar2",vDinRep.getInt("iOrden2"));
		    vDinRep = dINTCampos.Posicion(vDinRep,null);
	    }catch(Exception e){
	      cError="Guardar";
	    }
	    oAccion.setBeanPK(vDinRep.getPK());
	  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){
    oAccion.setCAccion("Actual");
    vDinRep = oAccion.setInputs("iCveCampo,iCveTramite,iCveModalidad,iCveRequisito");
    try{
    	dINTCampos.delete(vDinRep,null);
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else 
        cError="Borrar";
    }
  }
  
 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  Vector vcListado = dINTCampos.findByCustom("iCveTramite,iCveModalidad,iCveRequisito,iCveCampo,","SELECT TC.ICVECAMPO,C.CCAMPO,TC.iOrden,TC.cCarpeta FROM INTTRAMXCAMPO TC JOIN INTCAMPOS C ON C.ICVECAMPO=TC.ICVECAMPO " + oAccion.getCFiltro() + oAccion.getCOrden());
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
