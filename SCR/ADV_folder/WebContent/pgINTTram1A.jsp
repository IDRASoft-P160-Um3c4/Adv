<%@ page import="java.util.*"%>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*"%>
<%@ page import="com.micper.seguridad.vo.*"%>
<%@ page import="com.micper.seguridad.dao.*"%>
<%@ page import="gob.sct.sipmm.dao.*"%>
<%
	/**
	 * <p>Title: pgINTTram1.jsp</p>
	 * <p>Description: JSP "Catálogo" de la entidad CCAPersona</p>
	 * <p>Copyright: Copyright (c) 2005 </p>
	 * <p>Company: Tecnología InRed S.A. de C.V. </p>
	 * @author Adri
	 * @version 1.0  
	 */
	TLogger.setSistema("44");
	TParametro vParametros = new TParametro("44");
	
	//System.out.print("entra en pgINTTram1A");
	
	TVDinRep vDinRep = new TVDinRep(); 
	TDINTTRAMITES dObjeto = new TDINTTRAMITES();
	String cError = "";
	CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
	String guardado = new String("");
	/** Verifica si existe una o más sesiones */
	if (!oAccion.unaSesion(vParametros, (CFGSesiones) application
			.getAttribute("Sesiones"), (TVUsuario) request.getSession(
			true).getAttribute("UsrID")))
		out.print(oAccion.getErrorSesion(vParametros
				.getPropEspecifica("RutaFuncs")));
	else
	{
		//System.out.print(">>>>>>>>>>>oAccion.getCAccion(): "+oAccion.getCAccion());
	  /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
	  if(oAccion.getCAccion().equals("Guardar"))
	  {
		vDinRep = oAccion.setInputs("iEjercicio,iNumSolicitud,ICVETIPOPERMISIONA,ICVETIPOTRAMITE,ICVEDEPARTAMENTO");
		try{ vDinRep = dObjeto.insert(vDinRep, null); }
		catch(Exception e)
		{
		  e.printStackTrace();
		  cError = "Guardar";
	     }
		 oAccion.setBeanPK(vDinRep.getPK());
	  }
	  else if(oAccion.getCAccion().equals("Buscar"))
	  {
		vDinRep = oAccion.setInputs("iNumCita");
	  }
	  else if(oAccion.getCAccion().equals("Borrar"))
	  {
			vDinRep = oAccion.setInputs("ICVETRAMITE,ICONSECUTIVO");
			try{ 
				 TDINTSolicitud dINTSolicitud = new TDINTSolicitud();
				 dINTSolicitud.delete(vDinRep, null);
				 dObjeto.delete(vDinRep, null);
			}
			catch(Exception e)
			{
			   e.printStackTrace();
			   cError = "Borrar";
			}
			oAccion.setBeanPK(vDinRep.getPK());
	  }
	  else if(oAccion.getCAccion().equals("Finalizar"))
	  {
		vDinRep = oAccion.setInputs("ICVETRAMITE");
		try{ dObjeto.finaliza(vDinRep, null); }
		catch (Exception e)
		{
		  e.printStackTrace();
		  cError = "Finalizar";
		}
		oAccion.setBeanPK(vDinRep.getPK());
	  }
	  else if(oAccion.getCAccion().equals("GuardarB"))
	  {
		vDinRep = oAccion.setInputs("ICVETRAMITE,ICVETIPOPERMISIONA,ICVETIPOTRAMITE,ICVEDEPTO");
		vDinRep.put("ICVEDEPARTAMENTO",vDinRep.getInt("ICVEDEPTO"));
		try{ vDinRep = dObjeto.insert(vDinRep, null); }
		catch(Exception e)
		{
		  e.printStackTrace();
		  cError = "Guardar";
		}
		oAccion.setBeanPK(vDinRep.getPK());
	  }
	  else if(oAccion.getCAccion().equals("GuardarSol"))
	  {
		vDinRep = oAccion.setInputs("iCveTramite,iCveModalidad,iCveSolicitante,iCveOficina,cRFCRep,cCveInterna,cModalidad");
		//vDinRep.put("ICVEDEPARTAMENTO",vDinRep.getInt("ICVEDEPTO"));
		try{ vDinRep = dObjeto.insertSol(vDinRep, null); }
		catch(Exception e)
		{
		  e.printStackTrace();
		  cError = "Guardar";
		}
		oAccion.setBeanPK(vDinRep.getPK());
	  }
	  else if(oAccion.getCAccion().equals("GuardarSolADV"))
	  {
		vDinRep = oAccion.setInputs("cRFCRep,iCveRepLegal,iCveSolicitante,iCveTramite,iCveModalidad,iCveCarretera,iCveOficina,cHechos,cKmSentido,cAutorizado,hdNomAuto,cCadReqs");
		
		//vDinRep.put("ICVEDEPARTAMENTO",vDinRep.getInt("ICVEDEPTO"));
		try{ vDinRep = dObjeto.insertSolADV(vDinRep, null); }
		catch(Exception e)
		{
		  e.printStackTrace();
		  cError = "Guardar";
		}
		oAccion.setBeanPK(vDinRep.getPK());
	  }
	  else if(oAccion.getCAccion().equals("GuardarCE"))
	  {
		vDinRep = oAccion.setInputs("ICVETRAMITE,ICVETIPOPERMISIONA,ICVETIPOTRAMITE,ICVEDEPTO");
		vDinRep.put("ICVEDEPARTAMENTO",vDinRep.getInt("ICVEDEPTO"));
		try{ vDinRep = dObjeto.insert(vDinRep, null); }
		catch(Exception e)
		{
		  e.printStackTrace();
		  cError = "Guardar";
		}
		oAccion.setBeanPK(vDinRep.getPK());
	  }
	  Vector vcListado = new Vector();
	  Vector vcDetalle = new Vector();
	  
	  
	  if(vDinRep.getInt("ID") > 0 || vDinRep.getInt("iNumCita")>0){
			  
			  String cSql = "SELECT " +
			                "s.iidCita,char(s.IEJERCICIO)||'/'||char(s.INUMSOLICITUD) as csolicitud,s.icvetramite,s.icvemodalidad,s.TSREGISTRO, " +
			                "t.CDSCBREVE,m.CDSCMODALIDAD,s.DTESTIMADAENTREGA, 1 as cconteo,"+
			                "s.IEJERCICIO,s.INUMSOLICITUD " +
			                "FROM TRAREGSOLICITUD s " +
			                "join TRACATTRAMITE t on t.ICVETRAMITE=s.ICVETRAMITE " +
			                "join tramodalidad m on m.ICVEMODALIDAD=s.ICVEMODALIDAD " +
			                "where s.IIdCita= "+
			                (vDinRep.getInt("ID") > 0 ? vDinRep.getInt("ID") : vDinRep.getInt("iNumCita"))
			                + oAccion.getCFiltro() + oAccion.getCOrden();
			  //System.out.print(">>>>>>>>>>>-----cSql: "+cSql);
				vcListado = dObjeto.findByCustom("", cSql);
				vcDetalle = null;
				
				
				for(int i=0; i < vcListado.size(); i++){ 
		
					vcDetalle = dObjeto.findByCustom("", "select COUNT(*) as CONTEO from TRAREGSOLICITUD S " +
							                                 "left join INTTRAMITEDETALLE d on d.iejercicio=s.iejercicio and d.INUMSOLICITUD=s.INUMSOLICITUD " +
							                                 "where s.IIDCITA = " + (vDinRep.getInt("ID") > 0 ? vDinRep.getInt("ID") : vDinRep.getInt("iNumCita")) );
					((TVDinRep)vcListado.get(i)).put("CONTEO",((TVDinRep)vcDetalle.get(0)).getInt("CONTEO"));
				}
	  }
		
		oAccion.navega(vcListado);
		if(vcDetalle != null)
	      guardado = ((TVDinRep) vcDetalle.get(0)).getString("CONTEO");
		else
			guardado = "0";
		String cNavStatus = oAccion.getCNavStatus();
		
%>
<SCRIPT LANGUAGE="JavaScript"
	SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
<script language="JavaScript">
<%out.print(oAccion.getArrayCD());%>
  fEngResultado('<%=request.getParameter("cNombreFRM")%>',
                '<%=request.getParameter("cId")%>',
                '<%=cError%>',
                '<%=cNavStatus%>',
                '<%=oAccion.getIRowPag()%>',
                '<%=oAccion.getBPK()%>',
                '<%=guardado%>');
</script>
<%
	}
%>
