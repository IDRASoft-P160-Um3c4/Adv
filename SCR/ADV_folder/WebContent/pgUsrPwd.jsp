<%@page import="com.micper.seguridad.PasswordHash"%>
<%@ page import="java.util.*" %>
<%@ page import="com.micper.ingsw.*"%>
<%@ page import="com.micper.util.logging.*" %>
<%@ page import="com.micper.seguridad.dao.*" %>
<%@ page import="com.micper.seguridad.vo.*" %>

<%
  int iCveSistema = 44;
  TParametro  vParametros = new TParametro("44");
  TLogger.setSistema("44");
  TDPermisos dPermisos = new TDPermisos();
  String cError = "3";
  Integer usrGpo =-1;
  Integer usrADVId =-1;
  Vector vcMenuUsuario = new Vector();
  HashMap hmPUsuario = new HashMap();
  TVUsuario vUsuario;
  
  boolean lChPwd=false;

  if(application.getAttribute("Sesiones")==null
  &&  vParametros.getPropEspecifica("UnaSesion").toUpperCase().equals("TRUE"))
     application.setAttribute("Sesiones",new CFGSesiones());

  request.getSession(true).removeAttribute("UsrID");

  if(request.getMethod().toUpperCase().compareTo("POST") == 0){
    String         cUsuario = request.getParameter("cUsuario");
    String         cContras = request.getParameter("cContrasena");
    if (request.getParameter("hdBoton").toUpperCase().compareTo("ACEPTAR") == 0){
      if (cUsuario != null && cContras != null){
         vUsuario = dPermisos.accesoUsuario(cUsuario, cContras, "44");
         //TODO modificar el metodo accesoUsuario y agregar la oficina (entero) y guardar en variable usrOfc;          
         if(vUsuario != null){
        	 
        	lChPwd = dPermisos.islChgPwd();
        	
        	//comentar para omitir el cambio de contrasena si son iguales
     //  if(vUsuario.getCUsuario().equals(vUsuario.getCPassword()))
     //  		lChPwd = true;
            //comentar para omitir el cambio de contrasena si son iguales
        	
        	usrGpo=vUsuario.getiCveGrupo();
        	usrADVId=vUsuario.getICveusuario();
            
            dPermisos.menuUsuario("44",vUsuario.getICveusuario());
            vcMenuUsuario = dPermisos.getVcMenuUsuario();
            hmPUsuario = dPermisos.getHmPUsuario();
            if(!vcMenuUsuario.isEmpty()){
               request.getSession(true).setAttribute("UsrID", vUsuario);

               if(application.getAttribute("Sesiones") != null){
                  CFGSesiones cfgSesion = (CFGSesiones) application.getAttribute("Sesiones");
                  if(cfgSesion.setSesion(vUsuario.getCUsuario(),vUsuario.getID(),false))
                     cError = "."+vUsuario.getICveusuario();
                  else
                     cError = "ErrorSesion";
               }else
                 cError = "."+vUsuario.getICveusuario();

            }else{
               cError = "1";
            }
         }else{
            cError = "2";
         }
         if(!cError.substring(0,1).equals(".")){
           request.getSession(true).removeAttribute("UsrID");
         }
      }
    }
  }
%>
<SCRIPT  SRC="<%=vParametros.getPropEspecifica("RutaFuncs")%>CD/ineng.js"></SCRIPT>
<script language="JavaScript">
  aRes[0] = '<%=cError%>';
  
  top.usrGpoId= '<%=usrGpo%>';
  
  <%
   String cNodo;
   if(cError.substring(0,1).equals(".")){
     int i;
     TVMenu vMenu;
     for(i=0;i<vcMenuUsuario.size();i++){
       vMenu = (TVMenu) vcMenuUsuario.get(i);
       
       
       if(vMenu.getICveSistema() == iCveSistema){
         if(vMenu.getCReferencia().equals("#"))
           cNodo = "0";
         else
           cNodo = "2";
         out.println("aRes["+(i+1)+"]=['"+vMenu.getCDscMenu()+"','"+vMenu.getCReferencia()+"','"+vMenu.getIOpcPadre()+"','"+vMenu.getIOrden()+"',"+cNodo+",0,0];");
       }
     }
     i++;
     out.println("aRes["+i+"]=['Permisos'];");
     Set setPU = hmPUsuario.keySet();
     Iterator itPU = setPU.iterator();
     String cPrograma;
     while (itPU.hasNext()){
       i++;
       cPrograma = itPU.next().toString();
       StringTokenizer stPU = new StringTokenizer(hmPUsuario.get(cPrograma).toString(),"|");
       out.println("aRes["+i+"]=['"+cPrograma+"','"+stPU.nextToken()+"','"+stPU.nextToken()+"'];");
     }
   }
  %>
  
  fEngResultado('<%=request.getParameter("cNombreFRM")%>','<%=request.getParameter("cId")%>','<%=cError%>','<%=lChPwd%>');
</script>

