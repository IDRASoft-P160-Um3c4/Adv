<%@ page import="java.util.*"%>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*"%>
<%@ page import="com.micper.seguridad.vo.*"%>
<%@ page import="com.micper.seguridad.dao.*"%>
<%@ page import="gob.sct.sipmm.dao.*"%>
<%@ page import="com.micper.sql.*"%>
<%
/**
 * <p>Title: pgGRLPersona.jsp</p>
 * <p>Description: JSP "Catálogo" de la entidad GRLPersona</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author Sandor Trujillo Q.
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDGRLPersona dGRLPersona = new TDGRLPersona();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  String cSQL ="";
  Vector vcListado=null;
  
  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
	  
  //System.out.println(oAccion.getCAccion());
	  
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("cRFC,hdiTipo,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,iCveEntidadFed,iCveMunicipio,iCveLocalidad");
    try{
      vDinRep = dGRLPersona.insertPersonaDomicilioADV(vDinRep,null);
      cSQL="SELECT GRLPERSONA.ICVEPERSONA AS ICOLA, "+
    		  "GRLDOMICILIO.ICVEDOMICILIO AS ICOLB "+
    		  "FROM GRLPERSONA  "+
    		  "INNER JOIN GRLDOMICILIO ON GRLDOMICILIO.ICVEPERSONA = GRLPERSONA.ICVEPERSONA "+
    		  "WHERE GRLPERSONA.ICVEPERSONA = "+ vDinRep.getString("iCvePersona");
      vcListado = dGRLPersona.findByCustom("",cSQL);
      
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("iCvePersona,iCveDomicilio,cRFC,hdiTipo,cNomRazonSocial,cApPaterno,cApMaterno,cCorreoE,cCalle,cNumExterior,cNumInterior,cColonia,cCodPostal,cTelefono,iCveEntidadFed,iCveMunicipio,iCveLocalidad");
    try{
      vDinRep = dGRLPersona.updatePersonaDomicilioADV(vDinRep,null);
      cSQL="SELECT GRLPERSONA.ICVEPERSONA AS ICOLA, "+
    		  "GRLDOMICILIO.ICVEDOMICILIO AS ICOLB "+
    		  "FROM GRLPERSONA  "+
    		  "INNER JOIN GRLDOMICILIO ON GRLDOMICILIO.ICVEPERSONA = GRLPERSONA.ICVEPERSONA "+
    		  "WHERE GRLPERSONA.ICVEPERSONA = "+ vDinRep.getString("iCvePersona");
      vcListado = dGRLPersona.findByCustom("",cSQL);
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "Borrar" (Ya sea físico o lógico) */
  if(oAccion.getCAccion().equals("Borrar")){

	  try{
		  vDinRep = oAccion.setInputs("iCvePersona");
		  cSQL = "SELECT INUMSOLICITUD FROM TRAREGSOLICITUD WHERE ICVESOLICITANTE = " + vDinRep.getInt("iCvePersona");
		  vcListado = dGRLPersona.findByCustom("",cSQL);
		  
		  if(!(vcListado!=null && vcListado.size()>0))		  
		  	 dGRLPersona.borrarPersonaDomicilio(vDinRep,null);
  
    }catch(Exception ex){
      if(ex.getMessage().equals("")==false){
        cError="Cascada";
      }else
        cError="Borrar";
    }
  }
 
  
  if(oAccion.getCAccion().equals("guardarRepresentanteEmergente")){
	  vDinRep = oAccion.setInputs("iCveRepL,iCveRepresentado");
	  //System.out.println(vDinRep.getInt("iCveRepL"));
	  //System.out.println(vDinRep.getInt("iCveRepresentado"));
	  dGRLPersona.guardaAsociacionPersonaRepL(vDinRep,null);
  }else if(oAccion.getCAccion().equals("eliminarAsociacionRepresentante")){
	  vDinRep = oAccion.setInputs("iCveRepL,iCveRepresentado");
	  //System.out.println(vDinRep.getInt("iCveRepL"));
	  //System.out.println(vDinRep.getInt("iCveRepresentado"));
	  dGRLPersona.borraAsociacionPersonaRepL(vDinRep,null);
  }else if(oAccion.getCAccion().equals("setearRepresentantePrincipal")){
	  vDinRep = oAccion.setInputs("iCveRepL,iCveRepresentado");
	  //System.out.println(vDinRep.getInt("iCveRepL"));
	  //System.out.println(vDinRep.getInt("iCveRepresentado"));
	  dGRLPersona.setPrincipal(vDinRep,null);
  }
  else if(oAccion.getCAccion().equals("getEntidades")){
  	cSQL ="SELECT ICVEENTIDADFED, CNOMBRE FROM GRLENTIDADFED WHERE ICVEPAIS = 1";
	 vcListado = dGRLPersona.findByCustom("",cSQL);
 }
 else if(oAccion.getCAccion().equals("getMunicipios")){
	 vDinRep = oAccion.setInputs("iCveEntidadFed");
 	 cSQL ="select ICVEMUNICIPIO, CNOMBRE FROM GRLMUNICIPIO WHERE ICVEPAIS = 1 AND ICVEENTIDADFED = "+vDinRep.getInt("iCveEntidadFed");
	 vcListado = dGRLPersona.findByCustom("",cSQL);
 }
 else if(oAccion.getCAccion().equals("getLocalidades")){
	 vDinRep = oAccion.setInputs("iCveEntidadFed,iCveMunicipio");
 	 cSQL ="SELECT ICVELOCALIDAD, CNOMBRE FROM GRLLOCALIDAD WHERE ICVEPAIS = 1 AND ICVEENTIDADFED ="+vDinRep.getInt("iCveEntidadFed")+" AND ICVEMUNICIPIO ="+vDinRep.getInt("iCveMunicipio");
	 vcListado = dGRLPersona.findByCustom("",cSQL);
 }else if(oAccion.getCAccion().equals("buscaPersona")){

	 cSQL =  "SELECT GRLPERSONA.ICVEPERSONA AS COLA, "+
			 "GRLDOMICILIO.ICVEDOMICILIO AS COLB, "+
			 "GRLPERSONA.CRFC AS COLC, "+
			 "GRLPERSONA.ITIPOPERSONA AS COLD, "+
			 "GRLPERSONA.CNOMRAZONSOCIAL AS COLE, "+
			 "GRLPERSONA.CAPPATERNO AS COLF, "+
			 "GRLPERSONA.CAPMATERNO AS COLG, "+
			 "GRLPERSONA.CCORREOE AS COLH, "+
			 "GRLDOMICILIO.CCALLE AS COLI, "+
			 "GRLDOMICILIO.CNUMEXTERIOR AS COLJ, "+
			 "GRLDOMICILIO.CNUMINTERIOR AS COLK, "+
			 "GRLDOMICILIO.CCOLONIA AS COLL, "+
			 "GRLDOMICILIO.CCODPOSTAL AS COLM, "+
			 "GRLDOMICILIO.CTELEFONO AS COLN, "+
			 "GRLDOMICILIO.ICVEENTIDADFED AS COLO, "+
			 "GRLDOMICILIO.ICVEMUNICIPIO AS COLP, "+
			 "GRLDOMICILIO.ICVELOCALIDAD AS COLQ, "+
			 "REPL.ICVEPERSONA AS COLAA, "+
			 "DOMREPL.ICVEDOMICILIO AS COLBB, "+
			 "REPL.CRFC AS COLCC, "+
			 "REPL.ITIPOPERSONA AS COLDD, "+
			 "REPL.CNOMRAZONSOCIAL AS COLEE, "+
			 "REPL.CAPPATERNO AS COLFF, "+
			 "REPL.CAPMATERNO AS COLGG, "+
			 "REPL.CCORREOE AS COLHH, "+
			 "DOMREPL.CCALLE AS COLII, "+
			 "DOMREPL.CNUMEXTERIOR AS COLJJ, "+
			 "DOMREPL.CNUMINTERIOR AS COLKK, "+
			 "DOMREPL.CCOLONIA AS COLLL, "+
			 "DOMREPL.CCODPOSTAL AS COLMM, "+
			 "DOMREPL.CTELEFONO AS COLNN, "+
			 "GRLPAIS.CNOMBRE AS COLOO, "+
			 "GRLENTIDADFED.CNOMBRE AS COLPP, "+
			 "GRLMUNICIPIO.CNOMBRE AS COLQQ, "+
			 "GRLLOCALIDAD.CNOMBRE AS COLRR "+
			 "FROM GRLPERSONA  "+
			 "INNER JOIN GRLDOMICILIO ON GRLDOMICILIO.ICVEPERSONA = GRLPERSONA.ICVEPERSONA "+
			 "LEFT JOIN  GRLREPLEGAL ON GRLREPLEGAL.ICVEEMPRESA = GRLPERSONA.ICVEPERSONA AND GRLREPLEGAL.LPRINCIPAL = 1 "+
			 "LEFT JOIN GRLPERSONA REPL ON REPL.ICVEPERSONA = GRLREPLEGAL.ICVEPERSONA "+
			 "LEFT JOIN GRLDOMICILIO DOMREPL ON DOMREPL.ICVEPERSONA = REPL.ICVEPERSONA "+
			 "LEFT JOIN GRLPAIS ON  "+
			 " 	DOMREPL.ICVEPAIS = GRLPAIS.ICVEPAIS AND GRLPAIS.ICVEPAIS=1 "+
			 "LEFT JOIN GRLENTIDADFED ON  "+
			 " 	DOMREPL.ICVEENTIDADFED = GRLENTIDADFED.ICVEENTIDADFED AND GRLENTIDADFED.ICVEPAIS=1 "+
			 "LEFT JOIN GRLMUNICIPIO ON  "+
			 " 	DOMREPL.ICVEMUNICIPIO = GRLMUNICIPIO.ICVEMUNICIPIO AND GRLMUNICIPIO.ICVEENTIDADFED = GRLENTIDADFED.ICVEENTIDADFED AND GRLMUNICIPIO.ICVEPAIS=1 "+
			 "LEFT JOIN GRLLOCALIDAD ON  "+
			 "	DOMREPL.ICVELOCALIDAD = GRLLOCALIDAD.ICVELOCALIDAD AND GRLLOCALIDAD.ICVEMUNICIPIO = GRLMUNICIPIO.ICVEMUNICIPIO AND GRLLOCALIDAD.ICVEENTIDADFED = GRLENTIDADFED.ICVEENTIDADFED AND GRLLOCALIDAD.ICVEPAIS = 1 ";
	 vcListado = dGRLPersona.findByCustom("",cSQL + oAccion.getCFiltro() + oAccion.getCOrden());
 }else if(oAccion.getCAccion().equals("buscaRepresentantesAsignados")){

	 vDinRep = oAccion.setInputs("iCveRepresentado");
			 
	 cSQL =  "SELECT "
			 +"GRLPERSONA.ICVEPERSONA AS COLA, "
			 +"GRLDOMICILIO.ICVEDOMICILIO AS COLB, "
			 +"GRLPERSONA.CRFC AS COLC, "
			 +"GRLPERSONA.ITIPOPERSONA AS COLD, "
			 +"GRLPERSONA.CNOMRAZONSOCIAL || ' ' || GRLPERSONA.CAPPATERNO || ' ' || GRLPERSONA.CAPMATERNO AS COLE, "
			 +"'tel. '|| COALESCE(GRLDOMICILIO.CTELEFONO,'NA') ||'; email. ' || COALESCE(GRLPERSONA.CCORREOE,'NA') AS COLN, "
			 +"'calle. ' || COALESCE(GRLDOMICILIO.CCALLE,'NA') || ' ext. ' || COALESCE(GRLDOMICILIO.CNUMEXTERIOR,'NA') || ' int. ' || COALESCE(GRLDOMICILIO.CNUMINTERIOR,'NA') || ' col. ' ||COALESCE(GRLDOMICILIO.CCOLONIA,'NA')||' cp. '||COALESCE(GRLDOMICILIO.CCODPOSTAL,'NA')||', '|| GRLMUNICIPIO.CNOMBRE||', '|| GRLENTIDADFED.CNOMBRE AS COLI, "
		     +"GRLREPLEGAL.LPRINCIPAL as COLR, "
		     +"GRLPERSONA.ITIPOPERSONA AS COLZ " 
			 +"FROM GRLPERSONA  INNER "
			 +"JOIN GRLDOMICILIO ON GRLDOMICILIO.ICVEPERSONA = GRLPERSONA.ICVEPERSONA "
			 +"JOIN GRLREPLEGAL ON GRLPERSONA.ICVEPERSONA = GRLREPLEGAL.ICVEPERSONA "
			 +"JOIN GRLPERSONA EMP ON GRLREPLEGAL.ICVEEMPRESA = EMP.ICVEPERSONA "
			 +"LEFT JOIN GRLPAIS ON GRLDOMICILIO.ICVEPAIS = GRLPAIS.ICVEPAIS AND GRLPAIS.ICVEPAIS=1 "
			 +"LEFT JOIN GRLENTIDADFED ON GRLDOMICILIO.ICVEENTIDADFED = GRLENTIDADFED.ICVEENTIDADFED AND GRLENTIDADFED.ICVEPAIS=1 "   
			 +"LEFT JOIN GRLMUNICIPIO ON GRLDOMICILIO.ICVEMUNICIPIO = GRLMUNICIPIO.ICVEMUNICIPIO AND GRLMUNICIPIO.ICVEENTIDADFED = GRLENTIDADFED.ICVEENTIDADFED AND GRLMUNICIPIO.ICVEPAIS=1 "
			 +"LEFT JOIN GRLLOCALIDAD ON  	GRLDOMICILIO.ICVELOCALIDAD = GRLLOCALIDAD.ICVELOCALIDAD AND GRLLOCALIDAD.ICVEMUNICIPIO = GRLMUNICIPIO.ICVEMUNICIPIO AND GRLLOCALIDAD.ICVEENTIDADFED = GRLENTIDADFED.ICVEENTIDADFED AND GRLLOCALIDAD.ICVEPAIS = 1 " 
			 +"WHERE  EMP.ICVEPERSONA = " + vDinRep.getString("iCveRepresentado")
			 +" ORDER BY  GRLPERSONA.CRFC ASC ";
	 vcListado = dGRLPersona.findByCustom("",cSQL);
 }else if(oAccion.getCAccion().equals("buscaRepresentanteEmergente")){

	 vDinRep = oAccion.setInputs("iCveRepL,iCveRepresentado");
			 
	 cSQL =  "SELECT "
		       +"GRLPERSONA.ICVEPERSONA AS COLA, "
		       +"GRLDOMICILIO.ICVEDOMICILIO AS COLB, "
		       +"GRLPERSONA.CRFC AS COLC, "
		       +"GRLPERSONA.ITIPOPERSONA AS COLD, "
		       +"GRLPERSONA.CNOMRAZONSOCIAL || ' ' || GRLPERSONA.CAPPATERNO || ' ' || GRLPERSONA.CAPMATERNO AS COLE, "
		       +"'TEL. '|| COALESCE(GRLDOMICILIO.CTELEFONO,'NA') ||'; EMAIL. ' || COALESCE(GRLPERSONA.CCORREOE,'NA') AS COLN, "
		       +"'CALLE. ' || COALESCE(GRLDOMICILIO.CCALLE,'NA') || ' EXT. ' || COALESCE(GRLDOMICILIO.CNUMEXTERIOR,'NA') || ' INT. ' || COALESCE(GRLDOMICILIO.CNUMINTERIOR, "
		       +"'NA') || ' COL. ' ||COALESCE(GRLDOMICILIO.CCOLONIA,'NA')||' CP. '||COALESCE(GRLDOMICILIO.CCODPOSTAL,'NA')||','|| GRLMUNICIPIO.CNOMBRE||','|| GRLENTIDADFED.CNOMBRE AS COLI,GRLPERSONA.ITIPOPERSONA AS COLZ "
		       +"FROM GRLPERSONA  INNER "
		       +"JOIN GRLDOMICILIO ON GRLDOMICILIO.ICVEPERSONA = GRLPERSONA.ICVEPERSONA "
		       +"LEFT JOIN GRLPAIS ON GRLDOMICILIO.ICVEPAIS = GRLPAIS.ICVEPAIS "
		       +"AND GRLPAIS.ICVEPAIS=1 "
		       +"LEFT JOIN GRLENTIDADFED ON GRLDOMICILIO.ICVEENTIDADFED = GRLENTIDADFED.ICVEENTIDADFED "
		       +"AND GRLENTIDADFED.ICVEPAIS=1 "
		       +"LEFT JOIN GRLMUNICIPIO ON GRLDOMICILIO.ICVEMUNICIPIO = GRLMUNICIPIO.ICVEMUNICIPIO "
		       +"AND GRLMUNICIPIO.ICVEENTIDADFED = GRLENTIDADFED.ICVEENTIDADFED "
		       +"AND GRLMUNICIPIO.ICVEPAIS=1 "
		       +"LEFT JOIN GRLLOCALIDAD ON  	GRLDOMICILIO.ICVELOCALIDAD = GRLLOCALIDAD.ICVELOCALIDAD "
		       +"AND GRLLOCALIDAD.ICVEMUNICIPIO = GRLMUNICIPIO.ICVEMUNICIPIO "
		       +"AND GRLLOCALIDAD.ICVEENTIDADFED = GRLENTIDADFED.ICVEENTIDADFED "
		       +"AND GRLLOCALIDAD.ICVEPAIS = 1 "
		       +"WHERE  GRLPERSONA.ICVEPERSONA NOT IN (SELECT ICVEPERSONA FROM GRLREPLEGAL WHERE ICVEEMPRESA="+ vDinRep.getString("iCveRepresentado")
		       +") AND GRLPERSONA.ICVEPERSONA <> "+ vDinRep.getString("iCveRepresentado")
		       +" AND GRLPERSONA.ICVEPERSONA = "+vDinRep.getString("iCveRepL")
		       +" ORDER BY  GRLPERSONA.CRFC ASC";
	 vcListado = dGRLPersona.findByCustom("",cSQL);
 }
 
 if(vcListado!=null)
  	oAccion.setINumReg(vcListado.size());
 
  oAccion.navega(vcListado);
  String cNavStatus = oAccion.getCNavStatus();
%>
<SCRIPT LANGUAGE="JavaScript"
	SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
<script language="JavaScript">
<%
   out.print(oAccion.getArrayCD());
%>
  fEngResultado('<%=request.getParameter("cNombreFRM")%>',
                '<%=request.getParameter("cId")%>',
                '<%=cError%>',
                '<%=cNavStatus%>');
</script>
<%}%>