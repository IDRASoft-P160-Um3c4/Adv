<%@ page import="java.util.*" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="gob.sct.sipmm.dao.*" %>
<%@ page import="com.micper.sql.*" %>
<%@ page import="com.micper.util.*" %>


<%
/**
 * <p>Title: pgRecibeDatosIngresos.jsp</p>
 * <p>Description: JSP que recibe las referencias generadas por el sistema de ingresos</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author ABarrientos
 * @version 1.0
 */
  TLogger.setSistema("44");
  TParametro  vParametros = new TParametro("44");
  TVDinRep vDinRep;

  int iNumElem;
  boolean lHuboError=false;
  StringBuffer sbError = new StringBuffer();
  String cJSSource = "", cRutaImg="", cConceptos;

  CFGAccion oAccion = new CFGAccion(pageContext.getRequest());

  /** Verifica si existe una o más sesiones */
  if(!oAccion.unaSesion(vParametros,(CFGSesiones)application.getAttribute("Sesiones"),(TVUsuario)request.getSession(true).getAttribute("UsrID")))
    out.print(oAccion.getErrorSesion(vParametros.getPropEspecifica("RutaFuncs")));
  else{

    try{

       vDinRep = new TVDinRep();

       //0=Ejercicio, 1=NoSolicitud, 2=hdRutaJSSource, 3=hdRutaImg, 4=conceptos
       String[] aDatosAdic = request.getParameter("hdDatosAdicionales").split(",");
       String[] aLlaveRefAlfaNum = request.getParameter("cRefAlfaNum").split(",");
       String cRefsAlfanum="";

       for(int iContAlf=0;iContAlf<aLlaveRefAlfaNum.length;iContAlf++){
         String cTemp = aLlaveRefAlfaNum[iContAlf];
         cTemp = cTemp.replace('.',',');
                  String[] aRefAlfanum = cTemp.split(",");
         cRefsAlfanum += (iContAlf==0)?aRefAlfanum[1]:","+aRefAlfanum[1];
       }

       cJSSource = aDatosAdic[2];
       cRutaImg = aDatosAdic[3] + "wwwrooting/img/";
       String cTemp = "";
       cTemp = aDatosAdic[4];
       cTemp = cTemp.replace('\\',',');
       cConceptos = cTemp;

       vDinRep = new TVDinRep();
       vDinRep.put("iEjercicio",aDatosAdic[0]);
       vDinRep.put("iNumSolicitud",aDatosAdic[1]);
       vDinRep.put("Mov_iRefNumerica",request.getParameter("cRefNumerica"));
       vDinRep.put("Mov_cRefAlfaNum",cRefsAlfanum);
       vDinRep.put("Mov_dImportePagar",request.getParameter("cImporte"));

       //Para no modificar el método que inserta el registro de referencias se hace una cadena de personas
       //y fecha de movimiento separados por comas
       String[] aRefsNum     = request.getParameter("cRefNumerica").split(",");
       String[] aRefsAlfaNum = cRefsAlfanum.split(",");
       String[] aImportesAPagar = request.getParameter("cImporte").split(",");

       int iNumRef = aRefsNum.length;
       String cCvePersona = "", cFechaMov="", cNumUnidades="";
       for(int iCont=0;iCont<iNumRef;iCont++){
         cCvePersona += (iCont==0)?request.getParameter("iCvePersona"):","+request.getParameter("iCvePersona");
         cFechaMov   += (iCont==0)?request.getParameter("dtFechaMovim"):","+request.getParameter("dtFechaMovim");
         cNumUnidades   += (iCont==0)?"1":","+"1";
       }
       vDinRep.put("Mov_iNumUnidades",cNumUnidades);
       vDinRep.put("Mov_iCveConcepto",cConceptos);
       vDinRep.put("Mov_iCveSolicitanteIngresos",cCvePersona);
       vDinRep.put("Mov_dFechaRef",cFechaMov);

       TDTRARegRefPago dTRARegRefPago = new TDTRARegRefPago();
       try{
         vDinRep = dTRARegRefPago.insertBatch(vDinRep,null);
       }catch(Exception ex){
         ex.printStackTrace();
       }

       if(vDinRep.size()>0){
         sbError =  new StringBuffer();
         sbError.append("<b>Se registraron las referencias:</b>")
                .append("<br><br>Ejercicio: "+aDatosAdic[0]+"   No. Solicitud: "+aDatosAdic[1] )
                .append("<br>Oprima el botón salir para continuar con su proceso.")
                ;
       }

    } catch(Exception e){
      e.printStackTrace();
    }


%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>

<%
   //out.print(oAccion.getArrayCD());
        out.println("<html><head><title>Referencias Generadas En El Sistema de Ingresos</title></head><body>");
        //out.println("<form name='form1' method='post' action=''>");
        out.println("<body onLoad='fCambiaTamanio();' bgcolor='DEEFF7'>");
        out.println("<form name='form1' enctype='multipart/form-data' method='post' action='pgLeeArchivo.jsp'>");
        out.println("<SCRIPT LANGUAGE='JavaScript' SRC='"+cJSSource+"fnc/prop.js'></SCRIPT>");
        out.println("<SCRIPT LANGUAGE='JavaScript' SRC='"+cJSSource+"fnc/CD/valida_nt.js'></SCRIPT>");
        out.println("<SCRIPT LANGUAGE='JavaScript' SRC='"+cJSSource+"fnc/CD/ineng.js'></SCRIPT>");
        out.println("<SCRIPT LANGUAGE='JavaScript' SRC='"+cJSSource+"fnc/CD/imagenes.js'></SCRIPT>");
        out.println("<SCRIPT LANGUAGE='JavaScript' SRC='"+cJSSource+"fnc/CD/componentes.js'></SCRIPT>");
        out.println("<link rel='stylesheet' href='"+cJSSource+"wwwrooting/estilos/estilos.css' TYPE='text/css'>");

        out.println("<tr>");
        out.println("<td class=\"ESTitulo\" width=\"100%\" align=\"center\">");
        out.println("<table border=\"0\" class=\"ESTitulo\" width=\"100%\" height=\"23\">");
        out.println("<tr>");
        out.println("<td class=\"ESTitulo\" align=\"center\">");
        out.println("Referencias Generadas En El Sistema de Ingresos");
        out.println("</td>");
        out.println("<td class=\"ESTitulo\" width=\"70\">");
        out.println("<a href=\"JavaScript:fCargaAyuda();\"");
        out.println(" onMouseOut=\"if(fCambiaImagen)fCambiaImagen(document,'BtnAyuda','','"+cRutaImg+"ayuda01.gif',1);self.status='';return true;\"");
        out.println(" onMouseOver=\"if(fCambiaImagen)fCambiaImagen(document,'BtnAyuda','','"+cRutaImg+"ayuda02.gif',1);self.status='Muestra la Ayuda de la Pantalla...';return true;\">");
        out.println("<img border=\"0\" name=\"BtnAyuda\" src=\""+cRutaImg+"ayuda01.gif\" alt=\"Muestra la Ayuda de la Pantalla...\"></a>");
        out.println("</td>");
        out.println("<td class=\"ESTitulo\" width=\"70\">");
        out.println("<a href=\"JavaScript:if(top.opener)top.close();\"");
        out.println(" onMouseOut=\"if(fCambiaImagen)fCambiaImagen(document,'BtnSalir','','"+cRutaImg+"salir01.gif',1);self.status='';return true;\"");
        out.println(" onMouseOver=\"if(fCambiaImagen)fCambiaImagen(document,'BtnSalir','','"+cRutaImg+"salir02.gif',1);self.status='Cierra la ventana sin ejecutar acción alguna...';return true;\">");
        out.println("<img border=\"0\" name=\"BtnSalir\" src=\""+cRutaImg+"salir01.gif\" alt=\"Cierra la ventana sin ejecutar acción alguna...\"></a>");
        out.println("</td></tr>");

        out.println("<table border='0' width='100%' cellspacing='center'>");
        out.println("<table border='0' width='100%' align='center'>");

        out.println("<table border='0' class='ETablaInfo' cellspacing='center' align='center'>");

        out.println("<tr><td class='EEtiquetaL'><p>"+sbError.toString());
        out.println("</p></td></tr>");

        out.println("</table>");
        out.println("</table>");
        out.println("</table>");

        out.println("</form></body></html>");

        out.println("<SCRIPT LANGUAGE='JavaScript'>");
        out.println(" function fEngResultado(cError){");
        //out.println("   form1.cObs.value = cError;");
        out.println("   if(top.opener.fRefrescaListado) ");
        out.println("     top.opener.fRefrescaListado(); ");
        out.println("   else ");
        out.println("     top.opener.fNavega(); ");
        out.println(" }");
        out.println(" function fCambiaTamanio(){");
        out.println("     window.resizeTo(600,300); ");
        out.println("      iX =  (screen.availWidth - 600) / 2;");
        out.println("      iY =  (screen.availHeight - 300) / 2;");
        out.println("      window.moveTo(iX, iY);");
        out.println(" }</SCRIPT>");

%>
<script language="JavaScript">
  fEngResultado('<%=sbError%>');
</script>

<%}%>
