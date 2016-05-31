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
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;
  TDTRARegReqXTramb dTRARegReqXTram = new TDTRARegReqXTramb();
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  String strOficios="";

  //TVUsuario vUsuario = (TVUsuario)request.getSession(true).getAttribute("UsrID");
  int iCveUsuario = Integer.parseInt(request.getParameter("iCveUsuario")==null?"0":request.getParameter("iCveUsuario"));

  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{
  /** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
  if(oAccion.getCAccion().equals("Guardar")){
    vDinRep = oAccion.setInputs("");
    try{

    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }

 /** Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) */
  if(oAccion.getCAccion().equals("GuardarA")){
    vDinRep = oAccion.setInputs("");
    try{
      
    }catch(Exception e){
      cError="Guardar";
    }
    oAccion.setBeanPK(vDinRep.getPK());
  }
 
 
  if(oAccion.getCAccion().equals("buscaDocumentosEtapa")){
	    vDinRep = oAccion.setInputs("iCveEtapa,iEjercicio,iNumSolicitud");
	    try{
	    	strOficios = dTRARegReqXTram.buscaDocumentosEtapa(vDinRep, null);
	    }catch(Exception e){
	      cError="Guardar";
	    }
	    oAccion.setBeanPK(vDinRep.getPK());
	  }
  
  if(oAccion.getCAccion().equals("buscaDocumentosDAJL")){
	    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud");
	    try{
	    	strOficios = dTRARegReqXTram.buscaDocumentosDAJLDGST(vDinRep, null, "DAJL");
	    }catch(Exception e){
	      cError="Guardar";
	    }
	    oAccion.setBeanPK(vDinRep.getPK());
	  }
  
  if(oAccion.getCAccion().equals("buscaDocumentosDGST")){
	    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud");
	    try{
	    	strOficios = dTRARegReqXTram.buscaDocumentosDAJLDGST(vDinRep, null, "DGST");
	    }catch(Exception e){
	      cError="Guardar";
	    }
	    oAccion.setBeanPK(vDinRep.getPK());
	  }
  
  if(oAccion.getCAccion().equals("buscaDocumentosDGDC")){
	    vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iCveEtapa");
	    try{
	    	strOficios = dTRARegReqXTram.buscaDocumentosDGDC(vDinRep, null);
	    }catch(Exception e){
	      cError="Guardar";
	    }
	    oAccion.setBeanPK(vDinRep.getPK());
	  }
 /** Verifica si la Acción a través de hdBotón es igual a "Cambia" (UpDate) */
  
  String cSQL="";
  String CID=  (String)request.getParameter("cId");
  
  if(CID.equals("buscaOficiosSolicitud")){
	  					//		0			1					2					3                       4
	  cSQL="SELECT CAT.ICVEOFICIO COLA, CAT.CDSCOFICIO COLB, CAT.ICVEGRUPO COLC, CAT.LPARAPNC COLD, COALESCE(REG.IIDGESTORDOCUMENTO,-1) COLE, REG.DTREG COLF, REG.CUSR COLG FROM GRLOFICIOADV CAT "
			+"  LEFT JOIN (SELECT REGOF.ICVEOFICIOADV, REGOF.IIDGESTORDOCUMENTO, DATE(REGOF.TSREGISTRO) DTREG,"
		    +"  USR.CNOMBRE||' '||USR.CAPPATERNO||' '|| USR.CAPMATERNO||' ( ' || OFI.CDSCBREVE || ' )'  CUSR FROM TRAREGOFICIOADV REGOF " 
			+"  	  INNER JOIN GRLDOCFOLIOCM DOCCM ON DOCCM.IIDGESTORDOCUMENTO = REGOF.IIDGESTORDOCUMENTO" 
			+"	INNER JOIN SEGUSUARIO USR ON USR.ICVEUSUARIO = REGOF.ICVEUSUARIO "
  			+"	INNER JOIN GRLUSUARIOXOFIC USRXOF ON USR.ICVEUSUARIO = USRXOF.ICVEUSUARIO"
  			+"	INNER JOIN GRLOFICINA OFI ON OFI.ICVEOFICINA = USRXOF.ICVEOFICINA"
		    + oAccion.getCFiltro() + ") REG ON CAT.ICVEOFICIO = REG.ICVEOFICIOADV " 
			+"  	  WHERE CAT.LPARAPNC = 0 ORDER BY CAT.ICVEOFICIO";
  }else
  if(CID.equals("buscaOficiosSolicitudPNC")){
	  cSQL="SELECT CAT.ICVEOFICIO COLA, CAT.CDSCOFICIO COLB, CAT.ICVEGRUPO COLC, CAT.LPARAPNC COLD, COALESCE(REG.IIDGESTORDOCUMENTO,-1) COLE, REG.DTREG COLF, REG.CUSR COLG FROM GRLOFICIOADV CAT "
				+"  LEFT JOIN (SELECT REGOF.ICVEOFICIOADV, REGOF.IIDGESTORDOCUMENTO, DATE(REGOF.TSREGISTRO) DTREG,"
			    +"  USR.CNOMBRE||' '||USR.CAPPATERNO||' '|| USR.CAPMATERNO||' ( ' || OFI.CDSCBREVE || ' )'  CUSR FROM TRAREGOFICIOADV REGOF " 
				+"  	  INNER JOIN GRLDOCFOLIOCM DOCCM ON DOCCM.IIDGESTORDOCUMENTO = REGOF.IIDGESTORDOCUMENTO" 
				+"	INNER JOIN SEGUSUARIO USR ON USR.ICVEUSUARIO = REGOF.ICVEUSUARIO "
	  			+"	INNER JOIN GRLUSUARIOXOFIC USRXOF ON USR.ICVEUSUARIO = USRXOF.ICVEUSUARIO"
	  			+"	INNER JOIN GRLOFICINA OFI ON OFI.ICVEOFICINA = USRXOF.ICVEOFICINA"
			    + oAccion.getCFiltro() + ") REG ON CAT.ICVEOFICIO = REG.ICVEOFICIOADV " 
				+"  	  WHERE CAT.LPARAPNC = 1 ORDER BY CAT.ICVEOFICIO ";
  }else
  if(CID.equals("buscaRegistroOficio")){
	  cSQL="SELECT "
		        +" OFI.ICVEOFICIO COLA, "
		        +" REGOFF.IIDGESTORDOCUMENTO COLB, "
		        +" OFI.CDSCOFICIO COLC  "
		        +" FROM GRLOFICIOADV OFI  "
		        +" LEFT JOIN (SELECT ICVEOFICIOADV, IIDGESTORDOCUMENTO FROM TRAREGOFICIOADV REGOF "
		        + oAccion.getCFiltro() +") REGOFF ON REGOFF.ICVEOFICIOADV = OFI.ICVEOFICIO "
		        +" WHERE OFI.ICVEOFICIO ="+(String)request.getParameter("iCveOficio");
  } else if(CID.equals("obtenerDiasDesdeUltimaEtapa")){
	  
	  vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud");
	  
	    String iEjercicio = vDinRep.getString("iEjercicio");
		String iNumSolicitud = vDinRep.getString("iNumSolicitud");
		
		cSQL = "select (YEAR(current_date - date(TRE.tsregistro))*365 + MONTH(current_date - date(TRE.tsregistro))*30 + DAY(current_date - date(TRE.tsregistro))) AS DIASTRANS "
					+"from TRAREGETAPASXMODTRAM TRE  "
					+"where TRE.IORDEN = (SELECT MAX(TAB.IORDEN) FROM TRAREGETAPASXMODTRAM TAB WHERE TAB.IEJERCICIO = "+iEjercicio+" AND TAB.INUMSOLICITUD = "+iNumSolicitud+") " 
					+"AND TRE.IEJERCICIO="+iEjercicio+" AND TRE.INUMSOLICITUD ="+iNumSolicitud;
	 
}	else if(CID.equals("registraRetraso")){
	  vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iDiasUltimaEtapa,iCveEtapa,iCveUsuario");
	  strOficios = dTRARegReqXTram.registraRetraso(vDinRep, null);
}  else if(CID.equals("registraRetrasoDAJLDGST")){ 
	//ya que ambos tienen el mismo tiempo para evaluar se ocupa el mismo metodo
	  vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iDiasUltimaEtapa,iCveUsuario");
	  strOficios = dTRARegReqXTram.registraRetrasoDAJLDGST(vDinRep, null);
}  
  
else if(CID.equals("buscaRetraso")){
	
	vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud");
	  
    String iEjercicio = vDinRep.getString("iEjercicio");
	String iNumSolicitud = vDinRep.getString("iNumSolicitud");
	  
	cSQL = "SELECT SUM(INUMDIAS) ISUMA FROM TRAREGRETRASO WHERE IEJERCICIO = "+ iEjercicio +" AND INUMSOLICITUD="+iNumSolicitud;
}
  
  

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
 Vector vcListado = null;
 
 if(!cSQL.equals("")){
	 try{
	     vcListado = dTRARegReqXTram.findByCustom("iEjercicio,iNumSolicitud",cSQL);
	     oAccion.navega(vcListado);
	 }catch(Exception ex){
		 cError = "Error al buscar!"; 
	 }
 }
 
 
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
                '<%=strOficios%>');
</script>
<%}%>
