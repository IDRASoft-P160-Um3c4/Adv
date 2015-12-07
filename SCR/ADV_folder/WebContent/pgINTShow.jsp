<%@ page import="java.util.*"%>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*"%>
<%@ page import="com.micper.seguridad.vo.*"%>
<%@ page import="com.micper.seguridad.dao.*"%>
<%@ page import="gob.sct.sipmm.dao.*"%>
<%@ page import="com.micper.sql.*"%>
<%
	/**
	 * <p>Title: pgINTShow.jsp</p>
	 * <p>Description: JSP "Catálogo" de la entidad INTSolicitud</p>
	 * <p>Copyright: Copyright (c) 2005 </p>
	 * <p>Company: Tecnología InRed S.A. de C.V. </p>
	 * @author Adri
	 * @version 1.0
	 */
	TLogger.setSistema("44");
	TParametro vParametros = new TParametro("44");
	TVDinRep vDinRep = new TVDinRep();
	TDINTSolicitud dINTSolicitud = new TDINTSolicitud();
	String cError = "";
	String cSol = "";
	TVDinRep vFirma = new TVDinRep();

	CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
	CFGAccion oAccion1 = new CFGAccion(pageContext.getRequest());
	oAccion1.setINoRes("2");
	String cCad = "";
	/** Verifica si existe una o más sesiones */
	if (!oAccion.unaSesion(vParametros, (CFGSesiones) application
			.getAttribute("Sesiones"), (TVUsuario) request.getSession(
			true).getAttribute("UsrID"))) {
		out.print(oAccion.getErrorSesion(vParametros
				.getPropEspecifica("RutaFuncs")));
	} else {
		/** Verifica si la Acción a través de hdBotón es igual a "Guardar" */
		if (oAccion.getCAccion().equals("Guardar")) {
			vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,iNumCita,ICONSECUTIVO,cCamSol,cRequisitos");
					
			try {
				vDinRep = dINTSolicitud.insert(vDinRep, null);
			} catch (Exception e) {
				cError = "Guardar";
			}
		}

		//Verifica si la Acción a través de hdBotón es igual a "GuardarA" (Actualizar) 
		if (oAccion.getCAccion().equals("GuardarA")) {
			vDinRep = oAccion.setInputs("ICVETRAMITE,ICONSECUTIVO,cCamSol");
			try {
				vDinRep = dINTSolicitud.update(vDinRep, null);
			} catch (Exception e) {
				cError = "Guardar";
			}
		}

		/** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
		String cFiltro = oAccion.getCFiltro();
		String cSql = "";
		String cOs = "";
		Vector vcSol = new Vector();
		Vector vcListado = new Vector();
		boolean lShow = true;

		if (request.getParameter("ICVETRAMITE") != null && request.getParameter("ICONSECUTIVO") != null) {
			cSql = "SELECT d.IEJERCICIO,D.INUMSOLICITUD, CCAMPO,CVALOR1,CVALOR2,IORDEN "+
			       "FROM INTTRAMITEDETALLE D "+
			       "JOIN TRAREGSOLICITUD S ON S.IEJERCICIO = D.IEJERCICIO AND S.INUMSOLICITUD=D.INUMSOLICITUD "+
			       "WHERE S.iIdCita=" + request.getParameter("iNumCita") + 
			       " ORDER BY IORDEN ";

			vcSol = dINTSolicitud.findByCustom("", cSql);
			oAccion1.navega(vcSol);
			if (vcSol.isEmpty()) {
				lShow = false;
			}
			cSql = "SELECT CCADORIGEN, CCADFIRMA, CFIRMANTE FROM INTFIRMADO WHERE ICVETRAMITE = " + request.getParameter("iNumCita") + 
		       " AND ICONSECUTIVO = 1";
			/*cSql = "SELECT CCADORIGEN, replace(CCADFIRMA,chr(10),'~') AS CCADFIRMA, CFIRMANTE FROM INTFIRMADO WHERE ICVETRAMITE = " + request.getParameter("iNumCita") +
			" AND ICONSECUTIVO = 1";
			Vector vcFirma = dINTSolicitud.findByCustom("", cSql);
			if(vcFirma.size() > 0){
				vFirma = (TVDinRep) vcFirma.get(0);
				cCad = vFirma.getString("CCADFIRMA");//.replace("\r","~");
			}*/

			
			Vector vcFirma = dINTSolicitud.findByCustom("", cSql);
			if(vcFirma.size() > 0){
			    System.out.print(System.getProperty("os.name"));
			    cOs = System.getProperty("os.name");
			    vFirma = (TVDinRep) vcFirma.get(0);
			    //cCad = vFirma.getString("CCADFIRMA").replace("\r\n","~");
			    //cCad = vFirma.getString("CCADFIRMA").replaceAll("\r", "~");
			    
			    if (cOs.startsWith("Windows")){
			    	System.out.print(">>>>>>>>>Windows");
                        cCad = vFirma.getString("CCADFIRMA").replaceAll("\r\n", "~"); //windows
			    }else{
			    	System.out.print(">>>>>>>>>Linux");
			      cCad = vFirma.getString("CCADFIRMA").replaceAll("\n", "~"); //linux
			    }
			    System.out.print(cCad);

			}
		}
		
		cSql = " SELECT INTTRAMXCAMPO.ICVECAMPO,CCAMPO,CETIQUETA,ILARGO,ICVETIPOCAMPO,CTABLA,CCVE,CDSC,LMANDATORIO,LFIJO,LENCABEZADO,LSELECCIONAR,CLIGADO,CSCRIPT,R.ICVEREQUISITO,R.CDSCBREVE,INTTRAMXCAMPO.IORDEN " +
		       " FROM INTTRAMXCAMPO " +
		       " JOIN INTCAMPOS ON INTTRAMXCAMPO.ICVECAMPO = INTCAMPOS.ICVECAMPO " +
		       " JOIN TRAREQUISITO R ON INTTRAMXCAMPO.ICVEREQUISITO = R.ICVEREQUISITO " +
		       " where INTTRAMXCAMPO.ICVETRAMITE = "+request.getParameter("iCveTramite")+" and INTTRAMXCAMPO.ICVEMODALIDAD = "+request.getParameter("iCveModalidad") +
		       " union " +
		       " SELECT 0 as ICVECAMPO,'cReq'||char(r.ICVEREQUISITO),r.CDSCBREVE,0,7,'','','',0,0,0,0,'','',500 as iCveRequisito,r.CDSCBREVE,(10000+mt.IORDEN) as iOrden " +
		       " FROM TRAREQXMODTRAMITE MT " +
		       " JOIN TRAREQUISITO R ON MT.ICVEREQUISITO=R.ICVEREQUISITO " +
		       "JOIN TRAREQDIGINT on TRAREQDIGINT.ICVEREQUISITO = R.ICVEREQUISITO " +
		       " where mt.ICVETRAMITE="+request.getParameter("iCveTramite")+" and mt.ICVEMODALIDAD= "+request.getParameter("iCveModalidad")+
		       " AND TRAREQDIGINT.IEJERCICIO ="+request.getParameter("iEjercicio")+" AND  TRAREQDIGINT.INUMSOLICITUD = "+request.getParameter("iNumSolicitud")+
		       " ORDER BY iOrden ";

		
		vcListado = dINTSolicitud.findByCustom("", cSql);
		
		System.out.print("Pasa el Querie");
		oAccion.navega(vcListado);
		String cNavStatus = oAccion.getCNavStatus();
%>
<SCRIPT LANGUAGE="JavaScript"
	SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
<script language="JavaScript">
aRes2 = new Array();
<%if (lShow) {
					out.print(oAccion1.getArrayCD());
				}
				out.print(oAccion.getArrayCD());%>
  fEngResultado('<%=request.getParameter("cNombreFRM")%>',
                '<%=request.getParameter("cId")%>',
                '<%=cError%>',
                '<%=cNavStatus%>',
                '<%=cSol%>',
                aRes2,
                '<%=vFirma.getString("CCADORIGEN")%>',
                '<%=cCad%>',                
                '<%=vFirma.getString("CFIRMANTE")%>');
</script>
<%
	}
%>
