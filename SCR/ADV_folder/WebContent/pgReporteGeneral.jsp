<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.util.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.reporte.*" %>
<%@ page import="com.micper.sql.*" %>
<body onLoad="JavaScript:fDocCargado();">
<%
/**
 * <p>Title: pgReporteGeneral.jsp</p>
 * <p>Description: JSP para generar un reporte</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ABarrientos<dd>Rafael Miranda Blumenkron
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;

  TReportes Reportes = new TReportes("44");
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());
  /** Verifica si existe una o más sesiones */
  TVDinRep vParametro = oAccion.setInputs("lAutoImprimir,iNumCopias,lMostrarAplicacion,cArchivoOrig,cNomDestino,cDAOEjecutar,cMetodoTemp,cDigitosFolio,iCveUsuario,lRequiereFolio,iCveOficina,iCveDepartamento,hdFiltrosRep,cFirmante,iNumSolicitud,iEjercicioSol,");
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  runMethodInClass tEjecuta = new runMethodInClass();
  StringBuffer sbResultado = new StringBuffer(""),
               sbObjeto    = new StringBuffer("");
  Object oDatos;
  Vector vDatosAdicionales = new Vector();
  HashMap hParametros = new HashMap();
  //Si esta configurado que necesita folio par2 se le asigna el folio, par3 se le asigna la cve de oficina  y par 4 se le asigna la cve de depto
  String par2 = "", par3 = "", par4 = "", par5="";

  TVDinRep vDatos = oAccion.setInputs("iCveOficina,iCveDepartamento,iNumCopias");

  try{
    String folio = "";
    if ( vParametro.getString("lRequiereFolio").equals("1") ){
       TDGRLFolio dGRLFolio = new TDGRLFolio();
       folio = dGRLFolio.asignaFolio( new Integer(vDatos.getInt("iCveOficina")).intValue(), new Integer(vDatos.getInt("iCveDepartamento")).intValue(), vParametro.getString("cDigitosFolio"), new Integer(vParametro.getString("iCveUsuario")).intValue() );
    }
    if (folio!=""){
      par2 = "Par2^"+folio+"$";
      if (!vParametro.getString("cFirmante").equals("")) par2 = "Par2^"+folio+"#"+vParametro.getString("cFirmante")+"$";
      par3 = "Par3^"+vParametro.getString("iCveOficina")+"$";
      par4 = "Par4^"+vParametro.getString("iCveDepartamento")+"$";
    }

    //Ejecuta runMethodInClass con los siguientes parámetros (ver documentación de runMethodInClass)
    // *vParametros.getPropEspecifica("cRutaDAO") - Contiene el package de donde se encuentra la clase
    // *request.getParameter("cDAOEjecutar") - Contiene el nombre de la clase donde se encuentra el método a ejecutar
    // *"Par1^" + request.getParameter("hdFiltrosRep") + "¨"+par2...   - Contiene los parámetros q recibe el método a ejecutar
    // *request.getParameter("cMetodoTemp") - Contiene el nombre del método a ejecutar
    //En este caso recibe como resultado los datos con los que se va a armar el reporte o los que van a reemplazar etiquetas en un documento word

	
	if((vParametro.getString("cDAOEjecutar").equals(".reporte.TDCapacidadJuridica"))&&(vParametro.getString("cMetodoTemp").equals("WordOficio"))){  //Reportes de Word XML...
		
		//System.out.print("param1--> "+vParametros.getPropEspecifica("cRutaDAO")+ vParametro.getString("cDAOEjecutar"));
		
		//System.out.print("param2--> "+vParametro.getString("cMetodoTemp"));
		
		//System.out.print("param3--> "+"Par1^" + vParametro.getString("hdFiltrosRep") +"," + vParametro.getString("cArchivoOrig") + "$"+par2+par3+par4+par5);
		
		oDatos = (Object)tEjecuta.runMethodInClass(vParametros.getPropEspecifica("cRutaDAO")+ vParametro.getString("cDAOEjecutar"),vParametro.getString("cMetodoTemp"), "Par1^" + vParametro.getString("hdFiltrosRep") +"," + vParametro.getString("cArchivoOrig") + "$"+par2+par3+par4+par5);
	}       
	else{  // Reportes Tradicionales
		//System.out.print("param1--> "+ vParametros.getPropEspecifica("cRutaDAO")+ vParametro.getString("cDAOEjecutar"));
	
		//System.out.print("param2--> "+ vParametro.getString("cMetodoTemp"));
		
		//System.out.print("param3--> "+ "Par1^" + vParametro.getString("hdFiltrosRep") + "$"+par2+par3+par4+par5);
		
		oDatos = (Object)tEjecuta.runMethodInClass(vParametros.getPropEspecifica("cRutaDAO")+ vParametro.getString("cDAOEjecutar"),vParametro.getString("cMetodoTemp"), "Par1^" + vParametro.getString("hdFiltrosRep") + "$"+par2+par3+par4+par5);
	}
			
    if(oDatos instanceof Vector){
      sbResultado = (StringBuffer)((Vector)oDatos).get(0);
      vDatosAdicionales = (Vector)((Vector)oDatos).get(1);
    }else if(oDatos instanceof StringBuffer){
      sbResultado = (StringBuffer)oDatos;
      vDatosAdicionales = new Vector();
    }else if(oDatos instanceof HashMap){
      sbResultado = new StringBuffer("Reporte en PDF");
      hParametros = (HashMap)oDatos;
    }
  }catch(Exception ex){ex.printStackTrace();  cError = ex.getMessage(); }
  boolean lAutoImprimir      = false,
          lMostrarAplicacion = false,
          lCerrarAlFinal     = false;
  int iNumCopias        = 1,
      iPausaEntreCopias = 3000;

  if (vParametro.getString("lAutoImprimir").equals("1"))
    lAutoImprimir = true;

  if (lAutoImprimir)
    iNumCopias = Integer.parseInt(vParametro.getString("iNumCopias"));
  if (iNumCopias <= 0)
    iNumCopias = 1;

  if (vParametro.getString("lMostrarAplicacion").equals("1"))
    lMostrarAplicacion = true;

  if (lAutoImprimir && !lMostrarAplicacion)
    lCerrarAlFinal = true;

  if (sbResultado == null || sbResultado.toString().equals(""))
    cError = "No hay datos para desplegar en el reporte";

  if (cError.equals("")){
    //Genera el reporte en excel
    if(oAccion.getCAccion().equals("Excel")){
    	
   	//System.out.print("lo que regresan las consultas"+sbResultado);
      //sbObjeto = Reportes.creaExcelActiveX(vParametro.getString("cArchivoOrig"),vParametro.getString("cNomDestino"), lAutoImprimir, iNumCopias, iPausaEntreCopias, lCerrarAlFinal, lMostrarAplicacion, sbResultado);
      sbObjeto = Reportes.creaExcelActiveX(vParametro.getString("cArchivoOrig"),vParametro.getString("cNomDestino"), false, 1, iPausaEntreCopias, false, true, sbResultado);
    }
    //Abre la plantilla word y reemplaza las etiquetas por los valores obtenidos
    //Cuando iNumSolicitud y iEjercicioSol traigan valor, quiere decir que se va a realizar una copia de la plantilla en el directorio NAS
    if(oAccion.getCAccion().equals("Word")){
    	String cNombreTemp = "";
    	 //System.out.print(">>>>>>>sbResultado:"+sbResultado);
    	 //System.out.print(">>>>>>>vDatosAdicionales:"+vDatosAdicionales);
    	//sbObjeto= Reportes.creaWordActiveX(vParametro.getString("cArchivoOrig"), vParametro.getString("cNomDestino"), lAutoImprimir, iNumCopias, lMostrarAplicacion, sbResultado, vDatosAdicionales );
    	 sbObjeto= Reportes.creaWordActiveX(vParametro.getString("cArchivoOrig"), vParametro.getString("cNomDestino"), false, 1, true, sbResultado, vDatosAdicionales );
    }

    if(oAccion.getCAccion().equals("WordXML")){
    	String cNombreTemp = "";
    	
    	 if( (!vParametro.getString("iEjercicioSol").equals(null)) && 
    			 (!vParametro.getString("iNumSolicitud").equals(null)) ) {
    		 
    		if (vParametro.getString("iEjercicioSol").length() >0 ){ 
    			cNombreTemp = vParametro.getString("cArchivoOrig");
    	   		vParametro.remove("cArchivoOrig");
    	   		cNombreTemp = cNombreTemp + "_" + vParametro.getString("iEjercicioSol")+"_"+vParametro.getString("iNumSolicitud");
    	   		
    	   		vParametro.put("cArchivoOrig",cNombreTemp);
    		}    	       	   
    	}    	 
      sbObjeto= Reportes.creaWordXMLActiveX(vParametro.getString("iEjercicioSol")+"_"+vParametro.getString("iNumSolicitud"), vParametro.getString("cNomDestino"), lAutoImprimir, iNumCopias, lMostrarAplicacion, sbResultado, vDatosAdicionales );
    }
    
    if(oAccion.getCAccion().equals("PDF")){
    		//System.out.print(">>>>>>>>>>>>>cNombreReporte:"+vParametro.getString("cArchivoOrig"));
    		//System.out.print(">>>>>>>>>>>>>parametros:"+hParametros.toString());
    		 request.getSession().removeAttribute("cNombreReporte");
    	      request.getSession().removeAttribute("hashParametros");
      request.getSession().setAttribute("cNombreReporte",vParametro.getString("cArchivoOrig"));
      request.getSession().setAttribute("hashParametros", hParametros);
      //System.out.print("holaaaaaa tembloooo1111");
      	
      try{
      	sbObjeto = Reportes.creaPDFObject();
      }catch(Exception ex){
    	  ex.printStackTrace();
      }
      
      
      //System.out.print("holaaaaaa tembloooo2222|:" + sbObjeto +":");
    }
  }
  Vector vcListado = new Vector();
  oAccion.navega(vcListado);
  String cNavStatus = oAccion.getCNavStatus();

%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
<script language="JavaScript">
<%
   out.print(oAccion.getArrayCD());
%>
  function fDocCargado(){
    top.FRMCuerpo.fProcesoActual("");
    fEngResultado('<%=request.getParameter("cNombreFRM")%>','CIdDocCargado','','','','');
    top.FRMCuerpo.fSetIntervalo(false);
  }

  top.FRMCuerpo.fProcesoActual("Resultado recibido");
  top.FRMCuerpo.fSetIntervalo(true);

  fEngResultado('<%=request.getParameter("cNombreFRM")%>',
                '<%=request.getParameter("cId")%>',
                '<%=cError%>',
                '<%=cNavStatus%>',
                '<%=oAccion.getIRowPag()%>',
                '<%=oAccion.getBPK()%>');
  top.FRMCuerpo.fProcesoActual("Enviando datos a la aplicación");
</script>
<%
  out.println(sbObjeto);
}%>


