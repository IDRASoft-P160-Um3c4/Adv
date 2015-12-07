<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>
<%
  int iCveSistema = 44;
  TLogger.setSistema(""+iCveSistema);
  TParametro  vParametros = new TParametro(""+iCveSistema);

  TDPermisos dPermisos = new TDPermisos();

  String cError = "ineng-3";

  Vector vcMenuUsuario = new Vector();
  HashMap hmPUsuario = new HashMap();

  TVUsuario vUsuario = null;
    if(request.getMethod().toUpperCase().compareTo("POST") == 0){
      String cParametro = request.getParameter("cParametro");
      StringTokenizer stParametro = new StringTokenizer(cParametro,"/");
      String         cUsuario = stParametro.nextToken();
      String         cContras = stParametro.nextToken();
      boolean lSesionEstablecida = false;
      if (cUsuario != null && cContras != null){
        try{
           vUsuario = dPermisos.accesoUsuario(cUsuario, cContras, ""+iCveSistema);
           if(application.getAttribute("Sesiones")==null &&  vParametros.getPropEspecifica("UnaSesion").toUpperCase().equals("TRUE"))
             application.setAttribute("Sesiones",new CFGSesiones());
           request.getSession(true).removeAttribute("UsrID");
        }catch(Exception e){
          vUsuario = null;
        }
         if(vUsuario != null){
           try{
             request.getSession(true).setAttribute("UsrID", vUsuario);
             cError = "ineng-1";
           }catch(Exception e){
           }
         }
      }
    }
%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
<script language="JavaScript">
  aRes[0] = '<%=cError%>';
  fEngResultado('<%=request.getParameter("cNombreFRM")%>','<%=request.getParameter("cId")%>');
</script>

