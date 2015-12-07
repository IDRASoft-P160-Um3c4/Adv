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
 * @author Rafael Miranda Blumenkron
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;

  TReportes Reportes = new TReportes("44");
  String cError = "";
  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());

 /** Se realiza la actualización de Datos a través de actualizar el vector con el Query */
  runMethodInClass tEjecuta = new runMethodInClass();
  StringBuffer sbResultado = new StringBuffer(""),
               sbObjeto    = new StringBuffer("");
  Object oDatos;
  Vector vDatosAdicionales = new Vector();
  HashMap hParametros = new HashMap();
  //Si esta configurado que necesita folio par2 se le asigna el folio, par3 se le asigna la cve de oficina  y par 4 se le asigna la cve de depto
  String par2 = "", par3 = "", par4 = "";
  try{
    String folio = "";

    //Ejecuta runMethodInClass con los siguientes parámetros (ver documentación de runMethodInClass)
    // *vParametros.getPropEspecifica("cRutaDAO") - Contiene el package de donde se encuentra la clase
    // *request.getParameter("cDAOEjecutar") - Contiene el nombre de la clase donde se encuentra el método a ejecutar
    // *"Par1^" + request.getParameter("hdFiltrosRep") + "¨"+par2...   - Contiene los parámetros q recibe el método a ejecutar
    // *request.getParameter("cMetodoTemp") - Contiene el nombre del método a ejecutar
    //En este caso recibe como resultado los datos con los que se va a armar el reporte o los que van a reemplazar etiquetas en un documento word
//    System.out.print("--------request de hdFiltroRep-------->> "+request.getParameter("hdFiltrosRep"));
    oDatos = (Object)tEjecuta.runMethodInClass(vParametros.getPropEspecifica("cRutaDAO")+ request.getParameter("cDAOEjecutar"),request.getParameter("cMetodoTemp"), "Par1^" + request.getParameter("hdFiltrosRep") + "$"+par2+par3+par4);
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

  if (request.getParameter("lAutoImprimir").equals("1"))
    lAutoImprimir = true;

  if (lAutoImprimir)
    iNumCopias = Integer.parseInt(request.getParameter("iNumCopias"));
  if (iNumCopias <= 0)
    iNumCopias = 1;

  if (request.getParameter("lMostrarAplicacion").equals("1"))
    lMostrarAplicacion = true;

  if (lAutoImprimir && !lMostrarAplicacion)
    lCerrarAlFinal = true;

  if (sbResultado == null || sbResultado.toString().equals(""))
    cError = "No hay datos para desplegar en el reporte";

  if (cError.equals("")){
    //Genera el reporte en excel
    if(oAccion.getCAccion().equals("Excel")){
      sbObjeto = Reportes.creaExcelActiveX(request.getParameter("lMostrarAplicacion"),request.getParameter("cNomDestino"), lAutoImprimir, iNumCopias, iPausaEntreCopias, lCerrarAlFinal, lMostrarAplicacion, sbResultado);
    }
    //Abre la plantilla word y reemplaza las etiquetas por los valores obtenidos
    if(oAccion.getCAccion().equals("Word"))
      sbObjeto= Reportes.creaWordActiveX(request.getParameter("cArchivoOrig"), request.getParameter("cNomDestino"), lAutoImprimir, iNumCopias, lMostrarAplicacion, sbResultado, vDatosAdicionales );

    if(oAccion.getCAccion().equals("PDF")){
      request.getSession().setAttribute("cNombreReporte",request.getParameter("cArchivoOrig"));
      request.getSession().setAttribute("hashParametros", hParametros);
      sbObjeto = Reportes.creaPDFObject();
    }
  }
  Vector vcListado = new Vector();
  oAccion.navega(vcListado);
  String cNavStatus = oAccion.getCNavStatus();

%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>prop.js"></SCRIPT>
<script language="JavaScript">
  var aRes = new Array();
<%
   out.print(oAccion.getArrayCD());
%>
  function fDocCargado(){
    window.parent.fResultado(new Array(),"CIdDocCargado","");
  }
</script>
</body>

<%
  out.println(sbObjeto);
%>


